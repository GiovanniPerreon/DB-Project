package db_project.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Users {

    private final int id;
    private final String email;
    private final String password;
    private final String name;
    private final String surname;
    private final String birth_date;

    public Users(int id, String email, String password, String name, String surname, String birth_date) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birth_date = birth_date;
    }
    /**
     * Returns a string representation of the user.
     */
    @Override
    public String toString() {
        return Printer.stringify(
            "User",
            List.of(
                Printer.field("id", this.id),
                Printer.field("email", this.email),
                Printer.field("password", this.password),
                Printer.field("name", this.name),
                Printer.field("surname", this.surname),
                Printer.field("birth_date", this.birth_date)
            )
        );
    }

    public static final class DAO {
        /**
         * Returns a list of all users in the database.
         */
        public static List<Optional<Users>> list(Connection connection) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.USER_LIST);
                var resultSet = statement.executeQuery()
            ) {
                var users = new ArrayList<Optional<Users>>();
                while (resultSet.next()) {
                    users.add(Optional.of(new Users(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("birth_date")
                    )));
                }
                return users;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static Optional<Users> find(Connection connection, int id) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_USER, id);
                var resultSet = statement.executeQuery()
            ) {
                if (resultSet.next()) {
                    return Optional.of(new Users(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("birth_date")
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
