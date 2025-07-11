package db_project.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AchievementsUser {
    private final int achievementID;
    private final int userID;
    private final int gameID;

    public AchievementsUser(int achievementID, int userID, int gameID) {
        this.achievementID = achievementID;
        this.userID = userID;
        this.gameID = gameID;
    }

    // Getters
    public int getAchievementID() {
        return achievementID;
    }

    public int getUserID() {
        return userID;
    }

    public int getGameID() {
        return gameID;
    }

    /**
     * Returns a string representation of the user achievement.
     */
    @Override
    public String toString() {
        return Printer.stringify(
            "AchievementsUser",
            List.of(
                Printer.field("achievementID", this.achievementID),
                Printer.field("userID", this.userID),
                Printer.field("gameID", this.gameID)
            )
        );
    }

    public static final class DAO {
        /**
         * Gets all achievements for a specific user.
         */
        public static List<Achievements> getUserAchievements(Connection connection, int userID) {
            List<Achievements> achievements = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.USER_ACHIEVEMENTS_LIST, userID);
                var resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    Achievements achievement = new Achievements(
                        resultSet.getInt("achievementID"),
                        resultSet.getInt("gameID"),
                        resultSet.getString("title"),
                        resultSet.getString("description")
                    );
                    achievements.add(achievement);
                }
                return achievements;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * Checks if a user has a specific achievement.
         */
        public static boolean hasAchievement(Connection connection, int userID, int achievementID, int gameID) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_USER_ACHIEVEMENT, userID, achievementID, gameID);
                var resultSet = statement.executeQuery()
            ) {
                return resultSet.next();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * Adds an achievement to a user.
         */
        public static void addUserAchievement(Connection connection, int achievementID, int userID, int gameID) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.ADD_USER_ACHIEVEMENT, achievementID, userID, gameID)
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
