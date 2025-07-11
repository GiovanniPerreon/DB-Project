package db_project.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Genres {
    private final String genre;
    private final String description;

    public Genres(String genre, String description) {
        this.genre = genre;
        this.description = description;
    }

    // Getters
    public String getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns a string representation of the genre.
     */
    @Override
    public String toString() {
        return Printer.stringify(
            "Genre",
            List.of(
                Printer.field("genre", this.genre),
                Printer.field("description", this.description)
            )
        );
    }

    public static final class DAO {
        /**
         * Returns a list of all genres in the database.
         */
        public static List<Optional<Genres>> list(Connection connection) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GENRE_LIST);
                var resultSet = statement.executeQuery()
            ) {
                var genres = new ArrayList<Optional<Genres>>();
                while (resultSet.next()) {
                    genres.add(Optional.of(new Genres(
                        resultSet.getString("genre"),
                        resultSet.getString("description")
                    )));
                }
                return genres;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * Returns a genre by its name.
         */
        public static Optional<Genres> find(Connection connection, String genre) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_GENRE, genre);
                var resultSet = statement.executeQuery()
            ) {
                if (resultSet.next()) {
                    return Optional.of(new Genres(
                        resultSet.getString("genre"),
                        resultSet.getString("description")
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
