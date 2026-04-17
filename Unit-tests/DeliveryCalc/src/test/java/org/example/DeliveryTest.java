package org.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты расчета стоимости доставки")
class DeliveryTest {

    private List<Product> products;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
    }

    //  БАЗОВЫЕ ТЕСТЫ

    @Nested
    @DisplayName("Базовые сценарии")
    @Tag("basic")
    class BasicTests {

        @Test
        @DisplayName("Обычный товар, малый размер, небольшое расстояние")
        void testNormalProductSmallDistance() throws FragileDistanceExceededException {
            products.add(new Product(1000, "book", false, "small"));
            Delivery delivery = new Delivery(5, products, Workload.NORMAL);

            double result = delivery.deliveryCoast();
            // small: +100, расстояние 5км: +100, итого 200 * 1.0 = 200
            // минималка 400 -> возвращает 0
            assertEquals(0.0, result, 0.001);
        }

        @Test
        @DisplayName("Крупногабаритный товар, среднее расстояние")
        void testLargeProductMediumDistance() throws FragileDistanceExceededException {
            products.add(new Product(5000, "table", false, "large"));
            Delivery delivery = new Delivery(20, products, Workload.NORMAL);

            double result = delivery.deliveryCoast();
            // large: +200, расстояние 20км: +200, итого 400 * 1.0 = 400
            assertEquals(400.0, result, 0.001);
        }

        @Test
        @DisplayName("Хрупкий товар, короткое расстояние")
        void testFragileProductShortDistance() throws FragileDistanceExceededException {
            products.add(new Product(3000, "vase", true, "small"));
            Delivery delivery = new Delivery(15, products, Workload.NORMAL);

            double result = delivery.deliveryCoast();
            // small: +100, fragile: +300, расстояние 15км: +200, итого 600 * 1.0 = 600
            assertEquals(600.0, result, 0.001);
        }

        @Test
        @DisplayName("Несколько товаров разных типов")
        void testMultipleProducts() throws FragileDistanceExceededException {
            products.add(new Product(250, "lamp", true, "small"));
            products.add(new Product(10000, "desk", false, "large"));
            products.add(new Product(25000, "phone", true, "small"));
            Delivery delivery = new Delivery(20, products, Workload.VERY_HIGH);

            double result = delivery.deliveryCoast();
            // lamp: 100+300=400, desk: 200, phone: 100+300=400 -> итого 1000
            // расстояние 20км: +200 -> 1200 * 1.6 = 1920
            assertEquals(1920.0, result, 0.001);
        }
    }

    // ТЕСТЫ РАССТОЯНИЙ

    @Nested
    @DisplayName("Тесты расстояний")
    @Tag("edge")
    class DistanceTests {

        @BeforeEach
        void addStandardProduct() {
            products.add(new Product(1000, "book", false, "small"));
        }

        @ParameterizedTest(name = "расстояние = {0} км, ожидаемая наценка = {1}")
        @CsvSource({
                "1,   50",   // < 2 км -> +50
                "2,   100",   // 2 км -> +100
                "5,   100",   // 2-10 км -> +100
                "10,  200",   // 10 км -> +200
                "15,  200",  // 10-30 км -> +200
                "30,  200",  // 30 км -> +200
                "50,  300"   // > 30 км -> +300
        })
        void testDistanceSurcharge(double distance, double expectedSurcharge) throws FragileDistanceExceededException {
            Delivery delivery = new Delivery(distance, products, Workload.HIGH);
            double result = delivery.deliveryCoast();
            // small: +100, плюс наценка за расстояние
            double expected = (100 + expectedSurcharge) * 1.4;
            if (expected < 400) expected = 0;
            assertEquals(expected, result, 0.001);
        }

        @Test
        @DisplayName("Расстояние = 0 км")
        void testZeroDistance() throws FragileDistanceExceededException {
            products.add(new Product(1000, "book", false, "small"));
            Delivery delivery = new Delivery(0, products, Workload.NORMAL);
            double result = delivery.deliveryCoast();
            // small: +100, расстояние <2км: +50, итого 150 (<400) -> 0
            assertEquals(0.0, result, 0.001);
        }
    }

    // ТЕСТЫ ГАБАРИТОВ

    @Nested
    @DisplayName("Тесты габаритов")
    @Tag("basic")
    class DimensionsTests {

        @ParameterizedTest(name = "Размер = {0}, ожидаемая наценка = {1}")
        @CsvSource({
                "small, 100",
                "large, 200"
        })
        void testDimensionsSurcharge(String dimensions, int expectedSurcharge)
                throws FragileDistanceExceededException {
            products.add(new Product(1000, "product", false, dimensions));
            Delivery delivery = new Delivery(1, products, Workload.NORMAL);
            double result = delivery.deliveryCoast();
            double expected = (expectedSurcharge + 50) * 1.0; // +50 за расстояние <2км
            if (expected < 400) expected = 0;
            assertEquals(expected, result, 0.001);
        }

        @Test
        @DisplayName("Неизвестный размер (должен обработаться в else)")
        void testInvalidDimensions() throws FragileDistanceExceededException {
            products.add(new Product(1000, "product", false, "unknown"));
            Delivery delivery = new Delivery(1, products, Workload.NORMAL);
            double result = delivery.deliveryCoast();
            // unknown -> else -> +100, расстояние +50 -> 150 (<400) -> 0
            assertEquals(0.0, result, 0.001);
        }
    }

    // ТЕСТЫ ХРУПКОСТИ

    @Nested
    @DisplayName("Тесты хрупкости")
    @Tag("error")
    class FragileTests {

        @Test
        @DisplayName("Хрупкий товар, расстояние > 30 км -> исключение")
        void testFragileDistanceExceeded() {
            products.add(new Product(1000, "vase", true, "small"));
            Delivery delivery = new Delivery(50, products, Workload.NORMAL);

            assertThrows(FragileDistanceExceededException.class, () -> {
                delivery.deliveryCoast();
            });
        }

        @Test
        @DisplayName("Хрупкий товар, расстояние = 30 км -> ок (без исключения)")
        void testFragileDistanceBoundary() throws FragileDistanceExceededException {
            products.add(new Product(1000, "vase", true, "small"));
            Delivery delivery = new Delivery(30, products, Workload.NORMAL);

            double result = delivery.deliveryCoast();
            // small: 100, fragile: 300, расстояние 30км: +200 -> 600
            assertEquals(600.0, result, 0.001);
        }

        @Test
        @DisplayName("Хрупкий товар, расстояние = 30.1 км -> исключение")
        void testFragileDistanceJustAboveBoundary() {
            products.add(new Product(1000, "vase", true, "small"));
            Delivery delivery = new Delivery(30.1, products, Workload.NORMAL);

            assertThrows(FragileDistanceExceededException.class, () -> {
                delivery.deliveryCoast();
            });
        }

        @Test
        @DisplayName("Несколько товаров: один хрупкий на расстояние >30 км -> исключение")
        void testMultipleProductsWithFragileExceeded() {
            products.add(new Product(1000, "book", false, "small"));
            products.add(new Product(5000, "vase", true, "small"));
            products.add(new Product(3000, "table", false, "large"));
            Delivery delivery = new Delivery(40, products, Workload.NORMAL);

            assertThrows(FragileDistanceExceededException.class, () -> {
                delivery.deliveryCoast();
            });
        }

        @Test
        @DisplayName("Хрупкий товар, но расстояние в пределах нормы")
        void testFragileNormalDistance() throws FragileDistanceExceededException {
            products.add(new Product(1000, "vase", true, "small"));
            Delivery delivery = new Delivery(25, products, Workload.NORMAL);
            double result = delivery.deliveryCoast();
            // small: 100, fragile: 300, расстояние 25км: +200 -> 600
            assertEquals(600.0, result, 0.001);
        }
    }

    // ТЕСТЫ НАГРУЗКИ (WORKLOAD)

    @Nested
    @DisplayName("Тесты коэффициентов нагрузки")
    @Tag("basic")
    class WorkloadTests {

        @ParameterizedTest
        @EnumSource(Workload.class)
        @DisplayName("Проверка всех уровней нагрузки")
        void testAllWorkloads(Workload workload) throws FragileDistanceExceededException {
            products.add(new Product(5000, "desk", false, "large"));
            Delivery delivery = new Delivery(20, products, workload);
            double result = delivery.deliveryCoast();

            // large: 200, расстояние 20км: +200 -> 400
            double expected = 400 * workload.getCoefficient();
            assertEquals(expected, result, 0.001);
        }

        @Test
        @DisplayName("VERY_HIGH коэффициент = 1.6")
        void testVeryHighCoefficient() throws FragileDistanceExceededException {
            products.add(new Product(1000, "book", false, "small"));
            Delivery delivery = new Delivery(20, products, Workload.VERY_HIGH);
            double result = delivery.deliveryCoast();
            // small: 100, расстояние: 200 -> 300 * 1.6 = 480
            assertEquals(480.0, result, 0.001);
        }

        @Test
        @DisplayName("NORMAL коэффициент = 1.0")
        void testNormalCoefficient() throws FragileDistanceExceededException {
            products.add(new Product(1000, "book", false, "small"));
            Delivery delivery = new Delivery(40, products, Workload.NORMAL);
            double result = delivery.deliveryCoast();
            // small: 100, расстояние: 300 -> 400 * 1.0 = 400
            assertEquals(400.0, result, 0.001);
        }
    }

    // ТЕСТЫ МИНИМАЛЬНОЙ СТОИМОСТИ

    @Nested
    @DisplayName("Тесты минимальной стоимости (400 руб)")
    @Tag("edge")
    class MinimumCostTests {

        @Test
        @DisplayName("Стоимость ровно 400 руб -> доставка возможна")
        void testExactly400() throws FragileDistanceExceededException {
            products.add(new Product(1000, "book", false, "large"));
            Delivery delivery = new Delivery(20, products, Workload.NORMAL);
            double result = delivery.deliveryCoast();
            // large: 200, расстояние: 200 -> 400 * 1.0 = 400
            assertEquals(400.0, result, 0.001);
        }

        @Test
        @DisplayName("Стоимость чуть меньше 400 -> возвращаем 0")
        void testJustBelow400() throws FragileDistanceExceededException {
            products.add(new Product(1000, "book", false, "small"));
            Delivery delivery = new Delivery(20, products, Workload.NORMAL);
            double result = delivery.deliveryCoast();
            // small: 100, расстояние: 200 -> 300 (<400) -> 0
            assertEquals(0.0, result, 0.001);
        }

        @Test
        @DisplayName("Стоимость намного ниже 400 с учетом коэффициента")
        void testMuchBelow400() throws FragileDistanceExceededException {
            products.add(new Product(1000, "book", false, "small"));
            Delivery delivery = new Delivery(1, products, Workload.VERY_HIGH);
            double result = delivery.deliveryCoast();
            // small: 100, расстояние: 50 -> 150 * 1.6 = 240 (<400) -> 0
            assertEquals(0.0, result, 0.001);
        }

    }

    // ПАРАМЕТРИЗОВАННЫЕ ТЕСТЫ

    @Nested
    @DisplayName("Параметризованные тесты")
    @Tag("basic")
    class ParameterizedTests {

        static Stream<Arguments> deliveryScenarios() {
            return Stream.of(
                    // distance, fragile, dimensions, workload, expected
                    Arguments.of(1, false, "small", Workload.NORMAL, 0.0),      // <400
                    Arguments.of(1, false, "large", Workload.NORMAL, 0.0),      // 250 <400
                    Arguments.of(5, false, "large", Workload.NORMAL, 0.0),      // 310 <400
                    Arguments.of(20, false, "large", Workload.NORMAL, 400.0),   // ровно 400
                    Arguments.of(20, false, "large", Workload.VERY_HIGH, 640.0), // с коэффициентом
                    Arguments.of(15, true, "small", Workload.NORMAL, 600.0),    // хрупкий
                    Arguments.of(25, true, "large", Workload.HIGH, 980.0)     // large + хрупкий
            );
        }

        @ParameterizedTest
        @MethodSource("deliveryScenarios")
        @DisplayName("Комбинированные сценарии")
        void testCombinedScenarios(double distance, boolean fragile, String dimensions, Workload workload, double expected) {
            products.add(new Product(1000, "product", fragile, dimensions));
            Delivery delivery = new Delivery(distance, products, workload);
            double result = delivery.deliveryCoast();
            assertEquals(expected, result, 0.001);
        }
    }

    // ТЕСТ МЕТОДА PRODUCTS_COAST

    @Nested
    @DisplayName("Тесты расчета суммы товаров")
    @Tag("basic")
    class ProductsTotalTests {

        @Test
        @DisplayName("Сумма нескольких товаров")
        void testSumOfProducts() {
            products.add(new Product(250, "lamp", true, "small"));
            products.add(new Product(10000, "desk", false, "large"));
            products.add(new Product(25000, "phone", true, "small"));
            Delivery delivery = new Delivery(20, products, Workload.NORMAL);

            double total = delivery.calculateProductsTotal();
            // 25000 + 10000 + 250 = 35250
            assertEquals(35250.0, total, 0.001);
        }

        @Test
        @DisplayName("Пустой список товаров")
        void testEmptyProducts() {
            Delivery delivery = new Delivery(20, products, Workload.NORMAL);
            double total = delivery.calculateProductsTotal();
            assertEquals(0.0, total, 0.001);
        }

        @Test
        @DisplayName("Один товар")
        void testSingleProduct() {
            products.add(new Product(1000, "book", false, "small"));
            Delivery delivery = new Delivery(20, products, Workload.NORMAL);
            double total = delivery.calculateProductsTotal();
            assertEquals(1000.0, total, 0.001);
        }
    }
}