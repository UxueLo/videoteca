package videoteca.gui;

import java.time.LocalDate;

import videoteca.domain.Usuario;

public class Main {

	public static void main(String[] args) {
		//Para ir probando los JFrames
		Usuario usuario= new Usuario("Nerea", "nerea.f.m@opendeusto.es", 19, LocalDate.of(1, 1, 1), "1234");
		UsuarioFrame frame = new UsuarioFrame(usuario);
		frame.setVisible(true);
	}
	
}
