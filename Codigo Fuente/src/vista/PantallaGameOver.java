package vista;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JLabel;
import javax.swing.JButton;


public class PantallaGameOver extends JPanel{

	private static final long serialVersionUID = 1L;
	protected JLabel imagenGameOver;
	protected JLabel imagenTimeUp;
	protected JLabel tituloGameOver;
	protected ControladorPantallas controlador;
	protected JButton botonRanking;
	private Timer timer;


	public PantallaGameOver(ControladorPantallas controlador) {
		this.controlador = controlador;
		this.setPreferredSize(new Dimension(ConstantesPantalla.panelAncho, ConstantesPantalla.panelAlto));
		this.setLayout(null);
		this.setBackground(Color.BLACK);

		agregarTituloGameOver();
		agregarBotonRanking();		
	}

	public void iniciarTemporizador() {
		iniciarTemporizador(5000);
	}

	private void agregarTituloGameOver() {
		tituloGameOver = new JLabel("GAME OVER");
		tituloGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		tituloGameOver.setBounds(0, 200, ConstantesPantalla.panelAncho, 50);

		decorarLabelGameOver(tituloGameOver);
		add(tituloGameOver);
	}	

	protected void agregarBotonRanking() {
		botonRanking = new JButton("Ranking");
		botonRanking.setBounds(280, ConstantesPantalla.panelAlto - 100, 240, 50);
		botonRanking.setBackground(Color.BLACK);
		botonRanking.setForeground(Color.WHITE);    
		botonRanking.setOpaque(true);             
		decorarBotonRanking(botonRanking);
		botonRanking.addActionListener(e -> {
			detenerTemporizador(); 
			controlador.mostrarPantallaRanking();
		});
		add(botonRanking);
	}	

	protected void decorarBotonRanking(JButton boton) {
		Font marioFont = null;
		try {
			InputStream is = getClass().getResourceAsStream("/archivos/mario-font.ttf");
			marioFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(20f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			marioFont = new Font("Arial", Font.BOLD, 20);
		}

		boton.setFont(marioFont);
		boton.setForeground(Color.WHITE);
	}

	private void decorarLabelGameOver(JLabel tituloGameOver) {
		Font marioFont = null;
		try {
			InputStream is = getClass().getResourceAsStream("/archivos/mario-font.ttf");
			marioFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(28f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			marioFont = new Font("Arial", Font.BOLD, 20);
		}
		if(tituloGameOver != null) {
			tituloGameOver.setFont(marioFont);
			tituloGameOver.setForeground(Color.WHITE);
		}
	}

	protected void iniciarTemporizador(int delay) {
		timer = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				finalizarPantalla();
				timer.stop(); 
			}
		});
		timer.setRepeats(false); 
		timer.start();
	}

	protected void detenerTemporizador() {
		if (timer != null) {
			timer.stop();
		}
	}

	protected void finalizarPantalla() {
		controlador.mostrarPantallaInicial();
	}
}
