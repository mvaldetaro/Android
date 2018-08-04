package br.edu.infnet.at.intents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    // Array de dados, que serão exibidos na ListView...
    String[] items = {"João da Silva", "Maria Oliveira", "Francisco Aragão"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        String name = intent.getStringExtra("my_name");
        int age = intent.getIntExtra("my_age", 0);
        byte[] random = intent.getByteArrayExtra("random");
        TextView label;

        label = (TextView) findViewById(R.id.textView);
        label.setText(name);

        // Inicializando o adapter...
        ArrayAdapter<String> itensAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        // Instanciando a ListView...
        ListView listview = (ListView) findViewById(R.id.list);

        // Definindo o adapter (com os dados) para a ListView...
        listview.setAdapter(itensAdapter);
    }

    public void startThirdActivity(View view){
        Intent thirdActivity = new Intent(this, ThirdActivity.class);
        startActivity(thirdActivity);
    }
}
