package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import db.DBManager;
import domain.Dieta;
import domain.Entrenamiento;
import domain.Usuario;

public class ExportarDatos {
	private static File obtenerRutaFichero (String titulo, String extension) {
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setDialogTitle(titulo);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int dialog = fileChooser.showSaveDialog(null);
        if (dialog == JFileChooser.APPROVE_OPTION) {
        	if(!fileChooser.getSelectedFile().getAbsolutePath().endsWith(extension)){
        	    return new File(fileChooser.getSelectedFile() + extension);
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
				e1.printStackTrace();
				RegistroLogger.anadirLogeo(Level.SEVERE, "Error al guardar los datos");
				JOptionPane.showConfirmDialog(null, "Error al guardar los datos", "Error", JOptionPane.PLAIN_MESSAGE);
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
				e1.printStackTrace();
				RegistroLogger.anadirLogeo(Level.SEVERE, "Error al guardar los datos del historial");
				JOptionPane.showConfirmDialog(null, "Error al guardar los datos del historial", "Error", JOptionPane.PLAIN_MESSAGE);
			}
        }
	}
	
	public static void exportarFicheroUsuario() {
		Connection conn = DBManager.obtenerConexion();
		List<Usuario> listaUsuarios = DBManager.obtenerUsuarios(conn);
		File file = obtenerRutaFichero("Guardar archivo en...", ".dat");
		
		if (file != null) {
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){
				
				oos.writeObject(listaUsuarios);
				
				conn.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				RegistroLogger.anadirLogeo(Level.SEVERE, "Error al crear el archivo");
	    		JOptionPane.showConfirmDialog(null, "Error al crear el archivo", "Error", JOptionPane.PLAIN_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
				RegistroLogger.anadirLogeo(Level.SEVERE, "Error al guardar los datos de usuarios");
				JOptionPane.showConfirmDialog(null, "Error al guardar los datos de usuarios", "Error", JOptionPane.PLAIN_MESSAGE);
			} catch (SQLException e) {
				e.printStackTrace();
				RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
				JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
			}
		}
			
		

	}
	
	public static void exportarFicheroDieta() {
		Connection conn = DBManager.obtenerConexion();
		List<Dieta> listaDietas = DBManager.obtenerDietas(conn);
		File file = obtenerRutaFichero("Guardar archivo en...", ".dat");
		
		if (file != null) {
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){
				
				oos.writeObject(listaDietas);
				
				conn.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				RegistroLogger.anadirLogeo(Level.SEVERE, "Error al crear el archivo");
	    		JOptionPane.showConfirmDialog(null, "Error al crear el archivo", "Error", JOptionPane.PLAIN_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
				RegistroLogger.anadirLogeo(Level.SEVERE, "Error al guardar los datos de dietas");
				JOptionPane.showConfirmDialog(null, "Error al guardar los datos de dietas", "Error", JOptionPane.PLAIN_MESSAGE);
			} catch (SQLException e) {
				e.printStackTrace();
				RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
				JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
			}
		}
		
		
	}
	
	public static void exportarFicheroEntrenamiento() {
		Connection conn = DBManager.obtenerConexion();
		List<Entrenamiento> listaEntrenamientos = DBManager.obtenerEntrenamientos(conn);
		File file = obtenerRutaFichero("Guardar archivo en...", ".dat");
		
		if (file != null) {
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){
				
				oos.writeObject(listaEntrenamientos);	
				
				conn.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				RegistroLogger.anadirLogeo(Level.SEVERE, "Error al crear el archivo");
	    		JOptionPane.showConfirmDialog(null, "Error al crear el archivo", "Error", JOptionPane.PLAIN_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
				RegistroLogger.anadirLogeo(Level.SEVERE, "Error al guardar los datos de entrenamientos");
				JOptionPane.showConfirmDialog(null, "Error al guardar los datos de entrenamientos", "Error", JOptionPane.PLAIN_MESSAGE);
			} catch (SQLException e) {
				e.printStackTrace();
				RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
				JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
			}
		}
			
	}
	
