package debug.z;

import com.rpg.ente.Creador;
import com.rpg.ente.Ente;
import com.rpg.ente.Funcion;
import herramientas.texto.Escritor; // Importamos tu interfaz

public class PruebaEnte001 implements Escritor {/*

    public static void main(String[] args) {
        // Instanciamos la clase para poder usar los métodos default de Escritor
        new PruebaEnte001().iniciarSimulacion();
    }

    public void iniciarSimulacion() {
        // Usamos narrar en lugar de System.out.println
        narrar("--- INICIANDO CRÓNICAS DEL MUNDO ---", 30);

        // Creación mediante el Framework
        Ente slime = Creador.obtenerInstancia().crearNuevoEnte("Slime Alpha", Funcion.OBJETO);
        
        // Modificación de atributos
        slime.teletransportar(10.5f, 0.0f, 50.5f);
        
        narrar("Un destello de luz aparece en la posición " + slime.obtenerNombre() + "...", 50);
        narrar("Manifestación física completada: " + slime.toString(), 20);
        
        if (slime.obtenerIdentificadorUnico() > 0) {
            narrar("El ente [" + slime.obtenerNombre() + "] comienza a respirar...", 60);
        
            Ente slimeComida = Creador.obtenerInstancia().crearNuevoEnte("Slime de Fresa", Funcion.ALIMENTO);
            
            narrar("De repente, " + slime.obtenerNombre() + " detecta algo cerca...", 40);
            narrar("¡Es un " + slimeComida.obtenerNombre() + "! Lo identifica como: " + Funcion.ALIMENTO, 50);
            narrar("" + slimeComida.toString(), 20);
            narrar(slime.obtenerNombre() + " observa a su presa con hambre...", 80);
        }
    }*/
}