package plataformas;

import archivos.ControladorSonidos;
import archivos.Sprite;
import archivos.TipoSonidos;
import elementos.Enemigo;
import elementos.Plataforma;
import elementos.PowerUp;
import juego.ControladorPartida;
import juego.Jugador;
import parseo.GameFactory;

public class BloqueDePregunta extends Plataforma{

	protected PowerUp powerUp;
	protected Sprite bloqueDesactivado;
	protected GameFactory fabrica;
	protected ControladorPartida controladorPartida;
	protected boolean activado;

	public BloqueDePregunta(int x, int y, Sprite imagen) {
		super(x, y, imagen);
		activado = true;
	}

	public void generarPowerUp() {
		this.colisiones.agregarPowerUp(powerUp);
		powerUp.activarImagen();
	}

	//Get
	public Sprite getSprite() {
		return imagen;
	}
	
	public Sprite getSpriteDesactivado() {
		return bloqueDesactivado;
	}

	//Set
	public void setPowerUp(PowerUp power) {
		this.powerUp = power;		
	}

	public void setFabrica(GameFactory factory) {
		this.fabrica = factory;
		this.bloqueDesactivado = new Sprite(fabrica.getRutaCarpeta() + "/bloqueDePreguntaDesactivado.png");
	}

	public void setControlador(ControladorPartida partida) {
		this.controladorPartida = partida;
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
				ubicarArriba(jugador);
				jugador.ultimoBloqueColision(this);
			}else { 
				jugador.setPosY((int) (this.getPosY() - (this.getAlto() + 5)));
				jugador.setVelY(-1);
				if(activado) {
					generarPowerUp();
					activado = false;
					this.getSprite().setSprite(bloqueDesactivado.getRutaImagen());
					this.observer.actualizar();
					notificar();
					sonido();
				}
			}
		}
	}

	public void sonido() {
		ControladorSonidos.getInstance().reproducirSonidoJuego(TipoSonidos.aparecePowerUp);
	}

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