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
import java.io.File;
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
    private final String TEMPLATE_ROOT = "templateRoot";
    private String contextConfigLocation;

    private BGApplicationContext applicationContext;

    private List<BGHandlerMapping> handlerMappings = new ArrayList<BGHandlerMapping>();

    private List<BGHandlerAdapter> handlerAdapters = new ArrayList<>();

    private List<BGViewResolver> viewResolvers = new ArrayList<>();

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
        //从config中获取模板的目录
        String templateRoot = ""+context.getConfig().get(TEMPLATE_ROOT);
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getPath();
        File templateRootFile = new File(templateRootPath);
        File[] files = templateRootFile.listFiles();
        for(File file:files){
            viewResolvers.add(new BGViewResolver(file));
        }

    }

    private void initRequestToViewNameTranslator(BGApplicationContext context) {
    }

    private void initHandlerExceptionResolvers(BGApplicationContext context) {
    }

    private void initHandlerAdapters(BGApplicationContext context) {
        //每一个handlerMapping对应了每一个Controller每一个方法
        //Adapter在这里的作用只要是解析request的参数值与Method方法形参的对应
        //这个处理对于所有的handlerMapping来说都是一样的，所以所有的只需要一个adapter对象就好了
        BGHandlerAdapter handlerAdapter = new BGHandlerAdapter();
        handlerAdapters.add(handlerAdapter);
//        handlerMappings.forEach(handler->{
//
//        });
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
        doDispatch(req,resp);
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {
        BGHandlerMapping handler = getHandler(req);

        BGHandlerAdapter handlerAdapter = getHandlerAdapter(handler);

        try {
            BGModelAndView modelAndView = handlerAdapter.handle(req,resp,handler);

            processDispatchResult(req,resp,modelAndView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, BGModelAndView modelAndView) throws Exception {
        //这里是真正页面输出的地方
        //根据modelAndView获取viewName
        String viewName = modelAndView.getViewName();
        BGViewResolver bgViewResolver = null;
        BGView bgView = null;
        for(BGViewResolver viewResolver:viewResolvers){
            bgView = viewResolver.resolveViewName(viewName, null);
            if(bgView !=null){
                break;
            }
        }

        bgView.render(modelAndView.getModel(),req,resp);
        //根据viewName在viewResolvers里进行遍历，看是否会有对应的template
        //如果有，则用templateView进行render
    }

    private BGHandlerAdapter getHandlerAdapter(BGHandlerMapping handler) {
        return null;
    }

    private BGHandlerMapping getHandler(HttpServletRequest req) {
        return null;
    }

}
