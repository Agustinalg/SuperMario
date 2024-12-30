package archivos;

import java.util.HashMap;
import java.util.Map;

public class ControladorSonidosAcciones {
	protected Map<TipoSonidos, Sonido> sonidosAcciones= new HashMap<>();

	public ControladorSonidosAcciones() {
		sonidosAcciones.put(TipoSonidos.bolaFuego, new Sonido ("/audio/bolaFuego.wav"));
		sonidosAcciones.put(TipoSonidos.champiñonVerde, new Sonido ("/audio/champiñonVerde.wav"));
		sonidosAcciones.put(TipoSonidos.moneda, new Sonido ("/audio/moneda.wav"));
		sonidosAcciones.put(TipoSonidos.muerteEnemigo, new Sonido ("/audio/pisaEnemigo.wav"));
		sonidosAcciones.put(TipoSonidos.muerteMario, new Sonido ("/audio/marioDies.wav"));
		sonidosAcciones.put(TipoSonidos.rompeBloque, new Sonido ("/audio/rompeBloque.wav"));
		sonidosAcciones.put(TipoSonidos.saltaNormal, new Sonido ("/audio/saltoEstadoNormal.wav"));
		sonidosAcciones.put(TipoSonidos.saltaSuperMario, new Sonido ("/audio/saltoEstadoSuperMario.wav"));
		sonidosAcciones.put(TipoSonidos.powerUp, new Sonido ("/audio/powerUp.wav"));
		sonidosAcciones.put(TipoSonidos.agarroEstrella, new Sonido ("/audio/agarroEstrella.wav"));
	}

	public void reproducirSonido (TipoSonidos tipo) {
		sonidosAcciones.get(tipo).reproducirSonido();
	}

	public void detenerSonido(TipoSonidos tipo) {
		sonidosAcciones.get(tipo).detener();
	}
}
