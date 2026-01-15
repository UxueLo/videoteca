package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.awt.Cursor;

import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import videoteca.domain.Pelicula;
import videoteca.domain.Usuario;
import videoteca.io.GestionImagenes;

public class VentanaInicioFrame extends JFrame{
	public Usuario usuario;
	private ArrayList<Pelicula> peliculas;
	private JPanel centro;
	private Color colorPrincipal= new Color(111, 130, 179); //cambiar en el codigo
	private Color colorFondo= new Color(35, 35, 35);
	private JScrollPane scrollCentro;
	private JLabel textoCabeza;
	private JPanel peliculasPanel;
	private JPanel menu;
	private JButton botonReanudar;
	private JButton botonParar;
	private JPanel panelPosterDinamico;
	private hiloMuestraPeliculas hilo;
	boolean pausado=false;
	private JPanel panelIncial;
	
	public VentanaInicioFrame(Usuario usuario, ArrayList<Pelicula> peliculas) {
	    // Asegurarse de que el usuario no sea null
	    if (usuario == null) {
	        this.usuario = new Usuario("", "", 0, null, "");
	    } else {
	        this.usuario = usuario;
	    }
	    this.peliculas = peliculas;
		
		//Ventana
		setTitle("Ventana de Inicio"); //titulo
		setDefaultCloseOperation(EXIT_ON_CLOSE); //parar al cerrar
		setExtendedState(JFrame.MAXIMIZED_BOTH); //abrirlo en pantalla completa
		setResizable(false); //siempre estara en pantalla completa
		getContentPane().setBackground(colorFondo); //cambiar el fondo
		setIconImage(new ImageIcon("Images/logo.png").getImage());
		
		//Layouts
		setLayout(new BorderLayout()); // el layout principal sera un BorderLayout
		
		
		panelIncial= new JPanel(new BorderLayout());
		// Panel superior: título + poster + botones
		JPanel panelPeliculasCambiando = new JPanel(new BorderLayout());
		panelPeliculasCambiando.setBackground(colorFondo);
		
        JLabel tituloPoster = new JLabel("Películas destacada", JLabel.CENTER);
        tituloPoster.setFont(new Font("Garamond", Font.BOLD, 45));
        tituloPoster.setForeground(colorPrincipal);
        panelPeliculasCambiando.add(tituloPoster, BorderLayout.NORTH);
        
        panelPosterDinamico = new JPanel(new BorderLayout());
        panelPosterDinamico.setOpaque(true);
        panelPosterDinamico.setBackground(colorFondo);
        panelPeliculasCambiando.add(panelPosterDinamico, BorderLayout.CENTER);

		botonReanudar= new JButton("Reanudar");
		botonReanudar.setEnabled(false);
		botonReanudar.addActionListener(evt ->{
			botonReanudar.setEnabled(false);
			botonParar.setEnabled(true);
			pausado=false;
		});
		
		botonParar = new JButton("Parar");
		botonParar.addActionListener(evt ->{
		    botonParar.setEnabled(false);
		    botonReanudar.setEnabled(true);
		    pausado=true;
		});
		
		JPanel botones = new JPanel();
        botones.setBackground(new Color(20, 20, 20)); 
		botones.add(botonReanudar);
		botones.add(botonParar);
		panelPeliculasCambiando.add(botones, BorderLayout.SOUTH);
		
		// Hilo para mostrar posters
        hilo = new hiloMuestraPeliculas(peliculas, panelPosterDinamico);
        hilo.start();
        
		add(panelPeliculasCambiando, BorderLayout.NORTH);
		panelIncial.add(panelPeliculasCambiando, BorderLayout.NORTH);
		
		
		
		//centro: titulo y peliculas
		centro= new JPanel();
		centro.setLayout(new BorderLayout());
		centro.setBackground(colorFondo);
		//Insertar el centro en un ScrollPane
		scrollCentro= new JScrollPane(centro);
		scrollCentro.getVerticalScrollBar().setUnitIncrement(16);
		add(scrollCentro, BorderLayout.CENTER);
		
        
		//titulo
		textoCabeza = new JLabel("Películas mejor valoradas", JLabel.CENTER);
		textoCabeza.setForeground(colorPrincipal);
		textoCabeza.setFont(new Font("Garamond", Font.BOLD, 45));
		textoCabeza.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); 
		textoCabeza.setOpaque(true);
		textoCabeza.setBackground(colorFondo);
		
