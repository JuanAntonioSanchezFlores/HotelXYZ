package app.modelo;

public class Servicio {

	private Integer idServicio;
	private Integer idCliente;
	private boolean taxi;
	private boolean maletas;
	
	public Servicio(Integer idServicio, Integer idCliente, boolean taxi, boolean maletas) {
		super();
		this.idServicio = idServicio;
		this.idCliente = idCliente;
		this.taxi = taxi;
		this.maletas = maletas;
	}
	public Integer getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public boolean isTaxi() {
		return taxi;
	}
	public void setTaxi(boolean taxi) {
		this.taxi = taxi;
	}
	public boolean isMaletas() {
		return maletas;
	}
	public void setMaletas(boolean maletas) {
		this.maletas = maletas;
	}
	
	
	
}
