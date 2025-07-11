package db_project.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Reviews {
    private final int userID;
    private final int gameID;
    private final int rating;
    private final String comment;

    public Reviews(int userID, int gameID, int rating, String comment) {
        this.userID = userID;
        this.gameID = gameID;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters
    public int getUserID() {
        return userID;
    }

    public int getGameID() {
        return gameID;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    /**
     * Returns a string representation of the review.
     */
    @Override
    public String toString() {
        return Printer.stringify(
            "Review",
            List.of(
                Printer.field("userID", this.userID),
                Printer.field("gameID", this.gameID),
                Printer.field("rating", this.rating),
                Printer.field("comment", this.comment)
            )
        );
    }

    public static final class DAO {
        /**
         * Returns a list of all reviews in the database.
         */
        public static List<Optional<Reviews>> list(Connection connection) {
            List<Optional<Reviews>> reviews = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.REVIEW_LIST);
                var resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    Reviews review = new Reviews(
                        resultSet.getInt("userID"),
                        resultSet.getInt("gameID"),
                        resultSet.getInt("rating"),
                        resultSet.getString("comment")
                    );
                    reviews.add(Optional.of(review));
                }
                return reviews;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * Finds a specific review by userID and gameID.
         */
        public static Optional<Reviews> find(Connection connection, int userID, int gameID) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_REVIEW, userID, gameID);
                var resultSet = statement.executeQuery()
            ) {
                if (resultSet.next()) {
                    return Optional.of(new Reviews(
                        resultSet.getInt("userID"),
                        resultSet.getInt("gameID"),
                        resultSet.getInt("rating"),
                        resultSet.getString("comment")
                    ));
                } else {
                    return Optional.empty();
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * Adds a new review to the database.
         */
        public static void addReview(Connection connection, int userID, int gameID, int rating, String comment) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.ADD_REVIEW, userID, gameID, rating, comment)
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
