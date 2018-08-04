package br.com.infnet.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.infnet.todolist.adapters.TaskAdapter;
import br.com.infnet.todolist.adapters.TaskCompleteAdapter;
import br.com.infnet.todolist.models.Task;

public class CompleteTasksActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    ListView listeview;

    private ArrayList<Task> tasksArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_tasks);

        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        listeview = findViewById(R.id.listview_complete_tasks);
        tasksArrayList = new ArrayList<Task>();

        initFirebase();

        currentUser = mAuth.getCurrentUser();
        getDatabase();

    }

    private void initFirebase() {
        FirebaseApp.initializeApp(CompleteTasksActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    private void getDatabase() {
        databaseReference.child("tasks").orderByChild("uid").equalTo(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tasksArrayList.clear();
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Task objTask = objSnapshot.getValue(Task.class);

                    if (objTask.getStatus() == true) {
                        tasksArrayList.add(objTask);
                    }

                    Log.i("OBJ", objTask.getTitle());
                }

                TaskCompleteAdapter adapterItem = new TaskCompleteAdapter(CompleteTasksActivity.this, tasksArrayList);
                listeview.setAdapter(adapterItem);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
