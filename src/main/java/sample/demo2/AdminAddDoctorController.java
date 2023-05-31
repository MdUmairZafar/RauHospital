package sample.demo2;import Model.Department;import javafx.collections.FXCollections;import javafx.collections.ObservableList;import javafx.event.ActionEvent;import javafx.fxml.FXML;import javafx.fxml.FXMLLoader;import javafx.fxml.Initializable;import javafx.scene.Node;import javafx.scene.Parent;import javafx.scene.Scene;import javafx.scene.control.*;import javafx.stage.Stage;import java.io.IOException;import java.net.URL;import java.time.LocalDate;import java.time.format.DateTimeFormatter;import java.util.Objects;import java.util.ResourceBundle;public class AdminAddDoctorController implements Initializable {	@FXML	private TextField cityRegister, cnicRegister, emailRegister, fnameRegister, houseNoRegister, phoneNumberRegister, lnameRegister;		@FXML	private ComboBox <String> departmentField;	@FXML	private DatePicker dobRegister;	@FXML	private Label invalidLabelRegister;	@FXML	private PasswordField passwordRegister;	@FXML	private Button registerButtonFormRegister;	DatabaseConnection database = DatabaseConnection.getInstance ();	@Override	public void initialize ( URL url, ResourceBundle resourceBundle ) {		try {			ObservableList<Department> departmentList = database.getAllDepartment ();			ObservableList<String> departmentOptions = FXCollections.observableArrayList ();			for( Department department: departmentList) {				departmentOptions.add(department.getName () + "-" + department.getId ());			}			departmentField.setItems ( departmentOptions );					} catch (Exception e) {			System.out.println (e.getMessage ());		}		}		boolean validPassword = true;	boolean validity = true;	private Stage stage;	private Scene scene;	private Parent root;		public void removeFieldBorder () {		cityRegister.setStyle("-fx-border-style: none;");		fnameRegister.setStyle("-fx-border-style: none;");		lnameRegister.setStyle("-fx-border-style: none;");		phoneNumberRegister.setStyle("-fx-border-style: none;");		emailRegister.setStyle("-fx-border-style: none;");		passwordRegister.setStyle("-fx-border-style: none;");		departmentField.setStyle("-fx-border-style: none;");		houseNoRegister.setStyle("-fx-border-style: none;");		dobRegister.setStyle("-fx-border-color: none;");		cnicRegister.setStyle("-fx-border-style: none;");	}		public void checkFieldsValidity () {		validity = true;		validPassword = true;		LocalDate currentDate = LocalDate.now ();				if ( !AlphabetValidator.isValid ( cityRegister )) {			cityRegister.setStyle("-fx-border-color: #eb1111b5;");			validity = false;		}		if ( !PasswordValidator.isValid( passwordRegister )) {			passwordRegister.setStyle("-fx-border-color: #eb1111b5;");			validPassword = false;		}		if ( departmentField.getItems () == null ) {			departmentField.setStyle("-fx-border-color: #eb1111b5;");			validPassword = false;		}		if ( !AlphabetValidator.isValid ( fnameRegister )) {			fnameRegister.setStyle("-fx-border-color: #eb1111b5;");			validity = false;		}		if ( !AlphabetValidator.isValid ( lnameRegister )) {			lnameRegister.setStyle("-fx-border-color: #eb1111b5;");			validity = false;		}		if ( !NumberValidator.isValid ( phoneNumberRegister )) {			phoneNumberRegister.setStyle("-fx-border-color: #eb1111b5;");			validity = false;		}		if ( houseNoRegister.getText().isBlank()) {			houseNoRegister.setStyle("-fx-border-color: #eb1111b5;");			validity = false;		}		if ( !EmailValidator.isValid( emailRegister )) {			emailRegister.setStyle("-fx-border-color: #eb1111b5;");			validity = false;		}		if ( !CNICValidator.isValid ( cnicRegister )) {			cnicRegister.setStyle("-fx-border-color: #eb1111b5;");			validity = false;		}		if ( dobRegister.getValue () == null || dobRegister.getValue ().isAfter ( currentDate )) {			dobRegister.setStyle("-fx-border-color: #eb1111b5;");			validity = false;		}	}		public void registerAction () {				removeFieldBorder ();		checkFieldsValidity ();				if ( validity ) {			fillData();		}		else if ( !validPassword ) {			invalidLabelRegister.setText(" Password Should 8 Characters Long with at least one Alphabet, Number and Special Character!");		}		else {			invalidLabelRegister.setText("Please Write Carefully And Fill out All the Fields!");		}	}		public void fillData() {		try {			DatabaseConnection database = new DatabaseConnection ();						LocalDate date = dobRegister.getValue ();			String DOB = date.format ( DateTimeFormatter.ofPattern ( "yyyy-MM-dd" ) );												boolean registered = database.doctorRegistered ( emailRegister.getText (), cnicRegister.getText () );						if ( registered ) {				invalidLabelRegister.setText ( "Doctor Already Registered!" );				cnicRegister.setStyle ( "-fx-border-color: #eb1111b5;" );				emailRegister.setStyle ( "-fx-border-color: #eb1111b5;" );							} else {				String selectedDepartment = departmentField.getValue ();				System.out.println (departmentField.getValue ());				String[] department = selectedDepartment.split("-");				database.addDoctor ( fnameRegister.getText (), lnameRegister.getText (), cnicRegister.getText (), phoneNumberRegister.getText (), emailRegister.getText (), houseNoRegister.getText (), cityRegister.getText (), passwordRegister.getText (), DOB, department[1]);				toAllDoctor ( );			}					} catch ( Exception e ) {			System.out.println (e.getMessage ());		}	}		private void toAllDoctor () {		try {			FXMLLoader loader = new FXMLLoader ( getClass ().getResource ( "adminAllDoctor.fxml" ) );			root = loader.load ();			scene = new Scene ( root );			stage = (Stage) registerButtonFormRegister.getScene ().getWindow ();			stage.setScene ( scene );		} catch (Exception e) {			System.out.println (e.getMessage ());		}	}		public void toAllPatient ( ActionEvent event) throws IOException {		root = FXMLLoader.load( Objects.requireNonNull(getClass().getResource("adminAllPatients.fxml")));		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();		scene = new Scene (root);		stage.setScene(scene);		stage.show();	}		public void toAllAdmin ( ActionEvent event) throws IOException {		root = FXMLLoader.load( Objects.requireNonNull(getClass().getResource("adminAllAdmin.fxml")));		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();		scene = new Scene (root);		stage.setScene(scene);		stage.show();	}		public void toAdminHome ( ActionEvent event) throws IOException {		root = FXMLLoader.load( Objects.requireNonNull(getClass().getResource("adminHome.fxml")));		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();		scene = new Scene (root);		stage.setScene(scene);		stage.show();	}	public void toAllDoctor ( ActionEvent event) throws IOException {		root = FXMLLoader.load( Objects.requireNonNull(getClass().getResource("adminAllDoctor.fxml")));		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();		scene = new Scene (root);		stage.setScene(scene);		stage.show();	}		public void logout ( ActionEvent event) throws IOException {		root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("landing.fxml")));		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();		scene = new Scene(root);		stage.setScene(scene);		stage.show();	}}