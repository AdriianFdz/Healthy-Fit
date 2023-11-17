package guiTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import domain.Dieta;
import domain.TipoDificultad;
import gui.VentanaDieta;

public class TestVentanaDieta {

	Dieta dieta;
	VentanaDieta f;
	
	@Before
	public void setUp() {
		 dieta = new Dieta();
		 f = new VentanaDieta(dieta);
		 
	}
	
	
	 @Test
	    public void testNombreVentana() { 
	        assertEquals("Dieta", f.getTitle());
	    }

	    @Test
	    public void testEtiquetaNombre() {
	        assertEquals(" Ejemplo", f.labelNombre.getText());
	    }

	    @Test
	    public void testEtiquetaMinutos() {
	        assertEquals("30 minutos", f.labelMinutos.getText());
	    }

	    @Test
	    public void testEtiquetaDificultad() {
	    	dieta.setDificultad(TipoDificultad.FACIL);
		    f.labelDificultad.setText("Dificultad: 🔥");
		    assertEquals("Dificultad: 🔥", f.labelDificultad.getText());
	    	dieta.setDificultad(TipoDificultad.MEDIO);
	    	f.labelDificultad.setText("Dificultad: 🔥🔥");
	        assertEquals("Dificultad: 🔥🔥", f.labelDificultad.getText());
	        dieta.setDificultad(TipoDificultad.DIFICIL);
	        f.labelDificultad.setText("Dificultad: 🔥🔥🔥");
	        assertEquals("Dificultad: 🔥🔥🔥", f.labelDificultad.getText());
	      
	    }

	    @Test
	    public void testEtiquetaKcal() {
	        assertEquals("Kcal: 500", f.labelKcal.getText());
	    }

	    @Test
	    public void testAreaIngredientes() {
	    	 String ingredientesEsperados = "-Ingrediente1\n-Ingrediente2";
	         assertEquals(ingredientesEsperados, f.areaIngredientes.getText());
	    }

	    @Test
	    public void testAreaPasos() {
	    	String pasosEsperados = "-Paso1\n-Paso2";
	        assertEquals(pasosEsperados, f.areaPasos.getText());
	    }
	
}
