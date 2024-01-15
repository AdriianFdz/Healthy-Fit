package ioTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.RegistroLogger;

public class RegistroLoggerTest {
	
    private ByteArrayOutputStream salidaContenido = new ByteArrayOutputStream();
    private PrintStream salidaOrigial = System.out;
	
	
	@Before
	public void setUp() {
		System.setOut(new PrintStream(salidaContenido));
	}
	
	@After
	public void tearDown() {
		System.setOut(salidaOrigial);
	}
	
	
	@Test
	public void testCargarLogger() {
		try {
			RegistroLogger.cargarLogger();
			//Si no hay ninguna excepcion, se ha cargado la configuracion correctamente
			assertTrue(true);
			
		} catch (Exception e) {
			//Si hay alguna excepcion, el test falla
			fail();
		}
	}
	
	@Test
	public void testGetLogger() {
		assertEquals(Logger.getLogger(RegistroLogger.class.getName()), RegistroLogger.getLogger());
		
	}
	
	
}
