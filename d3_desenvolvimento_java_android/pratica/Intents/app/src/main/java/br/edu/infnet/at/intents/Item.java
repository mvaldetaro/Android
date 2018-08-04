package br.edu.infnet.at.intents;

/**
 * Created by Magno on 07/10/2017.
 */

public class Item {
    private int imagem;
    private String nome;
    private String descricao;

    public Item(int imagem, String nome, String descricao){
        this.imagem = imagem;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
