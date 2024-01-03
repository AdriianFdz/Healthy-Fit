package db;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level; 

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import domain.Dieta;
import domain.Entrenamiento;
import domain.TipoAlergias;
import domain.TipoDificultad;
import domain.TipoEnfermedades;
import domain.TipoEntrenamiento;
import domain.TipoPermiso;
import domain.TipoSexo;
import domain.Usuario;
import io.RegistroLogger;

public class DBManager {
	private static Connection conn;
	

	public static Connection obtenerConexion() {
		try (FileReader reader = new FileReader("conf/db.properties")){
			Properties propiedades = new Properties();
			propiedades.load(reader);
			
			String conexion = propiedades.getProperty("database");
			conn = DriverManager.getConnection(conexion);
			
		} catch (IOException e1) {
			e1.printStackTrace();
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo leer el archivo db.properties");
			JOptionPane.showConfirmDialog(null, "Error al leer el archivo db.properties", "Error", JOptionPane.PLAIN_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
		}
		return conn;
	}
	
	public static void anadirEnfermedad(Connection connection, TipoEnfermedades enfermedad) {
		try {
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO Enfermedades VALUES (null, ?)");
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
		try {
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO Alergias VALUES (null, ?)");
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
		try {
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO Usuarios VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
			stmt.setInt(12, usuario.getTiempoEntrenado());
			stmt.setString(13, usuario.getUltimaVezEntreno().toString());
			stmt.setInt(14, usuario.getCaloriasConsumidas());
			stmt.setInt(15, usuario.getVasosDeAgua());
			stmt.setString(16, usuario.getContrasena());
			stmt.setBytes(17, convertirFotoABytes(usuario.getFoto()));
			stmt.setString(18, usuario.getPermiso().toString());
			 
			stmt.executeUpdate();
			stmt.close();
			 
			PreparedStatement stmtAnadirEnfermedades = connection.prepareStatement("INSERT INTO Usuario_Enfermedades VALUES (?, ?)");
			for (TipoEnfermedades enfermedad : usuario.getEnfermedades()) {
				stmtAnadirEnfermedades.setString(1, usuario.getNombreUsuario());
				PreparedStatement stmConsultarID = connection.prepareStatement("SELECT id FROM enfermedades WHERE nombreEnfermedad = ?");
				stmConsultarID.setString(1, enfermedad.toString());
				ResultSet idSet = stmConsultarID.executeQuery();
				
				int id = idSet.getInt("id");
				stmtAnadirEnfermedades.setInt(2, id);
				
				stmtAnadirEnfermedades.executeUpdate();
				stmConsultarID.close();
			}
			PreparedStatement stmtAnadirAlergias = connection.prepareStatement("INSERT INTO Usuario_Alergias VALUES (?, ?)");
			for (TipoAlergias alergia: usuario.getAlergias()) {
				stmtAnadirAlergias.setString(1, usuario.getNombreUsuario());
				PreparedStatement stmConsultarID = connection.prepareStatement("SELECT id FROM alergias WHERE nombrealergia = ?");
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

	public static void actualizarFotoUsuario(Connection connection, Usuario usuario, ImageIcon foto) {
		try {
			PreparedStatement pstmt = connection.prepareStatement("UPDATE usuarios SET foto = ? WHERE nombreUsuario = ?");
			pstmt.setBytes(1, convertirFotoABytes(foto));
			pstmt.setString(2, usuario.getNombreUsuario());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}	
	public static void actualizarFotoEntena(Connection connection, Entrenamiento entrenamiento, ImageIcon foto) {
		try {
			PreparedStatement pstmt = connection.prepareStatement("UPDATE entrenamientos SET foto = ? WHERE nombre = ?");
			pstmt.setBytes(1, convertirFotoABytes(foto));
			pstmt.setString(2, entrenamiento.getNombre());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}	
	
	public static byte[] convertirFotoABytes(ImageIcon foto) {
	        BufferedImage bi = new BufferedImage(foto.getIconWidth(), foto.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

	        bi.createGraphics().drawImage(foto.getImage(), 0, 0, null);

	        ByteArrayOutputStream baos = new ByteArrayOutputStream();

	        try {
	            ImageIO.write(bi, "png", baos);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return baos.toByteArray();
	    }

	public static void anadirDieta(Connection connection, Dieta dieta)  {
		try {
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO Dietas VALUES (?, ?, ?, ?)");
			stmt.setString(1, dieta.getNombre());
			stmt.setInt(2, dieta.getTiempo());
			stmt.setString(3, dieta.getDificultad().toString());
			stmt.setInt(4, dieta.getKcal());
			
			stmt.executeUpdate();
			stmt.close();
			
			PreparedStatement stmtAnadirPasos = connection.prepareStatement("INSERT INTO pasos_dietas VALUES (null, ?, ?)");
			for (String s : dieta.getPasos()) {
				stmtAnadirPasos.setString(1, s);
				stmtAnadirPasos.setString(2, dieta.getNombre());
				stmtAnadirPasos.executeUpdate();
				
			}
			stmtAnadirPasos.close();
			
			PreparedStatement stmtAnadirIngredientes = connection.prepareStatement("INSERT INTO ingredientes_dietas VALUES (null, ?, ?)");
			for (String s : dieta.getIngredientes()) {
				stmtAnadirIngredientes.setString(1, s);
				stmtAnadirIngredientes.setString(2, dieta.getNombre());
				stmtAnadirIngredientes.executeUpdate();
			}
			stmtAnadirIngredientes.close();

			PreparedStatement stmtAnadirAlergias = connection.prepareStatement("INSERT INTO dieta_alergias VALUES (null, ?, ?)");
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
		try {
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO Entrenamientos VALUES (?, ?, ?, ? ,?, ?, ?, ?, ?)");
			stmt.setString(1, entrenamiento.getNombre());
			stmt.setString(2, entrenamiento.getTipoEntrenamiento().toString());
			stmt.setString(3, entrenamiento.getDificultad().toString());
			stmt.setInt(4, entrenamiento.getTiempo());
			stmt.setString(5, entrenamiento.getDescripcion());
			stmt.setInt(6, entrenamiento.getCalorias());
			stmt.setInt(7, entrenamiento.getSeries());
			stmt.setInt(8, entrenamiento.getRepeticiones());
			stmt.setBytes(9, convertirFotoABytes(entrenamiento.getFoto()));
			
			stmt.executeUpdate();
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
			
		}
	}
	
	public static void anadirUsuarioEntrenamientos(Connection connection, Usuario usuario, Entrenamiento entrenamiento) {
		try {
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO usuario_entrenamientos VALUES (?, ?, ?, ?)");
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
		try {
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO usuario_dieta VALUES (?, ?, ?)");
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
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
			
		}
		
	}
	
	public static boolean existeUsuario(Connection connection, Usuario usuario) {
		PreparedStatement pstmt;
		try {
			pstmt = connection.prepareStatement("SELECT * FROM usuarios WHERE nombreUsuario = ?");
			pstmt.setString(1, usuario.getNombreUsuario());
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				rs.close();
				pstmt.close();
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);		}
		return true;
	}

	public static boolean existeDieta(Connection connection, Dieta dieta) {
		PreparedStatement pstmt;
		try {
			pstmt = connection.prepareStatement("SELECT * FROM dietas WHERE nombre = ?");
			pstmt.setString(1, dieta.getNombre());
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				rs.close();
				pstmt.close();
				return false;
			}
		} catch (SQLException e) {
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);		}
		return true;
	}

	public static boolean existeEntrenamiento(Connection connection, Entrenamiento entrenamiento) {
		PreparedStatement pstmt;
		try {
			pstmt = connection.prepareStatement("SELECT * FROM entrenamientos WHERE nombre = ?");
			pstmt.setString(1, entrenamiento.getNombre());
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				rs.close();
				pstmt.close();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static void eliminarDieta(Connection connection, Dieta dieta) {
		try {
			PreparedStatement stmtDieta = connection.prepareStatement("DELETE FROM dietas WHERE nombre = ?");
				stmtDieta.setString(1, dieta.getNombre());
			PreparedStatement stmtPasosDieta = connection.prepareStatement("DELETE FROM pasos_dietas WHERE nombreDieta = ?");
				stmtPasosDieta.setString(1, dieta.getNombre());
			PreparedStatement stmtIngredientesDieta = connection.prepareStatement("DELETE FROM ingredientes_dietas WHERE nombreDieta = ?");
				stmtIngredientesDieta.setString(1, dieta.getNombre());
				stmtDieta.executeUpdate();
				stmtPasosDieta.executeUpdate();
				stmtIngredientesDieta.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void eliminarUsuario(Connection connection, Usuario usuario) {
		try {
			PreparedStatement stmtUsuario = connection.prepareStatement("DELETE FROM usuarios WHERE nombreUsuario = ?");
			stmtUsuario.setString(1, usuario.getNombreUsuario());
			stmtUsuario.executeUpdate();
			stmtUsuario.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void eliminarEntrenamientos (Connection connection, Entrenamiento entrenamiento) {
		try {
			PreparedStatement stmtEntrenamiento = connection.prepareStatement("DELETE FROM entrenamientos WHERE nombre = ?");
			stmtEntrenamiento.setString(1, entrenamiento.getNombre());
			stmtEntrenamiento.executeUpdate();
			stmtEntrenamiento.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Dieta> obtenerDietas (Connection connection){
		List<Dieta> resultado = new ArrayList<Dieta>();
		
		try {
			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM dietas");
			while (rs.next()) {
				Dieta d = new Dieta();
				String nombre = rs.getString("nombre");
				int tiempo = rs.getInt("tiempo");
				TipoDificultad difiicultad = TipoDificultad.valueOf(rs.getString("dificultad"));
				int kcal = rs.getInt("kcal");
				
				PreparedStatement stmtPasos = connection.prepareStatement("SELECT * FROM pasos_dietas WHERE nombreDieta = ?");
				stmtPasos.setString(1, nombre);
				ResultSet rsPasos = stmtPasos.executeQuery();
				List<String> pasos = new ArrayList<String>();
				while (rsPasos.next()) {
					pasos.add(rsPasos.getString("denominacion"));
				}
				d.setPasos(new ArrayList<String>(pasos));
				
				PreparedStatement stmtIngredientes = connection.prepareStatement("SELECT * FROM ingredientes_dietas WHERE nombreDieta = ?");
				stmtIngredientes.setString(1, nombre);
				ResultSet rsIngredientes = stmtIngredientes.executeQuery();
				List<String> ingredientes = new ArrayList<String>();
				while (rsIngredientes.next()) {
					ingredientes.add(rsIngredientes.getString("nombreIngrediente"));
				}
				d.setIngredientes(new ArrayList<String>(ingredientes));

				PreparedStatement stmtAlergias = connection.prepareStatement("SELECT nombreAlergia FROM alergias WHERE id = (SELECT alergia FROM dieta_alergias WHERE nombreDieta = ?)");
				stmtAlergias.setString(1, nombre);
				ResultSet rsAlergias = stmtAlergias.executeQuery();
				List<TipoAlergias> alergias = new ArrayList<TipoAlergias>();
				while (rsAlergias.next()) {
					alergias.add(TipoAlergias.valueOf(rsAlergias.getString("nombreAlergia")));
				}
				d.setAlergias(new ArrayList<TipoAlergias>(alergias));
				
				d.setNombre(nombre);
				d.setTiempo(tiempo);
				d.setDificultad(difiicultad);
				d.setKcal(kcal);
				
				
				resultado.add(d);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		return resultado;
			
	}
	
	public static Dieta obtenerDietas (Connection connection, String nombreCondicion){
		
		Dieta d = new Dieta();
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM dietas WHERE nombre = ?");
			stmt.setString(1, nombreCondicion);
			
			ResultSet rs = stmt.executeQuery();
		
			
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				int tiempo = rs.getInt("tiempo");
				TipoDificultad difiicultad = TipoDificultad.valueOf(rs.getString("dificultad"));
				int kcal = rs.getInt("kcal");
				
				PreparedStatement stmtPasos = connection.prepareStatement("SELECT * FROM pasos_dietas WHERE nombreDieta = ?");
				stmtPasos.setString(1, nombre);
				ResultSet rsPasos = stmtPasos.executeQuery();
				List<String> pasos = new ArrayList<String>();
				while (rsPasos.next()) {
					pasos.add(rsPasos.getString("denominacion"));
				}
				d.setPasos(new ArrayList<String>(pasos));
				
				PreparedStatement stmtIngredientes = connection.prepareStatement("SELECT * FROM ingredientes_dietas WHERE nombreDieta = ?");
				stmtIngredientes.setString(1, nombre);
				ResultSet rsIngredientes = stmtIngredientes.executeQuery();
				List<String> ingredientes = new ArrayList<String>();
				while (rsIngredientes.next()) {
					ingredientes.add(rsIngredientes.getString("nombreIngrediente"));
				}
				d.setIngredientes(new ArrayList<String>(ingredientes));
				
				PreparedStatement stmtAlergias = connection.prepareStatement("SELECT nombreAlergia FROM alergias WHERE id = (SELECT alergia FROM dieta_alergias WHERE nombreDieta = ?)");
				stmtAlergias.setString(1, nombre);
				ResultSet rsAlergias = stmtAlergias.executeQuery();
				List<TipoAlergias> alergias = new ArrayList<TipoAlergias>();
				while (rsAlergias.next()) {
					alergias.add(TipoAlergias.valueOf(rsAlergias.getString("nombreAlergia")));
				}
				d.setAlergias(new ArrayList<TipoAlergias>(alergias));
				
				d.setNombre(nombre);
				d.setTiempo(tiempo);
				d.setDificultad(difiicultad);
				d.setKcal(kcal);
				
			}
		}	catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);		}
		return d;
		
		
	}
	
	public static List<Usuario> obtenerUsuarios(Connection connection) {
		
		List<Usuario> resultado = new ArrayList<Usuario>();
		try {
			Statement stmt = connection.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");
			
			while (rs.next()) {
				String nombreUsuario = rs.getString("nombreUsuario");
				String nombre = rs.getString("nombre");
				String apellido1 = rs.getString("apellido1");
				String apellido2 = rs.getString("apellido2");
				LocalDate fechaNacimiento = LocalDate.parse(rs.getString("fechaNacimiento"));
				TipoSexo sexo = TipoSexo.valueOf(rs.getString("sexo"));
				double altura = rs.getDouble("altura");
				int peso = rs.getInt("peso");
				String correoElectronico = rs.getString("correoElectronico");
				int caloriasGastadas = rs.getInt("caloriasGastadas");
				int tiempoEntrenado = rs.getInt("tiempoEntrenado");
				LocalDate ultimaVezEntreno = LocalDate.parse(rs.getString("ultimaVezEntreno"));
				int caloriasConsumidas = rs.getInt("caloriasConsumidas");
				int vasosDeAgua = rs.getInt("vasosDeAgua");
				String contrasena = rs.getString("contrasena");
				ImageIcon foto = new ImageIcon(rs.getBytes("foto"));
				TipoPermiso permiso = TipoPermiso.valueOf(rs.getString("permiso"));

				PreparedStatement pstmtAlergias = connection.prepareStatement("SELECT * FROM usuario_alergias WHERE nombreUsuario = ?");
				pstmtAlergias.setString(1, nombreUsuario);
				ResultSet rsAlergias = pstmtAlergias.executeQuery();

				List<TipoAlergias> alergias = new ArrayList<TipoAlergias>();
				while (rsAlergias.next()) {
					alergias.add(TipoAlergias.values()[rsAlergias.getInt("id_alergia")-1]);
				}

				PreparedStatement pstmtEnfermedades = connection.prepareStatement("SELECT * FROM usuario_enfermedades WHERE nombreUsuario = ?");
				pstmtEnfermedades.setString(1, nombreUsuario);
				ResultSet rsEnfermedades = pstmtEnfermedades.executeQuery();

				List<TipoEnfermedades> enfermedades = new ArrayList<TipoEnfermedades>();
				while (rsEnfermedades.next()) {
					enfermedades.add(TipoEnfermedades.values()[rsEnfermedades.getInt("id_enfermedad")-1]);
				}

				PreparedStatement pstmtProximaComida = connection.prepareStatement("SELECT * FROM usuario_dieta WHERE nombreUsuario = ?");
				pstmtProximaComida.setString(1, nombreUsuario);
				ResultSet rsProximaComida = pstmtProximaComida.executeQuery();
				Map<LocalDate, Dieta> proximaComida = new HashMap<LocalDate, Dieta>();
				while (rsProximaComida.next()) {
					LocalDate fecha = LocalDate.parse(rsProximaComida.getString("fecha"));
					String nombreDieta = rsProximaComida.getString("nombreDieta");

					PreparedStatement pstmtObtenerDieta = connection.prepareStatement("SELECT * FROM dietas WHERE nombre = ?");
					pstmtObtenerDieta.setString(1, nombreDieta);
					ResultSet rsObtenerDieta = pstmtObtenerDieta.executeQuery();
					while (rsObtenerDieta.next()) {
						int tiempo = rsObtenerDieta.getInt("tiempo");
						TipoDificultad dificultad = TipoDificultad.valueOf(rsObtenerDieta.getString("dificultad"));
						int kcal = rsObtenerDieta.getInt("kcal");

						PreparedStatement pstmtObtenerPasosDieta = connection.prepareStatement("SELECT * FROM pasos_dietas WHERE nombreDieta = ?");
						pstmtObtenerPasosDieta.setString(1, nombreDieta);
						ResultSet rsObtenerPasosDieta = pstmtObtenerPasosDieta.executeQuery();
						List<String> pasos = new ArrayList<String>();
						while (rsObtenerPasosDieta.next()) {
							pasos.add(rsObtenerPasosDieta.getString("denominacion"));
						}

						PreparedStatement pstmtObtenerIngredientesDieta = connection.prepareStatement("SELECT * FROM ingredientes_dietas WHERE nombreDieta = ?");
						pstmtObtenerIngredientesDieta.setString(1, nombreDieta);
						ResultSet rsObtenerIngredientesDieta = pstmtObtenerIngredientesDieta.executeQuery();
						List<String> ingredientes = new ArrayList<String>();
						while (rsObtenerIngredientesDieta.next()) {
							ingredientes.add(rsObtenerIngredientesDieta.getString("nombreIngrediente"));
						}

						PreparedStatement pstmtObtenerAlergiasDieta = connection.prepareStatement("SELECT * FROM dieta_alergias WHERE nombreDieta = ?");
						pstmtObtenerAlergiasDieta.setString(1, nombreDieta);
						ResultSet rsObtenerAlergiasDieta = pstmtObtenerAlergiasDieta.executeQuery();
						List<TipoAlergias> alergiasDieta = new ArrayList<TipoAlergias>();
						while (rsObtenerAlergiasDieta.next()) {
							alergiasDieta.add(TipoAlergias.valueOf(rsObtenerAlergiasDieta.getString("alergia")));
						}

						Dieta dieta = new Dieta(nombreDieta, tiempo, dificultad, kcal, pasos, ingredientes,
								alergiasDieta);
						proximaComida.putIfAbsent(fecha, dieta);
					}
				}

				// OBTENER REGISTRO ENTRENAMIENTOS del usuario
				PreparedStatement pstmtRegEntrenamientos = connection.prepareStatement(
						"SELECT * FROM entrenamientos WHERE nombre IN (SELECT nombreEntrenamiento FROM usuario_entrenamientos WHERE nombreUsuario = ?)");
				pstmtRegEntrenamientos.setString(1, nombreUsuario);
				ResultSet rsUsuarioEntrenamientos = pstmtRegEntrenamientos.executeQuery();

				List<Entrenamiento> listaEntrenamientos = new ArrayList<Entrenamiento>();
				while (rsUsuarioEntrenamientos.next()) {
					String nombreEntrenamiento = rsUsuarioEntrenamientos.getString("nombre");
					TipoEntrenamiento tipoEntrenamiento = TipoEntrenamiento
							.valueOf(rsUsuarioEntrenamientos.getString("tipoEntrenamiento"));
					TipoDificultad dificultad = TipoDificultad
							.valueOf(rsUsuarioEntrenamientos.getString("dificultad"));
					int tiempo = rsUsuarioEntrenamientos.getInt("tiempo");
					String descripcion = rsUsuarioEntrenamientos.getString("descripcion");
					int calorias = rsUsuarioEntrenamientos.getInt("calorias");
					int series = rsUsuarioEntrenamientos.getInt("series");
					int repeticiones = rsUsuarioEntrenamientos.getInt("repeticiones");
					ImageIcon fotoEntrena = new ImageIcon(rsUsuarioEntrenamientos.getBytes("foto"));
					
					Entrenamiento entrenamiento = new Entrenamiento(nombreEntrenamiento, tipoEntrenamiento,
							dificultad, tiempo, descripcion, calorias, series, repeticiones, fotoEntrena);
					listaEntrenamientos.add(entrenamiento);

					pstmtRegEntrenamientos.close();
				}
				Usuario usuario = new Usuario(nombre, nombreUsuario, apellido1, apellido2, fechaNacimiento,
						sexo, altura, peso, new ArrayList<TipoAlergias>(alergias), correoElectronico, new ArrayList<TipoEnfermedades>(enfermedades),
						caloriasGastadas, tiempoEntrenado, ultimaVezEntreno,
						caloriasConsumidas, new HashMap<LocalDate, Dieta>(proximaComida), vasosDeAgua, contrasena, foto, permiso,
						new ArrayList<Entrenamiento>(listaEntrenamientos));
				resultado.add(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);		}
		return resultado;
	}
	
	public static Usuario obtenerUsuarios(Connection connection, String nombreCondicion) {
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM usuarios WHERE nombreUsuario = ?");
			stmt.setString(1, nombreCondicion);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				String nombreUsuario = rs.getString("nombreUsuario");
				String nombre = rs.getString("nombre");
				String apellido1 = rs.getString("apellido1");
				String apellido2 = rs.getString("apellido2");
				LocalDate fechaNacimiento = LocalDate.parse(rs.getString("fechaNacimiento"));
				TipoSexo sexo = TipoSexo.valueOf(rs.getString("sexo"));
				double altura = rs.getDouble("altura");
				int peso = rs.getInt("peso");
				String correoElectronico = rs.getString("correoElectronico");
				int caloriasGastadas = rs.getInt("caloriasGastadas");
				int tiempoEntrenado = rs.getInt("tiempoEntrenado");
				LocalDate ultimaVezEntreno = LocalDate.parse(rs.getString("ultimaVezEntreno"));
				int caloriasConsumidas = rs.getInt("caloriasConsumidas");
				int vasosDeAgua = rs.getInt("vasosDeAgua");
				String contrasena = rs.getString("contrasena");
				ImageIcon foto = new ImageIcon(rs.getBytes("foto"));
				TipoPermiso permiso = TipoPermiso.valueOf(rs.getString("permiso"));

				PreparedStatement pstmtAlergias = connection.prepareStatement("SELECT * FROM usuario_alergias WHERE nombreUsuario = ?");
				pstmtAlergias.setString(1, nombreUsuario);
				ResultSet rsAlergias = pstmtAlergias.executeQuery();

				List<TipoAlergias> alergias = new ArrayList<TipoAlergias>();
				while (rsAlergias.next()) {
					alergias.add(TipoAlergias.values()[rsAlergias.getInt("id_alergia")-1]);
				}

				PreparedStatement pstmtEnfermedades = connection.prepareStatement("SELECT * FROM usuario_enfermedades WHERE nombreUsuario = ?");
				pstmtEnfermedades.setString(1, nombreUsuario);
				ResultSet rsEnfermedades = pstmtEnfermedades.executeQuery();

				List<TipoEnfermedades> enfermedades = new ArrayList<TipoEnfermedades>();
				while (rsEnfermedades.next()) {
					enfermedades.add(TipoEnfermedades.values()[rsEnfermedades.getInt("id_enfermedad")-1]);
				}

				PreparedStatement pstmtProximaComida = connection.prepareStatement("SELECT * FROM usuario_dieta WHERE nombreUsuario = ?");
				pstmtProximaComida.setString(1, nombreUsuario);
				ResultSet rsProximaComida = pstmtProximaComida.executeQuery();
				Map<LocalDate, Dieta> proximaComida = new HashMap<LocalDate, Dieta>();
				while (rsProximaComida.next()) {
					LocalDate fecha = LocalDate.parse(rsProximaComida.getString("fecha"));
					String nombreDieta = rsProximaComida.getString("nombreDieta");

					PreparedStatement pstmtObtenerDieta = connection.prepareStatement("SELECT * FROM dietas WHERE nombre = ?");
					pstmtObtenerDieta.setString(1, nombreDieta);
					ResultSet rsObtenerDieta = pstmtObtenerDieta.executeQuery();
					while (rsObtenerDieta.next()) {
						int tiempo = rsObtenerDieta.getInt("tiempo");
						TipoDificultad dificultad = TipoDificultad.valueOf(rsObtenerDieta.getString("dificultad"));
						int kcal = rsObtenerDieta.getInt("kcal");

						PreparedStatement pstmtObtenerPasosDieta = connection.prepareStatement("SELECT * FROM pasos_dietas WHERE nombreDieta = ?");
						pstmtObtenerPasosDieta.setString(1, nombreDieta);
						ResultSet rsObtenerPasosDieta = pstmtObtenerPasosDieta.executeQuery();
						List<String> pasos = new ArrayList<String>();
						while (rsObtenerPasosDieta.next()) {
							pasos.add(rsObtenerPasosDieta.getString("denominacion"));
						}

						PreparedStatement pstmtObtenerIngredientesDieta = connection.prepareStatement("SELECT * FROM ingredientes_dietas WHERE nombreDieta = ?");
						pstmtObtenerIngredientesDieta.setString(1, nombreDieta);
						ResultSet rsObtenerIngredientesDieta = pstmtObtenerIngredientesDieta.executeQuery();
						List<String> ingredientes = new ArrayList<String>();
						while (rsObtenerIngredientesDieta.next()) {
							ingredientes.add(rsObtenerIngredientesDieta.getString("nombreIngrediente"));
						}

						PreparedStatement pstmtObtenerAlergiasDieta = connection.prepareStatement("SELECT * FROM dieta_alergias WHERE nombreDieta = ?");
						pstmtObtenerAlergiasDieta.setString(1, nombreDieta);
						ResultSet rsObtenerAlergiasDieta = pstmtObtenerAlergiasDieta.executeQuery();
						List<TipoAlergias> alergiasDieta = new ArrayList<TipoAlergias>();
						while (rsObtenerAlergiasDieta.next()) {
							alergiasDieta.add(TipoAlergias.valueOf(rsObtenerAlergiasDieta.getString("alergia")));
						}

						Dieta dieta = new Dieta(nombreDieta, tiempo, dificultad, kcal, pasos, ingredientes,
								alergiasDieta);
						proximaComida.putIfAbsent(fecha, dieta);
					}
				}

				// OBTENER REGISTRO ENTRENAMIENTOS del usuario
				PreparedStatement pstmtRegEntrenamientos = connection.prepareStatement(
						"SELECT * FROM entrenamientos WHERE nombre IN (SELECT nombreEntrenamiento FROM usuario_entrenamientos WHERE nombreUsuario = ?)");
				pstmtRegEntrenamientos.setString(1, nombreUsuario);
				ResultSet rsUsuarioEntrenamientos = pstmtRegEntrenamientos.executeQuery();

				List<Entrenamiento> listaEntrenamientos = new ArrayList<Entrenamiento>();
				while (rsUsuarioEntrenamientos.next()) {
					String nombreEntrenamiento = rsUsuarioEntrenamientos.getString("nombre");
					TipoEntrenamiento tipoEntrenamiento = TipoEntrenamiento
							.valueOf(rsUsuarioEntrenamientos.getString("tipoEntrenamiento"));
					TipoDificultad dificultad = TipoDificultad
							.valueOf(rsUsuarioEntrenamientos.getString("dificultad"));
					int tiempo = rsUsuarioEntrenamientos.getInt("tiempo");
					String descripcion = rsUsuarioEntrenamientos.getString("descripcion");
					int calorias = rsUsuarioEntrenamientos.getInt("calorias");
					int series = rsUsuarioEntrenamientos.getInt("series");
					int repeticiones = rsUsuarioEntrenamientos.getInt("repeticiones");
					ImageIcon fotoEnt = new ImageIcon(rsUsuarioEntrenamientos.getBytes("foto"));
					
					Entrenamiento entrenamiento = new Entrenamiento(nombreEntrenamiento, tipoEntrenamiento,
							dificultad, tiempo, descripcion, calorias, series, repeticiones, fotoEnt);
					listaEntrenamientos.add(entrenamiento);

					pstmtRegEntrenamientos.close();
				}

				Usuario usuario = new Usuario(nombre, nombreUsuario, apellido1, apellido2, fechaNacimiento,
						sexo, altura, peso, new ArrayList<TipoAlergias>(alergias), correoElectronico, new ArrayList<TipoEnfermedades>(enfermedades),
						caloriasGastadas, tiempoEntrenado, ultimaVezEntreno,
						caloriasConsumidas, new HashMap<LocalDate, Dieta>(proximaComida), vasosDeAgua, contrasena, foto, permiso,
						new ArrayList<Entrenamiento>(listaEntrenamientos));
				return usuario;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);		
		}
		return null;
	}

	public static List<Entrenamiento> obtenerEntrenamientos(Connection connection) {
		
		List<Entrenamiento> resultado = new ArrayList<Entrenamiento>();
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM entrenamientos");
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				TipoEntrenamiento tipoEntrenamiento = TipoEntrenamiento.valueOf(rs.getString("tipoEntrenamiento"));
				TipoDificultad dificultad = TipoDificultad.valueOf(rs.getString("dificultad"));
				int tiempo = rs.getInt("tiempo");
				String descripcion = rs.getString("descripcion");
				int calorias = rs.getInt("calorias");
				int series = rs.getInt("series");
				int repeticiones = rs.getInt("repeticiones");
				ImageIcon fotoEnt = new ImageIcon(rs.getBytes("foto"));

				Entrenamiento entrenamiento = new Entrenamiento(nombre, tipoEntrenamiento, dificultad, tiempo, descripcion, calorias, series, repeticiones, fotoEnt);
				resultado.add(entrenamiento);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	public static Entrenamiento obtenerEntrenamientos(Connection connection, String nombreCondicion) {
				
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM entrenamientos WHERE nombre = ?");
			stmt.setString(1, nombreCondicion);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				TipoEntrenamiento tipoEntrenamiento = TipoEntrenamiento.valueOf(rs.getString("tipoEntrenamiento"));
				TipoDificultad dificultad = TipoDificultad.valueOf(rs.getString("dificultad"));
				int tiempo = rs.getInt("tiempo");
				String descripcion = rs.getString("descripcion");
				int calorias = rs.getInt("calorias");
				int series = rs.getInt("series");
				int repeticiones = rs.getInt("repeticiones");
				ImageIcon fotoEnt = new ImageIcon(rs.getBytes("foto"));

				Entrenamiento entrenamiento = new Entrenamiento(nombre, tipoEntrenamiento, dificultad, tiempo, descripcion, calorias, series, repeticiones, fotoEnt);
				return entrenamiento;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
}
	
