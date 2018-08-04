package br.edu.infnet.at.intents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        ArrayList<Item> lista = new ArrayList<Item>();

        Item a = new Item(R.drawable.dreamcast, "Dreamcast", "Lançado pela SEGA em 1999");
        Item b = new Item(R.drawable.mario, "Mario", "Foi um encanador a muito tempo atrás");
        Item c = new Item(R.drawable.telejogo, "Telejogo", "Video game de primeira geração lançado nos anos 70");
        Item d = new Item(R.drawable.dreamcast, "Dreamcast", "Lançado pela SEGA em 1999");
        Item e = new Item(R.drawable.mario, "Mario", "Foi um encanador a muito tempo atrás");
        Item f = new Item(R.drawable.telejogo, "Telejogo", "Video game de primeira geração lançado nos anos 70");

        lista.add(a);
        lista.add(b);
        lista.add(c);
        lista.add(d);
        lista.add(e);
        lista.add(f);

        // Inicializando o adapter...
        ListaAdapterItem adapterItem = new ListaAdapterItem(this, lista);

        // Instanciando a ListView...
        ListView listview = (ListView) findViewById(R.id.list);

        // Definindo o adapter (com os dados) para a ListView...
        listview.setAdapter(adapterItem);
    }

    public void startMainActivity(View view){
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}
