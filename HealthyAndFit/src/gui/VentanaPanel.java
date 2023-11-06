package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.table.DefaultTableModel;

import db.BaseDeDatos;
import domain.Dieta;
import domain.Entrenamiento;
import domain.Usuario;

public class VentanaPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DefaultTableModel modeloU;
	DefaultTableModel modeloD;
	DefaultTableModel modeloE;

	public VentanaPanel(Usuario p) {

		JPanel tablas = new JPanel(new GridLayout(3, 1));

		// PANEL USUARIOS
		JPanel usuarios = new JPanel(new GridLayout(1, 3));
		JLabel labelU = new JLabel("USUARIOS");

		modeloU = new DefaultTableModel() {
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

		JScrollPane scrollU = new JScrollPane(tablaU);

		JPanel panelBotonesU = new JPanel();
		JButton añadirU = new JButton("AÑADIR");
		JButton modificarU = new JButton("MODIFICAR");
		JButton eliminarU = new JButton("ELIMINAR");
		panelBotonesU.add(añadirU);
		panelBotonesU.add(modificarU);
		panelBotonesU.add(eliminarU);

		usuarios.add(labelU);
		usuarios.add(scrollU);
		usuarios.add(panelBotonesU);

		// PANEL DIETAS
		JPanel dietas = new JPanel(new GridLayout(1, 3));
		JLabel labelD = new JLabel("DIETAS");

		modeloD = new DefaultTableModel() {
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

		JScrollPane scrollD = new JScrollPane(tablaD);

		JPanel panelBotonesD = new JPanel();
		JButton añadirD = new JButton("AÑADIR");
		JButton modificarD = new JButton("MODIFICAR");
		JButton eliminarD = new JButton("ELIMINAR");
		panelBotonesD.add(añadirD);
		panelBotonesD.add(modificarD);
		panelBotonesD.add(eliminarD);

		dietas.add(labelD);
		dietas.add(scrollD);
		dietas.add(panelBotonesD);

		// PANEL ENTRENAMIENTOS

		JPanel entrenamientos = new JPanel(new GridLayout(2, 3));
		JLabel labelE = new JLabel("ENTRENAMIENTOS");

		modeloE = new DefaultTableModel() {
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

		JScrollPane scrollE = new JScrollPane(tablaE);

		JPanel panelBotonesE = new JPanel();
		JButton añadirE = new JButton("AÑADIR");
		JButton modificarE = new JButton("MODIFICAR");
		JButton eliminarE = new JButton("ELIMINAR");
		panelBotonesE.add(añadirE);
		panelBotonesE.add(modificarE);
		panelBotonesE.add(eliminarE);
		
		//AÑADIR BOTON VOLVER
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

		//METODOS PARA RELLENAR DE LA BASE DE DATOS LAS TABLAS
		rellenarUsuarios();
		rellenarDietas();
		rellenarEntrenamientos();
		//AÑADIT TOOLTIP A LAS CELDAS DE LAS COLUMNAS PARA QUE SE VEA TODO EL TEXTO DE CADA CELDA
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
				// TODO Auto-generated method stub
				SwingUtilities.invokeLater(() -> new VentanaEditarUsuario(p));
				dispose();
			}
		});
		
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Panel");
		this.setSize(800, 500);
	}

	public void rellenarUsuarios() {
		for (Usuario usuario : BaseDeDatos.getListaUsuarios()) {
			Object[] filaU = { usuario.getnombreUsuario(), usuario.getNombre(), usuario.getApellido1(),
					usuario.getApellido2(), usuario.getcorreoElectronico(), usuario.getPermiso() };
			modeloU.addRow(filaU);
		}
	}

	public void rellenarDietas() {
		for (Dieta dieta : BaseDeDatos.getListaDietas()) {
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

}
