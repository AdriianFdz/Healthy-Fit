package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.color.ColorSpace;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import domain.Dieta;
import domain.TipoDificultad;

public class VentanaDieta extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		JPanel panel;
		JPanel panelArriba;
		JPanel panelAbajo;
		JLabel nombre;
		JLabel minutos;
		JLabel dificultad;
		JLabel kcal;
		JLabel Labelingredientes;
		JTextArea ingredientes;
		JLabel labelPasos;
		JTextArea pasos;
		
	
	
	public VentanaDieta(Dieta d) {
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panelArriba = new JPanel();
		panelArriba.setLayout(new GridLayout(4, 2, 50, 50));
		//panelAbajo = new JPanel();
		//panelAbajo.setLayout(new GridLayout(2,2,100,3));
		
		
		nombre = new JLabel(" " + d.getNombre());
		nombre.setFont(new Font("Tahoma", Font.BOLD, 25));
		nombre.setBackground((Color.gray));
		nombre.setOpaque(true);
		minutos = new JLabel("    ⏰" + d.getTiempo() + " minutos");
		
		
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
			ingredientes.append(s);
		}
	
		
	
		labelPasos = new JLabel("Pasos a realizar:");
		
		pasos = new JTextArea();
		pasos.setEditable(false);
		for (String s : d.getPasos()) {
			pasos.append(s);
		}
		
		JScrollPane pane = new JScrollPane();
		pasos.add(pane);
		ingredientes.add(pane);
		
		kcal.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 20));
		Labelingredientes.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 20));
		labelPasos.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 20));
		minutos.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 20));
		dificultad.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 20));
		
		JLabel label = new JLabel();
		
		panelArriba.add(minutos);
		panelArriba.add(dificultad);
		panelArriba.add(kcal);
		panelArriba.add(labelPasos);
		panelArriba.add(Labelingredientes);
		panelArriba.add(pasos);
		panelArriba.add(ingredientes);
		
		//panelAbajo.add(Labelingredientes);
		//panelAbajo.add(labelPasos);
		//panelAbajo.add(ingredientes);
		//panelAbajo.add(pasos);
		
		panel.add(panelArriba,BorderLayout.NORTH);
		//panel.add(panelAbajo);

		
		
		this.add(nombre,BorderLayout.NORTH);
		this.add(panel);
		
		
		this.setVisible(true);
		this.setSize(800, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Dieta");
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> new VentanaDieta(new Dieta("Pollo al curry", 50, TipoDificultad.FACIL, 500,new ArrayList<String>(),new ArrayList<String>() )));
	}
}
