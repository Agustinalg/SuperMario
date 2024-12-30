package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import archivos.ControladorSonidos;
import archivos.TipoSonidos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import elementos.ElementoJugador;
import elementos.ElementoLogico;
import observers.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PantallaJuego extends JPanel implements KeyListener{


	private static final long serialVersionUID = 1L;
	protected JPanel panelJuego;
	protected JPanel panelInformacion;
	protected JLabel imagenFondoJuego;
	protected JLabel imagenFondoInfo; 
	protected JScrollPane panelScroll;
	protected JLabel labelPuntaje;
	protected JLabel labelMonedas;
	protected JLabel labelTiempo;
	protected JLabel labelVidas;
	protected JLabel labelNivelActual;
	protected ControladorPantallas controladorPantalla;
	protected Timer timerNivel;
	protected int tiempoRestante=400;


	public PantallaJuego(ControladorPantallas controladorPantalla) {
		this.controladorPantalla = controladorPantalla;
		this.setPreferredSize(new Dimension(ConstantesPantalla.panelAncho, ConstantesPantalla.panelAlto));
		this.setLayout(new BorderLayout());
		agregarPanelInformacion();
		agregarPanelJuego();
		this.setFocusable(true);
		this.addKeyListener(this);
		this.requestFocusInWindow();
	}

	@Override
	public void addNotify() {
		super.addNotify();
		requestFocusInWindow(); // Asegura que el panel recibe el foco cuando es mostrado.
	}

	private void agregarPanelJuego() {
		imagenFondoJuego = new JLabel();
		imagenFondoJuego.setLayout(null);
		imagenFondoJuego.setBounds(0, 0, ConstantesPantalla.panelAncho, ConstantesPantalla.panelJuegoAlto);

		panelJuego = new JPanel(null);
		panelJuego.setPreferredSize(new Dimension(ConstantesPantalla.panelAncho, ConstantesPantalla.panelJuegoAlto));

		agregarImagenFondo();
		panelJuego.add(imagenFondoJuego);

		panelJuego.setPreferredSize(new Dimension(imagenFondoJuego.getWidth(), imagenFondoJuego.getHeight()));

		panelScroll = new JScrollPane(panelJuego);
		panelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		panelScroll.setBounds(0, 0, ConstantesPantalla.panelAncho, ConstantesPantalla.panelJuegoAlto);
		panelScroll.setBorder(null);

		this.add(panelScroll, BorderLayout.CENTER);
	}

	private void agregarImagenFondo() {
		imagenFondoJuego = new JLabel();
		ImageIcon iconoImagen = new ImageIcon(this.getClass().getResource("/imagenes/background.png"));

		Image imagenOriginal = iconoImagen.getImage();
		int anchoOriginal = imagenOriginal.getWidth(null);
		int altoOriginal = imagenOriginal.getHeight(null);
		int nuevoAlto = ConstantesPantalla.panelJuegoAlto;
		int nuevoAncho = (int) ((anchoOriginal / (double) altoOriginal) * nuevoAlto);

		Image imagenEscalada = iconoImagen.getImage().getScaledInstance(nuevoAncho, ConstantesPantalla.panelJuegoAlto,  Image.SCALE_SMOOTH);
		Icon iconoImagenEscalado = new ImageIcon(imagenEscalada);
		imagenFondoJuego.setIcon(iconoImagenEscalado);
		imagenFondoJuego.setBounds(0, 0, nuevoAncho, nuevoAlto);		
	}

	private void agregarPanelInformacion() {
		panelInformacion = new JPanel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = -3722514639939007983L;
			private Image imagenFondo;
			{
				ImageIcon iconoImagen = new ImageIcon(this.getClass().getResource("/imagenes/modoUno/vacio.png")); 
				imagenFondo = iconoImagen.getImage();
			}

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (imagenFondo != null) {
					g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
				}
			}
		};


		panelInformacion.setLayout(null);
		panelInformacion.setBorder(null);
		panelInformacion.setPreferredSize(new Dimension(ConstantesPantalla.panelAncho, ConstantesPantalla.panelInformacionAlto));
		panelInformacion.setOpaque(false);

		agregarLabelsInfo();
		this.add(panelInformacion, BorderLayout.NORTH);		
	}

	private void agregarLabelsInfo() {
		JLabel labelTituloPuntaje = new JLabel("SCORE");
		labelPuntaje = new JLabel("000000");

		JLabel labelTituloMonedas = new JLabel("COINS");
		labelMonedas = new JLabel("00");

		JLabel labelTituloTiempo = new JLabel("TIME");
		labelTiempo = new JLabel("400");

		JLabel labelTituloVidas = new JLabel("LIVES");
		labelVidas = new JLabel("3");

		JLabel labelTituloNivel = new JLabel("LEVEL");
		labelNivelActual = new JLabel("1");

		// Decorar etiquetas
		decorarLabelsInfo(labelTituloPuntaje, labelPuntaje);
		decorarLabelsInfo(labelTituloMonedas, labelMonedas);
		decorarLabelsInfo(labelTituloTiempo, labelTiempo);
		decorarLabelsInfo(labelTituloVidas, labelVidas);
		decorarLabelsInfo(labelTituloNivel, labelNivelActual);

		// Colocar etiquetas en el panel
		colocarLabelsInfo(labelTituloPuntaje, labelPuntaje, 5);
		colocarLabelsInfo(labelTituloMonedas, labelMonedas, 180);
		colocarLabelsInfo(labelTituloTiempo, labelTiempo, 335);
		colocarLabelsInfo(labelTituloVidas, labelVidas, 495);
		colocarLabelsInfo(labelTituloNivel, labelNivelActual, 655);

		panelInformacion.revalidate();
		panelInformacion.repaint();			
	}

	private void decorarLabelsInfo(JLabel labelTitulo, JLabel labelValor) {
		Font marioFont = null;
		try {
			InputStream is = getClass().getResourceAsStream("/archivos/mario-font.ttf");
			marioFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(20f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			marioFont = new Font("Arial", Font.BOLD, 20);
		}

		labelTitulo.setFont(marioFont);
		labelValor.setFont(marioFont);

		labelTitulo.setForeground(Color.WHITE);
		labelValor.setForeground(Color.WHITE);
	}


	private void colocarLabelsInfo(JLabel labelTitulo, JLabel labelValor, int posicionX) {
		labelTitulo.setBounds(posicionX, 5, 150, 20);  
		labelValor.setBounds(posicionX, 25, 150, 20);  

		panelInformacion.add(labelTitulo);
		panelInformacion.add(labelValor);
	}


	public Observer incorporarElemento(ElementoLogico elem) {
		ObserverElementos observerElemento = new ObserverElementos(elem);
		observerElemento.setPantallaJuego(this);
		imagenFondoJuego.add(observerElemento);

		return observerElemento;
	}

	public ObserverJugador incorporarElementoJugador(ElementoJugador player) {
		ObserverJugador observerJugador = new ObserverJugador(this, player);
		observerJugador.setPantallaJuego(this);
		imagenFondoJuego.add(observerJugador);
		actualizarInfoJugador(player);

		return observerJugador;
	}

	public void removerElemento(ObserverGrafico observer) {
		imagenFondoJuego.remove(observer); 
		imagenFondoJuego.repaint();
	}

	public void actualizarInfoJugador(ElementoJugador player) {
		actualizarLabelsJugador(player);
		actualizarScroll(player);
	}

	private void actualizarLabelsJugador(ElementoJugador player) {
		labelPuntaje.setText(textoConDigitos(player.getPuntaje(), 6));
		labelMonedas.setText(textoConDigitos(player.getMonedas(), 2));
		labelVidas.setText(textoConDigitos(player.getVida(), 1));
	}

	private String textoConDigitos(int num, int digitos) {
		String texto = Integer.toString(num);

		// Calcula cuantos ceros hay que añadir
		int cerosAAgregar = digitos - texto.length();

		if (cerosAAgregar > 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < cerosAAgregar; i++) {
				sb.append('0');
			}
			sb.append(texto);
			texto = sb.toString();
		}

		return texto;
	}

	public void actualizarScroll(ElementoJugador player) {
		int jugadorX = player.getPosX();

		int nuevaPosScroll = jugadorX - (panelScroll.getViewport().getWidth() / 2);

		if (nuevaPosScroll < 0) {
			nuevaPosScroll = 0;
		} else if (nuevaPosScroll > panelJuego.getWidth() - panelScroll.getViewport().getWidth()) {
			nuevaPosScroll = panelJuego.getWidth() - panelScroll.getViewport().getWidth();
		}

		panelScroll.getHorizontalScrollBar().setValue(nuevaPosScroll);
	}

	public void iniciarTimer() {
		timerNivel = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarTimer();
			}
		});
		timerNivel.start();
	}
	public void actualizarTimer() {
		if (tiempoRestante > 61 || tiempoRestante < 61 & tiempoRestante > 4 || tiempoRestante <= 3 && tiempoRestante > 0) {
			tiempoRestante--; 
			labelTiempo.setText(textoConDigitos(tiempoRestante, 3)); 
		} else if (tiempoRestante == 0){
			timerNivel.stop();
			this.controladorPantalla.getControladorPartida().timeOut();
		}else
			if (tiempoRestante == 61) {
				sonidoSpeedBackground();
				tiempoRestante--; 
				labelTiempo.setText(textoConDigitos(tiempoRestante, 3));
			}else
				if (tiempoRestante == 4) {
					sonidoPocoTiempo();
					tiempoRestante--; 
					labelTiempo.setText(textoConDigitos(tiempoRestante, 3));
				}
	}
	

	public void sonidoPocoTiempo() {
		ControladorSonidos.getInstance().detenerSonidoJuego(TipoSonidos.speedBackground);
		ControladorSonidos.getInstance().reproducirSonidoJuego(TipoSonidos.advertenciaTiempo);
	}

	public void sonidoSpeedBackground() {
		this.controladorPantalla.pararSonidoPartida();
		ControladorSonidos.getInstance().reproducirSonidoJuego(TipoSonidos.speedBackground);
	}


	public void actualizarLabelsNivel() {
		int numeroNivelActual = this.controladorPantalla.getNumeroNivel();	
		labelNivelActual.setText(textoConDigitos(numeroNivelActual, 1));
	}

	public void keyPressed(KeyEvent e) {
		controladorPantalla.activarMovement(e);  
	}

	public void keyReleased(KeyEvent e) {
		controladorPantalla.desactivarMovement(e);     
	}

	@Override
	public void keyTyped(KeyEvent e) {
	
	}
}
