package archivos;
import javax.sound.sampled.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Sonido {
    private Clip clip;
    private boolean audioOn;

    public Sonido(String ruta) {
       /*
    	try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(ruta));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    audioOn = false;
                }
            });
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        audioOn = false;
        */
        
        
        
        try {
            InputStream audioSrc = getClass().getResourceAsStream(ruta);
            if (audioSrc == null) {
                throw new IllegalArgumentException("El archivo de audio no fue encontrado: " + ruta);
            }
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
            
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    audioOn = false;
                }
            });
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        audioOn = false; 
    }

    public void reproducirAudioFondo() {
        if (!audioOn) {
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            audioOn = true;
        }
    }

    public void reproducirSonido() {
        clip.setFramePosition(0);
        clip.start();
        audioOn = true;
    }

    public void configurarLoop() {
        if (!audioOn) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            audioOn = true;
        }
    }

    public void stopLoop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
        audioOn = false;
    }

    public void renaudar() {
        if (!audioOn) {
            clip.start();
            audioOn = true;
        }
    }

    public void detener() {
        if (clip.isRunning()) {
            clip.stop();
            audioOn = false;
        }
    }

    public void cerrar() {
        clip.close();
    }

    public boolean enReproduccion() {
        return audioOn;
    }
}