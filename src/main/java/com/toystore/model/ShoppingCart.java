package com.toystore.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a shopping cart containing selected toys
 */
public class ShoppingCart {
    private List<CartItem> items;
    
    public ShoppingCart() {
        this.items = new ArrayList<>();
    }
    
    public void addItem(Toy toy, int quantity) {
        // Check if item already exists in cart
        for (CartItem item : items) {
            if (item.getToy().getId().equals(toy.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        // Add new item to cart
        items.add(new CartItem(toy, quantity));
    }
    
    public void removeItem(String toyId) {
        items.removeIf(item -> item.getToy().getId().equals(toyId));
    }
    
    public void updateQuantity(String toyId, int quantity) {
        for (CartItem item : items) {
            if (item.getToy().getId().equals(toyId)) {
                if (quantity <= 0) {
                    removeItem(toyId);
                } else {
                    item.setQuantity(quantity);
                }
                return;
            }
        }
    }
    
    public void clear() {
        items.clear();
    }
    
    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }
    
    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(item -> item.getToy().getPrice() * item.getQuantity())
                .sum();
    }
    
    public int getTotalItems() {
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
    
    public boolean isEmpty() {
        return items.isEmpty();
    }
}
