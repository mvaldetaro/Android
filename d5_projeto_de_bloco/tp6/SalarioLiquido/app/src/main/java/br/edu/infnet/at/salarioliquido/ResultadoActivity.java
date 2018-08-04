package br.edu.infnet.at.salarioliquido;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultadoActivity extends AppCompatActivity {

    TextView salarioLiquido;
    TextView totalDescontos;
    TextView percentualDescontos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        salarioLiquido = (TextView) findViewById(R.id.result_salario);
        totalDescontos = (TextView) findViewById(R.id.result_tot_desconto);
        percentualDescontos = (TextView) findViewById(R.id.result_porcentagem_desconto);

        salarioLiquido.setText(intent.getStringExtra("salario_liquido"));
        totalDescontos.setText(intent.getStringExtra("total_descontos"));
        percentualDescontos.setText(intent.getStringExtra("percentual_descontos") + "%");
    }
}
