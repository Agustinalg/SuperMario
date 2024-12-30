package juego;


public class HiloElementos extends Thread {
	protected ControladorPartida controlador;
	protected ColisionesNivel colisiones;
	protected boolean enEjecucion;

	public HiloElementos(ControladorPartida controlador, ColisionesNivel colisiones) {
		this.controlador = controlador;
		this.colisiones = colisiones;
		enEjecucion = true;
	}

	public void run(){
		while(enEjecucion){
			colisiones.actualizarEntidades();
			try {
				Thread.sleep(16); // Aproximadamente 60fps
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} 
	}

	public void detener() {
		enEjecucion = false;
	}
}
