package br.edu.infnet.at.gamehub.models.feed;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RssObject {

    @SerializedName("feed")
    @Expose
    private List<Feed> feed = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public RssObject() {
    }

    /**
     *
     * @param feed
     */
    public RssObject(List<Feed> feed) {
        super();
        this.feed = feed;
    }

    public List<Feed> getFeed() {
        return feed;
    }

    public void setFeed(List<Feed> feed) {
        this.feed = feed;
    }

}