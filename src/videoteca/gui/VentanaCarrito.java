package videoteca.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import videoteca.domain.Pelicula;
import videoteca.domain.Usuario;
import videoteca.io.GestionImagenes;

public class VentanaCarrito extends JFrame {

    private static final long serialVersionUID = 1L;

    private Usuario usuario;
    private ArrayList<Pelicula> carrito;

    private JPanel panelCarrito;
    private JLabel precioTotal;

    public VentanaCarrito(Usuario usuario, ArrayList<Pelicula> carrito) {
        this.usuario = usuario;
        this.carrito = carrito;
        inicializarVentana();
    }

    private void inicializarVentana() {
        setTitle("Carrito de Películas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Ocupa toda la pantalla
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);

        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        // TEXTO SUPERIOR
        JLabel info = new JLabel("Aquí puede alquilar sus películas seleccionadas.", SwingConstants.CENTER);
        info.setFont(new Font("Garamond", Font.BOLD, 45));
        info.setForeground(Color.WHITE);
        info.setBorder(new EmptyBorder(15, 0, 15, 0));
        add(info, BorderLayout.NORTH);

        // PANEL CENTRAL DEL CARRITO
        panelCarrito = new JPanel();
        panelCarrito.setLayout(new BoxLayout(panelCarrito, BoxLayout.Y_AXIS));
        panelCarrito.setBackground(new Color(32,32,32));
        JScrollPane scroll = new JScrollPane(panelCarrito);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        // PANEL INFERIOR CON TOTAL Y BOTONES
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        panelInferior.setBackground(Color.BLACK);

        precioTotal = new JLabel();
        precioTotal.setFont(new Font("SansSerif", Font.BOLD, 18));
        precioTotal.setForeground(Color.WHITE);

        JButton btnLimpiar = new JButton("Limpiar carrito");
        estiloBotonRojo(btnLimpiar);
        btnLimpiar.addActionListener(e -> limpiarCarrito());

        JButton btnAlquilar = new JButton("Alquilar");
        estiloBotonRojo(btnAlquilar);
        btnAlquilar.addActionListener(e -> alquilarPeliculas());

        panelInferior.add(precioTotal);
        panelInferior.add(btnLimpiar);
        panelInferior.add(btnAlquilar);

        add(panelInferior, BorderLayout.SOUTH);

        actualizarCarrito();

        setVisible(true);
    }

    private void actualizarCarrito() {
        panelCarrito.removeAll();
        double total = 0;

        for (Pelicula p : carrito) {
            JPanel panelPeli = new JPanel(new BorderLayout());
            panelPeli.setBackground(Color.DARK_GRAY);
            panelPeli.setBorder(new EmptyBorder(10, 10, 10, 10));

            // Imagen cargada mediante GestionImagenes
            JLabel lblImg = GestionImagenes.cargarImagen(p.getTitulo(), 150, 210);
            panelPeli.add(lblImg, BorderLayout.WEST);

            // Info de la película
            JPanel info = new JPanel();
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
            info.setBackground(Color.DARK_GRAY);

            JLabel lblTitulo = new JLabel(p.getTitulo());
            lblTitulo.setForeground(Color.WHITE);
            lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));

            JLabel lblAno = new JLabel("Año: " + p.getAñoLanzamiento());
            lblAno.setForeground(Color.WHITE);

            JLabel lblDuracion = new JLabel("Duración: " + p.getDuracion() + " min");
            lblDuracion.setForeground(Color.WHITE);

            JLabel lblPrecio = new JLabel(String.format("Precio: %.2f €", p.getPrecio()));
            lblPrecio.setForeground(Color.WHITE);

            info.add(lblTitulo);
            info.add(lblAno);
            info.add(lblDuracion);
            info.add(lblPrecio);

            panelPeli.add(info, BorderLayout.CENTER);

            // Botón quitar
            JButton btnQuitar = new JButton("Quitar");
            estiloBotonRojo(btnQuitar);
            btnQuitar.addActionListener(e -> {
                carrito.remove(p);
                actualizarCarrito();
            });
            panelPeli.add(btnQuitar, BorderLayout.EAST);

            panelCarrito.add(panelPeli);
            panelCarrito.add(Box.createVerticalStrut(15));

            total += p.getPrecio();
        }

        precioTotal.setText("Total: " + String.format("%.2f €", total));

        panelCarrito.revalidate();
        panelCarrito.repaint();
    }

    private void limpiarCarrito() {
        carrito.clear();
        actualizarCarrito();
    }

    private void alquilarPeliculas() {
        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPanel panelPago = new JPanel(new GridLayout(3, 2, 10, 10));
        panelPago.add(new JLabel("Número de tarjeta:"));
        JTextField tfNumTarjeta = new JTextField();
        panelPago.add(tfNumTarjeta);

        panelPago.add(new JLabel("Nombre en la tarjeta:"));
        JTextField tfNombre = new JTextField();
        panelPago.add(tfNombre);

        panelPago.add(new JLabel("CVV:"));
        JTextField tfCVV = new JTextField();
        panelPago.add(tfCVV);

        int opcion = JOptionPane.showConfirmDialog(this, panelPago, "Datos de pago",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opcion == JOptionPane.OK_OPTION) {
            if (tfNumTarjeta.getText().isEmpty() || tfNombre.getText().isEmpty() || tfCVV.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "¡Alquiler realizado con éxito! 🎬", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                carrito.clear();
                actualizarCarrito();
            }
        }
    }

    private void estiloBotonRojo(JButton b) {
        b.setBackground(new Color(180, 0, 0));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                b.setBackground(new Color(220, 0, 0));
            }

            public void mouseExited(MouseEvent e) {
                b.setBackground(new Color(180, 0, 0));
            }
        });
    }
}