package com.arkadroid.dagger.components;

import com.arkadroid.activity.HomeActivity;
import com.arkadroid.activity.MovieDetailActivity;
import com.arkadroid.activity.PersonDetailActivity;
import com.arkadroid.dagger.modules.AppModule;
import com.arkadroid.fragment.MovieFragment;
import com.arkadroid.fragment.PersonFragment;
import com.arkadroid.presenters.MoviePresenter;
import com.arkadroid.presenters.MovieDetailPresenter;
import com.arkadroid.presenters.PersonDetailPresenter;
import com.arkadroid.presenters.PersonPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.05.01
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(HomeActivity homeActivity);
    void inject(MovieFragment movieFragment);
    void inject(PersonFragment personFragment);
    void inject(MoviePresenter homePresenter);
    void inject(PersonPresenter personPresenter);
    void inject(MovieDetailPresenter movieDetailPresenter);
    void inject(MovieDetailActivity movieDetailActivity);
    void inject(PersonDetailPresenter personDetailPresenter);
    void inject(PersonDetailActivity personDetailActivity);
}