package com.arkadroid.presenters;

import com.arkadroid.BaseApplication;
import com.arkadroid.models.MovieModel;
import com.arkadroid.models.MovieResultModel;
import com.arkadroid.network.request.MovieRequest;
import com.arkadroid.repositories.MovieRepository;

import java.util.ArrayList;
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
public class MoviePresenter {

    @Inject
    MovieRepository repository;

    private int pageSectionOne = 1;

    private int pageSectionTwo = 1;

    private int pageSectionThree = 1;

    private int pageSectionFour = 1;

    private int minYear = MovieRequest.THIS_YEAR;

    private int maxYear = MovieRequest.MIN_YEAR;

    private int totalPagesSectionOne = 1;

    private int totalPagesSectionTwo = 1;

    private int totalPagesSectionThree = 1;

    private int totalPagesSectionFour = 1;

    private HomeView homeView;

    private List<MovieModel> movieSectionOne = new ArrayList<>();

    private List<MovieModel> movieSectionTwo = new ArrayList<>();

    private List<MovieModel> movieSectionThree = new ArrayList<>();

    private List<MovieModel> movieSectionFour = new ArrayList<>();

    private boolean loading;

    private String query;

    public MoviePresenter() {
        BaseApplication.getInstance().getApplicationComponent().inject(this);
    }

    public void startNow(HomeView homeView) {
        this.homeView = homeView;
        fetchFirst();
    }

    public void fetchFirst() {
        resetFilters();
        fetchMoviesPopular(minYear, maxYear, pageSectionOne);
        fetchMoviesNowPlaying(minYear, maxYear, pageSectionTwo);
        fetchMoviesUpcoming(minYear, maxYear, pageSectionThree);
    }

    public void fetchSearch(String query) {
        this.query = query;
        movieSectionFour.clear();
        homeView.notifyMoviesListChanged(4);
        fetchMoviesSearch(query, minYear, maxYear, pageSectionFour);
    }

    public void fetchNext(int section) {
        if (section == 1) {
            int nextPage = ++pageSectionOne;
            if (nextPage <= totalPagesSectionOne) {
                fetchMoviesPopular(minYear, maxYear, nextPage);
            }
        } else if (section == 2) {
            int nextPage = ++pageSectionTwo;
            if (nextPage <= totalPagesSectionTwo) {
                fetchMoviesNowPlaying(minYear, maxYear, nextPage);
            }
        } else if (section == 3) {
            int nextPage = ++pageSectionThree;
            if (nextPage <= totalPagesSectionThree) {
                fetchMoviesUpcoming(minYear, maxYear, nextPage);
            }
        } else if (section == 4) {
            int nextPage = ++pageSectionFour;
            if (nextPage <= totalPagesSectionFour) {
                fetchMoviesSearch(query, minYear, maxYear, nextPage);
            }
        }
    }

    private void resetFilters() {
        pageSectionOne = 1;
        pageSectionTwo = 1;
        pageSectionThree = 1;
        minYear = MovieRequest.THIS_YEAR;
        maxYear = MovieRequest.MIN_YEAR;
        movieSectionOne.clear();
        movieSectionTwo.clear();
        movieSectionThree.clear();

        homeView.notifyMoviesListChanged(1);
        homeView.notifyMoviesListChanged(2);
        homeView.notifyMoviesListChanged(3);
    }

    private void fetchMoviesPopular(final int minYear, final int maxYear, final int nextPage) {

        Observable<MovieResultModel> observable = repository.getMovies(null, minYear, maxYear, nextPage, 1);

        observable.subscribe(new Observer<MovieResultModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                loading = true;
                homeView.showLoadingProgress();
            }

            @Override
            public void onNext(@NonNull MovieResultModel discoverModel) {

                if (discoverModel == null) {
                    return;
                }
                pageSectionOne = discoverModel.getPage();
                totalPagesSectionOne = discoverModel.getTotalPages();

                List<MovieModel> newList = discoverModel.getMovies();
                if (newList != null) {
                    if (movieSectionOne == null || movieSectionOne.size() == 0) {
                        movieSectionOne = newList;
                        homeView.showMovies(movieSectionOne, minYear, maxYear, 1);
                    } else {
                        movieSectionOne.addAll(newList);
                        homeView.notifyMoviesListChanged(1);
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

                homeView.onError(e);
            }

            @Override
            public void onComplete() {
                loading = false;
                homeView.hideLoadingProgress();
            }
        });
    }

