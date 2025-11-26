package videoteca.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import videoteca.domain.*;

public class VentanaCatalogo extends JFrame {

    private static final long serialVersionUID = 1L;

    private Usuario usuario;
    private ArrayList<Pelicula> peliculas;
    private ArrayList<Pelicula> carrito = new ArrayList<>();

    private JPanel panelCatalogo;
    private JComboBox<Genero> comboGenero;
    private JComboBox<EdadRecomendada> comboEdad;
    private JButton btnCarrito;

	private VentanaInicioFrame ventanaInicio;

    public VentanaCatalogo(VentanaInicioFrame ventanaInicio, ArrayList<Pelicula> peliculas) {
    	this.ventanaInicio = ventanaInicio;
        this.usuario = ventanaInicio.usuario;
        this.peliculas = peliculas;
        mostrarBarraDeProgreso(peliculas);
    }
    
    private void mostrarBarraDeProgreso(ArrayList<Pelicula> peliculas) {
        JDialog dialogoCarga = new JDialog(this, "Cargando Catálogo...", true);
        JProgressBar barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setStringPainted(true);
        dialogoCarga.add(BorderLayout.CENTER, barraProgreso);
        dialogoCarga.setSize(300, 75);
        dialogoCarga.setResizable(false);
        dialogoCarga.setLocationRelativeTo(this);

        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                int total = peliculas.size();
                int actual = 0;

                for (Pelicula p : peliculas) {
                    // Simular carga de imagen
                    new ImageIcon("images/" + p.getTitulo() + ".jpg");
                    Thread.sleep(20);
                    actual++;
                    publish((int) ((double) actual / total * 100));
                }

                // Inicializa la ventana principal cuando termine la carga
                SwingUtilities.invokeAndWait(() -> inicializarVentana());
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                barraProgreso.setValue(chunks.get(chunks.size() - 1));
            }

