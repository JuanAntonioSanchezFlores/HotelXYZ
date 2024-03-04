package app.vista;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

import app.Conexion;
import app.modelo.Cliente;
import app.modelo.DatosCompartidos;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuController implements Initializable {
	
	
	
	@FXML
	private AnchorPane apMenu, apReservas;
	@FXML
	private TableView<Cliente> tblListaClientes;
	@FXML
	private TableColumn<Cliente, String> tbcNombre;
	@FXML
	private TableColumn<Cliente, String> tbcApellidos;
	@FXML
	private TableColumn<Cliente, String> tbcDni;
	@FXML
	private Pane pnRegClientes, pnInfoDiaria,pnVistaEliminar;
	
	@FXML
	private TextField txfNombreCliente, txfApellidoCliente, txfDniCli,txfDomicilioCli,txfCiudadCli,txfProvinciaCli,txfEmailCli,txfTelefonoCli,
						txfFiltroNombre, txfFiltroApellido, txfNumCuenta;
	
	@FXML
	private Button btnRegistroCli, btnEliminarCli, btnModificarCli, btnActualizar;
	
	@FXML
	private MenuButton btnClientes, btnReservas,btnIncidencias, btnServicios;

	@FXML
	private ChoiceBox<String> chFormaPago;
    		
	@FXML
	private LineChart<String, Integer> grafGraficaMensual;

	@FXML
	private CategoryAxis catAxis;
	
	@FXML
	private NumberAxis numAxis;
	
	@FXML
	private Label lblRegistroClientes,lblEliminarCliente,lblClientesReg,lblFecha,lblHDisponibles,lblHoraSesion,lblIncidencias,lblUsuario;

	@FXML
	private Label lblNum1, lblNum2,lblNum3, lblNum4,lblNum5, lblNum6;
	
	Conexion miConexion;
	Stage stage = new Stage();
	Boolean cambiaVista;
	DatosCompartidos sesion = new DatosCompartidos();
	
	
	ObservableList<MenuButton> botonesMenu = FXCollections.observableArrayList();
	ObservableList<String> pagos = FXCollections.observableArrayList("TARGETA", "METÁLICO");
	ObservableList<Cliente> listadoClientes = FXCollections.observableArrayList();
	
	Calendar calendario;
	
	
	public void reloj() {
		
		calendario = Calendar.getInstance();//Hora actual 
		int hora=calendario.get(Calendar.HOUR_OF_DAY);
		int minuto = calendario.get(Calendar.MINUTE);
		int segundo = calendario.get(Calendar.SECOND);
		
		this.lblNum1.setText(String.valueOf(hora / 10));
		this.lblNum2.setText(String.valueOf(hora % 10));
		this.lblNum3.setText(String.valueOf(minuto / 10));
		this.lblNum4.setText(String.valueOf(minuto % 10));
		this.lblNum5.setText(String.valueOf(segundo / 10));
		this.lblNum6.setText(String.valueOf(segundo % 10));
		
	}
	
	public void actualizarReloj() {
		calendario = Calendar.getInstance();//Hora actual 
		int hora=calendario.get(Calendar.HOUR_OF_DAY);
		int minuto = calendario.get(Calendar.MINUTE);
		int segundo = calendario.get(Calendar.SECOND);
		
		this.lblNum1.setText(String.valueOf(hora / 10));
		this.lblNum2.setText(String.valueOf(hora % 10));
		this.lblNum3.setText(String.valueOf(minuto / 10));
		this.lblNum4.setText(String.valueOf(minuto % 10));
		this.lblNum5.setText(String.valueOf(segundo / 10));
		this.lblNum6.setText(String.valueOf(segundo % 10));
	}
	
	public void ejecutaReloj() {
		Timeline lineaTiempo = new Timeline();
		Timeline lineaSecundaria = new Timeline();
		lineaSecundaria.setCycleCount(Timeline.INDEFINITE);
		
		KeyFrame keyPrimario = new KeyFrame(
				new Duration(1000-(calendario.get(Calendar.MILLISECOND))%1000),
				(event)->{
					actualizarReloj();
					lineaSecundaria.play();
				}
		);
		
		KeyFrame keySecundario = new KeyFrame(
				Duration.seconds(1),
				(event)->{
					actualizarReloj();
					
				}
				);
		lineaTiempo.getKeyFrames().add(keyPrimario);
		lineaSecundaria.getKeyFrames().add(keySecundario);
		lineaTiempo.play();
		
	}
	
	public void obtenerUsuario(String s) {
		this.lblUsuario.setText(s);
	}
	
	public void inicializarFormaPago() {
		this.chFormaPago.setItems(pagos);
	}
	private void iniciarLineChart()
	{
		
			// Configurar los datos del gráfico de líneas
	        catAxis.setLabel("Dias de la semana");
	        numAxis.setLabel("Numero Habitaciones (18)");
	        grafGraficaMensual.setTitle("Gráfica Semanal");
	        catAxis.lookup(".axis-label").setStyle("-fx-text-fill: #90eb09;");
	        numAxis.lookup(".axis-label").setStyle("-fx-text-fill: #90eb09;");
	        
	        XYChart.Series<String, Integer> serieInformatica;
	        
			serieInformatica = new Series<String, Integer> ();
			serieInformatica.setName("");
			serieInformatica.getData().add(new XYChart.Data<String,Integer>("Lunes", 10));
			serieInformatica.getData().add(new XYChart.Data<String,Integer>("Martes", 7));
			serieInformatica.getData().add(new XYChart.Data<String,Integer>("Miercoles",3));
			serieInformatica.getData().add(new XYChart.Data<String,Integer>("Jueves", 8));
			serieInformatica.getData().add(new XYChart.Data<String,Integer>("Viernes", 15));
			serieInformatica.getData().add(new XYChart.Data<String,Integer>("Sábado", 18));
			serieInformatica.getData().add(new XYChart.Data<String,Integer>("Domingo", 18));
			
			
	        grafGraficaMensual.getData().add(serieInformatica);
	        for (XYChart.Data<String, Integer> data : serieInformatica.getData()) {
	            data.getNode().lookup(".chart-line-symbol").setStyle("-fx-background-color: black, #90eb09;");
	            
	        }
	        grafGraficaMensual.setStyle("-fx-background-color:  #141414;");
	        grafGraficaMensual.lookup(".chart-title").setStyle("-fx-text-fill: #90eb09;");
	       
	 }
	//Método que recibe la conexión a la base de datos
	public void getConexion(Conexion conexion){
		this.miConexion=conexion;
	}
	
	public void desactivaBotonesMenu() {
		for(MenuButton boton : botonesMenu) {
			boton.setDisable(true);
		}
	}
	
	public void activaBotonesMenu() {
		for(MenuButton boton : botonesMenu) {
			boton.setDisable(false);
		}
	}
	
	public void tablaClientes() {
		
		this.tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.tbcApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
		this.tbcDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
		
		this.tblListaClientes.setItems(listadoClientes);
		
		FilteredList<Cliente> listaFiltrada = new FilteredList<>(listadoClientes);
		this.tblListaClientes.setItems(listaFiltrada);
		this.txfFiltroNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            listaFiltrada.setPredicate(cliente -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String filtro = newValue.toLowerCase();
                return cliente.getNombre().toLowerCase().contains(filtro);
                        
            });
        });
		this.tblListaClientes.setItems(listaFiltrada);
		this.txfFiltroApellido.textProperty().addListener((observable, oldValue, newValue) -> {
            listaFiltrada.setPredicate(cliente -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String filtro = newValue.toLowerCase();
                return cliente.getApellidos().toLowerCase().contains(filtro);
                        
            });
        });
	}
	
	public void inicializarClientes() {
		
		try {
			this.miConexion = new Conexion("hotelxyz","root","","jdbc:mysql://127.0.0.1:3306/");
			String sentencia = "SELECT persona.*, cliente.* FROM persona JOIN cliente ON persona.idPersona=cliente.idPersona;";
			Statement stmt = this.miConexion.getConexion().prepareStatement(sentencia);
			ResultSet resultado = stmt.executeQuery(sentencia);
		
			while (resultado.next()) {
	        	Cliente nuevo = new Cliente(resultado.getInt("idPersona"), resultado.getString("nombre"), resultado.getString("apellidos"), resultado.getString("dni"), resultado.getString("domicilio"), resultado.getString("ciudad"),
			resultado.getString("provincia"),resultado.getString("email"),resultado.getInt("telefono"), resultado.getInt("idCliente"), resultado.getInt("idPersona"), resultado.getString("numCuenta"),
			resultado.getString("formaPago"));
	            this.listadoClientes.add(nuevo);
	            System.out.println(nuevo.getNombre());
			}
		
		}catch (SQLException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("No se pudo establecer conexion con la base de datos");
			alert.showAndWait();
			e.printStackTrace();
		}
	}
	
	@FXML
	public void vistaRegistroClientes() {
	    this.pnInfoDiaria.setVisible(false);
	    this.pnVistaEliminar.setVisible(false);
	    this.lblRegistroClientes.setText("Registro Clientes");
	    this.txfNombreCliente.setText(null);
		this.txfApellidoCliente.setText(null);
		this.txfDniCli.setText(null);
		this.txfDomicilioCli.setText(null);
		this.txfCiudadCli.setText(null);
		this.txfProvinciaCli.setText(null);
		this.txfEmailCli.setText(null);
		this.txfTelefonoCli.setText(null);
		this.btnRegistroCli.setVisible(true);
		this.btnActualizar.setVisible(false);
		
    	this.pnRegClientes.setVisible(true);
    	desactivaBotonesMenu();
    	
	}
	@FXML
	public void vistaEliminarCliente() {
		this.listadoClientes.clear();
		inicializarClientes();
		tablaClientes();
		this.pnInfoDiaria.setVisible(false);
		this.pnRegClientes.setVisible(false);
		this.btnModificarCli.setVisible(false);
	    this.btnEliminarCli.setVisible(true);
	    this.lblEliminarCliente.setText("Eliminar Cliente");
	    
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.0),this.pnVistaEliminar);
    	fadeTransition.setFromValue(0.0);
    	fadeTransition.setToValue(1.0);
    	fadeTransition.play();
    	this.pnVistaEliminar.setVisible(true);
    	desactivaBotonesMenu();
	}
	
	@FXML
	public void vistaEditarCliente() {
		vistaEliminarCliente();
		this.btnEliminarCli.setVisible(false);
		this.btnModificarCli.setVisible(true);
		this.lblEliminarCliente.setText("Editar Cliente");
	} 
	
	@FXML
	private void editarCliente() {
		int indice = this.tblListaClientes.getSelectionModel().getSelectedIndex();
		Cliente seleccionado = this.listadoClientes.get(indice);
		
		vistaRegistroClientes();
		this.lblRegistroClientes.setText("Editar Clientes");
		this.btnRegistroCli.setVisible(false);
		this.btnActualizar.setVisible(true);
		this.txfNombreCliente.setText(seleccionado.getNombre());
		this.txfApellidoCliente.setText(seleccionado.getApellidos());
		this.txfDniCli.setText(seleccionado.getDni());
		this.txfDomicilioCli.setText(seleccionado.getDomicilio());
		this.txfCiudadCli.setText(seleccionado.getCiudad());
		this.txfProvinciaCli.setText(seleccionado.getProvincia());
		this.txfEmailCli.setText(seleccionado.getEmail());
		this.txfTelefonoCli.setText(String.valueOf(seleccionado.getTelefono()));
		this.txfNumCuenta.setText(String.valueOf(seleccionado.getNumCuenta()));
			
		TextField[] campo = {this.txfNombreCliente,this.txfApellidoCliente,this.txfDniCli,this.txfDomicilioCli,this.txfCiudadCli,this.txfProvinciaCli,this.txfEmailCli,this.txfTelefonoCli,this.txfNumCuenta};
		int cuentaVacios = 0;
		for(TextField texto : campo) {
			if(texto.getText()==null || texto.getText().isBlank()) {
				cuentaVacios++;
			}
		}
		if(cuentaVacios!=0) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Hay "+cuentaVacios+" campos sin rellenar");
			alert.showAndWait();
		}else {
			this.btnActualizar.setOnAction(e->{
				Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		    	alerta.setContentText("Está seguro que desea actualizar este cliente?");
		    
		    	ButtonType buttonTypeYes = new ButtonType("Sí");
		    	ButtonType buttonTypeNo = new ButtonType("No");

		    	alerta.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

		    	alerta.showAndWait().ifPresent(response -> {
		    	    if (response == buttonTypeYes) {
		    	    	String dni = this.txfDniCli.getText();
		    	    	int id = 0;
		    	    	try {
			    	    	this.miConexion = new Conexion("hotelxyz","root","","jdbc:mysql://127.0.0.1:3306/");
							String sentencia = "UPDATE persona SET nombre='"+this.txfNombreCliente.getText()+"', apellidos='"+
			    	    	this.txfApellidoCliente.getText()+"', dni='"+this.txfDniCli.getText()+"', domicilio='"+
							this.txfDomicilioCli.getText()+"', ciudad='"+this.txfCiudadCli.getText()+"', provincia='"+
			    	    	this.txfProvinciaCli.getText()+"', email='"+this.txfEmailCli.getText()+"', telefono='"+
							(Integer.parseInt(this.txfTelefonoCli.getText()))+"' WHERE idPersona="+
			    	    	(Integer.valueOf(seleccionado.getIdPersona()))+"";
							
					    	PreparedStatement stmt = this.miConexion.getConexion().prepareStatement(sentencia);
					    	int count = stmt.executeUpdate(sentencia);
					    	System.out.println("filas afectadas: " + count);
					    	
					    	
					    	String sentencia2 = "SELECT idPersona FROM persona WHERE dni='"+dni+"'";
							Statement stmt2 = this.miConexion.getConexion().prepareStatement(sentencia2);
							ResultSet resultado = stmt2.executeQuery(sentencia2);
							while(resultado.next()) {
								id = resultado.getInt("idPersona");
							}
							
							this.miConexion = new Conexion("hotelxyz","root","","jdbc:mysql://127.0.0.1:3306/");
							String formaPago = this.chFormaPago.getValue();
							String sentencia3 = "UPDATE cliente SET numCuenta='"+campo[8].getText()+"', formaPago='"+formaPago+"' WHERE idPersona = '"+id+"';";
					    	PreparedStatement stmt3 = this.miConexion.getConexion().prepareStatement(sentencia3);
					    	int contar = stmt3.executeUpdate(sentencia3);
					    	System.out.println("filas afectadas: " + contar);
					    	
					    	for(TextField texto : campo) {
					    		texto.setText(null);
					    	}
					    	activaBotonesMenu();
					    	consultarEstadoHabitacionesClientes();
			    	    	Alert alerta1 = new Alert(Alert.AlertType.INFORMATION);
					    	alerta1.setContentText("El cliente se ha actualizado correctamente");
					    	alerta1.showAndWait();
		    	    }catch (SQLException e1) {
						Alert alert4 = new Alert(Alert.AlertType.ERROR);
						alert4.setContentText("No se pudo establecer conexion con la base de datos");
						alert4.showAndWait();
						e1.printStackTrace();
					}
		    	    }
			});
			
	    	    
	    	});
	    	    	
	    	
		}
	}
	
			
	
	@FXML
	public void cerrarClientes() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmación");
		alert.setHeaderText("¿Estás seguro que desea salir de la vista Clientes ?");
		alert.setContentText("Se perderán todos los cambios no guardados.");
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			this.pnRegClientes.setVisible(false);
			this.pnVistaEliminar.setVisible(false);
			this.pnInfoDiaria.setVisible(true);
			FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.0),this.pnInfoDiaria);
	    	fadeTransition.setFromValue(0.0);
	    	fadeTransition.setToValue(1.0);
	    	fadeTransition.play();
	    	activaBotonesMenu();
		}
    	
    	
	}
	
	
	@FXML
	public void registrarCliente() {
		
			this.btnActualizar.setVisible(false);
			this.btnRegistroCli.setVisible(true);
			
				TextField[] campo = {this.txfNombreCliente,this.txfApellidoCliente,this.txfDniCli,this.txfDomicilioCli,this.txfCiudadCli,this.txfProvinciaCli,this.txfEmailCli,this.txfTelefonoCli, this.txfNumCuenta};
				int cuentaVacios = 0;
				for(TextField texto : campo) {
					if(texto.getText()==null || texto.getText().isBlank()) {
						cuentaVacios++;
					}
				}
				if(cuentaVacios!=0) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Hay "+cuentaVacios+" campos sin rellenar");
					alert.showAndWait();
				}else {
					
					 String nombre = campo[0].getText();
					 String apellido = campo[1].getText();
					
					 if(contieneNumeros(nombre)) {
						 Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setContentText("El nombre no puede contener números");
							alert.showAndWait();
							campo[0].clear();
					 }else if(contieneNumeros(apellido)) {
						 Alert alert = new Alert(Alert.AlertType.ERROR);
						 alert.setContentText("El apellido no puede contener números");
						 alert.showAndWait();
						 campo[1].clear();
					 }else {
					
						Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
				    	alerta.setContentText("Está seguro que desea insertar el cliente?");
				    
				    	ButtonType buttonTypeYes = new ButtonType("Sí");
				    	ButtonType buttonTypeNo = new ButtonType("No");
	
				    	alerta.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
	
				    	alerta.showAndWait().ifPresent(response -> {
				    	    if (response == buttonTypeYes) {
				    	    	String dni = this.txfDniCli.getText();
				    	    	int id = 0;
				    	    	try {
					    	    	this.miConexion = new Conexion("hotelxyz","root","","jdbc:mysql://127.0.0.1:3306/");
									String sentencia = "INSERT INTO persona (nombre, apellidos, dni, domicilio, ciudad, provincia, email, telefono) "
											+ "VALUES ('"+campo[0].getText()+"','"+campo[1].getText()+"', '"+campo[2].getText()+
											"','"+campo[3].getText()+"', '"+campo[4].getText()+"','"+campo[5].getText()+"', '"+campo[6].getText()+
											"', '"+Integer.parseInt(campo[7].getText())+"');";
							    	PreparedStatement stmt = this.miConexion.getConexion().prepareStatement(sentencia);
							    	int count = stmt.executeUpdate(sentencia);
							    	System.out.println("filas afectadas: " + count);
							    	
							    	String sentencia2 = "SELECT idPersona FROM persona WHERE dni='"+dni+"'";
									Statement stmt2 = this.miConexion.getConexion().prepareStatement(sentencia2);
									ResultSet resultado = stmt2.executeQuery(sentencia2);
									while(resultado.next()) {
										id = resultado.getInt("idPersona");
									}
									
									this.miConexion = new Conexion("hotelxyz","root","","jdbc:mysql://127.0.0.1:3306/");
									String formaPago = this.chFormaPago.getValue();
									String sentencia3 = "INSERT INTO cliente(idPersona,numCuenta, formaPago)"
											+ " VALUES ('"+id+"','"+(Integer.parseInt(campo[8].getText()))+
											"','"+formaPago+"');";
							    	PreparedStatement stmt3 = this.miConexion.getConexion().prepareStatement(sentencia3);
							    	int contar = stmt3.executeUpdate(sentencia3);
							    	System.out.println("filas afectadas: " + contar);
							    	
							    	for(TextField texto : campo) {
							    		texto.setText(null);
							    	}
							    	activaBotonesMenu();
							    	consultarEstadoHabitacionesClientes();
					    	    	Alert alerta1 = new Alert(Alert.AlertType.INFORMATION);
							    	alerta1.setContentText("El cliente se ha insertado correctamente");
							    	alerta1.showAndWait();
				    	    }catch (SQLException e) {
								Alert alert4 = new Alert(Alert.AlertType.ERROR);
								alert4.setContentText("No se pudo establecer conexion con la base de datos");
								alert4.showAndWait();
								e.printStackTrace();
							}
				    	    } 
				    	});
				    	    	
				    	
					}
			
				}
			
	}

	@FXML
	public void eliminarClientes() {
		tablaClientes();
		
		int indice = this.tblListaClientes.getSelectionModel().getSelectedIndex();
		int campo = this.listadoClientes.get(indice).getIdCliente();
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setContentText("Está seguro que desea eliminar el cliente?");
    	ButtonType buttonTypeYes = new ButtonType("Sí");
    	ButtonType buttonTypeNo = new ButtonType("No");

    	alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

    	alert.showAndWait().ifPresent(response -> {
    	    if (response == buttonTypeYes) {
    	    	try {
    	    		this.miConexion = new Conexion("hotelxyz","root","","jdbc:mysql://127.0.0.1:3306/");
    	    		String eliminarCliente ="DELETE FROM cliente WHERE idCliente="+campo+"";
    				PreparedStatement stmt = this.miConexion.getConexion().prepareStatement(eliminarCliente);
    		    	
    		    	int count = stmt.executeUpdate(eliminarCliente);
    		    	System.out.println("filas afectadas: " + count);
    		    	
    		    	if(count!=0) {
    		    		this.txfFiltroNombre.setText(null);
    		    		this.txfFiltroApellido.setText(null);
    		    		this.listadoClientes.clear();
    		    		inicializarClientes();
    		    		consultarEstadoHabitacionesClientes();
    		    		
    		    		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    			    	alerta.setContentText("El cliente se ha eliminado correctamente");
    			    	alerta.showAndWait();
    			    	/**/
    		    	}else {
    		    		Alert alerta2 = new Alert(Alert.AlertType.ERROR);
    					alerta2.setContentText("El cliente no se ha eliminado\nNo se pudo establecer conexion con la base de datos");
    					alerta2.showAndWait();
    		    	}
    	    	}catch (SQLException e) {
    				Alert alerta3 = new Alert(Alert.AlertType.ERROR);
    				alerta3.setContentText("No se pudo establecer conexion con la base de datos");
    				alerta3.showAndWait();
    				e.printStackTrace();
    			}
    	    	
    	    }
    	});
		
		
	    	
		
	}
	
	 private boolean contieneNumeros(String s) {
		 
	        for (char c : s.toCharArray()) {
	            if (Character.isDigit(c)) {
	                return true;
	            }
	        }
	        return false;
	    }
	
	 public boolean contieneLetras(String c, int n) {
		 int numero = n;
		 if(c.matches("\\d{"+numero+"}")) {
				return true;
			} else {
				return false;
			}
		}
	 
	
	/**** RESERVAS  ******/
	
	
	
	public void vistaReservas() {
		
		try {
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(getClass().getResource("Reservas.fxml"));
			AnchorPane root = (AnchorPane)cargador.load();
			ReservasController controlador = (ReservasController)cargador.getController();
			controlador.obtenerEscenario(stage);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			//stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
			FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.0),this.apReservas);
	    	fadeTransition.setFromValue(0.0);
	    	fadeTransition.setToValue(1.0);
	    	fadeTransition.play();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
