package ru.productstar.mockito.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.productstar.mockito.model.Customer;
import ru.productstar.mockito.repository.CustomerRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    /**
     * Тест 1 - Получение покупателя "Ivan"
     * Проверки:
     * - очередность и точное количество вызовов каждого метода из CustomerRepository
     */
    @Test
    void testGetCustomerIvan() {
        // Подготовка
        CustomerRepository mockRepo = mock(CustomerRepository.class);
        CustomerService service = new CustomerService(mockRepo);
        Customer ivan = new Customer("Ivan");

        when(mockRepo.getByName("Ivan")).thenReturn(ivan);

        // Вызов
        Customer result = service.getOrCreate("Ivan");

        // Проверка результата
        assertSame(ivan, result);

        // Проверка очередности и точного количества вызовов
        InOrder inOrder = inOrder(mockRepo);
        inOrder.verify(mockRepo, times(1)).getByName("Ivan");
        inOrder.verify(mockRepo, never()).add(any(Customer.class));
    }

    /**
     * Тест 2 - Получение покупателя "Oleg"
     * Проверки:
     * - очередность и точное количество вызовов каждого метода из CustomerRepository
     * - в метод getOrCreate была передана строка "Oleg"
     */
    @Test
    void testGetCustomerOleg() {
        // Подготовка
        CustomerRepository mockRepo = mock(CustomerRepository.class);
        CustomerService service = new CustomerService(mockRepo);
        Customer oleg = new Customer("Oleg");

        when(mockRepo.getByName("Oleg")).thenReturn(null);
        when(mockRepo.add(any(Customer.class))).thenReturn(oleg);

        // Вызов
        Customer result = service.getOrCreate("Oleg");

        // Проверка результата
        assertSame(oleg, result);

        // Проверка очередности и точного количества вызовов
        InOrder inOrder = inOrder(mockRepo);
        inOrder.verify(mockRepo, times(1)).getByName("Oleg");
        inOrder.verify(mockRepo, times(1)).add(argThat(customer -> "Oleg".equals(customer.getName())));
    }
}
