package db_project.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import db_project.data.Achievements;
import db_project.data.Users;
import db_project.data.VideoGames;

/**
 * Panel that displays the user dashboard with 4 info panels
 */
public class UserDashboardPanel extends JPanel {
    
    private final ViewManager viewManager;
    private JTextArea userInfoArea;
    private JTextArea ownedGamesArea;
    private JTextArea wishlistArea;
    private JTextArea achievementsArea;
    /**
     * Constructor for UserDashboardPanel
     * @param viewManager the main view manager instance
     */
    public UserDashboardPanel(ViewManager viewManager) {
        this.viewManager = viewManager;
        setupDashboard();
    }
    /**
     * Setup the dashboard layout and components
     */
    private void setupDashboard() {
        setLayout(new GridLayout(2, 2, 15, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(UIStyler.createTitledBorder("INFO PANEL", UIStyler.STEEL_BLUE));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(UIStyler.ALICE_BLUE);
        JLabel userInfoLabel = new JLabel("User Information");
        userInfoLabel.setFont(UIStyler.LABEL_FONT);
        userInfoLabel.setForeground(UIStyler.MIDNIGHT_BLUE);
        userInfoArea = new JTextArea(5, 20);
        userInfoArea.setEditable(false);
        userInfoArea.setOpaque(false);
        
        // Create logout button
        JButton logoutButton = UIStyler.createStyledButton("Logout", new Color(220, 20, 60));
        logoutButton.setMaximumSize(new Dimension(150, 30));
        logoutButton.setPreferredSize(new Dimension(150, 30));
        logoutButton.addActionListener(e -> viewManager.performLogout());
        
        infoPanel.add(userInfoLabel);
        infoPanel.add(userInfoArea);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(logoutButton);
        JPanel gameOwnedPanel = new JPanel();
        gameOwnedPanel.setBorder(UIStyler.createTitledBorder("GAMES OWNED", UIStyler.FOREST_GREEN));
        gameOwnedPanel.setLayout(new BoxLayout(gameOwnedPanel, BoxLayout.Y_AXIS));
        gameOwnedPanel.setBackground(UIStyler.HONEYDEW);
        JButton viewCollectionButton = UIStyler.createStyledButton("View Collection", UIStyler.FOREST_GREEN);
        ownedGamesArea = new JTextArea(5, 20);
        ownedGamesArea.setEditable(false);
        ownedGamesArea.setOpaque(false);
        ownedGamesArea.setFont(UIStyler.SMALL_FONT);
        gameOwnedPanel.add(viewCollectionButton);
        gameOwnedPanel.add(Box.createVerticalStrut(5));
        gameOwnedPanel.add(ownedGamesArea);
        JPanel wishlistPanel = new JPanel();
        wishlistPanel.setBorder(UIStyler.createTitledBorder("WISHLIST", UIStyler.MEDIUM_SLATE_BLUE));
        wishlistPanel.setLayout(new BoxLayout(wishlistPanel, BoxLayout.Y_AXIS));
        wishlistPanel.setBackground(UIStyler.GHOST_WHITE);
        JButton viewWishlistButton = UIStyler.createStyledButton("View Wishlist", UIStyler.MEDIUM_SLATE_BLUE);
        wishlistArea = new JTextArea(3, 20);
        wishlistArea.setEditable(false);
        wishlistArea.setOpaque(false);
        wishlistArea.setFont(UIStyler.SMALL_FONT);
        wishlistPanel.add(viewWishlistButton);
        wishlistPanel.add(Box.createVerticalStrut(5));
        wishlistPanel.add(wishlistArea);
        JPanel achievementsPanel = new JPanel();
        achievementsPanel.setBorder(UIStyler.createTitledBorder("ACHIEVEMENTS GOT", UIStyler.DARK_ORANGE));
        achievementsPanel.setLayout(new BoxLayout(achievementsPanel, BoxLayout.Y_AXIS));
        achievementsPanel.setBackground(UIStyler.CORNSILK);
        JButton viewAchievementsButton = UIStyler.createStyledButton("View Achievements", UIStyler.DARK_ORANGE);
        achievementsArea = new JTextArea(3, 20);
        achievementsArea.setEditable(false);
        achievementsArea.setOpaque(false);
        achievementsArea.setFont(UIStyler.SMALL_FONT);
        achievementsPanel.add(viewAchievementsButton);
        achievementsPanel.add(achievementsArea);
        add(infoPanel);
        add(gameOwnedPanel);
        add(wishlistPanel);
        add(achievementsPanel);
        viewCollectionButton.addActionListener(e -> {
            viewManager.showUserCollectionView();
        });
        viewWishlistButton.addActionListener(e -> {
            viewManager.showUserWishlistView();
        });
        viewAchievementsButton.addActionListener(e -> {
            viewManager.showAchievementsView();
        });
    }
    /**
     * Load user data into the dashboard panels
     */
    public void loadUserData(Users user) {
        if (user != null) {
            loadUserInfo(user);
            loadUserCollection(user);
            loadUserWishlist(user);
            loadUserAchievements(user);
        }
    }
    /**
     * Load user information into the info panel
     * @param user the user to load data for
     */
    private void loadUserInfo(Users user) {
        if (userInfoArea != null) {
            userInfoArea.setText("Name: " + user.getName() + " " + user.getSurname() + 
                                "\nEmail: " + user.getEmail() + 
                                "\nBirth Date: " + user.getBirthDate() + 
                                "\nPermissions: " + viewManager.getController().getUserTypes(user));
        }
    }
    /**
     * Load user's owned games into the owned games panel
     * @param user the user to load data for
     */
    private void loadUserCollection(Users user) {
        List<VideoGames> collection = viewManager.getController().getUserCollection(user);
        StringBuilder sb = new StringBuilder();
        sb.append("Games Owned: ").append(collection.size()).append("\n");
        for (int i = 0; i < Math.min(3, collection.size()); i++) {
            sb.append("• ").append(collection.get(i).getTitle()).append("\n");
        }
        if (collection.size() > 3) {
            sb.append("... and ").append(collection.size() - 3).append(" more");
        }
        ownedGamesArea.setText(sb.toString());
    }
    /**
     * Load user's wishlist into the wishlist panel
     * @param user the user to load data for
     */
    private void loadUserWishlist(Users user) {
        List<VideoGames> wishlist = viewManager.getController().getUserWishlist(user);
        StringBuilder sb = new StringBuilder();
        sb.append("Wishlist: ").append(wishlist.size()).append(" games\n");
        for (int i = 0; i < Math.min(2, wishlist.size()); i++) {
            sb.append("• ").append(wishlist.get(i).getTitle()).append("\n");
        }
        if (wishlist.size() > 2) {
            sb.append("... and ").append(wishlist.size() - 2).append(" more");
        }
        wishlistArea.setText(sb.toString());
    }
    /**
     * Load user's achievements into the achievements panel
     * @param user the user to load data for
     */
    private void loadUserAchievements(Users user) {
        List<Achievements> achievements = viewManager.getController().getUserAchievements(user);
        if (achievements.isEmpty()) {
            achievementsArea.setText("No achievements unlocked yet.\nPlay some games to earn achievements!");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Achievements: ").append(achievements.size()).append("\n");
            int maxToShow = Math.min(achievements.size(), 3);
            for (int i = 0; i < maxToShow; i++) {
                sb.append("• ").append(achievements.get(i).getTitle()).append("\n");
            }
            if (achievements.size() > 3) {
                sb.append("... and ").append(achievements.size() - 3).append(" more");
            }
            achievementsArea.setText(sb.toString());
        }
    }
    /**
     * Refresh the dashboard with latest data
     */
    public void refresh() {
        loadUserData(viewManager.getCurrentUser());
    }
}
