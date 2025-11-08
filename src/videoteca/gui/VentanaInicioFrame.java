package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import videoteca.io.GestionImagenes;

public class VentanaInicioFrame extends JFrame{
	private Usuario usuario;
	private ArrayList<Pelicula> peliculas;
	private JPanel centro;
	private Color colorPrincipal= new Color(255,0,0); //cambiar en el codigo
	private Color colorFondo= new Color(35, 35, 35);
	private JScrollPane scrollCentro;
	private JLabel textoCabeza;
	private JPanel peliculasPanel;
	private JPanel menu;
	
	public VentanaInicioFrame(Usuario usuario, ArrayList<Pelicula> peliculas) {
		this.usuario=usuario;
		this.peliculas=peliculas;
		//Ventana
		setTitle("Ventana de Inicio"); //titulo
		setDefaultCloseOperation(EXIT_ON_CLOSE); //parar al cerrar
		setExtendedState(JFrame.MAXIMIZED_BOTH); //abrirlo en pantalla completa
		setResizable(false); //siempre estara en pantalla completa
		getContentPane().setBackground(colorFondo); //cambiar el fondo
		setIconImage(new ImageIcon("Images/logo.png").getImage());
		
		//Layouts
		setLayout(new BorderLayout()); // el layout principal sera un BorderLayout
		
		//centro: titulo y peliculas
		centro= new JPanel();
		centro.setLayout(new BorderLayout());
		centro.setBackground(colorFondo);
		//Insertar el centro en un ScrollPane
		scrollCentro= new JScrollPane(centro);
		scrollCentro.getVerticalScrollBar().setUnitIncrement(16);
		add(scrollCentro, BorderLayout.CENTER);
		
        
			//titulo
		textoCabeza = new JLabel("Peliculas mejor valoradas", JLabel.CENTER);
		textoCabeza.setForeground(colorPrincipal);
		textoCabeza.setFont(new Font("Garamond", Font.BOLD, 45));
		textoCabeza.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); //IAG
		centro.add(textoCabeza, BorderLayout.NORTH);
			//peliculas
		peliculasPanel= new JPanel();
		peliculasPanel.setLayout(new GridLayout(5,2, 100, 100));
		peliculasPanel.setBorder(BorderFactory.createEmptyBorder(0,0, 50, 0));
		peliculasPanel.setBackground(colorFondo);
		ArrayList<Pelicula> Top10= getTopPeliculas(peliculas);
		setTop10(Top10, peliculasPanel);
		centro.add(peliculasPanel, BorderLayout.CENTER);
		
		//menu del lado
		menu = new JPanel();
		menu.setBackground(colorPrincipal);
		menu.setPreferredSize(new Dimension(300, 0));
		menu.setLayout(new GridLayout(8,1, 30, 30));
		
		String [] opciones = {"Usuario","Iniciar Sesion", "Registrarse", "Catalogo", "Carrito", "Prueba Suerte", "Cerrar sesion"};
		for (String opcion:opciones) {
			JLabel campo= new JLabel();
			//si es el usuario: foto y nombew
			if (opcion=="Usuario") {
				campo.setText("Hola " + usuario.getNombre()+ "!");
				campo.setFont(new Font("Arial", Font.BOLD, 40));
				campo.setIcon(new ImageIcon("Images/iconoUsuario.png")); //TODO la foto del perfil
				campo.setHorizontalTextPosition(JLabel.RIGHT); 
				campo.setVerticalTextPosition(JLabel.CENTER);
			}else { //sino, solo el texto
				campo.setText(opcion);
				campo.setHorizontalAlignment(JLabel.CENTER);
				campo.setFont(new Font("Arial",Font.PLAIN, 18));
			}
			
			campo.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// nada
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					if (opcion=="Iniciar Sesion") {
						LoginFrame logIn= new LoginFrame();
						logIn.setVisible(true);
					}else if(opcion=="Registrarse") {
						RegistrarFrame reg= new RegistrarFrame();
						reg.setVisible(true);
					}else if (opcion=="Catalogo") {
						VentanaCatalogo venCat= new VentanaCatalogo(usuario, peliculas);
						venCat.setVisible(true);
					}else if (opcion=="Usuario") {
						UsuarioFrame us= new UsuarioFrame(usuario);
						us.setVisible(true);
					}else if (opcion=="Carrito") {
						VentanaCarrito vcr= new VentanaCarrito(usuario);
						vcr.setVisible(true);
					}
					//TODO implementar el cerrar sesion y el Prueba Suerte
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					if (opcion!="Usuario") {
						campo.setFont(new Font("Arial",Font.PLAIN, 18));
					}
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					if (opcion!="Usuario") {
						campo.setFont(new Font("Arial",Font.BOLD, 23));
					}
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// nada
					
				}
			});
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
	    for (int i=0; i<top10.size();i++) {
	        JLabel peliculaActual= new JLabel();
	        //poner la foto del poster
	        Pelicula pel= top10.get(i);
	        ImageIcon posterPelicula= pel.getPoster();
	        
	        // Crear el poster escalado original una sola vez
	        JLabel imagenPoster = GestionImagenes.cargarImagen(pel.getTitulo(), 200, 300);
            peliculaActual.setIcon(imagenPoster.getIcon());
            peliculaActual.setHorizontalAlignment(JLabel.CENTER);
	        
	        peliculaActual.addMouseListener(new MouseListener() {

	            @Override
	            public void mouseReleased(MouseEvent e) {
	                // nada
	            }

	            @Override
	            public void mousePressed(MouseEvent e) {
	                VentanaPelicula vp= new VentanaPelicula(pel, usuario);
	                vp.setVisible(true);
	            }

	            @Override
	            public void mouseExited(MouseEvent e) {
	            	peliculaActual.setForeground(Color.white);
	            }

	            @Override
	            public void mouseEntered(MouseEvent e) {
	            	peliculaActual.setForeground(Color.gray);
	            }

	            @Override
	            public void mouseClicked(MouseEvent e) {
	                // nada
	            }
	        });
	        
	        //poner el numero en el podio alado
	        peliculaActual.setText(Integer.toString(i+1));
	        peliculaActual.setFont(new Font("Arial", Font.BOLD, 60));
	        peliculaActual.setForeground(Color.white);
	        peliculaActual.setVerticalTextPosition(JLabel.BOTTOM);
	        peliculaActual.setHorizontalTextPosition(JLabel.LEFT);

	        panelPeliculas.add(peliculaActual);
	    }
	}
	
}
