package states;

import archivos.ControladorSonidos;
import archivos.Sprite;
import archivos.TipoSonidos;
import elementos.Enemigo;
import juego.Jugador;
import parseo.ConstantesHitbox;
import parseo.GameFactory;


public class SuperMario extends State{

	protected Sprite sprite;
	protected Sprite spriteInvulnerableDer, spriteInvulnerableIzq, spriteInvulnerableSaltandoDer, spriteInvulnerableSaltandoIzq;
	protected Sprite spriteIzq;
	protected Sprite spriteSaltoDer;
	protected Sprite spriteSaltoIzq;
	protected String rutaCarpeta;
	protected long tiempoActivacion;
	protected boolean invulnerable = false;
	protected long tiempoInicioInvulnerable;
	protected static final long DURACION_INVULNERABLE = 3000; 

	public SuperMario(Jugador jugador) {
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
		jugador.setAlto(ConstantesHitbox.altoJugadorGrande);
		jugador.setAncho(ConstantesHitbox.anchoJugadorGrande);
	
		int posY = (int) (jugador.getPosY() + (ConstantesHitbox.altoJugadorGrande - jugador.getHitbox().getHeight()));
		
		this.jugador.setPosY(posY);
		this.jugador.getHitbox().setBounds(jugador.getPosX(), posY, ConstantesHitbox.anchoJugadorGrande, ConstantesHitbox.altoJugadorGrande); 
	}

	public void recibirDaño(Enemigo e) {
		jugador.actualizarPuntaje(-e.puntosQueResta());
		long tiempoActual = System.currentTimeMillis();
		
		if(!(invulnerable && tiempoActual - tiempoInicioInvulnerable < DURACION_INVULNERABLE)) {
			invulnerable = true; 
		    tiempoInicioInvulnerable = tiempoActual;
		    this.getNormal().activar();
		}
	}

	public void reproducirSonidoSalto() {
		ControladorSonidos.getInstance().reproducirSonidoAccion(TipoSonidos.saltaSuperMario);
	}

	public void recibirSuperChampiñon() {
		jugador.actualizarPuntaje(this.obtenerPuntosSChamp());
	}

	public void recibirFlorDeFuego() {
		jugador.actualizarPuntaje(this.obtenerPuntosFFuego());
		this.getFuego().setControlador(controlador);
		this.getFuego().setFabrica(fabrica);
		this.getFuego().activar();
	}

	public void recibirEstrella() {
		jugador.actualizarPuntaje(this.obtenerPuntosEstrella());
		this.getInvulnerable().setControlador(controlador);
		this.getInvulnerable().setFabrica(fabrica);
		this.getInvulnerable().setAnterior(this);
		this.getInvulnerable().activar();
	}


	public int obtenerPuntosEstrella() {
		return 30;
	}

	public int obtenerPuntosSChamp() {
		return 50;
	}

	public int obtenerPuntosFFuego() {
		return 30;
	}

	@Override
	public void disparar() {
	}

	public void setFabrica(GameFactory factory) {
		this.fabrica = factory;
		this.rutaCarpeta = fabrica.getRutaCarpeta();
		inicializarSprites(rutaCarpeta);
	}
	
	protected void inicializarSprites(String rutaCarpeta) {
		this.sprite = new Sprite(rutaCarpeta + "/supermario.png");
		this.spriteIzq = new Sprite(rutaCarpeta + "/supermariomirandoizq.png");
		this.spriteSaltoDer = new Sprite(rutaCarpeta + "/supermariosaltandoder.png");
		this.spriteSaltoIzq = new Sprite(rutaCarpeta + "/supermariosaltandoizq.png");
		inicializarSpritesInvulnerable();
	}
	
	private void inicializarSpritesInvulnerable() {
		this.spriteInvulnerableDer = new Sprite(rutaCarpeta + "/invulnerable.png");
		this.spriteInvulnerableIzq = new Sprite(rutaCarpeta + "/invulnerablegrandemirandoizq.png");
		this.spriteInvulnerableSaltandoDer = new Sprite(rutaCarpeta + "/invulnerablegrandesaltandoder.png");
		this.spriteInvulnerableSaltandoIzq = new Sprite(rutaCarpeta + "/invulnerablegrandesaltandoizq.png");
	}
	
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
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
}
