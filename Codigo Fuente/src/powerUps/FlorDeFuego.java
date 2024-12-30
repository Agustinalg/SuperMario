package powerUps;

import archivos.ControladorSonidos;
import archivos.Sprite;
import archivos.TipoSonidos;
import elementos.PowerUp;
import juego.Jugador;
import parseo.GameFactory;

public class FlorDeFuego extends PowerUp{

	public FlorDeFuego(int x, int y, Sprite imagen) {
		super(x, y, imagen);

	}

	public void visitar(Jugador jugador) {
		jugador.recibirFlorDeFuego();
		morir();
	}

	public void morir() {
		super.morir();
		ControladorSonidos.getInstance().reproducirSonidoAccion(TipoSonidos.powerUp);
	}
	
	public void setFabrica(GameFactory factory) {
		this.fabrica = factory;
		this.activado = new Sprite(fabrica.getRutaCarpeta() + "/florDeFuego.png");
	}
	
}
