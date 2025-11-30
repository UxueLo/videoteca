package videoteca.domain;

public class Comentario {
	
	private String texto;
	private int valoracion;
	private Usuario user;
	private Pelicula p;
	
	
	public Comentario(int valoracion, String texto, Usuario user, Pelicula p) {
		super();
		this.texto = texto;
		this.valoracion = valoracion;
		this.user = user;
		this.p = p;
	}


	public String getTexto() {
		return texto;
	}


	public void setTexto(String texto) {
		this.texto = texto;
	}


	public int getValoracion() {
		return valoracion;
	}


	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}
	
	public Usuario getUser() {
		return user;
	}


	public void setUser(Usuario user) {
		this.user = user;
	}


	public Pelicula getP() {
		return p;
	}


	public void setP(Pelicula p) {
		this.p = p;
	}


	@Override
	public String toString() {
		return "Comentario [texto=" + texto + ", valoracion=" + valoracion + ", user=" + user + "]";
	}
	
	
	
	
}
