package videoteca.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.Period;

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
		private Color colorTexto = new Color(111, 130, 179);
		private Font fuenteTexto = new Font("Arial",Font.BOLD,20);
		private GestorBD gestor;
		private VentanaInicioFrame ventanaInicio;
		
		//constructor
		public RegistrarFrame(VentanaInicioFrame ventanaInicio) {
			
			//Gestor de la BD del Usuario
			this.gestor = new GestorBD();
			this.ventanaInicio = ventanaInicio;
			
			//Datos del Frame
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
			//añadir yyyy-mm-dd
			fechaNacimiento.setText("YYYY-MM-DD");
			// Listener para que inicie sesion al darle a Enter
			fechaNacimiento.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						registrarUsuario();
					}
				}
			});
			
			//botonRegistrarse
			botonIniciarSesion = new JButton();
			botonIniciarSesion.setBackground(colorTexto);
			botonIniciarSesion.setText("Registrarse");
			botonIniciarSesion.setFont(fuenteTexto);
			botonIniciarSesion.setBounds(310,350,220,50);
			botonIniciarSesion.addActionListener(e ->{
				registrarUsuario();
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

		private void registrarUsuario() {
			//Comprobar que no hay una sesion abierta
			Usuario us = ventanaInicio.usuario;
			if (!us.getNombre().isBlank()) {
				int opcion = JOptionPane.showConfirmDialog(botonIniciarSesion,
						"Ya hay una sesion abierta, desea cerrarla?",
						"Sesion existente",
		                JOptionPane.YES_NO_OPTION,
		                JOptionPane.QUESTION_MESSAGE);
				
		        if (opcion == JOptionPane.NO_OPTION) {
		        	this.dispose(); // Cerrar ventana
		        	return;
		        } 
			}
			
			//Comprobar que el usuario no existe ya
			String nombre = usuario.getText();
			if (gestor.existeUsuario(nombre)) {
				JOptionPane.showMessageDialog(null,"Este nombre de Usuario ya existe, inicia sesión o escoge otro","Usuario existente",JOptionPane.PLAIN_MESSAGE);
			}else {
				//Crear usuario e iniciar sesion
				if(usuario.getText().isBlank() && email.getText().isBlank() &&contraseña.getPassword().length == 0 && 
						repetirContraseña.getPassword().length == 0 && fechaNacimiento.getText().isBlank()) {
					JOptionPane.showMessageDialog(null,"Rellena todos los campos, por favor","Campos faltantes",JOptionPane.PLAIN_MESSAGE);
				}else if(!email.getText().contains("@") || !email.getText().contains(".")){
					JOptionPane.showMessageDialog(null,"Email no valido","Email invalido",JOptionPane.PLAIN_MESSAGE);
				}else if (!new String(contraseña.getPassword()).equals(new String(repetirContraseña.getPassword()))){
					JOptionPane.showMessageDialog(null,"Las dos contraseñas no son iguales","Contraseña incorrecta",JOptionPane.PLAIN_MESSAGE);
				}else {
					//Calcular edad
					LocalDate nacimiento= LocalDate.parse(fechaNacimiento.getText());
			        LocalDate hoy = LocalDate.now();
			        int edad = Period.between(nacimiento, hoy).getYears();
					
			        //Crear nuevo usuario
			        Usuario user= new Usuario(nombre, email.getText(), edad, nacimiento, new String(contraseña.getPassword()));
					gestor.registrarUsuario(user);
					JOptionPane.showMessageDialog(null,"Usuario "+usuario.getText()+" ha iniciado sesión","Sesión iniciada",JOptionPane.PLAIN_MESSAGE);
					//Cambiar usuario en la ventana Principal
					this.ventanaInicio.usuario = user;
					this.ventanaInicio.actualizarUsuario(user);
					this.dispose(); //elimina ventana de iniciar sesion
				}
			}
		}
		
	}

