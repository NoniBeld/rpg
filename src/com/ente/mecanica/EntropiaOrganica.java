package com.ente.mecanica;

import com.ente.Atributo;
import com.ente.Ente;
import com.ente.Funcion;
import com.ente.Potencia;

public class EntropiaOrganica {
    
    public void aplicar(Ente ente, float delta, float tempAmbiente) {
        // Si el objeto es legendario o muy resistente, no se pudre
        if (ente.obtenerValorAtributo(Atributo.RESISTENCIA) > 999) return;

        switch (ente.obtenerFuncionActual()) {
            case ALIMENTO -> procesarPudricion(ente, delta, tempAmbiente);
            case ARMA, CONTENEDOR -> procesarDesgaste(ente);
            default -> {} 
        }
    }

    private void procesarPudricion(Ente ente, float delta, float temp) {
        // El calor acelera la descomposición
        float factorClima = temp / 20.0f; 
        ente.modificarTiempoVida(-delta * factorClima);

        if (ente.obtenerTiempoVida() <= 0) {
            ente.transformar(Potencia.DIGERIDO);
            ente.cambiarNombre("Materia Orgánica Descompuesta de " + ente.obtenerNombre());
            ente.cambiarFuncion(Funcion.ALIMENTO); // Se vuelve abono
        }
    }
    
    private void procesarDesgaste(Ente ente) {
        // Aquí podrías restar resistencia si se usa mucho el arma
        if (ente.obtenerValorAtributo(Atributo.RESISTENCIA) <= 0) {
            ente.cambiarNombre(ente.obtenerNombre() + " (Roto)");
        }
    }
}