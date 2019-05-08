package com.bingo.customize.spring.framework.mvc.servlet;

import com.bingo.customize.spring.framework.context.BGApplicationContext;
import com.bingo.customize.spring.framework.stereotype.BGController;
import com.bingo.customize.spring.framework.stereotype.BGRequestMapping;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Bingo
 * @title: BGDispatcherServlet
 * @projectName java-hub
 */
@Slf4j
public class BGDispatcherServlet extends HttpServlet {
    private final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";
    private String contextConfigLocation;

    private BGApplicationContext applicationContext;

    private List<BGHandlerMapping> handlerMappings = new ArrayList<BGHandlerMapping>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        contextConfigLocation = config.getInitParameter(CONTEXT_CONFIG_LOCATION);
        initWebApplicationContext();
    }

    private void initWebApplicationContext() {
        applicationContext = new BGApplicationContext(contextConfigLocation);
        onRefresh(applicationContext);
    }

    protected void onRefresh(BGApplicationContext context) {
        initStrategies(context);
    }

    private void initStrategies(BGApplicationContext context) {
        initMultipartResolver(context);
        //国际化
        initLocaleResolver(context);
        //主题
        initThemeResolver(context);
        //handlerMapping
        initHandlerMappings(context);
        //url->method 的Adapter
        initHandlerAdapters(context);
        //异常处理
        initHandlerExceptionResolvers(context);
        //request默认的view
        initRequestToViewNameTranslator(context);
        //模板
        initViewResolvers(context);
        //参数缓存
        initFlashMapManager(context);
    }

    private void initFlashMapManager(BGApplicationContext context) {
    }

    private void initViewResolvers(BGApplicationContext context) {
    }

    private void initRequestToViewNameTranslator(BGApplicationContext context) {
    }

    private void initHandlerExceptionResolvers(BGApplicationContext context) {
    }

    private void initHandlerAdapters(BGApplicationContext context) {
        
    }

    private void initHandlerMappings(BGApplicationContext context) {
        //context.getBeanDefinitionCounts();
        Set<String> beanDefinitionNames = context.getBeanDefinitionNames();
        beanDefinitionNames.forEach(beanName->{
            Object obj = context.getBean(beanName);
            Class<?> clazz = obj.getClass();
            String basePath = "";
            if(clazz.isAnnotationPresent(BGController.class)) {
                if (clazz.isAnnotationPresent(BGRequestMapping.class)) {
                    basePath = ((BGRequestMapping) clazz.getAnnotation(BGRequestMapping.class)).value();
                }
                Method[] methods = clazz.getDeclaredMethods();
                String subPath = "";
                for (Method method : methods) {
                    if (method.isAnnotationPresent(BGRequestMapping.class)) {
                        subPath = ((BGRequestMapping) method.getAnnotation(BGRequestMapping.class)).value();
                        //拼接url
                        String url = (basePath + "/" + subPath).replaceAll("//*", "/").replace("\\*",".*");
                        Pattern pattern = Pattern.compile(url);
                        handlerMappings.add(new BGHandlerMapping(pattern, obj, method));
//                        if(requestHandlerMapping.get(url)!=null) continue;
//                        System.out.println(String.format("rqeust[%s] mapped with handler[%s]", url,clazz.getSimpleName()));
//                        requestHandlerMapping.put(url, method);
                    }

                }
            }
        });
    }

    private void initThemeResolver(BGApplicationContext context) {
    }

    private void initLocaleResolver(BGApplicationContext context) {
    }

    private void initMultipartResolver(BGApplicationContext context) {
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) {

    }

}
