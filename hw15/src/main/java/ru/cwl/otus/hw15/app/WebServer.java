package ru.cwl.otus.hw15.app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cwl.otus.hw12.servlets.AuthFilter;
import ru.cwl.otus.hw12.servlets.LoginServlet;
import ru.cwl.otus.hw15.servlets.CacheMonitoringServlet;
import ru.cwl.otus.hw15.system.FrontendService;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Created by vadim.tishenko
 * on 10.03.2018 21:34.
 */
public class WebServer {
    private static final Logger log = LoggerFactory.getLogger(WebServer.class);

    static Server configureAndRunWebServer(int port, FrontendService frontendService) throws Exception {


        ResourceHandler rh = new ResourceHandler();
        rh.setBaseResource(Resource.newClassPathResource("/pub/"));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addFilter(new FilterHolder(new AuthFilter()), "/cache", EnumSet.of(DispatcherType.REQUEST));
        final ServletHolder cacheInfoServlet = new ServletHolder(new CacheMonitoringServlet(frontendService));
        cacheInfoServlet.setAsyncSupported(true);
        context.addServlet(cacheInfoServlet, "/cache");
        context.addServlet(new ServletHolder(new LoginServlet()), "/login");

        Server server = new Server(port);
        server.setHandler(new HandlerList(rh, context));

        server.start();
        log.info("frontend started. http://localhost:{}", port);
        return server;
    }
}
