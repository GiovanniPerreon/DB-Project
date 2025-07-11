package db_project;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

import db_project.data.Users;
import db_project.data.VideoGames;
import db_project.data.Transactions;
import db_project.data.Reviews;
import db_project.data.Wishlists;

public final class View {

    private Optional<Controller> controller;
    private final JFrame mainFrame;
    private JPanel mainPanel;
    private JTextArea outputArea;
    private Users currentUser;

    // We take an action to run before closing the view so that one can gracefully
    // deal with open resources.
    public View(Runnable onClose) {
        this.controller = Optional.empty();
        this.currentUser = null;
        this.mainFrame = this.setupMainFrame(onClose);
        this.setupUI();
    }

    private JFrame setupMainFrame(Runnable onClose) {
        var frame = new JFrame("SteamDB - Video Game Store");
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
        
        JTextField userIdField = new JTextField(10);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register New User");
        
        loginPanel.add(new JLabel("User ID:"));
        loginPanel.add(userIdField);
        loginPanel.add(loginButton);
        loginPanel.add(registerButton);
        
        loginButton.addActionListener(e -> {
            try {
                int userId = Integer.parseInt(userIdField.getText());
                login(userId);
            } catch (NumberFormatException ex) {
                showError("Please enter a valid User ID");
            }
        });
        
        registerButton.addActionListener(e -> showRegisterDialog());
        
        mainPanel.add(loginPanel);
    }

    private void setupMainMenu() {
        JPanel menuPanel = new JPanel();
        menuPanel.setBorder(BorderFactory.createTitledBorder("Main Menu"));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setVisible(false);
        
        // User operations
        JPanel userOpsPanel = new JPanel();
        userOpsPanel.setBorder(BorderFactory.createTitledBorder("User Operations"));
        
        JButton buyGameButton = new JButton("Buy Video Game");
        JButton viewCollectionButton = new JButton("View My Collection");
        JButton addToWishlistButton = new JButton("Add to Wishlist");
        JButton viewWishlistButton = new JButton("View My Wishlist");
        JButton addReviewButton = new JButton("Add Review");
        
        userOpsPanel.add(buyGameButton);
        userOpsPanel.add(viewCollectionButton);
        userOpsPanel.add(addToWishlistButton);
        userOpsPanel.add(viewWishlistButton);
        userOpsPanel.add(addReviewButton);
        
        // Publisher operations
        JPanel publisherOpsPanel = new JPanel();
        publisherOpsPanel.setBorder(BorderFactory.createTitledBorder("Publisher Operations"));
        
        JButton createGameButton = new JButton("Create New Game");
        
        publisherOpsPanel.add(createGameButton);
        
        // Query operations
        JPanel queryOpsPanel = new JPanel();
        queryOpsPanel.setBorder(BorderFactory.createTitledBorder("Browse & Search"));
        
        JButton topGamesButton = new JButton("Top 10 Games");
        JButton lowRatedPublishersButton = new JButton("Low-Rated Publishers");
        JButton mostBoughtGameButton = new JButton("Most Bought Game");
        JButton viewAllGamesButton = new JButton("View All Games");
        
        queryOpsPanel.add(topGamesButton);
        queryOpsPanel.add(lowRatedPublishersButton);
        queryOpsPanel.add(mostBoughtGameButton);
        queryOpsPanel.add(viewAllGamesButton);
        
        // Admin operations
        JPanel adminOpsPanel = new JPanel();
        adminOpsPanel.setBorder(BorderFactory.createTitledBorder("Admin Operations"));
        
        JButton blockUserButton = new JButton("Block User");
        JButton unblockUserButton = new JButton("Unblock User");
        JButton viewUsersButton = new JButton("View All Users");
        
        adminOpsPanel.add(blockUserButton);
        adminOpsPanel.add(unblockUserButton);
        adminOpsPanel.add(viewUsersButton);
        
        // Add action listeners
        buyGameButton.addActionListener(e -> showBuyGameDialog());
        viewCollectionButton.addActionListener(e -> viewUserCollection());
        addToWishlistButton.addActionListener(e -> showAddToWishlistDialog());
        viewWishlistButton.addActionListener(e -> viewUserWishlist());
        addReviewButton.addActionListener(e -> showAddReviewDialog());
        createGameButton.addActionListener(e -> showCreateGameDialog());
        topGamesButton.addActionListener(e -> showTopGames());
        lowRatedPublishersButton.addActionListener(e -> showLowRatedPublishers());
        mostBoughtGameButton.addActionListener(e -> showMostBoughtGame());
        viewAllGamesButton.addActionListener(e -> viewAllGames());
        blockUserButton.addActionListener(e -> showBlockUserDialog());
        unblockUserButton.addActionListener(e -> showUnblockUserDialog());
        viewUsersButton.addActionListener(e -> viewAllUsers());
        
        menuPanel.add(userOpsPanel);
        menuPanel.add(publisherOpsPanel);
        menuPanel.add(queryOpsPanel);
        menuPanel.add(adminOpsPanel);
        
        mainPanel.add(menuPanel);
    }

    private void setupOutputArea() {
        outputArea = new JTextArea(15, 70);
        outputArea.setEditable(false);
        outputArea.setFont(outputArea.getFont().deriveFont(12f));
        
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output"));
        
        mainPanel.add(scrollPane);
    }

    private void login(int userId) {
        if (getController().loginUser(userId)) {
            currentUser = getController().getCurrentUser();
            showMainMenu();
            appendOutput("Logged in as: " + currentUser.toString());
        } else {
            showError("User not found!");
        }
    }

    private void showMainMenu() {
        // Show/hide panels based on user permissions
        for (java.awt.Component comp : mainPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                if (panel.getBorder() != null && panel.getBorder().toString().contains("Main Menu")) {
                    panel.setVisible(true);
                    
                    // Hide/show publisher operations
                    for (java.awt.Component subComp : panel.getComponents()) {
                        if (subComp instanceof JPanel) {
                            JPanel subPanel = (JPanel) subComp;
                            if (subPanel.getBorder() != null) {
                                String borderTitle = subPanel.getBorder().toString();
                                if (borderTitle.contains("Publisher Operations")) {
                                    subPanel.setVisible(currentUser != null && getController().isPublisher(currentUser));
                                } else if (borderTitle.contains("Admin Operations")) {
                                    subPanel.setVisible(currentUser != null && getController().isAdmin(currentUser));
                                }
                            }
                        }
                    }
                }
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
        String gameIdStr = JOptionPane.showInputDialog(mainFrame, "Enter Game ID to add to wishlist:");
        if (gameIdStr != null) {
            try {
                int gameId = Integer.parseInt(gameIdStr);
                if (getController().addToWishlist(currentUser, gameId)) {
                    showMessage("Game added to wishlist!");
                } else {
                    showError("Failed to add to wishlist!");
                }
            } catch (NumberFormatException e) {
                showError("Please enter a valid Game ID");
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
}
