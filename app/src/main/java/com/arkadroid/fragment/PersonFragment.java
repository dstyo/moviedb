package com.arkadroid.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.arkadroid.BaseApplication;
import com.arkadroid.R;
import com.arkadroid.activity.PersonDetailActivity;
import com.arkadroid.adapters.PersonListAdapter;
import com.arkadroid.models.PersonModel;
import com.arkadroid.presenters.PersonPresenter;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;


public class PersonFragment extends Fragment implements PersonPresenter.PersonView, MaterialSearchBar.OnSearchActionListener {


    @Inject
    PersonPresenter presenter;

    private CoordinatorLayout coordinatorLayout;
    private RecyclerView rvPerson;
    private View loadingProgressView;
    private MaterialSearchBar searchBar;
    private View emptyView;
    private PersonListAdapter personListAdapter;
    private String searchQuery;

    public static PersonFragment newInstance() {
        PersonFragment fragment = new PersonFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        BaseApplication.getInstance().getApplicationComponent().inject(this);
        initViews(view);
        presenter.startNow(this, searchQuery);
        return view;
    }

    private void initViews(View view) {
        coordinatorLayout = view.findViewById(R.id.drawer_layout);
        rvPerson = view.findViewById(R.id.rvPersonPopular);
        searchBar = view.findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        setupPersonList();
        loadingProgressView = view.findViewById(R.id.loadingProgressView);
        emptyView = view.findViewById(R.id.emptyView);
    }

    private void setupPersonList() {
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false);
        rvPerson.setLayoutManager(layoutManager);

        personListAdapter = new PersonListAdapter(getActivity());
        personListAdapter.setPersonSelectionListener(new PersonListAdapter.OnPersonSelectionListener() {
            @Override
            public void onPersonSelected(PersonModel model, View view, int position) {
                personSelected(model, view, position);
            }
        });
        rvPerson.setAdapter(personListAdapter);

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
                    fetchNext();
                }
            }
        };
        rvPerson.addOnScrollListener(scrollListener);
    }

    private void fetchNext() {
        presenter.fetchNext(searchQuery);
    }

    private void personSelected(PersonModel model, View view, int position) {
        Intent intent = new Intent(getContext(), PersonDetailActivity.class);
        intent.putExtra(PersonDetailActivity.EXTRA_PERSON_ID, model.getId());
        intent.putExtra(PersonDetailActivity.EXTRA_PERSON_NAME, model.getName());
        String transitionName = getString(R.string.thumb_transition_name);
        View transitionView = view.findViewById(R.id.ivThumbnailPerson);
        ViewCompat.setTransitionName(transitionView, transitionName);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), transitionView, transitionName);
        startActivity(intent, options.toBundle());
        showLoadingProgress();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!presenter.isLoading()) {
            hideLoadingProgress();
        }
    }

    @Override
    public void showPerson(List<PersonModel> personList) {
        if (personList == null || personList.size() == 0) {
            showEmptyView();
            return;
        }

        hideEmptyView();
        personListAdapter.setPersons(personList);
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
    public void notifyPersonListChanged() {
        personListAdapter.notifyMoviesListChanged();
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
            searchQuery = null;
            presenter.fetchFirst(searchQuery);
            hideSoftKeyboard(coordinatorLayout);
        }
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        searchQuery = text.toString();
        presenter.fetchFirst(text.toString());
        hideSoftKeyboard(coordinatorLayout);
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
