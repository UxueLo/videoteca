package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import videoteca.domain.Comentario;
import videoteca.domain.Genero;
import videoteca.domain.Pelicula;
import videoteca.domain.Reserva;
import videoteca.domain.Usuario;

import videoteca.io.GestionImagenes;

public class VentanaPelicula extends JFrame {

	private Pelicula p;
	private Usuario usuario;
	
	private JPanel panelPrincipal;	// Panel principal, en el centro pondremos toda la info de la pelicula
	private JPanel panelCentral;		// Informacion de la pelicula
	private JPanel panelCentralCentral;	// Informacion de la pelicula
	private JPanel panelCentralOeste;	// Informacion de la pelicula
	private JPanel panelCentralEste; 	// Pondra si la pelicula esta en el carrito o no
	private JPanel panelSur;		// Panel de comentarios
	private JPanel panelSurSur;		// Panel de botones "alquiler" y "peliculas"
	private JPanel panelOeste;		// Panel de poster y puntuacion media
	private JPanel panelNorte;		// Panel del titulo
	private JButton alquilar;
	private JButton reseñar;
	private JTable comentarios;
	private int numCom = 0;			// Contador del numero de comentarios de la pelicula
	private DefaultTableModel modeloTablaComentarios;
	private JLabel poster;
	private ArrayList<Pelicula> carrito;
	private JLabel mensaje;		// Nos dira si la pelicula esta alquilada/en el carrito o no
	private int filaTablaComentarios=-1;
	
