package herramientas.guardado;

import com.rpg.teatro.Escena;

import herramientas.clima.Bioma;
import herramientas.clima.Clima;
import herramientas.tiempo.CalendarioLunar;

import com.rpg.ente.Creador;
import com.rpg.ente.Ente;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GestorMapa {
	
	public static Escena despertarEscena(String ruta, CalendarioLunar cal) {
        try {
            List<String> lineas = Files.readAllLines(Paths.get(ruta));
            // La primera línea podría guardar el Bioma y Nombre de la Escena
            // Sector [0,0,0]|BOSQUE|Descripcion...
            String[] infoEscena = lineas.get(0).split("\\|");
            
            Bioma bioma = Bioma.valueOf(infoEscena[1]);
            Clima clima = new Clima(bioma.obtenerNombre(), bioma.obtenerTemperaturaBase(), bioma.obtenerHumedadBase());
            
            Escena escena = new Escena(infoEscena[0], infoEscena[2], cal, clima, bioma);

            // Cargamos los Entes (a partir de la línea 1)
            for (int i = 1; i < lineas.size(); i++) {
                Ente e = reconstruirEnte(lineas.get(i));
                escena.agregarEnte(e);
            }
            return escena;
        } catch (Exception e) {
            return null; 
        }
    }
    
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
    
    private static Ente reconstruirEnte(String datos) {
        // ID:1|N:Slime|F:SUJETO|V:40...
        String[] partes = datos.split("\\|");
        String nombre = partes[1].split(":")[1];
        // Aquí usamos el Creador para instanciar y luego setear los valores guardados
        Ente nuevo = Creador.obtenerInstancia().crearNuevoEnte(nombre, null); 
        // Lógica para parsear vida, atributos, etc.
        return nuevo;
    }

}