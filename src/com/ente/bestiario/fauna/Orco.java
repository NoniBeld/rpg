package com.ente.bestiario.fauna;

import com.ente.Tamaño;
import com.ente.bestiario.PlantillaEnte;

public record Orco(String nombre, int vidaBase, int fuerza, int agilidad, int destreza, 
		int inteligencia, int sabiduria, int carisma, int suerte, int critico,
		int resistencia,  int espiritu, int constitucion,  int vida_max, 
		int magia, Tamaño escala) 
implements PlantillaEnte {
    public static final Orco Og = new Orco("Og", 100, 20, 20, 20, 
												10, 10, 10, 10,10,
												20, 10, 20, 200,
												10,Tamaño.MEDIO);

}