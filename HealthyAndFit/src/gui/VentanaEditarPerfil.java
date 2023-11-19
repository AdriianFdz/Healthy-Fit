package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.Period;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import domain.Usuario;

public class VentanaEditarPerfil extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JPanel panelColumna1;
		public JTextField fieldNombre;
		public JTextField fieldApellidos;
		public JTextField fieldFechaNac;
		public JLabel labelTipoU;
		
	public JPanel panelDatos;
		public JTextField fieldEdad;
		public JTextField fieldSexo;
		public JTextField fieldAltura;
		public JLabel labelEnfer;
		public JTextField fieldCorreo;
		public JTextField fieldPeso;
		public JLabel labelIMC;
		public JLabel labelAleg;
	
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
		fotoUsuario.setToolTipText("Haz click para elegir otra foto");
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
		
		JPanel panelBotonesCanAcep = new JPanel();
			panelBotonesCanAcep.setLayout(new BoxLayout(panelBotonesCanAcep, BoxLayout.X_AXIS));
			panelBotonesCanAcep.add(cancelaBot);
			panelBotonesCanAcep.add(aceptarBot);
		
		panelColumna1.add(panelBotonesCanAcep);
	
		add(panelColumna1, BorderLayout.WEST);
		

		//Panel del resto de datos
		panelDatos = new JPanel();
		panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));

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
		panelDatos.add(labelEnfer);
		panelDatos.add(labelAleg);
		
		JPanel panelDerecha = new JPanel(new BorderLayout());
		panelDerecha.add(panelDatos, BorderLayout.NORTH);
		
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
				SwingUtilities.invokeLater(() -> new VentanaPerfil(u));
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
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Perfil");
	}
}
