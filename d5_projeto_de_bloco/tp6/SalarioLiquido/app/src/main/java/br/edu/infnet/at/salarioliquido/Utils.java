package br.edu.infnet.at.salarioliquido;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Magno on 22/10/2017.
 */

public class Utils {
    public static void msg(String mensagem, Context contexto){
        Toast.makeText(contexto, mensagem, Toast.LENGTH_SHORT).show();
    }
}
