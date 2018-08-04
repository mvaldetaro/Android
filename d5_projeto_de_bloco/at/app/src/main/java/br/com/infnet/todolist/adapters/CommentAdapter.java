package br.com.infnet.todolist.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.infnet.todolist.DetailsActivity;
import br.com.infnet.todolist.R;
import br.com.infnet.todolist.commons.Utils;
import br.com.infnet.todolist.models.Comment;
import br.com.infnet.todolist.models.Task;
import br.com.infnet.todolist.models.User;

/**
 * Created by Magno on 25/11/2017.
 */

public class CommentAdapter extends ArrayAdapter<Comment> {
    private Context context;
    private ArrayList<Comment> comments;

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        super(context, 0, comments);
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Comment itemPosicao = this.comments.get(position);
        convertView = LayoutInflater.from(this.context).inflate(R.layout.single_comment,null);

        TextView autor = (TextView) convertView.findViewById(R.id.comment_autor);
        TextView comment = (TextView) convertView.findViewById(R.id.comment_comment);
        //ImageView deleteButton = (ImageView) convertView.findViewById(R.id.btn_item_edit);

        autor.setText(itemPosicao.getAutor());
        comment.setText(itemPosicao.getComment());

        /*deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        return convertView;
    }


}
