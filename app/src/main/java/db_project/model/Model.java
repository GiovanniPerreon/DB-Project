package db_project.model;

import java.util.List;
import java.util.Optional;

import db_project.data.Genres;
import db_project.data.Languages;
import db_project.data.LeastRatedUser;
import db_project.data.Reviews;
import db_project.data.TransactionItems;
import db_project.data.Transactions;
import db_project.data.Users;
import db_project.data.VideoGameGenres;
import db_project.data.VideoGames;
import db_project.data.Wishlists;
import db_project.data.Achievements;
import db_project.data.VideogameDevelopers;
import db_project.data.VideogameLanguages;
import db_project.data.WishlistItems;

public interface Model {
    /**
     * @param userId
     * @return an Optional of the user with the given ID.
     */
    Optional<Users> find(int userId);   
    
    /**
     * @param email user's email
     * @param password user's password
     * @return an Optional of the user with the given email and password.
     */
    Optional<Users> findByEmailPassword(String email, String password);

    /**
     * @return all users in the database.
     */
    List<Optional<Users>> getUsers();
    /**
     * @return all the videogames in the database.
     */
    List<Optional<VideoGames>> getVideoGames();
    /**
     * @return all the genres in the database.
     */
    List<Optional<Genres>> getGenres();
    /**
     * @return all the videogame genres in the database.
     */
    List<Optional<VideoGameGenres>> getVideoGameGenres();
    /**
     * @return all the languages in the database.
     */
    List<Optional<Languages>> getLanguages();
    /**
     * @return all the videogame languages in the database.
     */
    List<Optional<VideogameLanguages>> getVideogameLanguages();
    /**
     * @return all the transactions in the database.
     */
    List<Optional<Transactions>> getTransactions();
    
    /**
     * @return all the transactions for a specific user in the database.
     */
    List<Optional<Transactions>> getTransactionsByUser(int userID);
    
    /**
     * @return all the reviews in the database.
     */
    List<Optional<Reviews>> getReviews();
    /**
     * @return all the wishlists in the database.
     */
    List<Optional<Wishlists>> getWishlists();
    /**
     * @return all the achievements in the database.
     */
    List<Optional<Achievements>> getAchievements();
    
    /**
     * @param userID the user ID
     * @return all achievements for a specific user.
     */
    List<Optional<Achievements>> getUserAchievements(int userID);
    
    /**
     * @return all the videogame developers in the database.
     */
    List<Optional<VideogameDevelopers>> getVideogameDevelopers();
    /**
     * @return all the transaction items in the database.
     */
    List<Optional<TransactionItems>> getTransactionItems();
    /**
     * @return all the wishlist items in the database.
     */
    List<Optional<WishlistItems>> getWishlistItems();

    /**
     * Registers a new user in the database.
     * @param email user's email
     * @param password user's password
     * @param name user's name
     * @param surname user's surname
     * @param birthDate user's birth date
     * @return true if registration was successful, false otherwise
     */
    boolean registerUser(String email, String password, String name, String surname, String birthDate);

    /**
     * Adds a game to user's wishlist.
     * @param userId the user ID
     * @param gameId the game ID
     * @return true if addition was successful, false otherwise
     */
    boolean addToWishlist(int userId, int gameId);

    /**
     * Removes a game from user's wishlist.
     * @param userId the user ID
     * @param gameId the game ID
     * @return true if removal was successful, false otherwise
     */
    boolean removeFromWishlist(int userId, int gameId);
    
    /**
     * Checks if a game is in user's wishlist.
     * @param userId the user ID
     * @param gameId the game ID
     * @return true if game is in wishlist, false otherwise
     */
    boolean isGameInWishlist(int userId, int gameId);
    
    /**
     * Adds a transaction for a user.
     * @param userId the user ID
     * @param totalCost the total cost of the transaction
     * @return the ID of the created transaction, or -1 if creation failed
     */
    int addTransaction(int userId, double totalCost);
    
    /**
     * Adds an item to a transaction.
     * @param transactionId the transaction ID
     * @param gameId the game ID
     * @return true if item was added successfully, false otherwise
     */
    boolean addTransactionItem(int transactionId, int gameId);
    
    /**
     * Adds a review for a game.
     * @param userId the user ID
     * @param gameId the game ID
     * @param rating the rating (1-10)
     * @param comment the review comment
     * @return true if review was added successfully, false otherwise
     */
    boolean addReview(int userId, int gameId, int rating, String comment);
    
