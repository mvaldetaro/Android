package br.edu.infnet.at.agenda;

import java.io.Serializable;

/**
 * Created by Magno on 12/10/2017.
 */

public class Contato implements Serializable {

    private String Uid;
    private String nome;
    private String senha;
    private String email;
    private String telefone;
    private String celular;
    private String cpf;
    private String cidade;

    public Contato(){

    }

    public Contato(String Uid, String nome, String senha, String email, String telefone, String celular, String cpf, String cidade) {
        this.Uid = Uid;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.telefone = telefone;
        this.celular = celular;
        this.cpf = cpf;
        this.cidade = cidade;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}
