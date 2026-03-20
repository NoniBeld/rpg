package debug;

import com.rpg.ente.Creador;
import com.rpg.ente.Ente;
import com.rpg.ente.Funcion;

public class PruebaEnte000 {

	public static void main(String[] args) {
		// Creación mediante el Framework
        Ente slime = Creador.obtenerInstancia().crearNuevoEnte("Slime", Funcion.OBJETO );
          // Modificación de atributos
        slime.teletransportar(10.5f, 0.0f, 50.5f);
        
        // Simulación de "Modo Ciego/Texto"
        System.out.println("--- LOG DEL MUNDO ---");
        System.out.println("Se ha manifestado en la materia: " + slime.toString());
        
        if (slime.obtenerIdentificadorUnico() > 0) {
            System.out.println("El ente " + slime.obtenerNombre() + " respira en el espacio tridimensional.");
        
            Ente slime2 = Creador.obtenerInstancia().crearNuevoEnte("Slime", Funcion.ALIMENTO);
            
            System.out.println("El Slime" + slime.obtenerNombre() + "ve a " + slime2.obtenerNombre()+"ve la comida del dia");
            
        }
    }
}