package db_project;

import db_project.view.ViewManager;
import db_project.data.Users;
import db_project.data.VideoGames;

/**
 * View2 - A modular refactored version of the original View class.
 * 
 * This class maintains the exact same public API as the original View class
 * but internally uses a modular architecture with separate components:
 * - ViewManager: Main coordinator
 * - LoginPanel: User authentication
 * - MainMenuPanel: Navigation and layout
 * - UserDashboardPanel: User info display
 * - GameBrowserPanel: Game browsing and filtering
 * - DialogManager: All dialog interactions
 * - UIStyler: Consistent styling utilities
 * 
 * The functionality and appearance remain identical to the original View.
 */
public final class View {
    
    private final ViewManager viewManager;
    
    /**
     * Constructor - creates the modular view system
     * @param onClose cleanup action for database connection
     */
    public View(Runnable onClose) {
        this.viewManager = new ViewManager(onClose);
    }
    
    /**
     * Set the controller - same API as original View
     * @param controller the controller instance
     */
    public void setController(Controller controller) {
        viewManager.setController(controller);
    }
    
    /**
     * Show a user in the view - same API as original View
     * @param user the user to display
     */
    public void showUser(Users user) {
        viewManager.showUser(user);
    }
    
    /**
     * Show a videogame in the view - same API as original View
     * @param videogame the videogame to display
     */
    public void showVideoGame(VideoGames videogame) {
        viewManager.showVideoGame(videogame);
    }
    
    /**
     * Get the internal ViewManager (for testing or advanced usage)
     * @return the view manager instance
     */
    ViewManager getViewManager() {
        return viewManager;
    }
}
