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
import javax.swing.JPanel;
import javax.swing.JSlider;
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
		//ASIGNACION DE USUARIO Y PELICULAS POR HACER
	    this.usuario=ventanaInicio.usuario;
	    this.peliculas=peliculas;
	    
	    //Configuracion de la ventana
	    setTitle("Manta y Maraton");
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setSize(700, 700);
	    setLocationRelativeTo(null);
	    setResizable(false);
	    this.getContentPane().setBackground(colorFondo);
	    setIconImage(new ImageIcon("Images/logo.png").getImage());
	    
	    //Layouts principales
	    setLayout(new BorderLayout());
	    //texto de pagina
	    tituloPagina= new JLabel("Maraton y manta");
	    tituloPagina.setFont(new Font("Arial", Font.BOLD, 30));
	    tituloPagina.setForeground(colorPrincipal);
	    tituloPagina.setHorizontalAlignment(JLabel.CENTER);
	    add(tituloPagina, BorderLayout.NORTH);
	    //centro de la ventana
	    centro= new JPanel(new BorderLayout());
	    centro.setOpaque(true);
	    centro.setBackground(Color.black);
	    add(centro, BorderLayout.CENTER);
	    //FILTROS
        panelFiltros = new JPanel(new java.awt.GridLayout(0, 2, 20, 25));
        panelFiltros.setBackground(colorFondo);
        panelFiltros.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // GENEROS 
        JPanel panelGeneros = new JPanel(new GridLayout(0, 2));
        panelGeneros.setBackground(colorFondo);
        generos = new ArrayList<>();
        Font fuenteChecks = new Font("Arial", Font.PLAIN, 10);
        for (Genero g : Genero.values()) {
            JCheckBox act = new JCheckBox(g.toString());
            act.setForeground(Color.WHITE);
            act.setBackground(colorFondo);
            act.setFont(fuenteChecks);
            generos.add(act);
            panelGeneros.add(act);
        }
        JLabel labelGeneros=new JLabel("Generos: ");
        labelGeneros.setForeground(Color.white);
        panelFiltros.add(labelGeneros);
        panelFiltros.add(panelGeneros);

        // TIEMPO
        Integer[] opcionesHoras = {1, 2, 3, 4, 5, 6, 7, 8, 10, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        horasMin = new JComboBox<>(opcionesHoras);
        horasMax = new JComboBox<>(opcionesHoras);
        horasMax.setSelectedItem(5);
        
        Dimension dimCombo = new Dimension(100, 30);
        horasMin.setPreferredSize(dimCombo);
        horasMax.setPreferredSize(dimCombo);

        JLabel horaMinLabel= new JLabel("Duracion minima del maraton: ");
        horaMinLabel.setForeground(Color.white);
        panelFiltros.add(horaMinLabel);
        panelFiltros.add(horasMin);
        JLabel horaMaxLabel= new JLabel("Duracion maxima del maraton: ");
        horaMaxLabel.setForeground(Color.white);
        panelFiltros.add(horaMaxLabel);
        panelFiltros.add(horasMax);

        // EDAD 
        EdadRecomendada[] opcionesEdad= EdadRecomendada.values();
        edad = new JComboBox<>(opcionesEdad);
        JLabel labelEdad= new JLabel("Edad Recomendada: ");
        labelEdad.setForeground(Color.white);
        panelFiltros.add(edad);

        // PRESUPUESTO: 
        
        presupuestoSlider = new JSlider(0, 300, 60);
        presupuestoSlider.setBackground(colorFondo);
        presupuestoSlider.setForeground(Color.WHITE);
        presupuestoSlider.setMajorTickSpacing(50); 
        presupuestoSlider.setPaintTicks(true); 
        presupuestoSlider.setPaintLabels(true); 

        presupuestoLabel = new JLabel("Presupuesto: 60€");
        presupuestoLabel.setForeground(colorPrincipal);
        presupuestoLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // IA: Evento para que el label se actualice al mover el slider
        presupuestoSlider.addChangeListener(e -> {
            presupuestoLabel.setText("Presupuesto: " + presupuestoSlider.getValue() + "€");
        });
        panelFiltros.add(presupuestoLabel); 
        panelFiltros.add(presupuestoSlider);

        // boton para filtrar
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(colorFondo);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
        
        JButton btnGenerar = new JButton("ENCONTRAR MI MARATÓN");
        btnGenerar.setFont(new Font("Arial", Font.BOLD, 18));
        btnGenerar.setBackground(colorPrincipal);
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.setFocusPainted(false);
        btnGenerar.setPreferredSize(new Dimension(300, 50)); 
        
        panelBoton.add(btnGenerar);
        
        centro.add(panelFiltros, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);
        
	}
}
