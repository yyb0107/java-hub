package com.bingo.java.pattern.proxy.bingoproxy;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.*;

/**
 * @author Bingo
 * @title: BGClassLoader
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/5  11:30
 */
public class BGClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String basePath = BGClassLoader.class.getPackage().getName();
        //只需要classname  不需要加上.class
        String className = String.format("%s.%s",basePath, name);
        File file = new File(BGClassLoader.class.getResource("").getPath() + File.separator + name + ".class");
        System.out.println(file.getPath());
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            FileInputStream fis = new FileInputStream(file);) {
            byte[] buf = new byte[1024];
            int len = -1;
            while ((len = fis.read(buf)) != -1) {
                baos.write(buf,0,len);
            }
            baos.flush();
            return defineClass(className,baos.toByteArray(),0,baos.toByteArray().length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
