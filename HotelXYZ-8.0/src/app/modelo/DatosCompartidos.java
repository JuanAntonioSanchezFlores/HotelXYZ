package app.modelo;

import java.util.Date;

public class DatosCompartidos {

	private String usuario, dni;
	private Date fecha;
	public DatosCompartidos(String usuario, Date fecha, String dni) {
		super();
		this.dni = dni;
		this.usuario = usuario;
		this.fecha = fecha;
	}
	
	public DatosCompartidos(String dni) {
		
		this.dni = dni;
	}

	public DatosCompartidos() {}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	
	
	
}
