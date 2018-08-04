package br.edu.infnet.at.storage;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Magno on 16/10/2017.
 */

public class Contato implements Serializable {
    private String nome;
    private String email;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static boolean saveObj(ArrayList<Contato> obj, Context context) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        boolean keep = true;

        try {
            fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            oos.flush();
            fos.close();
        } catch(Exception e) {
            keep = false;
        }



        return keep;
    }
}
