package app.vista;


import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import app.Conexion;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import javafx.util.Duration;

public class ReservasController implements Initializable {

	
	@FXML
	private TextField txfDniReservas;
	@FXML
	private Button btnCancelarReserva;

	@FXML
	private Button btnConfirmarReserva;

	@FXML
	private RadioButton btnRadioSinFecha;

	@FXML
	private DatePicker dateEntrada;

	@FXML
	private DatePicker dateSalida;
	@FXML
	private AnchorPane apReservas;
	@FXML
	private GridPane gridHabitaciones;

	@FXML
	private VBox vbContBotRes, vbModificar;
	@FXML
	private Button btnReservasAniadir;

	@FXML
	private Button btnReservasEliminar;

	@FXML
	private Button btnReservasModificar;

	@FXML
	private ToggleButton btn1, btn2, btn3,btn4, btn5, btn6, btn7, btn8, btn9,btn10, btn11, btn12,btn13, btn14, btn15,btn16, btn17, btn18;
	    
	@FXML
	private Label lblHabitacionesDisponibles, lblHabitacionesReservadas, lblDniReservas;
	    
	@FXML
	private Pane paneReservar, pnModificar;
	    
	@FXML
	private ImageView imgAtras;
	    
	ObservableList<ToggleButton> habitaciones = FXCollections.observableArrayList();
	boolean seleccionado = true;
	int habitacionesReservadas = 0;
	Stage stage;
	Conexion conn;
	
	
	
	/****RESERVAR****/
	@FXML
	public void formularioReserva() {
		 this.txfDniReservas.setDisable(true);
		 this.dateEntrada.setDisable(true);
		 this.dateSalida.setDisable(true);
		 this.btnRadioSinFecha.setDisable(true);
	}
	
