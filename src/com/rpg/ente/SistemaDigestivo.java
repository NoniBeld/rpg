package com.rpg.ente;

import com.rpg.ente.Ente;
import herramientas.texto.Narrador;

public interface SistemaDigestivo {
    // Definimos las etapas del proceso
    void ingerir(Ente alimento, Ente organismo);
    void procesar(double delta, Ente organismo);
    void excretar(Ente organismo);
}