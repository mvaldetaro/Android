package br.com.infnet.atividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.infnet.atividades.models.Atividades;
import br.com.infnet.atividades.models.Tarefa;
import br.com.infnet.atividades.models.Utils;
import br.com.infnet.atividades.services.AtividadesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private static final String TAG = "MSG";
    public String user;
    public TextView usr_email;
    ListView listview;
    private ArrayList<Tarefa> listTarefas;
    private String nome;
    Button btnLogout;

    private AccessTokenTracker accessTokenTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        usr_email = findViewById(R.id.txt_email);
        usr_email.setText("Conectado como: " + user);
        btnLogout = findViewById(R.id.btn_logout);

        listview = (ListView) findViewById(R.id.listView);

        listTarefas = new ArrayList<Tarefa>();

        btnLogout.setOnClickListener(this);



        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AtividadesService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AtividadesService service = retrofit.create(AtividadesService.class);

        Call<Atividades> reqAtividades = service.atividades();

        reqAtividades.enqueue(new Callback<Atividades>(){

            @Override
            public void onResponse(Call<Atividades> call, Response<Atividades> response) {
                if (!response.isSuccessful()){
                    Log.i(TAG, "Error: " + response.code());
                } else {
                    Atividades atividades = response.body();

                    for (Tarefa a : atividades.getTarefa()) {
                        listTarefas.add(a);
                        System.out.println(a);
                        Log.i(TAG, "ID: " + a.getId());
                        Log.i(TAG, "Descrição: " + a.getDescricao());
                    }

                    TarefaAdapter adapterItem = new TarefaAdapter(MainActivity.this, listTarefas);
                    listview.setAdapter(adapterItem);
                }
            }

            @Override
            public void onFailure(Call<Atividades> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_logout ) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();

            startStartActivity();
        }
    }

    public void startStartActivity(){
        Intent startActivity = new Intent(this, StartActivity.class);
        startActivity(startActivity);
    }

    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken == null) {

            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser == null) {
                startStartActivity();
            }

        }
    }
}
