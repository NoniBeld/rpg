package com.meridia.entities;

public class EnemigoFactory {

    public static Entidad crearRataGigante() {
        // Nombre, Masa, Densidad
        Entidad rata = new Heroe("Rata Gigante", 5.0, 0.8); 
        rata.getAtributos().put(Atributo.FUERZA, 3);
        rata.getAtributos().put(Atributo.DESTREZA, 12); 
        rata.getAtributos().put(Atributo.VELOCIDAD, 15);
        
        // CORRECCIÓN: Usar el nombre del método unificado
        rata.recalcularVida(); 
        
        return rata;
    }
    
    public static Entidad crearMurcielago() {
        Entidad m = new Heroe("Murciélago", 2.0, 0.5); // Muy poca vida
        m.getAtributos().put(Atributo.VELOCIDAD, 20); // Muy rápidos, difíciles de golpear
        m.recalcularVida();
        return m;
    }

    public static Entidad crearLoboGris() {
        Entidad l = new Heroe("Lobo Gris", 15.0, 1.2); // Más tanque
        l.getAtributos().put(Atributo.FUERZA, 8);
        l.getAtributos().put(Atributo.DESTREZA, 10);
        l.recalcularVida();
        return l;
    }
}