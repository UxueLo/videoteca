package videoteca.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import videoteca.domain.*;

public class VentanaCatalogo extends JFrame {

    private static final long serialVersionUID = 1L;

    private Usuario usuario;
    private ArrayList<Pelicula> peliculas;
    private ArrayList<Pelicula> carrito = new ArrayList<>();

    private JPanel panelCatalogo;
    private JComboBox<Genero> comboGenero;
    private JComboBox<EdadRecomendada> comboEdad;

    public VentanaCatalogo(Usuario usuario, ArrayList<Pelicula> peliculas) {
        this.usuario = usuario;
        this.peliculas = peliculas;
        inicializarVentana();
    }

    private void inicializarVentana() {
        setTitle("Catálogo de Películas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Ocupa toda la pantalla y no se puede redimensionar
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);

        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        // PANEL SUPERIOR - FILTROS
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

        JButton btnReset = new JButton("Reiniciar");
        estiloBotonNegro(btnReset);
        btnReset.addActionListener(e -> resetFiltros());

        JButton btnCarrito = new JButton("🛒 Ir al carrito");
        estiloBotonRojo(btnCarrito);
        btnCarrito.addActionListener(e -> abrirCarrito());

        JLabel lblGenero = new JLabel("Género:");
        lblGenero.setForeground(Color.WHITE);
        panelFiltros.add(lblGenero);
        panelFiltros.add(comboGenero);

        JLabel lblEdad = new JLabel("Edad Recomendada:");
        lblEdad.setForeground(Color.WHITE);
        panelFiltros.add(lblEdad);
        panelFiltros.add(comboEdad);

        panelFiltros.add(btnFiltrar);
        panelFiltros.add(btnReset);
        panelFiltros.add(btnCarrito);

        add(panelFiltros, BorderLayout.NORTH);

        // PANEL CENTRAL - CATALOGO
        panelCatalogo = new JPanel(new GridLayout(0, 4, 20, 20));
        panelCatalogo.setBackground(Color.BLACK);
        JScrollPane scroll = new JScrollPane(panelCatalogo);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        cargarPeliculas(peliculas);
        setVisible(true);
    }

    private void cargarPeliculas(ArrayList<Pelicula> lista) {
        panelCatalogo.removeAll();

        for (Pelicula p : lista) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(Color.DARK_GRAY);

            // Imagen
            JLabel lblImg = new JLabel();
            lblImg.setAlignmentX(CENTER_ALIGNMENT);
            lblImg.setPreferredSize(new Dimension(200, 280));

            String ruta = "images/" + p.getTitulo() + ".jpg";
            File archivo = new File(ruta);
            ImageIcon icon;

            if (archivo.exists()) {
                icon = new ImageIcon(ruta);
            } else {
                icon = new ImageIcon("images/default.jpg");
            }

            // Escalar la imagen y asignarla al JLabel
            Image imagenEscalada = icon.getImage().getScaledInstance(200, 280, Image.SCALE_SMOOTH);
            lblImg.setIcon(new ImageIcon(imagenEscalada));

            // Título 
            JLabel lblTitulo = new JLabel(p.getTitulo(), SwingConstants.CENTER);
            lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
            lblTitulo.setForeground(Color.WHITE);
            lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
            lblTitulo.setBorder(new EmptyBorder(5, 0, 5, 0));

            // Precio
            JLabel lblPrecio = new JLabel(String.format("%.2f €", p.getPrecio()), SwingConstants.CENTER);
            lblPrecio.setFont(new Font("SansSerif", Font.BOLD, 13));
            lblPrecio.setForeground(Color.WHITE);
            lblPrecio.setAlignmentX(CENTER_ALIGNMENT);

            // Botón agregar al carrito
            JButton btnCarrito = new JButton("🛒 Añadir al carrito");
            estiloBotonRojo(btnCarrito);
            btnCarrito.setAlignmentX(CENTER_ALIGNMENT);
            btnCarrito.addActionListener(e -> agregarAlCarrito(p));

            // Click en la película → abrir ventana de detalles
            card.addMouseListener(new MouseAdapter() { 
                @Override 
                public void mouseClicked(MouseEvent e) { 
                    // Abrir la ventana de película
                    VentanaPelicula ventanaPelicula = new VentanaPelicula(p, usuario);
                    ventanaPelicula.setVisible(true);
                }
                @Override 
                public void mouseEntered(MouseEvent e) { 
                    card.setBackground(new Color(60, 0, 0)); 
                } 
                @Override 
                public void mouseExited(MouseEvent e) { 
                    card.setBackground(Color.DARK_GRAY); 
                } 
            });

            card.add(lblImg);
            card.add(Box.createVerticalStrut(5));
            card.add(lblTitulo);
            card.add(lblPrecio);
            card.add(Box.createVerticalStrut(10));
            card.add(btnCarrito);

            panelCatalogo.add(card);
        }

        panelCatalogo.revalidate();
        panelCatalogo.repaint();
    }


    private void agregarAlCarrito(Pelicula p) {
        if (carrito.contains(p))
            JOptionPane.showMessageDialog(this, "Esta película ya está en el carrito.", "Aviso", JOptionPane.WARNING_MESSAGE);
        else {
            usuario.getCarritoUsuario().add(p);
            JOptionPane.showMessageDialog(this, p.getTitulo() + " añadida al carrito 🎬", "Carrito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void abrirCarrito() {
        new VentanaCarrito(usuario);
    }

    private void aplicarFiltros() {
        Genero generoSel = (Genero) comboGenero.getSelectedItem();
        EdadRecomendada edadSel = (EdadRecomendada) comboEdad.getSelectedItem();

        ArrayList<Pelicula> filtradas = new ArrayList<>();
        for (Pelicula p : peliculas)
            if ((generoSel == null || p.getGeneros().contains(generoSel)) &&
                (edadSel == null || p.getEdadRecomendada() == edadSel))
                filtradas.add(p);

        cargarPeliculas(filtradas);
    }

    private void resetFiltros() {
        comboGenero.setSelectedIndex(0);
        comboEdad.setSelectedIndex(0);
        cargarPeliculas(peliculas);
    }

    private void estiloBotonRojo(JButton b) {
        b.setBackground(new Color(180, 0, 0));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setBorder(new LineBorder(Color.BLACK, 1));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(new Color(220, 0, 0)); }
            public void mouseExited(MouseEvent e) { b.setBackground(new Color(180, 0, 0)); }
        });
    }

    private void estiloBotonNegro(JButton b) {
        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(new Color(50, 0, 0)); }
            public void mouseExited(MouseEvent e) { b.setBackground(Color.BLACK); }
        });
    }
}

