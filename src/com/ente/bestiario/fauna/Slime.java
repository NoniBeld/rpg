package com.ente.bestiario.fauna;

import com.ente.Funcion;
import com.ente.Tamaño;
import com.ente.bestiario.PlantillaEnte;

/**
 * Cada Record aquí actúa como un "molde" genético.
 * Si alguien quiere un nuevo monstruo, solo añade un Record.
 */
public record Slime(String nombre, int vidaBase, int fuerza, int agilidad, int destreza, 
					int inteligencia, int sabiduria, int carisma, int suerte, int critico,
					int resistencia,  int espiritu, int constitucion,  int vida_max, 
					int magia, Tamaño escala) 
implements PlantillaEnte {
    public static final Slime SLIME_FRESA = new Slime("Slime Fresa", 50, 10, 10, 10, 
																	10, 10, 10, 10, 10,
																	20, 10, 30, 200,
																	10, Tamaño.MEDIO);
    public static final Slime SLIME_MORA = new Slime("Slime Mora",  50, 20, 20, 20, 
    																10, 10, 10, 10, 10,
    																10, 10, 10, 200,
    																10, Tamaño.MEDIO);
    public static final Slime SLIME_LIMON = new Slime("Slime Limon", 50, 10, 10, 10, 
    																10, 10, 10, 10, 10,
    																10, 10, 10, 200,
    																30, Tamaño.MEDIO);
    public static final Slime MICRO_BASURERO = new Slime("Micro-Slime", 50, 10, 10, 10, 
    																10, 10, 10, 10, 10,
    																10, 10, 10, 220,
    																10, Tamaño.MEDIO);
}