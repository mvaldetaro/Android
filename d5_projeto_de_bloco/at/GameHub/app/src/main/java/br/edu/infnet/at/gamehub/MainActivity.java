package br.edu.infnet.at.gamehub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showSelectScreen(R.id.nav_meus_canais);

        // Carrega o anuncio na view do adMob
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Ações do menu de configurações
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent SetCanaisActivity = new Intent(this, SetCanaisActivity.class);
            startActivity(SetCanaisActivity);
        } else if (id == R.id.action_notify) {
            Intent SetNotificacoesActivity = new Intent(this, SetNotificacoesActivity.class);
            startActivity(SetNotificacoesActivity);
        }

        return super.onOptionsItemSelected(item);
    }


    private void showSelectScreen(int id){
        Fragment fragment = null;

        switch (id){
            case R.id.nav_meus_canais:
                fragment = new CanaisActivity();
                break;
            case R.id.nav_consoles:
                fragment = new ConsolesActivity();
                break;
            case R.id.nav_jogos:
                fragment = new JogosActivity();
                break;
            case R.id.nav_controllers:
                fragment = new JoystickActivity();
                break;
            case R.id.nav_acessorios:
                fragment = new AcessoriosActivity();
                break;
            case R.id.nav_perfil:
                fragment = new PerfilActivity();
                break;
            case R.id.nav_sobre:
                fragment = new SobreActivity();
                break;
            case R.id.nav_politicas:
                fragment = new PoliticasActivity();
                break;
        }

        if (fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    // Ações do menu de navegação
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        showSelectScreen(id);
        return true;
    }
}
