package db_project.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;

/**
 * Utility class for consistent UI styling across all components
 */
public final class UIStyler {
    
    // Color constants
    public static final Color LIGHT_BACKGROUND = new Color(248, 249, 250);
    public static final Color ALICE_BLUE = new Color(240, 248, 255);
    public static final Color STEEL_BLUE = new Color(70, 130, 180);
    public static final Color MIDNIGHT_BLUE = new Color(25, 25, 112);
    public static final Color FOREST_GREEN = new Color(34, 139, 34);
    public static final Color HONEYDEW = new Color(240, 255, 240);
    public static final Color CRIMSON = new Color(220, 20, 60);
    public static final Color LAVENDER_BLUSH = new Color(255, 240, 245);
    public static final Color MEDIUM_SLATE_BLUE = new Color(147, 112, 219);
    public static final Color GHOST_WHITE = new Color(248, 248, 255);
    public static final Color DARK_ORANGE = new Color(255, 140, 0);
    public static final Color CORNSILK = new Color(255, 248, 220);
    
    // Font constants
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    
    private UIStyler() {
        // Utility class - prevent instantiation
    }
    
    /**
     * Creates a styled button with the given text and background color
     */
    public static JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        button.setPreferredSize(new Dimension(150, 35));
        button.setFont(LABEL_FONT);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return button;
    }
    
    /**
     * Creates a compact filter button
     */
    public static JButton createCompactFilterButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        button.setBackground(STEEL_BLUE);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return button;
    }
    
    /**
     * Style a component with center alignment
     */
    public static void centerAlign(JComponent component) {
        component.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    }
    
    /**
     * Create an empty border with specified padding
     */
    public static javax.swing.border.Border createPadding(int top, int left, int bottom, int right) {
        return BorderFactory.createEmptyBorder(top, left, bottom, right);
    }
    
    /**
     * Create a titled border with specified color
     */
    public static javax.swing.border.Border createTitledBorder(String title, Color color) {
        return BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(color, 2), title,
            javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), color
        );
    }
}
