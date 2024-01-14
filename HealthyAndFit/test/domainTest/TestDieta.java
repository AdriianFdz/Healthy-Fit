package domainTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import domain.Dieta;
import domain.TipoAlergias;
import domain.TipoDificultad;

public class TestDieta {
	
	Dieta dieta;
	Dieta dietaSinArgs;
	
	@Before
	public void setUp() {
		dieta = new Dieta("Pollo", 20, TipoDificultad.FACIL, 400, new ArrayList<String>(Arrays.asList("1.Freir huevo...")), new ArrayList<String>(Arrays.asList("1.Tomate...")), new ArrayList<TipoAlergias>(Arrays.asList(TipoAlergias.GLUTEN)));
		dietaSinArgs = new Dieta();
	}

	@Test
	public void testGetNombre() {
		assertEquals("Pollo", dieta.getNombre());
		
	}
	
	@Test
	public void testSetNombre() {
		dieta.setNombre("Oier");
		assertEquals("Oier", dieta.getNombre());
	}
	
	@Test
	public void testGetTiempo() {
		assertEquals(20, dieta.getTiempo());
	}
	
	@Test
	public void testSetTiempo() {
		dieta.setTiempo(40);
		assertEquals(40, dieta.getTiempo());
		dieta.setTiempo(-1);
		assertEquals(40, dieta.getTiempo());
	}
	
	@Test
	public void testGetDificultad() {
		assertEquals(TipoDificultad.FACIL, dieta.getDificultad());
	}
	
	@Test
	public void testSetDificultad() {
		dieta.setDificultad(TipoDificultad.MEDIO);
		assertEquals(TipoDificultad.MEDIO, dieta.getDificultad());
	}
	
	@Test
	public void testGetKcal() {
		assertEquals(400, dieta.getKcal());
	}
	
	@Test
	public void testSetKcal() {
		dieta.setKcal(400);
		assertEquals(400, dieta.getKcal());
		dieta.setKcal(-1);
		assertEquals(400, dieta.getKcal());
	}
	
	@Test
	public void testGetPasos() {
		assertEquals(Arrays.asList("1.Freir huevo..."), dieta.getPasos());
	}
	
	@Test
	public void testSetPasos() {
		dieta.setPasos(Arrays.asList("1.Freir el huevo","2.Servir con tomate"));
		assertEquals(Arrays.asList("1.Freir el huevo","2.Servir con tomate"), dieta.getPasos());
	}
	
	@Test
	public void testGetIngredientes() {
		assertEquals(Arrays.asList("1.Tomate..."), dieta.getIngredientes());
	}
	
	@Test
	public void testSetIngredientes() {
		dieta.setIngredientes(Arrays.asList("1.Huevo","2.Tomate"));
		assertEquals(Arrays.asList("1.Huevo","2.Tomate"), dieta.getIngredientes());
	}
	
	@Test
	public void testGetAlergias() {
		assertEquals(Arrays.asList(TipoAlergias.GLUTEN), dieta.getAlergias());
	}

	@Test
	public void testSetAlergias() {
		dieta.setAlergias(Arrays.asList(TipoAlergias.APIO, TipoAlergias.CACAHUETES));
		assertEquals(Arrays.asList(TipoAlergias.APIO, TipoAlergias.CACAHUETES), dieta.getAlergias());
	}
	
	@Test
	public void testToString() {
		assertEquals("Dieta [nombre=Pollo, tiempo=20, dificultad=FACIL, kcal=400, pasos=[1.Freir huevo...], ingredientes=[1.Tomate...], alergias=[GLUTEN]]", dieta.toString());
		
	}
}
