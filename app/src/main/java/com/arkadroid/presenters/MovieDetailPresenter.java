package com.arkadroid.presenters;

import android.util.Log;

import com.arkadroid.BaseApplication;
import com.arkadroid.models.MovieDetailModel;
import com.arkadroid.models.PersonModel;
import com.arkadroid.models.PersonResultModel;
import com.arkadroid.repositories.MovieRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.04.01
 */
public class MovieDetailPresenter {

    @Inject
    MovieRepository repository;

    private MovieDetailView movieDetailView;

    private MovieDetailModel movieDetail;

    private int movieId;

    public MovieDetailPresenter() {

        BaseApplication.getInstance().getApplicationComponent().inject(this);
    }

    public void startNow(MovieDetailView view, int movieId) {
        this.movieDetailView = view;
        this.movieId = movieId;
        if (movieDetail != null) {
            Log.d("MovieDetailPresenter", "Showing cashed ");
            view.showMovieDetail(movieDetail);
            return;
        }
        fetchMovie(movieId);
        fetchListActress(movieId);
    }

    private void fetchMovie(int id) {

        Log.d("MovieDetailPresenter", "Fetching from API");

        Observable<MovieDetailModel> observable = repository.getMovieDetail(id);
        observable.subscribe(new Observer<MovieDetailModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                movieDetailView.showLoadingProgress();
            }

            @Override
            public void onNext(@NonNull MovieDetailModel model) {

                if (model == null) {
                    return;
                }
                movieDetail = model;
                movieDetailView.showMovieDetail(model);
            }

            @Override
            public void onError(@NonNull Throwable e) {

                movieDetailView.onError(e);
            }

            @Override
            public void onComplete() {
                movieDetailView.hideLoadingProgress();
            }
        });
    }

    private void fetchListActress(int id) {

        Log.d("MovieDetailPresenter", "Fetching from API");

        Observable<PersonResultModel> observable = repository.getActressMovieDetail(id);
        observable.subscribe(new Observer<PersonResultModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                movieDetailView.showLoadingProgress();
            }

            @Override
            public void onNext(@NonNull PersonResultModel model) {

                if (model == null) {
                    return;
                }
                List<PersonModel> newList = model.getPersonModelList();
                movieDetailView.showActressMovieDetail(newList);
            }

            @Override
            public void onError(@NonNull Throwable e) {

                movieDetailView.onError(e);
            }

            @Override
            public void onComplete() {
                movieDetailView.hideLoadingProgress();
            }
        });
    }

    public void cleanUp() {
        movieId = 0;
        movieDetail = null;
    }

    public interface MovieDetailView {

        void showMovieDetail(MovieDetailModel movieDetailModel);

        void showActressMovieDetail(List<PersonModel> personModelList);

        void showLoadingProgress();

        void hideLoadingProgress();

        void onError(Throwable e);
    }
}
