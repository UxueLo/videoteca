package videoteca.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import videoteca.domain.Pelicula;
import videoteca.domain.Reserva;
import videoteca.domain.Usuario;
import videoteca.io.GestionImagenes;
import videoteca.jdbc.GestorBD;

public class VentanaCarrito extends JFrame {

    private static final long serialVersionUID = 1L;

    private Usuario usuario;
    private ArrayList<Pelicula> carrito;

    private JPanel panelCarrito;
    private JLabel precioTotal;

	private GestorBD gestor;

    public VentanaCarrito(VentanaInicioFrame ventanaInicio) {
    	
    	//Añadir gestorBD
    	this.gestor = new GestorBD();
    	
    	//Atributos de la ventana
        this.usuario = ventanaInicio.usuario;
        this.carrito = usuario.getCarritoUsuario();
        inicializarVentana();
    }

    private void inicializarVentana() {
    	setIconImage(new ImageIcon("Images/logo.png").getImage());
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
        panelCarrito.setLayout(new GridLayout(0, 4, 20, 20)); // 0 filas (ilimitadas), 4 columnas, espacio de 20px
        panelCarrito.setBackground(new Color(32,32,32));
        panelCarrito.setBorder(new EmptyBorder(20, 20, 20, 20));
        
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
            // Panel individual para cada película
            JPanel panelPeli = new JPanel();
            panelPeli.setLayout(new BoxLayout(panelPeli, BoxLayout.Y_AXIS));
            panelPeli.setBackground(new Color(60, 60, 60));
            panelPeli.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(111, 130, 179), 2),
                new EmptyBorder(15, 15, 15, 15)
            ));

            // Imagen (poster)
            JLabel lblImg = GestionImagenes.cargarImagen(p.getTitulo(), 150, 210);
            lblImg.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelPeli.add(lblImg);
            panelPeli.add(Box.createVerticalStrut(10));

            // Nombre de la película
            JLabel nombre = new JLabel(p.getTitulo());
            nombre.setForeground(Color.WHITE);
            nombre.setFont(new Font("SansSerif", Font.BOLD, 16));
            nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelPeli.add(nombre);
            panelPeli.add(Box.createVerticalStrut(5));

            // Año
            JLabel anyo = new JLabel("Año: " + p.getAñoLanzamiento());
            anyo.setForeground(Color.WHITE);
            anyo.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelPeli.add(anyo);
            panelPeli.add(Box.createVerticalStrut(5));

            // Duración
            JLabel duracion = new JLabel("Duración: " + p.getDuracion() + " min");
            duracion.setForeground(Color.WHITE);
            duracion.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelPeli.add(duracion);
            panelPeli.add(Box.createVerticalStrut(5));

            // Precio
            JLabel precio = new JLabel(String.format("Precio: %.2f €", p.getPrecio()));
            precio.setForeground(new Color(111, 130, 179));
            precio.setFont(new Font("Arial", Font.BOLD, 16));
            precio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelPeli.add(precio);
            panelPeli.add(Box.createVerticalStrut(10));

            // Botón quitar
            JButton btnQuitar = new JButton("Quitar");
            estiloBotonAzul(btnQuitar);
            btnQuitar.addActionListener(e -> {
                carrito.remove(p);
                actualizarCarrito();
            });
            panelPeli.add(btnQuitar, BorderLayout.EAST);

            panelCarrito.add(panelPeli);

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
            String tarjeta = numTarjeta.getText().trim();
            String nombre = nombreTarjeta.getText().trim();
            String cvv = CVV.getText().trim();

            String error = validarDatosPago(tarjeta, nombre, cvv);
            if (error != null) {
                JOptionPane.showMessageDialog(this, error, "Error en los datos", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Simular pago exitoso
            JOptionPane.showMessageDialog(this,
                    "¡Alquiler realizado con éxito! 🎬\n\n" + generarResumenAlquiler(),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            //Añadir peliculas al usuario
            for (Pelicula p : carrito) {
            	gestor.registrarReserva(usuario, new Reserva(p, LocalDate.now()));
            	usuario.setPuntos(usuario.getPuntos()+1);
            }
            
            carrito.clear();
            actualizarCarrito();
        }
    }
    
    private String validarDatosPago(String tarjeta, String nombre, String cvv) {
        if (tarjeta.isEmpty()) return "El número de tarjeta no puede estar vacío.";
        if (nombre.isEmpty()) return "El nombre en la tarjeta no puede estar vacío.";
        if (cvv.isEmpty()) return "El CVV no puede estar vacío.";
        if (!tarjeta.matches("\\d{13,19}")) return "Número de tarjeta inválido (13-19 dígitos).";
        if (!cvv.matches("\\d{3,4}")) return "CVV inválido (3 o 4 dígitos).";
        return null;
    }
    
    private String generarResumenAlquiler() {
        StringBuilder sb = new StringBuilder("Películas alquiladas:\n");
        double total = 0;
        for (Pelicula p : carrito) {
            sb.append(" - ").append(p.getTitulo()).append("\n");
            total += p.getPrecio();
        }
        sb.append("\nTotal: ").append(String.format("%.2f €", total));
        return sb.toString();
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