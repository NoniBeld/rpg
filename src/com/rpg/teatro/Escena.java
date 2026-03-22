package com.rpg.teatro;

import com.rpg.combate.ArbitroCombate;
import com.rpg.combate.AtaqueBase;
import com.rpg.ente.Atributo;
import com.rpg.ente.Ente;
import com.rpg.ente.EstadoVital;
import com.rpg.ente.ParteDelCuerpo;
import com.rpg.ente.Tamaño;
import herramientas.clima.Bioma;
import herramientas.clima.Clima;
import herramientas.texto.Narrador;
import herramientas.tiempo.CalendarioLunar;

import java.util.ArrayList;
import java.util.List;

public final class Escena {
    private String nombre;
    private Bioma bioma;
    private String descripcion;
    private List<Ente> presentes;
    private CalendarioLunar calendario;
    private Clima clima;

    public Escena(String nombre, String descripcion, CalendarioLunar calendario, Clima clima, Bioma bioma) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.calendario = calendario;
        this.clima = clima;
        this.bioma = bioma;
        this.presentes = new ArrayList<>();
    }
    public Escena(String nombre, String descripcion, CalendarioLunar calendario, Clima clima) {
        this(nombre, descripcion, calendario, clima, Bioma.PLANICIE); // Valor por defecto
    }
    public void agregarEnte(Ente ente) {
        if (!presentes.contains(ente)) {
            presentes.add(ente);
        }
    }

    private double calcularDistancia(Ente a, Ente b) {
        if (a == null || b == null) return Double.MAX_VALUE;
        return Math.sqrt(
            Math.pow(b.obtenerPosicionX() - a.obtenerPosicionX(), 2) +
            Math.pow(b.obtenerPosicionY() - a.obtenerPosicionY(), 2) +
            Math.pow(b.obtenerPosicionZ() - a.obtenerPosicionZ(), 2)
        );
    }

    /**
     * Encuentra al Ente VIVO más cercano al actuante que no sea él mismo.
     */
    public Ente buscarObjetivoCercano(Ente actuante) {
        Ente masCercano = null;
        double distanciaMinima = Double.MAX_VALUE;

        for (Ente posibleObjetivo : presentes) {
            if (posibleObjetivo == actuante) continue;
            if (posibleObjetivo.obtenerEstadoVital() != EstadoVital.VIVO) continue;

            double dist = calcularDistancia(actuante, posibleObjetivo);
            if (dist < distanciaMinima) {
                distanciaMinima = dist;
                masCercano = posibleObjetivo;
            }
        }
        return masCercano;
    }

    /**
     * Verifica si quedan al menos dos entes vivos de diferentes naturalezas
     * para continuar la batalla.
     */
    private boolean verificarSupervivenciaFacciones() {
        int vivos = 0;
        for (Ente e : presentes) {
            if (e.obtenerEstadoVital() == EstadoVital.VIVO) vivos++;
        }
        return vivos > 1;
    }

    // --- CICLOS DE JUEGO ---

    public void simularCiclo() {
    	presentes.removeIf(e -> e.obtenerEstadoVital() == EstadoVital.ELIMINADO);
    	// 1. IMPRIMIR RADAR DE ESTADO
        System.out.println("\n--- ESTADO ACTUAL DEL CAMPO ---");
        
        List<Ente> copiaPresentes = new ArrayList<>(presentes);
        
        for (Ente e : copiaPresentes) {
            // Aquí se disparan los errores 92 y 96 si Ente no tiene el método
            if (e.obtenerEstadoVital() == EstadoVital.VIVO) {
                e.decidirAccion(this); // El Ente usa su IA
            }
        }

        // Reporte visual
        for (Ente e : presentes) {
            String estadoLabel = (e.obtenerEstadoVital() == EstadoVital.VIVO) ? "VIVO" : "CAÍDO";
            System.out.println(String.format("[%s] HP: %d/%d | Pos: (%.1f, %.1f) | Estado: %s", 
                e.obtenerNombre(), e.obtenerVidaActual(), e.obtenerVidaMax(), 
                e.obtenerPosicionX(), e.obtenerPosicionZ(), estadoLabel));
        }
        
        for (Ente e : new ArrayList<>(presentes)) { 
            if (e.obtenerEstadoVital() == EstadoVital.VIVO) {
                e.decidirAccion(this); 
            }
        }
        for (Ente e : presentes) {
            String estado = (e.obtenerVidaActual() > 0) ? "VIVO" : "CAÍDO";
            System.out.println(String.format("[%s] HP: %d/%d | Pos: (%.1f, %.1f) | Estado: %s", 
                e.obtenerNombre(), e.obtenerVidaActual(), e.obtenerVidaMax(), 
                e.obtenerPosicionX(), e.obtenerPosicionZ(), estado));
            System.out.println("   Salud Torso: " + e.obtenerSaludDeParte(ParteDelCuerpo.TORSO));
        }
        System.out.println("-------------------------------\n");

        // 2. ORDENAR Y FILTRAR (Solo los vivos actúan)
        presentes.sort((a, b) -> Integer.compare(
            b.obtenerValorAtributo(Atributo.AGILIDAD), 
            a.obtenerValorAtributo(Atributo.AGILIDAD)
        ));
        
        for (Ente actuante : presentes) {
        	if (actuante.obtenerVidaActual() <= 0) continue;

            Ente objetivo = buscarObjetivoCercano(actuante);
            
            // REGLA 2: No se ataca a lo que ya no tiene vida
            if (objetivo == null || objetivo.obtenerVidaActual() <= 0) continue;
            double distancia = calcularDistancia(actuante, objetivo);

            // Umbral de ataque (1.5 unidades de distancia para cuerpo a cuerpo)
            if (distancia <= 1.5) {
                AtaqueBase habilidad = (actuante.obtenerValorAtributo(Atributo.MAGIA) > 20) 
                                       ? AtaqueBase.EXPLOSION_ARCANA : AtaqueBase.EMBESTIDA;
                
                ParteDelCuerpo zona = (actuante.obtenerValorAtributo(Atributo.INTELIGENCIA) > 15)
                                      ? ParteDelCuerpo.NUCLEO : ParteDelCuerpo.TORSO;

                ArbitroCombate.procesarAtaqueDirigido(actuante, objetivo, habilidad, zona);
            } else {
                // Movimiento congruente hacia el objetivo
                actuante.avanzarHacia(objetivo.obtenerPosicionX(), objetivo.obtenerPosicionY(), objetivo.obtenerPosicionZ());
                Narrador.obtenerInstancia().narrar(actuante.obtenerNombre() + " avanza hacia " + objetivo.obtenerNombre() + " [Dist: " + String.format("%.2f", distancia) + "]", 10);
            }
        }
    }

    public void ejecutarBatallaReal() {
        Narrador n = Narrador.obtenerInstancia();
        int ciclo = 1;
        while (verificarSupervivenciaFacciones() && ciclo <= 50) {
            n.narrar("\n--- CICLO ESPACIAL " + ciclo + " ---", 10);
            simularCiclo();
            ciclo++;
        }
        n.narrar("\n=== SIMULACIÓN FINALIZADA ===", 20);
    }

    // --- GETTERS Y OTROS ---
    public String obtenerNombre() { return this.nombre; }
    public List<Ente> obtenerPresentes() { return this.presentes; }

    /**
     * Busca al ente vivo con la menor cantidad de vida actual.
     * Ideal para IAs depredadoras o alineamientos malignos.
     */
    public Ente obtenerMasDebil() {
        return presentes.stream()
            .filter(e -> e.obtenerEstadoVital() == EstadoVital.VIVO) // Solo los que respiran
            .min((e1, e2) -> Integer.compare(e1.obtenerVidaActual(), e2.obtenerVidaActual()))
            .orElse(null); // Si no hay nadie, devuelve null
    }

    /**
     * Elimina físicamente a un ente de la simulación.
     * Se usa cuando un cuerpo es totalmente consumido o limpiado por el sistema.
     */
    public void removerEnte(Ente objetivo) {
        if (objetivo != null && presentes.contains(objetivo)) {
            presentes.remove(objetivo);
            Narrador.obtenerInstancia().narrar(
                String.format(">>> [SISTEMA]: %s ha sido removido de la existencia.", objetivo.obtenerNombre()), 
                10
            );
        }
    }
}