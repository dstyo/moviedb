package com.arkadroid.network.retrofit;

import com.arkadroid.network.entity.MovieDetailParser;
import com.arkadroid.network.entity.MovieResponseParser;
import com.arkadroid.network.entity.PersonDetailParser;
import com.arkadroid.network.entity.PersonResponseParser;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.04.01
 */
public interface ApiInterface {
    @GET("movie/popular")
    Observable<Response<MovieResponseParser>> moviePopular(@Query("api_key") String apiKey,
                                                           @Query("page") int page,
                                                           @Query("sort_by") String sortBy,
                                                           @Query("primary_release_date.lte") String releaseDateLte,
                                                           @Query("primary_release_date.gte") String releaseDateGte,
                                                           @Query("with_original_language") String withOriginalLanguage);

    @GET("movie/now_playing")
    Observable<Response<MovieResponseParser>> movieNowPlaying(@Query("api_key") String apiKey,
                                                              @Query("page") int page,
                                                              @Query("sort_by") String sortBy,
                                                              @Query("primary_release_date.lte") String releaseDateLte,
                                                              @Query("primary_release_date.gte") String releaseDateGte,
                                                              @Query("with_original_language") String withOriginalLanguage);

    @GET("movie/upcoming")
    Observable<Response<MovieResponseParser>> movieUpComming(@Query("api_key") String apiKey,
                                                             @Query("page") int page,
                                                             @Query("sort_by") String sortBy,
                                                             @Query("primary_release_date.lte") String releaseDateLte,
                                                             @Query("primary_release_date.gte") String releaseDateGte,
                                                             @Query("with_original_language") String withOriginalLanguage);

    @GET("search/movie")
    Observable<Response<MovieResponseParser>> searchMovie(@Query("api_key") String apiKey,
                                                          @Query("query") String query,
                                                          @Query("page") int page,
                                                          @Query("sort_by") String sortBy,
                                                          @Query("primary_release_date.lte") String releaseDateLte,
                                                          @Query("primary_release_date.gte") String releaseDateGte,
                                                          @Query("with_original_language") String withOriginalLanguage);

    @GET("movie/{id}")
    Observable<Response<MovieDetailParser>> getMovieDetail(@Path("id") int id,
                                                           @Query("api_key") String apiKey);

    @GET("movie/{id}/lists")
    Observable<Response<PersonResponseParser>> getActressDetail(@Path("id") int id,
                                                           @Query("api_key") String apiKey);

    @GET("person/popular")
    Observable<Response<PersonResponseParser>> personPopular(@Query("api_key") String apiKey,
                                                             @Query("page") int page);

    @GET("person/{person_id}")
    Observable<Response<PersonDetailParser>> getPersonDetails(@Path("person_id") int personId,
                                                              @Query("api_key") String apiKey);

    @GET("search/person")
    Observable<Response<PersonResponseParser>> searchPerson(@Query("api_key") String apiKey,
                                                           @Query("query") String query,
                                                           @Query("page") int page);

}