package states;

import archivos.Sprite;
import elementos.BolaDeFuego;
import juego.Jugador;
import parseo.ConstantesHitbox;
import parseo.GameFactory;

public class Fuego extends SuperMario{

	protected Sprite sprite, spriteIzq, spriteSaltoDer, spriteSaltoIzq;
	protected Sprite spriteInvulnerableDer, spriteInvulnerableIzq, spriteInvulnerableSaltandoDer, spriteInvulnerableSaltandoIzq;
	protected String rutaCarpeta;
	protected State estadoAnterior;
	protected boolean invulnerable = false;
	protected long tiempoInicioInvulnerable;
	protected static final long DURACION_INVULNERABLE = 3000;


	public Fuego(Jugador jugador) {
		super(jugador);
		controlador = null;
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
		jugador.setAlto(ConstantesHitbox.altoJugadorGrande);
		jugador.setAncho(ConstantesHitbox.anchoJugadorGrande);

		int posY = (int) (jugador.getPosY() + (ConstantesHitbox.altoJugadorGrande - jugador.getHitbox().getHeight()));

		this.jugador.setPosY(posY);
		this.jugador.getHitbox().setBounds(jugador.getPosX(), posY, ConstantesHitbox.anchoJugadorGrande, ConstantesHitbox.altoJugadorGrande);
	}

	public void disparar() { 
		lanzarBolaDeFuego();
	}

	public void lanzarBolaDeFuego() {   
		int direccionX = jugador.estaMirandoDerecha() ? 1 : -1;

		int posicionYBola = (int) (jugador.getPosY() - jugador.getHitbox().getHeight()/4);
		BolaDeFuego nuevaBola = this.fabrica.crearBolaDeFuego(jugador.getPosX(), posicionYBola); //ver pos
		nuevaBola.setVelX(direccionX * 5);
	}

	@Override
	public void recibirSuperChampiñon() {
		jugador.actualizarPuntaje(this.obtenerPuntosSChamp());
	}

	@Override
	public void recibirFlorDeFuego() {
		jugador.actualizarPuntaje(this.obtenerPuntosFFuego());
	}

	public void recibirEstrella() {
		jugador.actualizarPuntaje(this.obtenerPuntosEstrella());
		this.getInvulnerable().setControlador(controlador);
		this.getInvulnerable().setAnterior(this);
		this.getInvulnerable().activar();
	}

	public int obtenerPuntosFFuego() {
		return 50;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public void setFabrica(GameFactory factory) {
		this.fabrica = factory;
		this.rutaCarpeta = fabrica.getRutaCarpeta();
		inicializarSprites(rutaCarpeta);
	}

	public void inicializarSprites(String rutaCarpeta) {
		this.sprite = new Sprite(rutaCarpeta + "/mariofuego.png");
		this.spriteIzq = new Sprite(rutaCarpeta + "/mariofuegomirandoizq.png");
		this.spriteSaltoDer = new Sprite(rutaCarpeta + "/mariofuegosaltandoder.png");
		this.spriteSaltoIzq = new Sprite(rutaCarpeta + "/mariofuegosaltandoizq.png");
		inicializarSpritesInvulnerable();	
	}

	private void inicializarSpritesInvulnerable() {
		this.spriteInvulnerableDer = new Sprite(rutaCarpeta + "/invulnerable.png");
		this.spriteInvulnerableIzq = new Sprite(rutaCarpeta + "/invulnerablegrandemirandoizq.png");
		this.spriteInvulnerableSaltandoDer = new Sprite(rutaCarpeta + "/invulnerablegrandesaltandoder.png");
		this.spriteInvulnerableSaltandoIzq = new Sprite(rutaCarpeta + "/invulnerablegrandesaltandoizq.png");
	}

	public void moverDerecha() {
		jugador.setMirandoDerecha(true);
		jugador.setImagen(sprite);
	}

	public void moverIzquierda() {
		jugador.setMirandoDerecha(false);
		jugador.setImagen(spriteIzq);
	}

	public void saltar() {
		if (!jugador.isJumped()) {
			jugador.setImagen(jugador.getVelocidadX() >= 0 ? spriteSaltoDer : spriteSaltoIzq);
		}
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

	public Sprite getSprite() {
		return sprite;
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

}
