package com.ente;

public enum Tamaño {
    MICROSCOPICO(0.001f, 1000), // 1000x más rápido / Invisible
    MINUSCULO(0.1f, 100),       // Insectos / Solo se ven si buscas
    PEQUEÑO(0.5f, 10),          // Roedores / Se ven a simple vista
    MEDIO(1.0f, 1),             // Humano / Slime estándar
    GRANDE(10.0f, 0.1f),        // Árboles / Edificios
    COLOSAL(100.0f, 0.01f);      // Montañas / Represas

    public final float escalaVisual;
    public final float factorReloj; // Multiplicador de velocidad del tiempo

    Tamaño(float escala, float reloj) {
        this.escalaVisual = escala;
        this.factorReloj = reloj;
    }
}