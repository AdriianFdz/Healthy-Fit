package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;


import domain.Dieta;
import domain.TipoDificultad;


public class VentanaEditarDieta extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JPanel pIzq;
		public JLabel labelNombre;
		public JTextField fieldNombre;
		public JLabel labelTiempo;
		public JSpinner spinnerTiempo;
		public JLabel labelDiff;
		public JComboBox<TipoDificultad> comboDif;
		public JLabel labelKcal;
		public JSpinner spinnerKcal;
		public JLabel labelIng;
		
	public JPanel pDer;
		public JLabel labelPasos;
		public JScrollPane panelDcha;
	
	public VentanaEditarDieta(Dieta d) {
		
		JPanel datos = new JPanel(new GridLayout(1,2));
		JPanel panelBotones = new JPanel();
		
		//Panel izqueirda y componenetes del panel
		pIzq = new JPanel(new GridLayout(10,1));
		
		labelNombre = new JLabel("NOMBRE");
		fieldNombre = new JTextField(20);
		labelTiempo = new JLabel("TIEMPO");
		labelDiff = new JLabel("DIFICULTAD");
		comboDif = new JComboBox<TipoDificultad>();
		labelKcal = new JLabel("KCAL");
		
		//Inicializamos los otros elementos con los datos del usuario
		fieldNombre.setText(d.getNombre());
		spinnerTiempo = new JSpinner(new SpinnerNumberModel(d.getTiempo(),0,999,1));
		comboDif.setSelectedItem(d.getDificultad());
		spinnerKcal = new JSpinner(new SpinnerNumberModel(d.getTiempo(), 0, 9999, 1));
		
		//Agregamos componentes al paneIzq
		pIzq.add(labelNombre);
		pIzq.add(fieldNombre);
		pIzq.add(labelTiempo);
		pIzq.add(spinnerTiempo);
		pIzq.add(labelDiff);
		pIzq.add(comboDif);
		pIzq.add(labelKcal);
		pIzq.add(spinnerKcal);
		
		//Panel derecha
		pDer = new JPanel();
		labelPasos = new JLabel();
		
		
		pDer.add(labelPasos);
		
		
		datos.add(pIzq);
		datos.add(pDer);
		//Crear botones y añadir al panelBotones
		JButton botonCancelar = new JButton("CANCELAR");
		JButton botonConfirmar= new JButton("CONFIRMAR");
		
		panelBotones.add(botonCancelar, BorderLayout.WEST);
		panelBotones.add(botonConfirmar, BorderLayout.EAST);
		
		this.add(datos, BorderLayout.NORTH);
		this.add(panelBotones, BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Editar Usuario");
	}
	
	
}
