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

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import domain.Entrenamiento;
import domain.TipoDificultad;
import domain.TipoEnfermedades;
import domain.TipoEntrenamiento;

public class VentanaEntrenamiento extends JFrame{
	
	List<Entrenamiento> entrenamientos = new ArrayList<Entrenamiento>();
	Entrenamiento e1 = new Entrenamiento("Entrenamiento 1", TipoEntrenamiento.SUPERIOR, TipoDificultad.FACIL, 120, "E");
	Entrenamiento e2 = new Entrenamiento("Entrenamiento 2", TipoEntrenamiento.INFERIOR, TipoDificultad.MEDIO, 60, "E");
	Entrenamiento e3 = new Entrenamiento("Entrenamiento 3", TipoEntrenamiento.SUPERIOR, TipoDificultad.DIFICIL, 180, "E");
	
	JButton botonIniciar = new JButton("Iniciar entrenamiento");
	
	public VentanaEntrenamiento() {
		
		
		DefaultListModel<Entrenamiento> modeloListaEntrenamiento = new DefaultListModel<>();
		JList<Entrenamiento> listaEntrenamientos = new JList<Entrenamiento>(modeloListaEntrenamiento);
		ScrollPane scrollListaEntrenamientos = new ScrollPane();
	
		scrollListaEntrenamientos.add(listaEntrenamientos);
	
		add(scrollListaEntrenamientos, BorderLayout.WEST);
		
		modeloListaEntrenamiento.addElement(e1);
		modeloListaEntrenamiento.addElement(e2);
		modeloListaEntrenamiento.addElement(e3);
		
		listaEntrenamientos.setCellRenderer(new RenderListaEntrenamientos());
		
		
		
		
		
		listaEntrenamientos.addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				
				int index = listaEntrenamientos.locationToIndex(new Point(x,y));
				
				Entrenamiento entrenamientoSeleccionado = modeloListaEntrenamiento.get(index);
				
				
				
				
				
			
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
