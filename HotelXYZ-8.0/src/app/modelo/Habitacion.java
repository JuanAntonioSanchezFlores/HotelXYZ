package app.modelo;

import java.util.Date;

public class Habitacion {

	private Integer idHabitacion, precio;

	public Habitacion(Integer idHabitacion, Integer precio) {
		super();
		this.idHabitacion = idHabitacion;
		this.precio = precio;
	}

	public Integer getIdHabitacion() {
		return idHabitacion;
	}

	public void setIdHabitacion(Integer idHabitacion) {
		this.idHabitacion = idHabitacion;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}
	
	
	 
	
	
}
