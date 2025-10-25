package videoteca.domain;

import java.util.ArrayList;

public class Pelicula {
	
	private String titulo;
	private String director;
	private ArrayList<String> reparto;
	private ArrayList<Genero> generos;
	private int añoLanzamiento;
	private int duracion;
	private EdadRecomendada edadRecomendada;
	private double puntuacionMedia;
	// Faltan comentarios
	
	
	public Pelicula(String titulo, String director, ArrayList<String> reparto, ArrayList<Genero> generos,
			int añoLanzamiento, int duracion, EdadRecomendada edadRecomendada, double puntuacionMedia) {
		
		super();
		this.titulo = titulo;
		this.director = director;
		this.reparto = reparto;
		this.generos = generos;
		this.añoLanzamiento = añoLanzamiento;
		this.duracion = duracion;
		this.edadRecomendada = edadRecomendada;
		this.puntuacionMedia = puntuacionMedia;
		
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

	@Override
	public String toString() {
		return "Pelicula [titulo=" + titulo + ", director=" + director + ", reparto=" + reparto + ", generos=" + generos
				+ ", añoLanzamiento=" + añoLanzamiento + ", duracion=" + duracion + ", edadRecomendada="
				+ edadRecomendada + ", puntuacionMedia=" + puntuacionMedia + "]";
	}
	
	
}
