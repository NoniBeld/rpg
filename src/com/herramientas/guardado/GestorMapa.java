package com.herramientas.guardado;

import com.ente.Creador;
import com.ente.Ente;
import com.rpg.teatro.Escena;

import com.herramientas.clima.Bioma;
import com.herramientas.clima.Clima;
import com.herramientas.tiempo.CalendarioLunar;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GestorMapa {
	
	public static Escena despertarEscena(String ruta, CalendarioLunar cal) {
        try {
            List<String> lineas = Files.readAllLines(Paths.get(ruta));
            // La primera línea podría guardar el Bioma y Nombre de la Escena
            // Sector [0,0,0]|BOSQUE|Descripcion...
            String[] infoEscena = lineas.get(0).split("\\|");
            
            Bioma bioma = Bioma.valueOf(infoEscena[1]);
            Clima clima = new Clima(bioma.obtenerNombre(), bioma.obtenerTemperaturaBase(), bioma.obtenerHumedadBase());
            
            Escena escena = new Escena(infoEscena[0], infoEscena[2], cal, clima, bioma);

            // Cargamos los Entes (a partir de la línea 1)
            for (int i = 1; i < lineas.size(); i++) {
                Ente e = reconstruirEnte(lineas.get(i));
                escena.agregarEnte(e);
            }
            return escena;
        } catch (Exception e) {
            return null; 
        }
    }
    
    public static void dormirEscena(Escena escena) {
        String nombreArchivo = "usuario/mapa/" + escena.obtenerNombre() + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (Ente e : escena.obtenerPresentes()) {
                writer.println(e.compactar()); // Guardamos solo la esencia
            }
        } catch (IOException ex) {
            System.err.println("Error al 'dormir' la escena.");
        }
    }
    
    private static Ente reconstruirEnte(String datos) {
        // El split separa: ["1", "Slime", "SUJETO", "10.5,0.0,5.2", "100"]
        String[] partes = datos.split("\\|");
        
        int id = Integer.parseInt(partes[0]);
        String nombre = partes[1];
        com.ente.Funcion funcion = com.ente.Funcion.valueOf(partes[2]);
        
        // Usamos el creador para la base
        Ente nuevo = Creador.obtenerInstancia().crearNuevoEnte(nombre, funcion);
        
        // Parseamos la posición (X,Y,Z)
        String[] coords = partes[3].split(",");
        double x = Double.parseDouble(coords[0]);
        double y = Double.parseDouble(coords[1]);
        double z = Double.parseDouble(coords[2]);
        nuevo.teletransportar(x, y, z);
        
        // Parseamos la vida
        int vida = Integer.parseInt(partes[4]);
        nuevo.establecerValorAtributo(com.ente.Atributo.VIDA, vida);
        
        return nuevo;
    }

}