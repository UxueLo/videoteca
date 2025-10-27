package videoteca.gui;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

import videoteca.domain.EdadRecomendada;
import videoteca.domain.Genero;
import videoteca.domain.Pelicula;

public class VentanaPelicula {

	private Pelicula pelicula;
	private JButton alquilar;
	private JButton reseñar;
	private JLabel labelDirector;
	private JLabel reparto;
	private JLabel repartoPers;
	private JLabel generos;
	private JLabel generosPers;
	private JLabel duracion;

	
	public VentanaPelicula (Pelicula p) {
		p = pelicula;
	}
	
	public void CrearVentana () {
		labelDirector = new JLabel("Director: " + pelicula.getDirector());
	}
	
	
	
	
	
	
	
}
