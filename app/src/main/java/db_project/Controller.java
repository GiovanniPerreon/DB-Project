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
import db_project.data.VideogameDevelopers;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public final class Controller {
    private final Model model;
    private Users currentUser;    
    /**
     * Constructor for View2 architecture
     * @param model The model instance
     * @param viewManager The ViewManager instance
     */
    public Controller(Model model) {
        Objects.requireNonNull(model, "Controller created with null model");
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
    /**
     * Checks if the given user is the developer of the given game.
     * @param user The user to check
     * @param gameID The game ID
     * @return true if user is a developer of the game
     */
    public boolean isDeveloperOfGame(Users user, int gameID) {
        return model.getVideogameDevelopers().stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .anyMatch(dev -> dev.getGameID() == gameID && dev.getDeveloperID() == user.getUserID());
    }
    public String getUserTypes(Users user) {
        return user.getUserTypes();
    }
    public boolean createGame(Users user, String title, double price, String description, String requirements, String releaseDate) {
        try {
            if (!user.isPublisher()) {
                return false;
            }
            return model.createGame(user.getUserID(), title, price, description, requirements, releaseDate);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean addToWishlist(Users user, int gameId) {
        try {
            if (userOwnsGame(user, gameId)) {
                return false;
            }
            if (isGameInWishlist(user, gameId)) {
                return false;
            }
            boolean result = model.addToWishlist(user.getUserID(), gameId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<VideoGames> getUserWishlist(Users user) {
        try {
            List<VideoGames> wishlistGames = new ArrayList<>();
            List<Optional<Wishlists>> wishlists = model.getWishlists();
            int userWishlistId = -1;  
            for (Optional<Wishlists> wishlistOpt : wishlists) {
                if (wishlistOpt.isPresent()) {
                    Wishlists wishlist = wishlistOpt.get();
                    if (wishlist.getUserID() == user.getUserID()) {
                        userWishlistId = wishlist.getWishlistID();
                        break;
                    }
                }
            }
            if (userWishlistId != -1) {
                List<Optional<WishlistItems>> wishlistItems = model.getWishlistItems();
                List<Optional<VideoGames>> allGames = model.getVideoGames();    
                for (Optional<WishlistItems> itemOpt : wishlistItems) {
                    if (itemOpt.isPresent()) {
                        WishlistItems item = itemOpt.get();
                        if (item.getWishlistID() == userWishlistId) {
                            int gameId = item.getGameID();
                            for (Optional<VideoGames> gameOpt : allGames) {
                                if (gameOpt.isPresent() && gameOpt.get().getGameID() == gameId) {
                                    wishlistGames.add(gameOpt.get());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            return wishlistGames;
        } catch (Exception e) {
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
     * Checks if a user can add a game to their wishlist
     * (user must not own the game and game must not already be in wishlist)
     */
    public boolean canAddToWishlist(Users user, int gameId) {
        return !userOwnsGame(user, gameId) && !isGameInWishlist(user, gameId);
    }
    /**
     * Gets the user's collection of owned games
     */
    public List<VideoGames> getUserCollection(Users user) {
        try {
            List<VideoGames> collectionGames = new ArrayList<>();
            List<Optional<Transactions>> transactions = model.getTransactions();
            List<Optional<TransactionItems>> transactionItems = model.getTransactionItems();
            List<Optional<VideoGames>> allGames = model.getVideoGames();
            List<Integer> userTransactionIds = new ArrayList<>();
            for (Optional<Transactions> transactionOpt : transactions) {
                if (transactionOpt.isPresent() && transactionOpt.get().getUserID() == user.getUserID()) {
                    userTransactionIds.add(transactionOpt.get().getTransactionID());
                }
            }
            for (Optional<TransactionItems> itemOpt : transactionItems) {
                if (itemOpt.isPresent()) {
                    TransactionItems item = itemOpt.get();
                    if (userTransactionIds.contains(item.getTransactionID())) {
                        for (Optional<VideoGames> gameOpt : allGames) {
                            if (gameOpt.isPresent() && gameOpt.get().getGameID() == item.getGameID()) {
                                collectionGames.add(gameOpt.get());
                                break;
                            }
                        }
                    }
                }
            }
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
            int transactionId = model.addTransaction(user.getUserID(), gamePrice);
            if (transactionId == -1) {
                System.out.println("DEBUG: Failed to create transaction");
                return false;
            }
            boolean itemAdded = model.addTransactionItem(transactionId, gameId);
            if (itemAdded && isGameInWishlist(user, gameId)) {
                boolean removedFromWishlist = removeFromWishlist(user, gameId);
                System.out.println("DEBUG: Removed game from wishlist after purchase: " + removedFromWishlist);
            }
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
            if (user.isBlocked()) {
                System.out.println("DEBUG: User is blocked, cannot add review");
                return false;
            }
            if (!userOwnsGame(user, gameId)) {
                System.out.println("DEBUG: User does not own game " + gameId + ", cannot add review");
                return false;
            }
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
     * Adds a genre to a game (admin or developer only)
     */
    public boolean addGenreToGame(Users user, int gameId, String genre) {
        try {
            if (!user.isAdministrator() && !isDeveloperOfGame(user, gameId)) {
                System.out.println("DEBUG: User is not admin or developer, cannot add genre to game");
                return false;
            }
            
            System.out.println("DEBUG: Admin or developer adding genre " + genre + " to game " + gameId);
            return model.addGenreToGame(gameId, genre);
        } catch (Exception e) {
            System.err.println("Error adding genre to game: " + e.getMessage());
            return false;
        }
    }
    /**
     * Removes a genre from a game (admin or developer only)
     */
    public boolean removeGenreFromGame(Users user, int gameId, String genre) {
        try {
            if (!user.isAdministrator() && !isDeveloperOfGame(user, gameId)) {
                System.out.println("DEBUG: User is not admin or developer, cannot remove genre from game");
                return false;
            }
            
            System.out.println("DEBUG: Admin or developer removing genre " + genre + " from game " + gameId);
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
    /**
     * Gets the reviewer name for a review
     */
    public String getReviewerName(int userId) {
        try {
            Optional<Users> user = model.find(userId);
            if (user.isPresent()) {
                Users u = user.get();
                return u.getName() + " " + u.getSurname();
            }
            return "Unknown User";
        } catch (Exception e) {
            return "Unknown User";
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
    public List<Optional<VideoGames>> getAllNewestGames() {
        return model.getAllNewestGames();
    }
    public List<Optional<VideoGames>> getAllOldestGames() {
        return model.getAllOldestGames();
    }
    public List<Optional<VideoGames>> getAllHighestRatedGames() {
        return model.getAllHighestRatedGames();
    }
    public List<Optional<VideoGames>> getAllLowestRatedGames() {
        return model.getAllLowestRatedGames();
    }
    public List<Optional<VideoGames>> getAllMostExpensiveGames() {
        return model.getAllMostExpensiveGames();
    }
    
    public List<Optional<VideoGames>> getAllCheapestGames() {
        return model.getAllCheapestGames();
    }
    public List<Optional<VideoGames>> getAllMostSoldGames() {
        return model.getAllMostSoldGames();
    }
    public List<Optional<VideoGames>> getAllGamesByGenre(String genre) {
        return model.getAllGamesByGenre(genre);
    }
    public boolean createGame(Users user, String title, double price, String description, String requirements, String releaseDate, String developerID) {
        try {
            if (!user.isPublisher()) {
                return false;
            }
            
            // Validate developer ID
            if (developerID == null || developerID.trim().isEmpty()) {
                return false;
            }
            
            int devID;
            try {
                devID = Integer.parseInt(developerID.trim());
            } catch (NumberFormatException e) {
                return false;
            }
            
            // Check if developer user exists and is actually a developer
            Optional<Users> developerUser = model.find(devID);
            if (developerUser.isEmpty() || !developerUser.get().isDeveloper()) {
                return false;
            }
            
            boolean gameCreated = model.createGame(user.getUserID(), title, price, description, requirements, releaseDate);
            
            // If game was created successfully, add developer
            if (gameCreated) {
                int gameID = model.getLastCreatedGameID();
                if (gameID > 0) {
                    return model.addDeveloperToGame(devID, gameID);
                }
            }
            
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static class GameCreationResult {
        public final boolean success;
        public final String errorMessage;
        
        public GameCreationResult(boolean success, String errorMessage) {
            this.success = success;
            this.errorMessage = errorMessage;
        }
        
        public static GameCreationResult success() {
            return new GameCreationResult(true, null);
        }
        
        public static GameCreationResult failure(String errorMessage) {
            return new GameCreationResult(false, errorMessage);
        }
    }
    
    public GameCreationResult createGameWithValidation(Users user, String title, double price, String description, String requirements, String releaseDate, String developerID) {
        try {
            if (!user.isPublisher()) {
                return GameCreationResult.failure("Only publishers can create games.");
            }
            
            // Validate developer ID
            if (developerID == null || developerID.trim().isEmpty()) {
                return GameCreationResult.failure("Developer ID is required.");
            }
            
            int devID;
            try {
                devID = Integer.parseInt(developerID.trim());
            } catch (NumberFormatException e) {
                return GameCreationResult.failure("Developer ID must be a valid number.");
            }
            
            // Check if developer user exists and is actually a developer
            Optional<Users> developerUser = model.find(devID);
            if (developerUser.isEmpty()) {
                return GameCreationResult.failure("No user found with ID " + devID + ".");
            }
            
            if (!developerUser.get().isDeveloper()) {
                return GameCreationResult.failure("User with ID " + devID + " is not a developer.");
            }
            
            boolean gameCreated = model.createGame(user.getUserID(), title, price, description, requirements, releaseDate);
            
            if (!gameCreated) {
                return GameCreationResult.failure("Failed to create the game in the database.");
            }
            
            // If game was created successfully, add developer
            int gameID = model.getLastCreatedGameID();
            if (gameID <= 0) {
                return GameCreationResult.failure("Failed to retrieve the created game ID.");
            }
            
            if (!model.addDeveloperToGame(devID, gameID)) {
                return GameCreationResult.failure("Game was created but failed to associate it with the developer.");
            }
            
            return GameCreationResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return GameCreationResult.failure("An unexpected error occurred: " + e.getMessage());
        }
    }
    public Model getModel() {
        return model;
    }
    /**
     * Gets the publisher name for a game
     */
    public String getGamePublisherName(int gameId) {
        try {
            // Find the game first to get publisher ID
            Optional<VideoGames> gameOpt = model.getVideoGames().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(game -> game.getGameID() == gameId)
                .findFirst();
            
            if (gameOpt.isPresent()) {
                int publisherId = gameOpt.get().getPublisherID();
                // Find the publisher user
                Optional<Users> publisherOpt = model.find(publisherId);
                if (publisherOpt.isPresent()) {
                    Users publisher = publisherOpt.get();
                    return publisher.getName() + " " + publisher.getSurname();
                }
            }
            return "Unknown Publisher";
        } catch (Exception e) {
            return "Unknown Publisher";
        }
    }
    
    /**
     * Gets the developer names for a game
     */
    public List<String> getGameDeveloperNames(int gameId) {
        try {
            List<String> developerNames = new ArrayList<>();
            List<Optional<VideogameDevelopers>> videogameDevelopers = model.getVideogameDevelopers();
            
            for (Optional<VideogameDevelopers> devOpt : videogameDevelopers) {
                if (devOpt.isPresent()) {
                    VideogameDevelopers dev = devOpt.get();
                    if (dev.getGameID() == gameId) {
                        // Find the developer user
                        Optional<Users> developerUserOpt = model.find(dev.getDeveloperID());
                        if (developerUserOpt.isPresent()) {
                            Users developer = developerUserOpt.get();
                            developerNames.add(developer.getName() + " " + developer.getSurname());
                        }
                    }
                }
            }
            
            if (developerNames.isEmpty()) {
                developerNames.add("Unknown Developer");
            }
            
            return developerNames;
        } catch (Exception e) {
            return List.of("Unknown Developer");
        }
    }
    /**
     * Class to hold transaction details for display
     */
    public static class TransactionDetail {
        private final int transactionId;
        private final String gameTitle;
        private final double finalAmount;
        
        public TransactionDetail(int transactionId, String gameTitle, double finalAmount) {
            this.transactionId = transactionId;
            this.gameTitle = gameTitle;
            this.finalAmount = finalAmount;
        }
        
        public int getTransactionId() { return transactionId; }
        public String getGameTitle() { return gameTitle; }
        public double getFinalAmount() { return finalAmount; }
    }

    /**
     * Gets the user's transaction history with game details
     */
    public List<TransactionDetail> getUserTransactions(Users user) {
        try {
            List<TransactionDetail> transactionDetails = new ArrayList<>();
            List<Optional<Transactions>> transactions = model.getTransactions();
            List<Optional<TransactionItems>> transactionItems = model.getTransactionItems();
            List<Optional<VideoGames>> allGames = model.getVideoGames();
            
            // Get all user transactions
            for (Optional<Transactions> transactionOpt : transactions) {
                if (transactionOpt.isPresent() && transactionOpt.get().getUserID() == user.getUserID()) {
                    Transactions transaction = transactionOpt.get();
                    int transactionId = transaction.getTransactionID();
                    
                    // Find transaction items for this transaction
                    for (Optional<TransactionItems> itemOpt : transactionItems) {
                        if (itemOpt.isPresent()) {
                            TransactionItems item = itemOpt.get();
                            if (item.getTransactionID() == transactionId) {
                                // Find the game details
                                for (Optional<VideoGames> gameOpt : allGames) {
                                    if (gameOpt.isPresent() && gameOpt.get().getGameID() == item.getGameID()) {
                                        VideoGames game = gameOpt.get();
                                        
                                        // Calculate final amount (price with discount applied)
                                        double originalPrice = Double.parseDouble(game.getPrice());
                                        double discountPercent = game.getDiscount() != null ? 
                                            Double.parseDouble(game.getDiscount()) : 0.0;
                                        double finalAmount = originalPrice * (1.0 - discountPercent / 100.0);
                                        
                                        transactionDetails.add(new TransactionDetail(
                                            transactionId, 
                                            game.getTitle() != null ? game.getTitle() : "Unknown Game", 
                                            finalAmount
                                        ));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            return transactionDetails;
        } catch (Exception e) {
            System.err.println("Error getting user transactions: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    /**
     * Changes the discount of a game
     */
    public boolean changeGameDiscount(int gameId, double newDiscount) {
        try {
            return model.changeGameDiscount(gameId, newDiscount);
        } catch (Exception e) {
            System.err.println("Error changing game discount: " + e.getMessage());
            return false;
        }
    }
    /**
     * Reloads a specific game from the database
     */
    public Optional<VideoGames> reloadGame(int gameId) {
        try {
            List<Optional<VideoGames>> allGames = model.getVideoGames();
            return allGames.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(game -> game.getGameID() == gameId)
                .findFirst();
        } catch (Exception e) {
            System.err.println("Error reloading game: " + e.getMessage());
            return Optional.empty();
        }
    }
}
