package Launcher;


import archivos.TopRanking;
import juego.ControladorPartida;
import vista.ControladorPantallas;

import java.awt.EventQueue;
import java.io.*;;

public class Launcher {

	public static void main(String [] args) {		

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TopRanking ranking = new TopRanking();			
					 
					 try (InputStream inputStream = getClass().getResourceAsStream("/archivos/puntajes.tdp")) {
    						if (inputStream == null) {
        						throw new IllegalArgumentException("El archivo puntajes.tdp no fue encontrado dentro del .jar.");
    						}
    						ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
    						ranking = (TopRanking) objectInputStream.readObject();
    						objectInputStream.close();
						} catch (IOException | ClassNotFoundException e) {
    						e.printStackTrace();
						}

					ControladorPartida partida =  new ControladorPartida(ranking);
					ControladorPantallas pantallas = new ControladorPantallas(partida);
					partida.setControladorPantallas(pantallas);
					pantallas.mostrarPantallaInicial();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
