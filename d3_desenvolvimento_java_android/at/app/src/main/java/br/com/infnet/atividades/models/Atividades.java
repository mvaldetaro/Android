package br.com.infnet.atividades.models;

/**
 * Created by Magno on 16/11/2017.
 */

import java.util.List;

public class Atividades {

    private List<Tarefa> tarefa = null;

    public Atividades() {
    }

    public Atividades(List<Tarefa> tarefa) {
        super();
        this.tarefa = tarefa;
    }

    public List<Tarefa> getTarefa() {
        return tarefa;
    }

    public void setTarefa(List<Tarefa> tarefa) {
        this.tarefa = tarefa;
    }

}
