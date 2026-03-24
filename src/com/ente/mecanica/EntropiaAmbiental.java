package com.ente.mecanica;

import com.ente.*;

import com.herramientas.clima.Clima;

public class EntropiaAmbiental {
    public void aplicar(Ente ente, float delta, Clima clima) {
        if (ente.obtenerValorAtributo(Atributo.RESISTENCIA) > 999) return; 

        if (ente.obtenerFuncionActual() == Funcion.ALIMENTO || ente.obtenerNombre().contains("Resto de")) {
            float factorDescomposicion = delta * (clima.temperaturaAmbiente() / 20.0f); 
            ente.modificarTiempoVida(-factorDescomposicion);
            
            if (ente.obtenerTiempoVida() <= 0) {
                ente.transformar(Potencia.DIGERIDO); 
                ente.cambiarNombre("Materia Orgánica Descompuesta de " + ente.obtenerNombre());
            }
        }
    }
}