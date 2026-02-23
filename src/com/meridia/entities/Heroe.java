package com.meridia.entities;

import java.util.Random;

public class Heroe extends Entidad {

    public Heroe(String nombre, double masa, double densidad) {
        super(nombre, masa, densidad);
    }

public double calcularDano(int tipoAccion) {
    if (tipoAccion == 1) { // ESPECIAL
        // Verificamos si la habilidad está lista
        if (this.turnosParaHabilidad > 0) {
            System.out.println("¡" + nombre + " aún está canalizando energía! (Faltan " + turnosParaHabilidad + " turnos)");
            return 0;
        }

        // Si se usa, se activa el enfriamiento/carga según la clase
        double dano = 0;
        switch (this.nombre) {
            case "Soul" -> {
                dano = (getAtributos().get(Atributo.FUERZA) + densidad) * nivel;
                this.turnosParaHabilidad = 0; // Soul puede usarla seguido
            }
            case "Alejandra" -> {
                dano = (getAtributos().get(Atributo.INTELIGENCIA) + 10) * nivel;
                this.turnosParaHabilidad = 1; // Alejandra espera 1 turno
            }
            case "Nalanga" -> {
                dano = (getAtributos().get(Atributo.INTELIGENCIA) + masa) * nivel;
                this.turnosParaHabilidad = 3; // El gran golpe de Nalanga requiere 3 turnos
            }
            case "Laxir" -> {
                dano = (getAtributos().get(Atributo.FUERZA) + 10) * nivel;
                this.turnosParaHabilidad = 0;
            }
        }
        return dano;
    } else {
        return (getAtributos().get(Atributo.FUERZA) + 10) * 0.5;
    }
}

    @Override protected void gestionarSubidaAtributos() {}
    @Override protected void gestionarNuevaHabilidad() {}
    
 // En Heroe.java
    private final Random dado = new Random();

    public double realizarAtaque(int tipoAccion, Entidad objetivo) {
        int tirada = dado.nextInt(20) + 1; // 1 a 20
        
        if (tirada == 1) {
            System.out.println("¡FALLO CRÍTICO! " + this.nombre + " tropezó con su propio pan mohoso.");
            return 0;
        }

        double danoBase = calcularDano(tipoAccion);

        if (tirada == 20) {
            System.out.println("¡GOLPE CRÍTICO! El ataque de " + this.nombre + " fue devastador.");
            danoBase *= 2; // Doble daño
        }

        return danoBase;
    }
}