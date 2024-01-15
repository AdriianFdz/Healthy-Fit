package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import db.DBManager;
import domain.Dieta;
import domain.Entrenamiento;
import domain.Usuario;
import io.ExportarDatos;
import io.RegistroLogger;

public class VentanaPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DefaultTableModel modeloU;
	private DefaultTableModel modeloD;
	private DefaultTableModel modeloE;

	public VentanaPanel(Usuario p) {

		JPanel tablas = new JPanel();
		tablas.setLayout(new BoxLayout(tablas, BoxLayout.Y_AXIS));

		// PANEL USUARIOS
		JPanel usuarios = new JPanel();
		usuarios.setLayout(new BoxLayout(usuarios, BoxLayout.X_AXIS));
		JLabel labelU = new JLabel(" USUARIOS:       ");
		labelU.setFont(new Font("Consolas", Font.BOLD, 20));

		modeloU = new DefaultTableModel() {
	
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		modeloU.addColumn("Nombre Usuario");
		modeloU.addColumn("Nombre");
		modeloU.addColumn("Apellido 1");
		modeloU.addColumn("Apellido 2");
		modeloU.addColumn("Correo");
		modeloU.addColumn("Tipo");

		JTable tablaU = new JTable(modeloU);
		tablaU.setDefaultRenderer(Object.class, new RendererUsuarios());

		JScrollPane scrollU = new JScrollPane(tablaU);

		JPanel panelBotonesU = new JPanel(new GridLayout(4,1));
		JPanel panelBotonesU2 = new JPanel();
		JButton anadirU = new JButton("AÑADIR");
		JButton modificarU = new JButton("MODIFICAR");
		JButton eliminarU = new JButton("ELIMINAR");
		JButton guardarU = new JButton("GUARDAR");
		
		panelBotonesU.add(anadirU);
		panelBotonesU.add(modificarU);
		panelBotonesU.add(eliminarU);
		panelBotonesU.add(guardarU);
		
		panelBotonesU2.add(panelBotonesU);
		
		usuarios.add(labelU);
		usuarios.add(scrollU);
		usuarios.add(panelBotonesU2);

		// PANEL DIETAS
		JPanel dietas = new JPanel();
		dietas.setLayout(new BoxLayout(dietas, BoxLayout.X_AXIS));
		JLabel labelD = new JLabel(" DIETAS:         ");
		labelD.setHorizontalAlignment(JLabel.CENTER);
		labelD.setFont(new Font("Consolas", Font.BOLD, 20));
		
		modeloD = new DefaultTableModel() {
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		modeloD.addColumn("Nombre");
		modeloD.addColumn("Tiempo");
		modeloD.addColumn("Dificultad");
		modeloD.addColumn("Ingredientes");

		JTable tablaD = new JTable(modeloD);
		tablaD.setDefaultRenderer(Object.class, new RendererDietas());


		JScrollPane scrollD = new JScrollPane(tablaD);
		JPanel panelBotonesD2 = new JPanel();
		JPanel panelBotonesD = new JPanel(new GridLayout(4,1));
		JButton anadirD = new JButton("AÑADIR");
		JButton modificarD = new JButton("MODIFICAR");
		JButton eliminarD = new JButton("ELIMINAR");
		JButton guardarD = new JButton("GUARDAR");
		
		panelBotonesD.add(anadirD);
		panelBotonesD.add(modificarD);
		panelBotonesD.add(eliminarD);
		panelBotonesD.add(guardarD);

		panelBotonesD2.add(panelBotonesD);
		
		dietas.add(labelD);
		dietas.add(scrollD);
		dietas.add(panelBotonesD2);

		// PANEL ENTRENAMIENTOS

		JPanel entrenamientos = new JPanel();
		entrenamientos.setLayout(new BoxLayout(entrenamientos, BoxLayout.X_AXIS));
		JLabel labelE = new JLabel(" ENTRENAMIENTOS: ");
		labelE.setFont(new Font("Consolas", Font.BOLD, 20));
		
		modeloE = new DefaultTableModel() {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modeloE.addColumn("Nombre");
		modeloE.addColumn("Tiempo");
		modeloE.addColumn("Dificultad");
		modeloE.addColumn("Kcal");

		JTable tablaE = new JTable(modeloE);
		tablaE.setDefaultRenderer(Object.class, new RendererEntrenamientos());
		
		JScrollPane scrollE = new JScrollPane(tablaE);

		JPanel panelBotonesE = new JPanel(new GridLayout(4,1));
		JPanel panelBotonesE2 = new JPanel();
		JButton anadirE = new JButton("AÑADIR");
		JButton modificarE = new JButton("MODIFICAR");
		JButton eliminarE = new JButton("ELIMINAR");
		JButton guardarE = new JButton("GUARDAR");
		
		panelBotonesE.add(anadirE);
		panelBotonesE.add(modificarE);
		panelBotonesE.add(eliminarE);
		panelBotonesE.add(guardarE);
		
		panelBotonesE2.add(panelBotonesE);

		

		entrenamientos.add(labelE);
		entrenamientos.add(scrollE);
		entrenamientos.add(panelBotonesE2);


		// AÑADIR BOTON VOLVER
		JPanel panelVolver = new JPanel();
		panelVolver.setLayout(new FlowLayout(FlowLayout.LEADING));
		JButton volver = new JButton("VOLVER");
		panelVolver.add(volver);
		
		// METO TODAS LAS TABLAS EN EL PANEL
		tablas.add(usuarios);
		tablas.add(dietas);
		tablas.add(entrenamientos);
		tablas.add(panelVolver);
		this.add(tablas);

		// METODOS PARA RELLENAR DE LA BASE DE DATOS LAS TABLAS
		rellenarUsuarios();
		rellenarDietas();
		rellenarEntrenamientos();
		// AnADIT TOOLTIP A LAS CELDAS DE LAS COLUMNAS PARA QUE SE VEA TODO EL TEXTO DE
		// CADA CELDA
		ToolTipManager.sharedInstance().setInitialDelay(0);
		tablaU.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {

				int fila = tablaU.rowAtPoint(e.getPoint());
				int columna = tablaU.columnAtPoint(e.getPoint());
				tablaU.setToolTipText(modeloU.getValueAt(fila, columna).toString());

			}
		});

		tablaD.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {

				int fila = tablaD.rowAtPoint(e.getPoint());
				int columna = tablaD.columnAtPoint(e.getPoint());
				tablaD.setToolTipText(modeloD.getValueAt(fila, columna).toString());

			}
		});

		tablaE.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {

				int fila = tablaE.rowAtPoint(e.getPoint());
				int columna = tablaE.columnAtPoint(e.getPoint());
				tablaE.setToolTipText(modeloE.getValueAt(fila, columna).toString());

			}
		});

		volver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				SwingUtilities.invokeLater(() -> new VentanaPerfil(p));
				dispose();
			}
		});

		modificarU.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {				
				int usuarioSeleccionado = tablaU.getSelectedRow();
				if (usuarioSeleccionado >= 0) {
					Connection conn = DBManager.obtenerConexion();
					String nombreU = (String) modeloU.getValueAt(usuarioSeleccionado, 0);
					Usuario usuarioAModificar = DBManager.obtenerUsuarios(conn, nombreU);
					try {
						conn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
						RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo conectar con la base de datos");
						JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
					}
					dispose();
					SwingUtilities.invokeLater(() -> new VentanaEditarPerfil(p, usuarioAModificar, null, VentanaPanel.this, false));
				} else {
					JOptionPane.showMessageDialog(null, "Tienes que seleccionar un usuario");
				}

			}
		});

		modificarE.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int entrenamientoSeleccionado = tablaE.getSelectedRow();
				if (entrenamientoSeleccionado >= 0) {
					Connection conn = DBManager.obtenerConexion();
					Entrenamiento entrenamiento = DBManager.obtenerEntrenamientos(conn, (String) modeloE.getValueAt(entrenamientoSeleccionado, 0));
					try {
						conn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
						RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo conectar con la base de datos");
						JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
					}
					
					SwingUtilities.invokeLater(() -> new VentanaEditarEntrenamiento(entrenamiento, p));
					dispose();
		
				} else {
					JOptionPane.showMessageDialog(null, "Tienes que seleccionar un entrenamiento");
				}

			}
		});

		modificarD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablaD.getSelectedRow();
				if (filaSeleccionada >= 0) {
					String nombreD = (String) modeloD.getValueAt(filaSeleccionada, 0);

					Connection conn = DBManager.obtenerConexion();
					Dieta dietaSeleccionada = DBManager.obtenerDietas(conn, nombreD);
					try {
						conn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
						RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo conectar con la base de datos");
						JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
					}
					
					dispose();
					SwingUtilities.invokeLater(() -> new VentanaEditarDieta(dietaSeleccionada, p));
				} else {
					JOptionPane.showMessageDialog(null, "Tienes que seleccionar una dieta");
				}

			}
		});

		// BOTONES PARA AÑADIR USUARIOS, DIETAS Y ENTRENAMIENTOS

		anadirU.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JPanel botonesAnadir = new JPanel();
				ButtonGroup btGroup = new ButtonGroup();
				
					JRadioButton manualBT = new JRadioButton("Manualmente");
					JRadioButton ficheroBT = new JRadioButton("Fichero");
					btGroup.add(manualBT);
					btGroup.add(ficheroBT);
					btGroup.setSelected(manualBT.getModel(), true);
					
				botonesAnadir.add(manualBT);
				botonesAnadir.add(ficheroBT);
				
				
				int opcion = JOptionPane.showOptionDialog(null, botonesAnadir, "Selecciona una opcion", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
				
				if (opcion == JOptionPane.YES_OPTION) {
					if (manualBT.isSelected()) {
						Usuario nuevoUsuario = new Usuario();
						SwingUtilities.invokeLater(() -> new VentanaEditarPerfil(p, nuevoUsuario, null, VentanaPanel.this, false));
						dispose();
					} else {
						ExportarDatos.importarFicheroUsuario();
						rellenarUsuarios();
					}
					
				}
				
			}
		});

		anadirD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JPanel botonesAnadir = new JPanel();
				ButtonGroup btGroup = new ButtonGroup();
				
					JRadioButton manualBT = new JRadioButton("Manualmente");
					JRadioButton ficheroBT = new JRadioButton("Fichero");
					btGroup.add(manualBT);
					btGroup.add(ficheroBT);
					btGroup.setSelected(manualBT.getModel(), true);
					
				botonesAnadir.add(manualBT);
				botonesAnadir.add(ficheroBT);
				
				
				int opcion = JOptionPane.showOptionDialog(null, botonesAnadir, "Selecciona una opcion", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
				
				if (opcion == JOptionPane.YES_OPTION) {
					if (manualBT.isSelected()) {
						Dieta nuevaDieta = new Dieta();
						SwingUtilities.invokeLater(() -> new VentanaEditarDieta(nuevaDieta,p));
						dispose();
					} else {
						ExportarDatos.importarFicheroDieta();
						rellenarDietas();
					}
					
				}
			}
		});

		anadirE.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JPanel botonesAnadir = new JPanel();
				ButtonGroup btGroup = new ButtonGroup();
				
					JRadioButton manualBT = new JRadioButton("Manualmente");
					JRadioButton ficheroBT = new JRadioButton("Fichero");
					btGroup.add(manualBT);
					btGroup.add(ficheroBT);
					btGroup.setSelected(manualBT.getModel(), true);
					
				botonesAnadir.add(manualBT);
				botonesAnadir.add(ficheroBT);
				
				
				int opcion = JOptionPane.showOptionDialog(null, botonesAnadir, "Selecciona una opcion", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
				
				if (opcion == JOptionPane.YES_OPTION) {
					if (manualBT.isSelected()) {
						Entrenamiento nuevoEntrenamiento = new Entrenamiento();
						SwingUtilities.invokeLater(() -> new VentanaEditarEntrenamiento(nuevoEntrenamiento, p));
						dispose();
					} else {
						ExportarDatos.importarFicheroEntrenamiento();
						rellenarEntrenamientos();
					}
					
				}
			}
		});

		// BOTONES PARA ELIMINAR USUARIOS, DIETAS Y ENTRENAMIENTOS

		eliminarU.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int usuarioSeleccionado = tablaU.getSelectedRow();
				if (usuarioSeleccionado >= 0) {
					String nombreU = (String) modeloU.getValueAt(usuarioSeleccionado, 0);
					Connection conn = DBManager.obtenerConexion();
					Usuario usuarioBorrar = DBManager.obtenerUsuarios(conn, nombreU);
					int respuesta = JOptionPane.showConfirmDialog(null, "Seguro que quieres borrar este usuario?",
							"", JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_OPTION) {
						DBManager.eliminarUsuario(conn, usuarioBorrar);
						rellenarUsuarios();
					}
					try {
						conn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
						RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo conectar con la base de datos");
						JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Tienes que seleccionar un usuario");
				}

			}
		});

		eliminarD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int dietaSeleccionada = tablaD.getSelectedRow();
				if (dietaSeleccionada >= 0) {
					String nombreD = (String) modeloD.getValueAt(dietaSeleccionada, 0);
					Connection conn = DBManager.obtenerConexion();
					Dieta dietaEliminar = DBManager.obtenerDietas(conn, nombreD);
					int respuesta = JOptionPane.showConfirmDialog(null, "Seguro que quieres borrar esta dieta?",
							"", JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_OPTION) {
						DBManager.eliminarDieta(conn, dietaEliminar);
						rellenarDietas();
					}
					try {
						conn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
						RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo conectar con la base de datos");
						JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
					}					
				} else {
					JOptionPane.showMessageDialog(null, "Tienes que seleccionar una dieta");
				}

			}
		});

		eliminarE.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int entrenamientoSeleccionado = tablaE.getSelectedRow();
				if (entrenamientoSeleccionado >= 0) {
					String nombreE = (String) modeloE.getValueAt(entrenamientoSeleccionado, 0);
					Connection conn = DBManager.obtenerConexion();
					Entrenamiento entrenamientoEliminar = DBManager.obtenerEntrenamientos(conn, nombreE);
					int respuesta = JOptionPane.showConfirmDialog(null, "Seguro que quieres borrar este entrenamiento?",
							"", JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_OPTION) {
						DBManager.eliminarEntrenamientos(conn, entrenamientoEliminar);
						rellenarEntrenamientos();
					}
					try {
						conn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
						RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo conectar con la base de datos");
						JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "Tienes que seleccionar un entrenamiento");
				}

			}
		});
		
		guardarU.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ExportarDatos.exportarFicheroUsuario();
				
			}
		});
		
		guardarD.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ExportarDatos.exportarFicheroDieta();
				
			}
		});
		
		guardarE.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ExportarDatos.exportarFicheroEntrenamiento();
				
			}
		});

		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Panel");
		this.setSize(800, 500);
	}
	// RENDERER PERSONALIZADO PARA LOS USUARIOS

	public class RendererUsuarios extends JLabel implements TableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			this.setOpaque(true);
			this.setFont(new Font("fuente", Font.PLAIN, 12));
			this.setText(value.toString());
			this.setForeground(Color.black);

			if (!isSelected) {
				if (row % 2 == 0) {
					this.setBackground(new Color(179, 179, 255));
				} else {
					this.setBackground(new Color(189, 154, 255));
				}
			} else {
				this.setBackground(new Color(230, 230, 255));
			}

			return this;
		}

	}

	// RENDERER PERSONALIZADO PARA LAS DIETAS

	public class RendererDietas extends JLabel implements TableCellRenderer {


		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			this.setOpaque(true);
			this.setFont(new Font("fuente", Font.PLAIN, 12));
			this.setText(value.toString());
			this.setForeground(Color.black);

			if (column == 2) {
				String valor = value.toString();
				switch (valor) {
				case "FACIL":
					this.setText("🔥");
					this.setForeground(Color.RED);
					break;
				case "MEDIO":
					this.setText("🔥🔥");
					this.setForeground(Color.RED);
					break;
				case "DIFICIL":
					this.setText("🔥🔥🔥");
					this.setForeground(Color.RED);
					break;

				default:
					this.setText(valor);
					this.setForeground(Color.black);
					break;
				}
			}
			if (!isSelected) {
				if (row % 2 == 0) {
					this.setBackground(new Color(179, 246, 182));
				} else {
					this.setBackground(new Color(189, 236, 182));
				}
			} else {
				this.setBackground(new Color(230, 255, 230));
			}

			return this;
		}

	}

	// RENDERER PERSONALIZADO PARA LOS ENTRENAMIENTOS

	public class RendererEntrenamientos extends JLabel implements TableCellRenderer {


		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			this.setOpaque(true);
			this.setFont(new Font("fuente", Font.PLAIN, 12));
			this.setText(value.toString());
			this.setForeground(Color.black);

			if (column == 2) {
				String valor = value.toString();
				switch (valor) {
				case "FACIL":
					this.setText("🔥");
					this.setForeground(Color.RED);
					break;
				case "MEDIO":
					this.setText("🔥🔥");
					this.setForeground(Color.RED);
					break;
				case "DIFICIL":
					this.setText("🔥🔥🔥");
					this.setForeground(Color.RED);
					break;

				default:
					this.setText(valor);
					this.setForeground(Color.black);
					break;
				}
			}
			if (!isSelected) {
				if (row % 2 == 0) {
					this.setBackground(new Color(255, 192, 192));
				} else {
					this.setBackground(new Color(255, 170, 160));
				}
			} else {
				this.setBackground(new Color(255, 230, 230));
			}

			return this;
		}

	}

	// RELLENAR USUARIOS, DIETAS Y ENTRENAMIENTOS DE LA BD
	public void rellenarUsuarios() {
		modeloU.setRowCount(0);
		Connection conn = DBManager.obtenerConexion();
		List<Usuario> usuarios = DBManager.obtenerUsuarios(conn);
		
		for (Usuario usuario : usuarios) {
			Object[] filaU = { usuario.getNombreUsuario(), usuario.getNombre(), usuario.getApellido1(),
					usuario.getApellido2(), usuario.getCorreoElectronico(), usuario.getPermiso() };
			modeloU.addRow(filaU);
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
		}
	}

	public void rellenarDietas() {
		modeloD.setRowCount(0);
		Connection conn = DBManager.obtenerConexion();
		
		for (Dieta dieta : DBManager.obtenerDietas(conn)) {
			Object[] filaD = { dieta.getNombre(), dieta.getTiempo(), dieta.getDificultad(), dieta.getIngredientes() };
			modeloD.addRow(filaD);
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
		}
	}

	public void rellenarEntrenamientos() {
		modeloE.setRowCount(0);
		Connection conn = DBManager.obtenerConexion();
		List<Entrenamiento> entrenamientos = DBManager.obtenerEntrenamientos(conn);
		
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
		}
	
		
		for (Entrenamiento entrenamiento : entrenamientos) {
			Object[] filaE = { entrenamiento.getNombre(), entrenamiento.getTiempo(), entrenamiento.getDificultad(),
					entrenamiento.getCalorias() };
			modeloE.addRow(filaE);
		}
	}

	// VACIAR USUARIOS,DIETAS y ENTRENAMIENTOS

	public void vaciarUsuarios() {
		modeloU.setRowCount(0);
	}

	public void vaciarDietas() {
		modeloD.setRowCount(0);
	}

	public void vaciarEntrenamientos() {
		modeloE.setRowCount(0);
	}

}
