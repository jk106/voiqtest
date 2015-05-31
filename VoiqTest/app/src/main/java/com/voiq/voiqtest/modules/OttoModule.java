package com.voiq.voiqtest.modules;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import com.voiq.voiqtest.app.VoiqTestApplication;
import com.voiq.voiqtest.fragments.MainFragment;
import com.voiq.voiqtest.fragments.RegisterFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by juanchaparro on 31/05/15.
 */
@Module(
        injects = {
                MainFragment.class,
                RegisterFragment.class,
                VoiqTestApplication.class
        }, complete=false)
public class OttoModule {
    @Provides
    @Singleton
    public Bus provideBus() {
        // our event bus running on any thread
        return new Bus(ThreadEnforcer.ANY);
    }


}

