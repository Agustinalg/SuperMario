package juego;

import archivos.Sonido;

public class HiloSonido extends Thread {
    protected boolean enEjecucion;
    protected Sonido backGround;

    public HiloSonido() {
        enEjecucion = true;
        backGround = new Sonido("/audio/background.wav");
        backGround.configurarLoop();
    }

    public void run(){
        while (enEjecucion) {
            if (!backGround.enReproduccion()) {
                backGround.renaudar(); 
            }
            try {
                Thread.sleep(16); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pararLoop() {
        backGround.stopLoop();
        enEjecucion = false;
    }

    public void renaudar() {
        if (!enEjecucion) {
            enEjecucion = true;
            backGround.reproducirAudioFondo(); 
        }
    }

    public void detener() {
        backGround.detener();
        enEjecucion = false;
    }

    public boolean enEjecucion() {
        return enEjecucion;
    }
}