package db_project.model;

import java.util.List;
import java.sql.Connection;
import java.util.Objects;
import java.util.Optional;

import db_project.data.Genres;
import db_project.data.Reviews;
import db_project.data.Transactions;
import db_project.data.Users;
import db_project.data.VideoGameGenres;
import db_project.data.VideoGames;
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
     * @return all the transactions in the database.
     */
    @Override
    public List<Optional<Transactions>> getTransactions() {
        return Transactions.DAO.list(this.connection);
    }
    /*
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
}
