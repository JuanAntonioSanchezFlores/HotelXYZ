package app.modelo;


public class Factura extends Persona{

	
	private Integer numFactura,idCliente;
	private Integer importe=0;
	private Integer numHabitacion;
	private final Integer TIPOIVA = 21;
	private Integer importeIva = (importe*TIPOIVA)/100;
	private Integer totalFactura = importeIva+importe;
	
	
	public Factura(Integer idPersona, String nombre, String apellidos, String dni, String domicilio, String ciudad,
			String provincia, String email, Integer telefono,Integer numHabitacion,Integer numFactura, Integer idCliente, Integer importe,
			Integer importeIva, Integer totalFactura) {
		super(idPersona, nombre, apellidos, dni, domicilio, ciudad, provincia, email, telefono);
		
		this.numFactura = numFactura;
		this.idCliente = idCliente;
		this.importe = importe;
		this.importeIva = importeIva;
		this.totalFactura = totalFactura;
	}
	public Factura() {}
	
	public Integer getNumFactura() {
		return numFactura;
	}

	public void setNumFactura(Integer numFactura) {
		this.numFactura = numFactura;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getImporte() {
		return importe;
	}

	public void setImporte(Integer importe) {
		this.importe = importe;
	}

	public Integer getNumHabitacion() {
		return numHabitacion;
	}

	public void setNumHabitacion(Integer numHabitacion) {
		this.numHabitacion = numHabitacion;
	}

	public Integer getImporteIva() {
		return importeIva;
	}

	public void setImporteIva(Integer importeIva) {
		this.importeIva = importeIva;
	}

	public Integer getTotalFactura() {
		return totalFactura;
	}

	public void setTotalFactura(Integer totalFactura) {
		this.totalFactura = totalFactura;
	}

	public Integer getTIPOIVA() {
		return TIPOIVA;
	}
	
	
	
}
