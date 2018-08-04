package br.edu.infnet.tp3_dev_android;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void exibirMsg(View v){

        try  {
            InputMethodManager teclado = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            teclado.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }

        // pega valor do input text
        EditText editNome = (EditText) findViewById(R.id.editNome);
        String nome =  editNome.getText().toString();

        // add o valor do input text e exibe com Hello World

        TextView labelText = (TextView) findViewById(R.id.textView);
        String mensagem =  getResources().getString(R.string.hello) + " " + editNome.getText().toString();

        labelText.setText(mensagem);

        if(labelText.getVisibility() == View.INVISIBLE){
            labelText.setVisibility(View.VISIBLE);
        }

        String msg = getResources().getString(R.string.toastyMsg);

        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

}
