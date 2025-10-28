package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.geom.Dimension2D;
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
	private JPanel panelPrincipal;
	private JPanel panelSur;		// Panel de comentarios
	private JPanel panelSurSur;		// Panel de botones "alquiler" y "peliculas"
	private JPanel panelOeste;		// Panel de poster y puntuacion media
	private JButton alquilar;
	private JButton reseñar;
	private JLabel labelDirector;
	private JLabel labelReparto;
	private JLabel labelDuracion;
	private JLabel labelGenero;
	private JLabel año;

	
	public VentanaPelicula (Pelicula p) {
		p = this.p;
		JPanel panelPrincipal= (JPanel) this.getContentPane();
		panelPrincipal.setBackground(new Color(32,32,32));
		panelPrincipal.setLayout(new BorderLayout());
		setResizable(false);
		
		this.panelSur = new JPanel();
		panelSur.setBackground(Color.CYAN);
		add(panelSur, BorderLayout.SOUTH);
		
		this.panelSurSur = new JPanel();
		panelSurSur.setLayout(new GridLayout(1,2,5,5));
		panelSurSur.setBackground(Color.RED);
		panelSur.add(panelSurSur, BorderLayout.SOUTH);
		
		JLabel label = new JLabel("hola mundo");
		panelSur.add(label, BorderLayout.CENTER);
		
		this.alquilar = new JButton("alquilar");
		alquilar.setSize(500, 20);
		this.reseñar = new JButton("reseñar");
		
		panelSurSur.add(alquilar);
		panelSurSur.add(reseñar);
		
		
		
		CrearVentana();
		

	}
	
	public void CrearVentana () {
		
		setTitle("Película");
		setSize(800,500);
		setLocationRelativeTo(null);
		
		
		
		
//		labelDirector = new JLabel("Director: " + p.getDirector());
//		labelReparto = new JLabel("Reparto: " + p.getReparto());
//		labelDuracion = new JLabel("Duración: " + p.getDuracion());
//		labelGenero = new JLabel("Géneros: " + p.getGeneros());
//		año = new JLabel(String.valueOf(p.getAñoLanzamiento()));
		
		
	}
	
	
	
	
	
	
	
}
