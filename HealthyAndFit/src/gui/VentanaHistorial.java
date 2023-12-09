package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import domain.Entrenamiento;
import domain.TipoDificultad;
import domain.Usuario;
import io.DBManager;
import io.RegistroLogger;

public class VentanaHistorial extends JFrame {

	private JTable table;
	
	private JPanel panelDerecha;
	private JPanel panelIzquierda;
	private JPanel panelBotones;
	
	private JButton botonVolver;
	private JButton botonEliminar;
	private JButton botonGuardar;
	
	private JLabel foto;
	
	private Map<LocalDateTime, Entrenamiento> map;
	
	private static final long serialVersionUID = 1L;
		
	public VentanaHistorial(Usuario u) throws SQLException {
	map = new HashMap<LocalDateTime, Entrenamiento>();
	
			getUsuarios(map, u);
		
	
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
			

	        
	        ImageIcon background = new ImageIcon("resources\\images\\calendario2.jpg");
	        foto = new JLabel(new ImageIcon(background.getImage().getScaledInstance(700, 700, Image.SCALE_SMOOTH)));

	    
	        panelDerecha = new JPanel(new GridLayout());
	        panelDerecha.setOpaque(false);
	        panelDerecha.add(foto);
	    
	        botonVolver = new JButton("Volver");
	        botonEliminar = new JButton("Eliminar");
	        botonGuardar = new JButton("Exportar Datos");
	        
	        
	        panelBotones = new JPanel(new GridLayout(5,1));
	        JTextArea area = new JTextArea();
	        JTextArea area2 = new JTextArea();
	        recursividad(area);
	        recursividad(area2);
	        area.setFont((new Font("Tahoma", Font.BOLD, 20)));
	        area2.setFont((new Font("Tahoma", Font.BOLD, 20)));
	        
	        panelBotones.add(botonEliminar);
	        panelBotones.add(area);
	        panelBotones.add(botonGuardar);
	        panelBotones.add(area2);
	        panelBotones.add(botonVolver);
	        
	        panelIzquierda = new JPanel(new BorderLayout());
	        panelIzquierda.add(new JScrollPane(table), BorderLayout.CENTER);
	        panelIzquierda.add(panelBotones, BorderLayout.SOUTH);
	   
	        this.add(panelIzquierda, BorderLayout.WEST);
	        this.add(panelDerecha);
	        
	        botonGuardar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					guardarHistorial(map);
					
					
				}
			});
	        
	        botonVolver.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
    				dispose();
					
				}
			});
	        
	        botonEliminar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					 try {
						eliminar();
						refresh(u);
					} catch (SQLException e1) {
						e1.printStackTrace();
						RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
						JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
					}
			          
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
	
	public void getUsuarios(Map<LocalDateTime, Entrenamiento> map, Usuario u) throws SQLException {
	    Connection conn = DBManager.obtenerConexion();
		Statement s = conn.createStatement();
		try {
			ResultSet rs = s.executeQuery("SELECT nombreEntrenamiento, fecha, dificultad FROM usuario_entrenamientos WHERE nombreUsuario =" + "'" + u.getNombreUsuario() + "'");
			while (rs.next()) {
				String nombreEntrenamiento = rs.getString("nombreEntrenamiento");
				LocalDateTime f = LocalDateTime.parse(rs.getString("fecha"));
				TipoDificultad dificultad = TipoDificultad.valueOf(rs.getString("dificultad").toUpperCase());
				Entrenamiento e = new Entrenamiento(nombreEntrenamiento, null, dificultad, 0, "", 0, 0, 0);
				map.put(f, e);								
			}
			rs.close();
		} finally {
			
			s.close();
			conn.close();
		}
						
	}
	
	public void eliminar() throws SQLException {
		int selectedRow = table.getSelectedRow();
		String fecha = table.getModel().getValueAt(selectedRow, 2).toString();
		 int opcion = JOptionPane.showConfirmDialog(null, "¿Quieres eliminar el entrenamiento?", "Eliminar Entrenamiento", JOptionPane.YES_NO_OPTION);
         if (opcion == JOptionPane.YES_OPTION) {
        	 Connection conn = DBManager.obtenerConexion();
        	 Statement s = null;
        	 try {
				s = conn.createStatement();
				s.executeUpdate("DELETE FROM usuario_entrenamientos WHERE fecha = " + "'" + fecha + "'");
				table.repaint();
			} catch (SQLException e1) {
				e1.printStackTrace();
				RegistroLogger.anadirLogeo(Level.SEVERE, "No se pudo conectar con la base de datos");
				JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);
			
			} 
        	s.close();
        	conn.close();
        	
         }
	}
	
	public void refresh(Usuario u) throws SQLException {
		dispose();
		new VentanaHistorial(u);
	}
	
	public static JTextArea recursividad(JTextArea area) {
		if (area.getText().length() > 33) {
			return area;
		}
		
		area.append("▀▄");
		return recursividad(area);
	}
	
	public static void guardarHistorial(Map<LocalDateTime, Entrenamiento> map) {
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setDialogTitle("Guardar Historial");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int userSelection = fileChooser.showSaveDialog(null);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
		try {
			PrintWriter pw = new PrintWriter(new File(fileToSave + ".csv"));
			for (Entry<LocalDateTime, Entrenamiento> entry : map.entrySet()) {
				String s = entry.getKey().toString().substring(0, 16).replace("T", " / ");
				pw.write(entry.getValue().getNombre() + ";" + entry.getValue().getDificultad() + ";" + s + "\n");
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			RegistroLogger.anadirLogeo(Level.SEVERE, "Error al crear el archivo csv");
			JOptionPane.showConfirmDialog(null, "Error al crear el archivo csv", "Error", JOptionPane.PLAIN_MESSAGE);
		} finally {
			JOptionPane.showConfirmDialog(null, "Historial guardado correctamente", "Exito", JOptionPane.PLAIN_MESSAGE);
		}
		}
	}
}



