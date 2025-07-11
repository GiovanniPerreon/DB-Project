package db_project.model;

import java.util.List;
import java.sql.Connection;
import java.util.Objects;
import java.util.Optional;

import db_project.data.Users;
import db_project.data.VideoGames;
import db_project.data.Achievements;
import db_project.data.VideogameDevelopers;
import db_project.data.TransactionItems;
import db_project.data.WishlistItems;

public final class DBModel implements Model {
    private final Connection connection;
    private List<Optional<Users>> users;
    private List<Optional<VideoGames>> videogames;

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
        this.users = Users.DAO.list(this.connection);
        return this.users;
    }
    /**
     * @return all the videogames in the database.
     */
    @Override
    public List<Optional<VideoGames>> getVideoGames() {
        this.videogames = VideoGames.DAO.list(this.connection);
        return this.videogames;
    }
    /**
     * @return all the transactions in the database.
     */
    @Override
    public List<Optional<db_project.data.Transactions>> getTransactions() {
        return db_project.data.Transactions.DAO.list(this.connection);
    }
    /*
     * @return all the reviews in the database.
     */
    @Override
    public List<Optional<db_project.data.Reviews>> getReviews() {
        return db_project.data.Reviews.DAO.list(this.connection);
    }
    /**
     * @return all the wishlists in the database.
     */
    @Override
    public List<Optional<db_project.data.Wishlists>> getWishlists() {
        return db_project.data.Wishlists.DAO.list(this.connection);
    }
    /**
     * @return all the achievements in the database.
     */
    @Override
    public List<Optional<Achievements>> getAchievements() {
        return Achievements.DAO.list(this.connection);
    }
    /**
     * @return all the videogame developers in the database.
     */
    @Override
    public List<Optional<VideogameDevelopers>> getVideogameDevelopers() {
        return VideogameDevelopers.DAO.list(this.connection);
    }
    /**
     * @return all the transaction items in the database.
     */
    @Override
    public List<Optional<TransactionItems>> getTransactionItems() {
        return TransactionItems.DAO.list(this.connection);
    }
    /**
     * @return all the wishlist items in the database.
     */
    @Override
    public List<Optional<WishlistItems>> getWishlistItems() {
        return WishlistItems.DAO.list(this.connection);
    }
}
