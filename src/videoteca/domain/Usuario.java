package videoteca.domain;

import java.time.LocalDate;
import java.util.ArrayList;


public class Usuario {
	
	private String nombre;
	private String email;
	private int edad;
	private LocalDate fechaNacimineto;
	private String contraseña;
	private int saldo = 0;
	private ArrayList<Reserva> peliculasAlquiladas = new ArrayList<Reserva>();
	
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

	public void aumentarSaldo(int suma) {
		saldo += suma;
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

	public int getSaldo() {
		return saldo;
	}

	public ArrayList<Reserva> getPeliculasAlquiladas() {
		return peliculasAlquiladas;
	}
	
	//metodos
	public void addrReserva(Reserva reserva) {
		this.peliculasAlquiladas.add(reserva);
	}
	
	
}
