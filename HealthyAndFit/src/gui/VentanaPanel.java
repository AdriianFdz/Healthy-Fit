package gui;

import java.awt.Color;
import java.awt.Component;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import db.BaseDeDatos;
import domain.Dieta;
import domain.Entrenamiento;
import domain.Usuario;
import io.DBManager;

public class VentanaPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DefaultTableModel modeloU;
	public DefaultTableModel modeloD;
	public DefaultTableModel modeloE;

	public VentanaPanel(Usuario p) {

		JPanel tablas = new JPanel(new GridLayout(3, 1));

		// PANEL USUARIOS
		JPanel usuarios = new JPanel(new GridLayout(1, 3));
		JLabel labelU = new JLabel("  USUARIOS:");
		labelU.setFont(new Font("Consolas", Font.BOLD, 20));

		modeloU = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
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
		tablaU.getColumnModel().getColumn(0).setCellRenderer(new RendererUsuarios());
		tablaU.getColumnModel().getColumn(1).setCellRenderer(new RendererUsuarios());
		tablaU.getColumnModel().getColumn(2).setCellRenderer(new RendererUsuarios());
		tablaU.getColumnModel().getColumn(3).setCellRenderer(new RendererUsuarios());
		tablaU.getColumnModel().getColumn(4).setCellRenderer(new RendererUsuarios());
		tablaU.getColumnModel().getColumn(5).setCellRenderer(new RendererUsuarios());

		JScrollPane scrollU = new JScrollPane(tablaU);

		JPanel panelBotonesU = new JPanel();
		JButton anadirU = new JButton("AÑADIR");
		JButton modificarU = new JButton("MODIFICAR");
		JButton eliminarU = new JButton("ELIMINAR");
		panelBotonesU.add(anadirU);
		panelBotonesU.add(modificarU);
		panelBotonesU.add(eliminarU);

		usuarios.add(labelU);
		usuarios.add(scrollU);
		usuarios.add(panelBotonesU);

		// PANEL DIETAS
		JPanel dietas = new JPanel(new GridLayout(1, 3));
		JLabel labelD = new JLabel("  DIETAS:");
		labelD.setFont(new Font("Consolas", Font.BOLD, 20));

		modeloD = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};

		modeloD.addColumn("Nombre");
		modeloD.addColumn("Tiempo");
		modeloD.addColumn("Dificultad");
		modeloD.addColumn("Ingredientes");

		JTable tablaD = new JTable(modeloD);
		tablaD.getColumnModel().getColumn(0).setCellRenderer(new RendererDietas());
		tablaD.getColumnModel().getColumn(1).setCellRenderer(new RendererDietas());
		tablaD.getColumnModel().getColumn(2).setCellRenderer(new RendererDietas());
		tablaD.getColumnModel().getColumn(3).setCellRenderer(new RendererDietas());

		JScrollPane scrollD = new JScrollPane(tablaD);

		JPanel panelBotonesD = new JPanel();
		JButton anadirD = new JButton("AÑADIR");
		JButton modificarD = new JButton("MODIFICAR");
		JButton eliminarD = new JButton("ELIMINAR");
		panelBotonesD.add(anadirD);
		panelBotonesD.add(modificarD);
		panelBotonesD.add(eliminarD);

		dietas.add(labelD);
		dietas.add(scrollD);
		dietas.add(panelBotonesD);

		// PANEL ENTRENAMIENTOS

		JPanel entrenamientos = new JPanel(new GridLayout(2, 3));
		JLabel labelE = new JLabel("  ENTRENAMIENTOS:");
		labelE.setFont(new Font("Consolas", Font.BOLD, 20));
		
		modeloE = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		modeloE.addColumn("Nombre");
		modeloE.addColumn("Tiempo");
		modeloE.addColumn("Dificultad");
		modeloE.addColumn("Kcal");

		JTable tablaE = new JTable(modeloE);
		tablaE.getColumnModel().getColumn(0).setCellRenderer(new RendererEntrenamientos());
		tablaE.getColumnModel().getColumn(1).setCellRenderer(new RendererEntrenamientos());
		tablaE.getColumnModel().getColumn(2).setCellRenderer(new RendererEntrenamientos());
		tablaE.getColumnModel().getColumn(3).setCellRenderer(new RendererEntrenamientos());

		JScrollPane scrollE = new JScrollPane(tablaE);

		JPanel panelBotonesE = new JPanel();
		JButton anadirE = new JButton("AÑADIR");
		JButton modificarE = new JButton("MODIFICAR");
		JButton eliminarE = new JButton("ELIMINAR");
		panelBotonesE.add(anadirE);
		panelBotonesE.add(modificarE);
		panelBotonesE.add(eliminarE);

		// AÑADIR BOTON VOLVER
		JPanel panelVolver = new JPanel(new GridLayout(3, 3));
		panelVolver.add(new JLabel(""));
		panelVolver.add(new JLabel(""));
		panelVolver.add(new JLabel(""));
		panelVolver.add(new JLabel(""));
		panelVolver.add(new JLabel(""));
		panelVolver.add(new JLabel(""));
		JButton volver = new JButton("Volver");
		panelVolver.add(volver);

		entrenamientos.add(labelE);
		entrenamientos.add(scrollE);
		entrenamientos.add(panelBotonesE);
		entrenamientos.add(panelVolver);
		entrenamientos.add(new JLabel(""));

		// METO TODAS LAS TABLAS EN EL PANEL
		tablas.add(usuarios);
		tablas.add(dietas);
		tablas.add(entrenamientos);
		this.add(tablas);

		// METODOS PARA RELLENAR DE LA BASE DE DATOS LAS TABLAS
		rellenarUsuarios();
		rellenarDietas();
		rellenarEntrenamientos();
		// AnADIT TOOLTIP A LAS CELDAS DE LAS COLUMNAS PARA QUE SE VEA TODO EL TEXTO DE
		// CADA CELDA
		ToolTipManager.sharedInstance().setInitialDelay(0);
		tablaU.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub

				int fila = tablaU.rowAtPoint(e.getPoint());
				int columna = tablaU.columnAtPoint(e.getPoint());
				tablaU.setToolTipText(modeloU.getValueAt(fila, columna).toString());

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		tablaD.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub

				int fila = tablaD.rowAtPoint(e.getPoint());
				int columna = tablaD.columnAtPoint(e.getPoint());
				tablaD.setToolTipText(modeloD.getValueAt(fila, columna).toString());

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		tablaE.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub

				int fila = tablaE.rowAtPoint(e.getPoint());
				int columna = tablaE.columnAtPoint(e.getPoint());
				tablaE.setToolTipText(modeloE.getValueAt(fila, columna).toString());

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		volver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
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
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dispose();
					SwingUtilities.invokeLater(() -> new VentanaEditarPerfil(p, usuarioAModificar, null, VentanaPanel.this));
				} else {
					JOptionPane.showMessageDialog(null, "Tienes que seleccionar un usuario");
				}

			}
		});

		modificarE.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int entrenamientoSeleccionado = tablaE.getSelectedRow();
				Entrenamiento[] E = { null };
				if (entrenamientoSeleccionado >= 0) {
					String nombreE = (String) modeloE.getValueAt(entrenamientoSeleccionado, 0);

					for (Entrenamiento entrenamiento : BaseDeDatos.getListaEntrenamientos()) {
						if (entrenamiento.getNombre().equals(nombreE)) {
							E[0] = entrenamiento;
							break;
						}
					}
					if (E != null) {
						SwingUtilities.invokeLater(() -> new VentanaEditarEntrenamiento(E[0],p));
						dispose();
					}
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
				Usuario nuevoUsuario = new Usuario();
				SwingUtilities.invokeLater(() -> new VentanaEditarPerfil(p, nuevoUsuario, null, VentanaPanel.this));
				dispose();
			}
		});

		anadirD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Dieta nuevaDieta = new Dieta();
				SwingUtilities.invokeLater(() -> new VentanaEditarDieta(nuevaDieta,p));
				dispose();
				
				vaciarDietas();
				rellenarDietas();

			}
		});

		anadirE.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BaseDeDatos.getListaEntrenamientos().add(new Entrenamiento());
				vaciarEntrenamientos();
				rellenarEntrenamientos();

			}
		});

		// BOTONES PARA ELIMINAR USUARIOS, DIETAS Y ENTRENAMIENTOS

		eliminarU.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int usuarioSeleccionado = tablaU.getSelectedRow();
				Usuario[] U = { null };
				if (usuarioSeleccionado >= 0) {
					String nombreU = (String) modeloU.getValueAt(usuarioSeleccionado, 0);

					for (Usuario usuario : BaseDeDatos.getListaUsuarios()) {
						if (usuario.getNombreUsuario().equals(nombreU)) {
							U[0] = usuario;
							break;
						}
					}
					if (U != null) {
						int respuesta = JOptionPane.showConfirmDialog(null, "Seguro que quieres borrar este usuario?",
								"", JOptionPane.YES_NO_OPTION);
						if (respuesta == JOptionPane.YES_OPTION) {
							eliminarUsuario(U[0]);
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Tienes que seleccionar un usuario");
				}

			}
		});

		eliminarD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int dietaSeleccionada = tablaD.getSelectedRow();
				Dieta[] D = { null };
				if (dietaSeleccionada >= 0) {
					String nombreD = (String) modeloD.getValueAt(dietaSeleccionada, 0);

					for (Dieta dieta : BaseDeDatos.getListaDietas()) {
						if (dieta.getNombre().equals(nombreD)) {
							D[0] = dieta;
							break;
						}
					}
					if (D != null) {
						int respuesta = JOptionPane.showConfirmDialog(null, "Seguro que quieres borrar esta dieta?", "",
								JOptionPane.YES_NO_OPTION);
						if (respuesta == JOptionPane.YES_OPTION) {
							eliminarDieta(D[0]);
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Tienes que seleccionar una dieta");
				}

			}
		});

		eliminarE.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int entrenamientoSeleccionado = tablaE.getSelectedRow();
				Entrenamiento[] E = { null };
				if (entrenamientoSeleccionado >= 0) {
					String nombreE = (String) modeloE.getValueAt(entrenamientoSeleccionado, 0);

					for (Entrenamiento entrenamiento : BaseDeDatos.getListaEntrenamientos()) {
						if (entrenamiento.getNombre().equals(nombreE)) {
							E[0] = entrenamiento;
							break;
						}
					}
					if (E != null) {
						int respuesta = JOptionPane.showConfirmDialog(null,
								"Seguro que quieres borrar este entrenamiento?", "", JOptionPane.YES_NO_OPTION);
						if (respuesta == JOptionPane.YES_OPTION) {
							eliminarEntrenamiento(E[0]);
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Tienes que seleccionar un entrenamiento");
				}

			}
		});

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
			// TODO Auto-generated method stub
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

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			// TODO Auto-generated method stub
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

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			// TODO Auto-generated method stub
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void rellenarDietas() {
		Connection conn = DBManager.obtenerConexion();
		
		for (Dieta dieta : DBManager.obtenerDietas(conn)) {
			Object[] filaD = { dieta.getNombre(), dieta.getTiempo(), dieta.getDificultad(), dieta.getIngredientes() };
			modeloD.addRow(filaD);
		}
	}

	public void rellenarEntrenamientos() {
		for (Entrenamiento entrenamiento : BaseDeDatos.getListaEntrenamientos()) {
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

	// ELIMINAR USUARIOS,DIETAS Y ENTRENAMIENTOS
	public void eliminarUsuario(Usuario u) {
		BaseDeDatos.getListaUsuarios().remove(u);
		vaciarUsuarios();
		rellenarUsuarios();
	}

	public void eliminarDieta(Dieta d) {
		BaseDeDatos.getListaDietas().remove(d);
		vaciarDietas();
		rellenarDietas();
	}

	public void eliminarEntrenamiento(Entrenamiento e) {
		BaseDeDatos.getListaEntrenamientos().remove(e);
		vaciarEntrenamientos();
		rellenarEntrenamientos();
	}

}
