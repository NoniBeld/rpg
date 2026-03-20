package debug;

import com.rpg.ente.*;
import com.rpg.logica.Escena;

import herramientas.clima.Clima;
import herramientas.tiempo.*;

public class PruebaEnte006 {
    public static void main(String[] args) {
        // 1. Entorno
        CalendarioLunar cal = new CalendarioLunar();
        Clima cuevaTemplada = new Clima("Cueva Húmeda", 18.0f, 0.8f);
        Escena cueva = new Escena("Cueva Profunda", "Gotea agua del techo.", cal, cuevaTemplada);

        // 2. Los tres contendientes (Todos son SUJETOS pero también ALIMENTO)
        Ente azul = Creador.obtenerInstancia().crearNuevoEnte("Slime Mora", Funcion.SUJETO);
        Ente rojo = Creador.obtenerInstancia().crearNuevoEnte("Slime Fresa", Funcion.SUJETO);
        Ente verde = Creador.obtenerInstancia().crearNuevoEnte("Slime Limon", Funcion.SUJETO);

        // Los colocamos cerca pero no encima
        azul.teletransportar(0, 0, 0);
        rojo.teletransportar(1, 0, 0);
        verde.teletransportar(0, 1, 0);

        cueva.agregarEnte(azul);
        cueva.agregarEnte(rojo);
        cueva.agregarEnte(verde);

        // 3. Iniciar Simulación
        cueva.jugar();
        
        // Simulamos el paso del tiempo donde el azar decide quién tiene hambre primero
        java.util.Random azar = new java.util.Random();
        
        System.out.println("\n--- COMIENZA LA SUPERVIVENCIA ---");
        
        // El motor procesaría esto en el hilo de lógica:
        for(int i = 0; i < 5; i++) {
            Ente cazador = cueva.obtenerPresentes().get(azar.nextInt(3));
            Ente presa = cueva.obtenerPresentes().get(azar.nextInt(3));
            
            if (cazador != presa && cazador.percibir(presa, 2.0f)) {
                cazador.hablar("¡Tengo hambre de " + presa.obtenerNombre() + "!");
                cazador.interactuar(presa);
            }
        }
    }
}