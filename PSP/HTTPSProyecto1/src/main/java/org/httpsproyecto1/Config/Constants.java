package org.httpsproyecto1.config;

public class Constants {
    private Constants() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad");
    }

    // URLS

    public static final String URL_PANTALLAJUEGO = URLConstants.URL_PANTALLAJUEGO;
    public static final String URL_PANTALLAESTADISTICAS = URLConstants.URL_PANTALLAESTADISTICAS;
    public static final String TEMPLATE_PANTALLAJUEGO = URLConstants.TEMPLATE_PANTALLAJUEGO;
    public static final String TEMPLATE_ESTADISTICAS = URLConstants.TEMPLATE_ESTADISTICAS;


    // GAME CONSTANTS
    public static final String SESION_NOMBRE = GameConstants.SESION_NOMBRE;
    public static final String SESION_NUMERO_ADIVINADO = GameConstants.SESION_NUMERO_ADIVINADO;
    public static final String SESION_JUEGO_TERMINADO = GameConstants.SESION_JUEGO_TERMINADO;
    public static final String SESION_NUMERO_SECRETO = GameConstants.SESION_NUMERO_SECRETO;
    public static final String SESION_INTENTOS = GameConstants.SESION_INTENTOS;
    public static final String SESION_ESTADISTICAS = GameConstants.SESION_ESTADISTICAS;
    public static final String SESION_GANADO = GameConstants.SESION_GANADO;
    public static final String SESION_PERDIDO= GameConstants.SESION_PERDIDO;
    public static final String SESION_PARTIDAS = GameConstants.SESION_PARTIDAS;
    public static final int MAX_INTENTOS = GameConstants.MAX_INTENTOS;
    public static final int RANGO_NUMERO_SECRETO_MIN = GameConstants.RANGO_NUMERO_SECRETO_MIN;
    public static final int RANGO_NUMERO_SECRETO_MAX = GameConstants.RANGO_NUMERO_SECRETO_MAX;
}
