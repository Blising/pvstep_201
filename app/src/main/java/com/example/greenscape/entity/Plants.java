package com.example.greenscape.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plants {

    @Expose
    @SerializedName("id")

    private  int id;
    @Expose
    @SerializedName("name")
    private  String name;
    @Expose
    @SerializedName("description")
    private  String description;

    @Expose
    @SerializedName("photo_url")
    private  String photo_url;


    public Plants() {
    }

    public Plants(int id, String name, String description, String photo_url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo_url = photo_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

}
