package debug;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * El Monolito Maestro de Kronikas.
 * Punto de entrada único que gestiona el inicio y el registro de usuarios.
 * * @author NoniBeld & Gemini
 */
public class KronikasLauncher {

    // Ruta definida por el arquitecto
	// Java unirá las piezas con el separador correcto ( \ o / )
/*	private static final String CARPETA_USUARIO = "usuario"; 
	private static final String RUTA_USUARIO = CARPETA_USUARIO + File.separator + "perfil.txt";
	*/
	
	// Esto detecta C:\Users\Nombre o /home/nombre automáticamente
	// Por esto (la palabra "home" es la que Java reconoce):
	private static final String home = System.getProperty("user.home");
	
	// Creamos una subcarpeta oculta para no ensuciar la vista del usuario
	// En Linux los puntos al inicio (.) ocultan la carpeta.
	private static final String carpetaBase = home + File.separator + ".kronikas";
	private static final String rutaPerfil = carpetaBase + File.separator + "perfil.txt";
	private static final String rutaMods = carpetaBase + File.separator + "mods";

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // 1. Saludo inicial con efecto lento
        imprimirLento("Hola, bienvenido a Kronikas...", 50);
        System.out.println("\n"); // Salto de línea

        File archivoPerfil = new File(/*RUTA_USUARIO*/rutaPerfil);

        if (!archivoPerfil.exists()) {
            // --- FLUJO USUARIO NUEVO ---
            imprimirLento("Veo que es tu primera vez en este espacio.", 30);
            System.out.print("\n¿Cuál es tu nombre? > ");
            String nombre = teclado.nextLine();

            crearRegistroUsuario(nombre);
            
            System.out.println();
            imprimirLento("Registro completado. Firma grabada en el sistema.", 30);
        }

        // --- FLUJO USUARIO EXISTENTE ---
        String nombreGuardado = leerNombreUsuario();
        imprimirLento("Bienvenido de nuevo, " + nombreGuardado + ". Iniciando sistemas...", 50);

        // Aquí es donde en el futuro llamaremos al POO y al POD
        teclado.close();
    }

    /**
     * Imprime un texto en consola letra por letra.
     */
    private static void imprimirLento(String texto, int velocidad) {
        for (char c : texto.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(velocidad);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Crea el archivo de texto con el nombre del usuario.
     */
    private static void crearRegistroUsuario(String nombre) {
        try {
            // Asegurar que la carpeta exista
            Files.createDirectories(Paths.get(rutaPerfil));
            
            FileWriter escritor = new FileWriter(rutaPerfil);
            escritor.write(nombre);
            escritor.close();
        } catch (IOException e) {
            System.err.println("\n[ERROR CRITICO] No se pudo crear el archivo: " + e.getMessage());
        }
    }

    /**
     * Lee el nombre desde el archivo TXT.
     */
    private static String leerNombreUsuario() {
        try {
            return new String(Files.readAllBytes(Paths.get(rutaPerfil))).trim();
        } catch (IOException e) {
            return "Viajero Desconocido";
        }
    }
}