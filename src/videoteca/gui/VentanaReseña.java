package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import videoteca.domain.Pelicula;

public class VentanaReseña extends JFrame {
	
	private JPanel panelPrincipal;	// Panel principal, en el centro pondremos toda la info de la pelicula
	private JPanel panelCentral;
	private JPanel panelSur;		// Panel de comentarios y boton de "reseñar"
	private JPanel panelOeste;		// Panel de poster y puntuacion media
	private JPanel panelNorte;		// Panel del titulo
	private JButton reseñar;
	private JLabel poster;
	private JTextField comentarioEscrito;		//TODO
	
	public VentanaReseña (Pelicula p) {
		
		setTitle("Reseña");
		setSize(800,500);
		setResizable(false);
		setLocationRelativeTo(null);
		
		this.panelPrincipal = (JPanel) this.getContentPane();
		panelPrincipal.setBackground(new Color(32,32,32));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		
		
		
		// Crear el panelCentral donde estaran las estrellas para poder puntuar
		panelCentral = new JPanel();
		panelCentral.setLayout(new BorderLayout());
		panelCentral.setBackground(Color.blue);
		add(panelCentral, BorderLayout.CENTER);
		
		// Crear el panelNorte donde estara el titulo
		this.panelNorte = new JPanel();
		panelCentral.add(panelNorte, BorderLayout.NORTH);
		JLabel titulo = new JLabel();
		titulo.setText(p.getTitulo());
		titulo.setFont(new Font("Arial", Font.BOLD, 40));
		titulo.setBackground(panelPrincipal.getBackground());
		
		panelNorte.add(titulo);
		
		
		
		// Crear el PanelOeste, donde estara el poster
		this.panelOeste = new JPanel();
		panelOeste.setBackground(panelPrincipal.getBackground());
		add(panelOeste, BorderLayout.WEST);
		panelOeste.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));
		this.poster = new JLabel();												
		poster.setOpaque(true);
		poster.setBackground(new Color(32,32,32));
		
		// Colocar el poster
		Image imagenEscalada = p.getPoster().getImage();
		imagenEscalada = imagenEscalada.getScaledInstance(150, 200, Image.SCALE_SMOOTH);
		ImageIcon posterEscalado = new ImageIcon(imagenEscalada);
		poster.setIcon(posterEscalado);
		poster.setOpaque(true);
		panelOeste.add(poster, BorderLayout.CENTER);
		
		
		
		// Crear el panelSur
		panelSur = new JPanel();
		panelSur.setLayout(new BorderLayout());
		panelSur.setBackground(Color.red);
		add(panelSur, BorderLayout.SOUTH);
		
		this.reseñar = new JButton("Reseñar");			// Crear el boton de "reseñar"
		reseñar.setBackground(new Color(153,0,0));
		reseñar.setSize(500,30);
		reseñar.setFont(new Font("Arial", Font.BOLD, 25));
		
		this.comentarioEscrito = new JTextField();
		
		panelSur.add(comentarioEscrito, BorderLayout.CENTER);
		panelSur.add(reseñar, BorderLayout.CENTER);
		
		this.reseñar.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO falta añadir el comentario a comentarios
				VentanaReseña.this.dispose();
			}
		});
		
		
		
		
	}	
	
}
	
