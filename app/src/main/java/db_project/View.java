package db_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Box;
import javax.swing.border.TitledBorder;

import db_project.data.Users;
import db_project.data.VideoGames;
import db_project.data.Transactions;
import db_project.data.Reviews;
import db_project.data.Wishlists;
import db_project.data.Achievements;

public final class View {

    private Optional<Controller> controller;
    private final JFrame mainFrame;
    private JPanel mainPanel;
    private JTextArea outputArea;
    private Users currentUser;
    
    // Admin and publisher buttons
    private JButton publisherOpsButton;
    private JButton adminOpsButton;

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
        frame.setMinimumSize(new Dimension(800, 600));
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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header
        JLabel titleLabel = new JLabel("SteamDB - Video Game Store");
        titleLabel.setFont(titleLabel.getFont().deriveFont(24f));
        titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        // User login section
        setupLoginSection();

        // Main menu (initially hidden)
        setupMainMenu();

        // Output area
        setupOutputArea();

        mainFrame.add(mainPanel);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private void setupLoginSection() {
        JPanel loginPanel = new JPanel();
        loginPanel.setBorder(BorderFactory.createTitledBorder("Login"));
        
        JTextField emailField = new JTextField(20);
        JTextField passwordField = new JTextField(20);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register New User");
        
        loginPanel.add(new JLabel("Email:"));
        loginPanel.add(emailField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(registerButton);
        
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
        menuPanel.setBorder(BorderFactory.createTitledBorder("User Dashboard"));
        menuPanel.setLayout(new java.awt.BorderLayout());
        menuPanel.setVisible(false);
        
        // Left panel - Navigation (Single column of buttons)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(220, 500));
        
        // USER button (main dashboard view)
        JButton userButton = new JButton("USER");
        userButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        userButton.setPreferredSize(new Dimension(200, 40));
        
        // VIDEOGAMES section
        JPanel videogamesPanel = new JPanel();
        videogamesPanel.setBorder(BorderFactory.createTitledBorder("VIDEOGAMES"));
        videogamesPanel.setLayout(new BoxLayout(videogamesPanel, BoxLayout.Y_AXIS));
        videogamesPanel.setPreferredSize(new Dimension(200, 180));
        
        JButton viewAllGamesButton = new JButton("Browse Games");
        JButton topGamesButton = new JButton("Top Games");
        JButton mostBoughtButton = new JButton("Most Bought");
        JButton leastRatedButton = new JButton("Least Rated");
        
        // Make buttons bigger
        viewAllGamesButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        topGamesButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        mostBoughtButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        leastRatedButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        
        videogamesPanel.add(viewAllGamesButton);
        videogamesPanel.add(javax.swing.Box.createVerticalStrut(5));
        videogamesPanel.add(topGamesButton);
        videogamesPanel.add(javax.swing.Box.createVerticalStrut(5));
        videogamesPanel.add(mostBoughtButton);
        videogamesPanel.add(javax.swing.Box.createVerticalStrut(5));
        videogamesPanel.add(leastRatedButton);
        
        // OTHER section
        JPanel otherPanel = new JPanel();
        otherPanel.setBorder(BorderFactory.createTitledBorder("OTHER"));
        otherPanel.setLayout(new BoxLayout(otherPanel, BoxLayout.Y_AXIS));
        otherPanel.setPreferredSize(new Dimension(200, 120));
        
        publisherOpsButton = new JButton("Publisher Ops");
        adminOpsButton = new JButton("Admin Ops");
        
        // Make buttons bigger
        publisherOpsButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        adminOpsButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        
        otherPanel.add(publisherOpsButton);
        otherPanel.add(javax.swing.Box.createVerticalStrut(5));
        otherPanel.add(adminOpsButton);
        
        // Add spacing between sections
        leftPanel.add(userButton);
        leftPanel.add(javax.swing.Box.createVerticalStrut(10));
        leftPanel.add(videogamesPanel);
        leftPanel.add(javax.swing.Box.createVerticalStrut(10));
        leftPanel.add(otherPanel);
        leftPanel.add(javax.swing.Box.createVerticalGlue());
        
        // Right panel - User Information / Game Browser
        JPanel rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createTitledBorder("USER"));
        rightPanel.setLayout(new java.awt.BorderLayout());
        rightPanel.setPreferredSize(new Dimension(400, 400));
        
        // Create card layout for switching between views
        java.awt.CardLayout cardLayout = new java.awt.CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        
        // User Dashboard Panel (original 4-panel layout)
        JPanel userDashboardPanel = new JPanel();
        userDashboardPanel.setLayout(new java.awt.GridLayout(2, 2, 10, 10));
        
        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createTitledBorder("INFO PANEL"));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        JLabel userInfoLabel = new JLabel("User Information");
        JTextArea userInfoArea = new JTextArea(5, 20);
        userInfoArea.setEditable(false);
        userInfoArea.setOpaque(false);
        infoPanel.add(userInfoLabel);
        infoPanel.add(userInfoArea);
        
