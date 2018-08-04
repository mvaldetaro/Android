package br.com.infnet.atividades;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.infnet.atividades.models.Usuario;
import br.com.infnet.atividades.models.Utils;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private static String TAG = "FB_AUTH";

    private FirebaseAuth mAuth;


    boolean flag;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        initControls();
        initFirebase();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        System.out.println(currentUser);
        //updateUI(currentUser);
    }

    private void initControls(){

        flag = true;

        nome = (EditText) findViewById(R.id.ipt_nome);
        login = (EditText) findViewById(R.id.ipt_login);
        senha = (EditText) findViewById(R.id.ipt_senha);
        senha_check = (EditText) findViewById(R.id.ipt_senha_check);
        cpf = (EditText) findViewById(R.id.ipt_cpf);

        btnClear = (Button) findViewById(R.id.btn_clear);
        btnSave = (Button) findViewById(R.id.btn_save);

        camposForm = new ArrayList<EditText> ();

        camposForm.add(nome);
        camposForm.add(login);
        camposForm.add(senha);
        camposForm.add(senha_check);
        camposForm.add(cpf);

        btnClear.setOnClickListener(this);
        btnSave.setOnClickListener(this);
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
                Utils.msg("Dados salvos com sucesso", getApplicationContext());
                startLoginActivity(v);
            }
        }

    }

    public int validation(String n, String l, String s, String sc, String c){
        int check = 0;

        Pattern regexNome = Pattern.compile("[a-zA-Z0-9.? ]*");
        Pattern regexCPF = Pattern.compile("^([0-9]{3}\\.?){3}-?[0-9]{2}$");
        Matcher matcherNome;
        Matcher matcherCPF;

        if (n.isEmpty() || l.isEmpty() || s.isEmpty() || sc.isEmpty() || c.isEmpty()) {
            check = 1;
        } else {
            matcherNome = regexNome.matcher(n);
            matcherCPF = regexCPF.matcher(c);

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

    public void saveData(String nome, String login, String senha, String cpf){
        String uuid = UUID.randomUUID().toString();
        Usuario user = new Usuario(uuid, nome, login, senha, cpf);

        databaseReference.child("users").child(user.getUid()).setValue(user);
        cleanerForm(camposForm);

        System.out.println(user);
        Log.i("USER_OBJECT_NAME:", user.getNome());

        mAuth.createUserWithEmailAndPassword(user.getLogin(), user.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CadastroActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }

    private void initFirebase() {
        FirebaseApp.initializeApp(CadastroActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public void startLoginActivity(View view){
        Intent loginActivity = new Intent(this, LoginActivity.class);
        startActivity(loginActivity);
    }
}
