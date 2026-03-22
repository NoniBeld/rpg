package com.rpg.ente.mecanica;

import com.rpg.ente.Atributo;
import com.rpg.ente.Ente;

public class NavegadorPersonal {
    private double x, y, z;

    public void teletransportar(double x2, double y2, double z2) {
        this.x = x2;
        this.y = y2;
        this.z = z2;
    }

    public float calcularDistancia(Ente dueño, Ente otro) {
        // Usamos las coordenadas internas del navegador contra las del otro ente
        double dx = otro.obtenerPosicionX() - this.x;
        double dy = otro.obtenerPosicionY() - this.y;
        double dz = otro.obtenerPosicionZ() - this.z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public void avanzarHacia(Ente dueño, double destX, double destY, double destZ) {
        double dirX = destX - x, dirY = destY - y, dirZ = destZ - z;
        double distancia = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);

        if (distancia > 0) {
            double velocidad = dueño.obtenerValorAtributo(Atributo.AGILIDAD) / 10.0; 
            double paso = Math.min(velocidad, distancia);
            
            this.x += (dirX / distancia) * paso; 
            this.y += (dirY / distancia) * paso;
            this.z += (dirZ / distancia) * paso;
            
            System.out.println(String.format("[%s] camina a (%.1f, %.1f, %.1f)", 
                                dueño.obtenerNombre(), x, y, z));
        }
    }

    // Getters necesarios para que Ente.java pueda reportar su posición
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
}