package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jfree.data.time.TimeSeriesCollection;

import com.toedter.calendar.JCalendar;

import db.BaseDeDatos;
import domain.Dieta;
import domain.Entrenamiento;
import domain.TipoAlergias;
import domain.TipoDificultad;
import domain.TipoEnfermedades;
import domain.TipoEntrenamiento;
import domain.TipoPermiso;
import domain.TipoPreferencia;
import domain.TipoSexo;
import domain.Usuario;
import io.DBManager;
import io.RegistroLogger;

public class VentanaLogeoRegistro extends JFrame {
	private static final long serialVersionUID = 1L;

	public JTabbedPane paneles;

	public JPanel logeo;
	public JPanel logeoIzquierda;
	public JLabel preguntaYaTienesCuenta;
	public JLabel nombreLogeo;
	public JTextField meterNombreLogeo;
	public JLabel contraseñaLogeo;
	public JPasswordField meterContraseñaLogeo;
	public JButton iniciarSesion;
	public JButton inicioRapido;
	public JPanel logeoDerecha;

	// INFORMACIÓN PRIMARIA PARA REGISTRARSE

	public JPanel registro;
	public JScrollPane registroScroll;
	public JPanel meterDatos;
	public JLabel preguntaTodaviaSinCuenta;

	public JLabel nombreRegistro;
	public JTextField meterNombreRegistro;

	public JLabel correoRegistro;
	public JTextField meterCorreoRegistro;

	public JLabel contraseñaRegistro;
	public JTextField meterContraseñaRegistro;

	public JLabel contraseñaRepetidaRegistro;
	public JTextField meterContraseñaRepetidaRegistro;

	public JLabel apellido1;
	public JTextField meterApellido1;

	public JLabel apellido2;
	public JTextField meterApellido2;

	public JLabel altura;
	public JTextField meterAltura;

	public JLabel peso;
	public JTextField meterPeso;

	public JLabel genero;
	public JPanel generoB;
	public ButtonGroup meterGenero;
	public JRadioButton M;
	public JRadioButton H;
	public JRadioButton O;

	public JLabel fechaNac;
	public JCalendar meterFechaNac;

	public JLabel enfermedades;
	public JTable meterEnfermedades;

	public JLabel alergias;
	public JTable meterAlergias;

	public JButton registrarse;

