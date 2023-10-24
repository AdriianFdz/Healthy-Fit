package gui;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import domain.Entrenamiento;
import domain.TipoDificultad;
import domain.TipoEntrenamiento;


public class VentanaEntrenamientoEnCurso extends JFrame{
	
	 private static final long serialVersionUID = 1L;

	  
	    private JPanel panelDerecha;
	    private JPanel panelIzquierda;
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

	    private byte milisegundos;
	    private byte segundos;
	    private short minutos;

	    private DecimalFormat timeFormatter;
	  

	    private Timer timer;

	    public VentanaEntrenamientoEnCurso(Entrenamiento e) {
	    	milisegundos = 0;
		    segundos = 20;
		    minutos = 0;
		    
		    nombre = new JLabel(e.getNombre());
		    labelDificultad = new JLabel("Dificultad: " + e.getDificultad().toString());
		    labelKcal = new JLabel("🔥 " + e.getCalorias() + " kcal");
		    labelSeries = new JLabel(e.getRepeticiones() + "SERIES");
		    labelRepeticiones = new JLabel(e.getRepeticiones() + " REPETICIONES");
	        panelDerecha = new JPanel();
	        panelDerecha.setLayout(new GridLayout(2, 1, 20, 20));
	        panelIzquierda = new JPanel();
	        panelIzquierda.setLayout(new GridLayout(4, 1,20,20));
	        

	        panelTiempo = new JLabel();
	        panelTiempo.setFont(new Font("Consolas", Font.PLAIN, 30));
	        panelTiempo.setHorizontalAlignment(JLabel.CENTER);
	       

	        panelBotones = new JPanel();
	        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER));

	        botonStart = new JButton("Start");
	        botonStart.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {

	                timer.start();

	            }
	        });
	        panelBotones.add(botonStart);

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

	        panelBotones.add(botonReset);

	        botonStop = new JButton("Stop");
	        botonStop.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                timer.stop();
	            }
	        });

	        panelBotones.add(botonStop);

	        panelDerecha.add(panelBotones, BorderLayout.SOUTH);

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

	        panelDerecha.add(labelDificultad);
	        panelDerecha.add(labelKcal);
	        panelIzquierda.add(labelSeries);
	        panelIzquierda.add(labelRepeticiones);
	        panelIzquierda.add(panelTiempo);
	        panelIzquierda.add(panelBotones);

	        this.add(nombre, BorderLayout.NORTH);
	        this.add(panelDerecha, BorderLayout.WEST);
	        this.add(panelIzquierda, BorderLayout.EAST);
	        
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setTitle("EntrenamientoEnCurso");
	        pack();
	        setVisible(true);
	    }

	    public static void main(String[] args) {
	        EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	             

	                new VentanaEntrenamientoEnCurso(new Entrenamiento("Abdominales", TipoEntrenamiento.INFERIOR, TipoDificultad.FACIL, 5, "", 23, 33, 21));
	            }
	        });
	    }

}
