package powerUps;

import archivos.Sprite;
import elementos.PowerUp;
import juego.Jugador;
import parseo.GameFactory;


public class Estrella extends PowerUp{

	protected long limiteInferior;


	public Estrella(int x, int y, Sprite imagen) {
		super(x, y, imagen);
	}

	public void visitar (Jugador jugador) {
		jugador.recibirEstrella();
		morir();
	}

	public void moverse() {
		descender();
		movimientoADerecha();
	}

	public void descender() {
		if (this.hitbox.y < limiteInferior)
			setPosY(this.hitbox.y - 1);
	}

	public void movimientoADerecha() {
		int nuevaPosicionX = this.hitbox.x + velocidad*(1/60);
		setPosX(nuevaPosicionX);
	}
	
	public void movimientoAIzquierda() {
		int nuevaPosicionX = this.hitbox.x - velocidad*(1/60);
		setPosX(nuevaPosicionX);
	}
	
	public void setFabrica(GameFactory factory) {
		this.fabrica = factory;
		this.activado = new Sprite(fabrica.getRutaCarpeta() + "/estrella.png");
	}
}
