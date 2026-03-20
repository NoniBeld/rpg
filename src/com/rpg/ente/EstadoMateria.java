package com.rpg.ente;
/**
 * Define si el ente es tangible, gaseoso, etc. 
 * Esto determinará si se renderiza en 2D/3D o si solo existe en "modo texto".
 */
public enum EstadoMateria {
    SOLIDO, LIQUIDO, GASEOSO, PLASMATICO, ETÉREO
}