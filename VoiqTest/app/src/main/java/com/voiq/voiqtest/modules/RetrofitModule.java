package com.voiq.voiqtest.modules;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by juanchaparro on 31/05/15.
 */
@Module(
        library=true
)
public class RetrofitModule {

    public final static String VOIQ_URL = "http://54.205.46.179/app/controller";

    @Provides
    @Singleton
    public RestAdapter getRestAdapter() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(VOIQ_URL)
                .setClient(new OkClient(okHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter;
    }
}
