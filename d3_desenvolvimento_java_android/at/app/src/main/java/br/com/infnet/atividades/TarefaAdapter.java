package br.com.infnet.atividades;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import br.com.infnet.atividades.models.Tarefa;

/**
 * Created by Magno on 16/11/2017.
 */

public class TarefaAdapter extends ArrayAdapter<Tarefa> {
    private Context context;
    private ArrayList<Tarefa> tarefas;

    public TarefaAdapter(Context context, ArrayList<Tarefa> tarefas) {
        super(context, 0, tarefas);
        this.context = context;
        this.tarefas = tarefas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Tarefa itemPosicao = this.tarefas.get(position);
        convertView = LayoutInflater.from(this.context).inflate(R.layout.tarefa,null);

        TextView descricao = (TextView) convertView.findViewById(R.id.descricao);
        descricao.setText(itemPosicao.getDescricao());

        return convertView;
    }
}
