package br.com.infnet.todolist.adapters;

import android.content.Context;
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

import br.com.infnet.todolist.R;
import br.com.infnet.todolist.commons.Utils;
import br.com.infnet.todolist.models.Task;

/**
 * Created by Magno on 25/11/2017.
 */

public class TaskCompleteAdapter extends ArrayAdapter<Task> {
    private Context context;
    private ArrayList<Task> tasks;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    public TaskCompleteAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Task itemPosicao = this.tasks.get(position);
        convertView = LayoutInflater.from(this.context).inflate(R.layout.single_task_complete,null);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        TextView title = (TextView) convertView.findViewById(R.id.title_task);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_task);
        ImageView deleteButton = (ImageView) convertView.findViewById(R.id.btn_item_edit);

        title.setText(itemPosicao.getTitle());

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    Utils.msg("Tarefa Restaurada", getContext());
                    Log.i("OBJ", itemPosicao.getTitle());
                    itemPosicao.setStatus(false);
                    databaseReference.child("tasks").child(itemPosicao.getId()).setValue(itemPosicao);
                    Utils.shakeIt(context);
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.msg("Tarefa Excluída", getContext());
                Log.i("OBJ", itemPosicao.getTitle());
                databaseReference.child("tasks").child(itemPosicao.getId()).removeValue();
                Utils.shakeIt(context);
            }
        });

        return convertView;
    }
}
