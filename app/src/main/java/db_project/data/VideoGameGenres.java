package db_project.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VideoGameGenres {

    private final int gameID;
    private final String genre;
    
    public VideoGameGenres(int gameID, String genre) {
        this.gameID = gameID;
        this.genre = genre;
    }
    
    // Getters
    public int getGameID() {
        return gameID;
    }
    
    public String getGenre() {
        return genre;
    }
    
    /**
     * Returns a string representation of the video game genre.
     */
    @Override
    public String toString() {
        return Printer.stringify(
            "Videogame genre",
            List.of(
                Printer.field("gameID", this.gameID),
                Printer.field("genre", this.genre)
            )
        );
    }

    public static final class DAO {
        /**
         * Returns a list of all users in the database.
         */
        public static List<Optional<VideoGameGenres>> list(Connection connection) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.VIDEOGAME_GENRE_LIST);
                var resultSet = statement.executeQuery()
            ) {
                var videogame_genre = new ArrayList<Optional<VideoGameGenres>>();
                while (resultSet.next()) {
                    videogame_genre.add(Optional.of(new VideoGameGenres(
                        resultSet.getInt("gameID"),
                        resultSet.getString("genre")
                    )));
                }
                return videogame_genre;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static Optional<VideoGameGenres> find(Connection connection, int gameID, int genre) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_VIDEOGAME_GENRE, gameID, genre);
                var resultSet = statement.executeQuery()
            ) {
                if (resultSet.next()) {
                    return Optional.of(new VideoGameGenres(
                        resultSet.getInt("gameID"),
                        resultSet.getString("genre")
                    ));
                } else {
                    return Optional.empty();
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            
        }
    }
}
