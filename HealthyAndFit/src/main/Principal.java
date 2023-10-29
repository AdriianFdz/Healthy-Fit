package main;


import javax.swing.SwingUtilities;

import gui.VentanaLogeoRegistro;

public class Principal {
	public static void main(String[] args) {
		//Imagen sacada de www.dazn.com
		SwingUtilities.invokeLater(()-> new VentanaLogeoRegistro());
		
	}
}
