package herramientas.guardado;

import com.rpg.ente.Ente;
import java.io.*;
import java.nio.file.*;

public class GestorGuardado {
    private static final String RUTA = "usuario/perfil.txt";

    public static void guardarProgreso(Ente jugador) {
        try {
            String datos = "NOMBRE:" + jugador.obtenerNombre() + "\n" +
                           "VIDA:" + jugador.obtenerVidaActual() + "\n" +
                           "TAMANO:" + jugador.obtenerTamaño();
            Files.writeString(Paths.get(RUTA), datos);
            System.out.println(">>> Progreso guardado en " + RUTA);
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }
}