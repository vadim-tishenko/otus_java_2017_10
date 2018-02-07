package ru.cwl.otus.hw12.servlets;


import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vadim.tishenko
 * on 07.02.2018 20:23.
 */
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (isAuthorized(req)) {
            chain.doFilter(request, response);
            System.out.println("filter ok");
        } else {
            ((HttpServletResponse) response).sendRedirect("/login.html");
            System.out.println("no login!");
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
        System.out.println("destroy");
    }
}
