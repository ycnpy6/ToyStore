package com.toystore.ui;

import com.toystore.model.CartItem;
import com.toystore.model.Customer;
import com.toystore.model.ShoppingCart;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

/**
 * Panel for checkout process with customer information form
 */
public class CheckoutPanel extends JPanel {
    private ShoppingCart cart;
    private ToyStoreMainWindow mainWindow;
    private Customer customer;
    
    // Form fields
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField cityField;
    private JTextField stateField;
    private JTextField zipCodeField;
    
    // Order summary
    private JPanel orderSummaryPanel;
    private JLabel totalLabel;
    private JLabel taxLabel;
    private JLabel finalTotalLabel;
    
    // Buttons
    private JButton placeOrderBtn;
    private JButton backToCartBtn;
    
    public CheckoutPanel(ShoppingCart cart, ToyStoreMainWindow mainWindow) {
        this.cart = cart;
        this.mainWindow = mainWindow;
        this.customer = new Customer();
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        // Initialize form fields
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        addressField = new JTextField(20);
        cityField = new JTextField(20);
        stateField = new JTextField(20);
        zipCodeField = new JTextField(20);
        
        // Initialize order summary
        orderSummaryPanel = new JPanel();
        totalLabel = new JLabel();
        taxLabel = new JLabel();
        finalTotalLabel = new JLabel();
        
        // Initialize buttons
        placeOrderBtn = new JButton("Place Order");
        placeOrderBtn.setBackground(new Color(204, 85, 0)); // Darker orange for better contrast
        placeOrderBtn.setForeground(Color.WHITE); // White text
        placeOrderBtn.setFont(new Font("Arial", Font.BOLD, 14));
        placeOrderBtn.setPreferredSize(new Dimension(150, 40));
        
        backToCartBtn = new JButton("Back to Cart");
        backToCartBtn.setBackground(new Color(70, 130, 180)); // Steel blue
        backToCartBtn.setForeground(Color.WHITE);
        backToCartBtn.setFont(new Font("Arial", Font.BOLD, 12));
        backToCartBtn.setPreferredSize(new Dimension(120, 35));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(255, 228, 196, 200)); // Translucent bisque
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel headerLabel = new JLabel("💳 Checkout");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(new Color(255, 140, 0)); // Dark orange
        
        headerPanel.add(headerLabel);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(Color.WHITE);
        
        // Customer information form
        JPanel formPanel = createCustomerFormPanel();
        mainContentPanel.add(formPanel, BorderLayout.WEST);
        
        // Order summary
        JPanel summaryPanel = createOrderSummaryPanel();
        mainContentPanel.add(summaryPanel, BorderLayout.EAST);
        
        add(mainContentPanel, BorderLayout.CENTER);
        
        // Footer with buttons
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(new Color(240, 248, 255));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        footerPanel.add(backToCartBtn);
        footerPanel.add(Box.createHorizontalStrut(10));
        footerPanel.add(placeOrderBtn);
        
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createCustomerFormPanel() {
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setPreferredSize(new Dimension(400, 0));
        
        JLabel formTitleLabel = new JLabel("Customer Information");
        formTitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        formTitleLabel.setForeground(new Color(139, 69, 19)); // Brown
        
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Personal Information
        addFormField(fieldsPanel, gbc, "First Name *:", firstNameField, 0);
        addFormField(fieldsPanel, gbc, "Last Name *:", lastNameField, 1);
        addFormField(fieldsPanel, gbc, "Email *:", emailField, 2);
        addFormField(fieldsPanel, gbc, "Phone *:", phoneField, 3);
        
        // Address Information
        addFormField(fieldsPanel, gbc, "Address *:", addressField, 4);
        addFormField(fieldsPanel, gbc, "City *:", cityField, 5);
        addFormField(fieldsPanel, gbc, "State *:", stateField, 6);
        addFormField(fieldsPanel, gbc, "ZIP Code *:", zipCodeField, 7);
        
        formPanel.add(formTitleLabel, BorderLayout.NORTH);
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        
        return formPanel;
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField field, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        panel.add(field, gbc);
    }
    
    private JPanel createOrderSummaryPanel() {
        JPanel summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setBackground(new Color(255, 248, 240)); // Light orange-white
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        summaryPanel.setPreferredSize(new Dimension(300, 0));
        
        JLabel summaryTitleLabel = new JLabel("Order Summary");
        summaryTitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        summaryTitleLabel.setForeground(new Color(139, 69, 19)); // Brown
        summaryTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        orderSummaryPanel = new JPanel();
        orderSummaryPanel.setLayout(new BoxLayout(orderSummaryPanel, BoxLayout.Y_AXIS));
        orderSummaryPanel.setBackground(new Color(248, 248, 255));
        
        summaryPanel.add(summaryTitleLabel, BorderLayout.NORTH);
        summaryPanel.add(orderSummaryPanel, BorderLayout.CENTER);
        
        return summaryPanel;
    }
    
    private void setupEventHandlers() {
        placeOrderBtn.addActionListener(e -> {
            System.out.println("ACTION: Place Order clicked");
            placeOrder();
        });
        backToCartBtn.addActionListener(e -> {
            System.out.println("ACTION: Back to Cart clicked");
            mainWindow.showCartPanel();
        });
    }
    
    public void refreshDisplay() {
        updateOrderSummary();
    }
    