		//peliculas
		JPanel panelPeliculasTitulo= new JPanel(new BorderLayout());
		panelPeliculasTitulo.add(textoCabeza, BorderLayout.NORTH);
		peliculasPanel= new JPanel();
		peliculasPanel.setLayout(new GridLayout(5,2, 100, 100));
		peliculasPanel.setBorder(BorderFactory.createEmptyBorder(0,0, 50, 0));
		peliculasPanel.setBackground(colorFondo);
		ArrayList<Pelicula> Top10= getTopPeliculas(peliculas);
		setTop10(Top10, peliculasPanel);
		panelPeliculasTitulo.add(peliculasPanel, BorderLayout.CENTER);
		panelIncial.add(panelPeliculasTitulo, BorderLayout.CENTER);
		centro.add(panelIncial, BorderLayout.CENTER);
		
		//menu lateral
		menu = new JPanel();
		menu.setBackground(new Color(103, 114, 135));
		menu.setPreferredSize(new Dimension(300, 0));
		menu.setLayout(new GridLayout(8,1, 30, 30));
		
		String [] opciones = {"Usuario","Iniciar Sesion", "Registrarse", "Catálogo", "Carrito", "Prueba Suerte", "Cerrar sesion"};
		for (String opcion:opciones) {
			JLabel campo= new JLabel();
			//si es el usuario: foto y nombre
			if (opcion.equals("Usuario")) {
				if (usuario.getNombre()=="") {
					campo.setText("Inicia sesion o Registrate");
				}else {
					campo.setText("Hola " + usuario.getNombre()+ "!");
				}
				campo.setFont(new Font("Arial", Font.BOLD, 30));
				campo.setIcon(new ImageIcon("Images/iconoUsuario.png")); //TODO la foto del perfil
				campo.setHorizontalTextPosition(JLabel.RIGHT); 
				campo.setVerticalTextPosition(JLabel.CENTER);
			}else { //sino, solo el texto
				campo.setText(opcion);
				campo.setHorizontalAlignment(JLabel.CENTER);
				campo.setFont(new Font("Arial",Font.PLAIN, 18));
			}
			
			campo.addMouseListener(new MouseListener() {
                @Override public void mouseReleased(MouseEvent e) {}
                @Override public void mouseClicked(MouseEvent e) {}
                @Override public void mouseExited(MouseEvent e) { if (!opcion.equals("Usuario")) campo.setFont(new Font("Arial", Font.PLAIN, 18)); }
                @Override public void mouseEntered(MouseEvent e) { if (!opcion.equals("Usuario")) campo.setFont(new Font("Arial", Font.BOLD, 23)); }

                @Override
                public void mousePressed(MouseEvent e) {
                    switch (opcion) {
                        case "Iniciar Sesion":
                            new LoginFrame(VentanaInicioFrame.this).setVisible(true);
                            break;
                        case "Registrarse":
                            new RegistrarFrame(VentanaInicioFrame.this).setVisible(true);
                            break;
                        case "Catálogo":
                            new VentanaCatalogo(VentanaInicioFrame.this, peliculas).setVisible(true);
                            break;
                        case "Usuario":
                        case "Carrito":
                        case "Prueba Suerte":
                            // Requieren usuario - VERIFICACIÓN CORREGIDA
                            if (!estaUsuarioAutenticado()) {
                                JOptionPane.showMessageDialog(
                                    VentanaInicioFrame.this,
                                    "¡Tienes que iniciar sesión para acceder a esta opción!",
                                    "Usuario necesario",
                                    JOptionPane.INFORMATION_MESSAGE
                                );
                            } else {
                                if (opcion.equals("Usuario")) 
                                    new UsuarioFrame(VentanaInicioFrame.this).setVisible(true);
                                else if (opcion.equals("Carrito")) 
                                    new VentanaCarrito(VentanaInicioFrame.this).setVisible(true);
                                else if (opcion.equals("Prueba Suerte")) 
                                    new VentanaPrevioJuego(VentanaInicioFrame.this).setVisible(true);
                            }
                            break;
                        case "Cerrar sesion":
                            cerrarSesion();
                            break;
                    }
                }
            });
            menu.add(campo);
        }
        add(menu, BorderLayout.EAST);

