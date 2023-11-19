package guiTest;

import org.junit.Before;
import org.junit.Test;


import domain.Usuario;

import gui.VentanaEntrenamiento;

public class TestVentanaEntrenamiento {
	Usuario u;
	VentanaEntrenamiento f;
	
	@Before
	public void setUp() {
		 u = new Usuario();
		 f = new VentanaEntrenamiento(u);
		 
	}
	
	
	@Test
	public void testBotonIniciar() {
	
	}
}
