package plataformas;

import archivos.Sprite;
import elementos.Enemigo;
import elementos.Plataforma;
import enemigos.Piranha;
import juego.ControladorPartida;
import juego.Jugador;
import parseo.GameFactory;

public class Tuberia extends Plataforma {

	protected Piranha piranha;
	protected GameFactory fabrica;
	protected ControladorPartida controladorPartida;

	public Tuberia(int x, int y, Sprite imagen) {
		super(x, y, imagen);
	}

	public void setFabrica(GameFactory factory) {
		this.fabrica = factory;
	}

	public void setControlador(ControladorPartida controlador) {
		this.controladorPartida = controlador;
	}

	public void visitar(Jugador jugador) { 
		int interseccionAlto = calcularAlturaInterseccion(jugador);
		int interseccionAncho = calcularAnchoInterseccion(jugador);

		boolean colisionDeLado = interseccionAlto >= interseccionAncho;
		boolean colisionDeIzquierda = jugador.esColisionDeIzquierdaConPlataforma(this);
		boolean colisionDeArriba = this.elementoColisionaArriba(jugador); 

		if(colisionDeLado) {
			if(colisionDeIzquierda) {
				jugador.setPosX((int) (this.getPosX() - (jugador.getAncho()+2)));
			} else {
				jugador.setPosX((int) (this.getPosX() + (this.getAncho() +2)));
			} 
		} else {
			if(colisionDeArriba) {
				ubicarArriba(jugador);
				jugador.ultimoBloqueColision(this);
			}
		}	
	}	

	@Override
	public void visitar(Enemigo enemigo) {	
		int interseccionAlto = calcularAlturaInterseccion(enemigo);
		int interseccionAncho = calcularAnchoInterseccion(enemigo);

		boolean colisionDeLado = (interseccionAlto >= interseccionAncho);
		boolean colisionDeIzquierda = enemigo.esColisionDeIzquierdaConPlataforma(this);
		boolean colisionDeArriba = this.elementoColisionaArriba(enemigo);

		if(colisionDeLado) {
			if(colisionDeIzquierda) {
				enemigo.setPosX((int) (this.getPosX() - (enemigo.getAncho()+2)));
				enemigo.moverIzquierda();
			} else {
				enemigo.setPosX((int) (this.getPosX() + (this.getAncho() +5)));
				enemigo.moverDerecha();
			}
		} else {
			if(colisionDeArriba) {
				enemigo.setPosY((int) (this.getPosY() + enemigo.getAlto()));
			}
		}
	}

}
