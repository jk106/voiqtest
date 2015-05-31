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
 */
@Module(includes={
        OttoModule.class,
        RetrofitModule.class
}, injects={VoiqTestApplication.class}, complete=false)
public class ApiModule {
    @Provides
    public ApiService getApiService(RestAdapter r, Bus b)
    {
        return new ApiService(r.create(VoiqEndpoints.class), b);
    }

}

