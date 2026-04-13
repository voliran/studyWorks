package ru.productstar.mockito.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.productstar.mockito.ProductNotFoundException;
import ru.productstar.mockito.model.*;
import ru.productstar.mockito.repository.InitRepository;
import ru.productstar.mockito.repository.OrderRepository;
import ru.productstar.mockito.repository.ProductRepository;
import ru.productstar.mockito.repository.WarehouseRepository;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private CustomerService customerService;
    @Mock
    private WarehouseService warehouseService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(customerService, warehouseService,
                orderRepository, productRepository);
    }

    /**
     * Сценарий: создание ордера для существующего клиента
     */
    @Test
    void testCreateOrderForExistingCustomer() {
        // Подготовка
        Customer existingCustomer = new Customer("Ivan");
        Order expectedOrder = new Order(existingCustomer);
        expectedOrder.setId(1);

        when(customerService.getOrCreate("Ivan")).thenReturn(existingCustomer);
        when(orderRepository.create(existingCustomer)).thenReturn(expectedOrder);

        // Вызов
        Order result = orderService.create("Ivan");

        // Проверка
        assertSame(expectedOrder, result);

        // Проверка порядка и количества вызовов
        InOrder inOrder = inOrder(customerService, orderRepository);
        inOrder.verify(customerService, times(1)).getOrCreate("Ivan");
        inOrder.verify(orderRepository, times(1)).create(existingCustomer);
    }

    /**
     * Сценарий: создание ордера для нового клиента
     */
    @Test
    void testCreateOrderForNewCustomer() {
        // Подготовка
        Customer newCustomer = new Customer("Oleg");
        Order expectedOrder = new Order(newCustomer);
        expectedOrder.setId(1);

        when(customerService.getOrCreate("Oleg")).thenReturn(newCustomer);
        when(orderRepository.create(newCustomer)).thenReturn(expectedOrder);

        // Вызов
        Order result = orderService.create("Oleg");

        // Проверка
        assertSame(expectedOrder, result);

        // Проверка порядка и количества вызовов
        InOrder inOrder = inOrder(customerService, orderRepository);
        inOrder.verify(customerService, times(1)).getOrCreate("Oleg");
        inOrder.verify(orderRepository, times(1)).create(newCustomer);
    }

    /**
     * Сценарий: добавление существующего товара в достаточном количестве
     * Проверка: общая сумма заказа
     */
    @Test
    void testAddExistingProductWithEnoughStock() throws Exception {
        // Подготовка
        Order order = new Order(new Customer("Ivan"));
        order.setId(1);
        String productName = "laptop";
        int count = 1;
        int productPrice = 900;

        Product product = new Product(productName);
        Warehouse warehouse = new Warehouse("WH1", 10);
        Stock stock = new Stock(product, productPrice, 5);

        when(warehouseService.findWarehouse(productName, count)).thenReturn(warehouse);
        when(productRepository.getByName(productName)).thenReturn(product);
        when(warehouseService.getStock(warehouse, productName)).thenReturn(stock);

        // НАСТРОЙКА: при вызове addDelivery возвращаем обновленную сумму
        when(orderRepository.addDelivery(eq(1), any(Delivery.class))).thenAnswer(invocation -> {
            Delivery delivery = invocation.getArgument(1);
            long newTotal = order.addDelivery(delivery);  // реально добавляем в заказ
            return order;
        });

        // Вызов
        orderService.addProduct(order, productName, count, false);

        // ПРОВЕРКА: общая сумма заказа соответствует ожидаемой
        long expectedTotal = productPrice * count; // 900 * 1 = 900
        assertEquals(expectedTotal, order.getTotal());

        // Проверка порядка и количества вызовов
        InOrder inOrder = inOrder(warehouseService, productRepository, warehouseService, orderRepository);
        inOrder.verify(warehouseService, times(1)).findWarehouse(productName, count);
        inOrder.verify(productRepository, times(1)).getByName(productName);
        inOrder.verify(warehouseService, times(1)).getStock(warehouse, productName);
        inOrder.verify(orderRepository, times(1)).addDelivery(eq(1), any(Delivery.class));
    }

    /**
     * Сценарий: добавление несуществующего товара
     * Проверка: корректная работа, выбрасывание ProductNotFoundException
     */
    @Test
    void testAddNonExistingProduct() {
        // Подготовка
        Order order = new Order(new Customer("Ivan"));
        order.setId(1);
        String productName = "fake_product";
        int count = 1;

        when(warehouseService.findWarehouse(productName, count)).thenReturn(null);

        // Проверка, что выбросилось исключение
        assertThrows(ProductNotFoundException.class, () ->
                orderService.addProduct(order, productName, count, false)
        );

        // Проверка порядка и количества вызовов
        verify(warehouseService, times(1)).findWarehouse(productName, count);
        verify(productRepository, never()).getByName(anyString());
        verify(orderRepository, never()).addDelivery(anyInt(), any());
    }

    /**
     * Сценарий: добавление товара в недостаточном количестве
     * Проверка: выбрасывание ProductNotFoundException
     */
    @Test
    void testAddProductWithInsufficientStock() {
        // Подготовка
        Order order = new Order(new Customer("Ivan"));
        order.setId(1);
        String productName = "phone";
        int count = 100; // больше чем есть на складах

        when(warehouseService.findWarehouse(productName, count)).thenReturn(null);

        // Проверка, что выбросилось исключение
        assertThrows(ProductNotFoundException.class, () ->
                orderService.addProduct(order, productName, count, false)
        );

        // Проверка вызовов
        verify(warehouseService, times(1)).findWarehouse(productName, count);
    }

    /**
     * Сценарий: заказ товара с быстрой доставкой
     * Проверка: используется findClosestWarehouse вместо findWarehouse
     */
    @Test
    void testAddProductWithFastestDelivery() throws Exception {
        // Подготовка
        Order order = new Order(new Customer("Ivan"));
        order.setId(1);
        String productName = "keyboard";
        int count = 3;
        int productPrice = 40;

        Product product = new Product(productName);
        Warehouse closestWarehouse = new Warehouse("ClosestWH", 5);
        Stock stock = new Stock(product, productPrice, 10);

        when(warehouseService.findClosestWarehouse(productName, count)).thenReturn(closestWarehouse);
        when(productRepository.getByName(productName)).thenReturn(product);
        when(warehouseService.getStock(closestWarehouse, productName)).thenReturn(stock);
        when(orderRepository.addDelivery(eq(1), any(Delivery.class))).thenReturn(order);

        // Вызов с fastestDelivery = true
        orderService.addProduct(order, productName, count, true);

        // Проверка: вызван именно findClosestWarehouse
        verify(warehouseService, times(1)).findClosestWarehouse(productName, count);
        verify(warehouseService, never()).findWarehouse(anyString(), anyInt());
    }
}