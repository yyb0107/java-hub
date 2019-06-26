package serialize;

import java.io.*;

/**
 * @author Bingo
 * @title: SerializableDemo
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/26  22:02
 */
public class SerializableDemo {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ChildClass child = new ChildClass();
        child.setPid(111);
        child.setPname("pname");
        child.setCid(2222);
        child.setCname("cname");
        System.out.println("序列化前："+child);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(child);

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        ChildClass child2 = (ChildClass)ois.readObject();

        System.out.println("反序列化后："+child2);


    }
}
