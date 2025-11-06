package videoteca.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegistrarFrame extends JFrame{
	//private JLabel textoRegistrarse;
		private JLabel textoRegistrarse;
		private JLabel labelUsuario;
		private JLabel labelEmail;
		private JLabel labelContraseña;
		private JLabel labelRepetirContraseña;
		private JLabel labelFechaNacimiento;
		private JTextField usuario;
		private JTextField email;
		private JPasswordField contraseña;
		private JPasswordField repetirContraseña;
		private JTextField fechaNacimiento; //Cambiar por calendario 
		private JButton botonIniciarSesion;
		private Color colorTexto = new Color(204,0,0);
		private Font fuenteTexto = new Font("Arial",Font.BOLD,20);
		
		//constructor
		public RegistrarFrame() {
			setTitle("Registrarse");
			setSize(800,500);
			setLocationRelativeTo(null);
			
			//panel principal
			JPanel panelPrincipal = new JPanel();
			panelPrincipal.setLayout(null);
			panelPrincipal.setBackground(new Color(96, 96, 96));
			panelPrincipal.setOpaque(true);
			setContentPane(panelPrincipal);
			setIconImage(new ImageIcon("Images/logo.png").getImage());
			
			//Llama a constructor de objetos
			inicializarPanel();
		}

		private void inicializarPanel() {
			
			//Crea variable local panelPrincipal
			JPanel panelPrincipal = (JPanel) getContentPane();
			
			//textoRegistrarse
			textoRegistrarse = new JLabel();
			textoRegistrarse.setText("REGISTRARSE");
			textoRegistrarse.setForeground(colorTexto);
			textoRegistrarse.setFont(fuenteTexto);
			textoRegistrarse.setBounds(0,20,800,50);;
			textoRegistrarse.setHorizontalAlignment(JLabel.CENTER);
			
			//labelUsuario
			labelUsuario = new JLabel();
			labelUsuario.setText("Usuario: ");
			labelUsuario.setForeground(colorTexto);
			labelUsuario.setFont(fuenteTexto);
			labelUsuario.setBounds(80,90,150,30);;
			
			//usuario
			usuario = new JTextField();
			usuario.setForeground(colorTexto);
			usuario.setFont(fuenteTexto);
			usuario.setBounds(310,90,430,35);
			
			//labelEmail
			labelEmail = new JLabel();
			labelEmail.setText("Email: ");
			labelEmail.setForeground(colorTexto);
			labelEmail.setFont(fuenteTexto);
			labelEmail.setBounds(80,140,150,30);;
			
			//email
			email = new JTextField();
			email.setForeground(colorTexto);
			email.setFont(fuenteTexto);
			email.setBounds(310,140,430,35);
			
			//labelContraseña
			labelContraseña = new JLabel();
			labelContraseña.setText("Contraseña: ");
			labelContraseña.setForeground(colorTexto);
			labelContraseña.setFont(fuenteTexto);
			labelContraseña.setBounds(80,190,200,30);;
			
			//contraseña
			contraseña = new JPasswordField();
			contraseña.setForeground(colorTexto);
			contraseña.setFont(fuenteTexto);
			contraseña.setBounds(310,190,430,35);
			
			//labelRepetirContraseña
			labelRepetirContraseña = new JLabel();
			labelRepetirContraseña.setText("Repetir Contraseña: ");
			labelRepetirContraseña.setForeground(colorTexto);
			labelRepetirContraseña.setFont(fuenteTexto);
			labelRepetirContraseña.setBounds(80,240,220,30);
			
			//repetirContraseña
			repetirContraseña = new JPasswordField();
			repetirContraseña.setForeground(colorTexto);
			repetirContraseña.setFont(fuenteTexto);
			repetirContraseña.setBounds(310,240,430,35);
			
			//labelfechaNacimiento
			labelFechaNacimiento = new JLabel();
			labelFechaNacimiento.setText("Fecha Nacimiento: ");
			labelFechaNacimiento.setForeground(colorTexto);
			labelFechaNacimiento.setFont(fuenteTexto);
			labelFechaNacimiento.setBounds(80,290,230,30);;
			
			//fechaNacimiento
			fechaNacimiento = new JTextField();
			fechaNacimiento.setForeground(colorTexto);
			fechaNacimiento.setFont(fuenteTexto);
			fechaNacimiento.setBounds(310,290,430,35);
			
			//botonRegistrarse
			botonIniciarSesion = new JButton();
			botonIniciarSesion.setBackground(colorTexto);
			botonIniciarSesion.setText("Registrarse");
			botonIniciarSesion.setFont(fuenteTexto);
			botonIniciarSesion.setBounds(310,350,220,50);
			botonIniciarSesion.addActionListener(e ->{
				if(usuario.getText().isBlank() && email.getText().isBlank() &&contraseña.getPassword().length == 0 && 
						repetirContraseña.getPassword().length == 0 && fechaNacimiento.getText().isBlank()) {
					System.err.println("Rellena todos los campos, por favor");
				}else if(!email.getText().contains("@") || !email.getText().contains(".")){
					System.err.println("Email no valido");
				}else if (contraseña.getPassword() != repetirContraseña.getPassword()) {
					System.err.println("Las dos contraseñas no son iguales");
				}else {
					System.out.println("Usuario "+usuario.getText()+" ha iniciado sesión");
				}
				
			});
			
			
			//adds
			panelPrincipal.add(textoRegistrarse);
			panelPrincipal.add(labelUsuario);
			panelPrincipal.add(usuario);
			panelPrincipal.add(labelEmail);
			panelPrincipal.add(email);
			panelPrincipal.add(labelContraseña);
			panelPrincipal.add(contraseña);
			panelPrincipal.add(labelRepetirContraseña);
			panelPrincipal.add(repetirContraseña);
			panelPrincipal.add(labelFechaNacimiento);
			panelPrincipal.add(fechaNacimiento);
			panelPrincipal.add(botonIniciarSesion);
		}
		
	}

