package juego;

public class InfoJugador {

	protected Jugador jugador;
	protected int monedas;
	protected int puntaje;
	protected int vida;
	protected ColisionesNivel colisiones;

	public InfoJugador(Jugador jugador) {
		this.jugador = jugador;
		puntaje = 0;
		monedas = 0;
		vida = 3;
	}

	public void actualizarPuntaje(int puntos) {
		int resultado = puntaje + puntos; 
		if(resultado < 0) {
			puntaje = 0;
		}else {
			puntaje = resultado; 
		}
	}

	public void aumentarMoneda() {
		this.monedas++;
	}

	public void sumarVida() {
		this.vida++;
	}

	public void restarVida() {
		if (vida >= 2) {
			vida--;
			reiniciarNivel();
		}else {
			morir();
		}
	}

	private void reiniciarNivel() {
		this.colisiones.getControladorPartida().reiniciarNivel();
	}

	private void morir() {
		this.colisiones.getControladorPartida().gameOver();
	}

	//Set
	public void setVidas(int vida) {
		this.vida = vida;
	}

	public void setMonedas(int monedas) {
		this.monedas = monedas;
	}

	public void setColisiones(ColisionesNivel nivel) {
		this.colisiones = nivel;
	}
	public void setPuntaje(int p) {
		puntaje = p;
	}

	//Get
	public int getPuntaje() {
		return this.puntaje;
	}

	public int getMonedas() {
		return this.monedas;
	}

	public int getVida() {
		return this.vida;
	}

	public Jugador getJugador() {
		return this.jugador;
	}

	public ColisionesNivel getColisiones() {
		return this.colisiones;
	}

	public int tiempoRestante() {
		return colisiones.getTiempoRestante();
	}
}
