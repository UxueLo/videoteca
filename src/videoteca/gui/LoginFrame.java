package videoteca.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
		// Listener para que inicie sesion al darle a Enter en contraseña
		contraseña.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					iniciarSesion();
				}
			}
		});
		
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
	    String nombre = usuario.getText().trim(); // trim() elimina espacios
	    String pass = new String(contraseña.getPassword());
	    
	    // Verificar que los campos no están vacíos
	    if (nombre.isBlank() || pass.isBlank()) {
	        JOptionPane.showMessageDialog(this, 
	            "Rellena todos los campos, por favor", 
	            "Campos vacíos", 
	            JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	    
	    // Verificar si el usuario existe
	    if (!gestor.existeUsuario(nombre)) {
	        JOptionPane.showMessageDialog(this, 
	            "Para crear cuenta vete a Registrarme", 
	            "Usuario no existente", 
	            JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	    
	    // Intentar obtener el usuario
	    Usuario user = gestor.obtenerUsuario(nombre, pass);
	    
	    if (user != null) {
	        // Login exitoso
	        this.ventanaInicio.usuario = user;
	        this.ventanaInicio.actualizarUsuario(user);
	        JOptionPane.showMessageDialog(this, 
	            "Usuario " + nombre + " ha iniciado sesión", 
	            "Sesión iniciada", 
	            JOptionPane.INFORMATION_MESSAGE);
	        this.dispose();
	    } else {
	        // Contraseña incorrecta
	        JOptionPane.showMessageDialog(this, 
	            "La contraseña es incorrecta", 
	            "Error de autenticación", 
	            JOptionPane.ERROR_MESSAGE);
	    }
	}
	
}
