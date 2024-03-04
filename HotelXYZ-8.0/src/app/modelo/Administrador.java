package app.modelo;

public class Administrador extends Persona{

	private Integer idAdmin;
	private String idPersonaAdmin;
	private String usuario;
	private String contrasenia;
	
	public Administrador(Integer idPersona, String nombre, String apellidos, String dni, String domicilio,
			String ciudad, String provincia, String email, Integer telefono, Integer idAdmin, String idPersonaAdmin,
			String usuario, String contrasenia) {
		super(idPersona, nombre, apellidos, dni, domicilio, ciudad, provincia, email, telefono);
		this.idAdmin = idAdmin;
		this.idPersonaAdmin = idPersonaAdmin;
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}

	public Integer getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(Integer idAdmin) {
		this.idAdmin = idAdmin;
	}

	public String getIdPersonaAdmin() {
		return idPersonaAdmin;
	}

	public void setIdPersonaAdmin(String idPersonaAdmin) {
		this.idPersonaAdmin = idPersonaAdmin;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	
	
	
	
}
