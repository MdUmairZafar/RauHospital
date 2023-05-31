package sample.demo2;import Model.Admin;import javafx.collections.ObservableList;import javafx.event.ActionEvent;import javafx.fxml.FXML;import javafx.fxml.FXMLLoader;import javafx.fxml.Initializable;import javafx.scene.Node;import javafx.scene.Parent;import javafx.scene.Scene;import javafx.scene.control.Label;import javafx.stage.Stage;import java.io.IOException;import java.net.URL;import java.util.Objects;import java.util.ResourceBundle;public class AdminViewSingleAdminController implements Initializable {	@FXML	private Label adminAddress, adminAge, adminCity, adminCnic, adminEmail, id, adminName, adminPhone;	private static String adminId;	private Stage stage;	private Scene scene;	private Parent root;	public static void setAdminId ( String adminId ) {		AdminViewSingleAdminController.adminId = adminId;	}		@Override	public void initialize ( URL url, ResourceBundle resourceBundle ) {		try {			DatabaseConnection database = DatabaseConnection.getInstance ();			Admin admin = database.getAdminById ( adminId );			id.setText ( admin.getId () );			adminName.setText ( admin.getName () );			adminAddress.setText ( admin.getHouse () );			adminAge.setText ( admin.getAge () );			adminCity.setText ( admin.getCity () );			adminCnic.setText ( admin.getCnic () );			adminEmail.setText ( admin.getEmail () );			adminPhone.setText ( admin.getPhone () );		} catch (Exception e) {			System.out.println (e.getMessage ());		}	}		public void toAllPatient ( ActionEvent event) throws IOException {		root = FXMLLoader.load( Objects.requireNonNull(getClass().getResource("adminAllPatients.fxml")));		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();		scene = new Scene (root);		stage.setScene(scene);		stage.show();	}		public void toAllAdmin ( ActionEvent event) throws IOException {		root = FXMLLoader.load( Objects.requireNonNull(getClass().getResource("adminAllAdmin.fxml")));		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();		scene = new Scene (root);		stage.setScene(scene);		stage.show();	}		public void toAdminHome ( ActionEvent event) throws IOException {		root = FXMLLoader.load( Objects.requireNonNull(getClass().getResource("adminHome.fxml")));		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();		scene = new Scene (root);		stage.setScene(scene);		stage.show();	}	public void toAllDoctor ( ActionEvent event) throws IOException {		root = FXMLLoader.load( Objects.requireNonNull(getClass().getResource("adminAllDoctor.fxml")));		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();		scene = new Scene (root);		stage.setScene(scene);		stage.show();	}		public void logout ( ActionEvent event) throws IOException {		root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("landing.fxml")));		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();		scene = new Scene(root);		stage.setScene(scene);		stage.show();	}}