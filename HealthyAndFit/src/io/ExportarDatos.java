package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFileChooser;

import domain.Entrenamiento;

public class ExportarDatos {
	public static File obtenerRutaFichero () {
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setDialogTitle("Guardar combinaciones en...");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int dialog = fileChooser.showSaveDialog(null);
        if (dialog == JFileChooser.APPROVE_OPTION) {
        	return fileChooser.getSelectedFile();
        }
        return null;
	}
	
	public static void exportarCombinaciones(List<Map<String, Entrenamiento>> combinaciones) {
		File fichero = obtenerRutaFichero();
		if (fichero != null) {
			try {
				PrintWriter pw = new PrintWriter(fichero.getAbsolutePath()+"\\Combinaciones de entrenamientos.csv");
				pw.write("Lunes;Martes;Miercoles;Jueves;Viernes;Sabado;Domingo\n"); 
				for (Map<String, Entrenamiento> mapa : combinaciones) {
					String linea = "";
					for (Entry<String, Entrenamiento> entry : mapa.entrySet()) {
						linea += entry.getValue().getNombre()+";";
					}
					linea = linea.substring(0, linea.length());
					linea += "\n";
					pw.write(linea);
				}
				pw.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
	}
}
