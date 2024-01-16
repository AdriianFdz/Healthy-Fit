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
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import db.DBManager;
import domain.Dieta;
import domain.Entrenamiento;
import domain.TipoAlergias;
import domain.TipoDificultad;
import domain.Usuario;
import io.ExportarDatos;
import io.RegistroLogger;

public class VentanaResumen extends JFrame{

	//Propiedades de la ventana
	private JButton botonEntrenar;
	private JButton botonQueEntrenar;
	private JButton fotoPerfil;
	private JButton botonDieta;
	private JPanel panelImagenVasos = new JPanel();
	private Usuario persona;
	 // Hacer mapa con dia y dieta
	
	private static final long serialVersionUID = 1L;
	public VentanaResumen(Usuario usuario) {		
		persona = usuario;
		persona.setProximaComida(asignarDietaADia(persona.getProximaComida()));
		
		int caloriasGast = 0;
		int tiempoEnt = 0;
		for (Entrenamiento entrenamiento : persona.getRegistroEntrenamiento()) {
			caloriasGast += entrenamiento.getCalorias();
			tiempoEnt += entrenamiento.getTiempo();
		}
		persona.setCaloriasGastadas(caloriasGast);			
		persona.setTiempoEntrenado(tiempoEnt);
		int caloriasConsum = 0;
		for (Entry<LocalDate, Dieta> entry : persona.getProximaComida().entrySet()) {
			caloriasConsum += entry.getValue().getKcal();
		}
		persona.setCaloriasConsumidas(caloriasConsum);
		
		//ENTRENAMIENTO
		JLabel caloriasGastadas = new JLabel("Calorías gastadas: "+persona.getCaloriasGastadas());
		JLabel tiempoEntrenado= new JLabel("Tiempo entrenado: "+persona.getTiempoEntrenado() + " minutos");
		JLabel ultimaVez = new JLabel("Última vez: "+persona.getUltimaVezEntreno());
		ultimaVez.setBorder(new EmptyBorder(0, 0, 10, 0)); //Añadir margen inferior al JLabel

		//Cambios de estilo y tamaño de letra
		caloriasGastadas.setFont(new Font("Consolas", Font.PLAIN ,25));
		tiempoEntrenado.setFont(new Font("Consolas", Font.PLAIN, 25));
		ultimaVez.setFont(new Font("Consolas", Font.PLAIN, 25));
		
		
		JPanel panelTextosEntrenamiento = new JPanel(new GridLayout(3, 3, 50, 30));
			panelTextosEntrenamiento.add(caloriasGastadas);
			panelTextosEntrenamiento.add(tiempoEntrenado);
			panelTextosEntrenamiento.add(ultimaVez);
		
		JPanel panelEntrenamiento = new JPanel(new BorderLayout());
			panelEntrenamiento.add(panelTextosEntrenamiento, BorderLayout.NORTH);
			JPanel panelBotones = new JPanel(new GridLayout(1,2));
				botonEntrenar = new JButton("Quiero entrenar");  
				panelBotones.add(botonEntrenar);
				botonQueEntrenar = new JButton("Que entrenar");
				panelBotones.add(botonQueEntrenar);
			panelEntrenamiento.add(panelBotones, BorderLayout.SOUTH);
			anadirBordePanel("ENTRENAMIENTO", panelEntrenamiento);
			
		JPanel panelIzquierda = new JPanel(new BorderLayout());
			panelIzquierda.add(panelEntrenamiento, BorderLayout.CENTER);
		add(panelIzquierda, BorderLayout.WEST);
		
		//Grafica entrenamiento
		TimeSeriesCollection datasetEntrenamiento = crearDatasetEntrenamiento(persona);
	
		JFreeChart graficaEntrenamiento = crearGrafica("Calorías quemadas", "Dia", "Calorias", datasetEntrenamiento);
		ChartPanel panelGraficaEntrenamiento = new ChartPanel(graficaEntrenamiento);
		Dimension resPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		RegistroLogger.getLogger().log(Level.INFO, resPantalla.toString());
		
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
					ImageIcon iconoMinus = null;
					try {
						iconoMinus = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/minus.png")));
					} catch (IOException e) {
						e.printStackTrace();
					}
					Image iconoMinusImg = iconoMinus.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
					ImageIcon iconoMinusResized = new ImageIcon(iconoMinusImg);
					btnEliminarVaso.setIcon(iconoMinusResized);
					
				JButton btnAnadirVaso = new JButton();
					ImageIcon iconoPlus = null;
					try {
						iconoPlus = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/plus.png")));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
		
		botonQueEntrenar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SpinnerNumberModel modeloSpinner = new SpinnerNumberModel(1, 1, 7, 1);
				JSpinner spinner = new JSpinner(modeloSpinner);
				int opcion = JOptionPane.showOptionDialog(null, spinner, "Elige cuantos dias pretendes entrenar", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 1);
				if (opcion == JOptionPane.OK_OPTION) {					
					Connection conn = DBManager.obtenerConexion();
					List<Entrenamiento> listaEntrenamientos = DBManager.obtenerEntrenamientos(conn);
					try {
						conn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
						RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo conectar con la base de datos");
						JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
					}
					List<Map<String, Entrenamiento>> resultado = calcularCombinaciones((int)modeloSpinner.getValue(), listaEntrenamientos, new HashMap<>(), new ArrayList<Map<String, Entrenamiento>>());
					ExportarDatos.exportarCombinaciones(resultado, "Guardar combinaciones en...");
					JOptionPane.showMessageDialog(null, "Archivo exportado con éxito");
				}
				
			}
		});
		
		
		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Resumen");
	}
	
    private static List<Map<String, Entrenamiento>> calcularCombinaciones(int dias, List<Entrenamiento> entrenamientos, Map<String, Entrenamiento> resultadoInterior, List<Map<String, Entrenamiento>> resultado) {
        String nombreDia = obtenerNombreDia(dias);

        for (Entrenamiento entrenamiento : entrenamientos) {
            resultadoInterior.put(nombreDia, entrenamiento);

            if (dias == 1) {
                resultado.add(new HashMap<>(resultadoInterior));
            } else {
                calcularCombinaciones(dias-1, entrenamientos, new HashMap<>(resultadoInterior), resultado);
            }
        }
        return resultado;
    }
	
	private static String obtenerNombreDia(int dias) {
        switch (dias) {
            case 1:
                return "Lunes";
            case 2:
                return "Martes";
            case 3:
                return "Miercoles";
            case 4:
                return "Jueves";
            case 5:
                return "Viernes";
            case 6:
                return "Sabado";
            default:
                return "Domingo";
        }
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

	//Crear dataset
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
			RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo conectar con la base de datos");
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
			RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo conectar con la base de datos");
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
						RegistroLogger.getLogger().log(Level.SEVERE, "Hilo de animacion de texto interrumpido");
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
			ImageIcon vasotmp = null;
			try {
				vasotmp = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/vasoLleno.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Image vaso = vasotmp.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			ImageIcon vasoBueno = new ImageIcon(vaso);
			JLabel vasoL = new JLabel();
			vasoL.setIcon(vasoBueno);
			panelImagenVasos.add(vasoL);
			listaVasos.set(i, vasoL);
		}
		
		for (int i = persona.getVasosDeAgua(); i < 8; i++) {
			ImageIcon vasotmp = null;
			try {
				vasotmp = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/vasoVacio.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Image vaso = vasotmp.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			ImageIcon vasoBueno = new ImageIcon(vaso);
			JLabel vasoL = new JLabel();
			vasoL.setIcon(vasoBueno);
			panelImagenVasos.add(vasoL);
			listaVasos.set(i, vasoL);
		}
		
		RegistroLogger.getLogger().log(Level.INFO, "Vasos de agua actualizados");
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
				RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo conectar con la base de datos");
				JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
			}
			
			for (int i = 0; i < cantidad; i++) {
				ImageIcon vasotmp = null;
				try {
					vasotmp = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/vasoLleno.png")));
				} catch (IOException e) {
					e.printStackTrace();
				}
				Image vaso = vasotmp.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
				ImageIcon vasoBueno = new ImageIcon(vaso);
				
				listaVasos.get(i).setIcon(vasoBueno);
			}
			
			for (int i = cantidad; i < 8; i++) {
				ImageIcon vasotmp = null;
				try {
					vasotmp = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/vasoVacio.png")));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
				
				PreparedStatement stmtAlergias= conn.prepareStatement("SELECT nombreAlergia FROM alergias WHERE id IN (SELECT alergia FROM dieta_alergias WHERE nombreDieta = ?)");
				stmtAlergias.setString(1, nombre);
				ResultSet rsAlergias = stmtAlergias.executeQuery();
				List<TipoAlergias> alergiasDieta = new ArrayList<TipoAlergias>();
				while (rsAlergias.next()) {
					alergiasDieta.add(TipoAlergias.valueOf(rsAlergias.getString("nombreAlergia")));
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
			RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo conectar con la base de datos");
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
