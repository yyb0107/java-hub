package com.bingo.java.pattern.singletonpattern;

import java.util.Date;

/**
 * @author Bingo
 * @title: TestBean
 * @projectName java-hub
 * @description: TODO
 * @date 2019/3/24  13:35
 */
public class TestBean {
    private int tid;
    private String tname;
    private Date date;


    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
