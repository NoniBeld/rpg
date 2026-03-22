package herramientas.guardado;

import com.rpg.ente.Atributo;
import com.rpg.ente.Creador;
import com.rpg.ente.EjeEtico;
import com.rpg.ente.EjeMoral;
import com.rpg.ente.Ente;
import com.rpg.ente.Funcion;
import com.rpg.ente.bestiario.fauna.Slime;

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
    
    public static Ente cargarPerfilJugador() {
        Ente jugador = new Ente(999, "Temporal", Funcion.SUJETO);
        try (BufferedReader br = new BufferedReader(new FileReader("usuario/perfil.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Nombre:")) jugador.cambiarNombre(linea.split(":")[1]);
                if (linea.startsWith("Chasis:")) jugador.inicializarAnatomiaPorChasis(linea.split(":")[1]);
                if (linea.startsWith("Etica:")) {
                    EjeEtico et = EjeEtico.valueOf(linea.split(":")[1]);
                    EjeMoral mor = EjeMoral.valueOf(br.readLine().split(":")[1]);
                    jugador.establecerAlineamiento(et, mor);
                }
                // ... lógica para leer Atributos con split("|") y split(":")
            }
        } catch (IOException e) {
            System.err.println("Error al despertar al jugador: " + e.getMessage());
        }
        return jugador;
    }
    /*
    public static Ente cargarPerfilJugador() {
        File archivo = new File("usuario/perfil.txt");
        // Lógica para leer el archivo (BufferedReader)
        // Supongamos que extraemos: "Nombre: Mora_Player", "Fuerza: 25"...
        
        Ente jugador = Creador.obtenerInstancia().instanciarDesdePlantilla(Slime.SLIME_MORA);
        jugador.cambiarNombre("Jugador");
        jugador.establecerValorAtributo(Atributo.VIDA, 50);
        jugador.establecerValorAtributo(Atributo.FUERZA, 10); // Dato del TXT
        jugador.establecerValorAtributo(Atributo.AGILIDAD, 10);
        jugador.establecerValorAtributo(Atributo.DESTREZA, 10);
        jugador.establecerValorAtributo(Atributo.INTELIGENCIA, 10);
        jugador.establecerValorAtributo(Atributo.SABIDURIA, 10);
        jugador.establecerValorAtributo(Atributo.CARISMA, 10);
        jugador.establecerValorAtributo(Atributo.SUERTE, 10);
        jugador.establecerValorAtributo(Atributo.CRITICO,10);
        jugador.establecerValorAtributo(Atributo.RESISTENCIA, 10);
        jugador.establecerValorAtributo(Atributo.ESPIRITU, 10);
        jugador.establecerValorAtributo(Atributo.CONSTITUCION,10);
        jugador.establecerValorAtributo(Atributo.VIDA_MAX, 200);
        jugador.establecerValorAtributo(Atributo.MAGIA, 10);
        
        return jugador;
    }*/
}