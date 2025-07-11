package db_project.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VideogameDevelopers {
    private final int developerID;
    private final int gameID;

    public VideogameDevelopers(int developerID, int gameID) {
        this.developerID = developerID;
        this.gameID = gameID;
    }

    // Getters
    public int getDeveloperID() {
        return developerID;
    }

    public int getGameID() {
        return gameID;
    }

    /**
     * Returns a string representation of the videogame developer relation.
     */
    @Override
    public String toString() {
        return Printer.stringify(
            "VideogameDeveloper",
            List.of(
                Printer.field("developerID", this.developerID),
                Printer.field("gameID", this.gameID)
            )
        );
    }

    public static final class DAO {
        /**
         * Returns a list of all videogame-developer relations in the database.
         */
        public static List<Optional<VideogameDevelopers>> list(Connection connection) {
            List<Optional<VideogameDevelopers>> relations = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.VIDEOGAME_DEVELOPERS_LIST);
                var resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    VideogameDevelopers relation = new VideogameDevelopers(
                        resultSet.getInt("developerID"),
                        resultSet.getInt("gameID")
                    );
                    relations.add(Optional.of(relation));
                }
                return relations;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * Finds all developers for a specific game.
         */
        public static List<Optional<VideogameDevelopers>> findByGame(Connection connection, int gameID) {
            List<Optional<VideogameDevelopers>> relations = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_VIDEOGAME_DEVELOPERS, gameID);
                var resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    VideogameDevelopers relation = new VideogameDevelopers(
                        resultSet.getInt("developerID"),
                        resultSet.getInt("gameID")
                    );
                    relations.add(Optional.of(relation));
                }
                return relations;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        /**
         * Adds a developer to a game.
         */
        public static void addDeveloper(Connection connection, int developerID, int gameID) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.ADD_VIDEOGAME_DEVELOPER, developerID, gameID)
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
