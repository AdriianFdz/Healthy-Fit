package gui;

import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import domain.TipoEnfermedades;
import domain.TipoPreferencia;
import domain.Usuario;



public class VentanaPerfil extends JFrame{
	JPanel panel1;
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

		panelColum1 = new JPanel();
		panelColum1.setLayout(new BoxLayout(panelColum1, BoxLayout.Y_AXIS));

		ImageIcon imageIcon = new ImageIcon("");
		JLabel fotoUsuario = new JLabel(imageIcon);
		panelColum1.add(fotoUsuario);

		labelNombre = new JLabel(p.getNombre());
		labelApellido1 = new JLabel(p.getApellido1());
		labelApellido2 = new JLabel(p.getApellido2());
		labelFechaNac = new JLabel(p.getfechaNacimiento().toString()); //esto no se como seria con lo de DATE
		labelTipoU = new JLabel(""); //donde dice q tipo de usuario es

		panelColum1.add(labelNombre);
		panelColum1.add(labelApellido1);
		panelColum1.add(labelApellido2);
		panelColum1.add(labelTipoU);
		panelColum1.add(labelFechaNac);

		JButton modificarBot = new JButton("MODIFICAR DATOS");
		JButton accesoPanelBot = new JButton("ACEESO PANEL");

		panelColum1.add(modificarBot);
		panelColum1.add(accesoPanelBot);

		panel1.add(panelColum1);

		panelColum2 = new JPanel();
		panelColum2.setLayout(new BoxLayout(panelColum2, BoxLayout.Y_AXIS));

		//Problema con la fecha, da una exception debido al tipo de datos
		LocalDate ahora = LocalDate.now();
		Period periodo = Period.between(LocalDate.of(p.getfechaNacimiento().getYear(), p.getfechaNacimiento().getHours(), p.getfechaNacimiento().getMinutes()), ahora);
		
		labelEdad = new JLabel(periodo.toString());
		labelSexo = new JLabel(p.getSexo().name());
		labelAltura = new JLabel(Double.toString(p.getAltura()));
		labelEnfer = new JLabel("Enfermedades");

		JScrollPane paneEnfermedades = new JScrollPane();
		//for (TipoEnfermedades p.getEnfermedades() : enfermedades) {


		labelPrefAli = new JLabel("PREFERENCIAS ALIMENTICIAS");
		JComboBox<TipoPreferencia> preferenciasComboBox = new JComboBox<TipoPreferencia>();
		//for (TipoPreferencia preferenciaAlimenticia : ) {
			
		//}
		
		panelColum2.add(labelEdad);
		panelColum2.add(labelSexo);
		panelColum2.add(labelAltura);
		panelColum2.add(paneEnfermedades);
		panelColum2.add(preferenciasComboBox);
		
		panel1.add(panelColum2);

		panelColum3 = new JPanel();
		panelColum3.setLayout(new BoxLayout(panelColum3, BoxLayout.Y_AXIS));

		labelCorreo = new JLabel(p.getcorreoElectronico());
		labelPeso = new JLabel(Double.toString(p.getPeso()));
		labelIMC = new JLabel(""); //falta imc
		labelAleg = new JLabel("ALERGIAS");
		JScrollPane paneAlergias = new JScrollPane();
		
		
		panelColum3.add(labelEdad);
		panelColum3.add(labelSexo);
		panelColum3.add(labelAltura);
		panelColum3.add(labelAleg);
		panelColum3.add(paneAlergias);


		panel1.add(panelColum3);

		this.add(panel1);
		
		this.setVisible(true);
		this.setSize(800, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Healthy & Fit");
	}
}
