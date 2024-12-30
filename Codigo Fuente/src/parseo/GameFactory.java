package parseo;

import archivos.Sprite;
import elementos.BolaDeFuego;
import enemigos.*;
import powerUps.*;
import plataformas.*;
import juego.*;

public abstract class GameFactory {

	protected String rutaCarpeta;
	protected ControladorPartida controladorPartida;
	protected ColisionesNivel colision;


	protected GameFactory(String ruta) {
		this.rutaCarpeta = ruta;
	}

	public void setControladorPartida(ControladorPartida partida) {
		this.controladorPartida = partida;
	}
	
	//ahora seteo alto y ancho segun las constantes, despues veo si puedo usando el hitbox

	public Jugador crearJugador(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/mario.png");
		Jugador jugador = new Jugador(x, y, sprite);
		
		int ancho = ConstantesHitbox.anchoJugador;
		int alto = ConstantesHitbox.altoJugador;
		jugador.setHitbox(ancho, alto);
		jugador.setAlto(jugador.getHitbox().height);
		jugador.setAncho(jugador.getHitbox().width);
		
		jugador.getState().setFabrica(this);
		jugador.getState().setControlador(controladorPartida);

		return jugador;
	}

	//Enemigos
	public Lakitu crearLakitu(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/lakitu1.png");
		Lakitu lakitu = new Lakitu(x, y, sprite);
		lakitu.setFabrica(this);
		lakitu.setControlador(controladorPartida);
		
		int ancho = ConstantesHitbox.anchoLakitu;
		int alto = ConstantesHitbox.altoLakitu;
		lakitu.setHitbox(ancho, alto);
		lakitu.setAlto(alto);
		lakitu.setAncho(ancho);

		return lakitu;
	}

	public Koopa crearKoopa(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/koopa1.png");
		Koopa koopa = new Koopa(x, y, sprite);
		
		int ancho = ConstantesHitbox.anchoKoopa;
		int alto = ConstantesHitbox.altoKoopa;
		koopa.setHitbox(ancho, alto);
		koopa.setAlto(alto);
		koopa.setAncho(ancho);

		return koopa;
	}

	public Goomba crearGoomba(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/goomba1.png");
		Goomba goomba = new Goomba(x, y, sprite);
		
		int ancho = ConstantesHitbox.anchoGoomba;
		int alto = ConstantesHitbox.altoGoomba;
		goomba.setHitbox(ancho, alto);
		goomba.setAlto(alto);
		goomba.setAncho(ancho);

		return goomba;
	}

	public Spiny crearSpiny(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/spiny1.png");
		Spiny spiny = new Spiny(x, y, sprite);
		
		int ancho = ConstantesHitbox.anchoSpiny;
		int alto = ConstantesHitbox.altoSpiny;
		spiny.setHitbox(ancho, alto);
		spiny.setAlto(alto);
		spiny.setAncho(ancho);
		
		colision.registrarEnemigo(spiny);
		controladorPartida.registrarObserverElementoIndividual(spiny);
		spiny.setColisiones(colision);

		return spiny;
	}

	public Piranha crearPiranha(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/piranha1.png");
		Piranha piranha = new Piranha(x, y + 35, sprite);
		
		int ancho = ConstantesHitbox.anchoPiranha;
		int alto = ConstantesHitbox.altoPiranha;
		piranha.setHitbox(ancho, alto);
		piranha.setAlto(alto);
		piranha.setAncho(ancho);

		return piranha;
	}

	public Buzzy crearBuzzy(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/buzzy1.png");
		Buzzy buzzy = new Buzzy(x, y, sprite);
		
		int ancho = ConstantesHitbox.anchoBuzzy;
		int alto = ConstantesHitbox.altoBuzzy;
		buzzy.setHitbox(ancho, alto);
		buzzy.setAlto(alto);
		buzzy.setAncho(ancho);

		return buzzy;
	}