	public VentanaLogeoRegistro() {

		paneles = new JTabbedPane(); // El panel para seleccionar la ventana de logeo o registro

		logeo = new JPanel(new GridLayout(1, 3));

		logeoIzquierda = new JPanel();
		logeoIzquierda.setLayout(new BoxLayout(logeoIzquierda, BoxLayout.Y_AXIS));

		preguntaYaTienesCuenta = new JLabel("¿Ya tienes cuenta?");
		logeoIzquierda.add(preguntaYaTienesCuenta);

		nombreLogeo = new JLabel("NOMBRE DE USUARIO ");
		meterNombreLogeo = new JTextField(20);
		logeoIzquierda.add(nombreLogeo);
		logeoIzquierda.add(meterNombreLogeo);

		contraseñaLogeo = new JLabel("CONTRASEÑA");
		meterContraseñaLogeo = new JPasswordField(20);
		logeoIzquierda.add(contraseñaLogeo);
		logeoIzquierda.add(meterContraseñaLogeo);

		iniciarSesion = new JButton("INICIAR SESION");
		logeoIzquierda.add(iniciarSesion);

		inicioRapido = new JButton("INICIO RAPIDO");
		logeoIzquierda.add(inicioRapido);

		logeoDerecha = new JPanel();

		logeo.add(logeoIzquierda);
		logeo.add(logeoDerecha);

		// REGISTRO

		registro = new JPanel(new BorderLayout());
		preguntaTodaviaSinCuenta = new JLabel("¿Todavía no tienes cuenta?");
		registro.add(preguntaTodaviaSinCuenta, BorderLayout.NORTH);

		meterDatos = new JPanel();
		meterDatos.setLayout(new BoxLayout(meterDatos, BoxLayout.Y_AXIS));

		nombreRegistro = new JLabel("Nombre de Usuario");
		meterNombreRegistro = new JTextField();

		correoRegistro = new JLabel("Correo Electronico");
		meterCorreoRegistro = new JTextField();

		contraseñaRegistro = new JLabel("Contraseña");
		meterContraseñaRegistro = new JPasswordField();

		contraseñaRepetidaRegistro = new JLabel("Repetir Contraseña");
		meterContraseñaRepetidaRegistro = new JPasswordField();

		registrarse = new JButton("REGISTRARSE");
		registro.add(registrarse, BorderLayout.SOUTH);

		apellido1 = new JLabel("Primer Apellido");
		meterApellido1 = new JTextField();

		apellido2 = new JLabel("Segundo Apellido");
		meterApellido2 = new JTextField();

		altura = new JLabel("Peso");
		meterAltura = new JTextField();

		peso = new JLabel("Altura");
		meterPeso = new JTextField();

		genero = new JLabel("Genero");
		generoB = new JPanel(new BorderLayout());
		meterGenero = new ButtonGroup();
		M = new JRadioButton("Mujer");
		H = new JRadioButton("Hombre");
		O = new JRadioButton("Otro");
		meterGenero.add(H);
		meterGenero.add(M);
		meterGenero.add(O);
		generoB.add(M, BorderLayout.NORTH);
		generoB.add(H, BorderLayout.CENTER);
		generoB.add(O, BorderLayout.SOUTH);

		enfermedades = new JLabel("Enfermedades");
		DefaultTableModel modeloEnfermedades = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}

		};
		modeloEnfermedades.addColumn("Enfermedades");
		meterEnfermedades = new JTable(modeloEnfermedades);
		//JPanel enfermedadesScroll = new JPanel();
		//enfermedadesScroll.add(meterEnfermedades);
		for (TipoEnfermedades enfermedad : TipoEnfermedades.values()) {
			modeloEnfermedades.addRow(new Object[] { enfermedad });
		}

		alergias = new JLabel("Alergias");
		DefaultTableModel modeloAlergias = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}

		};
		modeloAlergias.addColumn("Alergias");
		meterAlergias = new JTable(modeloAlergias);
		for (TipoAlergias alergia : TipoAlergias.values()) {
			modeloAlergias.addRow(new Object[] { alergia });
		}
		
		
		fechaNac = new JLabel("Fecha Nacimiento");
		meterFechaNac = new JCalendar();

		meterDatos.add(nombreRegistro);
		meterDatos.add(meterNombreRegistro);
		meterDatos.add(correoRegistro);
		meterDatos.add(meterCorreoRegistro);
		meterDatos.add(contraseñaRegistro);
		meterDatos.add(meterContraseñaRegistro);
		meterDatos.add(contraseñaRepetidaRegistro);
		meterDatos.add(meterContraseñaRepetidaRegistro);
		meterDatos.add(apellido1);
		meterDatos.add(meterApellido1);
		meterDatos.add(apellido2);
		meterDatos.add(meterApellido2);
		meterDatos.add(peso);
		meterDatos.add(meterPeso);
		meterDatos.add(altura);
		meterDatos.add(meterAltura);
		meterDatos.add(genero);
		meterDatos.add(generoB);
		meterDatos.add(fechaNac);
		meterDatos.add(meterFechaNac);
		meterDatos.add(enfermedades);
		meterDatos.add(meterEnfermedades);
		meterDatos.add(alergias);
		meterDatos.add(meterAlergias);

		registroScroll = new JScrollPane(meterDatos);
		registro.add(registroScroll, BorderLayout.CENTER);

		iniciarSesion.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				Usuario usuarioSinComprobar = new Usuario();
				usuarioSinComprobar.setNombreUsuario(meterNombreLogeo.getText());
				// Corregir funcion deprecated. Cambiar en Usuario la contraseña a []char.
				// Eso evita no guardar en memoria la contraseña
				usuarioSinComprobar.setContrasena(meterContraseñaLogeo.getText());

				Usuario usuarioComprobado = usuarioContraseñaCorrectos(usuarioSinComprobar);
				if (usuarioComprobado != null) {
					SwingUtilities.invokeLater(() -> new VentanaResumen(usuarioComprobado));
					dispose();

				} else {
					JOptionPane.showMessageDialog(iniciarSesion, "La contraseña o el usuario no son validos", "Error",
							JOptionPane.ERROR_MESSAGE);
					RegistroLogger.anadirLogeo(Level.WARNING, "Contraseña incorrecta");
				}
			}
		});

		registrarse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!meterNombreRegistro.getText().isEmpty() && !meterCorreoRegistro.getText().isEmpty()
						&& !meterContraseñaRepetidaRegistro.getText().isEmpty()
						&& !meterContraseñaRegistro.getText().isEmpty()) {
					if (meterContraseñaRegistro.getText().equals(meterContraseñaRepetidaRegistro.getText())) {
						// crear usuario en base de datos
						RegistroLogger.anadirLogeo(Level.WARNING, "Contraseña incorrecta");
						Usuario usuarioRegistrado = new Usuario();
						usuarioRegistrado.setNombreUsuario(meterNombreRegistro.getText());
						usuarioRegistrado.setCorreoElectronico(meterCorreoRegistro.getText());
						usuarioRegistrado.setContrasena(meterContraseñaRegistro.getText());
						usuarioRegistrado.setApellido1(meterApellido1.getText());
						usuarioRegistrado.setApellido2(meterApellido2.getText());
						usuarioRegistrado.setAltura(Double.parseDouble(meterAltura.getText()));
						usuarioRegistrado.setPeso(Integer.parseInt(meterPeso.getText()));
						if(M.isSelected()) {
							usuarioRegistrado.setSexo(TipoSexo.MUJER);
						}else if(H.isSelected()) {
							usuarioRegistrado.setSexo(TipoSexo.HOMBRE);
						}else {
							usuarioRegistrado.setSexo(TipoSexo.OTRO);
						}
						usuarioRegistrado.setFechaNacimiento(meterFechaNac.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
						usuarioRegistrado.setPermiso(TipoPermiso.USUARIO);
						
						BaseDeDatos.getListaUsuarios().add(usuarioRegistrado);
						SwingUtilities.invokeLater(() -> new VentanaResumen(usuarioRegistrado));
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden",
								"Las contraseñas deben coincidir", JOptionPane.ERROR_MESSAGE);
						RegistroLogger.anadirLogeo(Level.WARNING, "Contraseñas no coinciden");

					}
				} else {
					JOptionPane.showMessageDialog(null, "Los datos introducidos no son correctos", "Error de datos",
							JOptionPane.ERROR_MESSAGE);
					RegistroLogger.anadirLogeo(Level.WARNING, "Datos incorrectos");
				}
			}
		});
		// INICIO RAPIDO PARA NO METER TODO EL RATO USUARIO Y CONTRASEÑA, BORRAR AL
		// ACABAR PROYECTO
		inicioRapido.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Usuario usuarioSinComprobar = new Usuario();
				usuarioSinComprobar.setNombreUsuario("juan_perez");

				usuarioSinComprobar.setContrasena("juan");

				Usuario usuarioComprobado = usuarioContraseñaCorrectos(usuarioSinComprobar);
				if (usuarioComprobado != null) {
					SwingUtilities.invokeLater(() -> new VentanaResumen(usuarioComprobado));
					dispose();
				}
			}
		});

		paneles.add(logeo, "Logeo");
		paneles.add(registro, "Registro");
		this.add(paneles);

		this.setVisible(true);
		this.setSize(500, 400);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Healthy & Fit");
		this.setLocationRelativeTo(null);
	}

