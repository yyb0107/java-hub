package com.bingo.io.tomcat;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bingo
 * @title: WebXmlReader
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/30  15:23
 */
public class WebXmlReader {
    private final Map<String, Class<?>> servletMappings = new HashMap<String, Class<?>>();
    private final Map<String, String> urlMappings = new HashMap<String, String>();
    InputStream is;

    public WebXmlReader(InputStream is) {
        this.is = is;
        init();
    }

    public WebXmlReader() {
        this(WebXmlReader.class.getClassLoader().getResourceAsStream("web.xml"));
    }

    public void init() {
        //创建一个DocumentBuilderFactory的对象
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //创建一个DocumentBuilder的对象
        try {
            //创建DocumentBuilder对象
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            //通过DocumentBuilder对象的parser方法加载books.xml文件到当前项目下
            Document document = docBuilder.parse(is);
            NodeList nodeList = document.getElementsByTagName("servlet");
            int len = nodeList.getLength();
            for (int i = 0; i < len; i++) {
                Node node = nodeList.item(i);
                NodeList children = node.getChildNodes();
                int childLength = children.getLength();
                String servletName = null;
                Class<?> servletClazz = null;
                for (int j = 0; j < childLength; j++) {
                    Node chNode = children.item(j);
                    if ("servlet-name".equals(chNode.getNodeName())) {
                        if (servletName == null) {
                            servletName = chNode.getTextContent();
                        } else {
                            throw new RuntimeException(chNode.getNodeName() + "重复");
                        }
                    } else if ("servlet-class".equals(chNode.getNodeName())) {
                        servletClazz = Class.forName(chNode.getTextContent());
                    }
                }
                servletMappings.put(servletName,servletClazz);
            }

            NodeList nodeList2 = document.getElementsByTagName("servlet-mapping");
            int len2 = nodeList.getLength();
            for (int i = 0; i < len; i++) {
                Node node = nodeList2.item(i);
                NodeList children = node.getChildNodes();
                int childLength = children.getLength();
                String servletName = null;
                String urlPattern = null;
                for (int j = 0; j < childLength; j++) {
                    Node chNode = children.item(j);
                    if ("servlet-name".equals(chNode.getNodeName())) {
                        servletName = chNode.getTextContent();
                    } else if ("url-pattern".equals(chNode.getNodeName())) {
                        if (urlPattern == null) {
                            urlPattern = chNode.getTextContent();
                        } else {
                            throw new RuntimeException(chNode.getNodeName() + "重复");
                        }
                    }
                }
                urlMappings.put(urlPattern, servletName);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Class<?>> getServletMappings() {
        return servletMappings;
    }

    public Map<String, String> getUrlMappings() {
        return urlMappings;
    }

    public static void main(String[] args) {
        WebXmlReader reader = new WebXmlReader();
        System.out.println(reader.getServletMappings());
    }

}