	//Power Ups
	public SuperChampiñon crearSuperChampiñon(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/bloqueTransparente.png");
		SuperChampiñon superChamp = new SuperChampiñon(x, y, sprite);
		
		int ancho = ConstantesHitbox.anchoSuperChampiñon;
		int alto = ConstantesHitbox.altoSuperChampiñon;
		superChamp.setHitbox(ancho, alto);
		superChamp.setAlto(alto);
		superChamp.setAncho(ancho);
		
		controladorPartida.registrarObserverElementoIndividual(superChamp);
		superChamp.setColisiones(colision);
		superChamp.setFabrica(this);
		
		return superChamp;
	}

	public Estrella crearEstrella(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/bloqueTransparente.png");
		Estrella estrella = new Estrella(x, y, sprite);
		
		int ancho = ConstantesHitbox.anchoEstrella;
		int alto = ConstantesHitbox.altoEstrella;
		estrella.setHitbox(ancho, alto);
		estrella.setAlto(alto);
		estrella.setAncho(ancho);
		
		controladorPartida.registrarObserverElementoIndividual(estrella);
		estrella.setColisiones(colision);
		estrella.setFabrica(this);

		return estrella;
	}

	public ChampiñonVerde crearChampiñonVerde(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/bloqueTransparente.png");
		ChampiñonVerde champVerde = new ChampiñonVerde(x, y, sprite);
		
		int ancho = ConstantesHitbox.anchoChampiñonVerde;
		int alto = ConstantesHitbox.altoChampiñonVerde;
		champVerde.setHitbox(ancho, alto);
		champVerde.setAlto(alto);
		champVerde.setAncho(ancho);
		
		controladorPartida.registrarObserverElementoIndividual(champVerde);
		champVerde.setColisiones(colision);
		champVerde.setFabrica(this);

		return champVerde;
	}

	public FlorDeFuego crearFlorDeFuego(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/bloqueTransparente.png");
		FlorDeFuego florFuego = new FlorDeFuego(x, y, sprite);
		
		int ancho = ConstantesHitbox.anchoFlorDeFuego;
		int alto = ConstantesHitbox.altoFlorDeFuego;
		florFuego.setHitbox(ancho, alto);
		florFuego.setAlto(alto);
		florFuego.setAncho(ancho);
		
		controladorPartida.registrarObserverElementoIndividual(florFuego);
		florFuego.setColisiones(colision);
		florFuego.setFabrica(this);

		return florFuego;
	}

	public Moneda crearMoneda(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/bloqueTransparente.png");
		Moneda moneda = new Moneda(x, y + 5, sprite);
		
		int ancho = ConstantesHitbox.anchoMoneda;
		int alto = ConstantesHitbox.altoMoneda;
		moneda.setHitbox(ancho, alto);
		moneda.setAlto(alto);
		moneda.setAncho(ancho);
		
		controladorPartida.registrarObserverElementoIndividual(moneda);
		moneda.setColisiones(colision);
		moneda.setFabrica(this);

		return moneda;
	}
	
	public Moneda crearMonedaSola(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/moneda.png");
		Moneda moneda = new Moneda(x, y + 5, sprite);
		
		int ancho = ConstantesHitbox.anchoMoneda;
		int alto = ConstantesHitbox.altoMoneda;
		moneda.setHitbox(ancho, alto);
		moneda.setAlto(alto);
		moneda.setAncho(ancho);

		moneda.setColisiones(colision);
		moneda.setFabrica(this);

		return moneda;
	}

	//Bola de Fuego
	public BolaDeFuego crearBolaDeFuego(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/bolaDeFuego.png");
		BolaDeFuego bolaFuego = new BolaDeFuego(x, y, sprite);
		
		int ancho = ConstantesHitbox.anchoBolaDeFuego;
		int alto = ConstantesHitbox.altoBolaDeFuego;
		bolaFuego.setHitbox(ancho, alto);
		bolaFuego.setAlto(alto);
		bolaFuego.setAncho(ancho);
	
		colision.registrarBolaDeFuego(bolaFuego);
		controladorPartida.registrarObserverElementoIndividual(bolaFuego);
		bolaFuego.setColisiones(colision);
		bolaFuego.setJugador(colision.getJugador());
		
		return bolaFuego;
	}

