
package domain;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import io.RegistroLogger;

public class Entrenamiento implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private TipoEntrenamiento tipoEntrenamiento;
	private TipoDificultad dificultad;
	private int tiempo;
	private String descripcion;
	private int calorias;
	private int series;
	private int repeticiones;
	private ImageIcon foto; 
	
	//Constructor con argumentos
	public Entrenamiento(String nombre, TipoEntrenamiento tipoEntrenamiento, TipoDificultad dificultad, int tiempo, String descripcion, int calorias, int series, int repeticiones, ImageIcon foto) {
		super();
		this.nombre = nombre;
		this.tipoEntrenamiento = tipoEntrenamiento;
		this.dificultad = dificultad;
		this.tiempo = tiempo;
		this.descripcion = descripcion;
		this.calorias = calorias;
		this.series = series;
		this.repeticiones = repeticiones;
		this.foto = foto;
		
	}
	
	//Constructor sin argumentos
	public Entrenamiento() {
		super();
		
		this.nombre = "";
		this.tipoEntrenamiento = TipoEntrenamiento.INFERIOR;
		this.dificultad = TipoDificultad.FACIL;
		this.tiempo = 0;
		this.descripcion = "";
		this.calorias = 0;
		this.series = 0;
		this.repeticiones = 0;
		try {
			this.foto = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/foto.png")));
		} catch (IOException e) {
			e.printStackTrace();
			RegistroLogger.getLogger().log(Level.SEVERE, "Error al cargar imagenes");
			JOptionPane.showConfirmDialog(null, "Error al cargar imagenes", "Error", JOptionPane.PLAIN_MESSAGE);
		}
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

	public ImageIcon getFoto() {
		return foto;
	}

	public void setFoto(ImageIcon foto) {
		this.foto = foto;
	}

	@Override
	public String toString() {
		return "Entrenamiento [nombre=" + nombre + ", tipoEntrenamiento=" + tipoEntrenamiento + ", dificultad="
				+ dificultad + ", tiempo=" + tiempo + ", descripcion=" + descripcion + ", calorias=" + calorias
				+ ", series=" + series + ", repeticiones=" + repeticiones + "]";
	}

}
