package com.bingo.customize.spring.framework.mvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;

/**
 * @author Bingo
 * @title: BGView
 * @projectName java-hub
 */
public class BGView {
    private File viewFile;

    public BGView(File viewFile) {
        this.viewFile = viewFile;
    }

    void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception{

    }
}
