package br.edu.infnet.at.agenda;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magno on 21/10/2017.
 */

public class ListaAdapterContato extends ArrayAdapter<Contato> {
    private Context context;
    private ArrayList<Contato> lista;

    public ListaAdapterContato(Context context, ArrayList<Contato> lista){
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Contato itemPosicao = this.lista.get(position);
        convertView = LayoutInflater.from(this.context).inflate(R.layout.contato,null);

        TextView nome = (TextView) convertView.findViewById(R.id.nome);
        nome.setText(itemPosicao.getNome());

        TextView telefone = (TextView) convertView.findViewById(R.id.telefone);
        telefone.setText(itemPosicao.getTelefone());

        TextView email = (TextView) convertView.findViewById(R.id.email);
        email.setText(itemPosicao.getEmail());

        TextView cidade = (TextView) convertView.findViewById(R.id.cidade);
        cidade.setText(itemPosicao.getCidade());

        return convertView;
    }
}
