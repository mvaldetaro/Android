package br.edu.infnet.at.salarioliquido;

import android.content.Context;
import android.content.Intent;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import br.edu.infnet.at.salarioliquido.Utils;

import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText salariobruto, qtdDependente, pensaoAlimenticia, planoSaude, outrosDescontos;
    public Button btnCalcular;
    public Button btnApagarResultados;
    public String filename = "resultados.txt";
    public Salario salario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Campos do formulário
        salariobruto = (EditText) findViewById(R.id.input_salario_bruto);
        qtdDependente =  (EditText) findViewById(R.id.input_qtd_dependentes);
        pensaoAlimenticia = (EditText) findViewById(R.id.input_pensao);
        planoSaude =(EditText) findViewById(R.id.input_saude);
        outrosDescontos =(EditText) findViewById(R.id.input_descontos);

        // Botões
        btnCalcular = (Button) findViewById(R.id.btn_calcular);
        btnCalcular.setOnClickListener(this);

        btnApagarResultados = (Button) findViewById(R.id.btn_apagar_resultados);
        btnApagarResultados.setOnClickListener(this);

        // Verifica se o arquivo txt já existe
        File file = new File(getFilesDir() + "/" + filename);
        System.out.println(getFilesDir() + "/" + filename);

        if (file.exists()){
            System.out.println("Arquivo existe!");
            Utils.msg("Existe um registro de consultas", getApplicationContext());
            btnApagarResultados.setVisibility(View.VISIBLE);
        } else {
            System.out.println("Arquivo NÃO existe!");
            Utils.msg("Não há um registro de consultas", getApplicationContext());
            btnApagarResultados.setVisibility(View.INVISIBLE);
        }
    }

    public void startListContactsActivity(View view, Salario result){
        Intent ResultadoActivity = new Intent(this, ResultadoActivity.class);
        ResultadoActivity.putExtra("salario_liquido", result.getSalarioLiquido().toString());
        ResultadoActivity.putExtra("total_descontos", result.getTotalDescontos().toString());
        ResultadoActivity.putExtra("percentual_descontos", result.getPercentualDescontos().toString());
        startActivity(ResultadoActivity);
    }

    public Boolean validation(String salario){
        Boolean check = true;
        if (salario.isEmpty()) {
            check = false;
        }
        return check;
    }


    // Retorna o valor do desocnto do INSS
    public Double inss(Double salarioBruto){
        Double inss = 0.0;

        if (salarioBruto <= 1659.38) {
            inss = salarioBruto * 0.08;
        } else if(salarioBruto > 1659.38 && salarioBruto <= 2765.66) {
            inss = salarioBruto * 0.09;
        } else if(salarioBruto > 2765.66 && salarioBruto <= 5531.31) {
            inss = salarioBruto * 0.11;
        } else {
            inss =  608.44;
        }
        System.out.println("INSS: " + inss);
        return inss;
    }

    // Retorna o valor do desocnto do IRPF
    public Double irpf(Double salarioBruto){
        Double irpf = 0.0;
        if (salarioBruto <= 1903.98) {
            irpf = 0.0;
        } else if(salarioBruto > 1903.98 && salarioBruto <= 2826.65) {
            irpf = salarioBruto * 0.075;
        } else if(salarioBruto > 2826.65 && salarioBruto <= 3751.05) {
            irpf = salarioBruto * 0.15;
        } else if(salarioBruto > 3751.05 && salarioBruto <= 4664.68) {
            irpf = salarioBruto * 0.225;
        } else {
            irpf =  salarioBruto * 0.275;
        }
        System.out.println("IRPF: " + Double.valueOf(round(irpf)));
        return Double.valueOf(round(irpf));
    }

    // Retorna o valor para os dependentes
    public Double dependentes(Integer qtdDependentes){
        Double valorDependentes = 0.0;
        valorDependentes = 189.59 * qtdDependentes;
        System.out.println("Dependentes: " + valorDependentes);
        return valorDependentes;
    }

    //Calcula o salario e seus descontos
    public Salario calcular(Double salarioBruto, Double inss, Double irpf, Double pensao, Double dependentes, Double saude, Double outros){

        //Salário Líquido
        Double descontos = inss + irpf + pensao + dependentes + saude + outros;
        Double salarioLiquido = salarioBruto - descontos;

        Double pctDescontos = Double.valueOf(round((descontos / salarioBruto)*100));

        System.out.println("Porcentagem: " + pctDescontos);
        System.out.println("Liquido: " + salarioLiquido);

        salario = new Salario(salarioLiquido, descontos, pctDescontos);

        return salario;
    }

    //private Integer qtdDependentes;
    //private Double pensaoAlimenticia;
    //private Double planoSaude;
    //private Double outrosDescontos;

    //private Double salarioLiquido;
    //private Double totalDescontos;
    //private Double porcentagemDescontos;

    public void saveConsulta(Integer qtdDependentes, Double pensaoAlimenticia, Double planoSaude, Double outrosDescontos, Salario salario){
        FileOutputStream fos;
        Consulta consulta = new Consulta(qtdDependentes, pensaoAlimenticia, planoSaude, outrosDescontos, salario.getSalarioLiquido(), salario.getTotalDescontos(), salario.getPercentualDescontos());

        System.out.println("Objeto consulta:\n");
        System.out.println(consulta.getDataConsulta() + "\n");
        System.out.println(consulta.getHorarioConsulta() + "\n");
        System.out.println(consulta.getSalarioLiquido() + "\n");

        try {
            fos = openFileOutput(filename, Context.MODE_APPEND);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(consulta);
            oos.close();
            fos.close();

        } catch (Exception event) {
            event.printStackTrace();
        }

        // Verifica se o arquivo txt já existe
        File file = new File(getFilesDir() + "/" + filename);
        if (file.exists()){
            btnApagarResultados.setVisibility(View.VISIBLE);
            Utils.msg("Arquivo de registro criado com suceso!", getApplicationContext());
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_calcular ) {

            String strSalario = salariobruto.getText().toString();

            // Verifica se o campo Quantidade de Dependentes é vazio e atribui o valor a variável.

            Integer intQtdDependente;

            if (qtdDependente.getText().toString().isEmpty()){
                intQtdDependente = 0;
            } else {
                intQtdDependente = Integer.parseInt(qtdDependente.getText().toString());
            }

            // Verifica se o campo Quantidade de Dependentes é vazio e atribui o valor a variável.

            Double dblPensaoAlimenticia;

            if (pensaoAlimenticia.getText().toString().isEmpty()){
                dblPensaoAlimenticia = 0.0;
            } else {
                dblPensaoAlimenticia = Double.parseDouble(pensaoAlimenticia.getText().toString());
            }

            Double dblPlanoSaude;

            if (planoSaude.getText().toString().isEmpty()){
                dblPlanoSaude = 0.0;
            } else {
                dblPlanoSaude = Double.parseDouble(planoSaude.getText().toString());
            }

            Double dblOutrosDescontos;

            if (outrosDescontos.getText().toString().isEmpty()){
                dblOutrosDescontos = 0.0;
            } else {
                dblOutrosDescontos = Double.parseDouble(outrosDescontos.getText().toString());
            }

            if (validation(strSalario)){

                // Valores para calculo
                Double dblSalario = Double.parseDouble(strSalario);
                Double totalIRPF = irpf(dblSalario);
                Double totalINSS = inss(dblSalario);
                Double totalDependentes = dependentes(intQtdDependente);

                // Double salarioBruto, Double inss, Double irpf, Double dependentes
                Salario resultadoSalario = calcular(dblSalario, totalINSS, totalIRPF, dblPensaoAlimenticia, totalDependentes, dblPlanoSaude, dblOutrosDescontos);

                saveConsulta(intQtdDependente, dblPensaoAlimenticia, dblPlanoSaude, dblOutrosDescontos, salario);

                startListContactsActivity(v, resultadoSalario);

            } else {
                Utils.msg("Campo 'Salário Bruto' é obrigátorio", getApplicationContext());
            }


        } else if(v.getId() == R.id.btn_apagar_resultados) {
            Context myContext = getApplicationContext();
            myContext.deleteFile(filename);

            // Verifica se o arquivo txt já existe
            File file = new File(getFilesDir() + "/" + filename);
            System.out.println(getFilesDir() + "/" + filename);

            if (file.exists()){
                btnApagarResultados.setVisibility(View.VISIBLE);
            } else {
                btnApagarResultados.setVisibility(View.INVISIBLE);
                Utils.msg("O registro de consultas foi excluído", getApplicationContext());
            }

        } else {

        }
    }
}
