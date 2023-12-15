package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import org.jfree.data.general.Dataset;
import org.jfree.data.time.TimeSeriesCollection;

public class Usuario {
	private String nombre;
	private String nombreUsuario;
	private String apellido1;
	private String apellido2;
	private LocalDate fechaNacimiento;
	private TipoSexo sexo;
	private double altura;
	private int peso;
	private List<TipoAlergias> alergias;
	private String correoElectronico;
	private List<TipoEnfermedades> enfermedades;
	private double imc;
	private int caloriasGastadas;
	private int rachaEntrenamiento;
	private String objetivo;
	private int tiempoEntrenado;
	private LocalDate ultimaVezEntreno;
	private int caloriasConsumidas;
	private Map<LocalDate, Dieta> proximaComida;
	private int vasosDeAgua;
	private String contrasena;
	private ImageIcon foto;
	private TipoPermiso permiso;
	private List<Entrenamiento> registroEntrenamiento;
	private List<Dieta> registroDietas;
	
	//Constructor con argumentos
	public Usuario(String nombre, String nombreUsuario, String apellido1, String apellido2, LocalDate fechaNacimiento,
			TipoSexo sexo, double altura, int peso, List<TipoAlergias> alergias, String correoElectronico,
			List<TipoEnfermedades> enfermedades, int caloriasGastadas,
			int rachaEntrenamiento, String objetivo, int tiempoEntrenado, LocalDate ultimaVezEntreno,
			int caloriasConsumidas, Map<LocalDate, Dieta> proximaComida, int vasosDeAgua, String contraseña, ImageIcon foto, TipoPermiso permiso,
			List<Entrenamiento> registroEntrenamiento, List<Dieta> dietas) {
		super();
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.fechaNacimiento = fechaNacimiento;
		this.sexo = sexo;
		this.altura = altura;
		this.peso = peso;
		this.alergias = new ArrayList<TipoAlergias>();
		for (TipoAlergias a : alergias) {
			this.alergias.add(a);
		}
		this.correoElectronico = correoElectronico;
		this.enfermedades = new ArrayList<TipoEnfermedades>();
		for (TipoEnfermedades e : enfermedades) {
			this.enfermedades.add(e);
		}
		this.imc = peso / (altura * altura);
		this.caloriasGastadas = caloriasGastadas;
		this.rachaEntrenamiento = rachaEntrenamiento;
		this.objetivo = objetivo;
		this.tiempoEntrenado = tiempoEntrenado;
		this.ultimaVezEntreno = ultimaVezEntreno;
		this.caloriasConsumidas = caloriasConsumidas;
		this.proximaComida = new HashMap<LocalDate, Dieta>(proximaComida);
		this.vasosDeAgua = vasosDeAgua;
		this.foto = foto;
		this.contrasena = contraseña;
		this.permiso = permiso;
		this.registroEntrenamiento = new ArrayList<Entrenamiento>();
		for (Entrenamiento e : registroEntrenamiento) {
			this.registroEntrenamiento.add(e);
		}
		this.registroDietas = new ArrayList<Dieta>();
		for (Dieta d : registroDietas) {
			this.registroDietas.add(d);
		}

	}
	
	//Constructor sin argumentos
	public Usuario() {
		super();
		this.nombre = "";
		this.nombreUsuario = "";
		this.apellido1 = "";
		this.apellido2 = "";
		this.fechaNacimiento = LocalDate.now();
		this.sexo = TipoSexo.OTRO;
		this.altura = 0.0;
		this.peso = 0;
		this.alergias = new ArrayList<TipoAlergias>();
		this.correoElectronico = "";
		this.enfermedades = new ArrayList<TipoEnfermedades>();
		this.imc = 0.0;
		this.caloriasGastadas = 0;
		this.rachaEntrenamiento = 0;
		this.objetivo = "";
		this.tiempoEntrenado = 0;
		this.ultimaVezEntreno = LocalDate.now();
		this.caloriasConsumidas = 0;
		this.proximaComida = new HashMap<LocalDate, Dieta>();
		this.vasosDeAgua = 0;
		this.contrasena = "";
		this.foto = new ImageIcon("resources\\images\\foto.png");
		this.permiso = TipoPermiso.USUARIO;
		this.registroEntrenamiento = new ArrayList<Entrenamiento>();
		this.registroDietas = new ArrayList<Dieta>();

	}
	
	//getters y setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
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

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public TipoSexo getSexo() {
		return sexo;
	}

