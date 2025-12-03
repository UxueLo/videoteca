package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import videoteca.domain.Comentario;
import videoteca.domain.Pelicula;
import videoteca.domain.Usuario;
import videoteca.jdbc.GestorBD;

public class VentanaComentario extends JFrame {
	
	private Pelicula p;
	private Comentario com;
	private Usuario user;
	private VentanaPelicula vp;
	
	private JPanel panelPrincipal;
	private JPanel panelCentral;		// Comentario (scroll)
	private JPanel panelNorte;			// Valoracion + usuario
	private JPanel panelUser;		 	// Usuario
	private JPanel panelSur;			// Boton eliminar
	
	private GestorBD gestor;
	
	public VentanaComentario (Pelicula p, Comentario com, Usuario user, VentanaPelicula vp) {
		this.p = p;
		this.com = com;
		this.user = user;
		this.vp = vp;
		this.gestor = new GestorBD();
		
		setIconImage(new ImageIcon("Images/logo.png").getImage());
		setTitle("Comentarios");
		setSize(800,500);
		setResizable(false);
		setLocationRelativeTo(null);
		
		this.panelPrincipal = (JPanel) this.getContentPane();
		panelPrincipal.setBackground(new Color(32,32,32));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		panelPrincipal.setLayout(new BorderLayout());
		
		
		
		// Panel central - comentario (scroll)
		this.panelCentral = new JPanel();
		panelCentral.setBackground(panelPrincipal.getBackground());
		panelCentral.setLayout(new BorderLayout());
		panelCentral.setBorder(BorderFactory.createEmptyBorder(0, 40, 40, 0));
		
		JTextArea texto = new JTextArea();
		
		if (!com.getTexto().isBlank()) {
			texto.setText(com.getTexto());
			texto.setFont(new Font("Arial", Font.PLAIN, 20));
		} else {
			texto.setText("\n \n \n No hay ningún comentrio en esta reseña");
			texto.setFont(new Font("Arial", Font.ITALIC, 20));
		}
		
		texto.setBackground(panelPrincipal.getBackground());
		texto.setForeground(Color.white);
		texto.setLineWrap(true);
		texto.setWrapStyleWord(true);
		
		JScrollPane scroll = new JScrollPane(texto);
		scroll.setBackground(panelPrincipal.getBackground());
		scroll.setBorder(null);
		
		texto.setEditable(false);
		texto.setOpaque(true);
		
		panelCentral.add(scroll);
		add(panelCentral);
		
		// Panel norte - valoracion + usuario
		// valoracion
		this.panelNorte = new JPanel();
		panelNorte.setLayout(new GridLayout(1, 2, 10, 10));
		panelNorte.setBackground(panelPrincipal.getBackground());
		
		JLabel valoracion = new JLabel();
		String ruta = "Images/Estrellas" + com.getValoracion() + ".png";
        ImageIcon icon = new ImageIcon(ruta);
        
		Image imagenEscalada = icon.getImage().getScaledInstance(240, 60, Image.SCALE_SMOOTH);
		valoracion.setIcon(new ImageIcon(imagenEscalada));
		valoracion.setHorizontalAlignment(JLabel.CENTER);
		valoracion.setVerticalAlignment(JLabel.TOP);
		
		panelNorte.add(valoracion);
		
		
		// usuario (panel user)
		this.panelUser = new JPanel();
		panelUser.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelUser.setBackground(panelPrincipal.getBackground());
		panelUser.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
		
		JLabel usuario = new JLabel();
		usuario.setText("Usuario: ");
		usuario.setFont(new Font("Arial", Font.BOLD, 30));
		usuario.setForeground(Color.white);
		
		JLabel userNom = new JLabel(com.getUser().getNombre());
		userNom.setFont(new Font("Arial", Font.ITALIC, 30));
		userNom.setForeground(Color.white);
		
		panelUser.add(usuario);
		panelUser.add(userNom);
		
		panelNorte.add(panelUser);
		panelCentral.add(panelNorte, BorderLayout.NORTH);
		
		
		// Panel sur - boton eliminar
		
		if (com.getUser().getNombre().equals(user.getNombre())) {		// TODO hacer bien esta igualdad
			this.panelSur = new JPanel();
			panelSur.setLayout(new GridLayout());
			JButton eliminar = new JButton("Eliminar comentario");
			eliminar.setFont(new Font("Arial", Font.BOLD, 35));
			eliminar.setBackground(new Color(112, 124, 156));
			
			eliminar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					String[] opciones = {"Sí"};
					
					int result = JOptionPane.showOptionDialog(
	                        null,
	                        "¿Estás seguro de que quieres borrar el comentario?",
	                        "Confirmación borrado comentario",
	                        JOptionPane.YES_OPTION,
	                        JOptionPane.QUESTION_MESSAGE,
	                        null,
	                        opciones,
	                        opciones[0]);
					
					if (result == JOptionPane.YES_OPTION) {
						gestor.borrarComentario(com.getUser(), p);
						vp.actualizarComentarios();
						VentanaComentario.this.dispose();
						
					}
					
				}
			});
		
		panelSur.add(eliminar);
		add(panelSur, BorderLayout.SOUTH);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		setVisible(true);
	}
	
	
	
}
