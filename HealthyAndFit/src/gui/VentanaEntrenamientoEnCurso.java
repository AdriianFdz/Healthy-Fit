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
import java.sql.Connection;
import java.text.DecimalFormat;
import java.time.LocalDate;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import db.DBManager;
import domain.Entrenamiento;
import domain.TipoDificultad;
import domain.Usuario;


public class VentanaEntrenamientoEnCurso extends JFrame{
	
	 private static final long serialVersionUID = 1L;
	 
	 //Definir propiedades necesarias para la ventana
	    private JPanel panelPrincipal;
	    private JPanel panelSecundario;
	    private JPanel panelAbajo;
	    private JPanel panelBotones;
	    private JPanel panelDerecha;
	    
	    private JLabel labelEstado;
	    private JLabel labelTiempo;
	    private JLabel labelNombre;
	    private JLabel labelDificultad;
	    private JLabel labelKcal;
	    private JLabel labelRepeticiones;
	    private JLabel labelSeries;
	    private JLabel foto;
	    
	    private JButton botonStart;
	    private JButton botonReset;
	    private JButton botonStop;
	    private JButton botonVolver;
	    
	    private int seriesRestantes;
	    private int repeticionesRestantes;
	    
	    private byte milisegundos;
	    private byte segundos;
	    private short minutos;

	    private boolean descanso;
	    
	    private DecimalFormat timeFormatter;
	  
	    private Timer timer;

