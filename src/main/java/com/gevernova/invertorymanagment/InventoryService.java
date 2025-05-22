package com.gevernova.invertorymanagment;

import java.util.*;
import java.util.stream.*;

public class InventoryService {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product.getPrice() < 0 || product.getQuantity() < 0)
            throw new InvalidProductException("Invalid product");
        products.add(product);
    }

    public void removeProduct(String name) {
        Product product = products.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        products.remove(product);
    }

    public List<Product> searchByName(String name) {
        List<Product> result = products.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
        if (result.isEmpty()) throw new ProductNotFoundException("No product found");
        return result;
    }

    public List<Product> searchByCategory(String category) {
        List<Product> result = products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
        if (result.isEmpty()) throw new ProductNotFoundException("No product found");
        return result;
    }

    public List<Product> getLowStockItems() {
        return products.stream()
                .filter(p -> p.getQuantity() < 5)
                .collect(Collectors.toList());
    }

    public List<Product> getSortedByCategoryAndPrice() {
        return products.stream()
                .sorted(Comparator.comparing(Product::getCategory)
                        .thenComparing(Product::getPrice))
                .collect(Collectors.toList());
    }
}

