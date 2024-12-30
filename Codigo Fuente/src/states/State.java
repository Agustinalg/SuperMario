package states;

import archivos.Sprite;
import elementos.Enemigo;
import juego.ControladorPartida;
import juego.Jugador;
import parseo.GameFactory;


public abstract class State {

	protected Jugador jugador;
	protected Sprite sprite;
	protected ControladorPartida controlador;
	protected GameFactory fabrica;
	protected String rutaCarpeta;
	protected Normal normal;
	protected SuperMario superMario;
	protected Fuego fuego;
	protected Invulnerable invulnerable;
	protected boolean esInvulnerable = false;
	protected long tiempoInicioInvulnerable;
	protected static final long DURACION_INVULNERABLE = 3000;
	protected int ultimaVelocidadX = 0;


	public State(Jugador jugador) {
		this.jugador = jugador;	
	}

	public abstract void recibirDaño(Enemigo e);
	public abstract Sprite obtenerSpriteInvulnerableIzq();
	public abstract Sprite obtenerSpriteInvulnerableDer();
	public abstract Sprite obtenerSpriteInvulnerableSaltandoDer();
	public abstract Sprite obtenerSpriteInvulnerableSaltandoIzq();
	public abstract int obtenerPuntosEstrella();
	public abstract int obtenerPuntosSChamp();
	public abstract int obtenerPuntosFFuego();
	public abstract Sprite getSprite();

	public abstract void activar();
	public abstract void disparar();
	public abstract void moverDerecha();
	public abstract void moverIzquierda();
	public abstract void saltar();
	public abstract void actualizarSprite();
	
	protected abstract void actualizarMedidas();
	public abstract void recibirSuperChampiñon();
	public abstract void recibirFlorDeFuego();
	public abstract void recibirEstrella();
	public abstract void reproducirSonidoSalto();
	public abstract void setFabrica(GameFactory factory);
	
	//Set
	public void setNormal(Normal normal) {
		this.normal = normal;
	}

	public void setFuego(Fuego fuego) {
		this.fuego = fuego;
	}

	public void setSuperMario(SuperMario superMario) {
		this.superMario = superMario;
	}

	public void setInvulnerable(Invulnerable invulnerable) {
		this.invulnerable = invulnerable;
	}
	
	public void setControlador(ControladorPartida controlador) {
		this.controlador = controlador;
	}

	//Get
	public Fuego getFuego() {
		return fuego;
	}

	public Normal  getNormal() {
		return normal;
	}

	public SuperMario getSuperMario() {
		return superMario;
	}

	public Invulnerable getInvulnerable() {
		return invulnerable;
	}

}
