package sample.demo2;import Model.Department;import javafx.collections.FXCollections;import javafx.collections.ObservableList;import javafx.event.ActionEvent;import javafx.fxml.FXML;import javafx.fxml.FXMLLoader;import javafx.fxml.Initializable;import javafx.scene.Node;import javafx.scene.Parent;import javafx.scene.Scene;import javafx.scene.control.Label;import javafx.scene.layout.HBox;import javafx.scene.layout.VBox;import javafx.stage.Stage;import java.io.IOException;import java.net.URL;import java.util.ArrayList;import java.util.List;import java.util.Objects;import java.util.ResourceBundle;public class DoctorHomeController implements Initializable {	@FXML	private VBox appointmentTable;	@FXML	private Label cnic, department, email, id, name, phone;	private Stage stage;	private Scene scene;	private Parent root;		DatabaseConnection database = DatabaseConnection.getInstance ();		@Override	public void initialize ( URL url, ResourceBundle resourceBundle ) {				}		public void toLandingPage ( ActionEvent event) throws IOException {		root = FXMLLoader.load( Objects.requireNonNull(getClass().getResource("landing.fxml")));		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();		scene = new Scene (root);		stage.setScene(scene);		stage.show();	}			}