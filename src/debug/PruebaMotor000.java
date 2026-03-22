package debug;

import com.rpg.motor.MotorJuego;
import com.rpg.teatro.Escena;

import herramientas.clima.Clima;
import herramientas.tiempo.CalendarioLunar;

public class PruebaMotor000 {
    public static void main(String[] args) {
        // 1. Preparación
        CalendarioLunar cal = new CalendarioLunar();
        Clima clima = new Clima("Tarde Soleada", 25.0f, 0.4f);
        Escena inicio = new Escena("Pradera", "Un lugar tranquilo.", cal, clima, null);
        
        // 2. Encender el Corazón
        MotorJuego motor = new MotorJuego(cal);
        motor.establecerEscena(inicio);
        
        motor.iniciar(); 
        
        // 3. El juego ahora corre en segundo plano.
        // Aquí podríamos poner un Scanner para recibir comandos del jugador.
        System.out.println(">>> El motor está corriendo. Escribe 'salir' para terminar (Simulado).");
    }
}