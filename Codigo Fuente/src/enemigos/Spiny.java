package enemigos;

import archivos.Sprite;
import elementos.Enemigo;
import juego.Jugador;

public class Spiny extends Enemigo{

	public Spiny (int x, int y, Sprite imagen) {
		super (x,y,imagen);
	}

	public void visitar (Jugador jugador) {
		jugadorRecibeDaño(jugador);
	}

	private void jugadorRecibeDaño(Jugador jugador) {
		jugador.recibirDaño(this);
	}

	public int recibirDaño() {
		this.morir();
		return this.puntosQueDa();
	}

	public int puntosQueResta() {
		return 30;
	}

	public int puntosQueDa() {
		return 60;
	}
}
