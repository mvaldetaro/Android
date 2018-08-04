package br.com.infnet.atividades.models;

/**
 * Created by Magno on 15/11/2017.
 */

public class Tarefa {

    private String id;
    private String descricao;
    private String imagem;

    public Tarefa() {
    }

    public Tarefa(String id, String descricao, String imagem) {
        super();
        this.id = id;
        this.descricao = descricao;
        this.imagem = imagem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

}
