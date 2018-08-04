package br.com.infnet.todolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.view.inputmethod.EditorInfo;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.UUID;

import br.com.infnet.todolist.commons.Utils;
import br.com.infnet.todolist.adapters.TaskAdapter;
import br.com.infnet.todolist.models.Task;
import br.com.infnet.todolist.models.TasksList;
import br.com.infnet.todolist.services.TasksService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "Firebase_AUTH";

    CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    Menu menu;

    EditText inputNewTask;
    ListView listViewTasks;
    Button btnCompleteTasks;

    private ArrayList<Task> tasksArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };

        initFirebase();
        init();

        currentUser = mAuth.getCurrentUser();
        getDatabase();

    }

    @Override
    public void onStart() {
        super.onStart();

        if (currentUser == null) {
            startLoginActivity();
        } else {
            Log.i(TAG, "CurrentUser: " + currentUser.getUid());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                startLoginActivity();
                return true;

            case R.id.action_about:
                startAboutActivity();
                return true;

            case R.id.action_privacy:
                startPPActivity();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_goto_complete_tasks ) {
            startCompleteActivity();
        }
    }

    public void init(){
        inputNewTask = findViewById(R.id.input_new_task);
        listViewTasks = findViewById(R.id.listview_tasks);
        btnCompleteTasks = findViewById(R.id.btn_goto_complete_tasks);

        btnCompleteTasks.setOnClickListener(this);
        inputNewTask.setOnEditorActionListener(newTask);

        tasksArrayList = new ArrayList<Task>();
    }

    public void startLoginActivity(){
        Intent loginActivity = new Intent(this, LoginActivity.class);
        startActivity(loginActivity);
    }

    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken == null) {

            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser == null) {
                startLoginActivity();
            } else {
                Log.e(TAG, "User: " + currentUser.getDisplayName());
            }
        }
    }

    public void startCompleteActivity(){
        Intent completeActivity = new Intent(this, CompleteTasksActivity.class);
        startActivity(completeActivity);
    }

    public void startAboutActivity(){
        Intent aboutActivity = new Intent(this, AboutActivity.class);
        startActivity(aboutActivity);
    }

    public void startPPActivity(){
        Intent ppActivity = new Intent(this, PolicyPivacityActivity.class);
        startActivity(ppActivity);
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    TextView.OnEditorActionListener newTask = new TextView.OnEditorActionListener(){

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId == EditorInfo.IME_ACTION_DONE){
                newTask(v);
            }
            return false;
        }
    };

    public void newTask(TextView task) {
        Utils.msg("Nova Tarefa Adicionada", getApplicationContext());
        String idTask = UUID.randomUUID().toString();
        Task objTask = new Task(idTask, currentUser.getUid(), task.getText().toString());
        databaseReference.child("tasks").child(objTask.getId()).setValue(objTask);
        task.setText("");
        Log.i("USER_OBJECT_ID:", objTask.getId());
    }

    private void getDatabase() {
        databaseReference.child("tasks").orderByChild("uid").equalTo(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tasksArrayList.clear();
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Task objTask = objSnapshot.getValue(Task.class);

                    if (objTask.getStatus() == false) {
                        tasksArrayList.add(objTask);
                    }

                    Log.i("OBJ", objTask.getTitle());
                }

                TaskAdapter adapterItem = new TaskAdapter(MainActivity.this, tasksArrayList);
                listViewTasks.setAdapter(adapterItem);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
