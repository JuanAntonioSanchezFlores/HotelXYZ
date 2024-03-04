module Estruconsa {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires java.sql;
	requires javafx.graphics;
	requires java.desktop;
	
	
	opens app to javafx.graphics, javafx.fxml, javafx.base, javafx.controls, java.sql;
	opens app.modelo to javafx.graphics, javafx.fxml, javafx.base, javafx.controls, java.sql;
	opens app.vista to javafx.graphics, javafx.fxml, javafx.base, javafx.controls, java.sql;
	
	
}
