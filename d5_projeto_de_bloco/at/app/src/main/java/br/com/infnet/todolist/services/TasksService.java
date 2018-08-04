package br.com.infnet.todolist.services;

import br.com.infnet.todolist.models.Task;
import br.com.infnet.todolist.models.TasksList;
import br.com.infnet.todolist.models.User;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Magno on 21/11/2017.
 */

public interface TasksService {
    public static final String BASE_URL = "https://todo-6d354.firebaseio.com/";

    @GET("users")
    Call<User> users();

    @GET("tasks")
    Call<TasksList> tasks();
}
