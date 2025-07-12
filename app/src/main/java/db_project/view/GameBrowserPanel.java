package db_project.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import db_project.data.LeastRatedUser;
import db_project.data.VideoGames;

/**
 * Panel for browsing and filtering games
 */
public class GameBrowserPanel extends JPanel {
    
    private final ViewManager viewManager;
    private JPanel gameListPanel;
    private JPanel filtersPanel;
    private JComboBox<String> genreComboBox;
    /**
     * Constructor for GameBrowserPanel
     */
    public GameBrowserPanel(ViewManager viewManager) {
        this.viewManager = viewManager;
        setupBrowser();
    }
    /**
     * Sets up the game browser panel with filters and game list
     */    
    private void setupBrowser() {
        setLayout(new BorderLayout());
        gameListPanel = new JPanel();
        gameListPanel.setLayout(new BoxLayout(gameListPanel, BoxLayout.Y_AXIS));
        JScrollPane gameListScrollPane = new JScrollPane(gameListPanel);
        gameListScrollPane.setPreferredSize(new Dimension(580, 500));
        JButton backToUserButton = UIStyler.createStyledButton("Back to User Dashboard", UIStyler.STEEL_BLUE);
        backToUserButton.addActionListener(e -> {
            viewManager.showDashboardView();
        });
        createFiltersPanel();
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(backToUserButton, BorderLayout.NORTH);
        topPanel.add(filtersPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        add(gameListScrollPane, BorderLayout.CENTER);
    }
    /**
     * Creates the filters panel with sorting and genre options
     */
    private void createFiltersPanel() {
        filtersPanel = new JPanel();
        filtersPanel.setLayout(new FlowLayout());
        filtersPanel.setBorder(BorderFactory.createTitledBorder("Filters"));
        JButton newestGamesButton = UIStyler.createCompactFilterButton("Newest");
        JButton oldestGamesButton = UIStyler.createCompactFilterButton("Oldest");
        JButton highestRatedButton = UIStyler.createCompactFilterButton("Top Rated");
        JButton lowestRatedButton = UIStyler.createCompactFilterButton("Low Rated");
        JButton mostExpensiveButton = UIStyler.createCompactFilterButton("Expensive");
        JButton cheapestButton = UIStyler.createCompactFilterButton("Cheap");
        JButton mostSoldButton = UIStyler.createCompactFilterButton("Best Selling");
        JButton allGamesButton = UIStyler.createCompactFilterButton("All Games");
        genreComboBox = UIStyler.createStyledComboBox();
        genreComboBox.addItem("Select Genre...");
        filtersPanel.add(new JLabel("Sort by:"));
        filtersPanel.add(newestGamesButton);
        filtersPanel.add(oldestGamesButton);
        filtersPanel.add(highestRatedButton);
        filtersPanel.add(lowestRatedButton);
        filtersPanel.add(mostExpensiveButton);
        filtersPanel.add(cheapestButton);
        filtersPanel.add(mostSoldButton);
        filtersPanel.add(new JLabel("|"));
        JLabel genreLabel = new JLabel("Filter by Genre:");
        genreLabel.setFont(UIStyler.LABEL_FONT);
        genreLabel.setForeground(UIStyler.MIDNIGHT_BLUE);
        filtersPanel.add(genreLabel);
        filtersPanel.add(genreComboBox);
        filtersPanel.add(new JLabel("|"));
        filtersPanel.add(allGamesButton);
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
    }
    
    public void loadAllGames() {
        gameListPanel.removeAll();
        filtersPanel.setVisible(true);
        List<Optional<VideoGames>> allGames = viewManager.getController().getAllGames();
        for (Optional<VideoGames> gameOpt : allGames) {
            if (gameOpt.isPresent()) {
                VideoGames game = gameOpt.get();
                JPanel gamePanel = createGameListItem(game);
                gameListPanel.add(gamePanel);
                gameListPanel.add(Box.createVerticalStrut(5));
            }
        }
        gameListPanel.revalidate();
        gameListPanel.repaint();
    }
    /**
     * Loads the top 10 highest rated games
     */
    public void loadTopGames() {
        gameListPanel.removeAll();
        filtersPanel.setVisible(false);
        List<VideoGames> topGames = viewManager.getController().getTopGames(10);
        JLabel titleLabel = new JLabel("TOP 10 HIGHEST RATED GAMES");
        titleLabel.setFont(titleLabel.getFont().deriveFont(16f));
        UIStyler.centerAlign(titleLabel);
        gameListPanel.add(titleLabel);
        gameListPanel.add(Box.createVerticalStrut(10));
        for (VideoGames game : topGames) {
            JPanel gamePanel = createGameListItem(game);
            gameListPanel.add(gamePanel);
            gameListPanel.add(Box.createVerticalStrut(5));
        }
        gameListPanel.revalidate();
        gameListPanel.repaint();
    }
    /**
     * Loads the most bought game
     */
    public void loadMostBoughtGame() {
        gameListPanel.removeAll();
        filtersPanel.setVisible(false);
        Optional<VideoGames> mostBoughtGame = viewManager.getController().getMostBoughtGame();
        JLabel titleLabel = new JLabel("MOST BOUGHT GAME");
        titleLabel.setFont(titleLabel.getFont().deriveFont(16f));
        UIStyler.centerAlign(titleLabel);
        gameListPanel.add(titleLabel);
        gameListPanel.add(Box.createVerticalStrut(10));
        if (mostBoughtGame.isPresent()) {
            VideoGames game = mostBoughtGame.get();
            JPanel gamePanel = createGameListItem(game);
            gameListPanel.add(gamePanel);
        } else {
            JLabel noGameLabel = new JLabel("No games found");
            UIStyler.centerAlign(noGameLabel);
            gameListPanel.add(noGameLabel);
        }
        gameListPanel.revalidate();
        gameListPanel.repaint();
    }
    /**
     * Loads the least rated users
     */
    public void loadLeastRatedUsers() {
        gameListPanel.removeAll();
        filtersPanel.setVisible(false);
        List<LeastRatedUser> leastRatedUsers = viewManager.getController().getLeastRatedUsers();
        JLabel titleLabel = new JLabel("LOWEST RATED DEVELOPERS");
        titleLabel.setFont(titleLabel.getFont().deriveFont(16f));
        UIStyler.centerAlign(titleLabel);
        gameListPanel.add(titleLabel);
        gameListPanel.add(Box.createVerticalStrut(10));
        if (leastRatedUsers.isEmpty()) {
            JLabel noDataLabel = new JLabel("No data available - all publishers/developers have good ratings!");
            UIStyler.centerAlign(noDataLabel);
            gameListPanel.add(noDataLabel);
        } else {
            for (LeastRatedUser user : leastRatedUsers) {
                JPanel userPanel = new JPanel();
                userPanel.setLayout(new BorderLayout());
                userPanel.setBorder(BorderFactory.createEtchedBorder());
                userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
                JLabel userLabel = new JLabel(String.format(
                    "<html><b>%s %s</b><br>Role: %s<br>Average Rating: %.2f/5<br>Email: %s</html>",
                    user.getName(), user.getSurname(), user.getRole(), user.getAvgRating(), user.getEmail()
                ));
                userPanel.add(userLabel, BorderLayout.CENTER);
                UIStyler.centerAlign(userPanel);
                gameListPanel.add(userPanel);
                gameListPanel.add(Box.createVerticalStrut(5));
            }
        }
        gameListPanel.revalidate();
        gameListPanel.repaint();
    }
    
    private void showFilteredGames(String filter) {
        gameListPanel.removeAll();
        List<Optional<VideoGames>> games = null;
        String title = "";
        switch (filter) {
            case "newest":
                games = viewManager.getController().getTop10NewestGames();
                title = "NEWEST GAMES";
                break;
            case "oldest":
                games = viewManager.getController().getTop10OldestGames();
                title = "OLDEST GAMES";
                break;
            case "highest_rated":
                games = viewManager.getController().getTop10HighestRatedGames();
                title = "HIGHEST RATED GAMES";
                break;
            case "lowest_rated":
                games = viewManager.getController().getTop10LowestRatedGames();
                title = "LOWEST RATED GAMES";
                break;
            case "most_expensive":
                games = viewManager.getController().getTop10MostExpensiveGames();
                title = "MOST EXPENSIVE GAMES";
                break;
            case "cheapest":
                games = viewManager.getController().getAllCheapestGames();
                title = "CHEAPEST GAMES";
                break;
            case "most_sold":
                games = viewManager.getController().getAllMostSoldGames();
                title = "BEST SELLING GAMES";
                break;
            case "all":
                games = viewManager.getController().getAllGames();
                title = "ALL GAMES";
                break;
            default:
                if (filter.startsWith("genre:")) {
                    String genre = filter.substring(6);
                    games = viewManager.getController().getAllGamesByGenre(genre);
                    title = "GAMES IN GENRE: " + genre.toUpperCase();
                } else {
                    games = viewManager.getController().getAllGames();
                    title = "ALL GAMES";
                }
                break;
        }
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UIStyler.HEADER_FONT);
        titleLabel.setForeground(UIStyler.STEEL_BLUE);
        UIStyler.centerAlign(titleLabel);
        gameListPanel.add(titleLabel);
        gameListPanel.add(Box.createVerticalStrut(10));
        if (games == null || games.isEmpty()) {
            JLabel noGamesLabel = new JLabel("No games found");
            UIStyler.centerAlign(noGamesLabel);
            gameListPanel.add(noGamesLabel);
        } else {
            for (Optional<VideoGames> gameOpt : games) {
                if (gameOpt.isPresent()) {
                    VideoGames game = gameOpt.get();
                    JPanel gamePanel = createGameListItem(game);
                    gameListPanel.add(gamePanel);
                    gameListPanel.add(Box.createVerticalStrut(5));
                }
            }
        }
        gameListPanel.revalidate();
        gameListPanel.repaint();
    }
    /**
     * Creates a panel for displaying a single game in the list
     * @param game The VideoGames object to display
     * @return JPanel with game details
     */
    private JPanel createGameListItem(VideoGames game) {
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
        titleLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
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
        priceLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        JLabel ratingLabel = new JLabel();
        double rating = game.getAverageRating();
        if (rating > 0.0) {
            ratingLabel.setText(String.format("%.1f", rating) + "/5");
            ratingLabel.setForeground(new Color(255, 140, 0));
            ratingLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        }
        String description = game.getDescription();
        if (description == null) {
            description = "No description available";
        } else if (description.length() > 120) {
            description = description.substring(0, 120) + "...";
        }
        JLabel descLabel = new JLabel("<html><div style='width: 350px'>" + description + "</div></html>");
        descLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
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
                panel.setBackground(new Color(230, 240, 250));
                panel.repaint();
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                panel.setBackground(new Color(240, 248, 255));
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
     * Shows detailed view of a game with action buttons
     */
    private void showGameDetails(VideoGames game) {
        // Delegate to ViewManager to show the actual game details view
        viewManager.showGameDetails(game);
    }
    /**
     * Initializes the panel with controller-dependent data
     */
    public void initializeWithController() {
        loadGenresIntoComboBox();
    }
    /**
     * Loads all available genres into the genre combo box
     */
    private void loadGenresIntoComboBox() {
        genreComboBox.removeAllItems();
        genreComboBox.addItem("Select Genre...");
        try {
            List<String> genres = viewManager.getController().getAllGenres();
            for (String genre : genres) {
                genreComboBox.addItem(genre);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
