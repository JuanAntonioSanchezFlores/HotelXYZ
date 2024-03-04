package app.vista;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import app.Conexion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ServicioController implements Initializable {
	
    @FXML
    private AnchorPane apServicios;

    @FXML
    private Button btnGuardarServicio;

    @FXML
    private Label cerrarServicio,lblFecha,lblHoraSesion,lblNombreUsuario2,lblUsuario;

    @FXML
    private Pane pnInfoDiaria;

    @FXML
    private TextField txfDni;
	
    @FXML
    private CheckBox checkTaxi, checkMaletas;
    
	Conexion miConexion;
	Stage stage;
	
	

	public void obtenerEscenario(Stage stage) {
		this.stage = stage;
	}
	
	@FXML
	public void cerrarVentana() {
		this.stage.close();
	}
	
	@FXML
	public void aniadirServicio() {
		if(this.txfDni.getText().isBlank()) {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("El campo DNI no puede estar vacio");
			alerta.showAndWait();
		}else if(this.checkMaletas.isSelected()==false && this.checkTaxi.isSelected()==false) {
			
			Alert alerta2 = new Alert(Alert.AlertType.ERROR);
			alerta2.setContentText("Debe seleccionar al menos un servicio");
			alerta2.showAndWait();
		}else {	
			String dni = this.txfDni.getText();
			int taxi = 0;
			int maleta = 0;
			if(this.checkTaxi.isSelected()) {
				taxi = 1;
			}else {
				taxi = 0;
			}
			if(this.checkMaletas.isSelected()) {
				maleta = 1;
			}else {
				maleta = 0;
			}
			try {
			int idCliente=0;
			this.miConexion = new Conexion("hotelxyz","root","","jdbc:mysql://127.0.0.1:3306/");
			String sentencia = "SELECT idCliente  FROM cliente join persona on cliente.idPersona=persona.idPersona WHERE"
					+ " persona.dni = '"+dni+"';";
			PreparedStatement stmt;
			
				stmt = this.miConexion.getConexion().prepareStatement(sentencia);
				ResultSet resultado = stmt.executeQuery(sentencia);
				while(resultado.next()) {
					idCliente = resultado.getInt("idCliente");
				}
				if(idCliente == 0) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("No existe un cliente con el dni indicado");
					alert.showAndWait();
				}else {
				
					this.miConexion = new Conexion("hotelxyz","root","","jdbc:mysql://127.0.0.1:3306/");
					String sentencia2 = "INSERT INTO servicios (idCliente, taxi, recogidaMaletas) VALUES ('"+(idCliente)+"','"+(taxi)+
							"','"+(maleta)+"');";
			    	PreparedStatement stmt2 = this.miConexion.getConexion().prepareStatement(sentencia2);
			    	int count = stmt2.executeUpdate(sentencia2);
			    	
			    	if(count!=0) {
			    		Alert alerta1 = new Alert(Alert.AlertType.INFORMATION);
				    	alerta1.setContentText("El servicio se ha guardado correctamente");
				    	alerta1.showAndWait();
			    	}else {
			    		Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setContentText("No existe un cliente con el dni indicado");
						alert.showAndWait();
					
			    	}
				}
			} catch (SQLException e) {
				Alert alert4 = new Alert(Alert.AlertType.ERROR);
				alert4.setContentText("No se pudo establecer conexion con la base de datos");
				alert4.showAndWait();
				e.printStackTrace();
			}
			
			
			
		}
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
