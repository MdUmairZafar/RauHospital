package sample.demo2;import Model.Department;import javafx.collections.ObservableList;import javafx.event.ActionEvent;import javafx.fxml.FXML;import javafx.fxml.FXMLLoader;import javafx.fxml.Initializable;import javafx.scene.Node;import javafx.scene.Parent;import javafx.scene.Scene;import javafx.scene.control.Button;import javafx.scene.control.TableCell;import javafx.scene.control.TableColumn;import javafx.scene.control.TableView;import javafx.scene.control.cell.PropertyValueFactory;import javafx.stage.Stage;import java.io.IOException;import java.net.URL;import java.util.Objects;import java.util.ResourceBundle;public class AdminAllDepartmentController implements Initializable {	@FXML	private TableColumn <Department, String> dptId, dptName, srNo;	@FXML	private TableColumn<Department, Button> editButton, deleteButton;	@FXML	private TableView <Department> dptTable;	private Stage stage;	private Scene scene;	private Parent root;		DatabaseConnection database = DatabaseConnection.getInstance ();		@Override	public void initialize ( URL url, ResourceBundle resourceBundle ) {		try {			ObservableList <Department> departmentList = database.getAllDepartment ();						srNo.setCellValueFactory(new PropertyValueFactory <> ("srNo"));			dptId.setCellValueFactory(new PropertyValueFactory<>("id"));			dptName.setCellValueFactory(new PropertyValueFactory<>("name"));			editButton.setCellValueFactory(new PropertyValueFactory<>("editButton"));			deleteButton.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));						dptTable.setItems(departmentList);						editButton.setCellFactory(param -> new TableCell<Department, Button>() {				@Override				protected void updateItem(Button button, boolean empty) {					super.updateItem(button, empty);										if (empty || button == null) {						setGraphic(null);					} else {						setGraphic(button);						button.setOnAction(event -> {							Department department = getTableView().getItems().get(getIndex());							AdminEditSingleDepartmentController.setDepartmentId (department.getId());														try {								toEditDepartment (event);							} catch (IOException e) {								throw new RuntimeException(e);							}						});					}				}			});						deleteButton.setCellFactory(param -> new TableCell<Department, Button>() {				@Override				protected void updateItem(Button button, boolean empty) {					super.updateItem(button, empty);										if (empty || button == null) {						setGraphic(null);					} else {						setGraphic(button);						button.setOnAction(event -> {							Department department = getTableView().getItems().get(getIndex());														try {								deleteDepartment (event, department.getId ());							} catch (IOException e) {								throw new RuntimeException(e);							}						});					}				}			});		} catch (Exception e) {			System.out.println(e.getMessage());		}	}		public void toAllPatient ( ActionEvent event) throws IOException {		root = FXMLLoader.load( Objects.requireNonNull(getClass().getResource("adminAllPatients.fxml")));		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();		scene = new Scene (root);		stage.setScene(scene);		stage.show();	}		public void toAllAdmin ( ActionEvent event) throws IOException {		root = FXMLLoader.load( Objects.requireNonNull(getClass().getResource("adminAllAdmin.fxml")));		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();		scene = new Scene (root);		stage.setScene(scene);		stage.show();	}		public void toAdminHome ( ActionEvent event) throws IOException {		root = FXMLLoader.load( Objects.requireNonNull(getClass().getResource("adminHome.fxml")));		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();		scene = new Scene (root);		stage.setScene(scene);		stage.show();	}	public void toAllDoctor ( ActionEvent event) throws IOException {		root = FXMLLoader.load( Objects.requireNonNull(getClass().getResource("adminAllDoctor.fxml")));		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();		scene = new Scene (root);		stage.setScene(scene);		stage.show();	}		public void toEditDepartment(ActionEvent event) throws IOException {		root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminEditSingleDepartment.fxml")));		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();		scene = new Scene(root);		stage.setScene(scene);		stage.show();	}				public void deleteDepartment(ActionEvent event, String id) throws IOException {		database.deleteDepartmentById ( id );		dptTable.setItems(database.getAllDepartment ());	}		public void toAddDepartment(ActionEvent event) throws IOException {		root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminAddDepartment.fxml")));		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();		scene = new Scene(root);		stage.setScene(scene);		stage.show();	}		public void logout(ActionEvent event) throws IOException {		root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("landing.fxml")));		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();		scene = new Scene(root);		stage.setScene(scene);		stage.show();	}}