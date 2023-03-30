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
    
    public static final String loginQuery = "SELECT * FROM patients WHERE email = ? AND password = ?;";
    public static final String registerQuery = "INSERT INTO `patients` (`fname`, `lname`, `phone_number`, `email`, `password`, `house_no`, `city`, `dob`, `cnic`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String checkRegistration = "SELECT * FROM patients WHERE email = ? OR cnic = ?;";
    public static final String updateQuery = "UPDATE patients SET password = ? WHERE email = ? AND password = ?";
    
    
}
