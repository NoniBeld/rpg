package com.rpg.motor;

import herramientas.tiempo.RelojPrincipal;
import herramientas.tiempo.CalendarioLunar;
import herramientas.texto.Narrador;
import com.rpg.logica.Escena;

/**
 * El motor principal que mantiene el mundo vivo.
 */
public final class MotorJuego implements Runnable {
    private boolean enEjecucion = false;
    private Thread hiloMotor;
    
    // Dependencias del Mundo
    private final RelojPrincipal reloj;
    private final CalendarioLunar calendario;
    private Escena escenaActual;

    public MotorJuego(CalendarioLunar calendario) {
        this.reloj = new RelojPrincipal();
        this.calendario = calendario;
        this.enEjecucion = false;
    }

    public void establecerEscena(Escena nuevaEscena) {
        this.escenaActual = nuevaEscena;
    }

    public synchronized void iniciar() {
        if (enEjecucion) return;
        enEjecucion = true;
        hiloMotor = new Thread(this, "Hilo-Logica-RPG");
        hiloMotor.start();
    }

    @Override
    public void run() {
        Narrador.obtenerInstancia().narrar("--- Motor Iniciado ---", 10);
        
        // El bucle de tiempo real
        while (enEjecucion) {
            double delta = reloj.obtenerDeltaTiempo();
            
            actualizar(delta);
            
            // Limitamos el bucle para no consumir 100% de CPU innecesariamente (aprox 60 ticks)
            try {
                Thread.sleep(16); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void actualizar(double delta) {
        // 1. Avanzar el tiempo lógico si es necesario
        // Aquí podríamos hacer que cada X segundos reales pase un minuto en el juego
        
        // 2. Actualizar todos los entes en la escena actual
        if (escenaActual != null) {
            // Aquí iría la lógica de actualización masiva que mencionamos antes
        }
    }

    public void detener() {
        enEjecucion = false;
    }
}