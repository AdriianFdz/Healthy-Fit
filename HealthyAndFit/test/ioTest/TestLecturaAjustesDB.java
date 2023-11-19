package ioTest;



import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import io.LecturaAjustesDB;

public class TestLecturaAjustesDB {
	@Before
	public void setUp() {
		
	}
	
	
	@Test
	public void testResultado() {
		Map<String, String> resultadoEsperado = new HashMap<String, String>();
		resultadoEsperado.put("url", "jdbc:sqlite:src/db/HealthyAndFit.db");
		resultadoEsperado.put("autoCommit", "false");
		
		assertEquals(resultadoEsperado, LecturaAjustesDB.cargarAjustesDB());
		
	}
}
