package org.httpsproyecto1.Config;

public class URLConstants {
    // Constructor privado para prevenir instanciación
    private URLConstants() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad");
    }

    // URLs de los servlets
    public static final String URL_HOLA = "/hola";
    public static final String URL_ADIVINA = "/adivina";

    // Rutas de plantillas Thymeleaf
    public static final String TEMPLATE_PREFIX = "/WEB-INF/templates/";
    public static final String TEMPLATE_SUFFIX = ".html";

    // Nombres de plantillas
    public static final String TEMPLATE_HOLA = "hola";
    public static final String TEMPLATE_ADIVINA = "adivina";
}
