package app.vista;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.Conexion;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class EliminarReservasController implements Initializable {

	@FXML
    private AnchorPane apEliminarReservas;

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
    private Button btnConfirmarReserva, btnReservasEliminar;

    @FXML
    private RadioButton btnRadioSinFecha;

    @FXML
    private Button btnReservasAniadir;

    @FXML
    private DatePicker dateEntrada;

    @FXML
    private DatePicker dateSalida;

    @FXML
    private GridPane gridHabitaciones;

    @FXML
    private Label lblHabitacionesDisponibles, lblModificarNombre, lblModificarApellidos, lblModificarDni, lblFechaEntrada, lblFechaSalida;

    @FXML
    private Label lblHabitacionesReservadas;

    @FXML
    private Pane paneReservar;

    @FXML
    private Pane pnModificar;
    
    @FXML
    private Pane paneEliminarHabitacion;

    @FXML
    private TextField txfDniReservas;

    @FXML
    private VBox vbContBotRes;

    @FXML
    private VBox vbModificar;

   

    

    ObservableList<ToggleButton> habitaciones = FXCollections.observableArrayList();
	boolean seleccionado = true;
	int habitacionesReservadas = 0;
	Conexion conn;
	Stage stage;
	int modificarHabitacion;
	int reserva =0;
	@FXML
    void reservadas() {
		ArrayList<ToggleButton> numHabitacion = new ArrayList<>();
		conn = new Conexion("hotelxyz","root","","jdbc:mysql://127.0.0.1:3306/");
		String sentencia = "SELECT idHabitacionReserva FROM reservas WHERE reservada = 1";
		try {
			Statement stmt = conn.getConexion().prepareStatement(sentencia);
			ResultSet resultado = stmt.executeQuery(sentencia);
			
			while(resultado.next()) {
				int botonId = resultado.getInt("idHabitacionReserva");
				for(ToggleButton boton:habitaciones) {
					
					if(boton.getId().equals("btn"+String.valueOf(botonId))) {
						numHabitacion.add(boton);
					}
				}
			}
			for(ToggleButton boton:habitaciones) {
				boton.setDisable(true);
				if(numHabitacion.contains(boton)) {
					boton.setDisable(false);
					boton.setStyle("-fx-background-color:red");
				}
			}
			System.out.println("añadidos"+ numHabitacion.size());
				
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.lblHabitacionesReservadas.setText(String.valueOf(numHabitacion.size()));
	}   
	
	public void obtenerEscenario(Stage stage) {
		this.stage = stage;
	}
	
	@FXML
	void activaBotonesReservas() {
	
	    for(ToggleButton boton: habitaciones) {
	    	
	    	if(boton.isSelected()) {
	    		boton.setStyle("-fx-background-color:red;");
	    		boton.setDisable(false);
	    	}else {
	    		boton.setDisable(true);
	    	}
	    }
	    this.lblHabitacionesReservadas.setText(String.valueOf(habitacionesReservadas));
	    this.lblHabitacionesDisponibles.setText(String.valueOf(18-habitacionesReservadas));
	}

	@FXML
	public void vistaEliminar() {
		this.vbModificar.setVisible(false);
		this.paneEliminarHabitacion.setVisible(true);
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.0),this.paneEliminarHabitacion);
    	fadeTransition.setFromValue(0.0);
    	fadeTransition.setToValue(1.0);
    	fadeTransition.play();
    	this.btnReservasEliminar.setDisable(false);
    	
    	for(ToggleButton boton : habitaciones) {
			if(boton.isSelected() == true) {
				int habitacion = Integer.parseInt(boton.getText());
				modificarHabitacion = habitacion;
				boton.setStyle("-fx-background-color:orange;");
				try {
			    conn = new Conexion("hotelxyz","root","","jdbc:mysql://127.0.0.1:3306/");
			    String sentencia = "SELECT r.idReserva,r.fechaEntrada, r.fechaSalida, p.dni, p.nombre,"
			    		+ " p.apellidos from reservas r join cliente c ON r.idClienteReserva=c.idCliente\r\n"
			    		+ "JOIN persona p ON p.idPersona=c.idPersona\r\n"
			    		+ "WHERE r.idHabitacionReserva='"+habitacion+"'";
					
						Statement stmt = conn.getConexion().prepareStatement(sentencia);
						ResultSet resultado = stmt.executeQuery(sentencia);
						while(resultado.next()) {
							reserva = resultado.getInt("idReserva");
							String entrada = resultado.getString("fechaEntrada");
							String salida = resultado.getString("fechaSalida");
							String dni= resultado.getString("dni");
							String nombre = resultado.getString("nombre");
							String apellido = resultado.getString("apellidos");
							//LocalDate fechaEntrada = String.valueOf(LocalDate.parse(entrada);
							//LocalDate fechaSalida = LocalDate.parse(salida);
							this.lblFechaEntrada.setText(entrada);
							this.lblFechaSalida.setText(salida);
							this.lblModificarDni.setText(dni);
							this.lblModificarNombre.setText(nombre);
							this.lblModificarApellidos.setText(apellido);
						
						}
						
						
					}catch (SQLException e) {
						e.printStackTrace();
					}
			}
    	}
	}

	@FXML
	void eliminarReserva() {
	
			Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
	    	alerta.setContentText("Está seguro que desea eliminar esta reserva?");
	    
	    	ButtonType buttonTypeYes = new ButtonType("Sí");
	    	ButtonType buttonTypeNo = new ButtonType("No");

	    	alerta.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

	    	alerta.showAndWait().ifPresent(response -> {
	    	    if (response == buttonTypeYes) {
	    	    	
	    	    	try {
		    	    	this.conn = new Conexion("hotelxyz","root","","jdbc:mysql://127.0.0.1:3306/");
						String sentencia = "DELETE FROM reservas WHERE idReserva='"+reserva+"'";
				    	PreparedStatement stmt = this.conn.getConexion().prepareStatement(sentencia);
				    	int count = stmt.executeUpdate(sentencia);
				    	System.out.println("filas afectadas: " + count);
				    	if(count!=0) {
				    		Alert alerta1 = new Alert(Alert.AlertType.INFORMATION);
					    	alerta1.setContentText("La reserva se ha eliminado correctamente");
					    	alerta1.showAndWait();
					    	this.stage.close();
				    	}else {
				    		Alert alert4 = new Alert(Alert.AlertType.ERROR);
							alert4.setContentText("Algo falló en la consulta a la base de datos");
							alert4.showAndWait();
				    	}
				    	
		    	    	
	    	    }catch (SQLException e1) {
					Alert alert4 = new Alert(Alert.AlertType.ERROR);
					alert4.setContentText("No se pudo establecer conexion con la base de datos");
					alert4.showAndWait();
					e1.printStackTrace();
				}
	    	    }
		});
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		this.habitaciones.addAll(btn1, btn2, btn3,btn4, btn5, btn6, btn7, btn8, btn9,btn10, btn11, btn12,btn13, btn14, btn15,btn16, btn17, btn18);
		//activaBotonesReservas();
		reservadas();
	}

}
