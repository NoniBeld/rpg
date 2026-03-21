package com.rpg.ente.bestiario.objeto;

import com.rpg.ente.EstadoMateria;
import com.rpg.ente.Funcion;
import com.rpg.ente.Tamaño;
import com.rpg.ente.bestiario.Ente;
import com.rpg.ente.bestiario.PlantillaEnte;

public record Nucleo(String nombre, 	 int vidaBase, 	 int fuerza, 		int agilidad,  int destreza, 
					int inteligencia, 	 int sabiduria,	 int carisma, 		int suerte,    int critico,
					int resistencia, 	 int espiritu,   int constitucion,   int vida_max, int magia, 
					Tamaño escala,       Funcion funcion, EstadoMateria estado_materia) 
	
implements PlantillaEnte {
	
	public static final Ente nucleo = new Ente ("Nucleo", 999, 99, 99, 99, 
													99, 99, 99, 99,99,
													99, 99, 99, 99, 99,
													Tamaño.MEDIO, Funcion.OBJETO, EstadoMateria.SOLIDO );

}