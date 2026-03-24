package debug;

import com.ente.*;
import com.motor.Orden;
import com.motor.Verbo;

import java.util.Stack;

public class PruebaEnte11 {
    // Pila temporal para el comando de consola
    private static Stack<Ente> matruzaTemporal = new Stack<>();

    public static void main(String[] args) {
        System.out.println("=== PRUEBA 011: SISTEMA DE MATRUZAS ELEMENTALES ===\n");

        // 1. Creamos los eslabones de la cadena
        Ente gallo = Creador.obtenerInstancia().crearNuevoEnte("Gallo de Fuego", Funcion.CONTENEDOR);
        Ente leon = Creador.obtenerInstancia().crearNuevoEnte("Leon de Viento", Funcion.CONTENEDOR);
        Ente cetaceo = Creador.obtenerInstancia().crearNuevoEnte("Cetaceo de Agua", Funcion.CONTENEDOR);
        Ente caramuela = Creador.obtenerInstancia().crearNuevoEnte("Caramuela Mineral", Funcion.CONTENEDOR);

        // 2. SIMULACIÓN DE COMANDOS DE CONSOLA
        System.out.println(">>> Comando: crear matruza gallo, leon, cetaceo, caramuela");
        matruzaTemporal.push(gallo);
        matruzaTemporal.push(leon);
        matruzaTemporal.push(cetaceo);
        matruzaTemporal.push(caramuela);

        System.out.println(">>> Comando: cerrar matruza");
        Ente matruzaFinal = cerrarMatruza();

        // 3. VERIFICACIÓN DE INVENTARIO ANIDADO
        System.out.println("\n--- INSPECCIÓN DE LA MATRUZA FINAL ---");
        inspeccionarAnidamiento(matruzaFinal, 0);

        // 4. ENTREGA A JUGADOR
        Ente adan = Creador.obtenerInstancia().crearNuevoEnte("Adan", Funcion.SUJETO);
        adan.obtenerInventario().add(matruzaFinal);
        System.out.println("\n>>> Adan ha recibido la Matruza Elemental.");
    }

    private static Ente cerrarMatruza() {
        Ente hijo = null;
        while (!matruzaTemporal.isEmpty()) {
            Ente padre = matruzaTemporal.pop();
            if (hijo != null) {
                // Metemos al ente anterior dentro del inventario del actual
                padre.obtenerInventario().add(hijo);
                System.out.println("   Encapsulando " + hijo.obtenerNombre() + " dentro de " + padre.obtenerNombre());
            }
            hijo = padre;
            if (matruzaTemporal.isEmpty()) return padre; // El último en salir es el contenedor principal
        }
        return null;
    }

    private static void inspeccionarAnidamiento(Ente ente, int nivel) {
        String tab = "  ".repeat(nivel);
        System.out.println(tab + "-> " + ente.obtenerNombre() + " [ID: " + ente.obtenerIdentificadorUnico() + "]");
        for (Ente contenido : ente.obtenerInventario()) {
            inspeccionarAnidamiento(contenido, nivel + 5);
        }
    }
}