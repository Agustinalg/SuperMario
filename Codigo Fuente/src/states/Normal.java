package states;

import archivos.ControladorSonidos;
import archivos.Sprite;
import archivos.TipoSonidos;
import elementos.Enemigo;
import juego.Jugador;
import parseo.ConstantesHitbox;
import parseo.GameFactory;

public class Normal extends State {

	protected Sprite sprite, spriteIzq, spriteSaltoDer, spriteSaltoIzq;
	protected Sprite spriteInvulnerableDer, spriteInvulnerableIzq, spriteInvulnerableSaltandoDer, spriteInvulnerableSaltandoIzq;
	protected String rutaCarpeta;
	protected boolean invulnerable = false;
	protected long tiempoInicioInvulnerable;
	protected static final long DURACION_INVULNERABLE = 3000;

	public Normal(Jugador jugador) {
		super(jugador);
	}

	public void activar() {
		jugador.setState(this);
		jugador.getSprite().setSprite(this.sprite.getRutaImagen());
		inicializarSprites(rutaCarpeta);
		actualizarMedidas();
		
		invulnerable = true;
		tiempoInicioInvulnerable = System.currentTimeMillis();
	}

	protected void actualizarMedidas() {
		jugador.setAlto(ConstantesHitbox.altoJugador);
		jugador.setAncho(ConstantesHitbox.anchoJugador);

		int posY = (int) (jugador.getPosY() + (ConstantesHitbox.altoJugador - jugador.getHitbox().getHeight()));

		this.jugador.setPosY(posY);
		this.jugador.getHitbox().setBounds(jugador.getPosX(), posY, ConstantesHitbox.anchoJugador,
				ConstantesHitbox.altoJugador);
	}

	public void moverDerecha() {
		jugador.setImagen(sprite);
	}

	public void moverIzquierda() {
		jugador.setImagen(spriteIzq);
	}

	public void saltar() {
		if (!jugador.isJumped()) {
			jugador.setImagen(jugador.getVelocidadX() >= 0 ? spriteSaltoDer : spriteSaltoIzq);
		}
	}

	public void recibirSuperChampiñon() {
		jugador.actualizarPuntaje(this.obtenerPuntosSChamp());
		activarSuperMario();
	}
	
	public void recibirFlorDeFuego() {
		jugador.actualizarPuntaje(this.obtenerPuntosFFuego());
		activarSuperMario();
	}

	private void activarSuperMario() {
		this.getSuperMario().setControlador(controlador);
		this.getSuperMario().setFabrica(fabrica);
		this.getSuperMario().activar();
	}

	public void recibirEstrella() {
		jugador.actualizarPuntaje(this.obtenerPuntosEstrella());
		activarInvulnerable();
	}

	private void activarInvulnerable() {
		this.getInvulnerable().setControlador(controlador);
		this.getInvulnerable().setFabrica(fabrica);
		this.getInvulnerable().setAnterior(this);
		this.getInvulnerable().activar();
	}

	public void reproducirSonidoSalto() {
		ControladorSonidos.getInstance().reproducirSonidoAccion(TipoSonidos.saltaNormal);
	}

	public void recibirDaño(Enemigo e) {
		long tiempoActual = System.currentTimeMillis();
		if (invulnerable && tiempoActual - tiempoInicioInvulnerable < DURACION_INVULNERABLE) {
	        return; // Ignorar daño si es invulnerable
	    }
		jugador.actualizarPuntaje(-e.puntosQueResta());
		jugador.getInfo().restarVida();	
	}

	

	public int obtenerPuntosEstrella() {
		return 20;
	}

	public int obtenerPuntosSChamp() {
		return 10;
	}

	public int obtenerPuntosFFuego() {
		return 5;
	}

	@Override
	public void disparar() {
	}

	public void setFabrica(GameFactory factory) {
		this.fabrica = factory;
		this.rutaCarpeta = fabrica.getRutaCarpeta();
		inicializarSprites(rutaCarpeta);
	}
	
	public void inicializarSprites(String rutaCarpeta) {
		this.sprite = new Sprite(rutaCarpeta + "/mario.png");
		this.spriteIzq = new Sprite(rutaCarpeta + "/mariomirandoizq.png");
		this.spriteSaltoDer = new Sprite(rutaCarpeta + "/mariosaltandoder.png");
		this.spriteSaltoIzq = new Sprite(rutaCarpeta + "/mariosaltandoizq.png");
		this.spriteInvulnerableDer = new Sprite(rutaCarpeta + "/invulnerablemini.png");
		this.spriteInvulnerableIzq = new Sprite(rutaCarpeta + "/invulnerableminimirandoizq.png");
		this.spriteInvulnerableSaltandoDer = new Sprite(rutaCarpeta + "/invulnerableminisaltandoder.png");
		this.spriteInvulnerableSaltandoIzq = new Sprite(rutaCarpeta + "/invulnerableminisaltandoizq.png");	
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public void actualizarSprite() {
		if (jugador.getVelocidadX()!= 0) {
			ultimaVelocidadX = jugador.getVelocidadX();
		}
		if (jugador.isJumped()) {
			jugador.setImagen(ultimaVelocidadX >= 0 ? spriteSaltoDer : spriteSaltoIzq);
		} else if ((ultimaVelocidadX > 0)) {
			jugador.setImagen(sprite);
		} else if ((ultimaVelocidadX < 0)) { 
			jugador.setImagen(spriteIzq);
		}
	}
	
	public Sprite obtenerSpriteInvulnerableDer() {
		return spriteInvulnerableDer;
	}
	
	public Sprite obtenerSpriteInvulnerableIzq() {
		return spriteInvulnerableIzq;
		
	}
	
	public Sprite obtenerSpriteInvulnerableSaltandoDer() {
		return spriteInvulnerableSaltandoDer;
	}
	
	public Sprite obtenerSpriteInvulnerableSaltandoIzq() {
		return spriteInvulnerableSaltandoIzq;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void actualizarInvulnerabilidad() { 
	    if (invulnerable) {
	        long tiempoActual = System.currentTimeMillis();
	        if (tiempoActual - tiempoInicioInvulnerable >= DURACION_INVULNERABLE) {
	            invulnerable = false;
	        }
	    }
	}
	
}
