package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import domain.Entrenamiento;
import domain.TipoDificultad;
import domain.Usuario;
import io.DBManager;

public class VentanaHistorial extends JFrame {

	private JTable table;
	
	private JPanel panelDerecha;
	private JPanel panelIzquierda;
	private JPanel panelBotones;
	
	private JButton botonVolver;
	private JButton botonEliminar;
	
	private JLabel foto;
	private JLabel label;
	
	private static final long serialVersionUID = 1L;
		
	public VentanaHistorial(Usuario u) throws SQLException {
		Map<LocalDateTime, Entrenamiento> map= new HashMap<LocalDateTime, Entrenamiento>();
		    Connection conn = DBManager.obtenerConexion();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT nombreEntrenamiento, fecha, dificultad FROM usuario_entrenamientos WHERE nombreUsuario = 'juan_perez'");
			while (rs.next()) {
				String nombreEntrenamiento = rs.getString("nombreEntrenamiento");
				LocalDateTime f = LocalDateTime.parse(rs.getString("fecha"));
				TipoDificultad dificultad = TipoDificultad.valueOf(rs.getString("dificultad").toUpperCase());
				Entrenamiento e = new Entrenamiento(nombreEntrenamiento, null, dificultad, 0, "", 0, 0, 0);
				map.put(f, e);
			
				
				
			}
			Vector<String> header = new Vector<String>(Arrays.asList("Entrenamiento", "Dificultad", "Fecha" ));
			table = new JTable();
			table.setModel(new ModeloDatos(header, map));
		
			table.setDefaultRenderer(Object.class, new TableCellRenderer() {
				
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					JLabel label = new JLabel();
					label.setOpaque(true);
					label.setText(value.toString());
					label.setFont(new Font("Tahoma", Font.PLAIN, 15));
						if (column == 2) {
							label.setText(value.toString().substring(0, 16).replace("T", " / "));
							
						}
						if (isSelected) {
							label.setBackground(Color.CYAN);
							if (value.equals("Pierna")) {
								foto.setIcon(new ImageIcon("resources\\images\\pierna.jpg"));
							}else if (value.equals("Abdominales")){
								foto.setIcon(new ImageIcon("resources\\images\\abdominales.jpg"));
							}
						}
					label.setHorizontalAlignment(JLabel.CENTER);
					table.setRowHeight(30);
					return label;
				}
			});
			

	        // Establecer la imagen como fondo de la etiqueta foto
	        ImageIcon background = new ImageIcon("resources\\images\\calendario2.jpg");
	        foto = new JLabel(new ImageIcon(background.getImage().getScaledInstance(700, 700, Image.SCALE_SMOOTH)));

	        // Superponer la tabla sobre la etiqueta de la foto
	        panelDerecha = new JPanel(new GridLayout());
	        panelDerecha.setOpaque(false); // Hacer el panel transparente
	        panelDerecha.add(foto);
	    
	        botonVolver = new JButton("Volver");
	        botonEliminar = new JButton("Eliminar");
	        
	        panelBotones = new JPanel(new BorderLayout());
	        label = new JLabel("▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄");
	        label.setFont(new Font("Tahoma", Font.BOLD, 20));
	        label.setHorizontalAlignment(JLabel.CENTER);
	        panelBotones.add(botonEliminar, BorderLayout.NORTH);
	        panelBotones.add(label, BorderLayout.CENTER);
	        panelBotones.add(botonVolver, BorderLayout.SOUTH);
	        
	        panelIzquierda = new JPanel(new BorderLayout());
	        panelIzquierda.add(new JScrollPane(table), BorderLayout.CENTER);
	        panelIzquierda.add(panelBotones, BorderLayout.SOUTH);
	   
	        this.add(panelIzquierda, BorderLayout.WEST);
	        this.add(panelDerecha);
	        
	        
	        botonVolver.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
    				dispose();
					
				}
			});
	        
	        botonEliminar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					
				}
			});
	        
	        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        setTitle("Historial");
	        pack(); 
	        setLocationRelativeTo(null); 
	        setVisible(true);
	}
	
	
	
	public class ModeloDatos extends AbstractTableModel {

	
		private static final long serialVersionUID = 1L;

		Vector<String> header;
		Map<LocalDateTime, Entrenamiento> map;
		
		public ModeloDatos(Vector<String> header, Map<LocalDateTime, Entrenamiento> map) {
			this.header = new Vector<>(header);
			this.map = map;
		}
		
		
		@Override
		public boolean isCellEditable(int row, int column) {
			
			return false;
			
		}
		
		@Override
		public int getRowCount() {
			return map.size();
		}
		
		
		@Override
		public String getColumnName(int column) {
			return header.get(column);
		}
		
		
		@Override
		public int getColumnCount() {
			return header.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			 int i = 0;
			    for (Entry<LocalDateTime, Entrenamiento> entry : map.entrySet()) {
			        if (i == rowIndex) {
			            if (columnIndex == 0) {
			                return entry.getValue().getNombre(); 
			            } else if (columnIndex == 2) {
			                return entry.getKey();   
			            }else if (columnIndex == 1) {
			            	return entry.getValue().getDificultad().toString();
			            }
			        }
			        i++;
			    }
			    return "";
		}
		
	}
}



