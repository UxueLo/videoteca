package videoteca.gui;

import java.awt.Color;
import java.awt.Font;

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
		private JButton botonInicarSesion;
		private Color colorTexto = new Color(204,0,0);
		private Font fuenteTexto = new Font("Times New Roman",Font.BOLD,20);
		
		//constructor
		public RegistrarFrame() {
			setTitle("Registrarse");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setSize(800,500);
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
			
			//Crea variable local panelPrincipal
			JPanel panelPrincipal = (JPanel) getContentPane();
			
			//textoRegistrarse
			textoRegistrarse = new JLabel();
			textoRegistrarse.setText("REGISTRARSE");
			textoRegistrarse.setForeground(colorTexto);
			textoRegistrarse.setFont(fuenteTexto);
			textoRegistrarse.setBounds(0,40,800,50);;
			textoRegistrarse.setHorizontalAlignment(JLabel.CENTER);
			
			//labelUsuario
			labelUsuario = new JLabel();
			labelUsuario.setText("Usuario: ");
			labelUsuario.setForeground(colorTexto);
			labelUsuario.setFont(fuenteTexto);
			labelUsuario.setBounds(120,120,150,30);;
			
			//usuario
			usuario = new JTextField();
			usuario.setForeground(colorTexto);
			usuario.setFont(fuenteTexto);
			usuario.setBounds(350,120,430,35);
			
			//labelEmail
			labelEmail = new JLabel();
			labelEmail.setText("Email: ");
			labelEmail.setForeground(colorTexto);
			labelEmail.setFont(fuenteTexto);
			labelEmail.setBounds(120,175,150,30);;
			
			//email
			email = new JTextField();
			email.setForeground(colorTexto);
			email.setFont(fuenteTexto);
			email.setBounds(350,175,430,35);
			
			//labelContraseña
			labelContraseña = new JLabel();
			labelContraseña.setText("Contraseña: ");
			labelContraseña.setForeground(colorTexto);
			labelContraseña.setFont(fuenteTexto);
			labelContraseña.setBounds(120,230,200,30);;
			
			//contraseña
			contraseña = new JPasswordField();
			contraseña.setForeground(colorTexto);
			contraseña.setFont(fuenteTexto);
			contraseña.setBounds(350,230,430,35);
			
			//labelRepetirContraseña
			labelRepetirContraseña = new JLabel();
			labelRepetirContraseña.setText("Repetir Contraseña: ");
			labelRepetirContraseña.setForeground(colorTexto);
			labelRepetirContraseña.setFont(fuenteTexto);
			labelRepetirContraseña.setBounds(120,285,220,20);
			
			//repetirContraseña
			repetirContraseña = new JPasswordField();
			repetirContraseña.setForeground(colorTexto);
			repetirContraseña.setFont(fuenteTexto);
			repetirContraseña.setBounds(350,285,430,35);
			
			//labelfechaNacimiento
			labelFechaNacimiento = new JLabel();
			labelFechaNacimiento.setText("Fecha Nacimiento: ");
			labelFechaNacimiento.setForeground(colorTexto);
			labelFechaNacimiento.setFont(fuenteTexto);
			labelFechaNacimiento.setBounds(120,340,230,30);;
			
			//fechaNacimiento
			fechaNacimiento = new JTextField();
			fechaNacimiento.setForeground(colorTexto);
			fechaNacimiento.setFont(fuenteTexto);
			fechaNacimiento.setBounds(350,340,430,35);
			
			//botonRegistrarse
			botonInicarSesion = new JButton();
			botonInicarSesion.setBackground(colorTexto);
			botonInicarSesion.setText("Registrarse");
			botonInicarSesion.setFont(fuenteTexto);
			botonInicarSesion.setBounds(290,405,220,50);
			botonInicarSesion.addActionListener(e ->{
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
			panelPrincipal.add(botonInicarSesion);
		}
		
	}

