package br.edu.infnet.at.gamehub.models.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feed {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("guid")
    @Expose
    private String guid;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("pubdate")
    @Expose
    private String pubdate;
    @SerializedName("Description")
    @Expose
    private String description;

    /**
     * No args constructor for use in serialization
     *
     */
    public Feed() {
    }

    /**
     *
     * @param guid
     * @param title
     * @param description
     * @param pubdate
     * @param image
     * @param url
     */
    public Feed(String url, String guid, String title, String image, String pubdate, String description) {
        super();
        this.url = url;
        this.guid = guid;
        this.title = title;
        this.image = image;
        this.pubdate = pubdate;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}