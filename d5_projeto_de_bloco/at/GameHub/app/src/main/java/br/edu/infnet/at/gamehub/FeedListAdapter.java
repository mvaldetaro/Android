package br.edu.infnet.at.gamehub;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import br.edu.infnet.at.gamehub.models.feed.Feed;

/**
 * Created by Magno on 04/11/2017.
 */

public class FeedListAdapter extends ArrayAdapter<Feed> {
    private Context context;
    private ArrayList<Feed> lista;

    public FeedListAdapter(Context context, ArrayList<Feed> lista){
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Feed itemPosicao = this.lista.get(position);
        convertView = LayoutInflater.from(this.context).inflate(R.layout.cardview_canais,null);

        TextView nome = (TextView) convertView.findViewById(R.id.feed_item_title);
        nome.setText(itemPosicao.getTitle());

        ImageView img = (ImageView) convertView.findViewById(R.id.feed_item_image);

        try {
            Bitmap bm = BitmapFactory.decodeStream((InputStream) new URL(itemPosicao.getImage()).getContent());
            img.setImageBitmap(bm);
        } catch (Exception e) {
            System.out.println(e);
        }

        return convertView;
    }
}
