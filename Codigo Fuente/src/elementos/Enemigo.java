package elementos;

import archivos.Sprite;
import colisiones.Visitable;
import colisiones.Visitor;
import juego.Jugador;
import observers.AdaptadorPosicionPixel;
import observers.Observer;

public abstract class Enemigo extends Elemento implements Visitor, Visitable{

	protected Observer observer;
	protected int velX, velY;
	protected boolean aIzquierda;
	protected boolean colisionConBloque;


	public Enemigo(int x, int y, Sprite imagen) {
		super(x, y, imagen);
		velX = 0;
		velY = 0;
		aIzquierda = true;
	}

	public abstract int puntosQueResta();
	public abstract int puntosQueDa();

	public void moverIzquierda() {
		aIzquierda = true;
	}

	public void moverDerecha() {
		aIzquierda = false;
	}

	public void actualizar() {
	    moverEnDireccion(); 
	    actualizarPosicion(); 
	    verificarLimites();
	   // actualizarPosicionHitbox();
	    notificar();
	}

	private void actualizarPosicion() {
		this.setPosX(this.getPosX() + velX);
		this.setPosY(this.getPosY() + velY);
		
	    int alturaPiso = 72;
	    int alturaEnemigo = (int) (alturaPiso + this.getAlto());
	    
	    if (!colisionConBloque) {
	        velY -= 1;
	    } else {
	        if (this.getPosY() > alturaEnemigo) {
	            velY -= 1;
	        } else {
	        	this.setPosY(alturaEnemigo);
	            velY = 0;
	        }
	    }
	}

	private void verificarLimites() {
	    int limiteDerecho = AdaptadorPosicionPixel.transformarX(7471);
	    int limiteY_ventana = 0;

	    if (this.getPosX() < 0) {
	    	this.setPosX(0);
	    } else if (this.getPosX() > limiteDerecho) {
	    	this.setPosX(limiteDerecho);
	    }
	    if (this.getPosY() < limiteY_ventana) {
	        morir();
	    }
	}

	protected void moverEnDireccion() {
	    if (aIzquierda) {
	        velX = -2;
	    } else {
	        velX = 2;
	    }
	}
	
	public void declararMedidas() {
		
	}

	public void ColisionaConBloque(boolean valor) {
		colisionConBloque = valor;
	}

	public void setVelX(int direc) {
		this.velX = direc;
	}

	public void setVelY(int direc) {
		this.velY = direc;
	}

	protected void chocar(Jugador jugador) {
		jugador.setPosY((int) (this.getPosY() + jugador.getAlto()));
		jugador.setVelY(0);
		jugador.setJumped(false);
		jugador.saltarAlMatar();
	}
	/*
	public boolean elementoColisionaArriba(Elemento elem) {
		int pisoEnemigo = getPosY() - getAlto();
		int techoEnemigo = getPosY();
		int pisoElemento = elem.getPosY() - elem.getAlto();
		
		return ((pisoElemento >= pisoEnemigo) && (pisoElemento <= techoEnemigo));
	} */


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
	@Override
	public void aceptarVisita(Visitor visitor) {
		
		visitor.visitar(this);
	}
}
