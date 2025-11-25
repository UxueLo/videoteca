package videoteca.gui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;

import videoteca.domain.EdadRecomendada;
import videoteca.domain.Genero;
import videoteca.domain.Pelicula;
import videoteca.domain.Reserva;
import videoteca.domain.Usuario;
import videoteca.jdbc.GestorBD;

public class Main {

	public static void main(String[] args) {
		//Lista creada por DeepSeek
		// Crear lista de 50 películas
		//IAG
		ArrayList<Pelicula> peliculas = new ArrayList<>();

		// Películas 1-10
		peliculas.add(new Pelicula("The Menu", "Mark Mylod", new ArrayList<>(Arrays.asList("Anya Taylor-Joy", "Ralph Fiennes", "Nicholas Hoult")), new ArrayList<>(Arrays.asList(Genero.THRILLER, Genero.COMEDIA, Genero.DRAMA)), 2022, 106, EdadRecomendada.PLUS16, 7.2, 9.49));
		peliculas.add(new Pelicula("Dead Poets Society", "Peter Weir", new ArrayList<>(Arrays.asList("Robin Williams", "Ethan Hawke")), new ArrayList<>(Arrays.asList(Genero.DRAMA)), 1989, 128, EdadRecomendada.PLUS13, 8.1, 8.99));
        peliculas.add(new Pelicula("Matrix", "Lana Wachowski", new ArrayList<>(Arrays.asList("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss")), new ArrayList<>(Arrays.asList(Genero.CIENCIA_FICCION, Genero.ACCION)), 1999, 136, EdadRecomendada.PLUS16, 8.7, 9.99));
        peliculas.add(new Pelicula("Oppenheimer", "Christopher Nolan", new ArrayList<>(Arrays.asList("Cillian Murphy", "Robert Downey Jr.", "Emily Blunt")), new ArrayList<>(Arrays.asList(Genero.DRAMA, Genero.HISTORICA)), 2023, 180, EdadRecomendada.PLUS16, 8.6, 12.99));
        peliculas.add(new Pelicula("Chicago", "Rob Marshall", new ArrayList<>(Arrays.asList("Renée Zellweger", "Catherine Zeta-Jones", "Richard Gere")), new ArrayList<>(Arrays.asList(Genero.MUSICAL, Genero.DRAMA)), 2002, 113, EdadRecomendada.PLUS13, 7.2, 8.99));
        peliculas.add(new Pelicula("Star Wars A New Hope", "George Lucas", new ArrayList<>(Arrays.asList("Mark Hamill", "Harrison Ford", "Carrie Fisher")), new ArrayList<>(Arrays.asList(Genero.CIENCIA_FICCION, Genero.AVENTURA)), 1977, 121, EdadRecomendada.TP, 8.6, 9.99));
        peliculas.add(new Pelicula("The Phantom of the Opera", "Joel Schumacher", new ArrayList<>(Arrays.asList("Emmy Rossum", "Gerard Butler", "Patrick Wilson")), new ArrayList<>(Arrays.asList(Genero.MUSICAL, Genero.ROMACE, Genero.DRAMA)), 2004, 143, EdadRecomendada.PLUS13, 7.2, 8.99));
        peliculas.add(new Pelicula("Inglourious Basterds", "Quentin Tarantino", new ArrayList<>(Arrays.asList("Brad Pitt", "Christoph Waltz", "Diane Kruger")), new ArrayList<>(Arrays.asList(Genero.ACCION, Genero.DRAMA, Genero.GUERRA)), 2009, 153, EdadRecomendada.PLUS18, 8.3, 10.49));
        peliculas.add(new Pelicula("Your Name", "Makoto Shinkai", new ArrayList<>(Arrays.asList("Ryunosuke Kamiki", "Mone Kamishiraishi")), new ArrayList<>(Arrays.asList(Genero.ANIMACION, Genero.ROMACE, Genero.FANTASIA)), 2016, 112, EdadRecomendada.PLUS7, 8.9, 9.99));
        peliculas.add(new Pelicula("Arrival", "Denis Villeneuve", new ArrayList<>(Arrays.asList("Amy Adams", "Jeremy Renner", "Forest Whitaker")), new ArrayList<>(Arrays.asList(Genero.CIENCIA_FICCION, Genero.DRAMA)), 2016, 116, EdadRecomendada.PLUS13, 8.0, 10.49));
        
		// Películas 11-20
        peliculas.add(new Pelicula("Stand by Me Doraemon", "Ryuichi Yagi", new ArrayList<>(Arrays.asList("Wasabi Mizuta", "Megumi Oohara")), new ArrayList<>(Arrays.asList(Genero.ANIMACION, Genero.AVENTURA)), 2014, 95, EdadRecomendada.TP, 7.9, 8.49));
        peliculas.add(new Pelicula("Avatar", "James Cameron", new ArrayList<>(Arrays.asList("Sam Worthington", "Zoe Saldana", "Sigourney Weaver")), new ArrayList<>(Arrays.asList(Genero.CIENCIA_FICCION, Genero.AVENTURA)), 2009, 162, EdadRecomendada.PLUS13, 7.8, 10.99));
        peliculas.add(new Pelicula("Gladiador", "Ridley Scott", new ArrayList<>(Arrays.asList("Russell Crowe", "Joaquin Phoenix", "Connie Nielsen")), new ArrayList<>(Arrays.asList(Genero.ACCION, Genero.DRAMA)), 2000, 155, EdadRecomendada.PLUS16, 8.5, 9.99));
        peliculas.add(new Pelicula("Cadena Perpetua", "Frank Darabont", new ArrayList<>(Arrays.asList("Tim Robbins", "Morgan Freeman", "Bob Gunton")), new ArrayList<>(Arrays.asList(Genero.DRAMA)), 1994, 142, EdadRecomendada.PLUS16, 9.3, 10.99));
        peliculas.add(new Pelicula("El Rey León", "Roger Allers", new ArrayList<>(Arrays.asList("Matthew Broderick", "James Earl Jones", "Jeremy Irons")), new ArrayList<>(Arrays.asList(Genero.ANIMACION, Genero.AVENTURA)), 1994, 88, EdadRecomendada.TP, 8.5, 7.49));
        peliculas.add(new Pelicula("Volver al Futuro", "Robert Zemeckis", new ArrayList<>(Arrays.asList("Michael J. Fox", "Christopher Lloyd", "Lea Thompson")), new ArrayList<>(Arrays.asList(Genero.CIENCIA_FICCION, Genero.COMEDIA)), 1985, 116, EdadRecomendada.TP, 8.5, 8.49));
        peliculas.add(new Pelicula("Psicosis", "Alfred Hitchcock", new ArrayList<>(Arrays.asList("Anthony Perkins", "Janet Leigh", "Vera Miles")), new ArrayList<>(Arrays.asList(Genero.TERROR, Genero.THRILLER)), 1960, 109, EdadRecomendada.PLUS16, 8.5, 6.99));
        peliculas.add(new Pelicula("Apocalypse Now", "Francis Ford Coppola", new ArrayList<>(Arrays.asList("Martin Sheen", "Marlon Brando", "Robert Duvall")), new ArrayList<>(Arrays.asList(Genero.DRAMA, Genero.GUERRA)), 1979, 153, EdadRecomendada.PLUS18, 8.4, 9.99));
        peliculas.add(new Pelicula("Blade Runner", "Ridley Scott", new ArrayList<>(Arrays.asList("Harrison Ford", "Rutger Hauer", "Sean Young")), new ArrayList<>(Arrays.asList(Genero.CIENCIA_FICCION)), 1982, 117, EdadRecomendada.PLUS16, 8.1, 8.99));
        peliculas.add(new Pelicula("Alien", "Ridley Scott", new ArrayList<>(Arrays.asList("Sigourney Weaver", "Tom Skerritt", "John Hurt")), new ArrayList<>(Arrays.asList(Genero.CIENCIA_FICCION, Genero.TERROR)), 1979, 117, EdadRecomendada.PLUS16, 8.4, 9.49));

		// Películas 21-30
        peliculas.add(new Pelicula("Terminator", "James Cameron", new ArrayList<>(Arrays.asList("Arnold Schwarzenegger", "Linda Hamilton", "Michael Biehn")), new ArrayList<>(Arrays.asList(Genero.CIENCIA_FICCION, Genero.ACCION)), 1984, 107, EdadRecomendada.PLUS16, 8.0, 8.99));
        peliculas.add(new Pelicula("Indiana Jones", "Steven Spielberg", new ArrayList<>(Arrays.asList("Harrison Ford", "Karen Allen", "Paul Freeman")), new ArrayList<>(Arrays.asList(Genero.AVENTURA, Genero.ACCION)), 1981, 115, EdadRecomendada.TP, 8.4, 9.49));
        peliculas.add(new Pelicula("Seven", "David Fincher", new ArrayList<>(Arrays.asList("Brad Pitt", "Morgan Freeman", "Kevin Spacey")), new ArrayList<>(Arrays.asList(Genero.CRIMEN, Genero.THRILLER)), 1995, 127, EdadRecomendada.PLUS18, 8.6, 9.99));
        peliculas.add(new Pelicula("Fight Club", "David Fincher", new ArrayList<>(Arrays.asList("Brad Pitt", "Edward Norton", "Helena Bonham Carter")), new ArrayList<>(Arrays.asList(Genero.DRAMA)), 1999, 139, EdadRecomendada.PLUS18, 8.8, 10.99));
        peliculas.add(new Pelicula("El Resplandor", "Stanley Kubrick", new ArrayList<>(Arrays.asList("Jack Nicholson", "Shelley Duvall", "Danny Lloyd")), new ArrayList<>(Arrays.asList(Genero.TERROR)), 1980, 146, EdadRecomendada.PLUS16, 8.4, 8.99));
        peliculas.add(new Pelicula("Braveheart", "Mel Gibson", new ArrayList<>(Arrays.asList("Mel Gibson", "Sophie Marceau", "Patrick McGoohan")), new ArrayList<>(Arrays.asList(Genero.DRAMA, Genero.HISTORICA)), 1995, 178, EdadRecomendada.PLUS16, 8.3, 9.99));
        peliculas.add(new Pelicula("Salvar al Soldado Ryan", "Steven Spielberg", new ArrayList<>(Arrays.asList("Tom Hanks", "Matt Damon", "Tom Sizemore")), new ArrayList<>(Arrays.asList(Genero.DRAMA, Genero.GUERRA)), 1998, 169, EdadRecomendada.PLUS16, 8.6, 10.99));
        peliculas.add(new Pelicula("La Lista de Schindler", "Steven Spielberg", new ArrayList<>(Arrays.asList("Liam Neeson", "Ralph Fiennes", "Ben Kingsley")), new ArrayList<>(Arrays.asList(Genero.DRAMA, Genero.HISTORICA)), 1993, 195, EdadRecomendada.PLUS16, 8.9, 11.49));
        peliculas.add(new Pelicula("Logan", "James Mangold", new ArrayList<>(Arrays.asList("Hugh Jackman", "Patrick Stewart", "Dafne Keen")), new ArrayList<>(Arrays.asList(Genero.ACCION, Genero.DRAMA)), 2017, 137, EdadRecomendada.PLUS18, 8.1, 10.49));
        peliculas.add(new Pelicula("El Padrino", "Francis Ford Coppola", new ArrayList<>(Arrays.asList("Marlon Brando", "Al Pacino")), new ArrayList<>(Arrays.asList(Genero.DRAMA, Genero.CRIMEN)), 1972, 175, EdadRecomendada.PLUS18, 9.2, 11.99));
        
        peliculas.add(new Pelicula("Orgullo y Prejuicio", "Joe Wright", new ArrayList<>(Arrays.asList("Keira Knightley", "Matthew Macfadyen", "Rosamund Pike")), new ArrayList<>(Arrays.asList(Genero.ROMACE, Genero.DRAMA, Genero.HISTORICA)), 2005, 129, EdadRecomendada.PLUS7, 8.0, 8.99));
        peliculas.add(new Pelicula("Frozen", "Chris Buck", new ArrayList<>(Arrays.asList("Idina Menzel", "Kristen Bell", "Jonathan Groff")), new ArrayList<>(Arrays.asList(Genero.ANIMACION, Genero.MUSICAL)), 2013, 102, EdadRecomendada.TP, 7.5, 7.99));
        peliculas.add(new Pelicula("Shrek", "Andrew Adamson", new ArrayList<>(Arrays.asList("Mike Myers", "Eddie Murphy", "Cameron Diaz")), new ArrayList<>(Arrays.asList(Genero.ANIMACION, Genero.COMEDIA)), 2001, 90, EdadRecomendada.TP, 7.9, 7.49));
        peliculas.add(new Pelicula("Buscando a Nemo", "Andrew Stanton", new ArrayList<>(Arrays.asList("Albert Brooks", "Ellen DeGeneres", "Alexander Gould")), new ArrayList<>(Arrays.asList(Genero.ANIMACION, Genero.AVENTURA)), 2003, 100, EdadRecomendada.TP, 8.1, 7.99));

		
		//Prueba del Frame Principal
		Usuario usuario= new Usuario("", "", 19, LocalDate.of(1, 1, 1), ""); //Usuario nulo inicial
		//Cargar peliculas al Usuario
		for (Pelicula p : peliculas) {
			Reserva reserva = new Reserva(p,LocalDate.of(p.getAñoLanzamiento(), 1, 1));
			usuario.addrReserva(reserva);
		}
		VentanaInicioFrame framePrincipal= new VentanaInicioFrame(usuario, peliculas);
		
		//Prueba UsuarioFrame
		for (Pelicula p : peliculas) {
			Reserva reserva = new Reserva(p,LocalDate.of(p.getAñoLanzamiento(), 1, 1));
			usuario.addrReserva(reserva);
		}
//		UsuarioFrame frame = new UsuarioFrame(usuario);
//		frame.setVisible(true);
		
		GestorBD gestor = new GestorBD();
		gestor.inicializarTablas();
		
		//Para ir probando Ventana Catalogo
		//new VentanaCatalogo(usuario, peliculas);
	}
	
}
