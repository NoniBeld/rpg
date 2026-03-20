package herramientas.texto;

public interface Escritor {
    
    // Método por defecto: todas las clases que implementen Escritor lo tendrán
    default void narrar(String texto, int velocidad) {
        for (char caracter : texto.toCharArray()) {
            System.out.print(caracter);
            try {
                Thread.sleep(velocidad);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }
}