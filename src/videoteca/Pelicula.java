package videoteca;

import java.util.ArrayList;

public class Pelicula {
	
	private String titulo;
	private String director;
	private ArrayList<String> reparto;
	private ArrayList<Genero> generos;
	private int añoLanzamiento;
	private int duracion;
	private int edadRecomendada;
	private double puntuacionMedia;
	
	public Pelicula(String titulo, String director, ArrayList<String> reparto, ArrayList<Genero> generos,
			int añoLanzamiento, int duracion, int edadRecomendada, double puntuacionMedia) {
		
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

	public int getEdadRecomendada() {
		return edadRecomendada;
	}
	
	
	
	
	
}
