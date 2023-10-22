package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import domain.Dieta;
import domain.TipoDificultad;

public class VentanaDieta extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Definir atributos necesarios para la ventana
		private JPanel panelGeneral;
		private JPanel panelSecundario;
		private JLabel nombre;
		private JLabel minutos;
		private JLabel dificultad;
		private JLabel kcal;
		private JLabel Labelingredientes;
		private JTextArea ingredientes;
		private JLabel labelPasos;
		private JTextArea pasos;
		
	
	
	public VentanaDieta(Dieta d) {
		
		//Inicializar los atributos previamente definidos
		panelGeneral = new JPanel();
		panelGeneral.setLayout(new BorderLayout());
		panelSecundario = new JPanel();
		panelSecundario.setLayout(new GridLayout(4, 2, 50, 50));
		
		
		nombre = new JLabel(" " + d.getNombre());
		nombre.setFont(new Font("Tahoma", Font.BOLD, 25));
		nombre.setBackground((Color.gray));
		nombre.setOpaque(true);
		minutos = new JLabel("    ⏰" + d.getTiempo() + " minutos");
		
			//Poner emoji de fuego para reflejar la dificultad
		if (d.getDificultad() == TipoDificultad.FACIL) {
			dificultad = new JLabel("Dificultad: 🔥");
		}else if (d.getDificultad() == TipoDificultad.MEDIO) {
			dificultad = new JLabel("Dificultad: 🔥🔥");
		}else {
			dificultad = new JLabel("Dificultad: 🔥🔥🔥");
		}
		
		kcal = new JLabel("    Kcal: " + d.getKcal());
		Font fuenteFont = kcal.getFont();
		
		Labelingredientes = new JLabel("    Ingredientes:");
		
		ingredientes = new JTextArea();
		ingredientes.setEditable(false);
		for (String s : d.getIngredientes()) {
			ingredientes.append(" -" + s + "\n");
		}
	

		labelPasos = new JLabel("Pasos a realizar:");
		
		pasos = new JTextArea();
		pasos.setEditable(false);
		for (String s : d.getPasos()) {
			pasos.append(" -" + s + "\n");
		}
		
		JScrollPane pane = new JScrollPane();
		pasos.add(pane);
		ingredientes.add(pane);
		
		//Cambio del tipo y tamaño de letra
		kcal.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 20));
		Labelingredientes.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 20));
		labelPasos.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 20));
		minutos.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 20));
		dificultad.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 20));
		
		//Añadir los atributos al panel
		panelSecundario.add(minutos);
		panelSecundario.add(dificultad);
		panelSecundario.add(kcal);
		panelSecundario.add(labelPasos);
		panelSecundario.add(Labelingredientes);
		panelSecundario.add(pasos);
		panelSecundario.add(ingredientes);
		
		//Añadir el panelSecundario al panelGeneral
		panelGeneral.add(panelSecundario,BorderLayout.NORTH);

		
		//Añadir el panelGeneral a la ventana
		this.add(nombre,BorderLayout.NORTH);
		this.add(panelGeneral);
		
		
		this.setVisible(true);
		this.setSize(800, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Dieta");
		
	}
	
}
