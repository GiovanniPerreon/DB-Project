package db_project.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VideogameLanguages {
    private final int gameID;
    private final String languageName;

    public VideogameLanguages(int gameID, String languageName) {
        this.gameID = gameID;
        this.languageName = languageName;
    }
    
    // Getters
    public int getGameID() {
        return gameID;
    }
    
    public String getLanguageName() {
        return languageName;
    }
    
    /**
     * Returns a string representation of the videogame language.
     */
    @Override
    public String toString() {
        return Printer.stringify(
            "Videogame Language",
            List.of(
                Printer.field("gameID", this.gameID),
                Printer.field("language_name", this.languageName)
            )
        );
    }

    public static final class DAO {
        /**
         * Returns a list of all videogame_Languages in the database.
         */
        public static List<Optional<VideogameLanguages>> list(Connection connection) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.VIDEOGAME_LANGUAGE_LIST);
                var resultSet = statement.executeQuery()
            ) {
                var videogameLanguages = new ArrayList<Optional<VideogameLanguages>>();
                while (resultSet.next()) {
                    videogameLanguages.add(Optional.of(new VideogameLanguages(
                        resultSet.getInt("gameID"),
                        resultSet.getString("language_name")
                    )));
                }
                return videogameLanguages;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static Optional<VideogameLanguages> find(Connection connection, int gameID, String languageName) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_VIDEOGAME_LANGUAGE, gameID, languageName);
                var resultSet = statement.executeQuery()
            ) {
                if (resultSet.next()) {
                    return Optional.of(new VideogameLanguages(
                        resultSet.getInt("gameID"),
                        resultSet.getString("language_name")
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
