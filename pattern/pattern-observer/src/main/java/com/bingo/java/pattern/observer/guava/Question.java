package com.bingo.java.pattern.observer.guava;

import java.util.Date;

/**
 * @author Bingo
 * @title: Question
 * @projectName java-hub
 */
public class Question {
    private String title;
    private String content;
    private int questionId;
    private Date createDatetime;
    private Date lastUpdateDatetime;

    public Question(String title, String content, int questionId, Date createDatetime, Date lastUpdateDatetime) {
        this.title = title;
        this.content = content;
        this.questionId = questionId;
        this.createDatetime = createDatetime;
        this.lastUpdateDatetime = lastUpdateDatetime;
    }

    public Question() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getLastUpdateDatetime() {
        return lastUpdateDatetime;
    }

    public void setLastUpdateDatetime(Date lastUpdateDatetime) {
        this.lastUpdateDatetime = lastUpdateDatetime;
    }
}
