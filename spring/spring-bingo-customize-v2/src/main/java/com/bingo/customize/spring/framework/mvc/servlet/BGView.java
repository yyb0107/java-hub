package com.bingo.customize.spring.framework.mvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(viewFile));
        int len = -1;
        char[] buf = new char[1024];
        while((len = br.read(buf))!=-1){
            sb.append(buf,0,len);
        }
        br.close();
//        System.out.println("=="+new String(sb.toString().getBytes("ISO-8859-1"),"utf-8"));
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(new String(sb.toString().getBytes("ISO-8859-1"),"utf-8"));
    }
}
