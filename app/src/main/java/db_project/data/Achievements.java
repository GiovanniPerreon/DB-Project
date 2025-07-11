package db_project.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Achievements {
    private final int achievementID;
    private final int gameID;
    private final String title;
    private final String description;

    public Achievements(int achievementID, int gameID, String title, String description) {
        this.achievementID = achievementID;
        this.gameID = gameID;
        this.title = title;
        this.description = description;
    }

    // Getters
    public int getAchievementID() {
        return achievementID;
    }

    public int getGameID() {
        return gameID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns a string representation of the achievement.
     */
    @Override
    public String toString() {
        return Printer.stringify(
            "Achievement",
            List.of(
                Printer.field("achievementID", this.achievementID),
                Printer.field("gameID", this.gameID),
                Printer.field("title", this.title),
                Printer.field("description", this.description)
            )
        );
    }

    public static final class DAO {
        /**
         * Returns a list of all achievements in the database.
         */
        public static List<Optional<Achievements>> list(Connection connection) {
            List<Optional<Achievements>> achievements = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.ACHIEVEMENTS_LIST);
                var resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    Achievements achievement = new Achievements(
                        resultSet.getInt("achievementID"),
                        resultSet.getInt("gameID"),
                        resultSet.getString("title"),
                        resultSet.getString("description")
                    );
                    achievements.add(Optional.of(achievement));
                }
                return achievements;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * Finds all achievements for a specific game.
         */
        public static List<Optional<Achievements>> findByGame(Connection connection, int gameID) {
            List<Optional<Achievements>> achievements = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_ACHIEVEMENTS, gameID);
                var resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    Achievements achievement = new Achievements(
                        resultSet.getInt("achievementID"),
                        resultSet.getInt("gameID"),
                        resultSet.getString("title"),
                        resultSet.getString("description")
                    );
                    achievements.add(Optional.of(achievement));
                }
                return achievements;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * Adds a new achievement to the database.
         */
        public static void addAchievement(Connection connection, int achievementID, int gameID, String title, String description) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.ADD_ACHIEVEMENT, achievementID, gameID, title, description)
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
