package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import videoteca.domain.Pelicula;
import videoteca.domain.Reserva;
import videoteca.domain.Usuario;

public class UsuarioFrame extends JFrame{
	private Usuario usuario;
	private JPanel panelUsuario;
	private JPanel panelCentral;
	private JPanel panelPelicula;
	private JTable tablaPeliculas;
	private Color colorTexto = new Color(204,0,0);
	private Font fuenteTexto = new Font("Times New Roman",Font.BOLD,30);
	private DefaultTableModel modeloTablaPeliculas;
	private JLabel labelTitulo;
	private JLabel labelTituloPelicula;
	private JLabel labelDirector;
	private JLabel labelDirectorPelicula;
	private JLabel labelFechaEstreno;
	private JButton botonReseñar;



	public UsuarioFrame(Usuario usuario) {
		
		//Asignamos el usuario al Frame
		this.usuario = usuario;
		
		//Datos del JFrame
		this.setLayout(new BorderLayout());
		setTitle("Usuario");
		setSize(800,500);
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("Images/logo.png").getImage());
		
		//panel usuario
		panelUsuario = new JPanel();
		panelUsuario.setLayout(null);
		panelUsuario.setBackground(new Color(96, 96, 96));
		panelUsuario.setOpaque(true);
		panelUsuario.setPreferredSize(new Dimension(800, 115));
		
		inicializarPanelUsuario();
		
		//Panel central
		panelCentral = new JPanel();
		panelCentral.setLayout(null);
		panelCentral.setBackground(new Color(96, 96, 96));
		panelCentral.setOpaque(true);
		panelCentral.setPreferredSize(new Dimension(500, 500));
		
		//panel Pelicula
		panelPelicula = new JPanel();
		panelPelicula.setLayout(null);
		panelPelicula.setBackground(new Color(128,128,128));
		panelPelicula.setOpaque(true);
		panelPelicula.setBounds(20, 240, 740, 85);

		
		//crear table model
		this.modeloTablaPeliculas = new DefaultTableModel() {
			  @Override
			    public boolean isCellEditable(int row, int column) {
			        return false;
			    }
		};
		
		
		//JTables central
		tablaPeliculas = new JTable(modeloTablaPeliculas);
		
		//Accion para añadir los datos de la pelicula
		tablaPeliculas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				// Evitar duplicados
				if (e.getValueIsAdjusting()) {
					return;
				}
				
				int filaSeleccionada = tablaPeliculas.getSelectedRow();
				
