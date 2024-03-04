package app.modelo;

public class Incidencias {

	Integer idIncidencia;
	Integer idHabitacion;
	String observaciones;
	public Incidencias(Integer idIncidencia, Integer idHabitacion, String observaciones) {
		super();
		this.idIncidencia = idIncidencia;
		this.idHabitacion = idHabitacion;
		this.observaciones = observaciones;
	}
	public Integer getIdIncidencia() {
		return idIncidencia;
	}
	public void setIdIncidencia(Integer idIncidencia) {
		this.idIncidencia = idIncidencia;
	}
	public Integer getIdHabitacion() {
		return idHabitacion;
	}
	public void setIdHabitacion(Integer idHabitacion) {
		this.idHabitacion = idHabitacion;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		observaciones = observaciones;
	}
	
	
}
