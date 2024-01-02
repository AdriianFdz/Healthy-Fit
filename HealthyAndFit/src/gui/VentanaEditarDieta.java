package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import db.DBManager;
import domain.Dieta;
import domain.TipoAlergias;
import domain.TipoDificultad;
import domain.Usuario;


public class VentanaEditarDieta extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JPanel pIzq;
		public JLabel labelNombre;
		public JTextField fieldNombre;
		public JLabel labelTiempo;
		public SpinnerNumberModel modeloSpin;
		public JSpinner spinnerTiempo;
		public JLabel labelDiff;
		public JComboBox<TipoDificultad> comboDif;
		public JLabel labelKcal;
		public JSpinner spinnerKcal;
		public List<String> listaIngredientes;
		public List<String> listaPasos;
		private List<TipoAlergias> listaAlergias;

		
	public JPanel pDer;
		private JLabel labelIngredientes;
		private JList<String> JlistaPasos;
		private JLabel labelPasos;
		private JList<String> JlistaIngredientes;
		public JScrollPane panelDcha;
		private JLabel labelAlerg;
		private JComboBox<TipoAlergias> comboAleg;
	
	public VentanaEditarDieta(Dieta d, Usuario p) {
	
		JPanel datos = new JPanel(new GridLayout(1,2));
		JPanel panelBotones = new JPanel();
		
		//Panel izqueirda y componenetes del panel
		pIzq = new JPanel(new GridLayout(12,1));
		pIzq.setLayout(new BoxLayout(pIzq, BoxLayout.Y_AXIS));
	
		
		labelNombre = new JLabel("NOMBRE");
		fieldNombre = new JTextField(20);
		labelTiempo = new JLabel("TIEMPO");
		labelDiff = new JLabel("DIFICULTAD");
		comboDif = new JComboBox<>(TipoDificultad.values());
		labelKcal = new JLabel("KCAL");
		
		labelIngredientes = new JLabel("INGREDIENTES");
		
		listaIngredientes = new ArrayList<>(d.getIngredientes());
		listaPasos = new ArrayList<>(d.getPasos());
		listaAlergias = new ArrayList<TipoAlergias>(d.getAlergias());
		
		JlistaIngredientes = new JList<String>();
		DefaultListModel<String> modeloIngredientes = new DefaultListModel<>();
		JlistaIngredientes.setModel(modeloIngredientes);
		if (!d.getIngredientes().isEmpty()) {
			for (String ingrediente : d.getIngredientes()) {
				modeloIngredientes.addElement(ingrediente);
			}
		}
		
		
       JButton botonAñadirIngrediente = new JButton("AÑADIR ING");
       JButton botonElimiarIngrediente = new JButton("ELIMINAR ING");
       
		//Inicializamos los otros elementos con los datos del usuario
		fieldNombre.setText(d.getNombre());
		comboDif.setSelectedItem(d.getDificultad());
		SpinnerNumberModel modeloSpin = new SpinnerNumberModel(d.getTiempo(),0,999,1);
		spinnerTiempo = new JSpinner(modeloSpin);
		spinnerKcal = new JSpinner(new SpinnerNumberModel(d.getTiempo(), 0, 9999, 1));
	
		JScrollPane paneIng = new JScrollPane(JlistaIngredientes);
		JPanel btnAñaEli = new JPanel();
		btnAñaEli.setLayout(new BoxLayout(btnAñaEli, BoxLayout.X_AXIS));
		
		//Agregamos componentes al paneIzq
		pIzq.add(labelNombre);
		pIzq.add(fieldNombre);
		pIzq.add(labelTiempo);
		pIzq.add(spinnerTiempo);
		pIzq.add(labelDiff);
		pIzq.add(comboDif);
		pIzq.add(labelKcal);
		pIzq.add(spinnerKcal);
		pIzq.add(labelIngredientes);
		pIzq.add(paneIng);
		
		btnAñaEli.add(botonAñadirIngrediente);
		btnAñaEli.add(botonElimiarIngrediente);
		pIzq.add(btnAñaEli);
		
		//Panel derecha
		pDer = new JPanel();
		pDer.setLayout(new BoxLayout(pDer, BoxLayout.Y_AXIS));

		//Componentes para el panel de la derecha
		JlistaPasos = new JList<String>();
		DefaultListModel<String> modeloPasos = new DefaultListModel<>();
		JlistaPasos.setModel(modeloPasos);
		if (!d.getPasos().isEmpty()) {
			for (String s : d.getPasos()) {
				modeloPasos.addElement(s);
			}
		}
		
		labelPasos = new JLabel("PASOS");
		
		labelAlerg = new JLabel("ALERGIAS");
		labelAlerg.setHorizontalAlignment(JLabel.CENTER);
		
		comboAleg = new JComboBox<>(TipoAlergias.values());
		
		JList<TipoAlergias> listaAlergia = new JList<>();
		DefaultListModel<TipoAlergias> modeloAlergia = new DefaultListModel<>();
		listaAlergia.setModel(modeloAlergia);
	
		
		JButton anadirAlergia = new JButton("AÑADIR ALERGIA");
		JButton eliminarAlergia = new JButton("ELIMINAR ALERGIA");
		JPanel panelAlergia = new JPanel(new BorderLayout());
		JPanel panelAlergiaBotones = new JPanel();
		
		panelAlergia.add(labelAlerg, BorderLayout.NORTH);
		panelAlergia.add(listaAlergia,BorderLayout.CENTER);
		panelAlergiaBotones.add(anadirAlergia);
		panelAlergiaBotones.add(eliminarAlergia);
		panelAlergia.add(panelAlergiaBotones, BorderLayout.SOUTH);
		
		if (!d.getAlergias().isEmpty()) {
			for (TipoAlergias alergia : d.getAlergias()) {
				modeloAlergia.addElement(alergia);
			}
		}else {
			modeloAlergia.addElement(TipoAlergias.NINGUNA);
		}
		
		anadirAlergia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(
		                null,
		                comboAleg,
		                "Selecciona una alergia",
		                JOptionPane.OK_CANCEL_OPTION,
		                JOptionPane.QUESTION_MESSAGE);
				
				if (result == JOptionPane.OK_OPTION) {
					if (!modeloAlergia.contains(comboAleg.getSelectedItem())) {
						modeloAlergia.addElement((TipoAlergias) comboAleg.getSelectedItem());
						listaAlergias.add((TipoAlergias) comboAleg.getSelectedItem());
						if (modeloAlergia.contains(TipoAlergias.NINGUNA)) {
							modeloAlergia.removeElement(TipoAlergias.NINGUNA);
							listaAlergias.remove(TipoAlergias.NINGUNA);
						}
					} else {
						JOptionPane.showConfirmDialog(null, "Alergia ya añadida", "Error", JOptionPane.PLAIN_MESSAGE);	
					}
					
				}
				
			}
		});
		
		eliminarAlergia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TipoAlergias alergia = listaAlergia.getSelectedValue();
				modeloAlergia.removeElement(alergia);
				listaAlergias.remove(alergia);
				if (modeloAlergia.isEmpty()) {
					modeloAlergia.addElement(TipoAlergias.NINGUNA);
					listaAlergias.add(TipoAlergias.NINGUNA);
				}
			}
		});
		
		
		
	    
        JScrollPane panelDcha = new JScrollPane(JlistaPasos);
        JPanel btnAñaEliPasos = new JPanel();
		
        JButton botonAñadirPaso= new JButton("AÑADIR PASO");
        JButton botonElimiarPaso= new JButton("ELIMINAR PASO");
        
        pDer.add(labelPasos);
		pDer.add(panelDcha);
		btnAñaEliPasos.add(botonAñadirPaso);
		btnAñaEliPasos.add(botonElimiarPaso);
		pDer.add(btnAñaEliPasos);
		pDer.add(panelAlergia);
		
		
		datos.add(pIzq);
		datos.add(pDer);
		
		//Crear botones y añadir al panelBotones
		JButton botonCancelar = new JButton("CANCELAR");
		JButton botonConfirmar= new JButton("CONFIRMAR");
		
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
				// Obtener los nuevos valores de la dieta
				String nuevoNombre = fieldNombre.getText();
				Integer nuevoTiempo = (Integer) spinnerTiempo.getValue();
				TipoDificultad nuevoTipo = (TipoDificultad) comboDif.getSelectedItem();
				Integer nuevoKCAL = (Integer) spinnerKcal.getValue();
				
				String nombreAntiguo = d.getNombre();
				
		        //ANADIR A LA BD
		        Connection conn = DBManager.obtenerConexion();
		        try {
		        	d.setNombre(nuevoNombre);
			        if (DBManager.existeDieta(conn, d) && !d.getNombre().equals(nombreAntiguo)) {
			        	JOptionPane.showMessageDialog(null, "El nombre de la dieta ya existe");
						d.setNombre(nombreAntiguo);
			        
			        } else {
						d.setTiempo(nuevoTiempo);
						d.setDificultad(nuevoTipo);
						d.setKcal(nuevoKCAL);				
						d.setIngredientes(new ArrayList<String>(listaIngredientes));
						d.setPasos(new ArrayList<String>(listaPasos));
						d.setAlergias(new ArrayList<TipoAlergias>(listaAlergias));
			        	
						
						if (nombreAntiguo == "") {
							DBManager.anadirDieta(conn, d);
						} else {							
							PreparedStatement nombreStmt = conn.prepareStatement("UPDATE dietas set nombre = ?, tiempo = ?, dificultad = ?, kcal = ? where nombre = ?");
							nombreStmt.setString(1, d.getNombre());
							nombreStmt.setInt(2, d.getTiempo());
							nombreStmt.setString(3, d.getDificultad().toString());
							nombreStmt.setInt(4, d.getKcal());
							nombreStmt.setString(5, nombreAntiguo);
							nombreStmt.executeUpdate();
							nombreStmt.close();
							
							
							// MODIFICAR PASOS
							PreparedStatement eliminarPasosStmt = conn.prepareStatement("DELETE FROM pasos_dietas WHERE nombreDieta = ?");
							eliminarPasosStmt.setString(1, d.getNombre());
							eliminarPasosStmt.executeUpdate();
							eliminarPasosStmt.close();
							
							PreparedStatement anadirPasosStmt = conn.prepareStatement("INSERT INTO pasos_dietas VALUES(null, ?, ?)");
							anadirPasosStmt.setString(2, d.getNombre());
							for (String paso : d.getPasos()) {
								anadirPasosStmt.setString(1, paso);
								anadirPasosStmt.executeUpdate();
							}
							anadirPasosStmt.close();

							//MODIFICAR ALERGIAS
							PreparedStatement eliminarAlergiasStmt = conn.prepareStatement("DELETE FROM dieta_alergias WHERE nombreDieta = ?");
							eliminarAlergiasStmt.setString(1, d.getNombre());
							eliminarAlergiasStmt.executeUpdate();
							eliminarAlergiasStmt.close();
							
							PreparedStatement anadirAlergiasStmt = conn.prepareStatement("INSERT INTO dieta_alergias VALUES(null, ?, (SELECT id from alergias WHERE nombreAlergia = ?))");
							anadirAlergiasStmt.setString(1, d.getNombre());
							for (TipoAlergias alergia : d.getAlergias()) {
								anadirAlergiasStmt.setString(2, alergia.name());
								anadirAlergiasStmt.executeUpdate();
							}
							anadirAlergiasStmt.close();
							
							//MODIFICAR INGREDIENTES
							PreparedStatement eliminarIngredientesStmt = conn.prepareStatement("DELETE FROM ingredientes_dietas WHERE nombreDieta = ?");
							eliminarIngredientesStmt.setString(1, d.getNombre());
							eliminarIngredientesStmt.executeUpdate();
							eliminarIngredientesStmt.close();
							
							PreparedStatement anadirIngredientesStmt = conn.prepareStatement("INSERT INTO ingredientes_dietas VALUES (null, ?, ?)");
							anadirIngredientesStmt.setString(2, d.getNombre());
							for (String ingrediente : d.getIngredientes()) {
								anadirIngredientesStmt.setString(1, ingrediente);
								anadirIngredientesStmt.executeUpdate();
							}
							anadirIngredientesStmt.close();
						
							//MODIFICAR USUARIO_DIETA
							PreparedStatement modifUsuarioDieta = conn.prepareStatement("UPDATE usuario_dieta SET nombreDieta = ? WHERE nombreDieta = ?");
							modifUsuarioDieta.setString(1, d.getNombre());
							modifUsuarioDieta.setString(2, nombreAntiguo);
							modifUsuarioDieta.executeUpdate();
							modifUsuarioDieta.close();
						}
						
						
					
					}
			        conn.close();
		        } catch (SQLException e1) {
		        	e1.printStackTrace();
		        }					
		        
		        
				// Mostrar un mensaje de éxito
		        JOptionPane.showMessageDialog(null, "Cambios guardados correctamente");
		        dispose();
		        SwingUtilities.invokeLater(() -> new VentanaPanel(p));
			}
		});
		
        botonAñadirIngrediente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String nuevoIngrediente = obtenerNuevoIngrediente();

		       
				if (nuevoIngrediente != null && !nuevoIngrediente.isEmpty()) {
					modeloIngredientes.addElement(nuevoIngrediente);
					listaIngredientes.add(nuevoIngrediente);
		        }
			}
		});
        
        botonElimiarIngrediente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String ingrediente = JlistaIngredientes.getSelectedValue();
				modeloIngredientes.removeElement(ingrediente);
				listaIngredientes.remove(ingrediente);
			}
		});
        
        botonAñadirPaso.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String nuevoPaso= obtenerNuevoPaso();
				if (nuevoPaso != null && !nuevoPaso.isEmpty()) {
		           modeloPasos.addElement(nuevoPaso);
		           listaPasos.add(nuevoPaso);
		           
		        }
			}
		});
        
        botonElimiarPaso.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String paso = JlistaPasos.getSelectedValue();
				modeloPasos.removeElement(paso);
				listaPasos.remove(paso);
				
			}
		});
        
        panelBotones.add(botonCancelar, BorderLayout.WEST);
		panelBotones.add(botonConfirmar, BorderLayout.EAST);
	
		
		this.add(datos, BorderLayout.NORTH);
		this.add(panelBotones);
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Editar Usuario");
	}
	public String obtenerNuevoIngrediente() {
        return JOptionPane.showInputDialog(this, "Ingrese el nombre del nuevo ingrediente:", "Nuevo Ingrediente", JOptionPane.PLAIN_MESSAGE);
    }
	
	public String obtenerNuevoPaso() {
        return JOptionPane.showInputDialog(this, "Ingrese el nuevo paso:", "Nuevo Paso", JOptionPane.PLAIN_MESSAGE);
    }
	
}
