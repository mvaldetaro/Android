package br.com.infnet.todolist.commons;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import java.io.File;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by Magno on 21/11/2017.
 */

public class Utils {

    public static void msg(String mensagem, Context contexto){
        Toast.makeText(contexto, mensagem, Toast.LENGTH_SHORT).show();
    }

    public static String getDir() {
        File root = android.os.Environment.getRootDirectory();
        return root.toString();
    }

    public static void shakeIt(Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) context.getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) context.getSystemService(VIBRATOR_SERVICE)).vibrate(150);
        }
    }
}
