package br.edu.infnet.at.agenda;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.edu.infnet.at.agenda.Utils;

public class ListContactsActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private ArrayList<Contato> listContatos;
    private ListaAdapterContato arrayAdapterContato;
    ListView listview;
    Contato contatoSelecionado;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contacts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initFirebase();
        eventoDB();

        listview = (ListView) findViewById(R.id.listView);
        listContatos = new ArrayList<Contato>();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contatoSelecionado = (Contato)parent.getItemAtPosition(position);
                System.out.println(contatoSelecionado);
                System.out.println(contatoSelecionado.getUid());
                initActivity(contatoSelecionado.getUid());
            }
        });

    }

    public void initActivity(String struid){
        Intent detalhesActivity = new Intent(this, DetalhesContatoActivity.class);
        detalhesActivity.putExtra(uid, struid);
        startActivity(detalhesActivity);
    }

    private void eventoDB() {
        databaseReference.child("contatos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listContatos.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Contato contato = objSnapshot.getValue(Contato.class);
                    listContatos.add(contato);
                }

                System.out.println(listContatos);

                ListaAdapterContato adapterItem = new ListaAdapterContato(ListContactsActivity.this, listContatos);
                listview.setAdapter(adapterItem);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(ListContactsActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}
