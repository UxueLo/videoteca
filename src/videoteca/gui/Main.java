package videoteca.gui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import videoteca.domain.EdadRecomendada;
import videoteca.domain.Genero;
import videoteca.domain.Pelicula;
import videoteca.domain.Usuario;

public class Main {

	public static void main(String[] args) {
		//Lista creada por DeepSeek
		// Crear lista de 50 películas
		// Crear lista de 50 películas
		ArrayList<Pelicula> peliculas = new ArrayList<>();

		// Películas 1-10
		peliculas.add(new Pelicula("El Padrino", "Francis Ford Coppola", new ArrayList<>(Arrays.asList("Marlon Brando", "Al Pacino", "James Caan")), new ArrayList<>(Arrays.asList(Genero.DRAMA, Genero.CRIMEN)), 1972, 175, EdadRecomendada.PLUS18, 9.2));
		peliculas.add(new Pelicula("Pulp Fiction", "Quentin Tarantino", new ArrayList<>(Arrays.asList("John Travolta", "Uma Thurman", "Samuel L. Jackson")), new ArrayList<>(Arrays.asList(Genero.CRIMEN, Genero.DRAMA)), 1994, 154, EdadRecomendada.PLUS18, 8.9));
		peliculas.add(new Pelicula("El Señor de los Anillos", "Peter Jackson", new ArrayList<>(Arrays.asList("Elijah Wood", "Ian McKellen", "Viggo Mortensen")), new ArrayList<>(Arrays.asList(Genero.AVENTURA, Genero.FANTASIA)), 2001, 178, EdadRecomendada.PLUS13, 8.8));
		peliculas.add(new Pelicula("Matrix", "Lana Wachowski", new ArrayList<>(Arrays.asList("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss")), new ArrayList<>(Arrays.asList(Genero.CIENCIA_FICCION, Genero.ACCION)), 1999, 136, EdadRecomendada.PLUS16, 8.7));
		peliculas.add(new Pelicula("Forrest Gump", "Robert Zemeckis", new ArrayList<>(Arrays.asList("Tom Hanks", "Robin Wright", "Gary Sinise")), new ArrayList<>(Arrays.asList(Genero.DRAMA, Genero.COMEDIA)), 1994, 142, EdadRecomendada.PLUS13, 8.8));
		peliculas.add(new Pelicula("Star Wars", "George Lucas", new ArrayList<>(Arrays.asList("Mark Hamill", "Harrison Ford", "Carrie Fisher")), new ArrayList<>(Arrays.asList(Genero.CIENCIA_FICCION, Genero.AVENTURA)), 1977, 121, EdadRecomendada.TP, 8.6));
		peliculas.add(new Pelicula("Inception", "Christopher Nolan", new ArrayList<>(Arrays.asList("Leonardo DiCaprio", "Marion Cotillard", "Tom Hardy")), new ArrayList<>(Arrays.asList(Genero.CIENCIA_FICCION, Genero.ACCION)), 2010, 148, EdadRecomendada.PLUS13, 8.8));
		peliculas.add(new Pelicula("El Caballero Oscuro", "Christopher Nolan", new ArrayList<>(Arrays.asList("Christian Bale", "Heath Ledger", "Aaron Eckhart")), new ArrayList<>(Arrays.asList(Genero.ACCION, Genero.DRAMA)), 2008, 152, EdadRecomendada.PLUS13, 9.0));
		peliculas.add(new Pelicula("Titanic", "James Cameron", new ArrayList<>(Arrays.asList("Leonardo DiCaprio", "Kate Winslet", "Billy Zane")), new ArrayList<>(Arrays.asList(Genero.DRAMA, Genero.ROMACE)), 1997, 195, EdadRecomendada.PLUS13, 7.8));
		peliculas.add(new Pelicula("Jurassic Park", "Steven Spielberg", new ArrayList<>(Arrays.asList("Sam Neill", "Laura Dern", "Jeff Goldblum")), new ArrayList<>(Arrays.asList(Genero.AVENTURA, Genero.CIENCIA_FICCION)), 1993, 127, EdadRecomendada.PLUS13, 8.1));

		// Películas 11-20
		peliculas.add(new Pelicula("Avatar", "James Cameron", new ArrayList<>(Arrays.asList("Sam Worthington", "Zoe Saldana", "Sigourney Weaver")), new ArrayList<>(Arrays.asList(Genero.CIENCIA_FICCION, Genero.AVENTURA)), 2009, 162, EdadRecomendada.PLUS13, 7.8));
		peliculas.add(new Pelicula("Gladiador", "Ridley Scott", new ArrayList<>(Arrays.asList("Russell Crowe", "Joaquin Phoenix", "Connie Nielsen")), new ArrayList<>(Arrays.asList(Genero.ACCION, Genero.DRAMA)), 2000, 155, EdadRecomendada.PLUS16, 8.5));
		peliculas.add(new Pelicula("Cadena Perpetua", "Frank Darabont", new ArrayList<>(Arrays.asList("Tim Robbins", "Morgan Freeman", "Bob Gunton")), new ArrayList<>(Arrays.asList(Genero.DRAMA)), 1994, 142, EdadRecomendada.PLUS16, 9.3));
		peliculas.add(new Pelicula("El Rey León", "Roger Allers", new ArrayList<>(Arrays.asList("Matthew Broderick", "James Earl Jones", "Jeremy Irons")), new ArrayList<>(Arrays.asList(Genero.ANIMACION, Genero.AVENTURA)), 1994, 88, EdadRecomendada.TP, 8.5));
		peliculas.add(new Pelicula("Volver al Futuro", "Robert Zemeckis", new ArrayList<>(Arrays.asList("Michael J. Fox", "Christopher Lloyd", "Lea Thompson")), new ArrayList<>(Arrays.asList(Genero.CIENCIA_FICCION, Genero.COMEDIA)), 1985, 116, EdadRecomendada.TP, 8.5));
		peliculas.add(new Pelicula("Psicosis", "Alfred Hitchcock", new ArrayList<>(Arrays.asList("Anthony Perkins", "Janet Leigh", "Vera Miles")), new ArrayList<>(Arrays.asList(Genero.TERROR, Genero.THRILLER)), 1960, 109, EdadRecomendada.PLUS16, 8.5));
		peliculas.add(new Pelicula("Casablanca", "Michael Curtiz", new ArrayList<>(Arrays.asList("Humphrey Bogart", "Ingrid Bergman", "Paul Henreid")), new ArrayList<>(Arrays.asList(Genero.DRAMA, Genero.ROMACE)), 1942, 102, EdadRecomendada.TP, 8.5));
		peliculas.add(new Pelicula("Apocalypse Now", "Francis Ford Coppola", new ArrayList<>(Arrays.asList("Martin Sheen", "Marlon Brando", "Robert Duvall")), new ArrayList<>(Arrays.asList(Genero.DRAMA, Genero.GUERRA)), 1979, 153, EdadRecomendada.PLUS18, 8.4));
		peliculas.add(new Pelicula("Blade Runner", "Ridley Scott", new ArrayList<>(Arrays.asList("Harrison Ford", "Rutger Hauer", "Sean Young")), new ArrayList<>(Arrays.asList(Genero.CIENCIA_FICCION)), 1982, 117, EdadRecomendada.PLUS16, 8.1));
		peliculas.add(new Pelicula("Alien", "Ridley Scott", new ArrayList<>(Arrays.asList("Sigourney Weaver", "Tom Skerritt", "John Hurt")), new ArrayList<>(Arrays.asList(Genero.CIENCIA_FICCION, Genero.TERROR)), 1979, 117, EdadRecomendada.PLUS16, 8.4));

		// Películas 21-30
		peliculas.add(new Pelicula("Terminator", "James Cameron", new ArrayList<>(Arrays.asList("Arnold Schwarzenegger", "Linda Hamilton", "Michael Biehn")), new ArrayList<>(Arrays.asList(Genero.CIENCIA_FICCION, Genero.ACCION)), 1984, 107, EdadRecomendada.PLUS16, 8.0));
		peliculas.add(new Pelicula("Indiana Jones", "Steven Spielberg", new ArrayList<>(Arrays.asList("Harrison Ford", "Karen Allen", "Paul Freeman")), new ArrayList<>(Arrays.asList(Genero.AVENTURA, Genero.ACCION)), 1981, 115, EdadRecomendada.TP, 8.4));
		peliculas.add(new Pelicula("Seven", "David Fincher", new ArrayList<>(Arrays.asList("Brad Pitt", "Morgan Freeman", "Kevin Spacey")), new ArrayList<>(Arrays.asList(Genero.CRIMEN, Genero.THRILLER)), 1995, 127, EdadRecomendada.PLUS18, 8.6));
		peliculas.add(new Pelicula("Fight Club", "David Fincher", new ArrayList<>(Arrays.asList("Brad Pitt", "Edward Norton", "Helena Bonham Carter")), new ArrayList<>(Arrays.asList(Genero.DRAMA)), 1999, 139, EdadRecomendada.PLUS18, 8.8));
		peliculas.add(new Pelicula("El Resplandor", "Stanley Kubrick", new ArrayList<>(Arrays.asList("Jack Nicholson", "Shelley Duvall", "Danny Lloyd")), new ArrayList<>(Arrays.asList(Genero.TERROR)), 1980, 146, EdadRecomendada.PLUS16, 8.4));
		peliculas.add(new Pelicula("Braveheart", "Mel Gibson", new ArrayList<>(Arrays.asList("Mel Gibson", "Sophie Marceau", "Patrick McGoohan")), new ArrayList<>(Arrays.asList(Genero.DRAMA, Genero.HISTORICA)), 1995, 178, EdadRecomendada.PLUS16, 8.3));
		peliculas.add(new Pelicula("Salvar al Soldado Ryan", "Steven Spielberg", new ArrayList<>(Arrays.asList("Tom Hanks", "Matt Damon", "Tom Sizemore")), new ArrayList<>(Arrays.asList(Genero.DRAMA, Genero.GUERRA)), 1998, 169, EdadRecomendada.PLUS16, 8.6));
		peliculas.add(new Pelicula("La Lista de Schindler", "Steven Spielberg", new ArrayList<>(Arrays.asList("Liam Neeson", "Ralph Fiennes", "Ben Kingsley")), new ArrayList<>(Arrays.asList(Genero.DRAMA, Genero.HISTORICA)), 1993, 195, EdadRecomendada.PLUS16, 8.9));
		peliculas.add(new Pelicula("El Silencio de los Corderos", "Jonathan Demme", new ArrayList<>(Arrays.asList("Jodie Foster", "Anthony Hopkins", "Scott Glenn")), new ArrayList<>(Arrays.asList(Genero.CRIMEN, Genero.THRILLER)), 1991, 118, EdadRecomendada.PLUS18, 8.6));
		peliculas.add(new Pelicula("Regreso al Futuro", "Robert Zemeckis", new ArrayList<>(Arrays.asList("Michael J. Fox", "Christopher Lloyd", "Lea Thompson")), new ArrayList<>(Arrays.asList(Genero.CIENCIA_FICCION, Genero.COMEDIA)), 1985, 116, EdadRecomendada.TP, 8.5));

		// Películas 31-40
		peliculas.add(new Pelicula("Harry Potter", "Chris Columbus", new ArrayList<>(Arrays.asList("Daniel Radcliffe", "Emma Watson", "Rupert Grint")), new ArrayList<>(Arrays.asList(Genero.FANTASIA, Genero.AVENTURA)), 2001, 152, EdadRecomendada.PLUS7, 7.6));
		peliculas.add(new Pelicula("Los Vengadores", "Joss Whedon", new ArrayList<>(Arrays.asList("Robert Downey Jr.", "Chris Evans", "Scarlett Johansson")), new ArrayList<>(Arrays.asList(Genero.ACCION, Genero.CIENCIA_FICCION)), 2012, 143, EdadRecomendada.PLUS13, 8.0));
		peliculas.add(new Pelicula("Interestelar", "Christopher Nolan", new ArrayList<>(Arrays.asList("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain")), new ArrayList<>(Arrays.asList(Genero.CIENCIA_FICCION, Genero.DRAMA)), 2014, 169, EdadRecomendada.PLUS13, 8.6));
		peliculas.add(new Pelicula("Toy Story", "John Lasseter", new ArrayList<>(Arrays.asList("Tom Hanks", "Tim Allen", "Don Rickles")), new ArrayList<>(Arrays.asList(Genero.ANIMACION, Genero.COMEDIA)), 1995, 81, EdadRecomendada.TP, 8.3));
		peliculas.add(new Pelicula("La La Land", "Damien Chazelle", new ArrayList<>(Arrays.asList("Ryan Gosling", "Emma Stone", "John Legend")), new ArrayList<>(Arrays.asList(Genero.MUSICAL, Genero.ROMACE)), 2016, 128, EdadRecomendada.PLUS13, 8.0));
		peliculas.add(new Pelicula("Parásitos", "Bong Joon-ho", new ArrayList<>(Arrays.asList("Song Kang-ho", "Lee Sun-kyun", "Cho Yeo-jeong")), new ArrayList<>(Arrays.asList(Genero.DRAMA, Genero.THRILLER)), 2019, 132, EdadRecomendada.PLUS16, 8.6));
		peliculas.add(new Pelicula("Dune", "Denis Villeneuve", new ArrayList<>(Arrays.asList("Timothée Chalamet", "Rebecca Ferguson", "Oscar Isaac")), new ArrayList<>(Arrays.asList(Genero.CIENCIA_FICCION, Genero.AVENTURA)), 2021, 155, EdadRecomendada.PLUS13, 8.0));
		peliculas.add(new Pelicula("Joker", "Todd Phillips", new ArrayList<>(Arrays.asList("Joaquin Phoenix", "Robert De Niro", "Zazie Beetz")), new ArrayList<>(Arrays.asList(Genero.DRAMA, Genero.CRIMEN)), 2019, 122, EdadRecomendada.PLUS18, 8.4));
		peliculas.add(new Pelicula("Black Panther", "Ryan Coogler", new ArrayList<>(Arrays.asList("Chadwick Boseman", "Michael B. Jordan", "Lupita Nyong'o")), new ArrayList<>(Arrays.asList(Genero.ACCION, Genero.CIENCIA_FICCION)), 2018, 134, EdadRecomendada.PLUS13, 7.3));
		peliculas.add(new Pelicula("Spider-Man", "Sam Raimi", new ArrayList<>(Arrays.asList("Tobey Maguire", "Kirsten Dunst", "Willem Dafoe")), new ArrayList<>(Arrays.asList(Genero.ACCION, Genero.AVENTURA)), 2002, 121, EdadRecomendada.PLUS13, 7.4));

		// Películas 41-50
		peliculas.add(new Pelicula("Batman Begins", "Christopher Nolan", new ArrayList<>(Arrays.asList("Christian Bale", "Michael Caine", "Liam Neeson")), new ArrayList<>(Arrays.asList(Genero.ACCION, Genero.DRAMA)), 2005, 140, EdadRecomendada.PLUS13, 8.2));
		peliculas.add(new Pelicula("Iron Man", "Jon Favreau", new ArrayList<>(Arrays.asList("Robert Downey Jr.", "Gwyneth Paltrow", "Jeff Bridges")), new ArrayList<>(Arrays.asList(Genero.ACCION, Genero.CIENCIA_FICCION)), 2008, 126, EdadRecomendada.PLUS13, 7.9));
		peliculas.add(new Pelicula("Guardianes de la Galaxia", "James Gunn", new ArrayList<>(Arrays.asList("Chris Pratt", "Zoe Saldana", "Dave Bautista")), new ArrayList<>(Arrays.asList(Genero.ACCION, Genero.COMEDIA)), 2014, 121, EdadRecomendada.PLUS13, 8.0));
		peliculas.add(new Pelicula("Deadpool", "Tim Miller", new ArrayList<>(Arrays.asList("Ryan Reynolds", "Morena Baccarin", "Ed Skrein")), new ArrayList<>(Arrays.asList(Genero.ACCION, Genero.COMEDIA)), 2016, 108, EdadRecomendada.PLUS18, 8.0));
		peliculas.add(new Pelicula("Logan", "James Mangold", new ArrayList<>(Arrays.asList("Hugh Jackman", "Patrick Stewart", "Dafne Keen")), new ArrayList<>(Arrays.asList(Genero.ACCION, Genero.DRAMA)), 2017, 137, EdadRecomendada.PLUS18, 8.1));
		peliculas.add(new Pelicula("Mad Max", "George Miller", new ArrayList<>(Arrays.asList("Tom Hardy", "Charlize Theron", "Nicholas Hoult")), new ArrayList<>(Arrays.asList(Genero.ACCION, Genero.CIENCIA_FICCION)), 2015, 120, EdadRecomendada.PLUS16, 8.1));
		peliculas.add(new Pelicula("John Wick", "Chad Stahelski", new ArrayList<>(Arrays.asList("Keanu Reeves", "Michael Nyqvist", "Alfie Allen")), new ArrayList<>(Arrays.asList(Genero.ACCION, Genero.THRILLER)), 2014, 101, EdadRecomendada.PLUS16, 7.4));
		peliculas.add(new Pelicula("Mission Impossible", "Brian De Palma", new ArrayList<>(Arrays.asList("Tom Cruise", "Jon Voight", "Emmanuelle Béart")), new ArrayList<>(Arrays.asList(Genero.ACCION, Genero.THRILLER)), 1996, 110, EdadRecomendada.PLUS13, 7.1));
		peliculas.add(new Pelicula("James Bond", "Martin Campbell", new ArrayList<>(Arrays.asList("Daniel Craig", "Eva Green", "Mads Mikkelsen")), new ArrayList<>(Arrays.asList(Genero.ACCION, Genero.THRILLER)), 2006, 144, EdadRecomendada.PLUS13, 8.0));
		peliculas.add(new Pelicula("Wonder Woman", "Patty Jenkins", new ArrayList<>(Arrays.asList("Gal Gadot", "Chris Pine", "Robin Wright")), new ArrayList<>(Arrays.asList(Genero.ACCION, Genero.AVENTURA)), 2017, 141, EdadRecomendada.PLUS13, 7.4));

		
		//Prueba del Frame Principal
		Usuario usuario= new Usuario("Nerea", "nerea.f.m@opendeusto.es", 19, LocalDate.of(1, 1, 1), "1234");
		VentanaInicioFrame framePrincipal= new VentanaInicioFrame(usuario);
		
		//Para ir probando los JFrames
//		UsuarioFrame frame = new UsuarioFrame(usuario);
//		frame.setVisible(true);
		
	}
	
}
