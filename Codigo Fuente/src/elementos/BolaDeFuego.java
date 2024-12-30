package elementos;

import archivos.Sprite;
import colisiones.Visitable;
import colisiones.Visitor;
import juego.Jugador;

public class BolaDeFuego extends Elemento implements Visitor, Visitable{
	protected Jugador jugador;
	private int velX;

	public BolaDeFuego(int x, int y, Sprite imagen) {
		super(x, y, imagen);
		velX = 0;
	}

	public void actualizar() {
		this.setPosX(this.hitbox.x + velX);
		notificar(); 	
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	@Override
	public void visitar(Jugador jugador) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitar(PowerUp power) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitar(BolaDeFuego bola) {
		// TODO Auto-generated method stub
		
	}

	public void visitar(Enemigo enemigo) {
		int puntosPorMatar = enemigo.puntosQueDa();
		enemigo.morir();
		jugador.actualizarPuntaje(puntosPorMatar);
		this.morir();
	}

	
	@Override
	public void aceptarVisita(Visitor visitor) {
		System.out.println("entra a aceptar visita de bola de fuego");
		visitor.visitar(this);
	}

}