public void vistaEliminarReservas() {
		
		try {
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(getClass().getResource("EliminarReservas.fxml"));
			AnchorPane root = (AnchorPane)cargador.load();
			EliminarReservasController controlador = (EliminarReservasController)cargador.getController();
			controlador.obtenerEscenario(stage);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			//stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
			FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.0),this.apReservas);
	    	fadeTransition.setFromValue(0.0);
	    	fadeTransition.setToValue(1.0);
	    	fadeTransition.play();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@FXML
	public void vistaModificarReservas() {
		
		try {
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(getClass().getResource("ModificarReservas.fxml"));
			AnchorPane root = (AnchorPane)cargador.load();
			modificarReservasController controlador = (modificarReservasController)cargador.getController();
			controlador.obtenerEscenario(stage);
			
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.show();
			FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.0),this.apReservas);
	    	fadeTransition.setFromValue(0.0);
	    	fadeTransition.setToValue(1.0);
	    	fadeTransition.play();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public void consultarEstadoHabitacionesClientes() {
		this.miConexion = new Conexion("hotelxyz","root","","jdbc:mysql://127.0.0.1:3306/");
		String habitacionesReservadas ="SELECT COUNT(reservada) as cantidad FROM reservas WHERE reservada=1";
		String numeroClientes = "SELECT COUNT(idCliente) as clientes FROM cliente";
		String numeroIncidencias = "SELECT COUNT(solucionado) as numInci FROM incidencias WHERE solucionado=0;";
		
		try {
			PreparedStatement sentencia = this.miConexion.getConexion().prepareStatement(habitacionesReservadas);
			ResultSet resultado = sentencia.executeQuery();
			while(resultado.next()) {
				this.lblHDisponibles.setText(String.valueOf(18-(resultado.getInt("cantidad"))));
			}
			
			PreparedStatement sentencia2 = this.miConexion.getConexion().prepareStatement(numeroClientes);
			ResultSet resultado2 = sentencia2.executeQuery();
			while(resultado2.next()) {
				this.lblClientesReg.setText(String.valueOf(resultado2.getInt("clientes")));
			}
			
			PreparedStatement sentencia3 = this.miConexion.getConexion().prepareStatement(numeroIncidencias);
			ResultSet resultado3 = sentencia3.executeQuery();
			while(resultado3.next()) {
				this.lblIncidencias.setText(String.valueOf(resultado3.getInt("numInci")));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}	
	}
	
	
	public void fechaActual() {
		 LocalDateTime ahora = LocalDateTime.now();
		 DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		 DateTimeFormatter formato2 = DateTimeFormatter.ofPattern("HH:mm:ss");
	     String fechaFormateada = ahora.format(formato);
	     String hora = ahora.format(formato2);
	     this.lblFecha.setText(fechaFormateada);
	     this.lblHoraSesion.setText(hora);
	     
	}
	
	/***** INCIDENCIAS ****/
	
	@FXML
	public void vistaIncidencias() {
		
		try {
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(getClass().getResource("Incidencias.fxml"));
			AnchorPane root = (AnchorPane)cargador.load();
			IncidenciasController controlador = (IncidenciasController)cargador.getController();
			controlador.obtenerEscenario(stage);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.0),this.apReservas);
	    	fadeTransition.setFromValue(0.0);
	    	fadeTransition.setToValue(1.0);
	    	fadeTransition.play();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void vistaEliminarIncidencias() {
		
		try {
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(getClass().getResource("EliminarIncidencias.fxml"));
			AnchorPane root = (AnchorPane)cargador.load();
			EliminarIncidenciasControlador controlador = (EliminarIncidenciasControlador)cargador.getController();
			controlador.obtenerEscenario(stage);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			//stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
			FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.0),this.apReservas);
	    	fadeTransition.setFromValue(0.0);
	    	fadeTransition.setToValue(1.0);
	    	fadeTransition.play();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		actualizarReloj();
		ejecutaReloj();
		obtenerUsuario(sesion.getUsuario());
		inicializarClientes();
		inicializarFormaPago();
		tablaClientes();
		fechaActual();
		consultarEstadoHabitacionesClientes();
		iniciarLineChart();
		
		this.botonesMenu.addAll(this.btnClientes, this.btnReservas);

	}
	
	public void consultarIncidencias() {
		
	}

	/***  SERVICIOS  ***/
	
	@FXML
	public void vistaServicios() {
		
		try {
			//Stage stage = new Stage();
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(getClass().getResource("Servicio.fxml"));
			AnchorPane root = (AnchorPane)cargador.load();
			ServicioController controlador = (ServicioController)cargador.getController();
			controlador.obtenerEscenario(stage);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			//stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
			FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.0),this.apReservas);
	    	fadeTransition.setFromValue(0.0);
	    	fadeTransition.setToValue(1.0);
	    	fadeTransition.play();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void vistaFacturas() {
		try {
			
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(getClass().getResource("Facturacion.fxml"));
			ScrollPane root = (ScrollPane)cargador.load();
			FacturacionController controlador = (FacturacionController)cargador.getController();
			controlador.obtenerEscenario(stage);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
