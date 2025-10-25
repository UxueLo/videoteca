package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import videoteca.domain.Usuario;

public class VentanaInicioFrame extends JFrame{
	public VentanaInicioFrame(Usuario usuario) {
		//Ventana
		setTitle("Ventana de Inicio"); //titulo
		setDefaultCloseOperation(EXIT_ON_CLOSE); //parar al cerrar
		setExtendedState(JFrame.MAXIMIZED_BOTH); //abrirlo en pantalla completa
		setResizable(false); //siempre estara en pantalla completa
		getContentPane().setBackground(new Color(32,32,32)); //cambiar el fondo
		
		//Layouts
		setLayout(new BorderLayout()); // el layout principal sera un BorderLayout
		
		//centro: titulo y peliculas
		JPanel centro= new JPanel();
		centro.setLayout(new BorderLayout());
		centro.setBackground(new Color(32,32,32));
		add(centro, BorderLayout.CENTER);
			//titulo
		JLabel textoCabeza = new JLabel("Peliculas mejor valoradas", JLabel.CENTER);
		textoCabeza.setForeground(new Color(255,0,0));
		textoCabeza.setFont(new Font("Gabriola", Font.BOLD, 45));
		textoCabeza.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); //crear borde para que no este pegado arriba, ayuda de DeepSeek
		centro.add(textoCabeza, BorderLayout.NORTH);
			//peliculas
		JPanel peliculas= new JPanel();
		centro.add(peliculas, BorderLayout.CENTER);
		peliculas.setBackground(new Color(32,32,32));
		JScrollPane scrollPeliculas= new JScrollPane(peliculas);
		
		//menu del lado
		JPanel menu = new JPanel();
		menu.setBackground(new Color(153,0,0));
		menu.setPreferredSize(new Dimension(350, 0));
		menu.setLayout(new GridLayout(7,1));
			//parte del usuario
		JLabel perfil= new JLabel();
		perfil.setText("Hola " + usuario.getNombre()+ "!");
		perfil.setFont(new Font("Arial", Font.BOLD, 40));
		perfil.setIcon(new ImageIcon("Images/iconoUsuario.png"));
		perfil.setHorizontalTextPosition(JLabel.RIGHT); 
		perfil.setVerticalTextPosition(JLabel.CENTER);
		menu.add(perfil);
		String [] opciones = {"Iniciar Sesion", "Registrarse", "Catalogo", "Mis reservas", "Prueba Suerte", "Cerrar sesion"};
		for (String opcion:opciones) {
			JLabel campo= new JLabel(opcion);
			campo.setHorizontalAlignment(JLabel.CENTER);
			campo.setFont(new Font("Arial",Font.BOLD, 18));
			menu.add(campo);
		}
		add(menu, BorderLayout.EAST);
		
		setVisible(true); //ventana visible
	}
	
}
