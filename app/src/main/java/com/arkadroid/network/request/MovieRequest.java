package com.arkadroid.network.request;

import java.util.Calendar;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.04.01
 */
public class MovieRequest {

    public static final int MIN_YEAR = 1900;
    public static final int THIS_YEAR = Calendar.getInstance().get(Calendar.YEAR);
    public static final int MAX_YEAR = THIS_YEAR + 5;
    private int page;
    private String sortBy;
    private String releaseDateLte;
    private String releaseDateGte;
    private String withOriginalLanguage;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getReleaseDateLte() {
        return releaseDateLte;
    }

    public void setReleaseDateLte(String releaseDateLte) {
        this.releaseDateLte = releaseDateLte;
    }

    public String getReleaseDateGte() {
        return releaseDateGte;
    }

    public void setReleaseDateGte(String releaseDateGte) {
        this.releaseDateGte = releaseDateGte;
    }

    public String getWithOriginalLanguage() {
        return withOriginalLanguage;
    }

    public void setWithOriginalLanguage(String withOriginalLanguage) {
        this.withOriginalLanguage = withOriginalLanguage;
    }
}
