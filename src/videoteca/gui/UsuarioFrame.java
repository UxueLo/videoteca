package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
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
import javax.swing.table.DefaultTableModel;

import videoteca.domain.Usuario;

public class UsuarioFrame extends JFrame{
	private Usuario usuario;
	private JPanel panelUsuario;
	private JTable tablaPeliculas;
	private Color colorTexto = new Color(204,0,0);
	private Font fuenteTexto = new Font("Times New Roman",Font.BOLD,30);
	private DefaultTableModel modeloDatosPeliculas;

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
		panelUsuario.setPreferredSize(new Dimension(800, 150));

		
		inicializarPanelUsuario();
		
		//JTables central
		tablaPeliculas = new JTable();
		
		//Insertar tabla Peliculas en un scrollPane
		JScrollPane scrollPanePeliculas = new JScrollPane(this.tablaPeliculas);
		scrollPanePeliculas.setBorder(new TitledBorder("Tus Reservas: "));
		
		inicializarTablaPeliculas();
		
		//cargar Peliculas
		cargarPeliculas();
		
		//adds
        this.add(panelUsuario, BorderLayout.NORTH);
        this.add(scrollPanePeliculas, BorderLayout.CENTER);

	}

	private void cargarPeliculas() {
		//Se borran los datos del modelo de datos
		this.modeloDatosPeliculas.setRowCount(0);
		//Se añaden los comics uno a uno al modelo de datos
		this.usuario.getPeliculasAlquiladas().forEach(r -> this.modeloDatosPeliculas.addRow(
				new Object[] {r.getPelicula().getDirector(),r.getPelicula().getGeneros(),""} )
		);

	}

	private void inicializarTablaPeliculas() {
		
		//Cabecera del modelo de datos
		Vector<String> cabeceraPeliculas = new Vector<String>(Arrays.asList( "TITULO", "DIRECTOR", "GENERO", "RESEÑAR"));
		//Se crea el modelo de datos para la tabla de peliculas sólo con la cabecera
		this.modeloDatosPeliculas = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceraPeliculas);
		//Se crea la tabla de peliculas con el modelo de datos
		this.tablaPeliculas = new JTable(this.modeloDatosPeliculas);
		//Se modifica el modelo de selección de la tabla para que se pueda selecciona únicamente una fila
		this.tablaPeliculas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//Se define el comportamiento el evento de selección de una fila de la tabla
		this.tablaPeliculas.getSelectionModel().addListSelectionListener(e -> {
			//Cuando se selecciona una fila, se actualiza la tabla de personajes
			
		});
		
	}

	private void inicializarPanelUsuario() {
		
		//Icono usuario
		ImageIcon iconoUsuario = new ImageIcon("Images/iconoUsuario.png");
		JLabel labelIconoUsuario = new JLabel();
		labelIconoUsuario.setIcon(iconoUsuario);
		labelIconoUsuario.setBounds(10,5,70,70);
		
		//Labels datos usuario
		JLabel holaUsuario = new JLabel("Hola! "+this.usuario.getNombre());
		holaUsuario.setBounds(300,20,250,40);
		holaUsuario.setForeground(colorTexto);
		holaUsuario.setFont(fuenteTexto);
		JLabel saldoUsuario = new JLabel("Saldo: "+this.usuario.getSaldo());
		saldoUsuario.setBounds(300,80,250,40);
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
