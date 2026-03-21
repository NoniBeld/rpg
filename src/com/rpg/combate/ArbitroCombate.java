package com.rpg.combate;

import com.rpg.ente.Ente;
import com.rpg.ente.Integridad;
import com.rpg.ente.ParteDelCuerpo;
import com.rpg.ente.Potencia;
import com.rpg.ente.Atributo;
import herramientas.texto.Narrador;
import java.util.Random;

/**
 * Clase encargada de procesar las reglas de combate entre dos Entes.
 * Se comporta como una entidad imparcial que calcula daños y críticos.
 */
public final class ArbitroCombate {
    private static final Random azar = new Random();
	private static Ente atacante;
	private static Ente defensor;

    public static void procesarDuelo(Ente atacante, Ente defensor, AtaqueBase ataque) {
        Narrador n = Narrador.obtenerInstancia();
        
        // 1. OBTENCIÓN DE DATOS BASE
        // Sacamos el valor del atributo con el que escala el ataque (ej: Fuerza o Agilidad)
        int valorAtributo = atacante.obtenerValorAtributo(ataque.escala());
        // Sacamos la suerte del atacante para el cálculo del crítico
        int suerteAtacante = atacante.obtenerValorAtributo(Atributo.SUERTE);
        
        // 2. CÁLCULO DE DAÑO BASE
        // Multiplicamos el atributo por la potencia del ataque
        float dañoBase = valorAtributo * ataque.multiplicador();
        
        // 3. DETERMINACIÓN DE GOLPE CRÍTICO
        // Si un número aleatorio entre 0-99 es menor que la suerte del atacante, es crítico
        boolean esCritico = (azar.nextInt(100) < suerteAtacante);
        float multiplicadorFinal = esCritico ? 2.0f : 1.0f; // El crítico duplica el daño
        
        // 4. CÁLCULO DE VARIACIÓN (SUERTE MOMENTÁNEA)
        // Añadimos un pequeño extra aleatorio (0 a 4) para que el daño no sea siempre idéntico
        int variacion = azar.nextInt(5);
        
        // 5. RESULTADO FINAL
        // Daño total = (Base * multiplicador de crítico) + variación de suerte
        int dañoFinal = (int) (dañoBase * multiplicadorFinal) + variacion;

        // 6. NARRACIÓN DEL EVENTO
        n.narrar(String.format("\n¡%s lanza un %s contra %s!", 
            atacante.obtenerNombre(), ataque.nombre(), defensor.obtenerNombre()), 30);
        
        if (esCritico) {
            n.narrar("¡GOLPE CRÍTICO! El impacto ha sido devastador.", 10);
        }
        
        // 7. APLICACIÓN DEL IMPACTO
        // Restamos la vida al defensor
        defensor.recibirImpacto(dañoFinal);
        
        n.narrar(String.format("%s recibe %d de daño. Vida restante: %d", 
            defensor.obtenerNombre(), dañoFinal, defensor.obtenerVidaActual()), 40);
        
        // 8. VERIFICACIÓN DE DERROTA
        // Si la vida llega a 0 o menos, notificamos la caída
        if (defensor.obtenerVidaActual() <= 0) {
            n.narrar("¡" + defensor.obtenerNombre() + " ha sido derrotado y su materia está lista para ser procesada!", 20);
            // Aquí es donde el atacante podría decidir 'ingerir' al derrotado
        }
    }
    public static void evaluarEstadoFinal(Ente defensor, int ultimoDaño) {
        int vidaMax = defensor.obtenerValorAtributo(Atributo.VIDA_MAX); // Necesitamos este getter
        float porcentajeDaño = (float) ultimoDaño / vidaMax;
        
        Integridad resultado;

        if (porcentajeDaño >= 1.0f) {
            resultado = Integridad.PULVERIZADO;
            defensor.cambiarNombre("Polvo de " + defensor.obtenerNombre());
            defensor.transformar(Potencia.QUEMADO); 
        } else if (porcentajeDaño >= 0.75f) {
            resultado = Integridad.DESTROZADO;
            defensor.cambiarNombre("Restos descuartizados de " + defensor.obtenerNombre());
            defensor.transformar(Potencia.SANGRIENTO);
        } else if (ultimoDaño == defensor.obtenerVidaActual() + ultimoDaño) { 
            // Si fue un solo golpe preciso (suponiendo lógica de primer golpe)
            resultado = Integridad.INTACTO;
        } else {
            resultado = Integridad.MAGULLADO;
        }

        Narrador.obtenerInstancia().narrar("Estado del cuerpo: " + resultado, 20);
    }
    public static void procesarAtaqueDirigido(Ente atk, Ente def, AtaqueBase habilidad, ParteDelCuerpo parte) {
        // 1. Cálculo de Acierto
    	int des = atacante.obtenerValorAtributo(Atributo.DESTREZA);
        int agi = defensor.obtenerValorAtributo(Atributo.AGILIDAD);
        int suerte = atacante.obtenerValorAtributo(Atributo.SUERTE);
        
        
        float azarAcierto = new java.util.Random().nextFloat(); // 0.0 a 1.0
        float punteria = (atk.obtenerValorAtributo(Atributo.AGILIDAD) / 100.0f) + parte.probabilidadBase;

        if (azarAcierto > punteria) {
            Narrador.obtenerInstancia().narrar("¡Fallaste! El ataque a " + parte + " fue demasiado ambicioso.", 20);
            return;
        }

        // 2. Cálculo de Daño con Multiplicador de Anatomía
        int dañoFinal = (int) (atk.obtenerValorAtributo(habilidad.escala()) * habilidad.multiplicador() * parte.multiplicadorDaño);
        
        // 3. Verificación de ONE-SHOT (Si el daño supera la vida actual de un solo golpe en punto vital)
        if (dañoFinal >= def.obtenerVidaActual() && (parte == ParteDelCuerpo.OJO || parte == ParteDelCuerpo.CABEZA)) {
            Narrador.obtenerInstancia().narrar("¡EJECUCIÓN PERFECTA! Un solo golpe al " + parte + " terminó el duelo.", 10);
            def.morir(Integridad.INTACTO); // El cuerpo queda perfecto para loot
        } else {
            def.recibirImpacto(dañoFinal);
            if (def.obtenerVidaActual() <= 0) def.morir(Integridad.MAGULLADO);
        }
    }
    
    public void reportarEstadoEquipo(Grupo equipo) {
        for (Ente integrante : equipo.integrantes()) {
            if (integrante.obtenerVidaActual() < 20) {
                Narrador.obtenerInstancia().narrar(
                    "[SENTIDO OIDO]: Escuchas la respiración agitada de " + integrante.obtenerNombre(), 
                    50
                );
            }
        }
    }
}