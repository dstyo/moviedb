package com.arkadroid.models;

import com.arkadroid.network.entity.PersonDetailParser;

import java.util.List;

import static com.arkadroid.network.retrofit.AppUrls.IMAGES_BASE_URL;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.06.01
 */
public class PersonDetailModel {

    private float popularity;

    private int id;

    private String profile_path;

    private String name;

    private boolean adult;

    private String birthday;

    private String deathday;

    private int gender;

    private String place_of_birth;

    private String biography;

    private String imdb_id;

    private String homepage;

    private List<String> also_known_as;

    public PersonDetailModel(PersonDetailParser parser) {
        if (parser == null) {
            return;
        }

        this.popularity = parser.getPopularity();
        this.id = parser.getId();
        this.profile_path = parser.getProfile_path();
        this.name = parser.getName();
        this.adult = parser.isAdult();
        this.birthday = parser.getBirthday();
        this.deathday = parser.getDeathday();
        this.gender = parser.getGender();
        this.place_of_birth = parser.getPlace_of_birth();
        this.biography = parser.getBiography();
        this.imdb_id = parser.getImdb_id();
        this.homepage = parser.getHomepage();
        this.also_known_as = parser.getAlso_known_as();
    }

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
        if (profile_path != null) {
            return IMAGES_BASE_URL + profile_path;
        }
        return null;
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