    /**
     * Checks if a user has already reviewed a game.
     * @param userId the user ID
     * @param gameId the game ID
     * @return true if user has reviewed the game, false otherwise
     */
    boolean hasUserReviewedGame(int userId, int gameId);
    
    /**
     * Updates the blocked status of a user.
     * @param userId the user ID
     * @param isBlocked true to block, false to unblock
     * @return true if update was successful, false otherwise
     */
    boolean updateUserBlockedStatus(int userId, boolean isBlocked);
    
    /**
     * Creates a new videogame.
     * @param publisherId the publisher ID
     * @param title the game title
     * @param price the game price
     * @param description the game description
     * @param requirements the game requirements
     * @param releaseDate the release date (YYYY-MM-DD format)
     * @return true if game was created successfully, false otherwise
     */
    boolean createGame(int publisherId, String title, double price, String description, String requirements, String releaseDate);
    
    /**
     * Adds a genre to a videogame.
     * @param gameId the game ID
     * @param genre the genre to add
     * @return true if genre was added successfully, false otherwise
     */
    boolean addGenreToGame(int gameId, String genre);
    
    /**
     * Removes a genre from a videogame.
     * @param gameId the game ID
     * @param genre the genre to remove
     * @return true if genre was removed successfully, false otherwise
     */
    boolean removeGenreFromGame(int gameId, String genre);
    
    /**
     * @return all the least rated users in the database.
     */
    List<LeastRatedUser> getLeastRatedUsers();
    
    /**
     * Updates the average rating for a game based on all its reviews.
     * @param gameId the game ID
     * @return true if average rating was updated successfully, false otherwise
     */
    boolean updateGameAverageRating(int gameId);
    
    /**
     * @return top 10 newest games.
     */
    List<Optional<VideoGames>> getTop10NewestGames();
    
    /**
     * @return top 10 oldest games.
     */
    List<Optional<VideoGames>> getTop10OldestGames();
    
    /**
     * @return top 10 highest rated games.
     */
    List<Optional<VideoGames>> getTop10HighestRatedGames();
    
    /**
     * @return top 10 lowest rated games.
     */
    List<Optional<VideoGames>> getTop10LowestRatedGames();
    
    /**
     * @return top 10 most expensive games.
     */
    List<Optional<VideoGames>> getTop10MostExpensiveGames();
    
    /**
     * @return top 10 cheapest games.
     */
    List<Optional<VideoGames>> getTop10CheapestGames();
    
    /**
     * @return top 10 most sold games.
     */
    List<Optional<VideoGames>> getTop10MostSoldGames();
    
    /**
     * @param genre the genre to filter by
     * @return top 10 games by genre.
     */
    List<Optional<VideoGames>> getTop10GamesByGenre(String genre);
    
    // Methods to get ALL games sorted (not just top 10)
    /**
     * @return all games sorted by newest first.
     */
    List<Optional<VideoGames>> getAllNewestGames();
    
    /**
     * @return all games sorted by oldest first.
     */
    List<Optional<VideoGames>> getAllOldestGames();
    
    /**
     * @return all games sorted by highest rated first.
     */
    List<Optional<VideoGames>> getAllHighestRatedGames();
    
    /**
     * @return all games sorted by lowest rated first.
     */
    List<Optional<VideoGames>> getAllLowestRatedGames();
    
    /**
     * @return all games sorted by most expensive first.
     */
    List<Optional<VideoGames>> getAllMostExpensiveGames();
    
    /**
     * @return all games sorted by cheapest first.
     */
    List<Optional<VideoGames>> getAllCheapestGames();
    
    /**
     * @return all games sorted by most sold first.
     */
    List<Optional<VideoGames>> getAllMostSoldGames();
    
    /**
     * @param genre the genre to filter by
     * @return all games by genre.
     */
    List<Optional<VideoGames>> getAllGamesByGenre(String genre);
    
    /**
     * Gets the ID of the last created game
     * @return the game ID of the last created game, or -1 if no game was created
     */
    int getLastCreatedGameID();
    
    /**
     * Adds a developer to a videogame.
     * @param developerID the developer user ID
     * @param gameID the game ID
     * @return true if developer was added successfully, false otherwise
     */
    boolean addDeveloperToGame(int developerID, int gameID);
    
    /**
     * Changes the discount of a game
     * @param gameId the game ID
     * @param newDiscount the new discount percentage (0-100)
     * @return true if discount was changed successfully, false otherwise
     */
    boolean changeGameDiscount(int gameId, double newDiscount);
}
