package com.arkadroid.repositories;

import android.util.Log;

import com.arkadroid.models.PersonDetailModel;
import com.arkadroid.models.PersonResultModel;
import com.arkadroid.network.entity.PersonDetailParser;
import com.arkadroid.network.entity.PersonResponseParser;
import com.arkadroid.network.request.PersonRequest;
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
 * @since 2018.05.01
 */
public class PersonRepository {
    private static final String API_KEY = AppUrls.API_KEY;

    public Observable<PersonDetailModel> getPersonDetail(int id) {

        Observable<Response<PersonDetailParser>> observable = RestClient.get().getPersonDetails(id, API_KEY);

        observable = observable.subscribeOn(Schedulers.newThread());
        observable = observable.observeOn(AndroidSchedulers.mainThread());

        return observable.map(new Function<Response<PersonDetailParser>, PersonDetailModel>() {

            @Override
            public PersonDetailModel apply(@NonNull Response<PersonDetailParser> response) throws Exception {

                if (response.isSuccessful()) {
                    PersonDetailParser parser = response.body();
                    return new PersonDetailModel(parser);
                }

                return null;
            }
        });
    }

    public Observable<PersonResultModel> getPersons(String query, int page) {

        PersonRequest request = new PersonRequest();
        request.setPage(page);
        if (query == null) {
            return getPersons(request, RestClient.get().personPopular(API_KEY, page));
        }else {
            return getPersons(request, RestClient.get().searchPerson(API_KEY, query, page));
        }
    }

    private Observable<PersonResultModel> getPersons(PersonRequest personRequest, Observable<Response<PersonResponseParser>> personObservable) {

        int page = personRequest.getPage();

        Log.d("PersonRequest", "PersonRequest" +
                "\npage : " + page);

        personObservable = personObservable.subscribeOn(Schedulers.newThread());
        personObservable = personObservable.observeOn(AndroidSchedulers.mainThread());

        return personObservable.map(new Function<Response<PersonResponseParser>, PersonResultModel>() {

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
}
