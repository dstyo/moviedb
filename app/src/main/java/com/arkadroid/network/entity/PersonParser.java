package com.arkadroid.network.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.05.01
 */
public class PersonParser {

    @SerializedName("popularity")
    @Expose
    private float popularity;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName(value = "profile_path", alternate = {"poster_path"})
    @Expose
    private String profile_path;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("known_for")
    @Expose
    private List<MovieParser> known_for = null;

    @SerializedName("adult")
    @Expose
    private boolean adult;

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MovieParser> getKnown_for() {
        return known_for;
    }

    public void setKnown_for(List<MovieParser> known_for) {
        this.known_for = known_for;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }
}
