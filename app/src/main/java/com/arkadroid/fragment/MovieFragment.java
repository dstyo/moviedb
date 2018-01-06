package com.arkadroid.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.arkadroid.BaseApplication;
import com.arkadroid.R;
import com.arkadroid.activity.MovieDetailActivity;
import com.arkadroid.adapters.MovieListAdapter;
import com.arkadroid.models.MovieModel;
import com.arkadroid.presenters.MoviePresenter;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

public class MovieFragment extends Fragment implements MoviePresenter.HomeView, MaterialSearchBar.OnSearchActionListener {

    @Inject
    MoviePresenter presenter;

    private CoordinatorLayout coordinatorLayout;
    private RecyclerView rvMoviesPopular, rvMoviesNowPlaying, rvMoviesUpcoming, rvMoviesSearch;
    private View loadingProgressView;
    private View emptyView;
    private MaterialSearchBar searchBar;
    private LinearLayout llMoviesPopular, llMoviesNowPlaying, llMoviesUpcoming, llMoviesSearch;

    private MovieListAdapter movieListAdapter;
    private MovieListAdapter movieListNowPlayingAdapter;
    private MovieListAdapter movieListUpcomingAdapter;
    private MovieListAdapter movieListSearchAdapter;


    public static MovieFragment newInstance() {
        MovieFragment fragment = new MovieFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        BaseApplication.getInstance().getApplicationComponent().inject(this);
        initViews(view);
        presenter.startNow(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!presenter.isLoading()) {
            hideLoadingProgress();
        }
    }

    private void initViews(View view) {
        coordinatorLayout = view.findViewById(R.id.drawer_layout);
        rvMoviesPopular = view.findViewById(R.id.rvMoviesPopular);
        rvMoviesNowPlaying = view.findViewById(R.id.rvMoviesNowPlaying);
        rvMoviesUpcoming = view.findViewById(R.id.rvMoviesUpcoming);
        rvMoviesSearch = view.findViewById(R.id.rvMoviesSearch);
        llMoviesNowPlaying = view.findViewById(R.id.llMoviesNowPlaying);
        llMoviesPopular = view.findViewById(R.id.llMoviesPopular);
        llMoviesUpcoming = view.findViewById(R.id.llMoviesUpcoming);
        llMoviesSearch = view.findViewById(R.id.llMoviesSearch);
        searchBar = view.findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);

        setupMovieSection1();
        setupMovieSection2();
        setupMovieSection3();
        setupMovieSection4();

        loadingProgressView = view.findViewById(R.id.loadingProgressView);

