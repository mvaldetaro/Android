package br.edu.infnet.at.gamehub.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

import br.edu.infnet.at.gamehub.R;
import br.edu.infnet.at.gamehub.models.feed.Feed;

/**
 * Created by Magno on 04/11/2017.
 */

public class FeedAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_canais, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((FeedViewHolder) holder).
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView titulo;
        private ImageView img;


        public FeedViewHolder(View itemView) {
            super(itemView);

            titulo = (TextView) itemView.findViewById(R.id.feed_item_title);
            img = (ImageView) itemView.findViewById(R.id.feed_item_image);

            itemView.setOnClickListener(this);
        }

        public void bindView() {
            Feed feed = new Feed();
            titulo.setText(feed.getTitle());
            try {
                Bitmap bm = BitmapFactory.decodeStream((InputStream) new URL(feed.getImage()).getContent());
                img.setImageBitmap(bm);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        @Override
        public void onClick(View v) {

        }
    }
}

