package br.edu.infnet.widgets.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class OptionsFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_form);

        Spinner blocos = (Spinner) findViewById(R.id.spinner_blocos);
        ArrayAdapter<CharSequence> blocos_adapter = ArrayAdapter.createFromResource(this, R.array.blocos_array, android.R.layout.simple_spinner_item);
        blocos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blocos.setAdapter(blocos_adapter);

    }
}
