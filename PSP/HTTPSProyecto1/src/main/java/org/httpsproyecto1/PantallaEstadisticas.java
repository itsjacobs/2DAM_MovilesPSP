package org.httpsproyecto1;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.httpsproyecto1.config.ThymeleafConstants;
import org.httpsproyecto1.config.URLConstants;
import org.httpsproyecto1.domain.model.Estadisticas;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet("/PantallaEstadisticas")
public class PantallaEstadisticas extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession(true);

        Estadisticas estadisticas =
            (Estadisticas) session.getAttribute("estadisticas");


        if (estadisticas == null) {
            estadisticas = new Estadisticas();
        }

        var webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);

        WebContext ctx = new WebContext(webExchange);
        ctx.setVariable("partidas", estadisticas.getPartidas());

        resp.setContentType(ThymeleafConstants.CONTENT_TYPE);
        ((TemplateEngine)getServletContext().getAttribute(ThymeleafConstants.TEMPLATE_ENGINE_ATTR))
                .process(URLConstants.TEMPLATE_ESTADISTICAS, ctx, resp.getWriter());
    }
}
