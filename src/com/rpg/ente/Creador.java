package com.rpg.ente;

import java.util.concurrent.atomic.AtomicInteger;

import com.rpg.ente.bestiario.PlantillaEnte;
import com.rpg.ente.bestiario.fauna.Humano;
import com.rpg.ente.bestiario.fauna.Orco;
import com.rpg.ente.bestiario.fauna.Slime;

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
 // En Creador.java
    public Ente instanciarDesdePlantilla(PlantillaEnte p) {
        // 1. Creamos el objeto Ente base
        Ente nuevo = crearNuevoEnte(p.nombre(), Funcion.SUJETO);
        
        // 2. Inyectamos los atributos desde la interfaz
//      nuevo.establecerValorAtributo(Atributo., p.());
        nuevo.establecerValorAtributo(Atributo.VIDA, p.vidaBase());
        nuevo.establecerValorAtributo(Atributo.FUERZA, p.fuerza());
        nuevo.establecerValorAtributo(Atributo.AGILIDAD, p.agilidad());
        nuevo.establecerValorAtributo(Atributo.DESTREZA, p.destreza());
        nuevo.establecerValorAtributo(Atributo.INTELIGENCIA, p.inteligencia());
        nuevo.establecerValorAtributo(Atributo.SABIDURIA, p.sabiduria());
        nuevo.establecerValorAtributo(Atributo.CARISMA, p.carisma());
        nuevo.establecerValorAtributo(Atributo.SUERTE, p.suerte());
        nuevo.establecerValorAtributo(Atributo.CRITICO, p.critico());
        nuevo.establecerValorAtributo(Atributo.RESISTENCIA, p.resistencia());
        nuevo.establecerValorAtributo(Atributo.ESPIRITU, p.espiritu());
        nuevo.establecerValorAtributo(Atributo.CONSTITUCION, p.constitucion());
        nuevo.establecerValorAtributo(Atributo.VIDA_MAX, p.vida_max());
        nuevo.establecerValorAtributo(Atributo.MAGIA, p.magia());
        nuevo.cambiarTamaño(p.escala());
        
        // 3. ¡Listo! No importa si es Orco, Humano o Slime.
        return nuevo;
    }
    public void imprimirFichaTecnica(Ente e) {
        System.out.println("\n===== FICHA GENÉTICA: " + e.obtenerNombre() + " =====");
        System.out.println("Fuerza: " + e.obtenerValorAtributo(Atributo.FUERZA) + 
                           " | Agilidad: " + e.obtenerValorAtributo(Atributo.AGILIDAD) + 
                           " | Suerte: " + e.obtenerValorAtributo(Atributo.SUERTE));
        System.out.println("Constitución: " + e.obtenerValorAtributo(Atributo.CONSTITUCION) + 
                           " | Vida Max: " + e.obtenerVidaMax());
        System.out.println("============================================\n");
    }
}