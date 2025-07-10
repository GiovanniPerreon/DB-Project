package db_project.model;

import java.util.List;
import java.sql.Connection;
import java.util.Objects;
import java.util.Optional;

import db_project.data.Users;
import db_project.data.VideoGames;

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
}
