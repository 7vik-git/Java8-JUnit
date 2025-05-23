package com.gevernova;

import com.gevernova.invertorymanagment.Product;
import com.gevernova.invertorymanagment.InventoryService;
import com.gevernova.invertorymanagment.InvalidProductException;
import com.gevernova.invertorymanagment.ProductNotFoundException;


import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class InventoryServiceTest {
    private InventoryService service;
    private Product product;

    @BeforeEach
    void setUp() {
        service = new InventoryService();
    }


    @Test
    void testAddValidProduct() {
        Product p = new Product("Apple", "Fruits", 1.5, 10);
        service.addProduct(p);
        assertEquals(1, service.searchByName("Apple").size());
    }

    @Test
    void testAddInvalidProductNegativePrice() {
        assertThrows(InvalidProductException.class, () ->
                new Product("Orange", "Fruits", -2.0, 10));
    }

    @Test
    void testAddInvalidProductNegativeQuantity() {
        assertThrows(InvalidProductException.class, () ->
                new Product("Banana", "Fruits", 1.0, -5));
    }

    @Test
    void testRemoveExistingProduct() {
        Product p = new Product("Milk", "Dairy", 2.0, 3);
        service.addProduct(p);
        service.removeProduct("Milk");
        assertThrows(ProductNotFoundException.class, () -> service.searchByName("Milk"));
    }

    @Test
    void testRemoveNonExistentProduct() {
        assertThrows(ProductNotFoundException.class, () -> service.removeProduct("NonExistent"));
    }

    @Test
    void testSearchByNameSuccess() {
        Product p = new Product("Bread", "Bakery", 1.0, 6);
        service.addProduct(p);
        List<Product> result = service.searchByName("Bread");
        assertEquals("Bakery", result.get(0).getCategory());
    }

    @Test
    void testSearchByNameNotFound() {
        assertThrows(ProductNotFoundException.class, () -> service.searchByName("Unknown"));
    }

    @Test
    void testSearchByCategorySuccess() {
        service.addProduct(new Product("Pepsi", "Drinks", 1.0, 10));
        List<Product> result = service.searchByCategory("Drinks");
        assertFalse(result.isEmpty());
    }

    @Test
    void testSearchByCategoryNotFound() {
        assertThrows(ProductNotFoundException.class, () -> service.searchByCategory("Nonexistent"));
    }

    @Test
    void testLowStockItems() {
        service.addProduct(new Product("Butter", "Dairy", 3.0, 2));
        service.addProduct(new Product("Cheese", "Dairy", 4.0, 10));
        List<Product> lowStock = service.getLowStockItems();
        assertEquals(1, lowStock.size());
        assertEquals("Butter", lowStock.get(0).getName());
    }

    @Test
    void testLowStockItemsEmpty() {
        service.addProduct(new Product("Yogurt", "Dairy", 2.0, 10));
        List<Product> lowStock = service.getLowStockItems();
        assertTrue(lowStock.isEmpty());
    }

    @Test
    void testSortedByCategoryAndPrice() {
        service.addProduct(new Product("Item1", "B", 3.0, 10));
        service.addProduct(new Product("Item2", "A", 2.0, 10));
        service.addProduct(new Product("Item3", "A", 1.0, 10));
        List<Product> sorted = service.getSortedByCategoryAndPrice();
        assertEquals("Item3", sorted.get(0).getName());
        assertEquals("Item2", sorted.get(1).getName());
        assertEquals("Item1", sorted.get(2).getName());
    }
}
