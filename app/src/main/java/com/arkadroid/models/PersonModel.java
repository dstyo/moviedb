package com.arkadroid.models;

import com.arkadroid.network.entity.MovieParser;
import com.arkadroid.network.entity.PersonParser;

import java.util.List;

import static com.arkadroid.network.retrofit.AppUrls.IMAGES_BASE_URL;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.05.01
 */
public class PersonModel {

    private float popularity;

    private int id;

    private String profile_path;

    private String name;

    private List<MovieParser> known_for = null;

    private boolean adult;

    public PersonModel(PersonParser parser) {

        if (parser == null) {
            return;
        }

        this.id = parser.getId();
        this.popularity = parser.getPopularity();
        this.profile_path = parser.getProfile_path();
        this.name = parser.getName();
        this.known_for = parser.getKnown_for();
        this.adult = parser.isAdult();
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
