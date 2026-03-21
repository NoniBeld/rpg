package com.rpg.ente.bestiario;

import com.rpg.ente.Tamaño;

public record PlantillaOrganica(
    String especie,
    int vidaBase,
    int fuerzaBase,
    int agilidadBase,
    Tamaño tamañoBase
) {
    public static final PlantillaOrganica HUMANO = new PlantillaOrganica("Humano", 100, 10, 10, Tamaño.MEDIO);
    public static final PlantillaOrganica SLIME = new PlantillaOrganica("Slime", 40, 5, 15, Tamaño.MEDIO);
}