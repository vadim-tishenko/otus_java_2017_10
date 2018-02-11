package ru.cwl.otus.hw13.servlet;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import ru.cwl.otus.hw10.model.DataSet;
import ru.cwl.otus.hw11.CacheEngine;
import ru.cwl.otus.hw11.Key;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by vadim.tishenko
 * on 05.02.2018 20:42.
 */
@WebServlet("/cache")
public class CacheMonitoringServlet extends HttpServlet {

    private TemplateEngine templateEngine;
    private ServletContext servletContext;
    private CacheEngine cacheEngine;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        servletContext = config.getServletContext();
        cacheEngine = (CacheEngine) servletContext.getAttribute("cache");
        templateEngine = (TemplateEngine) servletContext.getAttribute("templateEngine");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        CacheInfo cacheInfo = new CacheInfo(cacheEngine);
        ctx.setVariable("cacheInfo", cacheInfo);
        templateEngine.process("cache", ctx, resp.getWriter());
    }
}

class CacheInfo {
    private final int maxSize;
    private final long maxLifeTimeMs;
    private final long maxIdleTimeMs;
    private final long hit;
    private final long mis;
    private final int size;

    public CacheInfo(CacheEngine ce) {
        maxSize = ce.getMaxSize();
        maxLifeTimeMs = ce.getMaxLifeTimeMs();
        maxIdleTimeMs = ce.getMaxIdleTimeMs();
        hit = ce.getHitCount();
        mis = ce.getMisCount();
        size = ce.size();
    }

    public int getMaxSize() {
        return maxSize;
    }

    public long getMaxLifeTimeMs() {
        return maxLifeTimeMs;
    }

    public long getMaxIdleTimeMs() {
        return maxIdleTimeMs;
    }

    public long getHit() {
        return hit;
    }

    public long getMis() {
        return mis;
    }

    public int getSize() {
        return size;
    }
}

