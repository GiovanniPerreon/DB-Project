package db_project.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Wishlists {
    private final int wishlistID;
    private final int userID;
    private final List<VideoGames> gameList;

    public Wishlists(int wishlistID, int userID) {
        this.wishlistID = wishlistID;
        this.userID = userID;
        this.gameList = new ArrayList<>();
    }

    public Wishlists(int wishlistID, int userID, List<VideoGames> gameList) {
        this.wishlistID = wishlistID;
        this.userID = userID;
        this.gameList = new ArrayList<>(gameList);
    }

    public int getWishlistID() {
        return wishlistID;
    }

    public int getUserID() {
        return userID;
    }

    public List<VideoGames> getGameList() {
        return new ArrayList<>(gameList);
    }

    public void addGame(VideoGames game) {
        if (!gameList.contains(game)) {
            gameList.add(game);
        }
    }

    public void removeGame(VideoGames game) {
        gameList.remove(game);
    }

    /**
     * Returns a string representation of the wishlist.
     */
    @Override
    public String toString() {
        return Printer.stringify(
            "Wishlist",
            List.of(
                Printer.field("wishlistID", this.wishlistID),
                Printer.field("userID", this.userID),
                Printer.field("gameList", this.gameList)
            )
        );
    }

    public static final class DAO {
        /**
         * Returns a list of all wishlists in the database.
         */
        public static List<Optional<Wishlists>> list(Connection connection) {
            List<Optional<Wishlists>> wishlists = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.WISHLIST_LIST);
                var resultSet = statement.executeQuery()
            ) {
               while (resultSet.next()) {
                    Wishlists wishlist = new Wishlists(
                        resultSet.getInt("wishlistID"),
                        resultSet.getInt("userID")
                    );
                    wishlists.add(Optional.of(wishlist));
               }
               return wishlists;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * Finds a specific wishlist by its ID.
         */
        public static Optional<Wishlists> find(Connection connection, int wishlistID) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_WISHLIST, wishlistID);
                var resultSet = statement.executeQuery()
            ) {
                if (resultSet.next()) {
                    return Optional.of(new Wishlists(
                        resultSet.getInt("wishlistID"),
                        resultSet.getInt("userID")
                    ));
                } else {
                    return Optional.empty();
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * Adds a game to a wishlist.
         */
        public static void addGameToWishlist(Connection connection, int wishlistID, int gameID) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.WISHLIST_ADD_GAME, wishlistID, gameID)
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * Removes a game from a wishlist.
         */
        public static void removeGameFromWishlist(Connection connection, int wishlistID, int gameID) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.WISHLIST_REMOVE_GAME, wishlistID, gameID)
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
