module sample.demo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;
	
	
	opens sample.demo2 to javafx.fxml;
	exports sample.demo2;
	
	opens Model to javafx.base;
}