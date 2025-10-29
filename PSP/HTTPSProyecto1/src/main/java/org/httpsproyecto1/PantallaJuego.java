package org.httpsproyecto1;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.httpsproyecto1.config.ThymeleafConstants;
import org.httpsproyecto1.config.URLConstants;
import org.httpsproyecto1.domain.model.Estadisticas;
import org.httpsproyecto1.domain.model.Partida;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(URLConstants.URL_PANTALLAJUEGO)
public class PantallaJuego extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession sesion = req.getSession(true);

        String nombre = req.getParameter("nombre");
        String numeroIngresadoStr = req.getParameter("numeroAdivinado");

        if (sesion.getAttribute("estadisticas") == null) {
            sesion.setAttribute("estadisticas", new Estadisticas());
        }

        if (nombre != null && !nombre.isEmpty()) {
            sesion.setAttribute("nombre", nombre);
            int numeroSecreto = (int) (Math.random() * 10) + 1;
            sesion.setAttribute("numeroSecreto", numeroSecreto);
            sesion.setAttribute("intentos", 5);
            sesion.setAttribute("juegoTerminado", false);
        } else {
            nombre = (String) sesion.getAttribute("nombre");
        }


        Integer numeroSecreto = (Integer) sesion.getAttribute("numeroSecreto");
        Integer intentos = (Integer) sesion.getAttribute("intentos");
        Boolean juegoTerminado = (Boolean) sesion.getAttribute("juegoTerminado");

        if (intentos == null) intentos = 5;
        if (juegoTerminado == null) juegoTerminado = false;

        boolean ganado = false;
        boolean perdido = false;


        if (numeroIngresadoStr != null && !numeroIngresadoStr.isEmpty() && !juegoTerminado) {
                int numeroIngresado = Integer.parseInt(numeroIngresadoStr);

                if (numeroIngresado == numeroSecreto) {
                    ganado = true;
                    juegoTerminado = true;
                    sesion.setAttribute("juegoTerminado", juegoTerminado);
                    Partida partida = crearPartida(nombre, true, numeroSecreto);
                    Estadisticas estadisticas = (Estadisticas) sesion.getAttribute("estadisticas");
                    estadisticas.getPartidas().add(partida);


                } else {
                    intentos--;
                    sesion.setAttribute("intentos", intentos);

                    if (intentos <= 0) {
                        perdido = true;
                        juegoTerminado = true;
                        sesion.setAttribute("juegoTerminado", juegoTerminado);
                        Partida partida = crearPartida(nombre, false, numeroSecreto);
                        Estadisticas estadisticas = (Estadisticas) sesion.getAttribute("estadisticas");
                        estadisticas.getPartidas().add(partida);

                    }
                }
            }


        var webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);

        WebContext ctx = new WebContext(webExchange);
        ctx.setVariable("mensaje", nombre);
        ctx.setVariable("intentos", intentos);
        ctx.setVariable("ganado", ganado);
        ctx.setVariable("perdido", perdido);
        ctx.setVariable("numeroSecreto", numeroSecreto);
        resp.setContentType(ThymeleafConstants.CONTENT_TYPE);

        ((TemplateEngine)getServletContext().getAttribute(ThymeleafConstants.TEMPLATE_ENGINE_ATTR))
                .process(URLConstants.TEMPLATE_PANTALLAJUEGO, ctx, resp.getWriter());

    }

    private Partida crearPartida(String nombre, boolean ganado, Integer numeroSecreto) {
        Partida partida = new Partida();
        partida.setNombreJugador(nombre);
        partida.setGanado(ganado);
        partida.setNumeroSecreto(numeroSecreto);
        return partida;
    }
}
