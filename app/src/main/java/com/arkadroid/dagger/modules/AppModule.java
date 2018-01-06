package com.arkadroid.dagger.modules;

import com.arkadroid.presenters.MovieDetailPresenter;
import com.arkadroid.presenters.MoviePresenter;
import com.arkadroid.presenters.PersonDetailPresenter;
import com.arkadroid.presenters.PersonPresenter;
import com.arkadroid.repositories.MovieRepository;
import com.arkadroid.repositories.PersonRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.05.01
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    public MoviePresenter providesHomePresenter() {
        return new MoviePresenter();
    }

    @Provides
    @Singleton
    public MovieDetailPresenter providesMovieDetailPresenter() {
        return new MovieDetailPresenter();
    }

    @Provides
    @Singleton
    public PersonDetailPresenter providesPersonDetailPresenter() {
        return new PersonDetailPresenter();
    }

    @Provides
    @Singleton
    public MovieRepository providesMoviesRepository() {
        return new MovieRepository();
    }

    @Provides
    @Singleton
    public PersonPresenter providesPersonPresenter(){
        return new PersonPresenter();
    }

    @Provides
    @Singleton
    public PersonRepository providesPersonRepository() {
        return new PersonRepository();
    }

}