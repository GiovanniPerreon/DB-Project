package db_project.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VideoGames {
    private final int gameID;
    private final int userID;
    private final String title;
    private final String price;
    private final String description;
    private final String requirements;
    private final String averageRating;
    private final String releaseDate;
    private final String discount;
    public VideoGames(int gameID, int userID, String title, String price, String description,
            String requirements, String averageRating, String releaseDate, String discount) {
        this.gameID = gameID;
        this.userID = userID;
        this.title = title;
        this.price = price;
        this.description = description;
        this.requirements = requirements;
        this.averageRating = averageRating;
        this.releaseDate = releaseDate;
        this.discount = discount;
    }

    /**
     * Returns a string representation of the video game.
     */
    @Override
    public String toString() {
        return Printer.stringify(
            "VideoGame",
            List.of(
                Printer.field("gameID", this.gameID),
                Printer.field("userID", this.userID),
                Printer.field("title", this.title),
                Printer.field("price", this.price),
                Printer.field("description", this.description),
                Printer.field("requirements", this.requirements),
                Printer.field("averageRating", this.averageRating),
                Printer.field("releaseDate", this.releaseDate),
                Printer.field("discount", this.discount)
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
                        resultSet.getInt("gameID"),
                        resultSet.getInt("userID"),
                        resultSet.getString("title"),
                        resultSet.getString("price"),
                        resultSet.getString("description"),
                        resultSet.getString("requirements"),
                        resultSet.getString("average_rating"),
                        resultSet.getString("release_date"),
                        resultSet.getString("discount")
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
         * @param gameID the ID of the video game
         * @return an Optional containing the video game if found, or empty if not found
         */
        public static Optional<VideoGames> find(Connection connection, int gameID) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_VIDEOGAME);
                var resultSet = statement.executeQuery()
                ) {
                    if (resultSet.next()) {
                        return Optional.of(new VideoGames(
                            resultSet.getInt("gameID"),
                            resultSet.getInt("userID"),
                            resultSet.getString("title"),
                            resultSet.getString("price"),
                            resultSet.getString("description"),
                            resultSet.getString("requirements"),
                            resultSet.getString("average_rating"),
                            resultSet.getString("release_date"),
                            resultSet.getString("discount")
                        ));
                    } else {
                        return Optional.empty();
                    }
                } catch (Exception e) {
                    throw new DAOException(e);
                }
            }
        }}
