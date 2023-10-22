package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.JobPriority;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import domain.Entrenamiento;
import domain.TipoDificultad;
import domain.TipoEnfermedades;
import domain.TipoEntrenamiento;

public class VentanaEntrenamiento extends JFrame{
	
	List<Entrenamiento> entrenamientos = new ArrayList<Entrenamiento>();
	Entrenamiento e1 = new Entrenamiento("Entrenamiento 1", TipoEntrenamiento.SUPERIOR, TipoDificultad.FACIL, 120, "DESCRIPCION1", 50);
	Entrenamiento e2 = new Entrenamiento("Entrenamiento 2", TipoEntrenamiento.INFERIOR, TipoDificultad.MEDIO, 60, "DESCRIPCION2", 70);
	Entrenamiento e3 = new Entrenamiento("Entrenamiento 3", TipoEntrenamiento.SUPERIOR, TipoDificultad.DIFICIL, 180, "DESCRIPCION3", 100);
	
	JButton botonIniciar = new JButton("Iniciar entrenamiento");
	
	Entrenamiento entrenamientoSeleccionado;
	
	
	JList<Entrenamiento> listaEntrenamientos;

	JLabel nombreEntrenamientoSeleccionado;
	JLabel tiempoEntrenamientoSeleccionado;
	JLabel caloriasEntrenamientoSeleccionado;
	JLabel dificultadEntrenamientoSeleccionado;
	JLabel descripcionEntrenamientoSeleccionado;
	
	
	
	
	
	public VentanaEntrenamiento() {
		
		
		DefaultListModel<Entrenamiento> modeloListaEntrenamiento = new DefaultListModel<>();
		listaEntrenamientos = new JList<Entrenamiento>(modeloListaEntrenamiento);
		ScrollPane scrollListaEntrenamientos = new ScrollPane();
	
		scrollListaEntrenamientos.add(listaEntrenamientos);
	
		
		modeloListaEntrenamiento.addElement(e1);
		modeloListaEntrenamiento.addElement(e2);
		modeloListaEntrenamiento.addElement(e3);
		
		listaEntrenamientos.setCellRenderer(new RenderListaEntrenamientos());
		
		
		JPanel panelIzquierdoEntrenamientos = new JPanel();
		panelIzquierdoEntrenamientos.add(scrollListaEntrenamientos);
				
		JPanel panelEntrenamientoSeleccionado = new JPanel();
		
		panelEntrenamientoSeleccionado.setLayout(new BoxLayout(panelEntrenamientoSeleccionado, BoxLayout.Y_AXIS));
		nombreEntrenamientoSeleccionado = new JLabel("");
		tiempoEntrenamientoSeleccionado = new JLabel("");
		caloriasEntrenamientoSeleccionado = new JLabel("");
		dificultadEntrenamientoSeleccionado = new JLabel("");
		descripcionEntrenamientoSeleccionado = new JLabel("");
		
		JPanel panelIntermedio = new JPanel();
		panelIntermedio.setLayout(new GridLayout(1,2));
		panelEntrenamientoSeleccionado.add(nombreEntrenamientoSeleccionado);
		panelIntermedio.add(tiempoEntrenamientoSeleccionado);
		panelIntermedio.add(caloriasEntrenamientoSeleccionado);
		panelEntrenamientoSeleccionado.add(panelIntermedio);
		panelEntrenamientoSeleccionado.add(dificultadEntrenamientoSeleccionado);
		panelEntrenamientoSeleccionado.add(descripcionEntrenamientoSeleccionado);
		
		
		panelIzquierdoEntrenamientos.add(panelEntrenamientoSeleccionado);
		
		add(panelIzquierdoEntrenamientos, BorderLayout.WEST);	
		
		panelIzquierdoEntrenamientos.add(botonIniciar, BorderLayout.EAST);
		
		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Resumen");		
	}
	
	public class RenderListaEntrenamientos extends JLabel implements ListCellRenderer<Entrenamiento>{
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
				
				
				repaint();
			}

			return this;
		}
		
	}
	
}
