package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import domain.Entrenamiento;
import domain.Usuario;


public class VentanaEntrenamientoEnCurso extends JFrame{
	
	 private static final long serialVersionUID = 1L;

	  
	    private JPanel panelPrincipal;
	    private JPanel panelSecundario;
	    private JPanel panelAbajo;
	    
	    private JLabel panelTiempo;
	    private JLabel nombre;
	    private JLabel labelDificultad;
	    private JLabel labelKcal;
	    private JLabel labelRepeticiones;
	    private JLabel labelSeries;
	    

	    private JPanel panelBotones;
	    private JButton botonStart;
	    private JButton botonReset;
	    private JButton botonStop;
	    private JButton botonVolver;

	    private byte milisegundos;
	    private byte segundos;
	    private short minutos;

	    private DecimalFormat timeFormatter;
	  

	    private Timer timer;

	    public VentanaEntrenamientoEnCurso(Entrenamiento e, Usuario persona) {
	    	milisegundos = 0;
		    segundos = 20;
		    minutos = 0;
		    
		    nombre = new JLabel(" "+ e.getNombre());
		    labelDificultad = new JLabel("   Dificultad: " + e.getDificultad().toString());
		    labelKcal = new JLabel("   🔥 " + e.getCalorias() + " kcal");
		    labelSeries = new JLabel(e.getSeries() + " SERIES");
		    labelRepeticiones = new JLabel(e.getRepeticiones() + " REPETICIONES");
	        panelPrincipal = new JPanel();
	        panelPrincipal.setLayout(new BorderLayout());
	        panelSecundario = new JPanel();
	        panelSecundario.setLayout(new GridLayout(3, 2, 1100, 80));
	        panelAbajo = new JPanel();
	        panelAbajo.setLayout(new FlowLayout(FlowLayout.LEADING));
	        botonVolver = new JButton("VOLVER");
	        
	        panelTiempo = new JLabel();
	        panelTiempo.setFont(new Font("Consolas", Font.PLAIN, 160));
	        panelTiempo.setHorizontalAlignment(JLabel.CENTER);
	       

	        panelBotones = new JPanel();
	        panelBotones.setLayout(new FlowLayout());

	        botonStart = new JButton("Start");
	        botonStart.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {

	                timer.start();

	            }
	        });
	        

	        botonReset = new JButton("Reset");
	        botonReset.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	

	                timer.stop();

	                milisegundos = 0;
	                segundos = 30;
	                minutos = 0;

	                panelTiempo.setText(timeFormatter.format(minutos) + ":"
	                        + timeFormatter.format(segundos) + "."
	                        + timeFormatter.format(milisegundos));
	            }
	        });

	    

	        botonStop = new JButton("Stop");
	        botonStop.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                timer.stop();
	            }
	        });

	       

	       

	        timeFormatter = new DecimalFormat("00");

	        timer = new Timer(10, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (milisegundos > 0) {
	                    milisegundos--;
	                } else {
	                    if (segundos == 0 && minutos == 0) {
	                        timer.stop();
	                    } else if (segundos > 0) {
	                        segundos--;
	                        milisegundos = 99;
	                    } else if (minutos > 0) {
	                        minutos--;
	                        segundos = 59;
	                        milisegundos = 99;
	                    }
	                }
	                panelTiempo.setText(timeFormatter.format(minutos) + ":"
	                        + timeFormatter.format(segundos) + "."
	                        + timeFormatter.format(milisegundos));
	            }
	        });

	        panelTiempo.setText(timeFormatter.format(minutos) + ":"
	                + timeFormatter.format(segundos) + "."
	                + timeFormatter.format(milisegundos));
	        
	        
	        /////
	   
			nombre.setFont(new Font("Tahoma", Font.BOLD, 50));
			nombre.setBackground((Color.gray));
			nombre.setOpaque(true);
	        
			Font fuenteFont = labelKcal.getFont();
			
			labelDificultad.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 40));
			labelKcal.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 40));
			labelRepeticiones.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 40));
			labelSeries.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 40));
		
	        ///////
			botonStart.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 30));
			botonReset.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 30));
			botonStop.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 30));
			
			botonStart.setBackground(Color.GREEN);
			botonStop.setBackground(Color.RED);
			
			botonStart.setPreferredSize(new Dimension(120,60));
			botonReset.setPreferredSize(new Dimension(120,60));
			botonStop.setPreferredSize(new Dimension(120,60));
			botonVolver.setPreferredSize(new Dimension(120,60));
			//////
			panelBotones.add(botonStart);
		    panelBotones.add(botonReset);
		    panelBotones.add(botonStop);
	        panelSecundario.add(labelDificultad);
	        panelSecundario.add(labelSeries);
	        panelSecundario.add(labelKcal);
	        panelSecundario.add(labelRepeticiones);
	        //panelSecundario.add(botonReset);
	        //panelSecundario.add(panelTiempo);
	       
	        panelAbajo.add(botonVolver);
	        
	        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
	        panelPrincipal.add(panelSecundario, BorderLayout.NORTH);
	        panelPrincipal.add(panelTiempo, BorderLayout.CENTER);
	       
	        this.add(nombre, BorderLayout.NORTH);
	       
	        this.add(panelAbajo, BorderLayout.SOUTH);
	        this.add(panelPrincipal);
	      
	        
	        //this.add(panelPrincipal, BorderLayout.WEST);
	        //this.add(panelSecundario, BorderLayout.CENTER);
	        
	        setSize(1920,1080);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setTitle("EntrenamientoEnCurso");
	        //pack();
	        setVisible(true);
	    }

//	    public static void main(String[] args) {
//	        EventQueue.invokeLater(new Runnable() {
//	            @Override
//	            public void run() {
//	             
//
//	                new VentanaEntrenamientoEnCurso(new Entrenamiento("Abdominales", TipoEntrenamiento.INFERIOR, TipoDificultad.FACIL, 5, "", 23, 33, 21));
//	            }
//	        });
//	    }

}