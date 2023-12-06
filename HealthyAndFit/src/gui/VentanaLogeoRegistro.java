package gui;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import db.BaseDeDatos;
import domain.Usuario;
import io.RegistroLogger;



public class VentanaLogeoRegistro extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public JTabbedPane paneles;
	
	public JPanel logeo;
		public JPanel logeoIzquierda;
			public JLabel preguntaYaTienesCuenta;
			public JLabel nombreLogeo;
			public JTextField meterNombreLogeo;
			public JLabel contraseñaLogeo;
			public JPasswordField meterContraseñaLogeo;
			public JButton iniciarSesion;
			public JButton inicioRapido;
		public JPanel logeoDerecha;
	
	//INFORMACIÓN PRIMARIA PARA REGISTRARSE
		
	public JPanel registro;
	public JLabel preguntaTodaviaSinCuenta;
	public JLabel nombreRegistro;
	public JTextField meterNombreRegistro;
	public JLabel correoRegistro;
	public JTextField meterCorreoRegistro;
	public JLabel contraseñaRegistro;
	public JTextField meterContraseñaRegistro;
	public JLabel contraseñaRepetidaRegistro;
	public JTextField meterContraseñaRepetidaRegistro;
	
	
	//METER TODA LA INFORMACIÓN DEL USUARIO
	public JScrollPane registro2;
	public JPanel registro2Grid;
	
	public JLabel apellido1;
	public JTextField meterApellido1;
	
	public JLabel apellido2;
	public JTextField meterApellido2;
	
	public JLabel altura;
	public JTextField meterAltura;
	
	public JLabel peso;
	public JTextField meterPeso;
	
	public JLabel genero;
	public JTextField meterGenero;
	
	public JLabel fechaNac;
	public JCalendar meterFechaNac;
	
	public JPanel registro3;
	
	public JLabel enfermedades;
	public JTable meterEnfermedades;
	
	public JLabel alergias;
	public JTable meterAlergias;
	
	public JButton siguiente;
	public JButton registrarse;
	
	public JPanel registro4;
	
	
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
			
			inicioRapido = new JButton("INICIO RAPIDO");
			logeoIzquierda.add(inicioRapido);
			
		logeoDerecha = new JPanel();	
			
		logeo.add(logeoIzquierda);
		logeo.add(logeoDerecha);
		
		//REGISTRO  INFORMACIÓN BASICA
		
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
		
		//REGISTRO 2  INFORMACIÓN ADICIONAL
		
		registro2Grid = new JPanel(new GridLayout(6, 2,10,10));
		
		
		apellido1 = new JLabel("Primer Apellido");
		meterApellido1 = new JTextField();
	
		apellido2 = new JLabel("Segundo Apellido");
		meterApellido2 = new JTextField();
		
		altura = new JLabel("Peso");
		meterAltura = new JTextField();
	
		peso = new JLabel("Altura");
		meterPeso = new JTextField();
		
		genero = new JLabel("Genero");
		meterGenero = new JTextField();
		
		fechaNac = new JLabel("Fecha Nacimiento");
		meterFechaNac = new JCalendar();
		
		registro2Grid.add(apellido1);
		registro2Grid.add(meterApellido1);
		registro2Grid.add(apellido2);
		registro2Grid.add(meterApellido2);
		registro2Grid.add(peso);
		registro2Grid.add(meterPeso);
		registro2Grid.add(altura);
		registro2Grid.add(meterAltura);
		registro2Grid.add(genero);
		registro2Grid.add(meterGenero);
		registro2Grid.add(fechaNac);
		registro2Grid.add(meterFechaNac);
		registro2= new JScrollPane(registro2Grid);
		
		//REGISTRO 3  ALERGIAS Y ENFERMEDADES
		
		registro3 = new JPanel();
		
		//REGISTRO 4  FOTO DE PERFIL
	
		registro4 = new JPanel();
	iniciarSesion.addActionListener(new ActionListener() {
		
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			Usuario usuarioSinComprobar = new Usuario();
			usuarioSinComprobar.setNombreUsuario(meterNombreLogeo.getText());
			//Corregir funcion deprecated. Cambiar en Usuario la contraseña a []char.
			//Eso evita no guardar en memoria la contraseña
			usuarioSinComprobar.setContrasena(meterContraseñaLogeo.getText());
			
			Usuario usuarioComprobado = usuarioContraseñaCorrectos(usuarioSinComprobar);
			if(usuarioComprobado != null) {
				SwingUtilities.invokeLater(() -> new VentanaResumen(usuarioComprobado));
				dispose();
			
			}else {
				JOptionPane.showMessageDialog(iniciarSesion,"La contraseña o el usuario no son validos","Error",JOptionPane.ERROR_MESSAGE);
				RegistroLogger.anadirLogeo(Level.WARNING, "Contraseña incorrecta");
			}
		}
	});
	
	registrarse.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!meterNombreRegistro.getText().isEmpty() && !meterCorreoRegistro.getText().isEmpty() && !meterContraseñaRepetidaRegistro.getText().isEmpty() && !meterContraseñaRegistro.getText().isEmpty()) {
				if(meterContraseñaRegistro.getText().equals(meterContraseñaRepetidaRegistro.getText())){
					//crear usuario en base de datos
					RegistroLogger.anadirLogeo(Level.WARNING, "Contraseña incorrecta");
					Usuario usuarioRegistrado = new Usuario();
					usuarioRegistrado.setNombreUsuario(meterNombreRegistro.getText());
					usuarioRegistrado.setCorreoElectronico(meterCorreoRegistro.getText());
					usuarioRegistrado.setContrasena(meterContraseñaRegistro.getText());
					BaseDeDatos.getListaUsuarios().add(usuarioRegistrado);
					SwingUtilities.invokeLater(() -> new VentanaResumen(usuarioRegistrado));
					dispose();
				} else {
					 JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "Las contraseñas deben coincidir", JOptionPane.ERROR_MESSAGE);
					 RegistroLogger.anadirLogeo(Level.WARNING, "Contraseñas no coinciden");

				}
			} else {
				 JOptionPane.showMessageDialog(null, "Los datos introducidos no son correctos", "Error de datos", JOptionPane.ERROR_MESSAGE);
				 RegistroLogger.anadirLogeo(Level.WARNING, "Datos incorrectos");
			}
		}
	});
	//INICIO RAPIDO PARA NO METER TODO EL RATO USUARIO Y CONTRASEÑA, BORRAR AL ACABAR PROYECTO
	inicioRapido.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Usuario usuarioSinComprobar = new Usuario();
			usuarioSinComprobar.setNombreUsuario("juan_perez");
			
			usuarioSinComprobar.setContrasena("juan");
			
			Usuario usuarioComprobado = usuarioContraseñaCorrectos(usuarioSinComprobar);
			if(usuarioComprobado != null) {
				SwingUtilities.invokeLater(() -> new VentanaResumen(usuarioComprobado));
				dispose();
		}
	}});
	
		paneles.add(logeo,"Logeo");
		paneles.add(registro,"Registro");
		paneles.add(registro2,"Registro 2");
		paneles.add(registro3,"Registro 3");
		paneles.add(registro4,"Registro 4");
		this.add(paneles);
		
		this.setVisible(true);
		this.setSize(500, 400);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Healthy & Fit");
		this.setLocationRelativeTo(null);
	}
	
	  public static Usuario usuarioContraseñaCorrectos(Usuario usuarioSinComprobar) {
		   
		  for (Usuario usuario : BaseDeDatos.getListaUsuarios()) {
			if (usuarioSinComprobar.getNombreUsuario().equals(usuario.getNombreUsuario()) && usuarioSinComprobar.getContrasena().equals(usuario.getContrasena())) {
				  RegistroLogger.anadirLogeo(Level.WARNING, "Inicio de sesion correcto");
				return usuario;
			}
		  }
		  RegistroLogger.anadirLogeo(Level.WARNING, "Usuario inexistente");
	      return null;
	   }
	
}
