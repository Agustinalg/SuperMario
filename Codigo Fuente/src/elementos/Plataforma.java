package elementos;

import archivos.Sprite;
import colisiones.Visitor;
import juego.Jugador;

public abstract class Plataforma extends Elemento implements Visitor{


	public Plataforma (int x, int y, Sprite imagen) {
		super (x,y,imagen);
	}
	
	protected void ubicarArriba(Jugador jugador) {
		jugador.setPosY((int) (this.getPosY() + jugador.getAlto()));
		jugador.setVelY(0);
		jugador.setJumped(false);
	}
	
	@Override
	public void visitar(PowerUp power) {
		//Vacio
	}

	@Override
	public void visitar(BolaDeFuego bola) {		
		int alto = calcularAlturaInterseccion(bola);
		int ancho = calcularAnchoInterseccion(bola);
		
		boolean colisionDeLado = alto >= ancho;

		if(colisionDeLado) {
				bola.morir();
		} 
	}
}
