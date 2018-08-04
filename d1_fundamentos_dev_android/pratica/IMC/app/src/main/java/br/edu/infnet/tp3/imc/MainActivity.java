package br.edu.infnet.tp3.imc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public EditText altura;
    public EditText peso;
    public Button btn;
    public float imc;
    public TextView resultado;
    public TextView labelResultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        altura = (EditText) findViewById(R.id.in_altura);
        peso = (EditText) findViewById(R.id.in_peso);
        resultado = (TextView) findViewById(R.id.resultado);
        btn = (Button) findViewById(R.id.btn_calcular);
        labelResultado = (TextView) findViewById(R.id.textView6);
    }

    public void calcular(View v){
        float valor_altura = Float.parseFloat(altura.getText().toString());
        int valor_peso = Integer.parseInt(peso.getText().toString());

        imc = valor_peso / (valor_altura * valor_altura);

        labelResultado.setVisibility(View.VISIBLE);
        resultado.setVisibility(View.VISIBLE);

        if (imc < 18.5) {
            resultado.setText(imc + " Baixo Peso");
        } else if(imc >= 18.6 && imc < 25) {
            resultado.setText(imc + " Peso Adequado");
        } else if(imc >= 26 && imc < 30) {
            resultado.setText(imc + " Sobrepeso");
        } else {
            resultado.setText(imc + " Obesidade");
        }

        try  {
            InputMethodManager teclado = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            teclado.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }
    }
}
