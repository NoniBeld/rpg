package com.ente.mecanica;

import com.herramientas.texto.Narrador;
import java.util.HashMap;
import java.util.Map;

import com.ente.*;

public class SistemaVital {
    private Map<ParteDelCuerpo, Integer> integridadFisica = new HashMap<>();
    private int puntosDeVidaActuales;
    private int puntosDeVidaMaximos;

    public void inicializarAnatomia() {
        for (ParteDelCuerpo parte : ParteDelCuerpo.values()) {
            this.integridadFisica.put(parte, parte.saludBase);
        }
    }

    public int calcularVidaMaxima(Ente dueño) {
        int sumaPartes = integridadFisica.values().stream().mapToInt(Integer::intValue).sum();
        int bonoGenetico = dueño.obtenerValorAtributo(Atributo.CONSTITUCION) * 2; 
        int bonoArmadura = dueño.obtenerValorAtributo(Atributo.RESISTENCIA); 
        return sumaPartes + bonoGenetico + bonoArmadura;
    }
    public void recibirImpactoLocalizado(Ente dueño, int daño, ParteDelCuerpo zona) {
        int saludZona = integridadFisica.getOrDefault(zona, 0);
        int nuevaSaludZona = Math.max(0, saludZona - daño);
        integridadFisica.put(zona, nuevaSaludZona);

        int dañoAlTotal = (int) (daño * zona.multiplicadorDañoAlTotal); 
        this.modificarVida(dueño, dañoAlTotal); 

        if (nuevaSaludZona <= 0) {
            Narrador.obtenerInstancia().narrar("¡La parte [" + zona + "] de " + dueño.obtenerNombre() + " ha sido destrozada!", 20); 
        }
    }

    public void modificarVida(Ente dueño, int cantidad) {
        this.puntosDeVidaActuales -= cantidad; 
        verificarEstadoFisico(dueño);
    }

    private void verificarEstadoFisico(Ente dueño) {
        if (puntosDeVidaActuales < 0 && puntosDeVidaActuales > -100) {
            dueño.hablar("Mi esencia se oscurece... Vida actual: " + puntosDeVidaActuales); 
        } else if (puntosDeVidaActuales <= -100) {
            dueño.hablar("Mi cuerpo no soporta más el vacío. Desvaneciendo...");}
    }
    
    public Map<ParteDelCuerpo, Integer> getIntegridadFisica() { return integridadFisica; }
    public int getVidaActual() { return puntosDeVidaActuales; }


}