package plataformas;

import archivos.ControladorSonidos;
import archivos.Sprite;
import archivos.TipoSonidos;
import elementos.Enemigo;
import elementos.Plataforma;
import juego.Jugador;
import parseo.ConstantesHitbox;

public class LadrilloSolido extends Plataforma{
	
	public LadrilloSolido(int x, int y, Sprite imagen) {
		super(x, y, imagen);
	}

	@Override
	public void visitar(Jugador jugador) {
		int alto = calcularAlturaInterseccion(jugador);
		int ancho = calcularAnchoInterseccion(jugador);
		
		boolean colisionDeLado = alto >= ancho;
		boolean colisionDeIzquierda = jugador.esColisionDeIzquierdaConPlataforma(this);
		boolean colisionDeArriba = jugador.esColisionDeArribaConPlataforma(this);

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
				if((jugador.getAlto() == ConstantesHitbox.altoJugadorGrande) && (jugador.getAncho() == ConstantesHitbox.anchoJugadorGrande)) {
					morir();
					estoyMuerto = true;
				}
			}
		}
		
	}
	
	public void morir() {
		super.morir();
		ControladorSonidos.getInstance().reproducirSonidoAccion(TipoSonidos.rompeBloque);
	}
	
	@Override
	public void visitar(Enemigo enemigo) {
		
		int alto = calcularAlturaInterseccion(enemigo);
		int ancho = calcularAnchoInterseccion(enemigo);

		boolean colisionDeLado = (alto >= ancho) && (ancho > 2);
		boolean colisionDeIzquierda = enemigo.esColisionDeIzquierdaConElemento(this);
		boolean colisionDeArriba = enemigo.esColisionDeArribaConElemento(this);


		if(colisionDeLado) {
			if(colisionDeIzquierda) {
				enemigo.setPosX((int) (this.getPosX() - (enemigo.getAncho()+2)));
				enemigo.moverIzquierda();
			} else {
				enemigo.setPosX((int) (this.getPosX() + (this.getAncho() +5)));
				enemigo.moverDerecha();
			}
		} else {
			if(colisionDeArriba) 
				enemigo.setPosY((int) (this.getPosY() + enemigo.getAlto()));

		}

	}
}
