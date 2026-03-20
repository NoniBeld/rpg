package com.rpg.ente;

/**
 * Define las partes físicas que pueden recibir daño o equipo.
 */
//En ParteDelCuerpo.java (Añadiendo lógica)
public enum ParteDelCuerpo {
 CUERPO(100, 1.0f, 1.0f, 1.0f),    // Fácil de dar, daño normal
 CABEZA(30, 2.5f, 0.4f, 2.5f),    // Difícil de dar (40% acierto), mucho daño
 OJO(5, 10.0f, 0.1f, 5.0f),       // Casi imposible (10% acierto), muerte instantánea
 EXTREMIDAD(50, 0.5f, 0.7f, 0.8f); // Daño reducido, pero afecta movimiento

	public final int saludBase;
    public final float multiplicadorDañoAlTotal;
 public final float probabilidadBase;
 public final float multiplicadorDaño;

 ParteDelCuerpo(int salud, float multi, float prob, float multi1) {
	 this.saludBase = salud;
     this.multiplicadorDañoAlTotal = multi1;
     this.probabilidadBase = prob;
     this.multiplicadorDaño = multi1;
 }
}