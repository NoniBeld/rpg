package com.rpg.ente.bestiario.fauna;

import com.rpg.ente.Tamaño;
import com.rpg.ente.bestiario.PlantillaEnte;

public record Humano(String nombre, int vidaBase, int fuerza, int agilidad, int destreza, 
		int inteligencia, int sabiduria, int carisma, int suerte, int critico,
		int resistencia,  int espiritu, int constitucion,  int vida_max, 
		int magia, Tamaño escala) 
implements PlantillaEnte{
    public static final Humano Adan = new Humano("Adan", 80, 10, 10, 10, 
														40, 20, 20, 10,10,
														10, 50, 10, 150,
														20, Tamaño.MEDIO);

}