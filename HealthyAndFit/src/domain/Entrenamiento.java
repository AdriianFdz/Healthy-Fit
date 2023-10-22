
package domain;


public class Entrenamiento {
	private String nombre;
	private TipoEntrenamiento tipoEntrenamiento;
	private TipoDificultad dificultad;
	private int tiempo;
	private String descripcion;
	private int calorias;
	private int series;
	private int repeticiones;
	
	//Constructor con argumentos
	public Entrenamiento(String nombre, TipoEntrenamiento tipoEntrenamiento, TipoDificultad dificultad, int tiempo, String descripcion, int calorias, int series, int repeticiones) {
		super();
		this.nombre = nombre;
		this.tipoEntrenamiento = tipoEntrenamiento;
		this.dificultad = dificultad;
		this.tiempo = tiempo;
		this.descripcion = descripcion;
		this.calorias = calorias;
		this.series = series;
		this.repeticiones = repeticiones;
		
	}
	
	//Constructor sin argumentos
	public Entrenamiento() {
		super();
		
		this.nombre = "Nombre por defecto";
		this.tipoEntrenamiento = TipoEntrenamiento.INFERIOR;
		this.dificultad = TipoDificultad.FACIL;
		this.tiempo = 0;
		this.descripcion = "";
		this.calorias = 0;
		this.series = 0;
		this.repeticiones = 0;
	}

	//getters y setters


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getCalorias() {
		return calorias;
	}

	public void setCalorias(int calorias) {
		this.calorias = calorias;
	}


	public TipoEntrenamiento getTipoEntrenamiento() {
		return tipoEntrenamiento;
	}

	public void setTipoEntrenamiento(TipoEntrenamiento tipoEntrenamiento) {
		this.tipoEntrenamiento = tipoEntrenamiento;
	}

	public TipoDificultad getDificultad() {
		return dificultad;
	}

	public void setDificultad(TipoDificultad dificultad) {
		this.dificultad = dificultad;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		if (tiempo > 0) {
			this.tiempo = tiempo;

		}
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	public int getSeries() {
		return series;
	}

	public void setSeries(int series) {
		if (series >= 0) {
			this.series = series;
		}
		
	}

	public int getRepeticiones() {
		return repeticiones;
	}

	public void setRepeticiones(int repeticiones) {
		if (repeticiones >= 0) {
			this.repeticiones = repeticiones;
		}
	
	}

	@Override
	public String toString() {
		return "Entrenamiento [nombre=" + nombre + ", tipoEntrenamiento=" + tipoEntrenamiento + ", dificultad="
				+ dificultad + ", tiempo=" + tiempo + ", descripcion=" + descripcion + ", calorias=" + calorias
				+ ", series=" + series + ", repeticiones=" + repeticiones + "]";
	}

}
