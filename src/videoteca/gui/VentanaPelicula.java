package videoteca.gui;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import videoteca.domain.EdadRecomendada;
import videoteca.domain.Genero;
import videoteca.domain.Pelicula;

public class VentanaPelicula extends JFrame {

	private Pelicula p;
	private JButton alquilar;
	private JButton reseñar;
	private JLabel labelDirector;
	private JLabel labelReparto;
	private JLabel labelDuracion;
	private JLabel labelGenero;
	private JLabel año;

	
	public VentanaPelicula (Pelicula p) {
		p = this.p;
		CrearVentana();
	}
	
	public void CrearVentana () {
		
		setTitle("Película");
		setSize(800,500);
		setLocationRelativeTo(null);
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBackground(new Color(32,32,32));
		panelPrincipal.setLayout(null);
		panelPrincipal.setOpaque(true);
		setResizable(false);
		
//		labelDirector = new JLabel("Director: " + p.getDirector());
//		labelReparto = new JLabel("Reparto: " + p.getReparto());
//		labelDuracion = new JLabel("Duración: " + p.getDuracion());
//		labelGenero = new JLabel("Géneros: " + p.getGeneros());
//		año = new JLabel(String.valueOf(p.getAñoLanzamiento()));
		
		
	}
	
	
	
	
	
	
	
}
