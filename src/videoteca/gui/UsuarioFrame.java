package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.ImageIcon;
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


	public UsuarioFrame(Usuario usuario) {
		
		//Asignamos el usuario
		this.usuario = usuario;
		
		//Datos del JFrame
		this.setLayout(new BorderLayout());
		setTitle("Usuario");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800,500);
		setLocationRelativeTo(null);
		
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
		panelPelicula.setBackground(new Color(96, 96, 96));
		panelPelicula.setOpaque(true);
		panelPelicula.setVisible(false);

		
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
				
				//encontrar indice fila
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
		
		//cargar Peliculas
		cargarPeliculas();
		
		//adds
		panelCentral.add(scrollPanePeliculas,JPanel.TOP_ALIGNMENT);
		panelCentral.add(panelPelicula,JPanel.BOTTOM_ALIGNMENT);
        this.add(panelUsuario, BorderLayout.NORTH);
        this.add(panelCentral, BorderLayout.CENTER);

	}
	
	private void pintarDatosPelicula(Pelicula p) {
		
		//Label titulo
		//label director
		//label fecha estreno
		//boton reseñar
		//adds
		
	}

	private void ocultarDatosPelicula() {
		panelPelicula.setSize(new Dimension(0,0));
		panelPelicula.setVisible(false);
	}

	private void cargarPeliculas() {
		
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
