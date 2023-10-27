package db;

import java.util.ArrayList;
import java.util.List;

import domain.Dieta;
import domain.Entrenamiento;
import domain.TipoDificultad;
import domain.TipoEntrenamiento;
import domain.Usuario;

public class BaseDeDatos {
	List<Usuario> listaUsuarios = new ArrayList<>();
	List<Entrenamiento> listaEntrenamiento = new ArrayList<>();
	List<Dieta> listaDietas= new ArrayList<>();

	public BaseDeDatos() {
		Entrenamiento e1 = new Entrenamiento("Abdominales", TipoEntrenamiento.SUPERIOR, TipoDificultad.MEDIO, 60, "Entrenamiento que mejorará los abdominalels", 200, 3, 20);
		Entrenamiento e2 = new Entrenamiento("Pierna", TipoEntrenamiento.INFERIOR, TipoDificultad.FACIL, 30, "Entrenamiento que mejorará los gemelos", 140, 2, 30);
	
		listaEntrenamiento.add(e1);
		listaEntrenamiento.add(e2);
		
		List<String> pasosD1 = new ArrayList<>();
		pasosD1.add("Limpia el arroz");
		pasosD1.add("Cuece el arroz");
		pasosD1.add("Haz el pollo");
		
		List<String> ingredientesD1 = new ArrayList<>();
		ingredientesD1.add("Arroz");
		ingredientesD1.add("Pollo");
		ingredientesD1.add("Sal");
		ingredientesD1.add("Aceite");
		
		Dieta d1 = new Dieta("Arroz con pollo", 30, TipoDificultad.MEDIO, 300, pasosD1, ingredientesD1);
	
		
		List<String> pasosD2 = new ArrayList<>();
		pasosD2.add("Cuece los macarrones");
		pasosD2.add("Echales sal");
		pasosD2.add("Echale el tomate");
		pasosD2.add("Frie unas salchichas y echalas en el plato");
		
		
		List<String> ingredientesD2 = new ArrayList<>();
		ingredientesD2.add("Macarrones");
		ingredientesD2.add("Tomate");
		ingredientesD2.add("Sal");
		ingredientesD2.add("Salchichas");
		
		Dieta d2 = new Dieta("Macarrones con tomate y salchichas", 25, TipoDificultad.FACIL, 230, pasosD2, pasosD2);
	}
}
