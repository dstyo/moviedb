package com.arkadroid;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.arkadroid.dagger.components.AppComponent;
import com.arkadroid.dagger.components.DaggerAppComponent;
import com.arkadroid.dagger.modules.AppModule;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.04.01
 */
public class BaseApplication extends Application {
    private static BaseApplication instance;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private AppComponent applicationComponent;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        applicationComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule())
                .build();

    }

    public AppComponent getApplicationComponent() {
        return applicationComponent;
    }
}
