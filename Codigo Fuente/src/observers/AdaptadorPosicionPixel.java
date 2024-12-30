package observers;

import vista.ConstantesPantalla;

public class AdaptadorPosicionPixel {

	public static int transformarX(int x) {
		return x;
	}
	
	public static int transformarY(int y) {
		return ConstantesPantalla.panelJuegoAlto - y; 
	}
	
}
