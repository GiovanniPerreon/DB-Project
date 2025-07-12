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
}
