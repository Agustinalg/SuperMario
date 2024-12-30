package archivos;


public class Sprite {

	protected String rutaImagen;

	public Sprite(String ruta) {
		this.rutaImagen = ruta;
	}

	public String getRutaImagen() {
		return this.rutaImagen;
	}

	public void setSprite(String ruta) {
		this.rutaImagen = ruta;
	}
}
