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
import db_project.data.Users;

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
    /**
     * Shows a styled create game dialog
     */
    public void showCreateGameDialog() {
        // Create a developer validator that checks if the developer exists and is actually a developer
        UIStyler.DeveloperValidator validator = (developerID) -> {
            try {
                int devID = Integer.parseInt(developerID);
                Optional<Users> developerUser = viewManager.getController().getModel().find(devID);
                if (developerUser.isEmpty()) {
                    return "No user found with ID " + devID + ".";
                }
                if (!developerUser.get().isDeveloper()) {
                    return "User with ID " + devID + " is not a developer.";
                }
                return null; // Valid developer
            } catch (NumberFormatException e) {
                return "Developer ID must be a valid number.";
            }
        };
        
        UIStyler.GameCreationResult result = UIStyler.showStyledGameCreationDialog(viewManager.getMainFrame(), validator);
        
        if (result != null) {
            try {
                double price = Double.parseDouble(result.price);
                
                // Since Developer ID is now required and validated, use the method with developer ID
                boolean success = viewManager.getController().createGame(
                        viewManager.getCurrentUser(), 
                        result.title, 
                        price, 
                        result.description, 
                        result.requirements, 
                        result.releaseDate,
                        result.developerID);
                
                if (success) {
                    viewManager.showMessage("Game created successfully! Developer ID " + result.developerID + " has been added to the game.");
                } else {
                    viewManager.showError("Failed to create game! Please try again.");
                }
            } catch (NumberFormatException e) {
                viewManager.showError("Please enter a valid price");
            } catch (Exception e) {
                viewManager.showError("Error creating game: " + e.getMessage());
                e.printStackTrace();
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