	public void setSexo(TipoSexo sexo) {
		this.sexo = sexo;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		if (altura > 0) {
			this.altura = altura;
		}
		
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		if (peso > 0) {
			this.peso = peso;
		}
	
	}

	public List<TipoAlergias> getAlergias() {
		return alergias;
	}

	public void setAlergias(List<TipoAlergias> alergias) {
		this.alergias = alergias;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public List<TipoEnfermedades> getEnfermedades() {
		return enfermedades;
	}

	public void setEnfermedades(List<TipoEnfermedades> enfermedades) {
		this.enfermedades = enfermedades;
	}

	public double getImc() {
		return imc;
	}

	public int getCaloriasGastadas() {
		return caloriasGastadas;
	}

	public void setCaloriasGastadas(int caloriasGastadas) {
		if (caloriasGastadas >= 0) {
			this.caloriasGastadas = caloriasGastadas;
		}
		
	}

	public int getRachaEntrenamiento() {
		return rachaEntrenamiento;
	}

	public void setRachaEntrenamiento(int rachaEntrenamiento) {
		if (rachaEntrenamiento >= 0) {
			this.rachaEntrenamiento = rachaEntrenamiento;
		}
		
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public int getTiempoEntrenado() {
		return tiempoEntrenado;
	}

	public void setTiempoEntrenado(int tiempoEntrenado) {
		if (tiempoEntrenado >= 0) {
			this.tiempoEntrenado = tiempoEntrenado;
		}
		
	}

	public LocalDate getUltimaVezEntreno() {
		return ultimaVezEntreno;
	}

	public void setUltimaVezEntreno(LocalDate ultimaVezEntreno) {
		this.ultimaVezEntreno = ultimaVezEntreno;
	}

	public int getCaloriasConsumidas() {
		return caloriasConsumidas;
	}

	public void setCaloriasConsumidas(int caloriasConsumidas) {
		if (caloriasConsumidas >= 0) {
			this.caloriasConsumidas = caloriasConsumidas;
		}
		
	}

	public Map<LocalDate, Dieta> getProximaComida() {
		return proximaComida;
	}

	public void setProximaComida(Map<LocalDate, Dieta> proximaComida) {
		this.proximaComida = proximaComida;
	}

	public int getVasosDeAgua() {
		return vasosDeAgua;
	}

	public void setVasosDeAgua(int vasosDeAgua) {
		if (vasosDeAgua >= 0 && vasosDeAgua <= 8)
		this.vasosDeAgua = vasosDeAgua;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		if (contrasena.length() > 0) {
			this.contrasena = contrasena;
		}
	
	}

	public ImageIcon getFoto() {
		return foto;
	}

	public void setFoto(ImageIcon foto) {
		this.foto = foto;
	}
	
	
	public TipoPermiso getPermiso() {
		return permiso;
	}

	public void setPermiso(TipoPermiso permiso) {
		this.permiso = permiso;
	}

	
	public List<Entrenamiento> getRegistroEntrenamiento() {
		return registroEntrenamiento;
	}

	public void setRegistroEntrenamiento(List<Entrenamiento> registroEntrenamiento) {
		this.registroEntrenamiento = registroEntrenamiento;
	}

	public List<Dieta> getRegistroDietas() {
		return registroDietas;
	}

	public void setRegistroDietas(List<Dieta> registroDietas) {
		this.registroDietas = registroDietas;
	}


	//Metodo toString
	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", nombreUsuario=" + nombreUsuario + ", apellido1=" + apellido1
				+ ", apellido2=" + apellido2 + ", fechaNacimiento=" + fechaNacimiento + ", sexo=" + sexo + ", altura="
				+ altura + ", peso=" + peso + ", alergias=" + alergias + ", correoElectronico=" + correoElectronico
				+ ", enfermedades=" + enfermedades + ", imc=" + imc
				+ ", caloriasGastadas=" + caloriasGastadas + ", rachaEntrenamiento="
				+ rachaEntrenamiento + ", objetivo=" + objetivo + ", tiempoEntrenado=" + tiempoEntrenado
				+ ", ultimaVezEntreno=" + ultimaVezEntreno + ", caloriasConsumidas=" + caloriasConsumidas
				+ ", proximaComida=" + proximaComida + ", vasosDeAgua=" + vasosDeAgua + ", contrasena=" + contrasena
				+ ", foto=" + foto + ", permiso=" + permiso + ", registroEntrenamiento=" + registroEntrenamiento
				+ ", registroDietas=" + registroDietas + "]";
	}

	
}
