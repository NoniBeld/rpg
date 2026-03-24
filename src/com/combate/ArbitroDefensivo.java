package com.combate;

import com.ente.Atributo;
import com.ente.Ente;

import com.herramientas.texto.Narrador;

public final class ArbitroDefensivo {
    
    public static int mitigarDaño(Ente defensor, int dañoEntrante, DefensaBase defensa) {
        int resistenciaActual = defensor.obtenerValorAtributo(Atributo.RESISTENCIA);
        int costeEsfuerzo = 5; // Coste base por bloquear

        if (resistenciaActual >= costeEsfuerzo) {
            // Bloqueo exitoso: Reducimos el daño según la mitigación de la defensa
            int dañoMitigado = (int) (dañoEntrante * (1.0f - defensa.mitigacion()));
            
            // Consumimos resistencia
            defensor.establecerValorAtributo(Atributo.RESISTENCIA, resistenciaActual - costeEsfuerzo);
            
            Narrador.obtenerInstancia().narrar(defensor.obtenerNombre() + " bloquea con " + defensa.nombre() + " (Resistencia restante: " + (resistenciaActual - costeEsfuerzo) + ")", 20);
            return dañoMitigado;
        } else {
            Narrador.obtenerInstancia().narrar("¡" + defensor.obtenerNombre() + " está demasiado cansado para defenderse!", 10);
            return dañoEntrante; // Recibe el daño completo
        }
    }
}