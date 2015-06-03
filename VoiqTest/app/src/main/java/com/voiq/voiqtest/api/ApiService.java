package com.voiq.voiqtest.api;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.voiq.voiqtest.eventbus.LogInRequest;
import com.voiq.voiqtest.eventbus.LogInSuccess;
import com.voiq.voiqtest.eventbus.NetworkError;
import com.voiq.voiqtest.eventbus.RegisterRequest;
import com.voiq.voiqtest.eventbus.RegisterSuccess;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by juanchaparro on 31/05/15.
 * Api Service to listen for events on the Event Bus, and respond accordingly
 */
public class ApiService {

    /**
     * Endpoints for making API requests
     */
    private VoiqEndpoints mEndpoints;

    /**
     * Event Bus instance. Notice that the injection is not annotated here, as the
     * object graph will take care of that
     */
    private Bus mBus;

    /**
     * Constructor
     * @param endpoints Retrofit-powered REST client
     * @param bus Event Bus instance
     */
    public ApiService(VoiqEndpoints endpoints, Bus bus)
    {
        mEndpoints=endpoints;
        mBus=bus;
    }

    /**
     * Listen and respond to log in request events
     * @param event The event containing the request parameters.
     */
    @Subscribe
    public void onLogInRequest(LogInRequest event)
    {
        /*
        Perform the request and create a callback
         */
        mEndpoints.LogIn(event.getEmail(), event.getPassword(), new Callback<LogInResponse>() {
            @Override
            public void success(LogInResponse logInResponse, Response response) {
                //If all goes well, post the success event.
                LogInSuccess lis= new LogInSuccess();
                lis.setResponse(logInResponse);
                mBus.post(lis);
            }

            @Override
            public void failure(RetrofitError error) {
                //If the request fails, send the network error event.
                NetworkError ne= new NetworkError();
                mBus.post(ne);
            }
        });
    }

    @Subscribe
    public void onRegisterRequest(RegisterRequest event)
    {
        mEndpoints.CreateUser(event.getUsrPicName(), event.getUserId(), event.getCampaignId(),
                event.getUndercover(), event.getFirstName(), event.getLastName(),
                event.getEmail(), event.getBirth(), event.getZipCode(), event.getPhone(),
                event.getAndroidDevice(), event.getPassword1(), event.getPassword2(),
                new Callback<RegisterResponse>() {
                    @Override
                    public void success(RegisterResponse registerResponse, Response response) {
                        RegisterSuccess rs=new RegisterSuccess();
                        rs.setResponse(registerResponse);
                        mBus.post(rs);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        NetworkError ne= new NetworkError();
                        mBus.post(ne);
                    }
                });
    }
}
