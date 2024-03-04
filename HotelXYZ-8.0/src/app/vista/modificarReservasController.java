package app.vista;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import app.Conexion;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class modificarReservasController implements Initializable {

	@FXML
	private GridPane gpEditarReservas;
	@FXML
	private TextField txfDniModificar;
	
	@FXML
	private DatePicker dateEntradaModificar,dateSalidaModificar;
	@FXML
	private AnchorPane apModificarReservas;

	@FXML
	private ToggleButton btn1;

	@FXML
	private ToggleButton btn10;

	@FXML
	private ToggleButton btn11;

	@FXML
	private ToggleButton btn12;

	@FXML
	private ToggleButton btn13;

	@FXML
	private ToggleButton btn14;

	@FXML
	private ToggleButton btn15;

	@FXML
	private ToggleButton btn16;

	@FXML
	private ToggleButton btn17;

	@FXML
	private ToggleButton btn18;

	@FXML
	private ToggleButton btn2;

	@FXML
	private ToggleButton btn3;

	@FXML
	private ToggleButton btn4;

	@FXML
	private ToggleButton btn5;

	@FXML
	private ToggleButton btn6;

	@FXML
	private ToggleButton btn7;

	@FXML
	private ToggleButton btn8;

	@FXML
	private ToggleButton btn9;

	@FXML
	private RadioButton btnRadioSinFecha;

	@FXML
	private Button btnReservasModificar, btnReservasCancelar;


	

	@FXML
	private GridPane gridHabitaciones;

	@FXML
	private Label lblHabitacionesDisponibles,lblTexto1, lblTexto2, lblInfoEditar, lblTituloEditar;

	@FXML
	private Label lblHabitacionesReservadas, lblModificarNombre, lblModificarApellidos, lblModificarDni;

	@FXML
	private Pane paneReservar;

	@FXML
	//private TextField txfDniReservas,txfModificarNombre, txfModificarApellido;
	
	
    
	Stage stage;
	int modificarHabitacion;
	Conexion conn;
	ObservableList<ToggleButton> habitaciones = FXCollections.observableArrayList();
	
	public void obtenerEscenario(Stage stage) {
		this.stage=stage;
	}
	
	public void activarHabitaciones() {
		for(ToggleButton boton : habitaciones) {
			if(boton.isDisable()) {
				boton.setDisable(false);
			}else {
				boton.setDisable(true);
			}
		}
	}
	
	public void reservadas() {
		conn = new Conexion("hotelxyz","root","","jdbc:mysql://127.0.0.1:3306/");
		String sentencia = "SELECT idHabitacionReserva FROM reservas WHERE reservada = 1";
		try {
			Statement stmt = conn.getConexion().prepareStatement(sentencia);
			ResultSet resultado = stmt.executeQuery(sentencia);
			
				if(!resultado.next()) {
					Alert alert4 = new Alert(Alert.AlertType.ERROR);
					alert4.setContentText("No hay habitaciones reservadas en este momento");
					alert4.showAndWait();
			    	this.stage.close();
			    
				}else {
					do {
						for(ToggleButton boton: habitaciones) {
			    	    	String id = boton.getId();
			    	    	if(id.equals("btn"+String.valueOf(resultado.getInt("idHabitacionReserva")))){
			    	    		boton.setStyle("-fx-background-color:red;");
			    	    		boton.setDisable(true);
			    	    	}
			        	}
					}while(resultado.next());
				}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}   
	
	
	
	@FXML
	public void activarBotones() {
		this.btnReservasModificar.setDisable(false);
		this.btnReservasCancelar.setDisable(false);
		for(ToggleButton boton : habitaciones) {
			if(boton.isSelected() == true) {
				int habitacion = Integer.parseInt(boton.getText());
				modificarHabitacion = habitacion;
				boton.setStyle("-fx-background-color:orange;");
				try {
			    conn = new Conexion("hotelxyz","root","","jdbc:mysql://127.0.0.1:3306/");
			    String sentencia = "SELECT r.fechaEntrada, r.fechaSalida, p.dni, p.nombre, p.apellidos from reservas r join cliente c ON r.idClienteReserva=c.idCliente\r\n"
			    		+ "JOIN persona p ON p.idPersona=c.idPersona\r\n"
			    		+ "WHERE r.idHabitacionReserva='"+habitacion+"'";
					
						Statement stmt = conn.getConexion().prepareStatement(sentencia);
						ResultSet resultado = stmt.executeQuery(sentencia);
						while(resultado.next()) {
							String entrada = resultado.getString("fechaEntrada");
							String salida = resultado.getString("fechaSalida");
							String dni= resultado.getString("dni");
							String nombre = resultado.getString("nombre");
							String apellido = resultado.getString("apellidos");
							LocalDate fechaEntrada = LocalDate.parse(entrada);
							LocalDate fechaSalida = LocalDate.parse(salida);
							this.dateEntradaModificar.setValue(fechaEntrada);
							this.dateSalidaModificar.setValue(fechaSalida);
							this.lblModificarDni.setText(resultado.getString("dni"));
							this.lblModificarNombre.setText(nombre);
							this.lblModificarApellidos.setText(apellido);
						
						}
						//this.lblTituloEditar.setVisible(true);
						//this.lblInfoEditar.setVisible(false);
						
					}catch (SQLException e) {
						e.printStackTrace();
					}
				}else {
				boton.setDisable(true);
			}
		}
	}
	
	
	@FXML
	public void modificar() {
		
		int sinFechaSalida;
		if(this.btnRadioSinFecha.isSelected()) {
			sinFechaSalida = 1;
		}else {
			sinFechaSalida = 0;
		}
		
		this.conn = new Conexion("hotelxyz","root","","jdbc:mysql://127.0.0.1:3306/");
		String sentencia = "UPDATE reservas SET fechaEntrada='"+this.dateEntradaModificar.getValue()+"', fechaSalida='"+
		this.dateSalidaModificar.getValue()+"', sinFechaSalida='"+sinFechaSalida+"' WHERE idHabitacionReserva='"+
		modificarHabitacion+"'";
    	PreparedStatement stmt;
		try {
			stmt = this.conn.getConexion().prepareStatement(sentencia);
			int count = stmt.executeUpdate(sentencia);
	    	System.out.println("filas afectadas: " + count);
	    	if(count!=0) {
	    		Alert alerta1 = new Alert(Alert.AlertType.INFORMATION);
		    	alerta1.setContentText("La reserva se ha actualizado correctamente");
		    	alerta1.showAndWait();
		    	this.stage.close();
	    	}
		} catch (SQLException e) {
			Alert alert4 = new Alert(Alert.AlertType.ERROR);
			alert4.setContentText("No se pudo establecer conexion con la base de datos");
			alert4.showAndWait();
			e.printStackTrace();
		}
    	
	}

	@FXML
	public void cancelar() {
		this.stage.close();
	}
	
	public void advertencia() {
		
		this.lblInfoEditar.setVisible(false);
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5),this.lblInfoEditar);
		fadeTransition.setFromValue(0.0);
		fadeTransition.setToValue(1.5);
		fadeTransition.setCycleCount(5);
		fadeTransition.play();
		this.lblInfoEditar.setVisible(true);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.habitaciones.addAll(btn1, btn2, btn3,btn4, btn5, btn6, btn7, btn8, btn9,btn10, btn11, btn12,btn13, btn14, btn15,btn16, btn17, btn18);
		reservadas();
		activarHabitaciones();
		advertencia();
	}

}
