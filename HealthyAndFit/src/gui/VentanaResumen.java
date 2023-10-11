package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesTableModel;
import org.jfree.data.xy.XYDataset;

public class VentanaResumen extends JFrame{

	private JButton botonEntrenar;
	
	private static final long serialVersionUID = 1L;
	public VentanaResumen() {		
		
		//ENTRENAMIENTO
		JPanel panelEntrenamiento = new JPanel(new BorderLayout());
		JPanel panelTextosEntrenamiento = new JPanel();
		panelTextosEntrenamiento.setLayout(new GridLayout(3, 3, 100, 50));
		
		JLabel caloriasGastadas = new JLabel("Calorías gastadas: ");
		JLabel racha = new JLabel("Racha: ");
		JLabel tiempoEntrenado= new JLabel("Tiempo entrenado: ");
		JLabel objetivo = new JLabel("Objetivo: ");
		JLabel ultimaVez = new JLabel("Última vez: ");
		
		panelTextosEntrenamiento.add(caloriasGastadas);
		panelTextosEntrenamiento.add(racha);
		panelTextosEntrenamiento.add(tiempoEntrenado);
		panelTextosEntrenamiento.add(objetivo);
		panelTextosEntrenamiento.add(ultimaVez);
		panelEntrenamiento.add(panelTextosEntrenamiento, BorderLayout.NORTH);
		
		botonEntrenar = new JButton("Quiero entrenar");
		panelEntrenamiento.add(botonEntrenar, BorderLayout.SOUTH);
	
		anadirBordePanel("ENTRENAMIENTO", panelEntrenamiento);
		add(panelEntrenamiento, BorderLayout.WEST);
		
		//DIETA

		
		//LISTENERS BOTONES
		
		botonEntrenar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton quiero entrenar pulsado");
				
			}
		});
		
		
		TimeSeriesCollection dataset = crearDatasetEjemplo();
		JFreeChart grafica = crearGrafica("Calorías consumidas", "Dia", "Calorias", dataset);
		ChartPanel panelGraficaEntrenamiento = new ChartPanel(grafica);

		panelEntrenamiento.add(panelGraficaEntrenamiento, BorderLayout.CENTER);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		setSize(1920,1080);
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
	
	
	private JFreeChart crearGrafica(String titulo, String tiempo, String valores, XYDataset xydataset) {
		JFreeChart chart = ChartFactory.createTimeSeriesChart(titulo, tiempo, valores, xydataset);
		chart.setBackgroundPaint(Color.WHITE);
		return chart;
	}
	
	private TimeSeriesCollection crearDatasetEjemplo() {
        TimeSeries s1 = new TimeSeries("Calorías consumidas");
        
        for (int i = 1; i < 31; i++) {
        	s1.add(new Day(i, LocalDateTime.now().getMonthValue(), LocalDateTime.now().getYear()), 100+i*10);			
		}

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s1);
        
        return dataset;

	}
	
	
	
}
