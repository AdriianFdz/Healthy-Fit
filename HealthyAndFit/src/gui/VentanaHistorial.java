package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

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
		Map<String, String> mapa= new HashMap<>();
		    Connection conn = DBManager.obtenerConexion();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT nombreEntrenamiento, fecha FROM usuario_entrenamientos WHERE nombreUsuario = 'juan_perez'");
			while (rs.next()) {
				String nombreEntrenamiento = rs.getString("nombreEntrenamiento");
				String fecha = rs.getString("fecha");
				LocalDateTime f = LocalDateTime.parse(rs.getString("fecha"));
				mapa.put(fecha.substring(0, 16).replace("T", " / "), nombreEntrenamiento);
				
			}
			System.out.println(mapa);
			Vector<String> header = new Vector<String>(Arrays.asList("Entrenamiento", "Fecha"));
			table = new JTable();
			table.setModel(new ModeloDatos(header, mapa));
		
			table.setDefaultRenderer(Object.class, new TableCellRenderer() {
				
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					JLabel label = new JLabel();
					label.setOpaque(true);
					label.setText(value.toString());
					label.setFont(new Font("Tahoma", Font.PLAIN, 15));
						if (isSelected) {
							label.setBackground(Color.CYAN);
							if (value.equals("Pierna")) {
								foto.setIcon(new ImageIcon("resources\\images\\pierna.jpg"));
							}else if (value.equals("Abdominales")){
								foto.setIcon(new ImageIcon("resources\\images\\abdominales.jpg"));
							}
						}
					table.setRowHeight(30);
					return label;
				}
			});
			

	        // Establecer la imagen como fondo de la etiqueta foto
	        ImageIcon background = new ImageIcon("resources\\images\\calendario2.jpg");
	        foto = new JLabel(new ImageIcon(background.getImage().getScaledInstance(1024, 1024, Image.SCALE_SMOOTH)));

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
					SwingUtilities.invokeLater(() -> new VentanaPerfil(u));
    				dispose();
					
				}
			});
	        
	        botonEliminar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					
				}
			});
	        
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setTitle("Historial");
	        pack(); 
	        setLocationRelativeTo(null); 
	        setVisible(true);
	}
	
	
	
	public class ModeloDatos extends AbstractTableModel {

	
		private static final long serialVersionUID = 1L;

		Vector<String> header;
		Map<String, String> mapa;
		
		public ModeloDatos(Vector<String> header, Map<String, String> mapa) {
			this.header = new Vector<>(header);
			this.mapa = mapa;
		}
		
		
		@Override
		public boolean isCellEditable(int row, int column) {
			
			return false;
			
		}
		
		@Override
		public int getRowCount() {
			return mapa.size();
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
			    for (Entry<String, String> entry : mapa.entrySet()) {
			        if (i == rowIndex) {
			            if (columnIndex == 0) {
			                return entry.getValue(); 
			            } else if (columnIndex == 1) {
			                return entry.getKey();   
			            }
			        }
			        i++;
			    }
			    return "";
		}
		
	}
}



