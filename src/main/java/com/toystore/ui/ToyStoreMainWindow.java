package com.toystore.ui;

import com.toystore.data.ToyInventory;
import com.toystore.model.ShoppingCart;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main window for the Toy Store application
 */
public class ToyStoreMainWindow extends JFrame {
    private ToyInventory inventory;
    private ShoppingCart cart;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    // UI Components
    private ProductDisplayPanel productPanel;
    private ShoppingCartPanel cartPanel;
    private CheckoutPanel checkoutPanel;
    private JLabel cartItemCountLabel;
    private JLabel cartTotalLabel;
    // Navigation buttons (kept as fields so event wiring is reliable)
    private JButton productsBtn;
    private JButton cartBtn;
    private JButton checkoutBtn;
    
    public ToyStoreMainWindow() {
        this.inventory = new ToyInventory();
        this.cart = new ShoppingCart();
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        
        setTitle("Toy Store - Your Fun Shopping Destination");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(true);
    }
    
    private void initializeComponents() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Initialize panels
        productPanel = new ProductDisplayPanel(inventory, cart, this);
        cartPanel = new ShoppingCartPanel(cart, this);
        checkoutPanel = new CheckoutPanel(cart, this);
        
        // Add panels to card layout
        mainPanel.add(productPanel, "PRODUCTS");
        mainPanel.add(cartPanel, "CART");
        mainPanel.add(checkoutPanel, "CHECKOUT");
        
        // Initialize cart display labels
        cartItemCountLabel = new JLabel("0 items");
        cartTotalLabel = new JLabel("$0.00");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Add main content
        add(mainPanel, BorderLayout.CENTER);
        
        // Create footer panel
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 140, 0, 200)); // Translucent dark orange
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Title
        JLabel titleLabel = new JLabel("🎪 Toy Store 🎪");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(255, 245, 238)); // Seashell white
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Navigation buttons
        JPanel navPanel = new JPanel(new FlowLayout());
        navPanel.setOpaque(false);
        
    // initialize navigation buttons as fields so we can wire listeners directly
    productsBtn = new JButton("🛍️ Products");
    cartBtn = new JButton("🛒 Cart");
    checkoutBtn = new JButton("💳 Checkout");
        
        // Style navigation buttons
        styleNavigationButton(productsBtn);
        styleNavigationButton(cartBtn);
        styleNavigationButton(checkoutBtn);
        
    navPanel.add(productsBtn);
    navPanel.add(cartBtn);
    navPanel.add(checkoutBtn);
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(navPanel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(255, 140, 0, 200)); // Translucent dark orange
        footerPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        
        // Cart summary
        JPanel cartSummaryPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cartSummaryPanel.setOpaque(false);
        
        cartSummaryPanel.add(new JLabel("Cart: "));
        cartSummaryPanel.add(cartItemCountLabel);
        cartSummaryPanel.add(new JLabel(" | Total: "));
        cartSummaryPanel.add(cartTotalLabel);
        
        footerPanel.add(cartSummaryPanel, BorderLayout.EAST);
        
        return footerPanel;
    }
    
    private void styleNavigationButton(JButton button) {
        // Force basic UI to respect background/foreground
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        button.setBackground(new Color(204, 85, 0)); // Darker orange
        button.setForeground(Color.WHITE); // High contrast text
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(153, 64, 0), 1), // Darker border
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        button.setPreferredSize(new Dimension(120, 35));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    
    private void setupEventHandlers() {
        // Navigation button handlers (directly wired to fields)
        if (productsBtn != null) {
            productsBtn.addActionListener(e -> {
                System.out.println("NAV: Products button clicked");
                showProductsPanel();
            });
        }
        if (cartBtn != null) {
            cartBtn.addActionListener(e -> {
                System.out.println("NAV: Cart button clicked");
                showCartPanel();
            });
        }
        if (checkoutBtn != null) {
            checkoutBtn.addActionListener(e -> {
                System.out.println("NAV: Checkout button clicked");
                showCheckoutPanel();
            });
        }
    }
    
    private JButton findButton(String text) {
        Component[] components = getContentPane().getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JButton button = findButtonInPanel((JPanel) comp, text);
                if (button != null) return button;
            }
        }
        return null;
    }
    
    private JButton findButtonInPanel(JPanel panel, String text) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JButton && ((JButton) comp).getText().equals(text)) {
                return (JButton) comp;
            } else if (comp instanceof JPanel) {
                JButton button = findButtonInPanel((JPanel) comp, text);
                if (button != null) return button;
            }
        }
        return null;
    }

    // NOTE: findButton/findButtonInPanel are kept for backward compatibility but
    // not used anymore because we wire navigation buttons directly to fields.
    
    public void showProductsPanel() {
        cardLayout.show(mainPanel, "PRODUCTS");
        productPanel.refreshDisplay();
    }
    
    public void showCartPanel() {
        cardLayout.show(mainPanel, "CART");
        cartPanel.refreshDisplay();
    }
    
    public void showCheckoutPanel() {
        cardLayout.show(mainPanel, "CHECKOUT");
        checkoutPanel.refreshDisplay();
    }
    
    public void updateCartDisplay() {
        cartItemCountLabel.setText(cart.getTotalItems() + " items");
        cartTotalLabel.setText("$" + String.format("%.2f", cart.getTotalPrice()));
    }
    
    public ShoppingCart getCart() {
        return cart;
    }
    
    public ToyInventory getInventory() {
        return inventory;
    }
}
