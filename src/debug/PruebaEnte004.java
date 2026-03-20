package debug;

import com.rpg.ente.*;
import herramientas.texto.Narrador;

public class PruebaEnte004 {
    public static void main(String[] args) {
        Ente zombi = Creador.obtenerInstancia().crearNuevoEnte("Slime Zombi", Funcion.RECEPTOR);
        
        Narrador.obtenerInstancia().narrar("--- EXPERIMENTO DE NECROMANCIA ---", 30);
        
        // El Slime comienza con 10 de vida
        zombi.establecerValorAtributo(Atributo.VIDA, 10);
        
        // Recibe un golpe mortal de 50
        Narrador.obtenerInstancia().narrar("Un golpe sagrado impacta al Slime...", 20);
        zombi.recibirImpacto(50); 
        
        // Resultado: Vida es -40
        if (zombi.obtenerValorAtributo(Atributo.VIDA) < 0) {
            zombi.hablar("... sigo... aquí... (Vida: " + zombi.obtenerValorAtributo(Atributo.VIDA) + ")");
        }
    }
}