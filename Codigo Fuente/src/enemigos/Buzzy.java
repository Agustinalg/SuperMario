package enemigos;

import archivos.Sprite;
import elementos.Enemigo;
import juego.Jugador;

public class Buzzy extends Enemigo{
	protected boolean escondido;

	public Buzzy(int x, int y, Sprite imagen) {
		super(x, y, imagen);
		escondido = false;
	}

	public void visitar(Jugador jugador) {
		int alto = calcularAlturaInterseccion(jugador);
		int ancho = calcularAnchoInterseccion(jugador);
		
		boolean colisionDeLado = alto >= ancho;
		boolean colisionDeArriba = esColisionDeArribaConElemento(jugador);
		
		if(!colisionDeLado) {
		
			if(colisionDeArriba) {
				chocar(jugador);
				if (escondido) {
					enemigoMuere(jugador);
				}else {
					escondido = true;
				}
					
			} else 
				jugadorRecibeDaño(jugador);
		}else 
			jugadorRecibeDaño(jugador);
	}

	private void jugadorRecibeDaño(Jugador jugador) {
		jugador.recibirDaño(this);
	}

	private void enemigoMuere(Jugador jugador) {
		jugador.actualizarPuntaje(this.puntosQueDa());
		morir();
	}

	public int recibirDaño() {
		this.morir();
		int puntos = this.puntosQueDa();
		return puntos;
	}

	public int puntosQueResta() {
		return 15;
	}

	public int puntosQueDa() {
		return 30;
	}	
}
