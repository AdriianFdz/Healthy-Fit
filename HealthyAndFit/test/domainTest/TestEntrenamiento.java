package domainTest;

import static org.junit.Assert.assertEquals;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;

import domain.Entrenamiento;
import domain.TipoDificultad;
import domain.TipoEntrenamiento;

public class TestEntrenamiento {
	Entrenamiento entrenamiento;
	Entrenamiento entrenamientoSinArgs;
	
	@Before
	public void setUp() {
		entrenamiento = new Entrenamiento("Abdominales", TipoEntrenamiento.SUPERIOR, TipoDificultad.MEDIO, 60, "Entrenamiento que mejorará los abdominales", 200, 3, 20, new ImageIcon("resources/images/foto.png"));
		entrenamientoSinArgs = new Entrenamiento();
	}
	
	@Test
	public void testGetNombre(){
		assertEquals("Abdominales", entrenamiento.getNombre());
	}
	@Test
	public void testSetNombre(){
		entrenamiento.setNombre("Pierna");
		assertEquals("Pierna", entrenamiento.getNombre());
	}
	@Test
	public void testGetCalorias(){
		assertEquals(200, entrenamiento.getCalorias());
	}
	@Test
	public void testSetCalorias(){
		entrenamiento.setCalorias(100);
		assertEquals(100, entrenamiento.getCalorias());
	}
	@Test
	public void testGetTipoEntrenamiento(){
		assertEquals(TipoEntrenamiento.SUPERIOR, entrenamiento.getTipoEntrenamiento());
	}
	@Test
	public void testSetTipoEntrenamiento(){
		entrenamiento.setTipoEntrenamiento(TipoEntrenamiento.INFERIOR);
		assertEquals(TipoEntrenamiento.INFERIOR, entrenamiento.getTipoEntrenamiento());
	}
	@Test
	public void testGetDificultad(){
		assertEquals(TipoDificultad.MEDIO, entrenamiento.getDificultad());
	}
	@Test
	public void testSetDificultad(){
		entrenamiento.setDificultad(TipoDificultad.FACIL);
		assertEquals(TipoDificultad.FACIL, entrenamiento.getDificultad());
	}
	@Test
	public void testGetTiempo(){
		assertEquals(60, entrenamiento.getTiempo());
	}
	@Test
	public void testSetTiempo(){
		entrenamiento.setTiempo(30);
		assertEquals(30, entrenamiento.getTiempo());
		entrenamiento.setTiempo(0);
		assertEquals(30, entrenamiento.getTiempo());
	}
	@Test
	public void testGetDescripcion(){
		assertEquals("Entrenamiento que mejorará los abdominales", entrenamiento.getDescripcion());
	}
	@Test
	public void testSetDescripcion(){
		entrenamiento.setDescripcion("Nueva descripcion");
		assertEquals("Nueva descripcion", entrenamiento.getDescripcion());
	}
	@Test
	public void testGetSeries(){
		assertEquals(3, entrenamiento.getSeries());
	}
	@Test
	public void testSetSeries(){
		entrenamiento.setSeries(5);
		assertEquals(5, entrenamiento.getSeries());
		entrenamiento.setSeries(-1);
		assertEquals(5, entrenamiento.getSeries());
	}
	@Test
	public void testGetRepeticiones(){
		assertEquals(20, entrenamiento.getRepeticiones());
	}
	@Test
	public void testSetRepeticiones(){
		entrenamiento.setRepeticiones(10);
		assertEquals(10, entrenamiento.getRepeticiones());
		entrenamiento.setRepeticiones(-1);
		assertEquals(10, entrenamiento.getRepeticiones());
	}
	@Test
	public void testGetFoto() {
		assertEquals(new ImageIcon("resources/images/foto.png").getImage(), entrenamiento.getFoto().getImage());
	}
	
	@Test
	public void testSetFoto() {
		entrenamiento.setFoto(new ImageIcon("resources/images/preparacion.jpg"));
		assertEquals(new ImageIcon("resources/images/preparacion.jpg").getImage(), entrenamiento.getFoto().getImage());
	}
	
	@Test
	public void testToString(){
		assertEquals("Entrenamiento [nombre=Abdominales, tipoEntrenamiento=SUPERIOR, dificultad=MEDIO, tiempo=60, descripcion=Entrenamiento que mejorará los abdominales, calorias=200, series=3, repeticiones=20]", entrenamiento.toString());
	}
	
	
}