	public static void importarFicheroUsuario() {
		Connection conn = DBManager.obtenerConexion();
		File file = obtenerRutaFichero("Obtener archivo en...", ".dat");
		
		if (file != null) {
			try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file))){
				
				@SuppressWarnings("unchecked")
				List<Usuario> listaUsuarios= (List<Usuario>) oos.readObject();
					
				for (Usuario usuario : listaUsuarios) {
					if (!DBManager.existeUsuario(conn, usuario)) {
						DBManager.anadirUsuario(conn, usuario);
					}
				}
				
				conn.close();
				
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					RegistroLogger.anadirLogeo(Level.SEVERE, "Error al crear el archivo");
		    		JOptionPane.showConfirmDialog(null, "Error al crear el archivo", "Error", JOptionPane.PLAIN_MESSAGE);
				} catch (IOException e) {
					e.printStackTrace();
					RegistroLogger.anadirLogeo(Level.SEVERE, "Error al guardar los datos de entrenamientos");
					JOptionPane.showConfirmDialog(null, "Error al guardar los datos de entrenamientos", "Error", JOptionPane.PLAIN_MESSAGE);
				} catch (ClassNotFoundException e) {
					RegistroLogger.anadirLogeo(Level.SEVERE, "Error al leer los usuarios");
					JOptionPane.showConfirmDialog(null, "Error al leer los usuarios", "Error", JOptionPane.PLAIN_MESSAGE);
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
					RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
					JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
				}
		}
			
		
	}
	
	public static void importarFicheroDieta() {
		Connection conn = DBManager.obtenerConexion();
		File file = obtenerRutaFichero("Obtener archivo en...", ".dat");
		
		if (file != null) {
			
			try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file))){
				
				@SuppressWarnings("unchecked")
				List<Dieta> listaDietas= (List<Dieta>) oos.readObject();
					
				for (Dieta dieta : listaDietas) {
					if (!DBManager.existeDieta(conn, dieta)) {
						DBManager.anadirDieta(conn, dieta);
					}
				}
				
				conn.close();
				
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					RegistroLogger.anadirLogeo(Level.SEVERE, "Error al crear el archivo");
		    		JOptionPane.showConfirmDialog(null, "Error al crear el archivo", "Error", JOptionPane.PLAIN_MESSAGE);
				} catch (IOException e) {
					e.printStackTrace();
					RegistroLogger.anadirLogeo(Level.SEVERE, "Error al guardar los datos de entrenamientos");
					JOptionPane.showConfirmDialog(null, "Error al guardar los datos de entrenamientos", "Error", JOptionPane.PLAIN_MESSAGE);
				} catch (ClassNotFoundException e) {
					RegistroLogger.anadirLogeo(Level.SEVERE, "Error al leer los usuarios");
					JOptionPane.showConfirmDialog(null, "Error al leer los usuarios", "Error", JOptionPane.PLAIN_MESSAGE);
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
					RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
					JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
				}
		}
		
		
		
	}
	
	public static void importarFicheroEntrenamiento() {
		Connection conn = DBManager.obtenerConexion();
		File file = obtenerRutaFichero("Obtener archivo en...", ".dat");
		
		if (file != null) {
			try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file))){
				
				@SuppressWarnings("unchecked")
				List<Entrenamiento> listaEntrenamientos= (List<Entrenamiento>) oos.readObject();
					
				for (Entrenamiento entrenamiento : listaEntrenamientos) {
					if (!DBManager.existeEntrenamiento(conn, entrenamiento)) {
						DBManager.anadirEntrenamiento(conn, entrenamiento);
					}
				}
				
				conn.close();
				
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					RegistroLogger.anadirLogeo(Level.SEVERE, "Error al crear el archivo");
		    		JOptionPane.showConfirmDialog(null, "Error al crear el archivo", "Error", JOptionPane.PLAIN_MESSAGE);
				} catch (IOException e) {
					e.printStackTrace();
					RegistroLogger.anadirLogeo(Level.SEVERE, "Error al guardar los datos de entrenamientos");
					JOptionPane.showConfirmDialog(null, "Error al guardar los datos de entrenamientos", "Error", JOptionPane.PLAIN_MESSAGE);
				} catch (ClassNotFoundException e) {
					RegistroLogger.anadirLogeo(Level.SEVERE, "Error al leer los usuarios");
					JOptionPane.showConfirmDialog(null, "Error al leer los usuarios", "Error", JOptionPane.PLAIN_MESSAGE);
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
					RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
					JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
				}
		}
		
			
		
	}
	
	
}
