package db_project.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Languages {
    private final String languageName;

    public Languages(String languageName) {
        this.languageName = languageName;
    }
    /**
     * Returns a string representation of the language.
     */
    @Override
    public String toString() {
        return Printer.stringify(
            "Language",
            List.of(
                Printer.field("language_name", this.languageName)
            )
        );
    }

    public static final class DAO {
        /**
         * Returns a list of all languages in the database.
         */
        public static List<Optional<Languages>> list(Connection connection) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.LANGUAGE_LIST);
                var resultSet = statement.executeQuery()
            ) {
                var languages = new ArrayList<Optional<Languages>>();
                while (resultSet.next()) {
                    languages.add(Optional.of(new Languages(
                        resultSet.getString("language_name")
                    )));
                }
                return languages;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static Optional<Languages> find(Connection connection, String languageName) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_LANGUAGE, languageName);
                var resultSet = statement.executeQuery()
            ) {
                if (resultSet.next()) {
                    return Optional.of(new Languages(
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
