package videoteca.io;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GestionImagenes {

    public static JLabel cargarImagen(String titulo, int ancho, int alto) {
        JLabel lblImg = new JLabel();
        lblImg.setPreferredSize(new Dimension(ancho, alto));

        String ruta = "images/" + titulo + ".jpg";
        File archivo = new File(ruta);
        ImageIcon icon = archivo.exists()
                ? new ImageIcon(ruta)
                : new ImageIcon("images/default.jpg");

        Image imagenEscalada = icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        lblImg.setIcon(new ImageIcon(imagenEscalada));

        return lblImg;
    }
}

