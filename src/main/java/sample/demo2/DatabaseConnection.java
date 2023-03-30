package sample.demo2;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public Connection databaseLink;

    public Connection getConnection() {
        String databaseName = "rau_hospitals";
        String user = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;

        try {
            databaseLink = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }
}