	public VentanaPelicula (Pelicula p, Usuario usuario) {
		
		this.usuario = usuario;
		this.p = p;
		this.usuario=usuario;
		
		setIconImage(new ImageIcon("Images/logo.png").getImage());
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
		director.setFont(new Font("Arial", Font.BOLD, 30));
		director.setForeground(Color.white);
		
		JLabel directorEsp = new JLabel(p.getDirector());
		directorEsp.setFont(new Font("Arial", Font.PLAIN, 30));
		directorEsp.setForeground(Color.white);
		
		
		// Año
		JLabel año = new JLabel(Integer.toString(p.getAñoLanzamiento()));
		año.setFont(new Font("Arial", Font.ITALIC, 30));
		año.setForeground(Color.white);
		
		
		// Reparto
		JLabel reparto = new JLabel("Reparto:");
		reparto.setFont(new Font("Arial", Font.BOLD, 30));
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
		repartoEsp.setFont(new Font("Arial", Font.PLAIN, 30));
		repartoEsp.setForeground(Color.white);
		
		
		// Generos
		JLabel generos = new JLabel("Géneros:");
		generos.setFont(new Font("Arial", Font.BOLD, 30));
		generos.setForeground(Color.white);
		
		String listaGeneros = "";
		for (Genero g : p.getGeneros()) {								// Añadir cada genero a la lista de generos siendo este un String
			String genero = g.toString().toLowerCase().substring(0, 1).toUpperCase();
			String generoMayMin = genero + g.toString().toLowerCase().substring(1);
			if (g == Genero.CIENCIA_FICCION) {
				if (g != p.getGeneros().get(p.getGeneros().size()-1)) {
					listaGeneros = listaGeneros + " Ciencia ficción,";
				} else {
					listaGeneros = listaGeneros + " Ciencia ficción";
				}
				
			}else {
				if (g != p.getGeneros().get(p.getGeneros().size()-1)) {
			
				listaGeneros = listaGeneros + " " + generoMayMin + ",";
				} else {
					listaGeneros = listaGeneros + " " + generoMayMin;
				}
			}
		}
		
		JLabel generosEsp = new JLabel(listaGeneros);
		generosEsp.setFont(new Font("Arial", Font.PLAIN, 30));
		generosEsp.setForeground(Color.white);
		
		
		// Duracion
		JLabel duracion = new JLabel(Integer.toString(p.getDuracion()) + "min");
		duracion.setFont(new Font("Arial", Font.ITALIC, 30));
		duracion.setForeground(Color.white);
		
		
		// Añadir PanelCentralOeste
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
		panelCentralCentral.add(generosEsp);
		panelCentralCentral.add(new JLabel(""));
		panelCentralCentral.add(new JLabel(""));
		
		
		
		// Añadir PanelCentralEste
		this.panelCentralEste = new JPanel();
		panelCentralEste.setBackground(panelPrincipal.getBackground());
		this.mensaje = new JLabel();
		mensaje.setFont(new Font("Arial", Font.BOLD, 40));
		mensaje.setOpaque(true);
		
		actualizarAlquilado();
		
		panelCentral.add(panelCentralEste, BorderLayout.EAST);
		panelCentralEste.add(mensaje);
		
		
		
		// Crear el PanelNorte desde el central, donde estara el titulo
		this.panelNorte = new JPanel();
		panelCentral.add(panelNorte, BorderLayout.NORTH);
		JLabel titulo = new JLabel();
		titulo.setText(p.getTitulo());
		titulo.setFont(new Font("Garamond", Font.BOLD, 55));
		titulo.setBackground(panelPrincipal.getBackground());
		titulo.setForeground(new Color(111, 130, 179));
		panelNorte.setBackground(panelPrincipal.getBackground());
		panelNorte.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		
		panelNorte.add(titulo);
		
		
		
		// Crear el PanelOeste, donde estara el poster y la puntuacion media
		this.panelOeste = new JPanel();
		panelOeste.setBackground(panelPrincipal.getBackground());
		add(panelOeste, BorderLayout.WEST);
		panelOeste.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));

		// Cargar poster
		this.poster = GestionImagenes.cargarImagen(p.getTitulo(), 250, 375);
		poster.setBackground(panelPrincipal.getBackground());
		poster.setOpaque(true);
		
		// Puntuación Media
		JLabel lblPuntuacion = new JLabel(String.format("%.1f", p.getPuntuacionMedia()));
		lblPuntuacion.setFont(new Font("Arial", Font.BOLD, 40));
		lblPuntuacion.setHorizontalAlignment(JLabel.CENTER);

		// Color según puntuación
		if (p.getPuntuacionMedia() >= 8) { // Si la puntuación es mayor o igual a 8, pinta de verde
		    lblPuntuacion.setForeground(Color.GREEN); 
		} else if (p.getPuntuacionMedia() >= 5) { // Si la puntuación es mayor o igual a 5, pinta de amarillo
		    lblPuntuacion.setForeground(Color.YELLOW);
		} else { // Si la puntuación es menor a 5, pinta de rojo
		    lblPuntuacion.setForeground(Color.RED);
		}

		// Añadir poster y puntuación al panel oeste, uno encima del otro
		panelOeste.setLayout(new BorderLayout(0, 10));
		panelOeste.add(poster, BorderLayout.CENTER);
		panelOeste.add(lblPuntuacion, BorderLayout.SOUTH);
		
		
		// Crear el PanelSur, donde estaran los comentarios
		this.panelSur = new JPanel();
		panelSur.setLayout(new BorderLayout());
		panelSur.setBackground(panelPrincipal.getBackground());
		add(panelSur, BorderLayout.SOUTH);
		panelSur.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		
		
		
		this.modeloTablaComentarios = new DefaultTableModel() {
			  @Override
			    public boolean isCellEditable(int row, int column) {
			        return false;
			    }
		};
		
		
		this.comentarios = new JTable(modeloTablaComentarios);
		comentarios.setSize(10, 10);
		comentarios.setDefaultRenderer(Object.class, new miRendererComentarios());
		MouseMotionListener miMouseMotionListener = new MouseMotionListener() {
		    @Override
		    public void mouseMoved(MouseEvent e) {
		        Point puntoRaton = new Point(e.getX(), e.getY());
		        // Detecta en qué fila está el ratón
		        filaTablaComentarios = comentarios.rowAtPoint(puntoRaton);
		        // Fuerza el repintado para aplicar el color
		        comentarios.repaint();
		    } 
		    
		    @Override
		    public void mouseDragged(MouseEvent e) {
		        // nada
		    }
		};
		MouseAdapter miMouseAdapter = new MouseAdapter() {
		    public void mouseExited(MouseEvent e) {
		        filaTablaComentarios = -1;  // Resetea cuando el ratón entra
		        comentarios.repaint(); // Repinta para quitar el color
		    }
		};
		comentarios.addMouseMotionListener(miMouseMotionListener);
		comentarios.addMouseListener(miMouseAdapter);
		
		// Scroll
		JScrollPane scrollComentarios = new JScrollPane(comentarios);
		scrollComentarios.getVerticalScrollBar().setUnitIncrement(16);
		scrollComentarios.setBorder(new TitledBorder("Reseñas: "));
		scrollComentarios.setPreferredSize(new Dimension(0, 350));
		
		panelSur.add(scrollComentarios, BorderLayout.CENTER);
		
		
		comentarios.setBackground(new Color(212,212,212));
		comentarios.setForeground(panelPrincipal.getBackground());
		
		actualizarComentarios();
		
		
		
		// Crear el PanelSurSur, colocado al sur del PanelSur, donde estaran los botones "alquilar" y "añadir"
		this.panelSurSur = new JPanel();
		panelSurSur.setLayout(new GridLayout(1,1,20,20));
		panelSurSur.setBackground(panelPrincipal.getBackground());
		panelSur.add(panelSurSur, BorderLayout.SOUTH);
		panelSurSur.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		
		
		// Crear ambos botones y ajustar color
		this.alquilar = new JButton("Alquilar");
		this.alquilar.setBackground(new Color(112, 124, 156));
		this.alquilar.setFont(new Font("Arial", Font.BOLD, 35));
		this.alquilar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Meter la pelicula en el carrito
				if (!usuario.getCarritoUsuario().contains(p)) {
					usuario.getPeliculasAlquiladas().add(new Reserva(p, LocalDate.now()));
					usuario.getCarritoUsuario().add(p);
					//Mensaje de confirmacion
					JOptionPane.showMessageDialog(null,"Película añadida al carrito","Película añadida",JOptionPane.PLAIN_MESSAGE);
					actualizarAlquilado();
				} else {
					JOptionPane.showMessageDialog(null,"La película ya está añadida al carrito","Película ya añadida",JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		
		this.reseñar = new JButton("Reseñar");
		this.reseñar.setBackground(new Color(112, 124, 156));
		this.reseñar.setFont(new Font("Arial", Font.BOLD, 35));
		this.reseñar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Abrir VentanaReseña
				abrirReseña();
			}

			private void abrirReseña() {
				VentanaReseña ventanaReseña = new VentanaReseña(p, usuario,VentanaPelicula.this);
				ventanaReseña.setVisible(true);
				
			}
		});
		
		
		panelSurSur.add(alquilar);
		panelSurSur.add(reseñar);
		
		

	}
	
	
	public void actualizarComentarios() {
		
		// Cargar comentarios
		
		if (modeloTablaComentarios.getRowCount() == 0) {					// si la tabla esta vacia
			modeloTablaComentarios.setColumnIdentifiers(new Object[] {
		            "VALORACIÓN", "COMENTARIO"
		        });
			
			ArrayList<Comentario> listaComentarios = p.getComentarios();
			for(Comentario c : listaComentarios) {
				this.numCom = numCom + 1;
				modeloTablaComentarios.addRow(new Object[] {Double.toString(c.getValoracion()),c.getTexto()} );
			}
			
			comentarios.setRowHeight(30);									// Agrandar la altura de cada fila de la tabla
			comentarios.getColumnModel().getColumn(0).setMaxWidth(300);		// Hacer que la columna de valoraciones sea mas pequeña
			comentarios.getColumnModel().getColumn(0).setMinWidth(300);
		} else {
			Comentario comAñadido = p.getComentarios().get(p.getComentarios().size()-1);
			modeloTablaComentarios.addRow(new Object[] {Double.toString(comAñadido.getValoracion()),comAñadido.getTexto()} );	// Añadir la ultima reseña a la tabla
		}
			
		comentarios.repaint();		// actualizar la tabla

	}
	
	
	public void actualizarAlquilado() {
		if (usuario.getCarritoUsuario().contains(p)) {
			mensaje.setText(" En el carrito ");
			mensaje.setBackground(Color.white);
		} else {
			mensaje.setText("");
			mensaje.setBackground(panelPrincipal.getBackground());
		}
		mensaje.repaint();
	}
	
	private class miRendererComentarios extends DefaultTableCellRenderer{
		public miRendererComentarios() {
			
		}
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if(column==0) { //si es la columna de valoracion
				setHorizontalAlignment(JLabel.CENTER);
				setFont(new Font("Arial", Font.BOLD, 18));
				double valoracion = Double.parseDouble(value.toString());
	            if (valoracion <= 2.0) {
	                setForeground(Color.red);
	            } else if (valoracion == 3.0) {
	                setForeground(Color.orange);
	            } else if (valoracion >= 4.0) {
	                setForeground(Color.green); 
	            }	
			}else { //si es la columna de texto
				setHorizontalAlignment(JLabel.LEFT);
				setForeground(Color.black);
			}
			
			if (filaTablaComentarios!=-1 && row == filaTablaComentarios && !isSelected) { //si esta dentro de la tabla & si la fila es en la que esta el raton & si esta seleccionado (pata que no se quite el color de seleccionado)
				setBackground(new Color(85, 85, 107));
				if(column==1) {
					setForeground(Color.white);
				}
			}else {
	            if (isSelected) {
	                setBackground(new Color(111, 130, 179));
	                if (column==1) {
	                	 setForeground(Color.black);
	                }
	            } else {
	            	setBackground(Color.white);
	            }
	        }
			return this;
		}
	}
}
