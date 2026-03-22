 package debug.z;

	import com.rpg.ente.*;
import com.rpg.teatro.Escena;

import herramientas.tiempo.*;
import herramientas.clima.Clima;
import herramientas.texto.Narrador;

	public class PruebaEscenario001 {
	    public static void main(String[] args) {
	        // 1. Inicializar el Tiempo del Mundo
	        CalendarioLunar calendario = new CalendarioLunar();
	        // Simulamos que han pasado algunos días para que no sea el día 1
	        for(int i = 0; i < 5; i++) calendario.avanzarDia();

	        // 2. Definir el Clima de la Escena
	        Clima vernoFrio = new Clima("Viento Helado", -5.0f, 40.0f);

	        // 3. Crear la Escena
	        Escena bosque = new Escena(
	            "Bosque de los Susurros", 
	            "Los árboles están cubiertos de escarcha y el silencio es sepulcral.",
	            calendario,
	            vernoFrio, null
	        );

	        // 4. Poblar la Escena
	        Ente jugador = Creador.obtenerInstancia().crearNuevoEnte("Erick", Funcion.SUJETO);
	        Ente slimeHielo = Creador.obtenerInstancia().crearNuevoEnte("Slime de Hielo", Funcion.ALIMENTO);
	        
	        bosque.agregarEnte(jugador);
	        bosque.agregarEnte(slimeHielo);

	        // 5. ¡ACCIÓN!
	        bosque.simularCiclo();
	        
	        Narrador.obtenerInstancia().narrar("\n[ Decides actuar ]", 30);
	        jugador.interactuar(slimeHielo);
	    }
	}