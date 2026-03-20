package com.rpg.combate;

import com.rpg.ente.Atributo;

public record AtaqueBase(String nombre, Atributo atributoEscala, float multiplicador) {
    public static final AtaqueBase CABEZAZO = new AtaqueBase("Cabezazo", Atributo.FUERZA, 1.2f);
    public static final AtaqueBase MORDISCO = new AtaqueBase("Mordisco", Atributo.FUERZA, 1.5f);
    public static final AtaqueBase EMBESTIDA = new AtaqueBase("Embestida", Atributo.AGILIDAD, 1.1f);
    
    
    public Atributo escala() {
	
		return this.atributoEscala;
	}
}