package videoteca.gui;
/*
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import videoteca.domain.Usuario;



public class PruebaSuerte extends JFrame{
	private Usuario usuario;
	
	public PruebaSuerte() {
		this.setSize(getMaximumSize());
		setTitle("Prueba Suerte");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		Image imagenFondo= (new ImageIcon("Images/fondo_dibujo_montaña.jpg")).getImage();
		JPanel panelPrincipal= new JPanel(new GridBagLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
			}
		};
		this.setContentPane(panelPrincipal);
		
		
		JButton botonJugar = new JButton("Probar Suerte");
		botonJugar.setPreferredSize(new Dimension(400, 200));
		botonJugar.setBackground(Color.white);
		botonJugar.setForeground(Color.green);
		botonJugar.setFont(new Font("Agency FB", Font.BOLD, 40));
		botonJugar.addActionListener(evt ->{
			usuario.incrementarIntentos();
			JOptionPane.showMessageDialog(
				    this, // Componente padre (normalmente 'this' o null)
				    "Te quedan "+ (3- usuario.getIntentosHechos()) +" intentos hoy", // El mensaje
				    "Intentos restantes", // Título de la ventana
				    JOptionPane.INFORMATION_MESSAGE // Icono que se muestra (un círculo azul)
				);
		});
		if (usuario.getIntentosHechos()>=3) {
			botonJugar.setEnabled(false);
		}
		
		//IAG
		// 2. CONFIGURACIÓN DEL CENTRADO USANDO GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        
        // weightx/weighty: Aseguran que el componente se centre en el espacio disponible.
        // Al darle un peso de 1.0, el layout sabe que el componente debe expandirse, 
        // pero como el botón tiene un tamaño preferido, solo se centrará en el área.
        gbc.weightx = 1.0; 
        gbc.weighty = 1.0; 
        
        // gridx/gridy: Lo colocamos en la posición (0, 0) de la cuadrícula, que es el centro si solo hay un elemento.
        gbc.gridx = 0; 
        gbc.gridy = 0;
        
        // 3. AÑADIR EL BOTÓN AL PANEL PRINCIPAL CON LAS RESTRICCIONES
		panelPrincipal.add(botonJugar, gbc); // <--- CLAVE 2: Pasar el objeto gbc
	}
	
	
}

*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.util.Timer;

/**
 * PruebaSuerte ahora recibe la lista de preguntas desde fuera (constructor).
 * No tiene método main.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.util.Timer;

public class PruebaSuerte extends JFrame {

    private JTable tablaOpciones;
    private JProgressBar barraTiempo;
    private JButton botonConfirmar;
    private JLabel preguntaLabel, tiempoLabel, descuentoLabel;
    private int tiempoRestante = 15;
    private Timer temporizador;
    private double dineroAcumulado = 0.0;
    private boolean respuestaConfirmada = false;
    private int preguntasEnsenadas = 0;

    private java.util.List<Pregunta> preguntas;
    private Pregunta preguntaActual;
    private int preguntaIndex = 0;

    private OpcionesTableModel tableModel;

    public PruebaSuerte() {
        inicializarPreguntas();
        configurarInterfaz();
        mostrarSiguientePregunta();
    }

    private void inicializarPreguntas() {
        preguntas = new ArrayList<>();
        preguntas.add(new Pregunta("¿Quién dirigió la película 'Matrix'?",
                new String[]{"Steven Spielberg", "James Cameron", "Lana Wachowski", "Christopher Nolan"}, 2));
        preguntas.add(new Pregunta("¿Qué película ganó el Oscar a Mejor Película en 2020?",
                new String[]{"1917", "Parasite", "Joker", "Once Upon a Time in Hollywood"}, 1));
        preguntas.add(new Pregunta("¿Quién interpreta a Tony Stark/Iron Man en el MCU?",
                new String[]{"Chris Evans", "Robert Downey Jr.", "Chris Hemsworth", "Mark Ruffalo"}, 1));
        preguntas.add(new Pregunta("¿En qué año se estrenó 'Star Wars: Una Nueva Esperanza'?",
                new String[]{"1975", "1977", "1980", "1983"}, 1));
        preguntas.add(new Pregunta("¿Qué director es conocido como 'El maestro del suspense'?",
                new String[]{"Alfred Hitchcock", "Stanley Kubrick", "Martin Scorsese", "Quentin Tarantino"}, 0));
        preguntas.add(new Pregunta("¿Qué película NO ha ganado un Oscar a la mejor película?",
                new String[]{"Anora", "Casablanca", "Moonlight", "La La Land"}, 3));
    }

    private void configurarInterfaz() {
        setTitle("Prueba tu suerte - Trivia Cinematográfica");
        setSize(600, 500);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (temporizador != null) {
                    temporizador.cancel();
                }
            }
        });

        // -- PANEL SUPERIOR --
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        preguntaLabel = new JLabel("", JLabel.CENTER);
        preguntaLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
        tiempoLabel = new JLabel("Tiempo: 15s");
        tiempoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        descuentoLabel = new JLabel("Descuento: 0%");
        descuentoLabel.setFont(new Font("Arial", Font.BOLD, 14));

        panelInfo.add(tiempoLabel);
        panelInfo.add(descuentoLabel);

        panelSuperior.add(preguntaLabel, BorderLayout.NORTH);
        panelSuperior.add(panelInfo, BorderLayout.SOUTH);
        add(panelSuperior, BorderLayout.NORTH);

        
        // -- TABLA --
        tableModel = new OpcionesTableModel();
        tablaOpciones = new JTable(tableModel);
        
        // Centrar las letras (la columna 0)
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        tablaOpciones.getColumnModel().getColumn(0).setCellRenderer(renderer);	

        tablaOpciones.setRowHeight(35);
        tablaOpciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaOpciones.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Añadir el key listener para que la tabla sea funcional con teclas
        tablaOpciones.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) confirmarRespuesta();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaOpciones);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Selecciona tu respuesta:"));
        add(scrollPane, BorderLayout.CENTER);

        
        // -- PANEL INFERIOR --
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));

        barraTiempo = new JProgressBar(0, tiempoRestante);
        barraTiempo.setStringPainted(true);

        botonConfirmar = new JButton("Confirmar respuesta (Enter)");
        botonConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
        botonConfirmar.addActionListener(e -> confirmarRespuesta());

        panelInferior.add(barraTiempo, BorderLayout.NORTH);
        panelInferior.add(botonConfirmar, BorderLayout.SOUTH);
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void mostrarSiguientePregunta() {
    	
    	if (preguntasEnsenadas != 6) {
    		if (preguntaIndex < preguntas.size()) {
    			preguntasEnsenadas += 1;
	            preguntaActual = preguntas.get(preguntaIndex);
	            preguntaLabel.setText((preguntaIndex + 1) + ". " + preguntaActual.getTexto());
	            
	            // Funcionamiento del temporizador y la barra de tiempo
	            tableModel.setOpciones(preguntaActual.getOpciones());
	            tiempoRestante = 15;
	            barraTiempo.setMaximum(tiempoRestante);
	            barraTiempo.setValue(tiempoRestante);
	            tiempoLabel.setText("Tiempo: " + tiempoRestante + "s");
	            respuestaConfirmada = false;
	            tablaOpciones.clearSelection();
	            iniciarTemporizador();
	            preguntaIndex++;
            
	        } else {
	            finDelJuego();
	        }
    	} else {
    		finDelJuego();
    	}
    	
        
    }

    private void iniciarTemporizador() {
        if (temporizador != null) temporizador.cancel();

        temporizador = new Timer();
        temporizador.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    if (!respuestaConfirmada) {
                        tiempoRestante--;
                        barraTiempo.setValue(tiempoRestante);
                        tiempoLabel.setText("Tiempo: " + tiempoRestante + "s");

                        if (tiempoRestante <= 0) {
                            temporizador.cancel();
                            tiempoAgotado();
                        }
                    }
                });
            }
        }, 1000, 1000);
    }

    private void tiempoAgotado() {
        JOptionPane.showMessageDialog(this, "¡Tiempo agotado! Pasando a la siguiente pregunta...");
        mostrarSiguientePregunta();
    }

    
    /**
     *  Confirma la respuesta del juego según la fila seleccionada.
     */
    private void confirmarRespuesta() {
        int filaSeleccionada = tablaOpciones.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una opción primero.");
            return;
        }

        respuestaConfirmada = true;
        temporizador.cancel();

        boolean correcta = (filaSeleccionada == preguntaActual.getRespuestaCorrecta());

        if (correcta) {
            double dinero = calcularDinero();
            dineroAcumulado += dinero;
            dineroAcumulado = Math.round(dineroAcumulado * 10.0) / 10.0;
            
            JOptionPane.showMessageDialog(this,
                    "¡Correcto!\n+ " + dinero + " €.\nDinero total acumulado en el juego: " + dineroAcumulado + " €");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Incorrecto.\nLa respuesta correcta era:\n" +
                    preguntaActual.getOpciones()[preguntaActual.getRespuestaCorrecta()]);
        }

        descuentoLabel.setText("Dinero ganado: " + dineroAcumulado + " €");
        mostrarSiguientePregunta();
    }

    
    /**
     *  Calcula el dinero ganado en cada pregunta según el tiempo restante.
     */
    private double calcularDinero() {
        if (tiempoRestante >= 12) return 0.5;
        if (tiempoRestante >= 8) return 0.4;
        if (tiempoRestante >= 5) return 0.3;
        if (tiempoRestante >= 3) return 0.2;
        return 0.1;
    }

    
    /**
     *  Método para finalizar el juego.
     */
    private void finDelJuego() {
        if (temporizador != null) temporizador.cancel();
        JOptionPane.showMessageDialog(this,
                "¡Juego terminado!\n\nDinero final: " + dineroAcumulado + " €");
        dispose();
    }

    class OpcionesTableModel extends AbstractTableModel {
        private String[] opciones = {};
        private final String[] columnNames = {"#", "Opción"};

        public void setOpciones(String[] opciones) {
            this.opciones = opciones;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() { return opciones.length; }
        @Override
        public int getColumnCount() { return columnNames.length; }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
        	
            return (columnIndex == 0) ? (char) ('A' + rowIndex) + "." : " " + opciones[rowIndex];
        }

        @Override
        public String getColumnName(int column) { return columnNames[column]; }
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }
    }

    static class Pregunta {
        private String texto;
        private String[] opciones;
        private int respuestaCorrecta;

        public Pregunta(String texto, String[] opciones, int respuestaCorrecta) {
            this.texto = texto;
            this.opciones = opciones;
            this.respuestaCorrecta = respuestaCorrecta;
        }

        public String getTexto() { return texto; }
        public String[] getOpciones() { return opciones; }
        public int getRespuestaCorrecta() { return respuestaCorrecta; }
    }
}

