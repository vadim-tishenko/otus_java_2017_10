package ru.cwl.otus.jawa.hw12.servlets;

import ru.cwl.otus.hw10.model.DataSet;
import ru.cwl.otus.hw11.CacheEngine;
import ru.cwl.otus.hw11.Key;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by vadim.tishenko
 * on 05.02.2018 20:42.
 */
public class CacheMonitoringServlet extends HttpServlet {
    private final CacheEngine<Key, DataSet> cacheEngine;

    public CacheMonitoringServlet(CacheEngine<Key, DataSet> cacheEngine) {
        this.cacheEngine = cacheEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter wr = resp.getWriter();
        wr.printf("<html>\n" +
                "<head>\n" +
                "    <title>cache statistics</title>\n" +
                "    <meta http-equiv=\"refresh\" content=\"2\" />\n" +
                "</head>\n" +
                "<body>\n");
        wr.printf("hit: %d,  mis: %d, size: %d",cacheEngine.getHitCount(),cacheEngine.getMisCount(),cacheEngine.size());
        wr.print("<br/>\n</body>\n</html>");
        wr.flush();
    }
}
