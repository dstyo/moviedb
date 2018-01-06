package com.arkadroid.models;

import com.arkadroid.network.entity.GenreParser;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.04.01
 */
public class GenreModel {

    private int id;

    private String name;

    public GenreModel() {
    }

    public GenreModel(GenreParser parser) {

        if(parser == null) {
            return;
        }

        this.id = parser.getId();
        this.name = parser.getName();
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

}
