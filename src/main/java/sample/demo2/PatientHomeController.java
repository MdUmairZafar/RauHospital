package sample.demo2;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class PatientHomeController {

    boolean show = true;

    @FXML
    private Pane changePwdBoxHome, footerHome, navHome, exitApplicationBoxHome;
    @FXML
    private PasswordField changePwdCurrentHome, changePwdNewHome, changePwdRewriteHome;
    @FXML
    private Label invalidLabelChangePwd, validLabelChangePwd;
    @FXML
    private Label welcomeHome;

    private  String currentUser;

    public void initiatePage() {
        welcomeHome.setText("Welcome " + currentUser);
    }

    public void setCurrentUser ( String string) {
        currentUser = string;

    }

    public void toLandingPage ( ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("landing.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showSettings () {

        if (show) {

            changePwdCurrentHome.setStyle("-fx-border-style: none");
            changePwdNewHome.setStyle("-fx-border-style: none");
            changePwdRewriteHome.setStyle("-fx-border-style: none");
            invalidLabelChangePwd.setText("");
            validLabelChangePwd.setText("");

            changePwdBoxHome.setVisible(true);
            exitApplicationBoxHome.setVisible(true);
            footerHome.setEffect(new BoxBlur());
            navHome.setEffect(new BoxBlur());
            navHome.setDisable(true);
            show = false;
        } else {
            changePwdBoxHome.setVisible(false);
            exitApplicationBoxHome.setVisible(false);
            navHome.setDisable(false);
            validLabelChangePwd.setText("");
            setFieldsColor ("none");
            
            DropShadow shadow = new DropShadow();
            shadow.setWidth(36);
            shadow.setHeight(35);
            shadow.setColor(Color.color(0,0,0,0.72));
            footerHome.setEffect(shadow);
            shadow.setWidth(5);
            shadow.setHeight(15);
            navHome.setEffect(shadow);

            show = true;
        }
    }

    public void exitApplication () {
        Platform.exit();
    }

    private void setFieldsColor ( String color) {
        changePwdCurrentHome.setStyle("-fx-border-color: "+ color +";");
        changePwdNewHome.setStyle("-fx-border-color: "+ color+ ";");
        changePwdRewriteHome.setStyle("-fx-border-color: "+ color +";");
    }

    private void setFieldsEmpty () {
        validLabelChangePwd.setText("");
        changePwdCurrentHome.setText("");
        changePwdNewHome.setText("");
        changePwdRewriteHome.setText("");
    }


    public void changePassword () {

        if ( changePwdCurrentHome.getText().isBlank() ||
            changePwdNewHome.getText().isBlank() ||
            changePwdRewriteHome.getText().isBlank()
            ) {
                invalidLabelChangePwd.setText("Please Fill Carefully!");
                setFieldsEmpty ();
                setFieldsColor ("red");

        } else if ( changePwdCurrentHome.getText().equals( changePwdNewHome.getText())) {
            invalidLabelChangePwd.setText("A new Password Should Not Match Current Password");
            setFieldsEmpty ();
            setFieldsColor ("red");

        } else if ((!changePwdNewHome.getText().equals( changePwdRewriteHome.getText()))) {
            invalidLabelChangePwd.setText("A new Password Should Match with Confirmation Field");
            setFieldsEmpty ();
            setFieldsColor ("red");
            
        } else if (!PasswordValidator.isValid( changePwdNewHome )) {
            invalidLabelChangePwd.setText("Password Should 8 Digits Long including Alphabets, Numbers, Special Characters");
            setFieldsEmpty ();
            setFieldsColor ("red");
        } else {
            updatePassword ();
        }

    }
    public void updatePassword () {
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            

            PreparedStatement statement = connectDB.prepareStatement(DatabaseConnection.updateQuery );
            statement.setString(1, changePwdNewHome.getText());
            statement.setString(2, currentUser );
            statement.setString(3, changePwdCurrentHome.getText());

            int change = statement.executeUpdate();

            if (change == 1) {
                setFieldsColor ("green");
                setFieldsEmpty ();
                invalidLabelChangePwd.setText("");
                validLabelChangePwd.setText("Password Changed Successfully!");
            }
            else {
                setFieldsEmpty ();
                setFieldsColor ("red");
                invalidLabelChangePwd.setText("Wrong Current Password! Write Carefully!");
            }
            connectDB.close();

        } catch ( Exception e) {
            e.printStackTrace();
        }
    }
}