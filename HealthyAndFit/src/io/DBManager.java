package io;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

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
		} catch (SQLException e) {
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");

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
			//MODIFICAR EXCP
			e.printStackTrace();
		}
			
		

	}

	public static void anadirUsuario(Connection connection, Usuario usuario) {
		Connection conn = connection;
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Usuarios VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, usuario.getNombreUsuario());
			stmt.setString(2, usuario.getNombre());
			stmt.setString(3, usuario.getApellido1());
			stmt.setString(4, usuario.getApellido2());
		 	stmt.setString(5, usuario.getfechaNacimiento().toString());
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
			stmt.setString(17, usuario.getProximaComida());
			stmt.setInt(18, usuario.getVasosDeAgua());
			stmt.setString(19, usuario.getContrasena());
			stmt.setBytes(20, convertirFotoABytes(usuario.getFoto().getImage()));
			 
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
			//MODIFICAR EXCP
			e.printStackTrace();
		}
	}
	
	public static ResultSet recuperarDatosTabla(Connection connection, String tabla) {
		Connection conn = connection;
		ResultSet resultado = null;
		try {
			PreparedStatement stmt = conn.prepareStatement(String.format("SELECT * from %s", tabla));
			resultado = stmt.executeQuery();
			stmt.close();
		} catch (SQLException e) {
			//MODIFICAR EXCP
			e.printStackTrace();
		}
		return resultado;
	}
	
	public static byte[] convertirFotoABytes(Image foto) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage bi = new BufferedImage(foto.getWidth(null), foto.getHeight(null), BufferedImage.TYPE_INT_RGB);
        try {
			ImageIO.write(bi, "png", baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return baos.toByteArray();
	   
	}
}
