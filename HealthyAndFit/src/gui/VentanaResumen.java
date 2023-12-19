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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.xy.XYDataset;

import domain.Dieta;
import domain.TipoAlergias;
import domain.TipoDificultad;
import domain.Usuario;
import io.DBManager;
import io.RegistroLogger;

public class VentanaResumen extends JFrame{

	//Propiedades de la ventana
	 JButton botonEntrenar;
	 JButton fotoPerfil;
	 JButton botonDieta;
	 JPanel panelImagenVasos = new JPanel();
	 Usuario persona;
	 // Hacer mapa con dia y dieta
	
	private static final long serialVersionUID = 1L;
	public VentanaResumen(Usuario persona) {		
		this.persona = persona;
		this.persona.setProximaComida(asignarDietaADia(this.persona.getProximaComida()));
		
		
		
		
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
//		TimeSeriesCollection datasetEntrenamiento = crearDatasetEjemplo("Calorías quemadas");
		TimeSeriesCollection datasetEntrenamiento = crearDatasetEntrenamiento(persona);
	
		JFreeChart graficaEntrenamiento = crearGrafica("Calorías quemadas", "Dia", "Calorias", datasetEntrenamiento);
		ChartPanel panelGraficaEntrenamiento = new ChartPanel(graficaEntrenamiento);
		Dimension resPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		RegistroLogger.anadirLogeo(Level.INFO, resPantalla.toString());
		
		panelGraficaEntrenamiento.setPreferredSize(new Dimension(resPantalla.getSize().width/2,resPantalla.getSize().height/2));
		panelEntrenamiento.add(panelGraficaEntrenamiento, BorderLayout.CENTER);
		
		
		
		//DIETA
		JLabel caloriasConsumidas = new JLabel("Calorías consumidas: "+this.persona.getCaloriasConsumidas());
		JLabel proximaComida = new JLabel("Próxima comida: "+this.persona.getProximaComida().get(LocalDate.now()).getNombre());
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
			actualizarVasosDeAgua(this.persona, listaVasos);
		
				
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
		//TimeSeriesCollection datasetDieta = crearDatasetEjemplo("Calorías consumidas");
		TimeSeriesCollection datasetDieta = crearDatasetDietas(this.persona);
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
			Image foto = this.persona.getFoto().getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
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
				SwingUtilities.invokeLater(() -> new VentanaEntrenamiento(persona, VentanaResumen.this));
				
			}
		});
	
		botonDieta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new VentanaDieta(persona.getProximaComida().get(LocalDate.now())));
				
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
		
        NumberAxis ejeY = (NumberAxis) chart.getXYPlot().getRangeAxis();
        ejeY.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
       
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
	
	public TimeSeriesCollection crearDatasetEntrenamiento(Usuario usuario){
		TimeSeries ts = new TimeSeries("Calorias quemadas");
        TimeSeriesCollection dataset = new TimeSeriesCollection();
		
		Connection conn = DBManager.obtenerConexion();
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuario_entrenamientos WHERE nombreUsuario = ?");
			stmt.setString(1, usuario.getNombreUsuario());
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				LocalDateTime fecha = LocalDateTime.parse(rs.getString("fecha"));
				String nombreEntrenamiento = rs.getString("nombreEntrenamiento");
				String dificultad = rs.getString("dificultad");
				PreparedStatement stmtEntrena = conn.prepareStatement("SELECT calorias FROM entrenamientos WHERE nombre = ? AND dificultad = ?");
				stmtEntrena.setString(1, nombreEntrenamiento);
				stmtEntrena.setString(2, dificultad);
				ResultSet rsEntrena = stmtEntrena.executeQuery();
				
				int caloriasTotales = 0;
				while (rsEntrena.next()) {
					int calorias = rsEntrena.getInt("calorias");
					Day fechaConvertida = new Day(fecha.getDayOfMonth(), fecha.getMonthValue(), fecha.getYear());
					if (ts.getValue(fechaConvertida) != null) {
						caloriasTotales = ts.getValue(fechaConvertida).intValue()+calorias;
					} else {						
						caloriasTotales = calorias;
					}
					ts.addOrUpdate(fechaConvertida, caloriasTotales);
				}
				stmtEntrena.close();
			}
	        dataset.addSeries(ts);	        
	        
	        stmt.close();
	        conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
		}
		repaint();
		return dataset;
	
	}
	
	public TimeSeriesCollection crearDatasetDietas(Usuario usuario) {
		TimeSeries ts = new TimeSeries("Calorias consumidas");
        TimeSeriesCollection dataset = new TimeSeriesCollection();
		
		Connection conn = DBManager.obtenerConexion();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuario_dieta WHERE nombreUsuario = ?");
			stmt.setString(1, usuario.getNombreUsuario());
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				LocalDate fecha = LocalDate.parse(rs.getString("fecha"));
				String nombreDieta = rs.getString("nombreDieta");
				
				PreparedStatement stmtDieta = conn.prepareStatement("SELECT kcal FROM dietas WHERE nombre = ?");
				stmtDieta.setString(1, nombreDieta);
				ResultSet rsDieta = stmtDieta.executeQuery();
				
				while (rsDieta.next()) {
					int calorias = rsDieta.getInt("kcal");
					Day fechaConvertida = new Day(fecha.getDayOfMonth(), fecha.getMonthValue(), fecha.getYear());
					ts.add(fechaConvertida, calorias);
				}
				stmtDieta.close();
			}
			dataset.addSeries(ts);
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
		}
		repaint();
		return dataset;
	}
	//Animacion de las alertas
	
	//Programa basado en https://www.tutorialspoint.com/how-can-we-implement-a-moving-text-using-a-jlabel-in-java
	public static void animacionTexto(JLabel texto) {
		Thread hilo = new Thread(new Runnable() {
			
			@Override
			public void run() {
				boolean detener = false;
				while (!detener) {
					String textoAnterior = texto.getText();
					// Cojo el texto anterior desde la 2 letra hasta el final, y le sumo la primera letra
					try {
					String textoDesplazado = textoAnterior.substring(1)+texto.getText().substring(0,1);  
					SwingUtilities.invokeLater(() -> texto.setText(textoDesplazado));
					} catch (StringIndexOutOfBoundsException e) {
						detener = true;
					}
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						System.out.println("Hilo de animaciones de texto interrumpido");
						RegistroLogger.anadirLogeo(Level.SEVERE, "Hilo de animacion de texto interrumpido");
						JOptionPane.showConfirmDialog(null, "Error en la animación del texto", "Error", JOptionPane.PLAIN_MESSAGE);
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
			Connection conn = DBManager.obtenerConexion();
			DBManager.modificarVasosAgua(conn, persona, cantidad);
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
				JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
			}
			
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
	
	
	private Map<LocalDate, Dieta> asignarDietaADia(Map<LocalDate, Dieta> dietaPorDia) {
		//Asignar dieta diaria
		Connection conn = DBManager.obtenerConexion();
		Statement stmt;
		List<Dieta> dietas = new ArrayList<Dieta>();
		try {		
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM dietas");
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				int tiempo = rs.getInt("tiempo");
				TipoDificultad dificultad = TipoDificultad.valueOf(rs.getString("dificultad"));
				int kcal = rs.getInt("kcal");
				
				PreparedStatement stmtPasos = conn.prepareStatement("SELECT * FROM pasos_dietas WHERE nombreDieta = ?");
				stmtPasos.setString(1, nombre);
				ResultSet rsPasos = stmtPasos.executeQuery();
				List<String> pasosDieta = new ArrayList<String>();
				while (rsPasos.next()) {
					pasosDieta.add(rsPasos.getString("denominacion"));
				}
				stmtPasos.close();
				
				PreparedStatement stmtIngredientes = conn.prepareStatement("SELECT * FROM ingredientes_dietas WHERE nombreDieta = ?");
				stmtIngredientes.setString(1, nombre);
				ResultSet rsIngredientes = stmtIngredientes.executeQuery();
				List<String> ingredientesDieta = new ArrayList<String>();
				while (rsIngredientes.next()) {
					ingredientesDieta.add(rsIngredientes.getString("nombreIngrediente"));
				}
				stmtIngredientes.close();
				
				PreparedStatement stmtAlergias= conn.prepareStatement("SELECT * FROM dieta_alergias WHERE nombreDieta = ?");
				stmtAlergias.setString(1, nombre);
				ResultSet rsAlergias = stmtAlergias.executeQuery();
				List<TipoAlergias> alergiasDieta = new ArrayList<TipoAlergias>();
				while (rsAlergias.next()) {
					alergiasDieta.add(TipoAlergias.valueOf(rsAlergias.getString("alergia")));
				}
				stmtAlergias.close();
				Dieta dieta = new Dieta(nombre, tiempo, dificultad, kcal, pasosDieta, ingredientesDieta, alergiasDieta);
				dietas.add(dieta);
	
			}
			
			stmt.close();

			Dieta dietaHoy;
			do {
				dietaHoy = dietas.get((int) (Math.random()*dietas.size()));
			} while (!Collections.disjoint(dietaHoy.getAlergias(), persona.getAlergias()));
			dietaPorDia.putIfAbsent(LocalDate.now(ZoneId.of("Europe/Madrid")), dietaHoy);
			DBManager.anadirUsuarioDieta(conn, persona, dietaHoy, LocalDate.now());
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
		}
	
		return dietaPorDia;
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
