package com.bingo.java.pattern.prototype;

import java.io.*;

/**
 * @author Bingo
 * @title: DocumentClone
 * 这里利用反序列化的方式对源进行深拷贝
 * @projectName java-hub
 */
public class DocumentClone {

    public static <T> T clone(T source) {
        T result = null;
        ObjectInputStream objectInputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;

        try (ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outBuf);) {
            objectOutputStream.writeObject(source);

            byteArrayInputStream = new ByteArrayInputStream(outBuf.toByteArray());
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            result = (T) objectInputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

}
