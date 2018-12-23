package org.bruno.mySimpleORM.webserver.servlets;

import org.bruno.mySimpleORM.utility.Tuple;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginServlet extends HttpServlet {

    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "pw";
    private List<Tuple<String, String>> list = new ArrayList<>();
    private String login;
    private String password;

    public LoginServlet() {
        list.add(new Tuple<>("postgres", "argon"));
        list.add(new Tuple<>("admin", "tset"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        login = req.getParameter(LOGIN_PARAM);
        password = req.getParameter(PASSWORD_PARAM);
    }
}
