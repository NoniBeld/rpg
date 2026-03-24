package com.ente.bestiario;

import com.ente.Tamaño;
import com.ente.TipoDeSer;

public record Ente(String nombre, int vidaBase, int fuerza, int agilidad, int destreza, 
		int inteligencia, int sabiduria, int carisma, int suerte, int critico,
		int resistencia,  int espiritu, int constitucion,  int vida_max, int magia,
		 Tamaño escala) 
	
implements PlantillaEnte{
	
	public static final Ente ente = new Ente ("Ente", 999, 99, 99, 99, 
													99, 99, 99, 99,99,
													99, 99, 99, 99, 99,
													Tamaño.MEDIO);

}
