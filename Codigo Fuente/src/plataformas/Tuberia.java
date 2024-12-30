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
		} else {
			if(colisionDeArriba) {
				ubicarArriba(jugador);
				jugador.ultimoBloqueColision(this);
			}else {	
			}
		}	
	}	

	@Override
	public void visitar(Enemigo enemigo) {	
		int alto = calcularAlturaInterseccion(enemigo);
		int ancho = calcularAnchoInterseccion(enemigo);

		boolean colisionDeLado = (alto >= ancho);
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
			System.out.println("Esta detectando colision por arriba/abajo");
			if(colisionDeArriba) {
				enemigo.setPosY((int) (this.getPosY() + enemigo.getAlto()));
			}else {

			}

		}
	}

}
