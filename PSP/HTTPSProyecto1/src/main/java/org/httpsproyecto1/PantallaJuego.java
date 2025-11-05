package org.httpsproyecto1;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.httpsproyecto1.config.Constants;
import org.httpsproyecto1.config.ThymeleafConstants;
import org.httpsproyecto1.domain.model.Estadisticas;
import org.httpsproyecto1.domain.model.Partida;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(Constants.URL_PANTALLAJUEGO)
public class PantallaJuego extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession sesion = req.getSession(true);

        String nombre = req.getParameter(Constants.SESION_NOMBRE);
        String numeroIngresadoStr = req.getParameter(Constants.SESION_NUMERO_ADIVINADO);

        if (sesion.getAttribute(Constants.SESION_ESTADISTICAS) == null) {
            sesion.setAttribute(Constants.SESION_ESTADISTICAS, new Estadisticas());
        }

        if (nombre != null && !nombre.isEmpty()) {
            sesion.setAttribute(Constants.SESION_NOMBRE, nombre);
            int numeroSecreto = (int) (Math.random() * Constants.RANGO_NUMERO_SECRETO_MAX) + Constants.RANGO_NUMERO_SECRETO_MIN;
            sesion.setAttribute(Constants.SESION_NUMERO_SECRETO, numeroSecreto);
            sesion.setAttribute(Constants.SESION_INTENTOS, Constants.MAX_INTENTOS);
            sesion.setAttribute(Constants.SESION_JUEGO_TERMINADO, false);
        } else {
            nombre = (String) sesion.getAttribute(Constants.SESION_NOMBRE);
        }


        Integer numeroSecreto = (Integer) sesion.getAttribute(Constants.SESION_NUMERO_SECRETO);
        Integer intentos = (Integer) sesion.getAttribute(Constants.SESION_INTENTOS);
        Boolean juegoTerminado = (Boolean) sesion.getAttribute(Constants.SESION_JUEGO_TERMINADO);

        if (intentos == null) intentos = Constants.MAX_INTENTOS;
        if (juegoTerminado == null) juegoTerminado = false;

        boolean ganado = false;
        boolean perdido = false;


        if (numeroIngresadoStr != null && !numeroIngresadoStr.isEmpty() && !juegoTerminado) {
                int numeroIngresado = Integer.parseInt(numeroIngresadoStr);

                if (numeroIngresado == numeroSecreto) {
                    ganado = true;
                    juegoTerminado = true;
                    sesion.setAttribute(Constants.SESION_JUEGO_TERMINADO, juegoTerminado);
                    Partida partida = crearPartida(nombre, true, numeroSecreto);
                    Estadisticas estadisticas = (Estadisticas) sesion.getAttribute(Constants.SESION_ESTADISTICAS);
                    estadisticas.getPartidas().add(partida);


                } else {
                    intentos--;
                    sesion.setAttribute(Constants.SESION_INTENTOS, intentos);

                    if (intentos <= 0) {
                        perdido = true;
                        juegoTerminado = true;
                        sesion.setAttribute(Constants.SESION_JUEGO_TERMINADO, juegoTerminado);
                        Partida partida = crearPartida(nombre, false, numeroSecreto);
                        Estadisticas estadisticas = (Estadisticas) sesion.getAttribute(Constants.SESION_ESTADISTICAS);
                        estadisticas.getPartidas().add(partida);

                    }
                }
            }


        var webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);

        WebContext ctx = new WebContext(webExchange);
        ctx.setVariable(Constants.SESION_NOMBRE, nombre);
        ctx.setVariable(Constants.SESION_INTENTOS, intentos);
        ctx.setVariable(Constants.SESION_GANADO, ganado);
        ctx.setVariable(Constants.SESION_PERDIDO, perdido);
        ctx.setVariable(Constants.SESION_NUMERO_SECRETO, numeroSecreto);
        resp.setContentType(ThymeleafConstants.CONTENT_TYPE);

        ((TemplateEngine)getServletContext().getAttribute(ThymeleafConstants.TEMPLATE_ENGINE_ATTR))
                .process(Constants.TEMPLATE_PANTALLAJUEGO, ctx, resp.getWriter());

    }

    private Partida crearPartida(String nombre, boolean ganado, Integer numeroSecreto) {
        Partida partida = new Partida();
        partida.setNombreJugador(nombre);
        partida.setGanado(ganado);
        partida.setNumeroSecreto(numeroSecreto);
        return partida;
    }
}
