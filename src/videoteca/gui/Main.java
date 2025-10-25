package videoteca.gui;

import java.time.LocalDate;

import videoteca.domain.Usuario;

public class Main {

	public static void main(String[] args) {
		//Prueba del Frame Principal
		Usuario usuario= new Usuario("Nerea", "nerea.f.m@opendeusto.es", 19, LocalDate.of(1, 1, 1), "1234");
		VentanaInicioFrame framePrincipal= new VentanaInicioFrame(usuario);
		
		//Para ir probando los JFrames
//		UsuarioFrame frame = new UsuarioFrame(usuario);
//		frame.setVisible(true);
		
	}
	
}
