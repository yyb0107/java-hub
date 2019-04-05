package com.bingo.java.pattern.proxy.bingoproxy;

import com.bingo.java.pattern.proxy.pojo.IPerson;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Bingo
 * @title: BGProxy
 * @projectName java-hub
 * @description: 生成临时的代理对象
 * @date 2019/4/5  9:50
 */
public class BGProxy {
    private static final String LN = "\n";

    public static Object newProxyInstance(BGClassLoader classLoader,
                                          Class<?>[] interfaces,
                                          BGInvocationHandler h) {
        //1.generate java text
        String text = generateText(interfaces[0]);
        //2.generate java file
        File file = generateFile(text);
        try {
            //3.compile java file
            generateClass(file);
            //4.instance object
//            delFile(file);
            return loadJvm(classLoader, "$Proxy0", h);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String generateText(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("package com.bingo.java.pattern.proxy.bingoproxy;%s", LN));
        sb.append(String.format("import com.bingo.java.pattern.proxy.pojo.IPerson;%s", LN));
        sb.append(String.format("import java.lang.reflect.*;%s", LN));
        sb.append(String.format("public final class $Proxy0 implements IPerson{%s", LN));
        sb.append(String.format("private BGInvocationHandler h;%s", LN));
        sb.append(String.format("public  $Proxy0(BGInvocationHandler h){%s", LN));
        sb.append(String.format("this.h = h;%s", LN));
        sb.append(String.format("}%s", LN));
        for (Method method : clazz.getDeclaredMethods()) {
//            System.out.println(method.getName());
//            System.out.println(method.getReturnType());
            StringBuffer parmaDeclare = new StringBuffer();
            StringBuffer parmaValue = new StringBuffer("new Object[]{");
            StringBuffer parmameterTypes = new StringBuffer("new Class[]{");
            if (method.getParameterTypes().length > 0) {
                for (int i = 0; i < method.getParameterTypes().length; i++) {
                    Class zz = method.getParameterTypes()[i];
                    parmaDeclare.append(zz.getName() + " param" + i + ",");
                    parmaValue.append("param" + i + ",");
                    parmameterTypes.append(zz.getName() + ".class,");
                }
                parmaDeclare.deleteCharAt(parmaDeclare.length() - 1);
                parmaValue.deleteCharAt(parmaValue.length() - 1);
                parmameterTypes.deleteCharAt(parmameterTypes.length() - 1);
            }
            parmaValue.append("}");
            parmameterTypes.append("}");

//            System.out.println(method.getParameterTypes());
            sb.append(String.format("public final %s %s(%s){%s", method.getReturnType(), method.getName(), parmaDeclare, LN));
            sb.append(String.format("try{%s", LN));
            sb.append(String.format("Class clazz = Class.forName(\"%s\");%s", clazz.getName(), LN));
            sb.append(String.format("Method method = clazz.getMethod(\"%s\",%s);%s", method.getName(), parmameterTypes, LN));
            sb.append(String.format("h.invoke(this,method,%s);%s", parmaValue, LN));
            sb.append(String.format("}catch(Error e){}%s", LN));
            sb.append(String.format("catch(Throwable e){throw new UndeclaredThrowableException(e);}%s", LN));
            sb.append(String.format("}%s", LN));
        }


        sb.append(String.format("}%s", LN));
        return sb.toString();
    }

    public static File generateFile(String text) {
        File file = new File(BGProxy.class.getResource("").getPath(), "$Proxy0.java");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(text);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 使用JavaCompiler 对生成java文件进行编译
     *
     * @param file
     * @throws IOException
     */
    public static void generateClass(File file) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable iterable = fileManager.getJavaFileObjects(file);

        JavaCompiler.CompilationTask compilationTask = compiler.getTask(null, fileManager, null, null, null, iterable);
        compilationTask.call();
        fileManager.close();
    }

    /**
     * 使用自定义的ClassLoader 找到对应的class 并且实例化一个对象
     *
     * @param classLoader
     * @param className
     * @param h
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public static Object loadJvm(BGClassLoader classLoader, String className, BGInvocationHandler h) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = classLoader.findClass(className);
        Constructor constructor = clazz.getConstructor(BGInvocationHandler.class);
        return constructor.newInstance(h);
    }

    protected static void delFile(File file) {
        file.delete();
    }


//    public static void main(String[] args) {
//        newProxyInstance(, IPerson.class, null);
//    }
}
