package enemigos;

import archivos.Sprite;
import elementos.Enemigo;
import juego.Jugador;

public class Koopa extends Enemigo{

	protected boolean escondido;

	public Koopa (int x, int y, Sprite imagen) {
		super (x,y,imagen);
		escondido = false;
	}

	public void visitar (Jugador jugador) {
		int alto = calcularAlturaInterseccion(jugador);
		int ancho = calcularAnchoInterseccion(jugador);
		
		boolean colisionDeLado = alto >= ancho;
		boolean colisionDeArriba = elementoColisionaArriba(jugador);
		
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

	private void enemigoMuere(Jugador jugador) {
		jugador.actualizarPuntaje(this.puntosQueDa());
		morir();
	}

	private void jugadorRecibeDaño(Jugador jugador) {
		jugador.recibirDaño(this);
	}

	public int recibirDaño() {
		this.morir();
		return this.puntosQueDa();
	}

	public int puntosQueResta() {
		return 45;
	}

	public int puntosQueDa() {
		return 90;
	}
}
