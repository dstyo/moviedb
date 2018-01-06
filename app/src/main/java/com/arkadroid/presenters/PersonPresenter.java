package com.arkadroid.presenters;

import com.arkadroid.BaseApplication;
import com.arkadroid.models.PersonModel;
import com.arkadroid.models.PersonResultModel;
import com.arkadroid.repositories.PersonRepository;

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
public class PersonPresenter {

    @Inject
    PersonRepository repository;

    private int page = 1;

    private int totalPage = 1;


    private PersonView personView;

    private List<PersonModel> personsList = new ArrayList<>();

    private boolean loading;

    public PersonPresenter() {
        BaseApplication.getInstance().getApplicationComponent().inject(this);
    }

    public void startNow(PersonView personView, String query) {
        this.personView = personView;
        fetchFirst(query);
    }

    public void fetchFirst(String query) {
        resetFilters();
        fetchPersonPopular(query, page);
    }

    public void fetchNext(String query) {
        int nextPage = ++page;
        if (nextPage <= totalPage) {
            fetchPersonPopular(query, nextPage);
        }
    }

    private void resetFilters() {
        page = 1;
        personsList.clear();
        personView.notifyPersonListChanged();
    }

    private void fetchPersonPopular(final String query, final int nextPage) {
        Observable<PersonResultModel> observable;
        if (query == null) {
            observable = repository.getPersons(null, nextPage);
        } else {
            observable = repository.getPersons(query, nextPage);
        }

        observable.subscribe(new Observer<PersonResultModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                loading = true;
                personView.showLoadingProgress();
            }

            @Override
            public void onNext(@NonNull PersonResultModel personResultModel) {

                if (personResultModel == null) {
                    return;
                }
                page = personResultModel.getPage();
                totalPage = personResultModel.getTotalPages();

                List<PersonModel> newList = personResultModel.getPersonModelList();
                if (newList != null) {
                    if (personsList == null || personsList.size() == 0) {
                        personsList = newList;
                        personView.showPerson(personsList);
                    } else {
                        personsList.addAll(newList);
                        personView.notifyPersonListChanged();
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

                personView.onError(e);
            }

            @Override
            public void onComplete() {
                loading = false;
                personView.hideLoadingProgress();
            }
        });
    }

    public boolean isLoading() {
        return loading;
    }

    public interface PersonView {

        void showPerson(List<PersonModel> personList);

        void notifyPersonListChanged();

        void showLoadingProgress();

        void hideLoadingProgress();

        void onError(Throwable e);
    }
}
