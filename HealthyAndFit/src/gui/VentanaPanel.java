package gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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

	public VentanaPanel() {

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

		JPanel entrenamientos = new JPanel(new GridLayout(1, 3));
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

		entrenamientos.add(labelE);
		entrenamientos.add(scrollE);
		entrenamientos.add(panelBotonesE);

		// METO TODAS LAS TABLAS EN EL PANEL
		tablas.add(usuarios);
		tablas.add(dietas);
		tablas.add(entrenamientos);
		this.add(tablas);

		rellenarUsuarios();
		rellenarDietas();
		rellenarEntrenamientos();

		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Panel");
		this.setSize(1000, 500);
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
