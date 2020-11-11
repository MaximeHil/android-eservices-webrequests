package android.eservices.webrequests.data.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookInfo {

    private String title;
    private ImageLinks imageLinks;
    private String description;
    private String language;
    private String publishedDate;

    @SerializedName("authors")
    private List<String> authorList;

    public String getTitle() {
        return title;
    }

    public List<String> getAuthorList() {
        return authorList;
    }

    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setAuthorList(List<String> authorList) {
        this.authorList = authorList;
    }
}
