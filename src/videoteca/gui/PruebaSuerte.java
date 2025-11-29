package videoteca.gui;


import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PruebaSuerte extends JFrame{
	
	
	public PruebaSuerte() {
		this.setSize(getMaximumSize());
		setTitle("Prueba Suerte");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//TODO EL FONDO DE CASINO
		Image imagenFondo= (new ImageIcon("/Imagenes/fondo_dibujo_montaña.jpg")).getImage();
		JPanel panelPrincipal= new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
			}
		};
	}
	
	
}
