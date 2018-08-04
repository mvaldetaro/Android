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
    public int imc;
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

        if(!"".equals(altura.getText().toString()) || !"".equals(peso.getText().toString())){


            float valor_altura = Float.parseFloat(altura.getText().toString());
            int valor_peso = Integer.parseInt(peso.getText().toString());

            imc = Math.round(valor_peso / (valor_altura * valor_altura));

            labelResultado.setVisibility(View.VISIBLE);

            if (imc < 16) {
                resultado.setText(imc + " Magreza Grave");
            } else if(imc >= 16 && imc < 17) {
                resultado.setText(imc + " Magreza Moderada");
            } else if(imc >= 17 && imc < 18.5) {
                resultado.setText(imc + " Magreza Leve");
            } else if(imc >= 18.5 && imc < 25) {
                resultado.setText(imc + " Saudável");
            } else if(imc >= 25 && imc < 30) {
                resultado.setText(imc + " Sobrepeso");
            } else if(imc >= 30 && imc < 35) {
                resultado.setText(imc + " Obesidade Grau I");
            } else if(imc >= 35 && imc < 40) {
                resultado.setText(imc + " Obesidade Grau II (severa)");
            } else {
                resultado.setText(imc + " Obesidade Grau III (mórbida)");
            }
        } else {
            resultado.setText("Por favor, preencha os campos peso e altura.");
        }

        resultado.setVisibility(View.VISIBLE);

        try  {
            InputMethodManager teclado = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            teclado.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }
    }
}
