package com.rpg.ente.bestiario.objeto.arma;

import com.rpg.ente.Tamaño;
import com.rpg.ente.bestiario.Ente;
import com.rpg.ente.bestiario.PlantillaEnte;

public record Hacha(String nombre, int vidaBase, int fuerza, int agilidad, int destreza, 
		int inteligencia, int sabiduria, int carisma, int suerte, int critico,
		int resistencia,  int espiritu, int constitucion,  int vida_max, int magia,
		 Tamaño escala) 
	
implements PlantillaEnte{
public static final Ente Hacha  = new Ente ("Hacha", 100, 20, 1, 1, 
													1, 1, 1, 1, 2,
													60, 1, 60, 130, 1,
													Tamaño.PEQUEÑO);
}
