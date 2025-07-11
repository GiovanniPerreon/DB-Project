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
        
        /**
         * Adds a game to user's wishlist.
         */
        public static boolean addToWishlist(Connection connection, int userId, int gameId) {
            try {
                System.out.println("DEBUG: Adding game " + gameId + " to wishlist for user " + userId);
                
                // First, find or create the user's wishlist
                int wishlistId = findOrCreateWishlist(connection, userId);
                System.out.println("DEBUG: Using wishlist ID: " + wishlistId);
                
                // Then add the game to the wishlist
                try (var statement = DAOUtils.prepare(connection, Queries.ADD_WISHLIST_ITEM, wishlistId, gameId)) {
                    int rows = statement.executeUpdate();
                    System.out.println("DEBUG: Inserted " + rows + " rows into wishlist_items");
                    return rows > 0;
                }
            } catch (Exception e) {
                System.err.println("Error adding game to wishlist: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        /**
         * Removes a game from user's wishlist.
         */
        public static boolean removeFromWishlist(Connection connection, int userId, int gameId) {
            try {
                // First, find the user's wishlist
                int wishlistId = findWishlistByUser(connection, userId);
                if (wishlistId == -1) {
                    return false; // User has no wishlist
                }
                
                // Then remove the game from the wishlist
                try (var statement = DAOUtils.prepare(connection, Queries.REMOVE_WISHLIST_ITEM, wishlistId, gameId)) {
                    return statement.executeUpdate() > 0;
                }
            } catch (Exception e) {
                System.err.println("Error removing game from wishlist: " + e.getMessage());
                return false;
            }
        }
        
        /**
         * Checks if a game is in user's wishlist.
         */
        public static boolean isGameInWishlist(Connection connection, int userId, int gameId) {
            try {
                // First, find the user's wishlist
                int wishlistId = findWishlistByUser(connection, userId);
                if (wishlistId == -1) {
                    return false; // User has no wishlist
                }
                
                // Then check if the game is in the wishlist
                try (var statement = DAOUtils.prepare(connection, 
                    "SELECT COUNT(*) FROM wishlist_items WHERE wishlistID = ? AND gameID = ?", 
                    wishlistId, gameId)) {
                    try (var rs = statement.executeQuery()) {
                        if (rs.next()) {
                            return rs.getInt(1) > 0;
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Error checking game in wishlist: " + e.getMessage());
            }
            return false;
        }
        
        private static int findOrCreateWishlist(Connection connection, int userId) throws Exception {
            // First try to find existing wishlist
            int wishlistId = findWishlistByUser(connection, userId);
            if (wishlistId != -1) {
                return wishlistId;
            }
            
            // Create new wishlist if not found
            try (var statement = connection.prepareStatement(Queries.ADD_WISHLIST, java.sql.Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, userId);
                statement.executeUpdate();
                
                // Get the generated wishlist ID
                try (var rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
            throw new Exception("Failed to create wishlist");
        }
        
        private static int findWishlistByUser(Connection connection, int userId) throws Exception {
            try (var statement = DAOUtils.prepare(connection, Queries.FIND_WISHLIST_BY_USER, userId)) {
                try (var rs = statement.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("wishlistID");
                    }
                }
            }
            return -1; // No wishlist found
        }
    }
}
