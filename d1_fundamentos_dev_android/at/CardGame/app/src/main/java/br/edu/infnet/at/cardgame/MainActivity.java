package br.edu.infnet.at.cardgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    ImageView cardUm;
    ImageView cardDois;
    ImageView cardTres;

    List<String> cards = new ArrayList<>();

    Button btnReebot;
    Button btnConfirm;

    boolean cardUmMostraVerso = true;
    boolean cardDoisMostraVerso = true;
    boolean cardTresMostraVerso = true;

    Animation toCenter;
    Animation fromCenter;

    TextView feedback;

    int flagCard = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardUm = (ImageView)findViewById(R.id.card_um);
        cardDois = (ImageView)findViewById(R.id.card_dois);
        cardTres = (ImageView)findViewById(R.id.card_tres);

        cardUm.setOnClickListener(this);
        cardDois.setOnClickListener(this);
        cardTres.setOnClickListener(this);

        fromCenter = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_uncentralizer);
        fromCenter.setAnimationListener(this);

        feedback = (TextView)findViewById(R.id.feedback);

        btnReebot = (Button)findViewById(R.id.btn_reboot);
        btnConfirm = (Button)findViewById(R.id.btn_confirm);

        btnReebot.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);

        startUp();
    }

    private void startUp() {
        cards.clear();
        cards.add("a");
        cards.add("k");
        cards.add("k");

        Collections.shuffle(cards);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    private void showAllCards() {
        // primeiro card
        if (cards.get(0).equals("a")) {
            cardUm.setImageResource(R.drawable.frente);
            cardUm.setAnimation(fromCenter);
            cardUm.startAnimation(fromCenter);
        } else if(cards.get(0).equals("k")){
            cardUm.setImageResource(R.drawable.coringa);
            cardUm.setAnimation(fromCenter);
            cardUm.startAnimation(fromCenter);
        }
        // segundo card
        if (cards.get(1).equals("a")) {
            cardDois.setImageResource(R.drawable.frente);
            cardDois.setAnimation(fromCenter);
            cardDois.startAnimation(fromCenter);
        } else if(cards.get(1).equals("k")){
            cardDois.setImageResource(R.drawable.coringa);
            cardDois.setAnimation(fromCenter);
            cardDois.startAnimation(fromCenter);
        }
        // terceiro card
        if (cards.get(2).equals("a")) {
            cardTres.setImageResource(R.drawable.frente);
            cardTres.setAnimation(fromCenter);
            cardTres.startAnimation(fromCenter);
        } else if(cards.get(2).equals("k")){
            cardTres.setImageResource(R.drawable.coringa);
            cardTres.setAnimation(fromCenter);
            cardTres.startAnimation(fromCenter);
        }

        btnReebot.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_reboot ) {
            newGame();
        } else if (v.getId() == R.id.btn_confirm ){
            showAllCards();
            validation();
        } else {
            if(v.getId() == R.id.card_um) {
                flagCard = 0;
                btnConfirm.setEnabled(true);
            } else if(v.getId() == R.id.card_dois) {
                flagCard = 1;
                btnConfirm.setEnabled(true);
            } else if(v.getId() == R.id.card_tres) {
                flagCard = 2;
                btnConfirm.setEnabled(true);
            }
        }
    }

    private void validation() {
        btnConfirm.setEnabled(false);
        if (flagCard == 0) {
            if (cards.get(0).equals("a")) {
                feedback.setText(getResources().getString(R.string.fdb_win));
            } else {
                feedback.setText(getResources().getString(R.string.fdb_fail));
            }
        } else if (flagCard == 1){
            if (cards.get(1).equals("a")) {
                feedback.setText(getResources().getString(R.string.fdb_win));
            } else {
                feedback.setText(getResources().getString(R.string.fdb_fail));
            }
        } else if (flagCard == 2){
            if (cards.get(2).equals("a")) {
                feedback.setText(getResources().getString(R.string.fdb_win));
            } else {
                feedback.setText(getResources().getString(R.string.fdb_fail));
            }
        }
    }

    private void newGame() {
        Collections.shuffle(cards); //trocar para random

        btnReebot.setVisibility(View.INVISIBLE);

        cardUm.setImageResource(R.drawable.verso);
        cardDois.setImageResource(R.drawable.verso);
        cardTres.setImageResource(R.drawable.verso);

        feedback.setText("");

        Animation anim_one = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_scroll_one);
        Animation anim_two = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_scroll_two);
        Animation anim_tree = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_scroll_tree);

        cardUm.startAnimation(anim_one);
        cardDois.startAnimation(anim_two);
        cardTres.startAnimation(anim_tree);

        cardUmMostraVerso = cardDoisMostraVerso = cardTresMostraVerso = true;
    }
}
