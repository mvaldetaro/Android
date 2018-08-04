package br.com.infnet.todolist.models;

import java.io.Serializable;

/**
 * Created by Magno on 21/11/2017.
 */

public class Comment implements Serializable {
    private String id;
    private String uid;
    private String autor;
    private String comment;

    public Comment() {
    }

    public Comment(String id, String uid, String autor, String comment) {
        this.id = id;
        this.uid = uid;
        this.autor = autor;
        this.comment = comment;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
