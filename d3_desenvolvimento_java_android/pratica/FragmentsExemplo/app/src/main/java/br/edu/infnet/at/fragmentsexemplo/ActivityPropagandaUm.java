package br.edu.infnet.at.fragmentsexemplo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

// Estamos herdando da classe FragmentActivity e não diretamente da classe Activity do Android.
// Se tiver trabalhando com Fragment dentro de uma Activity, lembre-se sempre de herdar FragmentActivity,
// esquecer isso é um erro comum.

public class ActivityPropagandaUm extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propaganda_um);
    }

    public void onClickTelaDois(View view){
        //Intent intent = new Intent(this, ActivityPropagandaDois.class);
        //startActivity(intent);
    }
}
