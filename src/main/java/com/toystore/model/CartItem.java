package com.toystore.model;

/**
 * Represents an item in the shopping cart with quantity
 */
public class CartItem {
    private Toy toy;
    private int quantity;
    
    public CartItem(Toy toy, int quantity) {
        this.toy = toy;
        this.quantity = quantity;
    }
    
    public Toy getToy() {
        return toy;
    }
    
    public void setToy(Toy toy) {
        this.toy = toy;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getSubtotal() {
        return toy.getPrice() * quantity;
    }
    
    @Override
    public String toString() {
        return toy.getName() + " x" + quantity + " - $" + String.format("%.2f", getSubtotal());
    }
}
