package com.bingo.customize.spring.framework.mvc.servlet;

import java.io.File;
import java.util.Locale;

/**
 * @author Bingo
 * @title: BGViewResolver
 * @projectName java-hub
 */
public class BGViewResolver {
    private String prefix;
    private String suffix;
    private File templateFile;

    public BGViewResolver(File templateFile) {
        this.templateFile = templateFile;
    }


    public BGView resolveViewName(String viewName, Locale locale) throws Exception {
        return null;
    }
}
