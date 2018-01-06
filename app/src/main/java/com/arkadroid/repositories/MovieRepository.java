package com.arkadroid.repositories;

import android.util.Log;

import com.arkadroid.models.MovieDetailModel;
import com.arkadroid.models.MovieResultModel;
import com.arkadroid.models.PersonResultModel;
import com.arkadroid.network.entity.MovieDetailParser;
import com.arkadroid.network.entity.MovieResponseParser;
import com.arkadroid.network.entity.PersonResponseParser;
import com.arkadroid.network.request.MovieRequest;
import com.arkadroid.network.retrofit.AppUrls;
import com.arkadroid.network.retrofit.RestClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.04.01
 */
public class MovieRepository {

    private static final String API_KEY = AppUrls.API_KEY;

    public Observable<MovieDetailModel> getMovieDetail(int id) {

        Observable<Response<MovieDetailParser>> observable = RestClient.get().getMovieDetail(id, API_KEY);

        observable = observable.subscribeOn(Schedulers.newThread());
        observable = observable.observeOn(AndroidSchedulers.mainThread());

        return observable.map(new Function<Response<MovieDetailParser>, MovieDetailModel>() {

            @Override
            public MovieDetailModel apply(@NonNull Response<MovieDetailParser> response) throws Exception {

                if (response.isSuccessful()) {
                    MovieDetailParser parser = response.body();
                    return new MovieDetailModel(parser);
                }

                return null;
            }
        });
    }

    public Observable<PersonResultModel> getActressMovieDetail(int id) {

        Observable<Response<PersonResponseParser>> observable = RestClient.get().getActressDetail(id, API_KEY);

        observable = observable.subscribeOn(Schedulers.newThread());
        observable = observable.observeOn(AndroidSchedulers.mainThread());

        return observable.map(new Function<Response<PersonResponseParser>, PersonResultModel>() {

            @Override
            public PersonResultModel apply(@NonNull Response<PersonResponseParser> response) throws Exception {

                if (response.isSuccessful()) {
                    PersonResponseParser parser = response.body();
                    return new PersonResultModel(parser);
                }

                return null;
            }
        });
    }

    public Observable<MovieResultModel> getMovies(String query, int yearStart, int yearEnd, int page, int section) {

        String sortBy = "release_date.asc";

        if (yearStart > yearEnd) {
            int temp = yearStart;
            yearStart = yearEnd;
            yearEnd = temp;
            sortBy = "release_date.desc";
        }

        String releaseDateLte = yearEnd + "-12-31";
        String releaseDateGte = yearStart + "-01-01";
        String withOriginalLanguage = "en";

        MovieRequest request = new MovieRequest();
        request.setPage(page);
        request.setReleaseDateGte(releaseDateGte);
        request.setReleaseDateLte(releaseDateLte);
        request.setSortBy(sortBy);
        request.setWithOriginalLanguage(withOriginalLanguage);

        if (section == 1) {
            return getMovies(request, RestClient.get().moviePopular(API_KEY, page, sortBy, releaseDateLte, releaseDateGte, withOriginalLanguage));
        } else if (section == 2) {
            return getMovies(request, RestClient.get().movieNowPlaying(API_KEY, page, sortBy, releaseDateLte, releaseDateGte, withOriginalLanguage));
        } else if (section == 3) {
            return getMovies(request, RestClient.get().movieUpComming(API_KEY, page, sortBy, releaseDateLte, releaseDateGte, withOriginalLanguage));
        } else if (section == 4) {
            return getMovies(request, RestClient.get().searchMovie(API_KEY, query, page, sortBy, releaseDateLte, releaseDateGte, withOriginalLanguage));
        }
        return null;
    }

    private Observable<MovieResultModel> getMovies(MovieRequest discoveryRequest, Observable<Response<MovieResponseParser>> discoverObservable) {

        String sortBy = discoveryRequest.getSortBy();
        String releaseDateLte = discoveryRequest.getReleaseDateLte();
        String releaseDateGte = discoveryRequest.getReleaseDateGte();
        String withOriginalLanguage = discoveryRequest.getWithOriginalLanguage();
        int page = discoveryRequest.getPage();

        Log.d("MovieRequest", "MovieRequest" +
                "\npage : " + page +
                "\nreleaseDateLte : " + releaseDateLte +
                "\nreleaseDateGte : " + releaseDateGte +
                "\nwithOriginalLanguage : " + withOriginalLanguage +
                "\nsortBy : " + sortBy);

        discoverObservable = discoverObservable.subscribeOn(Schedulers.newThread());
        discoverObservable = discoverObservable.observeOn(AndroidSchedulers.mainThread());

        return discoverObservable.map(new Function<Response<MovieResponseParser>, MovieResultModel>() {

            @Override
            public MovieResultModel apply(@NonNull Response<MovieResponseParser> response) throws Exception {

                if (response.isSuccessful()) {
                    MovieResponseParser parser = response.body();
                    return new MovieResultModel(parser);
                }

                return null;
            }
        });
    }
}
