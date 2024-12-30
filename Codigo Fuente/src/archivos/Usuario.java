package archivos;

import java.io.Serializable;

public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombre;
	private int puntajeTotal;

	public Usuario(String n) {
		nombre = n; 
	}

	public void setPuntajeTotal(int nuevoPuntaje) {
		puntajeTotal = nuevoPuntaje;
	}

	public String getNombre() {
		return nombre;
	}

	public int getPuntajeTotal() {
		return puntajeTotal;
	}
}
