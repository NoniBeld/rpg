package com.meridia.combat;

import com.meridia.entities.Atributo;
import com.meridia.entities.Entidad;
import java.util.*;

public class CombateManager {
    private final List<Entidad> participantes;
    private final PriorityQueue<EntidadIniciativa> ordenTurnos;
    private final Random dice = new Random();

    // Record auxiliar para manejar el orden
    private record EntidadIniciativa(Entidad entidad, int iniciativa) {}

    public CombateManager(List<Entidad> grupoA, List<Entidad> grupoB) {
        this.participantes = new ArrayList<>();
        this.participantes.addAll(grupoA);
        this.participantes.addAll(grupoB);
        // Orden descendente por iniciativa
        this.ordenTurnos = new PriorityQueue<>(Comparator.comparingInt(EntidadIniciativa::iniciativa).reversed());
    }

    public void iniciarSimulacion() {
        System.out.println("⚔️ ¡Inicia el combate en las calles de Meridia! ⚔️");
        calcularIniciativas();

        while (hayGanador()) {
            EntidadIniciativa turnoActual = ordenTurnos.poll();
            Entidad atacante = turnoActual.entidad();
            
            // Simulación de ataque manual (luego lo haremos con Scanner)
            ejecutarTurno(atacante);
            
            // Re-insertar en la cola para la siguiente ronda si sigue vivo
            ordenTurnos.add(new EntidadIniciativa(atacante, turnoActual.iniciativa()));
        }
    }

    private void calcularIniciativas() {
        ordenTurnos.clear();
        for (Entidad e : participantes) {
            int vel = e.getAtributos().get(Atributo.VELOCIDAD);
            int ini = vel + dice.nextInt(1, 21); // Velocidad + 1d20
            ordenTurnos.add(new EntidadIniciativa(e, ini));
        }
    }

    private void ejecutarTurno(Entidad atacante) {
        // Por ahora, una lógica simple para probar las fórmulas
        System.out.println("\n>>> Turno de: " + atacante.getNombre());
        // Aquí es donde el "maestro" te pide elegir la habilidad en el futuro
    }

    private boolean hayGanador() {
        // Lógica para detenerse si un grupo queda en 0 HP
        return true; 
    }
}