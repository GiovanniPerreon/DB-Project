package db_project.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionItems {
    private final int transactionID;
    private final int gameID;

    public TransactionItems(int transactionID, int gameID) {
        this.transactionID = transactionID;
        this.gameID = gameID;
    }

    // Getters
    public int getTransactionID() {
        return transactionID;
    }

    public int getGameID() {
        return gameID;
    }

    /**
     * Returns a string representation of the transaction item.
     */
    @Override
    public String toString() {
        return Printer.stringify(
            "TransactionItem",
            List.of(
                Printer.field("transactionID", this.transactionID),
                Printer.field("gameID", this.gameID)
            )
        );
    }

    public static final class DAO {
        /**
         * Returns a list of all transaction items in the database.
         */
        public static List<Optional<TransactionItems>> list(Connection connection) {
            List<Optional<TransactionItems>> items = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.TRANSACTION_ITEMS_LIST);
                var resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    TransactionItems item = new TransactionItems(
                        resultSet.getInt("transactionID"),
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
         * Finds all items for a specific transaction.
         */
        public static List<Optional<TransactionItems>> findByTransaction(Connection connection, int transactionID) {
            List<Optional<TransactionItems>> items = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_TRANSACTION_ITEMS, transactionID);
                var resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    TransactionItems item = new TransactionItems(
                        resultSet.getInt("transactionID"),
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
         * Adds an item to a transaction.
         */
        public static void addItem(Connection connection, int transactionID, int gameID) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.ADD_TRANSACTION_ITEM, transactionID, gameID)
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
