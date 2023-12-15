package db;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;

import org.jfree.data.time.TimeSeriesCollection;

import domain.Dieta;
import domain.Entrenamiento;
import domain.TipoAlergias;
import domain.TipoDificultad;
import domain.TipoEnfermedades;
import domain.TipoEntrenamiento;
import domain.TipoPermiso;
import domain.TipoPreferencia;
import domain.TipoSexo;
import domain.Usuario;

public class BaseDeDatos {
	private static Usuario p = new Usuario("Juan", "juan_perez", "Perez", "Carbon", LocalDate.of(2004, 6, 10), TipoSexo.HOMBRE, 1.75, 75, new ArrayList<TipoAlergias>(Arrays.asList(TipoAlergias.HUEVOS, TipoAlergias.GLUTEN)), "juan@gmail.com", new ArrayList<TipoEnfermedades>(Arrays.asList(TipoEnfermedades.CARDIOVASCULARES)), TipoPreferencia.NINGUNA, 12300, 3, "Ninguno", 300, LocalDate.now(), 13000, new HashMap<LocalDate, Dieta>(), 3, "juan", new ImageIcon("resources\\images\\foto.png"), TipoPermiso.ADMINISTRADOR, new ArrayList<Entrenamiento>(), new ArrayList<Dieta>());
	private static List<Usuario> listaUsuarios = new ArrayList<>(Arrays.asList(p));
	
	
	private static Entrenamiento e1 = new Entrenamiento("Abdominales", TipoEntrenamiento.SUPERIOR, TipoDificultad.MEDIO, 30, "Entrenamiento que mejorará los abdominales", 200, 3, 20);
	private static Entrenamiento e2 = new Entrenamiento("Pierna", TipoEntrenamiento.INFERIOR, TipoDificultad.FACIL, 30, "Entrenamiento que mejorará los gemelos", 140, 2, 30);
	private static List<Entrenamiento> listaEntrenamientos = new ArrayList<>(Arrays.asList(e1,e2));

	
	private static List<String> pasosD1 = new ArrayList<>(Arrays.asList("Limpia el arroz", "Cuece el arroz", "Haz el pollo"));
	private static List<String> ingredientesD1 = new ArrayList<>(Arrays.asList("Arroz", "Pollo", "Sal", "Aceite"));
	private static Dieta d1 = new Dieta("Arroz con pollo", 30, TipoDificultad.MEDIO, 300, pasosD1, ingredientesD1, new ArrayList<TipoAlergias>(Arrays.asList(TipoAlergias.GLUTEN, TipoAlergias.SOJA)));


	private static List<String> pasosD2 = new ArrayList<>(Arrays.asList("Cuece los macarrones", "Echales sal", "Echale el tomate", "Frie unas salchichas y echalas en el plato"));	
	private static List<String> ingredientesD2 = new ArrayList<>(Arrays.asList("Macarrones", "Tomate", "Sal", "Salchichas"));
	private static Dieta d2 = new Dieta("Macarrones con tomate y salchichas", 25, TipoDificultad.FACIL, 230, pasosD2, ingredientesD2, new ArrayList<TipoAlergias>());
	
	private static List<Dieta> listaDietas = new ArrayList<>(Arrays.asList(d1, d2));

	public static List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public static void setListaUsuarios(List<Usuario> listaUsuarios) {
		BaseDeDatos.listaUsuarios = listaUsuarios;
	}

	public static List<Entrenamiento> getListaEntrenamientos() {
		return listaEntrenamientos;
	}

	public static void setListaEntrenamientos(List<Entrenamiento> listaEntrenamiento) {
		BaseDeDatos.listaEntrenamientos = listaEntrenamiento;
	}

	public static List<Dieta> getListaDietas() {
		return listaDietas;
	}

	public static void setListaDietas(List<Dieta> listaDietas) {
		BaseDeDatos.listaDietas = listaDietas;
	}

	
	
	
	
}
