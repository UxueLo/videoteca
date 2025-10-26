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
import javax.swing.table.DefaultTableModel;

import videoteca.domain.Reserva;
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
		
		//crear table model
		DefaultTableModel modeloTablaPeliculas = new DefaultTableModel() {
			  @Override
			    public boolean isCellEditable(int row, int column) {
			        return false;
			    }
		};
		modeloTablaPeliculas.setColumnIdentifiers(new Vector<String>(Arrays.asList("Título","Fecha Reserva","Fecha Caducidad")));
		
		//JTables central
		tablaPeliculas = new JTable(modeloTablaPeliculas);
		
		//Insertar tabla Peliculas en un scrollPane
		JScrollPane scrollPanePeliculas = new JScrollPane(this.tablaPeliculas);
		scrollPanePeliculas.setBorder(new TitledBorder("Tus Reservas: "));
		
		//cargar Peliculas
		cargarPeliculas();
		
		//adds
        this.add(panelUsuario, BorderLayout.NORTH);
        this.add(scrollPanePeliculas, BorderLayout.CENTER);

	}

	private void cargarPeliculas() {
		
		ArrayList<Reserva> listaReservas = usuario.getPeliculasAlquiladas();
		for(Reserva r : listaReservas) {
			modeloDatosPeliculas.addRow(new Object[] {r.getPelicula().getTitulo(),r.getFechaReserva(),r.getFechaDevolucion()} );
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
