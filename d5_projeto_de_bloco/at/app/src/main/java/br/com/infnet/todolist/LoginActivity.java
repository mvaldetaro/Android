package br.com.infnet.todolist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.infnet.todolist.commons.AdMob;
import br.com.infnet.todolist.commons.Utils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private String nome;

    private Button btnLogar, btnCadastrar;
    private TextView login, senha;
    private AdView adView;

    CallbackManager callbackManager;
    LoginButton loginButton;

    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private static String TAG = "FB_AUTH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        adView = (AdView) findViewById(R.id.adView);
        AdMob.showAdView(adView);

        initFirebase();
        init();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        // Graph API Facebook
                        AccessToken accessToken = loginResult.getAccessToken();
                        Profile profile = Profile.getCurrentProfile();

                        System.out.println(accessToken);
                        System.out.println(profile);

                        GraphRequest req = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivty Response ", response.toString());

                                try{
                                    nome = object.getString("name");
                                    Log.v("Nome ", nome);
                                    Utils.msg("Conectado como: " + nome, getApplicationContext());
                                    startMainActivity(nome);
                                } catch(JSONException e){
                                    Log.e("LoginActivty Error ", e.getMessage());
                                }
                            }
                        });

                        Bundle param = new Bundle();
                        param.putString("fields", "id,name,email,gender,birthday");
                        req.setParameters(param);
                        req.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        LoginManager.getInstance().logOut();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("myTag", exception.getMessage());
                    }
                });
    }

    public void init() {
        callbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();

        btnLogar = findViewById(R.id.btn_login);
        btnCadastrar = findViewById(R.id.btn_cadastro);
        login = findViewById(R.id.input_login);
        senha = findViewById(R.id.input_senha);

        btnLogar.setOnClickListener(this);
        btnCadastrar.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null || isLoggedIn()) {

            if (currentUser != null) {
                startMainActivity(currentUser.getUid());
            } else {
                Profile profile = Profile.getCurrentProfile();
                startMainActivity(profile.getId());
            }
        }
        System.out.println("CurrentUser " + currentUser);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login ) {

            String email = login.getText().toString();
            String password = senha.getText().toString();

            if(validation(email, password) == false){
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setTitle("Falha no login:");
                alertDialog.setMessage("Por favor, preencha os campos ou cadastre-se.");
                alertDialog.show();
            } else {
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
            };

        } else if(v.getId() == R.id.btn_cadastro){
            startRegisterActivity();
        }
    }

    private void updateUI(FirebaseUser user) {
        startMainActivity(user.getEmail());
    }

    public void startMainActivity(String uid){
        Intent mainActivity = new Intent(this, MainActivity.class);
        mainActivity.putExtra("uid", uid);
        startActivity(mainActivity);
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(LoginActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public void startRegisterActivity(){
        Intent registerActivity = new Intent(this, RegisterActivity.class);
        startActivity(registerActivity);
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public void loginWithFacebook() {
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        // Graph API Facebook
                        AccessToken accessToken = loginResult.getAccessToken();
                        Profile profile = Profile.getCurrentProfile();

                        System.out.println(accessToken);
                        System.out.println(profile);

                        GraphRequest req = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivty Response ", response.toString());

                                try{
                                    nome = object.getString("name");
                                    Log.v("Nome ", nome);
                                    Utils.msg("Conectado como: " + nome, getApplicationContext());
                                    startMainActivity(nome);
                                } catch(JSONException e){
                                    Log.e("LoginActivty Error ", e.getMessage());
                                }
                            }
                        });

                        Bundle param = new Bundle();
                        param.putString("fields", "id,name,email,gender,birthday");
                        req.setParameters(param);
                        req.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        LoginManager.getInstance().logOut();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("myTag", exception.getMessage());
                    }
                });
    }

    public boolean validation(String login, String senha){
        boolean check = true;
        if (login.isEmpty() || senha.isEmpty()) {
            check = false;
        }
        Log.i("VALID", String.valueOf(check));
        return check;
    }
}
