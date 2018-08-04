package br.edu.infnet.widgets.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FormularioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
    }

    public void proximaTela(View v){
        Intent intent = new Intent(this, OptionsFormActivity.class);
        startActivity(intent);
    }

}
