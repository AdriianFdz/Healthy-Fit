package domainTest;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;

import domain.Dieta;
import domain.Entrenamiento;
import domain.TipoAlergias;
import domain.TipoDificultad;
import domain.TipoEnfermedades;
import domain.TipoEntrenamiento;
import domain.TipoPermiso;
import domain.TipoSexo;
import domain.Usuario;

public class TestUsuario {
	Usuario usuario;
	Usuario usuarioSinArgs;
	Entrenamiento e;
	
	@Before
	public void setUp() {
		e = new Entrenamiento("test", TipoEntrenamiento.INFERIOR, TipoDificultad.DIFICIL, 3, "test descripcion", 10, 5, 3, new ImageIcon("resources/images/foto.png"));
		usuario = new Usuario("Juan", "juan_perez", "Perez", "Carbon", LocalDate.of(2004, 6, 10), TipoSexo.HOMBRE, 1.75, 75, new ArrayList<TipoAlergias>(Arrays.asList(TipoAlergias.HUEVOS)), "juan@gmail.com", new ArrayList<TipoEnfermedades>(Arrays.asList(TipoEnfermedades.CARDIOVASCULARES)), 12300, 300, LocalDate.now(), 13000, new HashMap<LocalDate, Dieta>(), 3, "juan", new ImageIcon("resources\\images\\foto.png"), TipoPermiso.ADMINISTRADOR, new ArrayList<Entrenamiento>(Arrays.asList(e)));
		usuarioSinArgs = new Usuario();
				
	} 
	@Test
	public void testGetNombre(){
		assertEquals("Juan", usuario.getNombre());
	}
	@Test
	public void testSetNombre(){
		usuario.setNombre("Adrian");
		assertEquals("Adrian", usuario.getNombre());
	}
	@Test
	public void testGetNombreUsuario(){
		assertEquals("juan_perez", usuario.getNombreUsuario());
	}
	@Test
	public void testSetNombreUsuario(){
		usuario.setNombreUsuario("Adrian_fdz");
		assertEquals("Adrian_fdz", usuario.getNombreUsuario());
	}
	@Test
	public void testGetApellido1(){
		assertEquals("Perez", usuario.getApellido1());
	}
	@Test
	public void testSetApellido1(){
		usuario.setApellido1("Fernandez");
		assertEquals("Fernandez", usuario.getApellido1());
	}
	@Test
	public void testGetApellido2(){
		assertEquals("Carbon", usuario.getApellido2());
	}
	@Test
	public void testSetApellido2(){
		usuario.setApellido2("Martinez");
		assertEquals("Martinez", usuario.getApellido2());
	}
	@Test
	public void testGetFechaNacimiento(){
		assertEquals(LocalDate.of(2004, 6, 10), usuario.getFechaNacimiento());
	}
	@Test
	public void testSetFechaNacimiento(){
		usuario.setFechaNacimiento(LocalDate.of(2004, 1, 1));
		assertEquals(LocalDate.of(2004, 1, 1), usuario.getFechaNacimiento());
	}
	@Test
	public void testGetSexo(){
		assertEquals(TipoSexo.HOMBRE, usuario.getSexo());
	}
	@Test
	public void testSetSexo(){
		usuario.setSexo(TipoSexo.MUJER);
		assertEquals(TipoSexo.MUJER, usuario.getSexo());
	}
	@Test
	public void testGetAltura() {
		assertEquals(1.75, usuario.getAltura(), 0.001);
	}	
	@Test
	public void testSetAltura(){
		usuario.setAltura(1.83);
		assertEquals(1.83, usuario.getAltura(), 0.001);
		usuario.setAltura(-1);
		assertEquals(1.83, usuario.getAltura(), 0.001);
	}
	@Test
	public void testGetPeso() {
		assertEquals(75, usuario.getPeso());
	}	
	@Test
	public void testSetPeso(){
		usuario.setPeso(80);
		assertEquals(80, usuario.getPeso());
		usuario.setPeso(-1);
		assertEquals(80, usuario.getPeso());
	}
	@Test
	public void testGetAlergias() {
		assertEquals(Arrays.asList(TipoAlergias.HUEVOS), usuario.getAlergias());
	}	
	@Test
	public void testSetAlergias(){
		usuario.setAlergias(Arrays.asList(TipoAlergias.HUEVOS, TipoAlergias.GLUTEN));
		assertEquals(Arrays.asList(TipoAlergias.HUEVOS, TipoAlergias.GLUTEN), usuario.getAlergias());
	}
	@Test
	public void testGetCorreoElectronico(){
		assertEquals("juan@gmail.com", usuario.getCorreoElectronico());
	}
	@Test
	public void testSetCorreoElectronico(){
		usuario.setCorreoElectronico("perez@hotmail.es");
		assertEquals("perez@hotmail.es", usuario.getCorreoElectronico());
	}
	@Test
	public void testGetEnfermedades(){
		assertEquals(Arrays.asList(TipoEnfermedades.CARDIOVASCULARES), usuario.getEnfermedades());
	}
	@Test
	public void testSetEnfermedades(){
		usuario.setEnfermedades(Arrays.asList(TipoEnfermedades.DIABETES, TipoEnfermedades.CARDIOVASCULARES));;
		assertEquals(Arrays.asList(TipoEnfermedades.DIABETES, TipoEnfermedades.CARDIOVASCULARES), usuario.getEnfermedades());
	}
	@Test
	public void testGetIMC(){
		assertEquals(24.48, usuario.getImc(), 0.01);
	}
	@Test
	public void testGetCaloriasGastadas(){
		assertEquals(12300, usuario.getCaloriasGastadas());
	}
	@Test
	public void testSetCaloriasGastadas() {
		usuario.setCaloriasGastadas(100);
		assertEquals(100, usuario.getCaloriasGastadas());
		usuario.setCaloriasGastadas(-1);
		assertEquals(100, usuario.getCaloriasGastadas());
	}
	