    private void updateOrderSummary() {
        orderSummaryPanel.removeAll();
        
        double subtotal = cart.getTotalPrice();
        double tax = subtotal * 0.08; // 8% tax
        double finalTotal = subtotal + tax;
        
        // Add cart items
        for (CartItem item : cart.getItems()) {
            JLabel itemLabel = new JLabel(item.getToy().getName() + " x" + item.getQuantity());
            itemLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            orderSummaryPanel.add(itemLabel);
            
            JLabel priceLabel = new JLabel(String.format("%.2f", item.getSubtotal()) + " DZD");
            priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            orderSummaryPanel.add(priceLabel);
            
            orderSummaryPanel.add(Box.createVerticalStrut(5));
        }
        
        orderSummaryPanel.add(Box.createVerticalStrut(10));
        
        // Add totals
        JLabel subtotalLabel = new JLabel("Subtotal:");
        subtotalLabel.setFont(new Font("Arial", Font.BOLD, 12));
        orderSummaryPanel.add(subtotalLabel);
        
        totalLabel.setText(String.format("%.2f", subtotal) + " DZD");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 12));
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        orderSummaryPanel.add(totalLabel);
        
        JLabel taxLabelText = new JLabel("Tax (8%):");
        taxLabelText.setFont(new Font("Arial", Font.BOLD, 12));
        orderSummaryPanel.add(taxLabelText);
        
        taxLabel.setText(String.format("%.2f", tax) + " DZD");
        taxLabel.setFont(new Font("Arial", Font.BOLD, 12));
        taxLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        orderSummaryPanel.add(taxLabel);
        
        orderSummaryPanel.add(Box.createVerticalStrut(5));
        
        JLabel finalTotalTextLabel = new JLabel("Total:");
        finalTotalTextLabel.setFont(new Font("Arial", Font.BOLD, 14));
        orderSummaryPanel.add(finalTotalTextLabel);
        
        finalTotalLabel.setText(String.format("%.2f", finalTotal) + " DZD");
        finalTotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        finalTotalLabel.setForeground(new Color(0, 128, 0)); // Green
        finalTotalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        orderSummaryPanel.add(finalTotalLabel);
        
        orderSummaryPanel.revalidate();
        orderSummaryPanel.repaint();
    }
    
    private void placeOrder() {
        if (validateForm()) {
            // Update customer object
            customer.setFirstName(firstNameField.getText().trim());
            customer.setLastName(lastNameField.getText().trim());
            customer.setEmail(emailField.getText().trim());
            customer.setPhone(phoneField.getText().trim());
            customer.setAddress(addressField.getText().trim());
            customer.setCity(cityField.getText().trim());
            customer.setState(stateField.getText().trim());
            customer.setZipCode(zipCodeField.getText().trim());
            
            // Show confirmation dialog
            String message = "Order placed successfully!\n\n" +
                           "Customer: " + customer.getFullName() + "\n" +
                           "Email: " + customer.getEmail() + "\n" +
                           "Total: " + String.format("%.2f", cart.getTotalPrice() * 1.08) + " DZD\n\n" +
                           "Thank you for your purchase!";
            
            JOptionPane.showMessageDialog(this, message, "Order Confirmation", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear cart and return to products
            cart.clear();
            mainWindow.updateCartDisplay();
            mainWindow.showProductsPanel();
            clearForm();
        }
    }
    
    private boolean validateForm() {
        StringBuilder errors = new StringBuilder();
        
        if (firstNameField.getText().trim().isEmpty()) {
            errors.append("• First name is required\n");
        }
        
        if (lastNameField.getText().trim().isEmpty()) {
            errors.append("• Last name is required\n");
        }
        
        if (emailField.getText().trim().isEmpty()) {
            errors.append("• Email is required\n");
        } else if (!isValidEmail(emailField.getText().trim())) {
            errors.append("• Invalid email format\n");
        }
        
        if (phoneField.getText().trim().isEmpty()) {
            errors.append("• Phone number is required\n");
        } else if (!isValidPhone(phoneField.getText().trim())) {
            errors.append("• Invalid phone number format\n");
        }
        
        if (addressField.getText().trim().isEmpty()) {
            errors.append("• Address is required\n");
        }
        
        if (cityField.getText().trim().isEmpty()) {
            errors.append("• City is required\n");
        }
        
        if (stateField.getText().trim().isEmpty()) {
            errors.append("• State is required\n");
        }
        
        if (zipCodeField.getText().trim().isEmpty()) {
            errors.append("• ZIP code is required\n");
        } else if (!isValidZipCode(zipCodeField.getText().trim())) {
            errors.append("• Invalid ZIP code format\n");
        }
        
        if (errors.length() > 0) {
            JOptionPane.showMessageDialog(this, 
                "Please fix the following errors:\n\n" + errors.toString(), 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    
    private boolean isValidPhone(String phone) {
        String phoneRegex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
        Pattern pattern = Pattern.compile(phoneRegex);
        return pattern.matcher(phone).matches();
    }
    
    private boolean isValidZipCode(String zipCode) {
        String zipRegex = "^[0-9]{5}(-[0-9]{4})?$";
        Pattern pattern = Pattern.compile(zipRegex);
        return pattern.matcher(zipCode).matches();
    }
    
    private void clearForm() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        cityField.setText("");
        stateField.setText("");
        zipCodeField.setText("");
    }
}
