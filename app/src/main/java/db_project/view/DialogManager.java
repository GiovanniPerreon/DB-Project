package db_project.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import db_project.data.VideoGames;

/**
 * Manages all dialog boxes and popup interactions
 */
public class DialogManager {
    
    private final ViewManager viewManager;
    /**
     * Constructor for DialogManager
     * @param viewManager the main view manager instance
     */
    public DialogManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
    /**
     * Show the registration dialog
     */
    public void showRegisterDialog() {
        UIStyler.RegistrationDialogResult result = UIStyler.showStyledRegistrationDialog(viewManager.getMainFrame());
        if (result.submitted) {
            if (viewManager.getController().registerUser(
                    result.email,
                    result.password,
                    result.name,
                    result.surname,
                    result.birthDate)) {
                viewManager.showMessage("User registered successfully!");
            } else {
                viewManager.showError("Registration failed!");
            }
        }
    }
    /**
     * Show the buy game dialog
     */
    public void showBuyGameDialog() {
        String gameIdStr = UIStyler.showStyledInput(
            viewManager.getMainFrame(), 
            "Enter the Game ID you wish to purchase:", 
            "Buy Game", 
            ""
        );
        if (gameIdStr != null && !gameIdStr.trim().isEmpty()) {
            try {
                int gameId = Integer.parseInt(gameIdStr.trim());
                if (viewManager.getController().buyGame(viewManager.getCurrentUser(), gameId)) {
                    viewManager.showMessage("Game purchased successfully!");
                } else {
                    viewManager.showError("Purchase failed!");
                }
            } catch (NumberFormatException e) {
                viewManager.showError("Please enter a valid Game ID");
            }
        }
    }
    /**
     * Show the add to wishlist dialog
     */
    public void showAddToWishlistDialog() {
        List<Optional<VideoGames>> allGames = viewManager.getController().getAllGames();
        List<String> gameTitles = new ArrayList<>();
        Map<String, Integer> titleToIdMap = new HashMap<>();
        
        for (java.util.Optional<VideoGames> gameOpt : allGames) {
            if (gameOpt.isPresent()) {
                VideoGames game = gameOpt.get();
                String displayText = game.getTitle() + " - $" + game.getPrice();
                gameTitles.add(displayText);
                titleToIdMap.put(displayText, game.getGameID());
            }
        }
        if (gameTitles.isEmpty()) {
            viewManager.showError("No games available to add to wishlist");
            return;
        }
        String[] gameArray = gameTitles.toArray(new String[0]);
        String selectedGame = (String) JOptionPane.showInputDialog(
            viewManager.getMainFrame(),
            "Select a game to add to your wishlist:",
            "Add to Wishlist",
            JOptionPane.QUESTION_MESSAGE,
            null,
            gameArray,
            gameArray[0]
        );
        if (selectedGame != null) {
            int gameId = titleToIdMap.get(selectedGame);
            if (viewManager.getController().addToWishlist(viewManager.getCurrentUser(), gameId)) {
                viewManager.showMessage("Game added to wishlist!");
            } else {
                viewManager.showError("Failed to add to wishlist! Game may already be in your wishlist.");
            }
        }
    }
    /**
     * Show the add review dialog
     */
    public void showAddReviewDialog() {
        JTextField gameIdField = new JTextField(10);
        JTextField ratingField = new JTextField(10);
        JTextArea commentArea = new JTextArea(5, 20);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Game ID:"));
        panel.add(gameIdField);
        panel.add(new JLabel("Rating (1-5):"));
        panel.add(ratingField);
        panel.add(new JLabel("Comment:"));
        panel.add(new JScrollPane(commentArea));
        int result = JOptionPane.showConfirmDialog(viewManager.getMainFrame(), panel, "Add Review", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int gameId = Integer.parseInt(gameIdField.getText());
                int rating = Integer.parseInt(ratingField.getText());
                if (rating < 1 || rating > 5) {
                    viewManager.showError("Rating must be between 1 and 5!");
                    return;
                }
                if (viewManager.getController().addReview(viewManager.getCurrentUser(), gameId, rating, commentArea.getText())) {
                    viewManager.showMessage("Review added successfully!");
                } else {
                    viewManager.showError("Failed to add review!");
                }
            } catch (NumberFormatException e) {
                viewManager.showError("Please enter valid numbers for Game ID and Rating");
            }
        }
    }
    /**
     * Show the create game dialog
     */
    public void showCreateGameDialog() {
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
        int result = JOptionPane.showConfirmDialog(viewManager.getMainFrame(), panel, "Create New Game", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                double price = Double.parseDouble(priceField.getText());
                if (viewManager.getController().createGame(viewManager.getCurrentUser(), titleField.getText(), price, 
                        descriptionArea.getText(), requirementsArea.getText(), releaseDateField.getText())) {
                    viewManager.showMessage("Game created successfully!");
                } else {
                    viewManager.showError("Failed to create game!");
                }
            } catch (NumberFormatException e) {
                viewManager.showError("Please enter a valid price");
            }
        }
    }
    /**
     * Show the block user dialog
     */
    public void showBlockUserDialog() {
        String userIdStr = JOptionPane.showInputDialog(viewManager.getMainFrame(), "Enter User ID to block:");
        if (userIdStr != null) {
            try {
                int userId = Integer.parseInt(userIdStr);
                if (viewManager.getController().blockUser(userId)) {
                    viewManager.showMessage("User blocked successfully!");
                } else {
                    viewManager.showError("Failed to block user!");
                }
            } catch (NumberFormatException e) {
                viewManager.showError("Please enter a valid User ID");
            }
        }
    }
    /**
     * Show the unblock user dialog
     */
    public void showUnblockUserDialog() {
        String userIdStr = JOptionPane.showInputDialog(viewManager.getMainFrame(), "Enter User ID to unblock:");
        if (userIdStr != null) {
            try {
                int userId = Integer.parseInt(userIdStr);
                if (viewManager.getController().unblockUser(userId)) {
                    viewManager.showMessage("User unblocked successfully!");
                } else {
                    viewManager.showError("Failed to unblock user!");
                }
            } catch (NumberFormatException e) {
                viewManager.showError("Please enter a valid User ID");
            }
        }
    }
    /**
     * Show the admin operations dialog
     */
    public void showAdminOperations() {
        if (viewManager.getCurrentUser() != null && viewManager.getController().isAdmin(viewManager.getCurrentUser())) {
            String[] options = {"Block User", "Unblock User", "View All Users"};
            int choice = JOptionPane.showOptionDialog(viewManager.getMainFrame(), 
                "Select Admin Operation", "Admin Operations",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
                null, options, options[0]);
            switch (choice) {
                case 0: showBlockUserDialog(); break;
                case 1: showUnblockUserDialog(); break;
                case 2: /* handled by main menu panel */ break;
            }
        } else {
            viewManager.showError("Admin access required!");
        }
    }
}
