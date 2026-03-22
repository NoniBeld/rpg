package com.rpg.ente;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import java.util.List;

import com.rpg.ente.bestiario.PlantillaEnte;
import com.rpg.ente.bestiario.fauna.Slime;

/**
 * Fábrica encargada de centralizar la creación de entes.
 */
public final class Creador {
    private static Creador instancia;
    private final AtomicInteger contadorDeIdentidad;

    private Creador() {
        this.contadorDeIdentidad = new AtomicInteger(0);
    }

    public static Creador obtenerInstancia() {
        if (instancia == null) {
            instancia = new Creador();
        }
        return instancia;
    }

    public Ente crearNuevoEnte(String nombre, Funcion funcion) {
        Ente nuevo = new Ente(contadorDeIdentidad.incrementAndGet(), nombre, funcion);
        nuevo.teletransportar(0, 0, 0);
        return nuevo;
    }

    public Ente instanciarDesdePlantilla(PlantillaEnte p) {
        Ente nuevo = crearNuevoEnte(p.nombre(), Funcion.SUJETO);
        
        // Inyección de Atributos
        nuevo.establecerValorAtributo(Atributo.VIDA, p.vidaBase());
        nuevo.establecerValorAtributo(Atributo.FUERZA, p.fuerza());
        nuevo.establecerValorAtributo(Atributo.AGILIDAD, p.agilidad());
        nuevo.establecerValorAtributo(Atributo.DESTREZA, p.destreza());
        nuevo.establecerValorAtributo(Atributo.INTELIGENCIA, p.inteligencia());
        nuevo.establecerValorAtributo(Atributo.SABIDURIA, p.sabiduria());
        nuevo.establecerValorAtributo(Atributo.CARISMA, p.carisma());
        nuevo.establecerValorAtributo(Atributo.SUERTE, p.suerte());
        nuevo.establecerValorAtributo(Atributo.CRITICO, p.critico());
        nuevo.establecerValorAtributo(Atributo.RESISTENCIA, p.resistencia());
        nuevo.establecerValorAtributo(Atributo.ESPIRITU, p.espiritu());
        nuevo.establecerValorAtributo(Atributo.CONSTITUCION, p.constitucion());
        nuevo.establecerValorAtributo(Atributo.VIDA_MAX, p.vida_max());
        nuevo.establecerValorAtributo(Atributo.MAGIA, p.magia());
        
        nuevo.cambiarTamaño(p.escala());

        // Configuración de la anatomía según el tipo
        if (p instanceof Slime) {
            this.configurarCuerpo(nuevo, "AMORFO");
        } else {
            this.configurarCuerpo(nuevo, "HUMANOIDE");
        }

        return nuevo;
    }

    public void configurarCuerpo(Ente e, String chasis) {
        // Obtenemos el mapa que vive dentro del componente vital del Ente
        Map<ParteDelCuerpo, Integer> integridad = e.obtenerMapaIntegridad();
        integridad.clear();
        
        switch (chasis.toUpperCase()) {
            case "HUMANOIDE" -> {
                for (ParteDelCuerpo p : List.of(
                        ParteDelCuerpo.CABEZA, ParteDelCuerpo.TORSO, 
                        ParteDelCuerpo.BRAZO_IZQ, ParteDelCuerpo.BRAZO_DER, 
                        ParteDelCuerpo.PIERNA_IZQ, ParteDelCuerpo.PIERNA_DER)) {
                    // IMPORTANTE: Usamos 'integridad' que es el mapa que acabamos de obtener
                    integridad.put(p, p.saludBase);
                }
            }
            case "AMORFO" -> {
                integridad.put(ParteDelCuerpo.NUCLEO, 100);
                integridad.put(ParteDelCuerpo.CUERPO_GELATINOSO, 200);
            }
        }
    }

    public void imprimirFichaTecnica(Ente e) {
        System.out.println("\n===== FICHA GENÉTICA: " + e.obtenerNombre() + " =====");
        System.out.println("Fuerza: " + e.obtenerValorAtributo(Atributo.FUERZA) + 
                           " | Agilidad: " + e.obtenerValorAtributo(Atributo.AGILIDAD) + 
                           " | Suerte: " + e.obtenerValorAtributo(Atributo.SUERTE));
        System.out.println("Constitución: " + e.obtenerValorAtributo(Atributo.CONSTITUCION) + 
                           " | Vida Actual: " + e.obtenerVidaActual());
        System.out.println("============================================\n");
    }
}