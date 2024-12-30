package observers;

import elementos.ElementoJugador;
import vista.PantallaJuego;

public class ObserverJugador extends ObserverGrafico{

	private static final long serialVersionUID = 1L;
	protected PantallaJuego pantallaJuego;
	protected ElementoJugador jugadorObservado;

	public ObserverJugador(PantallaJuego pantallaJuego, ElementoJugador observado) {
		super(observado);
		this.pantallaJuego = pantallaJuego;
		this.jugadorObservado = observado;
		actualizar();		
	}

	@Override
	public void actualizar() {
		super.actualizar();
		pantallaJuego.actualizarInfoJugador(jugadorObservado);
	}
}
