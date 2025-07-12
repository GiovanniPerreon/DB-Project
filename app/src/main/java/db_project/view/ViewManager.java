package db_project.view;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import db_project.Controller;
import db_project.data.Users;

/**
 * Main view manager that coordinates all UI components.
 * This replaces the original monolithic View class while maintaining exact same functionality.
 */
public final class ViewManager {
    
    private Optional<Controller> controller;
    private final JFrame mainFrame;
    private JPanel mainPanel;
    private Users currentUser;
    
    // UI Components
    private LoginPanel loginPanel;
    private MainMenuPanel mainMenuPanel;
    private DialogManager dialogManager;
    
    public ViewManager(Runnable onClose) {
        this.controller = Optional.empty();
        this.currentUser = null;
        this.mainFrame = setupMainFrame(onClose);
        this.dialogManager = new DialogManager(this);
        setupUI();
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
        mainPanel.setBackground(UIStyler.LIGHT_BACKGROUND);
        
        // Header with modern styling
        JLabel titleLabel = new JLabel("SteamDB - Videogames Store");
        titleLabel.setFont(UIStyler.TITLE_FONT);
        titleLabel.setForeground(UIStyler.MIDNIGHT_BLUE);
        UIStyler.centerAlign(titleLabel);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        mainPanel.add(titleLabel);
        
        // Create and add login panel
        loginPanel = new LoginPanel(this);
        mainPanel.add(loginPanel);
        
        // Create main menu panel (initially hidden)
        mainMenuPanel = new MainMenuPanel(this);
        mainMenuPanel.setVisible(false);
        mainPanel.add(mainMenuPanel);
        
        mainFrame.getContentPane().setBackground(UIStyler.LIGHT_BACKGROUND);
        mainFrame.add(mainPanel);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
    
    // ===== PUBLIC API METHODS (same as original View) =====
    
    public void setController(Controller controller) {
        Objects.requireNonNull(controller, "Set null controller in view");
        this.controller = Optional.of(controller);
        
        // Initialize components that need the controller
        if (mainMenuPanel != null) {
            mainMenuPanel.initializeWithController();
        }
        if (mainMenuPanel.getGameBrowserPanel() != null) {
            mainMenuPanel.getGameBrowserPanel().initializeWithController();
        }
    }
    
    public void showUser(Users user) {
        System.out.println("User: " + user.toString());
    }
    
    public void showVideoGame(db_project.data.VideoGames videogame) {
        System.out.println("Video Game: " + videogame.toString());
    }
    
    // ===== PACKAGE-PRIVATE METHODS FOR COMPONENT INTERACTION =====
    
    Controller getController() {
        if (this.controller.isPresent()) {
            return this.controller.get();
        } else {
            throw new IllegalStateException("Controller not set in view");
        }
    }
    
    JFrame getMainFrame() {
        return mainFrame;
    }
    
    DialogManager getDialogManager() {
        return dialogManager;
    }
    
    Users getCurrentUser() {
        return currentUser;
    }
    
    void setCurrentUser(Users user) {
        this.currentUser = user;
    }
    
    void performLogin(String email, String password) {
        if (getController().loginUser(email, password)) {
            currentUser = getController().getCurrentUser();
            showMainMenu();
        } else {
            showError("Invalid email or password!");
        }
    }
    
    void showMainMenu() {
        // Hide login panel and show main menu
        loginPanel.setVisible(false);
        mainMenuPanel.setVisible(true);
        
        // Initialize the main menu with user data
        mainMenuPanel.loadUserData(currentUser);
        
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    
    void showMessage(String message) {
        JOptionPane.showMessageDialog(mainFrame, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    void showError(String message) {
        JOptionPane.showMessageDialog(mainFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    void showRegisterDialog() {
        dialogManager.showRegisterDialog();
    }
    
    void refreshFrame() {
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    
    // ===== ADDITIONAL VIEW METHODS =====
    
    /**
     * Shows the user's game collection
     */
    public void showUserCollectionView() {
        if (mainMenuPanel != null) {
            mainMenuPanel.showCollectionView();
        }
    }
    
    /**
     * Shows the user's wishlist
     */
    public void showUserWishlistView() {
        if (mainMenuPanel != null) {
            mainMenuPanel.showWishlistView();
        }
    }
    
    /**
     * Shows the user's achievements
     */
    public void showAchievementsView() {
        if (mainMenuPanel != null) {
            mainMenuPanel.showAchievementsView();
        }
    }
    
    /**
     * Shows all games browser
     */
    public void showAllGamesView() {
        if (mainMenuPanel != null) {
            mainMenuPanel.showGameBrowser();
        }
    }
    
    /**
     * Shows all users (admin only)
     */
    public void showUsersView() {
        if (mainMenuPanel != null) {
            mainMenuPanel.showAllUsersBrowser();
        }
    }
    
    /**
     * Shows detailed view of a game with action buttons
     */
    public void showGameDetails(db_project.data.VideoGames game) {
        if (mainMenuPanel != null) {
            mainMenuPanel.showGameDetailsView(game);
        }
    }
}
