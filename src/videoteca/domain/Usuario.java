package videoteca.domain;

import java.time.LocalDate;
import java.util.ArrayList;


public class Usuario {
	
	private String nombre;
	private String email;
	private int edad;
	private LocalDate fechaNacimineto;
	private String contraseña;
	private double saldo = 0.0;
	private int intentosHechos;
	private LocalDate ultimoDiaIntento;
	private ArrayList<Reserva> peliculasAlquiladas = new ArrayList<Reserva>();
	private ArrayList<Pelicula> carritoUsuario= new ArrayList<Pelicula>();
	private int puntos;		// cada vez que alquila una película se le añadirá un punto, que podrá gastar en el juego para conseguir dinero con preguntas
	
	
	//constructor
	public Usuario(String nombre, String email, int edad, LocalDate fechaNacimineto, String contraseña) {
		this.nombre = nombre;
		this.email = email;
		this.edad = edad;
		this.fechaNacimineto = fechaNacimineto;
		this.contraseña = contraseña;
		
	}
	
	//setters
	public void cambiarNombre(String nombre) {
		this.nombre = nombre;
	}

	public void aumentarSaldo(double suma) {
		saldo += suma;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	
	//getters
	public String getNombre() {
		return nombre;
	}

	public String getEmail() {
		return email;
	}
	public int getEdad() {
		return edad;
	}
	public LocalDate getFechaNacimineto() {
		return fechaNacimineto;
	}
	public String getContraseña() {
		return contraseña;
	}

	public double getSaldo() {
		return Math.round(this.saldo * 100.0) / 100.0;
	}

	public ArrayList<Reserva> getPeliculasAlquiladas() {
		return peliculasAlquiladas;
	}
	
	public ArrayList<Pelicula> getCarritoUsuario() {
		return carritoUsuario;
	}

	public void setCarritoUsuario(ArrayList<Pelicula> carritoUsuario) {
		this.carritoUsuario = carritoUsuario;
	}

	public int getIntentosHechos() {
		return intentosHechos;
	}
	
	public int getPuntos() {
		return puntos;
	}

	public void incrementarIntentos() {
		if (this.ultimoDiaIntento!=LocalDate.now()) { //si es un dia nuevo
			ultimoDiaIntento=LocalDate.now();
			intentosHechos=1;
		}else { //si el ultimo intento si que es de hoy
			intentosHechos=getIntentosHechos()+1;
		}
	}

	//metodos
	public void addrReserva(Reserva reserva) {
		this.peliculasAlquiladas.add(reserva);
	}
	
}
