package com.toystore.ui;

import com.toystore.data.ToyInventory;
import com.toystore.model.ShoppingCart;
import com.toystore.model.Toy;
import javax.swing.*;
import java.awt.*;

import java.util.List;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
/**
 * Panel for displaying products in a grid layout
 */
public class ProductDisplayPanel extends JPanel {
    private ToyInventory inventory;
    private ShoppingCart cart;
    private ToyStoreMainWindow mainWindow;
    private JPanel productGridPanel;
    private JComboBox<String> categoryFilter;
    private JTextField searchField;
    private JLabel statusLabel;
        
    public ProductDisplayPanel(ToyInventory inventory, ShoppingCart cart, ToyStoreMainWindow mainWindow) {
        this.inventory = inventory;
        this.cart = cart;
        this.mainWindow = mainWindow;
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        refreshDisplay();
    }
    
    private void initializeComponents() {
        productGridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        productGridPanel.setBackground(Color.WHITE);
        
        categoryFilter = new JComboBox<>();
        categoryFilter.addItem("All Categories");
        for (String category : inventory.getCategories()) {
            categoryFilter.addItem(category);
        }
        
        searchField = new JTextField(20);
        searchField.setToolTipText("Search for toys...");
        
        statusLabel = new JLabel("Showing all products");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusLabel.setForeground(Color.GRAY);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Create filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(new Color(255, 228, 196, 200)); // Translucent bisque
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        filterPanel.add(new JLabel("Category:"));
        filterPanel.add(categoryFilter);
        filterPanel.add(Box.createHorizontalStrut(20));
        filterPanel.add(new JLabel("Search:"));
        filterPanel.add(searchField);
        
        add(filterPanel, BorderLayout.NORTH);
        
        // Create scrollable product grid
        JScrollPane scrollPane = new JScrollPane(productGridPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Increase scroll speed
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setBlockIncrement(64);
        
        add(scrollPane, BorderLayout.CENTER);
        
        // Add status label
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(new Color(240, 248, 255));
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        categoryFilter.addActionListener(e -> filterProducts());
        searchField.addActionListener(e -> filterProducts());
    }
    
    private void filterProducts() {
        refreshDisplay();
    }
    
    public void refreshDisplay() {
        productGridPanel.removeAll();
        
        List<Toy> toysToShow;
        String selectedCategory = (String) categoryFilter.getSelectedItem();
        String searchTerm = searchField.getText().trim();
        
        if (!searchTerm.isEmpty()) {
            toysToShow = inventory.searchToys(searchTerm);
            statusLabel.setText("Search results for: \"" + searchTerm + "\"");
        } else if (!"All Categories".equals(selectedCategory)) {
            toysToShow = inventory.getToysByCategory(selectedCategory);
            statusLabel.setText("Category: " + selectedCategory);
        } else {
            toysToShow = inventory.getAllToys();
            statusLabel.setText("Showing all products");
        }
        
        for (Toy toy : toysToShow) {
            JPanel productCard = createProductCard(toy);
            productGridPanel.add(productCard);
        }
        
        // Add empty panels to fill grid if needed
        int remainingSlots = 3 - (toysToShow.size() % 3);
        if (remainingSlots < 3) {
            for (int i = 0; i < remainingSlots; i++) {
                productGridPanel.add(new JPanel());
            }
        }
        
        productGridPanel.revalidate();
        productGridPanel.repaint();
    }
    
    private JPanel createProductCard(Toy toy) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 140, 0, 180), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(new Color(255, 248, 240)); // Light orange-white
        card.setPreferredSize(new Dimension(300, 400));
        
        // Product image
        JLabel imageLabel = createImageLabel(toy);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        imageLabel.setPreferredSize(new Dimension(280, 150));
        
