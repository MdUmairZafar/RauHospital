package sample.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

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

    public static final String loginQuery = "SELECT * FROM patient WHERE email = ? AND password = ?;";
    public static final String registerQuery = "INSERT INTO `patient` (`fname`, `lname`, `phone_number`, `email`, `password`, `house_no`, `city`, `dob`, `cnic`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String checkRegistration = "SELECT * FROM patient WHERE email = ? OR cnic = ?;";
    public static final String updateQuery = "UPDATE patient SET password = ? WHERE email = ? AND password = ?";
    
    public static final String getPatientID = "SELECT `id` FROM `patient` WHERE `email` = ?";
    public static final String patientHomeAppointments = "SELECT * FROM `appointment` WHERE `id` = ? LIMIT 8 ";
    public static final String patientData = "SELECT * FROM `patient` WHERE `email` = ?";
    public static final String doctorData = "SELECT * FROM `doctor` WHERE id = ?";

    public final String getPatIdWithEmail ( String email ) {
        String patientId = new String();
        try {
            Connection connectDB = this.getConnection ();

            String query = "SELECT id FROM `patient` WHERE `email` = ?";

            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, email );

            ResultSet resultSet = statement.executeQuery ();

            while (resultSet.next()) {
                patientId = resultSet.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return patientId;
    }
    
    public final String getDocIdWithEmail ( String email ) {
        String docId = new String();
        try {
            Connection connectDB = this.getConnection ();
            
            String query = "SELECT id FROM `doctor` WHERE `email` = ?";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, email );
            
            ResultSet resultSet = statement.executeQuery ();
            
            while (resultSet.next()) {
                docId = resultSet.getString(1);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return docId;
    }

    public final ObservableList<String> getAppointIdsWithPatID(String id) {
        ObservableList<String> appointmentIds = FXCollections.observableArrayList();
        try {
            Connection connectDB = this.getConnection ();

            String query = "SELECT id FROM `appointment` WHERE `patient_id` = ?";

            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );

            ResultSet resultSet = statement.executeQuery ();

            while (resultSet.next()) {
                appointmentIds.add(resultSet.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointmentIds;
    }
    
    

    public final ObservableList<String> getAppointDocIdsWithPatID(String id) {
        ObservableList<String> appointmentDocs = FXCollections.observableArrayList();
        try {
            Connection connectDB = this.getConnection ();

            String query = "SELECT doctor_id FROM `appointment` WHERE `patient_id` = ?";

            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );

            ResultSet resultSet = statement.executeQuery ();

            while (resultSet.next()) {
                appointmentDocs.add(resultSet.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointmentDocs;
    }
    
    
    public final ObservableList<String> getAppointDepIdsWithPatID(String id) {
        ObservableList<String> appointmentDeps = FXCollections.observableArrayList();
        try {
            Connection connectDB = this.getConnection ();

            String query = "SELECT department_id FROM `appointment` WHERE `patient_id` = ?";

            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );

            ResultSet resultSet = statement.executeQuery ();

            while (resultSet.next()) {
                appointmentDeps.add(resultSet.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointmentDeps;
    }

    public final ObservableList<String> getAppointDateWithPatID(String id) {
        ObservableList<String> appointmentDate = FXCollections.observableArrayList();
        try {
            Connection connectDB = this.getConnection ();

            String query = "SELECT time FROM `appointment` WHERE `patient_id` = ?";

            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );

            ResultSet resultSet = statement.executeQuery ();

            while (resultSet.next()) {
                Timestamp timestamp = resultSet.getTimestamp(1);
                LocalDateTime dateTime = timestamp.toLocalDateTime();
                LocalDate date = dateTime.toLocalDate();
                appointmentDate.add(date.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointmentDate;
    }

    public final ObservableList<String> getAppointTimeWithPatID(String id) {
        ObservableList<String> appointmentTime = FXCollections.observableArrayList();
        try {
            Connection connectDB = this.getConnection ();

            String query = "SELECT time FROM `appointment` WHERE `patient_id` = ?";

            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );

            ResultSet resultSet = statement.executeQuery ();

            while (resultSet.next()) {
                Timestamp timestamp = resultSet.getTimestamp(1);
                LocalDateTime dateTime = timestamp.toLocalDateTime();
                LocalTime time = dateTime.toLocalTime();
                appointmentTime.add(time.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointmentTime;
    }

    public final ObservableList<String> getAppointVisitWithPatID(String id) {
        ObservableList<String> appointmentVisit = FXCollections.observableArrayList();
        try {
            Connection connectDB = this.getConnection ();

            String query = "SELECT visited FROM `appointment` WHERE `patient_id` = ?";

            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );

            ResultSet resultSet = statement.executeQuery ();

            while (resultSet.next()) {
                appointmentVisit.add(resultSet.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointmentVisit;
    }

    public final ObservableList<String> getAppointPrescWithPatID(String id) {
        ObservableList<String> appointmentPresc = FXCollections.observableArrayList();
        try {
            Connection connectDB = this.getConnection ();

            String query = "SELECT prescription FROM `appointment` WHERE `patient_id` = ?";

            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );

            ResultSet resultSet = statement.executeQuery ();

            while (resultSet.next()) {
                appointmentPresc.add(resultSet.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointmentPresc;
    }

    public final String getDocNameWithDocId(String id) {
        String docName = new String();
        try {
            Connection connectDB = this.getConnection ();

            String query = "SELECT fname, lname FROM `doctor` WHERE `id` = ?";

            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );

            ResultSet resultSet = statement.executeQuery ();

            if (resultSet.first()) {
                docName = resultSet.getString(1) + " " + resultSet.getString(2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return docName;
    }
    
    public final String getPatNamWithPatId(String id) {
        String patName = new String();
        try {
            Connection connectDB = this.getConnection ();
            
            String query = "SELECT fname, lname FROM `patient` WHERE `id` = ?";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            
            if (resultSet.first()) {
                patName = resultSet.getString(1) + " " + resultSet.getString(2);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patName;
    }
    
    public final String getPatCnicWithPatId(String id) {
        String cnic = new String();
        try {
            Connection connectDB = this.getConnection ();
            
            String query = "SELECT cnic FROM `patient` WHERE `id` = ?";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            
            if (resultSet.first()) {
                cnic = resultSet.getString(1);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cnic;
    }
    
    public final String getPatPhoneWithPatId(String id) {
        String phone = new String();
        try {
            Connection connectDB = this.getConnection ();
            
            String query = "SELECT phone_number FROM `patient` WHERE `id` = ?";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            
            if (resultSet.first()) {
                phone = resultSet.getString(1);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phone;
    }
    
    public final String getPatAgeWithPatId(String id) {
        String age = new String();
        try {
            Connection connectDB = this.getConnection ();
            
            String query = "SELECT dob FROM `patient` WHERE `id` = ?";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            
            if (resultSet.first ()) {
                Timestamp timestamp = resultSet.getTimestamp(1);
                LocalDateTime dateTime = timestamp.toLocalDateTime();
                LocalDate dob = dateTime.toLocalDate();
                LocalDate todayDate = LocalDate.now();
                Period difference = Period.between ( dob, todayDate );
                
                age = String.valueOf ( difference.getYears () );
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return age;
    }
//////////////////////////////////////////
    
    public final String getDocCnicWithDocId(String id) {
        String cnic = new String();
        try {
            Connection connectDB = this.getConnection ();
            
            String query = "SELECT cnic FROM `doctor` WHERE `id` = ?";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            
            if (resultSet.first()) {
                cnic = resultSet.getString(1);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cnic;
    }
    
    public final String getDocPhoneWithDocId(String id) {
        String phone = new String();
        try {
            Connection connectDB = this.getConnection ();
            
            String query = "SELECT phone FROM `doctor` WHERE `id` = ?";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            
            if (resultSet.first()) {
                phone = resultSet.getString(1);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phone;
    }
    
    public final String getDocAgeWithDocId(String id) {
        String age = new String();
        try {
            Connection connectDB = this.getConnection ();
            
            String query = "SELECT dob FROM `doctor` WHERE `id` = ?";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            
            if (resultSet.first ()) {
                Timestamp timestamp = resultSet.getTimestamp(1);
                LocalDateTime dateTime = timestamp.toLocalDateTime();
                LocalDate dob = dateTime.toLocalDate();
                LocalDate todayDate = LocalDate.now();
                Period difference = Period.between ( dob, todayDate );
                
                age = String.valueOf ( difference.getYears () );
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return age;
    }
    
    public final String getDocDptIdWithDocId(String id) {
        String dptId = new String();
        try {
            Connection connectDB = this.getConnection ();
            
            String query = "SELECT department_id FROM `doctor` WHERE `id` = ?";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            
            if (resultSet.first()) {
                dptId = resultSet.getString(1);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dptId;
    }
    
    public final String getDptNamWithDptId(String id) {
        String dptName = new String();
        try {
            Connection connectDB = this.getConnection ();
            
            String query = "SELECT name FROM `department_id` WHERE `id` = ?";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            
            if (resultSet.first()) {
                dptName = resultSet.getString(1);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dptName;
    }
    
    public final ObservableList<String> getAppointPatIdsWithDocID(String id) {
        ObservableList<String> appointmentPat = FXCollections.observableArrayList();
        try {
            Connection connectDB = this.getConnection ();
            
            String query = "SELECT patient_id FROM `appointment` WHERE `doctor_id` = ?";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            
            while (resultSet.next()) {
                appointmentPat.add(resultSet.getString(1));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointmentPat;
    }
    
    public final ObservableList<String> getAppointIdsWithDocID(String id) {
        ObservableList<String> appointmentIds = FXCollections.observableArrayList();
        try {
            Connection connectDB = this.getConnection ();
            
            String query = "SELECT id FROM `appointment` WHERE `doctor_id` = ?";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            
            while (resultSet.next()) {
                appointmentIds.add(resultSet.getString(1));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointmentIds;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    public final ObservableList<String> getAppointDateWithDocID(String id) {
        ObservableList<String> appointmentDate = FXCollections.observableArrayList();
        try {
            Connection connectDB = this.getConnection ();
            
            String query = "SELECT time FROM `appointment` WHERE `doctor_id` = ?";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            
            while (resultSet.next()) {
                Timestamp timestamp = resultSet.getTimestamp(1);
                LocalDateTime dateTime = timestamp.toLocalDateTime();
                LocalDate date = dateTime.toLocalDate();
                appointmentDate.add(date.toString());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointmentDate;
    }
    
    public final ObservableList<String> getAppointTimeWithDocID(String id) {
        ObservableList<String> appointmentTime = FXCollections.observableArrayList();
        try {
            Connection connectDB = this.getConnection ();
            
            String query = "SELECT time FROM `appointment` WHERE `doctor_id` = ?";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            
            while (resultSet.next()) {
                Timestamp timestamp = resultSet.getTimestamp(1);
                LocalDateTime dateTime = timestamp.toLocalDateTime();
                LocalTime time = dateTime.toLocalTime();
                appointmentTime.add(time.toString());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointmentTime;
    }
    
    public final ObservableList<String> getAppointVisitWithDocID(String id) {
        ObservableList<String> appointmentVisit = FXCollections.observableArrayList();
        try {
            Connection connectDB = this.getConnection ();
            
            String query = "SELECT visited FROM `appointment` WHERE `doctor_id` = ?";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            
            while (resultSet.next()) {
                appointmentVisit.add(resultSet.getString(1));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointmentVisit;
    }
    
    public final ObservableList<String> getAppointPrescWithDocID(String id) {
        ObservableList<String> appointmentPresc = FXCollections.observableArrayList();
        try {
            Connection connectDB = this.getConnection ();
            
            String query = "SELECT prescription FROM `appointment` WHERE `doctor_id` = ?";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            
            while (resultSet.next()) {
                appointmentPresc.add(resultSet.getString(1));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointmentPresc;
    }
    

    
    
}