	//Plataformas
	public BloqueDePregunta crearBloqueDePregunta(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/bloqueDePregunta.png");
		BloqueDePregunta bloquePregunta = new BloqueDePregunta(x, y, sprite);
		
		int ancho = ConstantesHitbox.anchoBloque;
		int alto = ConstantesHitbox.altoBloque;
		bloquePregunta.setHitbox(ancho, alto);
		bloquePregunta.setAlto(alto);
		bloquePregunta.setAncho(ancho);
		bloquePregunta.setFabrica(this);
		bloquePregunta.setControlador(controladorPartida);

		return bloquePregunta;
	}

	public LadrilloSolido crearLadrilloSolido(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/ladrilloSolido.png");
		LadrilloSolido ladrillo = new LadrilloSolido(x, y, sprite);
		
		int ancho = ConstantesHitbox.anchoBloque;
		int alto = ConstantesHitbox.altoBloque;
		ladrillo.setHitbox(ancho, alto);
		ladrillo.setAlto(alto);
		ladrillo.setAncho(ancho);

		return ladrillo;
	}

	public Vacio crearVacio(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/vacio.png");
		Vacio vacio = new Vacio(x, y, sprite);
		
		int ancho = ConstantesHitbox.anchoBloque;
		int alto = ConstantesHitbox.altoBloque;
		vacio.setHitbox(ancho, alto);
		vacio.setAlto(alto);
		vacio.setAncho(ancho);

		return vacio;
	}

	public Tuberia crearTuberia(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/tuberia.png");
		Tuberia tuberia = new Tuberia(x, y + 35, sprite);
		tuberia.setFabrica(this);
		tuberia.setControlador(controladorPartida);
		
		int ancho = ConstantesHitbox.anchoTuberia1;
		int alto = ConstantesHitbox.altoTuberia1;
		tuberia.setHitbox(ancho, alto);
		tuberia.setAlto(alto);
		tuberia.setAncho(ancho);

		return tuberia;
	} 

	public BloqueSolido crearBloqueTransparente(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/bloqueTransparente.png");
		BloqueSolido bloqueSolido = new BloqueSolido(x, y, sprite);
		
		int ancho = ConstantesHitbox.anchoBloque;
		int alto = ConstantesHitbox.altoBloque;
		bloqueSolido.setHitbox(ancho, alto);
		bloqueSolido.setAlto(alto);
		bloqueSolido.setAncho(ancho);

		return bloqueSolido;
	}

	public BloqueSolido crearBloqueSolido(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/bloqueSolido.png");
		BloqueSolido bloqueSolido = new BloqueSolido(x, y, sprite);
		
		int ancho = ConstantesHitbox.anchoBloque;
		int alto = ConstantesHitbox.altoBloque;
		bloqueSolido.setHitbox(ancho, alto);
		bloqueSolido.setAlto(alto);
		bloqueSolido.setAncho(ancho);
	

		return bloqueSolido;
	}

	public Castillo crearCastillo(int x, int y) {
		Sprite sprite = new Sprite(rutaCarpeta + "/bloqueTransparente.png");
		Castillo castillo = new Castillo(x, y, sprite);
		
		int ancho = ConstantesHitbox.anchoBloque;
		int alto = ConstantesHitbox.altoBloque;
		castillo.setHitbox(ancho, alto);
		castillo.setAlto(alto);
		castillo.setAncho(ancho);

		return castillo;
	}

	public String getRutaCarpeta() {
		return this.rutaCarpeta;
	}

	public void setColisiones(ColisionesNivel colisionNivel) {
		this.colision = colisionNivel;
	}

}

