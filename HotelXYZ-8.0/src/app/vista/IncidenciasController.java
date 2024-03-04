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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class IncidenciasController implements Initializable {

	@FXML
	private AnchorPane apMenu;
	@FXML
	private Button btnGuardarIncidencia;
	@FXML
	private Label lblClientesReg,lblFecha,lblHDisponible,lblHoraSesion,lblIncidencias,lblNombreUsuario2,lblUsuario;
	@FXML
	private Pane pnInfoDiaria;
	@FXML
	private TextField txfNumHabitacion;
	@FXML
	private TextArea txfTextoIncidencia;
	    
	Conexion miConexion = new Conexion();
	Stage stage;
	    
	public void obtenerEscenario(Stage stage) {
	    this.stage=stage;
	}
	    
	@FXML
	public void guardarIncidencia() {
	    	
	   int numHabitacion = 0;
	   if(this.txfNumHabitacion.getText().isBlank()) {
		    Alert alert = new Alert(Alert.AlertType.ERROR);
		    alert.setContentText("El campo número de habitacion no puede estar vacio");
			alert.showAndWait();
	   }else if(this.txfNumHabitacion.getText().isBlank()==false){
		   numHabitacion=Integer.valueOf(this.txfNumHabitacion.getText());
		   if(numHabitacion>18 || numHabitacion <= 0){
		    	Alert alert = new Alert(Alert.AlertType.ERROR);
		    	alert.setContentText("El número de habitación no es correcto");
				alert.showAndWait();
		   }else {
			    try {
		    	    String sentencia = "INSERT INTO incidencias (idHabitacion, Observaciones,solucionado) VALUES ('"+(numHabitacion)+"','"+this.txfTextoIncidencia.getText()+"','0')";
				    PreparedStatement stmt =this.miConexion.getConexion().prepareStatement(sentencia);
				    int count = stmt.executeUpdate(sentencia);
				    System.out.println("filas afectadas: " + count);
				    	if(count!=0) {
				    		Alert alerta1 = new Alert(Alert.AlertType.INFORMATION);
				    		alerta1.setContentText("La incidencia se ha guardado correctamente");
				    		alerta1.showAndWait();
				    		numIncidencias();
				    	}
				    	
			    	}catch(SQLException e){
			    		Alert alert4 = new Alert(Alert.AlertType.ERROR);
						alert4.setContentText("No se pudo establecer conexion con la base de datos");
						alert4.showAndWait();
						e.printStackTrace();
			    	}
		    	}
	    	}
	    	
	    }

	@FXML
	public void cerrarVentana() {
	    this.stage.close();
	}
	    
	public void numIncidencias() {
		ResultSet resultado = this.miConexion.consultaSeleccion("call getIncidencias();");
		try {
			while(resultado.next()) {
				
				this.lblIncidencias.setText(String.valueOf(resultado.getInt("num")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		numIncidencias();

	}

}
