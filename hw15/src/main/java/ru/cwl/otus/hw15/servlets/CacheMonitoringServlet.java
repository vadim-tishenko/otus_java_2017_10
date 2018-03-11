package ru.cwl.otus.hw15.servlets;

import ru.cwl.otus.hw15.system.FrontendService;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by vadim.tishenko
 * on 05.02.2018 20:42.
 */
public class CacheMonitoringServlet extends HttpServlet {

    private final FrontendService frontendService;

    public CacheMonitoringServlet(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        final AsyncContext asyncContext = req.startAsync();
        frontendService.handleCacheRequest(asyncContext);
    }

}


