package br.edu.infnet.at.gamehub.services;

import br.edu.infnet.at.gamehub.models.feed.RssObject;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Magno on 04/11/2017.
 */

public interface FeedService {

    public static final String BASE_URL = "https://gist.githubusercontent.com/mvaldetaro/2601ba2f7f1ba9080191c090b5b56725/raw/91f7a25402906d63f052392c5763b09ec88dd318/";

    @GET("feed")
    Call<RssObject> feedCatalog();
}
