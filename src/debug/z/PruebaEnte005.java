package debug.z;

import com.rpg.ente.*;

import herramientas.clima.Clima;
import herramientas.texto.Narrador;

public class PruebaEnte005 {/*
    public static void main(String[] args) {
        Narrador cronista = Narrador.obtenerInstancia();
        cronista.narrar("=== PRUEBA INTEGRAL 005: EL CICLO DEL ENTE ===", 20);

        // 1. Creación
        Ente rama = Creador.obtenerInstancia().crearNuevoEnte("Rama Antigua", Funcion.OBJETO);
        
        // 2. Movimiento y Percepción Espacial
        rama.teletransportar(5.0f, 0.0f, 0.0f);
        cronista.narrar("La rama yace en el suelo en la posición X:5", 40);

        // 3. Sensibilidad al Clima
        Clima tormentaNieve = new Clima("Pico Congelado", -10.5f, 80.0f);
        rama.sentirClima(tormentaNieve);

        // 4. CAMBIO DE FUNCIÓN (De Objeto a Arma)
        cronista.narrar("Un viajero recoge la rama y le saca punta...", 50);
        rama.cambiarFuncion(Funcion.ARMA);

        // 5. VIDA NEGATIVA Y AGONÍA
        cronista.narrar("El arma es golpeada por un martillo pesado...", 40);
        rama.recibirImpacto(150); // Esto la llevará a vida negativa (-50)

        // 6. Estado Final
        cronista.narrar("Estado final de la Rama: " + rama.obtenerVidaActual() + " HP. Función: " + rama.obtenerFuncionActual(), 30);
    }*/
}