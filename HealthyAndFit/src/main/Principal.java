package main;



import javax.swing.SwingUtilities;

import gui.VentanaLogeoRegistro;
import io.RegistroLogger;

public class Principal {
	public static void main(String[] args) {
		
		RegistroLogger.cargarLogger();
		
		SwingUtilities.invokeLater(()-> new VentanaLogeoRegistro());
		
	}
}
