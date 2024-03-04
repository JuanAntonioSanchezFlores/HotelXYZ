package app.modelo;

public class Persona {

	private Integer idPersona, telefono;
	
	private String nombre, apellidos,dni,domicilio, ciudad, provincia, email;
	public Persona(Integer idPersona,  String nombre, String apellidos, String dni, String domicilio,
			String ciudad, String provincia, String email, Integer telefono) {
		super();
		this.idPersona = idPersona;
		this.telefono = telefono;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.domicilio = domicilio;
		this.ciudad = ciudad;
		this.provincia = provincia;
		this.email = email;
	}
	
	public Persona(String nombre, String apellidos, String dni) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
	}
	
	public Persona() {}
	
	public Integer getIdPersona() {
		return idPersona;
	}
	public void setIdCliente(Integer idPersona) {
		this.idPersona = idPersona;
	}
	public Integer getTelefono() {
		return telefono;
	}
	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
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
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
