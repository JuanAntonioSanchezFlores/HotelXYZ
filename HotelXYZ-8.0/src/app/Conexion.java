package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.Alert;
/*Clase utilizada para gestionar la conexión con la base de datos
 * 
 */public class Conexion {
	
	 	//Los atributos de la clase son todos los parámetros de la conexión
		private String nombreBBDD;
		private String usuario;
		private String password;
		private String url;
		
		//Y la propia conexión
		private Connection conexion;

		//Generamos un constructor que recibe todos los datos de la cadena de conexión
		//Realiza la conexión
		public Conexion(String nombreBBDD, String usuario, String password, String url) {
			super();
			this.nombreBBDD = nombreBBDD;
			this.usuario = usuario;
			this.password = password;
			this.url = url;
			
			try
			{
				//Lo primero es cargar el driver
				Class.forName("com.mysql.jdbc.Driver");
				//Generamos la conexión
				conexion = (Connection) DriverManager.getConnection(this.url+this.nombreBBDD, this.usuario, this.password);
				if(conexion!=null)
				System.out.println("Conexión correcta");
				
			}catch(Exception e)
			{
				System.out.println("La conexión ha fallado");
				this.conexion=null;
			}
		}
		
		//Método sin parametros
		public Conexion() {
			
			try
			{
				//Lo primero es cargar el driver
				Class.forName("com.mysql.jdbc.Driver");
				//Generamos la conexión
				conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelxyz", "root", "");
				if(conexion!=null)
				System.out.println("Conexión correcta");
				
			}catch(Exception e)
			{
				System.out.println("La conexión ha fallado");
				this.conexion=null;
			}
		}
		
		//Método que nos devuelve la conexión de la base de datos
		public Connection getConexion()
		{
			return this.conexion;
		}
		
		
		public ResultSet consultaSeleccion(String procedimiento) {
			
			
			ResultSet resultado = null;
			Statement stmt;
			try {
				stmt = getConexion().prepareStatement(procedimiento);
				resultado = stmt.executeQuery(procedimiento);
				
						
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return resultado;
			
		}
		
		public int consultaInsertar(String sentencia) {
			int count;
			try {
				PreparedStatement stmt = getConexion().prepareStatement(sentencia);
		    	
				count = stmt.executeUpdate(sentencia);
				
				
			} catch (SQLException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				 alert.setContentText("No se pudo conectar con la base de datos");
				 alert.showAndWait();
				e.printStackTrace();
				return 0;
			}
			return count;
		}

	}


