package com.herramientas.tiempo;

/**
 * Gestiona el paso del tiempo real y su conversión a tiempo de juego.
 * Aplica el patrón Singleton.
 */
public final class RelojPrincipal {
    private static RelojPrincipal instancia;
    
    private long tiempoInicial;
    private long tiempoUltimaActualizacion;
    private double factorEscalaTiempo = 1.0; // 1.0 = Tiempo real, 2.0 = El doble de rápido

    public RelojPrincipal() {
        this.tiempoInicial = System.nanoTime();
        this.tiempoUltimaActualizacion = tiempoInicial;
    }

    public static RelojPrincipal obtenerInstancia() {
        if (instancia == null) {
        	instancia = new RelojPrincipal();
        }
        return instancia;
    }

    /**
     * Calcula cuántos segundos han pasado desde la última vez que se llamó.
     * Es el famoso 'Delta Time' usado en 2D y 3D.
     */
    public double obtenerDeltaTiempo() {
        long tiempoActual = System.nanoTime();
        double delta = (tiempoActual - tiempoUltimaActualizacion) / 1_000_000_000.0;
        tiempoUltimaActualizacion = tiempoActual;
        return delta * factorEscalaTiempo;
    }
}