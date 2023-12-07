package gui;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import domain.Usuario;
import io.DBManager;

public class VentanaHistorial extends JFrame {

	private JTable table;
	
	private static final long serialVersionUID = 1L;
	
	JPanel panel;
	
	public VentanaHistorial(Usuario u) throws SQLException {
		Map<String, String> mapa= new HashMap<>();
		    Connection conn = DBManager.obtenerConexion();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT nombreEntrenamiento, fecha FROM usuario_entrenamientos WHERE nombreUsuario = 'juan_perez'");
			while (rs.next()) {
				String nombreEntrenamiento = rs.getString("nombreEntrenamiento");
				String fecha = rs.getString("fecha");
				mapa.put(fecha, nombreEntrenamiento);
			}
			
			Vector<String> header = new Vector<String>(Arrays.asList("Entrenamiento", "Fecha"));
			table = new JTable();
			table.setModel(new ModeloDatos(header, mapa));
		
			panel = new JPanel();		
			panel.setLayout(new BorderLayout());
			panel.add(table);
			
			this.add(panel, BorderLayout.CENTER);
		 	setSize(1920,1080);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setTitle("Historial");
	        setVisible(true);
	}
	
	
	
	public class ModeloDatos extends AbstractTableModel {

	
		private static final long serialVersionUID = 1L;

		Vector<String> header;
		Map<String, String> mapa;
		
		public ModeloDatos(Vector<String> header, Map<String, String> mapa) {
			this.header = new Vector<>();
			this.header.addAll(header);
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
			System.out.println(header.get(column));
			return header.get(column);
		}
		
		
		@Override
		public int getColumnCount() {
			return header.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			for (Entry<String, String> entry : mapa.entrySet()) {
				if (columnIndex == 1) {
					return entry.getValue();
				}else {
					return entry.getKey();
				}
			}
			return "";
		}
		
	}
}



