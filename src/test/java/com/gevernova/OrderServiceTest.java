package com.gevernova;
import static org.junit.jupiter.api.Assertions.*;

import com.gevernova.onlinceordering.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class OrderServiceTest {
    OrderService service = new OrderService();
    @Test
    void testValidOrderWithPromoCode() throws Exception {
        User user = new User("Alice", "alice@example.com");
        Order order = new Order(user, Arrays.asList(new Item("Book", 20)), "Credit Card", "123 Street, City", "SAVE10");
        assertDoesNotThrow(() -> service.validateOrder(order));
        assertTrue(order.getPromoCode().isPresent());
    }

    @Test
    void testValidOrderWithoutPromoCode() throws Exception {
        User user = new User("Bob", "bob@example.com");
        Order order = new Order(user, Arrays.asList(new Item("Pen", 5)), "PayPal", "45 Baker Street", null);
        assertDoesNotThrow(() -> service.validateOrder(order));
        assertFalse(order.getPromoCode().isPresent());
    }
    @Test
    void testOrderWithNullPaymentThrows() {
        User user = new User("Charlie", "charlie@example.com");
        Order order = new Order(user, Arrays.asList(new Item("Notebook", 15)), null, "77 Elm Road", null);
        assertThrows(InvalidPaymentException.class, () -> service.validateOrder(order));
    }

    @Test
    void testOrderWithEmptyPaymentThrows() {
        User user = new User("David", "david@example.com");
        Order order = new Order(user, Arrays.asList(new Item("Charger", 25)), "  ", "77 Elm Road", null);
        assertThrows(InvalidPaymentException.class, () -> service.validateOrder(order));
    }

    @Test
    void testOrderWithInvalidShortAddressThrows() {
        User user = new User("Eva", "eva@example.com");
        Order order = new Order(user, Arrays.asList(new Item("Cable", 10)), "UPI", "Short", null);
        assertThrows(InvalidAddressException.class, () -> service.validateOrder(order));
    }

    @Test
    void testOrderWithNullAddressThrows() {
        User user = new User("Frank", "frank@example.com");
        Order order = new Order(user, Arrays.asList(new Item("Monitor", 200)), "Card", null, null);
        assertThrows(InvalidAddressException.class, () -> service.validateOrder(order));
    }
}
