package db_project.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Panel responsible for user login functionality
 */
public class LoginPanel extends JPanel {
    
    private final ViewManager viewManager;
    private JTextField emailField;
    private JTextField passwordField;
    
    public LoginPanel(ViewManager viewManager) {
        this.viewManager = viewManager;
        setupLoginPanel();
    }
    
    private void setupLoginPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 2),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        setBackground(UIStyler.LIGHT_BACKGROUND);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(10, 10, 10, 10);
        
        // Title
        JLabel loginTitle = new JLabel("SteamDB Login");
        loginTitle.setFont(UIStyler.HEADER_FONT);
        loginTitle.setForeground(UIStyler.MIDNIGHT_BLUE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginTitle, gbc);
        
        // Email label and field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(UIStyler.LABEL_FONT);
        emailLabel.setForeground(UIStyler.STEEL_BLUE);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(emailLabel, gbc);
        
        emailField = new JTextField(25);
        emailField.setFont(UIStyler.TEXT_FONT);
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(emailField, gbc);
        
        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(UIStyler.LABEL_FONT);
        passwordLabel.setForeground(UIStyler.STEEL_BLUE);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(passwordLabel, gbc);
        
        passwordField = new JTextField(25);
        passwordField.setFont(UIStyler.TEXT_FONT);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(passwordField, gbc);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);
        
        JButton loginButton = new JButton("Login");
        loginButton.setFont(UIStyler.LABEL_FONT);
        loginButton.setBackground(UIStyler.STEEL_BLUE);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        JButton registerButton = new JButton("Register New User");
        registerButton.setFont(UIStyler.LABEL_FONT);
        registerButton.setBackground(UIStyler.FOREST_GREEN);
        registerButton.setForeground(Color.WHITE);
        registerButton.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);
        
        // Event listeners
        loginButton.addActionListener(e -> attemptLogin());
        registerButton.addActionListener(e -> viewManager.showRegisterDialog());
    }
    
    private void attemptLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        if (email.isEmpty() || password.isEmpty()) {
            viewManager.showError("Please enter both email and password");
        } else {
            viewManager.performLogin(email, password);
        }
    }
    
    /**
     * Clear the login fields
     */
    public void clearFields() {
        emailField.setText("");
        passwordField.setText("");
    }
}
