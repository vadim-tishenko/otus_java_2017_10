package ru.cwl.otus.jawa.hw12;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

/**
 * Created by vadim.tishenko
 * on 21.01.2018 10:24.
 */
public class ResourceServerMain {
    public static void main(String[] args) throws Exception {
        final int PORT = 8888;

        ResourceHandler rh = new ResourceHandler();
        rh.setBaseResource(Resource.newClassPathResource("/pub/"));
        ResourceHandler rh2 = new ResourceHandler();
        rh2.setBaseResource(Resource.newClassPathResource("/pub2/"));
        //rh.setDirectoriesListed(false);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        MonitoringBean mb = new MonitoringBean();
        context.addServlet(new ServletHolder(new MonitoringServlet(mb)),"/serv1");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(rh,rh2,context));

        server.start();
        //server.toString()
        //System.out.println(server.dump());
        System.out.println("started! http://localhost:"+ PORT);
        server.join();
    }
}
