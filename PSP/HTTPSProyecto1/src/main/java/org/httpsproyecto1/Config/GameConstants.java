package org.httpsproyecto1.config;

public class GameConstants {
    private GameConstants() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad");
    }

    //Claves de Sesion
    public static final String SESION_NOMBRE = "nombre";
    public static final String SESION_NUMERO_ADIVINADO = "numeroAdivinado";
    public static final String SESION_JUEGO_TERMINADO = "juegoTerminado";
    public static final String SESION_NUMERO_SECRETO = "numeroSecreto";
    public static final String SESION_INTENTOS = "intentos";
    public static final String SESION_ESTADISTICAS = "estadisticas";
    public static final String SESION_GANADO = "ganado";
    public static final String SESION_PERDIDO= "perdido";
    public static final String SESION_PARTIDAS = "partidas";

    // Configuracion del juego
    public static final int MAX_INTENTOS = 5;
    public static final int RANGO_NUMERO_SECRETO_MIN = 1;
    public static final int RANGO_NUMERO_SECRETO_MAX = 10;

}
