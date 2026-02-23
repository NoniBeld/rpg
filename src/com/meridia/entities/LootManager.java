package com.meridia.entities;

public class LootManager {
    public static String calcularDrop(Entidad victima, double ultimoDano, int golpesRecibidos) {
        double vidaMax = victima.getMasa() + victima.getDensidad(); // Nuestra fórmula de HP
        
        // REGLA 1: Destrozado (Overkill)
        if (ultimoDano >= vidaMax * 1.5) {
            return "Polvo de huesos y vísceras irreconocibles (Basura)";
        }
        
        // REGLA 2: Perfecto (One-Shot exacto)
        if (golpesRecibidos == 1 && ultimoDano >= victima.getVidaActual()) {
            return "Cuerpo íntegro de " + victima.getNombre() + " (Ideal para disecar)";
        }
        
        // REGLA 3: Dañado (Múltiples ataques)
        return "Colmillos y trozos de piel de " + victima.getNombre();
    }
}