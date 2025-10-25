package videoteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import videoteca.domain.Usuario;

public class UsuarioFrame extends JFrame{
	private Usuario usuario;
	private JPanel panelUsuario;
	private JTable tablaPeliculas;
	private Color colorTexto = new Color(204,0,0);
	private Font fuenteTexto = new Font("Times New Roman",Font.BOLD,20);

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
		panelUsuario.setPreferredSize(new Dimension(800, 100));

		
		inicializarPanelUsuario();
		
		//JTables central
		tablaPeliculas = new JTable();
		
		//Insertar tabla Peliculas en un scrollPane
		JScrollPane scrollPanePeliculas = new JScrollPane(this.tablaPeliculas);
		scrollPanePeliculas.setBorder(new TitledBorder("Peliculas"));
		
		inicializarTablaPeliculas();
		
		//adds
        this.add(panelUsuario, BorderLayout.NORTH);
        this.add(scrollPanePeliculas, BorderLayout.CENTER);

	}

	private void inicializarTablaPeliculas() {
		// TODO Auto-generated method stub
		
	}

	private void inicializarPanelUsuario() {
		
		//Icono usuario
		ImageIcon iconoUsuario = new ImageIcon("Images/iconoUsuario.jpg");
		JLabel labelIconoUsuario = new JLabel();
		labelIconoUsuario.setIcon(iconoUsuario);
		labelIconoUsuario.setBounds(10,5,50,50);
		
		//Labels datos usuario
		JLabel holaUsuario = new JLabel("Hola! "+this.usuario.getNombre());
		holaUsuario.setBounds(350,10,200,20);
		holaUsuario.setForeground(colorTexto);
		holaUsuario.setFont(fuenteTexto);
		JLabel saldoUsuario = new JLabel("Saldo: "+this.usuario.getSaldo());
		saldoUsuario.setBounds(350,50,200,20);
		saldoUsuario.setForeground(colorTexto);
		saldoUsuario.setFont(fuenteTexto);
		
		//adds
		this.panelUsuario.add(labelIconoUsuario);
		this.panelUsuario.add(holaUsuario);
		this.panelUsuario.add(saldoUsuario);
		
	}
	
}
