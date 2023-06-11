package sample.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {
    
    // Loading FXML fx:ids
    @FXML
    private Label invalidCredLabelLogin;
    @FXML
    private TextField emailFormLogin, passwordFormLogin;
    @FXML
    private Button loginButtonFormLogin;
    @FXML
    private CheckBox patientCheckBox, doctorCheckBox, adminCheckBox;

    //Global Variables
    private Stage stage;
    private Scene scene;
    private Parent root;

    //A function to go to Registration Form from Login Form
    public void toRegistrationForm ( ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("registration.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //A function to go to Landing Page from Login Form
    public void toLandingPage ( ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("landing.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //A function to go to Homepage from Login Form
    public void toPatientHomePage () throws IOException, SQLException {
        DatabaseConnection.user = emailFormLogin.getText ();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("patientHome.fxml"));
        root = loader.load();
        PatientHomeController home = loader.getController();
        scene = new Scene(root);
        stage = (Stage) loginButtonFormLogin.getScene().getWindow();
        stage.setScene(scene);
    }
    
    public void toDoctorHomePage () throws IOException, SQLException {
        DatabaseConnection.user = emailFormLogin.getText ();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("doctorHome.fxml"));
        root = loader.load();
        DoctorHomeController home = loader.getController();
        scene = new Scene(root);
        stage = (Stage) loginButtonFormLogin.getScene().getWindow();
        stage.setScene(scene);
    }
    
    public void toAdminHomePage () throws IOException, SQLException {
        DatabaseConnection.user = emailFormLogin.getText ();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminHome.fxml"));
        root = loader.load();
        AdminHomeController home = loader.getController();
        scene = new Scene(root);
        stage = (Stage) loginButtonFormLogin.getScene().getWindow();
        stage.setScene(scene);
    }
    
    private void setFieldsEmpty () {
        emailFormLogin.setText("");
        passwordFormLogin.setText("");
        setCheckBoxesEmpty ();
    }
    
    private void setCheckBoxesEmpty () {
        patientCheckBox.setSelected ( false );
        doctorCheckBox.setSelected ( false );
        adminCheckBox.setSelected ( false );
    }

    
    public void loginButtonSetOnAction() {
        if (!emailFormLogin.getText().isBlank() && !passwordFormLogin.getText().isBlank()) {
            
            if (patientCheckBox.isSelected () && !doctorCheckBox.isSelected () && !adminCheckBox.isSelected ()) {
                validatePatientLogin ();
            }
            else if (!patientCheckBox.isSelected () && doctorCheckBox.isSelected () && !adminCheckBox.isSelected ()) {
                validateDoctorLogin ();
            }
            else if (!patientCheckBox.isSelected () && !doctorCheckBox.isSelected () && adminCheckBox.isSelected ()) {
                validateAdminLogin ();
            }
            else {
                invalidCredLabelLogin.setText ( "Please Choose a Single Checkbox!" );
                setCheckBoxesEmpty ();
            }
            
        }
        else {
            setFieldsEmpty ();
            invalidCredLabelLogin.setText("Invalid Login Credentials!");
            emailFormLogin.setStyle("-fx-border-color: #eb1111b5;");
            passwordFormLogin.setStyle("-fx-border-color: #eb1111b5;");

        }
    }
    

    public void validatePatientLogin () {
        try {
        DatabaseConnection connectNow = DatabaseConnection.getInstance ();
        Connection connectDB = connectNow.getConnection();
        

            PreparedStatement statement = connectDB.prepareStatement(DatabaseConnection.patientLoginQuery );
            statement.setString(1, emailFormLogin.getText());
            statement.setString(2, passwordFormLogin.getText());

            ResultSet queryResult = statement.executeQuery();

            if(queryResult.next()) {
                PatientHomeController.setPatientId ( queryResult.getString ( "id" ) );
                invalidCredLabelLogin.setText("");
                toPatientHomePage ();

            } else {
                setFieldsEmpty ();
                invalidCredLabelLogin.setText("INVALID LOGIN CREDENTIALS!");
                emailFormLogin.setStyle("-fx-border-color: #eb1111b5;");
                passwordFormLogin.setStyle("-fx-border-color: #eb1111b5;");
            }

            connectDB.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
    
    public void validateDoctorLogin () {
        try {
            DatabaseConnection connectNow = DatabaseConnection.getInstance ();
            Connection connectDB = connectNow.getConnection();
            
            
            PreparedStatement statement = connectDB.prepareStatement(DatabaseConnection.doctorLoginQuery );
            statement.setString(1, emailFormLogin.getText());
            statement.setString(2, passwordFormLogin.getText());
            
            ResultSet queryResult = statement.executeQuery();
            
            if(queryResult.next()) {
                DoctorHomeController.setDoctorId ( queryResult.getString ( "id" ) );
                invalidCredLabelLogin.setText("");
                toDoctorHomePage ();
                
            } else {
                setFieldsEmpty ();
                invalidCredLabelLogin.setText("INVALID LOGIN CREDENTIALS!");
                emailFormLogin.setStyle("-fx-border-color: #eb1111b5;");
                passwordFormLogin.setStyle("-fx-border-color: #eb1111b5;");
            }
            
            connectDB.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
    
    public void validateAdminLogin () {
        try {
            DatabaseConnection connectNow = DatabaseConnection.getInstance ();
            Connection connectDB = connectNow.getConnection();
            
            
            PreparedStatement statement = connectDB.prepareStatement(DatabaseConnection.adminLoginQuery );
            statement.setString(1, emailFormLogin.getText());
            statement.setString(2, passwordFormLogin.getText());
            
            ResultSet queryResult = statement.executeQuery();
            
            if(queryResult.next()) {

                invalidCredLabelLogin.setText("");
                toAdminHomePage ();
                
            } else {
                setFieldsEmpty ();
                invalidCredLabelLogin.setText("INVALID LOGIN CREDENTIALS!");
                emailFormLogin.setStyle("-fx-border-color: #eb1111b5;");
                passwordFormLogin.setStyle("-fx-border-color: #eb1111b5;");
            }
            
            connectDB.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
    

}
