package com.bingo.customize.spring.framework.mvc.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Bingo
 * @title: BGDispatcherServlet
 * @projectName java-hub
 */
public class BGDispatcherServlet extends HttpServlet {
    private final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispath(req, resp);
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String contextConfigLocation = config.getInitParameter(CONTEXT_CONFIG_LOCATION);
//        contextConfigLocation
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    private void doDispath(HttpServletRequest req, HttpServletResponse resp) {
    }
}
