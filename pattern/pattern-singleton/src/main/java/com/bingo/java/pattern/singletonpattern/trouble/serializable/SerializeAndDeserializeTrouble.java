package com.bingo.java.pattern.singletonpattern.trouble.serializable;

import java.io.*;

/**
 * @author Bingo
 * @title: SerializeAndDeserializeTrouble
 * @projectName java-hub
 */
public class SerializeAndDeserializeTrouble {

    public static void main(String[] args) {
        InnerClassSingletonSerializable obj = InnerClassSingletonSerializable.getInstance();
        //这里直接是序列化到我指定的缓存数组中
        StringWriter StringWriter = new StringWriter();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos);) {
            oos.writeObject(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());) {
            ObjectInputStream ois = new ObjectInputStream(bais);
            InnerClassSingletonSerializable obj1 = (InnerClassSingletonSerializable) ois.readObject();
            System.out.println(String.format("obj :" + obj));
            System.out.println(String.format("obj1 :" + obj1));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
