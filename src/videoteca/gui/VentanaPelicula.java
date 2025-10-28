package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

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
		this.p = p;
		JPanel panelPrincipal= (JPanel) this.getContentPane();
		panelPrincipal.setBackground(new Color(32,32,32));
		panelPrincipal.setLayout(new BorderLayout());
		setResizable(false);
		
		// Crear el PanelSur, donde estaran los comentarios
		this.panelSur = new JPanel();
		panelSur.setLayout(new BorderLayout());
		panelSur.setBackground(new Color(32,32,32));
		add(panelSur, BorderLayout.SOUTH);
		
		JLabel label = new JLabel("hola mundo");
		panelSur.add(label, BorderLayout.CENTER);
		
		// Crear el PanelSurSur, colocado al sur del PanelSur, donde estaran los botones "alquilar" y "añadir"
		this.panelSurSur = new JPanel();
		panelSurSur.setLayout(new GridLayout(1,2,20,20));
		panelSurSur.setBackground(new Color(32,32,32));
		panelSur.add(panelSurSur, BorderLayout.SOUTH);
		panelSurSur.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		
		// Crear ambos botoes y ajustar color
		this.alquilar = new JButton("alquilar");
		this.alquilar.setBackground(new Color(153,0,0));
		this.alquilar.setFont(new Font("Arial", Font.BOLD, 25));
		
		this.reseñar = new JButton("reseñar");
		this.reseñar.setBackground(new Color(153,0,0));
		this.reseñar.setFont(new Font("Arial", Font.BOLD, 25));
		
		
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
