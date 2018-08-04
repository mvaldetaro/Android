package br.com.infnet.atividades;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    public Button btnLogar;
    public TextView login;
    public TextView senha;

    private static String TAG = "FB_AUTH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseAuth.getInstance().signOut();
        mAuth = FirebaseAuth.getInstance();

        btnLogar = findViewById(R.id.btn_usrlogin);
        login = findViewById(R.id.in_login);
        senha = findViewById(R.id.in_pass);

        btnLogar.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        System.out.println(currentUser);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_usrlogin ) {

            String email = login.getText().toString();
            String password = senha.getText().toString();

            System.out.println(email);
            System.out.println(password);

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Usuário inválido",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    private void updateUI(FirebaseUser user) {
        startMainActivity(user.getEmail());
    }

    public void startMainActivity(String email){
        Intent mainActivity = new Intent(this, MainActivity.class);
        mainActivity.putExtra("user", email);
        startActivity(mainActivity);
    }
}
