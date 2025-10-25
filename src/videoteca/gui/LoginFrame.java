package videoteca.gui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame{
	
	//private JLabel textoIniciarSesion;
	private JTextField nombreUsuario;
	private JPasswordField contraseña;
	private JButton botonInicarSesion;
	
	//constructor
	public LoginFrame() {
		setTitle("Iniciar Sesión");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800,400);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(96, 96, 96));
		setOpacity(1f);
		
		//Llama a constructor de objetos
		inicializarPanel();
	}

	private void inicializarPanel() {
		// TODO Auto-generated method stub
		
	}
	
}
