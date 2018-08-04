package br.edu.infnet.aulainicial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public EditText nome;
    public Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nome = (EditText) findViewById(R.id.editText);
    }

    public void mostraToast(View v){
        String nomeTemp;
        nomeTemp = nome.getText().toString();
        Toast.makeText(v.getContext(), "Olá, " + nomeTemp, Toast.LENGTH_LONG).show();
        Log.d(nome.toString(), "info");
    }
}
