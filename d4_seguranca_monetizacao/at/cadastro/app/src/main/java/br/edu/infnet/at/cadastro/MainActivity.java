package br.edu.infnet.at.cadastro;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText nome;
    public EditText login;
    public EditText senha;
    public EditText senha_check;
    public EditText cpf;

    public ArrayList<EditText> camposForm;

    public Button btnClear;
    public Button btnSave;
    public Button btnView;

    String strNome;
    String strLogin;
    String strSenha;
    String strSenhaCheck;
    String strCPF;

    String filename = "contatos.txt";

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-9332400519043522~8082443389");

        nome = (EditText) findViewById(R.id.ipt_nome);
        login = (EditText) findViewById(R.id.ipt_login);
        senha = (EditText) findViewById(R.id.ipt_senha);
        senha_check = (EditText) findViewById(R.id.ipt_senha_check);
        cpf = (EditText) findViewById(R.id.ipt_cpf);

        btnClear = (Button) findViewById(R.id.btn_clear);
        btnSave = (Button) findViewById(R.id.btn_save);

        btnView = (Button) findViewById(R.id.btn_view_contacts);

        camposForm = new ArrayList<EditText> ();

        camposForm.add(nome);
        camposForm.add(login);
        camposForm.add(senha);
        camposForm.add(senha_check);
        camposForm.add(cpf);

        btnClear.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        File file = new File(getFilesDir() + "/" + filename);
        System.out.println(getFilesDir() + "/" + filename);

        if (file.exists()){
            System.out.println("Arquivo existe!");
            btnView.setVisibility(View.VISIBLE);
        } else {
            System.out.println("Arquivo NÃO existe!");
            btnView.setVisibility(View.INVISIBLE);
        }

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9332400519043522/7023554725");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public void startListContactsActivity(View view){
        Intent listContactsActivity = new Intent(this, ListContactsActivity.class);
        listContactsActivity.putExtra("file_name", filename);
        startActivity(listContactsActivity);
    }

    //strNome, strLogin, strSenha, strSenhaCheck, strCPF
    public int validation(String n, String l, String s, String sc, String c){
        int check = 0;

        Pattern regexNome = Pattern.compile("[a-zA-Z0-9.? ]*");
        Pattern regexCPF = Pattern.compile("^([0-9]{3}\\.?){3}-?[0-9]{2}$");
        Matcher matcherNome;
        Matcher matcherCPF;

        if (n.isEmpty() || l.isEmpty() || s.isEmpty() || sc.isEmpty() || c.isEmpty()) {
            check = 1;
        } else {

            System.out.println("Nome");
            System.out.println(n);
            matcherNome = regexNome.matcher(n);
            System.out.println(matcherNome);
            System.out.println(matcherNome.matches());


            System.out.println("Email");
            System.out.println(android.util.Patterns.EMAIL_ADDRESS.matcher(l).matches());

            System.out.println("Senha");
            System.out.println(s);
            System.out.println(sc);
            System.out.println(!s.equals(sc));

            System.out.println("CPF");
            System.out.println(c);
            matcherCPF = regexCPF.matcher(c);
            System.out.println(matcherCPF.matches());

            if(!matcherNome.matches()) {
                check = 2;
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(l).matches()) {
                check = 3;
            } else if (!s.equals(sc)){
                check = 4;
            } else if(!matcherCPF.matches()){
                check = 5;
            }
        }

        return check;
    }

    public void cleanerForm(ArrayList<EditText> campos){
        for (int i = 0, count = campos.size(); i < count; ++i) {
            EditText item = campos.get(i);
            item.setText("");
        }
        Utils.msg("Todos os campos foram limpos", getApplicationContext());
    }

    public void saveData(String n, String l, String s, String c){
        FileOutputStream fos;
        Contato contato = new Contato(n, l, s, c);

        try {
            fos = openFileOutput(filename, Context.MODE_APPEND);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(contato);
            oos.close();
            fos.close();

            Utils.msg("Informações salvas", getApplicationContext());
            btnView.setVisibility(View.VISIBLE);

        } catch (Exception event) {
            event.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_clear ) {
            cleanerForm(camposForm);
        } else if (v.getId() == R.id.btn_save ){


            strNome = nome.getText().toString();
            strLogin = login.getText().toString();
            strSenha = senha.getText().toString();
            strSenhaCheck = senha_check.getText().toString();
            strCPF = cpf.getText().toString();

            int verify = validation(strNome, strLogin, strSenha, strSenhaCheck, strCPF);

            if (verify != 0) {
                switch (verify) {
                    case 1:
                        Utils.msg("Todos os campos são obrigatórios", getApplicationContext());
                        break;
                    case 2:
                        Utils.msg("Apenas letras são permitidas no campo nome", getApplicationContext());
                        break;
                    case 3:
                        Utils.msg("Email inválido", getApplicationContext());
                        break;
                    case 4:
                        Utils.msg("As senhas devem ser iguais", getApplicationContext());
                        break;
                    case 5:
                        Utils.msg("CPF inávlido", getApplicationContext());
                        break;
                }
            } else {
                saveData(strNome, strLogin, strSenha, strCPF);
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("Ads", "A propaganda não foi carregada ainda.");
                }
            }

        } else {

        }
    }
}

