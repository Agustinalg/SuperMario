package archivos;

import java.util.HashMap;
import java.util.Map;

public class ControladorSonidosJuego {
	protected Map<TipoSonidos, Sonido> sonidosJuego= new HashMap<>();

	public ControladorSonidosJuego () {
		sonidosJuego.put(TipoSonidos.advertenciaTiempo, new Sonido ("/audio/advertenciaTiempo.wav"));
		sonidosJuego.put(TipoSonidos.aparecePowerUp, new Sonido ("/audio/aparecePowerUp.wav"));
		sonidosJuego.put(TipoSonidos.bajaBandera, new Sonido ("/audio/bajaBandera.wav"));
		sonidosJuego.put(TipoSonidos.finNivel, new Sonido ("/audio/sonidoFinalNivel.wav"));
		sonidosJuego.put(TipoSonidos.bajaCañeria, new Sonido ("/audio/cañeria.wav"));
		sonidosJuego.put(TipoSonidos.speedBackground, new Sonido ("/audio/speedBackground.wav"));
		sonidosJuego.put(TipoSonidos.victoria, new Sonido ("/audio/mundoDespejado.wav"));
		sonidosJuego.put(TipoSonidos.fuegosArtificiales, new Sonido ("/audio/fuegosArtificiales.wav"));
	}

	public void reproducirSonido (TipoSonidos tipo) {
		sonidosJuego.get(tipo).reproducirSonido();
	}
	
	public void detenerSonido (TipoSonidos tipo) {
		sonidosJuego.get(tipo).detener();
	}
}
