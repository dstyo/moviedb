package com.arkadroid.network.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.05.01
 */
public class PersonDetailParser {

    @SerializedName("popularity")
    @Expose
    private float popularity;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("profile_path")
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

    @SerializedName("birthday")
    @Expose
    private String birthday;

    @SerializedName("deathday")
    @Expose
    private String deathday;

    @SerializedName("gender")
    @Expose
    private int gender;

    @SerializedName("place_of_birth")
    @Expose
    private String place_of_birth;

    @SerializedName("biography")
    @Expose
    private String biography;

    @SerializedName("imdb_id")
    @Expose
    private String imdb_id;

    @SerializedName("homepage")
    @Expose
    private String homepage;

    @SerializedName("also_known_as")
    @Expose
    private List<String> also_known_as;

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public List<String> getAlso_known_as() {
        return also_known_as;
    }

    public void setAlso_known_as(List<String> also_known_as) {
        this.also_known_as = also_known_as;
    }


}
