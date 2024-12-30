package vista;


import javax.swing.JFrame;
import javax.swing.Timer;

import elementos.ElementoJugador;
import elementos.ElementoLogico;
import juego.ControladorPartida;
import parseo.GameFactory;
import observers.Observer;
import observers.ObserverJugador;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class ControladorPantallas implements  ControladorEntreJuegoVista{

	protected JFrame ventana;
	protected PantallaInicio panelInicio;
	protected PantallaSeleccionModo panelSeleccion;
	protected PantallaRanking panelRanking;
	protected PantallaJuego panelJuego;
	protected PantallaGameOver panelGameOver;
	protected PantallaTimeUp panelTimeUp;
	protected PantallaVictoria panelVictoria;
	protected ControladorPartida partida;
	protected GameFactory fabrica;


	public ControladorPantallas(ControladorPartida controladorPartida) {
		this.partida = controladorPartida;
		ConfigurarFuente.cargarFuente();
		panelInicio = new PantallaInicio(this, controladorPartida);
		panelSeleccion = new PantallaSeleccionModo(this);
		panelRanking = new PantallaRanking(this, partida.getRanking());
		panelJuego = new PantallaJuego(this);
		panelGameOver = new PantallaGameOver(this);
		panelTimeUp = new PantallaTimeUp(this);
		panelVictoria = new PantallaVictoria(this);

		configurarVentana();
		registrarOyenteVentana();
	}

	private void configurarVentana() {
		ventana = new JFrame("Super Mario Bros");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setResizable(false);
		ventana.setSize(ConstantesPantalla.ventanaAncho, ConstantesPantalla.ventanaAlto);
		ventana.setVisible(true);
	}

	public ControladorPartida getControladorPartida() {
		return this.partida;
	}

	private void refrescar() {
		ventana.revalidate();
		ventana.repaint();
	}

	public void mostrarPantallaInicial() {
		ventana.setContentPane(panelInicio);
		refrescar();
	}

	@Override
	public void mostrarPantallaJuego() {
		this.panelJuego = new PantallaJuego(this);
		ventana.setContentPane(panelJuego);
		accionarInicioJuego(fabrica);
		panelJuego.iniciarTimer();
		refrescar();
	}

	public void mostrarPantallaRanking() {
		panelRanking.actualizarRanking();
		ventana.setContentPane(panelRanking);
		refrescar();
	}

	public void mostrarPantallaGameOver() {
		ventana.setContentPane(panelGameOver);
		panelGameOver.iniciarTemporizador();
		refrescar();
	}

	public void mostrarPantallaTimeUp() {
		ventana.setContentPane(panelTimeUp);
		panelTimeUp.iniciarTemporizador();
		refrescar();
	}

	@Override
	public void mostrarPantallaSeleccion() {
		ventana.setContentPane(panelSeleccion);
		refrescar();
	}

	public void mostrarPantallaVictoria() {
		ventana.setContentPane(panelVictoria);
		panelVictoria.iniciarTemporizador();
		refrescar();
	}

	public void registrarOyenteVentana(){
		ventana.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evento){
				try {
					FileOutputStream fileOutputStream = new FileOutputStream("./puntajes.tdp");
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
					objectOutputStream.writeObject(partida.getRanking());
					objectOutputStream.flush();
					objectOutputStream.close();
				}
				catch(IOException  e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public Observer registrarElemento(ElementoLogico elem) {
		Observer observerElemento = this.panelJuego.incorporarElemento(elem);
		refrescar();
		return observerElemento;
	}

	@Override
	public Observer registrarElemento(ElementoJugador jugador) {
		ObserverJugador observerJugador = this.panelJuego.incorporarElementoJugador(jugador);
		refrescar();
		return observerJugador;
	}

	public void accionarInicioJuego(GameFactory fabrica) {
		this.partida.iniciarPartida(fabrica, 1);
		panelJuego.actualizarLabelsNivel();
	}

	public void reiniciarNivel() {
		this.panelJuego = new PantallaJuego(this);
		ventana.setContentPane(panelJuego);
		panelJuego.actualizarLabelsNivel();
		panelJuego.iniciarTimer();
		refrescar();
	}

	public Timer getTimerNivel() {
		return panelJuego.timerNivel;
	}

	public void setFabrica(GameFactory factory) {
		this.fabrica = factory;
	}

	public int getTiempoRestante() {
		return panelJuego.tiempoRestante;
	}

	public void pararSonidoPartida() {
            partida.pararSonido();
    }
	
	public int getNumeroNivel() {
		return partida.getNumNivel();
	}
	public void activarMovement (KeyEvent e) {
		partida.activeMovement(e);
	}
	public void desactivarMovement (KeyEvent e) {
		partida.desactiveMovement(e);
	}
}
