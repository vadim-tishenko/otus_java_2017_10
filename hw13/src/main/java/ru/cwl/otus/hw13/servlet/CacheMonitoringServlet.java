package ru.cwl.otus.hw13.servlet;

import ru.cwl.otus.hw10.model.DataSet;
import ru.cwl.otus.hw11.CacheEngine;
import ru.cwl.otus.hw11.Key;

import javax.servlet.ServletConfig;
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
    private CacheEngine<Key, DataSet> ce;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ce= (CacheEngine<Key, DataSet>) config.getServletContext().getAttribute("cache");
    }

   /* public CacheMonitoringServlet(CacheEngine<Key, DataSet> cacheEngine) {
        this.ce = cacheEngine;
    }*/

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter wr = resp.getWriter();
        wr.printf("<html>\n" +
                "<head>\n" +
                "    <title>cache statistics</title>\n" +
                "    <meta http-equiv=\"refresh\" content=\"2\" />\n" +
                "</head>\n" +
                "<body>\n");
        wr.printf("<br/>maxSize: %d, maxLifeTimeMs: %d, maxIdleTimeMs: %d\n",ce.getMaxSize(),ce.getMaxLifeTimeMs(),ce.getMaxIdleTimeMs());
        wr.printf("<br/>hit: %d,  mis: %d, size: %d", ce.getHitCount(), ce.getMisCount(), ce.size());
        wr.print("<br/>\n</body>\n</html>");
        wr.flush();
    }
}
