package domain;

import java.util.ArrayList;
import java.util.List;

public class Dieta{
	private String nombre;
	private int tiempo;
	private TipoDificultad dificultad;
	private int kcal;
	private List<String> pasos;
	private List<String> ingredientes;
	private List<TipoAlergias> alergias;
	
	//Constructor con argumentos
	public Dieta(String nombre, int tiempo, TipoDificultad dificultad, int kcal, List<String> pasos,
			List<String> ingredientes, List<TipoAlergias> alergias) {
		super();
		this.nombre = nombre;
		this.tiempo = tiempo;
		this.dificultad = dificultad;
		this.kcal = kcal;
		this.pasos = new ArrayList<String>();
		for (String s : pasos) {
			this.pasos.add(s);
		}
		this.ingredientes = new ArrayList<String>();
		for (String s: ingredientes) {
			this.ingredientes.add(s);
		}
		
		this.alergias = new ArrayList<TipoAlergias>();
		for (TipoAlergias alergia : alergias) {
			this.alergias.add(alergia);
		}
	}

	//Constructor sin argumentos
	public Dieta() {
		super();
		this.nombre = "";
		this.tiempo = 0;
		this.dificultad = TipoDificultad.FACIL;
		this.kcal = 0;
		this.pasos = new ArrayList<String>();
		this.ingredientes = new ArrayList<String>();
		this.alergias = new ArrayList<TipoAlergias>();
	}
	//getters y setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		if (tiempo > 0) {
			this.tiempo = tiempo;
		}
		
	}

	public TipoDificultad getDificultad() {
		return dificultad;
	}

	public void setDificultad(TipoDificultad dificultad) {
		this.dificultad = dificultad;
	}

	public int getKcal() {
		return kcal;
	}

	public void setKcal(int kcal) {
		if (kcal > 0) {
			this.kcal = kcal;
		}
		
	}

	public List<String> getPasos() {
		return pasos;
	}

	public void setPasos(List<String> pasos) {
		this.pasos = pasos;
	}

	public List<String> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<String> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public List<TipoAlergias> getAlergias() {
		return alergias;
	}

	public void setAlergias(List<TipoAlergias> alergias) {
		this.alergias = alergias;
	}

	@Override
	public String toString() {
		return "Dieta [nombre=" + nombre + ", tiempo=" + tiempo + ", dificultad=" + dificultad + ", kcal=" + kcal
				+ ", pasos=" + pasos + ", ingredientes=" + ingredientes + ", alergias=" + alergias + "]";
	}
	
	
	
}
