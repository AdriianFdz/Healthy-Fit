package guiTest;

import static org.junit.Assert.assertEquals;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeriesCollection;
import org.junit.Before;
import org.junit.Test;

import domain.Dieta;
import domain.TipoAlergias;
import domain.TipoEnfermedades;
import domain.TipoPermiso;
import domain.TipoPreferencia;
import domain.TipoSexo;
import domain.Usuario;
import gui.VentanaResumen;


public class TestVentanaResumen {
	Usuario usuario;
	VentanaResumen ventana;
	@Before
	public void setUp() {
		usuario = new Usuario("Juan", "juan_perez", "Perez", "Carbon", LocalDate.of(2004, 6, 10), TipoSexo.HOMBRE, 1.75, 75, new ArrayList<TipoAlergias>(Arrays.asList(TipoAlergias.HUEVOS)), "juan@gmail.com", new ArrayList<TipoEnfermedades>(Arrays.asList(TipoEnfermedades.CARDIOVASCULARES)), TipoPreferencia.NINGUNA, 12300, 3, "Ninguno", 300, LocalDate.now(), 13000, new Dieta().getNombre(), 3, "juan", new ImageIcon("resources\\images\\foto.png"), TipoPermiso.ADMINISTRADOR);
		ventana = new VentanaResumen(usuario);
	}

	@Test
	public void testCrearGrafica() {
		TimeSeriesCollection ejemploDS = ventana.crearDatasetEjemplo("test");
		JFreeChart grafica = ventana.crearGrafica("test", "mes", "calorias", ejemploDS);
		
		assertEquals(ChartFactory.createTimeSeriesChart("test", "mes", "calorias", ejemploDS),grafica);
		
	}
	
	@Test
	public void testBotones() {
		ventana.getBotonEntrenar().doClick();
		ventana.getBotonDieta().doClick();
		ventana.getFotoPerfil().doClick();
	}
	
	
}
