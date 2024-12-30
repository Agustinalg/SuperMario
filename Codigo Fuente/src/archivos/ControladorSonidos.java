package archivos;

public class ControladorSonidos {
    private static ControladorSonidos instanciaUnica;

    private ControladorSonidosAcciones controladorSonidosAcciones;
    private ControladorSonidosJuego controladorSonidosJuego;

    private ControladorSonidos() {
        this.controladorSonidosAcciones = new ControladorSonidosAcciones();
        this.controladorSonidosJuego = new ControladorSonidosJuego();
    }

    public static ControladorSonidos getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new ControladorSonidos();
        }
        return instanciaUnica;
    }

    public void reproducirSonidoAccion(TipoSonidos tipo) {
        controladorSonidosAcciones.reproducirSonido(tipo);
    }

    public void reproducirSonidoJuego(TipoSonidos tipo) {
        controladorSonidosJuego.reproducirSonido(tipo);
    }

    public void detenerSonidoAccion(TipoSonidos tipo) {
        controladorSonidosAcciones.detenerSonido(tipo);
    }

    public void detenerSonidoJuego(TipoSonidos tipo) {
        controladorSonidosJuego.detenerSonido(tipo);
    }
}