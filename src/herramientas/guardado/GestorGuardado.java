package herramientas.guardado;

import com.rpg.ente.*;
import com.rpg.ente.bestiario.fauna.Slime;
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class GestorGuardado {
    private static final String RUTA = "usuario/perfil.txt";

    public static void guardarProgreso(Ente jugador) {
        try {
            // Usamos el nuevo método que resume todo en una línea
            String datos = jugador.compactar();
            Files.writeString(Paths.get(RUTA), datos);
            System.out.println(">>> Alma de " + jugador.obtenerNombre() + " preservada en " + RUTA);
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }

    public static Ente cargarPerfilJugador() {
        try {
            // Leemos la línea compactada
            String datos = Files.readString(Paths.get(RUTA));
            String[] partes = datos.split("\\|");

            // partes[1] es el nombre, partes[2] es la Funcion
            String nombre = partes[1];
            Funcion func = Funcion.valueOf(partes[2]);

            // 1. Creamos la base con el Creador
            Ente jugador = Creador.obtenerInstancia().crearNuevoEnte(nombre, func);

            // 2. Restauramos posición (partes[3] -> "X,Y,Z")
            String[] coords = partes[3].split(",");
            jugador.teletransportar(
                Double.parseDouble(coords[0]), 
                Double.parseDouble(coords[1]), 
                Double.parseDouble(coords[2])
            );

            // 3. Restauramos Vida Actual (partes[4])
            int vidaCargada = Integer.parseInt(partes[4]);
            jugador.establecerValorAtributo(Atributo.VIDA, vidaCargada);

            return jugador;

        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.err.println("No se encontró perfil. Creando nuevo Slime de pruebas...");
            // Si falla el archivo, devolvemos un Slime por defecto para que el juego no truene
            return Creador.obtenerInstancia().instanciarDesdePlantilla(Slime.SLIME_MORA);
        }
    }
}