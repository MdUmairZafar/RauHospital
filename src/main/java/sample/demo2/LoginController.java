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

    @FXML
    private Label invalidCredLabelLogin;
    @FXML
    private TextField emailFormLogin;
    @FXML
    private TextField passwordFormLogin;
    @FXML
    private Button loginButtonFormLogin;


    private Stage stage;
    private Scene scene;
    private Parent root;

    public void toRegistrationForm ( ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("registration.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void toLandingPage ( ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("landing.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void toHomePage () throws  IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        HomeController.setCurrentUser ( emailFormLogin.getText());
        root = loader.load();
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
            invalidCredLabelLogin.setText("INVALID LOGIN CREDENTIALS!");
            emailFormLogin.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 1;" + "-fx-border-color: red;");
            passwordFormLogin.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 1;" + "-fx-border-color: red;");

        }
    }
    

    public void validateLogin() {
        try {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String login_query = "SELECT * FROM users WHERE email = ? AND password = ?";

            PreparedStatement statement = connectDB.prepareStatement(login_query);
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
                emailFormLogin.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 1;" + "-fx-border-color: red;");
                passwordFormLogin.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 1;" + "-fx-border-color: red;");
            }

            connectDB.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}
