package com.arkadroid.models;

import com.arkadroid.network.entity.PersonParser;
import com.arkadroid.network.entity.PersonResponseParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.04.01
 */
public class PersonResultModel {

    private int page;

    private int totalResults;

    private int totalPages;

    private List<PersonModel> personModelList = null;

    public PersonResultModel(PersonResponseParser parser) {

        if (parser == null) {
            return;
        }

        this.page = parser.getPage();
        this.totalResults = parser.getTotalResults();
        this.totalPages = parser.getTotalPages();

        List<PersonParser> personParsers = parser.getPersonParsers();
        if (personParsers != null) {
            personModelList = new ArrayList<>();
            for (PersonParser personParser : personParsers) {
                this.personModelList.add(new PersonModel(personParser));
            }
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<PersonModel> getPersonModelList() {
        return personModelList;
    }

    public void setPersonModelList(List<PersonModel> personModelList) {
        this.personModelList = personModelList;
    }
}
