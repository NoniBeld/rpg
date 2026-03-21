package com.rpg.ente.bestiario.elemento;

import com.rpg.ente.Tamaño;
import com.rpg.ente.bestiario.Ente;
import com.rpg.ente.bestiario.PlantillaEnte;

public record Hidrogeno(String nombre, int vidaBase, int fuerza, int agilidad, int destreza, 
		int inteligencia, int sabiduria, int carisma, int suerte, int critico,
		int resistencia,  int espiritu, int constitucion,  int vida_max, 
		int magia, Tamaño escala) 
	implements PlantillaEnte{
	public static final Ente hidrogeno  = new Ente ("Hidrogeno", 1, 1, 1, 1, 
														1, 1, 1, 1, 1,
														1, 1, 1, 1,
														1,Tamaño.MICROSCOPICO);

}
