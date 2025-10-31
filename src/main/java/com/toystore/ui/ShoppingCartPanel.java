package com.toystore.ui;

import com.toystore.model.CartItem;
import com.toystore.model.ShoppingCart;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel for displaying and managing the shopping cart
 */
public class ShoppingCartPanel extends JPanel {
    private ShoppingCart cart;
    private ToyStoreMainWindow mainWindow;
    private JPanel cartItemsPanel;
    private JLabel totalLabel;
    private JButton checkoutBtn;
    private JButton clearCartBtn;
    
    public ShoppingCartPanel(ShoppingCart cart, ToyStoreMainWindow mainWindow) {
        this.cart = cart;
        this.mainWindow = mainWindow;
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        cartItemsPanel = new JPanel();
        cartItemsPanel.setLayout(new BoxLayout(cartItemsPanel, BoxLayout.Y_AXIS));
        cartItemsPanel.setBackground(Color.WHITE);
        
        totalLabel = new JLabel("Total: 0.00 DZD");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setForeground(new Color(0, 128, 0)); // Green
        
        checkoutBtn = new JButton("Proceed to Checkout");
        checkoutBtn.setBackground(new Color(0, 128, 0)); // Green
        checkoutBtn.setForeground(Color.WHITE);
        checkoutBtn.setFont(new Font("Arial", Font.BOLD, 14));
        checkoutBtn.setPreferredSize(new Dimension(200, 40));
        
        clearCartBtn = new JButton("Clear Cart");
        clearCartBtn.setBackground(new Color(204, 85, 0)); // Darker orange for better contrast
        clearCartBtn.setForeground(Color.WHITE); // White text
        clearCartBtn.setFont(new Font("Arial", Font.BOLD, 12));
        clearCartBtn.setPreferredSize(new Dimension(120, 35));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(255, 228, 196, 200)); // Translucent bisque
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel headerLabel = new JLabel("🛒 Shopping Cart");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(new Color(255, 140, 0)); // Dark orange
        
        headerPanel.add(headerLabel);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Cart items scrollable area
        JScrollPane scrollPane = new JScrollPane(cartItemsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Increase scroll speed
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setBlockIncrement(64);
        
        add(scrollPane, BorderLayout.CENTER);
        
        // Footer with total and buttons
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(240, 248, 255));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Total display
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalPanel.setBackground(new Color(240, 248, 255));
        totalPanel.add(totalLabel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.add(clearCartBtn);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(checkoutBtn);
        
        footerPanel.add(totalPanel, BorderLayout.WEST);
        footerPanel.add(buttonPanel, BorderLayout.EAST);
        
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        checkoutBtn.addActionListener(e -> {
            if (!cart.isEmpty()) {
                System.out.println("ACTION: Proceed to Checkout clicked");
                mainWindow.showCheckoutPanel();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Your cart is empty!", 
                    "Empty Cart", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        clearCartBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to clear your cart?", 
                "Clear Cart", 
                JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.YES_OPTION) {
                cart.clear();
                refreshDisplay();
                mainWindow.updateCartDisplay();
            }
        });
    }
    
    public void refreshDisplay() {
        cartItemsPanel.removeAll();
        
        if (cart.isEmpty()) {
            JLabel emptyLabel = new JLabel("Your cart is empty");
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            emptyLabel.setForeground(Color.GRAY);
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
            
            cartItemsPanel.add(emptyLabel);
            checkoutBtn.setEnabled(false);
        } else {
            checkoutBtn.setEnabled(true);
            
            for (CartItem item : cart.getItems()) {
                JPanel itemPanel = createCartItemPanel(item);
                cartItemsPanel.add(itemPanel);
                cartItemsPanel.add(Box.createVerticalStrut(10));
            }
        }
        
        // Update total
        totalLabel.setText("Total: " + String.format("%.2f", cart.getTotalPrice()) + " DZD");
        
        cartItemsPanel.revalidate();
        cartItemsPanel.repaint();
    }
    
    private JPanel createCartItemPanel(CartItem item) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 140, 0), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panel.setBackground(new Color(255, 245, 238)); // Seashell white
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        // Product info
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);
        
        JLabel nameLabel = new JLabel(item.getToy().getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel priceLabel = new JLabel(String.format("%.2f", item.getToy().getPrice()) + " DZD");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        priceLabel.setForeground(Color.GRAY);
        
        JLabel subtotalLabel = new JLabel("Subtotal: " + String.format("%.2f", item.getSubtotal()) + " DZD");
        subtotalLabel.setFont(new Font("Arial", Font.BOLD, 12));
        subtotalLabel.setForeground(new Color(0, 128, 0)); // Green
        
        infoPanel.add(nameLabel, BorderLayout.NORTH);
        infoPanel.add(priceLabel, BorderLayout.CENTER);
        infoPanel.add(subtotalLabel, BorderLayout.SOUTH);
        
        // Quantity controls
        JPanel quantityPanel = new JPanel(new FlowLayout());
        quantityPanel.setBackground(Color.WHITE);
        
        JButton minusBtn = new JButton("-");
        minusBtn.setPreferredSize(new Dimension(30, 30));
        minusBtn.setFont(new Font("Arial", Font.BOLD, 12));
        
        JLabel quantityLabel = new JLabel(String.valueOf(item.getQuantity()));
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        quantityLabel.setPreferredSize(new Dimension(40, 30));
        quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        quantityLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        JButton plusBtn = new JButton("+");
        plusBtn.setPreferredSize(new Dimension(30, 30));
        plusBtn.setFont(new Font("Arial", Font.BOLD, 12));
        
        JButton removeBtn = new JButton("Remove");
        removeBtn.setBackground(new Color(220, 20, 60)); // Crimson
        removeBtn.setForeground(Color.WHITE);
        removeBtn.setFont(new Font("Arial", Font.BOLD, 10));
        removeBtn.setPreferredSize(new Dimension(80, 30));
        
        quantityPanel.add(minusBtn);
        quantityPanel.add(quantityLabel);
        quantityPanel.add(plusBtn);
        quantityPanel.add(Box.createHorizontalStrut(10));
        quantityPanel.add(removeBtn);
        
        // Event handlers for quantity controls
        minusBtn.addActionListener(e -> {
            if (item.getQuantity() > 1) {
                cart.updateQuantity(item.getToy().getId(), item.getQuantity() - 1);
                refreshDisplay();
                mainWindow.updateCartDisplay();
                System.out.println("ACTION: Decrement quantity for toyId=" + item.getToy().getId() + " newQty=" + (item.getQuantity()-1));
            }
        });
        
        plusBtn.addActionListener(e -> {
            if (item.getQuantity() < item.getToy().getStockQuantity()) {
                cart.updateQuantity(item.getToy().getId(), item.getQuantity() + 1);
                refreshDisplay();
                mainWindow.updateCartDisplay();
                System.out.println("ACTION: Increment quantity for toyId=" + item.getToy().getId() + " newQty=" + (item.getQuantity()+1));
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Cannot add more items. Stock limit reached!", 
                    "Stock Limit", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        removeBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, 
                "Remove " + item.getToy().getName() + " from cart?", 
                "Remove Item", 
                JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.YES_OPTION) {
                cart.removeItem(item.getToy().getId());
                refreshDisplay();
                mainWindow.updateCartDisplay();
                System.out.println("ACTION: Removed item toyId=" + item.getToy().getId());
            }
        });
        
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(quantityPanel, BorderLayout.EAST);
        
        return panel;
    }
}
