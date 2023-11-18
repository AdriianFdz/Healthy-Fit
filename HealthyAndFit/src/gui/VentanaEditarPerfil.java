package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.time.LocalDate;
import java.time.Period;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.Usuario;

public class VentanaEditarPerfil extends JFrame {
	
	JPanel panelColumna1;
		JTextField fieldNombre;
		JTextField fieldApellidos;
		JTextField fieldFechaNac;
		JLabel labelTipoU;
		
	JPanel panelDatos;
		JTextField fieldEdad;
		JTextField fieldSexo;
		JTextField fieldAltura;
		JLabel labelEnfer;
		JTextField fieldCorreo;
		JTextField fieldPeso;
		JLabel labelIMC;
		JLabel labelAleg;
	
	public VentanaEditarPerfil(Usuario u) {
		
		JPanel panelAbajo = new JPanel(new BorderLayout());
		add(panelAbajo, BorderLayout.SOUTH);
		
		//Panel Columna 1
		panelColumna1 = new JPanel();
		panelColumna1.setLayout(new BoxLayout(panelColumna1, BoxLayout.Y_AXIS));
		
		//Para imagen
		Image foto = u.getFoto().getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		JLabel fotoUsuario = new JLabel(new ImageIcon(foto));
		fotoUsuario.setPreferredSize(new Dimension(320, 320));
		panelColumna1.add(fotoUsuario);
		
		//Elementos del panel
		fieldNombre = new JTextField(String.format("Nombre: %s", u.getNombre().toUpperCase()));
		fieldApellidos = new JTextField(String.format("Apellidos: %s %s", u.getApellido1().toUpperCase(), u.getApellido2().toUpperCase()));
		fieldFechaNac = new JTextField(String.format("Nacimiento: %s",u.getfechaNacimiento().toString()));
		labelTipoU = new JLabel(String.format("Rango: %s", u.getPermiso().name()));
		
		//Añadir elementos al panel 
		panelColumna1.add(fieldNombre);
		panelColumna1.add(fieldApellidos);
		panelColumna1.add(fieldFechaNac);
		panelColumna1.add(labelTipoU);
		
		// Botones
		JButton cancelaBot = new JButton("CANCELAR");
		JButton aceptarBot = new JButton("ACEPTAR");
		JButton accesoPanelBot = new JButton("ACCESO PANEL");
		
		JButton volverBot = new JButton("VOLVER");
		JButton botonCerSesion = new JButton("CERRAR SESION");
		
		JPanel panelBotonesCanAcep = new JPanel();
			panelBotonesCanAcep.setLayout(new BoxLayout(panelBotonesCanAcep, BoxLayout.X_AXIS));
			panelBotonesCanAcep.add(cancelaBot);
			panelBotonesCanAcep.add(aceptarBot);
		
		panelColumna1.add(panelBotonesCanAcep);
		panelColumna1.add(accesoPanelBot);
	
		add(panelColumna1, BorderLayout.WEST);
		
		panelAbajo.add(volverBot, BorderLayout.WEST);
		panelAbajo.add(botonCerSesion, BorderLayout.EAST);

		//Panel del resto de datos
		JPanel panelDatos = new JPanel(new GridLayout(6,2,0,0));

		int edad = Period.between(u.getfechaNacimiento(), LocalDate.now()).getYears();
		fieldEdad = new JTextField(String.format("Edad: %d", edad));
		fieldSexo = new JTextField(String.format("Sexo: %s", u.getSexo().name()));
		fieldAltura = new JTextField(String.format("Altura: %.2f", u.getAltura()));
		labelEnfer = new JLabel("Enfermedades");
		
		fieldCorreo = new JTextField(String.format("Correo: %s", u.getCorreoElectronico()));
		fieldPeso = new JTextField(String.format("Peso: %d", u.getPeso()));
		labelIMC = new JLabel(String.format("IMC: %.2f", u.getImc()));
		labelAleg = new JLabel("ALERGIAS");

		//Agregar al panel
		panelDatos.add(fieldEdad);
		panelDatos.add(fieldCorreo);
		panelDatos.add(fieldSexo);
		panelDatos.add(fieldPeso);
		panelDatos.add(fieldAltura);
		panelDatos.add(labelIMC);
		
		JPanel panelDerecha = new JPanel(new BorderLayout());
		panelDerecha.add(panelDatos, BorderLayout.NORTH);
		
		add(panelDerecha, BorderLayout.CENTER);
		
		this.pack();
		this.setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Perfil");
	}
}