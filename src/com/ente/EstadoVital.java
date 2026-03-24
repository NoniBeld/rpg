package com.ente;

public enum EstadoVital {
    VIVO,      // Vida > 0
    AGONIZANTE, // Vida entre 0 y -10 (opcional)
    MUERTO,    // Vida = 0 (punto de quiebre)
    REANIMADO, // Vida < 0 (Muertos vivientes, espectros)
    INEXISTENTE, // Vida tan baja que el cuerpo se desintegra
    CAÍDO, ELIMINADO
}