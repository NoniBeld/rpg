package com.combate;

import com.herramientas.texto.Narrador;
import java.util.Random;

import com.ente.Atributo;
import com.ente.Ente;
import com.ente.Integridad;
import com.ente.ParteDelCuerpo;
import com.ente.Potencia;

/**
 * Clase encargada de procesar las reglas de combate entre dos Entes.
 * Se comporta como una entidad imparcial que calcula daños y críticos.
 */
public final class ArbitroCombate {
    private static final Random azar = new Random();

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
       //defensor.recibirImpacto(dañoFinal);
        
        ParteDelCuerpo zona = calcularZonaImpacto();
        if (zona != null) {
            defensor.recibirImpactoLocalizado(dañoFinal, zona);
        } else {
        	Narrador.obtenerInstancia().narrar("¡PIFIA! " + atacante.obtenerNombre() + " tropieza y su ataque golpea el aire.", 15);            return;
        }
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
    // 1. Cálculo de Acierto (Usando los parámetros atk y def, NO las variables estáticas)
    int desAtacante = atk.obtenerValorAtributo(Atributo.DESTREZA);
    int agiDefensor = def.obtenerValorAtributo(Atributo.AGILIDAD);
    int suerteAtacante = atk.obtenerValorAtributo(Atributo.SUERTE);
    int vidaAnterior = def.obtenerVidaActual();
    // Fórmula de precisión: (Destreza / Agilidad) modificada por la probabilidad de la zona
    float factorPrecision = (float) desAtacante / (agiDefensor > 0 ? agiDefensor : 1);
    float punteriaFinal = parte.probabilidadBase * factorPrecision;

    float azarAcierto = azar.nextFloat(); // 0.0 a 1.0

    // Si la suerte es muy alta, el atacante tiene una segunda oportunidad de acertar
    if (azarAcierto > punteriaFinal && azar.nextInt(100) > suerteAtacante) {
        Narrador.obtenerInstancia().narrar("¡Fallaste! El ataque a " + parte + " fue esquivado por " + def.obtenerNombre(), 20);
        return;
    }

    // 2. Cálculo de Daño con Multiplicador de Anatomía
    // Escalamos con el atributo de la habilidad (Fuerza, Magia, etc.)
    int dañoFinal = (int) (atk.obtenerValorAtributo(habilidad.escala()) * habilidad.multiplicador() * parte.multiplicadorDaño);
    
    // 3. Aplicación de Crítico aleatorio
    if (azar.nextInt(100) < suerteAtacante) {
        dañoFinal *= 2;
        Narrador.obtenerInstancia().narrar("¡GOLPE CRÍTICO en el " + parte + "!", 10);
    }

    // 4. Aplicación del Daño
    Narrador.obtenerInstancia().narrar(atk.obtenerNombre() + " golpea el " + parte + " de " + def.obtenerNombre() + " por " + dañoFinal + " de daño.", 30);
    
    if (dañoFinal >= def.obtenerVidaActual() && (parte == ParteDelCuerpo.OJO || parte == ParteDelCuerpo.CABEZA || parte == ParteDelCuerpo.NUCLEO)) {
        Narrador.obtenerInstancia().narrar("¡EJECUCIÓN PERFECTA! El impacto destruyó un centro vital.", 10);
        def.modificarVidaActual(-dañoFinal); // Esto disparará la lógica de muerte interna
    } else {
        def.recibirImpactoLocalizado(dañoFinal, parte); // Usamos el impacto localizado
    }

}
public static ParteDelCuerpo calcularZonaImpacto() {
    int dado = new java.util.Random().nextInt(20) + 1; // 1 a 20

    if (dado == 20) return ParteDelCuerpo.NUCLEO;   // ¡Crítico vital!
    if (dado == 1)  return null;                   // Pifia: Golpe al aire
    if (dado >= 15) return ParteDelCuerpo.CABEZA;   // Golpe alto
    if (dado >= 8)  return ParteDelCuerpo.TORSO;    // Zona media (más probable)
    if (dado >= 5)  return ParteDelCuerpo.BRAZO_DER; // Extremidades
    return ParteDelCuerpo.PIERNA_IZQ;               // Golpe bajo
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