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
 * Application for the app domain
 */
public class VoiqTestApplication extends Application{

    /**
     * Api Service instance, injected with Dagger
     */
    @Inject
    ApiService mApiService;

    /**
     * Object Graph for Dependency Injection across the entire app domain
     */
    private ObjectGraph objectGraph;

    /**
     * Event Bus instance, injected with Dagger
     */
    @Inject
    Bus mBus;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Get the object graph instance of the app, to allow other classes to inject themselves into it
     * @return the object graph
     */
    public ObjectGraph getObjectGraph()
    {
        if(objectGraph==null)
        {
            /*
            If there is no object graph, we create it.
            Then we register the Api Service and the app in the bus, so they can listen to events
             */
            objectGraph = ObjectGraph.create(getModules().toArray());
            objectGraph.inject(this);
            mBus.register(mApiService);
            mBus.register(this); //listen for "global" events
        }
        return objectGraph;
    }

    /**
     * Set the object graph. This is particularly important for testing purposes
     * @param objectGraph the object graph to be used in the app
     */
    public void setObjectGraph(ObjectGraph objectGraph)
    {
        this.objectGraph=objectGraph;
    }

    /**
     * retrieve the list of modules to be injected in the object graph at app creation
     * @return the list
     */
    protected List<Object> getModules() {
        List<Object> result = new ArrayList<Object>();
        result.add(new ApiModule());
        result.add(new OttoModule());
        return result;
    }
}
