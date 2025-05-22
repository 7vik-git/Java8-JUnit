package com.gevernova.onlinceordering;
import java.util.function.Predicate;

public class OrderService {
    private final Predicate<Order> validPayment = order -> order.getPaymentMethod() != null && !order.getPaymentMethod().trim().isEmpty();
    private final Predicate<Order> validAddress = order -> order.getAddress() != null && order.getAddress().length() >= 10;

    public void validateOrder(Order order) throws InvalidPaymentException, InvalidAddressException {
        if (!validPayment.test(order)) throw new InvalidPaymentException("Invalid payment method");
        if (!validAddress.test(order)) throw new InvalidAddressException("Invalid address");
    }
}

