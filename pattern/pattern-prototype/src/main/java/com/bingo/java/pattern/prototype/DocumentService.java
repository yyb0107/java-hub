package com.bingo.java.pattern.prototype;

import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Bingo
 * @title: DocumentService
 * @projectName java-hub
 */
public class DocumentService {

    public InterfaceDocument generateDefaultDocument() {
        InterfaceDocument interfaceDocument0 = getDocumentFromCache();
        InterfaceDocument interfaceDocumentReturn = new InterfaceDocument();
        interfaceDocumentReturn.setName(interfaceDocument0.getName());
        interfaceDocumentReturn.setLastModifiedDatetime(interfaceDocument0.getCreateDatetime());
        interfaceDocumentReturn.setLastModifiedDatetime(interfaceDocument0.getLastModifiedDatetime());
        interfaceDocumentReturn.getInterfaceNodeList().addAll(interfaceDocument0.getInterfaceNodeList());
        return interfaceDocumentReturn;
    }

    public InterfaceDocument getDocumentFromCache(){
        Random random =new Random(100);
        InterfaceDocument defaultDocument = new InterfaceDocument();
        defaultDocument.setCreateDatetime(new Date());
        defaultDocument.setLastModifiedDatetime(null);
        defaultDocument.setName("新建接口文档");
        defaultDocument.getInterfaceNodeList().addAll(IntStream.rangeClosed(1,2).mapToObj(l->l+"").map(l->{
            InterfaceNode node = new InterfaceNode();
            node.setName("第一个接口名"+random.nextInt());
            node.setDescription("这是XXX接口，主要功能"+random.nextInt());
            node.setKindlyReminder("需要注意的地方"+random.nextInt());
            node.setArgs(new String[]{"arg1","arg2","arg3",random.nextInt()+""});
            return node;
        }).collect(Collectors.toList()));

        return defaultDocument;

    }

}
