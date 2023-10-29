package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;

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
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import domain.TipoAlergias;
import domain.TipoEnfermedades;
import domain.TipoPermiso;
import domain.TipoPreferencia;
import domain.Usuario;

public class VentanaPerfil extends JFrame{
	private static final long serialVersionUID = 1L;
	
	JPanel panel1;
	JPanel panel2;
	JPanel panelColum1;
	JLabel labelNombre;
	JLabel labelApellido1;
	JLabel labelApellido2;
	JLabel labelTipoU;
	JLabel labelFechaNac;

	JPanel panelColum2;
	JLabel labelEdad;
	JLabel labelSexo;
	JLabel labelAltura;
	JLabel labelEnfer;

	JPanel panelColum3;
	JLabel labelCorreo;
	JLabel labelPeso;
	JLabel labelIMC;
	JLabel labelPrefAli;
	JLabel labelAleg;
	
	public VentanaPerfil(Usuario p) {
		panel1 = new JPanel(new GridLayout(1, 3));
		panel2 = new JPanel(new BorderLayout());
		add(panel2, BorderLayout.SOUTH);

		panelColum1 = new JPanel();
		panelColum1.setLayout(new BoxLayout(panelColum1, BoxLayout.Y_AXIS));

		Image foto = p.getFoto().getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		JLabel fotoUsuario = new JLabel(new ImageIcon(foto));
		fotoUsuario.setPreferredSize(new Dimension(320, 320));
		panelColum1.add(fotoUsuario);

		labelNombre = new JLabel(p.getNombre());
		labelApellido1 = new JLabel(p.getApellido1());
		labelApellido2 = new JLabel(p.getApellido2());
		labelFechaNac = new JLabel(p.getfechaNacimiento().toString()); // esto no se como seria con lo de DATE
		labelTipoU = new JLabel(p.getPermiso().name());

		panelColum1.add(labelNombre);
		panelColum1.add(labelApellido1);
		panelColum1.add(labelApellido2);
		panelColum1.add(labelTipoU);
		panelColum1.add(labelFechaNac);

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
		}

		panel1.add(panelColum1);

		panel2.add(volverBot, BorderLayout.WEST);
		panel2.add(botonCerSesion, BorderLayout.EAST);

		panelColum2 = new JPanel();
		panelColum2.setLayout(new BoxLayout(panelColum2, BoxLayout.Y_AXIS));

		int edad = Period.between(p.getfechaNacimiento(), LocalDate.now()).getYears();

		labelEdad = new JLabel(edad + "");
		labelSexo = new JLabel(p.getSexo().name());
		labelAltura = new JLabel(Double.toString(p.getAltura()));
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
		// for (TipoPreferencia preferencia : p.getpreferenciaAlimenticia() ) {
		// preferenciasComboBox.addItem(preferencia);
		// }

		panelColum2.add(labelEdad);
		panelColum2.add(labelSexo);
		panelColum2.add(labelAltura);
		panelColum2.add(new Label("ENFERMEDADES"));
		panelColum2.add(paneEnfermedades);
		panelColum2.add(new Label("PREFERENCIA ALIMENTICIA"));
		panelColum2.add(preferenciasComboBox);

		panel1.add(panelColum2);

		
		panelColum3 = new JPanel();
		panelColum3.setLayout(new BoxLayout(panelColum3, BoxLayout.Y_AXIS));

		labelCorreo = new JLabel(p.getcorreoElectronico());
		labelPeso = new JLabel(Double.toString(p.getPeso()));
		labelIMC = new JLabel(Double.toString(p.getImc()));
		labelAleg = new JLabel("ALERGIAS");

		JScrollPane paneAlergias = new JScrollPane();
		for (TipoAlergias alergia : p.getAlergias()) {
			JLabel label = new JLabel(alergia.name());
			paneAlergias.add(label);
		}

		panelColum3.add(labelCorreo);
		panelColum3.add(labelPeso);
		panelColum3.add(labelIMC);
		panelColum3.add(labelAleg);
		panelColum3.add(paneAlergias);

		panel1.add(panelColum3);
		
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
				// TODO Auto-generated method stub
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

		this.add(panel1);
		this.pack();
		this.setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Healthy & Fit");
	}
	
}
