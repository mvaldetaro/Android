package br.com.infnet.atividades.models;

import java.io.Serializable;

/**
 * Created by Magno on 15/11/2017.
 */

public class Usuario implements Serializable {

    private String Uid;
    private String nome;
    private String login;
    private String senha;
    private String cpf;

    public Usuario() {

    }

    public Usuario(String Uid, String nome, String login, String senha, String cpf) {
        this.Uid = Uid;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.cpf = cpf;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
