package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import videoteca.domain.EdadRecomendada;
import videoteca.domain.Genero;
import videoteca.domain.Pelicula;
import videoteca.domain.Usuario;
import videoteca.io.GestionImagenes;

public class MaratonFrame extends JFrame{
	private Usuario usuario;
	private ArrayList<Pelicula> peliculas;
	private Color colorPrincipal= new Color(111, 130, 179); 
	private Color colorFondo= new Color(35, 35, 35);
	private JPanel centro;
	private JLabel tituloPagina;
	private JPanel panelFiltros;
	private JComboBox<Integer> horasMin;
	private JComboBox<Integer> horasMax;
	private JComboBox<EdadRecomendada> edad;
	private ArrayList<JCheckBox> generos;
	private JSlider presupuestoSlider;
	private JLabel presupuestoLabel; 
	
	
	
	public MaratonFrame(VentanaInicioFrame ventanaInicio, ArrayList<Pelicula> peliculas) {
	    this.usuario = ventanaInicio.usuario;
	    this.peliculas = peliculas;
	    
	    // Configuración de ventana
	    setTitle("Manta y Maraton");
	    setDefaultCloseOperation(HIDE_ON_CLOSE);
	    setSize(900, 950);
	    setLocationRelativeTo(null);
	    setResizable(false);
	    this.getContentPane().setBackground(colorFondo);
	    setIconImage(new ImageIcon("Images/logo.png").getImage());
	    setLayout(new BorderLayout());

	    // Título de ventana
	    tituloPagina = new JLabel("Maraton y manta");
	    tituloPagina.setFont(new Font("Arial", Font.BOLD, 30));
	    tituloPagina.setForeground(colorPrincipal);
	    tituloPagina.setHorizontalAlignment(JLabel.CENTER);
	    tituloPagina.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
	    add(tituloPagina, BorderLayout.NORTH);

	    // Panel Centro: se ira repintando para mostrar o los filtros o las combinaciones de peliculas
	    centro = new JPanel(new BorderLayout());
	    centro.setOpaque(true);
	    centro.setBackground(colorFondo);
	    add(centro, BorderLayout.CENTER);

	    // Filtros
	    inicializarVistaFiltros();
	}

