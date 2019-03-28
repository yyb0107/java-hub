package com.bingo.java.pattern.prototype;

/**
 * @author Bingo
 * @title: PrototypeCloneTest
 * 这次采用反序列化的方式进行对象的深复制
 * @projectName java-hub
 */
public class PrototypeCloneTest {
    static DocumentService service = new DocumentService();

    public static void main(String[] args) {
        InterfaceDocument defaultDocument = service.getDocumentFromCache();

        System.out.println(String.format("初始的信息 %s",defaultDocument));
        System.out.println("*****************************************************************");

        InterfaceDocument document = DocumentClone.clone(defaultDocument);

        System.out.println(String.format("复制之后的信息 %s",document));
    }
}
