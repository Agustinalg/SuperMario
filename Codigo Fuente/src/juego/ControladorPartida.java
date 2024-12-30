package juego;

import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.Timer;

import parseo.*;
import archivos.ControladorSonidos;
import archivos.TipoSonidos;
import archivos.TopRanking;
import archivos.Usuario;
import elementos.*;
import observers.Observer;
import vista.ControladorEntreJuegoVista;

public class ControladorPartida {

	protected ControladorEntreJuegoVista pantallas;
	protected NivelBuilder creadorNivel;
	protected GameFactory fabrica;
	protected TopRanking ranking;
	protected ColisionesNivel colisiones;
	protected int numNivelActual;
	protected HiloElementos hiloElementos;
	protected HiloSonido hiloSonido;
	protected String nombreUsuario;
	protected boolean[] keyDown;


	public ControladorPartida(TopRanking r) {
		this.numNivelActual = 1;
		ranking = r;
		keyDown = new boolean[3];
	}

	//Gestion de niveles
	public void iniciarPartida(GameFactory factory, int numeroNivel){
		this.fabrica = factory;
		fabrica.setControladorPartida(this);
		this.creadorNivel = new NivelBuilder(fabrica, numeroNivel);
		this.colisiones = this.creadorNivel.getNivel();
		colisiones.setControladorPartida(this);
		registrarObservers();

		inicializarHilos(colisiones);
	}

	private void inicializarHilos(ColisionesNivel colisiones) {
		hiloElementos = new HiloElementos(this,colisiones); 
		hiloElementos.start();
		if (hiloSonido == null || !hiloSonido.isAlive()) {
	        hiloSonido = new HiloSonido();
	        hiloSonido.start();
	    }
	}

	public void reiniciarNivel(){
		detenerHilos();
		sonidoReinicio();
		timerParaReiniciar();
	}

	private void detenerHilos() {
		hiloSonido.pararLoop();
		hiloElementos.detener();
	}

	private void sonidoReinicio() {
        ControladorSonidos.getInstance().detenerSonidoJuego(TipoSonidos.speedBackground);
        ControladorSonidos.getInstance().reproducirSonidoAccion(TipoSonidos.muerteMario);
    }

	private void sonidoPaseNivel() {
        ControladorSonidos.getInstance().detenerSonidoJuego(TipoSonidos.speedBackground);
        ControladorSonidos.getInstance().reproducirSonidoJuego(TipoSonidos.finNivel);
        ControladorSonidos.getInstance().reproducirSonidoJuego(TipoSonidos.fuegosArtificiales);
    }

	private void setAtributosInfoJugador(int monedas, int puntaje, int vidas) {
		this.colisiones.getJugador().getInfo().setMonedas(monedas);
		this.colisiones.getJugador().actualizarPuntaje(puntaje);
		this.colisiones.getJugador().getInfo().setVidas(vidas);	
		this.colisiones.getJugador().getInfo().setColisiones(colisiones);
	}

	private void timerParaReiniciar() {
		if (pantallas.getTimerNivel() != null) {
			pantallas.getTimerNivel().stop();
		}
		Timer delayTimer = new Timer(2000, e -> {
			int monedas = this.colisiones.getJugador().getMonedas();
			int puntaje = this.colisiones.getJugador().getPuntaje();
			int vidas = this.colisiones.getJugador().getVida();
			reinicioPantallaYPartida();
			setAtributosInfoJugador(monedas, puntaje, vidas);
		});
		delayTimer.setRepeats(false); // Para que el temporizador solo ejecute una vez
		delayTimer.start();
	}

	private void reinicioPantallaYPartida() {
		pantallas.reiniciarNivel();
		iniciarPartida(this.fabrica, numNivelActual);
	} 

	private void timerParaPasar() {
		if (pantallas.getTimerNivel() != null) {
			pantallas.getTimerNivel().stop();
		}
		Timer delayTimer = new Timer(4500, e -> {
			int monedas = this.colisiones.getJugador().getMonedas();
			int puntaje = this.colisiones.getJugador().getPuntaje();
			int vidas = this.colisiones.getJugador().getVida();
			reinicioPantallaYPartida();
			setAtributosInfoJugador(monedas, puntaje, vidas);
		});
		delayTimer.setRepeats(false); // Para que el temporizador solo ejecute una vez
		delayTimer.start();
	}

	public void siguienteNivel(){
		if(numNivelActual < 3) {
			numNivelActual++;
			detenerHilos();
			sonidoPaseNivel();
			timerParaPasar();
		}else {
			victoria();
		} 
	}

