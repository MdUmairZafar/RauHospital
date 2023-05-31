package sample.demo2;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DatabaseConnection {

    private Connection databaseLink;
    
    public static String user;
    LocalDateTime currentDateTime = null;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    //========================Singleton Design Pattern Implementation ======================//
    private static DatabaseConnection instance = new DatabaseConnection ();
    
    public static DatabaseConnection getInstance () {
        return instance;
    }
    

    public Connection getConnection() {
        String databaseName = "rau_hospitals";
        String user = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;

        try {
            
            databaseLink = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println ( e.getMessage () );
        }
        return databaseLink;
    }

    public static final String patientLoginQuery = "SELECT * FROM patient WHERE email = ? AND password = ?;";
    public static final String doctorLoginQuery = "SELECT * FROM doctor WHERE email = ? AND password = ?;";
    public static final String adminLoginQuery = "SELECT * FROM admin WHERE email = ? AND password = ?;";
    public static final String registerQuery = "INSERT INTO `patient` (`fname`, `lname`, `phone`, `email`, `password`, `house_no`, `city`, `dob`, `cnic`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String checkRegistration = "SELECT * FROM patient WHERE email = ? OR cnic = ?;";
    public static final String updateQuery = "UPDATE patient SET password = ? WHERE email = ? AND password = ?";
    
    private String calculateAge(Timestamp date) {
        String age = null;
        Timestamp timestamp = date;
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        LocalDate dob = dateTime.toLocalDate();
        LocalDate todayDate = LocalDate.now();
        Period difference = Period.between ( dob, todayDate );
        
        age = String.valueOf ( difference.getYears () );
        return age;
    }
    
    //=========================Admin Functions ====================//
    
    public final ObservableList getAllAdmin () {
        ObservableList<Admin> adminList = FXCollections.observableArrayList ();
        int count = 0;
        try {
            Connection connectDB = this.getConnection ();
            String query = "SELECT * FROM `admin` WHERE deleted_at IS NULL;";
            PreparedStatement statement = connectDB.prepareStatement ( query );
            
            ResultSet resultSet = statement.executeQuery ();
            while (resultSet.next ()) {
                
                count++;
                Admin admin = new Admin(
                        "" + count,
                        resultSet.getString ( "fname" ) + " " + resultSet.getString ( "lname" ),
                        resultSet.getString ( "phone" ),
                        resultSet.getString("email"),
                        resultSet.getString ( "id" ),
                        resultSet.getString("house_no"),
                        resultSet.getString("city"),
                        calculateAge ( resultSet.getTimestamp ( "dob" ) ),
                        resultSet.getString ( "cnic" ),
                        new Button(),
                        new Button(),
                        new Button ()
                );
                adminList.add(admin);
                
            }
        } catch (Exception e) {
            System.out.println ( e.getMessage () );
        }
        return adminList;
    }
    
    public final Admin getAdminById (String id) {
        int count = 0;
        Admin admin = null;
        try {
            Connection connectDB = this.getConnection ();
            String query = "SELECT * FROM `admin` WHERE `id` = ?";
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            while (resultSet.next ()) {
                
                count++;
                admin = new Admin(
                        "" + count,
                        resultSet.getString ( "fname" ) + " " + resultSet.getString ( "lname" ),
                        resultSet.getString ( "phone" ),
                        resultSet.getString("email"),
                        resultSet.getString ( "id" ),
                        resultSet.getString("house_no"),
                        resultSet.getString("city"),
                        calculateAge ( resultSet.getTimestamp ( "dob" ) ),
                        resultSet.getString ( "cnic" ),
                        new Button(),
                        new Button(),
                        new Button ()
                );
                
                
            }
        } catch (Exception e) {
            System.out.println ( e.getMessage () );;
        }
        return admin;
    }
    
    public final void updateAdminById (String id, String fname, String lname, String cnic, String phone, String email, String house, String city) {
        
        try {
            Connection connectDB = this.getConnection ();
            String query = "UPDATE `admin` SET `fname` = ?, `lname` = ?, `cnic` = ?, `phone` = ?, `email` = ?, `house_no` = ?, `city` = ? WHERE `id` = ?;";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, fname );
            statement.setString ( 2, lname );
            statement.setString ( 3, cnic );
            statement.setString ( 4, phone );
            statement.setString ( 5, email );
            statement.setString ( 6, house );
            statement.setString ( 7, city );
            statement.setString ( 8, id );
            
            statement.executeUpdate ();
            
        } catch (Exception e) {
            System.out.println (e.getMessage ());
        }
       
    }
    
    
    public final void deleteAdminById (String id) {
        try {
	        currentDateTime = LocalDateTime.now();
	        
	        String formattedTimestamp = currentDateTime.format(formatter);
	        
	        Connection connectDB = this.getConnection ();
	        String query = "UPDATE `admin` SET deleted_at = ? WHERE id = ?;";
	        PreparedStatement statement = connectDB.prepareStatement ( query );
	        statement.setString ( 1, formattedTimestamp );
	        statement.setString ( 2, id );
            
            statement.executeUpdate ();
            
        } catch (Exception e) {
            System.out.println ( e.getMessage () );;
        }
    }
    
    public final void addAdmin (String fname, String lname, String cnic, String phone, String email, String house, String city, String password, String dob) {
        try {
            Connection connectDB = this.getConnection ();
            String query = "INSERT INTO `admin` (`fname`, `lname`, `phone`, `email`, `password`, `house_no`, `city`, `dob`, `cnic`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, fname );
            statement.setString ( 2, lname );
            statement.setString ( 3, phone );
            statement.setString ( 4, email );
            statement.setString ( 5, password );
            statement.setString ( 6, house );
            statement.setString ( 7, city );
            statement.setString ( 8, dob );
            statement.setString ( 9, cnic );
            
            statement.executeUpdate ();
            
        } catch (Exception e) {
            System.out.println ( e.getMessage () );;
        }
    }
    
    public final boolean adminRegistered (String email, String cnic) {
        try {
            Connection connectDB = this.getConnection ();
            String query = "SELECT * FROM admin WHERE email = ? OR cnic = ?;";
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, email );
            statement.setString ( 2, cnic );
            
            ResultSet resultSet = statement.executeQuery ();
            
            if (resultSet.next ()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println ( e.getMessage () );;
        }
        return false;
    }
    
    //=====================Doctor Functions ==========================//
    
    public final ObservableList getAllDoctor () {
        ObservableList<Doctor> doctorList = FXCollections.observableArrayList ();
        int count = 0;
        try {
            Connection connectDB = this.getConnection ();
            String query = "SELECT * FROM `doctor` WHERE deleted_at IS NULL";
            PreparedStatement statement = connectDB.prepareStatement ( query );
            
            ResultSet resultSet = statement.executeQuery ();
            while (resultSet.next ()) {
                
                count++;
                Doctor doctor = new Doctor (
                        "" + count,
                        resultSet.getString ( "fname" ) + " " + resultSet.getString ( "lname" ),
                        resultSet.getString ( "phone" ),
                        resultSet.getString("email"),
                        resultSet.getString ( "id" ),
                        resultSet.getString("house_no"),
                        resultSet.getString("city"),
                        calculateAge ( resultSet.getTimestamp ( "dob" ) ),
                        resultSet.getString ( "cnic" ),
                        resultSet.getString ( "department_id" ),
                        new Button(),
                        new Button(),
                        new Button ()
                );
                doctorList.add(doctor);
                
            }
        } catch (Exception e) {
            System.out.println ( e.getMessage () );;
        }
        return doctorList;
    }
    
    public final Doctor getDoctorById (String id) {
        int count = 0;
        Doctor doctor = null;
        try {
            Connection connectDB = this.getConnection ();
            String query = "SELECT * FROM `doctor` WHERE `id` = ?";
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            while (resultSet.next ()) {
                
                count++;
                doctor = new Doctor (
                        "" + count,
                        resultSet.getString ( "fname" ) + " " + resultSet.getString ( "lname" ),
                        resultSet.getString ( "phone" ),
                        resultSet.getString("email"),
                        resultSet.getString ( "id" ),
                        resultSet.getString("house_no"),
                        resultSet.getString("city"),
                        calculateAge ( resultSet.getTimestamp ( "dob" ) ),
                        resultSet.getString ( "cnic" ),
                        resultSet.getString ( "department_id" ),
                        new Button(),
                        new Button(),
                        new Button ()
                );
                
                
            }
        } catch (Exception e) {
            System.out.println ( e.getMessage () );;
        }
        return doctor;
    }
    
    public final void updateDoctorById (String id, String fname, String lname, String cnic, String phone, String email, String house, String city, String departmentId) {
        
        try {
            Connection connectDB = this.getConnection ();
            String query = "UPDATE `doctor` SET `fname` = ?, `lname` = ?, `cnic` = ?, `phone` = ?, `email` = ?, `house_no` = ?, `city` = ?, `department_id` = ? WHERE `id` = ?;";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, fname );
            statement.setString ( 2, lname );
            statement.setString ( 3, cnic );
            statement.setString ( 4, phone );
            statement.setString ( 5, email );
            statement.setString ( 6, house );
            statement.setString ( 7, city );
            statement.setString ( 8, departmentId );
            statement.setString ( 9, id );
            
            statement.executeUpdate ();
            
        } catch (Exception e) {
            System.out.println (e.getMessage ());
        }
        
    }
    
    public final void deleteDoctorById (String id) {
        try {
	        
	        currentDateTime = LocalDateTime.now();
	        
	        String formattedTimestamp = currentDateTime.format(formatter);
	        
	        Connection connectDB = this.getConnection ();
	        String query = "UPDATE `doctor` SET deleted_at = ? WHERE id = ?;";
	        PreparedStatement statement = connectDB.prepareStatement ( query );
	        statement.setString ( 1, formattedTimestamp );
	        statement.setString ( 2, id );
            
            statement.executeUpdate ();
            
        } catch (Exception e) {
            System.out.println (e.getMessage ());
        }
    }
    
    public final void addDoctor (String fname, String lname, String cnic, String phone, String email, String house, String city, String password, String dob, String departmentId) {
        try {
            Connection connectDB = this.getConnection ();
            String query = "INSERT INTO `doctor` (`fname`, `lname`, `phone`, `email`, `password`, `house_no`, `city`, `dob`, `cnic`, `department_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, fname );
            statement.setString ( 2, lname );
            statement.setString ( 3, phone );
            statement.setString ( 4, email );
            statement.setString ( 5, password );
            statement.setString ( 6, house );
            statement.setString ( 7, city );
            statement.setString ( 8, dob );
            statement.setString ( 9, cnic );
            statement.setString ( 10, departmentId );
            
            statement.executeUpdate ();
            
        } catch (Exception e) {
            System.out.println ( e.getMessage () );;
        }
    }
    
    public final boolean doctorRegistered (String email, String cnic) {
        try {
            Connection connectDB = this.getConnection ();
            String query = "SELECT * FROM doctor WHERE email = ? OR cnic = ?;";
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, email );
            statement.setString ( 2, cnic );
            
            ResultSet resultSet = statement.executeQuery ();
            
            if (resultSet.next ()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println ( e.getMessage () );;
        }
        return false;
    }
    
    
    //=======================Department Functions ==========================//
    
    
    public final Department getDepartmentById ( String id) {
        int count = 0;
        Department department = null;
        try {
            Connection connectDB = this.getConnection ();
            String query = "SELECT * FROM `department` WHERE `id` = ?";
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            while (resultSet.next ()) {
                
                count++;
                department = new Department (
                        "" + count,
                        resultSet.getString ( "name" ),
                        resultSet.getString ( "id" ),
                        new Button(),
                        new Button(),
                        new Button ()
                );
                
                
            }
        } catch (Exception e) {
            System.out.println ( e.getMessage () );;
        }
        return department;
    }
    
    public final ObservableList getAllDepartment () {
        ObservableList<Department> departmentList = FXCollections.observableArrayList ();
        int count = 0;
        try {
            Connection connectDB = this.getConnection ();
            String query = "SELECT * FROM `department` WHERE deleted_at IS NULL";
            PreparedStatement statement = connectDB.prepareStatement ( query );
            
            ResultSet resultSet = statement.executeQuery ();
            while (resultSet.next ()) {
                
                count++;
                Department department = new Department (
                        "" + count,
                        resultSet.getString ( "name" ),
                        resultSet.getString ( "id" ),
                        new Button(),
                        new Button(),
                        new Button ()
                );
                departmentList.add(department);
                
            }
        } catch (Exception e) {
            System.out.println ( e.getMessage () );;
        }
        return departmentList;
    }
    
    public final void updateDepartmentById (String id, String name) {
        
        try {
            Connection connectDB = this.getConnection ();
            String query = "UPDATE `department` SET `name` = ? WHERE `id` = ?;";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, name );
            statement.setString ( 2, id );
            
            statement.executeUpdate ();
            
        } catch (Exception e) {
            System.out.println (e.getMessage ());
        }
        
    }
    
    public final void deleteDepartmentById (String id) {
        try {
            currentDateTime = LocalDateTime.now();
            
            
            String formattedTimestamp = currentDateTime.format(formatter);
            
            Connection connectDB = this.getConnection ();
            String query = "UPDATE `department` SET deleted_at = ? WHERE id = ?;";
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, formattedTimestamp );
            statement.setString ( 2, id );
            
            statement.executeUpdate ();
            
        } catch (Exception e) {
            System.out.println (e.getMessage ());
        }
    }
    
    public final void addDepartment (String name) {
        try {
            Connection connectDB = this.getConnection ();
            String query = "INSERT INTO `department` (`name`) VALUES (?)";
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, name );
            
            statement.executeUpdate ();
            
        } catch (Exception e) {
            System.out.println ( e.getMessage () );;
        }
    }
    
    public final boolean departmentRegistered (String name) {
        try {
            Connection connectDB = this.getConnection ();
            String query = "SELECT * FROM department WHERE name = ?;";
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, name );
            
            ResultSet resultSet = statement.executeQuery ();
            
            if (resultSet.next ()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println ( e.getMessage () );;
        }
        return false;
    }
    
    
    //=========================Patient Functions =============================//
    
    public final ObservableList getAllPatient () {
        ObservableList<Patient> patientList = FXCollections.observableArrayList ();
        int count = 0;
        try {
            Connection connectDB = this.getConnection ();
            String query = "SELECT * FROM `patient` WHERE deleted_at IS NULL";
            PreparedStatement statement = connectDB.prepareStatement ( query );
            
            ResultSet resultSet = statement.executeQuery ();
            while (resultSet.next ()) {
                
                count++;
                Patient admin = new Patient (
                        "" + count,
                        resultSet.getString ( "fname" ) + " " + resultSet.getString ( "lname" ),
                        resultSet.getString ( "phone" ),
                        resultSet.getString("email"),
                        resultSet.getString ( "id" ),
                        resultSet.getString("house_no"),
                        resultSet.getString("city"),
                        calculateAge ( resultSet.getTimestamp ( "dob" ) ),
                        resultSet.getString ( "cnic" ),
                        new Button(),
                        new Button(),
                        new Button ()
                );
                patientList.add(admin);
                
            }
        } catch (Exception e) {
            System.out.println ( e.getMessage () );;
        }
        return patientList;
    }
    
    public final Patient getPatientById (String id) {
        int count = 0;
        Patient patient = null;
        try {
            Connection connectDB = this.getConnection ();
            String query = "SELECT * FROM `patient` WHERE `id` = ?";
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            while (resultSet.next ()) {
                
                count++;
                patient = new Patient (
                        "" + count,
                        resultSet.getString ( "fname" ) + " " + resultSet.getString ( "lname" ),
                        resultSet.getString ( "phone" ),
                        resultSet.getString("email"),
                        resultSet.getString ( "id" ),
                        resultSet.getString("house_no"),
                        resultSet.getString("city"),
                        calculateAge ( resultSet.getTimestamp ( "dob" ) ),
                        resultSet.getString ( "cnic" ),
                        new Button(),
                        new Button(),
                        new Button ()
                );
                
                
            }
        } catch (Exception e) {
            System.out.println ( e.getMessage () );;
        }
        return patient;
    }
    
    public final void updatePatientById (String id, String fname, String lname, String cnic, String phone, String email, String house, String city) {
        
        try {
            Connection connectDB = this.getConnection ();
            String query = "UPDATE `patient` SET `fname` = ?, `lname` = ?, `cnic` = ?, `phone` = ?, `email` = ?, `house_no` = ?, `city` = ? WHERE `id` = ?;";
            
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, fname );
            statement.setString ( 2, lname );
            statement.setString ( 3, cnic );
            statement.setString ( 4, phone );
            statement.setString ( 5, email );
            statement.setString ( 6, house );
            statement.setString ( 7, city );
            statement.setString ( 8, id );
            
            statement.executeUpdate ();
            
        } catch (Exception e) {
            System.out.println (e.getMessage ());
        }
        
    }
    
    public final void deletePatientById (String id) {
        try {
	        currentDateTime = LocalDateTime.now();
	        
	        String formattedTimestamp = currentDateTime.format(formatter);
			
            Connection connectDB = this.getConnection ();
            String query = "UPDATE `patient` SET deleted_at = ? WHERE id = ?;";
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, formattedTimestamp );
	        statement.setString ( 2, id );
            
            statement.executeUpdate ();
            
        } catch (Exception e) {
            System.out.println (e.getMessage ());
        }
    }
    
    
    //===========================Appointment Funcitons =====================//
    
    public final ObservableList getAllAppointment () {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList ();
        int count = 0;
        try {
            Connection connectDB = this.getConnection ();
            String query = "SELECT * FROM `appointment` WHERE deleted_at IS NULL";
            PreparedStatement statement = connectDB.prepareStatement ( query );
            
            ResultSet resultSet = statement.executeQuery ();
            while (resultSet.next ()) {
                
                Patient patient = getPatientById ( resultSet.getString ( "patient_id" ) );
                Doctor doctor = getDoctorById ( resultSet.getString ( "doctor_id" ) );
                Department department = getDepartmentById ( resultSet.getString ( "department_id" ) );
                Timestamp timestamp = resultSet.getTimestamp ("time");
                
                count++;
                Appointment appointment = new Appointment (
                        "" + count,
                        resultSet.getString("id"),
                        patient.getName (),
                        doctor.getName (),
                        department.getName (),
                        timestamp.toLocalDateTime ().toLocalTime ().toString (),
                        timestamp.toLocalDateTime().toLocalDate ().toString (),
                        resultSet.getBoolean ("visited"),
                        resultSet.getString ( "prescription" ),
                        new Button(),
                        new Button ()
                );
                appointmentList.add(appointment);
                
            }
        } catch (Exception e) {
            System.out.println ( e.getMessage () );;
        }
        return appointmentList;
    }
    
    public final Appointment getAppointmentById (String id) {
        int count = 0;
        Appointment appointment = null;
        try {
            Connection connectDB = this.getConnection ();
            String query = "SELECT * FROM `appointment` WHERE deleted_at IS NULL AND `id` = ?";
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, id );
            
            ResultSet resultSet = statement.executeQuery ();
            while (resultSet.next ()) {
                
                Patient patient = getPatientById ( resultSet.getString ( "patient_id" ) );
                Doctor doctor = getDoctorById ( resultSet.getString ( "doctor_id" ) );
                Department department = getDepartmentById ( resultSet.getString ( "department_id" ) );
                Timestamp timestamp = resultSet.getTimestamp ("time");
                count++;
                appointment = new Appointment (
                        "" + count,
                        resultSet.getString("id"),
                        patient.getName (),
                        doctor.getName (),
                        department.getName (),
                        timestamp.toLocalDateTime ().toLocalTime ().toString (),
                        timestamp.toLocalDateTime().toLocalDate ().toString (),
                        resultSet.getBoolean ("visited"),
                        resultSet.getString ( "prescription" ),
                        new Button(),
                        new Button ()
                );
                
                
            }
        } catch (Exception e) {
            System.out.println ( e.getMessage () );;
        }
        return appointment;
    }
    
    public final void deleteAppointmentById (String id) {
        try {
            currentDateTime = LocalDateTime.now();
            
            String formattedTimestamp = currentDateTime.format(formatter);
            
            Connection connectDB = this.getConnection ();
            String query = "UPDATE `appointment` SET deleted_at = ? WHERE id = ?;";
            PreparedStatement statement = connectDB.prepareStatement ( query );
            statement.setString ( 1, formattedTimestamp );
            statement.setString ( 2, id );
            
            statement.executeUpdate ();
            
        } catch (Exception e) {
            System.out.println (e.getMessage ());
        }
    }
    
    
}
