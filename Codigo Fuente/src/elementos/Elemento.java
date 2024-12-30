package elementos;

import java.awt.Rectangle;
import archivos.Sprite;
import juego.ColisionesNivel;
import observers.Observer;

public abstract class Elemento implements ElementoLogico{

	protected Rectangle hitbox;
	protected Sprite imagen;
	protected Observer observer;
	//protected int posicionX,posicionY;
	protected int alto, ancho;
	protected ColisionesNivel colisiones;
	protected boolean estoyMuerto;

	public Elemento(int x, int y, Sprite imagen) {
		//posicionX = x;
		//posicionY = y;
		hitbox = new Rectangle ();
		hitbox.setLocation(x,y);
		//hitbox.setLocation(posicionX,posicionY);
		this.imagen = imagen;
		estoyMuerto = false;
		alto = 0;
		ancho = 0;
	}

	public Sprite getSprite() {
		return imagen;
	}

	public int getPosX() {
		return (int) this.hitbox.getX();
	}

	public int getPosY() {
		return (int) this.hitbox.getY();
	}
	
	public int getAlto() {
		return alto;
	}
	
	public int getAncho() {
		return ancho;
	}
	
	public void setPosX(int posX) {
		this.hitbox.setLocation(posX, this.hitbox.y);
	}

	public void setPosY( int posY) {
		this.hitbox.setLocation(this.hitbox.x, posY);
	}
	
	public void setAlto( int alto) {
		this.alto = alto;
	}
	
	public void setAncho( int ancho) {
		this.ancho = ancho;
	}
	
	public void setHitbox(int width, int height) {
		hitbox.setSize(width, height);
	}
	
	public void setColisiones(ColisionesNivel controladorColisiones) {
		this.colisiones = controladorColisiones;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public boolean estaMuerto() {
		return estoyMuerto;
	}

	public void registrarObserver(Observer observer) {
		this.observer = observer;
	}

	protected void notificar() {
		this.observer.actualizar();

	}
	
	public void morir() {
		this.estoyMuerto = true;
		this.observer.eliminar();
	}
	
	protected boolean chocaArriba(Elemento elem) {
		return ((elem.getPosY() > this.getPosY()));
		
		//return hitbox.getMaxY()==this.getHitbox().getMaxY();
	}

	protected boolean chocaDerecha(Elemento elem) {
		return (elem.getPosX() >= this.getPosX());
		//return hitbox.getMaxX()==this.getHitbox().getMaxX();
	}

	protected boolean chocaIzquierda(Elemento elem) {
		return (elem.getPosX() < this.getPosX());
		//return hitbox.getMinX()==this.getHitbox().getMinX();
	}

	protected boolean chocaAbajo(Elemento elem) {
		return ((elem.getPosY() < this.getPosY()));
		//return hitbox.getMinY()==this.getHitbox().getMinY();
	}
	protected int calcularAlturaInterseccion(Elemento elem) {
		int  techoElemento1 = elem.getPosY();
	    int pisoElemento1 = elem.getPosY() - elem.getAlto();

	    int techoElemento2 = this.getPosY();
	    int pisoElemento2 = this.getPosY() - this.getAlto();

	    if (elem.getPosY()>= this.getPosY()) {
	        return techoElemento2 - pisoElemento1;
	    } else {
	        return techoElemento1 - pisoElemento2;
	    }
	}
	protected int calcularAnchoInterseccion(Elemento elem) {
		int extrIzqElemento1 = elem.getPosX();
	    int extrDerElemento1 = elem.getPosX() + elem.getAncho();

	    int extrIzqElemento2 = this.getPosX();
	    int extrDerElemento2 = this.getPosX() + this.getAncho();

	    if (elem.getPosX() < this.getPosX()) {
	        return extrDerElemento1 - extrIzqElemento2;
	    } else {
	        return extrDerElemento2 - extrIzqElemento1;
	    }
	}
}
