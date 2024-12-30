package elementos;

import archivos.Sprite;
import colisiones.Visitor;
import parseo.GameFactory;
import states.State;

public abstract class PowerUp extends Elemento implements Visitor{
	
	protected int velocidad = 2;
	protected State estadoMario;
	protected GameFactory fabrica;
	protected Sprite activado;
	
	public PowerUp(int x, int y, Sprite imagen) {
		super(x, y, imagen);
	}

	public void activarImagen() {
		this.getSprite().setSprite(activado.getRutaImagen());
		this.observer.actualizar();
	}
	
	@Override
	public void visitar(Enemigo enemigo) {
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
}
