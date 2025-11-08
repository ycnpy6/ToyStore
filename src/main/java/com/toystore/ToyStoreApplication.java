package com.toystore;

import com.toystore.ui.ToyStoreMainWindow;
import javax.swing.*;

/**
 * Main application class for the Toy Store
 */
public class ToyStoreApplication {
    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set system look and feel: " + e.getMessage());
        }
        
        // Create and show the main window
        SwingUtilities.invokeLater(() -> {
            ToyStoreMainWindow mainWindow = new ToyStoreMainWindow();
            mainWindow.setVisible(true);
        });
    }
}
