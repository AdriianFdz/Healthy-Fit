package io;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

public class LecturaAjustesDB {
	
	
	public static Map<String, String> cargarAjustesDB() {
		Map<String, String> ajustesDB = new HashMap<String, String>();
		try (FileReader reader = new FileReader("conf/db.properties")){
			Properties propiedades = new Properties();
			propiedades.load(reader);
			
			String url = propiedades.getProperty("database");
			String autoCommit = propiedades.getProperty("setAutoCommit");
			
			ajustesDB.put("url", url);
			ajustesDB.put("autoCommit", autoCommit);
		} catch (IOException e1) {
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo leer el archivo db.properties");
		}
		
		return ajustesDB;		
	}
}
