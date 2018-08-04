package br.com.infnet.todolist.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.infnet.todolist.CompleteTasksActivity;
import br.com.infnet.todolist.DetailsActivity;
import br.com.infnet.todolist.R;
import br.com.infnet.todolist.commons.Utils;
import br.com.infnet.todolist.models.Task;
import okhttp3.internal.Util;

/**
 * Created by Magno on 25/11/2017.
 */

public class TaskAdapter extends ArrayAdapter<Task> {
    private Context context;
    private ArrayList<Task> tasks;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    public void startDetailActivity(Task obj){
        Intent detailActivity = new Intent(getContext(), DetailsActivity.class);
        detailActivity.putExtra("task", obj);
        getContext().startActivity(detailActivity);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Task itemPosicao = this.tasks.get(position);
        convertView = LayoutInflater.from(this.context).inflate(R.layout.single_task,null);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        TextView title = (TextView) convertView.findViewById(R.id.title_task);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_task);
        ImageView editButton = (ImageView) convertView.findViewById(R.id.btn_item_edit);

        title.setText(itemPosicao.getTitle());

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    Utils.msg("Tarefa Concluída", getContext());
                    Log.i("OBJ", itemPosicao.getTitle());
                    itemPosicao.setStatus(true);
                    databaseReference.child("tasks").child(itemPosicao.getId()).setValue(itemPosicao);
                    Utils.shakeIt(context);
                }
            }
        });

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDetailActivity(itemPosicao);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDetailActivity(itemPosicao);
            }
        });

        return convertView;
    }
}
