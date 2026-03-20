package com.rpg.ente;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.rpg.combate.AtaqueBase;
import com.rpg.combate.DefensaBase;
import com.rpg.combate.MagiaBase;

import herramientas.clima.Clima;
import herramientas.texto.Narrador;

/**
 * Representa cualquier objeto con existencia en el mundo del juego.
 * Utilizamos una clase final para evitar jerarquías de herencia innecesarias (Composición).
 */
public final class Ente {
    private final int identificadorUnico;
    private String nombre;
    private Funcion funcionActual;
    private Potencia potenciaActual = Potencia.INTEGRO;
    
    private int puntosDeVidaActuales;
    private int puntosDeVidaMaximos;
	private EstadoMateria estadoActual;

    private float temperaturaIdeal = 20.0f; // Por defecto 20°C
    private List<AtaqueBase> ataquesConocidos = new ArrayList<>();
    private List<MagiaBase> hechizosConocidos = new ArrayList<>();
    private DefensaBase defensaEquipada = DefensaBase.PIEL_DURA;
    private List<Ente> inventario = new ArrayList<>();
        
 // Almacenamos los valores de los atributos de forma dinámica
    private final Map<Atributo, Integer> estadisticas;
    private final Map<ParteDelCuerpo, Integer> integridadFisica; // Salud por parte del cuerpo
    private final Map<Funcion, Consumer<Ente>> comportamientos = new EnumMap<>(Funcion.class);
    
    // Atributos de estado (POD - Programación Orientada a Datos)
    // En el futuro, estos podrían ser objetos "Componente"
    private float posicionX, posicionY, posicionZ;
    
    public Ente(int identificadorUnico, String nombre, Funcion funcionInicial) {
        this.identificadorUnico = identificadorUnico;
        this.nombre = nombre;
        this.funcionActual = funcionInicial;
        this.estadoActual = EstadoMateria.SOLIDO;
        
        this.estadisticas = new EnumMap<>(Atributo.class);
     //   this.integridadFisica = new EnumMap<>(ParteDelCuerpo.class);
        
     // Inicializamos vida por defecto
        this.puntosDeVidaMaximos = 10;
        this.puntosDeVidaActuales = 10;
		this.integridadFisica = new EnumMap<>(ParteDelCuerpo.class);
        
        inicializarEnte();
    
    }

    private void inicializarEnte() {
        // Por defecto todos los entes nacen con 0 en todo
    	for (Atributo a : Atributo.values()) estadisticas.put(a, 0);
        for (ParteDelCuerpo p : ParteDelCuerpo.values()) integridadFisica.put(p, 100); // 100% salud
        
        inicializarAnatomia();
    }
    private void inicializarAnatomia() {
        for (ParteDelCuerpo parte : ParteDelCuerpo.values()) {
            // Usamos la salud base definida en el Enum ParteDelCuerpo
            this.integridadFisica.put(parte, parte.saludBase);
        }
    }
    public void recibirImpactoLocalizado(int daño, ParteDelCuerpo zona) {
        int saludZona = integridadFisica.getOrDefault(zona, 0);
        int nuevaSaludZona = Math.max(0, saludZona - daño);
        
        integridadFisica.put(zona, nuevaSaludZona);

        // El daño a la zona se transmite al cuerpo principal (puntosDeVidaActuales)
        // aplicando el multiplicador de importancia de esa parte
        int dañoAlTotal = (int) (daño * zona.multiplicadorDañoAlTotal);
        this.recibirImpacto(dañoAlTotal);

        if (nuevaSaludZona <= 0) {
            Narrador.obtenerInstancia().narrar("¡La parte [" + zona + "] de " + this.nombre + " ha sido destrozada!", 20);
        }
    }
    public void establecerValorAtributo(Atributo atributo, int valor) {
        estadisticas.put(atributo, valor);
    }

    public int obtenerValorAtributo(Atributo atributo) {
        return estadisticas.getOrDefault(atributo, 0);
    }

 // ---Inventario---
    

