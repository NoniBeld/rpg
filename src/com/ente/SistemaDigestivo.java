package com.ente;

import com.ente.Ente;

import com.herramientas.texto.Narrador;

public interface SistemaDigestivo {
    // Definimos las etapas del proceso
    void ingerir(Ente alimento, Ente organismo);
    void procesar(double delta, Ente organismo);
    void excretar(Ente organismo);
}