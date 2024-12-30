package juego;

import java.awt.Rectangle;

import archivos.ControladorSonidos;
import archivos.Sprite;
import archivos.TipoSonidos;
import colisiones.Visitable;
import colisiones.Visitor;
import elementos.Elemento;
import elementos.ElementoJugador;
import elementos.Enemigo;
import elementos.Plataforma;
import observers.AdaptadorPosicionPixel;
import parseo.ConstantesHitbox;
import states.*;

public class Jugador extends Elemento implements Visitable, ElementoJugador{

	protected State estado;
	protected InfoJugador info;
	protected int velX, velY;
	protected boolean isJumped;
	protected ColisionesNivel nivel;
	protected boolean arribaDeBloque;
	protected boolean mirandoDerecha;
	protected Plataforma ultimoBloque;
	protected String nombre;


	//agregar si esta en un bloque o no, para el salto al caer de plataforma

	public Jugador(int x, int y, Sprite imagen) {
		super(x, y, imagen);
		velX = 0;
		velY = 0;
		isJumped = false;
		info = new InfoJugador(this);
		arribaDeBloque = false;
		ultimoBloque = null;
		mirandoDerecha = true;

		inicializarEstados();
	}

	private void inicializarEstados() {
		Normal normal = new Normal(this);
		SuperMario superMario = new SuperMario(this);
		Fuego fuego = new Fuego(this);
		Invulnerable invulnerable = new Invulnerable(this);

		normal.setFuego(fuego);
		normal.setSuperMario(superMario);
		normal.setInvulnerable(invulnerable);

		fuego.setNormal(normal);
		fuego.setSuperMario(superMario);
		fuego.setInvulnerable(invulnerable);

		superMario.setNormal(normal);
		superMario.setFuego(fuego);
		superMario.setInvulnerable(invulnerable);

		invulnerable.setNormal(normal);
		invulnerable.setSuperMario(superMario);
		invulnerable.setFuego(fuego);

		estado = normal;
	}

	public void moverDerecha() {
		velX = 5;
		this.getState().moverDerecha();
	}

	public void moverIzquierda() {
		velX = -5;
		this.getState().moverIzquierda();
	}

	public void frenarMovimiento() {
		velX = 0;
	}

	public void actualizar() { 
		int alturaPiso = 72;
		int alturaMario = (int) (alturaPiso + this.getAlto());

		int limiteDerecho =  AdaptadorPosicionPixel.transformarX(7471);
		int limiteY_ventana = 0;

		this.setPosX(this.getPosX() + velX);
		this.setPosY(this.getPosY() + velY);

		estaEnLaHitboxDelBloque();

		if(arribaDeBloque) {
			if(ultimoBloque != null) {
				int alturaMarioBloque = ultimoBloque.getPosY()  + this.getAlto();
				if(this.getPosY() > alturaMarioBloque || this.getPosY() < alturaMarioBloque) {
					velY -= 1;
				} else {
					velY = 0;
					isJumped = false;
				}
			} else {
				if(this.getPosY() > alturaMario || this.getPosY() < alturaMario) {
					velY -= 1;
				} else {
					velX = 0;
					isJumped = false;
				}
			}
		}else {
			velY -= 1;
		}

		if(this.getPosX() < 0) {
			this.setPosX(0);
		} else if (this.getPosX() > limiteDerecho) {
			this.setPosX(limiteDerecho);
		}
		if (this.getPosY() < limiteY_ventana) {
			this.getInfo().restarVida();
		}

		estado.actualizarSprite();
		notificar();
	}

	public void saltar() {
		if (arribaDeBloque) { 
			velY = 19; 
			isJumped = true;
			estado.reproducirSonidoSalto();
		}	
	}

	public void saltarAlMatar() {
		if (!isJumped) { 
			velY = 10; 
			isJumped = true;
			ControladorSonidos.getInstance().reproducirSonidoAccion(TipoSonidos.muerteEnemigo);
		}	
	}

