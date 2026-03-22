package com.rpg.ente.bestiario.objeto.arma;

import com.rpg.ente.Tamaño;
import com.rpg.ente.bestiario.Ente;
import com.rpg.ente.bestiario.PlantillaEnte;

public record Espada(String nombre, int vidaBase, int fuerza, int agilidad, int destreza, 
		int inteligencia, int sabiduria, int carisma, int suerte, int critico,
		int resistencia,  int espiritu, int constitucion,  int vida_max, int magia,
		 Tamaño escala) 
	
implements PlantillaEnte{
public static final Ente Espada  = new Ente ("Espada", 100, 10, 1, 1, 
													1, 1, 1, 1, 2,
													50, 1, 50, 150, 1,
													Tamaño.PEQUEÑO);

}
