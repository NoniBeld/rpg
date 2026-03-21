package com.rpg.ente.bestiario;

import com.rpg.ente.EstadoMateria;
import com.rpg.ente.EstadoVital;
import com.rpg.ente.Funcion;
import com.rpg.ente.Integridad;
import com.rpg.ente.ParteDelCuerpo;
import com.rpg.ente.Tamaño;
import com.rpg.ente.TipoDeSer;
import com.rpg.ente.Potencia;

public record Ente(String nombre, int vidaBase, int fuerza, int agilidad, int destreza, 
		int inteligencia, int sabiduria, int carisma, int suerte, int critico,
		int resistencia,  int espiritu, int constitucion,  int vida_max, int magia,
		 Tamaño escala, Funcion funcion, EstadoMateria estado_materia, EstadoVital estado_vital. Integridad integridad,
		 Potencia potencia, ParteDelCuerpo cuerpo, Sentido sentido, TipoDeSer ser) 
	
implements PlantillaEnte{
	
	public static final Ente ente = new Ente ("Ente", 999, 99, 99, 99, 
													99, 99, 99, 99,99,
													99, 99, 99, 99, 99,
						Tamaño.MEDIO, Funcion.SUJETO, EstadoMateria.SOLIDO, EstadoVital.VIVO, Integridad.INTACTO, // creo que estoy mal aqui );

}
