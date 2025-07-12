package db_project.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JComboBox;

/**
 * Utility class for consistent UI styling across all components
 */
public final class UIStyler {
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
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    /**
     * Utility class - prevent instantiation
     */
    private UIStyler() {
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
     * Creates a styled ComboBox with the given items
     */
    public static <T> JComboBox<T> createStyledComboBox(T[] items) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setFont(LABEL_FONT);
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.BLACK);
        comboBox.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return comboBox;
    }
    /**
     * Creates a styled ComboBox with consistent appearance
     */
    public static <T> JComboBox<T> createStyledComboBox() {
        JComboBox<T> comboBox = new JComboBox<>();
        comboBox.setFont(LABEL_FONT);
        comboBox.setBackground(ALICE_BLUE);
        comboBox.setForeground(MIDNIGHT_BLUE);
        comboBox.setPreferredSize(new Dimension(150, 32));
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(STEEL_BLUE, 2),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        comboBox.setFocusable(true);
        comboBox.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                javax.swing.JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setFont(LABEL_FONT);
                setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
                if (isSelected) {
                    setBackground(STEEL_BLUE);
                    setForeground(Color.WHITE);
                } else {
                    setBackground(ALICE_BLUE);
                    setForeground(MIDNIGHT_BLUE);
                }
                if (value != null && value.toString().contains("Select")) {
                    setForeground(isSelected ? Color.WHITE : new Color(120, 120, 120));
                    if (!isSelected) {
                        setFont(new Font("Segoe UI", Font.ITALIC, 14));
                    }
                }
                return this;
            }
        });
        comboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (comboBox.isEnabled()) {
                    comboBox.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(FOREST_GREEN, 2),
                        BorderFactory.createEmptyBorder(4, 10, 4, 10)
                    ));
                }
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                comboBox.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(STEEL_BLUE, 2),
                    BorderFactory.createEmptyBorder(4, 10, 4, 10)
                ));
            }
        });
        return comboBox;
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
    /**
     * Creates a styled message dialog with consistent appearance
     */
    public static void showStyledMessage(java.awt.Component parent, String message, String title) {
        javax.swing.JDialog dialog = new javax.swing.JDialog((java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(parent), title, true);
        dialog.setLayout(new java.awt.BorderLayout());
        dialog.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
        javax.swing.JPanel mainPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        mainPanel.setBackground(ALICE_BLUE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(STEEL_BLUE, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        javax.swing.JLabel messageLabel = new javax.swing.JLabel("<html><div style='text-align: center; width: 300px;'>" + message + "</div></html>");
        messageLabel.setFont(TEXT_FONT);
        messageLabel.setForeground(MIDNIGHT_BLUE);
        messageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        javax.swing.JButton okButton = createStyledButton("OK", STEEL_BLUE);
        okButton.addActionListener(e -> dialog.dispose());
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(okButton);
        mainPanel.add(messageLabel, java.awt.BorderLayout.CENTER);
        mainPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        dialog.add(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
    /**
     * Creates a styled error dialog with consistent appearance
     */
    public static void showStyledError(java.awt.Component parent, String message, String title) {
        javax.swing.JDialog dialog = new javax.swing.JDialog((java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(parent), title, true);
        dialog.setLayout(new java.awt.BorderLayout());
        dialog.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
        javax.swing.JPanel mainPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        mainPanel.setBackground(LAVENDER_BLUSH);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CRIMSON, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        javax.swing.JLabel messageLabel = new javax.swing.JLabel("<html><div style='text-align: center; width: 300px;'>" + message + "</div></html>");
        messageLabel.setFont(TEXT_FONT);
        messageLabel.setForeground(CRIMSON);
        messageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        javax.swing.JButton okButton = createStyledButton("OK", CRIMSON);
        okButton.addActionListener(e -> dialog.dispose());
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(okButton);
        mainPanel.add(messageLabel, java.awt.BorderLayout.CENTER);
        mainPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        dialog.add(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
    /**
     * Creates a styled input dialog with consistent appearance
     */
    public static String showStyledInput(java.awt.Component parent, String message, String title, String initialValue) {
        javax.swing.JDialog dialog = new javax.swing.JDialog((java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(parent), title, true);
        dialog.setLayout(new java.awt.BorderLayout());
        dialog.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
        final String[] result = {null};
        javax.swing.JPanel mainPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        mainPanel.setBackground(ALICE_BLUE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(STEEL_BLUE, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        javax.swing.JLabel messageLabel = new javax.swing.JLabel("<html><div style='text-align: center; width: 300px;'>" + message + "</div></html>");
        messageLabel.setFont(TEXT_FONT);
        messageLabel.setForeground(MIDNIGHT_BLUE);
        messageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        javax.swing.JTextField inputField = new javax.swing.JTextField(initialValue != null ? initialValue : "", 20);
        inputField.setFont(TEXT_FONT);
        inputField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(STEEL_BLUE, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        javax.swing.JButton okButton = createStyledButton("OK", FOREST_GREEN);
        javax.swing.JButton cancelButton = createStyledButton("Cancel", CRIMSON);
        okButton.addActionListener(e -> {
            result[0] = inputField.getText();
            dialog.dispose();
        });
        cancelButton.addActionListener(e -> dialog.dispose());
        inputField.addActionListener(e -> {
            result[0] = inputField.getText();
            dialog.dispose();
        });
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        javax.swing.JPanel contentPanel = new javax.swing.JPanel();
        contentPanel.setLayout(new javax.swing.BoxLayout(contentPanel, javax.swing.BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.add(messageLabel);
        contentPanel.add(javax.swing.Box.createVerticalStrut(15));
        contentPanel.add(inputField);
        mainPanel.add(contentPanel, java.awt.BorderLayout.CENTER);
        mainPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        dialog.add(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        javax.swing.SwingUtilities.invokeLater(() -> inputField.requestFocusInWindow());
        dialog.setVisible(true);
        return result[0];
    }
    /**
     * Creates a styled selection dialog with consistent appearance
     */
    public static String showStyledSelection(java.awt.Component parent, String message, String title, String[] options, String defaultOption) {
        javax.swing.JDialog dialog = new javax.swing.JDialog((java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(parent), title, true);
        dialog.setLayout(new java.awt.BorderLayout());
        dialog.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
        final String[] result = {null};
        javax.swing.JPanel mainPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        mainPanel.setBackground(ALICE_BLUE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(STEEL_BLUE, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        javax.swing.JLabel messageLabel = new javax.swing.JLabel("<html><div style='text-align: center; width: 300px;'>" + message + "</div></html>");
        messageLabel.setFont(TEXT_FONT);
        messageLabel.setForeground(MIDNIGHT_BLUE);
        messageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        javax.swing.JComboBox<String> selectionBox = createStyledComboBox();
        for (String option : options) {
            selectionBox.addItem(option);
        }
        if (defaultOption != null) {
            selectionBox.setSelectedItem(defaultOption);
        }
        javax.swing.JButton okButton = createStyledButton("OK", FOREST_GREEN);
        javax.swing.JButton cancelButton = createStyledButton("Cancel", CRIMSON);
        okButton.addActionListener(e -> {
            result[0] = (String) selectionBox.getSelectedItem();
            dialog.dispose();
        });
        cancelButton.addActionListener(e -> dialog.dispose());
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        javax.swing.JPanel contentPanel = new javax.swing.JPanel();
        contentPanel.setLayout(new javax.swing.BoxLayout(contentPanel, javax.swing.BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.add(messageLabel);
        contentPanel.add(javax.swing.Box.createVerticalStrut(15));
        contentPanel.add(selectionBox);
        mainPanel.add(contentPanel, java.awt.BorderLayout.CENTER);
        mainPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        dialog.add(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
        return result[0];
    }
    /**
     * Creates a styled review dialog for adding game reviews
     */
    public static ReviewDialogResult showStyledReviewDialog(java.awt.Component parent, String gameTitle) {
        javax.swing.JDialog dialog = new javax.swing.JDialog((java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(parent), "Add Review for " + gameTitle, true);
        dialog.setLayout(new java.awt.BorderLayout());
        dialog.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
        final ReviewDialogResult result = new ReviewDialogResult();
        javax.swing.JPanel mainPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        mainPanel.setBackground(ALICE_BLUE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(STEEL_BLUE, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        javax.swing.JPanel contentPanel = new javax.swing.JPanel();
        contentPanel.setLayout(new javax.swing.BoxLayout(contentPanel, javax.swing.BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        javax.swing.JLabel titleLabel = new javax.swing.JLabel("Review for: " + gameTitle);
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(MIDNIGHT_BLUE);
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        javax.swing.JLabel ratingLabel = new javax.swing.JLabel("Rating (1-5):");
        ratingLabel.setFont(LABEL_FONT);
        ratingLabel.setForeground(MIDNIGHT_BLUE);
        javax.swing.JTextField ratingField = new javax.swing.JTextField(10);
        ratingField.setFont(TEXT_FONT);
        ratingField.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 30));
        ratingField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(STEEL_BLUE, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        javax.swing.JLabel commentLabel = new javax.swing.JLabel("Comment:");
        commentLabel.setFont(LABEL_FONT);
        commentLabel.setForeground(MIDNIGHT_BLUE);
        javax.swing.JTextArea commentArea = new javax.swing.JTextArea(5, 25);
        commentArea.setFont(TEXT_FONT);
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        commentArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(STEEL_BLUE, 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        javax.swing.JScrollPane commentScrollPane = new javax.swing.JScrollPane(commentArea);
        commentScrollPane.setPreferredSize(new java.awt.Dimension(300, 120));
        contentPanel.add(titleLabel);
        contentPanel.add(javax.swing.Box.createVerticalStrut(20));
        contentPanel.add(ratingLabel);
        contentPanel.add(javax.swing.Box.createVerticalStrut(5));
        contentPanel.add(ratingField);
        contentPanel.add(javax.swing.Box.createVerticalStrut(15));
        contentPanel.add(commentLabel);
        contentPanel.add(javax.swing.Box.createVerticalStrut(5));
        contentPanel.add(commentScrollPane);
        javax.swing.JButton submitButton = createStyledButton("Submit Review", FOREST_GREEN);
        javax.swing.JButton cancelButton = createStyledButton("Cancel", CRIMSON);
        submitButton.addActionListener(e -> {
            try {
                String ratingText = ratingField.getText().trim();
                String comment = commentArea.getText().trim();
                if (ratingText.isEmpty()) {
                    showStyledError(dialog, "Please enter a rating!", "Invalid Input");
                    return;
                }
                int rating = Integer.parseInt(ratingText);
                if (rating < 1 || rating > 5) {
                    showStyledError(dialog, "Rating must be between 1 and 5!", "Invalid Rating");
                    return;
                }
                if (comment.isEmpty()) {
                    showStyledError(dialog, "Please enter a comment for your review!", "Invalid Input");
                    return;
                }
                result.rating = rating;
                result.comment = comment;
                result.submitted = true;
                dialog.dispose();
            } catch (NumberFormatException ex) {
                showStyledError(dialog, "Please enter a valid number for rating!", "Invalid Rating");
            }
        });
        cancelButton.addActionListener(e -> dialog.dispose());
        ratingField.addActionListener(e -> commentArea.requestFocusInWindow());
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(contentPanel, java.awt.BorderLayout.CENTER);
        mainPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        dialog.add(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        javax.swing.SwingUtilities.invokeLater(() -> ratingField.requestFocusInWindow());
        dialog.setVisible(true);
        return result;
    }
    /**
     * Result class for review dialog
     */
    public static class ReviewDialogResult {
        public int rating = 0;
        public String comment = "";
        public boolean submitted = false;
    }
    /**
     * Creates a styled registration dialog for new users
     */
    public static RegistrationDialogResult showStyledRegistrationDialog(java.awt.Component parent) {
        javax.swing.JDialog dialog = new javax.swing.JDialog((java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(parent), "Register New User", true);
        dialog.setLayout(new java.awt.BorderLayout());
        dialog.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
        final RegistrationDialogResult result = new RegistrationDialogResult();
        javax.swing.JPanel mainPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        mainPanel.setBackground(HONEYDEW);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(FOREST_GREEN, 2),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        javax.swing.JPanel contentPanel = new javax.swing.JPanel();
        contentPanel.setLayout(new javax.swing.BoxLayout(contentPanel, javax.swing.BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        javax.swing.JLabel titleLabel = new javax.swing.JLabel("Create New Account");
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(FOREST_GREEN);
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        javax.swing.JTextField emailField = createStyledTextField("example@email.com");
        javax.swing.JTextField passwordField = createStyledTextField("");
        javax.swing.JTextField nameField = createStyledTextField("");
        javax.swing.JTextField surnameField = createStyledTextField("");
        javax.swing.JTextField birthDateField = createStyledTextField("YYYY-MM-DD");
        contentPanel.add(titleLabel);
        contentPanel.add(javax.swing.Box.createVerticalStrut(20));
        contentPanel.add(createFieldSection("Email:", emailField));
        contentPanel.add(javax.swing.Box.createVerticalStrut(10));
        contentPanel.add(createFieldSection("Password:", passwordField));
        contentPanel.add(javax.swing.Box.createVerticalStrut(10));
        contentPanel.add(createFieldSection("Name:", nameField));
        contentPanel.add(javax.swing.Box.createVerticalStrut(10));
        contentPanel.add(createFieldSection("Surname:", surnameField));
        contentPanel.add(javax.swing.Box.createVerticalStrut(10));
        contentPanel.add(createFieldSection("Birth Date:", birthDateField));
        javax.swing.JButton registerButton = createStyledButton("Register", FOREST_GREEN);
        javax.swing.JButton cancelButton = createStyledButton("Cancel", CRIMSON);
        registerButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            String name = nameField.getText().trim();
            String surname = surnameField.getText().trim();
            String birthDate = birthDateField.getText().trim();
            if (email.isEmpty() || password.isEmpty() || name.isEmpty() || 
                surname.isEmpty() || birthDate.isEmpty()) {
                showStyledError(dialog, "Please fill in all fields!", "Incomplete Information");
                return;
            }
            if (!email.contains("@")) {
                showStyledError(dialog, "Please enter a valid email address!", "Invalid Email");
                return;
            }
            if (!birthDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                showStyledError(dialog, "Please enter birth date in YYYY-MM-DD format!", "Invalid Date");
                return;
            }
            result.email = email;
            result.password = password;
            result.name = name;
            result.surname = surname;
            result.birthDate = birthDate;
            result.submitted = true;
            dialog.dispose();
        });
        cancelButton.addActionListener(e -> dialog.dispose());
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(contentPanel, java.awt.BorderLayout.CENTER);
        mainPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        dialog.add(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        javax.swing.SwingUtilities.invokeLater(() -> emailField.requestFocusInWindow());
        dialog.setVisible(true);
        return result;
    }
    /**
     * Helper method to create styled text fields
     */
    private static javax.swing.JTextField createStyledTextField(String placeholder) {
        javax.swing.JTextField field = new javax.swing.JTextField(20);
        field.setFont(TEXT_FONT);
        field.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 30));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(STEEL_BLUE, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        if (!placeholder.isEmpty()) {
            field.setText(placeholder);
            field.setForeground(new Color(150, 150, 150));
            field.addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    if (field.getText().equals(placeholder)) {
                        field.setText("");
                        field.setForeground(MIDNIGHT_BLUE);
                    }
                }
                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    if (field.getText().isEmpty()) {
                        field.setText(placeholder);
                        field.setForeground(new Color(150, 150, 150));
                    }
                }
            });
        }
        return field;
    }
    /**
     * Helper method to create field sections
     */
    private static javax.swing.JPanel createFieldSection(String labelText, javax.swing.JTextField field) {
        javax.swing.JPanel section = new javax.swing.JPanel();
        section.setLayout(new javax.swing.BoxLayout(section, javax.swing.BoxLayout.Y_AXIS));
        section.setOpaque(false);
        javax.swing.JLabel label = new javax.swing.JLabel(labelText);
        label.setFont(LABEL_FONT);
        label.setForeground(MIDNIGHT_BLUE);
        section.add(label);
        section.add(javax.swing.Box.createVerticalStrut(3));
        section.add(field);
        return section;
    }
    /**
     * Functional interface for developer validation
     */
    public interface DeveloperValidator {
        String validateDeveloper(String developerID);
    }

    /**
     * Shows a styled game creation dialog
     * @param parent parent component for dialog positioning
     * @return GameCreationResult with the entered data, or null if cancelled
     */
    public static GameCreationResult showStyledGameCreationDialog(java.awt.Component parent) {
        return showStyledGameCreationDialog(parent, null);
    }
    
    /**
     * Shows a styled game creation dialog with developer validation
     * @param parent parent component for dialog positioning
     * @param developerValidator function to validate developer ID, returns null if valid or error message if invalid
     * @return GameCreationResult with the entered data, or null if cancelled
     */
    public static GameCreationResult showStyledGameCreationDialog(java.awt.Component parent, DeveloperValidator developerValidator) {
        final GameCreationResult[] result = {null};
        
        javax.swing.JDialog dialog = new javax.swing.JDialog(
            javax.swing.SwingUtilities.getWindowAncestor(parent), 
            "Create New Game", 
            java.awt.Dialog.ModalityType.APPLICATION_MODAL
        );
        
        dialog.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
        
        // Main panel
        javax.swing.JPanel mainPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        mainPanel.setBackground(ALICE_BLUE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(FOREST_GREEN, 3),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Title
        javax.swing.JLabel titleLabel = new javax.swing.JLabel("Create New Game", javax.swing.SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(FOREST_GREEN);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, java.awt.BorderLayout.NORTH);
        
        // Content panel with scroll
        javax.swing.JPanel contentPanel = new javax.swing.JPanel();
        contentPanel.setLayout(new javax.swing.BoxLayout(contentPanel, javax.swing.BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        
        // Game title field
        javax.swing.JLabel titleFieldLabel = new javax.swing.JLabel("Game Title:");
        titleFieldLabel.setFont(LABEL_FONT);
        titleFieldLabel.setForeground(MIDNIGHT_BLUE);
        javax.swing.JTextField titleField = createStyledTextField("Enter game title");
        
        // Price field
        javax.swing.JLabel priceLabel = new javax.swing.JLabel("Price:");
        priceLabel.setFont(LABEL_FONT);
        priceLabel.setForeground(MIDNIGHT_BLUE);
        javax.swing.JTextField priceField = createStyledTextField("0.00");
        
        // Description field
        javax.swing.JLabel descLabel = new javax.swing.JLabel("Description:");
        descLabel.setFont(LABEL_FONT);
        descLabel.setForeground(MIDNIGHT_BLUE);
        javax.swing.JTextArea descArea = new javax.swing.JTextArea(4, 30);
        descArea.setFont(TEXT_FONT);
        descArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(STEEL_BLUE, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setBackground(Color.WHITE);
        javax.swing.JScrollPane descScrollPane = new javax.swing.JScrollPane(descArea);
        descScrollPane.setPreferredSize(new java.awt.Dimension(350, 100));
        descScrollPane.setBorder(BorderFactory.createLineBorder(STEEL_BLUE, 1));
        
        // Requirements field
        javax.swing.JLabel reqLabel = new javax.swing.JLabel("System Requirements:");
        reqLabel.setFont(LABEL_FONT);
        reqLabel.setForeground(MIDNIGHT_BLUE);
        javax.swing.JTextArea reqArea = new javax.swing.JTextArea(4, 30);
        reqArea.setFont(TEXT_FONT);
        reqArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(STEEL_BLUE, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        reqArea.setLineWrap(true);
        reqArea.setWrapStyleWord(true);
        reqArea.setBackground(Color.WHITE);
        javax.swing.JScrollPane reqScrollPane = new javax.swing.JScrollPane(reqArea);
        reqScrollPane.setPreferredSize(new java.awt.Dimension(350, 100));
        reqScrollPane.setBorder(BorderFactory.createLineBorder(STEEL_BLUE, 1));
        
        // Release date field
        javax.swing.JLabel releaseDateLabel = new javax.swing.JLabel("Release Date (YYYY-MM-DD):");
        releaseDateLabel.setFont(LABEL_FONT);
        releaseDateLabel.setForeground(MIDNIGHT_BLUE);
        javax.swing.JTextField releaseDateField = createStyledTextField("2024-01-01");
        
        // Developer ID field
        javax.swing.JLabel developerIDLabel = new javax.swing.JLabel("Developer ID:");
        developerIDLabel.setFont(LABEL_FONT);
        developerIDLabel.setForeground(MIDNIGHT_BLUE);
        javax.swing.JTextField developerIDField = createStyledTextField("Enter developer user ID");
        
        // Add components with spacing
        contentPanel.add(titleFieldLabel);
        contentPanel.add(javax.swing.Box.createVerticalStrut(5));
        contentPanel.add(titleField);
        contentPanel.add(javax.swing.Box.createVerticalStrut(15));
        
        contentPanel.add(priceLabel);
        contentPanel.add(javax.swing.Box.createVerticalStrut(5));
        contentPanel.add(priceField);
        contentPanel.add(javax.swing.Box.createVerticalStrut(15));
        
        contentPanel.add(descLabel);
        contentPanel.add(javax.swing.Box.createVerticalStrut(5));
        contentPanel.add(descScrollPane);
        contentPanel.add(javax.swing.Box.createVerticalStrut(15));
        
        contentPanel.add(reqLabel);
        contentPanel.add(javax.swing.Box.createVerticalStrut(5));
        contentPanel.add(reqScrollPane);
        contentPanel.add(javax.swing.Box.createVerticalStrut(15));
        
        contentPanel.add(releaseDateLabel);
        contentPanel.add(javax.swing.Box.createVerticalStrut(5));
        contentPanel.add(releaseDateField);
        contentPanel.add(javax.swing.Box.createVerticalStrut(15));
        
        contentPanel.add(developerIDLabel);
        contentPanel.add(javax.swing.Box.createVerticalStrut(5));
        contentPanel.add(developerIDField);
        
        // Scroll pane for content
        javax.swing.JScrollPane mainScrollPane = new javax.swing.JScrollPane(contentPanel);
        mainScrollPane.setPreferredSize(new java.awt.Dimension(400, 450));
        mainScrollPane.setBorder(null);
        mainScrollPane.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        // Buttons
        javax.swing.JButton createButton = createStyledButton("Create Game", FOREST_GREEN);
        javax.swing.JButton cancelButton = createStyledButton("Cancel", STEEL_BLUE);
        
        createButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String description = descArea.getText().trim();
            String requirements = reqArea.getText().trim();
            String priceText = priceField.getText().trim();
            String releaseDate = releaseDateField.getText().trim();
            String developerID = developerIDField.getText().trim();
            
            // Validation
            if (title.isEmpty()) {
                showStyledError(dialog, "Please enter a game title.", "Invalid Input");
                return;
            }
            if (description.isEmpty()) {
                showStyledError(dialog, "Please enter a game description.", "Invalid Input");
                return;
            }
            if (requirements.isEmpty()) {
                showStyledError(dialog, "Please enter system requirements.", "Invalid Input");
                return;
            }
            
            try {
                double price = Double.parseDouble(priceText);
                if (price < 0) {
                    showStyledError(dialog, "Price must be a positive number.", "Invalid Price");
                    return;
                }
            } catch (NumberFormatException ex) {
                showStyledError(dialog, "Please enter a valid price (e.g., 19.99).", "Invalid Price");
                return;
            }
            
            // Basic date validation (format check)
            if (!releaseDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                showStyledError(dialog, "Please enter date in YYYY-MM-DD format.", "Invalid Date");
                return;
            }
            
            // Developer ID validation (required field)
            if (developerID.isEmpty() || developerID.equals("Enter developer user ID")) {
                showStyledError(dialog, "Please enter a Developer ID.", "Invalid Developer ID");
                return;
            }
            
            try {
                Integer.parseInt(developerID);
            } catch (NumberFormatException ex) {
                showStyledError(dialog, "Developer ID must be a valid number.", "Invalid Developer ID");
                return;
            }
            
            // Additional developer validation if validator is provided
            if (developerValidator != null) {
                String validationError = developerValidator.validateDeveloper(developerID);
                if (validationError != null) {
                    showStyledError(dialog, validationError, "Invalid Developer");
                    return;
                }
            }
            
            result[0] = new GameCreationResult(title, priceText, description, requirements, releaseDate, developerID);
            dialog.dispose();
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        // Button panel
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);
        
        mainPanel.add(mainScrollPane, java.awt.BorderLayout.CENTER);
        mainPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
        
        return result[0];
    }

    /**
     * Result class for game creation dialog
     */
    public static class GameCreationResult {
        public final String title;
        public final String price;
        public final String description;
        public final String requirements;
        public final String releaseDate;
        public final String developerID;
        
        public GameCreationResult(String title, String price, String description, String requirements, String releaseDate, String developerID) {
            this.title = title;
            this.price = price;
            this.description = description;
            this.requirements = requirements;
            this.releaseDate = releaseDate;
            this.developerID = developerID;
        }
    }

    /**
     * Result class for registration dialog
     */
    public static class RegistrationDialogResult {
        public String email = "";
        public String password = "";
        public String name = "";
        public String surname = "";
        public String birthDate = "";
        public boolean submitted = false;
    }
    /**
     * Shows a styled confirmation dialog
     * @param parent parent component for dialog positioning
     * @param message the message to display
     * @param title the dialog title
     * @return true if user clicked Yes, false if No or closed
     */
    public static boolean showStyledConfirmation(java.awt.Component parent, String message, String title) {
        final boolean[] result = {false};
        
        javax.swing.JDialog dialog = new javax.swing.JDialog(
            javax.swing.SwingUtilities.getWindowAncestor(parent),
            title,
            java.awt.Dialog.ModalityType.APPLICATION_MODAL
        );
        
        dialog.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
        
        // Main panel
        javax.swing.JPanel mainPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        mainPanel.setBackground(ALICE_BLUE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(STEEL_BLUE, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Icon and message panel
        javax.swing.JPanel messagePanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        messagePanel.setOpaque(false);
        
        // Question icon
        javax.swing.JLabel iconLabel = new javax.swing.JLabel("?");
        iconLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 36));
        iconLabel.setForeground(STEEL_BLUE);
        iconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconLabel.setPreferredSize(new java.awt.Dimension(60, 60));
        iconLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(STEEL_BLUE, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        iconLabel.setOpaque(true);
        iconLabel.setBackground(HONEYDEW);
        
        // Message label
        javax.swing.JLabel messageLabel = new javax.swing.JLabel("<html><div style='text-align: center; width: 250px;'>" + message + "</div></html>");
        messageLabel.setFont(TEXT_FONT);
        messageLabel.setForeground(MIDNIGHT_BLUE);
        messageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        messagePanel.add(iconLabel, java.awt.BorderLayout.WEST);
        messagePanel.add(messageLabel, java.awt.BorderLayout.CENTER);
        
        // Button panel
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
        buttonPanel.setOpaque(false);
        
        javax.swing.JButton yesButton = createStyledButton("Yes", FOREST_GREEN);
        javax.swing.JButton noButton = createStyledButton("No", new java.awt.Color(220, 20, 60));
        
        yesButton.addActionListener(e -> {
            result[0] = true;
            dialog.dispose();
        });
        
        noButton.addActionListener(e -> {
            result[0] = false;
            dialog.dispose();
        });
        
        buttonPanel.add(yesButton);
        buttonPanel.add(javax.swing.Box.createHorizontalStrut(10));
        buttonPanel.add(noButton);
        
        mainPanel.add(messagePanel, java.awt.BorderLayout.CENTER);
        mainPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
        
        return result[0];
    }
}
