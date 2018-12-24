package org.bruno.mySimpleORM.webserver.servlets;

import org.bruno.mySimpleORM.webserver.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "pw";
    private String login;
    private String password;

    public LoginServlet() {
    }

    private static String getPage(String login, String password) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(LOGIN_PARAM, login == null ? "" : login);
        pageVariables.put(PASSWORD_PARAM, password == null ? "" : password);
        return TemplateProcessor.instance().getPage("login.html", pageVariables);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        login = req.getParameter(LOGIN_PARAM);
        password = req.getParameter(PASSWORD_PARAM);
        if (login != null && password != null) {
            req.getSession().setAttribute(LOGIN_PARAM, login);
            req.getServletContext().setAttribute(LOGIN_PARAM, login);
            req.getSession().setAttribute(PASSWORD_PARAM, password);
            req.getServletContext().setAttribute(PASSWORD_PARAM, password);
            resp.addCookie(new Cookie(LOGIN_PARAM, login));
            resp.addCookie(new Cookie(PASSWORD_PARAM, password));
            String page = getPage(login, password);
            resp.getWriter().println(page);
            setOK(resp);
        }
    }

    private void setOK(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
