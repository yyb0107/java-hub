package com.bingo.java.pattern.prototype;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Bingo
 * @title: InterfaceNode
 * @projectName java-hub
 */
public class InterfaceNode implements Serializable {
    private String name;
    private String[] args;
    private String description;
    private String kindlyReminder;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKindlyReminder() {
        return kindlyReminder;
    }

    public void setKindlyReminder(String kindlyReminder) {
        this.kindlyReminder = kindlyReminder;
    }

    @Override
    public String toString() {
        return this.hashCode()+ "-InterfaceNode{" +
                "name='" + name + '\'' +
                ", args=" + Arrays.toString(args) +
                ", description='" + description + '\'' +
                ", kindlyReminder='" + kindlyReminder + '\'' +
                '}';
    }
}
