package br.com.infnet.atividades;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

import br.com.infnet.atividades.models.Utils;

public class StartActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    LoginButton loginButton;
    TextView status;

    Button btnCadastro;
    Button btnLogin;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    private String nome;

    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        flag = true;

        FirebaseUser currentUser = null;

        initFirebase();
        initControls();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        status.setText("Logado com sucesso\n"+loginResult.getAccessToken());

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
                        status.setText("Login Cancelado!");
                        LoginManager.getInstance().logOut();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        status.setText("Login Error:" + exception.getMessage());
                        Log.d("myTag", exception.getMessage());
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null || isLoggedIn()) {

            if (currentUser != null) {
                startMainActivity(currentUser.getEmail());
            } else {
                Profile profile = Profile.getCurrentProfile();
                startMainActivity(profile.getName());
            }
        }
        System.out.println(currentUser);
        //updateUI(currentUser);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void initControls(){
        callbackManager = CallbackManager.Factory.create();
        status = findViewById(R.id.txt_status);
        loginButton = findViewById(R.id.login_button);
        btnCadastro = findViewById(R.id.btn_cadastro);
        btnLogin = findViewById(R.id.btn_login);
    }

    public void startCadastroActivity(View view){
        Intent cadastroActivity = new Intent(this, CadastroActivity.class);
        startActivity(cadastroActivity);
    }

    public void startLoginActivity(View view){
        Intent loginActivity = new Intent(this, LoginActivity.class);
        startActivity(loginActivity);
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(StartActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public void startMainActivity(String nome){
        Intent mainActivity = new Intent(this, MainActivity.class);
        mainActivity.putExtra("user", nome);
        startActivity(mainActivity);
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
}