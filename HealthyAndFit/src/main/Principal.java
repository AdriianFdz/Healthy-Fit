package main;

import javax.swing.SwingUtilities;
import gui.VentanaResumen;

public class Principal {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> new VentanaResumen());
	}
}
