package ru.cwl.otus.hw13.servlet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vadim.tishenko
 * on 07.02.2018 20:23.
 */
@WebFilter("/cache")
public class AuthFilter implements Filter {
    private Logger log = LoggerFactory.getLogger(ru.cwl.otus.hw12.servlets.AuthFilter.class);
    @Override
    public void init(FilterConfig filterConfig) {
        log.debug("init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (isAuthorized(req)) {
            chain.doFilter(request, response);
            log.debug("filter ok");
        } else {
            ((HttpServletResponse) response).sendRedirect("/login.html");
            log.debug("no login!");
        }
    }

    private boolean isAuthorized(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return false;
        for (Cookie cookie : cookies) {
            if ("AUTH".equals(cookie.getName()) && "OK".equals(cookie.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
        log.debug("destroy");
    }
}
