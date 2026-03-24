package com.ente;

public enum Integridad {
    INTACTO,      // Muerte limpia (punto débil) -> Piel y órganos aprovechables.
    MAGULLADO,    // Muerte por varios golpes -> Carne dañada.
    DESTROZADO,   // Muerte violenta (>75% vida) -> Solo huesos o vísceras.
    PULVERIZADO   // Daño excesivo (>100% vida) -> Solo polvo o cenizas.
}