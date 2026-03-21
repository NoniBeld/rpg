package com.rpg.ente;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.rpg.combate.AtaqueBase;
import com.rpg.combate.DefensaBase;
import com.rpg.combate.MagiaBase;
import com.rpg.teatro.Escena;

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
    private Tamaño tamañoActual = Tamaño.MEDIO;
    
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
	private float tiempoVidaRestante;
    
    public Ente(int identificadorUnico, String nombre, Funcion funcionInicial) {
        this.identificadorUnico = identificadorUnico;
        this.nombre = nombre;
        this.funcionActual = funcionInicial;
        this.estadoActual = EstadoMateria.SOLIDO;
        
        this.estadisticas = new EnumMap<>(Atributo.class);
     //   this.integridadFisica = new EnumMap<>(ParteDelCuerpo.class);
        
     // Inicializamos vida por defecto
        this.puntosDeVidaMaximos = 100;
        this.puntosDeVidaActuales = 100;
        this.tamañoActual = Tamaño.MEDIO;
        
		this.integridadFisica = new EnumMap<>(ParteDelCuerpo.class);
        
        inicializarEnte();
        inicializarComportamientos();
    
    }

    private void inicializarEnte() {
        for (Atributo a : Atributo.values()) estadisticas.put(a, 10); // Iniciamos con base 10
        inicializarAnatomia();
    }
    
    
    private void inicializarAnatomia() {
        for (ParteDelCuerpo parte : ParteDelCuerpo.values()) {
            // Usamos la salud base definida en el Enum ParteDelCuerpo
            this.integridadFisica.put(parte, parte.saludBase);
        }
    }
    
    /**
     * Calcula la vida máxima basada en la suma de sus partes 
     * más los bonos de Constitución y Resistencia.
     */
    public int calcularVidaMaximaOrganica() {
        int sumaPartes = 0;
        for (int saludParte : integridadFisica.values()) {
            sumaPartes += saludParte;
        }
        
        int bonoGenetico = obtenerValorAtributo(Atributo.CONSTITUCION) * 2;
        int bonoArmadura = obtenerValorAtributo(Atributo.RESISTENCIA);
        
        return sumaPartes + bonoGenetico + bonoArmadura;
    }

    // Al inicio de un combate o tras sanar, actualizamos:
    // this.puntosDeVidaMaximos = calcularVidaMaximaOrganica();
    
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
    
    /**
     * Devuelve la escala de existencia actual del Ente.
     * Determina qué capas del mundo puede percibir y a qué velocidad fluye su tiempo.
     */
    public Tamaño obtenerTamaño() {
        return this.tamañoActual;
    }
    /**
     * Permite cambiar la escala del Ente (por encogimiento mágico o crecimiento).
     */
    public void cambiarTamaño(Tamaño nuevoTamaño) {
        this.tamañoActual = nuevoTamaño;
        Narrador.obtenerInstancia().narrar(
            String.format("¡%s ha cambiado su escala de existencia a %s!", this.nombre, nuevoTamaño), 
            30
        );
    }
    
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
     * Traduce datos invisibles (entropía, tamaño, clima) en mensajes para el Narrador.
     */
    public void percibirEntorno(Escena escenaActual) {
        Narrador n = Narrador.obtenerInstancia();
        
        for (Ente cercano : escenaActual.obtenerPresentes()) {
            if (cercano == this) continue;

            float distancia = this.calcularDistancia(cercano);
            
            // Lógica de sentidos según TAMAÑO y DISTANCIA
            if (this.tamañoActual == Tamaño.MEDIO && cercano.obtenerTamaño() == Tamaño.MICROSCOPICO) {
                if (distancia < 1.0f) n.narrar("[OLFATO]: Un olor acre a descomposición flota en el aire...", 50);
            }
            
            if (cercano.obtenerVidaActual() < 0) {
                n.narrar("[VISTA]: Ves un aura oscura emanando de los restos de " + cercano.obtenerNombre(), 40);
            }
        }
    }
    /**
     * Actualiza el estado del ente basándose en el tiempo transcurrido.
     * @param delta El tiempo que ha pasado desde la última actualización.
     */
    public void actualizar(double delta) {
    	float deltaSubjetivo = (float) delta * tamañoActual.factorReloj;
    	int recuperacion = (int) (obtenerValorAtributo(Atributo.CONSTITUCION) * 0.1f);
        int resActual = obtenerValorAtributo(Atributo.RESISTENCIA);
    	// Aquí podemos implementar regeneración pasiva, efectos de veneno, etc.
        if (resActual < 100) {
            establecerValorAtributo(Atributo.RESISTENCIA, resActual + recuperacion);
        }
        // Ejemplo: Recuperar 1 punto de VIDA cada 60 segundos si está descansando
        if (obtenerValorAtributo(Atributo.VIDA) < 100) {
            // Lógica de recuperación basada en delta
        }
        if (resActual < 100) { // Supongamos que 100 es el máximo
            establecerValorAtributo(Atributo.RESISTENCIA, resActual + recuperacion);
        }
        
        aplicarEntropia(deltaSubjetivo, null);
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

 // En Ente.java

    /**
     * Procesa la degradación física de los restos orgánicos.
     * @param horasPasadas Tiempo transcurrido en el motor.
     */
    public void procesarDescomposicion(float horasPasadas) {
        // Si el objeto está preservado (por sal, magia o frío), no se pudre.
        if (this.obtenerValorAtributo(Atributo.RESISTENCIA) > 999) return; 

        // La descomposición se acelera con la temperatura ambiente
        // (A mayor calor, más rápido se pudre)
        // float factorClima = climaActual.temperaturaAmbiente() / 20.0f;
        
        // Si el Ente es un "Resto", buscamos su tiempo base en el Enum
        for (ParteDelCuerpo parte : ParteDelCuerpo.values()) {
            if (this.nombre.contains(parte.name())) {
                float tiempoFinal = parte.tiempoDescomposicion;
                
                // Si el tiempo de vida del resto superó su límite
                if (horasPasadas > tiempoFinal && tiempoFinal > 0) {
                    this.transformar(Potencia.DIGERIDO); // O un nuevo estado: PODRIDO
                    this.cambiarNombre("Restos Podridos de " + parte.name());
                    this.establecerValorAtributo(Atributo.VIDA, -10); // Ahora quita vida al comerlo
                }
            }
        }
    }
    
    
    public void aplicarEntropia(float deltaTiempo, Clima climaActual) {
        // 1. Si el objeto es PRESERVADO, ignoramos la entropía
        if (this.obtenerValorAtributo(Atributo.RESISTENCIA) > 999) return;

        // 2. Lógica para ALIMENTOS / PARTES ORGÁNICAS (Pudrición)
        if (this.funcionActual == Funcion.ALIMENTO || nombre.contains("Resto de")) {
            this.tiempoVidaRestante -= deltaTiempo * (climaActual.temperaturaAmbiente() / 20.0f);
            
            if (this.tiempoVidaRestante <= 0) {
                this.cambiarFuncion(Funcion.ALIMENTO); // Se vuelve abono/excremento
                this.transformar(Potencia.DIGERIDO);
                this.cambiarNombre("Materia Orgánica Descompuesta de " + this.nombre);
            }
        }

        // 3. Lógica para ARMAS / CONTENEDORES (Desgaste)
        if (this.funcionActual == Funcion.ARMA || this.funcionActual == Funcion.CONTENEDOR) {
            if (this.obtenerValorAtributo(Atributo.RESISTENCIA) <= 0) {
                this.cambiarFuncion(Funcion.OBJETO);
                this.cambiarNombre(this.nombre + " (Roto/Inservible)");
                // Aquí el "recolector de basura" podría eliminarlo si nadie lo recoge en X tiempo
            }
        }
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
    
    
    /**
     * Convierte el Ente complejo en una línea de datos pura (String).
     * Esto es Serialización Selectiva.
     */
    public String compactar() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID:").append(identificadorUnico).append("|");
        sb.append("N:").append(nombre).append("|");
        sb.append("F:").append(funcionActual).append("|");
        sb.append("V:").append(puntosDeVidaActuales).append("|");
        
        // Solo guardamos anatomía si está dañado (Ahorro de espacio)
        if (puntosDeVidaActuales < puntosDeVidaMaximos) {
            sb.append("ANT:").append(integridadFisica.toString());
        }
        
        return sb.toString();
    }


}