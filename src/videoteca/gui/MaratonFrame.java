package videoteca.gui;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import videoteca.domain.Pelicula;
import videoteca.domain.Usuario;

public class MaratonFrame extends JFrame{
	public Usuario usuario;
	private ArrayList<Pelicula> peliculas;
	private Color colorPrincipal= new Color(111, 130, 179); 
	private Color colorFondo= new Color(35, 35, 35);
	
	
	public MaratonFrame(Usuario usuario, ArrayList<Pelicula> peliculas) {
		 // Asegurarse de que el usuario no sea null
	    if (usuario == null) {
	        this.usuario = new Usuario("", "", 0, null, "");
	    } else {
	        this.usuario = usuario;
	    }
	    this.peliculas = peliculas;
	    
	    //Configuracion de la ventana
	    setTitle("Manta y Maraton");
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setSize(700, 700);
	    setResizable(false);
	    setBackground(colorFondo);
	    setIconImage(new ImageIcon("Images/logo.png").getImage());
	}
}
