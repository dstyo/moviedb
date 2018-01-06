package com.arkadroid;

import android.support.test.runner.AndroidJUnit4;

import com.arkadroid.repositories.MovieRepository;

import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AppInstrumentedTest {

    private MovieRepository movieRepository;

    public AppInstrumentedTest() {
        movieRepository = new MovieRepository();
    }
}