	private void inicializarVistaFiltros() {
	    centro.removeAll(); 
	    
	    JPanel contenedorFiltros = new JPanel(new BorderLayout());
	    contenedorFiltros.setBackground(colorFondo);
	    
	    //texto de introduccion
	    JTextArea introduccion = new JTextArea(
	    	    "En esta ventana podrás descubrir cuáles son todas las combinaciones\n" +
	    	    "de maratones que coinciden con lo que buscas! Para ello, solo tienes que introducir\n" +
	    	    "tus preferencias en los siguientes filtros y pulsar el botón."
	    	);
	    introduccion.setEditable(false);
	    introduccion.setBackground(colorFondo);
	    introduccion.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
	    introduccion.setForeground(Color.gray);
	    contenedorFiltros.add(introduccion, BorderLayout.NORTH);

	    // FILTROS
	    panelFiltros = new JPanel(new GridLayout(0, 1, 0, 15));
	    panelFiltros.setBackground(colorFondo);
	    panelFiltros.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));

	    // Generos
	    JPanel panelGeneros = new JPanel(new BorderLayout());
	    panelGeneros.setBackground(colorFondo);
	    JLabel labelGeneros = new JLabel("Géneros: ");
	    labelGeneros.setForeground(Color.WHITE);
	    labelGeneros.setPreferredSize(new Dimension(120, 20));
	    JPanel panelOpciones = new JPanel(new GridLayout(0, 3));
	    panelOpciones.setBackground(colorFondo);
	    generos = new ArrayList<>();
	    for (Genero g : Genero.values()) {
	        JCheckBox act = new JCheckBox(g.toString());
	        act.setForeground(Color.WHITE);
	        act.setBackground(colorFondo);
	        act.setFont(new Font("Arial", Font.PLAIN, 10));
	        generos.add(act);
	        panelOpciones.add(act);
	    }
	    panelGeneros.add(labelGeneros, BorderLayout.WEST);
	    panelGeneros.add(panelOpciones, BorderLayout.CENTER);
	    panelFiltros.add(panelGeneros);

	    // Duracion
	    panelFiltros.add(crearFilaFormulario("Duración mínima (h):", horasMin = new JComboBox<>(new Integer[]{1,2,3,4,5,6, 7, 8, 9, 10, 11, 12, 13, 14})));
	    panelFiltros.add(crearFilaFormulario("Duración máxima (h):", horasMax = new JComboBox<>(new Integer[]{1,2,3,4,5,6,7, 8, 9, 10, 11, 12, 13, 14, 15})));
	    horasMax.setSelectedItem(5);
	    panelFiltros.add(crearFilaFormulario("Edad Recomendada:", edad = new JComboBox<>(EdadRecomendada.values())));

	    // FILA PRESUPUESTO: IAg
	    JPanel filaPres = new JPanel(new BorderLayout(10, 0));
	    filaPres.setBackground(colorFondo);
	    presupuestoLabel = new JLabel("Presupuesto: 60€");
	    presupuestoLabel.setForeground(colorPrincipal);
	    presupuestoLabel.setPreferredSize(new Dimension(140, 20));
	    presupuestoSlider = new JSlider(0, 300, 60);
	    presupuestoSlider.setBackground(colorFondo);
	    presupuestoSlider.setPaintTicks(true);
	    presupuestoSlider.setPaintLabels(true);
	    presupuestoSlider.setMajorTickSpacing(100);
	    presupuestoSlider.addChangeListener(e -> presupuestoLabel.setText("Presupuesto: " + presupuestoSlider.getValue() + "€"));
	    
	    filaPres.add(presupuestoLabel, BorderLayout.WEST);
	    filaPres.add(presupuestoSlider, BorderLayout.CENTER);
	    panelFiltros.add(filaPres);

	    contenedorFiltros.add(panelFiltros, BorderLayout.CENTER);
	    centro.add(contenedorFiltros, BorderLayout.CENTER);

	    // BOTÓN 
	    JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    panelBoton.setBackground(colorFondo);
	    panelBoton.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
	    JButton btnGenerar = new JButton("ENCONTRAR MI MARATÓN");
	    btnGenerar.setPreferredSize(new Dimension(300, 45));
	    btnGenerar.setBackground(colorPrincipal);
	    btnGenerar.setForeground(Color.WHITE);
	    btnGenerar.setFont(new Font("Arial", Font.BOLD, 16));
	    
	    // EVENTO DEL BOTÓN
	    btnGenerar.addActionListener(e -> {
	        int min = (int) horasMin.getSelectedItem();
	        int max = (int) horasMax.getSelectedItem();
	        
	        // Corregir automaticamente si el usuario mete la duracion maxima menor que la minima
	        if (min > max) {
	           
	            horasMax.setSelectedItem(min);
	            max = min; 
	            
	            JOptionPane.showMessageDialog(this, "Se ha cambiado la duracion maxima(se ha igualado con la minima) ya que era invalida: era menor que la duracion minima.");
	        }

	     // los generos elegidos
	        ArrayList<Genero> generosElegidos = new ArrayList<>();
	        for (JCheckBox cb : generos) {
	            if (cb.isSelected()) {
	                generosElegidos.add(Genero.valueOf(cb.getText()));
	            }
	        }

	        ArrayList<ArrayList<Pelicula>> resultados = obtenerCombinaciones(min, max, generosElegidos, (EdadRecomendada) edad.getSelectedItem(), presupuestoSlider.getValue());
	       
	        mostrarResultados(resultados);
	    });
	    panelBoton.add(btnGenerar);
	    centro.add(panelBoton, BorderLayout.SOUTH);

	    centro.revalidate();
	    centro.repaint();
	}

	private void mostrarResultados(ArrayList<ArrayList<Pelicula>> combinaciones) {
		centro.removeAll();
	    centro.setLayout(new BorderLayout());

	    // boton para volver a los filtros
	    JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    panelSuperior.setBackground(colorFondo);
	    JButton btnVolver = new JButton(" ← ");
	    btnVolver.setBackground(colorPrincipal);
	    btnVolver.setForeground(Color.WHITE);
	    btnVolver.setFont(new Font("Arial", Font.BOLD, 12));
	    btnVolver.addActionListener(e -> inicializarVistaFiltros()); // Repinta los filtros
	    panelSuperior.add(btnVolver);
	    centro.add(panelSuperior, BorderLayout.NORTH);

	    // Combinaciones
	    JPanel panelLista = new JPanel(new GridLayout(0, 1, 0, 10)); 
	    panelLista.setBackground(colorFondo);
	    //en caso de que no haya combinaciones: mostrar mensaje. Sino: mostrar combinaciones.
	    if (combinaciones.isEmpty()) {
	        JLabel sinResultados = new JLabel("Lo sentimos :(! No hay maratones que cumplan con tus especificaciones.");
	        sinResultados.setForeground(Color.WHITE);
	        sinResultados.setHorizontalAlignment(JLabel.CENTER);
	        panelLista.add(sinResultados);
	    } else {
	        for (ArrayList<Pelicula> maraton : combinaciones) {
	            panelLista.add(crearSeccionMaraton(maraton));
	        }
	    }
	    
	    JPanel contenedorScroll = new JPanel(new BorderLayout());
	    contenedorScroll.setBackground(colorFondo);
	    contenedorScroll.add(panelLista, BorderLayout.NORTH);

	    javax.swing.JScrollPane scroll = new JScrollPane(contenedorScroll);
	    scroll.setBorder(null);
	    scroll.getVerticalScrollBar().setUnitIncrement(16); //IAg: scroll mas suave
	    centro.add(scroll, BorderLayout.CENTER);

	    centro.revalidate();
	    centro.repaint();
	}
	

	private Component crearSeccionMaraton(ArrayList<Pelicula> maraton) {
		JPanel combinacion = new JPanel(new BorderLayout());
	    combinacion.setBackground(new Color(45, 45, 45));
	    combinacion.setBorder(BorderFactory.createCompoundBorder(
	        BorderFactory.createEmptyBorder(5, 10, 5, 10),
	        BorderFactory.createLineBorder(colorPrincipal, 1)
	    ));

	    // Poster
	    JPanel panelPelis = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
	    panelPelis.setBackground(new Color(45, 45, 45));
	    
	    double precioTotal = 0;
	    for (Pelicula p : maraton) {
	        JPanel peliIndividual = new JPanel(new BorderLayout());
	        peliIndividual.setBackground(new Color(45, 45, 45));
	        //poster
	        JLabel lblPoster = GestionImagenes.cargarImagen(p.getTitulo(), 100, 150);
	        lblPoster.setPreferredSize(new Dimension(70, 100));
	        lblPoster.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	        //titulo
	        JLabel lblNombre = new JLabel(p.getTitulo());
	        lblNombre.setForeground(Color.LIGHT_GRAY);
	        lblNombre.setFont(new Font("Arial", Font.BOLD, 10));
	        lblNombre.setHorizontalAlignment(JLabel.CENTER);

	        peliIndividual.add(lblPoster, BorderLayout.CENTER);
	        peliIndividual.add(lblNombre, BorderLayout.SOUTH);
	        
	        panelPelis.add(peliIndividual);
	        precioTotal += p.getPrecio();
	    }

	    //precio
	    JLabel lblPrecio = new JLabel(String.format("%.2f €", precioTotal));
	    lblPrecio.setForeground(colorPrincipal);
	    lblPrecio.setFont(new Font("Arial", Font.BOLD, 20));
	    lblPrecio.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

	    combinacion.add(panelPelis, BorderLayout.CENTER);
	    combinacion.add(lblPrecio, BorderLayout.EAST);

	    return combinacion;
	}

	private ArrayList<ArrayList<Pelicula>> obtenerCombinaciones(int HorasMin, int HorasMax, ArrayList<Genero> generosElegidos,
	        EdadRecomendada edadRecomendada, double presupuesto) {
		
		int minMinutos = HorasMin * 60;
	    int maxMinutos = HorasMax * 60;
	    
	    ArrayList<Pelicula> peliculasFiltradas = new ArrayList<Pelicula>();
	    for (Pelicula p : peliculas) {
	        boolean generoValido = generosElegidos.isEmpty(); 
	        for (Genero g : generosElegidos) {
	            if (p.getGeneros().contains(g)) {
	                generoValido = true;
	                break;
	            }
	        }
	        
	        if (generoValido && p.getEdadRecomendada().ordinal() <= edadRecomendada.ordinal()) {
	            peliculasFiltradas.add(p);
	        }
	    }

	    ArrayList<ArrayList<Pelicula>> combinaciones = new ArrayList<ArrayList<Pelicula>>();
	    ArrayList<Pelicula> aux = new ArrayList<Pelicula>();
	    
	    obtenerCombinacionesAux(minMinutos, maxMinutos, presupuesto, combinaciones, aux, peliculasFiltradas, 0, 0);
	    return combinaciones;
	}


	private void obtenerCombinacionesAux(int HorasMin, int HorasMax, double presupuesto, ArrayList<ArrayList<Pelicula>> combinaciones, 
	        ArrayList<Pelicula> aux, ArrayList<Pelicula> peliculas, int transcurrido, int indice) {
		
		if (combinaciones.size() >= 50) {//para evitar que pete
	        return;
	    }
	    // CASO BASE: el aux es una combinacion valida, lo añadimos al resultado
	    if (transcurrido >= HorasMin && transcurrido <= HorasMax) {
	        combinaciones.add(new ArrayList<>(aux));
	    }

	    //CASO BASE: no es valiudo, nos hemos pasado de presupuesto, indice o tiempo
	    if (transcurrido > HorasMax || presupuesto < 0 || indice >= peliculas.size()) {
	        return;
	    }

	    // RECURSIVIDAD
	    for (int i = indice; i < peliculas.size(); i++) {
	        Pelicula p = peliculas.get(i);
	        
	        if (p.getPrecio() <= presupuesto && (transcurrido + p.getDuracion()) <= HorasMax) {
	            aux.add(p);
	            obtenerCombinacionesAux(HorasMin, HorasMax, presupuesto - p.getPrecio(), 
	                    combinaciones, aux, peliculas, transcurrido + (int)p.getDuracion(), i + 1);
	            
	            aux.remove(aux.size() - 1); 
	        }
	    }
	}
	
// metodo para que los combobox no queden demasiado grandes: IAg
	private JPanel crearFilaFormulario(String texto, JComboBox<?> combo) {
	    JPanel p = new JPanel(new BorderLayout());
	    p.setBackground(colorFondo);
	    JLabel l = new JLabel(texto);
	    l.setForeground(Color.WHITE);
	    l.setPreferredSize(new Dimension(180, 20));
	    
	    JPanel pCombo = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    pCombo.setBackground(colorFondo);
	    combo.setPreferredSize(new Dimension(120, 25));
	    pCombo.add(combo);
	    
	    p.add(l, BorderLayout.WEST);
	    p.add(pCombo, BorderLayout.CENTER);
	    return p;
	}
}