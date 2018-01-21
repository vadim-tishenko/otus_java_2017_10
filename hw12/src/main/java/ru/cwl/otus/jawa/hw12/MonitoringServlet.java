package ru.cwl.otus.jawa.hw12;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by vadim.tishenko
 * on 21.01.2018 14:00.
 */
public class MonitoringServlet extends HttpServlet {
    MonitoringInt mon;

    public MonitoringServlet(MonitoringInt mon) {
        this.mon = mon;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String param1 = "QWERT";
        PrintWriter pw = resp.getWriter();
        pw.printf("param1: %s\n", mon.getParam1());
        pw.printf("param2: %s\n", mon.getParam2());
        pw.printf("param3: %s\n", mon.getParam3());
        pw.flush();

    }
}
