package debug.z;

import com.ente.*;
import com.motor.Navegador;
import com.rpg.ente.bestiario.fauna.*;
import com.rpg.teatro.Escena;

import herramientas.texto.Narrador;
import herramientas.tiempo.CalendarioLunar;

public class PruebaEscenario003 {
    public static void main(String[] args) {
        Narrador n = Narrador.obtenerInstancia();
        Creador c = Creador.obtenerInstancia();
        CalendarioLunar cal = new CalendarioLunar();
        
        // 1. PREPARAR EL ESCENARIO
        // Usamos el navegador para situarnos, pero configuramos manualmente para la prueba
        Escena coliseo = new Escena("Campo de Pruebas", "Una llanura infinita bajo el horizonte Y=100", cal, null, null);

        // 2. INSTANCIAR Y POSICIONAR (Siguiendo tu esquema cartesiano)
        Ente adan = c.instanciarDesdePlantilla(Humano.Adan);
        adan.teletransportar(-4, 100, 0); 
        
        Ente og = c.instanciarDesdePlantilla(Orco.Og);
        og.teletransportar(4, 100, 0); 

        Ente slimeMora = c.instanciarDesdePlantilla(Slime.SLIME_MORA);
        slimeMora.teletransportar(2, 100, -2);

        coliseo.agregarEnte(adan);
        coliseo.agregarEnte(og);
        coliseo.agregarEnte(slimeMora);

        n.narrar("=== INICIANDO PRUEBA 003: SIMULACIÓN CARTESIANA ===", 10);
        
        // 3. EL BUCLE DE SUPERVIVENCIA
        // Este método que completamos en Escena hará todo el trabajo
        coliseo.ejecutarBatallaReal();

        n.narrar("\nResultado final analizado. La entropía ha seguido su curso.", 20);
    }
}