package videoteca.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import videoteca.domain.Usuario;
import videoteca.jdbc.GestorBD;

public class LoginFrame extends JFrame{
	
	//private JLabel textoIniciarSesion;
	private JLabel textoIniciarSesion;
	private JLabel labelUsuario;
	private JLabel labelContraseña;
	private JTextField usuario;
	private JPasswordField contraseña;
	private JButton botonInicarSesion;
	private Color colorTexto = new Color(111, 130, 179);
	private Font fuenteTexto = new Font("Arial",Font.BOLD,20);
	private GestorBD gestor;
	private VentanaInicioFrame ventanaInicio;
	
	//constructor
	public LoginFrame(VentanaInicioFrame ventanaInicio) {
		
		//Gestor de la BD del Usuario
		this.gestor = new GestorBD();
		this.ventanaInicio = ventanaInicio;
		
		setTitle("Iniciar Sesión");
		setSize(800,500);
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("Images/logo.png").getImage());
		
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
		
		//Crea variable local panelPrincipal
		JPanel panelPrincipal = (JPanel) getContentPane();
		
		//textoIniciarSesion
		textoIniciarSesion = new JLabel();
		textoIniciarSesion.setText("INICIAR SESION");
		textoIniciarSesion.setForeground(colorTexto);
		textoIniciarSesion.setFont(fuenteTexto);
		textoIniciarSesion.setBounds(0,30,800,50);;
		textoIniciarSesion.setHorizontalAlignment(JLabel.CENTER);
		
		//labelUsuario
		labelUsuario = new JLabel();
		labelUsuario.setText("Usuario: ");
		labelUsuario.setForeground(colorTexto);
		labelUsuario.setFont(fuenteTexto);
		labelUsuario.setBounds(80,120,150,40);;
		
		//usuario
		usuario = new JTextField();
		usuario.setForeground(colorTexto);
		usuario.setFont(fuenteTexto);
		usuario.setBounds(240,120,480,40);
		
		//labelContraseña
		labelContraseña = new JLabel();
		labelContraseña.setText("Contraseña: ");
		labelContraseña.setForeground(colorTexto);
		labelContraseña.setFont(fuenteTexto);
		labelContraseña.setBounds(80,200,200,40);;
		
		//contraseña
		contraseña = new JPasswordField();
		contraseña.setForeground(colorTexto);
		contraseña.setFont(fuenteTexto);
		contraseña.setBounds(240,200,480,40);
		
		//botonIniciarSesion
		botonInicarSesion = new JButton();
		botonInicarSesion.setBackground(colorTexto);
		botonInicarSesion.setText("Iniciar Sesión");
		botonInicarSesion.setFont(fuenteTexto);
		botonInicarSesion.setBounds(290,290,220,60);
		botonInicarSesion.addActionListener(e ->{
			iniciarSesion();
		});
		
		
		//adds
		panelPrincipal.add(textoIniciarSesion);
		panelPrincipal.add(labelUsuario);
		panelPrincipal.add(usuario);
		panelPrincipal.add(labelContraseña);
		panelPrincipal.add(contraseña);
		panelPrincipal.add(botonInicarSesion);
	}

	private void iniciarSesion() {
		//Comprobar que el usuario no existe ya
		String nombre = usuario.getText();
		if (!gestor.existeUsuario(nombre)) {
			JOptionPane.showMessageDialog(null,"Para crear cuenta vete a Registrarme","Usuario no existente",JOptionPane.PLAIN_MESSAGE);
		}else {
			if(!usuario.getText().isBlank() && contraseña.getPassword().length > 0) {
				try {
					//cambiar usuario del resto de ventanas
					Usuario user = gestor.obtenerUsuario(nombre,new String(contraseña.getPassword()));
					this.ventanaInicio.usuario = user;
					this.ventanaInicio.actualizarUsuario(user);
					this.dispose(); //elimina ventana de iniciar sesion
					//mensaje correcto
					JOptionPane.showMessageDialog(null,"Usuario "+usuario.getText()+" ha iniciado sesión","Sesión iniciada",JOptionPane.PLAIN_MESSAGE);
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null,"Algo ha salido mal" ,"No se ha podido iniciar sesion",JOptionPane.PLAIN_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(null,"Rellena todos los campos, por favor","Iniciar Sesion",JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	
}
