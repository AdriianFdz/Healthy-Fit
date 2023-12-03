package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
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

import domain.Entrenamiento;
import domain.TipoDificultad;
import domain.Usuario;


public class VentanaEditarEntrenamiento extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JPanel panelIzquierdo;
		public JLabel labelNombre;
		public JTextField fieldNombre;
		public JLabel labelTiempo;
		public JSpinner spinnerTiempo;
		public JLabel labelDificultad;
		public JComboBox<TipoDificultad> comboDif;
		public JLabel labelKcal;
		public JSpinner spinnerKcal;
		
	public JPanel panelDerecha;
		public JLabel labelDesc;
		public DefaultTableModel descTableModel;
		public JTable tableDesc;
		public JScrollPane paneDesc;
		
	
	public VentanaEditarEntrenamiento(Entrenamiento ent, Usuario p) {

		JPanel datos = new JPanel(new GridLayout(1, 2));

		// Panel Izquierdo
		panelIzquierdo = new JPanel(new GridLayout(8, 1));

		// Componentes para el panel izquierdo
		labelNombre = new JLabel("NOMBRE");
		fieldNombre = new JTextField(20);
		labelTiempo = new JLabel("TIEMPO");
		labelDificultad = new JLabel("DIFICULTAD");
		comboDif = new JComboBox<>(TipoDificultad.values());
		labelKcal = new JLabel("KCAL");

		// Inicializamos con datos
		fieldNombre.setText(ent.getNombre());
		spinnerTiempo = new JSpinner(new SpinnerNumberModel(ent.getTiempo(), 0, 999, 1));
		comboDif.setSelectedItem(ent.getDificultad());
		spinnerKcal = new JSpinner(new SpinnerNumberModel(ent.getCalorias(), 0, 9999, 1));

		//Añadimos los elemetos al panel Izquierdo
		panelIzquierdo.add(labelNombre);
		panelIzquierdo.add(fieldNombre);
		panelIzquierdo.add(labelTiempo);
		panelIzquierdo.add(spinnerTiempo);
		panelIzquierdo.add(labelDificultad);
		panelIzquierdo.add(comboDif);
		panelIzquierdo.add(labelKcal);
		panelIzquierdo.add(spinnerKcal);
		
		//Creamos la parte derecha para el scroll panel
		panelDerecha = new JPanel();
		panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));

		//Componentes para el panel de la derecha
		labelDesc = new JLabel("DESCRIPCION");
		
		//Cargar la descripcion del entrenamiento en una tabla
		descTableModel = new DefaultTableModel();
		descTableModel.addColumn("DESCRIPCION DEL ENTRENAMIENTO");
		descTableModel.addRow(new Object[] {ent.getDescripcion()});
		
		// Crear la JTable con el modelo
	    tableDesc= new JTable(descTableModel);

	    //Personalizado de la tabla
	    tableDesc.setShowGrid(false);
	    
        JScrollPane panelDcha = new JScrollPane(tableDesc);
       
        panelDerecha.add(panelDcha);
        
        //Añadimos al panel de los datos los dos paneles
        datos.add(panelIzquierdo);
		datos.add(panelDerecha);
		
		// Panel botones
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		// Botones
		JButton botonCancelar = new JButton("Cancelar");
		JButton botonConfirmar = new JButton("Confirmar");

		// Listeners
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
				TipoDificultad nuevaDif = (TipoDificultad) comboDif.getSelectedItem();
				Integer nuevoKCAL = (Integer) spinnerKcal.getValue();
				
				// Actualizar los atributos del Entrenamiento
				ent.setNombre(nuevoNombre);
				ent.setTiempo(nuevoTiempo);
				ent.setDificultad(nuevaDif);
				ent.setCalorias(nuevoKCAL);
				
				// Obtener el número de filas en la tabla
		        int rowCount = descTableModel.getRowCount();
				
				// Mostrar un mensaje de éxito
		        JOptionPane.showMessageDialog(null, "Cambios guardados correctamente");
		        dispose();
		        SwingUtilities.invokeLater(() -> new VentanaPanel(p));
			}
		});
		panelBotones.add(botonCancelar, BorderLayout.WEST);
		panelBotones.add(botonConfirmar, BorderLayout.EAST);
		
		this.add(datos, BorderLayout.NORTH);
		this.add(panelBotones, BorderLayout.SOUTH);
			
		pack();
		this.setVisible(true);
		this.setSize(600, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Editar Entrenamiento");

	}
}