	@FXML
	void activaBotonesReservas() {
	    	
		
	    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1),this.vbContBotRes);
	    fadeTransition.setFromValue(0.0);
	    fadeTransition.setToValue(1.0);
	    fadeTransition.play();
	    this.vbContBotRes.setVisible(true);
	    this.pnModificar.setVisible(false);
	    this.btnReservasAniadir.setDisable(false);
	    //this.btnReservasEliminar.setDisable(false);
	    //this.btnReservasModificar.setDisable(false);
	    this.paneReservar.setVisible(false);
	  
	    for(ToggleButton boton: habitaciones) {
	    	
	    	if(boton.isSelected()) {
	    		boton.setStyle("-fx-background-color:red;");
	    	}else {
	    		boton.setDisable(true);
	    	}
	    }
	    this.lblHabitacionesReservadas.setText(String.valueOf(habitacionesReservadas));
	    this.lblHabitacionesDisponibles.setText(String.valueOf(18-habitacionesReservadas));
	    	
	}
	
	@FXML
	public void resetearReservas() {
		this.stage.close();
	}
	
	public Stage obtenerEscenario(Stage stage) {
	    this.stage=stage;
	    return this.stage;
	    
	}
	
	
	public void contarHabitaciones() {
	    habitacionesReservadas=0;
	    for(ToggleButton boton: habitaciones) {
	    	if(boton.isDisable()==true) 
				habitacionesReservadas++;
			}
	    	this.lblHabitacionesDisponibles.setText(String.valueOf(18-habitacionesReservadas));
			this.lblHabitacionesReservadas.setText(String.valueOf(habitacionesReservadas));
	    }
	
	@FXML
	void aniadirReserva() {
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1),this.paneReservar);
		fadeTransition.setFromValue(0.0);
		fadeTransition.setToValue(1.0);
		fadeTransition.play();
	    this.paneReservar.setVisible(true);
	    this.vbContBotRes.setVisible(false);
	    //this.imgAtras.setVisible(true);
	    this.txfDniReservas.setDisable(false);
	    this.dateEntrada.setDisable(false);
	    this.dateSalida.setDisable(false);
	    this.btnRadioSinFecha.setDisable(false);
	    this.btnConfirmarReserva.setDisable(false);
	    this.vbModificar.setVisible(false);
	  
	}
	    
	@FXML
	public void reservar() { 
	  
		if(this.txfDniReservas.getText().isBlank() || this.dateEntrada.getValue()==null){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Hay campos vacios, por favor rellene todos los campos");
			alert.showAndWait();
		}if(this.dateSalida.getValue()==null && this.btnRadioSinFecha.isSelected()==false) {
	    	Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Indique una fecha de salida o pulse la opción -> Sin fecha de salida");
			alert.showAndWait();
	    }
		if(this.dateSalida.getValue()!=null && this.btnRadioSinFecha.isSelected()) {
	    	Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Borre la fecha de salida o desmarque el boton-> Sin fecha de salida");
			alert.showAndWait();
	    }
		
	    	String dni = this.txfDniReservas.getText();
		    Integer idPersona=0;
		    String numeroHabitacion = "";
		    Integer sinFecha = 0;
		    
		    
		    try {
		    	conn = new Conexion("hotelxyz","root","","jdbc:mysql://127.0.0.1:3306/");
		    	String sentencia = "SELECT idPersona,dni FROM persona WHERE dni='"+dni+"'; ";
			
				Statement stmt = conn.getConexion().prepareStatement(sentencia);
				ResultSet resultado = stmt.executeQuery(sentencia);
				
				//while (resultado.next()) {
				do {
					if(!resultado.next()) {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setContentText("El DNI no corresponde a ningún cliente");
						alert.showAndWait();
					}else {
				        if(resultado.getString("dni").equals(dni)){
				        	idPersona = resultado.getInt("idPersona");
				       
				        	for(ToggleButton boton: habitaciones) {
				    	    	if(boton.isSelected() && boton.isDisabled()==false) {
				    	    		numeroHabitacion = boton.getText();
				    	    	}
				        	}
				        	System.out.println(idPersona);
				        	if(this.btnRadioSinFecha.isSelected()) {
				        		sinFecha=1;
				        	}
							
								String sentencia2 = "SELECT idCliente FROM cliente WHERE idPersona = '"+idPersona+"'"; 
					        	PreparedStatement stmt2 = conn.getConexion().prepareStatement(sentencia2);
					        	ResultSet resultado2 = stmt2.executeQuery(sentencia2);
					        	int idCliente=0;
					        	
								while(resultado2.next()) {
									idCliente = resultado2.getInt("idCliente");
									
								}	
					        	
								
								System.out.println(this.dateEntrada.getValue());
					        	String sentencia3 = "INSERT INTO reservas (idClienteReserva, idHabitacionReserva, fechaEntrada, fechaSalida, sinFechaSalida,reservada)"
					        			+ " VALUES ('"+idCliente+"','"+(Integer.parseInt(numeroHabitacion))+"','"+
					        			String.valueOf(this.dateEntrada.getValue())+"','"+String.valueOf(this.dateEntrada.getValue())+
					        			"','"+sinFecha+"','1')";
					        	PreparedStatement stmt3 = conn.getConexion().prepareStatement(sentencia3);
					        	int count = stmt3.executeUpdate(sentencia3);
							    System.out.println("filas afectadas: " + count);
							    if(count!=0) {
							    	System.out.println("insertado");
							    }else {
							    	System.out.println("No insertado");
							    }
					        	/*for(ToggleButton boton: habitaciones) {
					    	    	if(boton.isSelected()||boton.isDisable()==true) {
					    	    		boton.setDisable(true);
					    	    	}
					        	}*/
					        	 this.txfDniReservas.setText(null);
								    this.dateEntrada.setValue(null);
								    this.dateSalida.setValue(null);
								    this.btnRadioSinFecha.setSelected(false);
								    this.txfDniReservas.setDisable(true);
								    this.dateEntrada.setDisable(true);
								    this.dateSalida.setDisable(true);
								    this.btnRadioSinFecha.setDisable(true);
								    this.btnConfirmarReserva.setDisable(true);
							    contarHabitaciones();
							    Alert alerta1 = new Alert(Alert.AlertType.INFORMATION);
							    alerta1.setContentText("La habitación se ha reservado correctamente");
							    alerta1.showAndWait();
							    resetearReservas();
				        }
			        	
			        }
			   }while(resultado.next());	
				
			} catch (SQLException e) {
				Alert alert4 = new Alert(Alert.AlertType.ERROR);
				alert4.setContentText("No se pudo establecer conexion con la base de datos");
				alert4.showAndWait();
				this.stage.close();
					e.printStackTrace();
			}

	    
	    	
	}
	
	@FXML
	public void prueba() {
		reservar();
		reservadas();
		
	}
	    
	public void reservadas() {
		
		conn = new Conexion("hotelxyz","root","","jdbc:mysql://127.0.0.1:3306/");
		String sentencia = "SELECT idHabitacionReserva FROM reservas WHERE reservada = 1";
		try {
			Statement stmt = conn.getConexion().prepareStatement(sentencia);
			ResultSet resultado = stmt.executeQuery(sentencia);
			while(resultado.next()) {
				for(ToggleButton boton: habitaciones) {
	    	    	String id = boton.getId();
	    	    	String habitacion = String.valueOf(resultado.getInt("idHabitacionReserva"));
	    	    	if(id.equals("btn"+habitacion)){
	    	    		boton.setStyle("-fx-background-color:red;");
	    	    		boton.setDisable(true);
	    	    		
	    	    	}/*
	    	    	if(id.equals("btn"+habitacion)) {
	    	    		boton.setStyle("-fx-background-color:red;");
	    	    		boton.setDisable(false);
	    	    		break;
	    	    	}*/
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}   
	
	/*public void cambiarVistaReservas(boolean b) {
		cambiaVista = b;
		if(cambiaVista==true) {
			reservadas(true);
		}
		if(cambiaVista==false){
			reservadas(false);
		}
	}*/
	/*** MODIFICAR***/
	
	@FXML
	public void modificar() {
		
		this.vbModificar.setVisible(true);
		formularioReserva();
		for(ToggleButton boton: habitaciones) {
	    	if(boton.isDisabled()==true){
	    		boton.setDisable(false);
	    	}else {
	    		boton.setDisable(true);
	    	}
    	}
	}
	
	/***   ELIMINAR    ***/
	
   
	@FXML
	public void eliminarReserva() {
		//cambiaVista = false;
		aniadirReserva();
		//boolean habilitar = false;
		//cambiarVistaReservas(cambiaVista);
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.habitaciones.addAll(btn1, btn2, btn3,btn4, btn5, btn6, btn7, btn8, btn9,btn10, btn11, btn12,btn13, btn14, btn15,btn16, btn17, btn18);
		reservadas();
		//cambiarVistaReservas(false);
		contarHabitaciones();
	}

}
