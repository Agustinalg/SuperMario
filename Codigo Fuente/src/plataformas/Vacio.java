package plataformas;

import archivos.Sprite;
import elementos.Enemigo;
import elementos.Plataforma;
import juego.Jugador;

public class Vacio extends Plataforma { 

	public Vacio(int x, int y, Sprite imagen) {
		super(x, y, imagen);
	}

	public void visitar (Jugador jugador) {
		jugador.setVelY(-10);
		jugador.actualizarPuntaje(-15);			
	}

	@Override
	public void visitar(Enemigo enemigo) {
		enemigo.ColisionaConBloque(false);
		enemigo.setVelY(-10);	
	}	
}
