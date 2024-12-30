package juego;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import elementos.*;

public class ColisionesNivel {

	protected List<Plataforma> plataformas;
	protected List<Enemigo> enemigos;
	protected List<PowerUp> powerUps;
	protected List<BolaDeFuego> bolasDeFuego;
	protected Jugador jugador;
	protected ControladorPartida controladorPartida;

	protected List<Enemigo> enemigosPendientes;
	protected List<BolaDeFuego> bolasFuegoPendientes;

	public ColisionesNivel(){
		plataformas = new LinkedList<Plataforma>();
		enemigos = new LinkedList<Enemigo>();
		powerUps = new LinkedList<PowerUp>();
		bolasDeFuego = new LinkedList<BolaDeFuego>();

		enemigosPendientes = new ArrayList<>(); 
		bolasFuegoPendientes = new ArrayList<>(); 
	}

	//Metodos para gestion de colisiones
	public void actualizarEntidades() {
		actualizarJugador();
		actualizarEnemigos();
		actualizarBolasDeFuego();
		agregarPendientes();
	}

	private void actualizarJugador() {
		jugador.actualizar();
		detectarColisionJugador();
	}

	private void actualizarEnemigos() {
		Iterator<Enemigo> iteratorE = enemigos.iterator();	 
		while (iteratorE.hasNext()) {
			Enemigo e = iteratorE.next();
			e.actualizar();
			detectarColisionEnemigos(e);	
			if(e.estaMuerto()) {
				iteratorE.remove();
			}
		}
	}

	private void actualizarBolasDeFuego() {
		Iterator<BolaDeFuego> iteratorBF = bolasDeFuego.iterator();
		while(iteratorBF.hasNext()) {
			BolaDeFuego b = iteratorBF.next();
			b.actualizar();
			detectarColisionBolasDeFuego(b);
			if(b.estaMuerto()) {
				iteratorBF.remove();
			}
		}
	}

	private void agregarPendientes() {
		if (!enemigosPendientes.isEmpty()) {
			enemigos.addAll(enemigosPendientes);
			enemigosPendientes.clear();
		}
		if (!bolasFuegoPendientes.isEmpty()) {
			bolasDeFuego.addAll(bolasFuegoPendientes);
			bolasFuegoPendientes.clear();
		}
	}
	
	public void detectarColisionJugador() {
		colisionesJugadorEnemigos();
		colisionesJugadorPowerUps();
		colisionesJugadorPlataformas();
	}

	private void colisionesJugadorPlataformas() {	
		Iterator<Plataforma> iteratorPlat = plataformas.iterator();    
		while (iteratorPlat.hasNext()) {
			Plataforma p = iteratorPlat.next();

			if (intersecta(jugador, p)) {
				jugador.aceptarVisita(p);
				if(p.estaMuerto()) {
					iteratorPlat.remove();
				}
			} 
		}
	}

	private boolean intersecta(Elemento elem1, Elemento elem2) {
		boolean colisionaPorAbajo = colisionAbajo(elem1, elem2);
		boolean colisionaPorArriba = colisionArriba(elem1, elem2);
		boolean colisionaPorIzquierda = colisionIzquierda(elem1, elem2);
		boolean colisionaPorDerecha = colisionDerecha(elem1, elem2);
		
		boolean intersecta = ((colisionaPorAbajo || colisionaPorArriba) && (colisionaPorIzquierda || colisionaPorDerecha)); 	
		
		return intersecta;
	}

	private boolean colisionDerecha(Elemento elem1, Elemento elem2) {
		int extrIzqJugador = elem1.getPosX();
		int extrIzqElemento = elem2.getPosX();
		int extrDerElemento = elem2.getPosX() + elem2.getAncho();

		boolean colision = ((extrDerElemento >= extrIzqJugador) && (extrIzqJugador >= extrIzqElemento)); //colisiona por la derecha
		
		return colision;
	}

	private boolean colisionIzquierda(Elemento elem1, Elemento elem2) {
		int extrDerJugador = elem1.getPosX() + elem1.getAncho();
		int extrIzqElemento = elem2.getPosX();
		int extrDerElemento = elem2.getPosX() + elem2.getAncho();

		boolean colision = ((extrDerJugador >= extrIzqElemento) && (extrDerElemento >= extrDerJugador)); //colisiona por la izquierda
		
		return colision;
	}

	private boolean colisionArriba(Elemento elem1, Elemento elem2) {
		int techoJugador = elem1.getPosY();
		int pisoJugador = elem1.getPosY() - elem1.getAlto();
		int techoElemento = elem2.getPosY();	
		
		boolean colision = ((techoJugador >= techoElemento) && (techoElemento >= pisoJugador)); //colisiona arriba
		
		return colision;
	}

