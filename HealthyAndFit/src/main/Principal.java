package main;

import java.util.Date;

import javax.swing.SwingUtilities;

import domain.Preferencia;
import domain.Sexo;
import domain.Usuario;
import gui.VentanaResumen;

public class Principal {
	
	Usuario persona;
	public static void main(String[] args) {
		
		Usuario p = new Usuario("Juan", "juan_mart", "Martinez", "Perez", new Date(), Sexo.HOMBRE, 1.73, 75, null, "juan@gmail.com", null, 13, Preferencia.NINGUNA);
		SwingUtilities.invokeLater(()-> new VentanaResumen(p));
	}
}
