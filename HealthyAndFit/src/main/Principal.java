package main;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import domain.Dieta;
import domain.TipoAlergias;
import domain.TipoEnfermedades;
import domain.TipoPreferencia;
import domain.TipoSexo;
import domain.Usuario;
import gui.VentanaResumen;

public class Principal {
	
	Usuario persona;
	public static void main(String[] args) {
		Usuario p = new Usuario("Juan", "juan_perez", "Perez", "Carbon", new Date(), TipoSexo.HOMBRE, 1.75, 75, new ArrayList<TipoAlergias>(), "juan@gmail.com", new ArrayList<TipoEnfermedades>(), TipoPreferencia.NINGUNA, 12300, 3, "Ninguno", 300, new Date(), 13000, new Dieta().getNombre(), 3, "asd", new ImageIcon("resources\\images\\foto.png"));
		SwingUtilities.invokeLater(()-> new VentanaResumen(p));
	}
}
