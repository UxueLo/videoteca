package videoteca.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
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

    public VentanaCarrito(VentanaInicioFrame ventanaInicio) {
        this.usuario = ventanaInicio.usuario;
        this.carrito = usuario.getCarritoUsuario();
        inicializarVentana();
    }

    private void inicializarVentana() {
        setTitle("Carrito de Películas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Ocupa toda la pantalla
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);

        getContentPane().setBackground(new Color(32,32,32));
        setLayout(new BorderLayout());

        // TEXTO SUPERIOR
        JLabel info = new JLabel("Aquí puede alquilar sus películas seleccionadas", SwingConstants.CENTER);
        info.setFont(new Font("Garamond", Font.BOLD, 45));
        info.setForeground(new Color(111, 130, 179));
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
        estiloBotonAzul(btnLimpiar);
        btnLimpiar.addActionListener(e -> limpiarCarrito());

        JButton btnAlquilar = new JButton("Alquilar");
        estiloBotonAzul(btnAlquilar);
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

            JLabel nombre = new JLabel(p.getTitulo());
            nombre.setForeground(Color.WHITE);
            nombre.setFont(new Font("SansSerif", Font.BOLD, 16));

            JLabel anyo = new JLabel("Año: " + p.getAñoLanzamiento());
            anyo.setForeground(Color.WHITE);

            JLabel duracion = new JLabel("Duración: " + p.getDuracion() + " min");
            duracion.setForeground(Color.WHITE);

            JLabel precio = new JLabel(String.format("Precio: %.2f €", p.getPrecio()));
            precio.setForeground(Color.WHITE);

            info.add(nombre);
            info.add(anyo);
            info.add(duracion);
            info.add(precio);

            panelPeli.add(info, BorderLayout.CENTER);

            // Botón quitar
            JButton btnQuitar = new JButton("Quitar");
            estiloBotonAzul(btnQuitar);
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
        JTextField numTarjeta = new JTextField();
        panelPago.add(numTarjeta);

        panelPago.add(new JLabel("Nombre en la tarjeta:"));
        JTextField nombreTarjeta = new JTextField();
        panelPago.add(nombreTarjeta);

        panelPago.add(new JLabel("CVV:"));
        JTextField CVV = new JTextField();
        panelPago.add(CVV);

        int opcion = JOptionPane.showConfirmDialog(this, panelPago, "Datos de pago",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opcion == JOptionPane.OK_OPTION) {
            if (numTarjeta.getText().isEmpty() || nombreTarjeta.getText().isEmpty() || CVV.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "¡Alquiler realizado con éxito! 🎬", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                
                carrito.clear();
                actualizarCarrito();
            }
        }
    }

    private void estiloBotonAzul(JButton b) {
        b.setBackground(new Color(112, 124, 156));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                b.setBackground(new Color(220, 0, 0));
            }

            public void mouseExited(MouseEvent e) {
                b.setBackground(new Color(103, 114, 135));
            }
        });
    }
}