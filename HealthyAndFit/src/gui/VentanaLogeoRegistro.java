package gui;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import db.BaseDeDatos;
import domain.Usuario;

public class VentanaLogeoRegistro extends JFrame{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(VentanaLogeoRegistro.class.getName());
	
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
	JButton registrarse;
	
	
	
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
		
		
		registro = new JPanel(new BorderLayout());
		preguntaTodaviaSinCuenta = new JLabel("¿Todavía no tienes cuenta?");
		registro.add(preguntaTodaviaSinCuenta, BorderLayout.NORTH);

		JPanel meterDatos = new JPanel(new GridLayout(4, 2));
		nombreRegistro = new JLabel("NOMBRE DE USUARIO");
		meterNombreRegistro = new JTextField();

		correoRegistro = new JLabel("CORREO ELECTRONICO");
		meterCorreoRegistro = new JTextField();

		contraseñaRegistro = new JLabel("CONTRASEÑA");
		meterContraseñaRegistro = new JPasswordField();

		contraseñaRepetidaRegistro = new JLabel("REPETIR CONTRASEÑA");
		meterContraseñaRepetidaRegistro = new JPasswordField();
		
		meterDatos.add(nombreRegistro);
		meterDatos.add(correoRegistro);
		meterDatos.add(meterNombreRegistro);
		meterDatos.add(meterCorreoRegistro);
		meterDatos.add(contraseñaRegistro);
		meterDatos.add(contraseñaRepetidaRegistro);
		meterDatos.add(meterContraseñaRegistro);
		meterDatos.add(meterContraseñaRepetidaRegistro);

		registro.add(meterDatos, BorderLayout.CENTER);

		registrarse = new JButton("REGISTRARSE");
		registro.add(registrarse, BorderLayout.SOUTH);
		
		
	
	
	
	
	iniciarSesion.addActionListener(new ActionListener() {
		
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			Usuario usuarioSinComprobar = new Usuario();
			usuarioSinComprobar.setnombreUsuario(meterNombreLogeo.getText());
			//Corregir funcion deprecated. Cambiar en Usuario la contraseña a []char.
			//Eso evita no guardar en memoria la contraseña
			usuarioSinComprobar.setContraseña(meterContraseñaLogeo.getText());
			
			Usuario usuarioComprobado = usuarioContraseñaCorrectos(usuarioSinComprobar);
			if(usuarioComprobado != null) {
				SwingUtilities.invokeLater(() -> new VentanaResumen(usuarioComprobado));	
			
			}else {
				JOptionPane.showMessageDialog(iniciarSesion,"La contraseña o el usuario no son validos","Error",JOptionPane.ERROR_MESSAGE);
			}
		}
	});
	
	registrarse.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!meterNombreRegistro.getText().isEmpty() && !meterCorreoRegistro.getText().isEmpty() && !meterContraseñaRepetidaRegistro.getText().isEmpty() && !meterContraseñaRegistro.getText().isEmpty()) {
				if(meterContraseñaRegistro.getText().equals(meterContraseñaRepetidaRegistro.getText())){
					//crear usuario en base de datos
					logger.log(Level.INFO, BaseDeDatos.listaUsuarios.toString());
					Usuario usuarioRegistrado = new Usuario();
					usuarioRegistrado.setnombreUsuario(meterNombreRegistro.getText());
					usuarioRegistrado.setcorreoElectronico(meterCorreoRegistro.getText());
					usuarioRegistrado.setContraseña(meterContraseñaRegistro.getText());
					BaseDeDatos.listaUsuarios.add(usuarioRegistrado);
					logger.log(Level.INFO, BaseDeDatos.listaUsuarios.toString());
					SwingUtilities.invokeLater(() -> new VentanaResumen(usuarioRegistrado));
					dispose();
				}else {
					 JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "Las contraseñas deben coincidir", JOptionPane.ERROR_MESSAGE);
				}
			}else {
				 JOptionPane.showMessageDialog(null, "Los datos introducidos no son correctos", "Error de datos", JOptionPane.ERROR_MESSAGE);
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
	
	  public static Usuario usuarioContraseñaCorrectos(Usuario usuarioSinComprobar) {
		   
		  for (Usuario usuario : BaseDeDatos.listaUsuarios) {
			  System.out.println(usuario.getnombreUsuario());
			  System.out.println(usuario.getContraseña());
			if (usuarioSinComprobar.getnombreUsuario().equals(usuario.getnombreUsuario()) && usuarioSinComprobar.getContraseña().equals(usuario.getContraseña())) {
				return usuario;
			}
		  }

	      return null;
	   }
	
}
