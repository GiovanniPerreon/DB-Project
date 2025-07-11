package db_project.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WishlistItems {
    private final int wishlistID;
    private final int gameID;

    public WishlistItems(int wishlistID, int gameID) {
        this.wishlistID = wishlistID;
        this.gameID = gameID;
    }

    // Getters
    public int getWishlistID() {
        return wishlistID;
    }

    public int getGameID() {
        return gameID;
    }

    /**
     * Returns a string representation of the wishlist item.
     */
    @Override
    public String toString() {
        return Printer.stringify(
            "WishlistItem",
            List.of(
                Printer.field("wishlistID", this.wishlistID),
                Printer.field("gameID", this.gameID)
            )
        );
    }

    public static final class DAO {
        /**
         * Returns a list of all wishlist items in the database.
         */
        public static List<Optional<WishlistItems>> list(Connection connection) {
            List<Optional<WishlistItems>> items = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.WISHLIST_ITEMS_LIST);
                var resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    WishlistItems item = new WishlistItems(
                        resultSet.getInt("wishlistID"),
                        resultSet.getInt("gameID")
                    );
                    items.add(Optional.of(item));
                }
                return items;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * Finds all items for a specific wishlist.
         */
        public static List<Optional<WishlistItems>> findByWishlist(Connection connection, int wishlistID) {
            List<Optional<WishlistItems>> items = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_WISHLIST_ITEMS, wishlistID);
                var resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    WishlistItems item = new WishlistItems(
                        resultSet.getInt("wishlistID"),
                        resultSet.getInt("gameID")
                    );
                    items.add(Optional.of(item));
                }
                return items;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * Adds an item to a wishlist.
         */
        public static void addItem(Connection connection, int wishlistID, int gameID) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.ADD_WISHLIST_ITEM, wishlistID, gameID)
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * Removes an item from a wishlist.
         */
        public static void removeItem(Connection connection, int wishlistID, int gameID) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.REMOVE_WISHLIST_ITEM, wishlistID, gameID)
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
