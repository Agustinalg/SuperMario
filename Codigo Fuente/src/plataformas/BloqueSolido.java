package plataformas;


import archivos.Sprite;
import elementos.Enemigo;
import elementos.Plataforma;
import juego.Jugador;

public class BloqueSolido extends Plataforma{

	public BloqueSolido(int x, int y, Sprite imagen) {
		super(x, y, imagen);
	}

	@Override
	public void visitar(Jugador jugador) {
		int interseccionAlto = calcularAlturaInterseccion(jugador);
		int interseccionAncho = calcularAnchoInterseccion(jugador);
		
		boolean colisionDeLado = (interseccionAlto >= interseccionAncho);
		boolean colisionDeIzquierda = jugador.esColisionDeIzquierdaConPlataforma(this);
		boolean colisionDeArriba = this.elementoColisionaArriba(jugador);

		if(colisionDeLado) {
			if(colisionDeIzquierda) {
				jugador.setPosX((int) (this.getPosX() - (jugador.getAncho()+2)));
			} else {
				jugador.setPosX((int) (this.getPosX() + (this.getAncho() +2)));
			} 
		}else {
			if(colisionDeArriba) {
				jugador.ultimoBloqueColision(this);
				ubicarArriba(jugador);
			}else { 
				jugador.setPosY((int) (this.getPosY() - (this.getAlto() + 5)));
				jugador.setVelY(-1);
			}
		}
	}

	@Override
	public void visitar(Enemigo enemigo) {
		int interseccionAlto = calcularAlturaInterseccion(enemigo);
		int interseccionAncho = calcularAnchoInterseccion(enemigo);

		boolean colisionDeLado = (interseccionAlto >= interseccionAncho) && (interseccionAncho > 2);
		boolean colisionDeIzquierda = enemigo.esColisionDeIzquierdaConPlataforma(this);
		boolean colisionDeArriba = this.elementoColisionaArriba(enemigo);

		if(colisionDeLado) {
			if(colisionDeIzquierda) {
				enemigo.setPosX((int) (this.getPosX() - (enemigo.getAncho()+2)));
				enemigo.moverIzquierda();
			} else {
				enemigo.setPosX((int) (this.getPosX() + this.getAncho() + 15));
				enemigo.moverDerecha();
			}
		} else {
			if(colisionDeArriba) 
				enemigo.setPosY((int) (this.getPosY() + enemigo.getAlto()));
		}
	}

}
