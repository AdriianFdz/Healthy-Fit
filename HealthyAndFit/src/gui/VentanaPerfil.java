package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.logging.Level;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import domain.TipoAlergias;
import domain.TipoEnfermedades;
import domain.TipoPermiso;
import domain.Usuario;
import io.RegistroLogger;

public class VentanaPerfil extends JFrame{
	//LOGGER
	private static final long serialVersionUID = 1L;
	
	private JPanel panelColum1;
	private JLabel labelNombre;
	private JLabel labelApellido1;
	private JLabel labelApellido2;
	private JLabel labelTipoU;
	private JLabel labelFechaNac;

	private JLabel labelEdad;
	private JLabel labelSexo;
	private JLabel labelAltura;
	private JLabel labelEnfer;

	private JLabel labelCorreo;
	private JLabel labelPeso;
	private JLabel labelIMC;
	private JLabel labelAleg;
	private JLabel fotoUsuario;
	
	public VentanaPerfil(Usuario p) {
		JPanel panelAbajo = new JPanel(new BorderLayout());
		add(panelAbajo, BorderLayout.SOUTH);

		panelColum1 = new JPanel();
		panelColum1.setLayout(new BoxLayout(panelColum1, BoxLayout.Y_AXIS));

		Image foto = p.getFoto().getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		fotoUsuario = new JLabel(new ImageIcon(foto));
		fotoUsuario.setPreferredSize(new Dimension(320, 320));
		panelColum1.add(fotoUsuario);

		labelNombre = new JLabel(String.format("Nombre: %s", p.getNombre().toUpperCase()));
		labelApellido1 = new JLabel(String.format("Apellidos: %s %s", p.getApellido1().toUpperCase(), p.getApellido2().toUpperCase()));
		labelFechaNac = new JLabel(String.format("Nacimiento: %s",p.getFechaNacimiento().toString()));
		labelTipoU = new JLabel(String.format("Rango: %s", p.getPermiso().name()));

		panelColum1.add(labelNombre);
		panelColum1.add(labelApellido1);
		panelColum1.add(labelFechaNac);
		panelColum1.add(labelTipoU);
		
		// Botones
		JButton modificarBot = new JButton("MODIFICAR DATOS");
		JButton accesoPanelBot = new JButton("ACCESO PANEL");
		JButton historialBot = new JButton("ACCESO HISTORIAL");
 
		JButton volverBot = new JButton("VOLVER");
		JButton botonCerSesion = new JButton("CERRAR SESION");

		panelColum1.add(modificarBot);
		panelColum1.add(historialBot);
		panelColum1.add(accesoPanelBot);
		

		// Definir la condicion del boton Acceso
		if (p.getPermiso() != TipoPermiso.ADMINISTRADOR) {
			accesoPanelBot.setVisible(false);
			RegistroLogger.anadirLogeo(Level.INFO, "Panel bloqueado por no tener suficientes permisos");
		}

		add(panelColum1, BorderLayout.WEST);

		panelAbajo.add(volverBot, BorderLayout.WEST);
		panelAbajo.add(botonCerSesion, BorderLayout.EAST);

		JPanel panelDatos = new JPanel(new GridLayout(7,2,0,0));
		
		int edad = Period.between(p.getFechaNacimiento(), LocalDate.now()).getYears();

		labelEdad = new JLabel(String.format("Edad: %d", edad));
		labelSexo = new JLabel(String.format("Sexo: %s", p.getSexo().name()));
		labelAltura = new JLabel(String.format("Altura: %.2f", p.getAltura()));
		labelEnfer = new JLabel("Enfermedades");

		DefaultListModel<TipoEnfermedades> modeloLista = new DefaultListModel<>();
        for (TipoEnfermedades enfermedad : p.getEnfermedades()) {
            modeloLista.addElement(enfermedad);
        }
        JList<TipoEnfermedades> listaEnfe = new JList<>(modeloLista);
		JScrollPane paneEnfermedades = new JScrollPane(listaEnfe);
	

		JLabel labelEnfermedades = new JLabel("ENFERMEDADES");

		labelCorreo = new JLabel(String.format("Correo: %s", p.getCorreoElectronico()));
		labelPeso = new JLabel(String.format("Peso: %d", p.getPeso()));
		labelIMC = new JLabel(String.format("IMC: %.2f", p.getImc()));
		labelAleg = new JLabel("ALERGIAS");

		DefaultListModel<TipoAlergias> modeloAlergias = new DefaultListModel<>();
        for (TipoAlergias alergia : p.getAlergias()) {
            modeloAlergias.addElement(alergia);
        }
        JList<TipoAlergias> listaAlergias = new JList<>(modeloAlergias);
		JScrollPane paneAlergias = new JScrollPane(listaAlergias);
		
		//Cambios de estilo y tamaño de letra
		labelNombre.setFont(new Font("Consolas", Font.PLAIN, 20));
		labelApellido1.setFont(new Font("Consolas", Font.PLAIN, 20));
		labelTipoU.setFont(new Font("Consolas", Font.PLAIN, 20));
		labelFechaNac.setFont(new Font("Consolas", Font.PLAIN, 20));

		labelEdad.setFont(new Font("Consolas", Font.PLAIN, 22));
		labelSexo.setFont(new Font("Consolas", Font.PLAIN, 22));
		labelAltura.setFont(new Font("Consolas", Font.PLAIN, 22));
		labelEnfer.setFont(new Font("Consolas", Font.PLAIN, 22));

		labelCorreo.setFont(new Font("Consolas", Font.PLAIN, 22));
		labelPeso.setFont(new Font("Consolas", Font.PLAIN, 22));
		labelIMC.setFont(new Font("Consolas", Font.PLAIN, 22));
		labelAleg.setFont(new Font("Consolas", Font.BOLD, 22));
		labelEnfermedades.setFont(new Font("Consolas", Font.BOLD, 22));
		
		
		panelDatos.add(labelEdad);
		panelDatos.add(labelCorreo);
		panelDatos.add(labelSexo);
		panelDatos.add(labelPeso);
		panelDatos.add(labelAltura);
		panelDatos.add(labelIMC);
		//panelDatos.add(labelEnfermedades);
		JPanel panelEnfermedades = new JPanel();
			panelEnfermedades.setLayout(new BoxLayout(panelEnfermedades, BoxLayout.Y_AXIS));
			panelEnfermedades.add(labelEnfermedades);
			panelEnfermedades.add(paneEnfermedades);
		JPanel panelAlergias = new JPanel();
			panelAlergias.setLayout(new BoxLayout(panelAlergias, BoxLayout.Y_AXIS));
			panelAlergias.add(labelAleg);
			panelAlergias.add(paneAlergias);
			
			
		panelDatos.add(panelEnfermedades);
		panelDatos.add(panelAlergias);
	
		
		
		JPanel panelDerecha = new JPanel(new BorderLayout());
			panelDerecha.add(panelDatos, BorderLayout.NORTH);
			
			 JScrollPane scrollPane = new JScrollPane(panelDerecha);
		        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Ajusta el desplazamiento vertical	
		 
		VentanaResumen.anadirBordePanel("PERFIL", panelDerecha);
		add(scrollPane, BorderLayout.CENTER);
			
		// LISTENERS
		modificarBot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new VentanaEditarPerfil(p, p, VentanaPerfil.this, null, true));
				dispose();
			}
		});

		accesoPanelBot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new VentanaPanel(p));
				dispose();
			}
		});
		
		historialBot.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					try {
						new VentanaHistorial(p);
					} catch (SQLException e1) {
						e1.printStackTrace();
						RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
						JOptionPane.showConfirmDialog(null, "Error al cargar la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
						
					}
				});
				
			}
		});

		botonCerSesion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new VentanaLogeoRegistro());
				dispose();
			}
		});
		
		volverBot.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new VentanaResumen(p));
				dispose();
			}
		});
		
		this.pack();
		this.setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Perfil");
	}
	
	// METODOS UTILIDAD
	public void cambiarFoto(ImageIcon foto){
		fotoUsuario.setIcon(foto);
		repaint();
	}
}
