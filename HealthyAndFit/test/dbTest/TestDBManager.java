package dbTest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import db.DBManager;
import domain.Dieta;
import domain.Entrenamiento;
import domain.TipoAlergias;
import domain.TipoEnfermedades;
import domain.Usuario;
import io.RegistroLogger;

public class TestDBManager {

	Connection connTest;
	
	@Before
	public void setUp() {
		try {
			Files.copy(Paths.get("src/db/Healthy-Fit-Tests-No-Modificable.db"), Paths.get("src/db/Healthy-Fit-Tests.db"), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo clonar la BD para los tests");
			JOptionPane.showConfirmDialog(null, "No se pudo clonar la BD para los tests", "Error", JOptionPane.PLAIN_MESSAGE);		
		}
		
		connTest = DBManager.obtenerConexion("databaseTest");
	}
	
	@After
	public void tearDown() {
		try {
			connTest.close();
		} catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);		
		}
	}
	
	@Test ()
	public void testObtenerConexion() {
		try {
			assertTrue(connTest.isValid(0));
			Connection connReal = DBManager.obtenerConexion();
			assertTrue(DBManager.obtenerConexion().isValid(0));
			connReal.close();
						
		} catch (SQLException e) {
			e.printStackTrace();
			RegistroLogger.getLogger().log(Level.SEVERE, "No se pudo conectar con la base de datos");
			JOptionPane.showConfirmDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.PLAIN_MESSAGE);		
		}
	}
	
	@Test
	public void testObtenerDietas() {
		Dieta d = DBManager.obtenerDietas(connTest).get(0);
		assertTrue(DBManager.existeDieta(connTest, d));
		Dieta dietaConCondicion = DBManager.obtenerDietas(connTest, d.getNombre());
		assertTrue(DBManager.existeDieta(connTest, dietaConCondicion));

		Dieta dietaConAlergias = new Dieta();
		dietaConAlergias.setNombre("test");
		dietaConAlergias.setAlergias(Arrays.asList(TipoAlergias.APIO));
		DBManager.anadirDieta(connTest, dietaConAlergias);
		List<Dieta> listaDietas = DBManager.obtenerDietas(connTest);
		
		assertTrue(DBManager.existeDieta(connTest, listaDietas.get(listaDietas.size()-1)));
	}
	@Test
	public void testObtenerUsuarios() {
		Usuario u = DBManager.obtenerUsuarios(connTest).get(0);
		assertTrue(DBManager.existeUsuario(connTest, u));
		Usuario usuarioConCondicion = DBManager.obtenerUsuarios(connTest, u.getNombreUsuario());
		assertTrue(DBManager.existeUsuario(connTest, usuarioConCondicion));
		assertNull(DBManager.obtenerUsuarios(connTest, "NoExiste"));
	}

	@Test
	public void testObtenerEntrenamienntos() {
		Entrenamiento e = DBManager.obtenerEntrenamientos(connTest).get(0);
		assertTrue(DBManager.existeEntrenamiento(connTest, e));
		Entrenamiento entrenamientoConCondicion = DBManager.obtenerEntrenamientos(connTest, e.getNombre());
		assertTrue(DBManager.existeEntrenamiento(connTest, entrenamientoConCondicion));
		assertNull((DBManager.obtenerEntrenamientos(connTest, "NoExiste")));
		
		}
	@Test
	public void testObtenerFoto() {
		Usuario u = DBManager.obtenerUsuarios(connTest).get(0);
		byte[] foto = DBManager.obtenerFoto(connTest, u.getNombreUsuario());
		assertArrayEquals(DBManager.convertirFotoABytes(u.getFoto()), foto);
		assertNull(DBManager.obtenerFoto(connTest, "NoExiste"));

	}
	
	@Test
	public void testAnadirEnfermedad() {
		DBManager.eliminarEnfermedad(connTest, TipoEnfermedades.DIGESTIVAS);
		assertFalse(DBManager.existeEnfermedad(connTest, TipoEnfermedades.DIGESTIVAS));
		DBManager.anadirEnfermedad(connTest, TipoEnfermedades.DIGESTIVAS);
		assertTrue(DBManager.existeEnfermedad(connTest, TipoEnfermedades.DIGESTIVAS));
	}
	
	@Test
	public void testAnadirEntrenamiento() {
		Entrenamiento e = DBManager.obtenerEntrenamientos(connTest).get(0);
		DBManager.eliminarEntrenamientos(connTest, e);
		assertFalse(DBManager.existeEntrenamiento(connTest, e));
		DBManager.anadirEntrenamiento(connTest, e);
		assertTrue(DBManager.existeEntrenamiento(connTest, e));
		
	}

	@Test
	public void testAnadirUsuarioEntrenamiento() {
		Usuario u = DBManager.obtenerUsuarios(connTest).get(0);
		Entrenamiento e = DBManager.obtenerEntrenamientos(connTest).get(0);
		
		LocalDateTime fecha = LocalDateTime.now();
		DBManager.anadirUsuarioEntrenamientos(connTest, u, e);
		assertTrue(DBManager.existeUsuarioEntrenamiento(connTest, u, e, fecha));
		assertFalse(DBManager.existeUsuarioEntrenamiento(connTest, new Usuario(), new Entrenamiento(), LocalDateTime.now()));;

		
	}
	
	@Test
	public void testAnadirAlergia() {
		DBManager.eliminarAlergia(connTest, TipoAlergias.APIO);
		assertFalse(DBManager.existeAlergia(connTest, TipoAlergias.APIO));
		DBManager.anadirAlergia(connTest, TipoAlergias.APIO);
		assertTrue(DBManager.existeAlergia(connTest, TipoAlergias.APIO));
	}
	
	@Test
	public void testAnadirDieta() {
		Dieta d = DBManager.obtenerDietas(connTest).get(0);
		DBManager.eliminarDieta(connTest, d);
		assertFalse(DBManager.existeDieta(connTest, d));
		
		d.setAlergias(Arrays.asList(TipoAlergias.HUEVOS));
		DBManager.anadirDieta(connTest, d);
		assertTrue(DBManager.existeDieta(connTest, d));
	}
	
	@Test
	public void testAnadirUsuario() {
		Usuario u = new Usuario();
		u.setEnfermedades(Arrays.asList(TipoEnfermedades.NEUROLOGICAS));
		u.setAlergias(Arrays.asList(TipoAlergias.ALTRAMUCES));
		if (DBManager.existeUsuario(connTest, u)) {
			DBManager.eliminarUsuario(connTest, u);
		}
		DBManager.anadirUsuario(connTest, u);
		assertTrue(DBManager.existeUsuario(connTest, u));
		
	}
	
	@Test
	public void anadirUsuarioDieta() {
		Usuario u = DBManager.obtenerUsuarios(connTest).get(0);
		Dieta d = DBManager.obtenerDietas(connTest).get(0);
		DBManager.anadirUsuarioDieta(connTest, u, d, LocalDate.now());
		assertTrue(DBManager.existeUsuarioDieta(connTest, u, d, LocalDate.now()));
		
		Dieta nuevaD = new Dieta();
		LocalDate fecha = LocalDate.of(2023, 12, 9);
		DBManager.anadirUsuarioDieta(connTest, u, nuevaD, fecha);
		assertFalse(DBManager.existeUsuarioDieta(connTest, u, nuevaD, fecha));
	}
	
	@Test
	public void testEliminarDieta() {
		Dieta d = DBManager.obtenerDietas(connTest).get(0);
		assertTrue(DBManager.existeDieta(connTest, d));
		DBManager.eliminarDieta(connTest, d);
		assertFalse(DBManager.existeDieta(connTest, d));
	}
	
	@Test
	public void testEliminarUsuario() {
		Usuario u = DBManager.obtenerUsuarios(connTest).get(0);
		assertTrue(DBManager.existeUsuario(connTest, u));
		DBManager.eliminarUsuario(connTest, u);
		assertFalse(DBManager.existeUsuario(connTest, u));

	}
	
	@Test
	public void testActualizarFotoUsuario() {
		Usuario u = DBManager.obtenerUsuarios(connTest).get(0);
		DBManager.actualizarFotoUsuario(connTest, u, new ImageIcon("resources/images/foto.png"));
		assertArrayEquals(DBManager.convertirFotoABytes(new ImageIcon("resources/images/foto.png")), DBManager.obtenerFoto(connTest, u.getNombreUsuario()));
	}

	@Test
	public void testActualizarFotoEntrena() {
		Entrenamiento e = DBManager.obtenerEntrenamientos(connTest).get(0);
		DBManager.actualizarFotoEntrena(connTest, e, new ImageIcon("resources/images/foto.png"));
		assertArrayEquals(DBManager.convertirFotoABytes(new ImageIcon("resources/images/foto.png")), DBManager.convertirFotoABytes(DBManager.obtenerEntrenamientos(connTest, e.getNombre()).getFoto()));
	}
	
	@Test
	public void modificarVasosAgua() {
		Usuario u = DBManager.obtenerUsuarios(connTest).get(0);
		
		DBManager.modificarVasosAgua(connTest, u, 2);
		assertEquals(2, DBManager.obtenerUsuarios(connTest, u.getNombreUsuario()).getVasosDeAgua());
		
		DBManager.modificarVasosAgua(connTest, u, 5);
		assertEquals(5, DBManager.obtenerUsuarios(connTest, u.getNombreUsuario()).getVasosDeAgua());
		

	}
}
