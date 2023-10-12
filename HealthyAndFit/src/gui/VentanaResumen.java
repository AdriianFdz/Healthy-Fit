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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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

import domain.Usuario;

public class VentanaResumen extends JFrame{

	private JButton botonEntrenar;
	JPanel panelImagenVasos = new JPanel();

	private static final long serialVersionUID = 1L;
	public VentanaResumen(Usuario persona) {		
		
		//ENTRENAMIENTO
		JPanel panelIzquierda = new JPanel(new BorderLayout());
		add(panelIzquierda, BorderLayout.WEST);

		JPanel panelEntrenamiento = new JPanel(new BorderLayout());
		JPanel panelTextosEntrenamiento = new JPanel(new GridLayout(3, 3, 100, 50));
		
		JLabel caloriasGastadas = new JLabel("Calorías gastadas: "+persona.getcaloriasGastadas());
		JLabel racha = new JLabel("Racha: "+persona.getrachaEntrenamiento());
		JLabel tiempoEntrenado= new JLabel("Tiempo entrenado: "+persona.gettiempoEntrenado());
		JLabel objetivo = new JLabel("Objetivo: "+persona.getObjetivo());
		JLabel ultimaVez = new JLabel("Última vez: "+persona.getultimaVezEntreno());
		ultimaVez.setBorder(new EmptyBorder(0, 0, 10, 0)); //Añadir margen inferior al JLabel
		
		panelTextosEntrenamiento.add(caloriasGastadas);
		panelTextosEntrenamiento.add(racha);
		panelTextosEntrenamiento.add(tiempoEntrenado);
		panelTextosEntrenamiento.add(objetivo);
		panelTextosEntrenamiento.add(ultimaVez);
		panelEntrenamiento.add(panelTextosEntrenamiento, BorderLayout.NORTH);
		
		botonEntrenar = new JButton("Quiero entrenar");
		panelEntrenamiento.add(botonEntrenar, BorderLayout.SOUTH);
	
		anadirBordePanel("ENTRENAMIENTO", panelEntrenamiento);
		panelIzquierda.add(panelEntrenamiento, BorderLayout.CENTER);
		
			//Grafica entrenamiento
		TimeSeriesCollection datasetEntrenamiento = crearDatasetEjemplo("Calorías quemadas");
		JFreeChart graficaEntrenamiento = crearGrafica("Calorías quemadas", "Dia", "Calorias", datasetEntrenamiento);
		ChartPanel panelGraficaEntrenamiento = new ChartPanel(graficaEntrenamiento);
		panelGraficaEntrenamiento.setPreferredSize(new Dimension(940, 500));
		
		panelEntrenamiento.add(panelGraficaEntrenamiento, BorderLayout.CENTER);
		
		//DIETA
		
		JPanel panelDerecha = new JPanel(new BorderLayout());
		add(panelDerecha, BorderLayout.EAST);
		
		JPanel panelDieta = new JPanel(new BorderLayout());
		JPanel panelTextosDieta = new JPanel();
		panelTextosDieta.setLayout(new GridLayout(3, 1, 0, 0));
		
		JLabel caloriasConsumidas = new JLabel("Calorías consumidas: "+persona.getcaloriasConsumidas());
		JLabel proximaComida = new JLabel("Próxima comida: "+persona.getproximaComida());
		JLabel vasosDeAgua = new JLabel("Vasos de agua: ");
		
		JPanel panelVasosAgua = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelVasosAgua.add(vasosDeAgua);
		panelVasosAgua.add(panelImagenVasos);
		
		List<JLabel> listaVasos = new ArrayList<JLabel>();
//		for (int i = 0; i < persona.getvasosDeAgua(); i++) {
//			ImageIcon vasotmp = new ImageIcon(System.getProperty("user.dir")+"\\resources\\images\\vasoLleno.png");
//			Image vaso = vasotmp.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
//			ImageIcon vasoBueno = new ImageIcon(vaso);
//			JLabel vasoL = new JLabel();
//			vasoL.setIcon(vasoBueno);
//			panelImagenVasos.add(vasoL);
//			listaVasos.add(vasoL);
//		}
		crearVasosDeAgua(listaVasos);
		actualizarVasosDeAgua(persona, listaVasos);
		
				
		
		panelTextosDieta.add(caloriasConsumidas);
		panelTextosDieta.add(proximaComida);
		panelTextosDieta.add(panelVasosAgua);
		panelDieta.add(panelTextosDieta, BorderLayout.NORTH);
		
		
		JButton botonDieta = new JButton("Revisar dieta");
		panelDieta.add(botonDieta, BorderLayout.SOUTH);

		anadirBordePanel("DIETA", panelDieta);
		panelDerecha.add(panelDieta, BorderLayout.CENTER);
			
			//Grafica dieta
		TimeSeriesCollection datasetDieta = crearDatasetEjemplo("Calorías consumidas");
		JFreeChart graficaDieta = crearGrafica("Calorías consumidas", "Dia", "Calorias", datasetDieta);
		ChartPanel panelGraficaDieta = new ChartPanel(graficaDieta);
		panelGraficaDieta.setPreferredSize(new Dimension(940, 500));
		
		panelDieta.add(panelGraficaDieta, BorderLayout.CENTER);
				
		
		JPanel panelArriba = new JPanel(new BorderLayout());
		add(panelArriba, BorderLayout.NORTH);
		

		
		JLabel alertaEntrenamiento = new JLabel("          VAMOS, QUE HOY TE TOCA ENTRENAR!          ");
		alertaEntrenamiento.setFont(new Font("verdana", Font.BOLD, 15));
		alertaEntrenamiento.setHorizontalAlignment(SwingConstants.CENTER);
		alertaEntrenamiento.setBorder(new EmptyBorder(40, 0, 40, 0));
		panelIzquierda.add(alertaEntrenamiento, BorderLayout.NORTH);
		
		JLabel alertaAgua = new JLabel("          RECUERDA BEBER AGUA!          ");
		alertaAgua.setFont(new Font("verdana", Font.BOLD, 15));
		alertaAgua.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panelArribaDerecha = new JPanel(new BorderLayout());
		panelArribaDerecha.add(alertaAgua, BorderLayout.CENTER);
		
		Image foto = persona.getFoto().getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
		JButton fotoPerfil = new JButton(new ImageIcon(foto));
		fotoPerfil.setPreferredSize(new Dimension(100,100));
		panelArribaDerecha.add(fotoPerfil, BorderLayout.EAST);
		
		panelDerecha.add(panelArribaDerecha, BorderLayout.NORTH);
		
		animacionTexto(alertaEntrenamiento);
		animacionTexto(alertaAgua);	
					
		
		//LISTENERS BOTONES
		
		botonEntrenar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton quiero entrenar pulsado");
				
			}
		});
		
		botonDieta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton revisar dieta pulsado");
				
			}
		});
		
		fotoPerfil.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Foto de perfil pulsada");
				
			}
		});

		
		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Resumen");
	}
	
	private void anadirBordePanel(String titulo, JPanel panel) {
		LineBorder borde = new LineBorder(Color.BLACK, 5); //Crea un estilo de borde continuo, anchura 5
		TitledBorder bordeConTitulo = new TitledBorder(borde, titulo); //Añade el estilo de borde con un titulo
		
		bordeConTitulo.setTitleJustification(TitledBorder.CENTER);
		bordeConTitulo.setTitleFont(new Font("verdana", Font.BOLD, 20));
		panel.setBorder(bordeConTitulo);
	}
	
	//Crear grafica
	private JFreeChart crearGrafica(String titulo, String tiempo, String valores, XYDataset xydataset) {
		JFreeChart chart = ChartFactory.createTimeSeriesChart(titulo, tiempo, valores, xydataset);
		chart.setBackgroundPaint(Color.WHITE);
		return chart;
	}
	
	//Dataset Ejemplo
	private TimeSeriesCollection crearDatasetEjemplo(String titulo) {
        TimeSeries s1 = new TimeSeries(titulo);
        
        for (int i = 1; i < LocalDateTime.now().getMonth().length(true)+1; i++) { //Incluye años bisiestos
        	s1.add(new Day(i, LocalDateTime.now().getMonthValue(), LocalDateTime.now().getYear()), 100+i*10);		
        	
		}

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s1);
        
        return dataset;

	}
	
	//Animacion de las alertas
	private void animacionTexto(JLabel texto) {
		Thread hilo = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					String textoAnterior = texto.getText();
					// Cojo el texto anterior desde la 2 letra hasta el final, y le sumo la primera letra
					String textoDesplazado = textoAnterior.substring(1)+texto.getText().substring(0,1);  
					texto.setText(textoDesplazado);
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
	
	private void crearVasosDeAgua(List<JLabel>listaVasos) {
		for (int i = 0; i < 8; i++) {
			listaVasos.add(new JLabel());
		}
	}
	
	private void actualizarVasosDeAgua(Usuario persona, List<JLabel> listaVasos) {
		for (int i = 0; i < persona.getvasosDeAgua(); i++) {
			ImageIcon vasotmp = new ImageIcon(System.getProperty("user.dir")+"\\resources\\images\\vasoLleno.png");
			Image vaso = vasotmp.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			ImageIcon vasoBueno = new ImageIcon(vaso);
			JLabel vasoL = new JLabel();
			vasoL.setIcon(vasoBueno);
			panelImagenVasos.add(vasoL);
			listaVasos.set(i, vasoL);
		}
		
		for (int i = persona.getvasosDeAgua(); i < 8; i++) {
			ImageIcon vasotmp = new ImageIcon(System.getProperty("user.dir")+"\\resources\\images\\vasoVacio.png");
			Image vaso = vasotmp.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			ImageIcon vasoBueno = new ImageIcon(vaso);
			JLabel vasoL = new JLabel();
			vasoL.setIcon(vasoBueno);
			panelImagenVasos.add(vasoL);
			listaVasos.set(i, vasoL);
		}
	}
	
}
