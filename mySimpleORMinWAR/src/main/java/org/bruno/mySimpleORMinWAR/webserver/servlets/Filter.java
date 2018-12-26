package org.bruno.mySimpleORMinWAR.webserver.servlets;

import org.bruno.mySimpleORMinWAR.utility.Tuple;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Filter implements javax.servlet.Filter {
    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "pw";
    private String login;
    private String password;
    private List<Tuple<String, String>> list = new ArrayList<>();

    public Filter() {
        list.add(new Tuple<>("postgres", "argon"));
        list.add(new Tuple<>("admin", "tset"));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        if (session.getAttributeNames() != null) {
            login = (String) session.getAttribute(LOGIN_PARAM);
            password = (String) session.getAttribute(PASSWORD_PARAM);
        }

        Tuple<String, String> auth = new Tuple<>(login, password);

        if (session == null || !list.contains(auth)) {
            resp.sendRedirect("../login");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
