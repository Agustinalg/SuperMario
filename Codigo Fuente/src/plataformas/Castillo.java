package plataformas;

import archivos.ControladorSonidos;
import archivos.Sprite;
import archivos.TipoSonidos;
import elementos.Enemigo;
import elementos.Plataforma;
import juego.Jugador;

public class Castillo extends Plataforma {

	public Castillo(int x, int y, Sprite imagen) {
		super(x, y, imagen);
	}

	@Override
	public void visitar(Jugador jugador) {
		this.colisiones.getControladorPartida().siguienteNivel();
		sonido();
	}

	public void sonido() {
		ControladorSonidos.getInstance().reproducirSonidoJuego(TipoSonidos.finNivel);
	}

	public void visitar(Enemigo enemigo) {
		
	}
}
