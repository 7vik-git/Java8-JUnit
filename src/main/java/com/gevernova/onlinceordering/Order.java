package com.gevernova.onlinceordering;
import java.util.List;
import java.util.Optional;

public class Order {
    private User user;
    private List<Item> items;
    private String paymentMethod;
    private String address;
    private Optional<String> promoCode;

    public Order(User user, List<Item> items, String paymentMethod, String address, String promoCode) {
        this.user = user;
        this.items = items;
        this.paymentMethod = paymentMethod;
        this.address = address;
        this.promoCode = Optional.ofNullable(promoCode);
    }

    public User getUser() { return user; }
    public List<Item> getItems() { return items; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getAddress() { return address; }
    public Optional<String> getPromoCode() { return promoCode; }
}
