package db_project.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Users {

    private final int userID;
    private final String email;
    private final String password;
    private final String name;
    private final String surname;
    private final String birth_date;
    private final boolean is_administrator;
    private final boolean is_publisher;
    private final boolean is_developer;
    private final boolean is_blocked;

    public Users(int userID, String email, String password, String name, String surname,
            String birth_date,boolean is_administrator, boolean is_publisher, boolean is_developer, boolean is_blocked) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birth_date = birth_date;
        this.is_administrator = is_administrator;
        this.is_publisher = is_publisher;
        this.is_developer = is_developer;
        this.is_blocked = is_blocked;
    }
    /**
     * Returns a string representation of the user.
     */
    @Override
    public String toString() {
        return Printer.stringify(
            "User",
            List.of(
                Printer.field("id", this.userID),
                Printer.field("email", this.email),
                Printer.field("password", this.password),
                Printer.field("name", this.name),
                Printer.field("surname", this.surname),
                Printer.field("birth_date", this.birth_date),
                Printer.field("is_administrator", this.is_administrator),
                Printer.field("is_publisher", this.is_publisher),
                Printer.field("is_developer", this.is_developer),
                Printer.field("is_blocked", this.is_blocked)
            )
        );
    }

    // Getters
    public int getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthDate() {
        return birth_date;
    }

    public boolean isAdministrator() {
        return is_administrator;
    }

    public boolean isPublisher() {
        return is_publisher;
    }

    public boolean isDeveloper() {
        return is_developer;
    }

    public boolean isBlocked() {
        return is_blocked;
    }

    /**
     * Returns a string representation of all user types/roles
     */
    public String getUserTypes() {
        List<String> types = new ArrayList<>();
        
        if (is_administrator) {
            types.add("Admin");
        }
        if (is_publisher) {
            types.add("Publisher");
        }
        if (is_developer) {
            types.add("Developer");
        }
        
        if (types.isEmpty()) {
            return "User";
        }
        
        return String.join(", ", types);
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
                        resultSet.getInt("userID"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("birth_date"),
                        resultSet.getBoolean("is_administrator"),
                        resultSet.getBoolean("is_publisher"),
                        resultSet.getBoolean("is_developer"),
                        resultSet.getBoolean("is_blocked")
                    )));
                }
                return users;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static Optional<Users> find(Connection connection, int userID) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_USER, userID);
                var resultSet = statement.executeQuery()
            ) {
                if (resultSet.next()) {
                    return Optional.of(new Users(
                        resultSet.getInt("userID"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("birth_date"),
                        resultSet.getBoolean("is_administrator"),
                        resultSet.getBoolean("is_publisher"),
                        resultSet.getBoolean("is_developer"),
                        resultSet.getBoolean("is_blocked")
                    ));
                } else {
                    return Optional.empty();
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            
        }

        /**
         * Adds a new user to the database.
         */
        public static boolean addUser(Connection connection, String email, String password, String name, String surname, String birthDate, boolean isAdmin, boolean isPublisher, boolean isDeveloper, boolean isBlocked) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.ADD_USER, email, password, name, surname, birthDate, isAdmin, isPublisher, isDeveloper, isBlocked)
            ) {
                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * Finds a user by email and password for login.
         */
        public static Optional<Users> findByEmailPassword(Connection connection, String email, String password) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_USER_BY_EMAIL_PASSWORD, email, password);
                var resultSet = statement.executeQuery()
            ) {
                if (resultSet.next()) {
                    return Optional.of(new Users(
                        resultSet.getInt("userID"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("birth_date"),
                        resultSet.getBoolean("is_administrator"),
                        resultSet.getBoolean("is_publisher"),
                        resultSet.getBoolean("is_developer"),
                        resultSet.getBoolean("is_blocked")
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
