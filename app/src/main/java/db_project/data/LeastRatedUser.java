package db_project.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeastRatedUser {
    private final int userID;
    private final String name;
    private final String surname;
    private final String email;
    private final boolean isPublisher;
    private final boolean isDeveloper;
    private final double avgRating;

    public LeastRatedUser(int userID, String name, String surname, String email, 
                         boolean isPublisher, boolean isDeveloper, double avgRating) {
        this.userID = userID;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.isPublisher = isPublisher;
        this.isDeveloper = isDeveloper;
        this.avgRating = avgRating;
    }

    // Getters
    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public boolean isPublisher() {
        return isPublisher;
    }

    public boolean isDeveloper() {
        return isDeveloper;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public String getRole() {
        if (isPublisher && isDeveloper) {
            return "Publisher & Developer";
        } else if (isPublisher) {
            return "Publisher";
        } else if (isDeveloper) {
            return "Developer";
        } else {
            return "User";
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s (%s) - Average Rating: %.2f - Role: %s", 
                           name, surname, email, avgRating, getRole());
    }

    public static final class DAO {
        public static List<LeastRatedUser> getLeastRatedUsers(Connection connection) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.LEAST_RATED_PUBLISHERS_DEVELOPERS);
                var resultSet = statement.executeQuery()
            ) {
                var users = new ArrayList<LeastRatedUser>();
                while (resultSet.next()) {
                    users.add(new LeastRatedUser(
                        resultSet.getInt("userID"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("email"),
                        resultSet.getBoolean("is_publisher"),
                        resultSet.getBoolean("is_developer"),
                        resultSet.getDouble("avg_rating")
                    ));
                }
                return users;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }
}
