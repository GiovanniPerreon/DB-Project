package db_project;

import db_project.model.Model;
import db_project.data.Users;
import db_project.data.VideoGames;
import db_project.data.Transactions;
import db_project.data.TransactionItems;
import db_project.data.Reviews;
import db_project.data.Wishlists;
import db_project.data.WishlistItems;
import db_project.data.Achievements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

// The controller provides a holistic description of how the outside world can
// interact with our application: each public method is written as
//
//   subject + action + object (e.g. user + clicked + preview)
//
// So just by reading all the methods we know of all the possible interactions
// that can happen in our app. This makes it simpler to track all the possible
// actions that can take place as the application grows.
//
public final class Controller {
    private final Model model;
    private final View view;
    private Users currentUser;

    public Controller(Model model, View view) {
        Objects.requireNonNull(model, "Controller created with null model");
        Objects.requireNonNull(view, "Controller created with null view");
        this.view = view;
        this.model = model;
        this.currentUser = null;
    }
    
    public void testAllTables() {
        var users = this.model.getUsers();
        var videogames = this.model.getVideoGames();
        var videogameDevelopers = this.model.getVideogameDevelopers();
        var genres = this.model.getGenres();
        var videogameGenres = this.model.getVideoGameGenres();
        var languages = this.model.getLanguages();
        var videogameLanguages = this.model.getVideogameLanguages();
        var transactions = this.model.getTransactions();
        var transactionItems = this.model.getTransactionItems();
        var reviews = this.model.getReviews();
        var wishlists = this.model.getWishlists();
        var wishlistItems = this.model.getWishlistItems();
        var achievements = this.model.getAchievements();
        Stream.of(users, videogames, videogameDevelopers, genres,
                  videogameGenres, languages, videogameLanguages, transactions,
                  transactionItems, reviews, wishlists, wishlistItems, achievements)
            .flatMap(List::stream)
            .forEach(optional -> optional.ifPresentOrElse(
                item -> System.out.println("Item: " + item),
                () -> System.out.println("Item not found")
            ));
    }

