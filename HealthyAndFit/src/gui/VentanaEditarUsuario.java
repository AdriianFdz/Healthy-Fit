package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.TipoPermiso;
import domain.Usuario;

public class VentanaEditarUsuario extends JFrame {
	
	JLabel labelCorreo;
	JTextField fieldCorreo;
	JLabel labelTipo;
	JComboBox<TipoPermiso> comboTipo;
	
	JLabel labelNombreU;
	JTextField fieldNombreU;
	JLabel labelNombre;
	JTextField fieldNombre;
	JLabel labelApe1;
	JTextField fieldApe1;
	JLabel labelApe2;
	JTextField fieldApe2;
	
	
	public VentanaEditarUsuario(Usuario p) {
		//JPanel pDatos = new JPanel(new GridLayout(1,2));
		JPanel pDatos = new JPanel(new BorderLayout());
		
		//Panel Izquierdo
		JPanel pIzq = new JPanel(new GridLayout(3,1));
		
		Image foto = p.getFoto().getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		JLabel fotoUsuario = new JLabel(new ImageIcon(foto));
		fotoUsuario.setPreferredSize(new Dimension(320, 320));
		
		labelCorreo = new JLabel("CORREO");
		labelTipo = new JLabel("TIPO");
		
	
		
		pIzq.add(fotoUsuario);
		pIzq.add(labelCorreo);
		pIzq.add(fieldCorreo);
		pIzq.add(labelTipo);
		pIzq.add(comboTipo);

		
		//Panel Derecho
		JPanel pDer = new JPanel(new GridLayout(8,1));
		
		labelNombreU= new JLabel("NOMBRE DE USUARIO");
		labelNombre = new JLabel("NOMBRE");
		labelApe1 = new JLabel("APELLIDO1");
		labelApe2= new JLabel("APELLIDO2");
		
		pDer.add(labelNombreU);
		pDer.add(fieldNombreU);
		pDer.add(labelNombre);
		pDer.add(fieldNombre);
		pDer.add(labelApe1);
		pDer.add(fieldApe1);
		pDer.add(labelApe2);
		pDer.add(fieldApe2);
		
		
		//AÑADIRLO A LA VENTANA
		this.add(pIzq, BorderLayout.WEST);
		this.add(pDer, BorderLayout.EAST);
		
		pack();
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Editar Usuario");
	}

}
