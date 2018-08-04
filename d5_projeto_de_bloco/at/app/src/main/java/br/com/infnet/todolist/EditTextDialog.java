package br.com.infnet.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.infnet.todolist.models.User;

/**
 * Created by Magno on 25/11/2017.
 */

public class EditTextDialog extends AppCompatDialogFragment {

    private EditText editText;
    private EditDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edittext, null);

        Bundle activity_args = getArguments();

        builder.setView(view)
                .setTitle(activity_args.getString("DIALOG_TITLE"))
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = editText.getText().toString();
                        if (text.equals("") || text.equals(null)){
                            listener.applyText("EMPTY_STRING_IGNORE_DATA");
                        } else {
                            listener.applyText(text);
                        }
                    }
        });
        editText = view.findViewById(R.id.input_edit_dialog);
        editText.setText(activity_args.getString("STRING_CONTEXT"));
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (EditDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    public interface EditDialogListener {
        void applyText(String text);
    }

}
