package db_project.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VideoGames {
    private final int id;
    private final String title;
    private final String price;
    private final String releaseDate;
    private final String description;
    public VideoGames(int id, String title, String price, String releaseDate, String description) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.releaseDate = releaseDate;
        this.description = description;
    }

    /**
     * Returns a string representation of the video game.
     */
    @Override
    public String toString() {
        return Printer.stringify(
            "VideoGame",
            List.of(
                Printer.field("id", this.id),
                Printer.field("title", this.title),
                Printer.field("price", this.price),
                Printer.field("release_date", this.releaseDate),
                Printer.field("description", this.description)
            )
        );
    }

    public static final class DAO {
        /**
         * Returns a list of all video games in the database.
         */
        public static List<Optional<VideoGames>> list(Connection connection) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.VIDEOGAME_LIST);
                var resultSet = statement.executeQuery()
            ) {
                var videogames = new ArrayList<Optional<VideoGames>>();
                while (resultSet.next()) {
                    videogames.add(Optional.of(new VideoGames(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("price"),
                        resultSet.getString("release_date"),
                        resultSet.getString("description")
                    )));
                }
                return videogames;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
        /**
         * Finds a video game by its ID.
         * @param connection the database connection
         * @param id the ID of the video game
         * @return an Optional containing the video game if found, or empty if not found
         */
        public static Optional<VideoGames> find(Connection connection, int id) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_VIDEOGAME);
                var resultSet = statement.executeQuery()
                ) {
                    if (resultSet.next()) {
                        return Optional.of(new VideoGames(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("price"),
                            resultSet.getString("release_date"),
                            resultSet.getString("description")
                        ));
                    } else {
                        return Optional.empty();
                    }
                } catch (Exception e) {
                    throw new DAOException(e);
                }
            }
        }}