				//Verificar que la fila existe
				if(filaSeleccionada != -1) {
					//obtener titulo
					String titulo= (String) tablaPeliculas.getValueAt(filaSeleccionada, 0);
					
					//obtener pelicula de ese titulo
					for(Reserva r : usuario.getPeliculasAlquiladas()) {
						Pelicula p = r.getPelicula();
						if(p.getTitulo().equals(titulo)) {
							pintarDatosPelicula(p);
						}
					}
				}else {
					ocultarDatosPelicula();
					
				}
			}
		});
		
		//Insertar tabla Peliculas en un scrollPane
		JScrollPane scrollPanePeliculas = new JScrollPane(this.tablaPeliculas);
		scrollPanePeliculas.setBorder(new TitledBorder("Tus Reservas: "));
		scrollPanePeliculas.setBounds(20, 20, 740, 200);
		
		//cargar Peliculas
		cargarPeliculas();
		
		//adds
		panelCentral.add(scrollPanePeliculas);
		panelCentral.add(panelPelicula);
        this.add(panelUsuario, BorderLayout.NORTH);
        this.add(panelCentral, BorderLayout.CENTER);

	}
	
	private void pintarDatosPelicula(Pelicula p) {
		
		panelPelicula.setVisible(true);
		panelPelicula.removeAll();
		Font fuenteDatos = new Font("Times New Roman",Font.BOLD,20);
		
		Font fuenteTituloDato = new Font("Times New Roman", Font.BOLD, 14); 
	    Font fuenteValorDato = new Font("Times New Roman", Font.PLAIN, 16); 

	    Color colorDescripcion = Color.BLACK; 
	    Color colorValor = colorTexto;        

	    // labelTitulo
	    labelTitulo= new JLabel("Título:");
	    labelTitulo.setForeground(colorDescripcion);
	    labelTitulo.setFont(fuenteTituloDato);
	    labelTitulo.setBounds(20, 5, 200, 20);

	    // labeltituloPelicula
	    labelTituloPelicula = new JLabel(p.getTitulo());
	    labelTituloPelicula.setForeground(colorValor);
	    labelTituloPelicula.setFont(fuenteValorDato);
	    labelTituloPelicula.setBounds(20, 25, 200, 30);

	    // labelDirector
	    labelDirector = new JLabel("Director:");
	    labelDirector.setForeground(colorDescripcion);
	    labelDirector.setFont(fuenteTituloDato);
	    labelDirector.setBounds(250, 5, 200, 20);

	    // labelDirectorPelicula
	    labelDirectorPelicula = new JLabel(p.getDirector());
	    labelDirectorPelicula.setForeground(colorValor);
	    labelDirectorPelicula.setFont(fuenteValorDato);
	    labelDirectorPelicula.setBounds(250, 25, 200, 30);

	    // labelFecha
	    JLabel labelFecha = new JLabel("Año:");
	    labelFecha.setForeground(colorDescripcion);
	    labelFecha.setFont(fuenteTituloDato);
	    labelFecha.setBounds(480, 5, 80, 20);

	    // labelFechaEstreno
	    labelFechaEstreno = new JLabel(String.valueOf(p.getAñoLanzamiento()));
	    labelFechaEstreno.setForeground(colorValor);
	    labelFechaEstreno.setFont(fuenteValorDato);
	    labelFechaEstreno.setBounds(480, 25, 80, 30);
		
		//boton reseñar
		botonReseñar = new JButton();
		botonReseñar.setBackground(colorTexto);
		botonReseñar.setText("Reseñar");
		botonReseñar.setFont(fuenteDatos);
		botonReseñar.setBounds(600, 25, 120, 30);
		botonReseñar.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				//nada
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				VentanaPelicula vp = new VentanaPelicula(p, usuario);
				VentanaReseña reseña= new VentanaReseña(p, vp);
				reseña.setVisible(true);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				botonReseñar.setBackground(colorTexto);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				botonReseñar.setBackground(new Color(158,0,0));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// nada
				
			}
		});
		
		//adds
	    panelPelicula.add(labelTitulo);
	    panelPelicula.add(labelTituloPelicula);
	    panelPelicula.add(labelDirector);
	    panelPelicula.add(labelDirectorPelicula);
	    panelPelicula.add(labelFecha);
	    panelPelicula.add(labelFechaEstreno);
	    panelPelicula.add(botonReseñar);
		//volver a pintar panel
		panelPelicula.revalidate();
		panelPelicula.repaint();
		
	}

	private void ocultarDatosPelicula() {
		panelPelicula.setVisible(false);
	}

	private void cargarPeliculas() {
	    modeloTablaPeliculas.setColumnIdentifiers(new Object[] {
	            "Título", "Fecha de Reserva", "Fecha de Devolución"
	        });
		
		ArrayList<Reserva> listaReservas = usuario.getPeliculasAlquiladas();
		for(Reserva r : listaReservas) {
			modeloTablaPeliculas.addRow(new Object[] {r.getPelicula().getTitulo(),r.getFechaReserva(),r.getFechaDevolucion()} );
		}

	}

	private void inicializarPanelUsuario() {
		
		//Icono usuario
		ImageIcon iconoUsuario = new ImageIcon("Images/iconoUsuario.png");
		JLabel labelIconoUsuario = new JLabel();
		labelIconoUsuario.setIcon(iconoUsuario);
		labelIconoUsuario.setBounds(10,5,70,70);
		
		//Labels datos usuario
		JLabel holaUsuario = new JLabel("Hola! "+this.usuario.getNombre());
		holaUsuario.setBounds(300,10,250,40);
		holaUsuario.setForeground(colorTexto);
		holaUsuario.setFont(fuenteTexto);
		JLabel saldoUsuario = new JLabel("Saldo: "+this.usuario.getSaldo());
		saldoUsuario.setBounds(300,60,250,40);
		saldoUsuario.setForeground(colorTexto);
		saldoUsuario.setBackground(new Color(128,128,128));
		saldoUsuario.setOpaque(true);
		saldoUsuario.setFont(fuenteTexto);
		
		//adds
		this.panelUsuario.add(labelIconoUsuario);
		this.panelUsuario.add(holaUsuario);
		this.panelUsuario.add(saldoUsuario);
		
	}
	
}