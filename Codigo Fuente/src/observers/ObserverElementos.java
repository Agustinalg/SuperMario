package observers;

import elementos.ElementoLogico;

public class ObserverElementos extends ObserverGrafico{

	private static final long serialVersionUID = 1L;

	public ObserverElementos(ElementoLogico observado) {
		super(observado);
		actualizar();
	}

}
