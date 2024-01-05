package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ToolTipManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import db.DBManager;
import domain.Entrenamiento;
import domain.TipoDificultad;
import domain.TipoEntrenamiento;
import domain.Usuario;
import io.ExportarDatos;
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
		   
	
			Vector<String> header = new Vector<String>(Arrays.asList("Entrenamiento", "Dificultad", "Fecha", "Calorias", "Descripcion", "Tiempo", "Series", "Repeticiones" ));
			table = new JTable();
			table.setModel(new ModeloDatos(header, map));

			
			table.setDefaultRenderer(Object.class, new TableCellRenderer() {
				
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					table.getColumnModel().getColumn(column).setPreferredWidth(300);
					JLabel label = new JLabel();
					label.setOpaque(true);
					label.setText(value.toString());
					label.setFont(new Font("Tahoma", Font.PLAIN, 15));
					
						if (column == 2) {
							label.setText(value.toString().substring(0, 16).replace("T", " / "));
							
						}
					     
					     
						if (isSelected) {
							
							label.setBackground(Color.CYAN);
	 
						}
						
					label.setHorizontalAlignment(JLabel.CENTER);
					table.setRowHeight(30);
					table.repaint();
					return label;
				}
				
				
			});
			
			ToolTipManager.sharedInstance().setInitialDelay(0);
			table.addMouseMotionListener(new MouseMotionAdapter() {
				
				@Override
				public void mouseMoved(MouseEvent e) {
					int fila = table.rowAtPoint(e.getPoint());
					int columna = table.columnAtPoint(e.getPoint());
					if (columna == 4) {
						table.setToolTipText(table.getValueAt(fila, columna).toString());
					}else {
						table.setToolTipText(null);
					}
					
					
				}
				
			});
	        
	        ImageIcon background = new ImageIcon("resources\\images\\historial.jpeg");
	        foto = new JLabel(new ImageIcon(background.getImage().getScaledInstance(400, 600, Image.SCALE_SMOOTH)));

	    
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
	        panelIzquierda.add(new JScrollPane(table));
	        panelIzquierda.add(panelBotones, BorderLayout.SOUTH);
	   
	        this.add(panelIzquierda, BorderLayout.CENTER);
	        this.add(panelDerecha, BorderLayout.EAST);
	        
	        botonGuardar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ExportarDatos.guardarHistorial(map, "Guardar archivo en...");
					

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
	        pack(); 
	        setResizable(false);
	        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        setTitle("Historial");
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
			            }else if (columnIndex == 3) {
			            	return entry.getValue().getCalorias();
			            }else if (columnIndex == 4) {
			            	return entry.getValue().getDescripcion();
			            }else if (columnIndex == 5) {
			            	return entry.getValue().getTiempo();
			            }else if (columnIndex == 6) {
			            	return entry.getValue().getSeries();
			            }else if (columnIndex == 7) {
			            	return entry.getValue().getRepeticiones();
			            }
			        }
			        i++;
			    }
			    return "";
		}
		
	}
	
	public void getUsuarios(Map<LocalDateTime, Entrenamiento> map, Usuario u) throws SQLException {
	    Connection conn = DBManager.obtenerConexion();
		Entrenamiento e = null;
		LocalDateTime f = null;
		try {
			PreparedStatement s = conn.prepareStatement("SELECT nombreEntrenamiento, fecha, dificultad FROM usuario_entrenamientos WHERE nombreUsuario = ?");
			s.setString(1, u.getNombreUsuario());
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				String nombreEntrenamiento = rs.getString("nombreEntrenamiento");
				f = LocalDateTime.parse(rs.getString("fecha"));
				TipoDificultad dificultad = TipoDificultad.valueOf(rs.getString("dificultad").toUpperCase());
				PreparedStatement s2 = conn.prepareStatement("SELECT * FROM entrenamientos WHERE nombre = ? AND dificultad = ?");
				s2.setString(1, nombreEntrenamiento);
				s2.setString(2, dificultad.toString().toUpperCase());
				ResultSet rs2 = s2.executeQuery();
				while (rs2.next()) {
					TipoEntrenamiento tipoEntrenamiento = TipoEntrenamiento.valueOf(rs2.getString("tipoEntrenamiento").toUpperCase());
					int tiempo = rs2.getInt("tiempo");
					String descripcion = rs2.getString("descripcion");
					int calorias = rs2.getInt("calorias");
					int series = rs2.getInt("series");
					int repeticiones = rs2.getInt("repeticiones");
					ImageIcon fotoEnt = new ImageIcon(rs2.getBytes("foto"));

					e = new Entrenamiento(nombreEntrenamiento, tipoEntrenamiento, dificultad, tiempo, descripcion, calorias, series, repeticiones, fotoEnt);
					map.put(f, e);	
				}
				
			rs2.close();					
			}
			
			rs.close();
			
		} finally {
			
			
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
		if (area.getText().length() > 85) {
			return area;
		}
		
		area.append("▀▄");
		return recursividad(area);
	}
	
}



