package com.ente;

/**
 * Define la esencia funcional y el nivel de consciencia del Ente.
 */
public enum Funcion { 
    SUJETO,      // Ser pensante, razonante, con voluntad y movimiento (Jugador, NPCs).
    OBJETO,      // Ente inanimado, materia pasiva.
    ALIMENTO,    // Materia consumible que transfiere sus atributos al SUJETO.
    ARMA,        // Extensión del SUJETO para interactuar violentamente con la materia.
    CONTENEDOR,  // Ente capaz de albergar otros entes en su espacio interno.
    CANALIZADOR, // Ente que fluye energía (mágica o física).
    RECEPTOR,    // Ente diseñado para recibir señales o impactos.
    TRANSMISOR,  // Ente que emite información o energía al mundo.
    LOCALIZADOR,  // Ente con consciencia espacial aumentada.
    MIRAR
}