package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
		public JLabel labelIng;
		public DefaultTableModel modeloTabla;
		public JTable tablaIngredientes;
		public List<String> listaIngredientes;

		
	public JPanel pDer;
		public JLabel labelPasos;
		public DefaultTableModel pasosTableModel;
		public JTable tablaPasos;
		public JScrollPane panelDcha;
	
	public VentanaEditarDieta(Dieta d, Usuario p) {
	
		JPanel datos = new JPanel(new GridLayout(1,2));
		JPanel panelBotones = new JPanel();
		
		//Panel izqueirda y componenetes del panel
		pIzq = new JPanel(new GridLayout(10,1));
		pIzq.setLayout(new BoxLayout(pIzq, BoxLayout.Y_AXIS));

		labelNombre = new JLabel("NOMBRE");
		fieldNombre = new JTextField(20);
		labelTiempo = new JLabel("TIEMPO");
		labelDiff = new JLabel("DIFICULTAD");
		comboDif = new JComboBox<>(TipoDificultad.values());
		labelKcal = new JLabel("KCAL");
		labelIng = new JLabel("INGREDIENTES");
	
		listaIngredientes = new ArrayList<>(d.getIngredientes());
		
		// Crear el modelo de la tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("INGREDIENTES");
        
        for (String ingrediente : d.getIngredientes()) {
        	 modeloTabla.addRow(new Object[]{ingrediente});
       }
               
        // Crear la JTable con el modelo
       tablaIngredientes = new JTable(modeloTabla);
        
       
       // Configurar la JTable para permitir la edición directa
       tablaIngredientes.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JTextField()));
       
       JButton botonAñadirIngrediente = new JButton("AÑADIR ING");
       JButton botonElimiarIngrediente = new JButton("ELIMINAR ING");
       
		//Inicializamos los otros elementos con los datos del usuario
		fieldNombre.setText(d.getNombre());
		comboDif.setSelectedItem(d.getDificultad());
		SpinnerNumberModel modeloSpin = new SpinnerNumberModel(d.getTiempo(),0,999,1);
		spinnerTiempo = new JSpinner(modeloSpin);
		spinnerKcal = new JSpinner(new SpinnerNumberModel(d.getTiempo(), 0, 9999, 1));
	
		JScrollPane paneIng = new JScrollPane(tablaIngredientes);
		
		//Agregamos componentes al paneIzq
		pIzq.add(labelNombre);
		pIzq.add(fieldNombre);
		pIzq.add(labelTiempo);
		pIzq.add(spinnerTiempo);
		pIzq.add(labelDiff);
		pIzq.add(comboDif);
		pIzq.add(labelKcal);
		pIzq.add(spinnerKcal);
		pIzq.add(labelIng);
		pIzq.add(paneIng);
		pIzq.add(botonAñadirIngrediente);
		pIzq.add(botonElimiarIngrediente);
		
		//Panel derecha
		pDer = new JPanel();
		pDer.setLayout(new BoxLayout(pDer, BoxLayout.Y_AXIS));

		//Componentes para el panel de la derecha
		labelPasos = new JLabel("PASOS");
		pasosTableModel = new DefaultTableModel();
		pasosTableModel.addColumn("LISTA DE PASOS");
        
		for (String paso : d.getPasos()) {
        	pasosTableModel.addRow(new Object[]{paso});
        }
		// Crear la JTable con el modelo
	    tablaPasos= new JTable(pasosTableModel);

	    //Personalizado de la tabla
	    tablaPasos.setShowGrid(false);
	    
        JScrollPane panelDcha = new JScrollPane(tablaPasos);
		
        pDer.add(labelPasos);
		pDer.add(panelDcha);
		
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
				
				// Actualizar los atributos de Dieta
				d.setNombre(nuevoNombre);
				d.setTiempo(nuevoTiempo);
				d.setDificultad(nuevoTipo);
				d.setKcal(nuevoKCAL);
				d.setIngredientes(listaIngredientes);
				
				// Obtener el número de filas en la tabla
		        int rowCount = pasosTableModel.getRowCount();
		        // Actualizar la lista de pasos en la Dieta con los nuevos valores de la tabla
		        for (int i = 0; i < rowCount; i++) {
		            String nuevoPaso = (String) pasosTableModel.getValueAt(i, 0);
		            
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

		        // Añade la nueva fila al modelo de la tabla
				if (nuevoIngrediente != null && !nuevoIngrediente.isEmpty()) {
		            // Añade la nueva fila al modelo de la tabla
		            modeloTabla.addRow(new Object[]{nuevoIngrediente});
		            listaIngredientes.add(nuevoIngrediente);
		        }
			}
		});
        botonElimiarIngrediente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminarFila();
				
			}
		});
        panelBotones.add(botonCancelar, BorderLayout.WEST);
		panelBotones.add(botonConfirmar, BorderLayout.EAST);
		
		this.add(datos, BorderLayout.NORTH);
		this.add(panelBotones, BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Editar Usuario");
	}
	public String obtenerNuevoIngrediente() {
        return JOptionPane.showInputDialog(this, "Ingrese el nombre del nuevo ingrediente:", "Nuevo Ingrediente", JOptionPane.PLAIN_MESSAGE);
    }
	
	public void eliminarFila() {
		int filaSeleccionada = tablaIngredientes.getSelectedRow();
		if (filaSeleccionada != -1) {
			int confirmacion = JOptionPane.showConfirmDialog(this, 
					"¿Seguro que quieres eliminar esta fila?", "Confirmar Eliminación", 
					JOptionPane.YES_NO_OPTION);
			if (confirmacion == JOptionPane.YES_OPTION) {
                // Obtiene el nombre del ingrediente a eliminar
                String nombreIngrediente = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
                
                // Elimina la fila del modelo de la tabla
                modeloTabla.removeRow(filaSeleccionada);
                
                // Elimina el ingrediente de la lista asociada a la dieta
                listaIngredientes.remove(nombreIngrediente);
            }
		 } else {
	            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.", "Error", 
	            		JOptionPane.ERROR_MESSAGE);
	        }
		}
}
