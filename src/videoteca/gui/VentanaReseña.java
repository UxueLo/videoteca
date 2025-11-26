package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import videoteca.domain.Comentario;
import videoteca.domain.Pelicula;
import videoteca.domain.Usuario;
import videoteca.io.GestionImagenes;

public class VentanaReseña extends JFrame {
	
	private int mouseRowEstrellas;
	private Usuario usuario;
	private JPanel panelPrincipal;	
	private JPanel panelCentral;	// Panel en donde estaran las estrellas para puntuar
	private JPanel panelEstrellas;
	private JPanel panelSur;		// Panel de comentario
	private JPanel panelSurSur;		// Panel del boton "reseñar"
	private JPanel panelOeste;		// Panel de poster y puntuacion media
	private JPanel panelNorte;		// Panel del titulo
	private JButton reseñar;
	private JLabel poster;
	private JTextArea comentarioEscrito;
	
	private int valoracion = -1;
	private ArrayList<JLabel> estrellas = new ArrayList<>();
	private ImageIcon estrellaVacia;
	private ImageIcon estrellaRellena;
	private VentanaPelicula vp;
	
	
	public VentanaReseña (Pelicula p, Usuario usuario,VentanaPelicula vp) {
		this.usuario=usuario;
		this.vp = vp;
		
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
		
		this.panelEstrellas = new JPanel();
		panelEstrellas.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));		// Creo un nuevo panel FlowLayout para agrupar las estrellas en orden de izq a drch
		panelEstrellas.setBackground(panelPrincipal.getBackground());
		
		// Creacion estrellas
		// Estrella vacia
		Image estrellaVaciaEscalada = new ImageIcon("Images/Estrella Vacia.png").getImage();
		estrellaVaciaEscalada = estrellaVaciaEscalada.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
		this.estrellaVacia = new ImageIcon(estrellaVaciaEscalada);
		
		// Estrella rellena
		Image estrellaRellenaEscalada = new ImageIcon("Images/Estrella Rellena.png").getImage();
		estrellaRellenaEscalada = estrellaRellenaEscalada.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
		this.estrellaRellena= new ImageIcon(estrellaRellenaEscalada);
		
		
		// Crear las estrellas y el mouse listener
		for (int e = 0; e < 5; e++) {
			final int indice = e;
			JLabel estrella = new JLabel();
			estrella.setIcon(estrellaVacia);
			estrellas.add(estrella);
			panelEstrellas.add(estrella);
			
			estrella.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseEntered(MouseEvent e) {
					for (int i = 0; i <= indice; i++) {
						estrellas.get(i).setIcon(estrellaRellena);		// pintar todas las estrellas que sean anteriores a esta, inclyendola
					}
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					actualizarEstrellas();		// aunque el raton no este sobre ninguna estrella, mirara si hemos elegido una valoracion y pintara las estrellas menores
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					valoracion = indice + 1;
					actualizarEstrellas();		// entrar a esta funcion de nuevo con la valoracion actualizada, para que las estrellas menores que la valoracion se queden rellenas, las demas siguen con el mismo funcionamiento
				}
				
			});
		}
		
		panelCentral.add(panelEstrellas, BorderLayout.CENTER);
		
		
		
		// Crear el panelNorte donde estara el titulo
		this.panelNorte = new JPanel();
		panelCentral.add(panelNorte, BorderLayout.NORTH);
		JLabel titulo = new JLabel();
		titulo.setText(p.getTitulo());
		titulo.setFont(new Font("Garamond", Font.BOLD, 40));
		titulo.setBackground(panelPrincipal.getBackground());
		titulo.setForeground(new Color(112, 124, 156));
		panelNorte.setBackground(panelPrincipal.getBackground());
		panelNorte.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
		
		panelNorte.add(titulo);
		
		
		
		// Crear el PanelOeste, donde estara el poster
		this.panelOeste = new JPanel();
		panelOeste.setBackground(panelPrincipal.getBackground());
		add(panelOeste, BorderLayout.WEST);
		panelOeste.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));
		this.poster = new JLabel();	
		
		// Cargar y colocar el poster		
		this.poster = GestionImagenes.cargarImagen(p.getTitulo(), 150, 215);
		poster.setBackground(panelPrincipal.getBackground());
		poster.setOpaque(true);
		panelOeste.add(poster, BorderLayout.CENTER);
		
		
		
		// Crear el panelSur
		panelSur = new JPanel();
		panelSur.setLayout(new BorderLayout());
		panelSur.setBackground(panelPrincipal.getBackground());
		add(panelSur, BorderLayout.SOUTH);
		panelSur.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		
		this.comentarioEscrito = new JTextArea(3,1);						// Crear el JTextArea para escribir los comentarios
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
		reseñar.setBackground(new Color(112, 124, 156));
		reseñar.setFont(new Font("Arial", Font.BOLD, 35));
		
		panelSurSur.add(reseñar, BorderLayout.CENTER);
		
		this.reseñar.addActionListener(new ActionListener() {
			
			//TODO comprobar que la sesion esta iniciada
					
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Comprobar que la sesion esta iniciada
				if (!usuario.getNombre().isBlank()) {
					// TODO falta añadir la valoracion real y cambiar la tabla en VentanaPelicula
					
					if (valoracion != -1) {
						ArrayList<Comentario> comentarios = p.getComentarios();
						comentarios.add(new Comentario(valoracion, comentarioEscrito.getText()));
						p.setComentarios(comentarios);
						
						if (vp != null) {
							vp.actualizarComentarios();		// Actualizar la tabla de reseñas en la ventanaPelicula
						}
						
						VentanaReseña.this.dispose();
					} else {
						System.err.println("Valora la película");
					
					}
				
				}else {
					JOptionPane.showMessageDialog(VentanaReseña.this, 
				            "Inicia sesion antes de hacer reseñas", 
				            "Error de autenticación", 
				            JOptionPane.ERROR_MESSAGE);
				    }
				}
				
		});
		
		
		
		
	}	
	
	
	public void actualizarEstrellas() {
		for (int e = 0; e <5; e++) {
			JLabel estrella = estrellas.get(e);
			if (e < valoracion) {						// si la valoracion es mayor o igual a la estrella, se pinta de blanco
				estrella.setIcon(estrellaRellena);
			} else {									// si la valoracion es menor a la estrella, se coloca la vacia
				estrella.setIcon(estrellaVacia);
			}
		}
	}
	
	
}
	
