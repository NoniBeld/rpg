package com.rpg.ente.bestiario.flora;

import com.rpg.ente.Tamaño;
import com.rpg.ente.bestiario.Ente;
import com.rpg.ente.bestiario.PlantillaEnte;

public record Musgo(String nombre, int vidaBase, int fuerza, int agilidad, int destreza, 
		int inteligencia, int sabiduria, int carisma, int suerte, int critico,
		int resistencia,  int espiritu, int constitucion,  int vida_max, 
		int magia, Tamaño escala) 
	implements PlantillaEnte{
	public static final Ente musgo = new Ente ("Musgo", 1, 1, 1, 1, 
													1, 1, 1, 1, 1,
													1, 1, 1, 1,
													1,Tamaño.MICROSCOPICO);

}
