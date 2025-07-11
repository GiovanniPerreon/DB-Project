package db_project;

import db_project.data.DAOUtils;
import db_project.model.Model;
import java.sql.Connection;
import java.sql.SQLException;
import db_project.model.DBModel;

public final class App {
    public static void main(String[] args) throws SQLException {
        System.out.println("Starting application...");
        final Connection connection = DAOUtils.localMySQLConnection("tables", "root", "password");
        System.out.println("Database connection established");
        
        Model model = new DBModel(connection);
        System.out.println("Model created");
        
        View view = new View(() ->   {
            try {
                connection.close();
            } catch (Exception ignored) {}
        });
        System.out.println("View created");
        
        Controller controller = new Controller(model, view);
        view.setController(controller);
        System.out.println("Controller set up");
        
        controller.testAllTables();
        System.out.println("Application started successfully");
    }
}
