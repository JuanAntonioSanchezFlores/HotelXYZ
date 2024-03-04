package app.modelo;

import java.util.Date;

public class Reserva {

	private Integer habitacion, idReserva;
	private String nombre, apellidos, dni;
	private Date entrada, salida;
	public Reserva(Integer idReserva,Integer habitacion, String nombre, String apellidos, String dni, Date entrada, Date salida) {
		super();
		this.idReserva = idReserva;
		this.habitacion = habitacion;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.entrada = entrada;
		this.salida = salida;
	}
	
	public Integer getHabitacion() {
		return habitacion;
	}
	public void setHabitacion(Integer habitacion) {
		this.habitacion = habitacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public Date getEntrada() {
		return entrada;
	}
	public void setEntrada(Date entrada) {
		this.entrada = entrada;
	}
	public Date getSalida() {
		return salida;
	}
	public void setSalida(Date salida) {
		this.salida = salida;
	}

	public Integer getIdReserva() {
		return idReserva;
	}

	
	
	
	
}
