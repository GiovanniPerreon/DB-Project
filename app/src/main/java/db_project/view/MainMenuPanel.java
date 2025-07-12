package db_project.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import db_project.data.Users;

/**
 * Main menu panel that contains navigation and content areas
 */
public class MainMenuPanel extends JPanel {
    
    private final ViewManager viewManager;
    private JPanel rightPanel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    
    // Navigation buttons
    private JButton publisherOpsButton;
    private JButton adminOpsButton;
    
    // Content panels
    private UserDashboardPanel userDashboardPanel;
    private GameBrowserPanel gameBrowserPanel;
    private JPanel gameDetailPanel;
    private JPanel collectionViewPanel;
    private JPanel wishlistViewPanel;
    private JPanel achievementsViewPanel;
    private JPanel allUsersBrowserPanel;
    private JPanel userListPanel;
    
    // Filter components
    private JComboBox<String> genreComboBox;
    
    public MainMenuPanel(ViewManager viewManager) {
        this.viewManager = viewManager;
        setupMainMenu();
    }
    
    private void setupMainMenu() {
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        setLayout(new BorderLayout());
        setBackground(UIStyler.LIGHT_BACKGROUND);
        
        // Left panel - Navigation
        createNavigationPanel();
        
        // Right panel - Content area with card layout
        createContentPanel();
    }
    
