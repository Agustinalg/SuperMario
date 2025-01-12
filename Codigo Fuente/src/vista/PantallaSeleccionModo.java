package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import parseo.*;


public class PantallaSeleccionModo extends JPanel{


	/**
	 * 
	 */
	private static final long serialVersionUID = 3510466958737210007L;
	protected JButton botonModo1, botonModo2, backButton;
	protected JPanel panelBotones;
	protected JLabel background, selectionLabel;
	protected ControladorPantallas controlador;
	protected GameFactory fabricaUno, fabricaDos;


	public PantallaSeleccionModo(ControladorPantallas controlador){
		fabricaUno = new ModoUnoFactory();
		fabricaDos = new ModoDosFactory();
		this.controlador = controlador;
		this.setPreferredSize(new Dimension(ConstantesPantalla.panelAncho, ConstantesPantalla.panelAlto));
		setLayout(null);
		agregarImagenFondo();
		inicializarIconoSeleccion();
		agregarBotones();
	}
	
	private void inicializarIconoSeleccion() {
		ImageIcon iconSeleccion = new ImageIcon(getClass().getResource("/imagenes/selectIcon.png"));
		Image imagenSeleccion = iconSeleccion.getImage().getScaledInstance(46, 46, Image.SCALE_SMOOTH);

		selectionLabel = new JLabel(new ImageIcon(imagenSeleccion));
		selectionLabel.setPreferredSize(new Dimension(48,48));
	}


	private void agregarBotones() {
		panelBotones = new JPanel();
		panelBotones.setBounds(260,	300, 300, 150);
		panelBotones.setLayout(new FlowLayout());
		panelBotones.setOpaque(false);
		//panelBotones.setBackground(Color.black);
		
		botonModo1 = new JButton("Modo 1"); 
		botonModo2 = new JButton("Modo 2");
		backButton = new JButton("Volver");
		inicializarBoton(botonModo1);
		inicializarBoton(botonModo2);
		inicializarBoton(backButton);
		
		backButton.setBounds(150, 500, 260, 55);
		
		agregarEventos();
		
		panelBotones.add(botonModo1);
		panelBotones.add(botonModo2);
		add(panelBotones);
		add(backButton);
		background.add(panelBotones);
		background.add(backButton);
	}

	private void agregarEventos() {
		botonModo1.addActionListener(e -> {
			controlador.setFabrica(fabricaUno);
			controlador.mostrarPantallaJuego();
		});

		botonModo2.addActionListener(e -> {
			controlador.setFabrica(fabricaDos);
			controlador.mostrarPantallaJuego();
		});
		
		backButton.addActionListener(e -> {
			controlador.mostrarPantallaInicial();
		});
	}

	protected void agregarImagenFondo(){
		ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/imagenseleccionmodo.png"));
		Image imagen = icon.getImage().getScaledInstance(ConstantesPantalla.panelAncho,ConstantesPantalla.panelAlto,Image.SCALE_SMOOTH);
		background = new JLabel(new ImageIcon(imagen));
		background.setBounds(0,-40,ConstantesPantalla.panelAncho,ConstantesPantalla.panelAlto);

		add(background); 
	}

	private void inicializarBoton(JButton button){
		button.setPreferredSize(new Dimension(260, 55));
		button.setFont(ConfigurarFuente.getFuenteMario(22f));
		button.setForeground(Color.WHITE);
		addMouseActions(button);
		decorarBoton(button);
	}

	private void addMouseActions(JButton button) {
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.add(selectionLabel, BorderLayout.WEST);
				revalidate();
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.remove(selectionLabel);
				revalidate();
				repaint();
			}
		});	
	}

	private void decorarBoton(JButton boton) {
		boton.setContentAreaFilled(false); 
		boton.setBorderPainted(false); 
		boton.setFocusPainted(false);  
		boton.setOpaque(false);
	}
}



