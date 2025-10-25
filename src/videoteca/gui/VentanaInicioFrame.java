package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import videoteca.domain.Pelicula;
import videoteca.domain.Usuario;

public class VentanaInicioFrame extends JFrame{
	public VentanaInicioFrame(Usuario usuario, ArrayList<Pelicula> peliculas) {
		//Ventana
		setTitle("Ventana de Inicio"); //titulo
		setDefaultCloseOperation(EXIT_ON_CLOSE); //parar al cerrar
		setExtendedState(JFrame.MAXIMIZED_BOTH); //abrirlo en pantalla completa
		setResizable(false); //siempre estara en pantalla completa
		getContentPane().setBackground(new Color(32,32,32)); //cambiar el fondo
		
		//Layouts
		setLayout(new BorderLayout()); // el layout principal sera un BorderLayout
		
		//centro: titulo y peliculas
		JPanel centro= new JPanel();
		centro.setLayout(new BorderLayout());
		centro.setBackground(new Color(32,32,32));
		// CREAR Y AÑADIR EL SCROLLPANE
        JScrollPane scrollPeliculas = new JScrollPane(centro);
        scrollPeliculas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPeliculas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPeliculas.getViewport().setBackground(new Color(32,32,32));
        scrollPeliculas.setBorder(BorderFactory.createEmptyBorder());
        
        add(scrollPeliculas, BorderLayout.CENTER); // Añadir el scrollPane en lugar del panel directamente
			//titulo
		JLabel textoCabeza = new JLabel("Peliculas mejor valoradas", JLabel.CENTER);
		textoCabeza.setForeground(new Color(255,0,0));
		textoCabeza.setFont(new Font("Gabriola", Font.BOLD, 45));
		textoCabeza.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); //crear borde para que no este pegado arriba, ayuda de DeepSeek
		centro.add(textoCabeza, BorderLayout.NORTH);
			//peliculas
		JPanel peliculasPanel= new JPanel();
		centro.add(peliculasPanel, BorderLayout.CENTER);
		peliculasPanel.setLayout(new FlowLayout());
		peliculasPanel.setBackground(new Color(32,32,32));
		ArrayList<Pelicula> Top10= getTopPeliculas(peliculas);
		setTop10(Top10, peliculasPanel);
		centro.add(peliculasPanel, BorderLayout.CENTER);
		
		//menu del lado
		JPanel menu = new JPanel();
		menu.setBackground(new Color(153,0,0));
		menu.setPreferredSize(new Dimension(300, 0));
		menu.setLayout(new GridLayout(7,1));
			//parte del usuario
		JLabel perfil= new JLabel();
		perfil.setText("Hola " + usuario.getNombre()+ "!");
		perfil.setFont(new Font("Arial", Font.BOLD, 40));
		perfil.setIcon(new ImageIcon("Images/iconoUsuario.png"));
		perfil.setHorizontalTextPosition(JLabel.RIGHT); 
		perfil.setVerticalTextPosition(JLabel.CENTER);
		menu.add(perfil);
		String [] opciones = {"Iniciar Sesion", "Registrarse", "Catalogo", "Mis reservas", "Prueba Suerte", "Cerrar sesion"};
		for (String opcion:opciones) {
			JLabel campo= new JLabel(opcion);
			campo.setHorizontalAlignment(JLabel.CENTER);
			campo.setFont(new Font("Arial",Font.BOLD, 18));
			menu.add(campo);
		}
		add(menu, BorderLayout.EAST);
		
		setVisible(true); //ventana visible
	}
	
	//encontrar las 10 peliculas con mejor media
	public ArrayList<Pelicula> getTopPeliculas(ArrayList<Pelicula> peliculas){
		ArrayList<Pelicula> mejoresPeliculas= new ArrayList<Pelicula>();
		ArrayList<Pelicula> copiaPeliculas= new ArrayList<Pelicula>(peliculas);
		if (peliculas.size()<=10) {
			return peliculas; //si hay justo 10 o menos, se devuelven las peliculas
		}
		//sino, buscamos las 10 mejores
		//TODO preguntas Generos favoritos en el registro y si hubiera igualdad, mostrar el mas relevante
		//Ordenamos con Collections, para eso creamos un Comparator
		Collections.sort(copiaPeliculas, new Comparator<Pelicula>() {
			@Override
			public int compare(Pelicula p1, Pelicula p2) {
				return -(Double.compare(p1.getPuntuacionMedia(), p2.getPuntuacionMedia()));
			}
		});
		for (int i=0; i<10;i++) {
			mejoresPeliculas.addLast(copiaPeliculas.get(i));
		}
		return mejoresPeliculas;
	}
	
	
	
	
	//mostrar las Top 10 peliculas
	public void setTop10(ArrayList<Pelicula> top10, JPanel panelPeliculas) {
		for (int i=0; i<top10.size();i++) { //por si la lista de peliculas fuera de menos de 10
			JLabel peliculaActual= new JLabel();
			//poner la foto del poster
			ImageIcon posterPelicula= top10.get(i).getPoster();
			peliculaActual.setIcon(posterPelicula);
			peliculaActual.setPreferredSize(new Dimension(600,400));
			
			//poner el numero en el podio alado
			peliculaActual.setText(Integer.toString(i+1));
			peliculaActual.setFont(new Font("Arial", Font.BOLD, 40));
			peliculaActual.setForeground(Color.white);
			peliculaActual.setVerticalTextPosition(JLabel.CENTER);
			peliculaActual.setHorizontalTextPosition(JLabel.LEFT);
			
			panelPeliculas.add(peliculaActual);
		}
	}
	
}