        emptyView = view.findViewById(R.id.emptyView);

    }

    private void setupMovieSection1() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvMoviesPopular.setLayoutManager(layoutManager);

        movieListAdapter = new MovieListAdapter(getActivity());
        movieListAdapter.setMovieSelectionListener(new MovieListAdapter.OnMovieSelectionListener() {
            @Override
            public void onMovieSelected(MovieModel model, View view, int position) {
                movieSelected(model, view, position);
            }
        });
        rvMoviesPopular.setAdapter(movieListAdapter);

        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (presenter.isLoading()) {
                    return;
                }
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount - 10) {
                    fetchNext(1);
                }
            }
        };
        rvMoviesPopular.addOnScrollListener(scrollListener);
    }

    private void setupMovieSection2() {
        final LinearLayoutManager layoutManagerNowPlaying = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvMoviesNowPlaying.setLayoutManager(layoutManagerNowPlaying);

        movieListNowPlayingAdapter = new MovieListAdapter(getActivity());
        movieListNowPlayingAdapter.setMovieSelectionListener(new MovieListAdapter.OnMovieSelectionListener() {
            @Override
            public void onMovieSelected(MovieModel model, View view, int position) {
                movieSelected(model, view, position);
            }
        });
        rvMoviesNowPlaying.setAdapter(movieListNowPlayingAdapter);

        RecyclerView.OnScrollListener scrollListenerNowPlaying = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (presenter.isLoading()) {
                    return;
                }
                int visibleItemCount = layoutManagerNowPlaying.getChildCount();
                int totalItemCount = layoutManagerNowPlaying.getItemCount();
                int pastVisibleItems = layoutManagerNowPlaying.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount - 10) {
                    fetchNext(2);
                }
            }
        };

        rvMoviesNowPlaying.addOnScrollListener(scrollListenerNowPlaying);
    }

    private void setupMovieSection3() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvMoviesUpcoming.setLayoutManager(layoutManager);

        movieListUpcomingAdapter = new MovieListAdapter(getActivity());
        movieListUpcomingAdapter.setMovieSelectionListener(new MovieListAdapter.OnMovieSelectionListener() {
            @Override
            public void onMovieSelected(MovieModel model, View view, int position) {
                movieSelected(model, view, position);
            }
        });
        rvMoviesUpcoming.setAdapter(movieListUpcomingAdapter);

        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (presenter.isLoading()) {
                    return;
                }
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount - 10) {
                    fetchNext(3);
                }
            }
        };

        rvMoviesUpcoming.addOnScrollListener(scrollListener);
    }

    private void setupMovieSection4() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvMoviesSearch.setLayoutManager(layoutManager);
        rvMoviesSearch.getLayoutManager().setAutoMeasureEnabled(true);
        rvMoviesSearch.setNestedScrollingEnabled(false);
        rvMoviesSearch.setHasFixedSize(false);

        movieListSearchAdapter = new MovieListAdapter(getActivity());
        movieListSearchAdapter.setMovieSelectionListener(new MovieListAdapter.OnMovieSelectionListener() {
            @Override
            public void onMovieSelected(MovieModel model, View view, int position) {
                movieSelected(model, view, position);
            }
        });
        rvMoviesSearch.setAdapter(movieListSearchAdapter);

        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (presenter.isLoading()) {
                    return;
                }
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount - 10) {
                    fetchNext(4);
                }
            }
        };

        rvMoviesSearch.addOnScrollListener(scrollListener);
    }

    private void fetchNext(int section) {
        presenter.fetchNext(section);
    }

    private void movieSelected(MovieModel model, View view, int position) {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, model.getId());
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_NAME, model.getTitle());

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(),
                        view,
                        ViewCompat.getTransitionName(view));
        startActivity(intent, options.toBundle());
        showLoadingProgress();
    }

    @Override
    public void showMovies(List<MovieModel> movies, int minYear, int maxYear, int section) {
        if (movies == null || movies.size() == 0) {
            showEmptyView();
            return;
        }

        hideEmptyView();
        if (section == 1) {
            movieListAdapter.setMovies(movies);
        } else if (section == 2) {
            movieListNowPlayingAdapter.setMovies(movies);
        } else if (section == 3) {
            movieListUpcomingAdapter.setMovies(movies);
        } else if (section == 4) {
            movieListSearchAdapter.setMovies(movies);
        }
    }

    private void hideEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }
    }

    private void showEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void notifyMoviesListChanged(int section) {
        if (section == 1) {
            movieListAdapter.notifyMoviesListChanged();
        } else if (section == 2) {
            movieListNowPlayingAdapter.notifyMoviesListChanged();
        } else if (section == 3) {
            movieListUpcomingAdapter.notifyMoviesListChanged();
        } else if (section == 4) {
            movieListSearchAdapter.notifyMoviesListChanged();
        }
    }

    @Override
    public void showLoadingProgress() {
        loadingProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingProgress() {
        loadingProgressView.setVisibility(View.GONE);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        hideLoadingProgress();
        if (throwable instanceof UnknownHostException || throwable instanceof SocketTimeoutException) {
            Snackbar.make(coordinatorLayout, getString(R.string.check_network_connection) + " : " + throwable.getMessage(), Snackbar.LENGTH_LONG).show();
            return;
        }
        Snackbar.make(coordinatorLayout, getString(R.string.something_went_wrong) + " : " + throwable.getMessage(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
        if (!enabled) {
            llMoviesSearch.setVisibility(View.GONE);
            llMoviesNowPlaying.setVisibility(View.VISIBLE);
            llMoviesUpcoming.setVisibility(View.VISIBLE);
            llMoviesPopular.setVisibility(View.VISIBLE);
            hideSoftKeyboard(llMoviesSearch);
        }
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        presenter.fetchSearch(text.toString());
        llMoviesSearch.setVisibility(View.VISIBLE);
        llMoviesNowPlaying.setVisibility(View.GONE);
        llMoviesUpcoming.setVisibility(View.GONE);
        llMoviesPopular.setVisibility(View.GONE);
        hideSoftKeyboard(llMoviesSearch);
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }

    private void hideSoftKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
