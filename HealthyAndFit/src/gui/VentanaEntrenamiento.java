package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.JobPriority;
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
	
	Entrenamiento entrenamientoSeleccionado = e1;
	
	public VentanaEntrenamiento() {
		
		
		DefaultListModel<Entrenamiento> modeloListaEntrenamiento = new DefaultListModel<>();
		JList<Entrenamiento> listaEntrenamientos = new JList<Entrenamiento>(modeloListaEntrenamiento);
		ScrollPane scrollListaEntrenamientos = new ScrollPane();
	
		scrollListaEntrenamientos.add(listaEntrenamientos);
	
		//add(scrollListaEntrenamientos, BorderLayout.WEST);
		
		modeloListaEntrenamiento.addElement(e1);
		modeloListaEntrenamiento.addElement(e2);
		modeloListaEntrenamiento.addElement(e3);
		
		listaEntrenamientos.setCellRenderer(new RenderListaEntrenamientos());
		
		
		JPanel panelIzquierdoEntrenamientos = new JPanel();
		panelIzquierdoEntrenamientos.add(scrollListaEntrenamientos);
		
		
		JPanel panelEntrenamientoSeleccionado = new JPanel(new BorderLayout());		
		JLabel nombreEntrenamientoSeleccionado = new JLabel(entrenamientoSeleccionado.getNombre());
		JLabel tiempoEntrenamientoSeleccionado = new JLabel(Integer.toString(entrenamientoSeleccionado.getTiempo()) + " min");
		JLabel caloriasEntrenamientoSeleccionado = new JLabel(Integer.toString(entrenamientoSeleccionado.getCalorias()) + " kcal");
		JLabel dificultadEntrenamientoSeleccionado = new JLabel(entrenamientoSeleccionado.getDificultad().toString());
		JLabel descripcionEntrenamientoSeleccionado = new JLabel(entrenamientoSeleccionado.getDescripcion());
		
		JPanel panelIntermedio = new JPanel();
		panelEntrenamientoSeleccionado.add(nombreEntrenamientoSeleccionado, BorderLayout.NORTH);
		panelIntermedio.add(tiempoEntrenamientoSeleccionado);
		panelIntermedio.add(caloriasEntrenamientoSeleccionado);
		panelEntrenamientoSeleccionado.add(panelIntermedio);
		
		//panelEntrenamientoSeleccionado.add(dificultadEntrenamientoSeleccionado);
		//panelEntrenamientoSeleccionado.add(descripcionEntrenamientoSeleccionado);
		
		
		panelIzquierdoEntrenamientos.add(panelEntrenamientoSeleccionado);
		
		add(panelIzquierdoEntrenamientos, BorderLayout.WEST);
		
		listaEntrenamientos.addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				
				int index = listaEntrenamientos.locationToIndex(new Point(x,y));
				
				entrenamientoSeleccionado = modeloListaEntrenamiento.get(index);
			
				nombreEntrenamientoSeleccionado.setText(entrenamientoSeleccionado.getNombre());
				tiempoEntrenamientoSeleccionado.setText(Integer.toString(entrenamientoSeleccionado.getTiempo()) + " min");
				caloriasEntrenamientoSeleccionado.setText(Integer.toString(entrenamientoSeleccionado.getCalorias()) + " kcal");
				dificultadEntrenamientoSeleccionado.setText(entrenamientoSeleccionado.getDificultad().toString());
				descripcionEntrenamientoSeleccionado.setText(entrenamientoSeleccionado.getDescripcion());
			
			}
		});
			
		
		
		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Resumen");		
	}
	
	public class RenderListaEntrenamientos extends JLabel implements ListCellRenderer<Entrenamiento>{
		Color defaultBackground = getBackground();

		@Override
		public Component getListCellRendererComponent(JList<? extends Entrenamiento> list, Entrenamiento value,
				int index, boolean isSelected, boolean cellHasFocus) {

			setText(value.getNombre());
			setOpaque(true);
			
			
			setBackground(defaultBackground);
			if (isSelected) {
				setBackground(Color.LIGHT_GRAY);
			}			
			return this;
		}
		
	}
	
}
