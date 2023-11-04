package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import domain.Entrenamiento;
import domain.TipoDificultad;
import domain.TipoEntrenamiento;
import domain.Usuario;

public class VentanaEntrenamiento extends JFrame{

	private static final long serialVersionUID = 1L;
	// Propiedades de la ventana
	List<Entrenamiento> entrenamientos = new ArrayList<Entrenamiento>(); //Falta vincular la BD con los datos de ejemplo
	Entrenamiento e1 = new Entrenamiento("Entrenamiento 1", TipoEntrenamiento.SUPERIOR, TipoDificultad.FACIL, 120, "DESCRIPCIÓN1", 50, 5, 3);
	Entrenamiento e2 = new Entrenamiento("Entrenamiento 2", TipoEntrenamiento.INFERIOR, TipoDificultad.MEDIO, 60, "DESCRIPCIÓN2", 70, 2, 7);
	Entrenamiento e3 = new Entrenamiento("Entrenamiento 3", TipoEntrenamiento.SUPERIOR, TipoDificultad.DIFICIL, 180, "DESCRIPCIÓN3", 100, 1, 3);
	
	JButton botonIniciar = new JButton("Iniciar entrenamiento");
	
	Entrenamiento entrenamientoSeleccionado;
	
	
	JList<Entrenamiento> listaEntrenamientos;

	JLabel tiempoEntrenamientoSeleccionado;
	JLabel caloriasEntrenamientoSeleccionado;
	JLabel dificultadEntrenamientoSeleccionado;
	JLabel labelDescripcion;
	JLabel descripcionEntrenamientoSeleccionado;
	
	JPanel panelEntrenamientoSeleccionado = new JPanel();

	
	
	
	
