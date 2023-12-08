package io;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class RegistroLogger {
	private static Logger logger = Logger.getLogger(RegistroLogger.class.getName());
	
	public static void cargarLogger() {
		try (FileInputStream fis = new FileInputStream("conf/registro.properties")){
			LogManager.getLogManager().readConfiguration(fis);
		}catch (IOException e1) {
			logger.log(Level.SEVERE, "Lectura del fichero inicsesion.properties fallida.");
			JOptionPane.showConfirmDialog(null, "Error en la lectura del fichero", "Error", JOptionPane.PLAIN_MESSAGE);
		}
	}
	public static void anadirLogeo(Level nivel, String texto) {
		logger.log(nivel, texto);
	}

}
