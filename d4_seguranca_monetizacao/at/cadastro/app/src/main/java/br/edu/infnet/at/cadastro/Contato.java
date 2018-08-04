package br.edu.infnet.at.cadastro;

import java.io.Serializable;

/**
 * Created by Magno on 12/10/2017.
 */

//strNome, strLogin, strSenha, strSenhaCheck, strCPF

public class Contato implements Serializable {

    static final String FILE_NAME = "contatos.data";

    private String nome;
    private String login;
    private String senha;
    private String cpf;

    public Contato(String nome, String login, String senha, String cpf) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.cpf = cpf;
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