    // Authentication methods
    public boolean loginUser(String email, String password) {
        Optional<Users> user = model.findByEmailPassword(email, password);
        if (user.isPresent()) {
            this.currentUser = user.get();
            return true;
        }
        return false;
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    public boolean registerUser(String email, String password, String name, String surname, String birthDate) {
        try {
            return model.registerUser(email, password, name, surname, birthDate);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPublisher(Users user) {
        return user.isPublisher();
    }

    public boolean isAdmin(Users user) {
        return user.isAdministrator();
    }

    public boolean createGame(Users user, String title, double price, String description, String requirements, String releaseDate) {
        try {
            // Check if user is a publisher
            System.out.println("DEBUG: User " + user.getName() + " (ID: " + user.getUserID() + ") isPublisher: " + user.isPublisher());
            if (!user.isPublisher()) {
                System.out.println("DEBUG: User is not a publisher, cannot create game");
                return false;
            }
            
            System.out.println("DEBUG: Creating game for publisher " + user.getUserID());
            boolean result = model.createGame(user.getUserID(), title, price, description, requirements, releaseDate);
            System.out.println("DEBUG: Game creation result: " + result);
            return result;
            
        } catch (Exception e) {
            System.err.println("Error creating game: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public boolean addToWishlist(Users user, int gameId) {
        try {
            System.out.println("DEBUG: Controller.addToWishlist called with user=" + user.getUserID() + ", gameId=" + gameId);
            boolean result = model.addToWishlist(user.getUserID(), gameId);
            System.out.println("DEBUG: Controller.addToWishlist result: " + result);
            return result;
        } catch (Exception e) {
            System.err.println("DEBUG: Controller.addToWishlist exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<VideoGames> getUserWishlist(Users user) {
        try {
            System.out.println("DEBUG: Getting wishlist for user " + user.getUserID());
            
            // Get user's wishlist games directly from database
            List<VideoGames> wishlistGames = new ArrayList<>();
            
            // First find user's wishlist
            List<Optional<Wishlists>> wishlists = model.getWishlists();
            System.out.println("DEBUG: Found " + wishlists.size() + " total wishlists");
            
            int userWishlistId = -1;
            
            for (Optional<Wishlists> wishlistOpt : wishlists) {
                if (wishlistOpt.isPresent()) {
                    Wishlists wishlist = wishlistOpt.get();
                    System.out.println("DEBUG: Checking wishlist " + wishlist.getWishlistID() + " for user " + wishlist.getUserID());
                    if (wishlist.getUserID() == user.getUserID()) {
                        userWishlistId = wishlist.getWishlistID();
                        System.out.println("DEBUG: Found user's wishlist with ID: " + userWishlistId);
                        break;
                    }
                }
            }
            
            System.out.println("DEBUG: User " + user.getUserID() + " has wishlist ID: " + userWishlistId);
            
            if (userWishlistId != -1) {
                // Get wishlist items for this wishlist
                List<Optional<WishlistItems>> wishlistItems = model.getWishlistItems();
                System.out.println("DEBUG: Found " + wishlistItems.size() + " total wishlist items");
                
                List<Optional<VideoGames>> allGames = model.getVideoGames();
                System.out.println("DEBUG: Found " + allGames.size() + " total games");
                
                for (Optional<WishlistItems> itemOpt : wishlistItems) {
                    if (itemOpt.isPresent()) {
                        WishlistItems item = itemOpt.get();
                        System.out.println("DEBUG: Checking wishlist item - wishlist " + item.getWishlistID() + ", game " + item.getGameID());
                        
                        if (item.getWishlistID() == userWishlistId) {
                            int gameId = item.getGameID();
                            System.out.println("DEBUG: Found game " + gameId + " in user's wishlist");
                            
                            // Find the game with this ID
                            for (Optional<VideoGames> gameOpt : allGames) {
                                if (gameOpt.isPresent() && gameOpt.get().getGameID() == gameId) {
                                    wishlistGames.add(gameOpt.get());
                                    System.out.println("DEBUG: Added game '" + gameOpt.get().getTitle() + "' to wishlist");
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            
            System.out.println("DEBUG: Final wishlist contains " + wishlistGames.size() + " games");
            return wishlistGames;
            
        } catch (Exception e) {
            System.err.println("Error getting user wishlist: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }



    public boolean blockUser(int userId) {
        try {
            System.out.println("DEBUG: Blocking user " + userId);
            return model.updateUserBlockedStatus(userId, true);
        } catch (Exception e) {
            System.err.println("Error blocking user: " + e.getMessage());
            return false;
        }
    }

    public boolean unblockUser(int userId) {
        try {
            System.out.println("DEBUG: Unblocking user " + userId);
            return model.updateUserBlockedStatus(userId, false);
        } catch (Exception e) {
            System.err.println("Error unblocking user: " + e.getMessage());
            return false;
        }
    }

    public List<Optional<VideoGames>> getAllGames() {
        return model.getVideoGames();
    }

    public List<Optional<Users>> getAllUsers() {
        return model.getUsers();
    }

    public List<VideoGames> getTopGames(int limit) {
        try {

            List<Optional<VideoGames>> allGames = model.getVideoGames();
            return allGames.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted((g1, g2) -> Double.compare(g2.getAverageRating(), g1.getAverageRating()))
                .limit(limit)
                .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<Users> getLowRatedPublishers() {
        try {

            List<Optional<Users>> allUsers = model.getUsers();
            return allUsers.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(Users::isPublisher)
                .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            return List.of();
        }
    }

    public Optional<VideoGames> getMostBoughtGame() {
        try {

            List<Optional<VideoGames>> allGames = model.getVideoGames();
            return allGames.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean isGameInWishlist(Users user, int gameId) {
        try {
            List<VideoGames> wishlistGames = getUserWishlist(user);
            return wishlistGames.stream().anyMatch(game -> game.getGameID() == gameId);
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean removeFromWishlist(Users user, int gameId) {
        try {
            return model.removeFromWishlist(user.getUserID(), gameId);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Gets the user's collection of owned games
     */
    public List<VideoGames> getUserCollection(Users user) {
        try {
            System.out.println("DEBUG: Getting collection for user " + user.getUserID());
            
            List<VideoGames> collectionGames = new ArrayList<>();
            
            // Get all transactions for this user
            List<Optional<Transactions>> transactions = model.getTransactions();
            List<Optional<TransactionItems>> transactionItems = model.getTransactionItems();
            List<Optional<VideoGames>> allGames = model.getVideoGames();
            
            // Find all transaction IDs for this user
            List<Integer> userTransactionIds = new ArrayList<>();
            for (Optional<Transactions> transactionOpt : transactions) {
                if (transactionOpt.isPresent() && transactionOpt.get().getUserID() == user.getUserID()) {
                    userTransactionIds.add(transactionOpt.get().getTransactionID());
                }
            }
            
            System.out.println("DEBUG: Found " + userTransactionIds.size() + " transactions for user");
            
            // Get all games from these transactions
            for (Optional<TransactionItems> itemOpt : transactionItems) {
                if (itemOpt.isPresent()) {
                    TransactionItems item = itemOpt.get();
                    if (userTransactionIds.contains(item.getTransactionID())) {
                        // Find the game
                        for (Optional<VideoGames> gameOpt : allGames) {
                            if (gameOpt.isPresent() && gameOpt.get().getGameID() == item.getGameID()) {
                                collectionGames.add(gameOpt.get());
                                break;
                            }
                        }
                    }
                }
            }
            
            System.out.println("DEBUG: Found " + collectionGames.size() + " games in collection");
            return collectionGames;
            
        } catch (Exception e) {
            System.err.println("Error getting user collection: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Gets all achievements for all games
     */
    public List<Achievements> getAllAchievements() {
        // This method should be renamed to getUserAchievements and take a user parameter
        // For now, return empty list to avoid showing all achievements to all users
        return new ArrayList<>();
    }
    
    /**
     * Gets achievements for a specific user
     */
    public List<Achievements> getUserAchievements(Users user) {
        try {
            return model.getUserAchievements(user.getUserID()).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    /**
     * Buys a game for the user (adds to collection automatically)
     */
    public boolean buyGame(Users user, int gameId) {
        try {
            System.out.println("DEBUG: Buying game " + gameId + " for user " + user.getUserID());
            
            // Find the game to get its price
            Optional<VideoGames> gameOpt = model.getVideoGames().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(game -> game.getGameID() == gameId)
                .findFirst();
            
            if (!gameOpt.isPresent()) {
                System.out.println("DEBUG: Game not found");
                return false;
            }
            
            VideoGames game = gameOpt.get();
            double gamePrice = Double.parseDouble(game.getPrice());
            
            // Create a transaction and get the ID
            int transactionId = model.addTransaction(user.getUserID(), gamePrice);
            if (transactionId == -1) {
                System.out.println("DEBUG: Failed to create transaction");
                return false;
            }
            
            // Add the game to transaction items
            boolean itemAdded = model.addTransactionItem(transactionId, gameId);
            System.out.println("DEBUG: Game purchase result: " + itemAdded);
            
            return itemAdded;
            
        } catch (Exception e) {
            System.err.println("Error buying game: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks if user owns a game
     */
    public boolean userOwnsGame(Users user, int gameId) {
        try {
            List<VideoGames> collection = getUserCollection(user);
            return collection.stream().anyMatch(game -> game.getGameID() == gameId);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Adds a review for a game (only if user owns it, is not blocked, and hasn't already reviewed it)
     */
    public boolean addReview(Users user, int gameId, int rating, String comment) {
        try {
            // Check if user is blocked
            if (user.isBlocked()) {
                System.out.println("DEBUG: User is blocked, cannot add review");
                return false;
            }
            
            // Check if user owns the game
            if (!userOwnsGame(user, gameId)) {
                System.out.println("DEBUG: User does not own game " + gameId + ", cannot add review");
                return false;
            }
            
            // Check if user has already reviewed this game
            if (hasUserReviewedGame(user, gameId)) {
                System.out.println("DEBUG: User has already reviewed game " + gameId);
                return false;
            }
            
            System.out.println("DEBUG: Adding review for game " + gameId + " by user " + user.getUserID());
            return model.addReview(user.getUserID(), gameId, rating, comment);
            
        } catch (Exception e) {
            System.err.println("Error adding review: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks if a user has already reviewed a game
     */
    public boolean hasUserReviewedGame(Users user, int gameId) {
        try {
            return model.hasUserReviewedGame(user.getUserID(), gameId);
        } catch (Exception e) {
            System.err.println("Error checking if user has reviewed game: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks if a user can add a review for a game
     */
    public boolean canUserAddReview(Users user, int gameId) {
        return !user.isBlocked() && 
               userOwnsGame(user, gameId) && 
               !hasUserReviewedGame(user, gameId);
    }
    
    /**
     * Gets all genres for a specific game
     */
    public List<String> getGameGenres(int gameId) {
        try {
            return model.getVideoGameGenres().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(vgg -> vgg.getGameID() == gameId)
                .map(vgg -> vgg.getGenre())
                .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    /**
     * Gets all languages for a specific game
     */
    public List<String> getGameLanguages(int gameId) {
        try {
            return model.getVideogameLanguages().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(vgl -> vgl.getGameID() == gameId)
                .map(vgl -> vgl.getLanguageName())
                .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    /**
     * Gets all reviews for a specific game
     */
    public List<Reviews> getGameReviews(int gameId) {
        try {
            return model.getReviews().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(review -> review.getGameID() == gameId)
                .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    /**
     * Gets all achievements for a specific game
     */
    public List<Achievements> getGameAchievements(int gameId) {
        try {
            return model.getAchievements().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(achievement -> achievement.getGameID() == gameId)
                .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    /**
     * Adds a genre to a game (admin only)
     */
    public boolean addGenreToGame(Users user, int gameId, String genre) {
        try {
            if (!user.isAdministrator()) {
                System.out.println("DEBUG: User is not admin, cannot add genre to game");
                return false;
            }
            
            System.out.println("DEBUG: Admin adding genre " + genre + " to game " + gameId);
            return model.addGenreToGame(gameId, genre);
        } catch (Exception e) {
            System.err.println("Error adding genre to game: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Removes a genre from a game (admin only)
     */
    public boolean removeGenreFromGame(Users user, int gameId, String genre) {
        try {
            if (!user.isAdministrator()) {
                System.out.println("DEBUG: User is not admin, cannot remove genre from game");
                return false;
            }
            
            System.out.println("DEBUG: Admin removing genre " + genre + " from game " + gameId);
            return model.removeGenreFromGame(gameId, genre);
        } catch (Exception e) {
            System.err.println("Error removing genre from game: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Gets all available genres
     */
    public List<String> getAllGenres() {
        try {
            return model.getGenres().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(genre -> genre.getGenre())
                .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    /**
     * Gets publishers and developers with rating below average
     */
    public List<db_project.data.LeastRatedUser> getLeastRatedUsers() {
        try {
            return model.getLeastRatedUsers();
        } catch (Exception e) {
            System.err.println("Error getting least rated users: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public List<Optional<VideoGames>> getTop10NewestGames() {
        return model.getTop10NewestGames();
    }
    
    public List<Optional<VideoGames>> getTop10OldestGames() {
        return model.getTop10OldestGames();
    }
    
    public List<Optional<VideoGames>> getTop10HighestRatedGames() {
        return model.getTop10HighestRatedGames();
    }
    
    public List<Optional<VideoGames>> getTop10LowestRatedGames() {
        return model.getTop10LowestRatedGames();
    }
    
    public List<Optional<VideoGames>> getTop10MostExpensiveGames() {
        return model.getTop10MostExpensiveGames();
    }
    
    public List<Optional<VideoGames>> getTop10CheapestGames() {
        return model.getTop10CheapestGames();
    }
    
    public List<Optional<VideoGames>> getTop10MostSoldGames() {
        return model.getTop10MostSoldGames();
    }
    
    public List<Optional<VideoGames>> getTop10GamesByGenre(String genre) {
        return model.getTop10GamesByGenre(genre);
    }
}
