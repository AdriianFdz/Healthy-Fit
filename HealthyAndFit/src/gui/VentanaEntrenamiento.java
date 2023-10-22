package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import domain.Entrenamiento;
import domain.TipoDificultad;
import domain.TipoEntrenamiento;

public class VentanaEntrenamiento extends JFrame{

	private static final long serialVersionUID = 1L;
	// Propiedades de la ventana
	List<Entrenamiento> entrenamientos = new ArrayList<Entrenamiento>();
	Entrenamiento e1 = new Entrenamiento("Entrenamiento 1", TipoEntrenamiento.SUPERIOR, TipoDificultad.FACIL, 120, "DESCRIPCION1", 50, 5, 3);
	Entrenamiento e2 = new Entrenamiento("Entrenamiento 2", TipoEntrenamiento.INFERIOR, TipoDificultad.MEDIO, 60, "DESCRIPCION2", 70, 2, 7);
	Entrenamiento e3 = new Entrenamiento("Entrenamiento 3", TipoEntrenamiento.SUPERIOR, TipoDificultad.DIFICIL, 180, "DESCRIPCION3", 100, 1, 3);
	
	JButton botonIniciar = new JButton("Iniciar entrenamiento");
	
	Entrenamiento entrenamientoSeleccionado;
	
	
	JList<Entrenamiento> listaEntrenamientos;

	JLabel nombreEntrenamientoSeleccionado;
	JLabel tiempoEntrenamientoSeleccionado;
	JLabel caloriasEntrenamientoSeleccionado;
	JLabel dificultadEntrenamientoSeleccionado;
	JLabel descripcionEntrenamientoSeleccionado;
	
	
	
	
	
	public VentanaEntrenamiento() {
		
		//Definir lista de entrenamientos
		DefaultListModel<Entrenamiento> modeloListaEntrenamiento = new DefaultListModel<>();
		listaEntrenamientos = new JList<Entrenamiento>(modeloListaEntrenamiento);
		ScrollPane scrollListaEntrenamientos = new ScrollPane();
	
		scrollListaEntrenamientos.add(listaEntrenamientos);
	
		//Entrenamientos de ejemplo
		modeloListaEntrenamiento.addElement(e1);
		modeloListaEntrenamiento.addElement(e2);
		modeloListaEntrenamiento.addElement(e3);
		
		listaEntrenamientos.setCellRenderer(new RenderListaEntrenamientos());
		
		//Diseño de la ventana
		JPanel panelIntermedio = new JPanel();
			panelIntermedio.setLayout(new GridLayout(1,2));
			tiempoEntrenamientoSeleccionado = new JLabel("");
			caloriasEntrenamientoSeleccionado = new JLabel("");
			panelIntermedio.add(tiempoEntrenamientoSeleccionado);
			panelIntermedio.add(caloriasEntrenamientoSeleccionado);
			
		JPanel panelEntrenamientoSeleccionado = new JPanel();
			panelEntrenamientoSeleccionado.setLayout(new BoxLayout(panelEntrenamientoSeleccionado, BoxLayout.Y_AXIS));
			nombreEntrenamientoSeleccionado = new JLabel("");
			dificultadEntrenamientoSeleccionado = new JLabel("");
			descripcionEntrenamientoSeleccionado = new JLabel("");	
				
			panelEntrenamientoSeleccionado.add(nombreEntrenamientoSeleccionado);
			panelEntrenamientoSeleccionado.add(panelIntermedio);
			panelEntrenamientoSeleccionado.add(dificultadEntrenamientoSeleccionado);
			panelEntrenamientoSeleccionado.add(descripcionEntrenamientoSeleccionado);
		
		JPanel panelIzquierdoEntrenamientos = new JPanel();
			panelIzquierdoEntrenamientos.add(scrollListaEntrenamientos);
			panelIzquierdoEntrenamientos.add(panelEntrenamientoSeleccionado);
			panelIzquierdoEntrenamientos.add(botonIniciar, BorderLayout.EAST);
		
		add(panelIzquierdoEntrenamientos, BorderLayout.WEST);	
		
		
		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Resumen");		
	}
	
	
	//Render de listaEntrenamientos
	public class RenderListaEntrenamientos extends JLabel implements ListCellRenderer<Entrenamiento>{
		private static final long serialVersionUID = 1L;
		
		Color sinSeleccionar = getBackground();

		@Override
		public Component getListCellRendererComponent(JList<? extends Entrenamiento> list, Entrenamiento value,
				int index, boolean isSelected, boolean cellHasFocus) {

			setText(value.getNombre());
			setOpaque(true);
			setBackground(sinSeleccionar);

			if (isSelected) {
				entrenamientoSeleccionado = value;
				
				setBackground(Color.RED);
				
				nombreEntrenamientoSeleccionado.setText(value.getNombre());
				tiempoEntrenamientoSeleccionado.setText(Integer.toString(value.getTiempo()) + " min");
				caloriasEntrenamientoSeleccionado.setText(Integer.toString(value.getCalorias()) + " kcal");
				dificultadEntrenamientoSeleccionado.setText(value.getDificultad().toString());
				descripcionEntrenamientoSeleccionado.setText(value.getDescripcion());
			}

			return this;
		}
		
	}
	
}
