package gui;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import domain.Entrenamiento;
import domain.TipoDificultad;


public class VentanaEditarEntrenamiento extends JFrame{
	
	JPanel panelIzquierda;
		JLabel labelNombre;
		JTextField fieldNombre;
		JLabel labelTiempo;
		JSpinner spinnerTiempo;
		JLabel labelDificultad;
		JComboBox comboDif;
		JLabel labelKcal;
		JSpinner spinnerKcal;
		
	JPanel panelDerecha;
		JLabel labelDesc;
		JScrollPane paneDesc;
		
	
	public VentanaEditarEntrenamiento(Entrenamiento e) {
		
		JPanel datos = new JPanel(new GridLayout(1,2));
		
		//Panel Izquierdo
		JPanel panelIzquierdo = new JPanel(new GridLayout(8,1));
		
		// Componentes para el panel izquierdo
		labelNombre = new JLabel("NOMBRE");
		labelTiempo = new JLabel("TIEMPO");
		labelDificultad = new JLabel("DIFICULTAD");
		comboDif = new JComboBox<TipoDificultad>();
		labelKcal = new JLabel("KCAL");
		
		//Inicializamos con datos
		fieldNombre.setText(e.getNombre());
		spinnerTiempo = new JSpinner(new SpinnerNumberModel(e.getTiempo(),0,999,1));
		comboDif.setSelectedItem(e.getDificultad());
		spinnerKcal = new JSpinner(new SpinnerNumberModel(e.getCalorias(),0,9999,1));
		
		panelIzquierdo.add(labelNombre);
		panelIzquierdo.add(fieldNombre);
		panelIzquierdo.add(labelTiempo);
		panelIzquierdo.add(spinnerTiempo);
		panelIzquierdo.add(labelDificultad);
		panelIzquierdo.add(comboDif);
		panelIzquierdo.add(labelKcal);
		panelIzquierdo.add(spinnerKcal);
		
		
	}
}