        // Product info panel
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);
        
        // Name and price
        JLabel nameLabel = new JLabel("<html><div style='text-align: center;'>" + 
            toy.getName() + "</div></html>");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel priceLabel = new JLabel(String.format("%.2f", toy.getPrice()) + " DZD");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        priceLabel.setForeground(new Color(0, 128, 0)); // Green
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Description
        JLabel descLabel = new JLabel("<html><div style='text-align: center; width: 250px;'>" + 
            toy.getDescription() + "</div></html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        descLabel.setForeground(Color.GRAY);
        
        // Age range and stock
        JLabel ageLabel = new JLabel("Age: " + toy.getAgeRange());
        ageLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        ageLabel.setForeground(Color.BLUE);
        
        JLabel stockLabel = new JLabel("Stock: " + toy.getStockQuantity());
        stockLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        stockLabel.setForeground(toy.getStockQuantity() > 0 ? Color.BLACK : Color.RED);
        
        JPanel detailsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.add(ageLabel);
        detailsPanel.add(new JLabel(" | "));
        detailsPanel.add(stockLabel);
        
        // Add to cart button
        JButton addToCartBtn = new JButton("Add to Cart");
        addToCartBtn.setBackground(new Color(204, 85, 0)); // Darker orange for better contrast
        addToCartBtn.setForeground(Color.WHITE); // White text
        addToCartBtn.setFont(new Font("Arial", Font.BOLD, 12));
        addToCartBtn.setEnabled(toy.getStockQuantity() > 0);
        
        addToCartBtn.addActionListener(e -> addToCart(toy));
        
        // Layout components
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(nameLabel, BorderLayout.NORTH);
        topPanel.add(priceLabel, BorderLayout.CENTER);
        
        infoPanel.add(topPanel, BorderLayout.NORTH);
        infoPanel.add(descLabel, BorderLayout.CENTER);
        infoPanel.add(detailsPanel, BorderLayout.SOUTH);
        
        card.add(imageLabel, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(addToCartBtn, BorderLayout.SOUTH);
        
        return card;
    }
    
    private JLabel createImageLabel(Toy toy) {
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        
        try {
            // Try to load the image from the specified path
            String imagePath = toy.getImagePath();
            if (imagePath != null && !imagePath.isEmpty()) {
                File imageFile = new File(imagePath);
                Image originalImage = null;
                if (imageFile.exists()) {
                    originalImage = ImageIO.read(imageFile);
                } else {
                    // Try a base64 placeholder next to the expected image (image.jpg.b64)
                    File b64File = new File(imagePath + ".b64");
                    if (b64File.exists()) {
                        try {
                            System.out.println("INFO: Decoding base64 image for " + imagePath);
                            byte[] b64bytes = java.nio.file.Files.readAllBytes(b64File.toPath());
                            String b64 = new String(b64bytes).trim();
                            byte[] imgBytes = java.util.Base64.getDecoder().decode(b64);
                            originalImage = ImageIO.read(new java.io.ByteArrayInputStream(imgBytes));
                        } catch (Exception ex) {
                            System.out.println("WARN: Failed to decode base64 image for " + imagePath + " -> " + ex.getMessage());
                            originalImage = null;
                        }
                    }
                }

                if (originalImage != null) {
                    // Scale the image to fit the label size while maintaining aspect ratio
                    Image scaledImage = originalImage.getScaledInstance(280, 150, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaledImage));
                } else {
                    // Image file doesn't exist or could not be read, show placeholder
                    imageLabel.setText("🪀");
                    imageLabel.setFont(new Font("Arial", Font.PLAIN, 48));
                }
            } else {
                // No image path specified, show placeholder
                imageLabel.setText("🪀");
                imageLabel.setFont(new Font("Arial", Font.PLAIN, 48));
            }
        } catch (IOException e) {
            // Error loading image, show placeholder
            imageLabel.setText("🪀");
            imageLabel.setFont(new Font("Arial", Font.PLAIN, 48));
        }
        
        return imageLabel;
    }
    
    private void addToCart(Toy toy) {
        System.out.println("ACTION: Add to Cart clicked for toy id=" + toy.getId() + " name=\"" + toy.getName() + "\"");
        if (toy.getStockQuantity() > 0) {
            cart.addItem(toy, 1);
            mainWindow.updateCartDisplay();
            System.out.println("STATE: Item added to cart. Cart total items=" + cart.getTotalItems() + " totalPrice=" + cart.getTotalPrice());
            JOptionPane.showMessageDialog(this, 
                toy.getName() + " added to cart!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("WARN: Attempted to add out-of-stock item id=" + toy.getId());
            JOptionPane.showMessageDialog(this, 
                "Sorry, this item is out of stock!", 
                "Out of Stock", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
}
