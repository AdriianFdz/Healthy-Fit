package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import domain.Entrenamiento;
import domain.Usuario;


public class VentanaEntrenamientoEnCurso extends JFrame{
	
	 private static final long serialVersionUID = 1L;

	  
	    private JPanel panelPrincipal;
	    private JPanel panelSecundario;
	    private JPanel panelAbajo;
	    private JPanel panelBotones;
	    
	    private JLabel labelTiempo;
	    private JLabel labelNombre;
	    private JLabel labelDificultad;
	    private JLabel labelKcal;
	    private JLabel labelRepeticiones;
	    private JLabel labelSeries;
	   
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
	    
			//Imagenes sacadas de www.flaticon.com
		    ImageIcon tmpCronometro = new ImageIcon("resources\\images\\chronometer.png");
            Image iconoCronometro = tmpCronometro.getImage().getScaledInstance(200, 190, Image.SCALE_SMOOTH);
            ImageIcon tmpFuego = new ImageIcon("resources\\images\\calories.png");
            Image iconoFuego = tmpFuego.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		    
            
        	milisegundos = 0;
		    segundos = 20; //e.getTiempo()
		    minutos = 0;
		    
		    labelNombre = new JLabel(" "+ e.getNombre());
		    labelDificultad = new JLabel("   Dificultad: " + e.getDificultad().toString());
		    labelKcal = new JLabel(e.getCalorias() + " kcal");
		    labelKcal.setIcon(new ImageIcon(iconoFuego));
		    labelSeries = new JLabel(e.getSeries() + " SERIES");
		    labelRepeticiones = new JLabel(e.getRepeticiones() + " REPETICIONES");
		    
	        panelPrincipal = new JPanel();
	        panelPrincipal.setLayout(new BorderLayout());
	        panelSecundario = new JPanel();
	        panelSecundario.setLayout(new GridLayout(3, 2, 1100, 80));
	        panelAbajo = new JPanel();
	        panelAbajo.setLayout(new FlowLayout(FlowLayout.LEADING));
	        panelBotones = new JPanel();
	        panelBotones.setLayout(new FlowLayout());
	        
	        labelTiempo = new JLabel();
	        labelTiempo.setFont(new Font("Consolas", Font.PLAIN, 160));
	        labelTiempo.setHorizontalAlignment(JLabel.CENTER);
	        labelTiempo.setIcon(new ImageIcon(iconoCronometro));

	        botonVolver = new JButton("VOLVER");
	        botonStart = new JButton("Start");
	        botonReset = new JButton("Reset");
	        botonStop = new JButton("Stop");
	        
	        
	        //Implementacion de un posible cronometro usando la Timer (provisional y aun sin acabar)
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
	                
	                labelTiempo.setText(timeFormatter.format(minutos) + ":"
	                        + timeFormatter.format(segundos) + "."
	                        + timeFormatter.format(milisegundos));
	            }
	        });

	        labelTiempo.setText(timeFormatter.format(minutos) + ":"
	                + timeFormatter.format(segundos) + "."
	                + timeFormatter.format(milisegundos));
	        
	        
	        botonStart.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {

	                timer.start();

	            }
	        });
	        

	        
	        botonReset.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	

	                timer.stop();

	                milisegundos = 0;
	                segundos = 20;
	                minutos = 0;

	                labelTiempo.setText(timeFormatter.format(minutos) + ":"
	                        + timeFormatter.format(segundos) + "."
	                        + timeFormatter.format(milisegundos));
	            }
	        });

	    

	       
	        botonStop.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                timer.stop();
	            }
	        });

	       

	       botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new VentanaEntrenamiento(persona));
				dispose();
				
			}
		});

	       
	        
	        //Cambio del estilo y tamaño de diferentes componentes
	   
			labelNombre.setFont(new Font("Tahoma", Font.BOLD, 50));
			labelNombre.setBackground((Color.gray));
			labelNombre.setOpaque(true);
	        
				//Tomo la letra de cualquier JLabel y la recojo en una variable para utilizarla despues
				Font fuenteFont = labelKcal.getFont();
			
			labelDificultad.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 40));
			labelKcal.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 40));
			labelRepeticiones.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 40));
			labelSeries.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 40));
		
			botonStart.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 30));
			botonReset.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 30));
			botonStop.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 30));
			
			botonStart.setBackground(Color.GREEN);
			botonStop.setBackground(Color.RED);
			
			botonStart.setPreferredSize(new Dimension(120,60));
			botonReset.setPreferredSize(new Dimension(120,60));
			botonStop.setPreferredSize(new Dimension(120,60));
			botonVolver.setPreferredSize(new Dimension(120,60));
			
			
			//Añadir componentes a los paneles
			panelBotones.add(botonStart);
		    panelBotones.add(botonReset);
		    panelBotones.add(botonStop);
		    
	        panelSecundario.add(labelDificultad);
	        panelSecundario.add(labelSeries);
	        panelSecundario.add(labelKcal);
	        panelSecundario.add(labelRepeticiones);
	        
	        panelAbajo.add(botonVolver);
	        
	        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
	        panelPrincipal.add(panelSecundario, BorderLayout.NORTH);
	        panelPrincipal.add(labelTiempo, BorderLayout.CENTER);
	       
	        
	        //Añadir paneles y el nombre a la ventana
	        this.add(labelNombre, BorderLayout.NORTH);
	        this.add(panelAbajo, BorderLayout.SOUTH);
	        this.add(panelPrincipal);
	      
	        //Ajustes de la ventana
	        setSize(1920,1080);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setTitle("EntrenamientoEnCurso");
	        setVisible(true);
	    }
	    

}