	    public VentanaEntrenamientoEnCurso(Entrenamiento en, Usuario persona, VentanaResumen vResumen) {
	    
			//Imagenes sacadas de www.flaticon.com
		    ImageIcon tmpCronometro = new ImageIcon("resources\\images\\chronometer.png");
            Image iconoCronometro = tmpCronometro.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            ImageIcon tmpFuego = new ImageIcon("resources\\images\\calories.png");
            Image iconoFuego = tmpFuego.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		    
            //Inicializar las propiedades previamente definidas
            milisegundos = 0;
            minutos = 0;
            
            if (en.getDificultad() == TipoDificultad.FACIL) {
            	segundos = (byte) (en.getTiempo() + 20);
            }else if(en.getDificultad() == TipoDificultad.MEDIO) {
            	segundos = (byte) (en.getTiempo() + 10);
            }else {
            	segundos = (byte) en.getTiempo();
            }
        	
		    descanso = false;
		    
		    seriesRestantes = en.getSeries();
		    repeticionesRestantes = en.getRepeticiones();
		    
		    labelEstado = new JLabel(" ");
		    labelEstado.setBackground(Color.GRAY);
		    labelNombre = new JLabel(" "+ en.getNombre());
		    labelDificultad = new JLabel("   Dificultad: " + en.getDificultad().toString());
		    labelKcal = new JLabel(en.getCalorias() + " kcal");
		    labelKcal.setIcon(new ImageIcon(iconoFuego));
		    labelSeries = new JLabel(seriesRestantes + " SERIES");
		    labelRepeticiones = new JLabel(repeticionesRestantes + " REPETICIONES");
		    
	        panelPrincipal = new JPanel();
	        panelPrincipal.setLayout(new BorderLayout());
	        panelSecundario = new JPanel();
	        panelSecundario.setLayout(new GridLayout(3, 2, 80, 0));
	        panelAbajo = new JPanel();
	        panelAbajo.setBackground(Color.GRAY);
	        panelAbajo.setLayout(new FlowLayout(FlowLayout.LEADING));
	        panelBotones = new JPanel();
	        panelBotones.setLayout(new FlowLayout());
	        
	        
	        ImageIcon background = new ImageIcon("resources/images/preparacion.jpg");
            Image preparacion = background.getImage().getScaledInstance(1500, background.getIconHeight(), Image.SCALE_SMOOTH);
	        foto = new JLabel(new ImageIcon(preparacion));
	 
	    
	        panelDerecha = new JPanel(new GridLayout());
	        panelDerecha.setOpaque(false);
	        panelDerecha.add(foto);
	        
	        labelTiempo = new JLabel();
	        labelTiempo.setFont(new Font("Consolas", Font.PLAIN, 80));
	        labelTiempo.setHorizontalAlignment(JLabel.CENTER);
	        labelTiempo.setIcon(new ImageIcon(iconoCronometro));

	        botonVolver = new JButton("VOLVER");
	        botonStart = new JButton("Start");
	        botonReset = new JButton("Reset");
	        botonStop = new JButton("Stop");
	        
	        
	   
	        timeFormatter = new DecimalFormat("00");
	        
	        timer = new Timer(10, new ActionListener() {
	            @Override 
	            public void actionPerformed(ActionEvent e) {
	                if (milisegundos > 0) {
	                    milisegundos--;
	                } else {
	                	  if (seriesRestantes == 0) {
	                            // Solo muestra el JOptionPane al final del ejercicio
	                        	timer.stop();
	                            labelTiempo.setVisible(false);
	                            labelEstado.setText("  ENTRENAMIENTO FINALIZADO      ENTRENAMIENTO FINALIZADO      ENTRENAMIENTO FINALIZADO     ");
	                            int opcion = JOptionPane.showConfirmDialog(null, "¿Quieres guardar el entrenamiento?", "Guardar Entrenamiento", JOptionPane.YES_NO_OPTION);
	                        	if (opcion == JOptionPane.YES_OPTION) {
	                            	persona.getRegistroEntrenamiento().add(en);
	                            	persona.setUltimaVezEntreno(LocalDate.now());
	                            	Connection conn = DBManager.obtenerConexion();
	                            	DBManager.anadirUsuarioEntrenamientos(conn, persona, en);
	                            	SwingUtilities.invokeLater(() -> new VentanaResumen(persona));
	                            	dispose();
	                            } else {
	                            	SwingUtilities.invokeLater(() -> new VentanaResumen(persona));
	                				dispose();
	                            }
	                        }
	                	  
	                    if (segundos == 0 && minutos == 0) {
	                        timer.stop();
	                        
	                      
	                        if (!descanso) {
	                            // Si no está en el tiempo de descanso, inicia el tiempo de descanso
	                            descanso = true;
	                            seriesRestantes--;
	                            segundos = 30;  // 30 segundos de descanso entre series
	                            milisegundos = 0;
	                            timer.start();
	                            labelTiempo.setForeground(Color.RED);
	                            labelEstado.setText("  DESCANSO  DESCANSO  DESCANSO  DESCANSO  DESCANSO  DESCANSO  DESCANSO  ");
	                            VentanaResumen.animacionTexto(labelEstado);
	                            Icon background5 = new ImageIcon("resources/images/descanso.png");
	        	            	foto.setIcon(background5);
	                            
	                          
	                        } else {
	                            // Si ya está en el tiempo de descanso, reinicia el cronómetro para la próxima serie
	                            descanso = false;
	                            Image background6 = en.getFoto().getImage().getScaledInstance(1500, en.getFoto().getIconHeight(), Image.SCALE_SMOOTH);
	        	            	foto.setIcon(new ImageIcon(background6));
	                            if (seriesRestantes > 0) {
	                                minutos = 0;
	                                if (en.getDificultad() == TipoDificultad.FACIL) {
	                                	segundos = (byte) (en.getTiempo() + 20);
	                                }else if(en.getDificultad() == TipoDificultad.MEDIO) {
	                                	segundos = (byte) (en.getTiempo() + 10);
	                                }else {
	                                	segundos = (byte) en.getTiempo();
	                                }
	                                milisegundos = 0;
	                                timer.start();
	                                labelTiempo.setForeground(Color.BLACK);
	                                labelEstado.setText("");
	                            }
	                    }

	                    } else if (segundos > 0) {
	                        segundos--;
	                        milisegundos = 60;
	                        
	                        
	                    } else if (minutos > 0) {
	                        minutos--;
	                        segundos = 59;
	                        milisegundos = 60;
	                    }
	                }
	             
	                
	                
	                labelTiempo.setText(timeFormatter.format(minutos) + ":"
	                        + timeFormatter.format(segundos) + ":"
	                        + timeFormatter.format(milisegundos));
	                
	                labelSeries.setText(seriesRestantes + " SERIES");
	                labelRepeticiones.setText(repeticionesRestantes + " REPETICIONES");
	            }
	        });

	        labelTiempo.setText(timeFormatter.format(minutos) + ":"
	                + timeFormatter.format(segundos) + ":"
	                + timeFormatter.format(milisegundos));
	        
	        
	        botonStart.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
                    Image background2 = en.getFoto().getImage().getScaledInstance(1500, en.getFoto().getIconHeight(), Image.SCALE_SMOOTH);
	            	foto.setIcon(new ImageIcon(background2));
	                timer.start();

	            }
	        });
	        

	        
	        botonReset.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	             
	            	labelSeries.setText(en.getSeries() + " SERIES");
	                
	            	ImageIcon background3 = new ImageIcon("resources/images/preparacion.jpg");
	                Image background3IMG = background3.getImage().getScaledInstance(1500, background.getIconHeight(), Image.SCALE_SMOOTH);
	                foto.setIcon(new ImageIcon(background3IMG));
	            	
	                timer.stop();
	                
	                if (!descanso) {
	                	if (en.getDificultad() == TipoDificultad.FACIL) {
		                	segundos = (byte) (en.getTiempo() + 20);
		                }else if(en.getDificultad() == TipoDificultad.MEDIO) {
		                	segundos = (byte) (en.getTiempo() + 10);
		                }else {
		                	segundos = (byte) en.getTiempo();
		                }
	                }else {
	                	segundos = 30;
	                }
	                
	                
	                
	                
	                milisegundos = 0;
	                

	                labelTiempo.setText(timeFormatter.format(minutos) + ":"
	                        + timeFormatter.format(segundos) + "."
	                        + timeFormatter.format(milisegundos));
	            }
	        });

	    

	       
	        botonStop.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	Icon background4 = new ImageIcon("resources/images/pause.jpg");
	            	foto.setIcon(background4);
	                timer.stop();
	            }
	        });

	       

	       botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new VentanaResumen(persona));
				dispose();
				
			}
		});

	       
	        
	        //Cambio del estilo y tamaño de diferentes componentes
	   
			labelNombre.setFont(new Font("Tahoma", Font.BOLD, 50));
			labelNombre.setBackground((Color.gray));
			labelNombre.setOpaque(true);
	        
				//Tomo la letra de cualquier JLabel y la recojo en una variable para utilizarla despues
				Font fuenteFont = labelKcal.getFont();
			
			labelEstado.setFont(new Font(fuenteFont.getFontName(), Font.BOLD, 30));
			labelEstado.setForeground(Color.RED);
			labelEstado.setOpaque(true);
			labelDificultad.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 20));
			labelKcal.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 20));
			labelRepeticiones.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 20));
			labelSeries.setFont(new Font(fuenteFont.getFontName(), fuenteFont.getStyle(), 20));
		
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
	        panelAbajo.add(labelEstado);
	        panelAbajo.add(labelEstado);
	        
	        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
	        panelPrincipal.add(panelSecundario, BorderLayout.NORTH);
	        panelPrincipal.add(labelTiempo, BorderLayout.CENTER);
	       
	        
	        //Añadir paneles y el nombre a la ventana
	        this.add(labelNombre, BorderLayout.NORTH);
	        this.add(panelAbajo, BorderLayout.SOUTH);
	        this.add(panelPrincipal, BorderLayout.WEST);
	        this.add(panelDerecha);
	        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	        //Ajustes de la ventana
			setExtendedState(JFrame.MAXIMIZED_BOTH);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setTitle("Entrenamiento en curso");
	        setVisible(true);
	        setResizable(false);
	        setLocationRelativeTo(null); 
	    }
	    

}