package com.rpg.combate;

import com.rpg.ente.Atributo;

//Record para la Defensa: ¿Qué atributo me protege y cuánto reduce?
public record DefensaBase(String nombre, Atributo escala, float mitigacion) {
 public static final DefensaBase PIEL_DURA = new DefensaBase("Piel Dura", Atributo.RESISTENCIA, 0.2f);
 public static final DefensaBase ESCUDO = new DefensaBase("Bloqueo", Atributo.FUERZA, 0.5f);
}