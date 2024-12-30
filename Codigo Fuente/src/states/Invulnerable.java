package states;

import javax.swing.Timer;

import archivos.ControladorSonidos;
import archivos.Sprite;
import archivos.TipoSonidos;
import elementos.Enemigo;
import juego.Jugador;
import parseo.GameFactory;


public class Invulnerable extends State{

	protected long tiempoActivacion;
	protected final long duracion = 6500;
	protected State estadoAnterior;
	protected Sprite spriteDer, spriteGrande, spriteIzq, spriteSaltoIzq, spriteSaltoDer;

	protected Timer timer;


	public Invulnerable(Jugador jugador) {
		super(jugador);
		this.estadoAnterior = jugador.getState();
	}

	public void activar() {
		jugador.setState(this);
		spriteDer = estadoAnterior.obtenerSpriteInvulnerableDer();
		spriteIzq = estadoAnterior.obtenerSpriteInvulnerableIzq();
		spriteSaltoIzq = estadoAnterior.obtenerSpriteInvulnerableSaltandoIzq();
		spriteSaltoDer = estadoAnterior.obtenerSpriteInvulnerableSaltandoDer();
		
		jugador.getSprite().setSprite(spriteDer.getRutaImagen());
		this.jugador.pararSonidoPartida();
		ControladorSonidos.getInstance().detenerSonidoJuego(TipoSonidos.speedBackground);
		ControladorSonidos.getInstance().reproducirSonidoAccion(TipoSonidos.agarroEstrella);
		iniciarTemporizador();
		actualizarMedidas();
		
		if (estadoAnterior != null) {
	        // Aquí deberías asegurarte de que el sprite sea el correspondiente al estado anterior
	        // Esto puede implicar establecer el sprite de SuperMario, o el estado correspondiente
	        //jugador.getSprite().setSprite(estadoAnterior.getSprite().getRutaImagen());
	    }
	}

	protected void actualizarMedidas() {
		estadoAnterior.actualizarMedidas();
	}

	public void reproducirSonidoSalto() {
		estadoAnterior.reproducirSonidoSalto();
	}

	private void iniciarTemporizador() {
		timer = new Timer((int) duracion, e -> { 
			ControladorSonidos.getInstance().detenerSonidoAccion(TipoSonidos.agarroEstrella);

			// Verificar si es necesario reactivar speedBackground
			if (this.jugador.tiempoRestante()<= 60) {
				ControladorSonidos.getInstance().reproducirSonidoJuego(TipoSonidos.speedBackground);
			}else {
				//VER FORMA DE SEGUIR ENCAPSULAMIENTO
				if (!this.jugador.getInfo().getColisiones().getControladorPartida().getHiloSonido().enEjecucion()) {
					this.jugador.getInfo().getColisiones().getControladorPartida().getHiloSonido().renaudar();
				}
			}	
			estadoAnterior.activar();
			this.timer.stop();
		});
		timer.setRepeats(false); // Asegurarse de que solo se ejecute una vez
		timer.start();
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
	}

	/*
	public void actualizar() {
		
		long ahora = System.currentTimeMillis();
		if ((ahora - tiempoActivacion) >= duracion) {
			this.estadoAnterior.activar();
		}
	} */

	public void recibirDaño(Enemigo e) {
		jugador.actualizarPuntaje(e.puntosQueDa());
		e.morir();
	}
	
	public Sprite obtenerSpriteInvulnerable() {
		return spriteDer;
	}

	public int obtenerPuntosEstrella() {
		return 35;
	}

	public int obtenerPuntosSChamp() {
		return estadoAnterior.obtenerPuntosSChamp();
	}

	public int obtenerPuntosFFuego() {
		return estadoAnterior.obtenerPuntosFFuego();
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	@Override
	public void disparar() {	
	}
	
	public void setFabrica(GameFactory factory) {
		/*
		this.fabrica = factory;
		this.rutaCarpeta = fabrica.getRutaCarpeta();
		this.spriteDer = new Sprite(rutaCarpeta + "/invulnerablemini.png");
		this.spriteIzq = new Sprite(rutaCarpeta + "/invulnerableminimirandoizq.png");
		this.spriteSaltoDer = new Sprite(rutaCarpeta + "/invulnerableminisaltandoder.png");
		this.spriteSaltoIzq = new Sprite(rutaCarpeta + "/invulnerableminisaltandoizq.png");	
		this.spriteGrande = new Sprite(rutaCarpeta + "/invulnerable.png"); 
		//this.spriteNormal = new Sprite(rutaCarpeta + "/invulnerablemini.png");
		 * */
		 
	}

	public void setAnterior(State anterior) {
		this.estadoAnterior = anterior;
	}
	
	public void moverDerecha() {
		jugador.setImagen(spriteDer);
		//jugador.setImagen(estadoAnterior.obtenerSpriteInvulnerableDer());
	}

	public void moverIzquierda() {
		jugador.setImagen(spriteIzq);
		//jugador.setImagen(estadoAnterior.obtenerSpriteInvulnerableIzq());
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
			jugador.setImagen(spriteDer);
		} else if ((ultimaVelocidadX < 0)) { 
			jugador.setImagen(spriteIzq);
		}
	}
	
	public Sprite obtenerSpriteInvulnerableDer() {
		return spriteDer;
	}
	public Sprite obtenerSpriteInvulnerableIzq() {
		return spriteIzq;
	}
	
	public Sprite obtenerSpriteInvulnerableSaltandoDer() {
		return spriteSaltoDer;
	}
	
	public Sprite obtenerSpriteInvulnerableSaltandoIzq() {
		return spriteSaltoIzq;		
	}
	
	public Sprite getSprite() { //este no se usa
		return spriteDer;
	} 

}
