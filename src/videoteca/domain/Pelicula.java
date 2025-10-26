package videoteca.domain;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Pelicula {
	
	private String titulo;
	private String director;
	private ArrayList<String> reparto;
	private ArrayList<Genero> generos;
	private int añoLanzamiento;
	private int duracion;
	private ImageIcon poster;
	private EdadRecomendada edadRecomendada;
	private double puntuacionMedia;
	private double precio;
	// Faltan comentarios
	
	
	public Pelicula(String titulo, String director, ArrayList<String> reparto, ArrayList<Genero> generos,
			int añoLanzamiento, int duracion, EdadRecomendada edadRecomendada, double puntuacionMedia, double precio) {
		
		super();
		this.poster=new ImageIcon("Images/Your Name.jpg");
		this.titulo = titulo;
		this.director = director;
		this.reparto = reparto;
		this.generos = generos;
		this.añoLanzamiento = añoLanzamiento;
		this.duracion = duracion;
		this.edadRecomendada = edadRecomendada;
		this.puntuacionMedia = puntuacionMedia;
		this.precio = precio;
		
	}
	

	public ImageIcon getPoster() {
		return poster;
	}


	public void setPoster(ImageIcon poster) {
		this.poster = poster;
	}


	public double getPuntuacionMedia() {
		return puntuacionMedia;
	}

	public void setPuntuacionMedia(double puntuacionMedia) {
		this.puntuacionMedia = puntuacionMedia;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDirector() {
		return director;
	}

	public ArrayList<String> getReparto() {
		return reparto;
	}

	public ArrayList<Genero> getGeneros() {
		return generos;
	}

	public int getAñoLanzamiento() {
		return añoLanzamiento;
	}

	public int getDuracion() {
		return duracion;
	}

	public EdadRecomendada getEdadRecomendada() {
		return edadRecomendada;
	}

	public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
	@Override
	public String toString() {
		return "Pelicula [titulo=" + titulo + ", director=" + director + ", reparto=" + reparto + ", generos=" + generos
				+ ", añoLanzamiento=" + añoLanzamiento + ", duracion=" + duracion + ", poster=" + poster
				+ ", edadRecomendada=" + edadRecomendada + ", puntuacionMedia=" + puntuacionMedia + "]";
	}

	
	
}
