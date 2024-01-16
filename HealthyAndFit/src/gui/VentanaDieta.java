package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import domain.Dieta;
import domain.TipoDificultad;
import io.RegistroLogger;

public class VentanaDieta extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Definir propiedades necesarias para la ventana
		private JPanel panelGeneral;
		private JPanel panelSecundario;
		private JLabel labelNombre;
		private JLabel labelMinutos;
		private JLabel labelDificultad;
		private JLabel labelKcal;
		private JLabel labelIngredientes;
		private JTextArea areaIngredientes;
		private JLabel labelPasos;
		private JTextArea areaPasos;
		
	
	
	public VentanaDieta(Dieta d) {
		
		//Imagenes sacadas de www.flaticon.com
		ImageIcon tmpCronometro = null;
		try {
			tmpCronometro = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/chronometer.png")));
		} catch (IOException e) {
			e.printStackTrace();
			RegistroLogger.getLogger().log(Level.SEVERE, "Error al cargar la foto");
			JOptionPane.showConfirmDialog(null, "Error al cargar la foto", "Error", JOptionPane.PLAIN_MESSAGE);
		}
        Image iconoCronometro = tmpCronometro.getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH);
		
		//Inicializar las propiedades previamente definidas
		panelGeneral = new JPanel();
		panelGeneral.setLayout(new BorderLayout());
		panelSecundario = new JPanel();
		panelSecundario.setLayout(new GridLayout(4, 2, 50, 50));
		
		
		labelNombre = new JLabel(" " + d.getNombre());
		labelNombre.setFont(new Font("Tahoma", Font.BOLD, 40));
		labelNombre.setBackground((Color.gray));
		labelNombre.setOpaque(true);
		
		labelMinutos = new JLabel(d.getTiempo() + " minutos");
		labelMinutos.setIcon(new ImageIcon(iconoCronometro));
		
		labelKcal = new JLabel("    Kcal: " + d.getKcal());
		labelIngredientes = new JLabel("    Ingredientes:");
		labelPasos = new JLabel("Pasos a realizar:");
		
		
			//Poner emoji de fuego para reflejar la dificultad
		if (d.getDificultad() == TipoDificultad.FACIL) {
			labelDificultad = new JLabel("Dificultad: 🔥");
		}else if (d.getDificultad() == TipoDificultad.MEDIO) {
			labelDificultad = new JLabel("Dificultad: 🔥🔥");
		}else {
			labelDificultad = new JLabel("Dificultad: 🔥🔥🔥");
		}
		
		
		//Añadir datos a los JTextArea y modificaciones esteticas
		
		areaIngredientes = new JTextArea();
		areaIngredientes.setEditable(false);
		areaIngredientes.setFont(new Font("Consolas", Font.BOLD, 20));
		
		for (String s : d.getIngredientes()) {
			
			areaIngredientes.append("  -" + s + "\n");
		}
	
		areaPasos = new JTextArea();
		areaPasos.setFont(new Font("Consolas", Font.BOLD, 20));;
		areaPasos.setEditable(false);
		for (String s : d.getPasos()) {
			areaPasos.append("  -" + s + "\n");
		}
		
		//Añadir barra lateral de desplazamiento a los JTextArea
		JScrollPane pane = new JScrollPane();
		areaPasos.add(pane);
		areaIngredientes.add(pane);
		
		
		//Cambio del tipo y tamaño de letra de algunos JLabel
		
			//Tomo la letra de cualquier JLabel y la recojo en una variable para utilizarla despues
			Font fuenteFont = labelKcal.getFont();
		labelKcal.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 30));
		labelIngredientes.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 30));
		labelPasos.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 30));
		labelMinutos.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 30));
		labelDificultad.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 30));
		
		//Añadir las propiedades al panel
		panelSecundario.add(labelMinutos);
		panelSecundario.add(labelDificultad);
		panelSecundario.add(labelKcal);
		panelSecundario.add(labelPasos);
		panelSecundario.add(labelIngredientes);
		panelSecundario.add(areaPasos);
		panelSecundario.add(areaIngredientes);
		
		//Añadir el panelSecundario al panelGeneral
		panelGeneral.add(panelSecundario,BorderLayout.NORTH);

		
		//Añadir el panelGeneral a la ventana
		this.add(labelNombre,BorderLayout.NORTH);
		this.add(panelGeneral);
		
		//Ajustes de la ventana
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Dieta");
		 
	} 
	
	
}
