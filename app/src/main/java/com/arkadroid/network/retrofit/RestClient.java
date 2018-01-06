package com.arkadroid.network.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.04.01
 */
public class RestClient {

    private static ApiInterface restClient;

    static {
        setupRestClient();
    }

    private RestClient() {

    }

    public static ApiInterface get() {
        return restClient;
    }

    public static ApiInterface get(String baseUrl) {
        Retrofit retrofit = getRetrofitClient(baseUrl);
        return retrofit.create(ApiInterface.class);
    }

    private static void setupRestClient() {

        Retrofit retrofit = getRetrofitClient(AppUrls.BASE_URL);
        restClient = retrofit.create(ApiInterface.class);
    }

    public static Retrofit getRetrofitClient(String url) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}
