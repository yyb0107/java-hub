package com.bingo.java.pattern.prototype;

/**
 * @author Bingo
 * @title: PrototypeNormalTest
 * @projectName java-hub
 */
public class PrototypeNormalTest {
    static DocumentService service = new DocumentService();

    public static void main(String[] args) {
        InterfaceDocument defaultDocument = service.getDocumentFromCache();

        System.out.println(String.format("初始的信息 %s",defaultDocument));
        System.out.println("*****************************************************************");

        InterfaceDocument document = service.generateDefaultDocument();

        System.out.println(String.format("复制之后的信息 %s",document));
    }
}
