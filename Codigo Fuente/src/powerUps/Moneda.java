package powerUps;

import archivos.ControladorSonidos;
import archivos.Sprite;
import archivos.TipoSonidos;
import elementos.PowerUp;
import juego.Jugador;
import parseo.GameFactory;

public class Moneda extends PowerUp{

	public Moneda(int x, int y, Sprite imagen) {
		super(x, y, imagen);
	}

	public void visitar (Jugador jugador) {
		jugador.actualizarPuntaje(puntosQueDa());
		jugador.aumentarMoneda();
		morir();
	}

	public void morir() {
		super.morir();
		ControladorSonidos.getInstance().reproducirSonidoAccion(TipoSonidos.moneda);
	}
	
	public int puntosQueDa() {
		return 5;
	}
	
	public void setFabrica(GameFactory factory) {
		this.fabrica = factory;
		this.activado = new Sprite(fabrica.getRutaCarpeta() + "/moneda.png");
	}
}