    private void createNavigationPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(280, 700));
        leftPanel.setBackground(UIStyler.ALICE_BLUE);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // USER button (main dashboard view)
        JButton userButton = new JButton("USER DASHBOARD");
        userButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        userButton.setPreferredSize(new Dimension(200, 45));
        userButton.setFont(UIStyler.LABEL_FONT);
        userButton.setBackground(UIStyler.STEEL_BLUE);
        userButton.setForeground(Color.WHITE);
        userButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        userButton.setFocusPainted(false);
        userButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        // VIDEOGAMES section
        JPanel videogamesPanel = new JPanel();
        videogamesPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIStyler.FOREST_GREEN, 2), "VIDEOGAMES",
            TitledBorder.CENTER, TitledBorder.TOP,
            UIStyler.SMALL_FONT, UIStyler.FOREST_GREEN
        ));
        videogamesPanel.setLayout(new BoxLayout(videogamesPanel, BoxLayout.Y_AXIS));
        videogamesPanel.setPreferredSize(new Dimension(200, 200));
        videogamesPanel.setBackground(UIStyler.HONEYDEW);
        
        JButton viewAllGamesButton = UIStyler.createStyledButton("Browse Games", UIStyler.FOREST_GREEN);
        JButton topGamesButton = UIStyler.createStyledButton("Top Games", UIStyler.FOREST_GREEN);
        JButton mostBoughtButton = UIStyler.createStyledButton("Most Bought", UIStyler.FOREST_GREEN);
        JButton leastRatedButton = UIStyler.createStyledButton("Lowest Rated Developers", UIStyler.FOREST_GREEN);
        
        videogamesPanel.add(viewAllGamesButton);
        videogamesPanel.add(Box.createVerticalStrut(8));
        videogamesPanel.add(topGamesButton);
        videogamesPanel.add(Box.createVerticalStrut(8));
        videogamesPanel.add(mostBoughtButton);
        videogamesPanel.add(Box.createVerticalStrut(8));
        videogamesPanel.add(leastRatedButton);
        
        // OTHER section
        JPanel otherPanel = new JPanel();
        otherPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIStyler.CRIMSON, 2), "OTHER",
            TitledBorder.CENTER, TitledBorder.TOP,
            UIStyler.SMALL_FONT, UIStyler.CRIMSON
        ));
        otherPanel.setLayout(new BoxLayout(otherPanel, BoxLayout.Y_AXIS));
        otherPanel.setPreferredSize(new Dimension(200, 140));
        otherPanel.setBackground(UIStyler.LAVENDER_BLUSH);
        
        publisherOpsButton = UIStyler.createStyledButton("Publish Game", UIStyler.CRIMSON);
        adminOpsButton = UIStyler.createStyledButton("View All Users", UIStyler.CRIMSON);
        
        otherPanel.add(publisherOpsButton);
        otherPanel.add(Box.createVerticalStrut(8));
        otherPanel.add(adminOpsButton);
        
        // Add sections to left panel
        leftPanel.add(userButton);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(videogamesPanel);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(otherPanel);
        leftPanel.add(Box.createVerticalGlue());
        
        // Add action listeners
        userButton.addActionListener(e -> showUserDashboard());
        viewAllGamesButton.addActionListener(e -> showGameBrowser());
        topGamesButton.addActionListener(e -> showTopGamesBrowser());
        mostBoughtButton.addActionListener(e -> showMostBoughtBrowser());
        leastRatedButton.addActionListener(e -> showLeastRatedBrowser());
        publisherOpsButton.addActionListener(e -> showPublisherOperations());
        adminOpsButton.addActionListener(e -> showAllUsersBrowser());
        
        add(leftPanel, BorderLayout.WEST);
    }
    
    private void createContentPanel() {
        rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 2), "USER",
            TitledBorder.CENTER, TitledBorder.TOP,
            UIStyler.LABEL_FONT, UIStyler.STEEL_BLUE
        ));
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(600, 600));
        rightPanel.setBackground(Color.WHITE);
        
        // Create card layout for switching between views
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Color.WHITE);
        
        // Create content panels
        userDashboardPanel = new UserDashboardPanel(viewManager);
        gameBrowserPanel = new GameBrowserPanel(viewManager);
        
        // TODO: Create other panels (GameDetailPanel, CollectionPanel, etc.)
        gameDetailPanel = new JPanel();
        gameDetailPanel.setLayout(new BorderLayout());
        
        collectionViewPanel = new JPanel();
        collectionViewPanel.setLayout(new BorderLayout());
        
        wishlistViewPanel = new JPanel();
        wishlistViewPanel.setLayout(new BorderLayout());
        
        achievementsViewPanel = new JPanel();
        achievementsViewPanel.setLayout(new BorderLayout());
        
        // All users browser panel (for admin)
        allUsersBrowserPanel = new JPanel();
        allUsersBrowserPanel.setLayout(new BorderLayout());
        
        // Add panels to card layout
        cardPanel.add(userDashboardPanel, "USER_DASHBOARD");
        cardPanel.add(gameBrowserPanel, "GAME_BROWSER");
        cardPanel.add(gameDetailPanel, "GAME_DETAIL");
        cardPanel.add(collectionViewPanel, "USER_COLLECTION");
        cardPanel.add(wishlistViewPanel, "USER_WISHLIST");
        cardPanel.add(achievementsViewPanel, "ACHIEVEMENTS");
        cardPanel.add(allUsersBrowserPanel, "ALL_USERS");
        
        rightPanel.add(cardPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.CENTER);
    }
    
    // ===== NAVIGATION METHODS =====
    
    public void showUserDashboard() {
        cardLayout.show(cardPanel, "USER_DASHBOARD");
        rightPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 2), "USER",
            TitledBorder.CENTER, TitledBorder.TOP,
            UIStyler.LABEL_FONT, UIStyler.STEEL_BLUE
        ));
        userDashboardPanel.refresh();
        viewManager.refreshFrame();
    }
    
    public void showGameBrowser() {
        cardLayout.show(cardPanel, "GAME_BROWSER");
        rightPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 2), "VIDEOGAMES",
            TitledBorder.CENTER, TitledBorder.TOP,
            UIStyler.LABEL_FONT, UIStyler.STEEL_BLUE
        ));
        gameBrowserPanel.loadAllGames();
        viewManager.refreshFrame();
    }
    
    private void showTopGamesBrowser() {
        cardLayout.show(cardPanel, "GAME_BROWSER");
        rightPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 2), "TOP GAMES",
            TitledBorder.CENTER, TitledBorder.TOP,
            UIStyler.LABEL_FONT, UIStyler.STEEL_BLUE
        ));
        gameBrowserPanel.loadTopGames();
        viewManager.refreshFrame();
    }
    
    private void showMostBoughtBrowser() {
        cardLayout.show(cardPanel, "GAME_BROWSER");
        rightPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 2), "MOST BOUGHT",
            TitledBorder.CENTER, TitledBorder.TOP,
            UIStyler.LABEL_FONT, UIStyler.STEEL_BLUE
        ));
        gameBrowserPanel.loadMostBoughtGame();
        viewManager.refreshFrame();
    }
    
    private void showLeastRatedBrowser() {
        cardLayout.show(cardPanel, "GAME_BROWSER");
        rightPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 2), "LOWEST RATED DEVELOPERS",
            TitledBorder.CENTER, TitledBorder.TOP,
            UIStyler.LABEL_FONT, UIStyler.STEEL_BLUE
        ));
        gameBrowserPanel.loadLeastRatedUsers();
        viewManager.refreshFrame();
    }
    
    private void showPublisherOperations() {
        Users currentUser = viewManager.getCurrentUser();
        if (currentUser != null && viewManager.getController().isPublisher(currentUser)) {
            viewManager.getDialogManager().showCreateGameDialog();
        } else {
            viewManager.showError("Publisher access required!");
        }
    }
    
    // ===== COLLECTION, WISHLIST, AND ACHIEVEMENTS VIEWS =====
    
    public void showCollectionView() {
        cardLayout.show(cardPanel, "USER_COLLECTION");
        rightPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 2), "YOUR COLLECTION",
            TitledBorder.CENTER, TitledBorder.TOP,
            UIStyler.LABEL_FONT, UIStyler.STEEL_BLUE
        ));
        loadCollectionContent();
        viewManager.refreshFrame();
    }
    
    public void showWishlistView() {
        cardLayout.show(cardPanel, "USER_WISHLIST");
        rightPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 2), "YOUR WISHLIST",
            TitledBorder.CENTER, TitledBorder.TOP,
            UIStyler.LABEL_FONT, UIStyler.STEEL_BLUE
        ));
        loadWishlistContent();
        viewManager.refreshFrame();
    }
    
    public void showAchievementsView() {
        cardLayout.show(cardPanel, "ACHIEVEMENTS");
        rightPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 2), "YOUR ACHIEVEMENTS",
            TitledBorder.CENTER, TitledBorder.TOP,
            UIStyler.LABEL_FONT, UIStyler.STEEL_BLUE
        ));
        loadAchievementsContent();
        viewManager.refreshFrame();
    }
    
    public void showAllUsersBrowser() {
        Users currentUser = viewManager.getCurrentUser();
        if (currentUser != null && viewManager.getController().isAdmin(currentUser)) {
            // Create users browser panel if it doesn't exist
            if (allUsersBrowserPanel == null) {
                createAllUsersBrowserPanel();
            }
            
            // Switch to all users browser view
            cardLayout.show(cardPanel, "ALL_USERS");
            rightPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 2), "ALL USERS MANAGEMENT",
                javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP,
                UIStyler.LABEL_FONT, UIStyler.STEEL_BLUE
            ));
            
            // Load all users
            loadAllUsersList();
        } else {
            viewManager.showError("Admin access required!");
        }
    }
    
    // ===== INITIALIZATION METHODS =====
    
    public void initializeWithController() {
        // Initialize genre combo box and other controller-dependent components
        if (genreComboBox != null) {
            loadGenresIntoComboBox();
        }
    }
    
    private void loadGenresIntoComboBox() {
        // TODO: Load genres from controller
        // This would be moved from the original View's initializeGenreComboBox method
    }
    
    public void loadUserData(Users user) {
        if (user != null) {
            // Enable/disable admin and publisher buttons based on user type
            if (publisherOpsButton != null) {
                publisherOpsButton.setEnabled(user.isPublisher());
            }
            if (adminOpsButton != null) {
                adminOpsButton.setVisible(user.isAdministrator());
            }
            
            // Load user dashboard with user data
            userDashboardPanel.loadUserData(user);
        }
    }
    
    // ===== CONTENT LOADING METHODS =====
    
    private void loadCollectionContent() {
        collectionViewPanel.removeAll();
        
        // Back button
        JButton backButton = UIStyler.createStyledButton("Back to Dashboard", UIStyler.STEEL_BLUE);
        backButton.addActionListener(e -> showUserDashboard());
        collectionViewPanel.add(backButton, BorderLayout.NORTH);
        
        // Title
        JLabel titleLabel = new JLabel("Your Game Collection", JLabel.CENTER);
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        
        Users currentUser = viewManager.getCurrentUser();
        if (currentUser != null) {
            List<db_project.data.VideoGames> collection = viewManager.getController().getUserCollection(currentUser);
            
            if (collection.isEmpty()) {
                JLabel emptyLabel = new JLabel("No games in your collection yet. Buy some games!", JLabel.CENTER);
                contentPanel.add(emptyLabel, BorderLayout.CENTER);
            } else {
                JPanel gamesPanel = new JPanel();
                gamesPanel.setLayout(new BoxLayout(gamesPanel, BoxLayout.Y_AXIS));
                
                for (db_project.data.VideoGames game : collection) {
                    JPanel gamePanel = createGameListItem(game);
                    gamesPanel.add(gamePanel);
                    gamesPanel.add(Box.createVerticalStrut(5));
                }
                
                JScrollPane scrollPane = new JScrollPane(gamesPanel);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                contentPanel.add(scrollPane, BorderLayout.CENTER);
            }
        }
        
        collectionViewPanel.add(contentPanel, BorderLayout.CENTER);
        collectionViewPanel.revalidate();
        collectionViewPanel.repaint();
    }
    
    private void loadWishlistContent() {
        wishlistViewPanel.removeAll();
        
        // Back button
        JButton backButton = UIStyler.createStyledButton("Back to Dashboard", UIStyler.STEEL_BLUE);
        backButton.addActionListener(e -> showUserDashboard());
        wishlistViewPanel.add(backButton, BorderLayout.NORTH);
        
        // Title
        JLabel titleLabel = new JLabel("Your Wishlist", JLabel.CENTER);
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        
        Users currentUser = viewManager.getCurrentUser();
        if (currentUser != null) {
            List<db_project.data.VideoGames> wishlist = viewManager.getController().getUserWishlist(currentUser);
            
            if (wishlist.isEmpty()) {
                JLabel emptyLabel = new JLabel("Your wishlist is empty. Add some games!", JLabel.CENTER);
                contentPanel.add(emptyLabel, BorderLayout.CENTER);
            } else {
                JPanel gamesPanel = new JPanel();
                gamesPanel.setLayout(new BoxLayout(gamesPanel, BoxLayout.Y_AXIS));
                
                for (db_project.data.VideoGames game : wishlist) {
                    JPanel gamePanel = createGameListItem(game);
                    gamesPanel.add(gamePanel);
                    gamesPanel.add(Box.createVerticalStrut(5));
                }
                
                JScrollPane scrollPane = new JScrollPane(gamesPanel);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                contentPanel.add(scrollPane, BorderLayout.CENTER);
            }
        }
        
        wishlistViewPanel.add(contentPanel, BorderLayout.CENTER);
        wishlistViewPanel.revalidate();
        wishlistViewPanel.repaint();
    }
    
    private void loadAchievementsContent() {
        achievementsViewPanel.removeAll();
        
        // Back button
        JButton backButton = UIStyler.createStyledButton("Back to Dashboard", UIStyler.STEEL_BLUE);
        backButton.addActionListener(e -> showUserDashboard());
        achievementsViewPanel.add(backButton, BorderLayout.NORTH);
        
        // Title
        JLabel titleLabel = new JLabel("Your Achievements", JLabel.CENTER);
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        
        Users currentUser = viewManager.getCurrentUser();
        if (currentUser != null) {
            List<db_project.data.Achievements> achievements = viewManager.getController().getUserAchievements(currentUser);
            
            if (achievements.isEmpty()) {
                JLabel emptyLabel = new JLabel("No achievements unlocked yet. Play some games!", JLabel.CENTER);
                contentPanel.add(emptyLabel, BorderLayout.CENTER);
            } else {
                JPanel achievementsPanel = new JPanel();
                achievementsPanel.setLayout(new BoxLayout(achievementsPanel, BoxLayout.Y_AXIS));
                
                for (db_project.data.Achievements achievement : achievements) {
                    JPanel achievementPanel = createAchievementListItem(achievement);
                    achievementsPanel.add(achievementPanel);
                    achievementsPanel.add(Box.createVerticalStrut(5));
                }
                
                JScrollPane scrollPane = new JScrollPane(achievementsPanel);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                contentPanel.add(scrollPane, BorderLayout.CENTER);
            }
        }
        
        achievementsViewPanel.add(contentPanel, BorderLayout.CENTER);
        achievementsViewPanel.revalidate();
        achievementsViewPanel.repaint();
    }
    
    private JPanel createGameListItem(db_project.data.VideoGames game) {
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
        titleLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
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
        priceLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        
        // Rating if available
        JLabel ratingLabel = new JLabel();
        double rating = game.getAverageRating();
        if (rating > 0.0) {
            ratingLabel.setText(String.format("%.1f", rating) + "/5");
            ratingLabel.setForeground(new Color(255, 140, 0)); // Dark orange for rating
            ratingLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        }
        
        // Game description (truncated)
        String description = game.getDescription();
        if (description == null) {
            description = "No description available";
        } else if (description.length() > 120) {
            description = description.substring(0, 120) + "...";
        }
        JLabel descLabel = new JLabel("<html><div style='width: 350px'>" + description + "</div></html>");
        descLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        descLabel.setForeground(new Color(105, 105, 105)); // Dim gray
        
        // Top info panel (title and price)
        JPanel topInfoPanel = new JPanel(new BorderLayout());
        topInfoPanel.setOpaque(false);
        topInfoPanel.add(titleLabel, BorderLayout.WEST);
        topInfoPanel.add(priceLabel, BorderLayout.EAST);
        
        // Middle info panel (rating)
        JPanel middleInfoPanel = new JPanel(new BorderLayout());
        middleInfoPanel.setOpaque(false);
        if (rating > 0.0) {
            middleInfoPanel.add(ratingLabel, BorderLayout.WEST);
        }
        
        // Combine all info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.add(topInfoPanel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(middleInfoPanel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(descLabel);
        
        contentPanel.add(infoPanel, BorderLayout.CENTER);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        // Add mouse listener for clicks and hover effects
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
        
        // Set fixed size to prevent huge buttons with few games
        panel.setPreferredSize(new Dimension(500, 100));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        return panel;
    }
    
    private JPanel createAchievementListItem(db_project.data.Achievements achievement) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(achievement.getTitle());
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        
        JLabel descLabel = new JLabel(achievement.getDescription());
        descLabel.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(descLabel);
        infoPanel.setOpaque(false);
        
        panel.add(infoPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Shows detailed view of a game with action buttons
     */
    private void showGameDetails(db_project.data.VideoGames game) {
        // This should show a game detail view with all the action buttons
        // For now, delegate to ViewManager to handle game details
        viewManager.showGameDetails(game);
    }
    
    /**
     * Shows the game details view with the 4-panel layout
     */
    public void showGameDetailsView(db_project.data.VideoGames game) {
        // Clear and setup the game detail panel
        gameDetailPanel.removeAll();
        gameDetailPanel.setLayout(new BorderLayout());
        
        // Back button
        JButton backToGamesButton = UIStyler.createStyledButton("Back to Games", UIStyler.STEEL_BLUE);
        backToGamesButton.addActionListener(e -> showGameBrowser());
        gameDetailPanel.add(backToGamesButton, BorderLayout.NORTH);
        
        // Game detail content (4 panels as shown in the original)
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
        List<String> genres = viewManager.getController().getGameGenres(game.getGameID());
        List<String> languages = viewManager.getController().getGameLanguages(game.getGameID());
        
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
        
        // Add genre management buttons for admin or developer
        Users user = viewManager.getCurrentUser();
        boolean isAdmin = user != null && user.isAdministrator();
        boolean isDeveloper = user != null && viewManager.getController().isDeveloperOfGame(user, game.getGameID());
        if (isAdmin || isDeveloper) {
            JPanel genreButtonPanel = new JPanel(new FlowLayout());
            JButton addGenreButton = UIStyler.createStyledButton("Add Genre", UIStyler.FOREST_GREEN);
            JButton removeGenreButton = UIStyler.createStyledButton("Remove Genre", UIStyler.CRIMSON);
            
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
        List<db_project.data.Achievements> achievements = viewManager.getController().getGameAchievements(game.getGameID());
        StringBuilder achievementsText = new StringBuilder();
        if (achievements.isEmpty()) {
            achievementsText.append("No achievements available for this game.");
        } else {
            for (db_project.data.Achievements achievement : achievements) {
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
        List<db_project.data.Reviews> reviews = viewManager.getController().getGameReviews(game.getGameID());
        StringBuilder reviewsText = new StringBuilder();
        if (reviews.isEmpty()) {
            reviewsText.append("No reviews available for this game.");
        } else {
            for (db_project.data.Reviews review : reviews) {
                // Convert rating to stars
                String stars = "★".repeat(review.getRating()) + "☆".repeat(5 - review.getRating());
                // Get reviewer name
                String reviewerName = viewManager.getController().getReviewerName(review.getUserID());
                
                // Show full comments without truncation
                String comment = review.getComment();
                
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
        reviewsScrollPane.setPreferredSize(new Dimension(350, 120));
        reviewsPanel.add(reviewsScrollPane);
        
        // Add panels to content
        contentPanel.add(infoPanel);
        contentPanel.add(genresPanel);
        contentPanel.add(achievementsPanel);
        contentPanel.add(reviewsPanel);
        
        gameDetailPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Action buttons panel
        JPanel actionButtonPanel = new JPanel(new FlowLayout());
        Users currentUser = viewManager.getCurrentUser();
        
        if (currentUser != null) {
            // Buy button if user doesn't own the game
            if (!viewManager.getController().userOwnsGame(currentUser, game.getGameID())) {
                JButton buyButton = UIStyler.createStyledButton("Buy Game", UIStyler.FOREST_GREEN);
                buyButton.addActionListener(e -> {
                    if (viewManager.getController().buyGame(currentUser, game.getGameID())) {
                        viewManager.showMessage("Game purchased successfully!");
                        showGameDetailsView(game); // Refresh the view
                    } else {
                        viewManager.showError("Failed to purchase game!");
                    }
                });
                actionButtonPanel.add(buyButton);
            }
            
            // Wishlist buttons
            if (viewManager.getController().canAddToWishlist(currentUser, game.getGameID())) {
                JButton addToWishlistButton = UIStyler.createStyledButton("Add to Wishlist", UIStyler.MEDIUM_SLATE_BLUE);
                addToWishlistButton.addActionListener(e -> {
                    if (viewManager.getController().addToWishlist(currentUser, game.getGameID())) {
                        viewManager.showMessage("Added to wishlist!");
                        showGameDetailsView(game); // Refresh the view
                    } else {
                        viewManager.showError("Failed to add to wishlist!");
                    }
                });
                actionButtonPanel.add(addToWishlistButton);
            } else if (viewManager.getController().isGameInWishlist(currentUser, game.getGameID())) {
                JButton removeFromWishlistButton = UIStyler.createStyledButton("Remove from Wishlist", UIStyler.CRIMSON);
                removeFromWishlistButton.addActionListener(e -> {
                    if (viewManager.getController().removeFromWishlist(currentUser, game.getGameID())) {
                        viewManager.showMessage("Removed from wishlist!");
                        showGameDetailsView(game); // Refresh the view
                    } else {
                        viewManager.showError("Failed to remove from wishlist!");
                    }
                });
                actionButtonPanel.add(removeFromWishlistButton);
            }
            
            // Review button if user owns the game and can add review
            if (viewManager.getController().canUserAddReview(currentUser, game.getGameID())) {
                JButton addReviewButton = UIStyler.createStyledButton("Add Review", UIStyler.DARK_ORANGE);
                addReviewButton.addActionListener(e -> {
                    UIStyler.ReviewDialogResult result = UIStyler.showStyledReviewDialog(
                        viewManager.getMainFrame(), 
                        game.getTitle()
                    );
                    
                    if (result.submitted) {
                        if (viewManager.getController().addReview(currentUser, game.getGameID(), result.rating, result.comment)) {
                            viewManager.showMessage("Review added successfully!");
                            showGameDetailsView(game); // Refresh to show the new review
                        } else {
                            viewManager.showError("Failed to add review!");
                        }
                    }
                });
                actionButtonPanel.add(addReviewButton);
            }
        }
        
        gameDetailPanel.add(actionButtonPanel, BorderLayout.SOUTH);
        
        // Switch to game detail view
        cardLayout.show(cardPanel, "GAME_DETAIL");
        rightPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 2), "VIDEOGAME DETAILS",
            TitledBorder.CENTER, TitledBorder.TOP,
            UIStyler.LABEL_FONT, UIStyler.STEEL_BLUE
        ));
        
        gameDetailPanel.revalidate();
        gameDetailPanel.repaint();
        viewManager.refreshFrame();
    }
    
    /**
     * Shows dialog to add genre to a game
     */
    private void showAddGenreDialog(db_project.data.VideoGames game) {
        List<String> allGenres = viewManager.getController().getAllGenres();
        List<String> currentGenres = viewManager.getController().getGameGenres(game.getGameID());
        
        // Filter out genres already assigned to the game
        List<String> availableGenres = new java.util.ArrayList<>();
        for (String genre : allGenres) {
            if (!currentGenres.contains(genre)) {
                availableGenres.add(genre);
            }
        }
        
        if (availableGenres.isEmpty()) {
            viewManager.showError("All available genres are already assigned to this game.");
            return;
        }
        
        String[] genreArray = availableGenres.toArray(new String[0]);
        String selectedGenre = UIStyler.showStyledSelection(
            viewManager.getMainFrame(),
            "Select a genre to add to " + game.getTitle() + ":",
            "Add Genre",
            genreArray,
            genreArray[0]
        );
        
        if (selectedGenre != null) {
            if (viewManager.getController().addGenreToGame(viewManager.getCurrentUser(), game.getGameID(), selectedGenre)) {
                viewManager.showMessage("Genre '" + selectedGenre + "' added successfully!");
                showGameDetailsView(game); // Refresh the view
            } else {
                viewManager.showError("Failed to add genre to game!");
            }
        }
    }
    
    /**
     * Shows dialog to remove genre from a game
     */
    private void showRemoveGenreDialog(db_project.data.VideoGames game) {
        List<String> currentGenres = viewManager.getController().getGameGenres(game.getGameID());
        
        if (currentGenres.isEmpty()) {
            viewManager.showError("This game has no genres to remove.");
            return;
        }
        
        String[] genreArray = currentGenres.toArray(new String[0]);
        String selectedGenre = UIStyler.showStyledSelection(
            viewManager.getMainFrame(),
            "Select a genre to remove from " + game.getTitle() + ":",
            "Remove Genre",
            genreArray,
            genreArray[0]
        );
        
        if (selectedGenre != null) {
            if (viewManager.getController().removeGenreFromGame(viewManager.getCurrentUser(), game.getGameID(), selectedGenre)) {
                viewManager.showMessage("Genre '" + selectedGenre + "' removed successfully!");
                showGameDetailsView(game); // Refresh the view
            } else {
                viewManager.showError("Failed to remove genre from game!");
            }
        }
    }
    
    /**
     * Creates the all users browser panel for administrators
     */
    private void createAllUsersBrowserPanel() {
        allUsersBrowserPanel.removeAll();
        
        // Back button
        JButton backButton = UIStyler.createStyledButton("Back to Dashboard", UIStyler.STEEL_BLUE);
        backButton.addActionListener(e -> showUserDashboard());
        
        // User list panel with scroll
        userListPanel = new JPanel();
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
        JScrollPane userListScrollPane = new JScrollPane(userListPanel);
        userListScrollPane.setPreferredSize(new Dimension(600, 500));
        
        allUsersBrowserPanel.add(backButton, BorderLayout.NORTH);
        allUsersBrowserPanel.add(userListScrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Loads all users into the list for admin management
     */
    private void loadAllUsersList() {
        if (userListPanel == null) {
            createAllUsersBrowserPanel();
        }
        
        // Clear existing content
        userListPanel.removeAll();
        
        // Get all users from controller
        List<java.util.Optional<db_project.data.Users>> allUsersOptional = viewManager.getController().getAllUsers();
        
        // Filter out empty optionals
        List<db_project.data.Users> allUsers = allUsersOptional.stream()
            .filter(java.util.Optional::isPresent)
            .map(java.util.Optional::get)
            .collect(java.util.stream.Collectors.toList());
        
        // Add title
        JLabel titleLabel = new JLabel("ALL USERS MANAGEMENT");
        titleLabel.setFont(UIStyler.TITLE_FONT);
        titleLabel.setForeground(UIStyler.STEEL_BLUE);
        titleLabel.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
        userListPanel.add(titleLabel);
        userListPanel.add(Box.createVerticalStrut(10));
        
        if (allUsers.isEmpty()) {
            JLabel noDataLabel = new JLabel("No users found");
            noDataLabel.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
            userListPanel.add(noDataLabel);
        } else {
            for (db_project.data.Users user : allUsers) {
                JPanel userPanel = createUserListItem(user);
                userListPanel.add(userPanel);
                userListPanel.add(Box.createVerticalStrut(5));
            }
        }
        
        userListPanel.revalidate();
        userListPanel.repaint();
    }
    
    /**
     * Creates a user list item panel with block/unblock buttons
     */
    private JPanel createUserListItem(db_project.data.Users user) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setBackground(UIStyler.ALICE_BLUE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        panel.setPreferredSize(new Dimension(500, 120));
        
        // User info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        
        // User name and status
        String blockedStatus = user.isBlocked() ? " (BLOCKED)" : " (ACTIVE)";
        Color statusColor = user.isBlocked() ? UIStyler.CRIMSON : new Color(34, 139, 34); // Green for active
        
        JLabel nameLabel = new JLabel(user.getName() + " " + user.getSurname() + blockedStatus);
        nameLabel.setFont(UIStyler.LABEL_FONT.deriveFont(java.awt.Font.BOLD, 16f));
        nameLabel.setForeground(statusColor);
        
        JLabel emailLabel = new JLabel("Email: " + user.getEmail());
        emailLabel.setFont(UIStyler.TEXT_FONT);
        emailLabel.setForeground(UIStyler.MIDNIGHT_BLUE);
        
        JLabel roleLabel = new JLabel("Role: " + user.getRole());
        roleLabel.setFont(UIStyler.TEXT_FONT);
        roleLabel.setForeground(UIStyler.MIDNIGHT_BLUE);
        
        JLabel idLabel = new JLabel("ID: " + user.getUserID());
        idLabel.setFont(UIStyler.SMALL_FONT.deriveFont(java.awt.Font.ITALIC));
        idLabel.setForeground(Color.GRAY);
        
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(emailLabel);
        infoPanel.add(roleLabel);
        infoPanel.add(idLabel);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.setOpaque(false);
        
        JButton blockButton = UIStyler.createStyledButton("Block", UIStyler.CRIMSON);
        JButton unblockButton = UIStyler.createStyledButton("Unblock", new Color(34, 139, 34)); // Green
        
        // Enable/disable buttons based on user's blocked status
        blockButton.setEnabled(!user.isBlocked());
        unblockButton.setEnabled(user.isBlocked());
        
        // Don't allow blocking the current admin user
        Users currentUser = viewManager.getCurrentUser();
        if (currentUser != null && currentUser.getUserID() == user.getUserID()) {
            blockButton.setEnabled(false);
            blockButton.setText("Cannot Block Self");
        }
        
        // Add action listeners
        blockButton.addActionListener(e -> {
            if (viewManager.getController().blockUser(user.getUserID())) {
                viewManager.showMessage("User " + user.getName() + " " + user.getSurname() + " has been blocked.");
                loadAllUsersList(); // Refresh the list
            } else {
                viewManager.showError("Failed to block user!");
            }
        });
        
        unblockButton.addActionListener(e -> {
            if (viewManager.getController().unblockUser(user.getUserID())) {
                viewManager.showMessage("User " + user.getName() + " " + user.getSurname() + " has been unblocked.");
                loadAllUsersList(); // Refresh the list
            } else {
                viewManager.showError("Failed to unblock user!");
            }
        });
        
        buttonsPanel.add(blockButton);
        buttonsPanel.add(unblockButton);
        
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    /**
     * Gets the game browser panel for initialization
     */
    public GameBrowserPanel getGameBrowserPanel() {
        return gameBrowserPanel;
    }
}
