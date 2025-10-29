package videoteca.domain;

public class Comentario {
	
	private String texto;
	private double valoracion;
	
	
	public Comentario(double valoracion, String texto) {
		super();
		this.texto = texto;
		this.valoracion = valoracion;
	}


	public String getTexto() {
		return texto;
	}


	public void setTexto(String texto) {
		this.texto = texto;
	}


	public double getValoracion() {
		return valoracion;
	}


	public void setValoracion(double valoracion) {
		this.valoracion = valoracion;
	}


	@Override
	public String toString() {
		return "Comentario [texto=" + texto + ", valoracion=" + valoracion + "]";
	}
	
	
	
	
}
