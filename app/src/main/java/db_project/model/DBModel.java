package db_project.model;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

import db_project.data.Genres;
import db_project.data.Languages;
import db_project.data.Queries;
import db_project.data.Reviews;
import db_project.data.Transactions;
import db_project.data.Users;
import db_project.data.VideoGameGenres;
import db_project.data.VideoGames;
import db_project.data.Achievements;
import db_project.data.VideogameDevelopers;
import db_project.data.VideogameLanguages;
import db_project.data.TransactionItems;
import db_project.data.WishlistItems;
import db_project.data.Wishlists;

public final class DBModel implements Model {
    private final Connection connection;
    private int lastCreatedGameID = -1;

    public DBModel(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
    }
    /**
     * @param userId
     * @return an Optional of the user with the given ID.
     */
    @Override
    public Optional<Users> find(int userId) {
        return Users.DAO.find(connection, userId);
    }
    /**
     * @return all users in the database.
     */
    @Override
    public List<Optional<Users>> getUsers() {
        return Users.DAO.list(this.connection);
    }
    /**
     * @return all the videogames in the database.
     */
    @Override
    public List<Optional<VideoGames>> getVideoGames() {
        return VideoGames.DAO.list(this.connection);
    }
    /**
     * @return all the videogame developers in the database.
     */
    @Override
    public List<Optional<VideogameDevelopers>> getVideogameDevelopers() {
        return VideogameDevelopers.DAO.list(this.connection);
    }
    /**
     * @return all the genres in the database.
     */
    @Override
    public List<Optional<Genres>> getGenres() {
        return Genres.DAO.list(this.connection);
    }
    /**
     * @return all the videogame genres in the database.
     */
    @Override
    public List<Optional<VideoGameGenres>> getVideoGameGenres() {
        return VideoGameGenres.DAO.list(this.connection);
    }
    /**
     * @return all the languages in the database.
     */
    @Override
    public List<Optional<Languages>> getLanguages() {
        return Languages.DAO.list(this.connection);
    }
    /**
     * @return all the videogame languages in the database.
     */
    @Override
    public List<Optional<VideogameLanguages>> getVideogameLanguages() {
        return VideogameLanguages.DAO.list(this.connection);
    }
    /**
     * @return all the transactions in the database.
     */
    @Override
    public List<Optional<Transactions>> getTransactions() {
        return Transactions.DAO.list(this.connection);
    }
    /**
     * @return all the transaction items in the database.
     */
    @Override
    public List<Optional<TransactionItems>> getTransactionItems() {
        return TransactionItems.DAO.list(this.connection);
    }
    /**
     * @return all the reviews in the database.
     */
    @Override
    public List<Optional<Reviews>> getReviews() {
        return Reviews.DAO.list(this.connection);
    }
    /**
     * @return all the wishlists in the database.
     */
    @Override
    public List<Optional<Wishlists>> getWishlists() {
        return Wishlists.DAO.list(this.connection);
    }
    /**
     * @return all the wishlist items in the database.
     */
    @Override
    public List<Optional<WishlistItems>> getWishlistItems() {
        return WishlistItems.DAO.list(this.connection);
    }
    /**
     * @return all the achievements in the database.
     */
    @Override
    public List<Optional<Achievements>> getAchievements() {
        return Achievements.DAO.list(this.connection);
    }
    
    @Override
    public List<Optional<Achievements>> getUserAchievements(int userID) {
        return Achievements.DAO.getUserAchievements(this.connection, userID);
    }

    /**
     * Registers a new user in the database.
     */
    @Override
    public boolean registerUser(String email, String password, String name, String surname, String birthDate) {
        return Users.DAO.addUser(this.connection, email, password, name, surname, birthDate, false, false, false, false);
    }

    @Override
    public Optional<Users> findByEmailPassword(String email, String password) {
        return Users.DAO.findByEmailPassword(this.connection, email, password);
    }

