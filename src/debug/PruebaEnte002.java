package debug;

import com.rpg.ente.Creador;
import com.rpg.ente.Ente;
import com.rpg.ente.Funcion;
import herramientas.texto.Narrador;

public class PruebaEnte002 {

    public static void main(String[] args) {
        // 1. Obtenemos nuestras herramientas
        Creador fabrica = Creador.obtenerInstancia();
        Narrador cronista = Narrador.obtenerInstancia();

        cronista.narrar("--- INICIO DE LA CONVERSACIÓN ---", 20);

        // 2. Creamos los 3 Slimes
        Ente slimeAzul = fabrica.crearNuevoEnte("Slime Azul", Funcion.ALIMENTO);
        Ente slimeVerde = fabrica.crearNuevoEnte("Slime Verde", Funcion.ALIMENTO);
        Ente slimeRojo = fabrica.crearNuevoEnte("Slime Rojo", Funcion.ALIMENTO);

        // 3. El Diálogo (Interacción entre objetos)
        slimeAzul.hablar("¡Hola a todos! Yo tengo un aroma a moras azules.");
        
        slimeVerde.hablar("Pues yo me siento muy fresco... creo que soy de menta.");
        
        slimeRojo.hablar("¡Cuidado conmigo! Yo soy de fresa picante.");

        cronista.narrar("\n--- Los tres slimes se miran fijamente ---", 60);

        slimeAzul.hablar("Al menos todos somos del tipo: " + Funcion.ALIMENTO);
        
        cronista.narrar("Fin de la simulación de sabor.", 20);
    }
}