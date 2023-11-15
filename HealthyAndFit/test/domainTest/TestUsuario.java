package domainTest;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;

import domain.Dieta;
import domain.TipoAlergias;
import domain.TipoEnfermedades;
import domain.TipoPermiso;
import domain.TipoPreferencia;
import domain.TipoSexo;
import domain.Usuario;

public class TestUsuario {
	Usuario usuario;
	Usuario usuarioSinArgs;
	
	@Before
	public void setUp() {
		usuario = new Usuario("Juan", "juan_perez", "Perez", "Carbon", LocalDate.of(2004, 6, 10), TipoSexo.HOMBRE, 1.75, 75, new ArrayList<TipoAlergias>(Arrays.asList(TipoAlergias.HUEVOS)), "juan@gmail.com", new ArrayList<TipoEnfermedades>(Arrays.asList(TipoEnfermedades.CARDIOVASCULARES)), TipoPreferencia.NINGUNA, 12300, 3, "Ninguno", 300, LocalDate.now(), 13000, new Dieta().getNombre(), 3, "juan", new ImageIcon("resources\\images\\foto.png"), TipoPermiso.ADMINISTRADOR);
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
		assertEquals(LocalDate.of(2004, 6, 10), usuario.getfechaNacimiento());
	}
	@Test
	public void testSetFechaNacimiento(){
		usuario.setfechaNacimiento(LocalDate.of(2004, 1, 1));
		assertEquals(LocalDate.of(2004, 1, 1), usuario.getfechaNacimiento());
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
		assertEquals(24.48, usuario.getImc(), 0.001);
	}
	@Test
	public void testGetPreferenciaAlimenticia(){
		assertEquals(TipoPreferencia.NINGUNA, usuario.getPreferenciaAlimenticia());
	}
	@Test
	public void testSetPreferenciaAlimenticia(){
		usuario.setPreferenciaAlimenticia(TipoPreferencia.VEGETARIANO);
		assertEquals(TipoPreferencia.VEGETARIANO, usuario.getPreferenciaAlimenticia());
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
	public void testGetRachaEntrenamiento(){
		assertEquals(3, usuario.getRachaEntrenamiento());
	}
	@Test
	public void testSetRachaEntrenamiento() {
		usuario.setRachaEntrenamiento(0);
		assertEquals(0, usuario.getRachaEntrenamiento());
		usuario.setRachaEntrenamiento(-1);
		assertEquals(0, usuario.getRachaEntrenamiento());
	}
	@Test
	public void testGetObjetivo(){
		assertEquals("Ninguno", usuario.getObjetivo());
	}
	@Test
	public void testSetObjetivo() {
		usuario.setObjetivo("Entrenar");
		assertEquals("Entrenar", usuario.getObjetivo());
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
		assertEquals(new Dieta().getNombre(), usuario.getProximaComida());
	}
	@Test
	public void testSetProximaComida() {
		usuario.setProximaComida("Pimientos");
		assertEquals("Pimientos", usuario.getProximaComida());
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
		assertEquals(1, usuario.getVasosDeAgua());
		usuario.setVasosDeAgua(-1);
		assertEquals(1, usuario.getVasosDeAgua());
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
		assertEquals(new ImageIcon("resources\\images\\foto.png"), usuario.getFoto());
	}
	@Test
	public void testSetFoto() {
		usuario.setFoto(new ImageIcon("resources\\images\\calories.png"));
		assertEquals(new ImageIcon("resources\\images\\calories.png"), usuario.getFoto());	
	}
	@Test
	public void testGetPermiso(){
		assertEquals(TipoPermiso.ADMINISTRADOR, usuario.getPermiso());
	}
	@Test
	public void testSetPermiso() {
		usuario.setPermiso(TipoPermiso.COLABORADOR);
		assertEquals(TipoPermiso.COLABORADOR, usuario.getPermiso());	
	}
	@Test
	public void testToString() {
		assertEquals("Usuario [nombre=Juan, nombreUsuario=juan_perez, apellido1=Perez, apellido2=Carbon, fechaNacimiento=2004-06-10, sexo=HOMBRE, altura=1.75, peso=75, alergias=[HUEVOS], correoElectronico=juan@gmail.com, enfermedades=[CARDIOVASCULARES], imc=24.489795918367346, preferenciaAlimenticia=NINGUNA, caloriasGastadas=12300, rachaEntrenamiento=3, objetivo=Ninguno, tiempoEntrenado=300, ultimaVezEntreno=2023-11-13, caloriasConsumidas=13000, proximaComida=, vasosDeAgua=3, contraseña=juan, foto=resources\\images\\foto.png, permiso=ADMINISTRADOR]", usuario.toString());	
	}
	
	
	
}