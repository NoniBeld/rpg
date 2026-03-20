package herramientas.texto;

/**
 * Singleton que centraliza la salida de texto de todo el juego.
 */
public final class Narrador implements Escritor {
    private static Narrador instancia;

    private Narrador() {}

    public static Narrador obtenerInstancia() {
        if (instancia == null) {
        	instancia = new Narrador();
        	}
        return instancia;
    }

    // El método narrar ya viene de la interfaz Escritor
}