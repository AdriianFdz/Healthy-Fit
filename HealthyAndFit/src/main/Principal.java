package main;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import domain.Dieta;
import domain.TipoAlergias;
import domain.TipoEnfermedades;
import domain.TipoPermiso;
import domain.TipoPreferencia;
import domain.TipoSexo;
import domain.Usuario;
import gui.VentanaResumen;

public class Principal {
	
	Usuario persona;
	public static void main(String[] args) {

		//Imagen sacada de www.dazn.com
		Usuario p = new Usuario("Juan", "juan_perez", "Perez", "Carbon", LocalDate.of(2004, 6, 10), TipoSexo.HOMBRE, 1.75, 75, new ArrayList<TipoAlergias>(), "juan@gmail.com", new ArrayList<TipoEnfermedades>(), TipoPreferencia.NINGUNA, 12300, 3, "Ninguno", 300, LocalDate.now(), 13000, new Dieta().getNombre(), 3, "asd", new ImageIcon("resources\\images\\foto.png"), TipoPermiso.ADMINISTRADOR);
		System.out.println(p.getfechaNacimiento().getYear());
		SwingUtilities.invokeLater(()-> new VentanaResumen(p));
		
	}
}
