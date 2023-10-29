package gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VentanaPanel extends JFrame {

	public VentanaPanel() {

		JPanel tablas = new JPanel(new GridLayout(3, 1));

		// PANEL USUARIOS
		JPanel usuarios = new JPanel(new GridLayout(1, 3));
		JLabel labelU = new JLabel("USUARIOS");

		DefaultTableModel modeloU = new DefaultTableModel();
		modeloU.addColumn("Nombre Usuario");
		modeloU.addColumn("Nombre");
		modeloU.addColumn("Apellido 1");
		modeloU.addColumn("Apellido 2");
		modeloU.addColumn("Correo");
		modeloU.addColumn("Tipo");

		JTable tablaU = new JTable(modeloU);

		JScrollPane scrollU = new JScrollPane(tablaU);

		JPanel panelBotonesU = new JPanel(new GridLayout(3, 1));
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

		DefaultTableModel modeloD = new DefaultTableModel();
		modeloD.addColumn("Nombre");
		modeloD.addColumn("Tiempo");
		modeloD.addColumn("Dificultad");
		modeloD.addColumn("Ingredientes");
		modeloD.addColumn("Pasos");

		JTable tablaD = new JTable(modeloD);

		JScrollPane scrollD = new JScrollPane(tablaD);

		JPanel panelBotonesD = new JPanel(new GridLayout(3, 1));
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

		DefaultTableModel modeloE = new DefaultTableModel();
		modeloE.addColumn("Nombre");
		modeloE.addColumn("Tiempo");
		modeloE.addColumn("Dificultad");
		modeloE.addColumn("Kcal");
		modeloE.addColumn("Descripción");

		JTable tablaE = new JTable(modeloE);

		JScrollPane scrollE = new JScrollPane(tablaE);

		JPanel panelBotonesE = new JPanel(new GridLayout(3, 1));
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
		
		

		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Panel");
		this.setSize(1920, 1080);
	}
}
