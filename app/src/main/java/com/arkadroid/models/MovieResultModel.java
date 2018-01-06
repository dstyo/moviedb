package com.arkadroid.models;

import com.arkadroid.network.entity.MovieResponseParser;
import com.arkadroid.network.entity.MovieParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.04.01
 */
public class MovieResultModel {

    private int page;

    private int totalResults;

    private int totalPages;

    private List<MovieModel> movies = null;

    public MovieResultModel(MovieResponseParser parser) {

        if(parser == null) {
            return;
        }

        this.page = parser.getPage();
        this.totalResults = parser.getTotalResults();
        this.totalPages = parser.getTotalPages();

        List<MovieParser> movieParsers = parser.getMovieParsers();
        if(movieParsers != null) {
            movies = new ArrayList<>();
            for(MovieParser movieParser : movieParsers){
                this.movies.add(new MovieModel(movieParser));
            }
        }
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<MovieModel> getMovies() {
        return movies;
    }

}
