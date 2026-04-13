package ru.productstar.mockito.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.productstar.mockito.model.Product;
import ru.productstar.mockito.model.Stock;
import ru.productstar.mockito.model.Warehouse;
import ru.productstar.mockito.repository.InitRepository;
import ru.productstar.mockito.repository.WarehouseRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class WarehouseServiceTest {

    @Mock
    private WarehouseRepository warehouseRepository;

    private WarehouseService warehouseService;

    @BeforeEach
    void setUp() {
        warehouseService = new WarehouseService(warehouseRepository);
    }

    /**
     * Сценарий: поиск несуществующего товара
     * Проверка: корректная работа, возвращается null
     * Поиск на минимум 3 складах
     */
    @Test
    void testFindNonExistingProduct() {
        // Подготовка - создаем 3 склада с разными товарами
        Warehouse wh1 = new Warehouse("WH1", 30);
        wh1.addStock(new Stock(new Product("phone"), 400, 5));

        Warehouse wh2 = new Warehouse("WH2", 20);
        wh2.addStock(new Stock(new Product("laptop"), 900, 3));

        Warehouse wh3 = new Warehouse("WH3", 5);
        wh3.addStock(new Stock(new Product("printer"), 200, 4));

        when(warehouseRepository.all()).thenReturn(Arrays.asList(wh1, wh2, wh3));

        // Вызов - ищем несуществующий товар
        Warehouse result = warehouseService.findWarehouse("nonexistent", 1);

        // Проверка
        assertNull(result);
        verify(warehouseRepository, times(1)).all();
    }

    /**
     * Сценарий: поиск существующего товара с достаточным количеством
     * Проверка: товар находится на нужном складе, учитывается количество
     * Поиск на минимум 3 складах
     */
    @Test
    void testFindExistingProductWithEnoughStock() {
        // Подготовка - создаем 3 склада с телефонами в разном количестве
        Product phone = new Product("phone");

        Warehouse wh1 = new Warehouse("WH1", 30);
        wh1.addStock(new Stock(phone, 400, 5)); // 5 телефонов

        Warehouse wh2 = new Warehouse("WH2", 20);
        wh2.addStock(new Stock(phone, 380, 1)); // 1 телефон - НЕ достаточно

        Warehouse wh3 = new Warehouse("WH3", 5);
        wh3.addStock(new Stock(phone, 450, 3)); // 3 телефона

        when(warehouseRepository.all()).thenReturn(Arrays.asList(wh1, wh2, wh3));

        // Вызов - ищем 2 телефона (должен найти WH1 или WH3)
        Warehouse result = warehouseService.findWarehouse("phone", 2);

        // Проверка: товар найден на складе с достаточным количеством
        assertNotNull(result);
        assertTrue(result.getName().equals("WH1") || result.getName().equals("WH3"));

        // Проверяем что на найденном складе действительно достаточно товара
        Stock stock = warehouseService.getStock(result, "phone");
        assertTrue(stock.getCount() >= 2);

        verify(warehouseRepository, times(1)).all();
    }

    /**
     * Сценарий: поиск существующего товара с недостаточным количеством
     * Проверка: возвращается null, так как нет склада с нужным количеством
     * Поиск на минимум 3 складах
     */
    @Test
    void testFindExistingProductWithInsufficientStock() {
        // Подготовка - создаем 3 склада, но везде мало товара
        Product laptop = new Product("laptop");

        Warehouse wh1 = new Warehouse("WH1", 30);
        wh1.addStock(new Stock(laptop, 900, 3)); // только 3 ноутбука

        Warehouse wh2 = new Warehouse("WH2", 20);
        wh2.addStock(new Stock(laptop, 850, 1)); // только 1 ноутбук

        Warehouse wh3 = new Warehouse("WH3", 5);
        wh3.addStock(new Stock(laptop, 800, 2)); // только 2 ноутбука

        when(warehouseRepository.all()).thenReturn(Arrays.asList(wh1, wh2, wh3));

        // Вызов - ищем 10 ноутбуков (больше чем есть везде)
        Warehouse result = warehouseService.findWarehouse("laptop", 10);

        // Проверка: не найдено
        assertNull(result);
        verify(warehouseRepository, times(1)).all();
    }

    /**
     * Тест для findClosestWarehouse
     * Проверка: учитывается расстояние до склада
     * Поиск на минимум 3 складах
     */
    @Test
    void testFindClosestWarehouse() {
        // Подготовка - создаем 3 склада с клавиатурами на разном расстоянии
        Product keyboard = new Product("keyboard");

        Warehouse farWH = new Warehouse("FarWH", 30);      // расстояние 30
        farWH.addStock(new Stock(keyboard, 40, 10));

        Warehouse closeWH = new Warehouse("CloseWH", 5);   // расстояние 5 - самый близкий
        closeWH.addStock(new Stock(keyboard, 40, 10));

        Warehouse midWH = new Warehouse("MidWH", 15);      // расстояние 15
        midWH.addStock(new Stock(keyboard, 40, 10));

        when(warehouseRepository.all()).thenReturn(Arrays.asList(farWH, closeWH, midWH));

        // Вызов - ищем ближайший склад с 5 клавиатурами
        Warehouse result = warehouseService.findClosestWarehouse("keyboard", 5);

        // Проверка: должен вернуть самый близкий склад (расстояние 5)
        assertNotNull(result);
        assertEquals("CloseWH", result.getName());
        assertEquals(5, result.getDistance());

        verify(warehouseRepository, times(1)).all();
    }
}