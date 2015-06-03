package com.voiq.voiqtest.modules;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by juanchaparro on 31/05/15.
 * Retrofit REST client module
 */
@Module(
        library=true
)
public class RetrofitModule {

    //Backend URL
    public final static String VOIQ_URL = "http://54.205.46.179/app/controller";

    @Provides
    @Singleton
    public RestAdapter getRestAdapter() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(VOIQ_URL)
                //Use okHttpClient for speed
                .setClient(new OkClient(okHttpClient))
                //Log level just in case, not for release configuration
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter;
    }
}
