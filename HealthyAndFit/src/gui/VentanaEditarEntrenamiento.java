package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import db.DBManager;
import domain.Entrenamiento;
import domain.TipoDificultad;
import domain.TipoEntrenamiento;
import domain.Usuario;
import io.RegistroLogger;


public class VentanaEditarEntrenamiento extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel panelIzquierdo;
		private JLabel labelNombre;
		private JTextField fieldNombre;
		private JLabel labelTiempo;
		private JSpinner spinnerTiempo;
		private JLabel labelDificultad;
		private JComboBox<TipoDificultad> comboDif;
		private JLabel labelTipoEntrenamiento;
		private JComboBox<TipoEntrenamiento> comboEntrenamiento;
		private JLabel labelSeries;
		private JSpinner spinnerSeries;
		private JLabel labelRepeticiones;
		private JSpinner spinnerRepeticiones;
		private JLabel labelKcal;
		private JSpinner spinnerKcal;
		private JLabel fotoEnt;
		
	private JPanel panelDerecha;
		private JLabel labelDesc;
		
	ImageIcon imagenResized;
	Image imagenSinEscalar;
		
		
	public VentanaEditarEntrenamiento(Entrenamiento ent, Usuario p) {
		
		JPanel datos = new JPanel(new GridLayout(1, 2));

		// Panel Izquierdo
		panelIzquierdo = new JPanel();
		panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));

		// Componentes para el panel izquierdo
		labelNombre = new JLabel("NOMBRE");
		fieldNombre = new JTextField(20);
		labelTiempo = new JLabel("TIEMPO/SERIE");
		labelDificultad = new JLabel("DIFICULTAD");
		comboDif = new JComboBox<>(TipoDificultad.values());
		labelKcal = new JLabel("KCAL");
		labelTipoEntrenamiento = new JLabel("TIPO DE ENTRENAMIENTO");
		comboEntrenamiento = new JComboBox<>(TipoEntrenamiento.values());
		labelSeries = new JLabel("SERIES");
		labelRepeticiones = new JLabel("REPETICIONES");
		
		// Inicializamos con datos
		fieldNombre.setText(ent.getNombre());
		spinnerTiempo = new JSpinner(new SpinnerNumberModel(ent.getTiempo(), 0, 999, 1));
		comboDif.setSelectedItem(ent.getDificultad());
		spinnerKcal = new JSpinner(new SpinnerNumberModel(ent.getCalorias(), 0, 9999, 1));
		comboEntrenamiento.setSelectedItem(ent.getTipoEntrenamiento());
		
		spinnerSeries = new JSpinner(new SpinnerNumberModel(ent.getSeries(), 0, 999, 1));
		spinnerRepeticiones = new JSpinner(new SpinnerNumberModel(ent.getRepeticiones(), 0, 999, 1));
		
		Image foto = ent.getFoto().getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		fotoEnt = new JLabel(new ImageIcon(foto));
		fotoEnt.setToolTipText("Haz click para elegir otra foto");
		VentanaEditarPerfil.anadirBordeLabel("Fotografia", fotoEnt);
		
		//Añadimos los elemetos al panel Izquierdo
		panelIzquierdo.add(fotoEnt);
		panelIzquierdo.add(labelNombre);
		panelIzquierdo.add(fieldNombre);
		panelIzquierdo.add(labelTiempo);
		panelIzquierdo.add(spinnerTiempo);
		panelIzquierdo.add(labelDificultad);
		panelIzquierdo.add(comboDif);
		panelIzquierdo.add(labelKcal);
		panelIzquierdo.add(spinnerKcal);
		panelIzquierdo.add(labelTipoEntrenamiento);
		panelIzquierdo.add(comboEntrenamiento);
		panelIzquierdo.add(labelSeries);
		panelIzquierdo.add(spinnerSeries);
		panelIzquierdo.add(labelRepeticiones);
		panelIzquierdo.add(spinnerRepeticiones);

		//Creamos la parte derecha para el scroll panel
		panelDerecha = new JPanel();
		panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));

		//Componentes para el panel de la derecha
		labelDesc = new JLabel("DESCRIPCION");
		
		JTextArea area = new JTextArea(ent.getDescripcion());
		area.setLineWrap(true);
        area.setWrapStyleWord(true);
	
	    
        JScrollPane panelDcha = new JScrollPane(area);
       
        panelDerecha.add(labelDesc);
        panelDerecha.add(panelDcha);
        
        //Añadimos al panel de los datos los dos paneles
        datos.add(panelIzquierdo);
		datos.add(panelDerecha);
		
		// Panel botones
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		// Botones
		JButton botonCancelar = new JButton("CANCELAR");
		JButton botonConfirmar = new JButton("CONFIRMAR");

		// Listeners
		fotoEnt.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setDialogTitle("Selecciona una foto para subirla");
				int resp = fileChooser.showOpenDialog(VentanaEditarEntrenamiento.this);
				File file = fileChooser.getSelectedFile();
				
				if (resp==JFileChooser.APPROVE_OPTION && file!=null) {					
					try {
						imagenSinEscalar = ImageIO.read(file);
						imagenResized = new ImageIcon(imagenSinEscalar.getScaledInstance(200, 200, Image.SCALE_SMOOTH));
						fotoEnt.setIcon(imagenResized);
						repaint();
					} catch (IOException e1) {
						e1.printStackTrace();
						RegistroLogger.getLogger().log(Level.SEVERE, "ERROR al convertir fichero a imagen al subir una foto");
						JOptionPane.showConfirmDialog(null, "ERROR al convertir fichero a imagen", "Error", JOptionPane.PLAIN_MESSAGE);
					}
				}				
			}
		});
		
		botonCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new VentanaPanel(p));
				dispose();

			}
		});
		botonConfirmar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Obtener los nuevos valores de la dieta
				String antiguoNombre = ent.getNombre();
				
				String nuevoNombre = fieldNombre.getText();
				Integer nuevoTiempo = (Integer) spinnerTiempo.getValue();
				TipoDificultad nuevaDif = (TipoDificultad) comboDif.getSelectedItem();
				Integer nuevoKCAL = (Integer) spinnerKcal.getValue();
				TipoEntrenamiento tipoEntrenamiento = TipoEntrenamiento.valueOf(comboEntrenamiento.getSelectedItem().toString());
				int seriesNuevas = (int) spinnerSeries.getValue();
				int repeticionesNuevas = (int) spinnerRepeticiones.getValue();
				String nuevaDescripcion = area.getText();
				
				Connection conn = DBManager.obtenerConexion();
				ent.setNombre(nuevoNombre);
				if (DBManager.existeEntrenamiento(conn, ent) && !ent.getNombre().equals(antiguoNombre)) {
					JOptionPane.showMessageDialog(null, "El nombre del entrenamiento ya existe");
					ent.setNombre(antiguoNombre);

				} else {	
					
					if (imagenResized != null) {					
						ent.setFoto(new ImageIcon(imagenSinEscalar));
						DBManager.actualizarFotoEntrena(conn, ent, new ImageIcon(imagenSinEscalar));
					}
					
					// Actualizar los atributos del Entrenamiento
					ent.setTiempo(nuevoTiempo);
					ent.setDificultad(nuevaDif);
					ent.setCalorias(nuevoKCAL);
					ent.setTipoEntrenamiento(tipoEntrenamiento);
					ent.setSeries(seriesNuevas);
					ent.setRepeticiones(repeticionesNuevas);
					ent.setDescripcion(nuevaDescripcion);
					
					if (antiguoNombre == "") {
						DBManager.anadirEntrenamiento(conn, ent);
					} else {						
						try {	
							PreparedStatement pstmt = conn.prepareStatement("UPDATE entrenamientos set nombre = ?, tipoEntrenamiento = ?, dificultad = ?, tiempo = ?, descripcion = ?, calorias = ?, series = ?, repeticiones = ? WHERE nombre = ?");
							pstmt.setString(1, nuevoNombre);
							pstmt.setString(2, tipoEntrenamiento.name());
							pstmt.setString(3, nuevaDif.name());
							pstmt.setInt(4, nuevoTiempo);
							pstmt.setString(5, nuevaDescripcion);
							pstmt.setInt(6, nuevoKCAL);
							pstmt.setInt(7, seriesNuevas);
							pstmt.setInt(8, repeticionesNuevas);
							pstmt.setString(9, antiguoNombre);
							
							pstmt.executeUpdate();
							pstmt.close();
							
							PreparedStatement stmtUsuarioEntrenamientos = conn.prepareStatement("UPDATE usuario_entrenamientos SET nombreEntrenamiento = ? WHERE nombreEntrenamiento = ?");
							stmtUsuarioEntrenamientos.setString(1, nuevoNombre);
							stmtUsuarioEntrenamientos.setString(2, antiguoNombre);
							stmtUsuarioEntrenamientos.executeUpdate();
							stmtUsuarioEntrenamientos.close();
							
							conn.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
							RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo conectar con la base de datos");
							JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
						}
					}
					// Mostrar un mensaje de éxito
					JOptionPane.showMessageDialog(null, "Cambios guardados correctamente");
					dispose();
					SwingUtilities.invokeLater(() -> new VentanaPanel(p));
				}
				
			}
		});
		
		

		panelBotones.add(botonCancelar, BorderLayout.WEST);
		panelBotones.add(botonConfirmar, BorderLayout.EAST);
		
		this.add(datos, BorderLayout.NORTH);
		this.add(panelBotones, BorderLayout.SOUTH);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setTitle("Editar Entrenamiento");

	}
}
