package observers;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import elementos.ElementoLogico;
import vista.PantallaJuego;

public abstract class ObserverGrafico extends JLabel implements Observer{


	private static final long serialVersionUID = 1L;
	protected ElementoLogico elemObservado;
	protected ImageIcon iconoEscalado;
	protected String rutaAnterior;
	protected PantallaJuego pantallaJuego;

	protected ObserverGrafico(ElementoLogico observado) {
		super();
		this.elemObservado = observado;
		escalarImagen();
	}
	
	private void escalarImagen() {
		rutaAnterior = elemObservado.getSprite().getRutaImagen();
		
		String rutaImagen = elemObservado.getSprite().getRutaImagen();
		ImageIcon iconoOriginal = new ImageIcon(getClass().getClassLoader().getResource(rutaImagen));
	
		int alto = elemObservado.getAlto();
		int ancho = elemObservado.getAncho();
		Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH); 	
		iconoEscalado = new ImageIcon(imagenEscalada); 
	} 
	
	public void eliminar() {
		pantallaJuego.removerElemento(this);
	}
	
	public void setPantallaJuego (PantallaJuego pantalla) {
		this.pantallaJuego = pantalla;
	}

	public void actualizar() {
		actualizarImagen();
		actualizarPosicionTamaño();
	}

	protected void actualizarImagen() {	
		String rutaActual = elemObservado.getSprite().getRutaImagen();
		
		int anchoActual = elemObservado.getAncho();
		int altoActual = elemObservado.getAlto();
		
		boolean mismaRuta = (rutaAnterior == rutaActual);
		boolean mismoAncho = (anchoActual == iconoEscalado.getIconWidth());
		boolean mismoAlto = (altoActual == iconoEscalado.getIconHeight());
		
		if((!mismoAncho) || (!mismoAlto) || (!mismaRuta)) {
			escalarImagen();
		}
		setIcon(iconoEscalado); 
	}

	protected void actualizarPosicionTamaño() {
		int x = AdaptadorPosicionPixel.transformarX(this.elemObservado.getPosX());
		int y = AdaptadorPosicionPixel.transformarY(this.elemObservado.getPosY());

		int ancho = this.getIcon().getIconWidth();
		int alto = this.getIcon().getIconHeight();
		this.setBounds(x, y, ancho, alto);

	}
}
