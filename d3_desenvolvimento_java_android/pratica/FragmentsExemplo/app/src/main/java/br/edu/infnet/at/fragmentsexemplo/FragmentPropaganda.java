package br.edu.infnet.at.fragmentsexemplo;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentPropaganda extends Fragment {

    private static final String URL = "http://timespender.com/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_propaganda, container);

        Button buttonCliqueAqui = (Button) view.findViewById(R.id.button_clique_aqui);
        buttonCliqueAqui.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                irParaSite();
            }
        });

        return view;
    }

    private void irParaSite() {
        Uri uriVideo = Uri.parse(URL);
        Intent intent = new Intent(Intent.ACTION_VIEW, uriVideo);
        startActivity(intent);
    }
}
