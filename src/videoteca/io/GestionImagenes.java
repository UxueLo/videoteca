package videoteca.io;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GestionImagenes {
	
	public static JLabel cargarImagen(String titulo, int ancho, int alto) {
        JLabel lblImg = new JLabel();
        lblImg.setPreferredSize(new Dimension(ancho, alto));

        String ruta = "images/" + titulo + ".jpg";
        File archivo = new File(ruta);
        ImageIcon icon;
        
        if (archivo.exists()) {
            icon = new ImageIcon(ruta);
        } else {
            icon = new ImageIcon("images/default.jpg");
        }

        Image imagenEscalada = icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        lblImg.setIcon(new ImageIcon(imagenEscalada));

        return lblImg;
    }

}
