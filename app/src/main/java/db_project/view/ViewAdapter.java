package db_project.view;

import db_project.data.Users;
import db_project.data.VideoGames;

/**
 * Adapter that makes View2 compatible with the existing Controller
 * that expects a View object.
 */
public class ViewAdapter {
    
    private final ViewManager viewManager;
    
    public ViewAdapter(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
    
    /**
     * Show a user - delegates to ViewManager
     */
    public void showUser(Users user) {
        viewManager.showUser(user);
    }
    
    /**
     * Show a videogame - delegates to ViewManager
     */
    public void showVideoGame(VideoGames videogame) {
        viewManager.showVideoGame(videogame);
    }
}
