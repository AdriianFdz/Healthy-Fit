package gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;


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
		fieldNombre.setText(e.getNombre());
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

	}
}