	public void gameOver() {
        numNivelActual = 1;
        detenerHilos();
        ControladorSonidos.getInstance().reproducirSonidoAccion(TipoSonidos.muerteMario);
        pantallas.getTimerNivel().stop();
        agregarUsuario();
        this.pantallas.mostrarPantallaGameOver();
    }

	public void victoria() {
        numNivelActual = 1;
        detenerHilos();
        ControladorSonidos.getInstance().reproducirSonidoJuego(TipoSonidos.victoria);
        pantallas.getTimerNivel().stop();
        agregarUsuario();
        this.pantallas.mostrarPantallaVictoria();
    }

	public void timeOut() {
        if (this.colisiones.jugador.info.vida > 1) {
            numNivelActual = 1;
            this.colisiones.jugador.info.restarVida();
            detenerHilos();
            ControladorSonidos.getInstance().detenerSonidoJuego(TipoSonidos.advertenciaTiempo);
            this.pantallas.mostrarPantallaTimeUp();
            this.reiniciarNivel();
        } else {
            gameOver();
        }
    }

	//Gestion de observers
	private void registrarObservers() {
		registrarObserverJugador(this.colisiones.getJugador());
		registrarObserversPlataformas(this.colisiones.getPlataformas());
		registrarObserversEnemigos(this.colisiones.getEnemigos());
		registrarObserversPowerUps(this.colisiones.getPowerUps());
	}
	private void registrarObserversPowerUps(List<PowerUp> powerUps) {
		for(PowerUp elemento : powerUps) {
			Observer observer = pantallas.registrarElemento(elemento);
			elemento.registrarObserver(observer);
		}
	}

	private void registrarObserverJugador(Jugador player){
		Observer observerJugador = pantallas.registrarElemento(player);
		player.registrarObserver(observerJugador);
	}

	private void registrarObserversPlataformas(List<Plataforma> plataforma){
		for(Plataforma elemento : plataforma) {
			Observer observer = pantallas.registrarElemento(elemento);
			elemento.registrarObserver(observer);
		}
	}
	private void registrarObserversEnemigos(List<Enemigo> enem){
		for(Enemigo elemento : enem) {
			Observer observer = pantallas.registrarElemento(elemento);	        
			elemento.registrarObserver(observer);
		}
	}

	public void registrarObserverElementoIndividual(Elemento elem){
		Observer observer = pantallas.registrarElemento(elem);
		elem.registrarObserver(observer);
	}
	
	//Gestion de teclas
	public void activeMovement(KeyEvent e) {
		int tecla = e.getKeyCode();

		switch (tecla) {
		case KeyEvent.VK_A: 
			colisiones.getJugador().moverIzquierda();
			keyDown[1] = true;
			break;
		case KeyEvent.VK_D: 
			colisiones.getJugador().moverDerecha();
			keyDown[2] = true;
			break;
		case KeyEvent.VK_W:
			colisiones.getJugador().saltar();
			keyDown[0] = true;
			break;
		case KeyEvent.VK_SPACE:
			colisiones.getJugador().disparar();
		}
	}

	public void desactiveMovement (KeyEvent e) {
		int tecla = e.getKeyCode();
		if (tecla == KeyEvent.VK_A) {
			keyDown[1] = false;
		}
		if (tecla == KeyEvent.VK_D) {
			keyDown[2] = false;
		}
		if(!keyDown[1] && !keyDown[2]) {
			colisiones.getJugador().frenarMovimiento(); 
		}		
	}

	//Gestion de Ranking
	public void agregarUsuario() { 
		Usuario ingresado = new Usuario (nombreUsuario);
		ingresado.setPuntajeTotal(colisiones.getJugador().getPuntaje());
		ranking.agregarJugador(ingresado);
	}

	public void guardarNombre(String nombre) {
		nombreUsuario = nombre;
	}
	
	public void pararSonido() {
            hiloSonido.pararLoop();
    }

	//Getters
	
	public TopRanking getRanking(){
		return this.ranking;
	}

	public int getNumNivel() {
		return this.numNivelActual;
	}

	public HiloSonido getHiloSonido() { //este metodo solo es llamado en Invulnerable, ver si puede ser resuelto de otra forma
		return hiloSonido;
	}

	public String getNombreJugador() {
		return nombreUsuario;
	}

	//Setters
	public void setControladorPantallas(ControladorEntreJuegoVista controlador){
		this.pantallas = controlador;
	}

	public void setNombreJugador(String nombre){
		nombreUsuario = nombre;
	}

	public int timeLeft() {
		return pantallas.getTiempoRestante();
	}
}
