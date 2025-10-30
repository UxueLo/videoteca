package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import videoteca.domain.Comentario;
import videoteca.domain.EdadRecomendada;
import videoteca.domain.Genero;
import videoteca.domain.Pelicula;
import videoteca.domain.Reserva;

public class VentanaPelicula extends JFrame {

	private Pelicula p;
	private JPanel panelPrincipal;	// Panel principal, en el centro pondremos toda la info de la pelicula
	private JPanel panelCentral;		// Informacion de la pelicula
	private JPanel panelCentralCentral;	// Informacion de la pelicula
	private JPanel panelCentralOeste;	// Informacion de la pelicula
	private JPanel panelSur;		// Panel de comentarios
	private JPanel panelSurSur;		// Panel de botones "alquiler" y "peliculas"
	private JPanel panelOeste;		// Panel de poster y puntuacion media
	private JPanel panelNorte;		// Panel del titulo
	private JButton alquilar;
	private JButton reseñar;
	private JTable comentarios;
	private DefaultTableModel modeloTablaComentarios;
	private JLabel poster;

	
	public VentanaPelicula (Pelicula p) {
		
		this.p = p;
		
		setTitle("Película");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		
		
		this.panelPrincipal = (JPanel) this.getContentPane();
		panelPrincipal.setBackground(new Color(32,32,32));
		panelPrincipal.setLayout(new BorderLayout());
		setResizable(false);
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		
		
		// Crear el PanelCentral
		this.panelCentral = new JPanel();
		panelCentral.setLayout(new BorderLayout());
		add(panelCentral, BorderLayout.CENTER);
		panelCentral.setBackground(Color.white);
		
		// Colocar la informacion
		// Director
		JLabel director = new JLabel("Director:");
		director.setFont(new Font("Arial", Font.BOLD, 25));
		director.setForeground(Color.white);
		
		JLabel directorEsp = new JLabel(p.getDirector());
		directorEsp.setFont(new Font("Arial", Font.PLAIN, 20));
		directorEsp.setForeground(Color.white);
		
		
		// Año
		JLabel año = new JLabel(Integer.toString(p.getAñoLanzamiento()));
		año.setFont(new Font("Arial", Font.ITALIC, 20));
		año.setForeground(Color.white);
		
		
		// Reparto
		JLabel reparto = new JLabel("Reparto:");
		reparto.setFont(new Font("Arial", Font.BOLD, 25));
		reparto.setForeground(Color.white);
		
		String listaReparto = "";
		for (String r : p.getReparto()) {								// Añadir cada actor a la lista de repartos siendo este un String
			if (r != p.getReparto().get(p.getReparto().size()-1)) {
				listaReparto = listaReparto + r + ", ";
			} else {
				listaReparto = listaReparto + r;
			}
			
		}
		
		JLabel repartoEsp = new JLabel(listaReparto);
		repartoEsp.setFont(new Font("Arial", Font.PLAIN, 20));
		repartoEsp.setForeground(Color.white);
		
		
		// Generos
		JLabel generos = new JLabel("Géneros:");
		generos.setFont(new Font("Arial", Font.BOLD, 25));
		generos.setForeground(Color.white);
		
		String listaGeneros = "";
		for (Genero g : p.getGeneros()) {								// Añadir cada genero a la lista de generos siendo este un String
			String genero = g.toString();
			if (g != p.getGeneros().get(p.getGeneros().size()-1)) {
				listaGeneros = listaGeneros + genero + ", ";
			} else {
				listaGeneros = listaGeneros + genero;
			}
			
		}
		
		JLabel generosEsp = new JLabel(listaGeneros);
		generosEsp.setFont(new Font("Arial", Font.PLAIN, 20));
		generosEsp.setForeground(Color.white);
		
		
		// Duracion
		JLabel duracion = new JLabel(Integer.toString(p.getDuracion()) + "min");
		duracion.setFont(new Font("Arial", Font.ITALIC, 20));
		duracion.setForeground(Color.white);
		
		
		// Añadir PanelCentralEste
		this.panelCentralOeste = new JPanel();
		panelCentralOeste.setLayout(new GridLayout(5, 1, 0, 5));
		panelCentral.add(panelCentralOeste, BorderLayout.WEST);
		panelCentralOeste.setBackground(panelPrincipal.getBackground());
		
		//adds
		panelCentralOeste.add(director);
		panelCentralOeste.add(reparto);
		panelCentralOeste.add(generos);
		panelCentralOeste.add(año);
		panelCentralOeste.add(duracion);
		
		this.panelCentralCentral = new JPanel();
		panelCentralCentral.setLayout(new GridLayout(5, 1, 0, 5));
		panelCentralCentral.setBackground(panelPrincipal.getBackground());
		panelCentral.add(panelCentralCentral, BorderLayout.CENTER);
		
		panelCentralCentral.add(directorEsp);
		panelCentralCentral.add(repartoEsp);
		panelCentralCentral.add(generosEsp);			// TODO arreglar
		panelCentralCentral.add(new JLabel(""));
		panelCentralCentral.add(new JLabel(""));
		
		
		
		// Crear el PanelNorte desde el central, donde estara el titulo
		this.panelNorte = new JPanel();
		panelCentral.add(panelNorte, BorderLayout.NORTH);
		JLabel titulo = new JLabel();
		titulo.setText(p.getTitulo());
		titulo.setFont(new Font("Arial", Font.BOLD, 40));
		titulo.setBackground(panelPrincipal.getBackground());
		titulo.setForeground(Color.white);
		panelNorte.setBackground(panelPrincipal.getBackground());
		
		panelNorte.add(titulo);
		
		
		
		// Crear el PanelOeste, donde estara el poster y la puntuacion media
		this.panelOeste = new JPanel();
		panelOeste.setBackground(panelPrincipal.getBackground());
		add(panelOeste, BorderLayout.WEST);
		panelOeste.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));
		this.poster = new JLabel();												
		poster.setOpaque(true);
		poster.setBackground(panelPrincipal.getBackground());
		
		// Colocar el poster
		Image imagenEscalada = p.getPoster().getImage();
		imagenEscalada = imagenEscalada.getScaledInstance(250, 325, Image.SCALE_SMOOTH);
		ImageIcon posterEscalado = new ImageIcon(imagenEscalada);
		poster.setIcon(posterEscalado);
		
		// Puntuacion media
		poster.setText(Double.toString(p.getPuntuacionMedia()));
		poster.setFont(new Font("Arial", Font.BOLD, 30));
		
		// Condicional que pinta el numero de la puntuacion media segun esta
		if (p.getPuntuacionMedia() >= 8) {
			poster.setForeground(Color.green);			// Si la puntuacion mayor o igual a 8, pinta de verde
		} else if(p.getPuntuacionMedia() >= 5) {
			poster.setForeground(Color.yellow);			// Si la puntuacion mayor o igual a 5, pinta de amarillo
		} else {
			poster.setForeground(Color.red);			// Si la puntuacion menor a 5, pinta de rojo
		}

		poster.setHorizontalTextPosition(JLabel.CENTER);
		poster.setVerticalTextPosition(JLabel.BOTTOM);
		
		poster.setOpaque(true);
		panelOeste.add(poster, BorderLayout.CENTER);
		
		
		
		// Crear el PanelSur, donde estaran los comentarios
		this.panelSur = new JPanel();
		panelSur.setLayout(new BorderLayout());
		panelSur.setBackground(panelPrincipal.getBackground());
		add(panelSur, BorderLayout.SOUTH);
		panelSur.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		
		
		
		
		
		// TODO Comentarios
		this.modeloTablaComentarios = new DefaultTableModel() {
			  @Override
			    public boolean isCellEditable(int row, int column) {
			        return false;
			    }
		};
		
		
		this.comentarios = new JTable(modeloTablaComentarios);
		comentarios.setSize(10, 10);
		
		// Scroll
		JScrollPane scrollComentarios = new JScrollPane(comentarios);
		scrollComentarios.getVerticalScrollBar().setUnitIncrement(16);
		scrollComentarios.setBorder(new TitledBorder("Reseñas: "));
		
		panelSur.add(scrollComentarios, BorderLayout.CENTER);
		
		
		comentarios.setBackground(new Color(212,212,212));
		comentarios.setForeground(panelPrincipal.getBackground());
		
		// Cargar comentarios
		modeloTablaComentarios.setColumnIdentifiers(new Object[] {
	            "VALORACIÓN", "COMENTARIO"
	        });
		
		ArrayList<Comentario> listaComentarios = p.getComentarios();
		for(Comentario c : listaComentarios) {
			modeloTablaComentarios.addRow(new Object[] {Double.toString(c.getValoracion()),c.getTexto()} );
		}
		
		comentarios.setRowHeight(30);				// Agrandar la altura de cada fila de la tabla
		comentarios.getColumnModel().getColumn(0).setMaxWidth(300);		// Hacer que la columna de "valoraciones" sea mas pequeña
		comentarios.getColumnModel().getColumn(0).setMinWidth(300);
		comentarios.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {{ setHorizontalAlignment(javax.swing.JLabel.CENTER); }});		// Hacer que los numeros de las valoraciones se vean en el centro
		
		
		
		
		
		// Crear el PanelSurSur, colocado al sur del PanelSur, donde estaran los botones "alquilar" y "añadir"
		this.panelSurSur = new JPanel();
		panelSurSur.setLayout(new GridLayout(1,2,20,20));
		panelSurSur.setBackground(panelPrincipal.getBackground());
		panelSur.add(panelSurSur, BorderLayout.SOUTH);
		panelSurSur.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		
		
		// Crear ambos botones y ajustar color
		this.alquilar = new JButton("Alquilar");
		this.alquilar.setBackground(new Color(153,0,0));
		this.alquilar.setFont(new Font("Arial", Font.BOLD, 35));
		
		this.reseñar = new JButton("Reseñar");
		this.reseñar.setBackground(new Color(153,0,0));
		this.reseñar.setFont(new Font("Arial", Font.BOLD, 35));
		this.reseñar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Abrir VentanaReseña
				VentanaReseña ventanaReseña = new VentanaReseña(p);
				ventanaReseña.setVisible(true);
			}
		});
		
		panelSurSur.add(alquilar);
		panelSurSur.add(reseñar);
		
		

	}
	
}
