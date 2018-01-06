package com.arkadroid.models;

import com.arkadroid.network.entity.ProductionCompanyParser;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.04.01
 */
public class ProductionCompanyModel {

    private String name;

    private int id;

    public ProductionCompanyModel() {
    }

    public ProductionCompanyModel(ProductionCompanyParser parser) {

        if(parser == null) {
            return;
        }

        this.id = parser.getId();
        this.name = parser.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
