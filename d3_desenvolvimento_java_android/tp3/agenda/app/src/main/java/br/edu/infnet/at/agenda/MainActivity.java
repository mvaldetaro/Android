package br.edu.infnet.at.agenda;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.UUID;

import br.edu.infnet.at.agenda.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public EditText nome;
    public EditText senha;
    public EditText telefone;
    public EditText celular;
    public EditText email;
    public EditText cpf;
    public EditText cidade;

    public ArrayList<EditText> camposForm;

    public Button btnClear;
    public Button btnSave;
    public Button btnView;

    String strNome;
    String strSenha;
    String strTelefone;
    String strCelular;
    String strEmail;
    String strCpf;
    String strCidade;

    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFirebase();

        flag = true;

        nome = (EditText) findViewById(R.id.ipt_nome);
        senha = (EditText) findViewById(R.id.ipt_senha);
        telefone = (EditText) findViewById(R.id.ipt_telefone);
        celular = (EditText) findViewById(R.id.ipt_celular);
        email = (EditText) findViewById(R.id.ipt_email);
        cpf = (EditText) findViewById(R.id.ipt_cpf);
        cidade = (EditText) findViewById(R.id.ipt_cidade);

        btnClear = (Button) findViewById(R.id.btn_clear);
        btnSave = (Button) findViewById(R.id.btn_save);

        btnView = (Button) findViewById(R.id.btn_view_contacts);

        camposForm = new ArrayList<EditText> ();

        camposForm.add(nome);
        camposForm.add(senha);
        camposForm.add(telefone);
        camposForm.add(celular);
        camposForm.add(email);
        camposForm.add(cpf);
        camposForm.add(cidade);

        btnClear.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        if(flag)
        {
            firebaseDatabase.setPersistenceEnabled(true);
            flag = false;
        }
        databaseReference = firebaseDatabase.getReference();
    }

    public void startListContactsActivity(View view){
        Intent listContactsActivity = new Intent(this, ListContactsActivity.class);
        startActivity(listContactsActivity);
    }

    public Boolean validation(String nome, String senha, String tel, String cel, String email, String cpf, String cidade){
        Boolean check = true;
        if (nome.isEmpty() || senha.isEmpty() || tel.isEmpty() || cel.isEmpty() || email.isEmpty() || cpf.isEmpty() || cidade.isEmpty()) {
            check = false;
        }
        return check;
    }

    public void cleanerForm(ArrayList<EditText> campos){
        for (int i = 0, count = campos.size(); i < count; ++i) {
            EditText item = campos.get(i);
            item.setText("");
        }
        Utils.msg("Todos os campos foram limpos", getApplicationContext());
    }

    public void saveData(String nome, String senha, String tel, String cel, String email, String cpf, String cidade){

        String uuid = UUID.randomUUID().toString();
        //String Uid, String nome, String senha, String email, String telefone, String celular, String cpf, String cidade
        Contato contato = new Contato(uuid, nome, senha, email, tel, cel, cpf, cidade);

        databaseReference.child("contatos").child(contato.getUid()).setValue(contato);

        cleanerForm(camposForm);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_clear ) {
            cleanerForm(camposForm);
        } else if (v.getId() == R.id.btn_save ){

            strNome = nome.getText().toString();
            strSenha = senha.getText().toString();
            strTelefone = telefone.getText().toString();
            strCelular = celular.getText().toString();
            strEmail = email.getText().toString();
            strCpf = cpf.getText().toString();
            strCidade = cidade.getText().toString();

            if (validation(strNome, strSenha, strTelefone, strCelular, strEmail, strCpf, strCidade)) {
                saveData(strNome, strSenha, strTelefone, strCelular, strEmail, strCpf, strCidade);

            } else {
                Utils.msg("Por favor, preencha todos os campos", getApplicationContext());
            }
        }
    }
}
