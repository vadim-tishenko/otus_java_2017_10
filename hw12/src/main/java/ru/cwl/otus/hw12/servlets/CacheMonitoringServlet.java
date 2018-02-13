package ru.cwl.otus.hw12.servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.cwl.otus.hw10.model.DataSet;
import ru.cwl.otus.hw11.CacheEngine;
import ru.cwl.otus.hw11.Key;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vadim.tishenko
 * on 05.02.2018 20:42.
 */
public class CacheMonitoringServlet extends HttpServlet {
    private final CacheEngine<Key, DataSet> ce;
    private final TemplateEngine templateEngine;

    public CacheMonitoringServlet(CacheEngine<Key, DataSet> cacheEngine, TemplateEngine templateEngine) {
        this.ce = cacheEngine;
        this.templateEngine = templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Context ctx = new Context();
        CacheInfo cacheInfo = new CacheInfo(ce);
        ctx.setVariable("cacheInfo", cacheInfo);

        templateEngine.process("cache", ctx, resp.getWriter());

/*        PrintWriter wr = resp.getWriter();
        wr.printf("<html>\n" +
                "<head>\n" +
                "    <title>cache statistics</title>\n" +
                "    <meta http-equiv=\"refresh\" content=\"2\" />\n" +
                "</head>\n" +
                "<body>\n");
        wr.printf("<br/>maxSize: %d, maxLifeTimeMs: %d, maxIdleTimeMs: %d\n",ce.getMaxSize(),ce.getMaxLifeTimeMs(),ce.getMaxIdleTimeMs());
        wr.printf("<br/>hit: %d,  mis: %d, size: %d", ce.getHitCount(), ce.getMisCount(), ce.size());
        wr.print("<br/>\n</body>\n</html>");
        wr.flush();*/
    }
}

class CacheInfo {
    private final int maxSize;
    private final long maxLifeTimeMs;
    private final long maxIdleTimeMs;
    private final long hit;
    private final long mis;
    private final int size;

    CacheInfo(CacheEngine ce) {
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


