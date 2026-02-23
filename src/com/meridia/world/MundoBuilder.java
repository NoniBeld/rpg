package com.meridia.world;

public class MundoBuilder {

    public static Ubicacion crearMundo() {
        // 1. Crear los Nodos (Ubicaciones)
        Ubicacion claro = new Ubicacion("Claro del Bosque", 
            "Un claro místico donde los hilos del destino convergen. Hay restos de una fogata reciente.");
        
        Ubicacion ciudadNorte = new Ubicacion("Ciudad del Norte (Infernus)", 
            "Grandes forjas y torres de obsidiana. El aire huele a azufre y magia pura.");
        
        Ubicacion puebloEste = new Ubicacion("Pueblo del Este (Oakhaven)", 
            "Un pueblo pequeño rodeado de robles. Aquí, el silencio es ley y los extraños no son bienvenidos.");
            
        Ubicacion ciudadSur = new Ubicacion("Ciudad del Sur (Carnivália)", 
            "Un laberinto de carpas y luces de neón mágicas. El antiguo circo de Nalanga sigue allí.");
            
        Ubicacion ciudadOeste = new Ubicacion("Ciudad del Desierto", 
            "Dunas infinitas rodean esta urbe protegida por una cúpula de cristal.");

        // Lugares de interés en el área central (El Bosque)
        Ubicacion rio = new Ubicacion("Río de Plata", "Aguas rápidas que fluyen desde las montañas del norte.");
        Ubicacion montana = new Ubicacion("Pico del Vigía", "Desde aquí puedes ver casi toda Meridia, si el clima lo permite.");
        Ubicacion lago = new Ubicacion("Lago de los Sueños", "Un cuerpo de agua tan calmo que parece un espejo sólido.");

        Ubicacion pieGranPico = new Ubicacion("Pie del Gran Pico", 
        	    "El inicio de las cordilleras escarpadas. El aire es gélido y la nieve empieza a cubrir el suelo.");

        	Ubicacion casaSerGris = new Ubicacion("Refugio Gris", 
        	    "Una estructura mimetizada con la roca. Es cálida y huele a hierbas secas.");

        	Ubicacion cuevaHongos = new Ubicacion("Cueva de los Lamentos", 
        	    "Una entrada oscura de donde emana una luz fluorescente azulada.");
        // 2. Definir la Geografía (Conexiones)
        // Conexiones Principales (Puntos Cardinales)
        claro.conectarCon("norte", ciudadNorte, "sur");
        claro.conectarCon("este", puebloEste, "oeste");
        claro.conectarCon("sur", ciudadSur, "norte");
        claro.conectarCon("oeste", ciudadOeste, "este");

        // Conexiones Locales (Exploración del Bosque)
        claro.conectarCon("rio", rio, "claro");
        claro.conectarCon("montaña", montana, "claro");
        claro.conectarCon("lago", lago, "claro");

        // Conexión lógica adicional: Del Bosque del Norte a la Ciudad
        rio.conectarCon("norte", ciudadNorte, "rio");
        claro.conectarCon("noroeste", pieGranPico, "sureste");
        pieGranPico.conectarCon("entrar", casaSerGris, "salir");
        casaSerGris.conectarCon("cueva", cuevaHongos, "atras");

        return claro; // El juego inicia en el claro
    }
}