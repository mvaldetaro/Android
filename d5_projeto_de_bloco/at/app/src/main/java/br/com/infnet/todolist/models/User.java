package br.com.infnet.todolist.models;

/**
 * Created by Magno on 21/11/2017.
 */

public class User {

    private String uid;
    private String nome;
    private String login;
    private String senha;

    public User() {

    }

    public User(String login, String senha) {
        this.nome = nome;
        this.login = login;
    }

    public User(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public User(String Uid, String nome, String login, String senha) {
        this.uid = Uid;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
