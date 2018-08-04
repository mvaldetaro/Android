package br.edu.infnet.at.gamehub;

import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import br.edu.infnet.at.gamehub.adapters.FeedAdapter;
import br.edu.infnet.at.gamehub.models.feed.Feed;
import br.edu.infnet.at.gamehub.models.feed.RssObject;
import br.edu.infnet.at.gamehub.services.FeedService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CanaisActivity extends Fragment {

    private  static final String TAG = "Magno";
    ArrayList<Feed> itens;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    FeedListAdapter feedListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_canais, container, false);

        recyclerView = (RecyclerView) view.findViewById(android.R.id.list);

        FeedAdapter feedAdapter = new FeedAdapter();
        recyclerView.setAdapter(feedAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itens = new ArrayList<Feed>();

        getActivity().setTitle(R.string.nav_meus_canais);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FeedService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FeedService feedService = retrofit.create(FeedService.class);

        Call<RssObject> requestCatalog = feedService.feedCatalog();

        System.out.println(requestCatalog);

        requestCatalog.enqueue(new Callback<RssObject>() {
            @Override
            public void onResponse(Call<RssObject> call, Response<RssObject> response) {
                if (!response.isSuccessful()){
                    Log.i(TAG, "Erro " + response.code());
                } else {
                    RssObject obj = response.body();

                    for (Feed f : obj.getFeed()) {
                        Log.i(TAG, "Titulo: " + f.getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<RssObject> call, Throwable t) {
                Log.e(TAG, "ErroDoido: " + t.getMessage());
            }
        });



    }
}
