package br.com.infnet.atividades.models;

import android.content.Context;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Magno on 15/11/2017.
 */

public class Utils {
    public static void msg(String mensagem, Context contexto){
        Toast.makeText(contexto, mensagem, Toast.LENGTH_SHORT).show();
    }

    public static String getDir() {
        File root = android.os.Environment.getRootDirectory();
        return root.toString();
    }
}
