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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import br.edu.infnet.at.agenda.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText nome;
    public EditText telefone;
    public EditText email;
    public EditText cidade;
    public ArrayList<EditText> camposForm;
    public Button btnClear;
    public Button btnSave;
    public Button btnView;

    String strNome;
    String strTelefone;
    String strEmail;
    String strCidade;

    String filename = "contatos.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = (EditText) findViewById(R.id.ipt_nome);
        telefone = (EditText) findViewById(R.id.ipt_telefone);
        email = (EditText) findViewById(R.id.ipt_email);
        cidade = (EditText) findViewById(R.id.ipt_cidade);

        btnClear = (Button) findViewById(R.id.btn_clear);
        btnSave = (Button) findViewById(R.id.btn_save);

        btnView = (Button) findViewById(R.id.btn_view_contacts);

        camposForm = new ArrayList<EditText> ();

        camposForm.add(nome);
        camposForm.add(telefone);
        camposForm.add(email);
        camposForm.add(cidade);

        btnClear.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        File file = new File(getFilesDir() + "/" + filename);
        System.out.println(getFilesDir() + "/" + filename);

        if (file.exists()){
            System.out.println("Arquivo existe!");
            btnView.setVisibility(View.VISIBLE);
        } else {
            System.out.println("Arquivo NÃO existe!");
            btnView.setVisibility(View.INVISIBLE);
        }
    }

    public void startListContactsActivity(View view){
        Intent listContactsActivity = new Intent(this, ListContactsActivity.class);
        listContactsActivity.putExtra("file_name", filename);
        startActivity(listContactsActivity);
    }


    public Boolean validation(String n, String t, String e, String c){
        Boolean check = true;
        if (n.isEmpty() || t.isEmpty() || e.isEmpty() || c.isEmpty()) {
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

    public void saveData(String n, String t, String e, String c){
        FileOutputStream fos;
        Contato contato = new Contato(n, t, e, c);

        try {
            fos = openFileOutput(filename, Context.MODE_APPEND);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(contato);
            oos.close();
            fos.close();

            Utils.msg("Informações salvas", getApplicationContext());
            btnView.setVisibility(View.VISIBLE);

        } catch (Exception event) {
            event.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_clear ) {
            cleanerForm(camposForm);
        } else if (v.getId() == R.id.btn_save ){

            strNome = nome.getText().toString();
            strTelefone = telefone.getText().toString();
            strEmail = email.getText().toString();
            strCidade = cidade.getText().toString();

            if (validation(strNome, strTelefone, strEmail, strCidade)) {
                saveData(strNome, strTelefone, strEmail, strCidade);

            } else {
                Utils.msg("Por favor, preencha todos os campos", getApplicationContext());
            }

        } else {

        }
    }
}
