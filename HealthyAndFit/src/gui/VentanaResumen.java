package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import db.BaseDeDatos;
import domain.Usuario;
import io.RegistroLogger;

public class VentanaResumen extends JFrame{

	//Propiedades de la ventana
	 JButton botonEntrenar;
	 JButton fotoPerfil;
	 JButton botonDieta;
	 JPanel panelImagenVasos = new JPanel();

	
	 //LOGGER
	private static final long serialVersionUID = 1L;
	public VentanaResumen(Usuario persona) {		
	
		
		//ENTRENAMIENTO
		JLabel caloriasGastadas = new JLabel("Calorías gastadas: "+persona.getCaloriasGastadas());
		JLabel racha = new JLabel("Racha: "+persona.getRachaEntrenamiento());
		JLabel tiempoEntrenado= new JLabel("Tiempo entrenado: "+persona.getTiempoEntrenado());
		JLabel objetivo = new JLabel("Objetivo: "+persona.getObjetivo());
		JLabel ultimaVez = new JLabel("Última vez: "+persona.getUltimaVezEntreno());
		ultimaVez.setBorder(new EmptyBorder(0, 0, 10, 0)); //Añadir margen inferior al JLabel

		//Cambios de estilo y tamaño de letra
		caloriasGastadas.setFont(new Font("Consolas", Font.PLAIN ,25));
		racha.setFont(new Font("Consolas", Font.PLAIN, 25));
		tiempoEntrenado.setFont(new Font("Consolas", Font.PLAIN, 25));
		objetivo.setFont(new Font("Consolas", Font.PLAIN, 25));
		ultimaVez.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		
		JPanel panelTextosEntrenamiento = new JPanel(new GridLayout(3, 3, 50, 30));
			panelTextosEntrenamiento.add(caloriasGastadas);
			panelTextosEntrenamiento.add(racha);
			panelTextosEntrenamiento.add(tiempoEntrenado);
			panelTextosEntrenamiento.add(objetivo);
			panelTextosEntrenamiento.add(ultimaVez);
		
		JPanel panelEntrenamiento = new JPanel(new BorderLayout());
			panelEntrenamiento.add(panelTextosEntrenamiento, BorderLayout.NORTH);
			botonEntrenar = new JButton("Quiero entrenar");
			panelEntrenamiento.add(botonEntrenar, BorderLayout.SOUTH);
			anadirBordePanel("ENTRENAMIENTO", panelEntrenamiento);
			
		JPanel panelIzquierda = new JPanel(new BorderLayout());
			panelIzquierda.add(panelEntrenamiento, BorderLayout.CENTER);
		add(panelIzquierda, BorderLayout.WEST);
		
		//Grafica entrenamiento
		TimeSeriesCollection datasetEntrenamiento = crearDatasetEjemplo("Calorías quemadas");
		JFreeChart graficaEntrenamiento = crearGrafica("Calorías quemadas", "Dia", "Calorias", datasetEntrenamiento);
		ChartPanel panelGraficaEntrenamiento = new ChartPanel(graficaEntrenamiento);
		Dimension resPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		RegistroLogger.anadirLogeo(Level.INFO, resPantalla.toString());
		
		panelGraficaEntrenamiento.setPreferredSize(new Dimension(resPantalla.getSize().width/2,resPantalla.getSize().height/2));
		panelEntrenamiento.add(panelGraficaEntrenamiento, BorderLayout.CENTER);
		
		
		
		//DIETA
		JLabel caloriasConsumidas = new JLabel("Calorías consumidas: "+persona.getCaloriasConsumidas());
		JLabel proximaComida = new JLabel("Próxima comida: "+persona.getProximaComida());
		JLabel vasosDeAgua = new JLabel("Vasos de agua: ");
		JPanel panelVasosAgua = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panelVasosAgua.add(vasosDeAgua);
			panelVasosAgua.add(panelImagenVasos);
			JPanel botonesVasos = new JPanel();
			
				JButton btnEliminarVaso = new JButton();
					ImageIcon iconoMinus = new ImageIcon("resources\\images\\minus.png");
					Image iconoMinusImg = iconoMinus.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
					ImageIcon iconoMinusResized = new ImageIcon(iconoMinusImg);
					btnEliminarVaso.setIcon(iconoMinusResized);
					
				JButton btnAnadirVaso = new JButton();
					ImageIcon iconoPlus = new ImageIcon("resources\\images\\plus.png");
					Image iconoPlusImg = iconoPlus.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
					ImageIcon iconoPlusResized = new ImageIcon(iconoPlusImg);
					btnAnadirVaso.setIcon(iconoPlusResized);
				
					
				botonesVasos.add(btnEliminarVaso);
				botonesVasos.add(btnAnadirVaso);
			panelVasosAgua.add(botonesVasos);
		//Cambios de estilo y tamaño de letra
		caloriasConsumidas.setFont(new Font("Consolas", Font.PLAIN ,25));
		proximaComida.setFont(new Font("Consolas", Font.PLAIN, 25));
		vasosDeAgua.setFont(new Font("Consolas", Font.PLAIN, 25));
		
			
			List<JLabel> listaVasos = new ArrayList<JLabel>();

			crearVasosDeAgua(listaVasos);
			actualizarVasosDeAgua(persona, listaVasos);
		
				
		JPanel panelTextosDieta = new JPanel(new GridLayout(3, 1, 0, 0));
			panelTextosDieta.add(caloriasConsumidas);
			panelTextosDieta.add(proximaComida);
			panelTextosDieta.add(panelVasosAgua);
		
		JPanel panelDieta = new JPanel(new BorderLayout());
			panelDieta.add(panelTextosDieta, BorderLayout.NORTH);
		
		
			botonDieta = new JButton("Revisar dieta");
			panelDieta.add(botonDieta, BorderLayout.SOUTH);

			anadirBordePanel("DIETA", panelDieta);
			

			
		//Grafica dieta
		TimeSeriesCollection datasetDieta = crearDatasetEjemplo("Calorías consumidas");
		JFreeChart graficaDieta = crearGrafica("Calorías consumidas", "Dia", "Calorias", datasetDieta);
		ChartPanel panelGraficaDieta = new ChartPanel(graficaDieta);
		
		panelGraficaDieta.setPreferredSize(new Dimension(resPantalla.getSize().width/2-35,resPantalla.getSize().height/2-35));
		panelDieta.add(panelGraficaDieta, BorderLayout.CENTER);
				
		
		JPanel panelArriba = new JPanel(new BorderLayout());
			add(panelArriba, BorderLayout.NORTH);
		
		JLabel alertaEntrenamiento = new JLabel("          VAMOS, QUE HOY TE TOCA ENTRENAR!          ");
			alertaEntrenamiento.setFont(new Font("verdana", Font.BOLD, 15));
			alertaEntrenamiento.setHorizontalAlignment(SwingConstants.CENTER);
			alertaEntrenamiento.setBorder(new EmptyBorder(40, 0, 40, 0));
			panelIzquierda.add(alertaEntrenamiento, BorderLayout.NORTH);
			alertaEntrenamiento.setForeground(Color.RED);
		
			
		JLabel alertaAgua = new JLabel("          RECUERDA BEBER AGUA!          ");
			alertaAgua.setFont(new Font("verdana", Font.BOLD, 15));
			alertaAgua.setHorizontalAlignment(SwingConstants.CENTER);
			alertaAgua.setForeground(Color.RED);
		
		JPanel panelArribaDerecha = new JPanel(new BorderLayout());
			panelArribaDerecha.add(alertaAgua, BorderLayout.CENTER);
			Image foto = persona.getFoto().getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
			fotoPerfil = new JButton(new ImageIcon(foto));
			fotoPerfil.setPreferredSize(new Dimension(100,100));
			panelArribaDerecha.add(fotoPerfil, BorderLayout.EAST);

		JPanel panelDerecha = new JPanel(new BorderLayout());
			panelDerecha.add(panelDieta, BorderLayout.CENTER);
			add(panelDerecha, BorderLayout.EAST);
			panelDerecha.add(panelArribaDerecha, BorderLayout.NORTH);
		
		animacionTexto(alertaEntrenamiento);
		animacionTexto(alertaAgua);	
					
		
		//LISTENERS BOTONES
		botonEntrenar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new VentanaEntrenamiento(persona));
				
			}
		});
		
		botonDieta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new VentanaDieta(BaseDeDatos.getListaDietas().get((int) (Math.random()*BaseDeDatos.getListaDietas().size()))));
				
			}
		});
		
		fotoPerfil.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new VentanaPerfil(persona));
				dispose();				
			}
		});

		btnAnadirVaso.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int cantidad = persona.getVasosDeAgua() + 1;
				SwingUtilities.invokeLater(() -> modificarVasosDeAgua(persona, listaVasos, cantidad));
			}
		});
		
		
		btnEliminarVaso.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int cantidad = persona.getVasosDeAgua() - 1;
				SwingUtilities.invokeLater(() -> modificarVasosDeAgua(persona, listaVasos, cantidad));
			}
		});
		
		
		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Resumen");
	}
	
	public static void anadirBordePanel(String titulo, JPanel panel) {
		LineBorder borde = new LineBorder(Color.BLACK, 5); //Crea un estilo de borde continuo, anchura 5
		TitledBorder bordeConTitulo = new TitledBorder(borde, titulo); //Añade el estilo de borde con un titulo
		
		bordeConTitulo.setTitleJustification(TitledBorder.CENTER);
		bordeConTitulo.setTitleFont(new Font("verdana", Font.BOLD, 20));
		panel.setBorder(bordeConTitulo);
	}
	
	//Crear grafica
	public JFreeChart crearGrafica(String titulo, String tiempo, String valores, XYDataset xydataset) {
		JFreeChart chart = ChartFactory.createTimeSeriesChart(titulo, tiempo, valores, xydataset);
		chart.setBackgroundPaint(Color.WHITE);
		return chart;
	}
	
	//Dataset Ejemplo
	public TimeSeriesCollection crearDatasetEjemplo(String titulo) {
        TimeSeries s1 = new TimeSeries(titulo);
        
        for (int i = 1; i < LocalDateTime.now().getMonth().length(true)+1; i++) { //Incluye años bisiestos
        	s1.add(new Day(i, LocalDateTime.now().getMonthValue(), LocalDateTime.now().getYear()), 100+i*10);		
        	
		}

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s1);
        
        RegistroLogger.anadirLogeo(Level.INFO, "Dataset de ejemplo creado correctamente");
        
        return dataset;

	}
	
	//Animacion de las alertas
	
	//Programa basado en https://www.tutorialspoint.com/how-can-we-implement-a-moving-text-using-a-jlabel-in-java
	private void animacionTexto(JLabel texto) {
		Thread hilo = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					String textoAnterior = texto.getText();
					// Cojo el texto anterior desde la 2 letra hasta el final, y le sumo la primera letra
					String textoDesplazado = textoAnterior.substring(1)+texto.getText().substring(0,1);  
					SwingUtilities.invokeLater(() -> texto.setText(textoDesplazado));
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						System.out.println("Hilo de animaciones de texto interrumpido");
					}
				}
				
			}
		});
		hilo.start();

	}
	
	//FUNCIONES DE UTILIDAD
	
	private void crearVasosDeAgua(List<JLabel>listaVasos) {
		for (int i = 0; i < 8; i++) {
			listaVasos.add(new JLabel());
		}
	}
	
	//Imagenes sacadas de www.flaticon.com
	private void actualizarVasosDeAgua(Usuario persona, List<JLabel> listaVasos) {
		for (int i = 0; i < persona.getVasosDeAgua(); i++) {
			ImageIcon vasotmp = new ImageIcon("resources\\images\\vasoLleno.png");
			Image vaso = vasotmp.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			ImageIcon vasoBueno = new ImageIcon(vaso);
			JLabel vasoL = new JLabel();
			vasoL.setIcon(vasoBueno);
			panelImagenVasos.add(vasoL);
			listaVasos.set(i, vasoL);
		}
		
		for (int i = persona.getVasosDeAgua(); i < 8; i++) {
			ImageIcon vasotmp = new ImageIcon("resources\\images\\vasoVacio.png");
			Image vaso = vasotmp.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			ImageIcon vasoBueno = new ImageIcon(vaso);
			JLabel vasoL = new JLabel();
			vasoL.setIcon(vasoBueno);
			panelImagenVasos.add(vasoL);
			listaVasos.set(i, vasoL);
		}
		
		RegistroLogger.anadirLogeo(Level.INFO, "Vasos de agua actualizados");
	}
	
	
	private void modificarVasosDeAgua(Usuario persona, List<JLabel> listaVasos, int cantidad) {
		if (cantidad >= 0 && cantidad <= 8) {			
			persona.setVasosDeAgua(cantidad);
			
			for (int i = 0; i < cantidad; i++) {
				ImageIcon vasotmp = new ImageIcon("resources\\images\\vasoLleno.png");
				Image vaso = vasotmp.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
				ImageIcon vasoBueno = new ImageIcon(vaso);
				
				listaVasos.get(i).setIcon(vasoBueno);
			}
			
			for (int i = cantidad; i < 8; i++) {
				ImageIcon vasotmp = new ImageIcon("resources\\images\\vasoVacio.png");
				Image vaso = vasotmp.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
				ImageIcon vasoBueno = new ImageIcon(vaso);
				
				listaVasos.get(i).setIcon(vasoBueno);
			}
		}
	}
	
	//GETTERS
	public JButton getBotonEntrenar() {
		return botonEntrenar;
	}
	public JButton getFotoPerfil() {
		return fotoPerfil;
	}
	public JButton getBotonDieta() {
		return botonDieta;
	}
	
	
}
