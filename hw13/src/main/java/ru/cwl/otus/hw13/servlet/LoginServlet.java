package ru.cwl.otus.hw13.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vadim.tishenko
 * on 07.02.2018 20:03.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    static public final String admin = "admin";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password=req.getParameter("password");
        if(check(login,password)){
            Cookie cookie=new Cookie("AUTH","OK");
            resp.addCookie(cookie);
            resp.sendRedirect("/cache");
        }else{
            resp.getWriter().print("err!");
        }
    }
    boolean check(String login,String password){
        return admin.equals(login) && admin.equals(password);
    }
}
