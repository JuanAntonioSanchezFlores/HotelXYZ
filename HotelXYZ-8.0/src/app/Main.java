package app;


import java.util.Optional;

import app.vista.InicioController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	public static String estaticoDni;
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(getClass().getResource("vista/Inicio.fxml"));
			AnchorPane root = (AnchorPane)cargador.load();
			InicioController controlador = (InicioController)cargador.getController();
			controlador.getStage(primaryStage);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			// confirmar el cierre de la ventana
			primaryStage.setOnCloseRequest(event -> {
				event.consume(); // consume el evento para detener el cierre automático de la ventana

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmación");
				alert.setHeaderText("¿Estás seguro que deseas salir de la aplicación?");
				//alert.setContentText("Se perderán todos los cambios no guardados.");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					primaryStage.close();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		estaticoDni = "Juanito";
		launch(args);

	}
}
