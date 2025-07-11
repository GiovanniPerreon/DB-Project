package db_project.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Transactions {
    private final int transactionID;
    private final int userID;
    private final double total_cost;

    public Transactions(int transactionID, int userID, double total_cost) {
        this.transactionID = transactionID;
        this.userID = userID;
        this.total_cost = total_cost;
    }

    // Getters
    public int getTransactionID() {
        return transactionID;
    }

    public int getUserID() {
        return userID;
    }

    public double getTotalCost() {
        return total_cost;
    }

    /**
     * Returns a string representation of the transaction.
     */
    @Override
    public String toString() {
        return Printer.stringify(
            "Transaction",
            List.of(
                Printer.field("transactionID", this.transactionID),
                Printer.field("userID", this.userID),
                Printer.field("total_cost", this.total_cost)
            )
        );
    }

    public static final class DAO {
        /**
         * Returns a list of all transactions in the database.
         */
        public static List<Optional<Transactions>> list(Connection connection) {
            List<Optional<Transactions>> transactions = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.TRANSACTION_LIST);
                var resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    Transactions transaction = new Transactions(
                        resultSet.getInt("transactionID"),
                        resultSet.getInt("userID"),
                        resultSet.getDouble("total_cost")
                    );
                    transactions.add(Optional.of(transaction));
                }
                return transactions;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * Finds a specific transaction by its ID.
         */
        public static Optional<Transactions> find(Connection connection, int transactionID) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_TRANSACTION, transactionID);
                var resultSet = statement.executeQuery()
            ) {
                if (resultSet.next()) {
                    return Optional.of(new Transactions(
                        resultSet.getInt("transactionID"),
                        resultSet.getInt("userID"),
                        resultSet.getDouble("total_cost")
                    ));
                } else {
                    return Optional.empty();
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * Adds a new transaction and returns the generated transaction ID.
         */
        public static int add(Connection connection, int userID, double totalCost) {
            try (
                var statement = connection.prepareStatement(Queries.ADD_TRANSACTION, java.sql.Statement.RETURN_GENERATED_KEYS);
            ) {
                statement.setInt(1, userID);
                statement.setDouble(2, totalCost);
                
                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    throw new DAOException("Creating transaction failed, no rows affected.");
                }

                try (var generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new DAOException("Creating transaction failed, no ID obtained.");
                    }
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
