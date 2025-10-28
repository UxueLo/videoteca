package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import videoteca.domain.EdadRecomendada;
import videoteca.domain.Genero;
import videoteca.domain.Pelicula;

public class VentanaPelicula extends JFrame {

	private Pelicula p;
	private JPanel panelPrincipal;	// Panel principal, en el centro pondremos toda la info de la pelicula
	private JPanel panelCentral;
	private JPanel panelSur;		// Panel de comentarios
	private JPanel panelSurSur;		// Panel de botones "alquiler" y "peliculas"
	private JPanel panelOeste;		// Panel de poster y puntuacion media
	private JPanel panelNorte;		// Panel del titulo
	private JButton alquilar;
	private JButton reseñar;
	private JTable comentarios;
	private JLabel labelDirector;
	private JLabel labelReparto;
	private JLabel labelDuracion;
	private JLabel labelGenero;
	private JLabel año;
	private JLabel poster;

	
	public VentanaPelicula (Pelicula p) {
		
		this.p = p;
		
		setTitle("Película");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		
		
		this.panelPrincipal = (JPanel) this.getContentPane();
		panelPrincipal.setBackground(new Color(32,32,32));
		panelPrincipal.setLayout(new BorderLayout());
		setResizable(false);
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		
		
		// Crear el PanelCentral
		this.panelCentral = new JPanel();
		panelCentral.setLayout(new BorderLayout());
		add(panelCentral, BorderLayout.CENTER);
		panelCentral.setBackground(Color.white);
		
		
		
		// Crear el PanelNorte desde el central, donde estara el titulo
		this.panelNorte = new JPanel();
		panelCentral.add(panelNorte, BorderLayout.NORTH);
		JLabel titulo = new JLabel();
		titulo.setText(p.getTitulo());
		titulo.setFont(new Font("Arial", Font.BOLD, 40));
		titulo.setBackground(panelPrincipal.getBackground());
		
		panelNorte.add(titulo);
		
		
		
		// Crear el PanelOeste, donde estara el poster y la puntuacion media
		this.panelOeste = new JPanel();
		panelOeste.setBackground(panelPrincipal.getBackground());
		add(panelOeste, BorderLayout.WEST);
		panelOeste.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));
		this.poster = new JLabel();												
		poster.setOpaque(true);
		poster.setBackground(panelPrincipal.getBackground());
		
		// Colocar el poster
		Image imagenEscalada = p.getPoster().getImage();
		imagenEscalada = imagenEscalada.getScaledInstance(250, 325, Image.SCALE_SMOOTH);
		ImageIcon posterEscalado = new ImageIcon(imagenEscalada);
		poster.setIcon(posterEscalado);
		
		// Puntuacion media
		poster.setText(Double.toString(p.getPuntuacionMedia()));
		poster.setFont(new Font("Arial", Font.BOLD, 30));
		
		// Condicional que pinta el numero de la puntuacion media segun esta
		if (p.getPuntuacionMedia() >= 8) {
			poster.setForeground(Color.green);			// Si la puntuacion mayor o igual a 8, pinta de verde
		} else if(p.getPuntuacionMedia() >= 5) {
			poster.setForeground(Color.yellow);			// Si la puntuacion mayor o igual a 5, pinta de amarillo
		} else {
			poster.setForeground(Color.red);			// Si la puntuacion menor a 5, pinta de rojo
		}

		poster.setHorizontalTextPosition(JLabel.CENTER);
		poster.setVerticalTextPosition(JLabel.BOTTOM);
		
		poster.setOpaque(true);
		panelOeste.add(poster, BorderLayout.CENTER);
		
		
		
		// Crear el PanelSur, donde estaran los comentarios
		this.panelSur = new JPanel();
		panelSur.setLayout(new BorderLayout());
		panelSur.setBackground(panelPrincipal.getBackground());
		add(panelSur, BorderLayout.SOUTH);
		panelSur.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
//		panelSur.setSize(500, 500);
		
		
		
		
		
		// TODO Comentarios
		this.comentarios = new JTable();
		Vector<String> cabezeraComentarios = new Vector<String>(Arrays.asList("VALORACIÓN", "COMENTARIO"));
		comentarios.setSize(10, 10);
		
		// Scroll
		JScrollPane scrollComentarios = new JScrollPane(this.comentarios);
		scrollComentarios.getVerticalScrollBar().setUnitIncrement(16);
		panelSur.add(scrollComentarios, BorderLayout.CENTER);
		
		
		comentarios.setBackground(new Color(212,212,212));
		comentarios.setForeground(panelPrincipal.getBackground());
		
		
		for (int i=0; i<p.getComentarios().keySet().size(); i++) {
			JLabel comentario = new JLabel();
			
			
		}
		
		
		
		
		
		// Crear el PanelSurSur, colocado al sur del PanelSur, donde estaran los botones "alquilar" y "añadir"
		this.panelSurSur = new JPanel();
		panelSurSur.setLayout(new GridLayout(1,2,20,20));
		panelSurSur.setBackground(panelPrincipal.getBackground());
		panelSur.add(panelSurSur, BorderLayout.SOUTH);
		panelSurSur.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		
		
		// Crear ambos botones y ajustar color
		this.alquilar = new JButton("Alquilar");
		this.alquilar.setBackground(new Color(153,0,0));
		this.alquilar.setFont(new Font("Arial", Font.BOLD, 35));
		
		this.reseñar = new JButton("Reseñar");
		this.reseñar.setBackground(new Color(153,0,0));
		this.reseñar.setFont(new Font("Arial", Font.BOLD, 35));
		this.reseñar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Abrir VentanaReseña
				VentanaReseña ventanaReseña = new VentanaReseña(p);
				ventanaReseña.setVisible(true);
			}
		});
		
		panelSurSur.add(alquilar);
		panelSurSur.add(reseñar);
		
		

	}
	
	public void CrearVentana () {
		
		
		
		
		
//		labelDirector = new JLabel("Director: " + p.getDirector());
//		labelReparto = new JLabel("Reparto: " + p.getReparto());
//		labelDuracion = new JLabel("Duración: " + p.getDuracion());
//		labelGenero = new JLabel("Géneros: " + p.getGeneros());
//		año = new JLabel(String.valueOf(p.getAñoLanzamiento()));
		
		
	}
	
	
	
	
	
	
	
}
