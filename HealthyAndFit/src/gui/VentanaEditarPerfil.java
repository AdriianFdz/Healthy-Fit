package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.TipoAlergias;
import domain.TipoEnfermedades;
import domain.Usuario;

public class VentanaEditarPerfil extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JPanel panelColumna1;
		public JPanel panelColumna;
			public JTextField fieldNombre;
			public JLabel labelNombre; 
			
			public JTextField fieldApellido1;
			public JLabel labelApellido1;
			
			public JTextField fieldApellido2;
			public JLabel labelApellido2;
			
			public JTextField fieldFechaNac;
			public JLabel labelFechaNac;
		
		
	public JPanel panelColumna2;
		
		public JTextField fieldSexo;
		public JLabel labelSexo;
		
		public JTextField fieldAltura;
		public JLabel labelAltura;
		
		public JTextField fieldCorreo;
		public JLabel labelCorreo;
		
		public JTextField fieldPeso;
		public JLabel labelPeso;
		
		public JLabel labelAleg;
		public JComboBox<TipoAlergias> comboAleg;
		
		public JLabel labelEnfer;
		public JComboBox<TipoEnfermedades> comboEnfer;
		
	public VentanaEditarPerfil(Usuario u) {
		
		JPanel panelAbajo = new JPanel(new BorderLayout());
		add(panelAbajo, BorderLayout.SOUTH);
		
		//Panel Columna 1
		panelColumna1 = new JPanel();
		panelColumna1.setLayout(new BoxLayout(panelColumna1, BoxLayout.Y_AXIS));
		
		//Para imagen
		Image foto = u.getFoto().getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		JLabel fotoUsuario = new JLabel(new ImageIcon(foto));
		fotoUsuario.setPreferredSize(new Dimension(200, 200));
		fotoUsuario.setToolTipText("Haz click para elegir otra foto");
		panelColumna1.add(fotoUsuario);
		
		//Elementos del panel
		panelColumna = new JPanel(new GridLayout(5, 2));
		
		fieldNombre = new JTextField(u.getNombre());
		labelNombre = new JLabel("Nombre:");
		
		fieldApellido1 = new JTextField(u.getApellido1());
		labelApellido1 = new JLabel("Primer Apellido:");
		
		fieldApellido2 = new JTextField(u.getApellido2());
		labelApellido2 = new JLabel("Segundo Apellido:");
		
		fieldFechaNac = new JTextField(u.getFechaNacimiento().toString());
		labelFechaNac = new JLabel("Fecha nacimiento:");
		
		
		//Añadir elementos al panel 
		panelColumna.add(labelNombre);
		panelColumna.add(fieldNombre);
		
		panelColumna.add(labelApellido1);
		panelColumna.add(fieldApellido1);
		
		panelColumna.add(labelApellido2);
		panelColumna.add(fieldApellido2);
		
		panelColumna.add(labelFechaNac);
		panelColumna.add(fieldFechaNac);
		
		panelColumna1.add(panelColumna);
		
		// Botones
		JButton cancelaBot = new JButton("CANCELAR");
		JButton aceptarBot = new JButton("ACEPTAR");
		
		JPanel panelBotonesCanAcep = new JPanel();
			panelBotonesCanAcep.setLayout(new BoxLayout(panelBotonesCanAcep, BoxLayout.X_AXIS));
			panelBotonesCanAcep.add(cancelaBot);
			panelBotonesCanAcep.add(aceptarBot);
		
		panelColumna1.add(panelBotonesCanAcep);
	
		add(panelColumna1, BorderLayout.WEST);
		

		//Panel del resto de datos
		panelColumna2 = new JPanel();
		panelColumna2.setLayout(new BoxLayout(panelColumna2, BoxLayout.Y_AXIS));
	
		fieldSexo = new JTextField(u.getSexo().toString());
		labelSexo = new JLabel("Sexo");
		
		fieldAltura = new JTextField(String.valueOf(u.getAltura()));
		labelAltura = new JLabel("Altura");
		
		fieldCorreo = new JTextField(u.getCorreoElectronico());
		labelCorreo = new JLabel("Correo");
		
		fieldPeso = new JTextField(String.valueOf(u.getPeso()));
		labelPeso = new JLabel("Peso");
		
		labelAleg = new JLabel("Alergias");
		comboAleg = new JComboBox<>(TipoAlergias.values());
		
		labelEnfer = new JLabel("Enfermedades");
		comboEnfer = new JComboBox<>(TipoEnfermedades.values());

		//Agregar al panel
		panelColumna2.add(labelCorreo);
		panelColumna2.add(fieldCorreo);
		
		panelColumna2.add(labelSexo);
		panelColumna2.add(fieldSexo);
		
		panelColumna2.add(labelPeso);
		panelColumna2.add(fieldPeso);
		
		panelColumna2.add(labelAltura);
		panelColumna2.add(fieldAltura);
		
		panelColumna2.add(labelEnfer);
		panelColumna2.add(comboEnfer);
		
		panelColumna2.add(labelAleg);
		panelColumna2.add(comboAleg);
		
		JPanel panelDerecha = new JPanel(new BorderLayout());
		
		panelDerecha.add(panelColumna2, BorderLayout.NORTH);
		
		add(panelDerecha, BorderLayout.CENTER);
		
		aceptarBot.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		cancelaBot.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int respuesta = JOptionPane.showConfirmDialog(null, "Seguro que quieres salir sin guardar?",
						"", JOptionPane.YES_NO_OPTION);
				if(respuesta == JOptionPane.YES_OPTION) {
				dispose();
				}
			}
		});
		
		fotoUsuario.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				 //JFileChooser elegirFoto = new JFileChooser();
				//int resultado = elegirFoto.showOpenDialog
				//if(resultado == JFileChooser.APPROVE_OPTION){
				
				//}
			        
		}});
		
		this.pack();
		this.setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Perfil");
	}
}