    /**
     * Busca un ente dentro del inventario por su nombre.
     * @param nombreObjetivo El texto introducido por el usuario.
     * @return El Ente encontrado o null si no existe.
     */
    public Ente buscarEnInventario(String nombreObjetivo) {
        return this.inventario.stream()
            .filter(e -> e.obtenerNombre().equalsIgnoreCase(nombreObjetivo))
            .findFirst()
            .orElse(null); 
    }
    
    /**
     * Busca un objetivo primero en el inventario y luego en la escena actual.
     */
    public Ente buscarEnAlcance(String nombre, List<Ente> entesEnEscena) {
        // 1. Buscar en lo que cargo (Prioridad)
        Ente encontrado = buscarEnInventario(nombre);
        
        // 2. Si no lo tengo, buscar en el suelo (Escena)
        if (encontrado == null) {
            encontrado = entesEnEscena.stream()
                .filter(e -> e.obtenerNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
        }
        
        return encontrado;
    }
    
    public void mostrarContenido(int nivel) {
        String sangria = "  ".repeat(nivel); // Para crear jerarquía visual
        
        if (this.inventario.isEmpty()) {
            System.out.println(sangria + "- (Vacío)");
            return;
        }

        for (Ente e : this.inventario) {
            System.out.println(sangria + "└─ " + e.obtenerNombre() + " [" + e.obtenerFuncionActual() + "]");
            // Si el objeto es un CONTENEDOR, mostramos su interior también
            if (e.obtenerFuncionActual() == Funcion.CONTENEDOR) {
                e.mostrarContenido(nivel + 1);
            }
        }
    }
    
    
    @Override
    public String toString() {
        return String.format("Ente[ID: %d, Nombre: %s, Funcion: %s, Pos: (%.1f, %.1f, %.1f)]", 
                identificadorUnico, nombre, funcionActual, posicionX, posicionY, posicionZ);
    }

    public void decidirAccion(List<Ente> otrosEnEscena) {
        if (this.obtenerValorAtributo(Atributo.VIDA) < 50) {
            // Si tiene poca vida, busca comida
            Ente presa = otrosEnEscena.stream()
                .filter(e -> e != this && e.obtenerFuncionActual() == Funcion.ALIMENTO)
                .findFirst().orElse(null);
                
            if (presa != null && calcularDistancia(presa) < 2.0f) {
                this.interactuar(presa); // Aquí se dispara el Sistema Digestivo
            } else if (presa != null) {
                this.moverHacia(presa);
            }
        }
    }
    
    private void moverHacia(Ente presa) {
		// TODO Auto-generated method stub
		
	}

	//--------------------   Funciones  ----------------------------------------------
    
    public void hablar(String mensaje) {
    	String formato = String.format("[%s]: %s", this.obtenerNombre(), mensaje);    	
    	herramientas.texto.Narrador.obtenerInstancia().narrar(formato, 40);
    }
    public void recoger(Ente objeto) {
    	if(objeto.obtenerFuncionActual() != Funcion.SUJETO) {
    		this.inventario.add(objeto);
    		Narrador.obtenerInstancia().narrar(this.nombre + "ha Guardado " + objeto.obtenerNombre(), 30);
    	}
    }
    
    public void interactuar(Ente objetivo) {
    	switch (objetivo.obtenerFuncionActual()) {
    	case ALIMENTO -> {
    	consumir(objetivo);
    		this.hablar(objetivo.obtenerNombre() + ". es comido." );
    	}
    	case ARMA ->{ 
    		recoger(objetivo);
            this.hablar("Esta arma me será útil para el Coliseo.");
            }
        case SUJETO->{
            this.hablar("Saludos, " + objetivo.obtenerNombre() + ". ¿Qué haces en este lugar?");
        }
        default->
            this.hablar("Es un " + objetivo.obtenerNombre() + "... no parece hacer nada.");
      	}
    }
    
    private void consumir(Ente alimento) {
    	int nutricion = 20;
    	this.recibirImpacto(-nutricion);
    	
    	Narrador.obtenerInstancia().narrar(this.nombre +" consume " + alimento.obtenerNombre() + ". vida actual: " + this.puntosDeVidaActuales, 40);
    	alimento.cambiarFuncion(Funcion.OBJETO);
    }

    // --- NUEVA FUNCIÓN: Evolución de Función ---
    public void cambiarFuncion(Funcion nuevaFuncion) {
        herramientas.texto.Narrador.obtenerInstancia().narrar(
            String.format("¡Atención! %s ha dejado de ser %s para convertirse en %s.", 
            nombre, funcionActual, nuevaFuncion), 30);
        this.funcionActual = nuevaFuncion;
    }
    
    private void inicializarComportamientos() {
        comportamientos.put(Funcion.ALIMENTO, this::consumir);
        comportamientos.put(Funcion.ARMA, obj -> {
            recoger(obj);
            this.hablar("¡Un arma nueva!");
        });
        comportamientos.put(Funcion.SUJETO, obj -> this.hablar("Hola, " + obj.obtenerNombre()));
    }

    public void interactuarPro(Ente objetivo) {
        comportamientos.getOrDefault(objetivo.obtenerFuncionActual(), 
            obj -> this.hablar("No sé qué hacer con esto.")
        ).accept(objetivo);
    }

  //--------------------   Movimiento  ----------------------------------------------
    
    public void teletransportar(float x, float y, float z) {
        this.posicionX = x; this.posicionY = y; this.posicionZ = z;
    }
    
    /**
     * Calcula la distancia física entre este ente y otro en el espacio 3D.
     */
    public float calcularDistancia(Ente otro) {
        float deltaX = otro.posicionX - this.posicionX;
        float deltaY = otro.posicionY - this.posicionY;
        float deltaZ = otro.posicionZ - this.posicionZ;

        // Aplicamos Pitágoras: d = sqrt(dx² + dy² + dz²)
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }
    // Métodos para modificar el estado sin destruir el objeto
    public void mover(float deltaX, float deltaY) {
        this.posicionX += deltaX;
        this.posicionY += deltaY;
    }

    //--------------------   Fisica  ----------------------------------------------
    
    /**
     * Determina si otro ente está dentro de un rango de visión o percepción.
     */
    public boolean percibir(Ente otro, float rangoDeVision) {
        return calcularDistancia(otro) <= rangoDeVision;
    }
    
    /**
     * Actualiza el estado del ente basándose en el tiempo transcurrido.
     * @param delta El tiempo que ha pasado desde la última actualización.
     */
    public void actualizar(double delta) {
    	int recuperacion = (int) (obtenerValorAtributo(Atributo.CONSTITUCION) * 0.1f);
        int resActual = obtenerValorAtributo(Atributo.RESISTENCIA);
    	// Aquí podemos implementar regeneración pasiva, efectos de veneno, etc.
        
        // Ejemplo: Recuperar 1 punto de VIDA cada 60 segundos si está descansando
        if (obtenerValorAtributo(Atributo.VIDA) < 100) {
            // Lógica de recuperación basada en delta
        }
        if (resActual < 100) { // Supongamos que 100 es el máximo
            establecerValorAtributo(Atributo.RESISTENCIA, resActual + recuperacion);
        }
    }
    
    public void sentirClima(Clima climaActual) {
        float diferencia = Math.abs(climaActual.temperaturaAmbiente() - temperaturaIdeal);
        
        if (diferencia > 15.0f) {
            this.hablar("¡El clima es insoportable! Me siento débil.");
            // Aquí el framework podría bajarle la FUERZA automáticamente
        } else {
            this.hablar("El clima de " + climaActual.nombre() + " me sienta bien.");
        }
    }
    
    public void recibirImpacto(int cantidad) {
        this.puntosDeVidaActuales -= cantidad;
        
        verificarEstadoFisico();
    }

    private void verificarEstadoFisico() {
        if (puntosDeVidaActuales > 0) {
            this.estadoActual = EstadoMateria.SOLIDO; // O el que tuviera
        } else if (puntosDeVidaActuales < 0 && puntosDeVidaActuales > -100) {
            // Aquí entra tu idea: Vida Negativa
            this.hablar("Mi esencia se oscurece... Vida actual: " + puntosDeVidaActuales);
        } else if (puntosDeVidaActuales <= -100) {
            this.hablar("Mi cuerpo no soporta más el vacío. Desvaneciendo...");
            // Lógica de eliminación del mapa
        }
    }
    public void transformar(Potencia nuevaPotencia) {
        this.potenciaActual = nuevaPotencia;
        
        switch (nuevaPotencia) {
            case DIGERIDO -> {
                this.nombre = "Excremento de " + this.nombre;
                this.cambiarFuncion(Funcion.ALIMENTO); // El abono puede ser alimento para plantas
                this.establecerValorAtributo(Atributo.SUERTE, 1); // ¡Pisar esto da suerte!
            }
            case QUEMADO -> {
                this.nombre = "Cenizas de " + this.nombre;
                this.cambiarFuncion(Funcion.OBJETO);
            }
            case SANGRIENTO -> this.nombre = "Restos óseos de " + this.nombre;
        }
        
        Narrador.obtenerInstancia().narrar("La potencia de " + this.nombre + " ahora es: " + nuevaPotencia, 30);
    }
    
    public void alSerConsumido() {
        if (this.nombre.contains("Semilla")) {
            this.hablar("Espero que la tierra donde caiga sea fértil...");
        } else {
            this.hablar("Mi materia servirá para otro propósito.");
        }
    }
    
  
 // En com.rpg.ente.Ente
    /**
     * Permite que el Ente cambie su identidad nominal tras un evento (digestión, evolución, etc.)
     */
    public void cambiarNombre(String nuevoNombre) {
        Narrador.obtenerInstancia().narrar(
            String.format("¡La esencia de %s ha mutado! Ahora se conoce como: %s", this.nombre, nuevoNombre), 
            30
        );
        this.nombre = nuevoNombre;
    }
    
    public void morir(Integridad integridad) {
        this.estadoActual = EstadoMateria.SOLIDO; // O ETÉREO si es místico
        
        // Si está INTACTO, podemos extraer el "Corazón de Slime"
        if (integridad == Integridad.INTACTO) {
            Ente corazon = Creador.obtenerInstancia().crearNuevoEnte("Corazón Puro", Funcion.ALIMENTO);
            this.inventario.add(corazon);
        }
        
        // Soltamos todo al suelo de la Escena
        this.hablar("Mi forma física se desmorona...");
    }

    public void ejecutarDeshuesar(Ente cadaver) {
        for (ParteDelCuerpo parte : ParteDelCuerpo.values()) {
            int saludRestante = cadaver.obtenerSaludDeParte(parte);
            
            if (saludRestante > 0) {
                // Si la parte está sana, se extrae como objeto
                Ente trofeo = Creador.obtenerInstancia().crearNuevoEnte(
                    "Resto de " + parte + " de " + cadaver.obtenerNombre(), 
                    Funcion.OBJETO
                );
                this.inventario.add(trofeo);
                System.out.println("Has extraído con éxito: " + trofeo.obtenerNombre());
            } else {
                System.out.println("El " + parte + " de " + cadaver.obtenerNombre() + " está demasiado dañado.");
            }
        }
    }
    
    public int obtenerSaludDeParte(ParteDelCuerpo parte) {
        return integridadFisica.getOrDefault(parte, 0);
    }

    // Para que el Árbitro y otros sistemas lean la vida total
    public int obtenerVidaMax() {
        return puntosDeVidaMaximos;
    }
  // ------------------- Getters y Setters con nombres completos
    


	public int obtenerIdentificadorUnico(){ return identificadorUnico; }
    public String obtenerNombre() { return nombre;  	}
    public int obtenerVidaActual() { return puntosDeVidaActuales; }
    public Funcion obtenerFuncionActual() { return funcionActual; }
}