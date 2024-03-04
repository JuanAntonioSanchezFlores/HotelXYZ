package app.vista;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import app.Conexion;
import app.modelo.Incidencias;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EliminarIncidenciasControlador implements Initializable {

	  @FXML
	    private AnchorPane apMenu;

	    @FXML
	    private Button btnEliminarIncidencia;

	    @FXML
	    private Label cerraIncidencias;

	    @FXML
	    private Label lblFecha;

	    @FXML
	    private Label lblHoraSesion;

	    @FXML
	    private Label lblNombreUsuario2;

	    @FXML
	    private Label lblUsuario, lblNumInci;

	    @FXML
	    private Pane pnInfoDiaria;

	    @FXML
	    private TableColumn<Incidencias, Integer> tbcHabitacion;

	    @FXML
	    private TableColumn<Incidencias, Integer > tbcIncidencia;

	    @FXML
	    private TableColumn<Incidencias, String> tbcObservaciones;

	    @FXML
	    private TableView<Incidencias> tblIncidencias;
	    
	    Conexion miConexion;
	    Stage stage;
	    int incidencias = 0;
	    ObservableList<Incidencias> listaIncidencias = FXCollections.observableArrayList();

	    public void obtenerEscenario(Stage stage) {
	    	this.stage=stage;
		    
	    }
	    @FXML
	    void cerrarVentana() {
	    	this.stage.close();
	    }
	    
	    public void inicializarLista() {
	    	
	    	this.miConexion = new Conexion("hotelxyz","root","","jdbc:mysql://127.0.0.1:3306/");
			String sentencia = "SELECT idIncidencia, idHabitacion, Observaciones FROM incidencias";
	    	Statement stmt;
			try {
				stmt = this.miConexion.getConexion().prepareStatement(sentencia);
				ResultSet resultado= stmt.executeQuery(sentencia);
				while(resultado.next()) {
					int idInci = resultado.getInt("idIncidencia");
					int idHab = resultado.getInt("idHabitacion");
					String obs = resultado.getString("Observaciones");
					Incidencias nueva = new Incidencias(idInci, idHab, obs);
					listaIncidencias.add(nueva);
					incidencias++;
				}
				
			} catch (SQLException e) {
				Alert alert4 = new Alert(Alert.AlertType.ERROR);
				alert4.setContentText("No se pudo establecer conexion con la base de datos");
				alert4.showAndWait();
				e.printStackTrace();
			}
	    	this.lblNumInci.setText(String.valueOf(incidencias));
	    }
	    
	    public void inicializarTabla() {
	    	this.tbcIncidencia.setCellValueFactory(new PropertyValueFactory<Incidencias,Integer>("idIncidencia"));
	    	this.tbcHabitacion.setCellValueFactory(new PropertyValueFactory<Incidencias,Integer>("idHabitacion"));
	    	this.tbcObservaciones.setCellValueFactory(new PropertyValueFactory<Incidencias,String>("observaciones"));
	    	this.tblIncidencias.setItems(listaIncidencias);
	    }
	    
	    public void eliminarIncidencia() {
	    	
	    	int incidencia = this.tblIncidencias.getSelectionModel().getSelectedIndex();
	    	this.miConexion = new Conexion("hotelxyz","root","","jdbc:mysql://127.0.0.1:3306/");
			String sentencia = "DELETE FROM incidencias WHERE idIncidencia = '"+
	    	(this.listaIncidencias.get(incidencia).getIdIncidencia())+"'";
	    	PreparedStatement stmt;
			try {
				stmt = this.miConexion.getConexion().prepareStatement(sentencia);
				int count = stmt.executeUpdate(sentencia);
				if(count!=0) {
					incidencias--;
					this.tblIncidencias.refresh();
					Alert alerta1 = new Alert(Alert.AlertType.INFORMATION);
			    	alerta1.setContentText("La incidencia se ha borrado correctamente");
			    	alerta1.showAndWait();
			    	this.stage.close();
				}

				} catch (SQLException e) {
				Alert alert4 = new Alert(Alert.AlertType.ERROR);
				alert4.setContentText("No se pudo establecer conexion con la base de datos");
				alert4.showAndWait();
				e.printStackTrace();
			}
			this.lblNumInci.setText(String.valueOf(incidencias));
			
	    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		inicializarLista();
		inicializarTabla();

	}

}
