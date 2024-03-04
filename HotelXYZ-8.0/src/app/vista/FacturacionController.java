
package app.vista;


import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import app.Conexion;
import app.modelo.Reserva;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



public class FacturacionController implements Initializable {

	
	@FXML
	private AnchorPane apVistaFacturacion, apVistaFactura, apParent;
	@FXML
	private TableView<Reserva> tblFacturas;
	@FXML
	private TableColumn<Reserva, String> tbcNombre, tbcApellidos, tbcDni;
	@FXML
	private TableColumn<Reserva, Date> tbcEntrada, tbcSalida;
	@FXML
	private TableColumn<Reserva, Integer> tbcHabitacion;
	@FXML
	private TextField txfDni, txfFiltroDni;
	@FXML
	private ScrollPane scrollParent;
	@FXML
    private Label lblBase,lblDiaEntrada,lblDiaSalida,lblImporteHabitacion,lblImporteMaleta,lblImporteTaxi,lblIva,lblMaleta,lblNumHabitacion,lblPrecio,lblTaxi,lblTotal,
    lblNombre, lblApellidos, lblDomicilio,lblCiudad, lblDni, lblProvincia, gridNombre, gridApellidos, gridDni, gridEntrada,gridSalida, lblAdvertencia, lblFechaFactura,
    lblCerrarFacturacion, lblNumFactura;
	@FXML
	private Button btnCrearFactura;

	Conexion conn = new Conexion();
	Stage stage = new Stage();
	Integer numHabitacion;
	
	
	ObservableList<Reserva> clientesConReservas = FXCollections.observableArrayList();

	
	public void obtenerEscenario(Stage stage) {
		this.stage = stage;
	}
	
	@FXML
	private void vistaFactura() {
		
		
		this.apVistaFacturacion.setVisible(false);
		this.apVistaFactura.setVisible(true);
		this.apParent.setPrefHeight(900);
		this.scrollParent.setPrefHeight(900);
		this.scrollParent.fitToHeightProperty();
		//this.apVistaFactura.setPrefSize(900, 700);
		
	}

	

	public void insertarFactura() {
		if(this.tblFacturas.getSelectionModel().getSelectedIndex()!= -1) {
		int seleccion = this.tblFacturas.getSelectionModel().getSelectedIndex();
		Reserva seleccionado = clientesConReservas.get(seleccion);
		Integer idReserva = seleccionado.getIdReserva();
		String dni = seleccionado.getDni();
		numHabitacion = seleccionado.getHabitacion();
		String fechaEntrada = String.valueOf(seleccionado.getEntrada());
		String fechaSalida = String.valueOf(seleccionado.getSalida());
		LocalDate entrada = LocalDate.parse(fechaEntrada, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDate salida = LocalDate.parse((CharSequence) fechaSalida, DateTimeFormatter.ISO_LOCAL_DATE);
		Duration duration = Duration.between(entrada.atStartOfDay(), salida.atStartOfDay());//Dias a facturar
		int duracion = (int)duration.toDays();
		int precio=0;
		try {
			ResultSet resultado = conn.consultaSeleccion("SELECT precio from habitaciones where idHabitacion = "+numHabitacion+"");
			if(resultado.next()) {
			precio = resultado.getInt("precio");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int importe = (int)duracion * precio;
		
		String sentencia = "INSERT INTO facturas (idCliente, importe,importeIva,totalFactura,emitida) VALUES (obtenerIdCliente("+numHabitacion+"),"+(importe)+", (importe*21)/100, importe+importeIva, 1)";
		int fila = conn.consultaInsertar(sentencia);
		if(fila != 0) {
			Alert alerta1 = new Alert(Alert.AlertType.INFORMATION);
	    	alerta1.setContentText("La factura se ha generado correctamente");
	    	alerta1.showAndWait();
		}else {
			Alert alert4 = new Alert(Alert.AlertType.ERROR);
			alert4.setContentText("Se ha producido un error al generar la factura");
			alert4.showAndWait();
		}
		conn.consultaInsertar("UPDATE reservas SET facturado = 1 WHERE idReserva = "+idReserva+"");
		
		vistaFactura();
		obtenerFactura();
		}
		
	}

	@FXML
	public void cerrarVentana() {
		this.stage.close();
	}

	public void crearTblFacturas() {
		this.tbcHabitacion.setCellValueFactory(new PropertyValueFactory<>("habitacion"));
		this.tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.tbcApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
		this.tbcDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
		this.tbcEntrada.setCellValueFactory(new PropertyValueFactory<>("entrada"));
		this.tbcSalida.setCellValueFactory(new PropertyValueFactory<>("salida"));

		this.tblFacturas.setItems(clientesConReservas);
		if (this.clientesConReservas.size() == 0) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("No hay reservas para facturar");
			alert.showAndWait();
		}

		FilteredList<Reserva> listaFiltrada = new FilteredList<>(clientesConReservas);
		this.tblFacturas.setItems(listaFiltrada);
		this.txfFiltroDni.textProperty().addListener((observable, oldValue, newValue) -> {
			listaFiltrada.setPredicate(reserva -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String filtro = newValue.toLowerCase();
				return reserva.getDni().toLowerCase().contains(filtro);

			});
		});
	}

	public void seleccionFactura() {
		this.tblFacturas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Reserva>() {
			@Override
			public void changed(ObservableValue<? extends Reserva> observable, Reserva oldValue, Reserva newValue) {
				if (newValue != null) {
					btnCrearFactura.setDisable(false);

				} else
					btnCrearFactura.setDisable(true);

			}
		});
	}

