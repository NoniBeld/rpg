package com.rpg.ente;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Fábrica encargada de centralizar la creación de entes.
 * Aplica el patrón GoF: Factory Method.
 */
public final class Creador {
    private static Creador instancia;
    private final AtomicInteger contadorDeIdentidad;

    private Creador() {
        this.contadorDeIdentidad = new AtomicInteger(0);
    }

    public static Creador obtenerInstancia() {
        if (instancia == null) {
            instancia = new Creador();
        }
        return instancia;
    }

    public Ente crearNuevoEnte(String nombre, Funcion funcion) {
        Ente nuevo =  new Ente(contadorDeIdentidad.incrementAndGet(), nombre, funcion);
        nuevo.teletransportar(0, 0, 0);
        return nuevo;
    }
}