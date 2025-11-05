package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import videoteca.domain.Comentario;
import videoteca.domain.Pelicula;

public class VentanaReseña extends JFrame {
	
	private JPanel panelPrincipal;	
	private JPanel panelCentral;	// Panel en donde estaran las estrellas para puntuar
	private JPanel panelSur;		// Panel de comentario
	private JPanel panelSurSur;		// Panel del boton "reseñar"
	private JPanel panelOeste;		// Panel de poster y puntuacion media
	private JPanel panelNorte;		// Panel del titulo
	private JButton reseñar;
	private JLabel poster;
	private JTextArea comentarioEscrito;	
	
	public VentanaReseña (Pelicula p) {
		
		setIconImage(new ImageIcon("Images/logo.png").getImage());
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
		panelCentral.setBackground(panelPrincipal.getBackground());
		add(panelCentral, BorderLayout.CENTER);
		
		JPanel panelEstrellas = new JPanel();
		panelEstrellas.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));		// Creo un nuevo panel FlowLayout para agrupar las estrellas en orden y de izq a drch
		panelEstrellas.setBackground(panelPrincipal.getBackground());
		
		// TODO listener de estrellas
		Image estrellaVaciaEscalada = new ImageIcon("Images/Estrella vacia.png").getImage();
		estrellaVaciaEscalada = estrellaVaciaEscalada.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
		ImageIcon estrellaVacia = new ImageIcon(estrellaVaciaEscalada);
		
		JLabel estrella1 = new JLabel();		// Crear estrella 1
		estrella1.setIcon(estrellaVacia);
		
		JLabel estrella2 = new JLabel();		// Crear estrella 2
		estrella2.setIcon(estrellaVacia);
		
		JLabel estrella3 = new JLabel();		// Crear estrella 3
		estrella3.setIcon(estrellaVacia);
		
		JLabel estrella4 = new JLabel();		// Crear estrella 4
		estrella4.setIcon(estrellaVacia);
		
		JLabel estrella5 = new JLabel();		// Crear estrella 5
		estrella5.setIcon(estrellaVacia);
		
		panelEstrellas.add(estrella1);
		panelEstrellas.add(estrella2);
		panelEstrellas.add(estrella3);
		panelEstrellas.add(estrella4);
		panelEstrellas.add(estrella5);
		
		panelCentral.add(panelEstrellas, BorderLayout.CENTER);
		
		
		// Crear el panelNorte donde estara el titulo
		this.panelNorte = new JPanel();
		panelCentral.add(panelNorte, BorderLayout.NORTH);
		JLabel titulo = new JLabel();
		titulo.setText(p.getTitulo());
		titulo.setFont(new Font("Arial", Font.BOLD, 40));
		titulo.setBackground(panelPrincipal.getBackground());
		titulo.setForeground(Color.white);
		panelNorte.setBackground(panelPrincipal.getBackground());
		panelNorte.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		
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
		imagenEscalada = imagenEscalada.getScaledInstance(150, 175, Image.SCALE_SMOOTH);
		ImageIcon posterEscalado = new ImageIcon(imagenEscalada);
		poster.setIcon(posterEscalado);
		poster.setOpaque(true);
		panelOeste.add(poster, BorderLayout.CENTER);
		
		
		
		// Crear el panelSur
		panelSur = new JPanel();
		panelSur.setLayout(new BorderLayout());
		panelSur.setBackground(panelPrincipal.getBackground());
		add(panelSur, BorderLayout.SOUTH);
		panelSur.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		
		this.comentarioEscrito = new JTextArea(5,1);						// Crear el JTextArea para escribir los comentarios
		comentarioEscrito.setLineWrap(true);								// Hacer que salte a la proxima linea automaticamente
		comentarioEscrito.setFont(new Font("Arial", Font.PLAIN, 20));
		comentarioEscrito.setBackground(panelPrincipal.getBackground());
		comentarioEscrito.setForeground(Color.white);
		JScrollPane scrollPane = new JScrollPane(comentarioEscrito);		// Crear un scrollPane para el comentario
		panelSur.add(scrollPane);
		
		
		
		// Crear el panelSurSur
		panelSurSur = new JPanel();
		panelSurSur.setLayout(new BorderLayout());
		panelSurSur.setBackground(panelPrincipal.getBackground());
		panelSur.add(panelSurSur, BorderLayout.SOUTH);
		panelSurSur.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		
		this.reseñar = new JButton("Reseñar");			// Crear el boton de "reseñar"
		reseñar.setBackground(new Color(153,0,0));
		reseñar.setSize(500,30);
		reseñar.setFont(new Font("Arial", Font.BOLD, 25));
		
		panelSurSur.add(reseñar, BorderLayout.CENTER);
		
		this.reseñar.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO falta añadir la valoracion real y cambiar la tabla en VentanaPelicula
				ArrayList<Comentario> comentarios = p.getComentarios();
				comentarios.add(new Comentario(1, comentarioEscrito.getText()));
				p.setComentarios(comentarios);
				
				VentanaReseña.this.dispose();
				
				
			}
		});
		
		
		
		
	}	
	
}
	
