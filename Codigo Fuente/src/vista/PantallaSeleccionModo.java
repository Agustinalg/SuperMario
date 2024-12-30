package vista;

import java.awt.Dimension;
import java.awt.Image;
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
	protected JButton botonModo1;
	protected JButton botonModo2;
	protected JLabel imagenFondo;
	protected ControladorPantallas controlador;
	protected GameFactory fabricaUno, fabricaDos;


	public PantallaSeleccionModo(ControladorPantallas controlador){
		fabricaUno = new ModoUnoFactory();
		fabricaDos = new ModoDosFactory();
		this.controlador = controlador;
		this.setPreferredSize(new Dimension(ConstantesPantalla.panelAncho, ConstantesPantalla.panelAlto));
		setLayout(null);
		agregarImagenFondo();
		agregarBotonModoUno();
		agregarBotonModoDos();
	}

	protected void agregarImagenFondo(){
		ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/imagenseleccionmodo.png"));
		Image imagen = icon.getImage().getScaledInstance(ConstantesPantalla.panelAncho,ConstantesPantalla.panelAlto,Image.SCALE_SMOOTH);
		imagenFondo = new JLabel(new ImageIcon(imagen));
		imagenFondo.setBounds(0,-40,ConstantesPantalla.panelAncho,ConstantesPantalla.panelAlto);

		add(imagenFondo); 
	}

	private void agregarBotonModoUno(){
		botonModo1 = new JButton(); 
		botonModo1.setBounds(280,300,200,50);
		decorarBoton(botonModo1); 

		botonModo1.addActionListener(e -> {
			controlador.setFabrica(fabricaUno);
			controlador.mostrarPantallaJuego();
		});

		add(botonModo1);
		imagenFondo.add(botonModo1);
		botonModo1.setVisible(true);
	}

	private void agregarBotonModoDos(){
		botonModo2 = new JButton();
		botonModo2.setBounds(280,390,200,50);
		decorarBoton(botonModo2);

		botonModo2.addActionListener(e -> {
			controlador.setFabrica(fabricaDos);
			controlador.mostrarPantallaJuego();
		});

		add(botonModo2);
		imagenFondo.add(botonModo2);
		botonModo2.setVisible(true);
	}

	private void decorarBoton(JButton boton) {
		boton.setContentAreaFilled(false); 
		boton.setBorderPainted(false); 
		boton.setFocusPainted(false);  
		boton.setOpaque(false);
	}
}



