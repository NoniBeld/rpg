package com.ente;

public enum Potencia {
    INTEGRO,      // Estado natural
    HAMBRIENTO,   // El sistema digestivo del SUJETO exige combustible
    PROCESANDO,   // El objeto está dentro de un sistema digestivo
    DIGERIDO,     // Masa amorfa (excremento/abono)
    QUEMADO,      // Cenizas y carbón
    SANGRIENTO,   // Restos biológicos (huesos, piel)
    OXIDADO       // Degradación por tiempo/humedad
}