package db_project.model;

import java.util.List;
import java.sql.Connection;
import java.util.Objects;
import java.util.Optional;

import db_project.data.Genres;
import db_project.data.Languages;
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

    /**
     * Registers a new user in the database.
     */
    @Override
    public boolean registerUser(String email, String password, String name, String surname, String birthDate) {
        return Users.DAO.addUser(this.connection, email, password, name, surname, birthDate, false, false, false);
    }

    @Override
    public Optional<Users> findByEmailPassword(String email, String password) {
        return Users.DAO.findByEmailPassword(this.connection, email, password);
    }
}
