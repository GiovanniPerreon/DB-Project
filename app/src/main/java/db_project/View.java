package db_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.Box;
import javax.swing.border.TitledBorder;

import db_project.data.Users;
import db_project.data.VideoGames;

import db_project.data.Reviews;

import db_project.data.Achievements;

public final class View {

    private Optional<Controller> controller;
    private final JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel loginPanel;
    private Users currentUser;
    
    // Admin and publisher buttons
    private JButton publisherOpsButton;
    private JButton adminOpsButton;
    
    // Filter buttons for game browser
    private JComboBox<String> genreComboBox;
    
    // UI panels
    private JPanel filtersPanel;

    // We take an action to run before closing the view so that one can gracefully
    // deal with open resources.
    public View(Runnable onClose) {
        this.controller = Optional.empty();
        this.currentUser = null;
        this.mainFrame = this.setupMainFrame(onClose);
        this.setupUI();
    }

    private JFrame setupMainFrame(Runnable onClose) {
        var frame = new JFrame("SteamDB - Videogames Store");
        frame.setMinimumSize(new Dimension(1200, 700));
        frame.setPreferredSize(new Dimension(1300, 750));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    onClose.run();
                    System.exit(0);
                }
            }
        );
        return frame;
    }

    private void setupUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(248, 249, 250)); // Light background

        // Header with modern styling
        JLabel titleLabel = new JLabel("SteamDB - Videogames Store");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(25, 25, 112)); // Midnight blue
        titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        mainPanel.add(titleLabel);

        // User login section
        setupLoginSection();

        // Main menu (initially hidden)
        setupMainMenu();

        mainFrame.getContentPane().setBackground(new Color(248, 249, 250));
        mainFrame.add(mainPanel);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private void setupLoginSection() {
        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        loginPanel.setBackground(new Color(248, 249, 250)); // Light gray-blue background
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(10, 10, 10, 10);
        
        // Title
        JLabel loginTitle = new JLabel("SteamDB Login");
        loginTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        loginTitle.setForeground(new Color(25, 25, 112)); // Midnight blue
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginTitle, gbc);
        
        // Email label and field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        emailLabel.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(emailLabel, gbc);
        
        JTextField emailField = new JTextField(25);
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(emailField, gbc);
        
        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(passwordLabel, gbc);
        
        JTextField passwordField = new JTextField(25);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(passwordField, gbc);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);
        
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        JButton registerButton = new JButton("Register New User");
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerButton.setBackground(new Color(34, 139, 34));
        registerButton.setForeground(Color.WHITE);
        registerButton.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(buttonPanel, gbc);
        
        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            if (email.isEmpty() || password.isEmpty()) {
                showError("Please enter both email and password");
            } else {
                login(email, password);
            }
        }); 
        
        registerButton.addActionListener(e -> showRegisterDialog());
        
        mainPanel.add(loginPanel);
    }

    private void setupMainMenu() {
        JPanel menuPanel = new JPanel();
        menuPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        menuPanel.setLayout(new java.awt.BorderLayout());
        menuPanel.setVisible(false);
        menuPanel.setBackground(new Color(248, 249, 250)); // Light background
        
        // Left panel - Navigation (Single column of buttons)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(280, 700));
        leftPanel.setBackground(new Color(240, 248, 255)); // Alice blue
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // USER button (main dashboard view) - Primary color
        JButton userButton = new JButton("USER DASHBOARD");
        userButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        userButton.setPreferredSize(new Dimension(200, 45));
        userButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userButton.setBackground(new Color(70, 130, 180)); // Steel blue
        userButton.setForeground(Color.WHITE);
        userButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        userButton.setFocusPainted(false);
        userButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        // VIDEOGAMES section
        JPanel videogamesPanel = new JPanel();
        videogamesPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(34, 139, 34), 2), "VIDEOGAMES",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), new Color(34, 139, 34)
        ));
        videogamesPanel.setLayout(new BoxLayout(videogamesPanel, BoxLayout.Y_AXIS));
        videogamesPanel.setPreferredSize(new Dimension(200, 200));
        videogamesPanel.setBackground(new Color(240, 255, 240)); // Honeydew
        
        JButton viewAllGamesButton = createStyledButton("Browse Games", new Color(34, 139, 34));
        JButton topGamesButton = createStyledButton("Top Games", new Color(34, 139, 34));
        JButton mostBoughtButton = createStyledButton("Most Bought", new Color(34, 139, 34));
        JButton leastRatedButton = createStyledButton("Lowest Rated Developers", new Color(34, 139, 34));
        
        videogamesPanel.add(viewAllGamesButton);
        videogamesPanel.add(javax.swing.Box.createVerticalStrut(8));
        videogamesPanel.add(topGamesButton);
        videogamesPanel.add(javax.swing.Box.createVerticalStrut(8));
        videogamesPanel.add(mostBoughtButton);
        videogamesPanel.add(javax.swing.Box.createVerticalStrut(8));
        videogamesPanel.add(leastRatedButton);
        
        // OTHER section
        JPanel otherPanel = new JPanel();
        otherPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(220, 20, 60), 2), "OTHER",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), new Color(220, 20, 60)
        ));
        otherPanel.setLayout(new BoxLayout(otherPanel, BoxLayout.Y_AXIS));
        otherPanel.setPreferredSize(new Dimension(200, 140));
        otherPanel.setBackground(new Color(255, 240, 245)); // Lavender blush
        
        publisherOpsButton = createStyledButton("Publish Game", new Color(220, 20, 60));
        adminOpsButton = createStyledButton("View All Users", new Color(220, 20, 60));
        
        otherPanel.add(publisherOpsButton);
        otherPanel.add(javax.swing.Box.createVerticalStrut(8));
        otherPanel.add(adminOpsButton);
        
        // Add spacing between sections
        leftPanel.add(userButton);
        leftPanel.add(javax.swing.Box.createVerticalStrut(15));
        leftPanel.add(videogamesPanel);
        leftPanel.add(javax.swing.Box.createVerticalStrut(15));
        leftPanel.add(otherPanel);
        leftPanel.add(javax.swing.Box.createVerticalGlue());
        
        // Right panel - User Information / Game Browser
        rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2), "USER",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14), new Color(70, 130, 180)
        ));
        rightPanel.setLayout(new java.awt.BorderLayout());
        rightPanel.setPreferredSize(new Dimension(600, 600));
        rightPanel.setBackground(Color.WHITE);
        
        // Create card layout for switching between views
        cardLayout = new java.awt.CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Color.WHITE);
        
        // User Dashboard Panel (original 4-panel layout)
        JPanel userDashboardPanel = new JPanel();
        userDashboardPanel.setLayout(new java.awt.GridLayout(2, 2, 15, 15));
        userDashboardPanel.setBackground(Color.WHITE);
        userDashboardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Info Panel - Blue theme
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2), "INFO PANEL",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), new Color(70, 130, 180)
        ));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(240, 248, 255)); // Alice blue
        
        JLabel userInfoLabel = new JLabel("User Information");
        userInfoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userInfoLabel.setForeground(new Color(25, 25, 112));
        JTextArea userInfoArea = new JTextArea(5, 20);
        userInfoArea.setEditable(false);
        userInfoArea.setOpaque(false);
        
        // Logout button
        JButton logoutButton = createStyledButton("Logout", new Color(220, 20, 60));
        logoutButton.setMaximumSize(new Dimension(150, 30));
        logoutButton.setPreferredSize(new Dimension(150, 30));
        logoutButton.addActionListener(e -> logout());
        
        infoPanel.add(userInfoLabel);
        infoPanel.add(userInfoArea);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(logoutButton);
        
        // Game Owned Panel - Green theme
        JPanel gameOwnedPanel = new JPanel();
        gameOwnedPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(34, 139, 34), 2), "GAMES OWNED",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), new Color(34, 139, 34)
        ));
        gameOwnedPanel.setLayout(new BoxLayout(gameOwnedPanel, BoxLayout.Y_AXIS));
        gameOwnedPanel.setBackground(new Color(240, 255, 240)); // Honeydew
        
        JButton viewCollectionButton = createStyledButton("View Collection", new Color(34, 139, 34));
        JTextArea ownedGamesArea = new JTextArea(5, 20);
        ownedGamesArea.setEditable(false);
        ownedGamesArea.setOpaque(false);
        ownedGamesArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gameOwnedPanel.add(viewCollectionButton);
        gameOwnedPanel.add(Box.createVerticalStrut(5));
        gameOwnedPanel.add(ownedGamesArea);
        
        // Wishlist Panel - Purple theme
        JPanel wishlistPanel = new JPanel();
        wishlistPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(147, 112, 219), 2), "WISHLIST",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), new Color(147, 112, 219)
        ));
        wishlistPanel.setLayout(new BoxLayout(wishlistPanel, BoxLayout.Y_AXIS));
        wishlistPanel.setBackground(new Color(248, 248, 255)); // Ghost white
        
        JButton viewWishlistButton = createStyledButton("View Wishlist", new Color(147, 112, 219));
        JTextArea wishlistArea = new JTextArea(3, 20);
        wishlistArea.setEditable(false);
        wishlistArea.setOpaque(false);
        wishlistArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        wishlistPanel.add(viewWishlistButton);
        wishlistPanel.add(Box.createVerticalStrut(5));
        wishlistPanel.add(wishlistArea);
        
        // Achievements Panel - Orange theme
        JPanel achievementsPanel = new JPanel();
        achievementsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 140, 0), 2), "ACHIEVEMENTS GOT",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), new Color(255, 140, 0)
        ));
        achievementsPanel.setLayout(new BoxLayout(achievementsPanel, BoxLayout.Y_AXIS));
        achievementsPanel.setBackground(new Color(255, 248, 220)); // Cornsilk
        
        JButton viewAchievementsButton = createStyledButton("View Achievements", new Color(255, 140, 0));
        JTextArea achievementsArea = new JTextArea(3, 20);
        achievementsArea.setEditable(false);
        achievementsArea.setOpaque(false);
        achievementsArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        achievementsPanel.add(viewAchievementsButton);
        achievementsPanel.add(achievementsArea);
        
        // Add panels to user dashboard
        userDashboardPanel.add(infoPanel);
        userDashboardPanel.add(gameOwnedPanel);
        userDashboardPanel.add(wishlistPanel);
        userDashboardPanel.add(achievementsPanel);
        
        // Game Browser Panel
        JPanel gameBrowserPanel = new JPanel();
        gameBrowserPanel.setLayout(new java.awt.BorderLayout());
        
        // Game list
        JPanel gameListPanel = new JPanel();
        gameListPanel.setLayout(new BoxLayout(gameListPanel, BoxLayout.Y_AXIS));
        JScrollPane gameListScrollPane = new JScrollPane(gameListPanel);
        gameListScrollPane.setPreferredSize(new Dimension(580, 500));
        
        // Back button
        JButton backToUserButton = new JButton("Back to User Dashboard");
        
        // Filters Panel
        JPanel filtersPanel = new JPanel();
        filtersPanel.setLayout(new java.awt.FlowLayout());
        filtersPanel.setBorder(BorderFactory.createTitledBorder("Filters"));
        
        JButton newestGamesButton = createCompactFilterButton("Newest");
        JButton oldestGamesButton = createCompactFilterButton("Oldest");
        JButton highestRatedButton = createCompactFilterButton("Top Rated");
        JButton lowestRatedButton = createCompactFilterButton("Low Rated");
        JButton mostExpensiveButton = createCompactFilterButton("Expensive");
        JButton cheapestButton = createCompactFilterButton("Cheap");
        JButton mostSoldButton = createCompactFilterButton("Best Selling");
        JButton allGamesButton = createCompactFilterButton("All Games");
        
        // Genre filter
        JComboBox<String> genreComboBox = new JComboBox<>();
        genreComboBox.addItem("Select Genre...");
        // Genres will be loaded when controller is set
        
        filtersPanel.add(new JLabel("Sort by:"));
        filtersPanel.add(newestGamesButton);
        filtersPanel.add(oldestGamesButton);
        filtersPanel.add(highestRatedButton);
        filtersPanel.add(lowestRatedButton);
        filtersPanel.add(mostExpensiveButton);
        filtersPanel.add(cheapestButton);
        filtersPanel.add(mostSoldButton);
        filtersPanel.add(new JLabel("|"));
        filtersPanel.add(genreComboBox);
        filtersPanel.add(new JLabel("|"));
        filtersPanel.add(allGamesButton);
        
        // Create top panel with back button and filters
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new java.awt.BorderLayout());
        topPanel.add(backToUserButton, java.awt.BorderLayout.NORTH);
        topPanel.add(filtersPanel, java.awt.BorderLayout.CENTER);
        
        gameBrowserPanel.add(topPanel, java.awt.BorderLayout.NORTH);
        gameBrowserPanel.add(gameListScrollPane, java.awt.BorderLayout.CENTER);
        
        // Game Detail Panel (for individual game view)
        JPanel gameDetailPanel = new JPanel();
        gameDetailPanel.setLayout(new java.awt.BorderLayout());
        
        // Collection Panel
        JPanel collectionViewPanel = new JPanel();
        collectionViewPanel.setLayout(new java.awt.BorderLayout());
        
        // Wishlist Panel
        JPanel wishlistViewPanel = new JPanel();
        wishlistViewPanel.setLayout(new java.awt.BorderLayout());
        
        // Achievements Panel
        JPanel achievementsViewPanel = new JPanel();
        achievementsViewPanel.setLayout(new java.awt.BorderLayout());
        
        // Add panels to card layout
        cardPanel.add(userDashboardPanel, "USER_DASHBOARD");
        cardPanel.add(gameBrowserPanel, "GAME_BROWSER");
        cardPanel.add(gameDetailPanel, "GAME_DETAIL");
        cardPanel.add(collectionViewPanel, "USER_COLLECTION");
        cardPanel.add(wishlistViewPanel, "USER_WISHLIST");
        cardPanel.add(achievementsViewPanel, "ACHIEVEMENTS");
        
        rightPanel.add(cardPanel, java.awt.BorderLayout.CENTER);
        
        // Store references for buttons
        this.gameListPanel = gameListPanel;
        this.genreComboBox = genreComboBox;
        this.filtersPanel = filtersPanel;
        this.gameDetailPanel = gameDetailPanel;
        this.collectionViewPanel = collectionViewPanel;
        this.wishlistViewPanel = wishlistViewPanel;
        this.achievementsViewPanel = achievementsViewPanel;
        
        // Add action listeners
        userButton.addActionListener(e -> showUserDashboard());
        viewAllGamesButton.addActionListener(e -> showGameBrowser());
        topGamesButton.addActionListener(e -> showTopGamesBrowser());
        mostBoughtButton.addActionListener(e -> showMostBoughtBrowser());
        leastRatedButton.addActionListener(e -> showLeastRatedBrowser());
        publisherOpsButton.addActionListener(e -> showPublisherOperations());
        adminOpsButton.addActionListener(e -> showAllUsersBrowser());
        viewCollectionButton.addActionListener(e -> showUserCollectionView());
        viewWishlistButton.addActionListener(e -> showUserWishlistView());
        viewAchievementsButton.addActionListener(e -> showAchievementsView());
        backToUserButton.addActionListener(e -> showUserDashboard());
        
        // Filter action listeners
        newestGamesButton.addActionListener(e -> showFilteredGames("newest"));
        oldestGamesButton.addActionListener(e -> showFilteredGames("oldest"));
        highestRatedButton.addActionListener(e -> showFilteredGames("highest_rated"));
        lowestRatedButton.addActionListener(e -> showFilteredGames("lowest_rated"));
        mostExpensiveButton.addActionListener(e -> showFilteredGames("most_expensive"));
        cheapestButton.addActionListener(e -> showFilteredGames("cheapest"));
        mostSoldButton.addActionListener(e -> showFilteredGames("most_sold"));
        allGamesButton.addActionListener(e -> showFilteredGames("all"));
        genreComboBox.addActionListener(e -> {
            String selectedGenre = (String) genreComboBox.getSelectedItem();
            if (selectedGenre != null && !selectedGenre.equals("Select Genre...")) {
                showFilteredGames("genre:" + selectedGenre);
            }
        });
        
        // Store references to text areas for updates
        this.userInfoArea = userInfoArea;
        this.ownedGamesArea = ownedGamesArea;
        this.wishlistArea = wishlistArea;
        this.achievementsArea = achievementsArea;
        
        menuPanel.add(leftPanel, java.awt.BorderLayout.WEST);
        menuPanel.add(rightPanel, java.awt.BorderLayout.CENTER);
        
        mainPanel.add(menuPanel);
    }
    
    // Text area references for dynamic updates
    private JTextArea userInfoArea;
    private JTextArea ownedGamesArea;
    private JTextArea wishlistArea;
    private JTextArea achievementsArea;
    
    // Card layout components for switching views
    private JPanel rightPanel;
    private java.awt.CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel gameListPanel;
    private JPanel gameDetailPanel;
    private JPanel collectionViewPanel;
    private JPanel wishlistViewPanel;
    private JPanel achievementsViewPanel;

    private void login(String email, String password) {
        if (getController().loginUser(email, password)) {
            currentUser = getController().getCurrentUser();
            showMainMenu();
        } else {
            showError("Invalid email or password!");
        }
    }

    private void logout() {
        int result = JOptionPane.showConfirmDialog(
            mainFrame, 
            "Are you sure you want to logout?", 
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            // Clear current user session
            currentUser = null;
            
            // Return to login panel
            for (java.awt.Component comp : mainPanel.getComponents()) {
                comp.setVisible(false);
            }
            loginPanel.setVisible(true);
            mainFrame.setTitle("SteamDB - Videogames Store");
            
            // Show success message
            JOptionPane.showMessageDialog(mainFrame, "Logged out successfully!", "Logout", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showMainMenu() {
        // Hide login panel and show main menu with user dashboard
        for (java.awt.Component comp : mainPanel.getComponents()) {
            comp.setVisible(false);
        }
        
        // Show the main menu panel (navigation + card panel)
        for (java.awt.Component comp : mainPanel.getComponents()) {
            if (comp != loginPanel) {
                comp.setVisible(true);
            }
        }
        
        // Switch to user dashboard
        cardLayout.show(cardPanel, "USER_DASHBOARD");
        rightPanel.setBorder(BorderFactory.createTitledBorder("USER"));
        
        // Load user information into panels
        if (currentUser != null) {
            loadUserInfo();
            loadUserCollection(ownedGamesArea);
            loadUserWishlist(wishlistArea);
            loadUserAchievements(achievementsArea);
            
            // Enable/disable admin and publisher buttons based on user type
            if (publisherOpsButton != null) {
                publisherOpsButton.setEnabled(currentUser.isPublisher());
            }
            if (adminOpsButton != null) {
                adminOpsButton.setVisible(currentUser.isAdministrator());
            }
        }
        
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    // Dialog methods
    private void showRegisterDialog() {
        JTextField emailField = new JTextField(20);
        JTextField passwordField = new JTextField(20);
        JTextField nameField = new JTextField(20);
        JTextField surnameField = new JTextField(20);
        JTextField birthDateField = new JTextField(10);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Surname:"));
        panel.add(surnameField);
        panel.add(new JLabel("Birth Date (YYYY-MM-DD):"));
        panel.add(birthDateField);
        
        int result = JOptionPane.showConfirmDialog(mainFrame, panel, "Register New User", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (getController().registerUser(
                    emailField.getText(),
                    passwordField.getText(),
                    nameField.getText(),
                    surnameField.getText(),
                    birthDateField.getText())) {
                showMessage("User registered successfully!");
            } else {
                showError("Registration failed!");
            }
        }
    }
    private void showCreateGameDialog() {
        JTextField titleField = new JTextField(20);
        JTextField priceField = new JTextField(10);
        JTextArea descriptionArea = new JTextArea(5, 20);
        JTextArea requirementsArea = new JTextArea(5, 20);
        JTextField releaseDateField = new JTextField(10);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descriptionArea));
        panel.add(new JLabel("Requirements:"));
        panel.add(new JScrollPane(requirementsArea));
        panel.add(new JLabel("Release Date (YYYY-MM-DD):"));
        panel.add(releaseDateField);
        
        int result = JOptionPane.showConfirmDialog(mainFrame, panel, "Create New Game", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                double price = Double.parseDouble(priceField.getText());
                if (getController().createGame(currentUser, titleField.getText(), price, 
                        descriptionArea.getText(), requirementsArea.getText(), releaseDateField.getText())) {
                    showMessage("Game created successfully!");
                } else {
                    showError("Failed to create game!");
                }
            } catch (NumberFormatException e) {
                showError("Please enter a valid price");
            }
        }
    }

    private void loadUserInfo() {
        if (userInfoArea != null && currentUser != null) {
            userInfoArea.setText("Name: " + currentUser.getName() + " " + currentUser.getSurname() + 
                                "\nEmail: " + currentUser.getEmail() + 
                                "\nBirth Date: " + currentUser.getBirthDate() + 
                                "\nPermissions: " + getController().getUserTypes(currentUser));
        }
    }
    
    private void loadUserCollection(JTextArea area) {
        if (currentUser != null) {
            List<VideoGames> collection = getController().getUserCollection(currentUser);
            StringBuilder sb = new StringBuilder();
            sb.append("Games Owned: ").append(collection.size()).append("\n");
            for (int i = 0; i < Math.min(3, collection.size()); i++) {
                sb.append("• ").append(collection.get(i).getTitle()).append("\n");
            }
            if (collection.size() > 3) {
                sb.append("... and ").append(collection.size() - 3).append(" more");
            }
            area.setText(sb.toString());
        }
    }
    
    private void loadUserWishlist(JTextArea area) {
        if (currentUser != null) {
            List<VideoGames> wishlist = getController().getUserWishlist(currentUser);
            StringBuilder sb = new StringBuilder();
            sb.append("Wishlist: ").append(wishlist.size()).append(" games\n");
            for (int i = 0; i < Math.min(2, wishlist.size()); i++) {
                sb.append("• ").append(wishlist.get(i).getTitle()).append("\n");
            }
            if (wishlist.size() > 2) {
                sb.append("... and ").append(wishlist.size() - 2).append(" more");
            }
            area.setText(sb.toString());
        }
    }
    
    private void loadUserAchievements(JTextArea area) {
        if (currentUser != null) {
            List<Achievements> achievements = getController().getUserAchievements(currentUser);
            
            if (achievements.isEmpty()) {
                area.setText("No achievements unlocked yet.\nPlay some games to earn achievements!");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Achievements: ").append(achievements.size()).append("\n");
                
                // Show first few achievements
                int maxToShow = Math.min(achievements.size(), 3);
                for (int i = 0; i < maxToShow; i++) {
                    sb.append("• ").append(achievements.get(i).getTitle()).append("\n");
                }
                
                if (achievements.size() > 3) {
                    sb.append("... and ").append(achievements.size() - 3).append(" more");
                }
                
                area.setText(sb.toString());
            }
        }
    }
    
    private void showPublisherOperations() {
        if (currentUser != null && getController().isPublisher(currentUser)) {
            showCreateGameDialog();
        } else {
            showError("Publisher access required!");
        }
    }
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(mainFrame, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(mainFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private Controller getController() {
        if (this.controller.isPresent()) {
            return this.controller.get();
        } else {
            throw new IllegalStateException("Controller not set in view");
        }
    }

    /**
     * @param controller
     */
    public void setController(Controller controller) {
        Objects.requireNonNull(controller, "Set null controller in view");
        this.controller = Optional.of(controller);
        
        // Initialize UI components that need the controller
        initializeGenreComboBox();
    }
    
    private void initializeGenreComboBox() {
        if (genreComboBox != null && getController() != null) {
            loadGenresIntoComboBox(genreComboBox);
        }
    }
    
    /**
     * Show a user in the view.
     * @param user
     */
    public void showUser(Users user) {
        System.out.println("User: " + user.toString());
    }

    /**
     * Show a videogame in the view.
     * @param videogame
     */
    public void showVideoGame(VideoGames videogame) {
        System.out.println("Video Game: " + videogame.toString());
    }

    private void showUserDashboard() {
        // Switch back to user dashboard
        cardLayout.show(cardPanel, "USER_DASHBOARD");
        rightPanel.setBorder(BorderFactory.createTitledBorder("USER"));
        
        // Refresh the user dashboard with latest data
        if (currentUser != null) {
            loadUserInfo();
            loadUserCollection(ownedGamesArea);
            loadUserWishlist(wishlistArea);
            loadUserAchievements(achievementsArea);
        } else {
            showError("No user logged in!");
        }
    }
    
    private void showGameBrowser() {
        // Switch to game browser view
        cardLayout.show(cardPanel, "GAME_BROWSER");
        rightPanel.setBorder(BorderFactory.createTitledBorder("VIDEOGAMES"));
        
        // Show filters panel for this view
        if (filtersPanel != null) {
            filtersPanel.setVisible(true);
        }
        
        // Load games into the list
        loadAllGamesList();
    }
    
    private void showTopGamesBrowser() {
        // Switch to game browser view
        cardLayout.show(cardPanel, "GAME_BROWSER");
        rightPanel.setBorder(BorderFactory.createTitledBorder("TOP GAMES"));
        
        // Hide filters panel for this view
        if (filtersPanel != null) {
            filtersPanel.setVisible(false);
        }
        
        // Load top games into the list
        loadTopGamesList();
    }
    
    private void loadTopGamesList() {
        // Clear existing games
        gameListPanel.removeAll();
        
        // Get top games from controller
        List<VideoGames> topGames = getController().getTopGames(10);
        
        // Add title
        JLabel titleLabel = new JLabel("TOP 10 HIGHEST RATED GAMES");
        titleLabel.setFont(titleLabel.getFont().deriveFont(16f));
        titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        gameListPanel.add(titleLabel);
        gameListPanel.add(javax.swing.Box.createVerticalStrut(10));
        
        for (VideoGames game : topGames) {
            JPanel gamePanel = createGameListItem(game);
            gameListPanel.add(gamePanel);
            gameListPanel.add(javax.swing.Box.createVerticalStrut(5));
        }
        
        gameListPanel.revalidate();
        gameListPanel.repaint();
    }
    
    private void showMostBoughtBrowser() {
        // Switch to game browser view
        cardLayout.show(cardPanel, "GAME_BROWSER");
        rightPanel.setBorder(BorderFactory.createTitledBorder("MOST BOUGHT"));
        
        // Hide filters panel for this view
        if (filtersPanel != null) {
            filtersPanel.setVisible(false);
        }
        
        // Load most bought game into the list
        loadMostBoughtGameList();
    }
    
    private void loadMostBoughtGameList() {
        // Clear existing games
        gameListPanel.removeAll();
        
        // Get most bought game from controller
        Optional<VideoGames> mostBoughtGame = getController().getMostBoughtGame();
        
        // Add title
        JLabel titleLabel = new JLabel("MOST BOUGHT GAME");
        titleLabel.setFont(titleLabel.getFont().deriveFont(16f));
        titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        gameListPanel.add(titleLabel);
        gameListPanel.add(javax.swing.Box.createVerticalStrut(10));
        
        if (mostBoughtGame.isPresent()) {
            VideoGames game = mostBoughtGame.get();
            JPanel gamePanel = createGameListItem(game);
            gameListPanel.add(gamePanel);
        } else {
            JLabel noGameLabel = new JLabel("No games found");
            noGameLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            gameListPanel.add(noGameLabel);
        }
        
        gameListPanel.revalidate();
        gameListPanel.repaint();
    }
    
    private void showLeastRatedBrowser() {
        // Switch to game browser view
        cardLayout.show(cardPanel, "GAME_BROWSER");
        rightPanel.setBorder(BorderFactory.createTitledBorder("LOWEST RATED DEVELOPERS"));
        
        // Hide filters panel for this view
        if (filtersPanel != null) {
            filtersPanel.setVisible(false);
        }
        
        // Load least rated users into the list
        loadLeastRatedUsersList();
    }
    
    private void loadLeastRatedUsersList() {
        // Clear existing content
        gameListPanel.removeAll();
        
        // Get least rated users from controller
        List<db_project.data.LeastRatedUser> leastRatedUsers = getController().getLeastRatedUsers();
        
        // Add title
        JLabel titleLabel = new JLabel("LOWEST RATED DEVELOPERS");
        titleLabel.setFont(titleLabel.getFont().deriveFont(16f));
        titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        gameListPanel.add(titleLabel);
        gameListPanel.add(javax.swing.Box.createVerticalStrut(10));
        
        if (leastRatedUsers.isEmpty()) {
            JLabel noDataLabel = new JLabel("No data available - all publishers/developers have good ratings!");
            noDataLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            gameListPanel.add(noDataLabel);
        } else {
            for (db_project.data.LeastRatedUser user : leastRatedUsers) {
                // Create a panel for each user
                JPanel userPanel = new JPanel();
                userPanel.setLayout(new java.awt.BorderLayout());
                userPanel.setBorder(BorderFactory.createEtchedBorder());
                userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
                
                // User info
                JLabel userLabel = new JLabel(String.format(
                    "<html><b>%s %s</b><br>Role: %s<br>Average Rating: %.2f/5<br>Email: %s</html>",
                    user.getName(), user.getSurname(), user.getRole(), user.getAvgRating(), user.getEmail()
                ));
                
                userPanel.add(userLabel, java.awt.BorderLayout.CENTER);
                userPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                
                gameListPanel.add(userPanel);
                gameListPanel.add(javax.swing.Box.createVerticalStrut(5));
            }
        }
        
        gameListPanel.revalidate();
        gameListPanel.repaint();
    }
    
    private void showGameDetails(VideoGames game) {
        // Create the game detail view according to the image
        gameDetailPanel.removeAll();
        gameDetailPanel.setLayout(new java.awt.BorderLayout());
        
        // Back button
        JButton backToGamesButton = new JButton("Back to Games");
        backToGamesButton.addActionListener(e -> showGameBrowser());
        
        // Game detail content (4 panels as shown in the image)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new java.awt.GridLayout(2, 2, 10, 10));
        
        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createTitledBorder("INFO PANEL"));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        JTextArea infoArea = new JTextArea(5, 20);
        infoArea.setEditable(false);
        infoArea.setText("Title: " + game.getTitle() + "\n" +
                        "Price: $" + game.getPrice() + "\n" +
                        "Rating: " + game.getAverageRating() + "/5\n" +
                        "Release Date: " + game.getReleaseDate());
        infoPanel.add(infoArea);
        
        // Genres & Languages Panel
        JPanel genresPanel = new JPanel();
        genresPanel.setBorder(BorderFactory.createTitledBorder("GENRES & LANGUAGES"));
        genresPanel.setLayout(new BoxLayout(genresPanel, BoxLayout.Y_AXIS));
        
        JTextArea genresArea = new JTextArea(5, 20);
        genresArea.setEditable(false);
        
        // Get real genres and languages
        List<String> genres = getController().getGameGenres(game.getGameID());
        List<String> languages = getController().getGameLanguages(game.getGameID());
        
        StringBuilder genresText = new StringBuilder();
        genresText.append("Genres: ");
        if (genres.isEmpty()) {
            genresText.append("No genres available");
        } else {
            genresText.append(String.join(", ", genres));
        }
        genresText.append("\nLanguages: ");
        if (languages.isEmpty()) {
            genresText.append("No languages available");
        } else {
            genresText.append(String.join(", ", languages));
        }
        
        genresArea.setText(genresText.toString());
        genresPanel.add(genresArea);
        
        // Add admin or developer buttons for genre management
        boolean isAdmin = currentUser != null && currentUser.isAdministrator();
        boolean isDeveloper = currentUser != null && getController().isDeveloperOfGame(currentUser, game.getGameID());
        if (isAdmin || isDeveloper) {
            JPanel genreButtonPanel = new JPanel(new FlowLayout());
            JButton addGenreButton = new JButton("Add Genre");
            JButton removeGenreButton = new JButton("Remove Genre");
            
            addGenreButton.addActionListener(e -> showAddGenreDialog(game));
            removeGenreButton.addActionListener(e -> showRemoveGenreDialog(game));
            
            genreButtonPanel.add(addGenreButton);
            genreButtonPanel.add(removeGenreButton);
            genresPanel.add(genreButtonPanel);
        }
        
        // Achievements Panel
        JPanel achievementsPanel = new JPanel();
        achievementsPanel.setBorder(BorderFactory.createTitledBorder("ACHIEVEMENTS"));
        achievementsPanel.setLayout(new BoxLayout(achievementsPanel, BoxLayout.Y_AXIS));
        
        JTextArea achievementsArea = new JTextArea(3, 20);
        achievementsArea.setEditable(false);
        
        // Get real achievements for this game
        List<Achievements> achievements = getController().getGameAchievements(game.getGameID());
        StringBuilder achievementsText = new StringBuilder();
        if (achievements.isEmpty()) {
            achievementsText.append("No achievements available for this game.");
        } else {
            for (Achievements achievement : achievements) {
                achievementsText.append("• ").append(achievement.getTitle()).append("\n");
            }
        }
        
        achievementsArea.setText(achievementsText.toString());
        achievementsPanel.add(achievementsArea);
        
        // Reviews Panel
        JPanel reviewsPanel = new JPanel();
        reviewsPanel.setBorder(BorderFactory.createTitledBorder("REVIEWS"));
        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));
        
        JTextArea reviewsArea = new JTextArea(3, 20);
        reviewsArea.setEditable(false);
        
        // Get real reviews for this game
        List<Reviews> reviews = getController().getGameReviews(game.getGameID());
        StringBuilder reviewsText = new StringBuilder();
        if (reviews.isEmpty()) {
            reviewsText.append("No reviews available for this game.");
        } else {
            for (Reviews review : reviews) {
                // Convert rating to stars
                String stars = "★".repeat(review.getRating()) + "☆".repeat(5 - review.getRating());
                // Get reviewer name
                String reviewerName = getController().getReviewerName(review.getUserID());
                
                // Truncate long comments for readability
                String comment = review.getComment();
                if (comment.length() > 100) {
                    comment = comment.substring(0, 97) + "...";
                }
                
                reviewsText.append(stars)
                          .append(" by ")
                          .append(reviewerName)
                          .append("\n")
                          .append("\"")
                          .append(comment)
                          .append("\"")
                          .append("\n\n");
            }
        }
        
        reviewsArea.setText(reviewsText.toString());
        JScrollPane reviewsScrollPane = new JScrollPane(reviewsArea);
        reviewsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        reviewsScrollPane.setPreferredSize(new java.awt.Dimension(350, 120));
        reviewsPanel.add(reviewsScrollPane);
        
        // Add action buttons
        JPanel buttonPanel = new JPanel();
        JButton buyButton = new JButton("Buy Game");
        JButton addToWishlistButton = new JButton("Add to Wishlist");
        JButton removeFromWishlistButton = new JButton("Remove from Wishlist");
        JButton addReviewButton = new JButton("Add Review");
        
        // Check if game is in wishlist to enable/disable appropriate buttons
        boolean isInWishlist = getController().isGameInWishlist(currentUser, game.getGameID());
        
        // Check if user already owns the game
        boolean userOwnsGame = getController().userOwnsGame(currentUser, game.getGameID());
        
        // Check if user can add review
        boolean canAddReview = getController().canUserAddReview(currentUser, game.getGameID());
        
        // Enable/disable buttons based on wishlist status
        addToWishlistButton.setEnabled(!isInWishlist);
        removeFromWishlistButton.setEnabled(isInWishlist);
        
        // Enable/disable buy button based on ownership
        buyButton.setEnabled(!userOwnsGame);
        if (userOwnsGame) {
            buyButton.setText("Already Owned");
        }
        
        // Enable/disable add review button
        addReviewButton.setEnabled(canAddReview);
        if (currentUser.isBlocked()) {
            addReviewButton.setText("Blocked User");
        } else if (!userOwnsGame) {
            addReviewButton.setText("Must Own Game");
        } else if (getController().hasUserReviewedGame(currentUser, game.getGameID())) {
            addReviewButton.setText("Already Reviewed");
        }
        
        buyButton.addActionListener(e -> {
            if (!getController().userOwnsGame(currentUser, game.getGameID())) {
                buyGame(game);
            }
        });
        addToWishlistButton.addActionListener(e -> {
            addToWishlist(game);
            showGameDetails(game); // Refresh the view
        });
        removeFromWishlistButton.addActionListener(e -> {
            removeFromWishlist(game);
            showGameDetails(game); // Refresh the view
        });
        addReviewButton.addActionListener(e -> addReview(game));
        
        buttonPanel.add(buyButton);
        buttonPanel.add(addToWishlistButton);
        buttonPanel.add(removeFromWishlistButton);
        buttonPanel.add(addReviewButton);
        
        contentPanel.add(infoPanel);
        contentPanel.add(genresPanel);
        contentPanel.add(achievementsPanel);
        contentPanel.add(reviewsPanel);
        
        gameDetailPanel.add(backToGamesButton, java.awt.BorderLayout.NORTH);
        gameDetailPanel.add(contentPanel, java.awt.BorderLayout.CENTER);
        gameDetailPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        
        // Switch to game detail view
        cardLayout.show(cardPanel, "GAME_DETAIL");
        rightPanel.setBorder(BorderFactory.createTitledBorder("VIDEOGAME DETAILS"));
        
        gameDetailPanel.revalidate();
        gameDetailPanel.repaint();
    }
    
    private void buyGame(VideoGames game) {
        // Always succeed since we already check ownership in the UI
        if (getController().buyGame(currentUser, game.getGameID())) {
            showMessage("Game purchased successfully! Added to your collection.");
            showGameDetails(game); // Refresh the view to update buttons
        } else {
            // This should not happen since we ensure purchase always succeeds
            showError("Purchase failed!");
        }
    }
    
    private void addReview(VideoGames game) {
        // Check if user can add review and show appropriate message
        if (currentUser.isBlocked()) {
            showError("Cannot add review: Your account is blocked.");
            return;
        }
        
        if (!getController().userOwnsGame(currentUser, game.getGameID())) {
            showError("Cannot add review: You must own the game to review it.");
            return;
        }
        
        if (getController().hasUserReviewedGame(currentUser, game.getGameID())) {
            showError("Cannot add review: You have already reviewed this game.");
            return;
        }
        
        JTextField ratingField = new JTextField(10);
        JTextArea commentArea = new JTextArea(5, 20);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Rating (1-5):"));
        panel.add(ratingField);
        panel.add(new JLabel("Comment:"));
        panel.add(new JScrollPane(commentArea));
        
        int result = JOptionPane.showConfirmDialog(mainFrame, panel, "Add Review", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int rating = Integer.parseInt(ratingField.getText());
                if (rating < 1 || rating > 5) {
                    showError("Rating must be between 1 and 5.");
                    return;
                }
                
                if (getController().addReview(currentUser, game.getGameID(), rating, commentArea.getText())) {
                    showMessage("Review added successfully!");
                    showGameDetails(game); // Refresh the view to show the new review
                } else {
                    showError("Failed to add review!");
                }
            } catch (NumberFormatException e) {
                showError("Please enter a valid rating (1-5)");
            }
        }
    }
    
    private void addToWishlist(VideoGames game) {
        // Check if game is already in wishlist

        if (getController().userOwnsGame(currentUser, game.getGameID())) {
            showError("Cannot add '" + game.getTitle() + "' to wishlist: You already own this game!");
            return;
        }

        if (getController().isGameInWishlist(currentUser, game.getGameID())) {
            showError("'" + game.getTitle() + "' is already in your wishlist!");
            return;
        }
        
        if (getController().addToWishlist(currentUser, game.getGameID())) {
            showMessage("'" + game.getTitle() + "' added to your wishlist!");
        } else {
            showError("Failed to add '" + game.getTitle() + "' to wishlist!");
        }
    }
    
    private void removeFromWishlist(VideoGames game) {
        // Check if game is in wishlist
        if (!getController().isGameInWishlist(currentUser, game.getGameID())) {
            showError("'" + game.getTitle() + "' is not in your wishlist!");
            return;
        }
        
        if (getController().removeFromWishlist(currentUser, game.getGameID())) {
            showMessage("'" + game.getTitle() + "' removed from your wishlist!");
            showGameDetails(game);
        } else {
            showError("Failed to remove '" + game.getTitle() + "' from wishlist!");
        }
    }
    
    /**
     * Shows the user's collection of owned games
     */
    private void showUserCollectionView() {
        // Clear and setup the collection panel
        collectionViewPanel.removeAll();
        collectionViewPanel.setLayout(new BorderLayout());
        
        // Back button
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> showUserDashboard());
        collectionViewPanel.add(backButton, BorderLayout.NORTH);
        
        // Title
        JLabel titleLabel = new JLabel("Your Game Collection", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        
        List<VideoGames> collection = getController().getUserCollection(currentUser);
        
        if (collection.isEmpty()) {
            JLabel emptyLabel = new JLabel("No games in your collection yet. Buy some games!", JLabel.CENTER);
            contentPanel.add(emptyLabel, BorderLayout.CENTER);
        } else {
            JPanel gamesPanel = new JPanel();
            gamesPanel.setLayout(new BoxLayout(gamesPanel, BoxLayout.Y_AXIS));
            
            for (VideoGames game : collection) {
                JPanel gamePanel = createGameListItem(game);
                gamesPanel.add(gamePanel);
                gamesPanel.add(Box.createVerticalStrut(5));
            }
            
            JScrollPane scrollPane = new JScrollPane(gamesPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            contentPanel.add(scrollPane, BorderLayout.CENTER);
        }
        
        collectionViewPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Switch to collection view
        cardLayout.show(cardPanel, "USER_COLLECTION");
    }
    
    /**
     * Shows the user's wishlist
     */
    private void showUserWishlistView() {
        // Clear and setup the wishlist panel
        wishlistViewPanel.removeAll();
        wishlistViewPanel.setLayout(new BorderLayout());
        
        // Back button
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> showUserDashboard());
        wishlistViewPanel.add(backButton, BorderLayout.NORTH);
        
        // Title
        JLabel titleLabel = new JLabel("Your Wishlist", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        
        List<VideoGames> wishlist = getController().getUserWishlist(currentUser);
        
        if (wishlist.isEmpty()) {
            JLabel emptyLabel = new JLabel("Your wishlist is empty. Add some games!", JLabel.CENTER);
            contentPanel.add(emptyLabel, BorderLayout.CENTER);
        } else {
            JPanel gamesPanel = new JPanel();
            gamesPanel.setLayout(new BoxLayout(gamesPanel, BoxLayout.Y_AXIS));
            
            for (VideoGames game : wishlist) {
                JPanel gamePanel = createGameListItem(game);
                gamesPanel.add(gamePanel);
                gamesPanel.add(Box.createVerticalStrut(5));
            }
            
            JScrollPane scrollPane = new JScrollPane(gamesPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            contentPanel.add(scrollPane, BorderLayout.CENTER);
        }
        
        wishlistViewPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Switch to wishlist view
        cardLayout.show(cardPanel, "USER_WISHLIST");
    }
    
    /**
     * Shows all achievements
     */
    private void showAchievementsView() {
        // Clear and setup the achievements panel
        achievementsViewPanel.removeAll();
        achievementsViewPanel.setLayout(new BorderLayout());
        
        // Back button
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> showUserDashboard());
        achievementsViewPanel.add(backButton, BorderLayout.NORTH);
        
        // Title
        JLabel titleLabel = new JLabel("Your Achievements", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        
        List<Achievements> achievements = getController().getUserAchievements(currentUser);
        
        if (achievements.isEmpty()) {
            JLabel emptyLabel = new JLabel("You haven't unlocked any achievements yet.", JLabel.CENTER);
            contentPanel.add(emptyLabel, BorderLayout.CENTER);
        } else {
            JPanel achievementsPanel = new JPanel();
            achievementsPanel.setLayout(new BoxLayout(achievementsPanel, BoxLayout.Y_AXIS));
            
            for (Achievements achievement : achievements) {
                JPanel achievementPanel = createAchievementListItem(achievement);
                achievementsPanel.add(achievementPanel);
                achievementsPanel.add(Box.createVerticalStrut(5));
            }
            
            JScrollPane scrollPane = new JScrollPane(achievementsPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            contentPanel.add(scrollPane, BorderLayout.CENTER);
        }
        
        achievementsViewPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Switch to achievements view
        cardLayout.show(cardPanel, "ACHIEVEMENTS");
    }
    
    /**
     * Creates an achievement list item panel
     */
    private JPanel createAchievementListItem(Achievements achievement) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(achievement.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel descLabel = new JLabel(achievement.getDescription());
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(titleLabel);
        infoPanel.add(descLabel);
        
        panel.add(infoPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Creates a game list item panel for collection/wishlist views
     */
    private JPanel createGameListItem(VideoGames game) {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Create a modern card-like appearance with colors
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2), // Steel blue border
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Gradient background effect with a nice blue color
        panel.setBackground(new Color(240, 248, 255)); // Alice blue background
        panel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        // Left side - colored accent
        JPanel colorAccent = new JPanel();
        colorAccent.setBackground(new Color(70, 130, 180)); // Steel blue accent
        colorAccent.setPreferredSize(new Dimension(5, 80));
        panel.add(colorAccent, BorderLayout.WEST);
        
        // Main content area
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        
        // Game title with larger, bold font
        String title = game.getTitle();
        if (title == null) {
            title = "Unknown Title";
        }
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(25, 25, 112)); // Midnight blue
        
        // Price with attractive styling
        JLabel priceLabel;
        String price = game.getPrice();
        if (price == null) {
            priceLabel = new JLabel("N/A");
            priceLabel.setForeground(new Color(128, 128, 128)); // Gray for unknown price
        } else if (price.equals("0.00")) {
            priceLabel = new JLabel("FREE");
            priceLabel.setForeground(new Color(34, 139, 34)); // Forest green for free games
        } else {
            priceLabel = new JLabel("$" + price);
            priceLabel.setForeground(new Color(220, 20, 60)); // Crimson for paid games
        }
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        // Rating if available
        JLabel ratingLabel = new JLabel();
        double rating = game.getAverageRating();
        if (rating > 0.0) {
            ratingLabel.setText(String.format("%.1f", rating) + "/5");
            ratingLabel.setForeground(new Color(255, 140, 0)); // Dark orange for rating
            ratingLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        }
        
        // Game description (truncated)
        String description = game.getDescription();
        if (description == null) {
            description = "No description available";
        } else if (description.length() > 120) {
            description = description.substring(0, 120) + "...";
        }
        JLabel descLabel = new JLabel("<html><div style='width: 350px'>" + description + "</div></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(105, 105, 105)); // Dim gray
        
        // Release date
        String releaseDate = game.getReleaseDate();
        if (releaseDate == null) {
            releaseDate = "Unknown";
        }
        JLabel dateLabel = new JLabel("Released: " + releaseDate);
        dateLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        dateLabel.setForeground(new Color(128, 128, 128)); // Gray
        
        // Top panel for title and price
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(titleLabel, BorderLayout.WEST);
        
        JPanel rightInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightInfoPanel.setOpaque(false);
        if (!ratingLabel.getText().isEmpty()) {
            rightInfoPanel.add(ratingLabel);
        }
        rightInfoPanel.add(priceLabel);
        topPanel.add(rightInfoPanel, BorderLayout.EAST);
        
        // Info panel for description and date
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(descLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(dateLabel);
        
        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(infoPanel, BorderLayout.CENTER);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        // Hover effect
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                panel.setBackground(new Color(230, 240, 250)); // Lighter blue on hover
                panel.repaint();
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                panel.setBackground(new Color(240, 248, 255)); // Original color
                panel.repaint();
            }
            
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                showGameDetails(game);
            }
        });
        
        // Set preferred size for consistency
        panel.setPreferredSize(new Dimension(500, 100));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        return panel;
    }
    
    private void loadAllGamesList() {
        // Clear existing games
        gameListPanel.removeAll();
        
        // Add title
        JLabel titleLabel = new JLabel("ALL GAMES");
        titleLabel.setFont(titleLabel.getFont().deriveFont(16f));
        titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        gameListPanel.add(titleLabel);
        gameListPanel.add(javax.swing.Box.createVerticalStrut(10));
        
        // Get all games from controller
        List<Optional<VideoGames>> games = getController().getAllGames();
        
        for (Optional<VideoGames> gameOpt : games) {
            if (gameOpt.isPresent()) {
                VideoGames game = gameOpt.get();
                JPanel gamePanel = createGameListItem(game);
                gameListPanel.add(gamePanel);
                gameListPanel.add(javax.swing.Box.createVerticalStrut(5));
            }
        }
        
        gameListPanel.revalidate();
        gameListPanel.repaint();
    }
    
    private void showAllUsersBrowser() {
        // Switch to game browser view but use it for users
        cardLayout.show(cardPanel, "GAME_BROWSER");
        rightPanel.setBorder(BorderFactory.createTitledBorder("ALL USERS MANAGEMENT"));
        
        // Load all users into the list
        loadAllUsersList();
    }
    
    private void loadAllUsersList() {
        // Clear existing content
        gameListPanel.removeAll();
        
        // Get all users from controller
        List<Optional<db_project.data.Users>> allUsersOptional = getController().getAllUsers();
        
        // Filter out empty optionals
        List<db_project.data.Users> allUsers = allUsersOptional.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(java.util.stream.Collectors.toList());
        
        // Add title
        JLabel titleLabel = new JLabel("ALL USERS MANAGEMENT");
        titleLabel.setFont(titleLabel.getFont().deriveFont(16f));
        titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        gameListPanel.add(titleLabel);
        gameListPanel.add(javax.swing.Box.createVerticalStrut(10));
        
        if (allUsers.isEmpty()) {
            JLabel noDataLabel = new JLabel("No users found");
            noDataLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            gameListPanel.add(noDataLabel);
        } else {
            for (db_project.data.Users user : allUsers) {
                // Create a panel for each user
                JPanel userPanel = new JPanel();
                userPanel.setLayout(new java.awt.BorderLayout());
                userPanel.setBorder(BorderFactory.createEtchedBorder());
                userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
                
                // User info
                String blockedStatus = user.isBlocked() ? " (BLOCKED)" : " (ACTIVE)";
                JLabel userLabel = new JLabel(String.format(
                    "<html><b>%s %s</b>%s<br>Email: %s<br>Role: %s</html>",
                    user.getName(), user.getSurname(), blockedStatus, user.getEmail(), user.getRole()
                ));
                
                // Create buttons panel
                JPanel buttonsPanel = new JPanel();
                buttonsPanel.setLayout(new java.awt.FlowLayout());
                
                JButton blockButton = new JButton("Block");
                JButton unblockButton = new JButton("Unblock");
                
                // Enable/disable buttons based on user's blocked status
                blockButton.setEnabled(!user.isBlocked());
                unblockButton.setEnabled(user.isBlocked());
                
                // Add action listeners
                blockButton.addActionListener(e -> {
                    getController().blockUser(user.getUserID());
                    loadAllUsersList(); // Refresh the list
                });
                
                unblockButton.addActionListener(e -> {
                    getController().unblockUser(user.getUserID());
                    loadAllUsersList(); // Refresh the list
                });
                
                buttonsPanel.add(blockButton);
                buttonsPanel.add(unblockButton);
                
                userPanel.add(userLabel, java.awt.BorderLayout.CENTER);
                userPanel.add(buttonsPanel, java.awt.BorderLayout.EAST);
                userPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                
                gameListPanel.add(userPanel);
                gameListPanel.add(javax.swing.Box.createVerticalStrut(5));
            }
        }
        
        gameListPanel.revalidate();
        gameListPanel.repaint();
    }
    
    private void showAddGenreDialog(VideoGames game) {
        List<String> allGenres = getController().getAllGenres();
        List<String> currentGenres = getController().getGameGenres(game.getGameID());
        
        // Filter out genres already assigned to the game
        List<String> availableGenres = new ArrayList<>();
        for (String genre : allGenres) {
            if (!currentGenres.contains(genre)) {
                availableGenres.add(genre);
            }
        }
        
        if (availableGenres.isEmpty()) {
            showError("All available genres are already assigned to this game.");
            return;
        }
        
        String[] genreArray = availableGenres.toArray(new String[0]);
        String selectedGenre = (String) JOptionPane.showInputDialog(
            mainFrame,
            "Select a genre to add to " + game.getTitle() + ":",
            "Add Genre",
            JOptionPane.QUESTION_MESSAGE,
            null,
            genreArray,
            genreArray[0]
        );
        
        if (selectedGenre != null) {
            if (getController().addGenreToGame(currentUser, game.getGameID(), selectedGenre)) {
                showMessage("Genre '" + selectedGenre + "' added successfully!");
                showGameDetails(game); // Refresh the view
            } else {
                showError("Failed to add genre to game!");
            }
        }
    }
    
    private void showRemoveGenreDialog(VideoGames game) {
        List<String> currentGenres = getController().getGameGenres(game.getGameID());
        
        if (currentGenres.isEmpty()) {
            showError("This game has no genres to remove.");
            return;
        }
        
        String[] genreArray = currentGenres.toArray(new String[0]);
        String selectedGenre = (String) JOptionPane.showInputDialog(
            mainFrame,
            "Select a genre to remove from " + game.getTitle() + ":",
            "Remove Genre",
            JOptionPane.QUESTION_MESSAGE,
            null,
            genreArray,
            genreArray[0]
        );
        
        if (selectedGenre != null) {
            if (getController().removeGenreFromGame(currentUser, game.getGameID(), selectedGenre)) {
                showMessage("Genre '" + selectedGenre + "' removed successfully!");
                showGameDetails(game); // Refresh the view
            } else {
                showError("Failed to remove genre from game!");
            }
        }
    }
    
    private void loadGenresIntoComboBox(JComboBox<String> comboBox) {
        try {
            List<String> genres = getController().getAllGenres();
            for (String genre : genres) {
                comboBox.addItem(genre);
            }
        } catch (Exception e) {
            System.err.println("Error loading genres: " + e.getMessage());
        }
    }
    
    private void showFilteredGames(String filterType) {
        // Switch to game browser view
        cardLayout.show(cardPanel, "GAME_BROWSER");
        
        // Show filters panel for this view
        if (filtersPanel != null) {
            filtersPanel.setVisible(true);
        }
        
        // Set appropriate title based on filter
        String title = getFilterTitle(filterType);
        rightPanel.setBorder(BorderFactory.createTitledBorder(title));
        
        // Load filtered games
        loadFilteredGamesList(filterType);
    }
    
    private String getFilterTitle(String filterType) {
        switch (filterType) {
            case "newest": return "NEWEST GAMES";
            case "oldest": return "OLDEST GAMES";
            case "highest_rated": return "HIGHEST RATED GAMES";
            case "lowest_rated": return "LOWEST RATED GAMES";
            case "most_expensive": return "MOST EXPENSIVE GAMES";
            case "cheapest": return "CHEAPEST GAMES";
            case "most_sold": return "MOST SOLD GAMES";
            case "all": return "ALL VIDEOGAMES";
            default:
                if (filterType.startsWith("genre:")) {
                    String genre = filterType.substring(6);
                    return genre.toUpperCase() + " GAMES";
                }
                return "VIDEOGAMES";
        }
    }
    
    private void loadFilteredGamesList(String filterType) {
        // Clear existing games
        gameListPanel.removeAll();
        
        List<Optional<db_project.data.VideoGames>> games = null;
        
        try {
            // Get games based on filter type
            switch (filterType) {
                case "newest":
                    games = getController().getAllNewestGames();
                    break;
                case "oldest":
                    games = getController().getAllOldestGames();
                    break;
                case "highest_rated":
                    games = getController().getAllHighestRatedGames();
                    break;
                case "lowest_rated":
                    games = getController().getAllLowestRatedGames();
                    break;
                case "most_expensive":
                    games = getController().getAllMostExpensiveGames();
                    break;
                case "cheapest":
                    games = getController().getAllCheapestGames();
                    break;
                case "most_sold":
                    games = getController().getAllMostSoldGames();
                    break;
                case "all":
                    games = getController().getAllGames();
                    break;
                default:
                    if (filterType.startsWith("genre:")) {
                        String genre = filterType.substring(6);
                        games = getController().getAllGamesByGenre(genre);
                    } else {
                        games = getController().getAllGames();
                    }
                    break;
            }
            
            // Add title
            JLabel titleLabel = new JLabel(getFilterTitle(filterType));
            titleLabel.setFont(titleLabel.getFont().deriveFont(16f));
            titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            gameListPanel.add(titleLabel);
            gameListPanel.add(javax.swing.Box.createVerticalStrut(10));
            
            // Display games
            if (games == null || games.isEmpty()) {
                JLabel noGamesLabel = new JLabel("No games found");
                noGamesLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                gameListPanel.add(noGamesLabel);
            } else {
                for (Optional<db_project.data.VideoGames> gameOpt : games) {
                                       if (gameOpt.isPresent()) {
                        db_project.data.VideoGames game = gameOpt.get();
                        JPanel gamePanel = createGameListItem(game);
                        gameListPanel.add(gamePanel);
                        gameListPanel.add(javax.swing.Box.createVerticalStrut(5));
                    }
                }
            }
            
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Error loading games: " + e.getMessage());
            errorLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            gameListPanel.add(errorLabel);
            System.err.println("Error loading filtered games: " + e.getMessage());
        }
        
        gameListPanel.revalidate();
        gameListPanel.repaint();
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        button.setPreferredSize(new Dimension(200, 35));
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(color.darker());
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private JButton createCompactFilterButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        button.setPreferredSize(new Dimension(70, 25)); // Smaller size
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(3, 6, 3, 6));
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(70, 130, 180).darker());
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
        
        return button;
    }
}
