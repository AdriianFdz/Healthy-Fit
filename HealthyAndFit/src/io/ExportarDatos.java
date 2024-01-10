package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import domain.Entrenamiento;

public class ExportarDatos {
	private static File obtenerRutaFichero (String titulo, String extension) {
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setDialogTitle(titulo);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int dialog = fileChooser.showSaveDialog(null);
        if (dialog == JFileChooser.APPROVE_OPTION) {
        	if(!fileChooser.getSelectedFile().getAbsolutePath().endsWith(extension)){
        	    return new File(fileChooser.getSelectedFile() + ".csv");
        	}
        	
        	return fileChooser.getSelectedFile();
        }
        return null;
	}
	
	public static void exportarCombinaciones(List<Map<String, Entrenamiento>> combinaciones, String titulo) {
		File fichero = obtenerRutaFichero(titulo, ".csv");
		if (fichero != null) {
			try (BufferedWriter bf = new BufferedWriter(new FileWriter(fichero))){
				bf.write("Lunes;Martes;Miercoles;Jueves;Viernes;Sabado;Domingo\n"); 
				for (Map<String, Entrenamiento> mapa : combinaciones) {
					String linea = "";
					for (Entry<String, Entrenamiento> entry : mapa.entrySet()) {
						linea += entry.getValue().getNombre()+";";
					}
					linea = linea.substring(0, linea.length());
					linea += "\n";
					bf.write(linea);
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
        		RegistroLogger.anadirLogeo(Level.SEVERE, "Error al crear el archivo csv");
        		JOptionPane.showConfirmDialog(null, "Error al crear el archivo csv", "Error", JOptionPane.PLAIN_MESSAGE);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}

	public static void guardarHistorial(Map<LocalDateTime, Entrenamiento> map, String titulo) {

        File fichero = obtenerRutaFichero(titulo, ".csv");
        if (fichero != null) {			
        	try (BufferedWriter bf = new BufferedWriter(new FileWriter(fichero))){
        		for (Entry<LocalDateTime, Entrenamiento> entry : map.entrySet()) {
        			String s = entry.getKey().toString().substring(0, 16).replace("T", " / ");
        			bf.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s\n", entry.getValue().getNombre(),
        					entry.getValue().getDificultad(), s, entry.getValue().getCalorias(), entry.getValue().getDescripcion(),
        					entry.getValue().getTiempo(), entry.getValue().getSeries(), entry.getValue().getRepeticiones()));
        		}
        		bf.close();
        		JOptionPane.showMessageDialog(null, "Historial guardado correctamente");
        	} catch (FileNotFoundException e) {
        		e.printStackTrace();
        		RegistroLogger.anadirLogeo(Level.SEVERE, "Error al crear el archivo csv");
        		JOptionPane.showConfirmDialog(null, "Error al crear el archivo csv", "Error", JOptionPane.PLAIN_MESSAGE);
        	} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
	}
}
