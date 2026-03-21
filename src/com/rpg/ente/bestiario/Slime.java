package com.rpg.ente.bestiario;

import com.rpg.ente.Funcion;
import com.rpg.ente.Tamaño;

/**
 * Cada Record aquí actúa como un "molde" genético.
 * Si alguien quiere un nuevo monstruo, solo añade un Record.
 */
public record Slime(
    String nombre, 
    int vidaBase, 
    int fuerza, 
    int agilidad, 
    Tamaño escala
) {
    public static final Slime SLIME_FRESA = new Slime("Slime Fresa", 50, 5, 12, Tamaño.MEDIO);
    public static final Slime MICRO_BASURERO = new Slime("Micro-Slime", 1, 1, 50, Tamaño.MICROSCOPICO);
}