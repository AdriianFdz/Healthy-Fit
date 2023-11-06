package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import db.BaseDeDatos;
import domain.TipoAlergias;
import domain.TipoEnfermedades;
import domain.TipoPermiso;
import domain.TipoPreferencia;
import domain.Usuario;

public class VentanaPerfil extends JFrame{
	//LOGGER
	private static Logger logger = Logger.getLogger(VentanaPerfil.class.getName());
	private static final long serialVersionUID = 1L;
	
	JPanel panelColum1;
	JLabel labelNombre;
	JLabel labelApellido1;
	JLabel labelApellido2;
	JLabel labelTipoU;
	JLabel labelFechaNac;

	JLabel labelEdad;
	JLabel labelSexo;
	JLabel labelAltura;
	JLabel labelEnfer;

	JLabel labelCorreo;
	JLabel labelPeso;
	JLabel labelIMC;
	JLabel labelPrefAli;
	JLabel labelAleg;
	
	public VentanaPerfil(Usuario p) {
		JPanel panelAbajo = new JPanel(new BorderLayout());
		add(panelAbajo, BorderLayout.SOUTH);

		panelColum1 = new JPanel();
		panelColum1.setLayout(new BoxLayout(panelColum1, BoxLayout.Y_AXIS));

		Image foto = p.getFoto().getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		JLabel fotoUsuario = new JLabel(new ImageIcon(foto));
		fotoUsuario.setPreferredSize(new Dimension(320, 320));
		panelColum1.add(fotoUsuario);

		labelNombre = new JLabel(String.format("Nombre: %s", p.getNombre().toUpperCase()));
		labelApellido1 = new JLabel(String.format("Apellidos: %s %s", p.getApellido1().toUpperCase(), p.getApellido2().toUpperCase()));
		labelFechaNac = new JLabel(String.format("Nacimiento: %s",p.getfechaNacimiento().toString()));
		labelTipoU = new JLabel(String.format("Rango: %s", p.getPermiso().name()));

		panelColum1.add(labelNombre);
		panelColum1.add(labelApellido1);
		panelColum1.add(labelFechaNac);
		panelColum1.add(labelTipoU);
		
		// Botones
		JButton modificarBot = new JButton("MODIFICAR DATOS");
		JButton accesoPanelBot = new JButton("ACCESO PANEL");

		JButton volverBot = new JButton("VOLVER");
		JButton botonCerSesion = new JButton("CERRAR SESION");

		panelColum1.add(modificarBot);
		panelColum1.add(accesoPanelBot);

		// Definir la condicion del boton Acceso
		if (p.getPermiso() != TipoPermiso.ADMINISTRADOR) {
			accesoPanelBot.setVisible(false);
			logger.log(Level.INFO, "Panel bloqueado por no tener suficientes permisos");
		}

		add(panelColum1, BorderLayout.WEST);

		panelAbajo.add(volverBot, BorderLayout.WEST);
		panelAbajo.add(botonCerSesion, BorderLayout.EAST);

		JPanel panelDatos = new JPanel(new GridLayout(6,2,0,0));
		
		int edad = Period.between(p.getfechaNacimiento(), LocalDate.now()).getYears();

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
	

		labelPrefAli = new JLabel("PREFERENCIAS ALIMENTICIAS");
		JComboBox<TipoPreferencia> preferenciasComboBox = new JComboBox<TipoPreferencia>();
		preferenciasComboBox.addItem(p.getpreferenciaAlimenticia());

		JLabel labelEnfermedades = new JLabel("ENFERMEDADES");

		labelCorreo = new JLabel(String.format("Correo: %s", p.getcorreoElectronico()));
		labelPeso = new JLabel(String.format("Peso: %d", p.getPeso()));
		labelIMC = new JLabel(String.format("IMC: %.2f", p.getImc()));
		labelAleg = new JLabel("ALERGIAS");

		JScrollPane paneAlergias = new JScrollPane();
		for (TipoAlergias alergia : p.getAlergias()) {
			JLabel label = new JLabel(alergia.name());
			paneAlergias.add(label);
		}
		
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
		panelDatos.add(panelEnfermedades);
		JPanel panelAlergias = new JPanel();
			panelAlergias.setLayout(new BoxLayout(panelAlergias, BoxLayout.Y_AXIS));
			panelAlergias.add(labelAleg);
			panelAlergias.add(paneAlergias);
		panelDatos.add(panelAlergias);
		panelDatos.add(labelPrefAli);
		panelDatos.add(preferenciasComboBox);
		
		
		JPanel panelDerecha = new JPanel(new BorderLayout());
			panelDerecha.add(panelDatos, BorderLayout.NORTH);
			
		add(panelDerecha, BorderLayout.CENTER);
			
		// LISTENERS
		modificarBot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		accesoPanelBot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new VentanaPanel());
				dispose();
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
				// TODO Auto-generated method stub
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

	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new VentanaPerfil(BaseDeDatos.getListaUsuarios().get(0)));
	}
}
