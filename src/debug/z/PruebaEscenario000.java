package debug.z;

import com.ente.Creador;
import com.ente.Ente;
import com.ente.Funcion;
import com.rpg.teatro.Escena;

public class PruebaEscenario000 {
	public static void main(String[] args) {
        // 1. Crear el Mundo
        Escena calabozo = new Escena("Te encuentras en una celda húmeda. El olor a musgo es penetrante.", null, null, null, null);
        
        // 2. Crear a los Actores
        Ente jugador = Creador.obtenerInstancia().crearNuevoEnte("Slime de Agua", Funcion.SUJETO);
        Ente slimeComida = Creador.obtenerInstancia().crearNuevoEnte("Slime de Uva", Funcion.ALIMENTO);
        Ente daga = Creador.obtenerInstancia().crearNuevoEnte("Daga Oxidada", Funcion.ARMA);

        calabozo.agregarEnte(jugador);
        calabozo.agregarEnte(slimeComida);
        calabozo.agregarEnte(daga);

        // 3. Ejecutar Narrativa
        calabozo.ejecutarBatallaReal();;

        // 4. Interacciones Dinámicas
        jugador.interactuar(daga);        // Lo recoge
        jugador.interactuar(slimeComida); // Lo consume y se cura
    }
}