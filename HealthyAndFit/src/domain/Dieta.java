package domain;

import java.util.List;

public class Dieta {
	private String nombre;
	private int calorias_consumidas;
	private String proxima_comida;
	private int vasos_de_agua;
	private int tiempo;
	private Dificultad dificultad;
	private int kcal;
	private List<String> pasos;
	private List<String> ingredientes;
	
	public Dieta(String nombre, int calorias_consumidas, String proxima_comida, int vasos_de_agua, int tiempo,
			Dificultad dificultad, int kcal, List<String> pasos, List<String> ingredientes) {
		super();
		this.nombre = nombre;
		this.calorias_consumidas = calorias_consumidas;
		this.proxima_comida = proxima_comida;
		this.vasos_de_agua = vasos_de_agua;
		this.tiempo = tiempo;
		this.dificultad = dificultad;
		this.kcal = kcal;
		this.pasos = pasos;
		this.ingredientes = ingredientes;
	}
	
	public Dieta() {
		super();
		this.nombre = "";
		this.calorias_consumidas = 0;
		this.proxima_comida = "";
		this.vasos_de_agua = 0;
		this.tiempo = 0;
		this.dificultad = Dificultad.FACIL;
		this.kcal = 0;
		this.pasos = null;
		this.ingredientes = null;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCalorias_consumidas() {
		return calorias_consumidas;
	}

	public void setCalorias_consumidas(int calorias_consumidas) {
		this.calorias_consumidas = calorias_consumidas;
	}

	public String getProxima_comida() {
		return proxima_comida;
	}

	public void setProxima_comida(String proxima_comida) {
		this.proxima_comida = proxima_comida;
	}

	public int getVasos_de_agua() {
		return vasos_de_agua;
	}

	public void setVasos_de_agua(int vasos_de_agua) {
		this.vasos_de_agua = vasos_de_agua;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	public Dificultad getDificultad() {
		return dificultad;
	}

	public void setDificultad(Dificultad dificultad) {
		this.dificultad = dificultad;
	}

	public int getKcal() {
		return kcal;
	}

	public void setKcal(int kcal) {
		this.kcal = kcal;
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

	@Override
	public String toString() {
		return "Dieta [nombre=" + nombre + ", calorias_consumidas=" + calorias_consumidas + ", proxima_comida="
				+ proxima_comida + ", vasos_de_agua=" + vasos_de_agua + ", tiempo=" + tiempo + ", dificultad="
				+ dificultad + ", kcal=" + kcal + ", pasos=" + pasos + ", ingredientes=" + ingredientes + "]";
	}
	
}