	private boolean colisionAbajo(Elemento elem1, Elemento elem2) {
		int techoJugador = elem1.getPosY();
		int pisoJugador = elem1.getPosY() - elem1.getAlto();
		int pisoElemento = elem2.getPosY() - elem2.getAlto();
		
		boolean colisiona = ((techoJugador >= pisoElemento) && (pisoElemento >= pisoJugador)); //colisiona abajo
		
		return colisiona;
	}

	private void colisionesJugadorPowerUps() {
		Rectangle jugadorHitbox = jugador.getHitbox();
		
		Iterator<PowerUp> iteratorPU = powerUps.iterator();    
		while (iteratorPU.hasNext()) {
			PowerUp p = iteratorPU.next();

			if (jugadorHitbox.intersects(p.getHitbox())) {
				jugador.aceptarVisita(p);
				iteratorPU.remove();
			}     
		}
	}

	private void colisionesJugadorEnemigos() {
		Iterator<Enemigo> iteratorE = enemigos.iterator();	 
		while (iteratorE.hasNext()) {
			Enemigo e = iteratorE.next();
			
			if (intersecta(jugador, e)) {
				jugador.aceptarVisita(e);
				if(e.estaMuerto()) {
					iteratorE.remove();
				}
			}
		}
	}

	public void detectarColisionEnemigos(Enemigo e) {
		colisionEnemigosPlataformas(e);
		colisionEnemigosBolasDeFuego(e);
	}

	private void colisionEnemigosBolasDeFuego(Enemigo e) {
		Rectangle enemigoHitbox = e.getHitbox();
		Iterator<BolaDeFuego> iteratorBolasFuego = bolasDeFuego.iterator(); 
		while(iteratorBolasFuego.hasNext()) {
			BolaDeFuego b = iteratorBolasFuego.next();

			if(enemigoHitbox.intersects(b.getHitbox())) {
				e.aceptarVisita(b);
				if(e.estaMuerto()) {
					iteratorBolasFuego.remove();
				}
			}
		}
	}

	private void colisionEnemigosPlataformas(Enemigo e) {
		Iterator<Plataforma> iteratorPlat = plataformas.iterator();    
		while (iteratorPlat.hasNext()) {
			Plataforma p = iteratorPlat.next();

			if (intersecta(e, p)) {
				e.ColisionaConBloque(true);
				e.aceptarVisita(p);
			}
		}
	}

	public void detectarColisionBolasDeFuego(BolaDeFuego b) {
		colisionBolasDeFuegoPlataformas(b);	
	}

	private void colisionBolasDeFuegoPlataformas(BolaDeFuego b) {
		Iterator<Plataforma> iteratorPlat = plataformas.iterator();    
		while (iteratorPlat.hasNext()) {
			Plataforma p = iteratorPlat.next();

			if(intersecta(b, p)){
				System.out.println("detecta interseccion entre bola de fueog y plataforma ");
				b.aceptarVisita(p);
			}
		}
	}

	public synchronized void registrarEnemigo(Enemigo enemigo) {
		enemigosPendientes.add(enemigo);
	}

	public synchronized void registrarBolaDeFuego(BolaDeFuego bolaFuego) {
		bolasFuegoPendientes.add(bolaFuego);
	}

	//Agregar
	public void agregarPlataforma(Plataforma plat){
		this.plataformas.add(plat);
	}

	public void agregarEnemigo(Enemigo enem){
		this.enemigos.add(enem);
	}

	public void agregarPowerUp(PowerUp power){
		this.powerUps.add(power);
	}

	public void agregarBolaDeFuego(BolaDeFuego bola){
		this.bolasDeFuego.add(bola);
	}

	public void agregarJugador(Jugador player){
		this.jugador = player;
	}

	//Getters
	public List<Plataforma> getPlataformas(){
		return plataformas;
	}

	public List<Enemigo> getEnemigos(){
		return enemigos;
	}

	public List<PowerUp> getPowerUps(){
		return powerUps;
	}

	public List<BolaDeFuego> getBolasDeFuego(){
		return bolasDeFuego;
	}

	public Jugador getJugador(){
		return this.jugador;
	}

	public ControladorPartida getControladorPartida() {
		return this.controladorPartida;
	}

	public void setControladorPartida(ControladorPartida partida) { 
		this.controladorPartida = partida;
	}

	public int getTiempoRestante() {
		return controladorPartida.timeLeft();
	}

	public void pararSonidoPartida() {
		controladorPartida.pararSonido();
	}
}
