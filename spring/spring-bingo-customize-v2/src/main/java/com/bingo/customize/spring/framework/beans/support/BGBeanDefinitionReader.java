package com.bingo.customize.spring.framework.beans.support;

import com.bingo.customize.spring.framework.beans.config.BGBeanDefinition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Bingo
 * @title: BGBeanDefinitionReader
 * @projectName java-hub
 */
public class BGBeanDefinitionReader {
    private String configLocation;

    private final String SCAN_PACKAGE = "scanPackage";

    private Properties properties;

    private List<String> registyBeanClasses = new ArrayList<String>();

    public BGBeanDefinitionReader(String configLocation) throws IOException{
        //创建Reader对象的时候开始对配置文件进行解析

        try(FileInputStream fis = new FileInputStream(this.getClass().getClassLoader().getResource(configLocation).getPath());){
            properties = new Properties();
            properties.load(fis);
        }

        doLoadConfig();
    }

    /**
     * 只需要扫描需要加载的类名  不在这里进行实例化
     * @param basePackage
     */
    private void doScanner(String basePackage) {
        if (basePackage == null) return;
        String packagePath = this.getClass().getClassLoader().getResource("").getPath() + "/" + basePackage.replace(".", "/");
        File file = new File(packagePath);
        String className = "";
        //如果我们要解析的annotation多的话，放到数组里，可以用循环的方式
        if (file.isDirectory()) {
            File[] files = file.listFiles((dir, name) -> name.endsWith(".class") || (new File(dir, name)).isDirectory());
            for (File f : files) {
                if (f.isDirectory()) {
                    doScanner(basePackage + "." + f.getName());
                } else {
                    className = basePackage + "." + f.getName().substring(0, f.getName().length() - 6);
                    registyBeanClasses.add(className);
                }
            }
        }
    }

    public void doLoadConfig(){
        doScanner(properties.getProperty(SCAN_PACKAGE));
    }

    public List<BGBeanDefinition> loadBeanDefinitions(String configLocation) throws IOException {
        List<BGBeanDefinition> result = new ArrayList<BGBeanDefinition>();
        if(registyBeanClasses.isEmpty()) return null;
        registyBeanClasses.forEach(className->{
            try {
                Class<?> clazz = Class.forName(className);
                if(!clazz.isInterface()) {
                    result.add(doCreateBGDefinition(firstCharacterLower(clazz.getSimpleName()),clazz.getName()));
                    Class<?>[] interfaces = clazz.getInterfaces();
                    //如果一个接口有多个继承类，TODO
                    if(interfaces.length>0){
                        for(Class<?> interfazz :interfaces){
                            result.add(doCreateBGDefinition(interfazz.getSimpleName(),clazz.getName()));
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return result;
    }

    private BGBeanDefinition doCreateBGDefinition(String factoryBeanName,String beanClassName) {
        BGBeanDefinition bgBeanDefinition = new BGBeanDefinition();
        bgBeanDefinition.setFactoryBeanName(factoryBeanName);
        bgBeanDefinition.setBeanClassName(beanClassName);
        return bgBeanDefinition;
    }

    public List<BGBeanDefinition> loadBeanDefinitions() throws FileNotFoundException,IOException{
        return loadBeanDefinitions(this.configLocation);
    }

    private String firstCharacterLower(String simpleName) {
        return simpleName.replace(simpleName.charAt(0), (char) (simpleName.charAt(0) + 32));
    }

    private String firstCharacherUpper(String simpleName) {
        return simpleName.replace(simpleName.charAt(0), (char) (simpleName.charAt(0) - 32));
    }

    public Properties getConfig(){
        return this.properties;
    }

}