//	  public static Usuario usuarioContraseñaCorrectos(Usuario usuarioSinComprobar) {
//		  
//		  for (Usuario usuario : BaseDeDatos.getListaUsuarios()) {
//			if (usuarioSinComprobar.getNombreUsuario().equals(usuario.getNombreUsuario()) && usuarioSinComprobar.getContrasena().equals(usuario.getContrasena())) {
//				  RegistroLogger.anadirLogeo(Level.WARNING, "Inicio de sesion correcto");
//				return usuario;
//			}
//		  }
//		  RegistroLogger.anadirLogeo(Level.WARNING, "Usuario inexistente");
//	      return null;
//	   }

	public static Usuario usuarioContraseñaCorrectos(Usuario usuarioSinComprobar) {

		Connection conn = DBManager.obtenerConexion();

		try {

			PreparedStatement countStmt = conn
					.prepareStatement("SELECT COUNT(*) FROM usuarios WHERE nombreUsuario = ? AND contrasena = ?");
			countStmt.setString(1, usuarioSinComprobar.getNombreUsuario());
			countStmt.setString(2, usuarioSinComprobar.getContrasena());
			ResultSet count = countStmt.executeQuery();
			int size = 0;
			while (count.next()) {
				size = count.getInt(1);
			}

			PreparedStatement pstmt = conn
					.prepareStatement("SELECT * FROM usuarios WHERE nombreUsuario = ? AND contrasena = ?");
			pstmt.setString(1, usuarioSinComprobar.getNombreUsuario());
			pstmt.setString(2, usuarioSinComprobar.getContrasena());

			ResultSet rs = pstmt.executeQuery();

			if (size == 1) {
				RegistroLogger.anadirLogeo(Level.WARNING, "Inicio de sesion correcto");
				while (rs.next()) {
					String nombreUsuario = usuarioSinComprobar.getNombreUsuario();
					String nombre = rs.getString("nombre");
					String apellido1 = rs.getString("apellido1");
					String apellido2 = rs.getString("apellido2");
					LocalDate fechaNacimiento = LocalDate.parse(rs.getString("fechaNacimiento"));
					TipoSexo sexo = TipoSexo.valueOf(rs.getString("sexo"));
					double altura = rs.getDouble("altura");
					int peso = rs.getInt("peso");
					String correoElectronico = rs.getString("correoElectronico");
					int caloriasGastadas = rs.getInt("caloriasGastadas");
					int rachaEntrenamiento = rs.getInt("rachaEntrenamiento");
					String objetivo = rs.getString("objetivo");
					int tiempoEntrenado = rs.getInt("tiempoEntrenado");
					LocalDate ultimaVezEntreno = LocalDate.parse(rs.getString("ultimaVezEntreno"));
					int caloriasConsumidas = rs.getInt("caloriasConsumidas");
					int vasosDeAgua = rs.getInt("vasosDeAgua");
					String contrasena = rs.getString("contrasena");
					ImageIcon foto = new ImageIcon(rs.getBytes("foto"));
					TipoPreferencia preferencia = TipoPreferencia.valueOf(rs.getString("preferenciaAlimenticia"));
					TipoPermiso permiso = TipoPermiso.valueOf(rs.getString("permiso"));

					PreparedStatement pstmtAlergias = conn
							.prepareStatement("SELECT * FROM usuario_alergias WHERE nombreUsuario = ?");
					pstmtAlergias.setString(1, usuarioSinComprobar.getNombreUsuario());
					ResultSet rsAlergias = pstmtAlergias.executeQuery();

					List<TipoAlergias> alergias = new ArrayList<TipoAlergias>();
					while (rsAlergias.next()) {
						alergias.add(TipoAlergias.values()[rsAlergias.getInt("id_alergia")]);
					}

					PreparedStatement pstmtEnfermedades = conn
							.prepareStatement("SELECT * FROM usuario_enfermedades WHERE nombreUsuario = ?");
					pstmtEnfermedades.setString(1, usuarioSinComprobar.getNombreUsuario());
					ResultSet rsEnfermedades = pstmtEnfermedades.executeQuery();

					List<TipoEnfermedades> enfermedades = new ArrayList<TipoEnfermedades>();
					while (rsEnfermedades.next()) {
						enfermedades.add(TipoEnfermedades.values()[rsEnfermedades.getInt("id_enfermedad")]);
					}

					PreparedStatement pstmtProximaComida = conn
							.prepareStatement("SELECT * FROM usuario_dieta WHERE nombreUsuario = ?");
					pstmtProximaComida.setString(1, usuarioSinComprobar.getNombreUsuario());
					ResultSet rsProximaComida = pstmtProximaComida.executeQuery();
					Map<LocalDate, Dieta> proximaComida = new HashMap<LocalDate, Dieta>();
					while (rsProximaComida.next()) {
						LocalDate fecha = LocalDate.parse(rsProximaComida.getString("fecha"));
						String nombreDieta = rsProximaComida.getString("nombreDieta");

						PreparedStatement pstmtObtenerDieta = conn
								.prepareStatement("SELECT * FROM dietas WHERE nombre = ?");
						pstmtObtenerDieta.setString(1, nombreDieta);
						ResultSet rsObtenerDieta = pstmtObtenerDieta.executeQuery();
						while (rsObtenerDieta.next()) {
							int tiempo = rsObtenerDieta.getInt("tiempo");
							TipoDificultad dificultad = TipoDificultad.valueOf(rsObtenerDieta.getString("dificultad"));
							int kcal = rsObtenerDieta.getInt("kcal");

							PreparedStatement pstmtObtenerPasosDieta = conn
									.prepareStatement("SELECT * FROM pasos_dietas WHERE nombreDieta = ?");
							pstmtObtenerPasosDieta.setString(1, nombreDieta);
							ResultSet rsObtenerPasosDieta = pstmtObtenerPasosDieta.executeQuery();
							List<String> pasos = new ArrayList<String>();
							while (rsObtenerPasosDieta.next()) {
								pasos.add(rsObtenerPasosDieta.getString("denominacion"));
							}

							PreparedStatement pstmtObtenerIngredientesDieta = conn
									.prepareStatement("SELECT * FROM ingredientes_dietas WHERE nombreDieta = ?");
							pstmtObtenerIngredientesDieta.setString(1, nombreDieta);
							ResultSet rsObtenerIngredientesDieta = pstmtObtenerIngredientesDieta.executeQuery();
							List<String> ingredientes = new ArrayList<String>();
							while (rsObtenerIngredientesDieta.next()) {
								ingredientes.add(rsObtenerIngredientesDieta.getString("nombreIngrediente"));
							}

							PreparedStatement pstmtObtenerAlergiasDieta = conn
									.prepareStatement("SELECT * FROM dieta_alergias WHERE nombreDieta = ?");
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

						// OBTENER REGISTRO ENTRENAMIENTOS del usuario
						PreparedStatement pstmtRegEntrenamientos = conn.prepareStatement(
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

							Entrenamiento entrenamiento = new Entrenamiento(nombreEntrenamiento, tipoEntrenamiento,
									dificultad, tiempo, descripcion, calorias, series, repeticiones);
							listaEntrenamientos.add(entrenamiento);

							pstmtRegEntrenamientos.close();
						}
						// OBTENER REGISTRO DIETAS del usuario

						PreparedStatement pstmtRegDietas = conn.prepareStatement(
								"SELECT * FROM DIETAS WHERE nombre = (SELECT nombreDieta FROM usuario_dieta WHERE nombreUsuario = ?)");
						pstmtRegDietas.setString(1, nombreUsuario);
						ResultSet rsRegDietas = pstmtRegDietas.executeQuery();

						List<Dieta> listaDietas = new ArrayList<Dieta>();
						while (rsRegDietas.next()) {
							String nombreDietaReg = rsRegDietas.getString("nombre");
							int tiempoDietaReg = rsRegDietas.getInt("tiempo");
							TipoDificultad dificultadDietaReg = TipoDificultad
									.valueOf(rsRegDietas.getString("dificultad"));
							int kcal = rsRegDietas.getInt("kcal");

							PreparedStatement pstmtPasosDieta = conn
									.prepareStatement("SELECT * FROM pasos_dietas WHERE nombreDieta = ?");
							pstmtPasosDieta.setString(1, nombreDietaReg);
							ResultSet rsPasosDietaReg = pstmtPasosDieta.executeQuery();
							List<String> pasosDieta = new ArrayList<String>();
							while (rsPasosDietaReg.next()) {
								String paso = rsPasosDietaReg.getString("denominacion");
								pasosDieta.add(paso);
							}
							pstmtPasosDieta.close();

							PreparedStatement pstmtIngredientesDieta = conn
									.prepareStatement("SELECT * FROM ingredientes_dietas WHERE nombreDieta = ?");
							pstmtIngredientesDieta.setString(1, nombreDietaReg);
							ResultSet rsIngredientesDieta = pstmtIngredientesDieta.executeQuery();
							List<String> ingredientesDieta = new ArrayList<String>();
							while (rsIngredientesDieta.next()) {
								String ingrediente = rsIngredientesDieta.getString("nombreIngrediente");
								ingredientesDieta.add(ingrediente);
							}
							pstmtIngredientesDieta.close();

							PreparedStatement pstmtAlergiaDieta = conn
									.prepareStatement("SELECT * FROM dieta_alergias WHERE nombreDieta = ?");
							pstmtAlergiaDieta.setString(1, nombreDietaReg);
							ResultSet rsAlergiasDieta = pstmtAlergiaDieta.executeQuery();
							List<TipoAlergias> alergiasDieta = new ArrayList<TipoAlergias>();
							while (rsAlergiasDieta.next()) {
								TipoAlergias alergia = TipoAlergias.valueOf(rsAlergiasDieta.getString("alergia"));
								alergiasDieta.add(alergia);
							}
							pstmtAlergiaDieta.close();

							Dieta d = new Dieta(nombreDietaReg, tiempoDietaReg, dificultadDietaReg, kcal, pasosDieta,
									ingredientesDieta, alergiasDieta);
							listaDietas.add(d);
							pstmtRegDietas.close();
						}

						Usuario usuario = new Usuario(nombre, nombreUsuario, apellido1, apellido2, fechaNacimiento,
								sexo, altura, peso, alergias, correoElectronico, enfermedades, preferencia,
								caloriasGastadas, rachaEntrenamiento, objetivo, tiempoEntrenado, ultimaVezEntreno,
								caloriasConsumidas, proximaComida, vasosDeAgua, contrasena, foto, permiso,
								listaEntrenamientos, listaDietas);
						conn.close();
						return usuario;
					}
				}

				// Usuario usuarioCorrecto = new Usuario(nombre, nombreUsuario, apellido1,
				// apellido2, fechaNacimiento, sexo, altura, peso, null, correoElectronico,
				// null, null, caloriasGastadas, rachaEntrenamiento, objetivo, tiempoEntrenado,
				// ultimaVezEntreno, caloriasConsumidas, null, vasosDeAgua, contrasena, foto,
				// null, null, null, null, null)
			}

			RegistroLogger.anadirLogeo(Level.WARNING, "Usuario inexistente");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
