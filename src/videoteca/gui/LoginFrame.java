package videoteca.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame{
	
	//private JLabel textoIniciarSesion;
	private JLabel textoIniciarSesion;
	private JLabel labelUsuario;
	private JLabel labelContraseña;
	private JTextField nombreUsuario;
	private JPasswordField contraseña;
	private JButton botonInicarSesion;
	private Color colorTexto = new Color(204,0,0);
	private Font fuenteTexto = new Font("Times New Roman",Font.BOLD,20);
	
	//constructor
	public LoginFrame() {
		setTitle("Iniciar Sesión");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800,400);
		setLocationRelativeTo(null);
		
		//panel principal
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(null);
		panelPrincipal.setBackground(new Color(96, 96, 96));
		panelPrincipal.setOpaque(true);
		setContentPane(panelPrincipal);
		
		//Llama a constructor de objetos
		inicializarPanel();
	}

	private void inicializarPanel() {
		
		//textoIniciarSesion
		textoIniciarSesion = new JLabel();
		textoIniciarSesion.setText("INICIAR SESION");
		textoIniciarSesion.setForeground(colorTexto);
		textoIniciarSesion.setFont(fuenteTexto);
		textoIniciarSesion.setBounds(0,60,800,50);;
		textoIniciarSesion.setHorizontalAlignment(JLabel.CENTER);
		
		//labelUsuario
		labelUsuario = new JLabel();
		labelUsuario.setText("Usuario: ");
		labelUsuario.setForeground(colorTexto);
		labelUsuario.setFont(fuenteTexto);
		labelUsuario.setBounds(160,200,150,40);;
		
		//nombreUsuario
		nombreUsuario = new JTextField();
		nombreUsuario.setForeground(colorTexto);
		nombreUsuario.setFont(fuenteTexto);
		nombreUsuario.setBounds(380,200,400,40);
		
		
		//adds
		panelPrincipal.add(textoIniciarSesion);
		this.add(labelUsuario);
		this.add(nombreUsuario);
	}
	
}
