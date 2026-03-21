package herramientas.guardado;

import com.rpg.teatro.Escena;
import com.rpg.ente.Ente;
import java.io.*;

public class GestorMapa {
    
    public static void dormirEscena(Escena escena) {
        String nombreArchivo = "usuario/mapa/" + escena.obtenerNombre() + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (Ente e : escena.obtenerPresentes()) {
                writer.println(e.compactar()); // Guardamos solo la esencia
            }
        } catch (IOException ex) {
            System.err.println("Error al 'dormir' la escena.");
        }
    }
}