            @Override
            protected void done() {
                dialogoCarga.dispose();
            }
        };

        worker.execute();
        dialogoCarga.setVisible(true);
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

        comboGenero.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                aplicarFiltros();
            }
        });

        comboEdad.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                aplicarFiltros();
            }
        });

        JButton btnReset = new JButton("Reiniciar");
        estiloBotonNegro(btnReset);
        btnReset.addActionListener(e -> resetFiltros());

        btnCarrito = new JButton(); 
        estiloBotonAzul(btnCarrito);
        actualizarBotonCarrito(); 
        btnCarrito.addActionListener(e -> abrirCarrito());

        JLabel lblGenero = new JLabel("Género:");
        lblGenero.setForeground(Color.WHITE);
        panelFiltros.add(lblGenero);
        panelFiltros.add(comboGenero);

        JLabel lblEdad = new JLabel("Edad Recomendada:");
        lblEdad.setForeground(Color.WHITE);
        panelFiltros.add(lblEdad);
        panelFiltros.add(comboEdad);
        panelFiltros.add(btnReset);
        panelFiltros.add(btnCarrito);

        add(panelFiltros, BorderLayout.NORTH);

        // PANEL CENTRAL - CATALOGO
        panelCatalogo = new JPanel();
        panelCatalogo.setBackground(Color.BLACK);
        JScrollPane scroll = new JScrollPane(panelCatalogo);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        cargarPeliculas(peliculas);
        setVisible(true);
    }

    private void cargarPeliculas(ArrayList<Pelicula> lista) {
        // Quitamos todo lo que había antes en el panel
        panelCatalogo.removeAll();

        // Usamos GridBagLayout para organizar las tarjetas en filas y columnas
        panelCatalogo.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 20, 20); // espacio entre tarjetas
        gbc.fill = GridBagConstraints.HORIZONTAL; // que ocupen todo el ancho de la columna

        int columnas = 4;   // queremos 4 columnas
        int contador = 0;   // cuenta cuántas tarjetas hemos puesto

        // Guardamos todas las tarjeta y buscamos la más alta
        ArrayList<JPanel> tarjetas = new ArrayList<>();
        int maxHeight = 0; // altura máxima encontrada
        for (Pelicula p : lista) {
            JPanel tarjeta = crearCard(p);
            tarjetas.add(tarjeta);
            // actualizamos la altura máxima
            maxHeight = Math.max(maxHeight, tarjeta.getPreferredSize().height);
        }

        // Añadimos las cards reales al panel
        for (JPanel tarjeta : tarjetas) {
            // columna = resto de dividir contador entre columnas
            gbc.gridx = contador % columnas;
            // fila = división entera de contador entre columnas
            gbc.gridy = contador / columnas;
            gbc.weightx = 1;
            gbc.weighty = 0;

            // todas las tarjetas tendrán la misma altura (la mayor encontrada)
            Dimension pref = tarjeta.getPreferredSize();
            tarjeta.setPreferredSize(new Dimension(pref.width, maxHeight));

            panelCatalogo.add(tarjeta, gbc);
            contador++;
        }

        // Si la última fila no está completa, añadimos "tarjetas invisibles"
        int resto = contador % columnas;
        if (resto != 0) {
            int faltan = columnas - resto; // cuántas tarjetas faltan para completar la fila
            for (int i = 0; i < faltan; i++) {
                gbc.gridx = (contador + i) % columnas;
                gbc.gridy = (contador + i) / columnas;
                gbc.weightx = 1;
                gbc.weighty = 0;

                // panel invisible que ocupa el mismo espacio que una tarjeta
                JPanel filler = new JPanel();
                filler.setOpaque(false); // transparente
                filler.setPreferredSize(new Dimension(200, maxHeight));
                panelCatalogo.add(filler, gbc);
            }
            contador += faltan;
        }

        // Añadimos un panel invisible al final para que no se estire todo hacia abajo
        gbc.gridx = 0;
        gbc.gridy = (contador / columnas) + 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        JPanel fillerFinal = new JPanel();
        fillerFinal.setOpaque(false);
        panelCatalogo.add(fillerFinal, gbc);

        // Actualizamos el panel para que se vea todo correctamente
        panelCatalogo.revalidate();
        panelCatalogo.repaint();
    }


    // Crea la tarjeta de cada película 
    private JPanel crearCard(Pelicula p) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.DARK_GRAY);
        tarjeta.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Imagen
        JLabel imagen = new JLabel();
        imagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        String ruta = "images/" + p.getTitulo() + ".jpg";
        File archivo = new File(ruta);
        ImageIcon icon = archivo.exists() ? new ImageIcon(ruta) : new ImageIcon("images/default.jpg");
        Image imagenEscalada = icon.getImage().getScaledInstance(200, 280, Image.SCALE_SMOOTH);
        imagen.setIcon(new ImageIcon(imagenEscalada));

        // Título y precio
        JLabel titulo = new JLabel(p.getTitulo(), SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(new EmptyBorder(5, 0, 5, 0));

        JLabel precio = new JLabel(String.format("%.2f €", p.getPrecio()), SwingConstants.CENTER);
        precio.setFont(new Font("SansSerif", Font.BOLD, 13));
        precio.setForeground(Color.WHITE);
        precio.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón añadir al carrito
        JButton btnCarrito = new JButton("🛒 Añadir al carrito");
        estiloBotonAzul(btnCarrito);
        btnCarrito.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCarrito.addActionListener(e -> agregarAlCarrito(p));

        // Hover y click para abrir ventana de detalles
        tarjeta.addMouseListener(new MouseAdapter() { 
            @Override 
            public void mouseClicked(MouseEvent e) { new VentanaPelicula(p, usuario).setVisible(true); }
            @Override public void mouseEntered(MouseEvent e) { tarjeta.setBackground(new Color(60, 0, 0)); } 
            @Override public void mouseExited(MouseEvent e) { tarjeta.setBackground(Color.DARK_GRAY); } 
        });

        tarjeta.add(imagen);
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(titulo);
        tarjeta.add(precio);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(btnCarrito);

        return tarjeta;
    }


    private void agregarAlCarrito(Pelicula p) {
        if (usuario.getCarritoUsuario().contains(p))
            JOptionPane.showMessageDialog(this, "Esta película ya está en el carrito.", "Aviso", JOptionPane.WARNING_MESSAGE);
        else {
            usuario.getCarritoUsuario().add(p);
            JOptionPane.showMessageDialog(this, p.getTitulo() + " añadida al carrito 🎬", "Carrito", JOptionPane.INFORMATION_MESSAGE);
            actualizarBotonCarrito();
        }
    }

    private void abrirCarrito() {
        new VentanaCarrito(ventanaInicio);
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

    private void estiloBotonAzul(JButton b) {
        b.setBackground(new Color(112, 124, 156));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setBorder(new LineBorder(Color.BLACK, 1));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(new Color(220, 0, 0)); }
            public void mouseExited(MouseEvent e) { b.setBackground(new Color(103, 114, 135)); }
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

	private void actualizarBotonCarrito() {
	    int cantidad = usuario.getCarritoUsuario().size();
	    if (cantidad == 0) {
	        btnCarrito.setText("🛒 Carrito");
	    } else if (cantidad == 1) {
	        btnCarrito.setText("🛒 Carrito 1");
	    } else {
	        btnCarrito.setText("🛒 Carrito " + cantidad);
	    }
	}
}

