package sample.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class LoginController {
    
    // Loading FXML fx:ids
    @FXML
    private Label invalidCredLabelLogin;
    @FXML
    private TextField emailFormLogin, passwordFormLogin;
    @FXML
    private Button loginButtonFormLogin;

    //Gloabl Variables
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
    public void toHomePage () throws  IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("patientHome.fxml"));
        root = loader.load();
        PatientHomeController home = loader.getController();
        home.setCurrentUser ( emailFormLogin.getText());
        home.initiatePage();
        scene = new Scene(root);
        stage = (Stage) loginButtonFormLogin.getScene().getWindow();
        stage.setScene(scene);
    }


    
    public void loginButtonSetOnAction() {
        if (!emailFormLogin.getText().isBlank() && !passwordFormLogin.getText().isBlank()) {
            validateLogin();
        }
        else {
            emailFormLogin.setText("");
            passwordFormLogin.setText("");
            invalidCredLabelLogin.setText("Invalid Login Credentials!");
            emailFormLogin.setStyle("-fx-border-color: red;");
            passwordFormLogin.setStyle("-fx-border-color: red;");

        }
    }
    

    public void validateLogin() {
        try {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        

            PreparedStatement statement = connectDB.prepareStatement(DatabaseConnection.loginQuery);
            statement.setString(1, emailFormLogin.getText());
            statement.setString(2, passwordFormLogin.getText());

            ResultSet queryResult = statement.executeQuery();

            if(queryResult.next()) {
                invalidCredLabelLogin.setText("");
                toHomePage ();

            } else {
                emailFormLogin.setText("");
                passwordFormLogin.setText("");
                invalidCredLabelLogin.setText("INVALID LOGIN CREDENTIALS!");
                emailFormLogin.setStyle("-fx-border-color: red;");
                passwordFormLogin.setStyle("-fx-border-color: red;");
            }

            connectDB.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}