    @Override
    public boolean addToWishlist(int userId, int gameId) {
        try {
            boolean result = WishlistItems.DAO.addToWishlist(this.connection, userId, gameId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeFromWishlist(int userId, int gameId) {
        try {
            return WishlistItems.DAO.removeFromWishlist(this.connection, userId, gameId);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isGameInWishlist(int userId, int gameId) {
        try {
            return WishlistItems.DAO.isGameInWishlist(this.connection, userId, gameId);
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public int addTransaction(int userId, double totalCost) {
        try {
            int transactionId = Transactions.DAO.add(connection, userId, totalCost);
            return transactionId;
        } catch (Exception e) {
            return -1;
        }
    }
    
    @Override
    public boolean addTransactionItem(int transactionId, int gameId) {
        try {
            // Actually insert the transaction item into the database
            TransactionItems.DAO.addItem(connection, transactionId, gameId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public boolean addReview(int userId, int gameId, int rating, String comment) {
        try (PreparedStatement stmt = connection.prepareStatement(Queries.ADD_REVIEW)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, gameId);
            stmt.setInt(3, rating);
            stmt.setString(4, comment);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Automatically update the game's average rating
                updateGameAverageRating(gameId);
                return true;
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }
    
    @Override
    public boolean hasUserReviewedGame(int userId, int gameId) {
        try (PreparedStatement stmt = connection.prepareStatement(Queries.FIND_REVIEW)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, gameId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if a review exists
            }
        } catch (SQLException e) {
            return false;
        }
    }
    
    @Override
    public boolean updateUserBlockedStatus(int userId, boolean isBlocked) {
        try (PreparedStatement stmt = connection.prepareStatement(Queries.UPDATE_USER_BLOCKED)) {
            stmt.setBoolean(1, isBlocked);
            stmt.setInt(2, userId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    @Override
    public boolean createGame(int publisherId, String title, double price, String description, String requirements, String releaseDate) {
        try (PreparedStatement stmt = connection.prepareStatement(Queries.ADD_VIDEOGAME, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, publisherId);
            stmt.setString(2, title);
            stmt.setDouble(3, price);
            stmt.setString(4, description);
            stmt.setString(5, requirements);
            stmt.setDouble(6, 2.5);
            stmt.setString(7, releaseDate);
            stmt.setInt(8, 0);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        lastCreatedGameID = generatedKeys.getInt(1);
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean addGenreToGame(int gameId, String genre) {
        try (PreparedStatement stmt = connection.prepareStatement(Queries.ADD_VIDEOGAME_GENRE)) {
            stmt.setInt(1, gameId);
            stmt.setString(2, genre);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    @Override
    public boolean removeGenreFromGame(int gameId, String genre) {
        try (PreparedStatement stmt = connection.prepareStatement(Queries.REMOVE_VIDEOGAME_GENRE)) {
            stmt.setInt(1, gameId);
            stmt.setString(2, genre);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    @Override
    public List<db_project.data.LeastRatedUser> getLeastRatedUsers() {
        return db_project.data.LeastRatedUser.DAO.getLeastRatedUsers(connection);
    }
    
    @Override
    public List<Optional<VideoGames>> getTop10NewestGames() {
        return VideoGames.DAO.getTop10NewestGames(connection);
    }
    
    @Override
    public List<Optional<VideoGames>> getTop10OldestGames() {
        return VideoGames.DAO.getTop10OldestGames(connection);
    }
    
    @Override
    public List<Optional<VideoGames>> getTop10HighestRatedGames() {
        return VideoGames.DAO.getTop10HighestRatedGames(connection);
    }
    
    @Override
    public List<Optional<VideoGames>> getTop10LowestRatedGames() {
        return VideoGames.DAO.getTop10LowestRatedGames(connection);
    }
    
    @Override
    public List<Optional<VideoGames>> getTop10MostExpensiveGames() {
        return VideoGames.DAO.getTop10MostExpensiveGames(connection);
    }
    
    @Override
    public List<Optional<VideoGames>> getTop10CheapestGames() {
        return VideoGames.DAO.getTop10CheapestGames(connection);
    }
    
    @Override
    public List<Optional<VideoGames>> getTop10MostSoldGames() {
        return VideoGames.DAO.getTop10MostSoldGames(connection);
    }
    
    @Override
    public List<Optional<VideoGames>> getTop10GamesByGenre(String genre) {
        return VideoGames.DAO.getTop10GamesByGenre(connection, genre);
    }
    
    // Implementation of methods to get ALL games sorted (not just top 10)
    @Override
    public List<Optional<VideoGames>> getAllNewestGames() {
        return VideoGames.DAO.getAllNewestGames(connection);
    }
    
    @Override
    public List<Optional<VideoGames>> getAllOldestGames() {
        return VideoGames.DAO.getAllOldestGames(connection);
    }
    
    @Override
    public List<Optional<VideoGames>> getAllHighestRatedGames() {
        return VideoGames.DAO.getAllHighestRatedGames(connection);
    }
    
    @Override
    public List<Optional<VideoGames>> getAllLowestRatedGames() {
        return VideoGames.DAO.getAllLowestRatedGames(connection);
    }
    
    @Override
    public List<Optional<VideoGames>> getAllMostExpensiveGames() {
        return VideoGames.DAO.getAllMostExpensiveGames(connection);
    }
    
    @Override
    public List<Optional<VideoGames>> getAllCheapestGames() {
        return VideoGames.DAO.getAllCheapestGames(connection);
    }
    
    @Override
    public List<Optional<VideoGames>> getAllMostSoldGames() {
        return VideoGames.DAO.getAllMostSoldGames(connection);
    }
    
    @Override
    public List<Optional<VideoGames>> getAllGamesByGenre(String genre) {
        return VideoGames.DAO.getAllGamesByGenre(connection, genre);
    }
    
    @Override
    public boolean updateGameAverageRating(int gameId) {
        try (PreparedStatement stmt = connection.prepareStatement(Queries.UPDATE_GAME_AVERAGE_RATING)) {
            stmt.setInt(1, gameId);
            stmt.setInt(2, gameId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public int getLastCreatedGameID() {
        return lastCreatedGameID;
    }
    
    @Override
    public boolean addDeveloperToGame(int developerID, int gameID) {
        try (PreparedStatement stmt = connection.prepareStatement(Queries.ADD_VIDEOGAME_DEVELOPER)) {
            stmt.setInt(1, developerID);
            stmt.setInt(2, gameID);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean changeGameDiscount(int gameId, double newDiscount) {
        try (PreparedStatement stmt = connection.prepareStatement(
            "UPDATE videogames SET discount = ? WHERE gameID = ?")) {
            stmt.setDouble(1, newDiscount);
            stmt.setInt(2, gameId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
