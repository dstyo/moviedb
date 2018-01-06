package com.arkadroid.models;

import com.arkadroid.network.entity.LanguageParser;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.04.01
 */
public class LanguageModel {

    private String iso6391;

    private String name;

    public LanguageModel() {
    }

    public LanguageModel(LanguageParser parser) {

        if(parser == null) {
            return;
        }

        this.iso6391 = parser.getIso6391();
        this.name = parser.getName();
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
