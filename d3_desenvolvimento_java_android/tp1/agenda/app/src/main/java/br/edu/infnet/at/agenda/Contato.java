package br.edu.infnet.at.agenda;

import java.io.Serializable;

/**
 * Created by Magno on 12/10/2017.
 */

public class Contato implements Serializable {

    static final String FILE_NAME = "contatos.data";

    private String nome;
    private String telefone;
    private String email;
    private String cidade;

    public Contato(String nome, String telefone, String email, String cidade) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cidade = cidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}
