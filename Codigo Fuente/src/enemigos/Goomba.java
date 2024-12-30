package enemigos;

import archivos.Sprite;
import elementos.Enemigo;
import juego.Jugador;

public class Goomba extends Enemigo{
	
	public Goomba (int x, int y, Sprite imagen) {
		super (x,y,imagen);
	}
	
	public void visitar (Jugador jugador) {
		int alto = calcularAlturaInterseccion(jugador);
		int ancho = calcularAnchoInterseccion(jugador);
		
		boolean colisionDeLado = alto >= ancho;
		boolean colisionDeArriba = esColisionDeArribaConElemento(jugador);
		
		if(!colisionDeLado) {
		
			if(colisionDeArriba) {
				chocar(jugador);
				enemigoMuere(jugador);	
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

	public int puntosQueResta() {
		return 30;
	}
	
	public int puntosQueDa() {
		return 60;
	}	
}
