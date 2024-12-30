package powerUps;

import archivos.ControladorSonidos;
import archivos.Sprite;
import archivos.TipoSonidos;
import elementos.PowerUp;
import juego.Jugador;
import parseo.GameFactory;

public class ChampiñonVerde extends PowerUp{
	
	private long limiteInferior = 441;
	
	public ChampiñonVerde(int x, int y, Sprite imagen) {
		super(x, y, imagen);
	}
	
	public void morir() {
		super.morir();
		ControladorSonidos.getInstance().reproducirSonidoAccion(TipoSonidos.champiñonVerde);
	}
	
	public int puntosQueDa() {
		return 100;
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
	
	public void visitar (Jugador jugador) {
		jugador.sumarVida();
		jugador.actualizarPuntaje(puntosQueDa());
		morir();
	}
	
	
	public void setFabrica(GameFactory factory) {
		this.fabrica = factory;
		this.activado = new Sprite(fabrica.getRutaCarpeta() + "/champiñonVerde.png");
	}
}
