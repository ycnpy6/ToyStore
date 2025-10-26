package com.toystore.data;

import com.toystore.model.Toy;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages toy inventory data
 */
public class ToyInventory {
    private List<Toy> toys;
    
    public ToyInventory() {
        this.toys = new ArrayList<>();
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        // Sample toys for demonstration
        toys.add(new Toy("T001", "LEGO Classic Creative Box", 
                "Build anything you can imagine with this classic LEGO set", 
                4049.65, "Building Blocks", 50, "images/lego_classic.jpg", 4, 99));
        
        toys.add(new Toy("T002", "Barbie Dreamhouse", 
                "Three-story dollhouse with elevator and pool", 
                26998.65, "Dolls", 25, "images/barbie_dreamhouse.jpg", 3, 12));
        
        toys.add(new Toy("T003", "Hot Wheels Track Set", 
                "High-speed racing track with loops and jumps", 
                6748.65, "Vehicles", 30, "images/hotwheels_track.jpg", 3, 8));
        
        toys.add(new Toy("T004", "Nerf Blaster", 
                "Safe foam dart blaster for active play", 
                3373.65, "Action Toys", 40, "images/nerf_blaster.jpg", 6, 12));
        
        toys.add(new Toy("T005", "Educational Puzzle Set", 
                "Wooden puzzles for learning shapes and colors", 
                2698.65, "Educational", 60, "images/wooden_puzzle.jpg", 2, 5));
        
        toys.add(new Toy("T006", "Remote Control Car", 
                "Fast RC car with LED lights and sound effects", 
                10798.65, "Vehicles", 20, "images/rc_car.jpg", 6, 14));
        
        toys.add(new Toy("T007", "Art Supplies Kit", 
                "Complete art set with crayons, markers, and paper", 
                2158.65, "Arts & Crafts", 100, "images/art_kit.jpg", 3, 10));
        
        toys.add(new Toy("T008", "Board Game Collection", 
                "Family-friendly board games for all ages", 
                4723.65, "Games", 35, "images/board_games.jpg", 5, 99));
    }
    
    public List<Toy> getAllToys() {
        return new ArrayList<>(toys);
    }
    
    public List<Toy> getToysByCategory(String category) {
        return toys.stream()
                .filter(toy -> toy.getCategory().equalsIgnoreCase(category))
                .toList();
    }
    
    public List<String> getCategories() {
        return toys.stream()
                .map(Toy::getCategory)
                .distinct()
                .sorted()
                .toList();
    }
    
    public Toy getToyById(String id) {
        return toys.stream()
                .filter(toy -> toy.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public List<Toy> searchToys(String searchTerm) {
        String lowerSearchTerm = searchTerm.toLowerCase();
        return toys.stream()
                .filter(toy -> toy.getName().toLowerCase().contains(lowerSearchTerm) ||
                             toy.getDescription().toLowerCase().contains(lowerSearchTerm))
                .toList();
    }
    
    public boolean updateStock(String toyId, int quantity) {
        Toy toy = getToyById(toyId);
        if (toy != null && toy.getStockQuantity() >= quantity) {
            toy.setStockQuantity(toy.getStockQuantity() - quantity);
            return true;
        }
        return false;
    }
}