        // Game Owned Panel
        JPanel gameOwnedPanel = new JPanel();
        gameOwnedPanel.setBorder(BorderFactory.createTitledBorder("GAME OWNED"));
        gameOwnedPanel.setLayout(new BoxLayout(gameOwnedPanel, BoxLayout.Y_AXIS));
        
        JButton viewCollectionButton = new JButton("View Collection");
        JTextArea ownedGamesArea = new JTextArea(5, 20);
        ownedGamesArea.setEditable(false);
        ownedGamesArea.setOpaque(false);
        gameOwnedPanel.add(viewCollectionButton);
        gameOwnedPanel.add(ownedGamesArea);
        
        // Wishlist Panel
        JPanel wishlistPanel = new JPanel();
        wishlistPanel.setBorder(BorderFactory.createTitledBorder("WISHLIST"));
        wishlistPanel.setLayout(new BoxLayout(wishlistPanel, BoxLayout.Y_AXIS));
        
        JButton viewWishlistButton = new JButton("View Wishlist");
        JTextArea wishlistArea = new JTextArea(3, 20);
        wishlistArea.setEditable(false);
        wishlistArea.setOpaque(false);
        wishlistPanel.add(viewWishlistButton);
        wishlistPanel.add(wishlistArea);
        
        // Achievements Panel
        JPanel achievementsPanel = new JPanel();
        achievementsPanel.setBorder(BorderFactory.createTitledBorder("ACHIEVEMENTS GOT"));
        achievementsPanel.setLayout(new BoxLayout(achievementsPanel, BoxLayout.Y_AXIS));
        
        JButton viewAchievementsButton = new JButton("View Achievements");
        JTextArea achievementsArea = new JTextArea(3, 20);
        achievementsArea.setEditable(false);
        achievementsArea.setOpaque(false);
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
        gameListScrollPane.setPreferredSize(new Dimension(380, 350));
        
        // Back button
        JButton backToUserButton = new JButton("Back to User Dashboard");
        
        gameBrowserPanel.add(backToUserButton, java.awt.BorderLayout.NORTH);
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
        
        // Store references
        this.rightPanel = rightPanel;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.gameListPanel = gameListPanel;
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
        adminOpsButton.addActionListener(e -> showAdminOperations());
        viewCollectionButton.addActionListener(e -> showUserCollectionView());
        viewWishlistButton.addActionListener(e -> showUserWishlistView());
        viewAchievementsButton.addActionListener(e -> showAchievementsView());
        backToUserButton.addActionListener(e -> showUserDashboard());
        
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

    private void setupOutputArea() {
        outputArea = new JTextArea(15, 70);
        outputArea.setEditable(false);
        outputArea.setFont(outputArea.getFont().deriveFont(12f));
        
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output"));
        
        mainPanel.add(scrollPane);
    }

    private void login(String email, String password) {
        if (getController().loginUser(email, password)) {
            currentUser = getController().getCurrentUser();
            showMainMenu();
        } else {
            showError("Invalid email or password!");
        }
    }

