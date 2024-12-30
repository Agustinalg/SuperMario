package enemigos;

import archivos.Sprite;
import colisiones.Visitor;
import elementos.Enemigo;
import juego.Jugador;
import parseo.ConstantesHitbox;

public class Piranha extends Enemigo{
	private int minAltura, maxAltura;
	protected boolean subiendo;
	private int contadorEspera = 0; 
	private static final int TIEMPO_ESPERA = 60; 

	public Piranha(int x, int y, Sprite imagen) {
		super(x, y, imagen);
		minAltura = y - 3;
		subiendo = true;
		maxAltura = (int) (y + (ConstantesHitbox.altoPiranha));
	}

	public void subir() {
		velY = 1;
	}

	public void bajar() {
		velY = -1;
	}

	public void actualizar() {
		if (contadorEspera > 0) {
			contadorEspera--;
			return; 
		}
		chequeoSubiendo();
		gestionMovimiento();
		notificar();
	}

	private void gestionMovimiento() {
		if (subiendo) {
			subir();
		} else  {
			bajar();
		}
		this.setPosY(this.getPosY() + velY);
	}

	private void chequeoSubiendo() {
		if(this.getPosY() >= maxAltura) {
			subiendo = false;
			contadorEspera = TIEMPO_ESPERA;
		} else if (this.getPosY() <= minAltura) {
			subiendo = true;
			contadorEspera = TIEMPO_ESPERA;
		}
	}

	public void visitar(Jugador jugador) {
		jugadorRecibeDaño(jugador);
	}

	private void jugadorRecibeDaño(Jugador jugador) {
		jugador.recibirDaño(this);
	}

	public int recibirDaño() {
		this.morir();
		return this.puntosQueDa();
	}

	public int puntosQueResta() {
		return 30;
	}

	public int puntosQueDa() {
		return 30;
	}

	@Override
	public void aceptarVisita(Visitor visitor) {
		// Vacio.
	}
}
