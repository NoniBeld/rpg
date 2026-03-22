package com.rpg.ente;

/**
 * Define las partes físicas que pueden recibir daño o equipo.
 */
//En ParteDelCuerpo.java (Añadiendo lógica)
public enum ParteDelCuerpo {
	
	NUCLEO(5, 10.0f, 0.1f, 5.0f, 0.0f),     // El punto vital (One-Shot Kill) 0.0f en teoria seria infinito
	FALANJE(20, 0.5f, 0.7f, 0.8f, 1.5f),    // Extensiones viscosas
	TORSO(100, 1.0f, 1.0f, 1.0f, 8.5f),
	CUERPO(100, 1.0f, 1.0f, 1.0f, 12.5f),    // Fácil de dar, daño normal
	CABEZA(30, 2.5f, 0.4f, 2.5f, 15.0f),    // Difícil de dar (40% acierto), mucho daño
	OJO(5, 10.0f, 0.1f, 5.0f, 1.0f),       // Casi imposible (10% acierto), muerte instantánea
	EXTREMIDAD(50, 0.5f, 0.7f, 0.8f, 16.0f), 
	BRAZO_IZQ (20, 0.5f, 0.7f, 0.8f, 1.5f),
	PIERNA_IZQ(20, 0.5f, 0.7f, 0.8f, 1.5f), // Daño reducido, pero afecta movimiento
	PIERNA_DER(20, 0.5f, 0.7f, 0.8f, 1.5f),
	BRAZO_DER(20, 0.5f, 0.7f, 0.8f, 1.5f), 
	CUERPO_GELATINOSO(100, 1.0f, 1.0f, 1.0f, 12.5f); 


	public final int saludBase;
    public final float multiplicadorDañoAlTotal;
    public final float probabilidadBase;
    public final float multiplicadorDaño;
    public final float tiempoDescomposicion;

 ParteDelCuerpo(int salud, float multi, float prob, float multi1, float tdescom) {
	 this.saludBase = salud;
     this.multiplicadorDañoAlTotal = multi1;
     this.probabilidadBase = prob;
     this.multiplicadorDaño = multi1;
     this.tiempoDescomposicion = tdescom;
 }
}