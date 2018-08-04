package br.edu.infnet.at.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static br.edu.infnet.at.agenda.R.layout.contato;

public class DetalhesContatoActivity extends AppCompatActivity {

    private String uid;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public TextView nome;
    public TextView senha;
    public TextView telefone;
    public TextView celular;
    public TextView email;
    public TextView cpf;
    public TextView cidade;

    public String getUid() {
        return uid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_contato);

        initFirebase();

        Intent intent = getIntent();
        uid = intent.getStringExtra(uid);

        nome = (TextView) findViewById(R.id.nome);
        senha = (TextView) findViewById(R.id.senha);
        telefone = (TextView) findViewById(R.id.telefone);
        celular = (TextView) findViewById(R.id.celular);
        email = (TextView) findViewById(R.id.email);
        cpf = (TextView) findViewById(R.id.cpf);
        cidade = (TextView) findViewById(R.id.cidade);

        getContato(uid);

    }

    private void initFirebase() {
        FirebaseApp.initializeApp(DetalhesContatoActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void getContato(final String uidContato) {

        databaseReference.child("contatos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot objSnapshot = dataSnapshot.child(uidContato);
                Contato contato = objSnapshot.getValue(Contato.class);
                setViewData(contato);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setViewData(Contato c){
        nome.setText(c.getNome());
        senha.setText(c.getSenha());
        telefone.setText(c.getTelefone());
        celular.setText(c.getCelular());
        email.setText(c.getEmail());
        cpf.setText(c.getCpf());
        cidade.setText(c.getCidade());
    }
}
