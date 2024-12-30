package vista;

import javax.swing.Timer;

import elementos.ElementoJugador;
import elementos.ElementoLogico;
import observers.Observer;

public interface ControladorEntreJuegoVista {

	public void mostrarPantallaJuego();
	public void mostrarPantallaSeleccion();
	public void mostrarPantallaGameOver();
	public void mostrarPantallaTimeUp();
	public void mostrarPantallaVictoria();

	public Observer registrarElemento(ElementoLogico elem);
	public Observer registrarElemento(ElementoJugador jugador);
	public void reiniciarNivel();
	//public void removerObserver(ObserverGrafico observer);
	public Timer getTimerNivel();
	public int getTiempoRestante();
}
