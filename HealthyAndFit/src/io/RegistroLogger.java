package io;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class RegistroLogger {
	private static Logger logger = Logger.getLogger(RegistroLogger.class.getName());
	
	public static void cargarLogger() {

			try {
				LogManager.getLogManager().readConfiguration(RegistroLogger.class.getClassLoader().getResourceAsStream("registro.properties"));
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
	
	public static Logger getLogger() {
		return logger;
	}

}
