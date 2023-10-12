package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {
	private String nombre;
	private String nombreUsuario;
	private String apellido1;
	private String apellido2;
	private Date fechaNacimiento;
	private TipoSexo sexo;
	private double altura;
	private int peso;
	private List<TipoAlergias> alergias;
	private String correoElectronico;
	private List<TipoEnfermedades> enfermedades;
	private double imc;
	private TipoPreferencia preferenciaAlimenticia;
	private int caloriasGastadas;
	private int rachaEntrenamiento;
	private String objetivo;
	private int tiempoEntrenado;
	private Date ultimaVezEntreno;
	private int caloriasConsumidas;
	private String proximaComida;
	private int vasosDeAgua;
	
	//Constructor con argumentos
	public Usuario(String nombre, String nombreUsuario, String apellido1, String apellido2, Date fechaNacimiento,
			TipoSexo sexo, double altura, int peso, List<TipoAlergias> alergias, String correoElectronico,
			List<TipoEnfermedades> enfermedades, TipoPreferencia preferenciaAlimenticia, int caloriasGastadas,
			int rachaEntrenamiento, String objetivo, int tiempoEntrenado, Date ultimaVezEntreno,
			int caloriasConsumidas, String proximaComida, int vasosDeAgua) {
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
		this.preferenciaAlimenticia = preferenciaAlimenticia;
		this.caloriasGastadas = caloriasGastadas;
		this.rachaEntrenamiento = rachaEntrenamiento;
		this.objetivo = objetivo;
		this.tiempoEntrenado = tiempoEntrenado;
		this.ultimaVezEntreno = ultimaVezEntreno;
		this.caloriasConsumidas = caloriasConsumidas;
		this.proximaComida = proximaComida;
		this.vasosDeAgua = vasosDeAgua;
	}
	
	//Constructor sin argumentos
	public Usuario() {
		super();
		this.nombre = "";
		this.nombreUsuario = "";
		this.apellido1 = "";
		this.apellido2 = "";
		this.fechaNacimiento = new Date();
		this.sexo = TipoSexo.OTRO;
		this.altura = 0.0;
		this.peso = 0;
		this.alergias = new ArrayList<TipoAlergias>();
		this.correoElectronico = "";
		this.enfermedades = new ArrayList<TipoEnfermedades>();
		this.imc = 0.0;
		this.preferenciaAlimenticia = TipoPreferencia.NINGUNA;
		this.caloriasGastadas = 0;
		this.rachaEntrenamiento = 0;
		this.objetivo = "";
		this.tiempoEntrenado = 0;
		this.ultimaVezEntreno = new Date();
		this.caloriasConsumidas = 0;
		this.proximaComida = "";
		this.vasosDeAgua = 0;
	}

	//getters y setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getnombreUsuario() {
		return nombreUsuario;
	}

	public void setnombreUsuario(String nombreUsuario) {
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

	public Date getfechaNacimiento() {
		return fechaNacimiento;
	}

	public void setfechaNacimiento(Date fechaNacimiento) {
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

	public String getcorreoElectronico() {
		return correoElectronico;
	}

	public void setcorreoElectronico(String correoElectronico) {
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

	public TipoPreferencia getpreferenciaAlimenticia() {
		return preferenciaAlimenticia;
	}

	public void setpreferenciaAlimenticia(TipoPreferencia preferenciaAlimenticia) {
		this.preferenciaAlimenticia = preferenciaAlimenticia;
	}

	public int getcaloriasGastadas() {
		return caloriasGastadas;
	}

	public void setcaloriasGastadas(int caloriasGastadas) {
		if (caloriasGastadas >= 0) {
			this.caloriasGastadas = caloriasGastadas;
		}
		
	}

	public int getrachaEntrenamiento() {
		return rachaEntrenamiento;
	}

	public void setrachaEntrenamiento(int rachaEntrenamiento) {
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

	public int gettiempoEntrenado() {
		return tiempoEntrenado;
	}

	public void settiempoEntrenado(int tiempoEntrenado) {
		if (tiempoEntrenado >= 0) {
			this.tiempoEntrenado = tiempoEntrenado;
		}
		
	}

	public Date getultimaVezEntreno() {
		return ultimaVezEntreno;
	}

	public void setultimaVezEntreno(Date ultimaVezEntreno) {
		this.ultimaVezEntreno = ultimaVezEntreno;
	}

	public int getcaloriasConsumidas() {
		return caloriasConsumidas;
	}

	public void setcaloriasConsumidas(int caloriasConsumidas) {
		if (caloriasConsumidas >= 0) {
			this.caloriasConsumidas = caloriasConsumidas;
		}
		
	}

	public String getproximaComida() {
		return proximaComida;
	}

	public void setproximaComida(String proximaComida) {
		this.proximaComida = proximaComida;
	}

	public int getvasosDeAgua() {
		return vasosDeAgua;
	}

	public void setvasosDeAgua(int vasosDeAgua) {
		if (vasosDeAgua >= 0 && vasosDeAgua < 8)
		this.vasosDeAgua = vasosDeAgua;
	}

	//Metodo toString
	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", nombreUsuario=" + nombreUsuario + ", apellido1=" + apellido1
				+ ", apellido2=" + apellido2 + ", fechaNacimiento=" + fechaNacimiento + ", sexo=" + sexo + ", altura="
				+ altura + ", peso=" + peso + ", alergias=" + alergias + ", correoElectronico=" + correoElectronico
				+ ", enfermedades=" + enfermedades + ", imc=" + imc + ", preferenciaAlimenticia="
				+ preferenciaAlimenticia + ", caloriasGastadas=" + caloriasGastadas + ", rachaEntrenamiento="
				+ rachaEntrenamiento + ", objetivo=" + objetivo + ", tiempoEntrenado=" + tiempoEntrenado
				+ ", ultimaVezEntreno=" + ultimaVezEntreno + ", caloriasConsumidas=" + caloriasConsumidas
				+ ", proximaComida=" + proximaComida + ", vasosDeAgua=" + vasosDeAgua + "]";
	}
	
	
	
}
