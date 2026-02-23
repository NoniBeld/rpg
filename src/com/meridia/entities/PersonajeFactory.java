package com.meridia.entities;

import java.util.Map;

public class PersonajeFactory {

    public static Entidad crearSoul() {
        // Soul: El hermano ladrón robusto (Tanque/Luchador)
        Entidad soul = new Heroe("Soul", 80.0, 1.2); // Más masa y densidad = más vida
        Map<Atributo, Integer> stats = soul.getAtributos();
        
        stats.put(Atributo.FUERZA, 12);
        stats.put(Atributo.RUDEZA, 10);
        stats.put(Atributo.VOLUNTAD, 8);
        stats.put(Atributo.AGILIDAD, 5);
        stats.put(Atributo.DESTREZA, 5);
        stats.put(Atributo.VELOCIDAD, 5);
        stats.put(Atributo.SABIDURIA, 2);
        stats.put(Atributo.INTELIGENCIA, 3);
        // ... (el resto hasta completar 50)
        
        soul.recalcularVida();
        return soul;
    }

    public static Entidad crearLaxir() {
        // Laxir: El hermano ágil (Velocista/Críticos)
        Entidad laxir = new Heroe("Laxir", 65.0, 0.9);
        Map<Atributo, Integer> stats = laxir.getAtributos();
        
        stats.put(Atributo.AGILIDAD, 15);
        stats.put(Atributo.VELOCIDAD, 12);
        stats.put(Atributo.DESTREZA, 10);
        stats.put(Atributo.CRITICO, 8);
        stats.put(Atributo.MOVIMIENTO, 5);
        
        laxir.recalcularVida();
        return laxir;
    }

    public static Entidad crearNalanga() {
        // Nalanga: Psiónico (Control/Utilidad)
        Entidad nalanga = new Heroe("Nalanga", 70.0, 1.0);
        Map<Atributo, Integer> stats = nalanga.getAtributos();
        
        stats.put(Atributo.INTELIGENCIA, 12);
        stats.put(Atributo.CARISMA, 12);
        stats.put(Atributo.SABIDURIA, 10);
        stats.put(Atributo.VOLUNTAD, 10);
        stats.put(Atributo.DESTREZA, 6);
        
        nalanga.recalcularVida();
        return nalanga;
    }

    public static Entidad crearAlejandra() {
        // Alejandra: Pirómana (Daño Mágico Masivo)
        Entidad alejandra = new Heroe("Alejandra", 55.0, 0.85); // Frágil pero poderosa
        Map<Atributo, Integer> stats = alejandra.getAtributos();
        
        stats.put(Atributo.INTELIGENCIA, 15);
        stats.put(Atributo.VOLUNTAD, 12);
        stats.put(Atributo.MOVIMIENTO, 8);
        stats.put(Atributo.CRITICO, 10);
        stats.put(Atributo.SABIDURIA, 5);
        
        alejandra.recalcularVida();
        return alejandra;
    }
}