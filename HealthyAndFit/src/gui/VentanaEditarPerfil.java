package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JCalendar;

import db.DBManager;
import domain.TipoAlergias;
import domain.TipoEnfermedades;
import domain.TipoPermiso;
import domain.TipoSexo;
import domain.Usuario;
import io.RegistroLogger;

public class VentanaEditarPerfil extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel panelColumna1;
		private JPanel panelColumna;
			private JTextField fieldNombreUsuario;
			private JLabel labelNombreUsuario;
			
			private JPasswordField fieldContraseña;
			private JLabel labelContraseña;
			
			private JPasswordField fieldContraseña2;
			private JLabel labelrepetirContraseña;
			
			private JTextField fieldNombre;
			private JLabel labelNombre; 
			
			private JTextField fieldApellido1;
			private JLabel labelApellido1;
			
			private JTextField fieldApellido2;
			private JLabel labelApellido2;
			
			private JLabel labelFechaNac;
		
		
	private JPanel panelColumna2;		
		
		private JLabel labelAltura;
		
		private JTextField fieldCorreo;
		private JLabel labelCorreo;
		
		private JLabel labelPeso;
		
		private JLabel labelAleg;
		private JComboBox<TipoAlergias> comboAleg;
		
		private JLabel labelEnfer;
		private JComboBox<TipoEnfermedades> comboEnfer;
		
		private JLabel labelRango;
		private JComboBox<TipoPermiso> comboRango;
		
		ImageIcon imagenResized;

		private JLabel genero;

		private JPanel generoB;

		private ButtonGroup meterGenero;

		private JRadioButton M;

		private JRadioButton O;

		private JRadioButton H;

		private JCalendar meterFechaNac;
		
		private JPanel panelDerecha;

		private Container panelDerecha2;

		private JPanel panelIzquierda;
		
	public VentanaEditarPerfil(Usuario u, Usuario usuarioModificar, VentanaPerfil vPerfil, VentanaPanel vPanel, boolean mostrarRango) {
		
		JPanel panelAbajo = new JPanel(new BorderLayout());
		add(panelAbajo, BorderLayout.SOUTH);
		
		
		//Panel Columna 
		panelColumna = new JPanel(new BorderLayout());
		panelColumna1 = new JPanel();
		panelColumna1.setLayout(new BoxLayout(panelColumna1, BoxLayout.Y_AXIS));
		panelColumna2 = new JPanel();
		panelColumna2.setLayout(new BoxLayout(panelColumna2, BoxLayout.Y_AXIS));
		panelDerecha = new JPanel();
		panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));
		panelDerecha2 = new JPanel(new BorderLayout());
		panelIzquierda = new JPanel(new BorderLayout());
		
		//Para imagen
		Image foto = usuarioModificar.getFoto().getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		JLabel fotoUsuario = new JLabel(new ImageIcon(foto));
		//fotoUsuario.setPreferredSize(new Dimension(300, 200));
		fotoUsuario.setToolTipText("Haz click para elegir otra foto");
		anadirBordeLabel("Fotografía", fotoUsuario);
		panelColumna1.add(fotoUsuario);
		
		//Elementos del panel
		fieldNombreUsuario = new JTextField(usuarioModificar.getNombreUsuario());
		labelNombreUsuario = new JLabel("Nombre Usuario:");
		
		fieldCorreo = new JTextField(usuarioModificar.getCorreoElectronico());
		labelCorreo = new JLabel("Correo");
		
		fieldContraseña = new JPasswordField(usuarioModificar.getContrasena());
		labelContraseña = new JLabel("Contraseña:");
		
		fieldContraseña2 = new JPasswordField(usuarioModificar.getContrasena());
		labelrepetirContraseña = new JLabel("Repetir Contraseña");
		
		fieldNombre = new JTextField(usuarioModificar.getNombre());
		labelNombre = new JLabel("Nombre:");
		
		fieldApellido1 = new JTextField(usuarioModificar.getApellido1());
		labelApellido1 = new JLabel("Primer Apellido:");
		
		fieldApellido2 = new JTextField(usuarioModificar.getApellido2());
		labelApellido2 = new JLabel("Segundo Apellido:");
		
		labelFechaNac = new JLabel(" Fecha nacimiento:");
		
		
		meterFechaNac = new JCalendar();
		meterFechaNac.setDate(Date.from(usuarioModificar.getFechaNacimiento().atStartOfDay(ZoneId.of("Europe/Madrid")).toInstant()));
		
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
		
		switch (usuarioModificar.getSexo()) {
		case HOMBRE:
			meterGenero.setSelected(H.getModel(), true);
			break;
		case MUJER:
			meterGenero.setSelected(M.getModel(), true);
			break;
		default:
			meterGenero.setSelected(O.getModel(), true);
			break;
		}
		
		
		labelAltura = new JLabel("Altura (m)");
		JSpinner meterAltura = new JSpinner();
		meterAltura.setAlignmentX(SwingConstants.CENTER);
        SpinnerNumberModel model1 = new SpinnerNumberModel(usuarioModificar.getAltura(), 0.50, 3.00, 0.01);
        meterAltura.setModel(model1);
        
		labelPeso = new JLabel("Peso (kg)");
		JSpinner meterPeso = new JSpinner();
		meterPeso.setAlignmentX(SwingConstants.CENTER);
        SpinnerNumberModel model2 = new SpinnerNumberModel(usuarioModificar.getPeso(), 1, 300, 1);
        meterPeso.setModel(model2);
		
 
        labelRango = new JLabel("Rango");
        comboRango = new JComboBox<TipoPermiso>(TipoPermiso.values());
        comboRango.setSelectedItem(usuarioModificar.getPermiso());
       
        
        panelColumna1.add(labelRango);
        panelColumna1.add(comboRango);
        
        JScrollPane pane = new JScrollPane(panelColumna1);
        
		labelAleg = new JLabel("Alergias");
		comboAleg = new JComboBox<>(TipoAlergias.values());
		
		labelEnfer = new JLabel("Enfermedades");
		comboEnfer = new JComboBox<>(TipoEnfermedades.values());
		
		JList<TipoEnfermedades> listaEnfermedades = new JList<>();
		DefaultListModel<TipoEnfermedades> modeloEnfermedades = new DefaultListModel<>();
		listaEnfermedades.setModel(modeloEnfermedades);
		if (!usuarioModificar.getEnfermedades().isEmpty()) {
			for (TipoEnfermedades enfermedad : usuarioModificar.getEnfermedades()) {
				modeloEnfermedades.addElement(enfermedad);
			}
			
		}else {
			modeloEnfermedades.addElement(TipoEnfermedades.NINGUNA);
		}
		
		 
		JButton anadirEnfermedad = new JButton("Añadir");
		JButton eliminarEnfermedad = new JButton("Eliminar");
		JPanel panelEnfermedad = new JPanel(new BorderLayout());
		JPanel panelEnfermedadBotones = new JPanel();
		
		panelEnfermedad.add(labelEnfer, BorderLayout.NORTH);
		panelEnfermedad.add(listaEnfermedades,BorderLayout.CENTER);
		panelEnfermedadBotones.add(anadirEnfermedad);
		panelEnfermedadBotones.add(eliminarEnfermedad);
		panelEnfermedad.add(panelEnfermedadBotones, BorderLayout.SOUTH);
		
		anadirEnfermedad.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(
		                null,
		                comboEnfer,
		                "Selecciona una enfermedad",
		                JOptionPane.OK_CANCEL_OPTION,
		                JOptionPane.QUESTION_MESSAGE);
				
				if (result == JOptionPane.OK_OPTION) {
					if (!modeloEnfermedades.contains(comboEnfer.getSelectedItem())) {
						modeloEnfermedades.addElement((TipoEnfermedades) comboEnfer.getSelectedItem());
						if (modeloEnfermedades.contains(TipoEnfermedades.NINGUNA)) {
							modeloEnfermedades.removeElement(TipoEnfermedades.NINGUNA);
						}
					} else {
						JOptionPane.showConfirmDialog(null, "Enfermedad ya añadida", "Error", JOptionPane.PLAIN_MESSAGE);	
					}
					
				}
				
			}
		});
		
		eliminarEnfermedad.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TipoEnfermedades enfermedad = listaEnfermedades.getSelectedValue();
				modeloEnfermedades.removeElement(enfermedad);
				if (modeloEnfermedades.isEmpty()) {
					modeloEnfermedades.addElement(TipoEnfermedades.NINGUNA);
				}
			}
		});
		
		JList<TipoAlergias> listaAlergia = new JList<>();
		DefaultListModel<TipoAlergias> modeloAlergia = new DefaultListModel<>();
		listaAlergia.setModel(modeloAlergia);
		

		JButton anadirAlergia = new JButton("Añadir");
		JButton eliminarAlergia = new JButton("Eliminar");
		JPanel panelAlergia = new JPanel(new BorderLayout());
		JPanel panelAlergiaBotones = new JPanel();
		
		panelAlergia.add(labelAleg, BorderLayout.NORTH);
		panelAlergia.add(listaAlergia,BorderLayout.CENTER);
		panelAlergiaBotones.add(anadirAlergia);
		panelAlergiaBotones.add(eliminarAlergia);
		panelAlergia.add(panelAlergiaBotones, BorderLayout.SOUTH);
		
		if (!usuarioModificar.getAlergias().isEmpty()) {
			for (TipoAlergias alergia : usuarioModificar.getAlergias()) {
				modeloAlergia.addElement(alergia);
			}
		}else {
			modeloAlergia.addElement(TipoAlergias.NINGUNA);
		}
		
		anadirAlergia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(
		                null,
		                comboAleg,
		                "Selecciona una alergia",
		                JOptionPane.OK_CANCEL_OPTION,
		                JOptionPane.QUESTION_MESSAGE);
				
				if (result == JOptionPane.OK_OPTION) {
					if (!modeloAlergia.contains(comboAleg.getSelectedItem())) {
						modeloAlergia.addElement((TipoAlergias) comboAleg.getSelectedItem());
						if (modeloAlergia.contains(TipoAlergias.NINGUNA)) {
							modeloAlergia.removeElement(TipoAlergias.NINGUNA);
						}
					} else {
						JOptionPane.showConfirmDialog(null, "Alergia ya añadida", "Error", JOptionPane.PLAIN_MESSAGE);	
					}
					
				}
				
			}
		});
		
		eliminarAlergia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TipoAlergias alergia = listaAlergia.getSelectedValue();
				modeloAlergia.removeElement(alergia);
				if (modeloAlergia.isEmpty()) {
					modeloAlergia.addElement(TipoAlergias.NINGUNA);
				}
			}
		});
		
		// Botones
		JButton cancelarBot = new JButton("CANCELAR");
		JButton aceptarBot = new JButton("ACEPTAR");
		
		JPanel panelBotonesCanAcep = new JPanel();
			panelBotonesCanAcep.setLayout(new BoxLayout(panelBotonesCanAcep, BoxLayout.X_AXIS));		
			panelBotonesCanAcep.add(cancelarBot);
			panelBotonesCanAcep.add(aceptarBot);
			
		JPanel panelCalendario = new JPanel();
			panelCalendario.setAlignmentX(SwingConstants.CENTER);
			panelCalendario.add(meterFechaNac);
			
		if (mostrarRango) {
			noMostrarRango(comboRango, labelRango);
		}
			
		//Agregar al panel
		
		panelColumna2.add(labelNombreUsuario);
		panelColumna2.add(fieldNombreUsuario);
		panelColumna2.add(labelContraseña);
		panelColumna2.add(fieldContraseña);
		panelColumna2.add(labelrepetirContraseña);
		panelColumna2.add(fieldContraseña2);
		panelColumna2.add(labelCorreo);
		panelColumna2.add(fieldCorreo);
		panelColumna2.add(labelNombre);
		panelColumna2.add(fieldNombre);
		panelColumna2.add(labelApellido1);
		panelColumna2.add(fieldApellido1);
		panelColumna2.add(labelApellido2);
		panelColumna2.add(fieldApellido2);
		panelColumna2.add(labelAltura);
		panelColumna2.add(meterAltura);
		panelColumna2.add(labelPeso);
		panelColumna2.add(meterPeso);
		panelColumna2.add(genero);
		panelColumna2.add(generoB);
		
		panelColumna2.add(labelFechaNac);
		panelColumna2.add(panelCalendario);
		
	
		panelDerecha.add(panelEnfermedad);
		panelDerecha.add(panelAlergia);
		
		
		JScrollPane scroll2 = new JScrollPane(panelDerecha);
		
		JScrollPane scroll = new JScrollPane(panelColumna2);
		panelColumna.add(scroll, BorderLayout.NORTH);
		panelDerecha2.add(scroll2, BorderLayout.NORTH);
		panelIzquierda.add(pane, BorderLayout.NORTH);
		
		add(panelColumna, BorderLayout.CENTER);
		add(panelIzquierda, BorderLayout.WEST);
		add(panelDerecha, BorderLayout.EAST);
		
		add(panelBotonesCanAcep, BorderLayout.SOUTH);
		
		
		aceptarBot.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!(Arrays.equals(fieldContraseña.getPassword(), fieldContraseña2.getPassword()))) {
					JOptionPane.showConfirmDialog(null, "Las contraseñas no coinciden", "Error", JOptionPane.PLAIN_MESSAGE);
				} else if (fieldNombreUsuario.getText().equals("")
						|| fieldNombre.getText().equals("")
						|| fieldApellido1.getText().equals("")
						|| fieldApellido2.getText().equals("")
						|| fieldContraseña.getPassword().length == 0
						|| fieldCorreo.getText().equals("")){
					
					JOptionPane.showConfirmDialog(null, "Hay campos sin rellenar", "Error", JOptionPane.PLAIN_MESSAGE);
					
				} else {
					//cambiar datos
					String nombreAntiguo = usuarioModificar.getNombreUsuario();
					Connection conn = DBManager.obtenerConexion();
					
					try {						
						usuarioModificar.setNombreUsuario(fieldNombreUsuario.getText());
						if (DBManager.existeUsuario(conn, usuarioModificar) && !usuarioModificar.getNombreUsuario().equals(nombreAntiguo)) {
							usuarioModificar.setNombreUsuario(nombreAntiguo);
							JOptionPane.showConfirmDialog(null, "El usuario ya existe", "Error", JOptionPane.PLAIN_MESSAGE);
						} else {
							if (imagenResized != null) {					
								usuarioModificar.setFoto(imagenResized);
								if (vPerfil != null) {
									vPerfil.cambiarFoto(usuarioModificar.getFoto());							
								}
								DBManager.actualizarFotoUsuario(conn, usuarioModificar, imagenResized);
							}	
							usuarioModificar.setContrasena(String.valueOf(fieldContraseña.getPassword())); 
							usuarioModificar.setCorreoElectronico(fieldCorreo.getText());
							usuarioModificar.setNombre(fieldNombre.getText());
							usuarioModificar.setApellido1(fieldApellido1.getText());
							usuarioModificar.setApellido2(fieldApellido2.getText());
							usuarioModificar.setAltura((double) meterAltura.getValue());
							usuarioModificar.setPeso((int) meterPeso.getValue());
							usuarioModificar.setFechaNacimiento(meterFechaNac.getDate().toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate());
							usuarioModificar.setPermiso((TipoPermiso) comboRango.getSelectedItem());
							
							Enumeration<AbstractButton> generos = meterGenero.getElements();
							while (generos.hasMoreElements()) {
								AbstractButton boton = generos.nextElement();
								if (boton.isSelected()) {
									usuarioModificar.setSexo(TipoSexo.valueOf(boton.getText().toUpperCase()));
								}
							}
							
							List<TipoEnfermedades> listaEnfermedades = new ArrayList<TipoEnfermedades>();
							for (int i = 0; i < modeloEnfermedades.getSize(); i++) {
								listaEnfermedades.add(modeloEnfermedades.get(i));
							}
							usuarioModificar.setEnfermedades(new ArrayList<TipoEnfermedades>(listaEnfermedades));
							
							List<TipoAlergias> listaAlergias = new ArrayList<TipoAlergias>();
							for (int i = 0; i < modeloAlergia.getSize(); i++) {
								listaAlergias.add(modeloAlergia.get(i));
							}
							usuarioModificar.setAlergias(new ArrayList<TipoAlergias>(listaAlergias));
							
							
							// El usuario que estamos modificando es uno nuevo
							if (nombreAntiguo.equals("")) {
								DBManager.anadirUsuario(conn, usuarioModificar);
							} else {
								//USUARIOS
								PreparedStatement pstmt = conn.prepareStatement("UPDATE usuarios SET nombreUsuario = ?, nombre = ?, apellido1 = ?, apellido2 = ?, fechaNacimiento = ?, sexo = ?, altura = ?, peso = ?, correoElectronico = ?, imc = ?, contrasena = ?, permiso = ? WHERE nombreUsuario = ?");
								pstmt.setString(1, usuarioModificar.getNombreUsuario());
								pstmt.setString(2, usuarioModificar.getNombre());
								pstmt.setString(3, usuarioModificar.getApellido1());
								pstmt.setString(4, usuarioModificar.getApellido2());
								pstmt.setString(5, usuarioModificar.getFechaNacimiento().toString());
								pstmt.setString(6, usuarioModificar.getSexo().name());
								pstmt.setDouble(7, usuarioModificar.getAltura());
								pstmt.setInt(8, usuarioModificar.getPeso());
								pstmt.setString(9, usuarioModificar.getCorreoElectronico());
								pstmt.setDouble(10, usuarioModificar.getImc());
								pstmt.setString(11, usuarioModificar.getContrasena());
								pstmt.setString(12, usuarioModificar.getPermiso().toString());
								pstmt.setString(13, nombreAntiguo);
								pstmt.executeUpdate();
								pstmt.close();
								
								//DIETA
								PreparedStatement stmtUsuarioDieta = conn.prepareStatement("UPDATE usuario_dieta set nombreUsuario = ? WHERE nombreUsuario = ?");
								stmtUsuarioDieta.setString(1, usuarioModificar.getNombreUsuario());
								stmtUsuarioDieta.setString(2, nombreAntiguo);
								stmtUsuarioDieta.executeUpdate();
								stmtUsuarioDieta.close();
								
								//ALERGIAS
								PreparedStatement stmtUsuarioAlergias = conn.prepareStatement("DELETE FROM usuario_alergias WHERE nombreUsuario = ?");
								stmtUsuarioAlergias.setString(1, nombreAntiguo);
								stmtUsuarioAlergias.executeUpdate();
								stmtUsuarioAlergias.close();
								
								PreparedStatement stmtAnadirAlergias = conn.prepareStatement("INSERT INTO Usuario_alergias values (?, (SELECT id FROM alergias WHERE nombreAlergia = ?))");
								for (TipoAlergias alergia : usuarioModificar.getAlergias()) {
									stmtAnadirAlergias.setString(1, usuarioModificar.getNombreUsuario());
									stmtAnadirAlergias.setString(2, alergia.name());
									stmtAnadirAlergias.executeUpdate();
								}
								stmtAnadirAlergias.close();
								
								//ENFERMEDADES
								PreparedStatement stmtUsuarioEnfermedades = conn.prepareStatement("DELETE FROM usuario_enfermedades WHERE nombreUsuario = ?");
								stmtUsuarioEnfermedades.setString(1, nombreAntiguo);
								stmtUsuarioEnfermedades.executeUpdate();
								stmtUsuarioEnfermedades.close();
								
								PreparedStatement stmtAnadirEnfermedades = conn.prepareStatement("INSERT INTO Usuario_enfermedades values (?, (SELECT id FROM enfermedades WHERE nombreEnfermedad = ?))");
								for (TipoEnfermedades enfermedad : usuarioModificar.getEnfermedades()) {
									stmtAnadirEnfermedades.setString(1, usuarioModificar.getNombreUsuario());
									stmtAnadirEnfermedades.setString(2, enfermedad.name());
									stmtAnadirEnfermedades.executeUpdate();
								}
								stmtAnadirEnfermedades.close();
								
								//ENTRENAMIENTOS
								
								PreparedStatement stmtUsuarioEntrenamientos = conn.prepareStatement("UPDATE usuario_entrenamientos set nombreUsuario = ? WHERE nombreUsuario = ?");
								stmtUsuarioEntrenamientos.setString(1, usuarioModificar.getNombreUsuario());
								stmtUsuarioEntrenamientos.setString(2, nombreAntiguo);
								stmtUsuarioEntrenamientos.executeUpdate();
								stmtUsuarioEntrenamientos.close();
				
							}
							
							conn.close();
							
							
							if (vPerfil != null) {
								SwingUtilities.invokeLater(() -> new VentanaPerfil(usuarioModificar));
							} else {
								SwingUtilities.invokeLater(() -> new VentanaPanel(u));
							}
							dispose();
						}					
						
						
					} catch (SQLException e1) {
						e1.printStackTrace();
						RegistroLogger.anadirLogeo(Level.SEVERE, "ERROR al conectar con la base de datos");
						JOptionPane.showConfirmDialog(null, "ERROR al conectar con la BD", "Error", JOptionPane.PLAIN_MESSAGE);
					}
				}

				
			}
		});
		
		cancelarBot.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int respuesta = JOptionPane.showConfirmDialog(null, "Seguro que quieres salir sin guardar?",
						"Aviso", JOptionPane.YES_NO_OPTION);
				if(respuesta == JOptionPane.YES_OPTION) {
					dispose();
					if (vPerfil != null) {
						SwingUtilities.invokeLater(() -> new VentanaPerfil(usuarioModificar));
					} else {
						SwingUtilities.invokeLater(() -> new VentanaPanel(u));
					}
				}
			}
		});
	

		fotoUsuario.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setDialogTitle("Selecciona una foto para subirla (.png)");
				fileChooser.setFileFilter(new FileNameExtensionFilter("foto.png", "png"));
				int resp = fileChooser.showOpenDialog(VentanaEditarPerfil.this);
				File file = fileChooser.getSelectedFile();
				
				if (resp==JFileChooser.APPROVE_OPTION && file!=null) {					
					try {
						Image imagen = ImageIO.read(file).getScaledInstance(200, 200, Image.SCALE_SMOOTH);;
						imagenResized = new ImageIcon(imagen);
						fotoUsuario.setIcon(imagenResized);
						repaint();
					} catch (IOException e1) {
						RegistroLogger.anadirLogeo(Level.SEVERE, "ERROR al convertir fichero a imagen al subir una foto");
						JOptionPane.showConfirmDialog(null, "ERROR al convertir fichero a imagen", "Error", JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		});
		
		this.pack();
		this.setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Perfil");
	}
	public static void anadirBordeLabel(String titulo, JLabel label) {
		LineBorder borde = new LineBorder(Color.BLACK, 5); //Crea un estilo de borde continuo, anchura 5
		TitledBorder bordeConTitulo = new TitledBorder(borde, titulo); //Añade el estilo de borde con un titulo
		
		bordeConTitulo.setTitleJustification(TitledBorder.CENTER);
		bordeConTitulo.setTitleFont(new Font("Calibri", Font.BOLD, 30));
		label.setBorder(bordeConTitulo);
	}

	public static void noMostrarRango(JComboBox<TipoPermiso> combo, JLabel label) {
		combo.setVisible(false);
		label.setVisible(false);
	}
}