	public VentanaEntrenamiento(Usuario persona) {
		
		botonIniciar.setVisible(false);
		//Definir lista de entrenamientos
				
		DefaultListModel<Entrenamiento> modeloListaEntrenamiento = new DefaultListModel<>();
		listaEntrenamientos = new JList<Entrenamiento>(modeloListaEntrenamiento);

	
		//Entrenamientos de ejemplo
		modeloListaEntrenamiento.addElement(e1);
		modeloListaEntrenamiento.addElement(e2);
		modeloListaEntrenamiento.addElement(e3);
		
		listaEntrenamientos.setCellRenderer(new RenderListaEntrenamientos());
		
		//Diseño de la ventana
		ScrollPane scrollListaEntrenamientos = new ScrollPane();
		scrollListaEntrenamientos.add(listaEntrenamientos);
		
		
		Dimension resPantalla = Toolkit.getDefaultToolkit().getScreenSize();

		scrollListaEntrenamientos.setPreferredSize(new Dimension((int)(resPantalla.getSize().getWidth()/10), (int)(resPantalla.getSize().getHeight()/2)));

		
		
		JPanel panelIntermedio = new JPanel();
			panelIntermedio.setLayout(new BoxLayout(panelIntermedio, BoxLayout.X_AXIS));
		
			tiempoEntrenamientoSeleccionado = new JLabel("", SwingConstants.CENTER);
			tiempoEntrenamientoSeleccionado.setAlignmentX(CENTER_ALIGNMENT);
			caloriasEntrenamientoSeleccionado = new JLabel("", SwingConstants.CENTER);
			caloriasEntrenamientoSeleccionado.setAlignmentX(CENTER_ALIGNMENT);
			panelIntermedio.add(tiempoEntrenamientoSeleccionado);
			panelIntermedio.add(caloriasEntrenamientoSeleccionado);
			
			
		

		//Panel entrenamiento seleccionado
			
			panelEntrenamientoSeleccionado.setLayout(new BoxLayout(panelEntrenamientoSeleccionado, BoxLayout.Y_AXIS));
			
			dificultadEntrenamientoSeleccionado = new JLabel("", SwingConstants.CENTER);
			dificultadEntrenamientoSeleccionado.setAlignmentX(CENTER_ALIGNMENT);
			
			descripcionEntrenamientoSeleccionado = new JLabel("");
			descripcionEntrenamientoSeleccionado.setAlignmentX(CENTER_ALIGNMENT);
			
			labelDescripcion = new JLabel("<html><b>Descripción</b></html>", SwingConstants.CENTER);
			labelDescripcion.setAlignmentX(CENTER_ALIGNMENT);
			labelDescripcion.setVisible(false);
			
			panelEntrenamientoSeleccionado.add(panelIntermedio);
			panelEntrenamientoSeleccionado.add(dificultadEntrenamientoSeleccionado);
			panelEntrenamientoSeleccionado.add(labelDescripcion);
			panelEntrenamientoSeleccionado.add(descripcionEntrenamientoSeleccionado);

			
			// Asignacion de fuente a los componentes y espaciado entre ellos
			Font fuente = new Font(descripcionEntrenamientoSeleccionado.getFont().getName(), Font.PLAIN, 20);
			for (Component componente : panelIntermedio.getComponents()) {
				componente.setFont(fuente);
				((JComponent) componente).setBorder(new EmptyBorder(20, 0, 0, 0));
			}
			for (Component componente : panelEntrenamientoSeleccionado.getComponents()) {
				
				if (componente != (Component)labelDescripcion) {
					((JComponent) componente).setBorder(new EmptyBorder(10, 0, 15, 0));	
				} else {
					((JComponent) componente).setBorder(new EmptyBorder(15, 0, 0, 0));	
				}
				componente.setFont(fuente);
			}

			panelEntrenamientoSeleccionado.setVisible(false);
			
		// Panel de componentes excepto botonIniciar
		JPanel panelIzquierdoEntrenamientos = new JPanel();
			panelIzquierdoEntrenamientos.add(scrollListaEntrenamientos);
			panelIzquierdoEntrenamientos.add(panelEntrenamientoSeleccionado);
			panelIzquierdoEntrenamientos.add(botonIniciar);

		add(panelIzquierdoEntrenamientos, BorderLayout.WEST);		
		
		//LISTENERS BOTONES		
		
		botonIniciar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new VentanaEntrenamientoEnCurso(listaEntrenamientos.getSelectedValue(), persona));
				dispose();
			}
		});
		
		pack();
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Seleccionar entrenamiento");		
	}
	
	
	//Render de listaEntrenamientos
	//Imagenes sacadas de www.flaticon.com
	public class RenderListaEntrenamientos extends JLabel implements ListCellRenderer<Entrenamiento>{
		private static final long serialVersionUID = 1L;
		
		Color sinSeleccionar = getBackground();

		@Override
		public Component getListCellRendererComponent(JList<? extends Entrenamiento> list, Entrenamiento value,
				int index, boolean isSelected, boolean cellHasFocus) {

			Font fuente = new Font("Georgia", Font.BOLD, 15);
			this.setFont(fuente);
			
			setText(value.getNombre());
			setOpaque(true);
			setBackground(sinSeleccionar);

			if (isSelected) {
				panelEntrenamientoSeleccionado.setVisible(true);
				
				entrenamientoSeleccionado = value;
				
				setBackground(Color.RED);
				
				dificultadEntrenamientoSeleccionado.setText(String.format("<html><b>Dificultad: </b>%s</html>",value.getDificultad()));
				
				tiempoEntrenamientoSeleccionado.setText(String.format("<html><b>%d</b> minutos</html>", value.getTiempo()));
				ImageIcon tmpCronometro = new ImageIcon("resources\\images\\chronometer.png");
				Image iconoCronometro = tmpCronometro.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				tiempoEntrenamientoSeleccionado.setIcon(new ImageIcon(iconoCronometro));
				
				caloriasEntrenamientoSeleccionado.setText(String.format("<html><b>%d</b> calorias</html>", value.getCalorias()));
				ImageIcon tmpCalorias = new ImageIcon("resources\\images\\calories.png");
				Image iconoCalorias = tmpCalorias.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				caloriasEntrenamientoSeleccionado.setIcon(new ImageIcon(iconoCalorias));
			
				labelDescripcion.setVisible(true);
				
				descripcionEntrenamientoSeleccionado.setText(value.getDescripcion());
				VentanaResumen.anadirBordePanel(entrenamientoSeleccionado.getNombre(), panelEntrenamientoSeleccionado);
				
				botonIniciar.setVisible(true);
				
				pack();

			}

			return this;
		}
		
	}
	
}
