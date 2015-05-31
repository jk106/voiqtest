package com.voiq.voiqtest.app;

import android.app.Application;

import com.squareup.otto.Bus;
import com.voiq.voiqtest.api.ApiService;
import com.voiq.voiqtest.modules.ApiModule;
import com.voiq.voiqtest.modules.OttoModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;

/**
 * Created by juanchaparro on 31/05/15.
 */
public class VoiqTestApplication extends Application{

    @Inject
    ApiService mApiService;
    private ObjectGraph objectGraph;
    @Inject
    Bus mBus;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ObjectGraph getObjectGraph()
    {
        if(objectGraph==null)
        {
            objectGraph = ObjectGraph.create(getModules().toArray());
            objectGraph.inject(this);
            mBus.register(mApiService);
            mBus.register(this); //listen for "global" events
        }
        return objectGraph;
    }

    public void setObjectGraph(ObjectGraph objectGraph)
    {
        this.objectGraph=objectGraph;
    }
    protected List<Object> getModules() {
        List<Object> result = new ArrayList<Object>();
        result.add(new ApiModule());
        result.add(new OttoModule());
        return result;
    }

    public Bus getBus()
    {
        return mBus;
    }
}
