package videoteca.gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaInicioFrame extends JFrame{
	public VentanaInicioFrame() {
		setTitle("Ventana de Inicio"); //titulo
		setDefaultCloseOperation(EXIT_ON_CLOSE); //parar al cerrar
		setExtendedState(JFrame.MAXIMIZED_BOTH); //abrirlo en pantalla completa
		setResizable(false); //siempre estara en pantalla completa
		getContentPane().setBackground(new Color(64,64,64)); //cambiar el fondo
		setVisible(true);
	}
}
