package videoteca.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Flow;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import videoteca.domain.Usuario;
import videoteca.jdbc.GestorBD;

public class VentanaPrevioJuego extends JFrame {
	
	private Usuario user;
	private GestorBD gestor;
	private Color fondo = new Color(35, 35, 35);

	public VentanaPrevioJuego(VentanaInicioFrame ventanaInicio) {
		this.user = ventanaInicio.usuario;
		this.gestor = new GestorBD();
		
		setIconImage(new ImageIcon("Images/logo.png").getImage());
		setTitle("Prueba suerte - Inicio");
        setSize(800, 500);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        setBackground(fondo);
        setResizable(false);
        
        // Panel principal
        JPanel panelPrincipal = (JPanel) this.getContentPane();
        panelPrincipal.setBackground(fondo);
		panelPrincipal.setLayout(new BorderLayout());
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		
		// Panel norte
		JPanel panelNorte = new JPanel();
		panelNorte.setBackground(fondo);
		panelNorte.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
		JLabel puntosLabel = new JLabel(" Puntos: " + user.getPuntos() + " ");
		puntosLabel.setBackground(new Color(128,128,128));
		puntosLabel.setFont(new Font("Arial", Font.BOLD, 40));
		puntosLabel.setOpaque(true);
		
		
		panelNorte.add(puntosLabel);
		add(panelNorte, BorderLayout.NORTH);
		
        // Panel central
        JPanel panelCentral = new JPanel();
        panelCentral.setBackground(fondo);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelCentral.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JLabel info = new JLabel("Información:               ");
        info.setForeground(Color.white);
        info.setFont(new Font("Arial", Font.BOLD, 25));
        panelCentral.add(info);
        
        JLabel espacio1 = new JLabel("________________________________________________________________________________________________________________________________________________________________________________________");
        espacio1.setForeground(fondo);
        espacio1.setFont(new Font("Arial", Font.PLAIN, 5));
        panelCentral.add(espacio1);
        
        ArrayList<JLabel> reglas = new ArrayList<>();
        reglas.add(new JLabel("- El juego será un trivial de cinco preguntas sobre películas."));
        reglas.add(new JLabel("- Habrá un temporizador de 15 segundos para cada pregunta."));
        reglas.add(new JLabel("- En caso de acertar la pregunta, conseguirás más o menos cantidad según el tiempo restante."));
        reglas.add(new JLabel("- Necesitas un punto para cada partida."));
        reglas.add(new JLabel("- ¡Consigue puntos alquilando películas!  (alquilar una película = +1 punto)                        "));
        
        for (JLabel regla : reglas) {	// para modificar los labels con un for
        	regla.setForeground(Color.white);
        	regla.setFont(new Font("Arial", Font.PLAIN, 15));
        	panelCentral.add(regla);
        }
        
        JLabel espacio2 = new JLabel("______________________________________________________________________________--__________________________________________________________________________________________________________________________________________________________");
        espacio2.setForeground(fondo);
        espacio2.setFont(new Font("Arial", Font.PLAIN, 5));
        panelCentral.add(espacio2);
        
        JLabel ultimo = new JLabel("¡Disfruta del juego!");
        ultimo.setForeground(Color.white);
        ultimo.setFont(new Font("Arial", Font.BOLD, 20));
        panelCentral.add(ultimo);
        
        add(panelCentral);
        
        
        // Panel sur - Botón para empezar el juego (si tenemos al menos un punto)
        JPanel panelSur = new JPanel();
        panelSur.setBackground(fondo);
        JButton botonIniciarjuego = new JButton(" Iniciar Juego ");
        botonIniciarjuego.setFont(new Font("Arial", Font.PLAIN, 30));
        botonIniciarjuego.setBackground(new Color(112, 124, 156));
        
        
        botonIniciarjuego.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (user.getPuntos() == 0) {	// si no tiene puntos
					JOptionPane.showMessageDialog(null, "No tienes suficientes puntos");
					
				} else {						// si tiene puntos
					user.setPuntos(user.getPuntos()-1);
					PruebaSuerte ps = new PruebaSuerte(ventanaInicio);
					ps.setVisible(true);
					dispose();
				}
				
				
			}
		 });
        
        panelSur.add(botonIniciarjuego);
        add(panelSur, BorderLayout.SOUTH);
	}
}
