package domain;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Entrenamiento {
	private List<String> tipo_entrenamiento;
	private Dificultad dificultad;
	private int tiempo;
	private int calorias_gastadas;
	private String descripcion;
	private int racha_entrenamiento;
	private String objetivo;
	private int tiempo_entrenado;
	private Date ultima_vez_entreno;
	
	public Entrenamiento(List<String> tipo_entrenamiento, Dificultad dificultad, int tiempo, int calorias_gastadas,
			String descripcion, int racha_entrenamiento, String objetivo, int tiempo_entrenado,
			Date ultima_vez_entreno) {
		super();
		this.tipo_entrenamiento = tipo_entrenamiento;
		this.dificultad = dificultad;
		this.tiempo = tiempo;
		this.calorias_gastadas = calorias_gastadas;
		this.descripcion = descripcion;
		this.racha_entrenamiento = racha_entrenamiento;
		this.objetivo = objetivo;
		this.tiempo_entrenado = tiempo_entrenado;
		this.ultima_vez_entreno = ultima_vez_entreno;
	}
	
	public Entrenamiento() {
		super();
		this.tipo_entrenamiento = null;
		this.dificultad = Dificultad.FACIL;
		this.tiempo = 0;
		this.calorias_gastadas = 0;
		this.descripcion = "";
		this.racha_entrenamiento = 0;
		this.objetivo = "";
		this.tiempo_entrenado = 0;
		this.ultima_vez_entreno = new Date();
	}

	
	public List<String> getTipo_entrenamiento() {
		return tipo_entrenamiento;
	}

	public void setTipo_entrenamiento(List<String> tipo_entrenamiento) {
		this.tipo_entrenamiento = tipo_entrenamiento;
	}

	public Dificultad getDificultad() {
		return dificultad;
	}

	public void setDificultad(Dificultad dificultad) {
		this.dificultad = dificultad;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	public int getCalorias_gastadas() {
		return calorias_gastadas;
	}

	public void setCalorias_gastadas(int calorias_gastadas) {
		this.calorias_gastadas = calorias_gastadas;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getRacha_entrenamiento() {
		return racha_entrenamiento;
	}

	public void setRacha_entrenamiento(int racha_entrenamiento) {
		this.racha_entrenamiento = racha_entrenamiento;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public int getTiempo_entrenado() {
		return tiempo_entrenado;
	}

	public void setTiempo_entrenado(int tiempo_entrenado) {
		this.tiempo_entrenado = tiempo_entrenado;
	}

	public Date getUltima_vez_entreno() {
		return ultima_vez_entreno;
	}

	public void setUltima_vez_entreno(Date ultima_vez_entreno) {
		if (ultima_vez_entreno != null) {
			this.ultima_vez_entreno = ultima_vez_entreno;
		}
	
	}

	@Override
	public String toString() {
		return "Entrenamiento [tipo_entrenamiento=" + tipo_entrenamiento + ", dificultad=" + dificultad + ", tiempo="
				+ tiempo + ", calorias_gastadas=" + calorias_gastadas + ", descripcion=" + descripcion
				+ ", racha_entrenamiento=" + racha_entrenamiento + ", objetivo=" + objetivo + ", tiempo_entrenado="
				+ tiempo_entrenado + ", ultima_vez_entreno=" + ultima_vez_entreno + "]";
	}
	
	
}