	@Test
	public void testGetTiempoEntrenado(){
		assertEquals(300, usuario.getTiempoEntrenado());
	}
	@Test
	public void testSetTiempoEntrenado() {
		usuario.setTiempoEntrenado(500);
		assertEquals(500, usuario.getTiempoEntrenado());
		usuario.setTiempoEntrenado(-1);
		assertEquals(500, usuario.getTiempoEntrenado());
	}
	@Test
	public void testGetUltimaVezEntreno(){
		assertEquals(LocalDate.now(), usuario.getUltimaVezEntreno());
	}
	@Test
	public void testSetUltimaVezEntreno() {
		usuario.setUltimaVezEntreno(LocalDate.of(2023, 7, 1));
		assertEquals(LocalDate.of(2023, 7, 1), usuario.getUltimaVezEntreno());
	}
	@Test
	public void testGetCaloriasConsumidas(){
		assertEquals(13000, usuario.getCaloriasConsumidas());
	}
	@Test
	public void testSetCaloriasConsumidas() {
		usuario.setCaloriasConsumidas(10000);
		assertEquals(10000, usuario.getCaloriasConsumidas());
		usuario.setCaloriasConsumidas(-1);
		assertEquals(10000, usuario.getCaloriasConsumidas());
	}
	@Test
	public void testGetProximaComida(){
		assertEquals(new HashMap<LocalDate, Dieta>(), usuario.getProximaComida());
	}
	@Test
	public void testSetProximaComida() {
		Map<LocalDate, Dieta> comida = new HashMap<LocalDate, Dieta>();
		comida.putIfAbsent(LocalDate.now(), new Dieta("test", 10, TipoDificultad.FACIL, 5, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<TipoAlergias>()));
		usuario.setProximaComida(comida);
		assertEquals(comida, usuario.getProximaComida());
	}
	@Test
	public void testGetVasosDeAgua(){
		assertEquals(3, usuario.getVasosDeAgua());
	}
	@Test
	public void testSetVasosDeAgua() {
		usuario.setVasosDeAgua(1);
		assertEquals(1, usuario.getVasosDeAgua());
		usuario.setVasosDeAgua(8);
		assertEquals(8, usuario.getVasosDeAgua());
		usuario.setVasosDeAgua(-1);
		assertEquals(8, usuario.getVasosDeAgua());
	}
	@Test
	public void testGetContrasena(){
		assertEquals("juan", usuario.getContrasena());
	}
	@Test
	public void testSetContrasena() {
		usuario.setContrasena("test");
		assertEquals("test", usuario.getContrasena());
		usuario.setContrasena("");
		assertEquals("test", usuario.getContrasena());		
	}
	@Test
	public void testGetFoto(){
		assertEquals(new ImageIcon("resources\\images\\foto.png").getImage(), usuario.getFoto().getImage());
	}
	@Test
	public void testSetFoto() {
		usuario.setFoto(new ImageIcon("resources\\images\\calories.png"));
		assertEquals(new ImageIcon("resources\\images\\calories.png").getImage(), usuario.getFoto().getImage());	
	}
	@Test
	public void testGetPermiso(){
		assertEquals(TipoPermiso.ADMINISTRADOR, usuario.getPermiso());
	}
	@Test
	public void testSetPermiso() {
		usuario.setPermiso(TipoPermiso.USUARIO);
		assertEquals(TipoPermiso.USUARIO, usuario.getPermiso());	
	}
	@Test
	public void testGetRegistroEntrenamiento() {
		assertEquals(Arrays.asList(e), usuario.getRegistroEntrenamiento());
	}
	
	@Test
	public void testSetRegistroEntrenamiento() {
		usuario.setRegistroEntrenamiento(new ArrayList<Entrenamiento>());
		assertEquals(new ArrayList<Entrenamiento>(), usuario.getRegistroEntrenamiento());
	}
	
	
	@Test
	public void testToString() {
		assertEquals("Usuario [nombre=Juan, nombreUsuario=juan_perez, apellido1=Perez, apellido2=Carbon, fechaNacimiento=2004-06-10, sexo=HOMBRE, altura=1.75, peso=75, alergias=[HUEVOS], correoElectronico=juan@gmail.com, enfermedades=[CARDIOVASCULARES], imc=24.489795918367346, caloriasGastadas=12300, rachaEntrenamiento=, tiempoEntrenado=300, ultimaVezEntreno=2024-01-14, caloriasConsumidas=13000, proximaComida={}, vasosDeAgua=3, contrasena=juan, foto=resources\\images\\foto.png, permiso=ADMINISTRADOR, registroEntrenamiento=[Entrenamiento [nombre=test, tipoEntrenamiento=INFERIOR, dificultad=DIFICIL, tiempo=3, descripcion=test descripcion, calorias=10, series=5, repeticiones=3]]]", usuario.toString());	
	} 
	
	
	
}
