package herramientas.geometria.volumen;

import com.rpg.ente.Ente;

public record EsferaDeColision(float radio) {
    
    /**
     * Determina si un Ente ha entrado en el área de efecto (ej. una explosión).
     */
    public boolean verificarImpacto(Ente centro, Ente objetivo) {
        float distancia = centro.calcularDistancia(objetivo);
        return distancia <= this.radio;
    }
}