package enemigos;

import archivos.Sprite;
import elementos.Enemigo;
import juego.ControladorPartida;
import parseo.ConstantesHitbox;
import parseo.GameFactory;
import juego.Jugador;
import observers.AdaptadorPosicionPixel;


public class Lakitu extends Enemigo{

	protected Enemigo Spiny;
	protected long ultimoLanzamiento;
	protected long intervaloLanzamientoSpinys = 3000;
	protected GameFactory fabrica;
	protected ControladorPartida controladorPartida;
	protected long ahora; 

	public Lakitu (int x, int y, Sprite im) {
		super (x,y,im);
		ultimoLanzamiento = System.currentTimeMillis();
	}

	public void actualizar() {
		int limiteDerecho =  AdaptadorPosicionPixel.transformarX(7471);
		int limiteY_ventana = 0;
		moverEnDireccion();
		this.setPosX(this.hitbox.x + velX);

		if (this.hitbox.x < 0) {
			this.hitbox.x = 0; 
			this.moverDerecha();
		}else if (this.hitbox.x > limiteDerecho) {
			this.hitbox.x = limiteDerecho;	
			this.moverIzquierda();
		}
		if (this.hitbox.y < limiteY_ventana)
			morir();

		notificar();
		actualizarSpiny();
	}	

	public void actualizarSpiny() {
		ahora = System.currentTimeMillis();
		if (ahora - ultimoLanzamiento >= intervaloLanzamientoSpinys) {
			lanzarSpiny();
			ultimoLanzamiento = ahora;
		}
	}

	public void lanzarSpiny() {
		fabrica.crearSpiny(this.hitbox.x, this.hitbox.y - ConstantesHitbox.altoLakitu);
	}

	public void visitar (Jugador jugador) {
		int alto = calcularAlturaInterseccion(jugador);
		int ancho = calcularAnchoInterseccion(jugador);
		
		boolean colisionDeLado = alto >= ancho;
		boolean colisionDeArriba = esColisionDeArribaConElemento(jugador);
		
		if(!colisionDeLado) {
		
			if(colisionDeArriba) {
				chocar(jugador);
				enemigoMuere(jugador);	
			} else 
				jugadorRecibeDaño(jugador);
		}else 
			jugadorRecibeDaño(jugador);
	}

	private void jugadorRecibeDaño(Jugador jugador) {
		jugador.recibirDaño(this);
	}

	private void enemigoMuere(Jugador jugador) {
		jugador.actualizarPuntaje(this.puntosQueDa());
		morir();
	}

	public int recibirDaño() {
		this.morir();
		return this.puntosQueDa();
	}

	public int puntosQueResta() {
		return 30;
	}

	public int puntosQueDa() {
		return 60;
	}

	//Set
	public void setFabrica(GameFactory factory) {
		this.fabrica = factory;
	}

	public void setControlador(ControladorPartida partida) {
		this.controladorPartida = partida;
	}
}