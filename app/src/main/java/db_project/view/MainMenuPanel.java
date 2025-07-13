package db_project.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import db_project.data.Achievements;
import db_project.data.Reviews;
import db_project.data.Users;
import db_project.data.VideoGames;
import db_project.Controller;

/**
 * Main menu panel that contains navigation and content areas
 */
public class MainMenuPanel extends JPanel {
    
    private final ViewManager viewManager;
    private JPanel rightPanel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JButton transactionsButton;
    private JButton publisherOpsButton;
    private JButton adminOpsButton;
    private UserDashboardPanel userDashboardPanel;
    private GameBrowserPanel gameBrowserPanel;
    private JPanel gameDetailPanel;
    private JPanel collectionViewPanel;
    private JPanel wishlistViewPanel;
    private JPanel achievementsViewPanel;
    private JPanel transactionsViewPanel;
    private JPanel allUsersBrowserPanel;
    private JPanel userListPanel;
    /**
     * Constructor for MainMenuPanel
     * @param viewManager the main view manager instance
     */
    public MainMenuPanel(ViewManager viewManager) {
        this.viewManager = viewManager;
        setupMainMenu();
    }
    /**
     * Sets up the main menu UI
     */
    private void setupMainMenu() {
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        setLayout(new BorderLayout());
        setBackground(UIStyler.LIGHT_BACKGROUND);
        createNavigationPanel();
        createContentPanel();
    }
    /**
     * Creates the left navigation panel with buttons and sections
     */
    private void createNavigationPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(280, 700));
        leftPanel.setBackground(UIStyler.ALICE_BLUE);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        JButton userButton = new JButton("USER DASHBOARD");
        userButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        userButton.setPreferredSize(new Dimension(200, 45));
        userButton.setFont(UIStyler.LABEL_FONT);
        userButton.setBackground(UIStyler.STEEL_BLUE);
        userButton.setForeground(Color.WHITE);
        userButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        userButton.setFocusPainted(false);
        userButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        JPanel otherPanel = new JPanel();
        otherPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIStyler.CRIMSON, 2), "OTHER",
            TitledBorder.CENTER, TitledBorder.TOP,
            UIStyler.SMALL_FONT, UIStyler.CRIMSON
        ));
        otherPanel.setLayout(new BoxLayout(otherPanel, BoxLayout.Y_AXIS));
        otherPanel.setPreferredSize(new Dimension(200, 180));
        otherPanel.setBackground(UIStyler.LAVENDER_BLUSH);
        transactionsButton = UIStyler.createStyledButton("View Transactions", UIStyler.CRIMSON);
        publisherOpsButton = UIStyler.createStyledButton("Publish Game", UIStyler.CRIMSON);
        adminOpsButton = UIStyler.createStyledButton("View All Users", UIStyler.CRIMSON);
        otherPanel.add(transactionsButton);
        otherPanel.add(Box.createVerticalStrut(8));
        otherPanel.add(publisherOpsButton);
        otherPanel.add(Box.createVerticalStrut(8));
        otherPanel.add(adminOpsButton);
        leftPanel.add(userButton);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(videogamesPanel);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(otherPanel);
        leftPanel.add(Box.createVerticalGlue());
        userButton.addActionListener(e -> showUserDashboard());
        viewAllGamesButton.addActionListener(e -> showGameBrowser());
        topGamesButton.addActionListener(e -> showTopGamesBrowser());
        mostBoughtButton.addActionListener(e -> showMostBoughtBrowser());
        leastRatedButton.addActionListener(e -> showLeastRatedBrowser());
        transactionsButton.addActionListener(e -> showTransactionsView());
        publisherOpsButton.addActionListener(e -> showPublisherOperations());
        adminOpsButton.addActionListener(e -> showAllUsersBrowser());
        add(leftPanel, BorderLayout.WEST);
    }
    /**
     * Creates the right content panel with card layout for different views
     */
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
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Color.WHITE);
        userDashboardPanel = new UserDashboardPanel(viewManager);
        gameBrowserPanel = new GameBrowserPanel(viewManager);
        gameDetailPanel = new JPanel();
        gameDetailPanel.setLayout(new BorderLayout());
        collectionViewPanel = new JPanel();
        collectionViewPanel.setLayout(new BorderLayout());
        wishlistViewPanel = new JPanel();
        wishlistViewPanel.setLayout(new BorderLayout());
        achievementsViewPanel = new JPanel();
        achievementsViewPanel.setLayout(new BorderLayout());
        transactionsViewPanel = new JPanel();
        transactionsViewPanel.setLayout(new BorderLayout());
        allUsersBrowserPanel = new JPanel();
        allUsersBrowserPanel.setLayout(new BorderLayout());
        cardPanel.add(userDashboardPanel, "USER_DASHBOARD");
        cardPanel.add(gameBrowserPanel, "GAME_BROWSER");
        cardPanel.add(gameDetailPanel, "GAME_DETAIL");
        cardPanel.add(collectionViewPanel, "USER_COLLECTION");
        cardPanel.add(wishlistViewPanel, "USER_WISHLIST");
        cardPanel.add(achievementsViewPanel, "ACHIEVEMENTS");
        cardPanel.add(transactionsViewPanel, "TRANSACTIONS");
        cardPanel.add(allUsersBrowserPanel, "ALL_USERS");
        rightPanel.add(cardPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.CENTER);
    }
    /**
     * Shows the user dashboard view
     */
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
    /**
     * Shows the game browser view
     */
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
    /**
     * Shows the create game dialog for publishers
     */
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
    /**
     * Shows the most bought games browser
     */
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
    /**
     * Shows the least rated developers browser
     */
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
    /**
     * Shows the all users browser for admin
     */
    private void showPublisherOperations() {
        Users currentUser = viewManager.getCurrentUser();
        if (currentUser != null && (viewManager.getController().isPublisher(currentUser) || viewManager.getController().isAdmin(currentUser))) {
            viewManager.getDialogManager().showCreateGameDialog();
        } else {
            viewManager.showError("Publisher or administrator access required!");
        }
    }
    /**
     * Shows the create game dialog for publishers
     */
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
    /**
     * Shows the create game dialog for publishers
     */
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
    /**
     * Shows the create game dialog for publishers
     */
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
    /**
     * Shows the create game dialog for publishers
     */
    public void showAllUsersBrowser() {
        Users currentUser = viewManager.getCurrentUser();
        if (currentUser != null && viewManager.getController().isAdmin(currentUser)) {
            if (allUsersBrowserPanel == null) {
                createAllUsersBrowserPanel();
            }
            cardLayout.show(cardPanel, "ALL_USERS");
            rightPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 2), "ALL USERS MANAGEMENT",
                javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP,
                UIStyler.LABEL_FONT, UIStyler.STEEL_BLUE
            ));
            loadAllUsersList();
        } else {
            viewManager.showError("Admin access required!");
        }
    }
    public void loadUserData(Users user) {
        if (user != null) {
            if (publisherOpsButton != null) {
                publisherOpsButton.setEnabled(user.isPublisher() || user.isAdministrator());
            }
            if (adminOpsButton != null) {
                adminOpsButton.setVisible(user.isAdministrator());
            }
            userDashboardPanel.loadUserData(user);
        }
    }
    /**
     * Loads the user's game collection content
     */
    private void loadCollectionContent() {
        collectionViewPanel.removeAll();
        JButton backButton = UIStyler.createStyledButton("Back to Dashboard", UIStyler.STEEL_BLUE);
        backButton.addActionListener(e -> showUserDashboard());
        collectionViewPanel.add(backButton, BorderLayout.NORTH);
        JLabel titleLabel = new JLabel("Your Game Collection", JLabel.CENTER);
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
    /**
     * Loads the user's wishlist content
     */
    private void loadWishlistContent() {
        wishlistViewPanel.removeAll();
        JButton backButton = UIStyler.createStyledButton("Back to Dashboard", UIStyler.STEEL_BLUE);
        backButton.addActionListener(e -> showUserDashboard());
        wishlistViewPanel.add(backButton, BorderLayout.NORTH);
        JLabel titleLabel = new JLabel("Your Wishlist", JLabel.CENTER);
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        Users currentUser = viewManager.getCurrentUser();
        if (currentUser != null) {
            List<VideoGames> wishlist = viewManager.getController().getUserWishlist(currentUser);
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
    /**
     * Loads the user's achievements content
     */
    private void loadAchievementsContent() {
        achievementsViewPanel.removeAll();
        JButton backButton = UIStyler.createStyledButton("Back to Dashboard", UIStyler.STEEL_BLUE);
        backButton.addActionListener(e -> showUserDashboard());
        achievementsViewPanel.add(backButton, BorderLayout.NORTH);
        JLabel titleLabel = new JLabel("Your Achievements", JLabel.CENTER);
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        Users currentUser = viewManager.getCurrentUser();
        if (currentUser != null) {
            List<Achievements> achievements = viewManager.getController().getUserAchievements(currentUser);
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
    /**
     * Creates the all users browser panel for admin
     */
    private JPanel createGameListItem(db_project.data.VideoGames game) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setBackground(new Color(240, 248, 255));
        panel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JPanel colorAccent = new JPanel();
        colorAccent.setBackground(new Color(70, 130, 180));
        colorAccent.setPreferredSize(new Dimension(5, 80));
        panel.add(colorAccent, BorderLayout.WEST);
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        String title = game.getTitle();
        if (title == null) {
            title = "Unknown Title";
        }
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(25, 25, 112));
        JLabel priceLabel;
        String price = game.getPrice();
        if (price == null) {
            priceLabel = new JLabel("N/A");
            priceLabel.setForeground(new Color(128, 128, 128));
        } else if (price.equals("0.00")) {
            priceLabel = new JLabel("FREE");
            priceLabel.setForeground(new Color(34, 139, 34));
        } else {
            priceLabel = new JLabel("$" + price);
            priceLabel.setForeground(new Color(220, 20, 60));
        }
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel ratingLabel = new JLabel();
        double rating = game.getAverageRating();
        if (rating > 0.0) {
            ratingLabel.setText(String.format("%.1f", rating) + "/5");
            ratingLabel.setForeground(new Color(255, 140, 0));
            ratingLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        }
        String description = game.getDescription();
        if (description == null) {
            description = "No description available";
        } else if (description.length() > 120) {
            description = description.substring(0, 120) + "...";
        }
        JLabel descLabel = new JLabel("<html><div style='width: 350px'>" + description + "</div></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(105, 105, 105));
        JPanel topInfoPanel = new JPanel(new BorderLayout());
        topInfoPanel.setOpaque(false);
        topInfoPanel.add(titleLabel, BorderLayout.WEST);
        topInfoPanel.add(priceLabel, BorderLayout.EAST);
        JPanel middleInfoPanel = new JPanel(new BorderLayout());
        middleInfoPanel.setOpaque(false);
        if (rating > 0.0) {
            middleInfoPanel.add(ratingLabel, BorderLayout.WEST);
        }
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
        panel.setPreferredSize(new Dimension(500, 100));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        return panel;
    }
    /**
     * Creates the all users browser panel for admin
     */
    private JPanel createAchievementListItem(Achievements achievement) {
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
    private void showGameDetails(VideoGames game) {
        viewManager.showGameDetails(game);
    }
    /**
     * Shows the game details view with the 4-panel layout
     */
    public void showGameDetailsView(VideoGames game) {
        gameDetailPanel.removeAll();
        gameDetailPanel.setLayout(new BorderLayout());
        JButton backToGamesButton = UIStyler.createStyledButton("Back to Games", UIStyler.STEEL_BLUE);
        backToGamesButton.addActionListener(e -> showGameBrowser());
        gameDetailPanel.add(backToGamesButton, BorderLayout.NORTH);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new java.awt.GridLayout(2, 2, 10, 10));
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createTitledBorder("INFO PANEL"));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        JTextArea infoArea = new JTextArea(5, 20);
        infoArea.setEditable(false);
        
        // Get publisher and developer information
        String publisherName = viewManager.getController().getGamePublisherName(game.getGameID());
        List<String> developerNames = viewManager.getController().getGameDeveloperNames(game.getGameID());
        String developersText = String.join(", ", developerNames);
        
        // Calculate price with discount
        double originalPrice = Double.parseDouble(game.getPrice());
        double discountPercent = game.getDiscount() != null ? Double.parseDouble(game.getDiscount()) : 0.0;
        String priceText;
        if (discountPercent > 0) {
            double discountedPrice = originalPrice * (1.0 - discountPercent / 100.0);
            priceText = String.format("Price: $%.2f (Original: $%.2f, Discount: %.0f%%)", 
                                    discountedPrice, originalPrice, discountPercent);
        } else {
            priceText = "Price: $" + game.getPrice();
        }
        
        // Create info text with all basic information
        StringBuilder infoText = new StringBuilder();
        infoText.append("Title: ").append(game.getTitle()).append("\n");
        infoText.append("Publisher: ").append(publisherName).append("\n");
        infoText.append("Developer(s): ").append(developersText).append("\n");
        infoText.append(priceText).append("\n");
        infoText.append("Rating: ").append(game.getAverageRating()).append("/5\n");
        infoText.append("Release Date: ").append(game.getReleaseDate()).append("\n\n");
        
        // Add description
        String description = game.getDescription();
        if (description != null && !description.trim().isEmpty()) {
            infoText.append("Description:\n").append(description).append("\n\n");
        }
        
        // Add requirements
        String requirements = game.getRequirements();
        if (requirements != null && !requirements.trim().isEmpty()) {
            infoText.append("System Requirements:\n").append(requirements);
        }
        
        infoArea.setText(infoText.toString());
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        
        // Make info area scrollable
        JScrollPane infoScrollPane = new JScrollPane(infoArea);
        infoScrollPane.setPreferredSize(new Dimension(300, 200));
        infoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        infoScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        infoPanel.add(infoScrollPane);
        
        // Add discount button for publisher or administrator
        Users currentUser = viewManager.getCurrentUser();
        if (currentUser != null && (currentUser.getUserID() == game.getPublisherID() || currentUser.isAdministrator())) {
            JButton changeDiscountButton = UIStyler.createStyledButton("Change Discount", UIStyler.DARK_ORANGE);
            changeDiscountButton.addActionListener(e -> showChangeDiscountDialog(game));
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(changeDiscountButton);
        }
        JPanel genresPanel = new JPanel();
        genresPanel.setBorder(BorderFactory.createTitledBorder("GENRES & LANGUAGES"));
        genresPanel.setLayout(new BoxLayout(genresPanel, BoxLayout.Y_AXIS));
        JTextArea genresArea = new JTextArea(5, 20);
        genresArea.setEditable(false);
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
        JPanel achievementsPanel = new JPanel();
        achievementsPanel.setBorder(BorderFactory.createTitledBorder("ACHIEVEMENTS"));
        achievementsPanel.setLayout(new BoxLayout(achievementsPanel, BoxLayout.Y_AXIS));
        JTextArea achievementsArea = new JTextArea(3, 20);
        achievementsArea.setEditable(false);
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
        JPanel reviewsPanel = new JPanel();
        reviewsPanel.setBorder(BorderFactory.createTitledBorder("REVIEWS"));
        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));
        JTextArea reviewsArea = new JTextArea(3, 20);
        reviewsArea.setEditable(false);
        List<Reviews> reviews = viewManager.getController().getGameReviews(game.getGameID());
        StringBuilder reviewsText = new StringBuilder();
        if (reviews.isEmpty()) {
            reviewsText.append("No reviews available for this game.");
        } else {
            for (Reviews review : reviews) {
                String stars = "★".repeat(review.getRating()) + "☆".repeat(5 - review.getRating());
                String reviewerName = viewManager.getController().getReviewerName(review.getUserID());
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
        contentPanel.add(infoPanel);
        contentPanel.add(genresPanel);
        contentPanel.add(achievementsPanel);
        contentPanel.add(reviewsPanel);
        gameDetailPanel.add(contentPanel, BorderLayout.CENTER);
        JPanel actionButtonPanel = new JPanel(new FlowLayout());
        if (currentUser != null) {
            if (!viewManager.getController().userOwnsGame(currentUser, game.getGameID())) {
                JButton buyButton = UIStyler.createStyledButton("Buy Game", UIStyler.FOREST_GREEN);
                buyButton.addActionListener(e -> {
                    if (viewManager.getController().buyGame(currentUser, game.getGameID())) {
                        viewManager.showMessage("Game purchased successfully!");
                        showGameDetailsView(game);
                    } else {
                        viewManager.showError("Failed to purchase game!");
                    }
                });
                actionButtonPanel.add(buyButton);
            }
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
                        showGameDetailsView(game);
                    } else {
                        viewManager.showError("Failed to remove from wishlist!");
                    }
                });
                actionButtonPanel.add(removeFromWishlistButton);
            }
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
                            showGameDetailsView(game);
                        } else {
                            viewManager.showError("Failed to add review!");
                        }
                    }
                });
                actionButtonPanel.add(addReviewButton);
            }
        }
        gameDetailPanel.add(actionButtonPanel, BorderLayout.SOUTH);
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
                showGameDetailsView(game);
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
                showGameDetailsView(game);
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
        JButton backButton = UIStyler.createStyledButton("Back to Dashboard", UIStyler.STEEL_BLUE);
        backButton.addActionListener(e -> showUserDashboard());
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
        userListPanel.removeAll();
        List<Optional<Users>> allUsersOptional = viewManager.getController().getAllUsers();
        List<Users> allUsers = allUsersOptional.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
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
            for (Users user : allUsers) {
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
    private JPanel createUserListItem(Users user) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setBackground(UIStyler.ALICE_BLUE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        panel.setPreferredSize(new Dimension(500, 120));
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
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
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.setOpaque(false);
        JButton blockButton = UIStyler.createStyledButton("Block", UIStyler.CRIMSON);
        JButton unblockButton = UIStyler.createStyledButton("Unblock", new Color(34, 139, 34));
        blockButton.setEnabled(!user.isBlocked());
        unblockButton.setEnabled(user.isBlocked());
        Users currentUser = viewManager.getCurrentUser();
        if (currentUser != null && currentUser.getUserID() == user.getUserID()) {
            blockButton.setEnabled(false);
            blockButton.setText("Cannot Block Self");
        }
        blockButton.addActionListener(e -> {
            if (viewManager.getController().blockUser(user.getUserID())) {
                viewManager.showMessage("User " + user.getName() + " " + user.getSurname() + " has been blocked.");
                loadAllUsersList();
            } else {
                viewManager.showError("Failed to block user!");
            }
        });
        unblockButton.addActionListener(e -> {
            if (viewManager.getController().unblockUser(user.getUserID())) {
                viewManager.showMessage("User " + user.getName() + " " + user.getSurname() + " has been unblocked.");
                loadAllUsersList();
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

    /**
     * Shows the transactions view
     */
    public void showTransactionsView() {
        cardLayout.show(cardPanel, "TRANSACTIONS");
        rightPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 2), "YOUR TRANSACTIONS",
            TitledBorder.CENTER, TitledBorder.TOP,
            UIStyler.LABEL_FONT, UIStyler.STEEL_BLUE
        ));
        loadTransactionsContent();
        viewManager.refreshFrame();
    }

    /**
     * Loads the user's transaction content
     */
    private void loadTransactionsContent() {
        transactionsViewPanel.removeAll();
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        Users currentUser = viewManager.getCurrentUser();
        if (currentUser == null) {
            JLabel noUserLabel = new JLabel("Please log in to view transactions");
            UIStyler.centerAlign(noUserLabel);
            contentPanel.add(noUserLabel);
        } else {
            List<Controller.TransactionDetail> transactions = viewManager.getController().getUserTransactions(currentUser);
            
            if (transactions.isEmpty()) {
                JLabel noTransactionsLabel = new JLabel("No transactions found");
                UIStyler.centerAlign(noTransactionsLabel);
                contentPanel.add(noTransactionsLabel);
            } else {
                // Title
                JLabel titleLabel = new JLabel("Transaction History");
                titleLabel.setFont(UIStyler.TITLE_FONT);
                titleLabel.setForeground(UIStyler.STEEL_BLUE);
                UIStyler.centerAlign(titleLabel);
                contentPanel.add(titleLabel);
                contentPanel.add(Box.createVerticalStrut(20));
                
                // Header
                JPanel headerPanel = new JPanel(new GridLayout(1, 3, 10, 0));
                headerPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UIStyler.STEEL_BLUE, 2),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
                headerPanel.setBackground(UIStyler.ALICE_BLUE);
                
                JLabel idHeaderLabel = new JLabel("Transaction ID");
                idHeaderLabel.setFont(UIStyler.LABEL_FONT);
                idHeaderLabel.setForeground(UIStyler.MIDNIGHT_BLUE);
                
                JLabel titleHeaderLabel = new JLabel("Game Title");
                titleHeaderLabel.setFont(UIStyler.LABEL_FONT);
                titleHeaderLabel.setForeground(UIStyler.MIDNIGHT_BLUE);
                
                JLabel amountHeaderLabel = new JLabel("Final Amount");
                amountHeaderLabel.setFont(UIStyler.LABEL_FONT);
                amountHeaderLabel.setForeground(UIStyler.MIDNIGHT_BLUE);
                
                headerPanel.add(idHeaderLabel);
                headerPanel.add(titleHeaderLabel);
                headerPanel.add(amountHeaderLabel);
                contentPanel.add(headerPanel);
                contentPanel.add(Box.createVerticalStrut(5));
                
                // Transaction items
                for (Controller.TransactionDetail transaction : transactions) {
                    JPanel transactionPanel = new JPanel(new GridLayout(1, 3, 10, 0));
                    transactionPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)
                    ));
                    transactionPanel.setBackground(Color.WHITE);
                    
                    JLabel idLabel = new JLabel(String.valueOf(transaction.getTransactionId()));
                    idLabel.setFont(UIStyler.TEXT_FONT);
                    
                    JLabel titleLabel2 = new JLabel(transaction.getGameTitle());
                    titleLabel2.setFont(UIStyler.TEXT_FONT);
                    
                    JLabel amountLabel = new JLabel(String.format("$%.2f", transaction.getFinalAmount()));
                    amountLabel.setFont(UIStyler.TEXT_FONT);
                    amountLabel.setForeground(UIStyler.FOREST_GREEN);
                    
                    transactionPanel.add(idLabel);
                    transactionPanel.add(titleLabel2);
                    transactionPanel.add(amountLabel);
                    
                    contentPanel.add(transactionPanel);
                    contentPanel.add(Box.createVerticalStrut(2));
                }
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        transactionsViewPanel.add(scrollPane, BorderLayout.CENTER);
        transactionsViewPanel.revalidate();
        transactionsViewPanel.repaint();
    }
    /**
     * Shows the change discount dialog for publishers
     */
    private void showChangeDiscountDialog(VideoGames game) {
        String currentDiscount = game.getDiscount() != null ? game.getDiscount() : "0";
        String newDiscountStr = UIStyler.showStyledInput(
            viewManager.getMainFrame(),
            "Enter new discount percentage (0-100):\nCurrent discount: " + currentDiscount + "%",
            "Change Discount",
            currentDiscount
        );
        
        if (newDiscountStr != null && !newDiscountStr.trim().isEmpty()) {
            try {
                double newDiscount = Double.parseDouble(newDiscountStr.trim());
                if (newDiscount < 0 || newDiscount > 100) {
                    viewManager.showError("Discount must be between 0 and 100");
                    return;
                }
                
                if (viewManager.getController().changeGameDiscount(game.getGameID(), newDiscount)) {
                    viewManager.showMessage("Discount updated successfully!");
                    // Reload the game with updated data and refresh the view
                    Optional<VideoGames> updatedGameOpt = viewManager.getController().reloadGame(game.getGameID());
                    if (updatedGameOpt.isPresent()) {
                        showGameDetailsView(updatedGameOpt.get());
                    } else {
                        showGameDetailsView(game); // Fallback to original game if reload fails
                    }
                } else {
                    viewManager.showError("Failed to update discount");
                }
            } catch (NumberFormatException e) {
                viewManager.showError("Please enter a valid number");
            }
        }
    }
}
