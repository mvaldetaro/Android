package br.com.infnet.todolist.models;

import java.util.List;

/**
 * Created by Magno on 25/11/2017.
 */

public class TasksList {

    private List<Task> task = null;

    public TasksList() {
    }

    public TasksList(List<Task> task) {
        super();
        this.task = task;
    }

    public List<Task> getTask() {
        return task;
    }

    public void setTask(List<Task> tarefa) {
        this.task = tarefa;
    }
}
