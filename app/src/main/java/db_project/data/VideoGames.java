package db_project.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VideoGames {
    private final int gameID;
    private final int publisherID;
    private final String title;
    private final String price;
    private final String description;
    private final String requirements;
    private final String averageRating;
    private final String releaseDate;
    private final String discount;
    public VideoGames(int gameID, int publisherID, String title, String price, String description,
            String requirements, String averageRating, String releaseDate, String discount) {
        this.gameID = gameID;
        this.publisherID = publisherID;
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
                Printer.field("publisherID", this.publisherID),
                Printer.field("title", this.title),
                Printer.field("price", this.price),
                Printer.field("description", this.description),
                Printer.field("requirements", this.requirements),
                Printer.field("averageRating", this.getAverageRatingString()),
                Printer.field("releaseDate", this.releaseDate),
                Printer.field("discount", this.discount)
            )
        );
    }

    // Getters
    public int getGameID() {
        return gameID;
    }

    public int getPublisherID() {
        return publisherID;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getRequirements() {
        return requirements;
    }

    public double getAverageRating() {
        try {
            return averageRating != null ? Double.parseDouble(averageRating) : 0.0;
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    
    public String getAverageRatingString() {
        return averageRating != null ? averageRating : "No rating yet";
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getDiscount() {
        return discount;
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
                        resultSet.getInt("publisherID"),
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
                            resultSet.getInt("publisherID"),
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
