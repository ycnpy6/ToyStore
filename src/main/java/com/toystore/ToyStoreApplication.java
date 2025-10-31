package com.toystore;

import com.toystore.ui.ToyStoreMainWindow;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;

/**
 * Main application class for the Toy Store
 */
public class ToyStoreApplication {
    public static void main(String[] args) {
        // Set custom look and feel properties
        try {
            // Set cross-platform look and feel for consistent button rendering
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            
            // Ensure buttons are opaque
            UIManager.put("Button.background", new Color(153, 51, 0));
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));
            UIManager.put("Button.opaque", Boolean.TRUE);
            UIManager.put("Button.border", BorderFactory.createRaisedBevelBorder());
        } catch (Exception e) {
            System.err.println("Could not set look and feel: " + e.getMessage());
        }
        
        // Create and show the main window
        SwingUtilities.invokeLater(() -> {
            ToyStoreMainWindow mainWindow = new ToyStoreMainWindow();
            mainWindow.setVisible(true);
        });
    }
}
