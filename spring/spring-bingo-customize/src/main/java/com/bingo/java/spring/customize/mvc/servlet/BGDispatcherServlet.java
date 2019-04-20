package com.bingo.java.spring.customize.mvc.servlet;

import com.bingo.java.spring.customize.mvc.annotation.*;
import com.bingo.java.spring.customize.mvc.converter.Converter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * @author Bingo
 * @title: BGDispatcherServlet
 * @projectName java-hub
 */
public class BGDispatcherServlet extends HttpServlet {

    Properties properties;

    String basePackageName;

    final Map<String, Object> IOC = new HashMap<>();

    final Map<String, Method> requestHandlerMapping = new HashMap<>();

    final Map<String, Converter> converters = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //调用 运行阶段
        doDispather(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    /**
     * 解析http请求  找到uri对应的方法
     * @param req
     * @param resp
     */
    private void doDispather(HttpServletRequest req, HttpServletResponse resp) {
        String uri = req.getRequestURI();
        String key = uri.replace(req.getContextPath(), "");
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
            //如果url找到了可以找到对应的的处理方法
            if (requestHandlerMapping.get(key) != null) {
                Method method = requestHandlerMapping.get(key);
                Parameter[] parameters = method.getParameters();
                //方法的实参列表
                ArrayList<Object> paramList = new ArrayList<>();
                for(Parameter parameter:parameters){
                    if(parameter.getType().getName().endsWith("Request")){
                        paramList.add(req);
                        continue;
                    }
                    if(parameter.getType().getName().endsWith("Response")){
                        paramList.add(resp);
                        continue;
                    }
                    if(parameter.isAnnotationPresent(BGRequestParam.class)){
                        String paramName = ((BGRequestParam)parameter.getAnnotation(BGRequestParam.class)).value();
                        String paramValue = req.getParameter(paramName);
                        //新增参数的类型转换，将转换的逻辑从controller 转到这里来
                        Object paramValueAfterConvert = convert(paramValue, parameter.getType());
                        paramList.add(paramValueAfterConvert);
                    }

                }
                Object result = method.invoke(IOC.get(firstCharacterLower(method.getDeclaringClass().getSimpleName())), paramList.toArray());
                writer.write(result+"");
            } else {
                writer.write("404, Not Found Target");
            }
        } catch (Exception e) {
            e.printStackTrace();
            writer.write("500 " + e.getLocalizedMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }


    }

//将reqeust获取的参数按照对应方法参数类型进行转化
    private Object convert(String paramValue, Class<?> type) {
//首先看目标类型是否为String
        if (type == String.class) return paramValue;
        //如果不是  先初始化converter
        if (converters.isEmpty()) {
            initConverters(Converter.class);
        }
        //根据实际的type 获取对应的convert  执行convert方法
        Converter converter = converters.get(type.getName() );
        if(converter == null) return null;
        return converter.convert(paramValue, type);
    }

    private void initConverters(Class<Converter> baseClass) {
        Pattern pattern = Pattern.compile("<(.*),(.*)>");
        String packagePath = baseClass.getPackage().getName().replaceAll("\\.", "\\\\");
        String basePath = baseClass.getClassLoader().getResource("").getPath();
        File file = new File(basePath + "/" + packagePath);
        File[] files = file.listFiles();
        if (files == null) return;
        String className = null;
        try {
            for (File f : files) {
                className = baseClass.getPackage().getName() + "." + f.getName().substring(0, f.getName().length() - 6);
                Class clazz = Class.forName(className);
                //接下来获取当前类实现的接口 接口中有泛型的定义，在这里获取实际的类型
                //注意 如果在运行时 当前类仍然不知道泛型的具体类型，则会抛出ClassCastException
                if (!clazz.isInterface()) {
                    Type type = clazz.getGenericInterfaces()[0];
                    if (type instanceof ParameterizedType) {
                        Type[] actualTypes = ((ParameterizedType) type).getActualTypeArguments();
                        if (actualTypes.length < 2) continue;
                        Object obj = clazz.newInstance();
                        if (obj instanceof Converter) {
                            converters.put(actualTypes[1].getTypeName(), (Converter) obj);
                        }
                    }

                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        //1.加载配置文件
        propertiesLoad(config);


        //2.扫描包，初始化IOC
        packageScanner(basePackageName);


        //3.DI  依赖注入
        beanAutowired();

        //4.request handler mapping
        requestHandlerMapping();

    }

    private void requestHandlerMapping() {
        StringBuilder sb = new StringBuilder();
        IOC.forEach((key, obj) -> {
            Class clazz = obj.getClass();
            String basePath = "";
            if (clazz.isAnnotationPresent(BGRequestMapping.class)) {
                basePath = ((BGRequestMapping) clazz.getAnnotation(BGRequestMapping.class)).value();
            }
            Method[] methods = clazz.getDeclaredMethods();
            String subPath = "";
            for (Method method : methods) {
                if (method.isAnnotationPresent(BGRequestMapping.class)) {
                    subPath = ((BGRequestMapping) method.getAnnotation(BGRequestMapping.class)).value();
                    //拼接url
                    String url = (basePath + "/" + subPath).replaceAll("//*", "/");
                    if(requestHandlerMapping.get(url)!=null) continue;
                    System.out.println(String.format("rqeust[%s] mapped with handler[%s]", url,clazz.getSimpleName()));
                    requestHandlerMapping.put(url, method);
                }

            }

        });
    }

    private void beanAutowired() {

        IOC.forEach((key, obj) -> {
            Field[] fields = obj.getClass().getDeclaredFields();
            String fieldTypeSimpleName = "";
            try {
                for (Field field : fields) {
                    fieldTypeSimpleName = "";
                    if (field.isAnnotationPresent(BGAutowired.class)) {
                        Method method = obj.getClass().getMethod("set" + firstCharacherUpper(field.getName()), field.getType());
                        //从IOC获取对应name的bean
                        fieldTypeSimpleName = field.getType().getSimpleName();
                        Object param = IOC.get(firstCharacterLower(fieldTypeSimpleName));
                        method.setAccessible(true);
                        if (param != null) {
                            method.invoke(obj, param);
                        }
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 根据包名扫描包下的类，并对加上注解的类进行初始化
     *
     * @param packageName
     */
    private void packageScanner(String packageName) {
        if (packageName == null) return;
        String packagePath = this.getClass().getClassLoader().getResource("").getPath() + "/" + packageName.replace(".", "/");
        File file = new File(packagePath);
        String className = "";
        //如果我们要解析的annotation多的话，放到数组里，可以用循环的方式
        Class<? extends Annotation>[] annotationClass = new Class[]{BGController.class, BGService.class};
        if (file.isDirectory()) {
            File[] files = file.listFiles((dir, name) -> name.endsWith(".class") || (new File(dir, name)).isDirectory());
            for (File f : files) {
                if (f.isDirectory()) {
                    packageScanner(packageName + "." + f.getName());
                } else {
                    //实例化
                    className = packageName + "." + f.getName().substring(0, f.getName().length() - 6);
                    try {
                        Class<?> clazz = Class.forName(className);
                        String aliasName = null;
                        Object bean = null;

                        for (Class<? extends Annotation> annotationClazz : annotationClass) {
                            if (clazz.isAnnotationPresent(annotationClazz)) {
                                //首先按照默认的方式  类名首字母小写作为beanName
                                bean = clazz.newInstance();
                                IOC.put(firstCharacterLower(clazz.getSimpleName()), bean);

                                //下面是对别名进行扫描 aliasName
                                Object obj = clazz.getAnnotation(annotationClazz);
                                //用反射的方式获取value
                                Method method = obj.getClass().getMethod("value", null);
                                aliasName = "" + method.invoke(obj, null);
                                if (aliasName != null && aliasName.length() > 1) {
                                    if(IOC.get(aliasName)!=null) throw new RuntimeException(String.format("the aliasName[%s] exists ,cannot instance twice",aliasName));
                                    IOC.put(aliasName, bean);
                                    aliasName = null;
                                }
                            }
                        }
//                        if (clazz.isAnnotationPresent(BGController.class)) {
//                            aliasName = clazz.getAnnotation(BGController.class).value();
//                            if(aliasName!=null){
//                                IOC.put(aliasName, bean);
//                                aliasName =null;
//                            }
//                        }
//                        if (clazz.getAnnotationsByType(BGService.class) != null) {
//                            aliasName = clazz.getAnnotation(BGService.class).value();
//                            if(aliasName!=null){
//                                IOC.put(aliasName, bean);
//                                aliasName =null;
//                            }
//                        }

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private String firstCharacterLower(String simpleName) {
        return simpleName.replace(simpleName.charAt(0), (char) (simpleName.charAt(0) + 32));
    }

    private String firstCharacherUpper(String simpleName) {
        return simpleName.replace(simpleName.charAt(0), (char) (simpleName.charAt(0) - 32));
    }

    private void propertiesLoad(ServletConfig config) {
        String configFileName = config.getInitParameter("contextConfigLocation");
        configFileName = configFileName == null ? "application.properties" : configFileName;
        String fileName = BGDispatcherServlet.class.getClassLoader().getResource(configFileName).getPath();
        try (FileInputStream fis = new FileInputStream(new File(fileName));) {
            properties = new Properties();
            properties.load(fis);
            basePackageName = properties.getProperty("scanPackage");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
