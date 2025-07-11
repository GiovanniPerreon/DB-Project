package db_project.model;

import java.util.List;
import java.util.Optional;

import db_project.data.Users;
import db_project.data.VideoGames;

public interface Model {
    /**
     * @param userId
     * @return an Optional of the user with the given ID.
     */
    Optional<Users> find(int userId);   
    /**
     * @return all users in the database.
     */
    List<Optional<Users>> getUsers();
    /**
     * @return all the videogames in the database.
     */
    List<Optional<VideoGames>> getVideoGames();
}
