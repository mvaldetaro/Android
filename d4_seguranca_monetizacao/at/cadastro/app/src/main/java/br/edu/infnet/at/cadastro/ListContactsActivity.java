package br.edu.infnet.at.cadastro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ListContactsActivity extends AppCompatActivity {

    private ListView ListaViewContatos;
    ArrayList<Contato> listaDataContatos;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contacts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        listaDataContatos = new ArrayList<Contato>();
        fileName = intent.getStringExtra("file_name");
        loadData();
    }

    public void loadData() {

        ArrayList<Contato> contatos;
        ObjectInputStream is = null;

        try {
            FileInputStream fis = openFileInput(fileName);

            try {
                while (true) {
                    is = new ObjectInputStream(fis);
                    listaDataContatos.add((Contato) is.readObject());
                }
            } catch (EOFException e) {
                // Sempre será lançada
            } finally {
                if (fis != null)
                    fis.close();
            }

            System.out.println(listaDataContatos);

            // Inicializando o adapter...
            ListaAdapterContato adapterItem = new ListaAdapterContato(this, listaDataContatos);

            // Instanciando a ListView...
            ListView listview = (ListView) findViewById(R.id.listView);

            // Definindo o adapter (com os dados) para a ListView...
            listview.setAdapter(adapterItem);

            is.close();
            fis.close();

        } catch (java.io.IOException event) {
            event.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