	//Get
	public State getState() {
		return this.estado;
	}

	public InfoJugador getInfo() {
		return this.info;
	}

	public int getMonedas() {
		return this.info.getMonedas();
	}

	public int getPuntaje() {
		return this.info.getPuntaje();
	}

	public int getVida() {
		return this.info.getVida();
	}

	public int getVelocidadX() {
		return this.velX;
	}

	public String getName() {
		return nombre;
	}

	//Set
	public void setState(State estado) {
		this.estado = estado;
	}

	public void setColisiones(ColisionesNivel nivel) {
		this.nivel = nivel;
		this.info.setColisiones(nivel);
	}

	public void setVelX(int direc) {
		this.velX = direc;
	}

	public void setVelY(int direc) {
		this.velY = direc;
	}

	public void setJumped(boolean valor) {
		this.isJumped = valor;
	}

	public void setName(String nombre) {
		this.nombre=nombre;
	}

	public boolean estaArribaDeBloque() {
		return arribaDeBloque;
	}

	public void ultimoBloqueColision(Plataforma l) {
		ultimoBloque = l;
	}

	public void estaEnLaHitboxDelBloque(){
		if(ultimoBloque != null) {
			if (this.getHitbox().intersects(ultimoBloque.getHitbox())) {//para mario chico
				arribaDeBloque = true;
			}else 
				arribaDeBloque = false;

			if(this.getAlto() == ConstantesHitbox.altoJugadorGrande) { //para mario grande
				Rectangle hitboxCopia = new Rectangle(this.getPosX(),  this.getPosY() - ConstantesHitbox.altoJugador, this.ancho, this.alto);
				if(hitboxCopia.intersects(ultimoBloque.getHitbox())) {
					arribaDeBloque = true;
				}else
					arribaDeBloque = false;
			}
		}
	}

	public void pararSonidoPartida() {
		info.getColisiones().pararSonidoPartida();

	}

	public int tiempoRestante() {
		return info.tiempoRestante();
	}

	public void setImagen(Sprite nuevaImagen) {
		this.imagen = nuevaImagen; // Cambia el atributo `imagen` al nuevo sprite 
	}

	public boolean isJumped() {
		return isJumped;
	}

	public boolean estaMirandoDerecha() {
		return mirandoDerecha;
	}

	public void setMirandoDerecha(boolean mirandoDerecha) {
		this.mirandoDerecha = mirandoDerecha;
	}

	public void actualizarPuntaje(int puntaje) {
		this.info.actualizarPuntaje(puntaje);
	}

	public void recibirDaño(Enemigo e) {
		this.estado.recibirDaño(e);
	}

	public void sumarVida() {
		this.info.sumarVida();
	}

	public void aumentarMoneda() {
		this.info.aumentarMoneda();
	}

	public void recibirSuperChampiñon() {
		this.estado.recibirSuperChampiñon();
	}

	public void recibirFlorDeFuego() {
		this.estado.recibirFlorDeFuego();
	}

	public void recibirEstrella() {
		this.estado.recibirEstrella();
	}

	public void disparar() {
		this.estado.disparar();
	}

	@Override
	public void aceptarVisita(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visitar(this);
	}
	public boolean esColisionDeIzquierdaConPlataforma(Elemento elem) {
		int extrDerJugador = getPosX() + getAncho();
		int extrDerElemento = elem.getPosX() + elem.getAncho();
		int extrIzqElemento = elem.getPosX();
		
		return (extrDerJugador >= extrIzqElemento) && (extrDerJugador < extrDerElemento);
	}
	public boolean esColisionDeArribaConPlataforma(Elemento elem) {
		int pisoJugador = getPosY() - getAlto();
		int pisoElemento = elem.getPosY() - elem.getAlto();
		
		return (pisoJugador >= pisoElemento);
	}
}

