package app.modelo;



public class Cliente extends Persona{
	
	private Integer idCliente;
	private Integer idPersonaCliente;
	private String numCuenta;
	private String formaPago;
	
	public Cliente(Integer idPersona, String nombre, String apellidos, String dni, String domicilio, String ciudad,
			String provincia, String email, Integer telefono, Integer idCliente, Integer idPersonaCliente, String numCuenta,
			String formaPago) {
		super(idPersona, nombre, apellidos, dni, domicilio, ciudad, provincia, email, telefono);
		this.idCliente = idCliente;
		this.idPersonaCliente = idPersonaCliente;
		this.numCuenta = numCuenta;
		this.formaPago = formaPago;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public Integer getIdPersonaCliente() {
		return idPersonaCliente;
	}
	public void setIdPersonaCliente(Integer idPersona) {
		this.idPersonaCliente = idPersona;
	}
	public String getNumCuenta() {
		return numCuenta;
	}
	public void setNumCuenta(String numCuenta) {
		this.numCuenta = numCuenta;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	
	
	


	
	
	
}
