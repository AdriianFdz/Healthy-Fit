package io;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level; 

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import domain.Dieta;
import domain.Entrenamiento;
import domain.TipoAlergias;
import domain.TipoEnfermedades;
import domain.Usuario;

public class DBManager {
	private static Connection conn;
	
	public static Connection obtenerConexion() {
		try (FileReader reader = new FileReader("conf/db.properties")){
			Properties propiedades = new Properties();
			propiedades.load(reader);
			
			String conexion = propiedades.getProperty("database");
			conn = DriverManager.getConnection(conexion);
			
		} catch (IOException e1) {
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo leer el archivo db.properties");
			JOptionPane.showConfirmDialog(null, "Error al leer el archivo db.properties", "Error", JOptionPane.PLAIN_MESSAGE);
		} catch (SQLException e) {
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
		}

		return conn;
	}
	
	public static void anadirEnfermedad(Connection connection, TipoEnfermedades enfermedad) {
		Connection conn = connection;
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Enfermedades VALUES (null, ?)");
			 stmt.setString(1, enfermedad.toString());
			 stmt.executeUpdate();
			 
			 stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
		}
			
		

	}

	public static void anadirAlergia(Connection connection, TipoAlergias alergia) {
		Connection conn = connection;
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Alergias VALUES (null, ?)");
			 stmt.setString(1, alergia.toString());
			 stmt.executeUpdate();
			 
			 stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
		}
			
		

	}

	
	public static void anadirUsuario(Connection connection, Usuario usuario) {
		Connection conn = connection;
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Usuarios VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, usuario.getNombreUsuario());
			stmt.setString(2, usuario.getNombre());
			stmt.setString(3, usuario.getApellido1());
			stmt.setString(4, usuario.getApellido2());
		 	stmt.setString(5, usuario.getFechaNacimiento().toString());
			stmt.setString(6, usuario.getSexo().toString());
			stmt.setDouble(7, usuario.getAltura());
			stmt.setInt(8, usuario.getPeso());
			stmt.setString(9, usuario.getCorreoElectronico());
			stmt.setDouble(10, usuario.getImc());
			stmt.setInt(11, usuario.getCaloriasGastadas());
			stmt.setInt(12, usuario.getRachaEntrenamiento());
			stmt.setString(13, usuario.getObjetivo());
			stmt.setInt(14, usuario.getTiempoEntrenado());
			stmt.setString(15, usuario.getUltimaVezEntreno().toString());
			stmt.setInt(16, usuario.getCaloriasConsumidas());
			stmt.setInt(17, usuario.getVasosDeAgua());
			stmt.setString(18, usuario.getContrasena());
			stmt.setBytes(19, convertirFotoABytes(usuario.getFoto().getImage()));
			 
			stmt.executeUpdate();
			stmt.close();
			 
			PreparedStatement stmtAnadirEnfermedades = conn.prepareStatement("INSERT INTO Usuario_Enfermedades VALUES (?, ?)");
			for (TipoEnfermedades enfermedad : usuario.getEnfermedades()) {
				stmtAnadirEnfermedades.setString(1, usuario.getNombreUsuario());
				PreparedStatement stmConsultarID = conn.prepareStatement("SELECT id FROM enfermedades WHERE nombreEnfermedad = ?");
				stmConsultarID.setString(1, enfermedad.toString());
				ResultSet idSet = stmConsultarID.executeQuery();
				
				int id = idSet.getInt("id");
				stmtAnadirEnfermedades.setInt(2, id);
				
				stmtAnadirEnfermedades.executeUpdate();
				stmConsultarID.close();
			}
			PreparedStatement stmtAnadirAlergias = conn.prepareStatement("INSERT INTO Usuario_Alergias VALUES (?, ?)");
			for (TipoAlergias alergia: usuario.getAlergias()) {
				stmtAnadirAlergias.setString(1, usuario.getNombreUsuario());
				PreparedStatement stmConsultarID = conn.prepareStatement("SELECT id FROM alergias WHERE nombrealergia = ?");
				stmConsultarID.setString(1, alergia.toString());
				ResultSet idSet = stmConsultarID.executeQuery();
				 
				int id = idSet.getInt("id");
				stmtAnadirAlergias.setInt(2, id);
				 
				stmtAnadirAlergias.executeUpdate();
				 
				stmConsultarID.close();
			}
			
			stmtAnadirEnfermedades.close();
			stmtAnadirAlergias.close();
			} catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
		}
	}

	public static void actualizarFoto(Connection connection, Usuario usuario, Image foto) {
		try {
			PreparedStatement pstmt = connection.prepareStatement("UPDATE usuarios SET foto = ? WHERE nombreUsuario = ?");
			pstmt.setBytes(1, convertirFotoABytes(usuario.getFoto().getImage()));
			pstmt.setString(2, usuario.getNombreUsuario());
			
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}	
	
	public static byte[] convertirFotoABytes(Image foto) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage bi = new BufferedImage(foto.getWidth(null), foto.getHeight(null), BufferedImage.TYPE_INT_RGB);
        try {
			ImageIO.write(bi, "png", baos);
		} catch (IOException e) {
			e.printStackTrace();
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
		}
        
		return baos.toByteArray();
	   
	}
	
	public static void anadirDieta(Connection connection, Dieta dieta)  {
		Connection conn = connection;	
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Dietas VALUES (?, ?, ?, ?)");
			stmt.setString(1, dieta.getNombre());
			stmt.setInt(2, dieta.getTiempo());
			stmt.setString(3, dieta.getDificultad().toString());
			stmt.setInt(4, dieta.getKcal());
			
			stmt.executeUpdate();
			stmt.close();
			
			PreparedStatement stmtAnadirPasos = conn.prepareStatement("INSERT INTO pasos_dietas VALUES (null, ?, ?)");
			for (String s : dieta.getPasos()) {
				stmtAnadirPasos.setString(1, s);
				stmtAnadirPasos.setString(2, dieta.getNombre());
				stmtAnadirPasos.executeUpdate();
				
			}
			stmtAnadirPasos.close();
			
			PreparedStatement stmtAnadirIngredientes = conn.prepareStatement("INSERT INTO ingredientes_dietas VALUES (null, ?, ?)");
			for (String s : dieta.getIngredientes()) {
				stmtAnadirIngredientes.setString(1, s);
				stmtAnadirIngredientes.setString(2, dieta.getNombre());
				stmtAnadirIngredientes.executeUpdate();
			}
			stmtAnadirIngredientes.close();

			PreparedStatement stmtAnadirAlergias = conn.prepareStatement("INSERT INTO dieta_alergias VALUES (null, ?, ?)");
			for (TipoAlergias alergia : dieta.getAlergias()) {
				stmtAnadirAlergias.setString(1, dieta.getNombre());
				stmtAnadirAlergias.setString(2, alergia.toString());
				stmtAnadirAlergias.executeUpdate();
			}
			stmtAnadirAlergias.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
		} 
			
	}
	
	public static void anadirEntrenamiento(Connection connection, Entrenamiento entrenamiento) {
		Connection conn = connection;
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Entrenamientos VALUES (?, ?, ?, ? ,?, ?, ?, ?)");
			stmt.setString(1, entrenamiento.getNombre());
			stmt.setString(2, entrenamiento.getTipoEntrenamiento().toString());
			stmt.setString(3, entrenamiento.getDificultad().toString());
			stmt.setInt(4, entrenamiento.getTiempo());
			stmt.setString(5, entrenamiento.getDescripcion());
			stmt.setInt(6, entrenamiento.getCalorias());
			stmt.setInt(7, entrenamiento.getSeries());
			stmt.setInt(8, entrenamiento.getRepeticiones());
			
			stmt.executeUpdate();
			stmt.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
			
		}
	}
	
	public static void anadirUsuarioEntrenamientos(Connection connection, Usuario usuario, Entrenamiento entrenamiento) {
		Connection conn = connection;
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO usuario_entrenamientos VALUES (?, ?, ?, ?)");
			stmt.setString(1, usuario.getNombreUsuario());
			stmt.setString(2, entrenamiento.getNombre());
			stmt.setString(3, entrenamiento.getDificultad().toString());
			stmt.setString(4, LocalDateTime.now(ZoneId.of("Europe/Madrid")).toString());
			
			stmt.executeUpdate();
			stmt.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
		}
		
	}
	
	public static void anadirUsuarioDieta(Connection connection, Usuario usuario, Dieta dieta, LocalDate fecha){
		Connection conn = connection;
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO usuario_dieta VALUES (?, ?, ?)");
			stmt.setString(1, usuario.getNombreUsuario());
			stmt.setString(2, fecha.toString());
			stmt.setString(3, dieta.getNombre());
			
			stmt.executeUpdate();
			stmt.close();
			
		}catch (SQLException e) {
			//NO ANADIR NINGUN ERROR
		}
	}
	
	public static void modificarVasosAgua(Connection connection, Usuario usuario, int cantidad) {
		try {
			PreparedStatement pstmt = connection.prepareStatement("UPDATE usuarios SET vasosDeAgua = ? WHERE nombreUsuario = ?");
			pstmt.setInt(1, cantidad);
			pstmt.setString(2, usuario.getNombreUsuario());
			pstmt.executeUpdate();
			
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
