package com.ente.bestiario.objeto;


import com.ente.Tamaño;
import com.ente.bestiario.Ente;
import com.ente.bestiario.PlantillaEnte;

public record Nucleo(String nombre, 	 int vidaBase, 	 int fuerza, 		int agilidad,  int destreza, 
					int inteligencia, 	 int sabiduria,	 int carisma, 		int suerte,    int critico,
					int resistencia, 	 int espiritu,   int constitucion,   int vida_max, int magia, 
					Tamaño escala) 
	
implements PlantillaEnte {
	
	public static final Ente nucleo = new Ente ("Nucleo", 10, 1, 1, 1, 
													1, 1, 1, 1,1,
													1, 1, 1, 1, 1,
													Tamaño.PEQUEÑO );
	public static final Ente corazon = new Ente ("Corazon", 10, 1, 1, 1, 
															1, 1, 1, 1,1,
															1, 1, 1, 1, 1,
															Tamaño.PEQUEÑO );


}