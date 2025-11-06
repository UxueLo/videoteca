package videoteca.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import videoteca.domain.*;
import videoteca.io.GestionImagenes;

public class VentanaCatalogo extends JFrame {

    private static final long serialVersionUID = 1L;

    private Usuario usuario;
    private ArrayList<Pelicula> peliculas;
    private ArrayList<Pelicula> carrito = new ArrayList<>();

    private JPanel panelCatalogo;
    private JComboBox<Genero> comboGenero;
    private JComboBox<EdadRecomendada> comboEdad;

    public VentanaCatalogo(Usuario usuario, ArrayList<Pelicula> peliculas, ArrayList<Pelicula> carrito) {
        this.usuario = usuario;
        this.peliculas = peliculas;
        this.carrito = carrito;
        inicializarVentana();
    }

    private void inicializarVentana() {
        setTitle("Catálogo de Películas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        
        // PANEL SUPERIOR
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BorderLayout());
        panelSuperior.setBackground(new Color(32,32,32));

        // TÍTULO
        JLabel titulo = new JLabel("Catálogo de Películas", SwingConstants.CENTER);
        titulo.setFont(new Font("Garamond", Font.BOLD, 45));
        titulo.setForeground(new Color(255,0,0));
        titulo.setBorder(new EmptyBorder(20, 0, 10, 0));
        panelSuperior.add(titulo, BorderLayout.NORTH);

        // PANEL FILTRAR
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelFiltros.setBackground(Color.BLACK);

        comboGenero = new JComboBox<>(Genero.values());
        comboGenero.insertItemAt(null, 0);
        comboGenero.setSelectedIndex(0);

        comboEdad = new JComboBox<>(EdadRecomendada.values());
        comboEdad.insertItemAt(null, 0);
        comboEdad.setSelectedIndex(0);

        JButton btnFiltrar = new JButton("Filtrar");
        estiloBotonRojo(btnFiltrar);
        btnFiltrar.addActionListener(e -> aplicarFiltros());

        JButton btnReinicio = new JButton("Reiniciar");
        estiloBotonNegro(btnReinicio);
        btnReinicio.addActionListener(e -> reiniciarFiltros());

        JButton btnCarrito = new JButton("Ir al Carrito");
        estiloBotonRojo(btnCarrito);
        btnCarrito.addActionListener(e -> abrirCarrito());

        JLabel Genero = new JLabel("Género:");
        Genero.setForeground(Color.WHITE);
        panelFiltros.add(Genero);
        panelFiltros.add(comboGenero);

        JLabel Edad = new JLabel("Edad Recomendada:");
        Edad.setForeground(Color.WHITE);
        panelFiltros.add(Edad);
        panelFiltros.add(comboEdad);

        panelFiltros.add(btnFiltrar);
        panelFiltros.add(btnReinicio);
        panelFiltros.add(btnCarrito);

        panelSuperior.add(panelFiltros, BorderLayout.SOUTH);
        add(panelSuperior, BorderLayout.NORTH);

        // PANEL CATALOGO
        panelCatalogo = new JPanel(new GridLayout(0, 4, 20, 20));
        panelCatalogo.setBackground(Color.BLACK);
        JScrollPane scroll = new JScrollPane(panelCatalogo);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        cargarPeliculas(peliculas);
        setVisible(true);
    }
    
    // MÉTODOS

    private void cargarPeliculas(ArrayList<Pelicula> lista) {
        panelCatalogo.removeAll();

        for (Pelicula p : lista) {
            JPanel pelicula = new JPanel();
            pelicula.setLayout(new BoxLayout(pelicula, BoxLayout.Y_AXIS));
            pelicula.setBackground(new Color(32,32,32));

            // Cargar imagen desde GestionImagenes
            JLabel imagen = GestionImagenes.cargarImagen(p.getTitulo(), 200, 280);
            imagen.setAlignmentX(CENTER_ALIGNMENT);

            JLabel titulo = new JLabel(p.getTitulo());
            titulo.setFont(new Font("Arial", Font.BOLD, 16));
            titulo.setForeground(Color.WHITE);
            titulo.setAlignmentX(CENTER_ALIGNMENT);
            titulo.setBorder(new EmptyBorder(5, 0, 5, 0));

            JLabel precio = new JLabel(String.format("%.2f €", p.getPrecio()), SwingConstants.CENTER);
            precio.setFont(new Font("Arial", Font.BOLD, 13));
            precio.setForeground(Color.WHITE);
            precio.setAlignmentX(CENTER_ALIGNMENT);

            JButton btnCarrito = new JButton("Añadir al carrito");
            estiloBotonRojo(btnCarrito);
            btnCarrito.setAlignmentX(CENTER_ALIGNMENT);
            btnCarrito.addActionListener(e -> agregarAlCarrito(p));

            pelicula.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new VentanaPelicula(p).setVisible(true);
                }

                @Override
                public void mouseEntered(MouseEvent e) { 
                	pelicula.setBackground(new Color(60, 0, 0)); 
                }
                @Override
                public void mouseExited(MouseEvent e) { 
                	pelicula.setBackground(new Color(32,32,32)); 
                	}
            });

            pelicula.add(imagen);
            pelicula.add(Box.createVerticalStrut(5));
            pelicula.add(titulo);
            pelicula.add(precio);
            pelicula.add(Box.createVerticalStrut(10));
            pelicula.add(btnCarrito);

            panelCatalogo.add(pelicula);
        }

        panelCatalogo.revalidate();
        panelCatalogo.repaint();
    }


    private void agregarAlCarrito(Pelicula p) {
        if (carrito.contains(p))
            JOptionPane.showMessageDialog(this, "Esta película ya está en el carrito.", "Aviso", JOptionPane.WARNING_MESSAGE);
        else {
            carrito.add(p);
            JOptionPane.showMessageDialog(this, p.getTitulo() + " añadida al carrito", "Carrito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void abrirCarrito() {
        new VentanaCarrito(usuario, carrito);
    }

    private void aplicarFiltros() {
        Genero valorGenero = (Genero) comboGenero.getSelectedItem();
        EdadRecomendada valorEdad = (EdadRecomendada) comboEdad.getSelectedItem();

        ArrayList<Pelicula> filtradas = new ArrayList<>();
        for (Pelicula p : peliculas)
            if ((valorGenero == null || p.getGeneros().contains(valorGenero)) &&
                (valorEdad == null || p.getEdadRecomendada() == valorEdad))
                filtradas.add(p);

        cargarPeliculas(filtradas);
    }

    private void reiniciarFiltros() {
        comboGenero.setSelectedIndex(0);
        comboEdad.setSelectedIndex(0);
        
        cargarPeliculas(peliculas);
    }

    private void estiloBotonRojo(JButton b) {
        b.setBackground(new Color(180, 0, 0));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 13));
        b.setBorder(new LineBorder(Color.BLACK, 1));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { 
            	b.setBackground(new Color(220, 0, 0)); 
            }
            public void mouseExited(MouseEvent e) { 
            	b.setBackground(new Color(180, 0, 0)); 
            }
        });
    }

    private void estiloBotonNegro(JButton b) {
        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 13));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { 
            	b.setBackground(new Color(50, 0, 0)); 
            }
            public void mouseExited(MouseEvent e) { 
            	b.setBackground(Color.BLACK); 
            }
        });
    }
}

