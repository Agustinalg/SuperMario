package parseo;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import plataformas.*;
import enemigos.*;
import powerUps.*;
import juego.*;
import observers.AdaptadorPosicionPixel;

public class NivelBuilder {
	protected GameFactory fabrica;
	protected ColisionesNivel nivelCreado;
	protected int numNivel;
	protected BufferedImage mapa;

	public NivelBuilder(GameFactory factory, int nivelACrear) {
		this.fabrica = factory;
		this.numNivel = nivelACrear;
		this.nivelCreado = new ColisionesNivel(); 
		

		try {
			switch (nivelACrear) {
			case 1:
				mapa = ImageIO.read(getClass().getResourceAsStream("/imagenes/mapaRGB1Terminado.png")); 
				break;

			case 2:
				mapa = ImageIO.read(getClass().getResourceAsStream("/imagenes/mapaRGB2Terminado.png"));
				break;
			case 3:
				mapa = ImageIO.read(getClass().getResourceAsStream("/imagenes/mapaRGB3Terminado.png"));
				break;
			default:

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		crearNivel();	
		fabrica.setColisiones(nivelCreado);
	}

	private void crearNivel() {
		int ancho = mapa.getWidth();
		int alto = mapa.getHeight();

		int jugador = new Color(255, 0, 0).getRGB();

		int castillo = new Color(140,150,145).getRGB();
		int bloqueTransparente = new Color(30, 110, 110).getRGB();
		int bloqueSolido = new Color(30,110,70).getRGB();
		int ladrilloSolido = new Color(0, 0, 255).getRGB();
		int vacio = new Color(110,60,0).getRGB();
		int preguntaMoneda = new Color(0, 255, 0).getRGB();
		int preguntaSuperChampi = new Color(255, 100, 0).getRGB();
		int preguntaChampiVerde = new Color(0, 100, 0).getRGB();
		int preguntaFlorDeFuego = new Color(0, 255, 255).getRGB();
		int preguntaEstrella = new Color(115, 0, 255).getRGB();
		int tuberiaSinPiranha = new Color(255,255,0).getRGB();
		int tuberiaConPiranha = new Color(250, 50, 100).getRGB();	

		int goomba = new Color(255, 0, 255).getRGB();
		int koopa = new Color(255, 255, 255).getRGB();
		int lakitu = new Color(60, 255, 150).getRGB();
		int buzzy = new Color(255, 125, 125).getRGB();
		int spiny = new Color(255,200,125).getRGB();

		int moneda = new Color(255, 180, 0).getRGB();
		int superChamp = new Color(24, 16, 112).getRGB();
		int florFuego = new Color(16, 112, 104).getRGB();
		int estrella = new Color(115, 0, 60).getRGB();
		int champVerde = new Color(25, 135, 50).getRGB();

		double multiplicadorPixel = 36.8; 
		for (int x = 0; x < ancho; x++) {
			for (int y = 0; y < alto; y++) {

				int colorPixelActual = mapa.getRGB(x, y);

				int xLocation = AdaptadorPosicionPixel.transformarX((int) (x*multiplicadorPixel));
				int yLocation = AdaptadorPosicionPixel.transformarY((int) (y*multiplicadorPixel));

				if (colorPixelActual == jugador) {
					Jugador jugadorCreado = fabrica.crearJugador(xLocation, yLocation);
					nivelCreado.agregarJugador(jugadorCreado);
					jugadorCreado.setColisiones(nivelCreado);

				}
				else if (colorPixelActual == ladrilloSolido) {
					LadrilloSolido ladrilloCreado = fabrica.crearLadrilloSolido(xLocation,yLocation);
					nivelCreado.agregarPlataforma(ladrilloCreado);
					ladrilloCreado.setColisiones(nivelCreado);

				}
				else if (colorPixelActual == goomba) {
					Goomba goombaCreado = fabrica.crearGoomba(xLocation, yLocation);
					nivelCreado.agregarEnemigo(goombaCreado);
					goombaCreado.setColisiones(nivelCreado);

				}
				else if (colorPixelActual == preguntaMoneda) {
					BloqueDePregunta bloqueCreado = fabrica.crearBloqueDePregunta(xLocation, yLocation);
					Moneda monedaCreada = fabrica.crearMoneda(xLocation +5, yLocation + ConstantesHitbox.altoBloque);
					
					bloqueCreado.setPowerUp(monedaCreada);
					nivelCreado.agregarPlataforma(bloqueCreado);
					bloqueCreado.setColisiones(nivelCreado);

				}
				else if (colorPixelActual == preguntaSuperChampi) {
					BloqueDePregunta bloqueCreado = fabrica.crearBloqueDePregunta(xLocation, yLocation);
					SuperChampiñon champCreado = fabrica.crearSuperChampiñon(xLocation, yLocation + ConstantesHitbox.altoBloque);
					
					bloqueCreado.setPowerUp(champCreado);
					nivelCreado.agregarPlataforma(bloqueCreado);
					bloqueCreado.setColisiones(nivelCreado);

				}
				else if (colorPixelActual == preguntaChampiVerde) {
					BloqueDePregunta bloqueCreado = fabrica.crearBloqueDePregunta(xLocation, yLocation);
					ChampiñonVerde champVerdeCreado = fabrica.crearChampiñonVerde(xLocation, yLocation + ConstantesHitbox.altoBloque);
					
					bloqueCreado.setPowerUp(champVerdeCreado);
					nivelCreado.agregarPlataforma(bloqueCreado);
					bloqueCreado.setColisiones(nivelCreado);

				}	                
				else if (colorPixelActual == vacio) {
					Vacio vacioCreado = fabrica.crearVacio(xLocation, yLocation + 3);
					nivelCreado.agregarPlataforma(vacioCreado);
					vacioCreado.setColisiones(nivelCreado);
					
				}
				else if (colorPixelActual == preguntaFlorDeFuego) {
					BloqueDePregunta bloqueCreado = fabrica.crearBloqueDePregunta(xLocation, yLocation);
					FlorDeFuego florFuegoCreada = fabrica.crearFlorDeFuego(xLocation, yLocation + ConstantesHitbox.altoBloque);
					
					bloqueCreado.setPowerUp(florFuegoCreada);
					nivelCreado.agregarPlataforma(bloqueCreado);
					bloqueCreado.setColisiones(nivelCreado);
					
				}
				else if(colorPixelActual == preguntaEstrella){
					BloqueDePregunta bloqueCreado = fabrica.crearBloqueDePregunta(xLocation, yLocation);
					Estrella estrellaCreada = fabrica.crearEstrella(xLocation, yLocation + ConstantesHitbox.altoBloque);
					
					bloqueCreado.setPowerUp(estrellaCreada);
					nivelCreado.agregarPlataforma(bloqueCreado);
					bloqueCreado.setColisiones(nivelCreado);
					
				}
				else if(colorPixelActual == koopa) {
					Koopa koopaCreado = fabrica.crearKoopa(xLocation, yLocation);
					nivelCreado.agregarEnemigo(koopaCreado);
					koopaCreado.setColisiones(nivelCreado);
					
				}
				else if(colorPixelActual == bloqueSolido) {
					BloqueSolido bloqueSolidoCreado = fabrica.crearBloqueSolido(xLocation, yLocation);
					nivelCreado.agregarPlataforma(bloqueSolidoCreado);
					bloqueSolidoCreado.setColisiones(nivelCreado);

				}
				else if(colorPixelActual == bloqueTransparente) {
					BloqueSolido bloqueCreado = fabrica.crearBloqueTransparente(xLocation, yLocation);
					nivelCreado.agregarPlataforma(bloqueCreado);
					bloqueCreado.setColisiones(nivelCreado);
					
				}
				else if(colorPixelActual == lakitu) {
					Lakitu lakituCreado = fabrica.crearLakitu(xLocation, yLocation);
					nivelCreado.agregarEnemigo(lakituCreado);
					lakituCreado.setColisiones(nivelCreado);
					
				}
				else if(colorPixelActual == buzzy) {
					Buzzy buzzyCreado = fabrica.crearBuzzy(xLocation, yLocation);
					nivelCreado.agregarEnemigo(buzzyCreado);
					buzzyCreado.setColisiones(nivelCreado);
					
				}
				else if(colorPixelActual == spiny) {
					Spiny spinyCreado = fabrica.crearSpiny(xLocation, yLocation);
					nivelCreado.agregarEnemigo(spinyCreado);
					spinyCreado.setColisiones(nivelCreado);
					
				}
				else if(colorPixelActual == moneda) {
					Moneda monedaCreada = fabrica.crearMonedaSola(xLocation, yLocation);
					nivelCreado.agregarPowerUp(monedaCreada);
					monedaCreada.setColisiones(nivelCreado);

				}
				else if(colorPixelActual == superChamp) {
					SuperChampiñon superChampCreado = fabrica.crearSuperChampiñon(xLocation, yLocation);
					nivelCreado.agregarPowerUp(superChampCreado);
					superChampCreado.setColisiones(nivelCreado);

				}
				else if(colorPixelActual == florFuego) {
					FlorDeFuego florFuegoCreada = fabrica.crearFlorDeFuego(xLocation, yLocation);
					nivelCreado.agregarPowerUp(florFuegoCreada);
					florFuegoCreada.setColisiones(nivelCreado);

				}
				else if(colorPixelActual == estrella) {
					Estrella estrellaCreada = fabrica.crearEstrella(xLocation, yLocation);
					nivelCreado.agregarPowerUp(estrellaCreada);
					estrellaCreada.setColisiones(nivelCreado);

				}
				else if(colorPixelActual == champVerde) {
					ChampiñonVerde champiñonVerdeCreado = fabrica.crearChampiñonVerde(xLocation, yLocation);
					nivelCreado.agregarPowerUp(champiñonVerdeCreado);
					champiñonVerdeCreado.setColisiones(nivelCreado);

				}
				else if (colorPixelActual == tuberiaConPiranha) {				
					Tuberia tuberiaCreada = fabrica.crearTuberia(xLocation, yLocation);  
					int posicionPiranha = (int) (xLocation + tuberiaCreada.getHitbox().getWidth()/3);
					Piranha piranha = fabrica.crearPiranha(posicionPiranha, yLocation);
					
					this.nivelCreado.agregarEnemigo(piranha);
					piranha.setColisiones(nivelCreado);
					
					nivelCreado.agregarPlataforma(tuberiaCreada);
					tuberiaCreada.setColisiones(nivelCreado);

				}	
				else if (colorPixelActual == tuberiaSinPiranha) {
					Tuberia tuberiaCreada = fabrica.crearTuberia(xLocation, yLocation);      
					nivelCreado.agregarPlataforma(tuberiaCreada);
					tuberiaCreada.setColisiones(nivelCreado);
					
				}
				else if(colorPixelActual == castillo) {
					Castillo castilloCreado = fabrica.crearCastillo(xLocation, yLocation);
					nivelCreado.agregarPlataforma(castilloCreado);
					castilloCreado.setColisiones(nivelCreado);

				}  
			}
		}		
	}

	public ColisionesNivel getNivel() {
		return this.nivelCreado;
	}
}
