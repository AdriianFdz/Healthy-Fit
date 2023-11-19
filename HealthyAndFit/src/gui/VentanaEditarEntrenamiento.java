package gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import domain.Entrenamiento;
import domain.TipoDificultad;


public class VentanaEditarEntrenamiento extends JFrame{
	
	public JPanel panelIzquierdo;
		public JLabel labelNombre;
		public JTextField fieldNombre;
		public JLabel labelTiempo;
		public JSpinner spinnerTiempo;
		public JLabel labelDificultad;
		public JComboBox comboDif;
		public JLabel labelKcal;
		public JSpinner spinnerKcal;
		
	public JPanel panelDerecha;
		public JLabel labelDesc;
		public DefaultTableModel descTableModel;
		public JTable tableDesc;
		public JScrollPane paneDesc;
		
	
	public VentanaEditarEntrenamiento(Entrenamiento e) {

		JPanel datos = new JPanel(new GridLayout(1, 2));

		// Panel Izquierdo
		panelIzquierdo = new JPanel(new GridLayout(8, 1));

		// Componentes para el panel izquierdo
		labelNombre = new JLabel("NOMBRE");
		labelTiempo = new JLabel("TIEMPO");
		labelDificultad = new JLabel("DIFICULTAD");
		comboDif = new JComboBox<TipoDificultad>();
		labelKcal = new JLabel("KCAL");

		// Inicializamos con datos
		fieldNombre = new JTextField(e.getNombre());
		spinnerTiempo = new JSpinner(new SpinnerNumberModel(e.getTiempo(), 0, 999, 1));
		comboDif.setSelectedItem(e.getDificultad());
		spinnerKcal = new JSpinner(new SpinnerNumberModel(e.getCalorias(), 0, 9999, 1));

		panelIzquierdo.add(labelNombre);
		panelIzquierdo.add(fieldNombre);
		panelIzquierdo.add(labelTiempo);
		panelIzquierdo.add(spinnerTiempo);
		panelIzquierdo.add(labelDificultad);
		panelIzquierdo.add(comboDif);
		panelIzquierdo.add(labelKcal);
		panelIzquierdo.add(spinnerKcal);
		
		panelDerecha = new JPanel();
		panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));

		//Componentes para el panel de la derecha
		labelDesc = new JLabel("DESCRIPCION");
		descTableModel = new DefaultTableModel();
		descTableModel.addColumn("DESCRIPCION DEL ENTRENAMIENTO");
		
		descTableModel.addRow(new Object[] {e.getDescripcion()});
		
		// Crear la JTable con el modelo
	    tableDesc= new JTable(descTableModel);

	    //Personalizado de la tabla
	    tableDesc.setShowGrid(false);
	    
        JScrollPane panelDcha = new JScrollPane(tableDesc);
		
		// Panel botones
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));

		// Botones
		JButton botonCancelar = new JButton("Cancelar");
		JButton botonConfirmar = new JButton("Confirmar");

		panelBotones.add(botonCancelar);
		panelBotones.add(botonConfirmar);

		// Listeners
		botonCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// SwingUtilities.invokeLater(() -> new VentanaPanel());
				dispose();

			}
			});
			botonConfirmar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

				}
			});

			datos.add(panelIzquierdo);
			datos.add(panelDerecha);

			this.add(datos);
			this.add(panelBotones);
			
			this.setVisible(true);
			this.setSize(600, 500);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setTitle("Editar Entrenamiento");


	}
}
