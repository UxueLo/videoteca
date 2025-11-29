package videoteca.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import videoteca.domain.Usuario;

public class PruebaSuerte extends JFrame{
	private Usuario usuario;
	
	public PruebaSuerte() {
		this.setSize(getMaximumSize());
		setTitle("Prueba Suerte");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		Image imagenFondo= (new ImageIcon("Images/fondo_dibujo_montaña.jpg")).getImage();
		JPanel panelPrincipal= new JPanel(new GridBagLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
			}
		};
		this.setContentPane(panelPrincipal);
		
		
		JButton botonJugar = new JButton("Probar Suerte");
		botonJugar.setPreferredSize(new Dimension(400, 200));
		botonJugar.setBackground(Color.white);
		botonJugar.setForeground(Color.green);
		botonJugar.setFont(new Font("Agency FB", Font.BOLD, 40));
		botonJugar.addActionListener(evt ->{
			usuario.incrementarIntentos();
			JOptionPane.showMessageDialog(
				    this, // Componente padre (normalmente 'this' o null)
				    "Te quedan "+ (3- usuario.getIntentosHechos()) +" intentos hoy", // El mensaje
				    "Intentos restantes", // Título de la ventana
				    JOptionPane.INFORMATION_MESSAGE // Icono que se muestra (un círculo azul)
				);
		});
		if (usuario.getIntentosHechos()>=3) {
			botonJugar.setEnabled(false);
		}
		
		//IAG
		// 2. CONFIGURACIÓN DEL CENTRADO USANDO GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        
        // weightx/weighty: Aseguran que el componente se centre en el espacio disponible.
        // Al darle un peso de 1.0, el layout sabe que el componente debe expandirse, 
        // pero como el botón tiene un tamaño preferido, solo se centrará en el área.
        gbc.weightx = 1.0; 
        gbc.weighty = 1.0; 
        
        // gridx/gridy: Lo colocamos en la posición (0, 0) de la cuadrícula, que es el centro si solo hay un elemento.
        gbc.gridx = 0; 
        gbc.gridy = 0;
        
        // 3. AÑADIR EL BOTÓN AL PANEL PRINCIPAL CON LAS RESTRICCIONES
		panelPrincipal.add(botonJugar, gbc); // <--- CLAVE 2: Pasar el objeto gbc
	}
	
	
}
