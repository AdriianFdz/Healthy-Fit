package guiTest;

import org.junit.Before;



import domain.Usuario;

import gui.VentanaPanel;

public class TestVentanaPanel {

	VentanaPanel p;
	Usuario u;
	
	@Before
	public void setUp() {
		 u = new Usuario();
		 p = new VentanaPanel(u);
		 
	}

	
}
