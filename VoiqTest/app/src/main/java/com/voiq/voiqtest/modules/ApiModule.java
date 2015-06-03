package com.voiq.voiqtest.modules;

import com.squareup.otto.Bus;
import com.voiq.voiqtest.api.ApiService;
import com.voiq.voiqtest.api.VoiqEndpoints;
import com.voiq.voiqtest.app.VoiqTestApplication;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by juanchaparro on 31/05/15.
 * Api Module for Dagger DI
 * It includes the Otto and Retrofit modules, as they are required to create an Api Service
 * It injects the VoiqTestApplication, for its creation
 */
@Module(includes={
        OttoModule.class,
        RetrofitModule.class
}, injects={VoiqTestApplication.class}, complete=false)
public class ApiModule {
    @Provides
    public ApiService getApiService(RestAdapter r, Bus b)
    {
        //The REST client is created from the endpoints interface
        return new ApiService(r.create(VoiqEndpoints.class), b);
    }

}

