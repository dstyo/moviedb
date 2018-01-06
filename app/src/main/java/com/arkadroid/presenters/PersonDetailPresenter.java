package com.arkadroid.presenters;

import android.util.Log;

import com.arkadroid.BaseApplication;
import com.arkadroid.models.PersonDetailModel;
import com.arkadroid.repositories.PersonRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.04.01
 */
public class PersonDetailPresenter {

    @Inject
    PersonRepository repository;

    private PersonDetailView personDetailView;

    private PersonDetailModel personDetail;

    private int personId;

    public PersonDetailPresenter() {

        BaseApplication.getInstance().getApplicationComponent().inject(this);
    }

    public void startNow(PersonDetailView view, int movieId) {
        this.personDetailView = view;
        this.personId = movieId;
        if (personDetail != null) {
            Log.d("MovieDetailPresenter", "Showing cashed ");
            view.showPersonDetail(personDetail);
            return;
        }
        fetchMovie(movieId);
    }

    public void fetchMovie(int id) {

        Log.d("PersonDetailPresenter", "Fetching from API");

        Observable<PersonDetailModel> observable = repository.getPersonDetail(id);
        observable.subscribe(new Observer<PersonDetailModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                personDetailView.showLoadingProgress();
            }

            @Override
            public void onNext(@NonNull PersonDetailModel model) {

                if (model == null) {
                    return;
                }
                personDetail = model;
                personDetailView.showPersonDetail(model);
            }

            @Override
            public void onError(@NonNull Throwable e) {

                personDetailView.onError(e);
            }

            @Override
            public void onComplete() {
                personDetailView.hideLoadingProgress();
            }
        });
    }

    public void cleanUp() {
        personId = 0;
        personDetail = null;
    }

    public interface PersonDetailView {

        void showPersonDetail(PersonDetailModel personDetailModel);

        void showLoadingProgress();

        void hideLoadingProgress();

        void onError(Throwable e);
    }
}
