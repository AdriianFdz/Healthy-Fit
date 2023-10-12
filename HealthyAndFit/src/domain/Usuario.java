package domain;

import java.util.Date;

public class Usuario {
	private String nombre;
	private String nombre_usuario;
	private String apellido1;
	private String apellido2;
	private Date fecha_nacimiento;
	private Sexo sexo;
	private double altura;
	private int peso;
	private Alergias alergias;
	private String correo_electronico;
	private Enfermedades enfermedades;
	private double imc;
	private Preferencia preferencia_alimenticia;
	
	public Usuario(String nombre, String nombre_usuario, String apellido1, String apellido2, Date fecha_nacimiento,
			Sexo sexo, double altura, int peso, Alergias alergias, String correo_electronico, Enfermedades enfermedades,
			double imc, Preferencia preferencia_alimenticia) {
		super();
		this.nombre = nombre;
		this.nombre_usuario = nombre_usuario;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.fecha_nacimiento = fecha_nacimiento;
		this.sexo = sexo;
		this.altura = altura;
		this.peso = peso;
		this.alergias = alergias;
		this.correo_electronico = correo_electronico;
		this.enfermedades = enfermedades;
		this.imc = peso / (altura * altura);
		this.preferencia_alimenticia = preferencia_alimenticia;
	}
	
	public Usuario() {
		super();
		this.nombre = "";
		this.nombre_usuario = "";
		this.apellido1 = "";
		this.apellido2 = "";
		this.fecha_nacimiento = new Date();
		this.sexo = Sexo.HOMBRE;
		this.altura = 0.0;
		this.peso = 0;
		this.alergias = Alergias.NINGUNA;
		this.correo_electronico = "";
		this.enfermedades = Enfermedades.NINGUNA;
		this.imc = 0.0;
		this.preferencia_alimenticia = Preferencia.NINGUNA;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre_usuario() {
		return nombre_usuario;
	}

	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(Date fecha_nacimiento) {
		if (fecha_nacimiento != null) {
			this.fecha_nacimiento = fecha_nacimiento;
		}
	
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public Alergias getAlergias() {
		return alergias;
	}

	public void setAlergias(Alergias alergias) {
		this.alergias = alergias;
	}

	public String getCorreo_electronico() {
		return correo_electronico;
	}

	public void setCorreo_electronico(String correo_electronico) {
		this.correo_electronico = correo_electronico;
	}

	public Enfermedades getEnfermedades() {
		return enfermedades;
	}

	public void setEnfermedades(Enfermedades enfermedades) {
		this.enfermedades = enfermedades;
	}

	public double getImc() {
		return imc;
	}

	public void setImc(double imc) {
		this.imc = imc;
	}

	public Preferencia getPreferencia_alimenticia() {
		return preferencia_alimenticia;
	}

	public void setPreferencia_alimenticia(Preferencia preferencia_alimenticia) {
		this.preferencia_alimenticia = preferencia_alimenticia;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", nombre_usuario=" + nombre_usuario + ", apellido1=" + apellido1
				+ ", apellido2=" + apellido2 + ", fecha_nacimiento=" + fecha_nacimiento + ", sexo=" + sexo + ", altura="
				+ altura + ", peso=" + peso + ", alergias=" + alergias + ", correo_electronico=" + correo_electronico
				+ ", enfermedades=" + enfermedades + ", imc=" + imc + "]";
	}
	
	
	
}
