package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import com.toedter.calendar.JCalendar;

import db.DBManager;
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

public class VentanaLogeoRegistro extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTabbedPane paneles;

	private JPanel logeo;
	private JPanel logeoIzquierda;
	private JLabel preguntaYaTienesCuenta;
	private JLabel nombreLogeo;
	private JTextField meterNombreLogeo;
	private JLabel contraseñaLogeo;
	private JPasswordField meterContraseñaLogeo;
	private JButton iniciarSesion;

	// INFORMACIÓN PRIMARIA PARA REGISTRARSE

	private JPanel registro;
	private JScrollPane registroScroll;
	private JPanel meterDatos;
	private JLabel preguntaTodaviaSinCuenta;

	private JLabel nombreRegistro;
	private JTextField meterNombreRegistro;

	private JLabel correoRegistro;
	private JTextField meterCorreoRegistro;

	private JLabel contraseñaRegistro;
	private JPasswordField meterContraseñaRegistro;

	private JLabel contraseñaRepetidaRegistro;
	private JPasswordField meterContraseñaRepetidaRegistro;

	private JTextField meterNombre;
	
	private JLabel apellido1;
	private JTextField meterApellido1;

	private JLabel apellido2;
	private JTextField meterApellido2;

	private JLabel altura;

	private JLabel peso;

	private JLabel foto;
	
	private JLabel genero;
	private JPanel generoB;
	private ButtonGroup meterGenero;
	private JRadioButton M;
	private JRadioButton H;
	private JRadioButton O;

	private JLabel fechaNac;
	private JCalendar meterFechaNac;

	private JButton registrarse;

	public VentanaLogeoRegistro() {
		
		paneles = new JTabbedPane(); // El panel para seleccionar la ventana de logeo o registro

		logeo = new JPanel();

		logeoIzquierda = new JPanel();
		logeoIzquierda.setLayout(new BoxLayout(logeoIzquierda, BoxLayout.Y_AXIS));

		preguntaYaTienesCuenta = new JLabel("¿Ya tienes cuenta?");
		Font fuente = preguntaYaTienesCuenta.getFont();
		preguntaYaTienesCuenta.setFont(new Font(fuente.getFontName(), Font.PLAIN, 25));
		logeoIzquierda.add(preguntaYaTienesCuenta);
		
		JSeparator separador = new JSeparator();
		separador.setForeground(Color.BLACK);
		logeoIzquierda.add(separador);

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
		
				
		foto = new JLabel();
		ImageIcon imagen = null;
		try {
			imagen = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/logo.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
        Image imagen2 = imagen.getImage().getScaledInstance(330, 280, Image.SCALE_SMOOTH);
		foto.setIcon(new ImageIcon(imagen2));
		foto.setHorizontalAlignment(JLabel.CENTER);
		logeoIzquierda.add(foto);
		
		logeo.add(logeoIzquierda);

		// REGISTRO

		registro = new JPanel(new BorderLayout());
		preguntaTodaviaSinCuenta = new JLabel("¿Todavía no tienes cuenta?");
		preguntaTodaviaSinCuenta.setFont(new Font(fuente.getFontName(), Font.PLAIN, 25));
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

		JLabel nombre = new JLabel("Nombre");
		meterNombre= new JTextField();

		apellido1 = new JLabel("Primer Apellido");
		meterApellido1 = new JTextField();

		apellido2 = new JLabel("Segundo Apellido");
		meterApellido2 = new JTextField();

		altura = new JLabel("Altura (m)");
		JSpinner meterAltura = new JSpinner();
		meterAltura.setAlignmentX(SwingConstants.CENTER);
        SpinnerNumberModel model1 = new SpinnerNumberModel(0.500, 0.000, 3.000, 0.01);
        meterAltura.setModel(model1);
		
        model1.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if ((double) model1.getValue() < 0.500) {
					model1.setValue(0.500);
				}
				
			}
		});
        
		peso = new JLabel("Peso (kg)");
		JSpinner meterPeso = new JSpinner();
		meterPeso.setAlignmentX(SwingConstants.CENTER);
        SpinnerNumberModel model2 = new SpinnerNumberModel(1, 1, 300, 1);
        meterPeso.setModel(model2);

		genero = new JLabel("Genero");
		generoB = new JPanel();
		generoB.setLayout(new BoxLayout(generoB, BoxLayout.X_AXIS));
		generoB.setAlignmentX(SwingConstants.CENTER);
		meterGenero = new ButtonGroup();
		M = new JRadioButton("Mujer");
		H = new JRadioButton("Hombre");
		O = new JRadioButton("Otro");
		meterGenero.add(H);
		meterGenero.add(M);
		meterGenero.add(O);
		generoB.add(M);
		generoB.add(H);
		generoB.add(O);

		
		fechaNac = new JLabel(" Fecha Nacimiento");
		meterFechaNac = new JCalendar();

		meterDatos.add(nombreRegistro);
		meterDatos.add(meterNombreRegistro);
		meterDatos.add(correoRegistro);
		meterDatos.add(meterCorreoRegistro);
		meterDatos.add(contraseñaRegistro);
		meterDatos.add(meterContraseñaRegistro);
		meterDatos.add(contraseñaRepetidaRegistro);
		meterDatos.add(meterContraseñaRepetidaRegistro);
		meterDatos.add(nombre);
		meterDatos.add(meterNombre);
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
		
		JPanel panelCalendario = new JPanel();
		panelCalendario.setAlignmentX(SwingConstants.CENTER);
		panelCalendario.add(meterFechaNac);
		meterDatos.add(panelCalendario);

		registroScroll = new JScrollPane(meterDatos);
		registro.add(registroScroll, BorderLayout.CENTER);

		iniciarSesion.addActionListener(new ActionListener() {

			
			@Override
			public void actionPerformed(ActionEvent e) {
				Usuario usuarioSinComprobar = new Usuario();
				usuarioSinComprobar.setNombreUsuario(meterNombreLogeo.getText());
				usuarioSinComprobar.setContrasena(String.valueOf(meterContraseñaLogeo.getPassword()));

				Usuario usuarioComprobado = usuarioContraseñaCorrectos(usuarioSinComprobar);
				if (usuarioComprobado != null) {
					SwingUtilities.invokeLater(() -> new VentanaResumen(usuarioComprobado));
					dispose();

				} else {
					JOptionPane.showMessageDialog(iniciarSesion, "La contraseña o el usuario no son validos", "Error",
							JOptionPane.ERROR_MESSAGE);
					RegistroLogger.getLogger().log(Level.WARNING, "Contraseña incorrecta");
				}
			}
		});

		registrarse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (
						!meterNombre.getText().isEmpty()
						&& !meterNombreRegistro.getText().isBlank()
						&& !meterApellido1.getText().isBlank()
						&& !meterApellido2.getText().isBlank() 
						&& meterFechaNac.getDate() != null
						&& meterGenero.getButtonCount() != 0
						&& !meterCorreoRegistro.getText().isBlank()
						&& meterContraseñaRegistro.getPassword().length != 0
						&& meterContraseñaRepetidaRegistro.getPassword().length != 0		
						) {
								
								
					
					if (Arrays.equals(meterContraseñaRegistro.getPassword(), meterContraseñaRepetidaRegistro.getPassword())) {
						// crear usuario en base de datos
						
						String nombre = meterNombre.getText();
						String nombreUsuario = meterNombreRegistro.getText();
						String apellido1 = meterApellido1.getText();
						String apellido2 = meterApellido2.getText();
						LocalDate fechaNacimiento = meterFechaNac.getDate().toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate();
						TipoSexo sexo = TipoSexo.OTRO;
						Enumeration<AbstractButton> elementosBotones = meterGenero.getElements();
						while(elementosBotones.hasMoreElements()) {	
							AbstractButton botonSexo = elementosBotones.nextElement();
							if (botonSexo.isSelected()) {
								sexo = TipoSexo.valueOf(botonSexo.getText().toUpperCase());
							}
						}
						
						double altura = (Double)meterAltura.getValue();
						int peso = (int)meterPeso.getValue();
						String correoElectronico = meterCorreoRegistro.getText();
						List<TipoAlergias> alergias = new ArrayList<TipoAlergias>();
						List<TipoEnfermedades> enfermedades = new ArrayList<TipoEnfermedades>();
						int caloriasGastadas = 0;
						int tiempoEntrenado = 0;
						LocalDate ultimaVezEntreno = LocalDate.now(); 
						int caloriasConsumidas = 0;
						Map<LocalDate, Dieta> proximaComida = new HashMap<LocalDate, Dieta>();
						int vasosDeAgua = 0;
						String contrasena = String.valueOf(meterContraseñaRegistro.getPassword());
						ImageIcon foto = null;
						try {
							foto = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/foto.png")));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						TipoPermiso permiso = TipoPermiso.USUARIO;
						List<Entrenamiento> registroEntrenamiento = new ArrayList<Entrenamiento>();
						
						Usuario usuarioRegistrado = new Usuario(nombre, nombreUsuario, apellido1, apellido2, fechaNacimiento, sexo, altura, peso, alergias, correoElectronico, enfermedades, caloriasGastadas, tiempoEntrenado, ultimaVezEntreno, caloriasConsumidas, proximaComida, vasosDeAgua, contrasena, foto, permiso, registroEntrenamiento);
											
						try {
							Connection conn = DBManager.obtenerConexion();
							DBManager.anadirUsuario(conn, usuarioRegistrado);
							conn.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
							RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo conectar con la base de datos");
							JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
						}
						
						SwingUtilities.invokeLater(() -> new VentanaResumen(usuarioRegistrado));
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden",
								"Las contraseñas deben coincidir", JOptionPane.ERROR_MESSAGE);
						RegistroLogger.getLogger().log(Level.WARNING, "Contraseñas no coinciden");

					}
				} else {
					JOptionPane.showMessageDialog(null, "Los datos introducidos no son correctos", "Error de datos",
							JOptionPane.ERROR_MESSAGE);
					RegistroLogger.getLogger().log(Level.WARNING, "Datos incorrectos");
				}
			}
		});
		
		
		meterContraseñaLogeo.addKeyListener(new KeyAdapter() {
			
			
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					iniciarSesion.doClick();
				}
				
			}
		
	
		});
		
		meterNombreLogeo.addKeyListener(new KeyAdapter() {
			
			
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					meterContraseñaLogeo.requestFocus();
				}
				
			}
		
	
		});
		
		meterNombreLogeo.addKeyListener(new KeyAdapter() {
			
			
			@Override
			public void keyPressed(KeyEvent e) {
		
				if (e.getKeyCode() == KeyEvent.VK_DOWN)
					meterContraseñaLogeo.requestFocus();
				
				if (e.getKeyCode() == KeyEvent.VK_UP)
					meterContraseñaLogeo.requestFocus();
			
			
				
		
			}
		});
		
		meterContraseñaLogeo.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DOWN)
					meterNombreLogeo.requestFocus();
				
				if (e.getKeyCode() == KeyEvent.VK_UP)
					meterNombreLogeo.requestFocus();
				
			}
		});
		
		paneles.add(logeo, "Logeo");
		paneles.add(registro, "Registro");
		this.add(paneles);

		this.setSize(500,500);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Healthy & Fit");
		this.setLocationRelativeTo(null);
	}

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
					int tiempoEntrenado = rs.getInt("tiempoEntrenado");
					LocalDate ultimaVezEntreno = LocalDate.parse(rs.getString("ultimaVezEntreno"));
					int caloriasConsumidas = rs.getInt("caloriasConsumidas");
					int vasosDeAgua = rs.getInt("vasosDeAgua");
					String contrasena = rs.getString("contrasena");
					ImageIcon foto = new ImageIcon(rs.getBytes("foto"));
					String permisoStr = rs.getString("permiso");
					TipoPermiso permiso = TipoPermiso.valueOf(permisoStr);

					PreparedStatement pstmtAlergias = conn.prepareStatement("SELECT * FROM usuario_alergias WHERE nombreUsuario = ?");
					pstmtAlergias.setString(1, usuarioSinComprobar.getNombreUsuario());
					ResultSet rsAlergias = pstmtAlergias.executeQuery();

					List<TipoAlergias> alergias = new ArrayList<TipoAlergias>();
					while (rsAlergias.next()) {
						alergias.add(TipoAlergias.values()[rsAlergias.getInt("id_alergia")-1]);
					}

					PreparedStatement pstmtEnfermedades = conn.prepareStatement("SELECT * FROM usuario_enfermedades WHERE nombreUsuario = ?");
					pstmtEnfermedades.setString(1, usuarioSinComprobar.getNombreUsuario());
					ResultSet rsEnfermedades = pstmtEnfermedades.executeQuery();

					List<TipoEnfermedades> enfermedades = new ArrayList<TipoEnfermedades>();
					while (rsEnfermedades.next()) {
						enfermedades.add(TipoEnfermedades.values()[rsEnfermedades.getInt("id_enfermedad")-1]);
					}

					PreparedStatement pstmtProximaComida = conn.prepareStatement("SELECT * FROM usuario_dieta WHERE nombreUsuario = ?");
					pstmtProximaComida.setString(1, usuarioSinComprobar.getNombreUsuario());
					ResultSet rsProximaComida = pstmtProximaComida.executeQuery();
					Map<LocalDate, Dieta> proximaComida = new HashMap<LocalDate, Dieta>();
					while (rsProximaComida.next()) {
						LocalDate fecha = LocalDate.parse(rsProximaComida.getString("fecha"));
						String nombreDieta = rsProximaComida.getString("nombreDieta");

						PreparedStatement pstmtObtenerDieta = conn.prepareStatement("SELECT * FROM dietas WHERE nombre = ?");
						pstmtObtenerDieta.setString(1, nombreDieta);
						ResultSet rsObtenerDieta = pstmtObtenerDieta.executeQuery();
						while (rsObtenerDieta.next()) {
							int tiempo = rsObtenerDieta.getInt("tiempo");
							TipoDificultad dificultad = TipoDificultad.valueOf(rsObtenerDieta.getString("dificultad"));
							int kcal = rsObtenerDieta.getInt("kcal");

							PreparedStatement pstmtObtenerPasosDieta = conn.prepareStatement("SELECT * FROM pasos_dietas WHERE nombreDieta = ?");
							pstmtObtenerPasosDieta.setString(1, nombreDieta);
							ResultSet rsObtenerPasosDieta = pstmtObtenerPasosDieta.executeQuery();
							List<String> pasos = new ArrayList<String>();
							while (rsObtenerPasosDieta.next()) {
								pasos.add(rsObtenerPasosDieta.getString("denominacion"));
							}

							PreparedStatement pstmtObtenerIngredientesDieta = conn.prepareStatement("SELECT * FROM ingredientes_dietas WHERE nombreDieta = ?");
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
					}

					// OBTENER REGISTRO ENTRENAMIENTOS del usuario
					PreparedStatement pstmtRegEntrenamientos = conn.prepareStatement(
							"SELECT entrenamientos.* FROM entrenamientos, usuario_entrenamientos WHERE usuario_entrenamientos.nombreUsuario = ? AND entrenamientos.nombre = usuario_entrenamientos.nombreEntrenamiento");
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

					}
					pstmtRegEntrenamientos.close();
					Usuario usuario = new Usuario(nombre, nombreUsuario, apellido1, apellido2, fechaNacimiento,
							sexo, altura, peso, alergias, correoElectronico, enfermedades,
							caloriasGastadas, tiempoEntrenado, ultimaVezEntreno,
							caloriasConsumidas, proximaComida, vasosDeAgua, contrasena, foto, permiso,
							listaEntrenamientos);
					conn.close();
					RegistroLogger.getLogger().log(Level.WARNING, "Inicio de sesion correcto");
					return usuario;
				}

			}

			RegistroLogger.getLogger().log(Level.WARNING, "Usuario inexistente");
		} catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
		}

		return null;
	}

}
