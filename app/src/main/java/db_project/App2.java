package db_project;

import db_project.data.DAOUtils;
import db_project.model.Model;
import java.sql.Connection;
import java.sql.SQLException;
import db_project.model.DBModel;

public final class App2 {
    public static void main(String[] args) throws SQLException {
        final Connection connection = DAOUtils.localMySQLConnection("tables", "root", "password");
        Model model = new DBModel(connection);
        View2 view2 = new View2(() -> {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Controller controller = new Controller(model, view2.getViewManager());
        view2.setController(controller);
        controller.testAllTables();
    }
}
