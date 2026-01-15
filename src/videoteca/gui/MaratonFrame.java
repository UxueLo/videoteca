package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import videoteca.domain.EdadRecomendada;
import videoteca.domain.Genero;
import videoteca.domain.Pelicula;
import videoteca.domain.Usuario;

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
	    setSize(750, 800);
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
	        
	        // Aquí ya lanzas tu lógica sabiendo que min <= max
	        
	    });
	    panelBoton.add(btnGenerar);
	    centro.add(panelBoton, BorderLayout.SOUTH);

	    centro.revalidate();
	    centro.repaint();
	}

	// metodo para que los combobox no queden demasiado grandes
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