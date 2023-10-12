package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class VentanaLogeoRegistro extends JFrame{

	JTabbedPane paneles;
	
	JPanel logeo;
		JPanel logeoIzquierda;
			JLabel preguntaYaTienesCuenta;
			JLabel nombreLogeo;
			JTextField meterNombreLogeo;
			JLabel contraseñaLogeo;
			JPasswordField meterContraseñaLogeo;
			JButton iniciarSesion;
		JPanel logeoDerecha;
	
	
	JPanel registro;
	JLabel preguntaTodaviaSinCuenta;
	JLabel nombreRegistro;
	JTextField meterNombreRegistro;
	JLabel correoRegistro;
	JTextField meterCorreoRegistro;
	JLabel contraseñaRegistro;
	JTextField meterContraseñaRegistro;
	JLabel contraseñaRepetidaRegistro;
	JTextField meterContraseñaRepetidaRegistro;
	
	
	
	
	public VentanaLogeoRegistro() {
		
	paneles = new JTabbedPane(); //El panel para seleccionar la ventana de logeo o registro
		
	logeo = new JPanel(new GridLayout(1, 3));
		
		logeoIzquierda = new JPanel();
		logeoIzquierda.setLayout(new BoxLayout(logeoIzquierda,BoxLayout.Y_AXIS));
		
			preguntaYaTienesCuenta = new JLabel("¿Ya tienes cuenta?");		
			logeoIzquierda.add(preguntaYaTienesCuenta);
		
			nombreLogeo = new JLabel("NOMBRE DE USUARIO ");
			meterNombreLogeo = new JTextField(20);
			logeoIzquierda.add(nombreLogeo);
			logeoIzquierda.add(meterNombreLogeo);
			
			contraseñaLogeo = new JLabel("CONTRASEÑA");
			meterContraseñaLogeo = new JPasswordField(20);
			logeoIzquierda.add(contraseñaLogeo);
			logeoIzquierda.add(meterContraseñaLogeo);
			
			iniciarSesion = new JButton("INICIAR SESION");
			logeoIzquierda.add(iniciarSesion);
			
		logeoDerecha = new JPanel();	
			
		logeo.add(logeoIzquierda);
		logeo.add(logeoDerecha);
		
		
	registro = new JPanel();
		
		
		
		
		
	
	
	
	
	iniciarSesion.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(usuarioContraseñaCorrectos()) {
			new VentanaResumen(null);
			}else {
				JOptionPane.showMessageDialog(iniciarSesion,"La contraseña o el usuario no son validos","Error",JOptionPane.ERROR_MESSAGE);
			}
		}
	});
	
	
	
	
		paneles.add(logeo,"Logeo");
		paneles.add(registro,"Registro");
		this.add(paneles);
		
		this.setVisible(true);
		this.setSize(960,540);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Healthy & Fit");
	}
	
	   public static boolean usuarioContraseñaCorrectos() {
		   //Comprobar en base de datos
	        return true;
	    }
	
}
