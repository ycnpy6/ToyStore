package com.toystore.model;

/**
 * Represents a toy product in the store
 */
public class Toy {
    private String id;
    private String name;
    private String description;
    private double price;
    private String category;
    private int stockQuantity;
    private String imagePath;
    private int ageRangeMin;
    private int ageRangeMax;
    
    public Toy(String id, String name, String description, double price, 
               String category, int stockQuantity, String imagePath, 
               int ageRangeMin, int ageRangeMax) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.imagePath = imagePath;
        this.ageRangeMin = ageRangeMin;
        this.ageRangeMax = ageRangeMax;
    }
    
    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    
    public int getAgeRangeMin() { return ageRangeMin; }
    public void setAgeRangeMin(int ageRangeMin) { this.ageRangeMin = ageRangeMin; }
    
    public int getAgeRangeMax() { return ageRangeMax; }
    public void setAgeRangeMax(int ageRangeMax) { this.ageRangeMax = ageRangeMax; }
    
    public String getAgeRange() {
        return ageRangeMin + "-" + ageRangeMax + " years";
    }
    
    @Override
    public String toString() {
        return name + " - " + String.format("%.2f", price) + " DZD";
    }
}
