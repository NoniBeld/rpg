package debug;

import com.rpg.ente.*;
import com.rpg.teatro.Coliseo;

import herramientas.clima.Clima;

public class PruebaClima000{
    public static void main(String[] args) {
        Ente lava = Creador.obtenerInstancia().crearNuevoEnte("Slime de Lava", Funcion.RECEPTOR);
        Ente hielo = Creador.obtenerInstancia().crearNuevoEnte("Slime de Hielo", Funcion.RECEPTOR);
        
        // 1. Configurar Atributos
        lava.establecerValorAtributo(Atributo.FUERZA, 15);
        hielo.establecerValorAtributo(Atributo.AGILIDAD, 10);

        // 2. Simular Clima (Selva Calurosa)
        Clima selva = new Clima("Selva Profunda", 38.5f, 90.0f);
        
        lava.sentirClima(selva);  // Para él está bien
        hielo.sentirClima(selva); // Él está sufriendo
        
        // 3. El Coliseo
        Coliseo arena = new Coliseo();
        arena.duelo(lava, hielo);
    }
}