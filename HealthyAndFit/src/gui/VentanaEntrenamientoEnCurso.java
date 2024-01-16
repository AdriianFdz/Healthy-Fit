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
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import db.DBManager;
import domain.Entrenamiento;
import domain.Usuario;
import io.RegistroLogger;


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
	    private JButton botonVolver;
	    
	    private int seriesRestantes;
	    private int repeticionesRestantes;
	    
	    private int segundos;

	    private boolean descanso;
		
	    
	    private Boolean detener = false;


	    public VentanaEntrenamientoEnCurso(Entrenamiento en, Usuario persona, VentanaResumen vResumen) {
	    
			//Imagenes sacadas de www.flaticon.com
		    ImageIcon tmpCronometro = null;
			try {
				tmpCronometro = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/chronometer.png")));
			} catch (IOException e1) {
				e1.printStackTrace();
				RegistroLogger.getLogger().log(Level.SEVERE, "Error al cargar la foto");
				JOptionPane.showConfirmDialog(null, "Error al cargar la foto", "Error", JOptionPane.PLAIN_MESSAGE);
			}
            Image iconoCronometro = tmpCronometro.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            ImageIcon tmpFuego = null;
			try {
				tmpFuego = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/calories.png")));
			} catch (IOException e1) {
				e1.printStackTrace();
				RegistroLogger.getLogger().log(Level.SEVERE, "Error al cargar la foto");
				JOptionPane.showConfirmDialog(null, "Error al cargar la foto", "Error", JOptionPane.PLAIN_MESSAGE);
			}
            Image iconoFuego = tmpFuego.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		    
            //Inicializar las propiedades previamente definidas
        	segundos = en.getTiempo();
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
	        
	        
	        ImageIcon background = null;
			try {
				background = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/preparacion.jpg")));
			} catch (IOException e1) {
				e1.printStackTrace();
				RegistroLogger.getLogger().log(Level.SEVERE, "Error al cargar la foto");
				JOptionPane.showConfirmDialog(null, "Error al cargar la foto", "Error", JOptionPane.PLAIN_MESSAGE);
			}
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
	        
	        
	        labelTiempo.setText(String.valueOf(en.getTiempo()));
	        Thread hilo = new Thread(new Runnable() {
				
	        	@Override
				public void run() {
					while (!detener) {
						while (segundos > 0) {
							segundos--;
							SwingUtilities.invokeLater(() -> labelTiempo.setText(String.valueOf(segundos)));
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
								RegistroLogger.getLogger().log(Level.SEVERE, "Error al cargar la foto");
								JOptionPane.showConfirmDialog(null, "Error al cargar la foto", "Error", JOptionPane.PLAIN_MESSAGE);
							}
						}
						
						if (seriesRestantes == 0) {
							detener = true;
							SwingUtilities.invokeLater(() -> labelTiempo.setVisible(false));
							SwingUtilities.invokeLater(() -> labelEstado.setText("	ENTRENAMIENTO FINALIZADO	ENTRENAMIENTO FINALIZADO	ENTRENAMIENTO FINALIZADO	"));
							int opcion = JOptionPane.showConfirmDialog(null, "¿Quieres guardar el entrenamiento?", "Guardar Entrenamiento", JOptionPane.YES_NO_OPTION);
							if (opcion == JOptionPane.YES_OPTION) {
								persona.getRegistroEntrenamiento().add(en);
								persona.setUltimaVezEntreno(LocalDate.now());
								Connection conn = DBManager.obtenerConexion();
								DBManager.anadirUsuarioEntrenamientos(conn, persona, en);
							}
							SwingUtilities.invokeLater(() -> new VentanaResumen(persona));
							dispose();					
						} else {
							if (!descanso) {
								//Si no está en el tiempo de descanso, inicia el tiempo de descanso
								seriesRestantes--;
								SwingUtilities.invokeLater(() -> labelSeries.setText(seriesRestantes + " SERIES"));
								if (seriesRestantes==0) {
									continue;
								}
								
								descanso = true;
								segundos = 30;  // 30 segundos de descanso entre series
								SwingUtilities.invokeLater(() -> labelTiempo.setForeground(Color.RED));
								SwingUtilities.invokeLater(() -> labelEstado.setText("  DESCANSO  DESCANSO  DESCANSO  DESCANSO  DESCANSO  DESCANSO  DESCANSO  "));
								SwingUtilities.invokeLater(() -> VentanaResumen.animacionTexto(labelEstado));
								
								try {
									 Icon background5 = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/descanso.png")));
									 SwingUtilities.invokeLater(() -> foto.setIcon(background5));
								} catch (IOException e) {
									e.printStackTrace();
									RegistroLogger.getLogger().log(Level.SEVERE, "Error al cargar la foto");
									JOptionPane.showConfirmDialog(null, "Error al cargar la foto", "Error", JOptionPane.PLAIN_MESSAGE);
								}
								
							} else {
								// Si ya está en el tiempo de descanso, reinicia el cronómetro para la próxima serie
								descanso = false;
								Image background6 = en.getFoto().getImage().getScaledInstance(1500, en.getFoto().getIconHeight(), Image.SCALE_SMOOTH);
								SwingUtilities.invokeLater(() -> foto.setIcon(new ImageIcon(background6)));
								if (seriesRestantes > 0) {
									segundos = en.getTiempo();
									SwingUtilities.invokeLater(() -> labelTiempo.setForeground(Color.BLACK));
									SwingUtilities.invokeLater(() -> labelEstado.setText(""));
								}
							}
						}
						
					}
	        		
					
					
				}
			});
	        
				
	        botonStart.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	if (descanso) {
                        Icon background6 = new ImageIcon("resources/images/descanso.png");
                        foto.setIcon(background6);
                    } else {
                        Image background2 = en.getFoto().getImage().getScaledInstance(1500, en.getFoto().getIconHeight(), Image.SCALE_SMOOTH);
                        foto.setIcon(new ImageIcon(background2));
                    }
	            	hilo.start();
	            	botonStart.setVisible(false);

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
			
			botonStart.setBackground(Color.GREEN);
			
			botonStart.setPreferredSize(new Dimension(120,60));
			botonVolver.setPreferredSize(new Dimension(120,60));
			
			
			//Añadir componentes a los paneles
			panelBotones.add(botonStart);
		    
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
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setTitle("Entrenamiento en curso");
	        setVisible(true);
	        //setResizable(false);
	    }
	    
}