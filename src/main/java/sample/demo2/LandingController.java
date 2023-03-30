package sample.demo2;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LandingController {

    @FXML
    private Pane navLand, bodyLand, footerLand, exitApplicationBoxLand;
    boolean show = true;
    Parent root;
    Stage stage;
    Scene scene;
    public void toRegistrationForm ( ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("registration.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void toLoginForm ( ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void showSettings () {
        
        if (show) {
            exitApplicationBoxLand.setVisible(true);
            footerLand.setEffect(new BoxBlur ());
            navLand.setEffect(new BoxBlur());
            bodyLand.setEffect (new BoxBlur ());
            navLand.setDisable(true);
            bodyLand.setDisable(true);
            show = false;
        } else {
            exitApplicationBoxLand.setVisible ( false );
            DropShadow shadow = new DropShadow();
            shadow.setWidth(36);
            shadow.setHeight(35);
            shadow.setColor( Color.color(0,0,0,0.72));
            footerLand.setEffect(shadow);
            shadow.setWidth(5);
            shadow.setHeight(15);
            navLand.setEffect(shadow);
            navLand.setDisable(false);
            bodyLand.setEffect(null);
            bodyLand.setDisable(false);
            
            show = true;
        }
    }
    
    public void exitApplication () {
        Platform.exit();
    }
    
}
