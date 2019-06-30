package com.bingo.io.tomcat.bio;

import com.bingo.io.tomcat.NotFoundException;
import com.bingo.io.tomcat.WebXmlReader;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Bingo
 * @title: RequestHandler
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/30  16:57
 */
public class RequestHandler implements Runnable {
    private Socket socket;
    private WebXmlReader reader;
    private MiniRequest request;
    private MiniResponse response;

    public RequestHandler(Socket socket, WebXmlReader reader) throws IOException {
        this.socket = socket;
        request = new MiniRequest(socket.getInputStream());
        response = new MiniResponse(socket.getOutputStream());
        this.reader = reader;
    }


    @Override
    public void run() {
        String url = request.getUrl();

        String servletName = reader.getUrlMappings().get(url);
        try {
            if (servletName == null || "".equals(servletName)) {
                throw new NotFoundException("no servlet name found by url " + url);
            }
            Class<?> clazz = reader.getServletMappings().get(servletName);
            if (clazz == null) {
                throw new NotFoundException("no servlet class found by name " + servletName);
            }
            boolean isServlet = false;
            for (Class<?> inter : clazz.getInterfaces()) {
                if (inter.getName().equals(MiniHttpServlet.class.getName())) {
                    isServlet = true;
                    break;
                }
            }

            MiniHttpServlet servlet = (MiniHttpServlet) clazz.newInstance();
            servlet.doService(request, response);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            try {
                response.write("Sorry. Page Not Found!");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                request.close();
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
