package br.edu.infnet.at.intents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    String name = "Dragão";
    int age = 100;
    byte[] random = {99, 104, 97, 109, 97};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startSecondActivity(View view){
        // A classe Intent é uma descrição abstrata que representa uma operação a ser realizada.
        // Intents são usados para transição de Activities, solicitação de serviços de outros
        // aplicativos (câmera, compartilhamento em redes sociais…), comunicação de broadcasts,
        // entre outros processos.
        
        // Núcleo do processo de transição entre telas no Android
        Intent secondActivity = new Intent(this, SecondActivity.class);
        secondActivity.putExtra("my_name", name);
        secondActivity.putExtra("my_age", age);
        secondActivity.putExtra("random", random);
        startActivity(secondActivity);
    }

}