    private void showMainMenu() {
        // Hide login panel and show main menu panel
        for (java.awt.Component comp : mainPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                if (panel.getBorder() != null && panel.getBorder() instanceof TitledBorder) {
                    TitledBorder titledBorder = (TitledBorder) panel.getBorder();
                    String borderTitle = titledBorder.getTitle();
                    
                    if ("Login".equals(borderTitle)) {
                        panel.setVisible(false);
                    } else if ("User Dashboard".equals(borderTitle)) {
                        panel.setVisible(true);
                    }
                }
            }
        }
        
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
                adminOpsButton.setEnabled(currentUser.isAdministrator());
            }
            
            appendOutput("Welcome to your dashboard, " + currentUser.getName() + "!");
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

    private void showBuyGameDialog() {
        String gameIdStr = JOptionPane.showInputDialog(mainFrame, "Enter Game ID to buy:");
        if (gameIdStr != null) {
            try {
                int gameId = Integer.parseInt(gameIdStr);
                if (getController().buyGame(currentUser, gameId)) {
                    showMessage("Game purchased successfully!");
                } else {
                    showError("Purchase failed!");
                }
            } catch (NumberFormatException e) {
                showError("Please enter a valid Game ID");
            }
        }
    }

    private void showAddToWishlistDialog() {
        // Get all available games
        List<Optional<VideoGames>> allGames = getController().getAllGames();
        
        // Create a list of game titles
        List<String> gameTitles = new ArrayList<>();
        Map<String, Integer> titleToIdMap = new HashMap<>();
        
        for (Optional<VideoGames> gameOpt : allGames) {
            if (gameOpt.isPresent()) {
                VideoGames game = gameOpt.get();
                String displayText = game.getTitle() + " - $" + game.getPrice();
                gameTitles.add(displayText);
                titleToIdMap.put(displayText, game.getGameID());
            }
        }
        
        if (gameTitles.isEmpty()) {
            showError("No games available to add to wishlist");
            return;
        }
        
        // Create dropdown selection
        String[] gameArray = gameTitles.toArray(new String[0]);
        String selectedGame = (String) JOptionPane.showInputDialog(
            mainFrame,
            "Select a game to add to your wishlist:",
            "Add to Wishlist",
            JOptionPane.QUESTION_MESSAGE,
            null,
            gameArray,
            gameArray[0]
        );
        
        if (selectedGame != null) {
            int gameId = titleToIdMap.get(selectedGame);
            if (getController().addToWishlist(currentUser, gameId)) {
                showMessage("Game added to wishlist!");
                // Refresh wishlist display if currently showing user dashboard
                if (rightPanel.getBorder() != null && 
                    rightPanel.getBorder().toString().contains("USER")) {
                    loadUserWishlist(wishlistArea);
                }
            } else {
                showError("Failed to add to wishlist! Game may already be in your wishlist.");
            }
        }
    }

    private void showAddReviewDialog() {
        JTextField gameIdField = new JTextField(10);
        JTextField ratingField = new JTextField(10);
        JTextArea commentArea = new JTextArea(5, 20);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Game ID:"));
        panel.add(gameIdField);
        panel.add(new JLabel("Rating (1-10):"));
        panel.add(ratingField);
        panel.add(new JLabel("Comment:"));
        panel.add(new JScrollPane(commentArea));
        
        int result = JOptionPane.showConfirmDialog(mainFrame, panel, "Add Review", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int gameId = Integer.parseInt(gameIdField.getText());
                int rating = Integer.parseInt(ratingField.getText());
                if (getController().addReview(currentUser, gameId, rating, commentArea.getText())) {
                    showMessage("Review added successfully!");
                } else {
                    showError("Failed to add review!");
                }
            } catch (NumberFormatException e) {
                showError("Please enter valid numbers for Game ID and Rating");
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

    private void showBlockUserDialog() {
        String userIdStr = JOptionPane.showInputDialog(mainFrame, "Enter User ID to block:");
        if (userIdStr != null) {
            try {
                int userId = Integer.parseInt(userIdStr);
                if (getController().blockUser(userId)) {
                    showMessage("User blocked successfully!");
                } else {
                    showError("Failed to block user!");
                }
            } catch (NumberFormatException e) {
                showError("Please enter a valid User ID");
            }
        }
    }

    private void showUnblockUserDialog() {
        String userIdStr = JOptionPane.showInputDialog(mainFrame, "Enter User ID to unblock:");
        if (userIdStr != null) {
            try {
                int userId = Integer.parseInt(userIdStr);
                if (getController().unblockUser(userId)) {
                    showMessage("User unblocked successfully!");
                } else {
                    showError("Failed to unblock user!");
                }
            } catch (NumberFormatException e) {
                showError("Please enter a valid User ID");
            }
        }
    }

    // View methods
    private void viewUserCollection() {
        List<VideoGames> collection = getController().getUserCollection(currentUser);
        appendOutput("=== YOUR COLLECTION ===");
        for (VideoGames game : collection) {
            appendOutput(game.toString());
        }
        appendOutput("========================");
    }

    private void viewUserWishlist() {
        List<VideoGames> wishlist = getController().getUserWishlist(currentUser);
        appendOutput("=== YOUR WISHLIST ===");
        for (VideoGames game : wishlist) {
            appendOutput(game.toString());
        }
        appendOutput("=====================");
    }

    private void viewAllGames() {
        List<Optional<VideoGames>> games = getController().getAllGames();
        appendOutput("=== ALL GAMES ===");
        for (Optional<VideoGames> game : games) {
            if (game.isPresent()) {
                appendOutput(game.get().toString());
            }
        }
        appendOutput("=================");
    }

    private void viewAllUsers() {
        List<Optional<Users>> users = getController().getAllUsers();
        appendOutput("=== ALL USERS ===");
        for (Optional<Users> user : users) {
            if (user.isPresent()) {
                appendOutput(user.get().toString());
            }
        }
        appendOutput("=================");
    }

    private void showTopGames() {
        List<VideoGames> topGames = getController().getTopGames(10);
        appendOutput("=== TOP 10 GAMES ===");
        for (VideoGames game : topGames) {
            appendOutput(game.toString());
        }
        appendOutput("====================");
    }

    private void showLowRatedPublishers() {
        List<Users> publishers = getController().getLowRatedPublishers();
        appendOutput("=== LOW-RATED PUBLISHERS ===");
        for (Users publisher : publishers) {
            appendOutput(publisher.toString());
        }
        appendOutput("============================");
    }

    private void showMostBoughtGame() {
        Optional<VideoGames> game = getController().getMostBoughtGame();
        appendOutput("=== MOST BOUGHT GAME ===");
        if (game.isPresent()) {
            appendOutput(game.get().toString());
        } else {
            appendOutput("No games found");
        }
        appendOutput("========================");
    }

    // Dynamic loading methods for info panels
    private void loadUserInfo() {
        if (userInfoArea != null && currentUser != null) {
            userInfoArea.setText("Name: " + currentUser.getName() + " " + currentUser.getSurname() + 
                                "\nEmail: " + currentUser.getEmail() + 
                                "\nBirth Date: " + currentUser.getBirthDate() + 
                                "\nType: " + (getController().isPublisher(currentUser) ? "Publisher" : 
                                           getController().isAdmin(currentUser) ? "Admin" : "User"));
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
    
    private void showAdminOperations() {
        if (currentUser != null && getController().isAdmin(currentUser)) {
            // Show admin menu
            String[] options = {"Block User", "Unblock User", "View All Users"};
            int choice = JOptionPane.showOptionDialog(mainFrame, 
                "Select Admin Operation", "Admin Operations",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
                null, options, options[0]);
            
            switch (choice) {
                case 0: showBlockUserDialog(); break;
                case 1: showUnblockUserDialog(); break;
                case 2: viewAllUsers(); break;
            }
        } else {
            showError("Admin access required!");
        }
    }

    // Utility methods
    private void appendOutput(String text) {
        SwingUtilities.invokeLater(() -> {
            outputArea.append(text + "\n");
            outputArea.setCaretPosition(outputArea.getDocument().getLength());
        });
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
    }

    /**
     * Show a user in the view.
     * @param user
     */
    public void showUser(Users user) {
        appendOutput("User: " + user.toString());
    }

    /**
     * Show a videogame in the view.
     * @param videogame
     */
    public void showVideoGame(VideoGames videogame) {
        appendOutput("Video Game: " + videogame.toString());
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
            appendOutput("User dashboard refreshed");
        } else {
            showError("No user logged in!");
        }
    }
    
    private void showGameBrowser() {
        // Switch to game browser view
        cardLayout.show(cardPanel, "GAME_BROWSER");
        rightPanel.setBorder(BorderFactory.createTitledBorder("VIDEOGAMES"));
        
        // Load games into the list
        loadAllGamesList();
    }
    
    private void showTopGamesBrowser() {
        // Switch to game browser view
        cardLayout.show(cardPanel, "GAME_BROWSER");
        rightPanel.setBorder(BorderFactory.createTitledBorder("TOP GAMES"));
        
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
        
        int rank = 1;
        for (VideoGames game : topGames) {
            // Create a panel for each game
            JPanel gamePanel = new JPanel();
            gamePanel.setLayout(new java.awt.BorderLayout());
            gamePanel.setBorder(BorderFactory.createEtchedBorder());
            gamePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
            
            // Game info with ranking
            JLabel gameLabel = new JLabel("<html><b>#" + rank + " - " + game.getTitle() + "</b><br>" + 
                                          "Rating: " + game.getAverageRating() + "/10 - Price: $" + game.getPrice() + "</html>");
            
            // Click to view details
            JButton viewDetailsButton = new JButton("View Details");
            viewDetailsButton.addActionListener(e -> showGameDetails(game));
            
            gamePanel.add(gameLabel, java.awt.BorderLayout.CENTER);
            gamePanel.add(viewDetailsButton, java.awt.BorderLayout.EAST);
            
            gameListPanel.add(gamePanel);
            gameListPanel.add(javax.swing.Box.createVerticalStrut(5));
            
            rank++;
        }
        
        gameListPanel.revalidate();
        gameListPanel.repaint();
    }
    
    private void showMostBoughtBrowser() {
        // Switch to game browser view
        cardLayout.show(cardPanel, "GAME_BROWSER");
        rightPanel.setBorder(BorderFactory.createTitledBorder("MOST BOUGHT"));
        
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
            
            // Create a panel for the game
            JPanel gamePanel = new JPanel();
            gamePanel.setLayout(new java.awt.BorderLayout());
            gamePanel.setBorder(BorderFactory.createEtchedBorder());
            gamePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
            
            // Game info
            JLabel gameLabel = new JLabel("<html><b>" + game.getTitle() + "</b><br>" + 
                                          "Price: $" + game.getPrice() + "<br>" +
                                          "Rating: " + game.getAverageRating() + "/10</html>");
            
            // Click to view details
            JButton viewDetailsButton = new JButton("View Details");
            viewDetailsButton.addActionListener(e -> showGameDetails(game));
            
            gamePanel.add(gameLabel, java.awt.BorderLayout.CENTER);
            gamePanel.add(viewDetailsButton, java.awt.BorderLayout.EAST);
            
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
        rightPanel.setBorder(BorderFactory.createTitledBorder("LEAST RATED PUBLISHERS & DEVELOPERS"));
        
        // Load least rated users into the list
        loadLeastRatedUsersList();
    }
    
    private void loadLeastRatedUsersList() {
        // Clear existing content
        gameListPanel.removeAll();
        
        // Get least rated users from controller
        List<db_project.data.LeastRatedUser> leastRatedUsers = getController().getLeastRatedUsers();
        
        // Add title
        JLabel titleLabel = new JLabel("LEAST RATED PUBLISHERS & DEVELOPERS");
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
                    "<html><b>%s %s</b><br>Role: %s<br>Average Rating: %.2f/10<br>Email: %s</html>",
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
                        "Rating: " + game.getAverageRating() + "/10\n" +
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
        
        // Add admin buttons for genre management
        if (currentUser != null && currentUser.isAdministrator()) {
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
                String stars = "★".repeat(review.getRating()) + "☆".repeat(10 - review.getRating());
                reviewsText.append(stars).append(" ").append(review.getComment()).append("\n");
            }
        }
        
        reviewsArea.setText(reviewsText.toString());
        reviewsPanel.add(reviewsArea);
        
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
        panel.add(new JLabel("Rating (1-10):"));
        panel.add(ratingField);
        panel.add(new JLabel("Comment:"));
        panel.add(new JScrollPane(commentArea));
        
        int result = JOptionPane.showConfirmDialog(mainFrame, panel, "Add Review", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int rating = Integer.parseInt(ratingField.getText());
                if (rating < 1 || rating > 10) {
                    showError("Rating must be between 1 and 10.");
                    return;
                }
                
                if (getController().addReview(currentUser, game.getGameID(), rating, commentArea.getText())) {
                    showMessage("Review added successfully!");
                    showGameDetails(game); // Refresh the view to show the new review
                } else {
                    showError("Failed to add review!");
                }
            } catch (NumberFormatException e) {
                showError("Please enter a valid rating (1-10)");
            }
        }
    }
    
    private void addToWishlist(VideoGames game) {
        // Check if game is already in wishlist
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
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setBackground(Color.WHITE);
        panel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        // Game title
        JLabel titleLabel = new JLabel(game.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Game price
        JLabel priceLabel = new JLabel("$" + game.getPrice());
        priceLabel.setFont(new Font("Arial", Font.BOLD, 12));
        priceLabel.setForeground(new Color(0, 120, 0));
        
        // Game description (truncated)
        String description = game.getDescription();
        if (description.length() > 100) {
            description = description.substring(0, 100) + "...";
        }
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        
        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(titleLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(descLabel);
        
        panel.add(infoPanel, BorderLayout.CENTER);
        
        // Click listener to show game details
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                showGameDetails(game);
            }
        });
        
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
                
                // Create a panel for each game
                JPanel gamePanel = new JPanel();
                gamePanel.setLayout(new java.awt.BorderLayout());
                gamePanel.setBorder(BorderFactory.createEtchedBorder());
                gamePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
                
                // Game info
                JLabel gameLabel = new JLabel("<html><b>" + game.getTitle() + "</b><br>" + 
                                              "Price: $" + game.getPrice() + "</html>");
                
                // Click to view details
                JButton viewDetailsButton = new JButton("View Details");
                viewDetailsButton.addActionListener(e -> showGameDetails(game));
                
                gamePanel.add(gameLabel, java.awt.BorderLayout.CENTER);
                gamePanel.add(viewDetailsButton, java.awt.BorderLayout.EAST);
                
                gameListPanel.add(gamePanel);
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
}
