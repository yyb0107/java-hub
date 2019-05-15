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
    private String viewName;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public File getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(File templateFile) {
        this.templateFile = templateFile;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public BGViewResolver(File templateFile) {
        this.templateFile = templateFile;
        this.viewName=templateFile.getName();
    }


    public BGView resolveViewName(String viewName, Locale locale) throws Exception {
        if(viewName.equals(this.viewName)){
            return new BGView(templateFile);
        }
        return null;
    }
}
