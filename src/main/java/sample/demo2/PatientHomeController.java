package sample.demo2;

import Model.Appointment;
import Model.Patient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class PatientHomeController implements Initializable {

    boolean show = true;

    @FXML
    private Pane changePwdBoxHome, footerHome, navHome, exitApplicationBoxHome;
    @FXML
    private PasswordField changePwdCurrentHome, changePwdNewHome, changePwdRewriteHome;
    @FXML
    private Label invalidLabelChangePwd, validLabelChangePwd, patName, patAge, patPhone, patEmail, patId, patCnic;
    
    @FXML
    private TableColumn<Appointment, String> srNo, time,aptId,date, docName;
    @FXML
    private TableColumn <Appointment, Button> viewButton;
    @FXML
    private TableView <Appointment> aptTable;
    
    public static String patientId;
    
    public static void setPatientId ( String patientId ) {
        PatientHomeController.patientId = patientId;
    }
    
    public static String getPatientId () {
        return patientId;
    }
    
    DatabaseConnection database = DatabaseConnection.getInstance ();
    
    
    @Override
    public void initialize ( URL url, ResourceBundle resourceBundle ) {
        try {
            Patient patient = database.getPatientById ( patientId );
            patName.setText ( patient.getName () );
            patCnic.setText ( patient.getCnic () );
            patAge.setText ( patient.getAge () );
            patEmail.setText ( patient.getEmail () );
            patPhone.setText ( patient.getPhone () );
            patId.setText ( patientId );
            
            ObservableList<Appointment> appointmentList = database.getAllAppointmentByPatient ( patientId );
            
            srNo.setCellValueFactory(new PropertyValueFactory <> ("srNo"));
            aptId.setCellValueFactory(new PropertyValueFactory<>("id"));
            docName.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            time.setCellValueFactory(new PropertyValueFactory<>("time"));
            
            viewButton.setCellValueFactory(new PropertyValueFactory<>("viewButton"));
            
            aptTable.setItems ( appointmentList );
            viewButton.setCellFactory(param -> new TableCell <Appointment, Button> () {
                @Override
                protected void updateItem(Button button, boolean empty) {
                    super.updateItem(button, empty);
                    
                    if (empty || button == null) {
                        setGraphic(null);
                    } else {
                        setGraphic(button);
                        button.setOnAction(event -> {
                            Appointment appointment = getTableView().getItems().get(getIndex());
                            PatientViewSingleAppointmentController.setAppointmentId(appointment.getId ());
                            
                            try {
                                toViewAppointment(event);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                }
            });
        
        } catch (Exception e) {
                e.printStackTrace ();
        }
    }


    public void logout ( ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("landing.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void toViewAppointment ( ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patientViewSingleAppointment.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void toMakeAppointment (ActionEvent event ) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patientMakeAppointment.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
  
//    public void showSettings () {
//
//        if (show) {
//
//            changePwdCurrentHome.setStyle("-fx-border-style: none");
//            changePwdNewHome.setStyle("-fx-border-style: none");
//            changePwdRewriteHome.setStyle("-fx-border-style: none");
//            invalidLabelChangePwd.setText("");
//            validLabelChangePwd.setText("");
//
//            changePwdBoxHome.setVisible(true);
//            exitApplicationBoxHome.setVisible(true);
//            footerHome.setEffect(new BoxBlur());
//            navHome.setEffect(new BoxBlur());
//            navHome.setDisable(true);
//            show = false;
//        } else {
//            changePwdBoxHome.setVisible(false);
//            exitApplicationBoxHome.setVisible(false);
//            navHome.setDisable(false);
//            validLabelChangePwd.setText("");
//            setFieldsColor ("none");
//
//            DropShadow shadow = new DropShadow();
//            shadow.setWidth(36);
//            shadow.setHeight(35);
//            shadow.setColor(Color.color(0,0,0,0.72));
//            footerHome.setEffect(shadow);
//            shadow.setWidth(5);
//            shadow.setHeight(15);
//            navHome.setEffect(shadow);
//
//            show = true;
//        }
//    }
//
//    public void exitApplication () {
//        Platform.exit();
//    }
//
//    private void setFieldsColor ( String color) {
//        changePwdCurrentHome.setStyle("-fx-border-color: "+ color +";");
//        changePwdNewHome.setStyle("-fx-border-color: "+ color+ ";");
//        changePwdRewriteHome.setStyle("-fx-border-color: "+ color +";");
//    }
//
//    private void setFieldsEmpty () {
//        validLabelChangePwd.setText("");
//        changePwdCurrentHome.setText("");
//        changePwdNewHome.setText("");
//        changePwdRewriteHome.setText("");
//    }
//
//    public void changePassword () {
//
//        if ( changePwdCurrentHome.getText().isBlank() ||
//            changePwdNewHome.getText().isBlank() ||
//            changePwdRewriteHome.getText().isBlank()
//            ) {
//                invalidLabelChangePwd.setText("Please Fill Carefully!");
//                setFieldsEmpty ();
//                setFieldsColor ("#eb1111b5");
//
//        } else if ( changePwdCurrentHome.getText().equals( changePwdNewHome.getText())) {
//            invalidLabelChangePwd.setText("A new Password Should Not Match Current Password");
//            setFieldsEmpty ();
//            setFieldsColor ("#eb1111b5");
//
//        } else if ((!changePwdNewHome.getText().equals( changePwdRewriteHome.getText()))) {
//            invalidLabelChangePwd.setText("A new Password Should Match with Confirmation Field");
//            setFieldsEmpty ();
//            setFieldsColor ("#eb1111b5");
//
//        } else if (!PasswordValidator.isValid( changePwdNewHome )) {
//            invalidLabelChangePwd.setText("Password Should 8 Digits Long including Alphabets, Numbers, Special Characters");
//            setFieldsEmpty ();
//            setFieldsColor ("#eb1111b5");
//        } else {
//            updatePassword ();
//        }
//
//    }
//
//    public void updatePassword () {
//        try {
//            Connection connectDB = database.getConnection();
//
//            PreparedStatement statement = connectDB.prepareStatement(DatabaseConnection.updateQuery );
//            statement.setString(1, changePwdNewHome.getText());
//            statement.setString(2, DatabaseConnection.user );
//            statement.setString(3, changePwdCurrentHome.getText());
//
//            int change = statement.executeUpdate();
//
//            if (change == 1) {
//                setFieldsColor ("green");
//                setFieldsEmpty ();
//                invalidLabelChangePwd.setText("");
//                validLabelChangePwd.setText("Password Changed Successfully!");
//            }
//            else {
//                setFieldsEmpty ();
//                setFieldsColor ("#eb1111b5");
//                invalidLabelChangePwd.setText("Wrong Current Password! Write Carefully!");
//            }
//            connectDB.close();
//
//        } catch ( Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    
    


    
    
}
