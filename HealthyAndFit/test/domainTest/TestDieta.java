package domainTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import domain.Dieta;
import domain.TipoDificultad;

public class TestDieta {
	
	private Dieta d;
	
	@Before
	public void SetUp() {
		 d = new Dieta("Pollo", 20, TipoDificultad.FACIL, 400, new ArrayList<String>(), new ArrayList<String>());
	}
	
	@Test
	public void TestGetNombre() {
		assertEquals("Pollo", d.getNombre());
		
	}
}
