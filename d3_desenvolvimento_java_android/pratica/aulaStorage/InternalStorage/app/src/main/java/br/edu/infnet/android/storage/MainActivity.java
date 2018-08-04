package br.edu.infnet.android.storage;
/* importações */
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

//import com.journaldev.internalstorage.R;
import br.edu.infnet.android.storage.R;

public class MainActivity extends Activity {

    /*set de variaveis*/
    EditText textmsg;
    static final int READ_BLOCK_SIZE = 100;
    Button saveButtonExt,readButtonExt;
    Button saveButtonInt,readButtonInt;
    private String filenameInt = "TesteInt.txt";
    private String filenameExt = "TesteExt.txt";
    private String filepath = "MyFileStorage";
    File myExternalFile;
    String myData = "";
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* setando variaveis do layout*/
        textmsg=(EditText)findViewById(R.id.editText1);
        saveButtonInt =(Button) findViewById(R.id.btEscreveInt);
        saveButtonExt =
                (Button) findViewById(R.id.btEscreveExt);//saveExternalStorage);
        readButtonExt = (Button) findViewById(R.id.btLeExt);//getExternalStorage);

        /*validando se tem storage externo*/
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            saveButtonExt.setEnabled(false);
        }
        else {
            myExternalFile = new File(getExternalFilesDir(filepath), filenameExt);
        }
        /*onclick do externo*/
        saveButtonExt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    /*salva no arq externo e fecha o arq*/
                    FileOutputStream fos = new FileOutputStream(myExternalFile);
                    fos.write(textmsg.getText().toString().getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*mostra um toast*/
                textmsg.setText("");
                Toast.makeText(getBaseContext(), "Arquivo salvo com sucesso!",
                        Toast.LENGTH_SHORT).show();
            }
        });

/*le do arq externo*/
        readButtonExt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myData="";
                    /*inicia a leitura*/
                    FileInputStream fis = new FileInputStream(myExternalFile);
                    DataInputStream in = new DataInputStream(fis);
                    /*le conteudo*/
                    BufferedReader br =
                            new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    while ((strLine = br.readLine()) != null) {
                        myData = myData + strLine;
                    }
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*imprime o que fez*/
                textmsg.setText(myData);
                Toast.makeText(getBaseContext(), "Arquivo salvo com sucesso!",
                        Toast.LENGTH_SHORT).show();
            }
        });





        /* shared prefs*/
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains("sharedTexto")) {
            //name.setText(sharedpreferences.getString("sharedTexto", ""));
        }


    }

    // Escreve texto em arquivo interno
    public void WriteBtn(View v) {
        try {

            FileOutputStream fileout=openFileOutput("arquivoInt.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);

            outputWriter.write(textmsg.getText().toString());
            outputWriter.close();
            //Mensagem de salvo com sucesso
            Toast.makeText(getBaseContext(), "Arquivo Interno SALVO com sucesso!",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Ler texto do arquivo
    public void ReadBtn(View v) {
        try {
            FileInputStream fileIn=openFileInput("arquivoInt.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[READ_BLOCK_SIZE];
            String s="";
            int charRead;
            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();
            textmsg.setText(s);
            Toast.makeText(getBaseContext(), "Arquivo Interno LIDO com sucesso!",
                    Toast.LENGTH_SHORT).show();
            //Toast.makeText(getBaseContext(), s,Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*metodo de verificacao de leitura de storage externa*/
    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    /*metodo de validacao de existencia de storage externa*/
    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    /*metodo click de btSalvaPref*/
    public void Save(View view) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("sharedTexto", textmsg.getText().toString());
        editor.apply();
    }

    /*metodo click do botao clear*/

    public void clear(View view) {
        textmsg.setText("");

    }

    /*metodo get do botao btSalvaPref*/
    public void Get(View view) {
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        String p = "";
        if (sharedpreferences.contains("sharedTexto")) {
            p = sharedpreferences.getString("sharedTexto","");
        }
        textmsg.setText("sharedTexto="+ p);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
