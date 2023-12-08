package guiTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;

import org.jfree.data.time.TimeSeriesCollection;
import org.junit.Before;
import org.junit.Test;

import domain.Dieta;
import domain.Entrenamiento;
import domain.TipoAlergias;
import domain.TipoEnfermedades;
import domain.TipoPermiso;
import domain.TipoPreferencia;
import domain.TipoSexo;
import domain.Usuario;

import gui.VentanaEntrenamiento;

public class TestVentanaEntrenamiento {
	Usuario usuario;
	VentanaEntrenamiento ventana;
	
	@Before
	public void setUp() {
		usuario = new Usuario("Juan", "juan_perez", "Perez", "Carbon", LocalDate.of(2004, 6, 10), TipoSexo.HOMBRE, 1.75, 75, new ArrayList<TipoAlergias>(Arrays.asList(TipoAlergias.HUEVOS)), "juan@gmail.com", new ArrayList<TipoEnfermedades>(Arrays.asList(TipoEnfermedades.CARDIOVASCULARES)), TipoPreferencia.NINGUNA, 12300, 3, "Ninguno", 300, LocalDate.now(), 13000, new Dieta().getNombre(), 3, "juan", new ImageIcon("resources\\images\\foto.png"), TipoPermiso.ADMINISTRADOR, new ArrayList<Entrenamiento>(), new ArrayList<Dieta>(), new TimeSeriesCollection(), new TimeSeriesCollection());	
		ventana = new VentanaEntrenamiento(usuario);
		 
	}
	
	
	@Test
	public void testBotonIniciar() {
		ventana.setEntrenamientoSeleccionado(ventana.getListaEntrenamientos().getModel().getElementAt(0));
		ventana.getBotonIniciar().doClick();
	}
}
