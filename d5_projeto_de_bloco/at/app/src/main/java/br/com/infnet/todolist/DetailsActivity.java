package br.com.infnet.todolist;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import br.com.infnet.todolist.adapters.CommentAdapter;
import br.com.infnet.todolist.adapters.TaskAdapter;
import br.com.infnet.todolist.commons.AdMob;
import br.com.infnet.todolist.commons.Utils;
import br.com.infnet.todolist.models.Comment;
import br.com.infnet.todolist.models.Task;
import br.com.infnet.todolist.models.User;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener, EditTextDialog.EditDialogListener {

    private Task objTask;
    private AdView adView;
    private int flag;

    private Calendar calendar;
    private DateFormat dateFormat;

    private TextView titleTextView, annotationTextView, dateTextView, statusTextView;
    private EditText commentEditText;
    private Button btnAddComment;
    private ListView listViewComments;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private ArrayList<Comment> commentsArrayList;
    private User userData;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        flag = 0;
        adView = (AdView) findViewById(R.id.adView);
        AdMob.showAdView(adView);

        objTask = (Task) getIntent().getSerializableExtra("task");

        init();
        initFirebase();

        currentUser = mAuth.getCurrentUser();

        getDataUser();
        getDataComments();

        Log.i("CURRENT_USER", currentUser.getUid());
        System.out.println(currentUser);

        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance();

        context = getApplicationContext();
    }

    public void init(){
        titleTextView = findViewById(R.id.task_title);
        annotationTextView = findViewById(R.id.task_description);
        dateTextView = findViewById(R.id.task_date);
        statusTextView = findViewById(R.id.msg_no_comments);
        listViewComments = findViewById(R.id.listview_comments);
        commentEditText = findViewById(R.id.edittext_comment);
        btnAddComment = findViewById(R.id.btn_comment);

        commentsArrayList = new ArrayList<Comment>();

        titleTextView.setText(objTask.getTitle());

        if (objTask.getAnnotation() != null ) {
            annotationTextView.setText(objTask.getAnnotation());
        } else {
            annotationTextView.setAlpha((float) 0.5);
            annotationTextView.setText("Adicionar nota");
        }

        if (objTask.getDate() != null ) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd 'de' MMM 'de' yyyy");
            String dateText = simpleDateFormat.format(objTask.getDate());
            dateTextView.setText(dateText);
        } else {
            dateTextView.setAlpha((float) 0.5);
            dateTextView.setText("Adicionar data");
        }

        titleTextView.setOnClickListener(this);
        annotationTextView.setOnClickListener(this);
        dateTextView.setOnClickListener(this);
        btnAddComment.setOnClickListener(this);
    }

    public void updateTaskTitle(String newTitle){
        objTask.setTitle(newTitle);
        databaseReference.child("tasks").child(objTask.getId()).setValue(objTask);
    }

    public void updateTaskAnnotation(String newAnnotation){
        objTask.setAnnotation(newAnnotation);
        databaseReference.child("tasks").child(objTask.getId()).setValue(objTask);
    }

    public void updateTaskDate(){
        dateTextView.setText(dateFormat.format(calendar.getTime()));
        dateTextView.setAlpha((float) 1.0);
        objTask.setDate(calendar.getTime());
        databaseReference.child("tasks").child(objTask.getId()).setValue(objTask);
    }

    public void showDialog() {
        new DatePickerDialog(this, listenerDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener listenerDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateTaskDate();
        }
    };

    public void addTaskComment(){
        String strComment = commentEditText.getText().toString();
        String idComment = UUID.randomUUID().toString();
        Comment objComment = new Comment(idComment, currentUser.getUid(), userData.getNome(), strComment);

        databaseReference.child("tasks").child(objTask.getId()).child("comments").child(objComment.getId()).setValue(objComment);

        commentEditText.setText("");

        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void openEditDialog(){
        EditTextDialog editTextDialog = new EditTextDialog();

        Bundle args = new Bundle();
        switch (flag){
            case 1:
                args.putString("STRING_CONTEXT", objTask.getTitle());
                args.putString("DIALOG_TITLE", "Editar Título");
                break;
            case 2:
                args.putString("STRING_CONTEXT", objTask.getAnnotation());
                args.putString("DIALOG_TITLE", "Editar Descrição");
                break;
        }

        editTextDialog.setArguments(args);
        editTextDialog.show(getSupportFragmentManager(), "Edit Dialog");

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.task_title ) {
            flag = 1;
            openEditDialog();
        } else if(v.getId() == R.id.task_description){
            flag = 2;
            openEditDialog();

        } else if(v.getId() == R.id.task_date){
            flag = 0;
            showDialog();
        } else if(v.getId() == R.id.btn_comment){
            flag = 0;
            addTaskComment();
        }
    }

    @Override
    public void applyText(String text) {
        Log.i("TEXT", text);
        switch (flag){
            case 1:
                if(!text.equals("EMPTY_STRING_IGNORE_DATA")) {
                    titleTextView.setText(text);
                    updateTaskTitle(text);
                }
                break;
            case 2:
                if(!text.equals("EMPTY_STRING_IGNORE_DATA")) {
                    annotationTextView.setText(text);
                    annotationTextView.setAlpha((float) 1.0);
                    updateTaskAnnotation(text);
                }
                break;
        }
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(DetailsActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    private void getDataComments(){
        databaseReference.child("tasks").child(objTask.getId()).child("comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                commentsArrayList.clear();
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Comment objComment = objSnapshot.getValue(Comment.class);
                    commentsArrayList.add(objComment);
                    Log.i("OBJ", String.valueOf(objSnapshot));
                }

                if (!commentsArrayList.isEmpty()) {
                    statusTextView.setVisibility(View.INVISIBLE);
                    listViewComments.setVisibility(View.VISIBLE);

                    CommentAdapter adapterItem = new CommentAdapter(DetailsActivity.this, commentsArrayList);
                    listViewComments.setAdapter(adapterItem);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getDataUser(){
        databaseReference.child("users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userData = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