        setVisible(true);
    }
	
	private void cerrarSesion() {
	    if (usuario != null && !usuario.getNombre().trim().isEmpty()) {
	        int respuesta = JOptionPane.showConfirmDialog(
	            this,
	            "¿Estás seguro de que quieres cerrar sesión?",
	            "Cerrar sesión",
	            JOptionPane.YES_NO_OPTION
	        );
	        
	        if (respuesta == JOptionPane.YES_OPTION) {
	            // Crear un usuario "vacío"
	            this.usuario = new Usuario("", "", 0, null, "");
	            actualizarUsuario(this.usuario);
	            JOptionPane.showMessageDialog(
	                this,
	                "Sesión cerrada correctamente",
	                "Sesión finalizada",
	                JOptionPane.INFORMATION_MESSAGE
	            );
	        }
	    } else {
	        JOptionPane.showMessageDialog(
	            this,
	            "No hay ninguna sesión activa",
	            "Información",
	            JOptionPane.INFORMATION_MESSAGE
	        );
	    }
	}	
	
	private boolean estaUsuarioAutenticado() {
	    return usuario != null && 
	           usuario.getNombre() != null && 
	           !usuario.getNombre().trim().isEmpty() &&
	           !usuario.getNombre().equals("");
	}

	protected void actualizarUsuario(Usuario nuevoUsuario) {
	    this.usuario = nuevoUsuario;
	    
	    // Cambiar texto en el Label
	    JLabel userLabel = (JLabel) menu.getComponent(0); // El primer elemento es el usuario
	    
	    if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
	        userLabel.setText("Inicia sesion o Registrate");
	    } else {
	        userLabel.setText("Hola " + usuario.getNombre() + "!");
	    }
	    
	    // Actualizar icono si es necesario
	    userLabel.setIcon(new ImageIcon("Images/iconoUsuario.png"));
	    userLabel.setHorizontalTextPosition(JLabel.RIGHT); 
	    userLabel.setVerticalTextPosition(JLabel.CENTER);
	    
	    menu.revalidate();
	    menu.repaint();
	}

	//encontrar las 10 peliculas con mejor media
	public ArrayList<Pelicula> getTopPeliculas(ArrayList<Pelicula> peliculas){
		ArrayList<Pelicula> mejoresPeliculas= new ArrayList<Pelicula>();
		ArrayList<Pelicula> copiaPeliculas= new ArrayList<Pelicula>(peliculas);
		if (peliculas.size()<=10) {
			return peliculas; //si hay justo 10 o menos, se devuelven las peliculas
		}
		//sino, buscamos las 10 mejores
		Collections.sort(copiaPeliculas, (p1, p2) -> -Double.compare(p1.getPuntuacionMedia(), p2.getPuntuacionMedia()));
        for (int i = 0; i < 10; i++) 
            mejoresPeliculas.add(copiaPeliculas.get(i)); 
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
	                VentanaPelicula vp= new VentanaPelicula(pel, usuario, VentanaInicioFrame.this);
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
	
	
	
	public class hiloMuestraPeliculas extends Thread {
        private final ArrayList<Pelicula> peliculas;
        private final JPanel panelPeliculas;
        private final int ancho = 250;
        private final int alto = 350;

        public hiloMuestraPeliculas(ArrayList<Pelicula> peliculas, JPanel panelPeliculas) {
            this.peliculas = peliculas;
            this.panelPeliculas = panelPeliculas;
            panelPeliculas.setOpaque(true);
            panelPeliculas.setBackground(new Color(20, 20, 20));
        }

        @Override
        public void run() {
            Random rand = new Random();
            while (true) {
                if (!pausado && !peliculas.isEmpty()) {
                    int indice = rand.nextInt(peliculas.size());
                    Pelicula pelicula = peliculas.get(indice);

                    JLabel posterLabel = GestionImagenes.cargarImagen(pelicula.getTitulo(), ancho, alto);

                    SwingUtilities.invokeLater(() -> {
                        // Limpiar el panel
                        panelPeliculas.removeAll();
                        
                        // Crear label para el título
                        JLabel tituloLabel = new JLabel(pelicula.getTitulo(), JLabel.CENTER);
                        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
                        tituloLabel.setForeground(Color.white);
                        tituloLabel.setBackground(new Color(20, 20, 20));
                        tituloLabel.setOpaque(true);
                        tituloLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
                        
                        // Label para el poster
                        JLabel posterContainer = new JLabel();
                        posterContainer.setIcon(posterLabel.getIcon());
                        posterContainer.setHorizontalAlignment(JLabel.CENTER);
                        posterContainer.setBackground(new Color(20, 20, 20));
                        posterContainer.setOpaque(true);
                        
                        // Añadir componentes al panel
                        panelPeliculas.add(tituloLabel, BorderLayout.NORTH);
                        panelPeliculas.add(posterContainer, BorderLayout.CENTER);
                        
                        panelPeliculas.revalidate();
                        panelPeliculas.repaint();
                        
                        // Configurar el cursor y eventos del panel completo
                        panelPeliculas.setCursor(new Cursor(Cursor.HAND_CURSOR));

                        // Limpiar listeners anteriores
                        for (MouseListener ml : panelPeliculas.getMouseListeners()) {
                            panelPeliculas.removeMouseListener(ml);
                        }

                        // Añadir nuevo listener
                        panelPeliculas.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                new VentanaPelicula(pelicula, usuario, VentanaInicioFrame.this).setVisible(true);
                            }
                        });
                    });

                    try {
                        Thread.sleep(3000 + rand.nextInt(2000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
