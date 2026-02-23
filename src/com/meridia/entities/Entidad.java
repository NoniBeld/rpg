package com.meridia.entities;

import java.util.*;

public abstract class Entidad {
    protected String nombre;
    protected int nivel = 1;
    protected double masa;
    protected double densidad;
    protected double vidaMaxima;
    protected double vidaActual;
    protected int turnosParaHabilidad = 0;
    protected double escudoEnergia = 0; // Para el muro del Ser Gris
    
    protected final Map<Atributo, Integer> atributos = new EnumMap<>(Atributo.class);
    protected final Map<SlotAnatomico, Optional<Item>> equipo = new EnumMap<>(SlotAnatomico.class);
    private final Map<String, Integer> bestiario = new HashMap<>();
    
    public Entidad(String nombre, double masa, double densidad) {
        this.nombre = nombre;
        this.masa = masa;
        this.densidad = densidad;
        inicializarAtributos();
    }

    private void inicializarAtributos() {
        for (Atributo a : Atributo.values()) {
            atributos.put(a, 1);
        }
    }

    public void recalcularVida() {
        this.vidaMaxima = (this.masa + this.densidad) * this.nivel;
        this.vidaActual = this.vidaMaxima;
    }

    // --- VERSIÓN UNIFICADA DE RECIBIR DAÑO ---
    public void recibirDano(double cantidad) {
        // Primero verificamos si hay escudo del Ser Gris
        if (this.escudoEnergia > 0) {
            if (cantidad <= escudoEnergia) {
                escudoEnergia -= cantidad;
                System.out.println("¡El muro neutralizó el ataque hacia " + nombre + " por completo!");
                return;
            } else {
                cantidad -= escudoEnergia;
                escudoEnergia = 0;
                System.out.println("¡El muro de " + nombre + " se ha roto, pero absorbió parte del daño!");
            }
        }

        // El daño restante (o total si no hay escudo) se resta de la vida
        this.vidaActual -= cantidad;
        if (this.vidaActual < 0) this.vidaActual = 0;
    }

    public void aplicarEscudo(double cantidad) {
        this.escudoEnergia = cantidad;
        System.out.println("¡Un muro de energía neutral protege a " + this.nombre + "!");
    }

    public void reducirEspera() {
        if (turnosParaHabilidad > 0) turnosParaHabilidad--;
    }

    public void registrarMuerte(String nombreEnemigo) {
        int muertes = bestiario.getOrDefault(nombreEnemigo, 0) + 1;
        bestiario.put(nombreEnemigo, muertes);
        
        if (muertes == 10) {
            System.out.println("¡" + this.nombre + " ha dominado la anatomía de: " + nombreEnemigo + "!");
            System.out.println("Efecto: Ahora puedes ver el HP exacto para un drop perfecto.");
        }
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public double getVidaActual() { return vidaActual; }
    public double getVidaMaxima() { return vidaMaxima; }
    public int getNivel() { return nivel; }
    public double getMasa() { return masa; }
    public double getDensidad() { return densidad; }
    public int getEspera() { return turnosParaHabilidad; }
    public void setEspera(int turnos) { this.turnosParaHabilidad = turnos; }
    public Map<Atributo, Integer> getAtributos() { return atributos; }

    public void equipar(SlotAnatomico slot, Item item) {
        equipo.put(slot, Optional.of(item));
        System.out.println(nombre + " se ha equipado " + item.nombre() + " en " + slot);
    }

    public abstract double calcularDano(int tipoAccion);
    protected abstract void gestionarSubidaAtributos();
    protected abstract void gestionarNuevaHabilidad();
}