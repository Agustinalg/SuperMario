package colisiones;

import elementos.BolaDeFuego;
import elementos.Enemigo;
import elementos.PowerUp;
import juego.Jugador;

public interface Visitor {
	
	
	public void visitar(Jugador jugador);
	public void visitar(Enemigo enemigo);
	public void visitar(PowerUp power);
	public void visitar(BolaDeFuego bola);
}
