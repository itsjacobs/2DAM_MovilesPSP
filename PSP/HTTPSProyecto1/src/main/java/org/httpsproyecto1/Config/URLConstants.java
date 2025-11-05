package org.httpsproyecto1.config;

public class URLConstants {
    private URLConstants() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad");
    }

    public static final String URL_PANTALLAJUEGO = "/PantallaJuego";
    public static final String URL_PANTALLAESTADISTICAS = "/PantallaEstadisticas";


    public static final String TEMPLATE_PREFIX = "/WEB-INF/templates/";
    public static final String TEMPLATE_SUFFIX = ".html";


    public static final String TEMPLATE_PANTALLAJUEGO = "PantallaJuego";
    public static final String TEMPLATE_ESTADISTICAS = "PantallaEstadisticas";
}
