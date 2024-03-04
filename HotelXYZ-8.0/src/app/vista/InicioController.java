package app.vista;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import app.Conexion;
import app.modelo.DatosCompartidos;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InicioController implements Initializable {
    
	
	@FXML
    private AnchorPane apAutenticacion;
	@FXML
	private TextField txfUsuario;
	@FXML
	private PasswordField txfContrasenia;
	@FXML
	private Button btnAutenticar;
	 
	Stage stage;
	
	int intentos = 0;
    Conexion conn = new Conexion();
    DatosCompartidos sesion = new DatosCompartidos();
    int persona;
    String nombreSesion, apellidosSesion;
   
    
    public void getStage(Stage stage) {
    	this.stage = stage;
    }
    
    public void obtenerUsuario(DatosCompartidos d) {
    	this.sesion = d;
    }
    @FXML
    public void conexion() {
		
		if(this.txfUsuario.getText().isBlank() || this.txfContrasenia.getText().isBlank()) {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Algún campo está vacio, por favor rellene todos los campos");
			alerta.showAndWait();
		}else {
			try {
				boolean coincidencia = true;
				String query = "SELECT idPersonaAdmin,usuario, contrasenia FROM administradores;";
				PreparedStatement stmt = this.conn.getConexion().prepareStatement(query);
				ResultSet resultado = stmt.executeQuery(query);
				while (resultado.next()) {
					persona = resultado.getInt("idPersonaAdmin");
					String usuario = resultado.getString("usuario");
					String contrasenia = resultado.getString("contrasenia");
					
					if (this.txfUsuario.getText().equals(usuario) && this.txfContrasenia.getText().equals(contrasenia)) {
						coincidencia = true;
						
					} else {
						coincidencia = false;
					}
				}
				
				if (coincidencia) {
					
					String consulta = "SELECT nombre, apellidos FROM persona WHERE idPersona ='"+persona+"' ";
					Statement stmt2 = conn.getConexion().createStatement();
					ResultSet resultado2 = stmt2.executeQuery(consulta);
					while (resultado2.next()) {
						nombreSesion = resultado2.getString("nombre");
						apellidosSesion = resultado2.getString("apellidos");
						}
					this.sesion.setUsuario(nombreSesion+" "+apellidosSesion);
					this.stage.close();
					menu();
					
				} else {
					Alert alerta = new Alert(Alert.AlertType.ERROR);
					alerta.setContentText("Contraseña o usuario incorrectos, le quedan " + (3 - intentos) + " intentos");
					alerta.showAndWait();
					intentos++;
					if (intentos == 4) {
						Alert alert1 = new Alert(Alert.AlertType.ERROR);
						alert1.setContentText("Ha consumido los 3 intentos para autenticarse\nSe cerrará el programa");
						alert1.showAndWait();
						Platform.exit();
					}
				}
	
				stmt.close();
			} catch (SQLException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("No se pudo establecer conexion con la base de datos");
				e.printStackTrace();
			}
		}
	}
    
    public void menu() {
		try {
			
			Stage escenario=new Stage();
			
			FXMLLoader cargador=new FXMLLoader();
			cargador.setLocation(getClass().getResource("Menu.fxml"));
			AnchorPane root = cargador.load();
			MenuController controlador = (MenuController)cargador.getController();
			controlador.obtenerUsuario(sesion.getUsuario());
			Scene scene = new Scene(root);
			escenario.setScene(scene);
			escenario.setTitle("Menu Opciones");
			escenario.show();
			
			escenario.setOnCloseRequest(event -> {
				event.consume(); 

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmación");
				alert.setHeaderText("¿Estás seguro que deseas salir de la aplicación?");
				alert.setContentText("Se perderán todos los cambios no guardados.");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					escenario.close();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
