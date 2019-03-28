package com.bingo.java.pattern.prototype;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Bingo
 * @title: InterfaceDocument
 * @projectName java-hub
 */
public class InterfaceDocument implements Serializable {
    private String name;
    private Date createDatetime;
    private Date lastModifiedDatetime;
    private List<InterfaceNode> interfaceNodeList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getLastModifiedDatetime() {
        return lastModifiedDatetime;
    }

    public void setLastModifiedDatetime(Date lastModifiedDatetime) {
        this.lastModifiedDatetime = lastModifiedDatetime;
    }

    public List<InterfaceNode> getInterfaceNodeList() {
        if(interfaceNodeList == null){
            interfaceNodeList = new ArrayList<>();
        }
        return interfaceNodeList;
    }

    @Override
    public String toString() {
        return this.hashCode()+"-InterfaceDocument{" +
                "name='" + name + '\'' +
                ", createDatetime=" + createDatetime +
                ", lastModifiedDatetime=" + lastModifiedDatetime +
                ", interfaceNodeList=" + interfaceNodeList +
                '}';
    }
}
