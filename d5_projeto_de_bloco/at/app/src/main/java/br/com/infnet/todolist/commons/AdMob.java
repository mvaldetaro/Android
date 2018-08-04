package br.com.infnet.todolist.commons;

import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import br.com.infnet.todolist.R;

/**
 * Created by Magno on 25/11/2017.
 */

public class AdMob {
    public static void showAdView(AdView view){
        AdView adView = view;
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);
    }
}