    private void fetchMoviesNowPlaying(final int minYear, final int maxYear, final int nextPage) {

        Observable<MovieResultModel> observable = repository.getMovies(null, minYear, maxYear, nextPage, 2);

        observable.subscribe(new Observer<MovieResultModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                loading = true;
                homeView.showLoadingProgress();
            }

            @Override
            public void onNext(@NonNull MovieResultModel discoverModel) {

                if (discoverModel == null) {
                    return;
                }
                pageSectionTwo = discoverModel.getPage();
                totalPagesSectionTwo = discoverModel.getTotalPages();

                List<MovieModel> newList = discoverModel.getMovies();
                if (newList != null) {
                    if (movieSectionTwo == null || movieSectionTwo.size() == 0) {
                        movieSectionTwo = newList;
                        homeView.showMovies(movieSectionTwo, minYear, maxYear, 2);
                    } else {
                        movieSectionTwo.addAll(newList);
                        homeView.notifyMoviesListChanged(2);
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

                homeView.onError(e);
            }

            @Override
            public void onComplete() {
                loading = false;
                homeView.hideLoadingProgress();
            }
        });
    }

    private void fetchMoviesUpcoming(final int minYear, final int maxYear, final int nextPage) {

        Observable<MovieResultModel> observable = repository.getMovies(null, minYear, maxYear, nextPage, 3);

        observable.subscribe(new Observer<MovieResultModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                loading = true;
                homeView.showLoadingProgress();
            }

            @Override
            public void onNext(@NonNull MovieResultModel discoverModel) {

                if (discoverModel == null) {
                    return;
                }
                pageSectionThree = discoverModel.getPage();
                totalPagesSectionThree = discoverModel.getTotalPages();

                List<MovieModel> newList = discoverModel.getMovies();
                if (newList != null) {
                    if (movieSectionThree == null || movieSectionThree.size() == 0) {
                        movieSectionThree = newList;
                        homeView.showMovies(movieSectionThree, minYear, maxYear, 3);
                    } else {
                        movieSectionThree.addAll(newList);
                        homeView.notifyMoviesListChanged(3);
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

                homeView.onError(e);
            }

            @Override
            public void onComplete() {
                loading = false;
                homeView.hideLoadingProgress();
            }
        });
    }

    private void fetchMoviesSearch(final String query, final int minYear, final int maxYear, final int nextPage) {

        Observable<MovieResultModel> observable = repository.getMovies(query, minYear, maxYear, nextPage, 4);

        observable.subscribe(new Observer<MovieResultModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                loading = true;
                homeView.showLoadingProgress();
            }

            @Override
            public void onNext(@NonNull MovieResultModel discoverModel) {

                if (discoverModel == null) {
                    return;
                }
                pageSectionFour = discoverModel.getPage();
                totalPagesSectionFour = discoverModel.getTotalPages();

                List<MovieModel> newList = discoverModel.getMovies();
                if (newList != null) {
                    if (movieSectionFour == null || movieSectionFour.size() == 0) {
                        movieSectionFour = newList;
                        homeView.showMovies(movieSectionFour, minYear, maxYear, 4);
                    } else {
                        movieSectionFour.addAll(newList);
                        homeView.notifyMoviesListChanged(4);
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

                homeView.onError(e);
            }

            @Override
            public void onComplete() {
                loading = false;
                homeView.hideLoadingProgress();
            }
        });
    }

    public boolean isLoading() {
        return loading;
    }

    public interface HomeView {

        void showMovies(List<MovieModel> movies, int minYear, int maxYear, int section);

        void notifyMoviesListChanged(int section);

        void showLoadingProgress();

        void hideLoadingProgress();

        void onError(Throwable e);
    }
}
