package com.combate;

import com.ente.Atributo;

public record MagiaBase(String nombre, Atributo escala, int costeMana, float potencia) {
    public static final MagiaBase BOLA_FUEGO = new MagiaBase("Bola de Fuego", Atributo.INTELIGENCIA, 10, 2.5f);
    public static final MagiaBase CURAR = new MagiaBase("Luz Sanadora", Atributo.ESPIRITU, 15, -1.5f); // Daño negativo = Cura
}