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
import javax.swing.DefaultCellEditor;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import domain.Dieta;
import domain.TipoAlergias;
import domain.TipoDificultad;
import domain.Usuario;
import gui.VentanaHistorial.ModeloDatos;
import io.DBManager;


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
						if (modeloAlergia.contains(TipoAlergias.NINGUNA)) {
							modeloAlergia.removeElement(TipoAlergias.NINGUNA);
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
				if (modeloAlergia.isEmpty()) {
					modeloAlergia.addElement(TipoAlergias.NINGUNA);
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
				
				Dieta dietaAntigua = new Dieta(d);
				
				// Actualizar los atributos de Dieta
				d.setNombre(nuevoNombre);
				d.setTiempo(nuevoTiempo);
				d.setDificultad(nuevoTipo);
				d.setKcal(nuevoKCAL);
				d.setIngredientes(new ArrayList<String>(listaIngredientes));
				d.setPasos(new ArrayList<String>(listaPasos));
				
				
				
				
				
		        
		        //ANADIR A LA BD
		        Connection conn = DBManager.obtenerConexion();
		        try {
			        if (!DBManager.existeDieta(conn, dietaAntigua)) {
			        		DBManager.anadirDieta(conn, d);
			        		conn.close();
					} else {
	//					PreparedStatement nombreStmt = conn.prepareStatement("UPDATE dietas set nombre = ? where nombre = ?");
	//					nombreStmt.setString(1, d.getNombre());
	//					PreparedStatement tiempoStmt = conn.prepareStatement("UPDATE dietas set tiempo = ? where nombre = ?");
	//					PreparedStatement difiicultadStmt = conn.prepareStatement("UPDATE dietas set dificultad = ? where nombre = ?");
	//					PreparedStatement kcalStmt = conn.prepareStatement("UPDATE dietas set kcal = ? where nombre = ?");
	//				 
						DBManager.eliminarDieta(conn, dietaAntigua);
						PreparedStatement pstmt = conn.prepareStatement("UPDATE usuario_dieta set nombreDieta = ? WHERE nombreDieta = ?");
						pstmt.setString(1, nuevoNombre);
						pstmt.setString(2, dietaAntigua.getNombre());
						
						System.out.println(d.getNombre());
						System.out.println(dietaAntigua.getNombre());
						pstmt.executeUpdate();
						

						
						DBManager.anadirDieta(conn, d);
					
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
	
//	public void eliminarFilaIng() {
//		int filaSeleccionada = tablaIngredientes.getSelectedRow();
//		if (filaSeleccionada != -1) {
//			int confirmacion = JOptionPane.showConfirmDialog(this, 
//					"¿Seguro que quieres eliminar esta fila?", "Confirmar Eliminación", 
//					JOptionPane.YES_NO_OPTION);
//			if (confirmacion == JOptionPane.YES_OPTION) {
//                // Obtiene el nombre del ingrediente a eliminar
//                String nombreIngrediente = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
//                
//                // Elimina la fila del modelo de la tabla
//                modeloTabla.removeRow(filaSeleccionada);
//                
//                // Elimina el ingrediente de la lista asociada a la dieta
//                listaIngredientes.remove(nombreIngrediente);
//            }
//		 } else {
//	            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.", "Error", 
//	            		JOptionPane.ERROR_MESSAGE);
//	        }
//		}
	
//	public void eliminarFilaPaso() {
//		int filaSeleccionada = tablaPasos.getSelectedRow();
//		if (filaSeleccionada != -1) {
//			int confirmacion = JOptionPane.showConfirmDialog(this, 
//					"¿Seguro que quieres eliminar esta fila?", "Confirmar Eliminación", 
//					JOptionPane.YES_NO_OPTION);
//			if (confirmacion == JOptionPane.YES_OPTION) {
//                // Obtiene el paso a eliminar
//                String paso= (String) pasosTableModel.getValueAt(filaSeleccionada, 0);
//                
//                // Elimina la fila del modelo de la tabla
//                pasosTableModel.removeRow(filaSeleccionada);
//                
//                // Elimina el ingrediente de la lista asociada a la dieta
//                listaPasos.remove(paso);
//            }
//		 } else {
//	            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.", "Error", 
//	            		JOptionPane.ERROR_MESSAGE);
//	        }
//		}
}
