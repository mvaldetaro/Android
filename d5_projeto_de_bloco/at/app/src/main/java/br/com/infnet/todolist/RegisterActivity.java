package br.com.infnet.todolist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.infnet.todolist.commons.AdMob;
import br.com.infnet.todolist.commons.Utils;
import br.com.infnet.todolist.models.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "Firebase_AUTH";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private TextView nome, login, senha, senha_check;
    private ArrayList<TextView> camposForm;

    private Button btnClear, btnSave;

    String uid, strNome, strLogin, strSenha, strSenhaCheck;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        adView = (AdView) findViewById(R.id.adView);
        AdMob.showAdView(adView);

        init();
        initFirebase();
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

            int verify = validation(strNome, strLogin, strSenha, strSenhaCheck);

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
                saveData(strNome, strLogin, strSenha);
            }
        }

    }

    private void init(){

        nome =  findViewById(R.id.input_nome);
        login =  findViewById(R.id.input_email);
        senha =  findViewById(R.id.input_senha);
        senha_check = findViewById(R.id.input_senha_check);

        btnClear = (Button) findViewById(R.id.btn_clear);
        btnSave = (Button) findViewById(R.id.btn_save);

        camposForm = new ArrayList<TextView> ();

        camposForm.add(nome);
        camposForm.add(login);
        camposForm.add(senha);
        camposForm.add(senha_check);

        btnClear.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(RegisterActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public void startMainActivity(){
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    public void cleanerForm(ArrayList<TextView> campos){
        for (int i = 0, count = campos.size(); i < count; ++i) {
            TextView item = campos.get(i);
            item.setText("");
        }
        Utils.msg("Todos os campos foram limpos", getApplicationContext());
    }

    public int validation(String n, String l, String s, String sc){
        int check = 0;

        Pattern regexNome = Pattern.compile("[a-zA-Z.? ]*");
        Matcher matcherNome;

        if (n.isEmpty() || l.isEmpty() || s.isEmpty() || sc.isEmpty()) {
            check = 1;
        } else {
            matcherNome = regexNome.matcher(n);

            if(!matcherNome.matches()) {
                check = 2;
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(l).matches()) {
                check = 3;
            } else if (!s.equals(sc)){
                check = 4;
            }
        }

        return check;
    }

    public void saveData(String nom, String log, String sen){

        final User newUser = new User(nom, log, sen);

        mAuth.createUserWithEmailAndPassword(newUser.getLogin(), newUser.getSenha())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        uid = user.getUid();
                        User fireUser = newUser;
                        fireUser.setUid(uid);

                        databaseReference.child("users").child(fireUser.getUid()).setValue(fireUser);
                        cleanerForm(camposForm);

                        Utils.msg("Dados salvos com sucesso", getApplicationContext());
                        startMainActivity();

                    } else {

                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }
}

