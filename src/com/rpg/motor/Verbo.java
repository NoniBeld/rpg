package com.rpg.motor;

public enum Verbo {

	COMER, LANZAR, GURDAR,MIRAR, CANALIZAR, NARRAR, STATS, SALIR;
	
		//Metodo para convertir texto a Verbo de forma segura
	public static Verbo desdeCadena(String texto) {
		try {
			return Verbo.valueOf(texto.toUpperCase());
		}catch (IllegalArgumentException e) {
			return null;
		}
	}
}
