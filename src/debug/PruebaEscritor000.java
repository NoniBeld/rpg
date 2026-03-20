package debug;

import herramientas.texto.Escritor;

public class PruebaEscritor000 implements Escritor {

	
public static void main(String[] args) {
    new PruebaEscritor000().ejecutar();
    new PruebaEscritor000().ejecutar2();

}

public void ejecutar() { narrar("Iniciando sistema de pruebas...", 50); /* ¡Ya lo tienes disponible! */}
public void ejecutar2 () {narrar("esto es una prueba", 10);}

}
