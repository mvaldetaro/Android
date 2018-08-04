package br.com.infnet.todolist.models;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Magno on 21/11/2017.
 */

public class Task implements Serializable {
    private String id;
    private String uid;
    private String title;
    private String annotation;
    private Date date;
    private File file;
    private Comment comment;
    private Boolean status;

    public Task() {
        this.status = false;
        this.comment = null;
    }

    public Task(String id, String uid, String title) {
        this.id = id;
        this.title = title;
        this.uid = uid;
        this.status = false;
        this.comment = null;
    }

    public Task(String id, String uid, String title, Boolean status) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.status = status;
    }

    public Task(String id, String title, String annotation, Date date, File file, Comment comment) {
        this.title = title;
        this.annotation = annotation;
        this.date = date;
        this.file = file;
        this.status = false;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