	public void inicializarClientesConReservas() {
		try {
			ResultSet resultado = this.conn.consultaSeleccion("call obtenerClienteConReserva()");
			while (resultado.next()) {
				Integer idReserva = resultado.getInt("idReserva");
				Integer numHabitacion = resultado.getInt("idHabitacionReserva");
				String nombre = resultado.getString("nombre");
				String apellidos = resultado.getString("apellidos");
				String dni = resultado.getString("dni");
				Date entrada = resultado.getDate("fechaEntrada");
				Date salida = resultado.getDate("fechaSalida");

				Reserva cliente = new Reserva(idReserva, numHabitacion, nombre, apellidos, dni, entrada, salida);
				this.clientesConReservas.add(cliente);

			}
			resultado.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void fechaActual() {
		LocalDateTime ahora = LocalDateTime.now();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String fechaFormateada = ahora.format(formato);
		this.lblFechaFactura.setText(fechaFormateada);
	}
	
	public void obtenerFactura() {
		fechaActual();
		String fechaEntrada="";
		String fechaSalida="";
		int base=0;
		int iva = base*21/100;
		int total = base+iva;
		
		
		
		try {
			ResultSet resultado = this.conn.consultaSeleccion("call obtenerFactura("+(numHabitacion)+");");
			//do {
				
				if(!resultado.next()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("No existe ningún cliente con el D.N.I. indicado");
					alert.showAndWait();
				}else {
					this.lblNombre.setText(resultado.getString("nombre"));
					this.lblApellidos.setText(resultado.getString("apellidos"));
					this.lblDomicilio.setText(resultado.getString("domicilio"));
					this.lblCiudad.setText(resultado.getString("ciudad"));
					this.lblProvincia.setText(resultado.getString("provincia"));
					this.lblDni.setText(resultado.getString("dni"));
					this.lblNumHabitacion.setText(String.valueOf(numHabitacion));
					this.lblDiaEntrada.setText(String.valueOf(resultado.getDate("fechaEntrada")));
					this.lblDiaSalida.setText(String.valueOf(resultado.getDate("fechaSalida")));
					this.lblBase.setText(String.valueOf(resultado.getInt("importe")));
					this.lblIva.setText(String.valueOf(resultado.getInt("importeIva")));
					this.lblTotal.setText(String.valueOf(resultado.getInt("totalFactura")));
					this.lblImporteHabitacion.setText(String.valueOf(resultado.getInt("importe")));
					
					
					//Guardamos la fecha como string
					//fechaEntrada =String.valueOf(resultado.getDate("fechaEntrada"));
					//fechaSalida = String.valueOf(resultado.getDate("fechaSalida"));
					//this.lblDiaEntrada.setText(String.valueOf(resultado.getDate("fechaEntrada")));
					//this.lblDiaSalida.setText(String.valueOf(resultado.getDate("fechaSalida")));
					
				}
			//}while(resultado.next());
				ResultSet resultado2 = conn.consultaSeleccion("SELECT precio FROM habitaciones WHERE idHabitacion = "+(numHabitacion)+"");
				String precio = String.valueOf(resultado2.getInt("precio"));
				this.lblPrecio.setText(precio);
			resultado.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*LocalDate entrada = LocalDate.parse(fechaEntrada, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDate salida = LocalDate.parse(fechaSalida, DateTimeFormatter.ISO_LOCAL_DATE);
		Duration duracion = Duration.between(entrada.atStartOfDay(), salida.atStartOfDay());
		System.out.println("Duracion dias: "+ duracion.toDays());*/
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { 
	
	
		inicializarClientesConReservas();
		crearTblFacturas();
		seleccionFactura();
		//insertarFactura();

	}
	
}
