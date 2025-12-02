package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import videoteca.domain.Comentario;
import videoteca.domain.Pelicula;

public class VentanaComentario extends JFrame {
	
	private Pelicula p;
	private Comentario com;
	
	private JPanel panelPrincipal;
	private JPanel panelOeste;			// Valoracion
	private JPanel panelCentral;		// Comentario (scroll)
	private JPanel panelCentralNorte; 	// Usuario
	
	public VentanaComentario (Pelicula p, Comentario com) {
		this.p = p;
		this.com = com;
		
		setIconImage(new ImageIcon("Images/logo.png").getImage());
		setTitle("Comentarios");
		setSize(800,500);
		setResizable(false);
		setLocationRelativeTo(null);
		
		this.panelPrincipal = (JPanel) this.getContentPane();
		panelPrincipal.setBackground(new Color(32,32,32));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		panelPrincipal.setLayout(new BorderLayout());
		
		
		// Panel Oeste - Valoracion
		this.panelOeste = new JPanel();
		panelOeste.setBackground(panelPrincipal.getBackground());
		panelOeste.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));
		
		JLabel valoracion = new JLabel();
		valoracion.setBackground(panelPrincipal.getBackground());
		
		String ruta = "Images/" + com.getValoracion() + ".png";
        ImageIcon icon = new ImageIcon(ruta);
        
		Image imagenEscalada = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		valoracion.setIcon(new ImageIcon(imagenEscalada));
		
		valoracion.setOpaque(true);
		
		panelOeste.add(valoracion);
		add(panelOeste, BorderLayout.WEST);
		
		
		// Panel central - comentario (scroll)
		this.panelCentral = new JPanel();
		panelCentral.setBackground(panelPrincipal.getBackground());
		panelCentral.setLayout(new BorderLayout());
		
		JTextArea texto = new JTextArea();
		
		if (!com.getTexto().isBlank()) {
			texto.setText("\n" + com.getTexto());
			texto.setFont(new Font("Arial", Font.PLAIN, 20));
		} else {
			texto.setText("\n \n \n \n \n \n No hay ningún comentrio en esta reseña");
			texto.setFont(new Font("Arial", Font.ITALIC, 20));
		}
		
		texto.setBackground(panelPrincipal.getBackground());
		texto.setForeground(Color.white);;
		texto.setLineWrap(true);
		texto.setWrapStyleWord(true);
		
		JScrollPane scroll = new JScrollPane(texto);
		scroll.setBackground(panelPrincipal.getBackground());
		scroll.setBorder(null);
		
		texto.setOpaque(true);
		
		panelCentral.add(scroll);
		add(panelCentral);
		
		// Panel central norte - usuario
		this.panelCentralNorte = new JPanel();
		panelCentralNorte.setLayout(new FlowLayout());
		panelCentralNorte.setBackground(panelPrincipal.getBackground());
		
		JLabel usuario = new JLabel();
		usuario.setText("Usuario: ");
		usuario.setFont(new Font("Arial", Font.BOLD, 30));
		usuario.setForeground(Color.white);
		
		JLabel userNom = new JLabel(com.getUser().getNombre());
		userNom.setFont(new Font("Arial", Font.ITALIC, 30));
		userNom.setForeground(Color.white);
		
		panelCentralNorte.add(usuario);
		panelCentralNorte.add(userNom);
		
		panelCentral.add(panelCentralNorte, BorderLayout.NORTH);
		
		
		
		
		
		
		
		
		
		
		
		
		setVisible(true);
	}
	
	
	
}
