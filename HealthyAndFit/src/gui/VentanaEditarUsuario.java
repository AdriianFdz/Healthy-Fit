package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import domain.TipoPermiso;
import domain.Usuario;

public class VentanaEditarUsuario extends JFrame {
	
	JPanel pIzq;
		Integer spacing = 10;
		JLabel labelCorreo;
		JTextField fieldCorreo;
		JLabel labelTipo;
		JComboBox<TipoPermiso> comboTipo;
	
	JPanel pDer;
		JLabel labelNombreU;
		JTextField fieldNombreU;
		JLabel labelNombre;
		JTextField fieldNombre;
		JLabel labelApe1;
		JTextField fieldApe1;
		JLabel labelApe2;
		JTextField fieldApe2;
	
	JPanel pBoto;
		JButton botonCancelar;
		JButton botonConfirmar;
	
	
	public VentanaEditarUsuario(Usuario p) {
		JPanel pDatos = new JPanel(new GridLayout(1,2));
		
		
		//Panel Izquierdo
		JPanel pIzq = new JPanel(new GridLayout(3,1));
		pIzq.setLayout(new BoxLayout(pIzq, BoxLayout.Y_AXIS));
		pIzq.setBorder(BorderFactory.createEmptyBorder(2, spacing , 2, spacing)); // Izquierda
		
		// Componentes para el panel izquierdo
		Image foto = p.getFoto().getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		JLabel fotoUsuario = new JLabel(new ImageIcon(foto));
		fotoUsuario.setPreferredSize(new Dimension(150, 150));
		
		labelCorreo = new JLabel("CORREO");
		labelTipo = new JLabel("TIPO");
		
		fieldCorreo = new JTextField(20);
		comboTipo = new JComboBox<>(TipoPermiso.values());
		
		//Inicializamos con datos
		fieldCorreo.setText(p.getCorreoElectronico());
		comboTipo.setSelectedItem(p.getPermiso());
		
		pIzq.add(fotoUsuario);
		pIzq.add(labelCorreo);
		pIzq.add(fieldCorreo);
		pIzq.add(labelTipo);
		pIzq.add(comboTipo);

		
		//Panel Derecho
		JPanel pDer = new JPanel();
		pDer.setLayout(new BoxLayout(pDer, BoxLayout.Y_AXIS));
		pDer.setBorder(BorderFactory.createEmptyBorder(2,spacing,2,spacing)); // Derecha
		
		 // Componentes para el panel derecho
		labelNombreU= new JLabel("NOMBRE DE USUARIO");
		labelNombre = new JLabel("NOMBRE");
		labelApe1 = new JLabel("APELLIDO1");
		labelApe2= new JLabel("APELLIDO2");
		
		fieldNombreU = new JTextField(20);
		fieldNombre = new JTextField(20);
		fieldApe1 = new JTextField(20);
		fieldApe2 = new JTextField(20);
		
		//Inicializar campos con datos
		fieldNombreU.setText(p.getNombreUsuario());
		fieldNombre.setText(p.getNombre());
		fieldApe1.setText(p.getApellido1());
		fieldApe2.setText(p.getApellido2());
		
		
		pDer.add(labelNombreU);
		pDer.add(fieldNombreU);
		pDer.add(labelNombre);
		pDer.add(fieldNombre);
		pDer.add(labelApe1);
		pDer.add(fieldApe1);
		pDer.add(labelApe2);
		pDer.add(fieldApe2);
		
		
		//AÑADIRLO AL PANEL
		pDatos.add(pIzq);
		pDatos.add(pDer);
		
		//PANEL BOTONES
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		// Botones
        JButton botonCancelar = new JButton("Cancelar");
        JButton botonConfirmar = new JButton("Confirmar");	
		
        panelBotones.add(botonConfirmar);
        panelBotones.add(botonCancelar);
        
        //Listeners
        botonCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new VentanaPanel(p));
				dispose();
				
				
			}
		});
        botonConfirmar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		}); 
        
		
		this.add(pDatos);
		this.add(panelBotones, BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Editar Usuario");
	}

}
