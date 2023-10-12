package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Entrenamiento {
	private List<String> tipoEntrenamiento;
	private TipoDificultad dificultad;
	private int tiempo;
	private String descripcion;
	
	//Constructor con argumentos
	public Entrenamiento(List<String> tipoEntrenamiento, TipoDificultad dificultad, int tiempo, String descripcion) {
		super();
		this.tipoEntrenamiento = new ArrayList<String>();
		for (String s : tipoEntrenamiento) {
			this.tipoEntrenamiento.add(s);
		}
		this.dificultad = dificultad;
		this.tiempo = tiempo;
		this.descripcion = descripcion;
	}
	
	//Constructor sin argumentos
	public Entrenamiento() {
		super();
		this.tipoEntrenamiento = new ArrayList<String>();
		this.dificultad = TipoDificultad.FACIL;
		this.tiempo = 0;
		this.descripcion = "";
	}

	//getters y setters
	public List<String> gettipoEntrenamiento() {
		return tipoEntrenamiento;
	}

	public void settipoEntrenamiento(List<String> tipoEntrenamiento) {
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

	//Metodo toString
	@Override
	public String toString() {
		return "Entrenamiento [tipoEntrenamiento=" + tipoEntrenamiento + ", dificultad=" + dificultad + ", tiempo="
				+ tiempo + ", descripcion=" + descripcion + "]";
	}
	
	
}
