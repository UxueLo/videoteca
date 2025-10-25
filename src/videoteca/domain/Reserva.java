package videoteca.domain;

import java.time.LocalDate;

public class Reserva {
	
	private Pelicula pelicula;
	private LocalDate fechaReserva;
	private LocalDate fechaDevolucion;
	
	// Constructor
	public Reserva(Pelicula pelicula, LocalDate fechaReserva, LocalDate fechaDevolucion) {
		this.pelicula = pelicula;
		this.fechaReserva = fechaReserva;
		this.fechaDevolucion = fechaDevolucion;
	}

	// Getters
	public Pelicula getPelicula() {
		return pelicula;
	}

	public LocalDate getFechaReserva() {
		return fechaReserva;
	}

	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}
	
	// Métodos
	public void ampliarPlazoDevolucion(int dias) {
		fechaDevolucion = fechaDevolucion.plusDays(dias);
	}
	
}
